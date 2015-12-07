package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JSeparator;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JList;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.ScrollPane;
import javax.swing.JPanel;

public class PointList extends Panel
{
	public PointList()
	{
		setForeground(SystemColor.controlShadow);
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setToolTipText("Add a destination to your trip");
		btnAdd.addActionListener(new AddButtonActionListener());
		springLayout.putConstraint(SpringLayout.WEST, btnAdd, 108, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnAdd, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnAdd, -108, SpringLayout.EAST, this);
		add(btnAdd);
		
		scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -7, SpringLayout.NORTH, btnAdd);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, this);
		add(scrollPane);
		
		listPanel = new JPanel();
		scrollPane.setViewportView(listPanel);
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
	}
	
	public PointListElement addPointElement(String name)
	{
		if(elements.containsKey(name))
			return null;
		
		PointListElement element = new PointListElement(this, name);
		elements.put(name, element);
		listPanel.add(element);
		
		listPanel.revalidate();
		listPanel.repaint();
		
		return element;
	}
	
	public void removePointElement(String name)
	{
		remove(elements.get(name));
		elements.remove(name);
	}
	
	public void renamePointElement(String oldName, String newName)
	{
		PointListElement element = elements.get(oldName);
		
		elements.remove(oldName);
		elements.put(newName, element);
		
		
	}
	
	protected void removePointElement(PointListElement element)
	{
		removePointElement(element.getName());
		
		for(PointListEventHandler handler : handlers)
		{
			handler.pointDescriptorRemoved(element);
		}
	}
	
	protected void gotoPointElement(PointListElement element)
	{
		for(PointListEventHandler handler : handlers)
		{
			handler.pointDescriptorShow(element);
		}
	}
	
	protected void renamePointElement(PointListElement element, String oldName)
	{
		renamePointElement(oldName, element.getName());
		
		for(PointListEventHandler handler : handlers)
		{
			handler.pointDescriptorRenamed(element, oldName);
		}
	}
	
	private class AddButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			PointListElement newElement = addPointElement("");
			if(newElement == null)
				return;
			
			for(PointListEventHandler handler : handlers)
			{
				handler.pointDescriptorAdded(newElement);
			}
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1898176351964283864L;
	private JPanel listPanel;
	private JScrollPane scrollPane;
	private LinkedList<PointListEventHandler> handlers = new LinkedList<>();

	private HashMap<String, PointListElement> elements = new HashMap<>();
}
