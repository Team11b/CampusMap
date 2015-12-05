package WPI.CampusMap.UI;

import java.awt.Panel;
import javax.swing.SpringLayout;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import java.awt.Component;
import javax.swing.Box;
import java.awt.ScrollPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextPane;
import javax.swing.BoxLayout;
import javax.swing.JTree;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Label;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSlider;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class AppUserModeControl extends Panel
{
	public AppUserModeControl() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JComboBox comboBox = new JComboBox();
		springLayout.putConstraint(SpringLayout.NORTH, comboBox, 35, SpringLayout.NORTH, this);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"AK", "Fuller", "Campus Map"}));
		add(comboBox);
		
		DestinationsList scrollPane = new DestinationsList();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 80, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, comboBox, 0, SpringLayout.WEST, scrollPane);
		springLayout.putConstraint(SpringLayout.EAST, comboBox, 0, SpringLayout.EAST, scrollPane);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, this);
		add(scrollPane);
		
		JButton routeButton = new JButton("Route Me!");
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -20, SpringLayout.NORTH, routeButton);
		springLayout.putConstraint(SpringLayout.NORTH, routeButton, 373, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, routeButton, 54, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, routeButton, -61, SpringLayout.EAST, this);
		routeButton.setIcon(new ImageIcon(AppUserModeControl.class.getResource("/javax/swing/plaf/metal/icons/ocean/expanded.gif")));
		routeButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		add(routeButton);
		
		Label mapLabel = new Label("Map");
		springLayout.putConstraint(SpringLayout.WEST, mapLabel, 0, SpringLayout.WEST, comboBox);
		springLayout.putConstraint(SpringLayout.SOUTH, mapLabel, 0, SpringLayout.NORTH, comboBox);
		add(mapLabel);
		
		Label label = new Label("Destinations");
		springLayout.putConstraint(SpringLayout.WEST, label, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, label, 0, SpringLayout.NORTH, scrollPane);
		add(label);
		
		JSeparator separator = new JSeparator();
		springLayout.putConstraint(SpringLayout.NORTH, separator, 19, SpringLayout.SOUTH, routeButton);
		springLayout.putConstraint(SpringLayout.WEST, separator, 0, SpringLayout.WEST, comboBox);
		springLayout.putConstraint(SpringLayout.SOUTH, separator, 27, SpringLayout.SOUTH, routeButton);
		springLayout.putConstraint(SpringLayout.EAST, separator, 0, SpringLayout.EAST, comboBox);
		add(separator);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_1, 25, SpringLayout.SOUTH, separator);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_1, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_1, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_1, 0, SpringLayout.EAST, comboBox);
		add(scrollPane_1);
		
		Label label_1 = new Label("Directions");
		springLayout.putConstraint(SpringLayout.SOUTH, label_1, 0, SpringLayout.NORTH, scrollPane_1);
		springLayout.putConstraint(SpringLayout.WEST, label_1, 10, SpringLayout.WEST, this);
		
		JTree tree = new JTree();
		tree.setRootVisible(false);
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Directions") {
				{
					DefaultMutableTreeNode node_1;
					node_1 = new DefaultMutableTreeNode("AK");
						node_1.add(new DefaultMutableTreeNode("Turn left"));
						node_1.add(new DefaultMutableTreeNode("Go straight 20 feet"));
					add(node_1);
				}
			}
		));
		scrollPane_1.setViewportView(tree);
		add(label_1);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 768017196182798774L;
}
