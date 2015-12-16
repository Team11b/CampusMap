package WPI.CampusMap.Frontend.UI;

import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
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
import javax.swing.JTextPane;
import javax.swing.UIManager;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.twilio.sdk.TwilioRestException;

import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Communication.SMS.SMSClient;
import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Map.IMap;
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
import WPI.CampusMap.Frontend.Graphics.Print.PrintJob;
import WPI.CampusMap.Frontend.Graphics.User.UserGraphicalMap;
import WPI.CampusMap.Frontend.Graphics.User.UserPointGraphicsObject;

public class UserModeClass extends UIMode {
	private UserGraphicalMap graphicalMap;

	private LinkedList<IPoint> destinations = new LinkedList<>();
	private HashSet<IPoint> destinationsSet = new HashSet<>();

	private Path routedPath;
	private LocationPref pref;
	
	private boolean showAllPoints;

	public UserModeClass(AppMainWindow window) {
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

	/**
	 * This method is called when the "Route Me" button is pressed. Generates a
	 * route based on the currently selected points. This method also takes the
	 * current weather conditions into consideration when created the path.
	 */
	public void onRouteButton() {
		NodeProcessor nP = new DistanceProcessor(new BetweenMapsProcessor(new WeatherHeuristicProcessor(null, pref)));
		AStarPathProcessor processor = new AStarPathProcessor(nP);

		IPoint[] points = new IPoint[destinations.size()];
		destinations.toArray(points);

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
		onClearDestinations();
	}

	/**
	 * Highlights the given section on map.
	 * 
	 * @param section map section
	 */
	public void selectRouteSection(Section section) {
		graphicalMap.setShownSection(section);
	}

	/**
	 * Selects the given point on map.
	 * 
	 * @param point to select
	 */
	public void selectCurrentNode(IPoint point) {
		graphicalMap.setShownNode(point);
	}

	/**
	 * Method called when the "clear" button is pressed.
	 */
	public void onClearButton() {
		// destinations.resetLastPoint();
		onClearDestinations();
		// UserPathGraphicsObject.deleteAll();
	}

	/**
	 * Adds the given point to the list of destinations.
	 * 
	 * @param newPoint a new destination
	 */
	public void addPointToDestinations(UserPointGraphicsObject newPoint) {
		addPointToDestinations(newPoint.getRepresentedObject());

		if (newPoint != null && destinationsSet.contains(newPoint.getRepresentedObject()))
			getWindow().addDestination(newPoint);
	}

	/**
	 * Adds the given point to the route.
	 * 
	 * @param newPoint added to the route
	 */
	public void onPointAddedToRoute(UserPointGraphicsObject newPoint) {
		addPointToDestinations(newPoint.getRepresentedObject());

		if (newPoint != null && destinationsSet.contains(newPoint.getRepresentedObject()))
			getWindow().addDestination(newPoint);
	}

	/**
	 * Adds the given point to the list of destinations.
	 * 
	 * @param newPoint to destinations list
	 */
	public void addPointToDestinations(IPoint newPoint) {
		if (newPoint == null)
			return;

		// if (routedPath != null) {
		// clearDestinations();
		// routedPath = null;
		// graphicalMap.setPathSections(getRoutedPath());
		// }
		if (!destinationsSet.contains(newPoint)) {
			System.out.println("Added " + newPoint + " to route");
			destinationsSet.add(newPoint);
			destinations.add(newPoint);
		}
	}

	public void onPointDescriptorAddedToDestinations(String pointDescriptor, int index) {
		if (routedPath != null) {
			onClearDestinations();
			routedPath = null;
			graphicalMap.setPathSections(getRoutedPath());
		}

		IPoint point = AllPoints.getInstance().getPoint(pointDescriptor);
		if (point == null) {
			return;
		}

		if (!destinations.contains(point)) {
			destinations.add(index, point);
			destinationsSet.add(point);
		}
	}

	/**
	 * Clears the current route.
	 */
	public void clearRoute() {
		destinationsSet.clear();
		destinations.clear();
	}

	public void onPointDescriptorRenamedDestination(String oldName, String newName, int index) {
		IPoint oldPoint = AllPoints.getInstance().getPoint(oldName);
		if (oldPoint == null) {
			onPointDescriptorAddedToDestinations(newName, index);
			return;
		}

		IPoint newPoint = AllPoints.getInstance().getPoint(newName);
		if (destinationsSet.contains(oldPoint)) {
			destinationsSet.remove(oldPoint);
			destinations.remove(oldPoint);

			destinations.add(index, newPoint);
			destinationsSet.add(newPoint);
		}
	}

	public void onPointDescriptorShown(String pointName) {
		IPoint point = AllPoints.getInstance().getPoint(pointName);
		if (point == null)
			return;

		loadMap(point.getMap());
	}

	/**
	 * Clears the destinations in the Destinations view.
	 */
	public void onClearDestinations() {
		destinationsSet.clear();
		destinations.clear();
	}

	public void onPointRemovedFromDestinations(String pointDescriptor) {
		IPoint point = AllPoints.getInstance().getPoint(pointDescriptor);

		if (point == null)
			return;

		// if (routedPath != null) {
		// clearRoute();
		// routedPath = null;
		// graphicalMap.setPathSections(null);
		// }
		if (destinationsSet.contains(point)) {
			destinationsSet.remove(point);
			destinations.remove(point);
		}
	}

	public boolean onCheckPointName(String name) {
		IPoint point = AllPoints.getInstance().getPoint(name);
		return point != null;
	}
	// public boolean containsInRoute(UserPointGraphicsObject point){
	// return false;
	// }

	public boolean containsInDest(UserPointGraphicsObject point) {
		return destinationsSet.contains(point.getRepresentedObject());
	}

	/**
	 * Returns true if the given point is the start of a route.
	 * 
	 * @param point of start
	 * @return true if the given point is the start of a route.
	 */
	public boolean isRouteStart(UserPointGraphicsObject point) {
		if (destinations.isEmpty())
			return false;
		return destinations.getFirst().equals(point.getRepresentedObject());
	}

	/**
	 * Returns true if the given point is the end of a route.
	 * 
	 * @param point of route end
	 * @return true if the given point is the end of a route.
	 */
	public boolean isRouteEnd(UserPointGraphicsObject point) {
		if (destinations.isEmpty())
			return false;
		return destinations.getLast().equals(point.getRepresentedObject());
	}

	public boolean isSectionStart(UserPointGraphicsObject point) {
		// System.out.println(point.getRepresentedObject().getMap());
		Section sectionToCheck = graphicalMap.getShownSection();
		if (sectionToCheck == null)
			return false;

		if (sectionToCheck.getPoints().getFirst().equals(point.getRepresentedObject())) {
			return true;
		}
		return false;
	}

	public boolean isSectionEnd(UserPointGraphicsObject point) {
		// System.out.println(point.getRepresentedObject().getMap());
		Section sectionToCheck = graphicalMap.getShownSection();

		if (sectionToCheck == null)
			return false;

		if (sectionToCheck.getPoints().getLast().equals(point.getRepresentedObject())) {
			return true;
		}
		return false;
	}

	public boolean isSectionEndDestination(UserPointGraphicsObject graphicsObjectToCheck) {
		IPoint pointToCheck = graphicsObjectToCheck.getRepresentedObject();
		IMap mapToCheck = AllMaps.getInstance().getMap(pointToCheck.getMap());
		if (routedPath == null)
			return false;
		LinkedList<Section> allPaths = routedPath.getSections(mapToCheck);
		for (Section sectionToCheck : allPaths) {
			LinkedList<IPoint> listOfPoints = sectionToCheck.getPoints();
			if (listOfPoints.getFirst().equals(pointToCheck)) {
				return true;
			}
		}
		return false;
	}

	public boolean isSectionStartDestination(UserPointGraphicsObject graphicsObjectToCheck) {
		IPoint pointToCheck = graphicsObjectToCheck.getRepresentedObject();
		IMap mapToCheck = AllMaps.getInstance().getMap(pointToCheck.getMap());
		if (routedPath == null)
			return false;
		LinkedList<Section> allPaths = routedPath.getSections(mapToCheck);
		for (Section sectionToCheck : allPaths) {
			LinkedList<IPoint> listOfPoints = sectionToCheck.getPoints();
			if (listOfPoints.getFirst().equals(pointToCheck)) {
				return true;
			}
		}
		return false;
	}

	public boolean isUltimateFirst(UserPointGraphicsObject graphicsObjectToCheck) {
		IPoint pointToCheck = graphicsObjectToCheck.getRepresentedObject();
		IMap mapToCheck = AllMaps.getInstance().getMap(pointToCheck.getMap());
		if (routedPath == null)
			return false;
		LinkedList<Section> allPaths = routedPath.getSections(mapToCheck);
		if ((allPaths == null) || (allPaths.size() == 0))
			return false;
		LinkedList<IPoint> listOfPoints = allPaths.getFirst().getPoints();
		if (listOfPoints.getFirst().equals(pointToCheck)) {
			return true;
		}
		return false;
	}

	public boolean isUltimateLast(UserPointGraphicsObject graphicsObjectToCheck) {
		IPoint pointToCheck = graphicsObjectToCheck.getRepresentedObject();
		IMap mapToCheck = AllMaps.getInstance().getMap(pointToCheck.getMap());
		if (routedPath == null)
			return false;
		LinkedList<Section> allPaths = routedPath.getSections(mapToCheck);
		if ((allPaths == null) || (allPaths.size() == 0))
			return false;
		LinkedList<IPoint> listOfPoints = allPaths.getLast().getPoints();
		if (listOfPoints.getLast().equals(pointToCheck)) {
			return true;
		}
		return false;
	}

	public void onWeatherChosen(LocationPref option) {
		System.out.println("Weather chosen is " + option);
		this.pref = option;
	}

	public void onPrint() {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new PrintJob(routedPath));

		if (job.printDialog()) {
			try {
				job.print();
			} catch (PrinterException e) {
				e.printStackTrace();
			}
		}
	}

