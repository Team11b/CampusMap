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
import javax.swing.SpringLayout;

import WPI.CampusMap.Frontend.Graphics.Dev.DevPointGraphicsObject;

public class AppDevModeControl extends JComponent {
	private AppMainWindow window;

	public AppDevModeControl(AppMainWindow window) {
		this.window = window;

		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		Panel panel = new Panel();
		panel.setLayout(new BorderLayout());
		springLayout.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, panel, 0, SpringLayout.EAST, this);
		add(panel);
		
		JPanel buttonPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.SOUTH, buttonPanel, 100, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.SOUTH, buttonPanel);
		springLayout.putConstraint(SpringLayout.NORTH, buttonPanel, 10, SpringLayout.NORTH, this);
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
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -6, SpringLayout.NORTH, separator);
		springLayout.putConstraint(SpringLayout.WEST, separator, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, separator, -40, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, separator, 0, SpringLayout.EAST, this);
		add(separator);

		JButton btnSave = new JButton("Save");
		btnSave.setToolTipText("Save the current map");
		springLayout.putConstraint(SpringLayout.NORTH, btnSave, 6, SpringLayout.SOUTH, separator);
		springLayout.putConstraint(SpringLayout.WEST, btnSave, 80, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, btnSave, -80, SpringLayout.EAST, this);
		add(btnSave);
	}
	
	public void editPoint(DevPointGraphicsObject point)
	{
		
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

	/**
	 * 
	 */
	private static final long serialVersionUID = -7831300945301622071L;
	private final NoneSelectedButtonGroup modeButtonGroup = new NoneSelectedButtonGroup();
}
