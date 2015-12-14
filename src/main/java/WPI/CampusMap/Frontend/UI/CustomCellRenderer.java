package WPI.CampusMap.Frontend.UI;

import java.awt.Component;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import org.hamcrest.core.IsInstanceOf;

import WPI.CampusMap.Frontend.UI.AppUserModeControl.PathSectionTreeNode;

/**
 * This is a custom JTree Cell renderer. Each cell gets a custom renderer based on what type of "Direction" it represents.
 * @author Will
 *
 */
class CustomCellRenderer implements TreeCellRenderer{
	private JLabel icon;
	
	/**
	 * Returns the correct type of tree cell based on the contents of the direction string.
	 * Different types of instructions are given different icons.
	 */
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		Object o = ((DefaultMutableTreeNode) value).getUserObject();
		ImageIcon icon = null;
		JLabel label = new JLabel();
		if (o instanceof String) {
			String text = (String) o;

			if(text.contains("left")){
				icon = new ImageIcon("left-turn.png");
			}else if(text.contains("right")){
				icon = new ImageIcon("right-turn.png");
			}else if(text.contains("Walk")){
				icon = new ImageIcon("straight.png");
			}else if(text.contains("elevator")){
				icon = new ImageIcon("elevator.png");
			}else if(text.contains("stair")){
				icon = new ImageIcon("stairs.png");
			}else{
				icon = new ImageIcon("icon.png");
			}
			label.setIcon(icon);
			label.setText(text);
		}
		return label;
	}

	/**
	 * Basic class constructor. Initializes the icon JLabel.
	 */
	CustomCellRenderer() {
		icon = new JLabel();
	}


}
