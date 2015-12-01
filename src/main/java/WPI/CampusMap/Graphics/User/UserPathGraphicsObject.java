package WPI.CampusMap.Graphics.User;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;

import WPI.CampusMap.Backend.Coord;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.Core.TypedRef;
import WPI.CampusMap.Graphics.GraphicsObject;
import WPI.CampusMap.Graphics.RealMouseEvent;
import WPI.CampusMap.PathPlanning.Node;
import WPI.CampusMap.PathPlanning.Path;

public class UserPathGraphicsObject extends GraphicsObject<Path, UserGraphicalMap>
{
	private static LinkedList<TypedRef<UserPathGraphicsObject>> pathObjects = new LinkedList<>();
	
	public static void deleteAll()
	{
		for(TypedRef<UserPathGraphicsObject> path : pathObjects)
		{
			path.getValue().delete();
			path.release();
		}
		
		pathObjects.clear();
	}
	
	private Path backendPath;
	
	public UserPathGraphicsObject(Path path, UserGraphicalMap owner)
	{
		super(owner);
		this.backendPath = path;
		pathObjects.add(new TypedRef<UserPathGraphicsObject>(this));
	}

	@Override
	public int getDrawBatch() 
	{
		return 1;
	}

	@Override
	public void onDraw(Graphics2D graphics)
	{
		graphics.setStroke(new BasicStroke(3.0f));
		
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
		return Color.magenta;
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
	public Path getRepresentedObject() 
	{
		return backendPath;
	}
}
