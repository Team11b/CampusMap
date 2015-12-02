package WPI.CampusMap.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
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

import WPI.CampusMap.Backend.ConnectionPoint;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.Dev.EditorToolMode;
import WPI.CampusMap.Graphics.Dev.DevPointGraphicsObject;
import WPI.CampusMap.Graphics.User.UserPointGraphicsObject;
import WPI.CampusMap.PathPlanning.MultiPath;
import WPI.CampusMap.PathPlanning.Path;
import WPI.CampusMap.PathPlanning.Route.Instruction;
import WPI.CampusMap.PathPlanning.Route.Route;
import WPI.CampusMap.Serialization.Serializer;

public class AppUIObject {
	private static AppUIObject instance;
	
	public static AppUIObject getInstance()
	{
		return instance;
	}
	
	protected boolean devMode = false;
	private MultiPath lastRoutedPath;

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
	private final String[] pointTypes = {Point.HALLWAY, Point.STAIRS, Point.ELEVATOR, Point.OUT_DOOR};
	private JComboBox<String> typeSelector = new JComboBox<String>();
	private final String[] weatherTypes = {"Weather", "No Preference", "Prefer Outside", "Prefer Inside"};	private final JTextPane txtDirections = new JTextPane();
	private JComboBox<String> comboWeather = new JComboBox(weatherTypes);
	//	private JLabel lblStart = new JLabel("Start:");
//	private JLabel lblEnd = new JLabel("End:");
//	private JTextField txtStart;
//	private JTextField txtEnd;
	//private JToggleButton btnUseWeather = new JToggleButton("Use Weather");
	private final ArrayList<String> mapXMLStrings = new ArrayList<String>();
	private JComboBox<String> mapDropDown = new JComboBox<String>();
	private String[] mapStrings;
	private MouseListener mouseClick;
	private final SwingAction actionHandler = new SwingAction();

	private final JToggleButton btnRemoveEdge = new JToggleButton("Remove Edge");
	private final JToggleButton btnEdgeMode = new JToggleButton("Edge Mode");
	private final JButton btnSubmit;
	private JTextField txtScale;
	private JPasswordField txtDevPass;
	private final JLabel lblNodeId = new JLabel("Node ID:");
	private JTextField nodeTextField;
	
   public  Destinations destinations = new Destinations(directionsPanel);

	
	protected enum DevMode{
		none, addNode, addEdge, deleteNode, deleteEdge;
	}
	
	protected DevMode currentDevMode = DevMode.none;	
	private JTextField connectingMapTextField;
	private JTextField connectingPointTextField;
	
	private void clearNodeInfo(){
		typeSelector.setSelectedIndex(0);
		nodeTextField.setText("");
	}
	
	//getters for the BennyZone(tm)
	public String getID(){
		return nodeTextField.getText();
	}
	
	public void setMapConnectionTextFieldEditable(boolean b){
		connectingMapTextField.setEditable(b);
	}
	
	public void setPointConnectionTextFieldEditable(boolean b){
		connectingPointTextField.setEditable(b);
	}
	
	public String getTypeSelector(){
		return typeSelector.getItemAt(typeSelector.getSelectedIndex()); 
	}
	
	public void setPointConnectorText(String text){
		connectingPointTextField.setText(text);
	}
	
	public void setMapConnectorText(String text){
		connectingMapTextField.setText(text);
	}
	
	public String getMapConnectorText(){
		return connectingMapTextField.getText();
	}
	
	public String getPointConnectorText(){
		return connectingPointTextField.getText();
	}
	
	public void setNodeTextFieldEditable(boolean b){
		nodeTextField.setEnabled(b);
	}
	public void setTypeSelectorEditable(boolean b){
		typeSelector.setEnabled(b);
	}
	
	
	//Next 4 functions are around UserPointGraphicsObject and DevPointGraphicsObject
	public void setTypeSelector(int type){
		typeSelector.setSelectedIndex(type);
	}
	public void setNodeTextField(String Id){		
		nodeTextField.setText(Id);
	}
	
//	public void setStart(String Id){
//		txtStart.setText(Id);
//	}
//	
//	public void setEnd(String Id){
//		txtEnd.setText(Id);
//	}
	
	public void reDrawUI() {
		mapPanel.repaint();
	}
	
