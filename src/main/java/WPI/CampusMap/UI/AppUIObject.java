package WPI.CampusMap.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.xml.stream.XMLStreamException;

import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.PathPlanning.AStar.Path;
import WPI.CampusMap.XML.XML;

public class AppUIObject {
	protected boolean placeMode = false;
	protected boolean deleteMode = false;	
	protected boolean edgeMode = false;
	protected boolean devMode = false;
	protected boolean removeEdgeMode = false;

	// UI Elements
	private final JFrame frame = new JFrame("Path Finder");
	private final JPanel mainPanel = new JPanel();
	private final JLabel lblMapviewGoesHere = new JLabel("");
	private final JLabel lblScale = new JLabel("Scale: ");
	protected final MapPanel mapPanel = new MapPanel(this);
	private final JPanel directionsPanel = new JPanel();
	private final JButton btnEmail = new JButton("Email");
	private final JButton btnPrint = new JButton("Print");
	private final JLabel lblDirections = new JLabel("Directions:");
	protected final JButton btnGetDirections = new JButton("Route me");
	private final JButton btnNode = new JButton("Place Mode");
	private final JButton btnDelNode = new JButton("Delete Mode");
	private final JLabel lblMapColon = new JLabel("Map:");
	private final JButton btnDevMode = new JButton("Dev Mode");
	private final JButton btnSave = new JButton("Save");
	
	private final JTextPane txtDirections = new JTextPane();
	private String[] mapStrings = { "Atwater_Kent-0", "Atwater_Kent-1", "Atwater_Kent-2", "Atwater_Kent-3",
			"Boynton_Hall_3rd_floor_renovations-0", "Boynton_Hall_3rd_floor_renovations-1", "Boynton_Hall-0",
			"Boynton_Hall-1", "Boynton_Hall-2", "Boynton_Hall-3", "Campus_Center-0", "Campus_Center-1",
			"Campus_Center-2", "Gordon_Library-0", "Gordon_Library-1", "Gordon_Library-2", "Gordon_Library-3",
			"Gordon_Library-4", "Higgins_House_and_garage-0", "Higgins_House_and_garage-1",
			"Higgins_House_and_garage-2", "Higgins_House_and_garage-3", "Higgins_House_and_garage-4",
			"Higgins_House_and_garage-5", "Project_Center_1st_floor_renovations_2013",
			"Project_Center_1st_floor_renovationsRoomNumbers2014", "Project_Center-0", "Project_Center-1",
			"Stratton_Hall-0", "Stratton_Hall-1", "Stratton_Hall-2", "Stratton_Hall-3" };
	private final JComboBox<Object> mapDropDown = new JComboBox<Object>(mapStrings);
	private final StringBuilder mapName = new StringBuilder();
	private MouseListener mouseClick;
	private final SwingAction actionHandler = new SwingAction();

	private final JButton btnRemoveEdge = new JButton("Remove Edge");
	private final JButton btnEdgeMode = new JButton("Edge Mode");
	private final JButton btnSubmit;
	private JTextField txtScale;
	private JPasswordField txtDevPass;
	

	/**
	 * Re-draws all UI elements. Call after the map has changed.
	 */
	public void reDrawUI() {
		mapPanel.repaint();
	}

	/**
	 * Presents a view that allows the user to enter an email address and send
	 * an email with the walking directions.
	 */
	private static void sendEmail() {

	}

	/**
	 * Prints the walking directions.
	 */
	private static void printDirections() {

	}

	/** loadMap takes a mapName and loads it as the current map
	 * 
	 * @param mapName					map to load
	 * @throws XMLStreamException	
	 */
	private void loadMap(String mapName) throws XMLStreamException {
		System.out.println("UI: " + mapName);
		Map newMap = new Map(mapName);
		mapPanel.currentMap = newMap;
		reDrawUI();
	}
	
