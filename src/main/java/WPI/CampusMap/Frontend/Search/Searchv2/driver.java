package WPI.CampusMap.Frontend.Search.Searchv2;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import WPI.CampusMap.Backend.Core.Point.AllPoints;

public class driver {

	public driver() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		final String COMMIT_ACTION = "commit";
		JFrame frame;
		JTextField mainTextField = new JTextField();
		
		
		

		// Without this, cursor always leaves text field
		mainTextField.setFocusTraversalKeysEnabled(false);
		ArrayList<String> keywords;
		// Our words to complete
		keywords = AllPoints.getInstance().getAllPoints();
		System.out.println(keywords);
		Autocomplete autoComplete = new Autocomplete(mainTextField, keywords);
		mainTextField.getDocument().addDocumentListener(autoComplete);

		// Maps the tab key to the commit action, which finishes the autocomplete
		// when given a suggestion
		mainTextField.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
		mainTextField.getActionMap().put(COMMIT_ACTION, autoComplete.new CommitAction());
		mainTextField.setBounds(0, 0, 200,20);
		
		frame = new JFrame();
        frame.setLayout(new GridLayout(1, 100, 10, 10));
		frame.setSize(200,20);
        frame.add(mainTextField);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(100, 100);
        frame.pack();
        frame.setVisible(true);
	}

}
