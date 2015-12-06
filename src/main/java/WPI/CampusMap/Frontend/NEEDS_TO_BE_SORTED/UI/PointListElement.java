package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import javax.swing.JComponent;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.TextListener;
import java.awt.event.TextEvent;

public class PointListElement extends JComponent
{
	public PointListElement() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JButton removeButton = new JButton("");
		springLayout.putConstraint(SpringLayout.NORTH, removeButton, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, removeButton, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, removeButton, -10, SpringLayout.EAST, this);
		removeButton.setIcon(new ImageIcon(PointListElement.class.getResource("/javax/swing/plaf/metal/icons/ocean/paletteClose.gif")));
		removeButton.setMinimumSize(new Dimension(45, 25));
		add(removeButton);
		
		JButton goToButton = new JButton("");
		springLayout.putConstraint(SpringLayout.EAST, goToButton, -6, SpringLayout.WEST, removeButton);
		springLayout.putConstraint(SpringLayout.NORTH, goToButton, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, goToButton, -10, SpringLayout.SOUTH, this);
		goToButton.setIcon(new ImageIcon(PointListElement.class.getResource("/javax/swing/plaf/metal/icons/ocean/minimize.gif")));
		goToButton.setMinimumSize(new Dimension(45, 25));
		add(goToButton);
		
		TextField nodeName = new TextField();
		nodeName.addTextListener(new TextListener() {
			public void textValueChanged(TextEvent arg0) {
			}
		});
		springLayout.putConstraint(SpringLayout.WEST, removeButton, 55, SpringLayout.EAST, nodeName);
		springLayout.putConstraint(SpringLayout.WEST, goToButton, 6, SpringLayout.EAST, nodeName);
		springLayout.putConstraint(SpringLayout.NORTH, nodeName, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, nodeName, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, nodeName, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, nodeName, 213, SpringLayout.WEST, this);
		add(nodeName);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2960582722911027050L;
}
