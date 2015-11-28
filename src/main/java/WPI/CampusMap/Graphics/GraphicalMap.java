package WPI.CampusMap.Graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.renderable.RenderableImageOp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;

import WPI.CampusMap.Backend.Coord;
import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.UI.MapPanel;

public abstract class GraphicalMap
{
	private ArrayList<GraphicsObject<?, ?>> batchList = new ArrayList<>();
	private LinkedList<GraphicsObject<?, ?>> deletedList = new LinkedList<>();
	
	private Map map;
	private MapPanel panel;
	
	private GraphicsObject<?, ?> over;
	
	private AffineTransform transform;
	
	public GraphicalMap(Map map, MapPanel panel)
	{
		this.panel = panel;
		this.map = map;
		transform = AffineTransform.getScaleInstance(1, 1);
		this.spawnMap(map);
	}
	
	public final void onDraw(Graphics2D graphics)
	{
		graphics.clearRect(0, 0, panel.getWidth(), panel.getWidth());
		
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		graphics.setTransform(transform);

		if (map == null)
			return;

		graphics.setColor(Color.white);
		graphics.drawImage(map.getLoadedImage().getImage(), 0, 0, panel.getWidth(), panel.getHeight(), null);
		
		batchList.sort(new GraphicsBatchComparator());
		for(int i = 0; i < batchList.size(); i++)
		{
			GraphicsObject<?, ?> go = batchList.get(i);
			if(go.getRepresentedObject() == null || go.isDelelted())
			{
				batchList.remove(i);
				go.delete();
				go.onRemoved();
				
				i--;
			}
			else
			{
				graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, go.getAlpha()));
				graphics.setColor(go.getColor());
				go.onDraw(graphics);
			}
		}
	}
	
	public void addGraphicalObject(GraphicsObject<?, ?> go)
	{
		batchList.add(0, go);
	}
	
	protected void deleteGraphicalObject(GraphicsObject<?, ?> go)
	{
		deletedList.add(go);
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
	public final void mouseMove(MouseEvent e)
	{
		RealMouseEvent re = transformMouseEvent(e);
		if(onMouseMove(re))
			return;
		
		GraphicsObject<?, ?> lastOver = over;
		
		for(int i = batchList.size() - 1; i >= 0; i--)
		{
			GraphicsObject<?, ?> go = batchList.get(i);
			if(go.isMouseOver(re))
			{
				over = go;
				if(lastOver == over)
				{
					over.onMouseMove(re);
				}
				else if(lastOver != over)
				{
					if(lastOver != null)
						lastOver.onMouseLeave(re);
					over.onMouseOver(re);
				}
				
				return;
			}
		}
		
		if(lastOver != null)
		{
			lastOver.onMouseLeave(re);
			over = null;
		}
	}
	
	public final GraphicsObject<?, ?> getHoverObject()
	{
		return over;
	}
	
	public boolean onMouseMove(RealMouseEvent re)
	{
		return false;
	}
	
	public void mouseEnter(MouseEvent e)
	{
		//TODO: Implement
	}
	
	public final void mouseExit(MouseEvent e)
	{
		RealMouseEvent re = transformMouseEvent(e);
		if(onMouseExit(re))
			return;
		
		if(over != null)
		{
			over.onMouseLeave(re);
		}
	}
	
	public boolean onMouseExit(RealMouseEvent e)
	{
		return false;
	}
	
	public final void mouseClick(MouseEvent e)
	{
		RealMouseEvent re = transformMouseEvent(e);
		if(onMouseClick(re))
			return;
		
		if(over != null)
		{
			over.onMouseClick(re);
		}
	}
	
	public boolean onMouseClick(RealMouseEvent e)
	{
		return false;
	}
	
	public final void mouseDrag(MouseEvent e)
	{
		RealMouseEvent re = transformMouseEvent(e);
		if(onMouseDrag(re))
			return;
		
		if(over != null)
		{
			over.onMouseDrag(re);
		}
	}
	
	public boolean onMouseDrag(RealMouseEvent e)
	{
		return false;
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
	
	public final Coord getScreenCoord(Coord worldCoord)
	{
		return getScreenCoord(worldCoord.getX(), worldCoord.getY());
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
	
	private final RealMouseEvent transformMouseEvent(MouseEvent e)
	{
		Point2D.Float src = new Point2D.Float(e.getX(), e.getY());
		Point2D dst = null;
		try {
			dst = transform.inverseTransform(src, null);
		} catch (NoninvertibleTransformException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
		
		return new RealMouseEvent((float)dst.getX(), (float)dst.getY(), e.getButton(), e.isAltDown(), e.isControlDown(), e.isShiftDown());
	}
	
	private class GraphicsBatchComparator implements Comparator<GraphicsObject<?, ?>>
	{
		@Override
		public int compare(GraphicsObject<?, ?> arg0, GraphicsObject<?, ?> arg1) 
		{
			return arg0.getDrawBatch() - arg1.getDrawBatch();
		}
	}
}