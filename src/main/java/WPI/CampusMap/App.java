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
import java.awt.Color;
import java.awt.Dimension;

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
    	mainPanel.setBounds(6, 6, 539, 555);
    	frame.getContentPane().add(mainPanel);
    	mainPanel.setLayout(null);
    	
    	JButton btnMap = new JButton("Map");
    	btnMap.setBounds(402, 5, 75, 29);
    	mainPanel.add(btnMap);
    	
    	JButton btnEdit = new JButton("Edit");
    	btnEdit.setBounds(464, 5, 75, 29);
    	mainPanel.add(btnEdit);
    	
    	JButton btnGetDirections = new JButton("Get Directions");
    	btnGetDirections.setBounds(399, 520, 134, 29);
    	mainPanel.add(btnGetDirections);
    	
    	JLabel lblMapviewGoesHere = new JLabel("MapView goes here.");
    	lblMapviewGoesHere.setBounds(218, 286, 146, 16);
    	mainPanel.add(lblMapviewGoesHere);
    	
    	JPanel directionsPanel = new JPanel();
    	directionsPanel.setBounds(557, 6, 237, 555);
    	frame.getContentPane().add(directionsPanel);
    	directionsPanel.setLayout(null);
    	
    	JLabel lblDirections = new JLabel("Directions\ngo here");
    	lblDirections.setBounds(6, 6, 225, 41);
    	directionsPanel.add(lblDirections);
    	
    	JButton btnEmail = new JButton("Email");
    	btnEmail.setBounds(0, 520, 117, 29);
    	directionsPanel.add(btnEmail);
    	
    	JButton btnPrint = new JButton("Print");
    	btnPrint.setBounds(114, 520, 117, 29);
    	directionsPanel.add(btnPrint);
    	
    	JSeparator separator = new JSeparator();
    	separator.setBackground(Color.RED);
    	separator.setOrientation(SwingConstants.VERTICAL);
    	separator.setPreferredSize(new Dimension(50,10));
    	//separator.setBounds(100, 100, 174, 246);
    	frame.getContentPane().add(separator);
    	
    	frame.setSize(800, 600);
    	frame.setVisible(true);

        
    }
}
