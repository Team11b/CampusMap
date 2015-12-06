package WPI.CampusMap.UI;

import java.awt.Choice;

import javax.swing.JComponent;
import javax.swing.SpringLayout;

@SuppressWarnings("serial")
public class AppBaseModeControl extends JComponent
{
	protected SpringLayout springLayout = new SpringLayout();
	protected Choice comboBox = new Choice();
	
	public AppBaseModeControl()
	{
		super();
		
		setLayout(springLayout);
		
		comboBox.add("AK");
		comboBox.add("FL");
		comboBox.add("Campus Map");
		springLayout.putConstraint(SpringLayout.NORTH, comboBox, 35, SpringLayout.NORTH, this);
		add(comboBox);
	}
}
