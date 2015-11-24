package WPI.CampusMap.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


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
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.xml.stream.XMLStreamException;

import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.PathPlanning.Path;
import WPI.CampusMap.PathPlanning.AStar.AStar;
import WPI.CampusMap.Serialization.Serialization;
import WPI.CampusMap.XML.XML;

public class AppUIObject {
	protected boolean devMode = false;

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
	private final JToggleButton btnNode = new JToggleButton("Place Mode");
	private final JToggleButton btnDelNode = new JToggleButton("Delete Mode");
	private final JLabel lblMapColon = new JLabel("Map:");
	private final JButton btnDevMode = new JButton("Dev Mode");
	private final JButton btnSave = new JButton("Save");
	private final String[] pointTypes = {"1", "2", "3"};
	private JComboBox typeSelector = new JComboBox();
	
	private final JTextPane txtDirections = new JTextPane();
	/*private String[] mapStrings = { "Atwater_Kent-0", "Atwater_Kent-1", "Atwater_Kent-2", "Atwater_Kent-3",
			"Boynton_Hall_3rd_floor_renovations-0", "Boynton_Hall_3rd_floor_renovations-1", "Boynton_Hall-0",
			"Boynton_Hall-1", "Boynton_Hall-2", "Boynton_Hall-3", "Campus_Center-0", "Campus_Center-1",
			"Campus_Center-2", "Gordon_Library-0", "Gordon_Library-1", "Gordon_Library-2", "Gordon_Library-3",
			"Gordon_Library-4", "Higgins_House_and_garage-0", "Higgins_House_and_garage-1",
			"Higgins_House_and_garage-2", "Higgins_House_and_garage-3", "Higgins_House_and_garage-4",
			"Higgins_House_and_garage-5", "Project_Center_1st_floor_renovations_2013",
			"Project_Center_1st_floor_renovationsRoomNumbers2014", "Project_Center-0", "Project_Center-1",
			"Stratton_Hall-0", "Stratton_Hall-1", "Stratton_Hall-2", "Stratton_Hall-3" };*/
	private final ArrayList mapXMLStrings = new ArrayList<String>();
	private JComboBox mapDropDown = new JComboBox();
	private String[] mapStrings;
	//private final StringBuilder mapName = new StringBuilder();
	private MouseListener mouseClick;
	private final SwingAction actionHandler = new SwingAction();

	private final JToggleButton btnRemoveEdge = new JToggleButton("Remove Edge");
	private final JToggleButton btnEdgeMode = new JToggleButton("Edge Mode");
	private final JButton btnSubmit;
	private JTextField txtScale;
	private JPasswordField txtDevPass;
	private final JLabel lblNodeId = new JLabel("Node ID:");
	private JTextField nodeTextField;
	
	protected enum DevMode{
		none, addNode, addEdge, deleteNode, deleteEdge;
	}
	
	protected DevMode currentDevMode = DevMode.none;

	/**
	 * Re-draws all UI elements. Call after the map has changed.
	 */
	public void reDrawUI() {
		mapPanel.repaint();
	}
	
