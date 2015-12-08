package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Map.IMap;
import WPI.CampusMap.Backend.Core.Map.Map;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI_OLD.AppUIObject;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI_OLD.MapPanel;

/**
 * A graphical map represents a container of graphical objects for a map. It is the equivalent of a map in the backend.
 * @author Benny
 *
 */
public abstract class GraphicalMap
{
	private ArrayList<GraphicsObject<?, ?>> batchList = new ArrayList<>();
	private Hashtable<Object, GraphicsObject<?, ?>> graphicsObjectLookup = new Hashtable<>();
	
	private IMap map;
	private MapPanel panel;
	
	private GraphicsObject<?, ?> over;
	
	private AffineTransform transform;
	
	@Deprecated
	public GraphicalMap(IMap map, MapPanel panel)
	{
		this.panel = panel;
		this.map = map;
		transform = AffineTransform.getScaleInstance(1, 1);
		this.spawnMap(map);
	}
	
	/**
	 * Creates a new graphical map from a backend map.
	 * @param map The backend map to create from.
	 */
	public GraphicalMap(IMap map) 
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	@Deprecated
	public void setPanel(MapPanel panel)
	{
		this.panel = panel;
	}
	
	@Deprecated
	public MapPanel getPanel()
	{
		return this.panel;
	}
	
	/**
	 * Get an array of all graphical objects that have been created on this map.
	 * @return An array of all objects created on this map.
	 */
	public GraphicsObject<?, ?>[] getObjects()
	{
		synchronized (this)
		{
			GraphicsObject<?, ?>[] objs = new GraphicsObject<?, ?>[graphicsObjectLookup.size()];
			return graphicsObjectLookup.values().toArray(objs);
		}
	}
	
	/**
	 * Get a specific object by its backend object representation.
	 * @param representedObject The object that represents the graphical object.
	 * @return The graphical object that corresponds with this data, or null if not found.
	 */
	public GraphicsObject<?, ?> getObject(Object representedObject)
	{
		synchronized (this)
		{
			return graphicsObjectLookup.get(representedObject);
		}
	}
	
	/**
	 * Called every time the map is drawn.
	 * @param graphics The graphics to use to draw.
	 */
	public final void onDraw(Graphics2D graphics)
	{		
		graphics.clearRect(0, 0, panel.getWidth(), panel.getWidth());
		
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		graphics.setTransform(transform);

		if (map == null)
			return;
		
		synchronized (this)
		{
			graphics.setColor(Color.white);
			graphics.drawImage(map.getLoadedImage().getImage(), 0, 0, panel.getWidth(), panel.getHeight(), null);
			
			batchList.sort(new GraphicsBatchComparator());
			for(int i = 0; i < batchList.size(); i++)
			{
				GraphicsObject<?, ?> go = batchList.get(i);
				if(go.isDelelted())
				{
					batchList.remove(i);
					
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
	}
	
	/**
	 * Registers a graphical object with this graphical map.
	 * @param go The graphical object to register.
	 */
	protected void addGraphicalObject(GraphicsObject<?, ?> go)
	{
		synchronized (this)
		{
			batchList.add(0, go);
			graphicsObjectLookup.put(go.getRepresentedObject(), go);
		}
	}
	
	/**
	 * Deletes a registered object from this map. This method should NOT be used, instead use GraphicsObject.delete.
	 * @param go The graphical object to delete.
	 */
	protected void deleteGraphicalObject(GraphicsObject<?, ?> go)
	{
		synchronized (this)
		{
			graphicsObjectLookup.remove(go.getRepresentedObject());
			go.onDeleted();
		}
	}
	
	/**
	 * Called when a map should be converted into graphics objects.
	 * @param map The map to read in.
	 */
	@Deprecated
	public abstract void spawnMap(IMap map);
	
	/**
	 * Called to unload this graphical map.
	 */
	public void unload()
	{
	}
	
	/**
	 * Gets the graphics object that the mouse is hovering over.
	 * @return The graphics object that the mouse is hovering over.
	 */
	public final GraphicsObject<?, ?> getHoverObject()
	{
		return over;
	}
	
	/**
	 * Called when the mouse moves over the graphics map.
	 * @param e The mouse movement event.
	 */
	public final void mouseMove(MouseEvent e)
	{
		synchronized (this)
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
	}
	
	/**
	 * Called when the mouse moves over the graphical map.
	 * @param e The mouse event to cause the event.
	 * @return True to block the call continuing to the hover object.
	 */
	public boolean onMouseMove(RealMouseEvent e)
	{
		return false;
	}
	
	public final void mouseEnter(MouseEvent e)
	{
		//TODO: Implement
	}
	
	public final void mouseExit(MouseEvent e)
	{
		synchronized (this)
		{
			RealMouseEvent re = transformMouseEvent(e);
			if(onMouseExit(re))
				return;
			
			if(over != null)
			{
				over.onMouseLeave(re);
			}
		}
	}
	
	/**
	 * Called when the mouse exits the graphical map.
	 * @param e The mouse event to cause the event.
	 * @return True to block the call continuing to the hover object.
	 */
	public boolean onMouseExit(RealMouseEvent e)
	{
		return false;
	}
	
	public final void mouseClick(MouseEvent e)
	{
		synchronized (this)
		{
			RealMouseEvent re = transformMouseEvent(e);
			if(onMouseClick(re))
				return;
			
			if(over != null)
			{
				over.onMouseClick(re);
			}
		}
	}
	
	/**
	 * Called when the mouse clicks the graphical map.
	 * @param e The mouse event to cause the event.
	 * @return True to block the call continuing to the hover object.
	 */
	public boolean onMouseClick(RealMouseEvent e)
	{
		return false;
	}
	
	public final void mouseDrag(MouseEvent e)
	{
		synchronized (this)
		{
			RealMouseEvent re = transformMouseEvent(e);
			if(onMouseDrag(re))
				return;
			
			if(over != null)
			{
				over.onMouseDrag(re);
			}	
		}
	}
	
	/**
	 * Called when the mouse drags over the graphical map.
	 * @param e The mouse event to cause the event.
	 * @return True to block the call continuing to the hover object.
	 */
	public boolean onMouseDrag(RealMouseEvent e)
	{
		return false;
	}
	
	public final IMap getMap()
	{
		return map;
	}
	
	/**
	 * Converts screen coordinates to world coordinates.
	 * @param screenX The x screen coordinate.
	 * @param screenY The y screen coordinate.
	 * @return The world coordinates.
	 */
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
	
	/**
	 * Converts world coordinates to screen coordinates.
	 * @param worldCoord The world coordinates.
	 * @return The screen coordinates.
	 */
	public final Coord getScreenCoord(Coord worldCoord)
	{
		return getScreenCoord(worldCoord.getX(), worldCoord.getY());
	}
	
	/**
	 * Converts world coordinates to screen coordinates.
	 * @param worldX The x world coordinates.
	 * @param worldY The y world coordinates.
	 * @return The screen coordinates.
	 */
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
	
	protected final void updateReferencedObject(Object oldReference, Object newReference)
	{
		synchronized (this)
		{
			GraphicsObject<?,?> gfxObj = graphicsObjectLookup.get(oldReference);
			graphicsObjectLookup.remove(oldReference);
			System.out.println("gfxObj: "+gfxObj);
			graphicsObjectLookup.put(newReference, gfxObj);
		}
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


	@Deprecated
	public final AppUIObject getUI()	{
		return panel.uiObject;
	}


	
	
}
