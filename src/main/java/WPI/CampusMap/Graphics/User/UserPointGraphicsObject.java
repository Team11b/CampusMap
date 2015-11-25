package WPI.CampusMap.Graphics.User;

import java.awt.Color;

import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.Graphics.PointGraphicsObject;
import WPI.CampusMap.Graphics.RealMouseEvent;

public class UserPointGraphicsObject extends PointGraphicsObject<UserGraphicalMap>
{
	private static UserPointGraphicsObject startPoint;
	private static UserPointGraphicsObject endPoint;
	
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
			startPoint = this;
		}
		else if(endPoint == null && startPoint != this)
		{
			endPoint = this;
		}
	}
}