	public void resetDropDown(){
		//get all the files in the directory
		File[] listOfFiles = new File("maps/").listFiles();
		int oldIndex = mapDropDown.getSelectedIndex();
		mapXMLStrings.clear();
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {					        
	        int ext = listOfFiles[i].getName().lastIndexOf(".png"); //snip extension     
	        mapXMLStrings.add(listOfFiles[i].getName().substring(0, ext));	       	        
	      } 
	    }
	    //put in alphabetical order and convert to string array
	    mapXMLStrings.sort(null);
	    mapStrings = new String[mapXMLStrings.size()];
	    mapStrings = (String[]) mapXMLStrings.toArray(mapStrings);
	    if(mapDropDown == null){
	    mapDropDown = new JComboBox();
	    }
	    else{
	    	mapDropDown.removeAllItems();
	    }
	    int q = listOfFiles.length - 1; //because of dat org folder.
	    for(int j = 0; j < q; j++)
	    {
	    	if(mapStrings[j] != null)
	    		mapDropDown.addItem(mapStrings[j]);
	    }
	    //mapDropDown = new JComboBox<Object>(mapStrings);	    
	    mapDropDown.setSelectedIndex(oldIndex);
	    System.out.println("old index is " + oldIndex);
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
				Path path = AStar.single_AStar(mapPanel.startPoint, mapPanel.endPoint);
				mapPanel.currentRoute = path;
				reDrawUI();
				break;
			case "Print":
				printDirections();
				System.out.println("Print");
				break;
			case "Place Mode":
				if(currentDevMode != DevMode.addNode){
				currentDevMode = DevMode.addNode;
				btnDelNode.setSelected(false);
				btnRemoveEdge.setSelected(false);
				btnEdgeMode.setSelected(false);
				System.out.println("Place Mode");
				}
				else{
					currentDevMode = DevMode.none;
					System.out.println("null mode");
				}
				break;
			case "Delete Mode":
				if(currentDevMode != DevMode.deleteNode){
				currentDevMode = DevMode.deleteNode;
				btnNode.setSelected(false);
				btnRemoveEdge.setSelected(false);
				btnEdgeMode.setSelected(false);
				System.out.println("Delete Mode");
				}
				else{
					currentDevMode = DevMode.none;
					System.out.println("null mode");
				}
				break;
			case "Edge Mode":
				if(currentDevMode != DevMode.addEdge){
					currentDevMode = DevMode.addEdge;
					btnNode.setSelected(false);
					btnDelNode.setSelected(false);
					btnRemoveEdge.setSelected(false);
					System.out.println("Edge Mode");
					}
					else{
						currentDevMode = DevMode.none;
						System.out.println("null mode");
					}
				break;
			case "Remove Edge":
				if(currentDevMode != DevMode.deleteEdge){
					currentDevMode = DevMode.deleteEdge;
					btnNode.setSelected(false);
					btnDelNode.setSelected(false);
					btnEdgeMode.setSelected(false);
					System.out.println("Remove Mode");
					}
					else{
						currentDevMode = DevMode.none;
						System.out.println("null mode");
					}
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
		resetDropDown();
	    mapDropDown.setBounds(55, 6, 176, 24);
	    directionsPanel.add(mapDropDown);
		mapDropDown.setSelectedIndex(0);

		btnSave.setBounds(140, 42, 101, 25);
		directionsPanel.add(btnSave);
		btnRemoveEdge.setBounds(124, 116, 117, 29);
		
		directionsPanel.add(btnRemoveEdge);
		btnEdgeMode.setBounds(124, 76, 117, 29);
		
		directionsPanel.add(btnEdgeMode);
		
		final JLabel lblNodeType = new JLabel("Node Type:");
		lblNodeType.setBounds(26, 200, 85, 16);
		directionsPanel.add(lblNodeType);
				lblNodeId.setBounds(26, 228, 61, 16);
				
				directionsPanel.add(lblNodeId);
				
				typeSelector.setBounds(104, 196, 131, 27);
				directionsPanel.add(typeSelector);
				
				nodeTextField = new JTextField();
				nodeTextField.setBounds(99, 228, 130, 26);
				directionsPanel.add(nodeTextField);
				nodeTextField.setColumns(10);
				