	/**
	 * This class handles all Swing actions from the user interface.
	 * 
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
			switch (e.getActionCommand()) {
			case "Email":
				sendEmail();
				System.out.println("Send an Email!");
				break;
			case "Route me":
				Path path = mapPanel.currentMap.astar(mapPanel.startPoint, mapPanel.endPoint);
				mapPanel.currentRoute = path;
				reDrawUI();
				break;
			case "Print":
				printDirections();
				System.out.println("Print");
				break;
			case "Place Mode":
				placeMode = !placeMode;
				edgeMode = false;
				deleteMode = false;
				removeEdgeMode = false;
				System.out.println("Place Mode");
				break;
			case "Delete Mode":
				deleteMode = !deleteMode;
				placeMode = false;
				edgeMode = false;
				removeEdgeMode = false;
				System.out.println("Delete Mode");
				break;
			case "Edge Mode":
				edgeMode = !edgeMode;
				placeMode = false;
				deleteMode = false;
				removeEdgeMode = false;
				System.out.println("Edge Mode");
				break;
			case "Remove Edge":
				removeEdgeMode = !removeEdgeMode;
				edgeMode = false;
				placeMode = false;
				deleteMode = false;
				System.out.println("Remove Edge");
				break;
			default:
			}
		}
	}

	public AppUIObject() {

		// debug statements
		System.out.println(System.getProperty("user.dir"));

		mainPanel.add(mapPanel);
		mapPanel.setVisible(false);

		System.out.println(
				"Image Size X: " + mapPanel.getSize().getWidth() + " Y: " + mapPanel.getSize().getHeight());

		directionsPanel.setBounds(1031, 6, 237, 664);
		frame.getContentPane().add(directionsPanel);
		directionsPanel.setLayout(null);

		txtDirections.setBounds(26, 183, 215, 434);
		directionsPanel.add(txtDirections);

		btnEmail.setBounds(26, 629, 106, 29);
		directionsPanel.add(btnEmail);

		btnPrint.setBounds(130, 629, 111, 29);
		directionsPanel.add(btnPrint);
		
		btnSubmit = new JButton("Submit");	
		btnSubmit.setBounds(118, 79, 117, 25);
		directionsPanel.add(btnSubmit);
		btnSubmit.setVisible(false);
		
		txtDevPass = new JPasswordField();
		txtDevPass.setBounds(0, 82, 111, 19);
		directionsPanel.add(txtDevPass);
		txtDevPass.setVisible(false);

		btnGetDirections.setBounds(53, 89, 157, 36);
		directionsPanel.add(btnGetDirections);
		btnGetDirections.setEnabled(false);

		btnNode.setBounds(0, 79, 127, 25);

		directionsPanel.add(btnNode);

		btnDelNode.setBounds(0, 116, 127, 25);

		directionsPanel.add(btnDelNode);

		lblDirections.setBounds(26, 153, 80, 25);
		directionsPanel.add(lblDirections);

		lblMapColon.setBounds(6, 10, 70, 15);
		directionsPanel.add(lblMapColon);

		btnDevMode.setBounds(0, 42, 106, 25);
		directionsPanel.add(btnDevMode);
		
		// Drop down for map selection
	    mapDropDown.setBounds(55, 6, 176, 24);
	    directionsPanel.add(mapDropDown);
		mapDropDown.setSelectedIndex(0);

		btnSave.setBounds(140, 42, 101, 25);
		directionsPanel.add(btnSave);
		btnRemoveEdge.setBounds(124, 116, 117, 29);
		
		directionsPanel.add(btnRemoveEdge);
		btnEdgeMode.setBounds(124, 76, 117, 29);
		
		directionsPanel.add(btnEdgeMode);
		
		btnSave.setVisible(false);

		JSeparator separator = new JSeparator();
		separator.setBackground(Color.RED);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setPreferredSize(new Dimension(50, 10));
		// separator.setBounds(100, 100, 174, 246);
		separator.setVisible(false);
		frame.getContentPane().add(separator);

		frame.setSize(1280, 720);
		frame.setVisible(true);
		
		mouseClick = new MapMouseListener(this);

		frame.getContentPane().setLayout(null);

		// Dev Mode
		btnDevMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!devMode) {
					btnGetDirections.setVisible(false);
					txtDevPass.setVisible(true);
					btnSubmit.setVisible(true);
					txtDirections.setText("Enter the password and click submit!");
					btnDevMode.setText("User mode");
					devMode = true; //not actually true, but in order to switch without pass						
				} else {
					devMode = false;
					frame.setTitle("Path Finder");
					btnDevMode.setText("Dev Mode");
					deleteMode = !deleteMode;
					placeMode = !placeMode;
					btnGetDirections.setVisible(true);
					btnGetDirections.setEnabled(false);
					btnEdgeMode.setVisible(false);
					btnRemoveEdge.setVisible(false);
					btnNode.setVisible(false);
					btnDelNode.setVisible(false);
					btnSave.setVisible(false);
					txtScale.setVisible(false);
					txtDevPass.setVisible(false);
					btnSubmit.setVisible(false);
					txtDirections.setText("");
				}
				reDrawUI();
				mapPanel.selectedPoint = null;
				mapPanel.startPoint = null;
				mapPanel.endPoint = null;
				mapPanel.currentRoute = null;
			}
		});
		//password for devmode code
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(String.valueOf(txtDevPass.getPassword()).equals("0011")){
					btnSubmit.setVisible(false);
					txtDevPass.setVisible(false);
					txtDevPass.setText("");
					frame.setTitle("Dev Mode");									
					btnNode.setVisible(true);
					btnDelNode.setVisible(true);
					btnSave.setVisible(true);
					btnEdgeMode.setVisible(true);
					btnRemoveEdge.setVisible(true);
					txtScale.setVisible(true);
				}
				else{
					txtDevPass.setText("");
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
				System.out.println(txtScale.getText());
				mapPanel.currentMap.setScale(Integer.parseInt(txtScale.getText()));
				System.out.println("SAVING!");
				XML.writePoints(mapPanel.currentMap, mapPanel.currentMap.getMap());
				lblScale.setText("Scale: " + mapPanel.currentMap.getScale() + " inches per ft");
			}
		});

		mapDropDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//cleanup
				reDrawUI();
				mapPanel.selectedPoint = null;
				mapPanel.startPoint = null;
				mapPanel.endPoint = null;
				mapPanel.currentRoute = null;
				
				mapName.append((String) mapDropDown.getSelectedItem());
				try {
					// String path = mapName.toString() + ".xml";
					String path = mapName.toString();
					loadMap(path);
				} catch (XMLStreamException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// reset the StringBuilder
				mapName.setLength(0);

				// Display the map finally
				lblScale.setVisible(true);
				mapPanel.setVisible(true);
				//lblPicLabel.setIcon(currentMap.getLoadedImage());
				mapPanel.setBounds(5, 5, 1000, 660);
				lblMapviewGoesHere.setVisible(true);

				int scale = mapPanel.currentMap.getScale();
				if (scale != -1) {
					lblMapviewGoesHere.setText(mapPanel.currentMap.getName());
					lblScale.setText("Scale: " + scale + " inches per ft");
					txtScale.setText(Integer.toString(scale));
					
				} else {
					lblMapviewGoesHere.setText("");
					lblScale.setText("");
				}

				//This is how to do directions
				Icon icon = new ImageIcon("left.png");
				JLabel label = new JLabel(icon);
				StyleContext context = new StyleContext();
				Style labelStyle = context.getStyle(StyleContext.DEFAULT_STYLE);
				StyleConstants.setComponent(labelStyle, label);
				StyledDocument doc = txtDirections.getStyledDocument();
				// Style def = StyleContext.getDefaultStyleContext().getStyle(
				// StyleContext.DEFAULT_STYLE );
				// Style regular = doc.addStyle( "regular", def );
				try {
					doc.insertString(0, "Turn-by-turn directions coming soon!\n", null);
					//doc.insertString(doc.getLength(), "Ignored", labelStyle);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		mapPanel.addMouseListener(mouseClick);
		
		btnEdgeMode.setVisible(false);
		btnRemoveEdge.setVisible(false);
		btnNode.setVisible(false);
		btnDelNode.setVisible(false);
		
		btnEdgeMode.addActionListener(actionHandler);
		btnRemoveEdge.addActionListener(actionHandler);
		lblMapviewGoesHere.setBounds(446, 0, 327, 16);
		frame.getContentPane().add(lblMapviewGoesHere);
		lblMapviewGoesHere.setVisible(true);
		lblMapviewGoesHere.setVisible(true);
		lblMapviewGoesHere.setVisible(true);
		
		txtScale = new JTextField();
		txtScale.setBounds(37, 0, 130, 19);
		frame.getContentPane().add(txtScale);
		txtScale.setColumns(10);
		txtScale.setVisible(false);
		lblScale.setBounds(0, 0, 221, 16);
		frame.getContentPane().add(lblScale);
		lblScale.setHorizontalAlignment(SwingConstants.LEFT);
		lblScale.setVisible(true);

		mainPanel.setBounds(1, 15, 1018, 664);
		frame.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		
		try {
			loadMap(mapStrings[0]);
		} catch (XMLStreamException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mapPanel.setVisible(true);
		mapPanel.setBounds(5, 5, 1000, 660);

		int scale = mapPanel.currentMap.getScale();
		if (scale != -1) {
			lblMapviewGoesHere.setText(mapPanel.currentMap.getName());
			lblScale.setText("Scale: " + scale + " inches per ft");
			txtScale.setText(Integer.toString(scale));
		} else {
			lblMapviewGoesHere.setText("");
			lblScale.setText("");
		}		
		reDrawUI();		
	}
}