package WPI.CampusMap.Frontend.Graphics.User;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import WPI.CampusMap.Backend.Core.Coord;
import WPI.CampusMap.Backend.Core.Point.Point;
import WPI.CampusMap.Backend.PathPlanning.Node;
import WPI.CampusMap.Backend.PathPlanning.Path.Path;
import WPI.CampusMap.Frontend.Graphics.GraphicsObject;
import WPI.CampusMap.Frontend.Graphics.RealMouseEvent;

public class UserPathGraphicsObject extends GraphicsObject<Path, UserGraphicalMap>
{
	private Path backendPath;
	
	public UserPathGraphicsObject(Path path, UserGraphicalMap owner)
	{
		super(owner);
		this.backendPath = path;
		instance = this;
	}

	@Override
	public int getDrawBatch() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void onDraw(Graphics2D graphics)
	{
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
		return instance == this ? backendPath : null;
	}
	
	private static UserPathGraphicsObject instance;
}
