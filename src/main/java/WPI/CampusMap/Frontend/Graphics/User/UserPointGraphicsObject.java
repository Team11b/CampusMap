package WPI.CampusMap.Frontend.Graphics.User;

import java.awt.Color;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Frontend.Graphics.PointGraphicsObject;
import WPI.CampusMap.Frontend.Graphics.RealMouseEvent;
import WPI.CampusMap.Frontend.UI.UserMode;

public class UserPointGraphicsObject extends PointGraphicsObject<UserGraphicalMap>
{
	private boolean selected;
	
	public UserPointGraphicsObject(RealPoint backend, UserGraphicalMap owner) 
	{
		super(backend, owner);		
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Color getColor() 
	{
		if(getOwnerMode(UserMode.class).containsInDest(this))
		{
			if(getOwnerMode(UserMode.class).isRouteStart(this))
				return Color.green;
			else if(getOwnerMode(UserMode.class).isRouteEnd(this))
				return Color.blue;
			else if(getOwnerMode(UserMode.class).isSectionStart(this))
				return Color.magenta;
			else if(getOwnerMode(UserMode.class).isSectionEnd(this))
				return Color.pink;
			//else if(getOwnerMode(UserMode.class).isCurrentDirectionNode(this))
			//	return Color.cyan;
			else
				return Color.yellow;
		}
		else if(selected == true){
//			if(getOwnerMode(UserMode.class).isRouteStart(this))
//				return Color.green;
//			else if(getOwnerMode(UserMode.class).isRouteEnd(this))
//				return Color.blue;
			
			if(getOwnerMode(UserMode.class).isSectionEnd(this))
				return Color.blue;
			else if(getOwnerMode(UserMode.class).isSectionStart(this))
				return Color.green;
			
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
	
	/** getter for selected
	 * 
	 * @return
	 */
	public boolean getSelected(){
		return selected;
	}
	
	public void setSelected(boolean isSelected){
		selected = isSelected;
	}
}
