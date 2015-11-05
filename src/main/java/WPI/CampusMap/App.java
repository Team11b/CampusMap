package WPI.CampusMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JButton;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	JFrame frame = new JFrame("HelloWorldSwing");
    	frame.getContentPane().setLayout(null);
    	
    	JPanel mainPanel = new JPanel();
    	mainPanel.setBounds(6, 6, 252, 310);
    	frame.getContentPane().add(mainPanel);
    	mainPanel.setLayout(null);
    	
    	JButton btnMap = new JButton("Map");
    	btnMap.setBounds(98, 5, 75, 29);
    	mainPanel.add(btnMap);
    	
    	JButton btnEdit = new JButton("Edit");
    	btnEdit.setBounds(171, 5, 75, 29);
    	mainPanel.add(btnEdit);
    	
    	JButton btnGetDirections = new JButton("Get Directions");
    	btnGetDirections.setBounds(112, 275, 134, 29);
    	mainPanel.add(btnGetDirections);
    	
    	JLabel lblMapviewGoesHere = new JLabel("MapView goes here.");
    	lblMapviewGoesHere.setBounds(48, 149, 146, 16);
    	mainPanel.add(lblMapviewGoesHere);
    	
    	JPanel directionsPanel = new JPanel();
    	directionsPanel.setBounds(270, 6, 118, 310);
    	frame.getContentPane().add(directionsPanel);
    	directionsPanel.setLayout(null);
    	
    	JLabel lblDirections = new JLabel("Directions\ngo here");
    	lblDirections.setBounds(6, 6, 106, 41);
    	directionsPanel.add(lblDirections);
    	
    	JButton btnEmail = new JButton("Email");
    	btnEmail.setBounds(-5, 275, 117, 29);
    	directionsPanel.add(btnEmail);
    	
    	frame.setSize(500, 500);
    	frame.setVisible(true);

        
    }
}
