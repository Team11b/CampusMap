package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.User;

import java.awt.Color;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.PointGraphicsObject;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.RealMouseEvent;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI.UserMode;

public class UserPointGraphicsObject extends PointGraphicsObject<UserGraphicalMap>
{
	public UserPointGraphicsObject(RealPoint backend, UserGraphicalMap owner) 
	{
		super(backend, owner);		
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Color getColor() 
	{
		if(getOwnerMode(UserMode.class).containsInRoute(this))
		{
			if(getOwnerMode(UserMode.class).isRouteStart(this))
				return Color.green;
			else if(getOwnerMode(UserMode.class).isRouteEnd(this))
				return Color.blue;
			else
				return Color.yellow;
		}
		
		return super.getColor();
	}
	
	@Override
	public float getAlpha() 
	{
		return 1f;
	}
	
	@Override
	public void onMouseClick(RealMouseEvent e)
	{
		getOwnerMode(UserMode.class).onPointAddedToRoute(this);
	}
}