	public void resetDropDown(){
		//get all the files in the directory
		File[] listOfFiles = new File("maps/").listFiles();
		int oldIndex = mapDropDown.getSelectedIndex();
		String oldString = (String) mapDropDown.getItemAt(oldIndex);
		System.out.println("Oldstring is " + oldString);
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
	    	mapDropDown = new JComboBox<String>();
	    }
	    else{
	    	mapDropDown.removeAllItems();
	    }
	    int q = listOfFiles.length - 1; //because of dat org folder.
	    for(int j = 0; j < q; j++)
	    {
	    	if(mapStrings[j] != null){
	    		mapDropDown.addItem(mapStrings[j]);
	    		if(mapStrings[j].equals(oldString)){
	    			mapDropDown.setSelectedIndex(j);
	    		}
	    		
	    	}
	    }
	    //mapDropDown = new JComboBox<Object>(mapStrings);	    
	    // mapDropDown.setSelectedIndex(oldIndex);
	    System.out.println("old index is " + oldIndex);
	}
	
	public void onPointAddedToRoute(Point newPoint)
	{
		btnGetDirections.setEnabled(true);
		
		destinations.setDestination(newPoint.getId());
	}
	
	public void onRouteCleared()
	{
		btnGetDirections.setEnabled(false);
		
		//TODO: Clear destinations
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
		mapPanel.loadMap(mapName);
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
				MultiPath path = UserPointGraphicsObject.route();
				Route route = new Route(path);
				for(Instruction i: route.getRoute()){
					txtDirections.setText(txtDirections.getText() + i.getInstruction());
				}
				break;
			case "Print":
				printDirections();
				System.out.println("Print");
				break;
			case "Place Mode":
				clearNodeInfo();
				if(mapPanel.getDevMode() != EditorToolMode.Point){
					mapPanel.setDevMode(EditorToolMode.Point);
				}
				else{
				mapPanel.setDevMode(EditorToolMode.None);				
				}
			
				btnDelNode.setSelected(false);
				btnRemoveEdge.setSelected(false);
				btnEdgeMode.setSelected(false);

				break;
			case "Delete Mode":
				clearNodeInfo();
				if(mapPanel.getDevMode() != EditorToolMode.DeletePoint){
				mapPanel.setDevMode(EditorToolMode.DeletePoint);
				}
				else{
				mapPanel.setDevMode(EditorToolMode.None);	
				}
				
				btnNode.setSelected(false);
				btnRemoveEdge.setSelected(false);
				btnEdgeMode.setSelected(false);
				break;
			case "Edge Mode":
				clearNodeInfo();
				if(mapPanel.getDevMode() != EditorToolMode.Edge){
				mapPanel.setDevMode(EditorToolMode.Edge);
				}
				else{
				mapPanel.setDevMode(EditorToolMode.None);
				}
				
				btnNode.setSelected(false);
				btnDelNode.setSelected(false);
				btnRemoveEdge.setSelected(false);
				break;
			case "Remove Edge":
				clearNodeInfo();
				if(mapPanel.getDevMode() != EditorToolMode.DeleteEdge){
				mapPanel.setDevMode(EditorToolMode.DeleteEdge);
				}
				else{
				mapPanel.setDevMode(EditorToolMode.None);
				}

				btnNode.setSelected(false);
				btnDelNode.setSelected(false);
				btnEdgeMode.setSelected(false);
				break;
			default:
			}
		}
	}

	public AppUIObject() {

		instance = this;
		
		// debug statements
		System.out.println(System.getProperty("user.dir"));

		mainPanel.add(mapPanel);
		mapPanel.setVisible(false);
		
		System.out.println(
				"Image Size X: " + mapPanel.getSize().getWidth() + " Y: " + mapPanel.getSize().getHeight());

		directionsPanel.setBounds(1024, 6, 237, 664);
		frame.getContentPane().add(directionsPanel);
		directionsPanel.setLayout(null);
		
		connectingPointTextField = new JTextField();
		connectingPointTextField.setBounds(140, 282, 95, 26);
		directionsPanel.add(connectingPointTextField);
		connectingPointTextField.setColumns(10);
		
		connectingMapTextField = new JTextField();
		connectingMapTextField.setBounds(140, 254, 95, 26);
		directionsPanel.add(connectingMapTextField);
		connectingMapTextField.setColumns(10);
		
		final JLabel lblConnectingPoint = new JLabel("Connecting Point:");
		lblConnectingPoint.setBounds(26, 287, 117, 16);
		directionsPanel.add(lblConnectingPoint);
		
		final JLabel lblConnectingMap = new JLabel("Connecting Map:");
		lblConnectingMap.setBounds(24, 259, 108, 16);
		directionsPanel.add(lblConnectingMap);

		btnEmail.setBounds(26, 629, 106, 29);
		directionsPanel.add(btnEmail);
				
		/*btnUseWeather.setBounds(108, 42, 127, 25);
		directionsPanel.add(btnUseWeather);
		btnUseWeather.setVisible(true);*/

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

		btnGetDirections.setBounds(30, 213, 157, 36);
		directionsPanel.add(btnGetDirections);
		btnGetDirections.setEnabled(false);

		btnNode.setBounds(0, 79, 127, 25);

		directionsPanel.add(btnNode);

		btnDelNode.setBounds(0, 116, 127, 25);

		directionsPanel.add(btnDelNode);

		lblDirections.setBounds(0, 243, 80, 25);
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
		
		txtDirections.setBounds(0, 270, 220, 350);
		//directionsPanel.add(txtDirections);
		
		final JButton btnAddDest = new JButton("+ Dest");
		btnAddDest.setBounds(0, 76, 117, 25);
		directionsPanel.add(btnAddDest);
		
		final JButton btnRemoveDest = new JButton("- Dest");
		btnRemoveDest.setBounds(118, 76, 117, 25);
		directionsPanel.add(btnRemoveDest);		
		
		comboWeather.setBounds(118, 40, 117, 24);
		directionsPanel.add(comboWeather);
		
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

		frame.getContentPane().setLayout(null);
        
		//Destinations
        btnAddDest.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                destinations.addDestination();
                System.out.println("ADD dest");
            }
        });
        
        btnRemoveDest.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                destinations.removeDestination();
                System.out.println("SUB dest");
            }
        });

		// Dev Mode
		btnDevMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!devMode) {
					setTypeSelectorEditable(false);
					setNodeTextFieldEditable(false);
					setMapConnectionTextFieldEditable(false);
					setPointConnectionTextFieldEditable(false);
					btnGetDirections.setVisible(false);
					txtDevPass.setVisible(true);
					btnSubmit.setVisible(true);	
					
					btnAddDest.setVisible(false);
					btnRemoveDest.setVisible(false);
					destinations.setVisibility(false);
					/*if(mapPanel.selectedPoint == null){
						nodeTextField.setText("");
						typeSelector.setSelectedIndex(0);
					}else{
						nodeTextField.setText(mapPanel.selectedPoint.getId());
						typeSelector.setSelectedItem(mapPanel.selectedPoint.getType());
					}*/					
					txtDirections.setText("Enter the password and click submit!");
					btnDevMode.setText("User mode");
					devMode = true; //not actually true, but in order to switch without pass	
					onEnterDevMode();
				} else {
					devMode = false;
					frame.setTitle("Path Finder");
					btnDevMode.setText("Dev Mode");
					currentDevMode = DevMode.none;
					connectingMapTextField.setVisible(false);
					connectingPointTextField.setVisible(false);
					lblConnectingMap.setVisible(false);
					lblConnectingPoint.setVisible(false);
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
					//btnUseWeather.setVisible(true);
					comboWeather.setVisible(true);
					txtDirections.setText("");
//					txtStart.setText("");
//					txtEnd.setText("");
					btnAddDest.setVisible(true);
					btnRemoveDest.setVisible(true);
					destinations.setVisibility(true);
					onEnterUserMode();
				}

				reDrawUI();
				/*mapPanel.selectedPoint = null;
				mapPanel.startPoint = null;
				mapPanel.endPoint = null;
				mapPanel.currentRoute = null;*/
