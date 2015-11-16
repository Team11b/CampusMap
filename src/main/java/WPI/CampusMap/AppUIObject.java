package WPI.CampusMap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import WPI.CampusMap.AStar.Point;

//TODO: Select edges button
//TODO: Place path button
//TODO: Save button.

public class AppUIObject {

	private static Map map;
	
	private static Point selectedPoint;
	
	/**
	 * Presents a view that allows the user to enter an email address 
	 * and send an email with the walking directions.
	 */
	private static void sendEmail(){
		
	}
	
	/**
	 * Calculates the walking path and displays the directions.
	 */
	private static void getAndDisplayDirections(){
		
	}
	
	/**
	 * Prints the walking directions.
	 */
	private static void printDirections(){
		
	}
	
	private static void loadMap(String mapName) throws XMLStreamException{
		Map newMap = new Map(mapName);
		map = newMap;
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
	private static class SwingAction extends AbstractAction {
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
					getAndDisplayDirections();					 
					System.out.println("Get Directions");
					break;
				case "Print":
					printDirections();
					System.out.println("Print");
					break;
				default:
			}
		}
	}
	
	public AppUIObject(){
		
		SwingAction actionHandler = new SwingAction();
    	
    	final JFrame frame = new JFrame("Path Finder");
    	frame.getContentPane().setLayout(null);
    	
    	final JPanel mainPanel = new JPanel();
    	mainPanel.setBounds(1, 6, 1018, 664);
    	frame.getContentPane().add(mainPanel);
    	mainPanel.setLayout(null);
    	
    	final JLabel lblMapviewGoesHere = new JLabel("");
    	lblMapviewGoesHere.setBounds(166, 12, 146, 16);
    	mainPanel.add(lblMapviewGoesHere);
    	lblMapviewGoesHere.setVisible(true);
    	
    	//load map
    	//picture init code
        BufferedImage myPicture = null;
        try {
			myPicture = ImageIO.read(new File("outside.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //debug statements
        System.out.println(System.getProperty("user.dir"));
        System.out.println(myPicture.getWidth());
        System.out.println(myPicture.getHeight());    	
    	
    	final JLabel lblScale = new JLabel("");
    	lblScale.setBounds(872, 12, 146, 16);
    	mainPanel.add(lblScale);
    	lblScale.setVisible(true);
    	
    	MouseListener mouseClick = new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
				System.out.println("X: " + e.getX() + " Y: " + e.getY());
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
    	
    	final JLabel lblPicLabel = new JLabel();
    	mainPanel.add(lblPicLabel);
    	lblPicLabel.setVisible(false);
    	lblPicLabel.addMouseListener(mouseClick);
    	
    	System.out.println("Image Size X: " + lblPicLabel.getSize().getWidth() + " Y: " + lblPicLabel.getSize().getHeight());
    	
    	final JPanel directionsPanel = new JPanel();
    	directionsPanel.setBounds(1031, 6, 237, 664);
    	frame.getContentPane().add(directionsPanel);
    	directionsPanel.setLayout(null);
    	
    	final JTextPane txtDirections = new JTextPane();
    	txtDirections.setBounds(26, 99, 215, 518);
    	directionsPanel.add(txtDirections);
    	
    	JButton btnEmail = new JButton("Email");
    	btnEmail.addActionListener(actionHandler);
    	btnEmail.setBounds(26, 629, 106, 29);
    	directionsPanel.add(btnEmail);
    	
    	JButton btnPrint = new JButton("Print");
    	btnPrint.addActionListener(actionHandler);
    	btnPrint.setBounds(130, 629, 111, 29);
    	directionsPanel.add(btnPrint);
    	
    	JLabel lblDirections = new JLabel("Directions:");
    	lblDirections.setBounds(26, 73, 80, 25);
    	directionsPanel.add(lblDirections);
    	
    	final JButton btnGetDirections = new JButton("Route me");
    	btnGetDirections.setBounds(87, 0, 101, 29);
    	directionsPanel.add(btnGetDirections);
    	btnGetDirections.addActionListener(actionHandler);
    	
    	final JButton btnNode = new JButton("Place Node");
    	btnNode.setBounds(114, 2, 127, 25);
    	directionsPanel.add(btnNode);
    	btnNode.setVisible(false);
    	
    	final JButton btnDelNode = new JButton("Delete Node");
    	btnDelNode.setBounds(114, 41, 127, 25);
    	directionsPanel.add(btnDelNode);
    	btnDelNode.setVisible(false);
    	
    	//Dev Mode
    	final JButton btnDevMode = new JButton("Dev Mode");
    	btnDevMode.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) {
    			if(btnDevMode.getText() == "Dev Mode"){
    			    frame.setTitle("Dev Mode");
    			    btnDevMode.setText("User Mode");
    			    btnGetDirections.setVisible(false);
    			    btnNode.setVisible(true);
    			    btnDelNode.setVisible(true);
    			}
    			else{
    			    frame.setTitle("Path Finder");
    			    btnDevMode.setText("Dev Mode");
    			    btnGetDirections.setVisible(true);
    			    btnNode.setVisible(false);
    			    btnDelNode.setVisible(false);
    			}
    		}
    	});
    	btnDevMode.setBounds(10, 41, 106, 25);
    	directionsPanel.add(btnDevMode);
    	
    	//map button code    	
    	JButton btnLoadMap = new JButton("Load");
    	btnLoadMap.setBounds(0, 0, 75, 29);
    	directionsPanel.add(btnLoadMap);	
    	
    	btnLoadMap.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) 
    		{
    			try {
					loadMap("outside.xml");
				} catch (XMLStreamException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			
    	        if(lblPicLabel.isVisible()== true)
    	        {
    	        	lblPicLabel.setVisible(false);
    	        	lblMapviewGoesHere.setVisible(false);
    	        	lblScale.setVisible(false);
    	        }
    	        else
    	        {
    	        	
    	        	lblScale.setVisible(true);    	        	
    	        	lblPicLabel.setVisible(true);
    	        	lblPicLabel.setIcon(map.getLoadedImage());
    	        	lblPicLabel.setBounds(5, 5, 1000, 660);
    	        	lblMapviewGoesHere.setVisible(true);
    	        	lblMapviewGoesHere.setText("Walking map");
    	        	lblScale.setText("Scale: 1 inch : 100 ft");
    	        	
    	        	
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
    		}
    	});
    	
    	JSeparator separator = new JSeparator();
    	separator.setBackground(Color.RED);
    	separator.setOrientation(SwingConstants.VERTICAL);
    	separator.setPreferredSize(new Dimension(50,10));
    	//separator.setBounds(100, 100, 174, 246);
    	frame.getContentPane().add(separator);
    	
    	   	
    	frame.setSize(1280, 720);
    	frame.setVisible(true);
	}
}
