package WPI.CampusMap.Frontend.Graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Map.IMap;
import WPI.CampusMap.Frontend.UI.UIMode;

/**
 * A graphical map represents a container of graphical objects for a map. It is
 * the equivalent of a map in the backend.
 * 
 * @author Benny
 *
 */
public abstract class GraphicalMap {
	private ArrayList<GraphicsObject<?, ?>> batchList = new ArrayList<>();
	private Hashtable<Object, GraphicsObject<?, ?>> graphicsObjectLookup = new Hashtable<>();

	private IMap map;

	private Rectangle lastClip;

	private GraphicsObject<?, ?> over;

	private AffineTransform transform;

	private Coord cameraPosition = new Coord(0, 0);

	private UIMode mode;

	/**
	 * Creates a new graphical map from a backend map.
	 * 
	 * @param map
	 *            The backend map to create from.
	 * @param mode
	 *            The ui mode that is the owner of this.
	 */
	public GraphicalMap(String map, UIMode mode) {
		this.mode = mode;
		this.map = AllMaps.getInstance().getMap(map);

		cameraPosition = new Coord(this.map.getLoadedImage().getIconWidth() * 0.5f,
				this.map.getLoadedImage().getIconHeight() * 0.5f);
	}

	/**
	 * Get an array of all graphical objects that have been created on this map.
	 * 
	 * @return An array of all objects created on this map.
	 */
	public GraphicsObject<?, ?>[] getObjects() {
		synchronized (this) {
			GraphicsObject<?, ?>[] objs = new GraphicsObject<?, ?>[graphicsObjectLookup.size()];
			return graphicsObjectLookup.values().toArray(objs);
		}
	}

