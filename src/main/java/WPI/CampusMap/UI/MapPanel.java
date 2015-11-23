package WPI.CampusMap.UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;

import javax.swing.JPanel;
import javax.xml.stream.XMLStreamException;

import WPI.CampusMap.Backend.Coord;
import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.PathPlanning.Node;
import WPI.CampusMap.PathPlanning.Path;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import WPI.CampusMap.AStar.Coord;
import WPI.CampusMap.AStar.Map;
import WPI.CampusMap.AStar.Node;
import WPI.CampusMap.AStar.Path;
import WPI.CampusMap.AStar.Point;

/**
 * Map Panel Class Contains the graphics and UI components of the app
 */
@SuppressWarnings("serial")
class MapPanel extends JPanel implements Runnable{
	private AppUIObject uiObject;
	protected Map currentMap;
	protected Path currentRoute;
	protected Point selectedPoint;
	protected Point startPoint, endPoint;
	
	private Thread renderingThread;
	
	//Hashtable of all objects that can be drawn
	private Hashtable<Object, IGraphicsObject> drawables = new Hashtable<>();
	private ArrayList<IGraphicsObject> batchList = new ArrayList<>();

	MapPanel(AppUIObject uiObject) {
		this.uiObject = uiObject;
		
		
		
		this.renderingThread = new Thread(this, "Render Thread");
		this.renderingThread.start();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		drawMap((Graphics2D) g);
	}

	/**
	 * loadMap takes a mapName and loads it as the current map
	 * 
	 * @param mapName
	 *            map to load
	 * @throws XMLStreamException
	 */
	protected void loadMap(String mapName) throws XMLStreamException {
		System.out.println("UI: " + mapName);
		Map newMap = new Map(mapName);
		currentMap = newMap;
		uiObject.reDrawUI();
	}

	/**
	 * drawMap takes in a graphics object and uses it to draw the map
	 * 
	 * @param graphics
	 *            graphics object to do the drawing
	 */
	private void drawMap(Graphics2D graphics) {
		graphics.clearRect(0, 0, getWidth(), getHeight());

		if (currentMap == null)
			return;

		graphics.setColor(Color.white);
		graphics.drawImage(currentMap.getLoadedImage().getImage(), 0, 0, getWidth(), getHeight(), null);

		batchList.sort(new GraphicsBatchComparator());
		for(int i = 0; i < batchList.size(); i++)
		{
			IGraphicsObject go = batchList.get(i);
			if(go.getRepresentedObject() == null)
			{
				
			}
			else
			{
				go.onDraw(graphics);
			}
		}
	}

	/**
	 * removeEdgeOnMap takes a mouse event e and removes the edge at that
	 * location
	 * 
	 * @param e
	 *            Mouse event used to find edge
	 * @return
	 */
	protected boolean removeEdgeOnMap(MouseEvent e) {
		if (selectedPoint == null) {
			selectPointOnMap(e);
			return false;
		}

		Point lastSelected = selectedPoint;
		if (!selectPointOnMap(e))
			return false;

		currentMap.removeEdge(lastSelected, selectedPoint);
		selectedPoint = null;
		return true;
	}

	/**
	 * Removes a point on the map at the mouse point.
	 * 
	 * @param e
	 *            The mouse event to trigger the method.
	 * @return The point that was created.
	 */
	protected boolean deletePointOnMap(MouseEvent e) {
		// Coord screenCoord = new Coord(e.getX(), e.getY());
		// Coord mapCoord =
		// AppUIObject.currentMap.screenToWorldSpace(screenCoord);

		selectPointOnMap(e);
		return currentMap.removePoint(selectedPoint);
	}

	/**
	 * Selects a point on the map.
	 * 
	 * @param e
	 *            The mouse event to select a point from.
	 * @return True if a point was selected, false otherwise.
	 */
	protected boolean selectPointOnMap(MouseEvent e) {
		Coord screenCoord = new Coord(e.getX(), e.getY());

		HashMap<String, Point> points = currentMap.getAllPoints();

		Point closestPoint = null;
		float closestDistance = Float.MAX_VALUE;
		final float clickThreshold = 5.0f;

		for (Point p : points.values()) {
			float distance = screenCoord.distance(currentMap.worldToScreenSpace(p.getCoord()));

			if (distance < clickThreshold && distance < closestDistance) {
				closestPoint = p;
				closestDistance = distance;
			}
		}

		// No point selected
		if (closestPoint == null)
			return false;

		selectedPoint = closestPoint;
		return true;
	}

	/**
	 * If no point is selected then it selects a point. If a point is selected
	 * already then it tries to select a new point and if successful it creates
	 * an edge between the two points.
	 * 
	 * @param e
	 *            The mouse event to try and create an edge from.
	 * @return True if an edge was created, false otherwise.
	 */
	protected boolean addEdgeOnMap(MouseEvent e) {
		if (selectedPoint == null) {
			selectPointOnMap(e);
			return false;
		}

		Point lastSelected = selectedPoint;
		if (!selectPointOnMap(e)) {
			selectedPoint = lastSelected;
			return false;
		}

		currentMap.addEdge(lastSelected, selectedPoint);
		selectedPoint = null;

		return true;
	}

	@Override
	public void run() {
		while(true)
		{
			//Ensure that rendering does not happen while objects are updating
			synchronized(this)
			{
				repaint();
			}
			
			try 
			{
				Thread.sleep(33);
			} catch (InterruptedException e)
			{
				return;
			}
		}
	}
	
	private class GraphicsBatchComparator implements Comparator<IGraphicsObject>
	{

		@Override
		public int compare(IGraphicsObject arg0, IGraphicsObject arg1) 
		{
			return arg0.getDrawBatch() - arg1.getDrawBatch();
		}
		
	}
	
	private class MapPannelMouseListener implements MouseListener, MouseMotionListener
	{
		public MapPannelMouseListener(MapPanel panel)
		{
			this.panel = panel;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			if(over != null)
			{
				synchronized (panel)
				{
					over.onMouseClick(e);
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) 
		{
		}

		@Override
		public void mouseExited(MouseEvent e) 
		{
			if(over != null)
			{
				synchronized (panel)
				{
					over.onMouseLeave(e);
					over = null;
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) 
		{
		}

		@Override
		public void mouseReleased(MouseEvent e) 
		{
			
		}
		@Override
		public void mouseDragged(MouseEvent e)
		{
			if(over != null)
			{
				synchronized(panel)
				{
					over.onMouseDrag(e);
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) 
		{
			synchronized (panel)
			{
				for(int i = panel.batchList.size() - 1; i >= 0; i--)
				{
					IGraphicsObject go = panel.batchList.get(i);
					if(go.isMouseOver(e))
					{
						over = go;
						over.onMouseOver(e);
						break;
					}
				}
				
				if(over != null)
				{
					over.onMouseLeave(e);
					over = null;
				}
			}
			
			
		}
		
		private IGraphicsObject over;
		
		private MapPanel panel;
	}
}