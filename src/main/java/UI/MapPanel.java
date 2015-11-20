package UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

import javax.swing.JPanel;

import WPI.CampusMap.AStar.Coord;
import WPI.CampusMap.AStar.Node;
import WPI.CampusMap.AStar.Path;
import WPI.CampusMap.AStar.Point;

/**Map Panel Class
 * Contains the graphics and UI components of the app
 */
class MapPanel extends JPanel{
	private AppUIObject uiObject;
	
	MapPanel(AppUIObject uiObject){
		this.uiObject = uiObject;
	}
	
	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);

		drawMap((Graphics2D)g);
	}

	/**drawPoint method intakes a point and a graphics object, and draws the point
	 * 
	 * @param p				Point to be drawn
	 * @param graphics		graphics object to do the drawing
	 */
	private void drawPoint(Point p, Graphics2D graphics)
	{
		if(AppUIObject.startPoint == p && !uiObject.devMode)
		{
			graphics.setColor(Color.green);
		}
		else if(uiObject.endPoint == p && !uiObject.devMode)
		{
			graphics.setColor(Color.blue);
		}
		else if(uiObject.selectedPoint == p && uiObject.devMode)
		{
			graphics.setColor(Color.yellow);
		}
		else
		{
			graphics.setColor(Color.red);
		}

		Coord screenCoord = AppUIObject.currentMap.worldToScreenSpace(p.getCoord());

		final float size = 10.0f;
		final float halfSize = size * 0.5f;

		Coord truePosition = new Coord(screenCoord.getX() - halfSize, screenCoord.getY() - halfSize);

		graphics.fillOval((int)truePosition.getX(), (int)truePosition.getY(), (int)size, (int)size);
	}

	/** drawEdges takes a point, hashtable of drawn points
	 *  and a graphics object and draws the edges of point p
	 * @param p					point to have edges drawn
	 * @param drawnPoints		points to draw edges to
	 * @param graphics			graphics object to do the drawing
	 */
	private void drawEdges(Point p, Hashtable<Point, HashSet<Point>> drawnPoints, Graphics2D graphics)
	{
		graphics.setColor(Color.gray);
		graphics.setStroke(new BasicStroke(2));

		ArrayList<Point> neighbors = p.getValidNeighbors();

		HashSet<Point> skipPoints = drawnPoints.get(p);
		if(skipPoints == null)
		{
			skipPoints = new HashSet<>();
			drawnPoints.put(p, skipPoints);
		}

		for(Point n : neighbors)
		{
			if(skipPoints.contains(n))
				continue;

			HashSet<Point> neighborPoints = drawnPoints.get(n);
			if(neighborPoints != null)
			{
				if(neighborPoints.contains(p))
					continue;
			}
			else
			{
				drawnPoints.put(n, neighborPoints = new HashSet<>());
			}

			neighborPoints.add(p);
			skipPoints.add(n);

			Coord screenStart = AppUIObject.currentMap.worldToScreenSpace(p.getCoord());
			Coord screenStop = AppUIObject.currentMap.worldToScreenSpace(n.getCoord());

			graphics.drawLine((int)screenStart.getX(), (int)screenStart.getY(), (int)screenStop.getX(), (int)screenStop.getY());
		}

		graphics.setStroke(new BasicStroke(1));
	}

	/** drawPath takes a path and a graphics object and draws the path
	 * 
	 * @param path			path to be drawn
	 * @param graphics		graphics object to do the drawing
	 */
	private void drawPath(Path path, Graphics2D graphics)
	{
		graphics.setColor(new Color(255, 0, 255));
		graphics.setStroke(new BasicStroke(3));

		ArrayList<Node> nodes = path.getPath();
		for(int i = 1; i < nodes.size(); i++)
		{
			int before = i - 1;

			Node currentNode = nodes.get(i);
			Node beforeNode = nodes.get(before);

			Coord startScreen = AppUIObject.currentMap.worldToScreenSpace(beforeNode.getPoint().getCoord());
			Coord endScreen = AppUIObject.currentMap.worldToScreenSpace(currentNode.getPoint().getCoord());

			graphics.drawLine((int)startScreen.getX(), (int)startScreen.getY(), (int)endScreen.getX(), (int)endScreen.getY());

		}

		graphics.setStroke(new BasicStroke(1));
	}

	/** drawMap takes in a graphics object and uses it to draw the map
	 * 
	 * @param graphics 		graphics object to do the drawing
	 */
	private void drawMap(Graphics2D graphics)
	{
		graphics.clearRect(0,  0,  getWidth(), getHeight());

		if(AppUIObject.currentMap == null)
			return;

		graphics.setColor(Color.white);
		graphics.drawImage(AppUIObject.currentMap.getLoadedImage().getImage(), 0, 0, getWidth(), getHeight(), null);

		ArrayList<Point> points = AppUIObject.currentMap.getMap();

		Hashtable<Point, HashSet<Point>> drawnPoints = new Hashtable<>();
		for(Point p : points)
		{
			drawEdges(p, drawnPoints, graphics);
		}

		for(Point p : points)
		{
			drawPoint(p, graphics);
		}

		if(AppUIObject.currentRoute != null && !uiObject.devMode)
		{
			drawPath(AppUIObject.currentRoute, graphics);
		}
	}
	
	/** removeEdgeOnMap takes a mouse event e and removes the edge at that location
	 * 
	 * @param e		Mouse event used to find edge
	 * @return
	 */
    protected static boolean removeEdgeOnMap(MouseEvent e) {
        if (AppUIObject.selectedPoint == null) {
            selectPointOnMap(e);
            return false;
        }

        Point lastSelected = AppUIObject.selectedPoint;
        if (!selectPointOnMap(e))
            return false;

        AppUIObject.currentMap.removeEdge(lastSelected, AppUIObject.selectedPoint);
        AppUIObject.selectedPoint = null;
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
		Coord screenCoord = new Coord(e.getX(), e.getY());

		Coord mapCoord = uiObject.currentMap.screenToWorldSpace(screenCoord);

		selectPointOnMap(e);

		return uiObject.currentMap.removePoint(uiObject.selectedPoint);
	}
    
    /**
	 * Selects a point on the map.
	 * 
	 * @param e
	 *            The mouse event to select a point from.
	 * @return True if a point was selected, false otherwise.
	 */
	protected static boolean selectPointOnMap(MouseEvent e) {
		Coord screenCoord = new Coord(e.getX(), e.getY());

		ArrayList<Point> points = AppUIObject.currentMap.getMap();

		Point closestPoint = null;
		float closestDistance = Float.MAX_VALUE;
		final float clickThreshold = 5.0f;

		for (Point p : points) {
			float distance = screenCoord.distance(AppUIObject.currentMap.worldToScreenSpace(p.getCoord()));

			if (distance < clickThreshold && distance < closestDistance) {
				closestPoint = p;
				closestDistance = distance;
			}
		}

		// No point selected
		if (closestPoint == null)
			return false;

		AppUIObject.selectedPoint = closestPoint;

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
	protected static boolean addEdgeOnMap(MouseEvent e) {
		if (AppUIObject.selectedPoint == null) {
			selectPointOnMap(e);
			return false;
		}

		Point lastSelected = AppUIObject.selectedPoint;
		if (!selectPointOnMap(e))
		{
			AppUIObject.selectedPoint = lastSelected;
			return false;
		}

		AppUIObject.currentMap.addEdge(lastSelected, AppUIObject.selectedPoint);
		
		AppUIObject.selectedPoint = null;

		return true;
	}
}