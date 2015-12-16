package WPI.CampusMap.Frontend.Graphics.User;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Frontend.Graphics.PointGraphicsObject;
import WPI.CampusMap.Frontend.Graphics.RealMouseEvent;
import WPI.CampusMap.Frontend.UI.UserMode;

public class UserPointGraphicsObject extends PointGraphicsObject<UserGraphicalMap>
{
	
	private boolean twitter;
	public enum selectionState{
		UNSELECTED,SELECTED,CURRENT
	};
	
	private selectionState state = selectionState.UNSELECTED;
	private ImageIcon normalImage;
	private ImageIcon hoverImage;
	
	public UserPointGraphicsObject(RealPoint backend, UserGraphicalMap owner) 
	{
		super(backend, owner);		
		
		switch(getRepresentedObject().getType())
		{
		case RealPoint.OUT_DOOR:
			normalImage = new ImageIcon("door.png");
			break;
		case RealPoint.ELEVATOR:
			normalImage = new ImageIcon("elevator.png");
			break;
		case RealPoint.STAIRS:
			normalImage = new ImageIcon("stairs.png");
		}
		
		hoverImage = new ImageIcon("icon-red.png");
	}
	
	@Override
	public Color getColor() 
	{
		if(getOwnerMode(UserMode.class).containsInDest(this))
		{
//			System.out.println("this node has been hit "+ this);
			if(getOwnerMode(UserMode.class).isRouteStart(this))
				return new Color(150,255,150);
			else if(getOwnerMode(UserMode.class).isRouteEnd(this))
				return new Color(150,150,255);
			//else if(getOwnerMode(UserMode.class).isCurrentDirectionNode(this))
			//	return Color.cyan;
//			else if(getOwnerMode(UserMode.class).isSectionEnd(this))
//				return Color.blue;
//			else if(getOwnerMode(UserMode.class).isSectionStart(this))
//				return Color.green;
			else
				return Color.yellow;
		}
		else if(state == selectionState.CURRENT){
			return Color.pink;
		}
		
		else if(getOwnerMode(UserMode.class).isUltimateFirst(this)){
			return Color.CYAN;
		}else if(getOwnerMode(UserMode.class).isUltimateLast(this)){
			return Color.MAGENTA;
		}
		else if(state == selectionState.SELECTED){
//			if(getOwnerMode(UserMode.class).isRouteStart(this))
//				return Color.green;
//			else if(getOwnerMode(UserMode.class).isRouteEnd(this))
//				return Color.blue;
			
			if(getOwnerMode(UserMode.class).isSectionEnd(this))
				return Color.blue;
			else if(getOwnerMode(UserMode.class).isSectionStart(this))
				return Color.green;
		}else if(getOwnerMode(UserMode.class).isSectionEndDestination(this)||
				getOwnerMode(UserMode.class).isSectionStartDestination(this)){
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
	public void onDraw(Graphics2D graphics)
	{
		ImageIcon renderImage;
		if(getOwner().getHoverObject() != this)
			renderImage = normalImage;
		else
			renderImage = hoverImage;
		
		if(renderImage != null)
			graphics.drawImage(renderImage.getImage(), -renderImage.getIconWidth() / 2, -renderImage.getIconHeight(), renderImage.getIconWidth(), renderImage.getIconHeight(), renderImage.getImageObserver());
	}
	
	@Override
	public boolean isVisible()
	{
		return !getRepresentedObject().getType().equals(RealPoint.HALLWAY);
	}
	
	@Override
	public void onMouseClick(RealMouseEvent e)
	{
		getOwnerMode(UserMode.class).addPointToDestinations(this);
	}
	
	/** getter for selected
	 * 
	 * @return
	 */
	public selectionState getState(){
		return state;
	}
	
	public void setSelected(){
		state = selectionState.SELECTED;
	}

	public void setCurrent(){
		state = selectionState.CURRENT;
	}
	
	public void setUnselected(){
		state = selectionState.UNSELECTED;
	}
}
