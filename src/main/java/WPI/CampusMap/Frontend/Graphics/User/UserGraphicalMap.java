package WPI.CampusMap.Frontend.Graphics.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Backend.PathPlanning.Path.Section;
import WPI.CampusMap.Frontend.Graphics.GraphicalMap;
import WPI.CampusMap.Frontend.UI.UserModeClass;

public class UserGraphicalMap extends GraphicalMap
{
	HashMap<IPoint, UserPointGraphicsObject> allPointsInMap = new HashMap<IPoint,UserPointGraphicsObject>();

	public UserGraphicalMap(String map, UserModeClass mode)
	{
		super(map, mode);
	}
	
	@Override
	public void spawnMap()
	{
		for(IPoint p : getMap().getAllPoints())
		{
			
			UserPointGraphicsObject toAdd = new UserPointGraphicsObject((RealPoint) p, this);
			allPointsInMap.put(p,toAdd);
		}
		
		setPathSections(getMode(UserModeClass.class).getRoutedPath());
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
		
		for(IPoint key : allPointsInMap.keySet()){
				allPointsInMap.get(key).setUnselected();
		}
		
		for(IPoint toShow : section){
			allPointsInMap.get(toShow).setSelected();
		}
	}
	
	public Section getShownSection(){
		for(UserPathGraphicsObject graphicalSection : getObjectsOfType(UserPathGraphicsObject.class))
		{
			if(graphicalSection.getIsSelected()){
				return graphicalSection.getRepresentedObject();
			}
		}
		return null;
	}
	
	public void setShownNode(IPoint point){
		System.out.println(point);
		allPointsInMap.get(point).setCurrent();
	}
	
	public void clearRoute(){
		for(IPoint key : allPointsInMap.keySet()){
			allPointsInMap.get(key).setUnselected();
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
