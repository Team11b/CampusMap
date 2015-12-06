package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics;

import java.awt.Color;
import java.awt.Graphics2D;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Point.Point;

public abstract class PointGraphicsObject<M extends GraphicalMap> extends GraphicsObject<Point, M>
{
	private Point backendPoint;
	
	private boolean isOver;
	
	public PointGraphicsObject(Point backend, M owner)
	{
		super(backend, owner);
		this.backendPoint = backend;
	}
	
	@Override
	public int getDrawBatch() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onDraw(Graphics2D graphics) 
	{
		Coord screenPoint = getOwner().getScreenCoord(backendPoint.getCoord().getX(), backendPoint.getCoord().getY());
		final float ovalSize = 10;
		
		graphics.fillOval((int)(screenPoint.getX() - ovalSize * 0.5f), (int)(screenPoint.getY() - ovalSize * 0.5f), (int)ovalSize, (int)ovalSize);
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
		Coord mouseCoord = new Coord(e.getX(), e.getY());
		Coord screenPosition = getOwner().getScreenCoord(backendPoint.getCoord());
		return mouseCoord.distance(screenPosition) <= 5;
	}
}
