package WPI.CampusMap.Graphics;

public class WorldMouseEvent
{
	private float x, y;
	private boolean isAlt, isCtrl, isShift;
	
	public WorldMouseEvent(float x, float y, boolean isAlt, boolean isCtrl, boolean isShift)
	{
		this.x = x;
		this.y = y;
		this.isAlt = isAlt;
		this.isCtrl = isCtrl;
		this.isShift = isShift;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
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
}
