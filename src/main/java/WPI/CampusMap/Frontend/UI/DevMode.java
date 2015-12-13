package WPI.CampusMap.Frontend.UI;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import WPI.CampusMap.Frontend.Dev.EditorToolMode;
import WPI.CampusMap.Frontend.Graphics.Dev.DevGraphicalMap;
import WPI.CampusMap.Frontend.Graphics.Dev.DevPointGraphicsObject;


public class DevMode extends UIMode 
{	
	private DevGraphicalMap graphicsMap;
	
	private EditorToolMode currentToolMode;
	
	private DevPointGraphicsObject selectedPoint;

	
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
	
	public void setSelectedPoint(DevPointGraphicsObject point)
	{
		selectedPoint = point;
	}
	
	public DevPointGraphicsObject getSelectedPoint()
	{
		return selectedPoint;
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
