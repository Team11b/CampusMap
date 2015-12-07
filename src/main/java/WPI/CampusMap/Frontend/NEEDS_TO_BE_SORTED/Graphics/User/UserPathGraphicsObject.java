package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.User;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.Core.Ref.TypedRef;
import WPI.CampusMap.Backend.PathPlanning.Node;
import WPI.CampusMap.Backend.PathPlanning.Path.Section;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.GraphicsObject;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.RealMouseEvent;

public class UserPathGraphicsObject extends GraphicsObject<Section, UserGraphicalMap>
{
	private static LinkedList<TypedRef<UserPathGraphicsObject>> pathObjects = new LinkedList<>();
	
	public static void deleteAll()
	{
		for(TypedRef<UserPathGraphicsObject> path : pathObjects)
		{
			path.getValue().delete();
		}
		
		pathObjects.clear();
	}
	
	private Section backendPath;
	
	public UserPathGraphicsObject(Section path, UserGraphicalMap owner)
	{
		super(path, owner);
		this.backendPath = path;
		pathObjects.add(new TypedRef<UserPathGraphicsObject>(this));
	}
	
	@Override
	public void onDeleted()
	{
		for(Iterator<TypedRef<UserPathGraphicsObject>> itr = pathObjects.iterator(); itr.hasNext();)
		{
			TypedRef<UserPathGraphicsObject> ref = itr.next();
			if(ref.getValue() == this)
			{
				itr.remove();
				break;
			}
		}
	}

	@Override
	public int getDrawBatch() 
	{
		return 1;
	}

	@Override
	public void onDraw(Graphics2D graphics)
	{
		BasicStroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		graphics.setStroke(dashed);
		
		IPoint last = null;
		for(IPoint point: backendPath)
		{
			if(last != null){
				Coord screenA = getOwner().getScreenCoord(point.getCoord());
				Coord screenB = getOwner().getScreenCoord(last.getCoord());
				graphics.drawLine((int)screenA.getX(), (int)screenA.getY(), (int)screenB.getX(), (int)screenB.getY());
			}
			last = point;
		}
		
		graphics.setStroke(new BasicStroke(1.0f));
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
}
