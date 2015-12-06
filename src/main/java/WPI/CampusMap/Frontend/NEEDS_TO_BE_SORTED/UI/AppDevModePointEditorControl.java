package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import java.awt.Choice;
import java.awt.Component;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.SpringLayout;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JRadioButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JToggleButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Box;

public class AppDevModePointEditorControl extends JComponent
{
	public AppDevModePointEditorControl()
	{
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, this);
		add(panel);
		panel.setLayout(new GridLayout(0, 2, 10, 10));
		
		JToggleButton rdbtnCreate = new JToggleButton("Create");
		modeButtonGroup.add(rdbtnCreate);
		panel.add(rdbtnCreate);
		
		JToggleButton rdbtnDelete = new JToggleButton("Delete");
		modeButtonGroup.add(rdbtnDelete);
		panel.add(rdbtnDelete);
		
		JToggleButton rdbtnEdge = new JToggleButton("Edge");
		modeButtonGroup.add(rdbtnEdge);
		panel.add(rdbtnEdge);
		
		JToggleButton rdbtnDeleteEdge = new JToggleButton("Delete Edge");
		modeButtonGroup.add(rdbtnDeleteEdge);
		panel.add(rdbtnDeleteEdge);
		
		JLabel lblName = new JLabel("Name: ");
		springLayout.putConstraint(SpringLayout.WEST, lblName, 10, SpringLayout.WEST, this);
		add(lblName);
		
		nameField = new JTextField();
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
	
		//button handling 	
		ActionListener aL = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				switch (e.getActionCommand()) {
				case "Create":
					DevMode.getInstance().setPlace();
						break;
				case "Delete":
					DevMode.getInstance().setRemove();
						break;
				case "Edge":
					DevMode.getInstance().setEdge();
					break;
				case "Delete Edge":
					DevMode.getInstance().setRemoveEdge();
					break;			
				
				}
				
			}
		};
		
		rdbtnCreate.addActionListener(aL);
		rdbtnDelete.addActionListener(aL);
		rdbtnEdge.addActionListener(aL);
		rdbtnDeleteEdge.addActionListener(aL);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7831300945301622071L;
	private final NoneSelectedButtonGroup modeButtonGroup = new NoneSelectedButtonGroup();
	private JTextField nameField;
}
