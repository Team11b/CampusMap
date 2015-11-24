package WPI.CampusMap.Graphics;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class GraphicsObject
{
	private GraphicalMap graphicsOwner;
	
	public GraphicsObject(GraphicalMap owner)
	{
		this.graphicsOwner = owner;
	}
	
	public GraphicalMap getOwner()
	{
		return graphicsOwner;
	}
	
	/**
	 * This number determines what drawing batch to draw this object in. Lower numbers are earlier.
	 * @return The drawing batch to draw this object in.
	 */
	public abstract int getDrawBatch();
	
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
	 * @return
	 */
	public abstract boolean isMouseOver(RealMouseEvent e);
	
	/**
	 * The object that this graphics object represents.
	 * @return The object that this graphics object represents. If null then this graphics object will be deleted.
	 */
	public abstract Object getRepresentedObject();
}
