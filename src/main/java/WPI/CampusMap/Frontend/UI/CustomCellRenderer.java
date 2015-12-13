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

class CustomCellRenderer implements TreeCellRenderer{
	private JLabel icon;
	
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		Object o = ((DefaultMutableTreeNode) value).getUserObject();
		URL imageUrl = null;
		if (o instanceof PathSectionTreeNode) {
			PathSectionTreeNode node = (PathSectionTreeNode) o;
			try {
				System.out.println("About to find image");
				imageUrl = new URL("left-turn.png");
				System.out.println("Found image");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				System.out.println("Can't find image");
				e.printStackTrace();
			}
			if (imageUrl != null) {
				icon.setIcon(new ImageIcon(imageUrl));
			}
		} else {
			System.out.println("Found image1");
			icon.setIcon(new ImageIcon("left-turn.png"));
			icon.setText("" + value);
		}
		return icon;
	}


	CustomCellRenderer() {
		icon = new JLabel();
	}


}
