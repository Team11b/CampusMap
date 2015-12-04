package WPI.CampusMap.UI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import WPI.CampusMap.Backend.ConnectionPoint;
import WPI.CampusMap.Backend.Point.Point;

public class ConnectionNodeList extends  JComponent
{
	private ConnectionPoint editingPoint;
	private Panel displayPanel;
	
	public ConnectionNodeList()
	{
		setLayout(new BorderLayout(0, 0));
		
		displayPanel = new Panel();
		FlowLayout flowLayout = (FlowLayout) displayPanel.getLayout();
		flowLayout.setHgap(0);
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(displayPanel, BorderLayout.CENTER);
		
		JPanel buttonsPanel = new JPanel();
		add(buttonsPanel, BorderLayout.SOUTH);
		
		JButton btnAdd = new JButton("Add");
		buttonsPanel.add(btnAdd);
		btnAdd.addActionListener(new AddButtonListener());
	}
	
	public void setConnectionPoint(Point point)
	{
		if(point instanceof ConnectionPoint)
		{
			editingPoint = (ConnectionPoint)point;
			clearElements();
			
			HashMap<String, String> connections = editingPoint.getLinkedPoints();
			for(Map.Entry<String, String> entry : connections.entrySet())
			{
				createElementFor(entry.getKey(), entry.getValue());
			}
			
			setVisible(true);
		}
		else
		{
			editingPoint = null;
			clearElements();
			setVisible(false);
		}
	}
	
	private void clearElements()
	{
		displayPanel.removeAll();
		displayPanel.revalidate();
		displayPanel.repaint();
	}
	
	private void createElementFor(String mapName, String nodeName)
	{
		HashMap<String, String> connections = editingPoint.getLinkedPoints();
		if(!nodeName.equals(connections.get(mapName)))
			return;
		
		ConnectionNodeElement element = new ConnectionNodeElement(editingPoint, mapName, nodeName);
		displayPanel.add(element);
		displayPanel.revalidate();
		displayPanel.repaint();
	}
	
	public void removeElement(ConnectionNodeElement element)
	{
		HashMap<String, String> connections = editingPoint.getLinkedPoints();
		connections.remove(element.getMapName());
		
		displayPanel.remove(element);
		displayPanel.revalidate();
		displayPanel.repaint();
	}
	
	private void addBlankConnection()
	{
		HashMap<String, String> connections = editingPoint.getLinkedPoints();
		
		String mapName = "None";
		if(connections.containsKey(mapName))
			return;
		
		String nodeName = "";
		
		connections.put(mapName, nodeName);
		
		ConnectionNodeElement element = new ConnectionNodeElement(editingPoint, mapName, nodeName);
		displayPanel.add(element);
		displayPanel.revalidate();
		displayPanel.repaint();
	}
	
	class AddButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			addBlankConnection();
		}
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1203948239594723487L;
}
