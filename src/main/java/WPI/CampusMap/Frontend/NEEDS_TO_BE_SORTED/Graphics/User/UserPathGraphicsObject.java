package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.User;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Point.Point;
import WPI.CampusMap.Backend.Core.Ref.TypedRef;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.Path.Path;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.PathFinding.Node.Node;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.GraphicsObject;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.RealMouseEvent;

public class UserPathGraphicsObject extends GraphicsObject<Path, UserGraphicalMap>
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
	
	private Path backendPath;
	
	public UserPathGraphicsObject(Path path, UserGraphicalMap owner)
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
		
		ArrayList<Node> nodes = backendPath.getPath();
		for(int i = 1; i < nodes.size(); i++)
		{
			Node a = nodes.get(i - 1);
			Node b = nodes.get(i);
			
			Point pa = a.getPoint();
			Point pb = b.getPoint();
			
			Coord screenA = getOwner().getScreenCoord(pa.getCoord());
			Coord screenB = getOwner().getScreenCoord(pb.getCoord());
			
			graphics.drawLine((int)screenA.getX(), (int)screenA.getY(), (int)screenB.getX(), (int)screenB.getY());
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
