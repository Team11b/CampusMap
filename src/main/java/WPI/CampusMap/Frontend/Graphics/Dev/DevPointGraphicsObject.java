package WPI.CampusMap.Frontend.Graphics.Dev;

import java.awt.Color;
import java.util.ArrayList;

import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Frontend.Dev.EditorToolMode;
import WPI.CampusMap.Frontend.Graphics.PointGraphicsObject;
import WPI.CampusMap.Frontend.Graphics.RealMouseEvent;
import WPI.CampusMap.Frontend.UI.DevModeClass;

public class DevPointGraphicsObject extends PointGraphicsObject<DevGraphicalMap>
{	
	public DevPointGraphicsObject(IPoint backend, DevGraphicalMap owner)
	{
		super(backend, owner);
		
		ArrayList<IPoint> connections = backend.getNeighborsP();
		for(IPoint p : connections)
		{
			if(p.getMap().equals(backend.getMap())){
				createGraphicsEdge(p);
			}
		}
	}
	
	@Override
	public Color getColor() 
	{
		EditorToolMode mode = getOwnerMode(DevModeClass.class).getCurrentToolMode();
		boolean isSelected = getOwnerMode(DevModeClass.class).isPointSelected(this);
		if(mode == EditorToolMode.Point || mode == EditorToolMode.Edge || mode == EditorToolMode.DeletePoint || mode == EditorToolMode.None)
		{
			if(isSelected)
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
	
	public String getPointId()
	{
		return getRepresentedObject().getId();
	}
	
	public String getPointType()
	{
		return getRepresentedObject().getType();
	}

	/**
	 * Adds an edge between this node and another.
	 * @param other The other graphical node to add an edge between.
	 */
	public void addEdgeTo(DevPointGraphicsObject other)
	{
		getRepresentedObject().addNeighbor(other.getRepresentedObject());
		other.getRepresentedObject().addNeighbor(getRepresentedObject());
		
		createGraphicsEdge(other.getRepresentedObject());
	}
	
	@Override
	public void onDeleted() 
	{
		removeEdges();
		IPoint ourPoint = getRepresentedObject();
		getOwner().getMap().removePoint(ourPoint);
	}
	
	public void removeEdges()
	{
		IPoint ourPoint = getRepresentedObject();
		ArrayList<IPoint> connections = ourPoint.getNeighborsP();
		
		for(IPoint other : connections)
		{
			DevEdgeGraphicsObject edge = getOwner().getGraphicalEdge(ourPoint, other);
			if(ourPoint.getMap().equals(other.getMap())){
				edge.delete();
			}
		}
	}
	
	@Override
	public void onMouseClick(RealMouseEvent e) 
	{
		EditorToolMode mode = getOwnerMode(DevModeClass.class).getCurrentToolMode();
		switch(mode)
		{
		case DeletePoint:
			delete();
			break;
		case None:
		case Point:
			if(!e.isShiftDown())
				getOwnerMode(DevModeClass.class).setSelectedPoint(this);
			else
				getOwnerMode(DevModeClass.class).addSelectedPoint(this);
			break;
		case Edge:
			getOwnerMode(DevModeClass.class).addSelectedPoint(this);
			getOwner().makeEdgeBetweenSelected();
			break;
			default:
				break;
		}
	}
	
	@Override
	public boolean isMouseOver(RealMouseEvent e)
	{
		if(getOwnerMode(DevModeClass.class).getCurrentToolMode() == EditorToolMode.DeleteEdge)
			return false;
		
		return super.isMouseOver(e);
	}
	
	/**
	 * Called when this point has been selected
	 */
	public void onSelected()
	{
	}
	
	private void createGraphicsEdge(IPoint other)
	{
		DevEdgeGraphicsObject.createGraphicsEdge(getRepresentedObject(), other, getOwner());
	}

	/**
	 * Changes the underlying type of the point.
	 * @param type The new type of the point.
	 */
	public void setType(String type)
	{
		IPoint point = getRepresentedObject();
		point.setType(type);
	}
	
	/**
	 * Changes the id of the point.
	 * @param newId The new id for the point to go by.
	 * @return Returns if successful.
	 */
	public boolean setId(String newId)
	{
		IPoint point = getRepresentedObject();
		return point.setId(newId);
	}
}
