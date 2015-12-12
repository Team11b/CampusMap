package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.User;

import java.util.HashSet;
import java.util.LinkedList;

import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Backend.PathPlanning.Path.Section;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.GraphicalMap;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI.UserMode;

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
		
		setPathSections(getMode(UserMode.class).getRoutedPath());
	}
	
	/**
	 * Refreshes the routed path sections to a new list of path sections.
	 * @param pathSections The new path sections.
	 */
	public void setPathSections(LinkedList<Section> pathSections)
	{
		for(UserPathGraphicsObject graphicalSection : getObjectsOfType(UserPathGraphicsObject.class))
		{
			graphicalSection.delete();
		}
		
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
			graphicalSection.setSelected(graphicalSection.getRepresentedObject() == section);
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
