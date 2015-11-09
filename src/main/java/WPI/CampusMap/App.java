package WPI.CampusMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.sun.javafx.geom.Rectangle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

/**
 * Hello world!
 *
 */
public class App 
{
	
	private static void sendEmail(){
		
	}
	
	private static void getAndDisplayDirections(){
		
	}
	
	private static void printDirections(){
		
	}
	
	private static void displayMapView(){
		
	}

	private static void displayEditView(){
	
	}
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
				case "Map":
					System.out.println("Map");
					displayMapView();
					break;
				case "Edit":
					System.out.println("Edit");
					displayEditView();
					break;
				case "Get Directions":
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
		
    public static void main( String[] args )
    {
    	SwingAction actionHandler = new App.SwingAction();
    	
    	final JFrame frame = new JFrame("Path Finder");
    	frame.getContentPane().setLayout(null);
    	
    	final JPanel mainPanel = new JPanel();
    	mainPanel.setBounds(6, 6, 539, 555);
    	frame.getContentPane().add(mainPanel);
    	mainPanel.setLayout(null);
    	
    	JButton btnMap = new JButton("Map");
    	btnMap.addActionListener(actionHandler);
    	btnMap.setBounds(402, 5, 75, 29);
    	mainPanel.add(btnMap);
    	
    	JButton btnEdit = new JButton("Edit");
    	btnEdit.addActionListener(actionHandler);
    	btnEdit.setBounds(464, 5, 75, 29);
    	mainPanel.add(btnEdit);
    	
    	JButton btnGetDirections = new JButton("Get Directions");
    	btnGetDirections.addActionListener(actionHandler);
    	btnGetDirections.setBounds(399, 520, 134, 29);
    	mainPanel.add(btnGetDirections);
    	
    	JLabel lblMapviewGoesHere = new JLabel("MapView goes here.");
    	lblMapviewGoesHere.setBounds(150, 44, 146, 16);
    	mainPanel.add(lblMapviewGoesHere);
    	
    	//load map    	
        System.out.println(System.getProperty("user.dir"));

    	//picture init code
        BufferedImage myPicture = null;
        try {
			myPicture = ImageIO.read(new File("walkingmap.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	final JLabel lblPicLabel = new JLabel(new ImageIcon(myPicture));
    	lblPicLabel.setBounds(115, 86, 311, 389);
    	mainPanel.add(lblPicLabel);
    	lblPicLabel.setVisible(false);
    	
    	//map button code    	
    	JButton btnLoadMap = new JButton("load map");
    	btnLoadMap.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) 
    		{
    	        if(lblPicLabel.isVisible()== true)
    	        {
    	        	lblPicLabel.setVisible(false);
    	        }
    	        else
    	        {
    	        	lblPicLabel.setVisible(true);
    	        }
    		}
    	});
    	btnLoadMap.setBounds(179, 7, 117, 25);
    	mainPanel.add(btnLoadMap);
    	
    	
		
		
		JPanel directionsPanel = new JPanel();
    	directionsPanel.setBounds(557, 6, 237, 555);
    	frame.getContentPane().add(directionsPanel);
    	directionsPanel.setLayout(null);
    	
    	JLabel lblDirections = new JLabel("Directions\ngo here");
    	lblDirections.setBounds(6, 6, 225, 41);
    	directionsPanel.add(lblDirections);
    	
    	JButton btnEmail = new JButton("Email");
    	btnEmail.addActionListener(actionHandler);
    	btnEmail.setBounds(0, 520, 117, 29);
    	directionsPanel.add(btnEmail);
    	
    	JButton btnPrint = new JButton("Print");
    	btnPrint.addActionListener(actionHandler);
    	btnPrint.setBounds(114, 520, 117, 29);
    	directionsPanel.add(btnPrint);
    	
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
