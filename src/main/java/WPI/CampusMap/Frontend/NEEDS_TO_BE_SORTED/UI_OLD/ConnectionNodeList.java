package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI_OLD;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import WPI.CampusMap.Backend.Core.Point.IPoint;

public class ConnectionNodeList extends  JComponent
{
	private IPoint editingPoint;
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
	
	public void setConnectionPoint(IPoint point)
	{
		if(point != null && !point.getType().equals("hallway"))
		{
			System.out.println("Point: " + point + "'s type: "+ point.getType());
			editingPoint = point;
			clearElements();
			
			HashMap<String, String> connections= new HashMap<String, String>();
			for(IPoint nPoint : editingPoint.getNeighborsP()){
				if(!nPoint.getMap().equals(editingPoint.getMap())){
					connections.put(nPoint.getMap(), nPoint.getId());
				}
			}
			
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
		HashMap<String, String> connections= new HashMap<String, String>();
		for(IPoint nPoint : editingPoint.getNeighborsP()){
			if(!nPoint.getMap().equals(editingPoint.getMap())){
				connections.put(nPoint.getMap(), nPoint.getId());
			}
		}
		if(!nodeName.equals(connections.get(mapName)))
			return;
		
		ConnectionNodeElement element = new ConnectionNodeElement(editingPoint, mapName, nodeName);
		displayPanel.add(element);
		displayPanel.revalidate();
		displayPanel.repaint();
	}
	
	public void removeElement(ConnectionNodeElement element)
	{
		HashMap<String, ArrayList<String>> connections = editingPoint.getNeighborPointsOnOtherMaps();
		connections.remove(element.getMapName());
		
		displayPanel.remove(element);
		displayPanel.revalidate();
		displayPanel.repaint();
	}
	
	private void addBlankConnection()
	{
		HashMap<String,  ArrayList<String>> connections = editingPoint.getNeighborPointsOnOtherMaps();
		
		String mapName = "None";
		if(connections.containsKey(mapName))
			return;
		
		String nodeName = "";
		
		//connections.get(mapName).clear();
		//connections.get(mapName).add(nodeName);
		
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
