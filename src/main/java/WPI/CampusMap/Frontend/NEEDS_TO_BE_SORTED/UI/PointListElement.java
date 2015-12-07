package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import javax.swing.JComponent;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.Dimension;
import java.awt.event.TextListener;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import javax.swing.JTextField;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

public class PointListElement extends JComponent
{
	private LinkedList<PointListEventHandler> handlers = new LinkedList<>();
	
	public PointListElement() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JButton removeButton = new JButton("");
		removeButton.addActionListener(new RemoveButtonActionListener());
		springLayout.putConstraint(SpringLayout.NORTH, removeButton, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, removeButton, -40, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, removeButton, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, removeButton, -10, SpringLayout.EAST, this);
		removeButton.setIcon(new ImageIcon(PointListElement.class.getResource("/javax/swing/plaf/metal/icons/ocean/paletteClose.gif")));
		removeButton.setMinimumSize(new Dimension(45, 25));
		add(removeButton);
		
		
		JButton goToButton = new JButton("");
		goToButton.addActionListener(new GotoButtonActionListener());
		springLayout.putConstraint(SpringLayout.WEST, goToButton, -40, SpringLayout.WEST, removeButton);
		springLayout.putConstraint(SpringLayout.EAST, goToButton, -6, SpringLayout.WEST, removeButton);
		springLayout.putConstraint(SpringLayout.NORTH, goToButton, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, goToButton, -10, SpringLayout.SOUTH, this);
		goToButton.setIcon(new ImageIcon(PointListElement.class.getResource("/javax/swing/plaf/metal/icons/ocean/minimize.gif")));
		goToButton.setMinimumSize(new Dimension(45, 25));
		add(goToButton);
		
		JTextField nodeName = new JTextField();
		nodeName.addInputMethodListener(new PointNameChanged());
		springLayout.putConstraint(SpringLayout.NORTH, nodeName, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, nodeName, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.WEST, nodeName, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, nodeName, -10, SpringLayout.WEST, goToButton);
		add(nodeName);
	}
	
	public void addPointListEventHandler(PointListEventHandler handler)
	{
		handlers.add(handler);
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
	public Dimension getMinimumSize() {
		// TODO Auto-generated method stub
		return new Dimension(100, 45);
	}
	
	private class RemoveButtonActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
		}
		
	}
	
	private class GotoButtonActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
		}
		
	}
	
	private class PointNameChanged implements InputMethodListener
	{
		public void caretPositionChanged(InputMethodEvent e)
		{
			
		}
		
		public void inputMethodTextChanged(InputMethodEvent e)
		{
			System.out.println(e.getText());
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2960582722911027050L;
}
