package WPI.CampusMap;
import javax.swing.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	JFrame frame = new JFrame("HelloWorldSwing");
        final JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        System.out.println( "This is a CI Test" );
        
    }
}
