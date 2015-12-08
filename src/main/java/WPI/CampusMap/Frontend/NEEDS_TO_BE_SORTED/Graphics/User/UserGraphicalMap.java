package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.User;

import java.util.HashSet;
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
		for(IPoint p : getMap().getAllPoints())
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
	
	/**
	 * Sets a section to be shown.
	 * @param section The section to be shown.
	 */
	public void setShownSection(Section section)
	{
		for(UserPathGraphicsObject graphicalSection : getObjectsOfType(UserPathGraphicsObject.class))
		{
			graphicalSection.setVisible(graphicalSection.getRepresentedObject() == section);
		}
	}
	
	/**
	 * Sets multiple sections to be shown.
	 * @param sections The sections to be shown.
	 */
	public void setShownSections(Section[] sections)
	{
		HashSet<Section> sectionsSet = new HashSet<>();
		
		for(Section section : sections)
		{
			sectionsSet.add(section);
		}
		
		for(UserPathGraphicsObject graphicalSection : getObjectsOfType(UserPathGraphicsObject.class))
		{
			graphicalSection.setVisible(sectionsSet.contains(graphicalSection.getRepresentedObject()));
		}
	}

	@Override
	public void unload()
	{
	}
}
