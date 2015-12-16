package WPI.CampusMap.Frontend.UI;

import java.awt.Choice;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Frontend.Graphics.Dev.DevPointGraphicsObject;

public class AppDevModePointEditorControl extends JComponent {
	private DevPointGraphicsObject point;
	private AppMainWindow window;

	public AppDevModePointEditorControl(DevPointGraphicsObject point, AppMainWindow window) 
	{
		this.point = point;
		this.window = window;
		
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
		typeSelector.add(RealPoint.STAIRS);
		typeSelector.add(RealPoint.HALLWAY);
		typeSelector.add(RealPoint.ROOM);
		typeSelector.add(RealPoint.OUT_DOOR);
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

		PointList connectionsList = new PointList();
		springLayout.putConstraint(SpringLayout.WEST, connectionsList, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, connectionsList, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, connectionsList, -10, SpringLayout.EAST, this);
		add(connectionsList);
		
		HashMap<String, ArrayList<String>> pointsMap = point.getRepresentedObject().getNeighborPointsOnOtherMaps();
		for(String mapName : pointsMap.keySet())
		{
			ArrayList<String> pointsOnMap = pointsMap.get(mapName);
			
			for(String pointName : pointsOnMap)
			{
				connectionsList.addPointDescriptor(pointName);
			}
		}
		
		connectionsList.addListener(new ConnectionListEventHandler());

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
	
	private class ConnectionListEventHandler implements PointListEventHandler
	{

		@Override
		public void pointDescriptorAdded(PointListElement element) 
		{
			window.getDevMode().pointDescriptorAdded(element);
		}

		@Override
		public void pointDescriptorRemoved(PointListElement element)
		{
			window.getDevMode().pointDescriptorRemoved(element);
		}

		@Override
		public boolean pointDescriptorNameCheck(PointListElement element, String newName)
		{
			return window.getDevMode().pointDescriptorNameCheck(element, newName);
		}

		@Override
		public void pointDescriptorRenamed(PointListElement element, String oldName) 
		{
			window.getDevMode().pointDescriptorRenamed(element, oldName);			
		}

		@Override
		public void pointDescriptorNameCheckFailed(PointListElement element, String failedName)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void pointDescriptorShow(PointListElement element) 
		{
			window.getDevMode().pointDescriptorShown(element);
		}

		@Override
		public void pointDescriptorMoved(PointListElement element)
		{
			
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7831300945301622071L;
	@SuppressWarnings("unused")
	private final NoneSelectedButtonGroup modeButtonGroup = new NoneSelectedButtonGroup();
	private JTextField nameField;
}
