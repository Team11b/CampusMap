package WPI.CampusMap.Graphics;

public class RealMouseEvent
{
	private float x, y;
	private boolean isAlt, isCtrl, isShift;
	private int button;
	
	public RealMouseEvent(float x, float y, int button, boolean isAlt, boolean isCtrl, boolean isShift)
	{
		this.x = x;
		this.y = y;
		this.button = button;
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
	
	public int getButton()
	{
		return button;
	}
}
