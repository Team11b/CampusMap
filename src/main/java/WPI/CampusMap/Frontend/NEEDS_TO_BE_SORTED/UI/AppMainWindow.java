package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Panel;

import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Map.IMap;
import WPI.CampusMap.Backend.Core.Map.ProxyMap;
import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.PathPlanning.Path;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.Dev.DevEdgeGraphicsObject;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.Dev.DevPointGraphicsObject;

import javax.swing.JLabel;
import java.awt.Component;
import java.awt.Container;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JProgressBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import javax.swing.KeyStroke;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.SpringLayout;
import java.awt.Choice;
import java.awt.Label;

public class AppMainWindow extends JFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7669431755757020202L;

	private JLabel taskName;
	private JProgressBar progressBar;
	private Thread renderThread;

	private UIMode currentMode;
	private AppUserModeControl userPanel;
	private AppDevModeControl devPanel;
	private final ArrayList<String> mapStrings = new ArrayList<String>();
	private final Action devModeAction = new DevModeAction();
	private final Action printAction = new PrintAction();
	
	private JMenu mnMaps = new JMenu("Maps");
	private Choice mapDropdown;

	private Panel sharedArea = new Panel();
	private Panel infoArea = new Panel();

	public AppMainWindow() {
		super();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(new Dimension(1200, 1900));
		// setMinimumSize(new Dimension(600, 400));
		setLocation(100, 100);
		setBounds(0, 0, 1300, 1000);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(1);
		splitPane.setDividerSize(5);
		getContentPane().add(splitPane, BorderLayout.CENTER);

		splitPane.setRightComponent(sharedArea);
		sharedArea.setMinimumSize(new Dimension(300, 200));

		userPanel = new AppUserModeControl(this);
		devPanel = new AppDevModeControl(this);
		
		SpringLayout sl_sharedArea = new SpringLayout();
		sharedArea.setLayout(sl_sharedArea);
		
		sl_sharedArea.putConstraint(SpringLayout.WEST, infoArea, 0, SpringLayout.WEST, sharedArea);
		sl_sharedArea.putConstraint(SpringLayout.SOUTH, infoArea, 0, SpringLayout.SOUTH, sharedArea);
		sl_sharedArea.putConstraint(SpringLayout.EAST, infoArea, 0, SpringLayout.EAST, sharedArea);
		sharedArea.add(infoArea);
		
		mapDropdown = new Choice();
		sl_sharedArea.putConstraint(SpringLayout.NORTH, infoArea, 10, SpringLayout.SOUTH, mapDropdown);
		sl_sharedArea.putConstraint(SpringLayout.NORTH, mapDropdown, 25, SpringLayout.NORTH, sharedArea);
		sl_sharedArea.putConstraint(SpringLayout.WEST, mapDropdown, 10, SpringLayout.WEST, sharedArea);
		sl_sharedArea.putConstraint(SpringLayout.EAST, mapDropdown, -10, SpringLayout.EAST, sharedArea);
		sharedArea.add(mapDropdown);
		
		infoArea.setLayout(new BorderLayout());
		
		Label label = new Label("Map");
		sl_sharedArea.putConstraint(SpringLayout.WEST, label, 0, SpringLayout.WEST, mapDropdown);
		sl_sharedArea.putConstraint(SpringLayout.SOUTH, label, 0, SpringLayout.NORTH, mapDropdown);
		sharedArea.add(label);

		MapPanel mapPanel = new MapPanel();
		splitPane.setLeftComponent(mapPanel);

		Panel bottomPane = new Panel();
		getContentPane().add(bottomPane, BorderLayout.SOUTH);
		bottomPane.setLayout(new BorderLayout(0, 0));

		progressBar = new JProgressBar();
		bottomPane.add(progressBar, BorderLayout.EAST);

		taskName = new JLabel("<Task Name>");
		bottomPane.add(taskName, BorderLayout.WEST);

		Component verticalStrut = Box.createVerticalStrut(20);
		bottomPane.add(verticalStrut, BorderLayout.CENTER);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.control);
		getContentPane().add(menuBar, BorderLayout.NORTH);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmSaveAsPdf = new JMenuItem("Save As PDF");
		mnFile.add(mntmSaveAsPdf);

		JMenuItem mntmSaveAsTxt = new JMenuItem("Save As TXT");
		mntmSaveAsTxt.addActionListener(UserMode.txtExporterAction);
		mnFile.add(mntmSaveAsTxt);

		JMenu mnSendAs = new JMenu("Send As...");
		mnFile.add(mnSendAs);

		JMenuItem mntmEmail = new JMenuItem("Email");
		mntmEmail.addActionListener(UserMode.emailAction);
		mnSendAs.add(mntmEmail);

		JMenuItem mntmSms = new JMenuItem("SMS");
		mntmSms.addActionListener(UserMode.SMSAction);
		mnSendAs.add(mntmSms);

		JMenuItem mntmPrint = new JMenuItem("Print");
		mntmPrint.setAction(printAction);
		mnFile.add(mntmPrint);

		JMenu mnRouting = new JMenu("Routing");
		menuBar.add(mnRouting);

		JMenu mnIndooroutdoor = new JMenu("Indoor/Outdoor");
		mnRouting.add(mnIndooroutdoor);

		JRadioButtonMenuItem rdbtnmntmPreferIndoor = new JRadioButtonMenuItem("Prefer Indoor");
		mnIndooroutdoor.add(rdbtnmntmPreferIndoor);

		JRadioButtonMenuItem rdbtnmntmPreferOutdoor = new JRadioButtonMenuItem("Prefer Outdoor");
		mnIndooroutdoor.add(rdbtnmntmPreferOutdoor);

		JRadioButtonMenuItem rdbtnmntmUseWeather = new JRadioButtonMenuItem("Use Weather");
		mnIndooroutdoor.add(rdbtnmntmUseWeather);

		menuBar.add(mnMaps);		
		getMaps();
		//mnMaps.addActionListener(topMapAction);

		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);

		JCheckBoxMenuItem chckbxmntmDevMode = new JCheckBoxMenuItem("Dev Mode");
		chckbxmntmDevMode.setAction(devModeAction);
		mnSettings.add(chckbxmntmDevMode);

		renderThread = new Thread(this, "Render Thread");
		renderThread.start();

		changeToUserMode();
		setVisible(true);
	}

	/**
	 * Gets the current UI mode.
	 * 
	 * @return The current UI mode.
	 */
	public UIMode getUIMode() {
		return currentMode;
	}

	/**
	 * Gets the current UI mode as a DevMode.
	 * 
	 * @return The current UI mode as a DevMode, null if not in dev mode.
	 */
	public DevMode getDevMode() {
		if (currentMode instanceof DevMode)
			return (DevMode) currentMode;
		return null;
	}

	/**
	 * Gets the current UI mode as a UserMode.
	 * 
	 * @return The current UI mode as a UserMode, null if not in user mode.
	 */
	public UserMode getUserMode() {
		if (currentMode instanceof UserMode)
			return (UserMode) currentMode;
		return null;
	}

	public void changeToDevMode() {
		Container parent = infoArea;
		parent.remove(userPanel);
		parent.add(devPanel, BorderLayout.CENTER);
		parent.revalidate();
		parent.repaint();		
		
		this.setTitle("Dev Mode");
		currentMode = new DevMode(this);
		currentMode.onModeEntered();
	}

	public void changeToUserMode() {
		Container parent = infoArea;
		parent.remove(devPanel);
		parent.add(userPanel, BorderLayout.CENTER);
		parent.revalidate();
		parent.repaint();

		this.setTitle("Path Finder");
		currentMode = new UserMode(this);
		currentMode.onModeEntered();
	}

	/**
	 * Adds a map to be registered by the UI. This will add the map to all
	 * required lists.
	 * 
	 * @param map
	 *            The map to be registered by the UI.
	 */
	public void addMap(IMap map) {
		throw new UnsupportedOperationException("not implemented");
	}

	/**
	 * Clears all registered maps.
	 */
	public void clearMaps() {
		throw new UnsupportedOperationException("not implemented");
	}
	
	private void getMaps(){
	//get all the files in the directory
			File[] listOfFiles = new File("maps/").listFiles((new FilenameFilter() {
			    public boolean accept(File dir, String name) {
			        return name.toLowerCase().endsWith(".png");
			    }
			}));
			mapStrings.clear();
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {					        
		        int ext = listOfFiles[i].getName().lastIndexOf(".png"); //snip extension     
		        mapStrings.add(listOfFiles[i].getName().substring(0, ext));	       	        
		      } 
		    }
		    //put in alphabetical order and convert to string array
		    mapStrings.sort(null);
		   
		    if(mnMaps.getItemCount() == 0){			    	
		    	System.out.println("itemcount 0");
		    }
		    else{
		    	//mapDropDown.removeAllItems();
		    	mnMaps.removeAll();
		    }
		    
		    String currentBuilding, currentFloor;
		    ArrayList<String> ddBuildings = new ArrayList<String>();
		    for(int j = 0; j < listOfFiles.length; j++)
		    {
		    	if(mapStrings.get(j) != null){
		    		String fileName = mapStrings.get(j);			    		
		    		IMap aMap = AllMaps.getInstance().getMap(fileName);		
		    		currentBuilding = aMap.getBuilding();
		    		JMenu mnEx = null;
		    		//System.out.println("currentBuilding is "+ currentBuilding);
		    		if(!ddBuildings.contains(currentBuilding)){
		    			ddBuildings.add(currentBuilding);
		    			mnEx = new JMenu(currentBuilding);
		    			mnMaps.add(mnEx);
		    		}
		    		else{
		    			//System.out.println("Building found!");
		    			for(int i = 0; i < mnMaps.getItemCount(); i++){
		    				if(mnMaps.getItem(i).getText().equals(currentBuilding))
		    					mnEx = (JMenu)mnMaps.getItem(i);
		    			}			    			
		    		}
		    		JMenuItem mntmFloor = new JMenuItem(aMap.getName());
		    		if(mnEx != null){
		    			mnEx.add(mntmFloor);
		    			makeALMenuItem(mntmFloor, aMap.getName(), mnEx);		    			
		    		}
		    			
		    	}
		    }
     }
	
	//Creates a custom handler to load a map
	private void makeALMenuItem(JMenuItem aMenuItem, String mapName, JMenu building){
		//incase we start polling maps again ...
		if(aMenuItem.getActionListeners().length == 0) 
		aMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				currentMode.loadMap(mapName);
				makeOtherDropDown(mapName, building);
				
			}
		});
		
		//hardcode for initial load
		if(mapName.equals("Atwater_Kent-3"))
			makeOtherDropDown("Atwater_Kent-0", building);
	}
	
	//called when a map is loaded from the topbar dropdown
	private void makeOtherDropDown(String mapName, JMenu building) {
		// clear the dropdown
		mapDropdown.removeAll();

		//if its a normal map
		if (!mapName.equals("CampusMap")) {
			// Add the buildings
			mapDropdown.add("CampusMap");
			for (int i = 0; i < building.getItemCount(); i++) {
				mapDropdown.add(building.getItem(i).getText());
			}
		} else {
			//load all the base
			for (int i = 0; i < mnMaps.getItemCount(); i++) {
				mapDropdown.add(mnMaps.getItem(i).getText());
			}
		}

		// Clear all item listeners in the dropdown.
		for (ItemListener act : mapDropdown.getItemListeners()) {
			mapDropdown.removeItemListener(act);
		}

		// show the current map when selecting from the other dropdown
		mapDropdown.select(mapName);

		// add the listeners
		mapDropdown.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				String selectedMap =  e.getItem().toString();
				System.out.println("item selected " + selectedMap);
				
				//load zero floor in case of CampusMap
				if(mapName.equals("CampusMap"))
					selectedMap = selectedMap + "-0";
					
				currentMode.loadMap(selectedMap);

			}
		});
	}

	/**
	 * Forces the map to switch to a new map. This method acts like the drop
	 * down menu selected a new map, so loading should not occur on the backend.
	 * 
	 * @param map
	 */
	public void forceMapSwitch(IMap map) {
		throw new UnsupportedOperationException("not implemented");
	}

	/**
	 * Adds a destination to the destination list.
	 * 
	 * @param point
	 *            The point that represents the destination.
	 */
	public void addDestination(IPoint point) {
		throw new UnsupportedOperationException("not implemented");
	}

	/**
	 * Clears the destination list.
	 */
	public void clearDestinations() {
		throw new UnsupportedOperationException("not implemented");
	}

	/**
	 * Sets the route from path planning.
	 * 
	 * @param path
	 *            The new route.
	 */
	public void setRoute(Path path) {
		userPanel.setRoute(path);
	}

	/**
	 * Removes the current route in the GUI without loading in another one.
	 */
	public void clearRoute() {
		throw new UnsupportedOperationException("not implemented");
	}

	/**
	 * Called when a point has been selected in dev mode.
	 * 
	 * @param selectedPoint
	 *            The graphical point that has been selected.
	 */
	public void devPointSelected(DevPointGraphicsObject selectedPoint) {
		throw new UnsupportedOperationException("not implemented");
	}

	/**
	 * Clears the selection from the UI.
	 */
	public void devClearPointSelection() {
		throw new UnsupportedOperationException("not implemented");
	}

	/**
	 * Called when an edge has been selected in dev mode.
	 * 
	 * @param selectedEdge
	 *            The selected edge.
	 */
	public void devEdgeSelected(DevEdgeGraphicsObject selectedEdge) {
		throw new UnsupportedOperationException("not implemented");
	}	


	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(33);
			} catch (InterruptedException e) {
				return;
			}

			repaint();
		}
	}

	@SuppressWarnings("serial")
	private class DevModeAction extends AbstractAction {
		public DevModeAction() {
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
			putValue(NAME, "Dev Mode");
			putValue(SHORT_DESCRIPTION, "Switch to and from dev mode.");
		}

		public void actionPerformed(ActionEvent e) {
			JCheckBoxMenuItem item = (JCheckBoxMenuItem) e.getSource();

			if (item.isSelected()) {
				changeToDevMode();
			} else {
				changeToUserMode();
			}
		}
	}

	@SuppressWarnings("serial")
	private class PrintAction extends AbstractAction {
		public PrintAction() {
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
			putValue(NAME, "Print");
			putValue(SHORT_DESCRIPTION, "Prints the route to a printer.");
		}

		public void actionPerformed(ActionEvent e) {
		}
	}
}
