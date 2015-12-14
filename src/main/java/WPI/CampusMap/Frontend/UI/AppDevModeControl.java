package WPI.CampusMap.Frontend.UI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import WPI.CampusMap.Backend.Core.Map.IMap;
import WPI.CampusMap.Frontend.Graphics.Dev.DevPointGraphicsObject;
import javax.swing.JSpinner;
import javax.swing.JLabel;

public class AppDevModeControl extends JComponent
{
	private AppMainWindow window;
	private Panel editorPanel = new Panel();

	/** Creates a new AppDevModeControl with the given window.
	 * @param window The window to create the control in.
	 */
	public AppDevModeControl(AppMainWindow window) 
	{
		this.window = window;

		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		
		editorPanel.setLayout(new BorderLayout());
		springLayout.putConstraint(SpringLayout.WEST, editorPanel, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, editorPanel, 0, SpringLayout.EAST, this);
		add(editorPanel);
		
		JPanel buttonPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, editorPanel, 10, SpringLayout.SOUTH, buttonPanel);
		springLayout.putConstraint(SpringLayout.WEST, buttonPanel, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, buttonPanel, -10, SpringLayout.EAST, this);
		add(buttonPanel);
		buttonPanel.setLayout(new GridLayout(0, 2, 10, 10));

		JToggleButton rdbtnCreate = new JToggleButton("Create");
		rdbtnCreate.setToolTipText("Create a new node on the map");
		rdbtnCreate.addActionListener(new PointEditorActionListener());
		modeButtonGroup.add(rdbtnCreate);
		buttonPanel.add(rdbtnCreate);

		JToggleButton rdbtnDelete = new JToggleButton("Delete");
		rdbtnDelete.setToolTipText("Delete a node on the map");
		rdbtnDelete.addActionListener(new DeleteEditorActionListener());
		modeButtonGroup.add(rdbtnDelete);
		buttonPanel.add(rdbtnDelete);

		JToggleButton rdbtnEdge = new JToggleButton("Edge");
		rdbtnEdge.setToolTipText("Create a new edge between two nodes on the map");
		rdbtnEdge.addActionListener(new EdgeEditorActionListener());
		modeButtonGroup.add(rdbtnEdge);
		buttonPanel.add(rdbtnEdge);

		JToggleButton rdbtnDeleteEdge = new JToggleButton("Delete Edge");
		rdbtnDeleteEdge.setToolTipText("Delete an existing edge on the map");
		rdbtnDeleteEdge.addActionListener(new DeleteEdgeEditorActionListener());
		modeButtonGroup.add(rdbtnDeleteEdge);
		buttonPanel.add(rdbtnDeleteEdge);

		JSeparator separator = new JSeparator();
		springLayout.putConstraint(SpringLayout.SOUTH, editorPanel, -6, SpringLayout.NORTH, separator);
		springLayout.putConstraint(SpringLayout.WEST, separator, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, separator, -40, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, separator, 0, SpringLayout.EAST, this);
		add(separator);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new SaveActionListener());
		btnSave.setToolTipText("Save the current map");
		springLayout.putConstraint(SpringLayout.NORTH, btnSave, 6, SpringLayout.SOUTH, separator);
		springLayout.putConstraint(SpringLayout.WEST, btnSave, 80, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, btnSave, -80, SpringLayout.EAST, this);
		add(btnSave);
		
		scaleSpinner = new JSpinner();
		scaleSpinner.addChangeListener(new ScaleChangedListener());
		springLayout.putConstraint(SpringLayout.WEST, scaleSpinner, -130, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, buttonPanel, 10, SpringLayout.SOUTH, scaleSpinner);
		springLayout.putConstraint(SpringLayout.SOUTH, buttonPanel, 100, SpringLayout.NORTH, scaleSpinner);
		springLayout.putConstraint(SpringLayout.NORTH, scaleSpinner, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, scaleSpinner, -10, SpringLayout.EAST, this);
		add(scaleSpinner);
		
		JLabel lblNewLabel = new JLabel("Scale (Inches/Feet): ");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 0, SpringLayout.NORTH, scaleSpinner);
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel, 0, SpringLayout.SOUTH, scaleSpinner);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel, 0, SpringLayout.WEST, scaleSpinner);
		add(lblNewLabel);
	}
	
	/**
	 * Changes the dev mode content to be editing a point.
	 * @param point The point to edit.
	 */
	public void editPoint(DevPointGraphicsObject point)
	{
		editorPanel.removeAll();
		
		AppDevModePointEditorControl pointEditor = new AppDevModePointEditorControl(point, window);
		editorPanel.add(pointEditor, BorderLayout.CENTER);
		
		revalidate();
	}
	
	public void changeMapInfo(IMap newMap)
	{
		scaleSpinner.setModel(new SpinnerNumberModel(newMap.getScale(), 0, Double.MAX_VALUE, 0.1));
	}
	
	public void clearSelection()
	{
		editorPanel.removeAll();
		
		revalidate();
	}
	
	private abstract class EditorModeActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JToggleButton toggleButton = (JToggleButton) e.getSource();
			if (toggleButton.isSelected())
				onActionPerformed(e);
			else
				window.getDevMode().setSelect();
		}

		protected abstract void onActionPerformed(ActionEvent e);

	}

	private class PointEditorActionListener extends EditorModeActionListener {
		@Override
		protected void onActionPerformed(ActionEvent e) {
			window.getDevMode().setPlace();
		}
	}

	private class EdgeEditorActionListener extends EditorModeActionListener {
		@Override
		protected void onActionPerformed(ActionEvent e) {
			window.getDevMode().setEdge();
		}
	}

	private class DeleteEditorActionListener extends EditorModeActionListener {
		@Override
		protected void onActionPerformed(ActionEvent e) {
			window.getDevMode().setRemove();
		}
	}

	private class DeleteEdgeEditorActionListener extends EditorModeActionListener {
		@Override
		protected void onActionPerformed(ActionEvent e) {
			window.getDevMode().setRemoveEdge();
		}
	}
	
	private class SaveActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			window.getDevMode().save();
		}
	}
	
	private class ScaleChangedListener implements ChangeListener
	{

		@Override
		public void stateChanged(ChangeEvent e)
		{
			JSpinner spinner = (JSpinner) e.getSource();
			Double value = (Double) spinner.getValue();
			
			window.getDevMode().onMapScaleChanged(value.floatValue());
		}
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7831300945301622071L;
	private final NoneSelectedButtonGroup modeButtonGroup = new NoneSelectedButtonGroup();
	private JSpinner scaleSpinner;
}
