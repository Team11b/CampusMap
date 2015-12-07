package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Button;
import java.awt.Canvas;
import javax.swing.SpringLayout;

import WPI.CampusMap.Backend.Core.Map.IMap;
import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.PathPlanning.Path;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.PointGraphicsObject;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.Dev.DevEdgeGraphicsObject;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.Dev.DevPointGraphicsObject;

import javax.swing.JComboBox;
import java.awt.Choice;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.Box;
import javax.swing.JRadioButton;
import javax.swing.JProgressBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JPanel;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import java.awt.event.InputEvent;

public class AppMainWindow extends JFrame implements Runnable
{
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
	private final Action devModeAction = new DevModeAction();
	private final Action printAction = new PrintAction();
	
	private Panel infoArea = new Panel();
	
	public AppMainWindow() {
		super();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(new Dimension(1200, 800));
		//setMinimumSize(new Dimension(600, 400));
		setLocation(200, 200);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(1);
		splitPane.setDividerSize(5);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		
		splitPane.setRightComponent(infoArea);
		infoArea.setLayout(new BorderLayout(0, 0));
		infoArea.setMinimumSize(new Dimension(300, 200));
		
		userPanel = new AppUserModeControl();
		devPanel = new AppDevModeControl();
		infoArea.add(userPanel, BorderLayout.CENTER);
		
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
		mnFile.add(mntmSaveAsTxt);
		
		JMenu mnSendAs = new JMenu("Send As...");
		mnFile.add(mnSendAs);
		
		JMenuItem mntmEmail = new JMenuItem("Email");
		mnSendAs.add(mntmEmail);
		
		JMenuItem mntmSms = new JMenuItem("SMS");
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
		
		JMenu mnMaps = new JMenu("Maps");
		menuBar.add(mnMaps);
		
		JMenu mnAk = new JMenu("AK");
		mnMaps.add(mnAk);
		
		JMenuItem mntmFirstFloor = new JMenuItem("First Floor");
		mnAk.add(mntmFirstFloor);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		JCheckBoxMenuItem chckbxmntmDevMode = new JCheckBoxMenuItem("Dev Mode");
		chckbxmntmDevMode.setAction(devModeAction);
		mnSettings.add(chckbxmntmDevMode);
		
		renderThread = new Thread(this, "Render Thread");
		renderThread.start();
		
		setVisible(true);
	}
	
	/**
	 * Gets the current UI mode.
	 * @return The current UI mode.
	 */
	public UIMode getUIMode()
	{
		return currentMode;
	}
	
	public void changeToDevMode()
	{
		Container parent = infoArea;
		parent.remove(userPanel);
		parent.add(devPanel);
		parent.revalidate();
		parent.repaint();
		
		currentMode = new DevMode(this);
		currentMode.onModeEntered();
	}
	
	public void changeToUserMode()
	{
		Container parent = infoArea;
		parent.remove(devPanel);
		parent.add(userPanel);
		parent.revalidate();
		parent.repaint();
		
		currentMode = new UserMode(this);
		currentMode.onModeEntered();
	}
	
	/**
	 * Adds a map to be registered by the UI. This will add the map to all required lists.
	 * @param map The map to be registered by the UI.
	 */
	public void addMap(IMap map)
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Clears all registered maps.
	 */
	public void clearMaps()
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Forces the map to switch to a new map. This method acts like the drop down menu selected a new map, so loading should not occur on the backend.
	 * @param map
	 */
	public void forceMapSwitch(IMap map)
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Adds a destination to the destination list.
	 * @param point The point that represents the destination.
	 */
	public void addDestination(IPoint point)
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Clears the destination list.
	 */
	public void clearDestinations()
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Sets the route from path planning.
	 * @param path The new route.
	 */
	public void setRoute(Path path)
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Removes the current route in the GUI without loading in another one.
	 */
	public void clearRoute()
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Called when a point has been selected in dev mode.
	 * @param selectedPoint The graphical point that has been selected.
	 */
	public void devPointSelected(DevPointGraphicsObject selectedPoint)
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Clears the selection from the UI.
	 */
	public void devClearPointSelection()
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Called when an edge has been selected in dev mode.
	 * @param selectedEdge The selected edge.
	 */
	public void devEdgeSelected(DevEdgeGraphicsObject selectedEdge)
	{
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public void run()
	{
		while(true)
		{
			try 
			{
				Thread.sleep(33);
			}
			catch (InterruptedException e)
			{
				return;
			}
			
			repaint();
		}
	}
	
	@SuppressWarnings("serial")
	private class DevModeAction extends AbstractAction
	{
		public DevModeAction()
		{
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
			putValue(NAME, "Dev Mode");
			putValue(SHORT_DESCRIPTION, "Switch to and from dev mode.");
		}
		
		public void actionPerformed(ActionEvent e)
		{
			JCheckBoxMenuItem item = (JCheckBoxMenuItem) e.getSource();
			
			if(item.isSelected())
			{
				changeToDevMode();
			}
			else
			{
				changeToUserMode();
			}
		}
	}
	
	
	@SuppressWarnings("serial")
	private class PrintAction extends AbstractAction
	{
		public PrintAction() {
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
			putValue(NAME, "Print");
			putValue(SHORT_DESCRIPTION, "Prints the route to a printer.");
		}
		public void actionPerformed(ActionEvent e)
		{
		}
	}
}
