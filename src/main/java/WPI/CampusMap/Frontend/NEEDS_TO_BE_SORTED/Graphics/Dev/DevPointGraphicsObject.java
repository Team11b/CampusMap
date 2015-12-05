package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.Dev;

import java.awt.Color;
import java.util.ArrayList;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Point.ConnectionPoint;
import WPI.CampusMap.Backend.Core.Point.Point;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Dev.EditorToolMode;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.PointGraphicsObject;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.RealMouseEvent;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI.AppUIObject;

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
	
	public void convertToConnectionPoint(String type)
	{
		Point point = getRepresentedObject();
		if(point instanceof ConnectionPoint)
		{
			point.setType(type);
			return;
		}
		
		Coord copyCoord = new Coord(point.getCoord().getX(), point.getCoord().getY());
		
		ConnectionPoint connectionPoint = new ConnectionPoint(copyCoord, type, point.getId(), point.getMap());
		
		@SuppressWarnings("unchecked")
		ArrayList<Point> neighbors = (ArrayList<Point>) point.getNeighborsP().clone();
		
		removeEdges();
		getOwner().getMap().removePoint(point);
		getOwner().getMap().addPoint(connectionPoint);
		
		setRepresentedObject(connectionPoint);
		
		for(Point p : neighbors)
		{
			connectionPoint.addNeighbor(p);
			createGraphicsEdge((Point) getOwner().getObject(p).getRepresentedObject());
		}
		
		AppUIObject.getInstance().onPointSelected(getRepresentedObject());
	}
	
	public void convertToNormalPoint(String type)
	{
		Point point = getRepresentedObject();
		if(!(point instanceof ConnectionPoint))
		{
			point.setType(type);
			return;
		}
		
		ConnectionPoint connectionPoint = (ConnectionPoint)point;
		
		Coord copyCoord = new Coord(point.getCoord().getX(), point.getCoord().getY());
		
		Point normalPoint = new Point(copyCoord, type, point.getId(), point.getMap());
		
		@SuppressWarnings("unchecked")
		ArrayList<Point> neighbors = (ArrayList<Point>) connectionPoint.getNeighborsP().clone();
		
		removeEdges();
		getOwner().getMap().removePoint(connectionPoint);
		getOwner().getMap().addPoint(normalPoint);
		
		setRepresentedObject(normalPoint);
		
		for(Point p : neighbors)
		{
			normalPoint.addNeighbor(p);
			createGraphicsEdge((Point) getOwner().getObject(p).getRepresentedObject());
		}
		
		AppUIObject.getInstance().onPointSelected(getRepresentedObject());
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
	public void onDeleted() 
	{
		removeEdges();
		Point ourPoint = getRepresentedObject();
		getOwner().getMap().removePoint(ourPoint);
	}
	
	public void removeEdges()
	{
		Point ourPoint = getRepresentedObject();
		ArrayList<Point> connections = ourPoint.getNeighborsP();
		
		for(Point other : connections)
		{
			DevEdgeGraphicsObject edge = DevEdgeGraphicsObject.getGraphicsEdge(ourPoint, other, getOwner());
			edge.delete();
		}
	}
	
	@Override
	public void onMouseClick(RealMouseEvent e) 
	{
		EditorToolMode mode = getOwner().getMode();
		switch(mode)
		{
		case DeletePoint:
			AppUIObject.getInstance().onPointSelected(null);
			delete();
			break;
		case None:
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
	
	/**
	 * Called when this point has been selected
	 */
	private void onSelected()
	{
		System.out.println(getRepresentedObject().getNeighborsID());
		AppUIObject.getInstance().onPointSelected(getRepresentedObject());
	}
	
	private void createGraphicsEdge(Point other)
	{
		DevEdgeGraphicsObject.createGraphicsEdge(getRepresentedObject(), other, getOwner());
	}
}
