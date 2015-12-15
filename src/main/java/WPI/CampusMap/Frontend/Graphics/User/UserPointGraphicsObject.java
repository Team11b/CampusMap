package WPI.CampusMap.Frontend.Graphics.User;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Frontend.Graphics.PointGraphicsObject;
import WPI.CampusMap.Frontend.Graphics.RealMouseEvent;
import WPI.CampusMap.Frontend.UI.UserMode;

public class UserPointGraphicsObject extends PointGraphicsObject<UserGraphicalMap>
{
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
		return Color.white;
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
}
