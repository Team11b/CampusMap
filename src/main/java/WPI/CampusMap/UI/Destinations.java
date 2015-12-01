package WPI.CampusMap.UI;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Destinations {
	final int MAX = 5;
	ArrayList<JLabel> labels = new ArrayList<JLabel>();
	ArrayList<JTextField> textFields = new ArrayList<JTextField>();
	JPanel panel;
	
	final int startX = 0, startY = 110, labelWidth = 50, labelHight = 20, textWidth = 170, textHight = 20;
	
	public Destinations(JPanel panel){
		this.panel = panel;
		
		addDestination();
		addDestination();
	}
	
	public void addDestination(){
		int size = labels.size();
		String name = "Dest" + size;
		
		if(size == 0){
			name = "Start";
		}
		
		if(size >= MAX) return;
		
		JLabel tempLabel = new JLabel();
		tempLabel.setBounds(startX, startY + size*labelHight, labelWidth, labelHight);
		panel.add(tempLabel);
		tempLabel.setText(name);
		
		JTextField tempText = new JTextField();
		tempText.setBounds(startX + labelWidth, startY + size*labelHight, textWidth, textHight);
		panel.add(tempText);
		
		labels.add(tempLabel);
		textFields.add(tempText);
		
		toggleVisibility(true);
		
		size++;
		System.out.println(size);
	}
	
	public void removeDestination(){
		int size = labels.size()-1;
		if(size < 2) return;
		setVisibility(false);
		panel.remove(labels.get(size));
		panel.remove(textFields.get(size));
		panel.revalidate();

		setVisibility(true);
		labels.remove(size);
		textFields.remove(size);

		System.out.println(size);
	}
	
	public void toggleVisibility(boolean visibility){
		setVisibility(!visibility);
		setVisibility(visibility);
	}
	
	public void setVisibility(boolean visibility){
		for(int i = 0; i < labels.size(); i++){
			labels.get(i).setVisible(visibility);
			textFields.get(i).setVisible(visibility);
		}
	}
}
