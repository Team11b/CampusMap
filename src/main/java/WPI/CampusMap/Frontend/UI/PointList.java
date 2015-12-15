package WPI.CampusMap.Frontend.UI;

import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import WPI.CampusMap.Backend.Core.Point.IPoint;

public class PointList extends Panel
{
	
	public PointList()
	{
		setForeground(SystemColor.controlShadow);
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		
		JButton btnAdd = new JButton("Add");
		//btnAdd.setToolTipText("Add a destination to your trip");
		btnAdd.addActionListener(new AddButtonActionListener());
		springLayout.putConstraint(SpringLayout.WEST, btnAdd, 108, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnAdd, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnAdd, -108, SpringLayout.EAST, this); add(btnAdd);

		scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 0, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, this);
		add(scrollPane);

		listPanel = new JPanel();
		scrollPane.setViewportView(listPanel);
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
	}

	public void addListener(PointListEventHandler handler)
	{
		handlers.add(handler);
	}
	
	/**
	 * Adds a new point descriptor to the list.
	 * @param name The name of the new point descriptor.
	 * @return True if the point descriptor could be added.
	 */
	public boolean addPointDescriptor(String name)
	{
		if(elements.containsKey(name))
			return false;
		
		PointListElement newElement = new PointListElement(name, elements.size(), this);
		return onElementAdded(newElement);
	}
	
	/**
	 * Adds a point descriptor to the list using an existing point.
	 * @param point The point to use as the point descriptor.
	 * @return True if the point could be added.
	 */
	public boolean addPoint(IPoint point)
	{
		return addPointDescriptor(point.toString());
	}
	
	public boolean removePointDescriptor(String name)
	{
		if(!elements.containsKey(name))
			return false;
		
		PointListElement element = elements.get(name);
		onElementRemoved(element);
		
		refreshIndicies();
		
		return true;
	}
	
	public void clearPointDescriptors()
	{
		//create an array because we cannot modify a collection while iterating through it.
		String[] names = new String[elements.size()];
		
		int i = 0;
		for(String name : elements.keySet())
		{
			names[i] = name;
			i++;
		}
		
		for(String name : names)
		{
			removePointDescriptor(name);
		}
	}

	/**
	 * Adds an element to the list.
	 * @param element The element to add.
	 * @return False if the element could not be added to the list, true otherwise.
	 */
	protected boolean onElementAdded(PointListElement element)
	{
		if(elements.containsKey(element.getCurrentName()))
			return false;
		
		elements.put(element.getCurrentName(), element);
		listPanel.add(element);
		
		for(PointListEventHandler handler : handlers)
		{
			handler.pointDescriptorAdded(element);
		}

		revalidate();
		
		return true;
	}
	
	/**
	 * Removes an element from the list.
	 * @param element The element to remove.
	 */
	protected void onElementRemoved(PointListElement element)
	{
		elements.remove(element.getCurrentName());
		listPanel.remove(element);
		
		for(PointListEventHandler handler : handlers)
		{
			handler.pointDescriptorRemoved(element);
		}
		
		revalidate();
		repaint();
	}
	
	/**
	 * Called when an element is told to show itself.
	 * @param element The element to show on the map.
	 */
	protected void onElementShown(PointListElement element)
	{
		for(PointListEventHandler handler : handlers)
		{
			handler.pointDescriptorShow(element);
		}
	}
	
	protected boolean onElementCheckName(PointListElement element, String newName)
	{
		if(elements.containsKey(newName))
			return false;
		
		for(PointListEventHandler handler : handlers)
		{
			if(!handler.pointDescriptorNameCheck(element, newName))
				return false;
		}
		
		return true;
	}
	
	protected void onElementRenamed(PointListElement element, String oldName)
	{
		for(PointListEventHandler handler : handlers)
		{
			handler.pointDescriptorRenamed(element, oldName);
		}
		
		elements.remove(oldName);
		elements.put(element.getCurrentName(), element);
	}
	
	protected void onElementNameCheckFailed(PointListElement element, String failedName)
	{
		for(PointListEventHandler handler : handlers)
		{
			handler.pointDescriptorNameCheckFailed(element, failedName);
		}
	}
	
	private void refreshIndicies()
	{
		for(int i = 0; i < listPanel.getComponentCount(); i++)
		{
			PointListElement element = (PointListElement)listPanel.getComponent(i);
			element.setIndex(i);
		}
	}
	
	private class AddButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			addPointDescriptor("");
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