						txtDirections.setBounds(26, 183, 215, 434);
						directionsPanel.add(txtDirections);
		
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
					if(mapPanel.selectedPoint == null){
						nodeTextField.setText("");
						typeSelector.setSelectedIndex(0);
					}else{
						nodeTextField.setText(mapPanel.selectedPoint.getId());
						typeSelector.setSelectedItem(mapPanel.selectedPoint.getType());
					}					
					txtDirections.setText("Enter the password and click submit!");
					btnDevMode.setText("User mode");
					devMode = true; //not actually true, but in order to switch without pass						
				} else {
					devMode = false;
					frame.setTitle("Path Finder");
					btnDevMode.setText("Dev Mode");
					currentDevMode = DevMode.none;
					lblNodeId.setVisible(false);
					lblNodeType.setVisible(false);
					nodeTextField.setVisible(false);
					typeSelector.setVisible(false);
					lblDirections.setVisible(true);
					txtDirections.setVisible(true);
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
				//resetDropDown();
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
					txtDirections.setVisible(false);
					lblDirections.setVisible(false);
					txtDevPass.setText("");
					frame.setTitle("Dev Mode");									
					btnNode.setVisible(true);
					btnDelNode.setVisible(true);
					btnSave.setVisible(true);
					btnEdgeMode.setVisible(true);
					btnRemoveEdge.setVisible(true);
					txtScale.setVisible(true);
					nodeTextField.setVisible(true);
					typeSelector.setVisible(true);
					lblNodeId.setVisible(true);
					lblNodeType.setVisible(true);
					
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
				mapPanel.currentMap.setScale(Float.parseFloat(txtScale.getText()));
				System.out.println("SAVING!");
				if(mapPanel.selectedPoint != null){
					mapPanel.selectedPoint.setId(nodeTextField.getText());
					mapPanel.selectedPoint.setType((String)typeSelector.getSelectedItem());
				}
				Serialization.write(mapPanel.currentMap);
				//XML.writePoints(mapPanel.currentMap);
				lblScale.setText("Scale: " + mapPanel.currentMap.getScale() + " inches per ft");
			}
		});

		mapDropDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mapPanel.selectedPoint = null;
				mapPanel.startPoint = null;
				mapPanel.endPoint = null;
				mapPanel.currentRoute = null;				
				
				String mapName = (String)mapDropDown.getSelectedItem();
				try {
					System.out.println("Index: " + mapDropDown.getSelectedIndex());					
					loadMap(mapName);
				} catch (XMLStreamException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

				// Display the map finally
				lblScale.setVisible(true);
				mapPanel.setVisible(true);
				//lblPicLabel.setIcon(currentMap.getLoadedImage());
				mapPanel.setBounds(5, 5, 1000, 660);
				lblMapviewGoesHere.setVisible(true);

				float scale = mapPanel.currentMap.getScale();
				if (scale != -1) {
					lblMapviewGoesHere.setText(mapPanel.currentMap.getName());
					lblScale.setText("Scale: " + scale + " inches per ft");
					txtScale.setText(Float.toString(scale));
					
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
				
				//cleanup
				reDrawUI();
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
		
		nodeTextField.setVisible(false);
		typeSelector.setVisible(false);
		lblNodeId.setVisible(false);
		lblNodeType.setVisible(false);
		typeSelector.addItem(pointTypes[0]);
		typeSelector.addItem(pointTypes[1]);
		typeSelector.addItem(pointTypes[2]);
		
		nodeTextField.setText(mapPanel.selectedPoint.getId());
		
		typeSelector.setSelectedItem(mapPanel.selectedPoint.getType());
		if(typeSelector.getSelectedIndex() == -1){
			typeSelector.setSelectedIndex(0);
		}
		
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
		
		//typeSelector.setBounds(0,90,100,20);
		//typeSelector.setLayout(null);
		//directionsPanel.add(typeSelector);
		//typeSelector.setVisible(true);
		
		try {
			loadMap(mapStrings[0]);
		} catch (XMLStreamException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mapPanel.setVisible(true);
		mapPanel.setBounds(5, 5, 1000, 660);

		float scale = mapPanel.currentMap.getScale();
		if (scale != -1) {
			lblMapviewGoesHere.setText(mapPanel.currentMap.getName());
			lblScale.setText("Scale: " + scale + " inches per ft");
			txtScale.setText(Float.toString(scale));
		} else {
			lblMapviewGoesHere.setText("");
			lblScale.setText("");
		}		
		reDrawUI();		
	}

	public void updatePoint() {
		nodeTextField.setText(mapPanel.selectedPoint.getId());
		if(mapPanel.selectedPoint.getType() == null){
			typeSelector.setSelectedIndex(2);
		}else{
			System.out.println(mapPanel.selectedPoint.getType());
			typeSelector.setSelectedItem(mapPanel.selectedPoint.getType());
		}
	}
}