//				txtEnd.setVisible(!txtEnd.isVisible());
//				txtStart.setVisible(!txtStart.isVisible());
//				lblStart.setVisible(!lblStart.isVisible());
//				lblEnd.setVisible(!lblEnd.isVisible());

			}
		});
		//password for devmode code
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(String.valueOf(txtDevPass.getPassword()).equals("0011")){
					connectingMapTextField.setVisible(true);
					connectingPointTextField.setVisible(true);
					lblConnectingMap.setVisible(true);
					lblConnectingPoint.setVisible(true);
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
					comboWeather.setVisible(false);
					//btnUseWeather.setVisible(false);
					
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
				
				//Copy the textbox to the type
				if(DevPointGraphicsObject.getSelected() != null)
				DevPointGraphicsObject.getSelected().updatePoint();
				
				//clearNodeInfo();
				
				//toggle buttons
				currentDevMode = DevMode.none;				
				System.out.println("null mode");
				btnNode.setSelected(false);
				btnDelNode.setSelected(false);
				btnRemoveEdge.setSelected(false);
				btnEdgeMode.setSelected(false);
				
				Serializer.write(mapPanel.currentMap);
				//XML.writePoints(mapPanel.currentMap);
				lblScale.setText("Scale: " + mapPanel.currentMap.getScale() + " inches per ft");
			}
		});
		
		/*btnUseWeather.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("You clicked weather button!");
			}
		});*/

		mapDropDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
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
			}
		});
		
		JScrollPane directionsPane = new JScrollPane(txtDirections);
		directionsPane.setBounds(txtDirections.getBounds());
		directionsPane.setVisible(true);
		directionsPanel.add(directionsPane);
		
		//mapPanel.addMouseListener(mouseClick);
		
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
		typeSelector.addItem(pointTypes[3]);
		
		//comboWeather
		comboWeather.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				switch(comboWeather.getSelectedIndex()){
				case 0:
					System.out.println("Weather");
					break;
				case 1:
					System.out.println("No Preference");
					break;
				case 2:
					System.out.println("Prefer Outside");
					break;
				case 3:
					System.out.println("Prefer Inside");
					break;
				default:
					System.out.println("Switch test");
					break;
				}
				
			}
		});
		
		//Type selector
		typeSelector.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					if (getTypeSelector() != pointTypes[0]) {
						ConnectionPoint cPoint = DevPointGraphicsObject.getSelected().getRepresentedObject().getConnectionPoint();
						String connMap = cPoint.getLinkedMapsString() == null ? "" : cPoint.getLinkedMapsString();
						String connPoint = cPoint.getLinkedPointsString() == null ? "" : cPoint.getLinkedPointsString();
						setMapConnectorText(connMap);
						setPointConnectorText(connPoint);
						setMapConnectionTextFieldEditable(true);
						setPointConnectionTextFieldEditable(true);
					} else {
						setMapConnectorText("");
						setPointConnectorText("");
						setMapConnectionTextFieldEditable(false);
						setPointConnectionTextFieldEditable(false);
					}
				}
		});
		
		//nodeTextField.setText(mapPanel.selectedPoint.getId());
		
		//typeSelector.setSelectedItem(mapPanel.selectedPoint.getType());		
		
