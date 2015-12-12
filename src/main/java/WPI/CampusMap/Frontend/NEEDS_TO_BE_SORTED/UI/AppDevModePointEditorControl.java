package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import java.awt.Choice;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JToggleButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AppDevModePointEditorControl extends JComponent {
	private AppMainWindow window;

	public AppDevModePointEditorControl(AppMainWindow window) {
		this.window = window;

		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, this);
		add(panel);
		panel.setLayout(new GridLayout(0, 2, 10, 10));

		JToggleButton rdbtnCreate = new JToggleButton("Create");
		rdbtnCreate.setToolTipText("Create a new node on the map");
		rdbtnCreate.addActionListener(new PointEditorActionListener());
		modeButtonGroup.add(rdbtnCreate);
		panel.add(rdbtnCreate);

		JToggleButton rdbtnDelete = new JToggleButton("Delete");
		rdbtnDelete.setToolTipText("Delete a node on the map");
		rdbtnDelete.addActionListener(new DeleteEditorActionListener());
		modeButtonGroup.add(rdbtnDelete);
		panel.add(rdbtnDelete);

		JToggleButton rdbtnEdge = new JToggleButton("Edge");
		rdbtnEdge.setToolTipText("Create a new edge between two nodes on the map");
		rdbtnEdge.addActionListener(new EdgeEditorActionListener());
		modeButtonGroup.add(rdbtnEdge);
		panel.add(rdbtnEdge);

		JToggleButton rdbtnDeleteEdge = new JToggleButton("Delete Edge");
		rdbtnDeleteEdge.setToolTipText("Delete an existing edge on the map");
		rdbtnDeleteEdge.addActionListener(new DeleteEdgeEditorActionListener());
		modeButtonGroup.add(rdbtnDeleteEdge);
		panel.add(rdbtnDeleteEdge);

		JLabel lblName = new JLabel("Name: ");
		springLayout.putConstraint(SpringLayout.WEST, lblName, 10, SpringLayout.WEST, this);
		add(lblName);

		nameField = new JTextField();
		nameField.setToolTipText("Enter a name for the selected node");
		springLayout.putConstraint(SpringLayout.WEST, nameField, 0, SpringLayout.EAST, lblName);
		springLayout.putConstraint(SpringLayout.NORTH, lblName, 0, SpringLayout.NORTH, nameField);
		springLayout.putConstraint(SpringLayout.SOUTH, lblName, 0, SpringLayout.SOUTH, nameField);
		springLayout.putConstraint(SpringLayout.EAST, nameField, -10, SpringLayout.EAST, this);
		add(nameField);
		nameField.setColumns(10);

		Choice typeSelector = new Choice();
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
		springLayout.putConstraint(SpringLayout.NORTH, nameField, 10, SpringLayout.SOUTH, separator);
		springLayout.putConstraint(SpringLayout.NORTH, separator, 120, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -10, SpringLayout.SOUTH, separator);
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
	private JTextField nameField;
}
