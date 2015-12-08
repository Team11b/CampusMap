package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.User;

import java.util.Hashtable;
import java.util.LinkedList;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Map.IMap;
import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Backend.Core.Ref.Ref;
import WPI.CampusMap.Backend.PathPlanning.Path.Section;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.GraphicalMap;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.GraphicsObject;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.RealMouseEvent;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI.UserMode;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI_OLD.MapPanel;

public class UserGraphicalMap extends GraphicalMap
{	
	public UserGraphicalMap(String map, UserMode mode)
	{
		super(map, mode);		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void spawnMap()
	{
		for(IPoint p : map.getAllPoints())
		{
			new UserPointGraphicsObject((RealPoint) p, this);
		}
	}
	
	public void setPathSections(LinkedList<Section> pathSections)
	{
		for(Section section : pathSections)
		{
			new UserPathGraphicsObject(section, this);
		}
	}

	@Override
	public void unload()
	{
	}
}
