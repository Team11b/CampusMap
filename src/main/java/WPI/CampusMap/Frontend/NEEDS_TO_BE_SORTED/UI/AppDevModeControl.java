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

public class AppDevModeControl extends JComponent
{
	public AppDevModeControl()
	{
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		Choice comboBox = new Choice();
		springLayout.putConstraint(SpringLayout.NORTH, comboBox, 35, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, comboBox, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, comboBox, -10, SpringLayout.EAST, this);
		comboBox.add("AK");
		comboBox.add("FL");
		comboBox.add("Campus Map");
		add(comboBox);
		
		Label mapLabel = new Label("Map");
		springLayout.putConstraint(SpringLayout.WEST, mapLabel, 0, SpringLayout.WEST, comboBox);
		springLayout.putConstraint(SpringLayout.SOUTH, mapLabel, 0, SpringLayout.NORTH, comboBox);
		add(mapLabel);
		
		AppDevModePointEditorControl panel = new AppDevModePointEditorControl();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 16, SpringLayout.SOUTH, comboBox);
		springLayout.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, panel, 0, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, panel, 0, SpringLayout.EAST, this);
		add(panel);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7831300945301622071L;
}
