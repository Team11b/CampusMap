package WPI.CampusMap.Graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;

import WPI.CampusMap.Backend.Coord;
import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.UI.MapPanel;

public abstract class GraphicalMap
{
	private ArrayList<GraphicsObject> batchList = new ArrayList<>();
	private Hashtable<Object, GraphicsObject> drawables = new Hashtable<>();
	
	private Map map;
	private MapPanel panel;
	
	private GraphicsObject over;
	
	public GraphicalMap(Map map, MapPanel panel)
	{
		this.panel = panel;
		this.map = map;
		this.spawnMap(map);
	}
	
	public final void onDraw(Graphics2D graphics)
	{
		graphics.clearRect(0, 0, panel.getWidth(), panel.getWidth());

		if (map == null)
			return;

		graphics.setColor(Color.white);
		graphics.drawImage(map.getLoadedImage().getImage(), 0, 0, panel.getWidth(), panel.getHeight(), null);
		
		batchList.sort(new GraphicsBatchComparator());
		for(int i = 0; i < batchList.size(); i++)
		{
			GraphicsObject go = batchList.get(i);
			if(go.getRepresentedObject() == null)
			{
				
			}
			else
			{
				go.onDraw(graphics);
			}
		}
	}
	
	public void addGraphicalObject(GraphicsObject go)
	{
		drawables.put(go.getRepresentedObject(), go);
		batchList.add(0, go);
	}
	
	/**
	 * Called when a map should be converted into graphics objects.
	 * @param map The map to read in.
	 */
	public abstract void spawnMap(Map map);
	
	/**
	 * Called when the mouse moves over the graphics map.
	 * @param e The mouse movement event.
	 */
	public void onMouseMove(MouseEvent e)
	{
		GraphicsObject lastOver = over;
		
		for(int i = batchList.size() - 1; i >= 0; i--)
		{
			GraphicsObject go = batchList.get(i);
			if(go.isMouseOver(e))
			{
				over = go;
				if(lastOver == over)
				{
					over.onMouseMove(e);
				}
				else if(lastOver != over)
				{
					if(lastOver != null)
						lastOver.onMouseLeave(e);
					over.onMouseOver(e);
				}
			}
		}
		
		if(lastOver != null)
		{
			lastOver.onMouseLeave(e);
			over = null;
		}
	}
	
	public void onMouseEnter(MouseEvent e)
	{
	}
	
	public void onMouseExit(MouseEvent e)
	{
		if(over != null)
		{
			over.onMouseLeave(e);
		}
	}
	
	public void onMouseClick(MouseEvent e)
	{
		if(over != null)
		{
			over.onMouseClick(e);
		}
	}
	
	public void onMouseDrag(MouseEvent e)
	{
		if(over != null)
		{
			over.onMouseDrag(e);
		}
	}
	
	public final Map getMap()
	{
		return map;
	}
	
	public final Coord getWorldCoord(int screenX, int screenY)
	{
		float imageX = (float)screenX / (float)panel.getWidth() * map.getLoadedImage().getIconWidth();
		float imageY = (float)screenY / (float)panel.getHeight() * map.getLoadedImage().getIconHeight();

		float inchesX = imageX / 72.0f;
		float inchesY = imageY / 72.0f;

		float feetX = inchesX / map.getScale();
		float feetY = inchesY / map.getScale();

		return new Coord(feetX, feetY);
	}
	
	public final Coord getScreenCoord(float worldX, float worldY)
	{
		float inchesX = worldX * map.getScale();
		float inchesY = worldY * map.getScale();

		float imageX = inchesX * 72.0f;
		float imageY = inchesY * 72.0f;

		float screenX = imageX / map.getLoadedImage().getIconWidth() * panel.getWidth();
		float screenY = imageY / map.getLoadedImage().getIconHeight() * panel.getHeight();

		return new Coord(screenX, screenY);
	}
	
	private class GraphicsBatchComparator implements Comparator<GraphicsObject>
	{
		@Override
		public int compare(GraphicsObject arg0, GraphicsObject arg1) 
		{
			return arg0.getDrawBatch() - arg1.getDrawBatch();
		}
	}
}
