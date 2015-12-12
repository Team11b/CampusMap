package WPI.CampusMap.Frontend.UI;

import javax.swing.JComponent;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

public class PointListElement extends JComponent {
	private PointList list;
	private String currentName;

	public PointListElement(PointList list, String name) {
		this.list = list;
		this.currentName = name;

		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JButton removeButton = new JButton("");
		removeButton.setToolTipText("Remove the destination from your itinerary");
		removeButton.addActionListener(new RemoveButtonActionListener(this));
		springLayout.putConstraint(SpringLayout.NORTH, removeButton, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, removeButton, -40, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, removeButton, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, removeButton, -10, SpringLayout.EAST, this);
		removeButton.setIcon(new ImageIcon(
				PointListElement.class.getResource("/javax/swing/plaf/metal/icons/ocean/paletteClose.gif")));
		removeButton.setMinimumSize(new Dimension(45, 25));
		add(removeButton);

		JButton goToButton = new JButton("");
		goToButton.setToolTipText("Get turn-by-turn directions to this destination");
		goToButton.addActionListener(new GotoButtonActionListener(this));
		springLayout.putConstraint(SpringLayout.WEST, goToButton, -40, SpringLayout.WEST, removeButton);
		springLayout.putConstraint(SpringLayout.EAST, goToButton, -6, SpringLayout.WEST, removeButton);
		springLayout.putConstraint(SpringLayout.NORTH, goToButton, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, goToButton, -10, SpringLayout.SOUTH, this);
		goToButton.setIcon(
				new ImageIcon(PointListElement.class.getResource("/javax/swing/plaf/metal/icons/ocean/minimize.gif")));
		goToButton.setMinimumSize(new Dimension(45, 25));
		add(goToButton);

		nodeName = new JTextField();
		PointNameChanged nameChangeEvent = new PointNameChanged(this);
		nodeName.addActionListener(nameChangeEvent);
		nodeName.addFocusListener(nameChangeEvent);
		nodeName.setText(name);
		springLayout.putConstraint(SpringLayout.NORTH, nodeName, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, nodeName, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, nodeName, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, nodeName, -10, SpringLayout.WEST, goToButton);
		add(nodeName);
	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(200, 45);
	}

	@Override
	public Dimension getMaximumSize()
	{
		return new Dimension(Integer.MAX_VALUE, 45);
	}

	@Override
	public Dimension getMinimumSize()
	{
		return new Dimension(100, 45);
	}

	public String getName()
	{
		return nodeName.getText();
	}

	protected void setPointName(String name)
	{
		nodeName.setText(name);
		currentName = name;
	}

	private class RemoveButtonActionListener implements ActionListener {

		private PointListElement element;

		public RemoveButtonActionListener(PointListElement element) {
			this.element = element;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			list.removePointElement(element.getName());
		}

	}

	private class GotoButtonActionListener implements ActionListener {
		private PointListElement element;

		public GotoButtonActionListener(PointListElement element) {
			this.element = element;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			list.gotoPointElement(element.getName());
		}

	}

	private class PointNameChanged implements FocusListener, ActionListener
	{
		private PointListElement element;

		public PointNameChanged(PointListElement element) {
			this.element = element;
		}

		@Override
		public void focusLost(FocusEvent arg0)
		{
			
		}

		@Override
		public void focusGained(FocusEvent e) {	}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			list.guiRenameElement(element, currentName);
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2960582722911027050L;
	private JTextField nodeName;
}
