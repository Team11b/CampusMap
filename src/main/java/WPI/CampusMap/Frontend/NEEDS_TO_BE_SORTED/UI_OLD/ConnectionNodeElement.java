package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI_OLD;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import WPI.CampusMap.Backend.Core.Point.ConnectionPoint;

public class ConnectionNodeElement extends JComponent
{
	private JTextField mapNameField;
	private JTextField nodeNameField;
	
	private String mapName;
	private String nodeName;
	
	private ConnectionPoint editingPoint;
	
	public ConnectionNodeElement(ConnectionPoint editingPoint, String mapName, String nodeName)
	{
		super();
		
		this.editingPoint = editingPoint;
		this.mapName = mapName;
		this.nodeName = nodeName;
		
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JButton btnRemove = new JButton("X");
		springLayout.putConstraint(SpringLayout.NORTH, btnRemove, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, btnRemove, 5, SpringLayout.WEST, this);
		add(btnRemove);
		btnRemove.addActionListener(new RemoveEventListener(this));
		
		mapNameField = new JTextField(mapName);
		springLayout.putConstraint(SpringLayout.NORTH, mapNameField, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, mapNameField, 6, SpringLayout.EAST, btnRemove);
		springLayout.putConstraint(SpringLayout.EAST, mapNameField, -10, SpringLayout.EAST, this);
		add(mapNameField);
		mapNameField.setColumns(10);
		mapNameField.addFocusListener(new CommitEventListener());
		
		nodeNameField = new JTextField(nodeName);
		springLayout.putConstraint(SpringLayout.NORTH, nodeNameField, 6, SpringLayout.SOUTH, mapNameField);
		springLayout.putConstraint(SpringLayout.WEST, nodeNameField, 0, SpringLayout.WEST, mapNameField);
		springLayout.putConstraint(SpringLayout.EAST, nodeNameField, -10, SpringLayout.EAST, this);
		nodeNameField.setColumns(10);
		add(nodeNameField);
		nodeNameField.addFocusListener(new CommitEventListener());
	}
	
	public String getMapName()
	{
		return mapName;
	}
	
	public String getNodeName()
	{
		return nodeName;
	}
	
	private class CommitEventListener implements FocusListener
	{

		@Override
		public void focusGained(FocusEvent e) 
		{
			
		}

		@Override
		public void focusLost(FocusEvent e) 
		{
			if(e.isTemporary())
				return;
			
			String newMapName = mapNameField.getText();
			String newNodeName = nodeNameField.getText();
			if(!newMapName.equals(mapName))
			{
				HashMap<String, String> connectionMap = editingPoint.getLinkedPoints();
				connectionMap.remove(mapName);
				connectionMap.put(newMapName, newNodeName);
				
				mapName = newMapName;
				nodeName = newNodeName;
			}
			else if(!newNodeName.equals(nodeName))
			{
				HashMap<String, String> connectionMap = editingPoint.getLinkedPoints();
				connectionMap.put(newMapName, newNodeName);
				
				nodeName = newNodeName;
			}
			
		}
	}
	
	private class RemoveEventListener implements ActionListener
	{
		private ConnectionNodeElement parent;
		
		public RemoveEventListener(ConnectionNodeElement parent)
		{
			this.parent = parent;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			ConnectionNodeList list = (ConnectionNodeList) getParent().getParent();
			list.removeElement(parent);
		}
	}
	
	@Override
	public Dimension getPreferredSize() 
	{
		return new Dimension(getParent().getSize().width, 65);
	}
	
	@Override
	public Dimension getMinimumSize()
	{
		return new Dimension(100, 40);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1059930392493049L;
}