//		lblStart.setBounds(0, 72, 70, 15);
//		directionsPanel.add(lblStart);		
//		
//		lblEnd.setBounds(0, 102, 70, 15);
//		directionsPanel.add(lblEnd);
//		
//		txtStart = new JTextField();
//		txtStart.setBounds(40, 70, 165, 19);
//		directionsPanel.add(txtStart);
//		txtStart.setColumns(10);
//		
//		txtEnd = new JTextField();
//		txtEnd.setColumns(10);
//		txtEnd.setBounds(40, 100, 165, 19);
//		directionsPanel.add(txtEnd);
		
		
	
		/*if(typeSelector.getSelectedIndex() == -1){
			typeSelector.setSelectedIndex(0);
		}*/
		
		connectingMapTextField.setVisible(false);
		connectingPointTextField.setVisible(false);
		lblConnectingMap.setVisible(false);
		lblConnectingPoint.setVisible(false);
		
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
	}

	/*public void updatePoint() {
		nodeTextField.setText(mapPanel.selectedPoint.getId());
		if(mapPanel.selectedPoint.getType() == null){
			typeSelector.setSelectedIndex(2);
		}else{
			System.out.println(mapPanel.selectedPoint.getType());
			typeSelector.setSelectedItem(mapPanel.selectedPoint.getType());
		}
	}*/
	
	public MultiPath getLastRoute()
	{
		return lastRoutedPath;
	}
	
	public void clearLastRoute()
	{
		lastRoutedPath = null;
	}
	
	private void onEnterDevMode()
	{
		mapPanel.onEnterDevMode();
	}
	
	private void onEnterUserMode()
	{
		mapPanel.onEnterUserMode();
	}
}
