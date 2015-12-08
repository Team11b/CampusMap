package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.User;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.Core.Ref.TypedRef;
import WPI.CampusMap.Backend.PathPlanning.Path.Section;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.GraphicsObject;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.RealMouseEvent;

public class UserPathGraphicsObject extends GraphicsObject<Section, UserGraphicalMap>
{
	private float phase;
	
	public UserPathGraphicsObject(Section path, UserGraphicalMap owner)
	{
		super(path, owner);
		setVisible(false);
	}
	
	@Override
	public void onDeleted()
	{
	}

	@Override
	public int getDrawBatch() 
	{
		return 1;
	}

	@Override
	public void onDraw(Graphics2D graphics)
	{
		graphics.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.0f, new float[]{10.0f}, phase));
		phase += 0.33f;
		
		LinkedList<IPoint> points = getRepresentedObject().getPoints();
		
		for(int i = 1; i < points.size(); i++)
		{
			IPoint currentPoint = points.get(i);
			IPoint lastPoint = points.get(i - 1);
			
			Coord currentRenderCoord = getOwner().getRenderFromWorld(currentPoint.getCoord());
			Coord lastRenderCoord = getOwner().getRenderFromWorld(lastPoint.getCoord());
			
			Line2D.Float line = new Line2D.Float(currentRenderCoord.getX(), currentRenderCoord.getY(), lastRenderCoord.getX(), lastRenderCoord.getY());
			
			graphics.draw(line);
		}
		
		graphics.setStroke(new BasicStroke(1));
	}
	
	@Override
	public Color getColor() 
	{
		return new Color(204, 0, 204);
	}

	@Override
	public void onMouseOver(RealMouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseLeave(RealMouseEvent e) {
		// TODO Auto-generated method stub
		
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
		return false;
	}

	@Override
	public Coord getWorldPosition() 
	{
		return new Coord(0, 0);
	}
}
