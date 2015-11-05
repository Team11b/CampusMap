package WPI.CampusMap;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class MainViewFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MainViewFrame() {
		System.out.println("2");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println("1");
		setBounds(100, 100, 450, 300);
		System.out.println("Created the frame");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAllComedyIs = new JLabel("All comedy is derived from fear.");
		lblAllComedyIs.setBounds(105, 62, 229, 16);
		contentPane.add(lblAllComedyIs);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		System.out.println("In Main");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("It's working");
					MainViewFrame frame = new MainViewFrame();
					System.out.println("It's working2");
					frame.setVisible(true);
				} catch (Exception e) {
					System.out.println("Error!");
					e.printStackTrace();
				}
			}
		});
	}

	
}
