package WPI.CampusMap.Frontend.Graphics.Dev;

import java.awt.Color;
import java.util.ArrayList;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Frontend.Dev.EditorToolMode;
import WPI.CampusMap.Frontend.Graphics.PointGraphicsObject;
import WPI.CampusMap.Frontend.Graphics.RealMouseEvent;
import WPI.CampusMap.Frontend.UI.DevMode;

public class DevPointGraphicsObject extends PointGraphicsObject<DevGraphicalMap>
{	
	public DevPointGraphicsObject(RealPoint backend, DevGraphicalMap owner)
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
		EditorToolMode mode = getOwnerMode(DevMode.class).getCurrentToolMode();
		DevPointGraphicsObject selected = getOwnerMode(DevMode.class).getSelectedPoint();
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
	public void onDeleted() 
	{
		removeEdges();
		RealPoint ourPoint = getRepresentedObject();
		getOwner().getMap().removePoint(ourPoint);
	}
	
	public void removeEdges()
	{
		RealPoint ourPoint = getRepresentedObject();
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
		EditorToolMode mode = getOwnerMode(DevMode.class).getCurrentToolMode();
		switch(mode)
		{
		case DeletePoint:
			delete();
			break;
		case None:
		case Point:
			getOwnerMode(DevMode.class).setSelectedPoint(this);
			break;
		case Edge:
			getOwnerMode(DevMode.class).setSelectedPoint(this);
			break;
			default:
				break;
		}
	}
	
	@Override
	public boolean isMouseOver(RealMouseEvent e)
	{
		if(getOwnerMode(DevMode.class).getCurrentToolMode() == EditorToolMode.DeleteEdge)
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

	public void setType(String type) {
		RealPoint point = getRepresentedObject();
		point.setType(type);
	}
}
