package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
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
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.xml.stream.XMLStreamException;

import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Map.ProxyMap;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.Path.MultiPath;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.Route.Instruction;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.Route.Route;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Dev.EditorToolMode;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.Dev.DevPointGraphicsObject;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.User.UserPathGraphicsObject;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.User.UserPointGraphicsObject;

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
	private final  JPanel directionsPanel = new  JPanel();
	private final JButton btnEmail = new JButton("Email");
	private final JButton btnPrint = new JButton("Print");
	private final JLabel lblDirections = new JLabel("Directions:");
	protected final JButton btnGetDirections = new JButton("Route me");	
	private final JToggleButton btnNode = new JToggleButton("Place Mode");
	private final JToggleButton btnDelNode = new JToggleButton("Delete Mode");
	private final JLabel lblMapColon = new JLabel("Map:");
	private final JButton btnDevMode = new JButton("Dev Mode");
	private final JButton btnSave = new JButton("Save");
	private final String[] pointTypes = {RealPoint.HALLWAY, RealPoint.STAIRS, RealPoint.ELEVATOR, RealPoint.OUT_DOOR};
	private JComboBox<String> typeSelector = new JComboBox<String>();
	private final String[] weatherTypes = {"Weather", "No Preference", "Prefer Outside", "Prefer Inside"};	private final JTextPane txtDirections = new JTextPane();
	private JComboBox<String> comboWeather = new JComboBox<String>(weatherTypes);
	//	private JLabel lblStart = new JLabel("Start:");
//	private JLabel lblEnd = new JLabel("End:");
//	private JTextField txtStart;
//	private JTextField txtEnd;
	//private JToggleButton btnUseWeather = new JToggleButton("Use Weather");
	private final ArrayList<String> mapXMLStrings = new ArrayList<String>();
	private JComboBox<String> mapDropDown = new JComboBox<String>();
