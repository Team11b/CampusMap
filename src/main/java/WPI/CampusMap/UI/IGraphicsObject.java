package WPI.CampusMap.UI;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public interface IGraphicsObject
{
	/**
	 * This number determines what drawing batch to draw this object in. Lower numbers are earlier.
	 * @return The drawing batch to draw this object in.
	 */
	int getDrawBatch();
	
	/**
	 * Is called when this object should be drawn.
	 * @param graphics The graphics to draw this object with.
	 */
	void onDraw(Graphics2D graphics);
	
	/**
	 * Called when the mouse hovers over this object.
	 * @param e The mouse event when the mouse started hovering over this object.
	 */
	void onMouseOver(MouseEvent e);
	
	/**
	 * Called when the mouse leaves this object.
	 * @param e The mouse event when the mouse left this object.
	 */
	void onMouseLeave(MouseEvent e);
	
	/**
	 * Called when the mouse moves over this object.
	 * @param e The mouse event when the mouse moved.
	 */
	void onMouseMove(MouseEvent e);
	
	/**
	 * Called when the mouse clicks this object.
	 * @param e The mouse event when
	 */
	void onMouseClick(MouseEvent e);
	
	/**
	 * Called when the mouse drags this object.
	 * @param e The mouse event to drag this object
	 */
	void onMouseDrag(MouseEvent e);
	
	/**
	 * Checks to see if the mouse is over the graphics object.
	 * @return
	 */
	boolean isMouseOver(MouseEvent e);
	
	/**
	 * The object that this graphics object represents.
	 * @return The object that this graphics object represents. If null then this graphics object will be deleted.
	 */
	Object getRepresentedObject();
}
