package WPI.CampusMap.Frontend.UI;

import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

/**
 * This is a custom JTree Cell renderer. Each cell gets a custom renderer based on what type of "Direction" it represents.
 * @author Will Spurgeon
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
		Image img = null;
		JLabel label = new JLabel();
		
		if (o instanceof String) {
			String text = (String) o;
			String file = "";

			if(text.contains("left")){
				file = "left-turn.png";
			}else if(text.contains("right")){
				file = "right-turn.png";
			}else if(text.contains("Walk")){
				file = "straight.png";
			}else if(text.contains("elevator")){
				file = "elevator.png";
			}else if(text.contains("stair")){
				file = "stairs.png";
			}else{
				file = "icon.png";
			}
			
			try {
				img = ImageIO.read(new File(file));
			} catch (IOException e) {
			}
			
			img = img.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
			icon = new ImageIcon(img);
			
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
