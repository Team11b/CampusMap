package WPI.CampusMap.Frontend.UI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.UIManager;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.twilio.sdk.TwilioRestException;

import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.SMS.SMSClient;
import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Point.AllPoints;
import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.PathPlanning.AStarPathProcessor;
import WPI.CampusMap.Backend.PathPlanning.BetweenMapsProcessor;
import WPI.CampusMap.Backend.PathPlanning.DistanceProcessor;
import WPI.CampusMap.Backend.PathPlanning.LocationPref;
import WPI.CampusMap.Backend.PathPlanning.NodeProcessor;
import WPI.CampusMap.Backend.PathPlanning.NotEnoughPointsException;
import WPI.CampusMap.Backend.PathPlanning.Path;
import WPI.CampusMap.Backend.PathPlanning.Path.Section;
import WPI.CampusMap.Backend.PathPlanning.PathFinder;
import WPI.CampusMap.Backend.PathPlanning.PathNotFoundException;
import WPI.CampusMap.Backend.PathPlanning.WeatherHeuristicProcessor;
import WPI.CampusMap.Backend.PathPlanning.Route.Route;
import WPI.CampusMap.Frontend.Graphics.User.UserGraphicalMap;
import WPI.CampusMap.Frontend.Graphics.User.UserPointGraphicsObject;

public class UserMode extends UIMode {
	private UserGraphicalMap graphicalMap;

	private LinkedList<IPoint> route = new LinkedList<>();
	private HashSet<IPoint> routeSet = new HashSet<>();

	private Path routedPath;
	private LocationPref pref;

	public UserMode(AppMainWindow window) {
		super(window);
	}

	@Override
	public void gotoPoint(String name) {
		IPoint point = AllPoints.getInstance().getPoint(name);
		loadMap(point.getMap());
	}

	/**
	 * Gets the routed path sections for the loaded map.
	 * 
	 * @return The routed path sections for the loaded map.
	 */
	public LinkedList<Section> getRoutedPath() {
		if (routedPath == null)
			return new LinkedList<>();
		return routedPath.getSections(graphicalMap.getMap());
	}

