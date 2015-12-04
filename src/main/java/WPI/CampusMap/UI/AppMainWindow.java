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

public class AppMainWindow extends JFrame
{
	public AppMainWindow() {
		super();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(new Dimension(1200, 700));
		//setMinimumSize(new Dimension(600, 400));
		setLocation(200, 200);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.75);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		Panel mapDisplay = new Panel();
		splitPane.setLeftComponent(mapDisplay);
		
		Panel infoArea = new Panel();
		splitPane.setRightComponent(infoArea);
		infoArea.setLayout(new BorderLayout(0, 0));
		
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
		
		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		
		JMenuItem mntmDevMode = new JMenuItem("Dev Mode");
		mnOptions.add(mntmDevMode);
		
		setVisible(true);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7669431755757020202L;
	private JLabel taskName;
	private JProgressBar progressBar;
}