	/**
	 * Get a specific object by its backend object representation.
	 * 
	 * @param representedObject
	 *            The object that represents the graphical object.
	 * @return The graphical object that corresponds with this data, or null if
	 *         not found.
	 */
	public GraphicsObject<?, ?> getObject(Object representedObject) {
		synchronized (this) {
			return graphicsObjectLookup.get(representedObject);
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends GraphicsObject<?, ?>> T[] getObjectsOfType(Class<T> type) {
		ArrayList<T> foundObjects = new ArrayList<>();

		synchronized (this) {
			for (GraphicsObject<?, ?> obj : graphicsObjectLookup.values()) {
				if (type.isInstance(obj))
					foundObjects.add((T) obj);
			}
		}

		T[] array = (T[]) Array.newInstance(type, foundObjects.size());
		foundObjects.toArray(array);

		return array;
	}

	public UIMode getMode() {
		return mode;
	}

	@SuppressWarnings("unchecked")
	public <T> T getMode(Class<T> type) {
		if (mode != null && type.isInstance(mode))
			return (T) mode;
		return null;
	}

	public AffineTransform getCameraTransform() {
		Rectangle viewRect = lastClip;

		float halfWidth = viewRect.width * 0.5f;
		float halfHeight = viewRect.height * 0.5f;

		AffineTransform transform = AffineTransform.getTranslateInstance(halfWidth, halfHeight);
		transform.concatenate(AffineTransform.getTranslateInstance(-cameraPosition.getX(), -cameraPosition.getY()));

		return transform;
	}

	/**
	 * A matrix for converting image&lt;-&gt;world.
	 * 
	 * @return The transform for image to world.
	 */
	public AffineTransform getImageToWorldTransform() {
		float invPixelsPerInch = 1.0f / 72.0f;
		float invInchesPerFoot = 1.0f / map.getScale();

		AffineTransform pixelsToInches = AffineTransform.getScaleInstance(invPixelsPerInch, invInchesPerFoot);
		AffineTransform inchesToFeet = AffineTransform.getScaleInstance(invInchesPerFoot, invInchesPerFoot);

		inchesToFeet.concatenate(pixelsToInches);

		return inchesToFeet;
	}

	/**
	 * A matrix for converting world&lt;-&gt;image.
	 * 
	 * @return The transform for world to image.
	 */
	public AffineTransform getWorldToImageTransform() {
		float pixelsPerInch = 72.0f;
		float inchesPerFoot = map.getScale();

		AffineTransform inchesToPixels = AffineTransform.getScaleInstance(pixelsPerInch, pixelsPerInch);
		AffineTransform feetToInches = AffineTransform.getScaleInstance(inchesPerFoot, inchesPerFoot);

		inchesToPixels.concatenate(feetToInches);

		return inchesToPixels;
	}

	public AffineTransform getImageToScreenTransform() {
		return getCameraTransform();
	}

	public AffineTransform getScreenToImageTransform() {
		try {
			return getCameraTransform().createInverse();
		} catch (NoninvertibleTransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * A matrix for converting world&lt;-&gt;screen.
	 * 
	 * @return The transform for world to screen.
	 */
	public AffineTransform getWorldToScreenTransform() {
		AffineTransform invCameraTransform = getCameraTransform();
		AffineTransform worldToImage = getWorldToImageTransform();

		invCameraTransform.concatenate(worldToImage);

		return invCameraTransform;
	}

	/**
	 * A matrix for converting screen&lt;-&gt;world.
	 * 
	 * @return The transform for screen to world.
	 */
	public AffineTransform getScreenToWorldTransform() {
		AffineTransform cameraTransform = getCameraTransform();
		AffineTransform imageToWorld = getImageToWorldTransform();

		imageToWorld.concatenate(cameraTransform);

		return imageToWorld;
	}

	/**
	 * Called every time the map is drawn.
	 * 
	 * @param graphics
	 *            The graphics to use to draw.
	 */
	public final void onDraw(Graphics2D graphics) {
		Rectangle view = graphics.getClipBounds();
		lastClip = view;

		graphics.clearRect(0, 0, view.width, view.height);

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		AffineTransform transform = getCameraTransform();
		transform.concatenate(AffineTransform.getTranslateInstance(0, 22));

		graphics.setTransform(transform);

		if (map == null)
			return;

		synchronized (this) {
			graphics.setColor(Color.white);
			graphics.drawImage(map.getLoadedImage().getImage(), 0, 0, map.getLoadedImage().getIconWidth(),
					map.getLoadedImage().getIconHeight(), null);

			batchList.sort(new GraphicsBatchComparator());
			for (int i = 0; i < batchList.size(); i++) {
				GraphicsObject<?, ?> go = batchList.get(i);
				if (go.isDelelted()) {
					batchList.remove(i);

					i--;
				} else if (go.isVisible()) {
					Coord position = go.getWorldPosition();
					position = getScreenFromWorld(position);
					AffineTransform objectTransform = AffineTransform.getTranslateInstance(position.getX(),
							position.getY());
					// objectTransform.concatenate(getWorldToScreenTransform());
					objectTransform.concatenate(AffineTransform.getTranslateInstance(0, 22));

					graphics.setTransform(objectTransform);

					graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, go.getAlpha()));
					graphics.setColor(go.getColor());
					go.onDraw(graphics);
				}
			}
		}
	}

	/**
	 * Registers a graphical object with this graphical map.
	 * 
	 * @param go
	 *            The graphical object to register.
	 */
	protected void addGraphicalObject(GraphicsObject<?, ?> go) {
		synchronized (this) {
			batchList.add(0, go);
			graphicsObjectLookup.put(go.getRepresentedObject(), go);
		}
	}

	/**
	 * Deletes a registered object from this map. This method should NOT be
	 * used, instead use GraphicsObject.delete.
	 * 
	 * @param go
	 *            The graphical object to delete.
	 */
	protected void deleteGraphicalObject(GraphicsObject<?, ?> go) {
		synchronized (this) {
			graphicsObjectLookup.remove(go.getRepresentedObject());
			go.onDeleted();
		}
	}

	/**
	 * Called when a map should be converted into graphics objects.
	 */
	public abstract void spawnMap();

	/**
	 * Called to unload this graphical map.
	 */
	public void unload() {
	}

	/**
	 * Gets the graphics object that the mouse is hovering over.
	 * 
	 * @return The graphics object that the mouse is hovering over.
	 */
	public final GraphicsObject<?, ?> getHoverObject() {
		return over;
	}

	/**
	 * Called when the mouse moves over the graphics map.
	 * 
	 * @param e
	 *            The mouse movement event.
	 */
	public final void mouseMove(MouseEvent e) {
		synchronized (this) {
			RealMouseEvent re = transformMouseEvent(e);
			if (onMouseMove(re))
				return;

			GraphicsObject<?, ?> lastOver = over;

			for (int i = batchList.size() - 1; i >= 0; i--) {
				GraphicsObject<?, ?> go = batchList.get(i);
				if (go.isMouseOver(re)) {
					over = go;
					if (lastOver == over) {
						over.onMouseMove(re);
					} else if (lastOver != over) {
						if (lastOver != null)
							lastOver.onMouseLeave(re);
						over.onMouseOver(re);
					}

					return;
				}
			}

			if (lastOver != null) {
				lastOver.onMouseLeave(re);
				over = null;
			}
		}
	}

	/**
	 * Called when the mouse moves over the graphical map.
	 * 
	 * @param e
	 *            The mouse event to cause the event.
	 * @return True to block the call continuing to the hover object.
	 */
	public boolean onMouseMove(RealMouseEvent e) {
		return false;
	}

	public final void mouseEnter(MouseEvent e) {
		// TODO: Implement
	}

	public final void mouseExit(MouseEvent e) {
		synchronized (this) {
			RealMouseEvent re = transformMouseEvent(e);
			if (onMouseExit(re))
				return;

			if (over != null) {
				over.onMouseLeave(re);
			}
		}
	}

	/**
	 * Called when the mouse exits the graphical map.
	 * 
	 * @param e
	 *            The mouse event to cause the event.
	 * @return True to block the call continuing to the hover object.
	 */
	public boolean onMouseExit(RealMouseEvent e) {
		return false;
	}

	public final void mouseClick(MouseEvent e) {
		synchronized (this) {
			RealMouseEvent re = transformMouseEvent(e);
			if (onMouseClick(re))
				return;

			if (over != null) {
				over.onMouseClick(re);
			}
		}
	}

	/**
	 * Called when the mouse clicks the graphical map.
	 * 
	 * @param e
	 *            The mouse event to cause the event.
	 * @return True to block the call continuing to the hover object.
	 */
	public boolean onMouseClick(RealMouseEvent e) {
		return false;
	}

	public final void mouseDrag(MouseEvent e) {
		synchronized (this) {
			RealMouseEvent re = transformMouseEvent(e);
			if (onMouseDrag(re))
				return;

			if (over != null) {
				over.onMouseDrag(re);
			}
		}
	}

	/**
	 * Called when the mouse drags over the graphical map.
	 * 
	 * @param e
	 *            The mouse event to cause the event.
	 * @return True to block the call continuing to the hover object.
	 */
	public boolean onMouseDrag(RealMouseEvent e) {
		return false;
	}

	public final IMap getMap() {
		return map;
	}

	/**
	 * Gets the current position of the camera.
	 * 
	 * @return The current position of the camera (Image coordinates).
	 */
	public final Coord getCameraPosition() {
		return cameraPosition;
	}

	public final void setCameraPosition(Coord newPosition) {
		cameraPosition = newPosition;
	}

	public final Coord getRenderFromWorld(Coord worldCoord) {
		Point2D.Float src = new Point2D.Float(worldCoord.getX(), worldCoord.getY());

		Point2D.Float dst = (Float) getWorldToImageTransform().transform(src, null);

		return new Coord(dst.getX(), dst.getY());
	}

	public final Coord getWorldFromRender(Coord renderCoord) {
		Point2D.Float src = new Point2D.Float(renderCoord.getX(), renderCoord.getY());

		Point2D.Float dst = (Float) getImageToWorldTransform().transform(src, null);

		return new Coord(dst.getX(), dst.getY());
	}

	public final Coord getRenderFromScreen(Coord screenCoord) {
		Point2D.Float src = new Point2D.Float(screenCoord.getX(), screenCoord.getY());

		Point2D.Float dst = (Float) getScreenToImageTransform().transform(src, null);

		return new Coord(dst.getX(), dst.getY());
	}

	public final Coord getScreenFromRender(Coord renderCoord) {
		Point2D.Float src = new Point2D.Float(renderCoord.getX(), renderCoord.getY());

		Point2D.Float dst = (Float) getImageToScreenTransform().transform(src, null);

		return new Coord(dst.getX(), dst.getY());
	}

	public final Coord getScreenFromWorld(Coord worldCoord) {
		Point2D.Float src = new Point2D.Float(worldCoord.getX(), worldCoord.getY());

		Point2D.Float dst = (Float) getWorldToScreenTransform().transform(src, null);

		return new Coord(dst.getX(), dst.getY());
	}

	public final Coord getWorldFromScreen(Coord screenCoord) {
		Point2D.Float src = new Point2D.Float(screenCoord.getX(), screenCoord.getY());

		Point2D.Float dst = (Float) getScreenToWorldTransform().transform(src, null);

		return new Coord(dst.getX(), dst.getY());
	}

	protected final void updateReferencedObject(Object oldReference, Object newReference) {
		synchronized (this) {
			GraphicsObject<?, ?> gfxObj = graphicsObjectLookup.get(oldReference);
			graphicsObjectLookup.remove(oldReference);
			System.out.println("gfxObj: " + gfxObj);
			graphicsObjectLookup.put(newReference, gfxObj);
		}
	}

	private final RealMouseEvent transformMouseEvent(MouseEvent e) {
		Coord screenCoords = new Coord(e.getX(), e.getY());
		Coord imageCoords = getRenderFromScreen(screenCoords);
		Coord worldCoords = getWorldFromRender(imageCoords);

		return new RealMouseEvent(worldCoords, imageCoords, e.getButton(), e.isAltDown(), e.isControlDown(),
				e.isShiftDown());
	}

	public class GraphicsBatchComparator implements Comparator<GraphicsObject<?, ?>> {
		@Override
		public int compare(GraphicsObject<?, ?> arg0, GraphicsObject<?, ?> arg1) {
			return arg0.getDrawBatch() - arg1.getDrawBatch();
		}
	}

}
