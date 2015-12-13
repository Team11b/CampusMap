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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Frontend.Graphics.Dev.DevPointGraphicsObject;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSpinner;

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
		
		JSpinner xCoordField = new JSpinner();
		xCoordField.setModel(new SpinnerNumberModel(point.getWorldPosition().getX(), Double.MIN_VALUE, Double.MAX_VALUE, 0.1));
		xCoordField.addChangeListener(new XCoordChangedAction());
		springLayout.putConstraint(SpringLayout.NORTH, xCoordField, 40, SpringLayout.SOUTH, typeSelector);
		springLayout.putConstraint(SpringLayout.WEST, xCoordField, 0, SpringLayout.WEST, typeSelector);
		springLayout.putConstraint(SpringLayout.EAST, xCoordField, -10, SpringLayout.EAST, this);
		add(xCoordField);
		
		JLabel lblXlabel = new JLabel("x: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblXlabel, 0, SpringLayout.NORTH, xCoordField);
		springLayout.putConstraint(SpringLayout.SOUTH, lblXlabel, 0, SpringLayout.SOUTH, xCoordField);
		springLayout.putConstraint(SpringLayout.EAST, lblXlabel, 0, SpringLayout.WEST, xCoordField);
		add(lblXlabel);
		
		JSpinner yCoordField = new JSpinner();
		yCoordField.setModel(new SpinnerNumberModel(point.getWorldPosition().getY(), Double.MIN_VALUE, Double.MAX_VALUE, 0.1));
		yCoordField.addChangeListener(new YCoordChangedAction());
		springLayout.putConstraint(SpringLayout.NORTH, yCoordField, 10, SpringLayout.SOUTH, xCoordField);
		springLayout.putConstraint(SpringLayout.WEST, yCoordField, 0, SpringLayout.WEST, xCoordField);
		springLayout.putConstraint(SpringLayout.EAST, yCoordField, 0, SpringLayout.EAST, xCoordField);
		add(yCoordField);
		
		JLabel lblYlabel = new JLabel("y: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblYlabel, 0, SpringLayout.NORTH, yCoordField);
		springLayout.putConstraint(SpringLayout.SOUTH, lblYlabel, 0, SpringLayout.SOUTH, yCoordField);
		springLayout.putConstraint(SpringLayout.EAST, lblYlabel, 0, SpringLayout.WEST, yCoordField);
		add(lblYlabel);
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
	
	private class XCoordChangedAction implements ChangeListener
	{
		@Override
		public void stateChanged(ChangeEvent e)
		{
			JSpinner spinner = (JSpinner)e.getSource();
			Double x = (Double)spinner.getValue();
			point.getRepresentedObject().setCoord(new Coord(x.floatValue(), point.getWorldPosition().getY()));
		}
	}
	
	private class YCoordChangedAction implements ChangeListener
	{
		@Override
		public void stateChanged(ChangeEvent e)
		{
			JSpinner spinner = (JSpinner)e.getSource();
			Double y = (Double)spinner.getValue();
			point.getRepresentedObject().setCoord(new Coord(point.getWorldPosition().getX(), y.floatValue()));
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7831300945301622071L;
	private final NoneSelectedButtonGroup modeButtonGroup = new NoneSelectedButtonGroup();
	private JTextField nameField;
}
