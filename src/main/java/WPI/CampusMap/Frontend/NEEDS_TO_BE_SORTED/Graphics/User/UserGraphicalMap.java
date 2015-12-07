package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.User;

import java.util.Hashtable;
import java.util.LinkedList;

import WPI.CampusMap.Backend.Core.Map.IMap;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Backend.Core.Ref.Ref;
import WPI.CampusMap.Backend.PathPlanning.Path;
import WPI.CampusMap.Backend.PathPlanning.Path.Section;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.GraphicalMap;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.GraphicsObject;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI.MapPanel;

public class UserGraphicalMap extends GraphicalMap
{
	private static Hashtable<String, UserGraphicalMap> loadedMaps = new Hashtable<>();
	
	public static UserGraphicalMap loadGraphicalMap(IMap map)
	{
		return loadGraphicalMap(map, null);
	}
	
	public static UserGraphicalMap loadGraphicalMap(IMap map, MapPanel panel)
	{
		UserGraphicalMap graphicalMap = loadedMaps.get(map.getName());
		if(graphicalMap == null)
		{
			graphicalMap = new UserGraphicalMap(map, panel);
			loadedMaps.put(map.getName(), graphicalMap);
		}
		else if(panel != null)
		{
			graphicalMap.setPanel(panel);
		}
		
		return graphicalMap;
	}
	
	private UserGraphicalMap(IMap map, MapPanel panel) {
		super(map, panel);		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void spawnMap(IMap map)
	{
		for(RealPoint p : map.getAllPoints())
		{
			new UserPointGraphicsObject(p, this);
		}
	}
	
	public void setPathSections(LinkedList<Section> pathSections)
	{
		for(Section section : pathSections)
		{
			UserPathGraphicsObject pathGraphics = new UserPathGraphicsObject(section, this);
			addGraphicalObject(pathGraphics);
		}
	}

	@Override
	public void unload()
	{
		if(Ref.getRefsTo(this) > 0)
			return;
		
		for(GraphicsObject<?, ?> obj : getObjects())
		{
			if(Ref.getRefsTo(obj) > 0)
			{
				return;
			}
		}
		
		loadedMaps.remove(getMap().getName());
	}
}
