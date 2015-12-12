package WPI.CampusMap.Frontend.Graphics;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;

public class RealMouseEvent
{
	private Coord worldCoords, imageCoords;
	private boolean isAlt, isCtrl, isShift;
	private int button;
	
	public RealMouseEvent(Coord worldCoords, Coord imageCoords, int button, boolean isAlt, boolean isCtrl, boolean isShift)
	{
		this.worldCoords = worldCoords;
		this.imageCoords = imageCoords;
		this.button = button;
		this.isAlt = isAlt;
		this.isCtrl = isCtrl;
		this.isShift = isShift;
	}
	
	public Coord getWorldCoords()
	{
		return worldCoords;
	}
	
	public Coord getImageCoord()
	{
		return imageCoords;
	}
	
	public boolean isAltDown()
	{
		return isAlt;
	}
	
	public boolean isControlDown()
	{
		return isCtrl;
	}
	
	public boolean isShiftDown()
	{
		return isShift;
	}
	
	public int getButton()
	{
		return button;
	}
}
