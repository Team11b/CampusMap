package WPI.CampusMap.Frontend.Graphics.Dev;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Hashtable;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Pair.UnorderedPair;
import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Frontend.Dev.EditorToolMode;
import WPI.CampusMap.Frontend.Graphics.GraphicsObject;
import WPI.CampusMap.Frontend.Graphics.RealMouseEvent;
import WPI.CampusMap.Frontend.UI.DevMode;

/**
 * A visual representation of an edge between two s.
 * @author Benny
 *
 */
public class DevEdgeGraphicsObject extends GraphicsObject<UnorderedPair<IPoint, IPoint>, DevGraphicalMap>
{
	/**
	 * Creates a unique edge on the current map.
	 * @param p1 The first  to create an edge between.
	 * @param p2 The second  to create an edge between.
	 * @param owner The graphical map owner.
	 * @return The graphical edge that was either created or already exist.
	 */
	public static DevEdgeGraphicsObject createGraphicsEdge(IPoint p1, IPoint p2, DevGraphicalMap owner)
	{
		return owner.getGraphicalEdge(p1, p2);
	}
	
	private UnorderedPair<IPoint, IPoint> edge;
	
	private boolean isOver;
	
	protected DevEdgeGraphicsObject(IPoint p1, IPoint p2, DevGraphicalMap owner) 
	{
		super(new UnorderedPair<IPoint, IPoint>(p1, p2), owner);
		edge = getRepresentedObject();
	}

	@Override
	public int getDrawBatch() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public void onDraw(Graphics2D graphics)
	{
		Coord screenCoord1 = getOwner().getRenderFromWorld(edge.getA().getCoord());
		Coord screenCoord2 = getOwner().getRenderFromWorld(edge.getB().getCoord());
		
		graphics.setStroke(new BasicStroke(3));
		graphics.drawLine((int)screenCoord1.getX(), (int)screenCoord1.getY(), (int)screenCoord2.getX(), (int)screenCoord2.getY());
		graphics.setStroke(new BasicStroke(1));
	}
	
	@Override
	public void onDeleted()
	{
		getOwner().unregisterGraphicalEdge(this);
	}
	
	@Override
	public Color getColor()
	{
		return isOver ? Color.yellow : Color.gray;
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
	public void onMouseClick(RealMouseEvent e) 
	{
		if(getOwnerMode(DevMode.class).getCurrentToolMode() != EditorToolMode.DeleteEdge)
			return;
		
		delete();
	}

	@Override
	public void onMouseDrag(RealMouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMouseOver(RealMouseEvent e)
	{
		if(getOwnerMode(DevMode.class).getCurrentToolMode() != EditorToolMode.DeleteEdge)
			return false;
		
		Coord screenCoord1 = getOwner().getRenderFromWorld(edge.getA().getCoord());
		Coord screenCoord2 = getOwner().getRenderFromWorld(edge.getB().getCoord());
		
		Line2D.Float line = new Line2D.Float(screenCoord1.getX(), screenCoord1.getY(), screenCoord2.getX(), screenCoord2.getY());
		
		return true;//line.ptSegDist(e.getX(), e.getY()) <= 5.0f;
	}

	@Override
	public Coord getWorldPosition() 
	{
		return new Coord(0, 0);
	}
}
