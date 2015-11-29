package WPI.CampusMap.Graphics.User;

import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.Graphics.GraphicalMap;
import WPI.CampusMap.Graphics.PointGraphicsObject;
import WPI.CampusMap.UI.MapPanel;

public class UserGraphicalMap extends GraphicalMap {

	public MapPanel MP;
	
	public UserGraphicalMap(Map map, MapPanel panel) {
		super(map, panel);
		this.MP = panel;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void spawnMap(Map map)
	{
		for(Point p : map.getAllPoints().values())
		{
			addGraphicalObject(new UserPointGraphicsObject(p, this));
		}
	}

}
