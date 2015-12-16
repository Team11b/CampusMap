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
	private static final ImageIcon redIcon = new ImageIcon("icon-red.png");
	private static final ImageIcon blueIcon = new ImageIcon("icon-blue.png");	
	private static final ImageIcon greenIcon = new ImageIcon("icon-green.png");	
	private static final ImageIcon yellowIcon = new ImageIcon("icon-yellow.png");	
	private static final ImageIcon purpleIcon = new ImageIcon("icon-purple.png");	
	private static final ImageIcon lightRedIcon = new ImageIcon("icon-lightred.png");
	private static final ImageIcon lightGreenIcon = new ImageIcon("icon-lightgreen.png");
	private static final ImageIcon pinkIcon = new ImageIcon("icon-pink.png");
	private static final ImageIcon normalIcon = new ImageIcon("icon.png");
	
	private static final ImageIcon doorIcon = new ImageIcon("door.png");
	private static final ImageIcon stairIcon = new ImageIcon("stairs.png");
	private static final ImageIcon elevatorIcon = new ImageIcon("elevator.png");
	
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
	public void onDraw(Graphics2D graphics)
	{
		ImageIcon renderImage = getRenderImage();
		
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
	
	private ImageIcon getRenderImage()
	{
		if(getOwnerMode(UserMode.class).containsInDest(this))
		{
			if(getOwnerMode(UserMode.class).isRouteStart(this))
				return lightGreenIcon;
			else if(getOwnerMode(UserMode.class).isRouteEnd(this))
				return lightRedIcon;
			else
				return yellowIcon;
		}
		else if(state == selectionState.CURRENT)
		{
			return pinkIcon;
		}
		else if(getOwnerMode(UserMode.class).isUltimateFirst(this))
		{
			return lightGreenIcon;
		}
		else if(getOwnerMode(UserMode.class).isUltimateLast(this))
		{
			return lightRedIcon;
		}
		else if(state == selectionState.SELECTED){
			
			if(getOwnerMode(UserMode.class).isSectionEnd(this))
				return blueIcon;
			else if(getOwnerMode(UserMode.class).isSectionStart(this))
				return greenIcon;
		}else if(getOwnerMode(UserMode.class).isSectionEndDestination(this)||
				getOwnerMode(UserMode.class).isSectionStartDestination(this)){
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
		}
		
		return null;
	}
}
