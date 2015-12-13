package WPI.CampusMap.Frontend.UI;

import java.awt.Choice;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComponent;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;

import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Frontend.Graphics.Dev.DevPointGraphicsObject;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JToggleButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AppDevModePointEditorControl extends JComponent {
	DevPointGraphicsObject point;

	public AppDevModePointEditorControl(DevPointGraphicsObject point) 
	{
		this.point = point;
		
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JLabel lblName = new JLabel("Name: ");
		springLayout.putConstraint(SpringLayout.WEST, lblName, 10, SpringLayout.WEST, this);
		add(lblName);

		nameField = new JTextField();
		nameField.setText(point.getPointId());
		nameField.setToolTipText("Enter a name for the selected node");
		PointNameChangedAction nameChangedEvent = new PointNameChangedAction();
		nameField.addFocusListener(nameChangedEvent);
		nameField.addActionListener(nameChangedEvent);
		springLayout.putConstraint(SpringLayout.WEST, nameField, 0, SpringLayout.EAST, lblName);
		springLayout.putConstraint(SpringLayout.NORTH, lblName, 0, SpringLayout.NORTH, nameField);
		springLayout.putConstraint(SpringLayout.SOUTH, lblName, 0, SpringLayout.SOUTH, nameField);
		springLayout.putConstraint(SpringLayout.EAST, nameField, -10, SpringLayout.EAST, this);
		add(nameField);
		nameField.setColumns(10);

		Choice typeSelector = new Choice();
		typeSelector.add(RealPoint.ELEVATOR);
		typeSelector.add(RealPoint.HALLWAY);
		typeSelector.add(RealPoint.STAIRS);
		typeSelector.addItemListener(new PointTypeChangedAction());
		typeSelector.select(point.getPointType());
		springLayout.putConstraint(SpringLayout.NORTH, typeSelector, 10, SpringLayout.SOUTH, nameField);
		springLayout.putConstraint(SpringLayout.WEST, typeSelector, 0, SpringLayout.WEST, nameField);
		springLayout.putConstraint(SpringLayout.EAST, typeSelector, 0, SpringLayout.EAST, nameField);
		add(typeSelector);

		JLabel lblType = new JLabel("Type: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblType, 0, SpringLayout.NORTH, typeSelector);
		springLayout.putConstraint(SpringLayout.SOUTH, lblType, 0, SpringLayout.SOUTH, typeSelector);
		springLayout.putConstraint(SpringLayout.EAST, lblType, 0, SpringLayout.EAST, lblName);
		add(lblType);

		JPanel connectionsList = new JPanel();
		springLayout.putConstraint(SpringLayout.WEST, connectionsList, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, connectionsList, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, connectionsList, -10, SpringLayout.EAST, this);
		add(connectionsList);

		Label connectionsLabel = new Label("Connections");
		springLayout.putConstraint(SpringLayout.SOUTH, connectionsLabel, 0, SpringLayout.NORTH, connectionsList);
		springLayout.putConstraint(SpringLayout.WEST, connectionsLabel, 0, SpringLayout.WEST, connectionsList);
		add(connectionsLabel);

		JSeparator separator = new JSeparator();
		springLayout.putConstraint(SpringLayout.NORTH, nameField, 10, SpringLayout.NORTH, separator);
		springLayout.putConstraint(SpringLayout.NORTH, separator, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, this, -10, SpringLayout.SOUTH, separator);
		springLayout.putConstraint(SpringLayout.WEST, separator, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, separator, 0, SpringLayout.EAST, this);
		add(separator);

		JSeparator separator_1 = new JSeparator();
		springLayout.putConstraint(SpringLayout.NORTH, connectionsList, 30, SpringLayout.SOUTH, separator_1);
		springLayout.putConstraint(SpringLayout.NORTH, separator_1, 200, SpringLayout.NORTH, separator);
		springLayout.putConstraint(SpringLayout.WEST, separator_1, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, separator_1, 0, SpringLayout.EAST, this);
		add(separator_1);
	}
	
	private class PointTypeChangedAction implements ItemListener
	{
		@Override
		public void itemStateChanged(ItemEvent e)
		{
			point.setType((String)e.getItem());
		}
	}
	
	private class PointNameChangedAction implements ActionListener, FocusListener
	{
		private String currentText;
		
		@Override
		public void focusGained(FocusEvent e) 
		{
			JTextField field = (JTextField)e.getComponent();
			currentText = field.getText();
		}

		@Override
		public void focusLost(FocusEvent e) 
		{
			JTextField field = (JTextField)e.getComponent();
			field.setText(currentText);
		}

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			JTextField field = (JTextField)e.getSource();
			if(!point.setId(field.getText()))
				field.setText(currentText);
			else
				currentText = field.getText();
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7831300945301622071L;
	private final NoneSelectedButtonGroup modeButtonGroup = new NoneSelectedButtonGroup();
	private JTextField nameField;
}