	public void onPdf() {
		System.out.println("PDF");
	}

	/**
	 * @author Will Spurgeon Prompts the user to enter a file name and a file
	 *         location. Writes the user's directions to the specified location.
	 */
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
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		System.out.println("TXT");
	}

	/**
	 * Displays a pop up prompting the user to enter an email address.
	 * 
	 * @return The user's email address.
	 */
	public String popUp() {
		final JFrame parent = new JFrame();
		// parent.add(button);
		parent.pack();
		parent.setVisible(true);

		String email = JOptionPane.showInputDialog(parent, "What is your email address?", null);
		parent.setVisible(false);
		return email;

	}

	/**
	 * @author Will Spurgeon Prompts the user for an email address. An email
	 *         with the user's directions are then sent.
	 */
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

	/**
	 * @author Will Spurgeon Calls the sendText method on SMSClient.
	 */
	public void onSMS() {
		System.out.println("SMS");
		try {
			Route sendingRoute = new Route(routedPath);
			SMSClient.sendText("+18184411799", sendingRoute.toString());
		} catch (TwilioRestException e) {
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
	public void onMousePressedMap(MouseEvent e) {
		if (graphicalMap != null)
			graphicalMap.mouseDown(e);
	}

	@Override
	public void onMouseReleaseMap(MouseEvent e) {
		if (graphicalMap != null)
			graphicalMap.mouseUp(e);
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
	public void onMouseScrollOnMap(int unitsToScroll) {
		if (graphicalMap != null)
			graphicalMap.mouseScrolled(unitsToScroll);
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

	/**
	 * @author Will Spurgeon Builds and displays a pop up window containing all
	 *         of the About information for the app.
	 */
	public void onAbout() {
		JOptionPane aboutWindow = new JOptionPane();
		JFrame aboutFrame = new JFrame("About Campus Mapper");
		aboutFrame.setBounds(300, 300, 700, 300);
		aboutFrame.setLayout(new FlowLayout());
		JLabel textLabel = new JLabel();
		textLabel.setText("<html><p style=\"text-align: center;\">"
				+ "<p style=\"text-align: center;\"><span style=\"font-size: medium;\"><strong>CS 3733: Software Engineering B-Term 2015</strong></span></p>\n"
				+ "<p style=\"text-align: center;\"><span style=\"font-size: medium;\">Prof. Wilson Wong</span></p>\n"
				+ "<p style=\"text-align: center;\"><span style=\"font-size: medium;\">Worcester Polytechnic Institute</span></p>\n"
				+ "<p style=\"text-align: center;\">&nbsp;</p>\n"
				+ "<p style=\"text-align: left;\"><span style=\"font-size: medium;\"><b>Team 0011b:</b></span></p>\n"
				+ "<p style=\"text-align: left;\"><span style=\"font-size: medium;\"><b>Lukas Hunter: Team Coach</b></span></p>\n"
				+ "<p style=\"text-align: left;\"><span style=\"font-size: medium;\">Chris Cormier: Testing Engineer (Iteration 1 and 2)</span></p>\n"
				+ "<p style=\"text-align: left;\"><span style=\"font-size: medium;\"><span style=\"font-size: medium;\">Will Craft: Lead Software Engineer (Iteration 3 and 4)</span></span></p>\n"
				+ "<p style=\"text-align: left;\"><span style=\"font-size: medium;\">Gavin Hayes: Test Engineer (Iteration 3 and 4)</span></p>\n"
				+ "<p style=\"text-align: left;\"><span style=\"font-size: medium;\">Michael LoTurco: Product Owner (Iteration 1 and 2)</span></p>\n"
				+ "<p style=\"text-align: left;\"><span style=\"font-size: medium;\"><span style=\"font-size: medium;\">Benny Peake: Product Manager (Iteration 1 and 2), UI Lead (3 and 4)</span></span></p>\n"
				+ "<p style=\"text-align: left;\"><span style=\"font-size: medium;\"><span style=\"font-size: medium;\">Will Spurgeon: UI Lead (Iteration 1 and 2)</span></span></p>\n"
				+ "<p style=\"text-align: left;\"><span style=\"font-size: medium;\">Max Stenke: Product Owner (Iteration 3 and 4)</span></p>\n"
				+ "<p style=\"text-align: left;\"><span style=\"font-size: medium;\"><span>Jacob Zizmor: Lead Software Engineer (Iteration 1 and 2), Prodcut Manager (Iteration 3 and 4)</span></span></p>\n"
				+ "<p style=\"text-align: left;\"><span style=\"font-size: medium;\">&nbsp;</span></p>\n"
				+ "<p style=\"text-align: left;\">&nbsp;</p>\n" + "<p style=\"text-align: center;\">&nbsp;</p>\n"
				+ "<p style=\"text-align: center;\">&nbsp;</p>\n"
				+ "<p style=\"text-align: center;\">&nbsp;</p></html>");
		aboutFrame.add(textLabel);
		aboutWindow.createDialog(aboutFrame, "About Campus Mapper");
		aboutFrame.setVisible(true);
		aboutWindow.setVisible(true);
	}

	/**
	 * @author Will Spurgeon Builds and displays the application User Guide in a
	 *         pop up window.
	 */
	public void onGuide() {
		@SuppressWarnings("unused")
		JOptionPane aboutWindow = new JOptionPane();
		JFrame guideFrame = new JFrame("User Guide");
		JTextPane textLabel = new JTextPane();
		textLabel.setContentType("text/html");
		// textLabel.setWrapStyleWord(true);
		// textLabel.setLineWrap(true);
		textLabel.setOpaque(false);
		textLabel.setEditable(false);
		textLabel.setFocusable(false);
		textLabel.setBackground(UIManager.getColor("Label.background"));
		textLabel.setFont(UIManager.getFont("Label.font"));
		textLabel.setBorder(UIManager.getBorder("Label.border"));
		textLabel.setText(
				"<html><h1><span style=\"font-family: 'arial black', 'avant garde'; font-size: large;\">Campus Mapper <span style=\"font-family: 'arial black', 'avant garde';\">User</span> Guide</span></strong></p>"
						+ "<p><span style=\"font-family: 'arial black', 'avant garde';\"><strong>Find a route:</strong></span></p>"
						+ "<ol>"
						+ "    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Navigate to the map with the desired starting point. </span><span style=\"font-family: arial, helvetica, sans-serif;\"><br>Maps can be selected by going to the \"Maps\" menu and selecting the desired building and floor.</span></li>"
						+ "    <li><span style=\"font-family: arial, helvetica, sans-serif;\">The red dots on the maps represent potential starting or ending positions. Click on one of the red dots.</span></li>"
						+ "    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Select another point on a map. You may navigate to a different floor or building if you wish to.</span></li>"
						+ "    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Additional points may be selected on any of the maps before a route is found.</span></li>"
						+ "    <li><span style=\"font-family: arial, helvetica, sans-serif;\">When you have selected all of the points you would like to visit, press the \"Route Me!\" button.<br>A route will be drawn between all of your points on the map and textual instructions will appear in the \"Directions\" box.</span></li>"
						+ "</ol>"
						+ "<p><span style=\"font-family: 'arial black', 'avant garde';\"><strong><strong><strong><strong><strong><strong>Navigate through a route:</strong></strong></strong></strong></strong></strong></span></p>"
						+ "<ol>"
						+ "    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Once a route has been created, you may step through it by selecting an instruction <br>in the \"Directions\" box and pressing the \"Next\" and \"Previous\" buttons.</span></li>"
						+ "    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Stepping between buildings will cause the map view to change to the correct building. <br>Likewise, selecting a specific route will highlight that path within the map view.</span></li>"
						+ "    <li><span style=\"font-family: arial, helvetica, sans-serif;\">You may expand or hide the instructions within each route in the \"Directions\" box.</span></li>"
						+ "</ol>"
						+ "<p><span style=\"font-family: 'arial black', 'avant garde';\"><strong><strong><strong><strong>Editing a route:</strong></strong></strong></strong></span></p>"
						+ "<ol>"
						+ "    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Routes may be edited by removing points listed in the \"Destinations\" box. <br>Once two or points have been selected, click on the button with an \"X\" on it to remove that point from your route. </span></li>"
						+ "</ol>"
						+ "<p><span style=\"font-family: 'arial black', 'avant garde';\"><strong><strong><strong><strong>Building a map in Dev Mode:</strong></strong></strong></strong></span></p>"
						+ "<ol>"
						+ "    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Enter Dev Mode by selecting Settings->Dev Mode. <br>You may exit Dev Mode at any time by deselecting Settings->Dev Mode.</span></li>"
						+ "    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Select a the map you would like to work on from the \"Map\" dropdown.</span></li>"
						+ "    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Create nodes by clicking on the \"Create\" button. You may now click anywhere on the map to add a node.</span></li>"
						+ "    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Create edges by clicking on the \"Edge\" button. Select the two nodes you would like to connect. <br>Once the second node is selected, an edge will be created between the two nodes.</span></li>"
						+ "    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Delete nodes by clicking on the \"Delete\" button. If you click on an existing node, it will be deleted.</span></li>"
						+ "    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Delete edges by clicking on the \"Delete Edge\" button. Click on the two nodes you would like to dissconnect. </span></li>"
						+ "    <li><span style=\"font-family: arial, helvetica, sans-serif;\">Once you have made all of your changes to the map, click on the \"Save\" button to save the changes to disk.</span></li>"
						+ "</ol>" + "</html>");
		guideFrame.add(textLabel);
		guideFrame.setBounds(100, 100, 850, 750);
		guideFrame.setVisible(true);
		guideFrame.setLayout(new FlowLayout());
	}
	
	public void setShowAllPoints(boolean value)
	{
		showAllPoints = value;
	}
	
	public boolean getShowAllPoints()
	{
		return showAllPoints;
	}
}
