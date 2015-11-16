package WPI.CampusMap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.xml.stream.XMLStreamException;

import WPI.CampusMap.AStar.Coord;
import WPI.CampusMap.AStar.Map;
import WPI.CampusMap.AStar.Path;
import WPI.CampusMap.AStar.Point;

//TODO: Select edges button
//TODO: Place path button
//TODO: Save button.

public class AppUIObject {

	private boolean placeMode = false;
	private boolean deleteMode = false;
	
	//UI Elements
	private final JFrame frame = new JFrame("Path Finder");
	private final JPanel mainPanel = new JPanel();
	private final JLabel lblMapviewGoesHere = new JLabel("");
	private final JLabel lblScale = new JLabel("");
	private final JLabel lblPicLabel = new JLabel();
	private final JPanel directionsPanel = new JPanel();
	private final JButton btnEmail = new JButton("Email");
	private final JButton btnPrint = new JButton("Print");
	private final JLabel lblDirections = new JLabel("Directions:");
	private final JButton btnGetDirections = new JButton("Route me");
	private final JButton btnNode = new JButton("Place Mode");
	private final JButton btnDelNode = new JButton("Delete Mode");
	private final JLabel lblMapColon = new JLabel("Map:");
	private final JButton btnDevMode = new JButton("Dev Mode");
	private final JButton btnSave = new JButton("Save");    	
	private final JTextPane txtDirections = new JTextPane();
	private String[] mapStrings = {"AK-0"};
	private final JComboBox mapDropDown = new JComboBox(mapStrings);    	
	private final StringBuilder mapName = new StringBuilder();
	private MouseListener mouseClick;
	private final SwingAction actionHandler = new SwingAction();

	private static Map map;
	
	private static Point selectedPoint;
	
	/**
	 * Re-draws all UI elements. Call after the map has changed.
	 */
	public void reDrawUI(){
    	lblMapviewGoesHere.setBounds(166, 12, 146, 16);
    	mainPanel.add(lblMapviewGoesHere);
    	lblMapviewGoesHere.setVisible(true);    	
    	
        //debug statements
        System.out.println(System.getProperty("user.dir"));     	
    	
    	lblScale.setBounds(781, 12, 225, 16);
    	mainPanel.add(lblScale);
    	lblScale.setVisible(true);
    	
    	mainPanel.add(lblPicLabel);
    	lblPicLabel.setVisible(false);
    	lblPicLabel.addMouseListener(mouseClick);
    	
    	System.out.println("Image Size X: " + lblPicLabel.getSize().getWidth() + " Y: " + lblPicLabel.getSize().getHeight());
    	
    	directionsPanel.setBounds(1031, 6, 237, 664);
    	frame.getContentPane().add(directionsPanel);
    	directionsPanel.setLayout(null);
    	
    	txtDirections.setBounds(26, 117, 215, 500);
    	directionsPanel.add(txtDirections);
    	
    	btnEmail.setBounds(26, 629, 106, 29);
    	directionsPanel.add(btnEmail);
    	
    	btnPrint.setBounds(130, 629, 111, 29);
    	directionsPanel.add(btnPrint);
    	
    	btnGetDirections.setBounds(42, 28, 157, 36);
    	directionsPanel.add(btnGetDirections);
    	
    	
    	btnNode.setBounds(0, 28, 127, 25);
    	
    	directionsPanel.add(btnNode);
    	btnNode.setVisible(false);
    	
    	btnDelNode.setBounds(0, 76, 127, 25);
    	
    	directionsPanel.add(btnDelNode);
    	btnDelNode.setVisible(false);
    	
    	lblDirections.setBounds(26, 80, 80, 25);
    	directionsPanel.add(lblDirections);
    	
    	lblMapColon.setBounds(12, 1, 70, 15);
    	directionsPanel.add(lblMapColon);
    	
    	btnDevMode.setBounds(135, 76, 106, 25);
    	directionsPanel.add(btnDevMode);
    	
    	//Drop down for map selection
    	mapDropDown.setBounds(49, -4, 176, 24);
    	directionsPanel.add(mapDropDown);
    	mapDropDown.setSelectedIndex(0);    	
    	
    	btnSave.setBounds(140, 28, 101, 25);
    	directionsPanel.add(btnSave);
    	btnSave.setVisible(false);
    	    	
    	JSeparator separator = new JSeparator();
    	separator.setBackground(Color.RED);
    	separator.setOrientation(SwingConstants.VERTICAL);
    	separator.setPreferredSize(new Dimension(50,10));
    	//separator.setBounds(100, 100, 174, 246);
    	frame.getContentPane().add(separator);
    	   	
    	frame.setSize(1280, 720);
    	frame.setVisible(true);
	}
	