	public void onRouteButton() {
		NodeProcessor nP = new DistanceProcessor(new BetweenMapsProcessor(new WeatherHeuristicProcessor(null, pref)));
		AStarPathProcessor processor = new AStarPathProcessor(nP);

		IPoint[] points = new IPoint[route.size()];
		route.toArray(points);

		try {
			routedPath = PathFinder.getPath(points, processor);
			graphicalMap.setPathSections(getRoutedPath());
			graphicalMap.setShownSection(getRoutedPath().getFirst());

			getWindow().setRoute(routedPath);
		} catch (PathNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (NotEnoughPointsException n) {
			JOptionPane.showMessageDialog(null, n.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}

		getWindow().clearDestinations();
		clearRoute();
	}

	public void selectRouteSection(Section section) {
		graphicalMap.setShownSection(section);
	}

	public void onClearButton() {
		// destinations.resetLastPoint();
		clearRoute();
		// UserPathGraphicsObject.deleteAll();
	}

	public void onAddDest(String destName) {
		System.out.println("AddDest");
	}

	public void onPointAddedToRoute(UserPointGraphicsObject newPoint) {
		onPointAddedToRoute(newPoint.getRepresentedObject());

		if (newPoint != null && routeSet.contains(newPoint.getRepresentedObject()))
			getWindow().addDestination(newPoint);
	}

	public void onPointAddedToRoute(IPoint newPoint) {
		if (newPoint == null)
			return;

		if (routedPath != null) {
			clearRoute();
			routedPath = null;
			graphicalMap.setPathSections(getRoutedPath());
		}

		if (!routeSet.contains(newPoint)) {
			System.out.println("Added " + newPoint + " to route");
			routeSet.add(newPoint);
			route.add(newPoint);
		}
	}

	public void clearRoute() {
		routeSet.clear();
		route.clear();

		getWindow().clearDestinations();

		// TODO: Call UI code
	}

	public void onPointRemovedFromRoute(UserPointGraphicsObject point) {
		onPointRemovedFromRoute(point.getRepresentedObject());
	}

	public void onPointRemovedFromRoute(IPoint point) {
		if (point == null)
			return;

		if (routedPath != null) {
			clearRoute();
			routedPath = null;
			graphicalMap.setPathSections(null);
		}

		if (routeSet.contains(point)) {
			routeSet.remove(point);
			route.remove(point);
		}
	}

	public boolean containsInRoute(UserPointGraphicsObject point) {
		return routeSet.contains(point.getRepresentedObject());
	}

	public boolean isRouteStart(UserPointGraphicsObject point) {
		return route.getFirst().equals(point.getRepresentedObject());
	}

	public boolean isRouteEnd(UserPointGraphicsObject point) {
		return route.getLast().equals(point.getRepresentedObject());
	}

	public void onWeatherChosen(LocationPref option) {
		System.out.println("Weather chosen is " + option);
		this.pref = option;
	}

	public void onPrint() {
		System.out.println("Print");
	}

	public void onPdf() {
		System.out.println("PDF");
	}

	public void onTxt() {
		JFileChooser chooser = new JFileChooser();
		chooser.showSaveDialog(chooser);
		if (chooser.getSelectedFile() == null) {
			System.out.println("Cancel pressed on txt");
			return;
		}
		File destination = chooser.getSelectedFile();
		FileWriter write;
		String directions = (new Route(routedPath).toString());
		try {
			write = new FileWriter(destination);
			PrintWriter printLine = new PrintWriter(write);
			printLine.print(directions);
			printLine.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		System.out.println("TXT");
	}

	public String popUp() {
		final JFrame parent = new JFrame();
		// parent.add(button);
		parent.pack();
		parent.setVisible(true);

		String email = JOptionPane.showInputDialog(parent, "What is your email address?", null);
		parent.setVisible(false);
		return email;

	}

	public void onEmail() {
		Email email = new SimpleEmail();
		email.setDebug(true);
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.setAuthenticator(new DefaultAuthenticator("team0011b@gmail.com", "SoftEng15"));
		try {
			email.getMailSession().getProperties().put("mail.smtp.auth", "true");
			email.getMailSession().getProperties().put("mail.debug", "true");
			email.getMailSession().getProperties().put("mail.smtp.port", "587");
			email.getMailSession().getProperties().put("mail.smtp.socketFactory.port", "587");
			email.getMailSession().getProperties().put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			email.getMailSession().getProperties().put("mail.smtp.socketFactory.fallback", "false");
			email.getMailSession().getProperties().put("mail.smtp.starttls.enable", "true");
			email.setFrom("team0011b@gmail.com", "Team 0011b");
			email.setSubject("Campus Directions");
			Route sendingRoute = new Route(routedPath);
			email.setMsg(sendingRoute.toString());
			String emailAddress = popUp();
			email.addTo(emailAddress, "WPI Campus Map User");
			email.send();
			System.out.println("Email sent");
		} catch (EmailException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			System.out.println("Could not send email.");
			e.printStackTrace();
		}
	}

	public void onSMS() {
		System.out.println("SMS");
		try {
			Route sendingRoute = new Route(routedPath);
			SMSClient.SendText("+18184411799", sendingRoute.toString());
		} catch (TwilioRestException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void outputDirections() {

	}

	@Override
	public void onDraw(Graphics2D graphics) {
		if (graphicalMap != null)
			graphicalMap.onDraw(graphics);
	}

	@Override
	public void onMouseClickMap(MouseEvent e) {
		if (graphicalMap != null)
			graphicalMap.mouseClick(e);
	}

	@Override
	public void onMouseEnterMap(MouseEvent e) {
		if (graphicalMap != null)
			graphicalMap.mouseEnter(e);
	}

	@Override
	public void onMouseLeaveMap(MouseEvent e) {
		if (graphicalMap != null)
			graphicalMap.mouseExit(e);
	}

	@Override
	public void onMouseMoveOverMap(MouseEvent e) {
		if (graphicalMap != null)
			graphicalMap.mouseMove(e);
	}

	@Override
	public void onMouseDraggedOverMap(MouseEvent e) {
		if (graphicalMap != null)
			graphicalMap.mouseDrag(e);
	}

	@Override
	public void loadMap(String mapName) {
		if (mapName == null)
			return;

		if (graphicalMap != null) {
			if (graphicalMap.getMap().equals(AllMaps.getInstance().getMap(mapName)))
				return;

			graphicalMap.unload();
		}
		graphicalMap = new UserGraphicalMap(mapName, this);
		graphicalMap.spawnMap();
	}

	public void onAbout() {
		JOptionPane aboutWindow = new JOptionPane();
		JFrame aboutFrame = new JFrame("About Campus Mapper");
		aboutFrame.setBounds(300, 300, 700, 300);
		aboutFrame.setLayout(new FlowLayout());
		JLabel textLabel = new JLabel();
		textLabel.setText("<html><p style=\"text-align: center;\">"+
				"<p style=\"text-align: center;\"><span style=\"font-size: medium;\"><strong>CS 3733: Software Engineering B-Term 2015</strong></span></p>\n"+
				"<p style=\"text-align: center;\"><span style=\"font-size: medium;\">Prof. Wilson Wong</span></p>\n"+
				"<p style=\"text-align: center;\"><span style=\"font-size: medium;\">Worcester Polytechnic Institute</span></p>\n"+
				"<p style=\"text-align: center;\">&nbsp;</p>\n"+
				"<p style=\"text-align: left;\"><span style=\"font-size: medium;\"><b>Team 0011b:</b></span></p>\n"+
				"<p style=\"text-align: left;\"><span style=\"font-size: medium;\"><b>Lukas Hunter: Team Coach</b></span></p>\n"+
				"<p style=\"text-align: left;\"><span style=\"font-size: medium;\">Chris Cormier: Testing Engineer (Iteration 1 and 2)</span></p>\n"+
				"<p style=\"text-align: left;\"><span style=\"font-size: medium;\"><span style=\"font-size: medium;\">Will Craft: Lead Software Engineer (Iteration 3 and 4)</span></span></p>\n"+
				"<p style=\"text-align: left;\"><span style=\"font-size: medium;\">Gavin Hayes: Test Engineer (Iteration 3 and 4)</span></p>\n"+
				"<p style=\"text-align: left;\"><span style=\"font-size: medium;\">Michael LoTurco: Product Owner (Iteration 1 and 2)</span></p>\n"+
				"<p style=\"text-align: left;\"><span style=\"font-size: medium;\"><span style=\"font-size: medium;\">Benny Peake: Product Manager (Iteration 1 and 2), UI Lead (3 and 4)</span></span></p>\n"+
				"<p style=\"text-align: left;\"><span style=\"font-size: medium;\"><span style=\"font-size: medium;\">Will Spurgeon: UI Lead (Iteration 1 and 2)</span></span></p>\n"+
				"<p style=\"text-align: left;\"><span style=\"font-size: medium;\">Max Stenke: Product Owner (Iteration 3 and 4)</span></p>\n"+
				"<p style=\"text-align: left;\"><span style=\"font-size: medium;\"><span>Jake Zizmor: Lead Software Engineer (Iteration 1 and 2), Prodcut Manager (Iteration 3 and 4)</span></span></p>\n"+
				"<p style=\"text-align: left;\"><span style=\"font-size: medium;\">&nbsp;</span></p>\n"+
				"<p style=\"text-align: left;\">&nbsp;</p>\n"+
				"<p style=\"text-align: center;\">&nbsp;</p>\n"+
				"<p style=\"text-align: center;\">&nbsp;</p>\n"+
				"<p style=\"text-align: center;\">&nbsp;</p></html>");
		aboutFrame.add(textLabel);
		aboutWindow.createDialog(aboutFrame, "About Campus Mapper");
		aboutFrame.setVisible(true);
		aboutWindow.setVisible(true);

	}

	public void onGuide() {
		// TODO Auto-generated method stub
		JOptionPane aboutWindow = new JOptionPane();
		JFrame guideFrame = new JFrame("User Guide");
		JLabel textLabel = new JLabel();
		textLabel.setText(
				"<html><p style=\"text-align: center;\"><span style=\"font-size: medium;\"><strong>About Campus Mapper</strong></span></p>\n"
						+ "<p style=\"text-align: center;\"><span style=\"font-size: medium;\"><strong>CS 3733: Software Engineering</strong></span></p>\n"
						+ "<p style=\"text-align: center;\"><span style=\"font-size: medium;\">Prof. Wilson Wong</span></p>\n"
						+ "<p style=\"text-align: center;\"><span style=\"font-size: medium;\">Worcester Polytechnic Institute</span></p>\n"
						+ "<p style=\"text-align: center;\">&nbsp;</p>\n"
						+ "<p style=\"text-align: left;\"><span style=\"font-size: medium;\">Team 0011b:</span></p>\n"
						+ "<p style=\"text-align: left;\"><span style=\"font-size: medium;\">Chris Cormier</span></p>\n"
						+ "<p style=\"text-align: left;\"><span style=\"font-size: medium;\"><span style=\"font-size: medium;\">Will Craft</span></span></p>\n"
						+ "<p style=\"text-align: left;\"><span style=\"font-size: medium;\">Gavin Hayes</span></p>\n"
						+ "<p style=\"text-align: left;\"><span style=\"font-size: medium;\">Michael LoTurco</span></p>\n"
						+ "<p style=\"text-align: left;\"><span style=\"font-size: medium;\"><span style=\"font-size: medium;\">Benny Peake</span></span></p>\n"
						+ "<p style=\"text-align: left;\"><span style=\"font-size: medium;\"><span style=\"font-size: medium;\">Will Spurgeon</span></span></p>\n"
						+ "<p style=\"text-align: left;\"><span style=\"font-size: medium;\">Max Stenke</span></p>\n"
						+ "<p style=\"text-align: left;\"><span style=\"font-size: medium;\"><span>Jake Zizmor</span></span></p>\n"
						+ "<p style=\"text-align: left;\"><span style=\"font-size: medium;\">&nbsp;</span></p>\n"
						+ "<p style=\"text-align: left;\">&nbsp;</p>\n"
						+ "<p style=\"text-align: center;\">&nbsp;</p>\n"
						+ "<p style=\"text-align: center;\">&nbsp;</p>\n"
						+ "<p style=\"text-align: center;\">&nbsp;</p></html>");
		guideFrame.add(textLabel);
		guideFrame.setBounds(100, 100, 850, 750);
		guideFrame.setVisible(true);
		guideFrame.setLayout(new FlowLayout());

	}
}
