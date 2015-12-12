package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics;

import java.awt.Color;
import java.awt.Graphics2D;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;

/**
 * An object that represents a piece of data graphically.
 * @author Benny
 *
 * @param <R> The type of data to represent.
 * @param <M> The graphical map container type that holds this object.
 */
public abstract class GraphicsObject<R, M extends GraphicalMap>
{
	private M graphicsOwner;
	
	private R representedObject;
	
	private boolean visible;
	
	private boolean deleted;
	
	/**
	 * Creates a new graphical object.
	 * @param representedObject The represented object.
	 * @param owner The owning map.
	 */
	public GraphicsObject(R representedObject, M owner)
	{
		this.graphicsOwner = owner;
		this.representedObject = representedObject;
		owner.addGraphicalObject(this);
		visible = true;
	}
	
	/**
	 * Gets the graphical map that owns this object.
	 * @return The owner of this graphics object.
	 */
	public M getOwner()
	{
		return graphicsOwner;
	}
	
	public <T> T getOwnerMode(Class<T> type)
	{
		return graphicsOwner.getMode(type);
	}
	
	public boolean isVisible()
	{
		return visible;
	}
	
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	
	/**
	 * Checks to see if this object is deleted.
	 * @return True if deleted, false otherwise.
	 */
	public boolean isDelelted()
	{
		return deleted;
	}
	
	/**
	 * Deletes this object from the graphical wrapper.
	 */
	public void delete()
	{
		deleted = true;
		getOwner().deleteGraphicalObject(this);
	}
	
	/**
	 * This number determines what drawing batch to draw this object in. Lower numbers are earlier.
	 * @return The drawing batch to draw this object in.
	 */
	public abstract int getDrawBatch();
	
	public abstract Coord getWorldPosition();
	
	/**
	 * Is called when this object should be drawn.
	 * @param graphics The graphics to draw this object with.
	 */
	public abstract void onDraw(Graphics2D graphics);
	
	public Color getColor()
	{
		return Color.white;
	}
	
	/**
	 * Gets the alpha channel to render this object with.
	 * @return The alpha channel value between 0-1 to render this object with.
	 */
	public float getAlpha()
	{
		return 1.0f;
	}
	
	/**
	 * Called when the graphics object is removed from its owner.
	 */
	public void onDeleted()
	{
		
	}
	
	/**
	 * Called when this object is unloaded from the graphical wrapper.
	 */
	public void onUnloaded()
	{
		
	}
	
	/**
	 * Called when the mouse hovers over this object.
	 * @param e The mouse event when the mouse started hovering over this object.
	 */
	public abstract void onMouseOver(RealMouseEvent e);
	
	/**
	 * Called when the mouse leaves this object.
	 * @param e The mouse event when the mouse left this object.
	 */
	public abstract void onMouseLeave(RealMouseEvent e);
	
	/**
	 * Called when the mouse moves over this object.
	 * @param e The mouse event when the mouse moved.
	 */
	public abstract void onMouseMove(RealMouseEvent e);
	
	/**
	 * Called when the mouse clicks this object.
	 * @param e The mouse event when
	 */
	public abstract void onMouseClick(RealMouseEvent e);
	
	/**
	 * Called when the mouse drags this object.
	 * @param e The mouse event to drag this object
	 */
	public abstract void onMouseDrag(RealMouseEvent e);
	
	/**
	 * Checks to see if the mouse is over the graphics object.
	 * @param e The real mouse event
	 * @return whether the mouse is over the graphics object
	 */
	public abstract boolean isMouseOver(RealMouseEvent e);
	
	/**
	 * The object that this graphics object represents.
	 * @return The object that this graphics object represents. If null then this graphics object will be deleted.
	 */
	public final R getRepresentedObject()
	{
		return representedObject;
	}
	
	/**
	 * Changes the represented object.
	 * @param newObject The new object to represent.
	 */
	public final void setRepresentedObject(R newObject)
	{
		getOwner().updateReferencedObject(representedObject, newObject);
		representedObject = newObject;
		onRepresentedObjectChanged();
	}
	
	/**
	 * Called when the represented object changes.
	 */
	protected void onRepresentedObjectChanged()
	{
		
	}
}