	/**
	 * Presents a view that allows the user to enter an email address 
	 * and send an email with the walking directions.
	 */
	private static void sendEmail(){
		
	}
	
	/**
	 * Calculates the walking path and displays the directions.
	 */
	private static String getAndDisplayDirections(Path path){
		String route = "";
		for (int i = 1; i < path.getPath().size(); i++) {
			String turn = "";
			String direction = "";
			float dist = path.getPath().get(i).getPoint().distance(path.getPath().get(i - 1).getPoint());
			float angle = path.getAngle(path.getPath().get(i - 1).getPoint(), path.getPath().get(i).getPoint());

			route += path.getPath().get(i - 1).getPoint().toString() + " to "
					+ path.getPath().get(i).getPoint().toString() + "";
			if (path.getPath().get(i).getPoint().getCoord().getX() == path.getPath().get(i - 1).getPoint().getCoord()
					.getX()
					|| path.getPath().get(i).getPoint().getCoord().getY() == path.getPath().get(i - 1).getPoint()
							.getCoord().getY()) {
				route += "Walk " + dist + " feet straight on.\n";
			} else {

				if (path.getPath().get(i - 1).getPoint().getCoord().getX() < path.getPath().get(i).getPoint().getCoord()
						.getX()) {
					System.out.println(angle);
					if (angle < 0)
						turn = "left";
					else
						turn = "right";

				}
				if (Math.abs(angle) > 0 && Math.abs(angle) < 45) {
					direction = "slightly";
				} else if (Math.abs(angle) > 45 && Math.abs(angle) < 90) {
					direction = "hard";
				}
				route += "Turn " + direction + " " + turn + " and walk " + dist + " feet\n";
			}
		}

		return route;
	}
	
	/**
	 * Prints the walking directions.
	 */
	private static void printDirections(){
		
	}
	
	private void loadMap(String mapName) throws XMLStreamException{
		System.out.println("UI: " + mapName);
		Map newMap = new Map(mapName);
		map = newMap;
		reDrawUI();
	}
	
	/**
	 * Creates a point on the map at the mouse point.
	 * @param e The mouse event to trigger the method.
	 * @return The point that was created.
	 */
	private static Point createPointOnMap(MouseEvent e)
	{
		Coord screenCoord = new Coord(e.getX(), e.getY());
		Coord mapCoord = map.screenToWorldSpace(screenCoord);
		
		Point newPoint = new Point(mapCoord, "", UUID.randomUUID().toString());
		map.addPoint(newPoint);
		
		return newPoint;
	}
	
	/**
	 * Selects a point on the map.
	 * @param e The mouse event to select a point from.
	 * @return True if a point was selected, false otherwise.
	 */
	private static boolean selectPointOnMap(MouseEvent e)
	{
		Coord screenCoord = new Coord(e.getX(), e.getY());
		Coord mapCoord = map.screenToWorldSpace(screenCoord);
		
		ArrayList<Point> points = map.getMap();
		
		Point closestPoint = null;
		float closestDistance = Float.MAX_VALUE;
		final float clickThreshold = 1.0f;
		
		for(Point p : points)
		{
			float distance = mapCoord.distance(p.getCoord());
			
			if(distance < clickThreshold && distance < closestDistance)
			{
				closestPoint = p;
				closestDistance = distance;
			}
		}
		
		//No point selected
		if(closestPoint == null)
			return false;
		
		selectedPoint = closestPoint;
		
		return true;
	}
	
	/**
	 * If no point is selected then it selects a point.
	 * If a point is selected already then it tries to select a new point and if successful it creates an edge between the two points.
	 * @param e The mouse event to try and create an edge from.
	 * @return True if an edge was created, false otherwise.
	 */
	private static boolean addEdgeOnMap(MouseEvent e)
	{
		if(selectedPoint == null)
		{ 
			selectPointOnMap(e);
			return false;
		}
		
		Point lastSelected = selectedPoint;
		if(!selectPointOnMap(e))
			return false;
		
		map.addEdge(lastSelected, selectedPoint);
		
		return true;
	}
	
