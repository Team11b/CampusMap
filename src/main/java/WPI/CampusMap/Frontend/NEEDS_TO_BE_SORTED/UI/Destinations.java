package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.User.UserPointGraphicsObject;

public class Destinations {
	final int MAX = 5;	
	int lastPoint = -1;
	ArrayList<JLabel> labels = new ArrayList<JLabel>();
	ArrayList<JTextField> textFields = new ArrayList<JTextField>();
	JPanel panel;
	
	final int startX = 0, startY = 110, labelWidth = 50, labelHight = 20, textWidth = 170, textHight = 20;
	
	public Destinations( JPanel directionsPanel){
		this.panel = directionsPanel;
		
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
		String id = textFields.get(size).getText();
		
		panel.remove(labels.get(size));
		panel.remove(textFields.get(size));
		panel.revalidate();

		setVisibility(true);
		labels.remove(size);
		textFields.remove(size);

		System.out.println(size);
		
		//Unselect point on map
		if(id.isEmpty() == false){
			UserPointGraphicsObject.pullPointOnRoute(lastPoint);					
			lastPoint--;
		}				
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
	
	public void setDestination(String idOfDest){
		int free = lastPoint+1;
		System.out.println("Size is "+ textFields.size()+ " free is " + free);
		if(textFields.size() > free)
			textFields.get(free).setText(idOfDest);
		else
		{
			System.out.println("Making space!");
			addDestination();
			textFields.get(free).setText(idOfDest);
		}
		lastPoint++;		
	}
	
	public void resetLastPoint(){		
		lastPoint = -1;
		for(int i = 0; i < textFields.size(); i++){
			textFields.get(i).setText("");
		}
	}
	
}
