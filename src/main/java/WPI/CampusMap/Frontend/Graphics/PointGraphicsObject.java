package WPI.CampusMap.Frontend.Graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Point.IPoint;

public abstract class PointGraphicsObject<M extends GraphicalMap> extends GraphicsObject<IPoint, M>
{
	private IPoint backendPoint;
	
	private boolean isOver;
	
	public PointGraphicsObject(IPoint backend, M owner)
	{
		super(backend, owner);
		this.backendPoint = backend;
	}
	
	@Override
	public float getScale() 
	{
		return 1.0f / getOwner().getZoomScale();
	}
	
	@Override
	public int getDrawBatch() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Coord getWorldPosition() 
	{
		return getRepresentedObject().getCoord();
	}

	@Override
	public void onDraw(Graphics2D graphics) 
	{
		Ellipse2D ellipse = new Ellipse2D.Float(-5, -5, 10, 10);
		graphics.fill(ellipse);
	}
	
	@Override
	public Color getColor()
	{
		return isOver ? Color.orange : Color.red;
	}

	@Override
	public void onMouseOver(RealMouseEvent e)
	{
		isOver = true;
	}

	@Override
	public void onMouseLeave(RealMouseEvent e)
	{
		isOver = false;
	}

	@Override
	public void onMouseMove(RealMouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseClick(RealMouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseDrag(RealMouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMouseOver(RealMouseEvent e) {
		// TODO Auto-generated method stub
		Coord mouseCoord = e.getScreenCoord();
		Coord screenPosition = getOwner().getScreenFromWorld(backendPoint.getCoord());
		float distance = mouseCoord.distance(screenPosition);
		
		return distance <= getMouseHoverDistance();
	}
	
	protected float getMouseHoverDistance()
	{
		return 7.0f;
	}
}
