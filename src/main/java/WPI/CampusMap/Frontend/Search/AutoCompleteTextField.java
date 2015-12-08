package WPI.CampusMap.Frontend.Search;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class AutoCompleteTextField {

    private JFrame frame;
    private ArrayList<String> listSomeString = new ArrayList<String>();
    private Java2sAutoTextField someTextField = new Java2sAutoTextField(listSomeString);
    private ArrayList<String> listSomeAnotherString = new ArrayList<String>();
//    private Java2sAutoComboBox someComboBox = new Java2sAutoComboBox(listSomeAnotherString);

    public AutoCompleteTextField() {
        listSomeString.add("");
        listSomeString.add("Snowboarding");
        listSomeString.add("Rowing");
        listSomeString.add("Knitting");
        listSomeString.add("Speed reading");
        listSomeString.add("Pool");
        listSomeString.add("None of the above");
//
        listSomeAnotherString.add("-");
        listSomeAnotherString.add("XxxZxx Snowboarding");
        listSomeAnotherString.add("AaaBbb Rowing");
        listSomeAnotherString.add("CccDdd Knitting");
        listSomeAnotherString.add("Eee Fff Speed reading");
        listSomeAnotherString.add("Eee Fff Pool");
        listSomeAnotherString.add("Eee Fff None of the above");
//
        someTextField.setFont(new Font("Serif", Font.BOLD, 16));
        someTextField.setForeground(Color.black);
        someTextField.setBackground(Color.orange);
        someTextField.setName("someTextField");
        someTextField.setDataList(listSomeString);
//
        
//
        frame = new JFrame();
        frame.setLayout(new GridLayout(0, 1, 10, 10));
        frame.add(someTextField);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(100, 100);
        frame.pack();
        frame.setVisible(true);
//
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                someTextField.setText("");
                someTextField.grabFocus();
                someTextField.requestFocus();
                someTextField.setText(someTextField.getText());
                someTextField.selectAll();
            }
        });

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @SuppressWarnings("unused")
			@Override
            public void run() {
                AutoCompleteTextField aCTF = new AutoCompleteTextField();
            }
        });
    }
}