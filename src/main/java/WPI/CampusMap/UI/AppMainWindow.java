package WPI.CampusMap.UI;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Button;
import java.awt.Canvas;
import javax.swing.SpringLayout;
import javax.swing.JComboBox;
import java.awt.Choice;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JRadioButton;
import javax.swing.JProgressBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.SystemColor;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class AppMainWindow extends JFrame implements Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7669431755757020202L;
	
	private JLabel taskName;
	private JProgressBar progressBar;
	private Thread renderThread;
	
	private UIMode mode;
	
	public AppMainWindow() {
		super();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(new Dimension(1200, 800));
		//setMinimumSize(new Dimension(600, 400));
		setLocation(200, 200);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
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
		mnSettings.add(chckbxmntmDevMode);
		
		Panel mainArea = new Panel();
		getContentPane().add(mainArea, BorderLayout.CENTER);
		SpringLayout sl_mainArea = new SpringLayout();
		mainArea.setLayout(sl_mainArea);
		
		Panel mapPanel = new Panel();
		sl_mainArea.putConstraint(SpringLayout.NORTH, mapPanel, 0, SpringLayout.NORTH, mainArea);
		sl_mainArea.putConstraint(SpringLayout.WEST, mapPanel, 0, SpringLayout.WEST, mainArea);
		sl_mainArea.putConstraint(SpringLayout.SOUTH, mapPanel, 0, SpringLayout.SOUTH, mainArea);
		sl_mainArea.putConstraint(SpringLayout.EAST, mapPanel, -300, SpringLayout.EAST, mainArea);
		mainArea.add(mapPanel);
		
		AppDevModeControl sidePanel = new AppDevModeControl();
		sl_mainArea.putConstraint(SpringLayout.NORTH, sidePanel, 0, SpringLayout.NORTH, mainArea);
		sl_mainArea.putConstraint(SpringLayout.WEST, sidePanel, 0, SpringLayout.EAST, mapPanel);
		sl_mainArea.putConstraint(SpringLayout.SOUTH, sidePanel, 0, SpringLayout.SOUTH, mainArea);
		sl_mainArea.putConstraint(SpringLayout.EAST, sidePanel, 0, SpringLayout.EAST, mainArea);
		mainArea.add(sidePanel);
		
		renderThread = new Thread(this, "Render Thread");
		renderThread.start();
		
		setVisible(true);
	}

	@Override
	public void run()
	{
		while(true)
		{
			try 
			{
				Thread.sleep(33);
			} catch (InterruptedException e)
			{
				return;
			}
			
			repaint();
		}
	}
}
