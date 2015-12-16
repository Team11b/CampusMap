package WPI.CampusMap.Frontend.Graphics.User;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Frontend.Graphics.PointGraphicsObject;
import WPI.CampusMap.Frontend.Graphics.RealMouseEvent;
import WPI.CampusMap.Frontend.UI.UserModeClass;

public class UserPointGraphicsObject extends PointGraphicsObject<UserGraphicalMap>
{
	@SuppressWarnings("unused")
	private static final ImageIcon redIcon = new ImageIcon("icon-red.png");
	private static final ImageIcon blueIcon = new ImageIcon("icon-blue.png");	
	private static final ImageIcon greenIcon = new ImageIcon("icon-green.png");	
	private static final ImageIcon yellowIcon = new ImageIcon("icon-yellow.png");	
	@SuppressWarnings("unused")
	private static final ImageIcon purpleIcon = new ImageIcon("icon-purple.png");	
	private static final ImageIcon lightRedIcon = new ImageIcon("icon-lightred.png");
	private static final ImageIcon lightGreenIcon = new ImageIcon("icon-lightgreen.png");
	private static final ImageIcon pinkIcon = new ImageIcon("icon-pink.png");
	private static final ImageIcon normalIcon = new ImageIcon("icon.png");
	
	private static final ImageIcon doorIcon = new ImageIcon("door.png");
	private static final ImageIcon stairIcon = new ImageIcon("stairs-icon.gif");
	private static final ImageIcon elevatorIcon = new ImageIcon("elevator-icon.jpg");
	private static final ImageIcon roomIcon = new ImageIcon("room.gif");
	
	@SuppressWarnings("unused")
	private boolean twitter;
	public enum selectionState{
		UNSELECTED,SELECTED,CURRENT
	};
	
	private selectionState state = selectionState.UNSELECTED;
	@SuppressWarnings("unused")
	private ImageIcon normalImage;
	@SuppressWarnings("unused")
	private ImageIcon hoverImage;
	
	public UserPointGraphicsObject(RealPoint backend, UserGraphicalMap owner) 
	{
		super(backend, owner);
	}
	
	@Override
	public Color getColor() 
	{
		return super.getColor();
	}
	
	@Override
	public float getAlpha() 
	{
		return 1f;
	}
	
	@Override
	public float getScale()
	{
		return super.getScale() * (getOwnerMode(UserModeClass.class).getHighVisibility() ? 1.5f : 1.0f);
	}
	
	@Override
	public void onDraw(Graphics2D graphics)
	{
		ImageIcon renderImage = getRenderImage();
		
		if(renderImage != null)
			graphics.drawImage(renderImage.getImage(), -10, -10, 20, 20, renderImage.getImageObserver());
		else
			super.onDraw(graphics);
	}
	
	@Override
	public boolean isVisible()
	{
		return getOwnerMode(UserModeClass.class).getShowAllPoints() || !getRepresentedObject().getType().equals(RealPoint.HALLWAY);
	}
	
	@Override
	public void onMouseClick(RealMouseEvent e)
	{
		getOwnerMode(UserModeClass.class).addPointToDestinations(this);
	}
	
	/** getter for selected
	 * 
	 * @return state the selection state
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
	
	private ImageIcon getRenderImage()
	{
		if(getOwnerMode(UserModeClass.class).containsInDest(this))
		{
			if(getOwnerMode(UserModeClass.class).isRouteStart(this))
				return lightGreenIcon;
			else if(getOwnerMode(UserModeClass.class).isRouteEnd(this))
				return lightRedIcon;
			else
				return yellowIcon;
		}
		else if(state == selectionState.CURRENT)
		{
			return pinkIcon;
		}
		else if(getOwnerMode(UserModeClass.class).isUltimateFirst(this))
		{
			return lightGreenIcon;
		}
		else if(getOwnerMode(UserModeClass.class).isUltimateLast(this))
		{
			return lightRedIcon;
		}
		else if(state == selectionState.SELECTED){
			
			if(getOwnerMode(UserModeClass.class).isSectionEnd(this))
				return blueIcon;
			else if(getOwnerMode(UserModeClass.class).isSectionStart(this))
				return greenIcon;
		}else if(getOwnerMode(UserModeClass.class).isSectionEndDestination(this)||
				getOwnerMode(UserModeClass.class).isSectionStartDestination(this)){
			return yellowIcon;
		
		}
		
		if(getOwner().getHoverObject() == this)
			return normalIcon;
		
		switch(getRepresentedObject().getType())
		{
		case RealPoint.OUT_DOOR:
			return doorIcon;
		case RealPoint.ELEVATOR:
			return elevatorIcon;
		case RealPoint.STAIRS:
			return stairIcon;
		case RealPoint.ROOM:
			return roomIcon;
		}
		
		return null;
	}
}
