package WPI.CampusMap.UI;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import WPI.CampusMap.Graphics.User.UserPointGraphicsObject;

public class Destinations {
	final int MAX = 5;
	int indexFreeTextBox = 0;
	int tempIndex;
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
		
		//Update where to setDest to if neccessary
		if(indexFreeTextBox == -1)
			indexFreeTextBox = tempIndex;
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
			UserPointGraphicsObject.pullPointOnRoute(tempIndex-1);
			System.out.println("ID is"+ id);
			indexFreeTextBox = -1;
		}		
		
		//Keep track if there is a free textbox
		if(indexFreeTextBox == -1){
			tempIndex--;			
		}
		else{			
			indexFreeTextBox--;
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
		//add a textbox if neccessary
		if(indexFreeTextBox == -1){
			addDestination();			
		}else{
		
		//set in destination textbox
		textFields.get(indexFreeTextBox).setText(idOfDest);
		
		//keep track if theres a free textbox
		indexFreeTextBox++;
		if(indexFreeTextBox == textFields.size()){
			tempIndex = indexFreeTextBox;
			indexFreeTextBox = -1;
		}
		}
	}
	
	public int getIndexFreeTextBox(){
		return indexFreeTextBox;
	}
}
