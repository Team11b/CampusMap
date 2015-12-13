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
	public void onModeEntered() {
		// Execute changes to UI

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
		aboutFrame.setSize(500, 500);
		aboutFrame.setLayout(new FlowLayout());
		JLabel textLabel = new JLabel();
		textLabel.setText("<html>Worcester Polytechnic Institute<br>CS3733 2015 B-Term<br>Team 0011b<br>Prof. Wilson Wong</html>");
		aboutFrame.add(textLabel);
		//aboutFrame.getContentPane().add(new JLabel("Worcester Polytechnic Institute"));
		//aboutFrame.getContentPane().add(new JLabel("CS3733 2015 B-Term"));
		//aboutFrame.getContentPane().add(new JLabel("Team 0011b"));
		//aboutFrame.getContentPane().add(new JLabel("Prof. Wilson Wong"));
		// aboutFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aboutWindow.createDialog(aboutFrame, "About Campus Mapper");
		aboutFrame.setVisible(true);
		aboutWindow.setVisible(true);

	}

	public void onGuide() {
		// TODO Auto-generated method stub
		JOptionPane aboutWindow = new JOptionPane();
		JFrame guideFrame = new JFrame("User Guide");
		JTextPane textLabel = new JTextPane();
		textLabel.setContentType("text/html");
		//textLabel.setWrapStyleWord(true);
		//textLabel.setLineWrap(true);
		textLabel.setOpaque(false);
		textLabel.setEditable(false);
		textLabel.setFocusable(false);
		textLabel.setBackground(UIManager.getColor("Label.background"));
	    textLabel.setFont(UIManager.getFont("Label.font"));
	    textLabel.setBorder(UIManager.getBorder("Label.border"));
		textLabel.setText("<html><h1><span style=\"font-family: 'arial black', 'avant garde'; font-size: large;\">Campus Mapper <span style=\"font-family: 'arial black', 'avant garde';\">User</span> Guide</span></strong></p>"+
"<p><span style=\"font-family: 'arial black', 'avant garde';\"><strong>Find a route:</strong></span></p>"+
"<ol>"+
"    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Navigate to the map with the desired starting point. </span><span style=\"font-family: arial, helvetica, sans-serif;\"><br>Maps can be selected by going to the \"Maps\" menu and selecting the desired building and floor.</span></li>"+
"    <li><span style=\"font-family: arial, helvetica, sans-serif;\">The red dots on the maps represent potential starting or ending positions. Click on one of the red dots.</span></li>"+
"    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Select another point on a map. You may navigate to a different floor or building if you wish to.</span></li>"+
"    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Additional points may be selected on any of the maps before a route is found.</span></li>"+
"    <li><span style=\"font-family: arial, helvetica, sans-serif;\">When you have selected all of the points you would like to visit, press the \"Route Me!\" button.<br>A route will be drawn between all of your points on the map and textual instructions will appear in the \"Directions\" box.</span></li>"+
"</ol>"+
"<p><span style=\"font-family: 'arial black', 'avant garde';\"><strong><strong><strong><strong><strong><strong>Navigate through a route:</strong></strong></strong></strong></strong></strong></span></p>"+
"<ol>"+
"    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Once a route has been created, you may step through it by selecting an instruction <br>in the \"Directions\" box and pressing the \"Next\" and \"Previous\" buttons.</span></li>"+
"    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Stepping between buildings will cause the map view to change to the correct building. <br>Likewise, selecting a specific route will highlight that path within the map view.</span></li>"+
"    <li><span style=\"font-family: arial, helvetica, sans-serif;\">You may expand or hide the instructions within each route in the \"Directions\" box.</span></li>"+
"</ol>"+
"<p><span style=\"font-family: 'arial black', 'avant garde';\"><strong><strong><strong><strong>Editing a route:</strong></strong></strong></strong></span></p>"+
"<ol>"+
"    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Routes may be edited by removing points listed in the \"Destinations\" box. <br>Once two or points have been selected, click on the button with an \"X\" on it to remove that point from your route. </span></li>"+
"</ol>"+
"<p><span style=\"font-family: 'arial black', 'avant garde';\"><strong><strong><strong><strong>Building a map in Dev Mode:</strong></strong></strong></strong></span></p>"+
"<ol>"+
"    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Enter Dev Mode by selecting Settings->Dev Mode. <br>You may exit Dev Mode at any time by deselecting Settings->Dev Mode.</span></li>"+
"    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Select a the map you would like to work on from the \"Map\" dropdown.</span></li>"+
"    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Create nodes by clicking on the \"Create\" button. You may now click anywhere on the map to add a node.</span></li>"+
"    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Create edges by clicking on the \"Edge\" button. Select the two nodes you would like to connect. <br>Once the second node is selected, an edge will be created between the two nodes.</span></li>"+
"    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Delete nodes by clicking on the \"Delete\" button. If you click on an existing node, it will be deleted.</span></li>"+
"    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Delete edges by clicking on the \"Delete Edge\" button. Click on the two nodes you would like to dissconnect. </span></li>"+
"    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Once you have made all of your changes to the map, click on the \"Save\" button to save the changes to disk.</span></li>"+
"</ol>"+
"<p> </p></html>");
		guideFrame.add(textLabel);
		guideFrame.setBounds(100, 100, 850, 750);
		guideFrame.setVisible(true);
		guideFrame.setLayout(new FlowLayout());
		
	}
}