//	private String[] mapStrings;
	private final SwingAction actionHandler = new SwingAction();

	private final JToggleButton btnRemoveEdge = new JToggleButton("Remove Edge");
	private final JToggleButton btnEdgeMode = new JToggleButton("Edge Mode");
	private final JButton btnSubmit;
	private JTextField txtScale;
	private JPasswordField txtDevPass;
	private final JLabel lblNodeId = new JLabel("Node ID:");
	private JTextField nodeTextField;
	private JScrollPane directionsPane = new JScrollPane(txtDirections);
	private ConnectionNodeList connectionEditorList;
	
   public  Destinations destinations = new Destinations(directionsPanel);

	
	protected enum DevMode{
		none, addNode, addEdge, deleteNode, deleteEdge;
	}
	
	protected DevMode currentDevMode = DevMode.none;
	
	
	public static AppUIObject get()
	{
		return instance;
	}
	
	public void clearDestinations(){
		destinations.resetLastPoint();
		
	}
	private void clearNodeInfo(){
		typeSelector.setSelectedIndex(0);
		nodeTextField.setText("");
	}
	
	//getters for the BennyZone(tm)
	public String getID(){
		return nodeTextField.getText();
	}
	
	public String getTypeSelector(){
		return typeSelector.getItemAt(typeSelector.getSelectedIndex()); 
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
		File[] listOfFiles = new File("maps/").listFiles((new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(".png");
		    }
		}));
		mapXMLStrings.clear();
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {					        
	        int ext = listOfFiles[i].getName().lastIndexOf(".png"); //snip extension     
	        mapXMLStrings.add(listOfFiles[i].getName().substring(0, ext));	       	        
	      } 
	    }
	    //put in alphabetical order and convert to string array
	    mapXMLStrings.sort(null);
	    if(mapDropDown == null){
	    	mapDropDown = new JComboBox<String>();
	    }
	    else{
	    	mapDropDown.removeAllItems();
	    }
	    
	    for(int j = 0; j < listOfFiles.length; j++)
	    {
	    	if(mapXMLStrings.get(j) != null){
	    		String fileName = mapXMLStrings.get(j);
	    		AllMaps.getInstance().addMap(new ProxyMap(fileName));
	    		mapDropDown.addItem(fileName);
	    	}
	    }
	}
	
	public void onPointAddedToRoute(RealPoint newPoint)
	{
		btnGetDirections.setEnabled(true);
		
		//Clear old data from directions
		if(txtDirections.getText().isEmpty()== false){
			destinations.resetLastPoint();
			txtDirections.setText("");
		}
		
		destinations.setDestination(newPoint.getId());
	}
	
	public void onRouteCleared()
	{
		btnGetDirections.setEnabled(false);
		
		//TODO: Clear destinations
	}
	
	private RealPoint lastPoint;
	
	public void onPointSelected(RealPoint point)
	{
		if(lastPoint != null)
		{
			lastPoint.setId(nodeTextField.getText());
		}
		
		if(point == null)
		{
			setTypeSelector(0);
			setTypeSelectorEditable(false);
			setNodeTextFieldEditable(false);
			setNodeTextField("");
		}
		else
		{
			int index = 0;
			
			String typeString = point.getType() == null ? "" : point.getType();
			switch(typeString)
			{
			case "":
			case RealPoint.HALLWAY:
				index = 0;
				break;
			case RealPoint.STAIRS:
				index = 1;
				break;
			case RealPoint.ELEVATOR:
				index = 2;
				break;
			case RealPoint.OUT_DOOR:
				index = 3;
				break;
			}
			
			setTypeSelector(index);
			setTypeSelectorEditable(true);
			setNodeTextFieldEditable(true);
			setNodeTextField(point.getId());
		}
		
		lastPoint = point;
		
		connectionEditorList.setConnectionPoint(point);
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

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
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

		btnGetDirections.setBounds(12, 211, 157, 36);
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
		directionsPane.setBounds(txtDirections.getBounds());
		//directionsPanel.add(txtDirections);
		
		final JButton btnAddDest = new JButton("+ Dest");
		btnAddDest.setBounds(0, 76, 117, 25);
		directionsPanel.add(btnAddDest);
		
		final JButton btnRemoveDest = new JButton("- Dest");
		btnRemoveDest.setBounds(118, 76, 117, 25);
		directionsPanel.add(btnRemoveDest);		
		
		final JButton btnClear = new JButton("Clear");	
		btnClear.setFont(new Font("DejaVu Serif Condensed", Font.BOLD, 13));
		btnClear.setBounds(171, 211, 70, 36);
		directionsPanel.add(btnClear);
		
		comboWeather.setBounds(118, 40, 117, 24);
		directionsPanel.add(comboWeather);
		comboWeather.setVisible(false);
		
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
					btnClear.setVisible(false);
					setTypeSelectorEditable(false);
					setNodeTextFieldEditable(false);
					btnGetDirections.setVisible(false);
					lblDirections.setVisible(false);
					txtDevPass.setVisible(true);
					btnSubmit.setVisible(true);	
					directionsPane.setVisible(false);
					
					btnAddDest.setVisible(false);
					btnRemoveDest.setVisible(false);
					destinations.setVisibility(false);
									
					txtDirections.setText("Enter the password and click submit!");
					btnDevMode.setText("User mode");					
					devMode = true; //not actually true, but in order to switch without pass	
					onEnterDevMode();					
				} else {
					devMode = false;
					btnClear.setVisible(true);
					frame.setTitle("Path Finder");
					btnDevMode.setText("Dev Mode");
					directionsPane.setVisible(true);
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
					//btnUseWeather.setVisible(true);
//					comboWeather.setVisible(true);
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
//					comboWeather.setVisible(false);
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
				
				//clearNodeInfo();
				
				//toggle buttons
				currentDevMode = DevMode.none;				
				System.out.println("null mode");
				btnNode.setSelected(false);
				btnDelNode.setSelected(false);
				btnRemoveEdge.setSelected(false);
				btnEdgeMode.setSelected(false);
				
				mapPanel.currentMap.save();
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
					txtDirections.setText("");
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
					doc.insertString(0, "", null);
					//doc.insertString(doc.getLength(), "Ignored", labelStyle);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		txtDirections.setEditable(false);
		directionsPane.setBounds(txtDirections.getBounds());
		directionsPane.setBounds(0, 267, 235, 350);
		directionsPane.setVisible(true);
		directionsPanel.add(directionsPane);
		
		connectionEditorList = new ConnectionNodeList();
		connectionEditorList.setToolTipText("Test");
		connectionEditorList.setBounds(0, 300, 220, 300);
		connectionEditorList.setVisible(false);
		directionsPanel.add(connectionEditorList);
		
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
					if(DevPointGraphicsObject.getSelected() == null)
						return;
					
					if(getTypeSelector().equalsIgnoreCase(DevPointGraphicsObject.getSelected().getRepresentedObject().getType()))
						return;
					DevPointGraphicsObject.getSelected().setType(getTypeSelector());
					
//					if (getTypeSelector() != pointTypes[0])
//					{
//						DevPointGraphicsObject.getSelected().convertToConnectionPoint(getTypeSelector());
//					} 
//					else 
//					{
//						DevPointGraphicsObject.getSelected().convertToNormalPoint(getTypeSelector());
//					}
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
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				destinations.resetLastPoint();
				UserPointGraphicsObject.clearSelected();
				UserPathGraphicsObject.deleteAll();
			}
		});

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
			loadMap(mapDropDown.getItemAt(0));
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
		connectionEditorList.setConnectionPoint(null);
		mapPanel.onEnterUserMode();
	}
}
