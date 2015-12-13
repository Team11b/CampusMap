package WPI.CampusMap.Frontend.UI;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import WPI.CampusMap.Frontend.Dev.EditorToolMode;
import WPI.CampusMap.Frontend.Graphics.Dev.DevGraphicalMap;
import WPI.CampusMap.Frontend.Graphics.Dev.DevPointGraphicsObject;


public class DevMode extends UIMode 
{	
	private DevGraphicalMap graphicsMap;
	
	private EditorToolMode currentToolMode;
	
	private HashSet<DevPointGraphicsObject> selectedPoints = new HashSet<>();
	
	public DevMode(AppMainWindow window)
	{
		super(window);
		setSelect();
	}
	
	@Override
	public void onModeEntered()
	{		
	}
	
	@Override
	public void gotoPoint(String name) 
	{
	}
	
	public void setSelect()
	{		
		currentToolMode = EditorToolMode.None;
	}

	public void setPlace()
	{	
		currentToolMode = EditorToolMode.Point;					
	}
	
	public void setRemove()
	{
		currentToolMode = EditorToolMode.DeletePoint;		
	}
	
	public void setEdge()
	{				
		currentToolMode = EditorToolMode.Edge;			
	}
	
	public void setRemoveEdge()
	{
		currentToolMode = EditorToolMode.DeleteEdge;	
	}
	
	public void save()
	{
	}
	
	public EditorToolMode getCurrentToolMode()
	{
		return currentToolMode;
	}
	
	public void clearSelectedPoints()
	{
		selectedPoints.clear();
	}
	
	public DevPointGraphicsObject[] getSelectedPoints()
	{
		DevPointGraphicsObject[] array = new DevPointGraphicsObject[selectedPoints.size()];
		return selectedPoints.toArray(array);
	}
	
	public void setSelectedPoint(DevPointGraphicsObject point)
	{
		selectedPoints.clear();
		selectedPoints.add(point);
		
		point.onSelected();
	}
	
	public void addSelectedPoint(DevPointGraphicsObject point)
	{
		selectedPoints.add(point);
		
		point.onSelected();
	}
	
	public boolean isPointSelected(DevPointGraphicsObject point)
	{
		return selectedPoints.contains(point);
	}
	
	public int getSelectedPointCount()
	{
		return selectedPoints.size();
	}

	@Override
	public final void onDraw(Graphics2D graphics)
	{
		if(graphicsMap != null)
			graphicsMap.onDraw(graphics);	
	}

	@Override
	public void onMouseClickMap(MouseEvent e)
	{
		if(graphicsMap != null)
			graphicsMap.mouseClick(e);
	}

	@Override
	public void onMouseEnterMap(MouseEvent e) 
	{
		if(graphicsMap != null)
			graphicsMap.mouseEnter(e);
	}

	@Override
	public void onMouseLeaveMap(MouseEvent e) 
	{
		if(graphicsMap != null)
			graphicsMap.mouseExit(e);		
	}

	@Override
	public void onMouseMoveOverMap(MouseEvent e) {
		if(graphicsMap != null)
			graphicsMap.mouseMove(e);
	}

	@Override
	public void onMouseDraggedOverMap(MouseEvent e) {
		if(graphicsMap != null)		
			graphicsMap.mouseDrag(e);		
	}

	@Override
	public void loadMap(String mapName)
	{
		graphicsMap = new DevGraphicalMap(mapName, this);
		graphicsMap.spawnMap();
	}
}
