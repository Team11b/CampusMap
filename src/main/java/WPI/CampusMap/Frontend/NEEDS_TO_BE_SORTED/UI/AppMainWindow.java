package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

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
		
		Panel infoArea = new Panel();
		splitPane.setRightComponent(infoArea);
		infoArea.setLayout(new BorderLayout(0, 0));
		infoArea.setMinimumSize(new Dimension(300, 200));
		
		userPanel = new AppUserModeControl();
		devPanel = new AppDevModeControl();
		infoArea.add(userPanel, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		
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
			if(!isDevMode)
			{
				isDevMode = true;
				
				Container parent = userPanel.getParent();
				parent.remove(userPanel);
				parent.add(devPanel);
				parent.revalidate();
				parent.repaint();
				
				currentMode = new DevMode();
			}
			else
			{
				isDevMode = false;
				
				Container parent = devPanel.getParent();
				parent.remove(devPanel);
				parent.add(userPanel);
				parent.revalidate();
				parent.repaint();
				
				currentMode = new UserMode();
			}
			
			currentMode.onModeEntered();
		}
		
		private boolean isDevMode;
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