	/**
	 * This class handles all Swing actions from the user interface.
	 * @author Will
	 *
	 */
	@SuppressWarnings("serial")
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()){
				case "Email":
					sendEmail();
					System.out.println("Send an Email!");
					break;
				case "Route me":
					getAndDisplayDirections(new Path());					 
					System.out.println("Get Directions");
					break;
				case "Print":
					printDirections();
					System.out.println("Print");
					break;
				case "Place Mode":
					placeMode = !placeMode;
					System.out.println("Place Mode");
					break;
				case "Delete Mode":
					deleteMode = !deleteMode;
					System.out.println("Delete Mode");
					break;
				default:
			}
		}
	}
	
	public AppUIObject(){
    	
    	mouseClick = new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(placeMode){
					System.out.println("Placing point on Map X: " + e.getX() + " Y: " + e.getY());
					createPointOnMap(e);
				}else{
					System.out.println("You clicked X: " + e.getX() + " Y: " + e.getY());
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
    	
		frame.getContentPane().setLayout(null);
    	
		//Dev Mode
    	btnDevMode.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) {
    			if(btnDevMode.getText() == "Dev Mode"){
    			    frame.setTitle("Dev Mode");
    			    btnDevMode.setText("Usr mode");
    			    btnGetDirections.setVisible(false);
    			    btnNode.setVisible(true);
    			    btnDelNode.setVisible(true);
    			    btnSave.setVisible(true);
    			}
    			else{
    			    frame.setTitle("Path Finder");
    			    btnDevMode.setText("Dev Mode");
    			    deleteMode = !deleteMode;
    			    placeMode = !placeMode;
    			    btnGetDirections.setVisible(true);
    			    btnNode.setVisible(false);
    			    btnDelNode.setVisible(false);
    			    btnSave.setVisible(false);
    			}
    		}
    	});
    	
    	btnEmail.addActionListener(actionHandler);
    	btnPrint.addActionListener(actionHandler);
    	btnGetDirections.addActionListener(actionHandler);
    	btnNode.addActionListener(actionHandler);
    	btnDelNode.addActionListener(actionHandler);
    	
    	btnSave.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			
    		}
    	});
    	
    	mapDropDown.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) 
    		{
    			
    			mapName.append((String)mapDropDown.getSelectedItem());
    			try {
//     				String path = mapName.toString() + ".xml";
    				String path = mapName.toString();
    				loadMap(path);
    			} catch (XMLStreamException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
     			
     			//reset the StringBuilder
     			mapName.setLength(0);
     			
     	        //Display the map finally
            	lblScale.setVisible(true);    	        	
            	lblPicLabel.setVisible(true);
            	lblPicLabel.setIcon(map.getLoadedImage());
            	lblPicLabel.setBounds(5, 5, 1000, 660);
            	lblMapviewGoesHere.setVisible(true);
            	
            	int scale = map.getScale();        	
            	if(scale != -1){
            		lblMapviewGoesHere.setText(map.getName());
            		lblScale.setText("Scale: " + scale + " inches per ft");
            	}
            	else
            	{
            		lblMapviewGoesHere.setText("");
            		lblScale.setText("");
            	}
            	
            	
            	//huge shit show but demonstrates using a Jtextpane with an icon. this is how to do directions
                Icon icon = new ImageIcon("left.png");
                JLabel label = new JLabel(icon);
                StyleContext context = new StyleContext(); 
                Style labelStyle = context.getStyle(StyleContext.DEFAULT_STYLE);
                StyleConstants.setComponent(labelStyle, label);   	        	   	        	
            	StyledDocument doc = txtDirections.getStyledDocument();    	        	
                //Style def = StyleContext.getDefaultStyleContext().getStyle( StyleContext.DEFAULT_STYLE );
                //Style regular = doc.addStyle( "regular", def );
            	try {
            		doc.insertString(0, "Start of text\n", null );
            		doc.insertString(doc.getLength(), "Ignored", labelStyle);
    			} catch (BadLocationException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
     	       
     		}    		       
    		
    	});
    	
    	mainPanel.setBounds(1, 6, 1018, 664);
    	frame.getContentPane().add(mainPanel);
    	mainPanel.setLayout(null);
		reDrawUI();
	}
}
