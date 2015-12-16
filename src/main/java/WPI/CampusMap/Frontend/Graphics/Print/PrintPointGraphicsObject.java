package WPI.CampusMap.Frontend.Graphics.Print;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Frontend.Graphics.PointGraphicsObject;

public class PrintPointGraphicsObject extends PointGraphicsObject<PrintGraphicalMap>
{	
	public enum PrintFlags
	{
		None,
		Node,
		StartRoute,
		StartSection,
		EndSection,
		EndRoute
	}
	
	private PrintFlags flags;
	private ImageIcon drawImage;
	
	public PrintPointGraphicsObject(IPoint backend, PrintGraphicalMap owner, PrintFlags flags) 
	{
		super(backend, owner);
		this.flags = flags;
		
		switch(flags)
		{
		case EndRoute:
			break;
		case StartSection:
			drawImage = new ImageIcon("icon-green.png");
			break;
		case EndSection:
			drawImage = new ImageIcon("icon-red.png");
			break;
		case Node:
			drawImage = new ImageIcon("icon-blue.png");
			break;
		case None:
			break;
		case StartRoute:
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onDraw(Graphics2D graphics)
	{
		graphics.drawImage(drawImage.getImage(), -drawImage.getIconWidth() / 2, -drawImage.getIconHeight(), drawImage.getIconWidth(), drawImage.getIconHeight(), drawImage.getImageObserver());
	}
	
	@Override
	public Color getColor()
	{
		return super.getColor();
	}
	
	@Override
	public boolean isVisible()
	{
		return flags != PrintFlags.None;
	}
}
