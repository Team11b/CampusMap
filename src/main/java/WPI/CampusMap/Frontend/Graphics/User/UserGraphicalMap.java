package WPI.CampusMap.Frontend.Graphics.User;

import WPI.CampusMap.Backend.Core.Map;
import WPI.CampusMap.Backend.Core.Point.Point;
import WPI.CampusMap.Frontend.Graphics.GraphicalMap;
import WPI.CampusMap.Frontend.Graphics.PointGraphicsObject;
import WPI.CampusMap.Frontend.UI.MapPanel;

public class UserGraphicalMap extends GraphicalMap {

	public UserGraphicalMap(Map map, MapPanel panel) {
		super(map, panel);
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
