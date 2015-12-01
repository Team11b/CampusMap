package WPI.CampusMap.Graphics.Dev;

import java.awt.Color;
import java.util.ArrayList;

import WPI.CampusMap.Backend.ConnectionPoint;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.Dev.EditorToolMode;
import WPI.CampusMap.Graphics.*;

public class DevPointGraphicsObject extends PointGraphicsObject<DevGraphicalMap>
{
	private static DevPointGraphicsObject selected;
	
	public static DevPointGraphicsObject getSelected()
	{
		return selected;
	}
	
	public static void clearSelection()
	{
		selected = null;
	}
	
	public DevPointGraphicsObject(Point backend, DevGraphicalMap owner)
	{
		super(backend, owner);
		
		ArrayList<Point> connections = backend.getNeighborsP();
		for(Point p : connections)
		{
			createGraphicsEdge(p);
		}
	}
	
	public void select()
	{
		selected = this;
	}
	
	@Override
	public Color getColor() 
	{
		EditorToolMode mode = getOwner().getMode();
		if(mode == EditorToolMode.Point || mode == EditorToolMode.Edge || mode == EditorToolMode.DeletePoint || mode == EditorToolMode.None)
		{
			if(selected == this)
			{
				return Color.yellow;
			}
		}
		else
		{
			return Color.red;
		}
		
		return super.getColor();
	}

	/**
	 * Adds an edge between this node and another.
	 * @param other The other graphical node to add an edge betwee.
	 */
	public void addEdgeTo(DevPointGraphicsObject other)
	{
		getRepresentedObject().addNeighbor(other.getRepresentedObject());
		other.getRepresentedObject().addNeighbor(getRepresentedObject());
		
		createGraphicsEdge(other.getRepresentedObject());
	}
	
	@Override
	public void onRemoved() 
	{
		Point ourPoint = getRepresentedObject();
		ArrayList<Point> connections = ourPoint.getNeighborsP();
		
		for(Point other : connections)
		{
			DevEdgeGraphicsObject edge = DevEdgeGraphicsObject.getGraphicsEdge(ourPoint, other, getOwner());
			edge.delete();
		}
		
		getOwner().getMap().removePoint(ourPoint);
	}
	
	@Override
	public void onMouseClick(RealMouseEvent e) 
	{
		EditorToolMode mode = getOwner().getMode();
		switch(mode)
		{
		case DeletePoint:
			delete();
			break;
		case None:
			Point selectedPoint = DevPointGraphicsObject.getSelected().getRepresentedObject();	
			getOwner().getUI().setTypeSelectorEditable(true);
			getOwner().getUI().setNodeTextFieldEditable(true);
			getOwner().getUI().setMapConnectorText(selectedPoint.getConnectionPoint().getLinkedMap());
			getOwner().getUI().setPointConnectorText(selectedPoint.getConnectionPoint().getLinkedPoint());
		case Point:
			selected = this;
			onSelected();
			break;
		case Edge:
			if(selected == null)
			{
				selected = this;
			}
			else
			{
				addEdgeTo(selected);
				if(!e.isShiftDown())
					selected = null;
				else
					selected = this;
			}
			break;
			default:
				break;
		}
	}
	
	@Override
	public boolean isMouseOver(RealMouseEvent e)
	{
		if(getOwner().getMode() == EditorToolMode.DeleteEdge)
			return false;
		
		return super.isMouseOver(e);
	}
	
	private void onSelected()
	{
		//selected.getOwner().
		//getColor();
	}
	
	private void createGraphicsEdge(Point other)
	{
		DevEdgeGraphicsObject.createGraphicsEdge(getRepresentedObject(), other, getOwner());
	}
	
	public void updatePoint(){
		//Copy the textbox to the type									
		DevPointGraphicsObject selectedObject = DevPointGraphicsObject.getSelected();
		if(selectedObject != null){
			Point selectedPoint = DevPointGraphicsObject.getSelected().getRepresentedObject();	
			getOwner().getMap().removePoint(selectedPoint);
			selectedPoint.setType(getOwner().getUI().getTypeSelector());
			selectedPoint.setId(getOwner().getUI().getID());
			
			if(getOwner().getUI().getTypeSelector() != "hallway"){
				//Is a connecting node.
				System.out.println("IS A CONNECTING NODE");
				ConnectionPoint connectionPoint = selectedPoint.getConnectionPoint();
				getOwner().getUI().setMapConnectionTextFieldEditable(true);
				getOwner().getUI().setMapConnectionTextFieldEditable(true);
				connectionPoint.setLinkedMap(getOwner().getUI().getMapConnectorText());
				connectionPoint.setLinkedPoint(getOwner().getUI().getPointConnectorText());
				getOwner().getMap().addPoint(connectionPoint);
			}else{
				System.out.println("IS NOT A CONNECTING NODE");
				getOwner().getUI().setMapConnectionTextFieldEditable(false);
				getOwner().getUI().setMapConnectionTextFieldEditable(false);
				getOwner().getMap().addPoint(selectedPoint.getNormalPoint());
			}
			
			System.out.println(getOwner().getMap().getAllPoints().containsKey(selectedPoint.getId()));
			
			selectedObject.clearSelection();
			getOwner().getUI().setNodeTextField("");
			getOwner().getUI().setTypeSelector(0);
		}
	}
}
