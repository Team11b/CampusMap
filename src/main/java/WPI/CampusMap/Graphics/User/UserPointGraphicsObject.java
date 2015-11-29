package WPI.CampusMap.Graphics.User;

import java.awt.Color;

import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.Graphics.PointGraphicsObject;
import WPI.CampusMap.Graphics.RealMouseEvent;

public class UserPointGraphicsObject extends PointGraphicsObject<UserGraphicalMap>
{
	private static UserPointGraphicsObject startPoint;
	private static UserPointGraphicsObject endPoint;
	private UserGraphicalMap owner;
	private Point backend;
	public static UserPointGraphicsObject getStartPoint()
	{
		return startPoint;
	}
	
	public static UserPointGraphicsObject getEndPoint()
	{
		return endPoint;
	}
	
	public UserPointGraphicsObject(Point backend, UserGraphicalMap owner) 
	{
		super(backend, owner);
		this.owner = owner;
		this.backend = backend;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Color getColor() 
	{
		if(startPoint == this)
		{
			return Color.green;
		}
		else if(endPoint == this)
		{
			return Color.blue;
		}
		
		return super.getColor();
	}
	
	@Override
	public void onMouseClick(RealMouseEvent e)
	{
		if(startPoint == null)
		{
			owner.MP.uiObject.setStart(backend.getId()); //terrible OO
			startPoint = this;			
		}
		else if(endPoint == null && startPoint != this)
		{
			owner.MP.uiObject.setEnd(backend.getId());
			endPoint = this;
		}	
	}
}
