package WPI.CampusMap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class AppUIObject {

	/**
	 * Presents a view that allows the user to enter an email address 
	 * and send an email with the walking directions.
	 */
	private static void sendEmail(){
		
	}
	
	/**
	 * Calculates the walking path and displays the directions.
	 */
	private static void getAndDisplayDirections(){
		
	}
	
	/**
	 * Prints the walking directions.
	 */
	private static void printDirections(){
		
	}
	
	/**
	 * This class handles all Swing actions from the user interface.
	 * @author Will
	 *
	 */
	@SuppressWarnings("serial")
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
				case "Route me":
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
	
	public AppUIObject(){
		
		SwingAction actionHandler = new SwingAction();
    	
    	final JFrame frame = new JFrame("Path Finder");
    	frame.getContentPane().setLayout(null);
    	
    	final JPanel mainPanel = new JPanel();
    	mainPanel.setBounds(1, 6, 1018, 664);
    	frame.getContentPane().add(mainPanel);
    	mainPanel.setLayout(null);
    	
    	JButton btnGetDirections = new JButton("Route me");
    	btnGetDirections.addActionListener(actionHandler);
    	btnGetDirections.setBounds(894, 635, 112, 29);
    	mainPanel.add(btnGetDirections);
    	
    	final JLabel lblMapviewGoesHere = new JLabel("Map Title goes here");
    	lblMapviewGoesHere.setBounds(155, 0, 146, 16);
    	mainPanel.add(lblMapviewGoesHere);
    	
    	//load map
    	//picture init code
        BufferedImage myPicture = null;
        try {
			myPicture = ImageIO.read(new File("walkingmap.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //debug statements
        System.out.println(System.getProperty("user.dir"));
        System.out.println(myPicture.getWidth());
        System.out.println(myPicture.getHeight());
        
        //calculate how bit to make label and image itself
    	int height = 700; //700
    	double ratio = ((double)myPicture.getWidth()/myPicture.getHeight());
    	Double width = (ratio * (double)700);
    	int w2 = width.intValue();
    	System.out.println(ratio);
    	
    	final ImageIcon img = new ImageIcon(myPicture.getScaledInstance(w2, height, Image.SCALE_SMOOTH));    	
    	final JLabel lblPicLabel = new JLabel(img);    			
    	lblPicLabel.setBounds(5, 0, w2, height); //width 988
    	mainPanel.add(lblPicLabel);
    	lblPicLabel.setVisible(false);
    	
    	final JPanel directionsPanel = new JPanel();
    	directionsPanel.setBounds(1031, 6, 237, 664);
    	frame.getContentPane().add(directionsPanel);
    	directionsPanel.setLayout(null);
    	
    	final JTextPane txtDirections = new JTextPane();
    	txtDirections.setBounds(16, 30, 215, 579);
    	directionsPanel.add(txtDirections);
    	
    	//map button code    	
    	JButton btnLoadMap = new JButton("Load");
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
    	        	
    	        	lblMapviewGoesHere.setText("Walking map");
    	        	//huge shit show but demonstrates using a Jtextpane with an icon. this is how to do directions
    	            Icon icon = new ImageIcon("left.png");
    	            JLabel label = new JLabel(icon);
    	            StyleContext context = new StyleContext(); 
    	            Style labelStyle = context.getStyle(StyleContext.DEFAULT_STYLE);
    	            StyleConstants.setComponent(labelStyle, label);   	        	   	        	
    	        	StyledDocument doc = txtDirections.getStyledDocument();    	        	
    	            //Style def = StyleContext.getDefaultStyleContext().getStyle( StyleContext.DEFAULT_STYLE );
    	            //Style regular = doc.addStyle( "regular", def );
    	        	try {
    	        		doc.insertString(0, "Start of text\n", null );
    	        		doc.insertString(doc.getLength(), "Ignored", labelStyle);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	        }
    		}
    	});
    	btnLoadMap.setBounds(931, 2, 75, 29);
    	mainPanel.add(btnLoadMap);
    	
    	JButton btnEmail = new JButton("Email");
    	btnEmail.addActionListener(actionHandler);
    	btnEmail.setBounds(0, 635, 106, 29);
    	directionsPanel.add(btnEmail);
    	
    	JButton btnPrint = new JButton("Print");
    	btnPrint.addActionListener(actionHandler);
    	btnPrint.setBounds(114, 635, 111, 29);
    	directionsPanel.add(btnPrint);
    	
    	JLabel lblDirections = new JLabel("Directions:");
    	lblDirections.setBounds(16, 0, 225, 41);
    	directionsPanel.add(lblDirections);
    	
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
