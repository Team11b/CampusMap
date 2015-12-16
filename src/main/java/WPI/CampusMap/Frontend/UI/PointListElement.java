package WPI.CampusMap.Frontend.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.apache.commons.lang3.text.WordUtils;

import WPI.CampusMap.Backend.Core.Map.AllMaps;

public class PointListElement extends JPanel
{
	private String currentName;
	private PointList list;
	private int index;
	private boolean valid;
	
	public PointListElement(String name, int index, PointList list)
	{
		this.list = list;
		this.index = index;
		this.currentName = name;
		
		String floorName = currentName.split("/")[0].replace("_", " ");
		if(!currentName.equals(AllMaps.getInstance().CampusMap)){
			floorName = floorName.split("-")[0].trim().replace("_", "-");
		}
		
		try{
			floorName = "Floor " + Integer.parseInt(floorName);
		}catch(NumberFormatException e){
			floorName = WordUtils.capitalizeFully(floorName);
		}
		
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JButton removeButton = new JButton("");
		removeButton.setToolTipText("Remove the destination from your itinerary");
		removeButton.addActionListener(new RemoveButtonActionListener(this));
		springLayout.putConstraint(SpringLayout.NORTH, removeButton, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, removeButton, -40, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, removeButton, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, removeButton, -10, SpringLayout.EAST, this);
		removeButton.setIcon(new ImageIcon(	PointListElement.class.getResource("/javax/swing/plaf/metal/icons/ocean/paletteClose.gif")));
		removeButton.setMinimumSize(new Dimension(45, 25));
		add(removeButton);

		JButton goToButton = new JButton("");
		goToButton.setToolTipText("Get turn-by-turn directions to this destination");
		goToButton.addActionListener(new GotoButtonActionListener(this));
		springLayout.putConstraint(SpringLayout.WEST, goToButton, -40, SpringLayout.WEST, removeButton);
		springLayout.putConstraint(SpringLayout.EAST, goToButton, -6, SpringLayout.WEST, removeButton);
		springLayout.putConstraint(SpringLayout.NORTH, goToButton, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, goToButton, -10, SpringLayout.SOUTH, this);
		goToButton.setIcon(new ImageIcon(PointListElement.class.getResource("/javax/swing/plaf/metal/icons/ocean/minimize.gif")));
		goToButton.setMinimumSize(new Dimension(45, 25));
		add(goToButton);

		pointNameField = new JTextField();
		PointNameChanged nameChangeEvent = new PointNameChanged(this);
		pointNameField.addActionListener(nameChangeEvent);
		pointNameField.addFocusListener(nameChangeEvent);
		pointNameField.setText(currentName);
		springLayout.putConstraint(SpringLayout.NORTH, pointNameField, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, pointNameField, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, pointNameField, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, pointNameField, -10, SpringLayout.WEST, goToButton);
		add(pointNameField);
		
		validateName();
	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(200, 45);
	}

	@Override
	public Dimension getMaximumSize()
	{
		return new Dimension(Integer.MAX_VALUE, 45);
	}

	@Override
	public Dimension getMinimumSize()
	{
		return new Dimension(100, 45);
	}
	
	/**
	 * Gets the current name used to represent this element. This is not necessarily the name that is displayed.
	 * @return The name that is currently use to represent this element.
	 */
	public String getCurrentName()
	{
		return currentName;
	}
	
	public int getIndex()
	{
		return index;
	}
	
	protected void setIndex(int newIndex)
	{
		index = newIndex;
	}
	
	public boolean isNameValid()
	{
		return valid;
	}
	
	/**
	 * Validates the current text in the name field.
	 * @return true if valid
	 */
	protected boolean validateName()
	{
		String newName = pointNameField.getText();
		
		if(!newName.contains("/"))
		{
			valid = false;
			setBackground(new Color(255, 201, 201));
			return false;
		}
		
		valid = list.onElementCheckName(this, newName);
		
		if(valid)
		{
			setBackground(new Color(240, 240, 240));
			currentName = newName;
		}
		else
		{
			setBackground(new Color(255, 201, 201));
		}
		
		return valid;
	}
	
	/**
	 * Triggered when the text in the name field is changed.
	 */
	protected void onNameChanged()
	{
		String oldName = currentName;
		if(validateName())
		{
			list.onElementRenamed(this, oldName);
		}
		else
		{
			list.onElementNameCheckFailed(this, pointNameField.getText());
		}
	}

	private class RemoveButtonActionListener implements ActionListener {

		private PointListElement element;

		public RemoveButtonActionListener(PointListElement element)
		{
			this.element = element;
		}

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			list.onElementRemoved(element);
		}
	}

	private class GotoButtonActionListener implements ActionListener {
		private PointListElement element;

		public GotoButtonActionListener(PointListElement element) 
		{
			this.element = element;
		}

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			list.onElementShown(element);
		}
	}

	private class PointNameChanged implements FocusListener, ActionListener 
	{
		@SuppressWarnings("unused")
		private PointListElement element;

		public PointNameChanged(PointListElement element)
		{
		}

		@Override
		public void focusLost(FocusEvent e) 
		{
			//pointNameField.setText(getCurrentName());
		}

		@Override
		public void focusGained(FocusEvent e)
		{
			currentName = pointNameField.getText();
		}

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			onNameChanged();
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2960582722911027050L;
	private JTextField pointNameField;
}
