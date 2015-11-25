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
import WPI.CampusMap.Graphics.GraphicalMap;
import WPI.CampusMap.Graphics.User.UserGraphicalMap;
import WPI.CampusMap.PathPlanning.Node;
import WPI.CampusMap.PathPlanning.Path;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

/**
 * Map Panel Class Contains the graphics and UI components of the app
 */
@SuppressWarnings("serial")
public class MapPanel extends JPanel implements Runnable{
	protected Map currentMap;
	protected Path currentRoute;
	protected Point selectedPoint;
	protected Point startPoint, endPoint;
	
	private AppUIObject uiObject;
	private Thread renderingThread;
	
	private GraphicalMap graphicsMap;

	MapPanel(AppUIObject uiObject) {
		selectedPoint = new Point();
		selectedPoint.setId("");
		selectedPoint.setType("1");
		this.uiObject = uiObject;
		
		MapPannelMouseListener mouse = new MapPannelMouseListener(this);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
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
		
		graphicsMap = new UserGraphicalMap(currentMap, this);
	}

	/**
	 * drawMap takes in a graphics object and uses it to draw the map
	 * 
	 * @param graphics
	 *            graphics object to do the drawing
	 */
	private void drawMap(Graphics2D graphics) 
	{
		if(graphicsMap != null)
		{
			Graphics2D newGraphics = (Graphics2D) graphics.create();
			
			graphicsMap.onDraw(newGraphics);
			
			newGraphics.dispose();
		}
		else
			graphics.clearRect(0, 0, getWidth(), getHeight());
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
	
	private class MapPannelMouseListener implements MouseListener, MouseMotionListener
	{
		private MapPanel panel;
		
		public MapPannelMouseListener(MapPanel panel)
		{
			this.panel = panel;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			synchronized (panel)
			{
				
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) 
		{
		}

		@Override
		public void mouseExited(MouseEvent e) 
		{
			if(panel.graphicsMap == null)
				return;
			
			synchronized (panel)
			{
				panel.graphicsMap.onMouseExit(e);
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
			if(panel.graphicsMap == null)
				return;
			
			synchronized (panel)
			{
				panel.graphicsMap.onMouseDrag(e);
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) 
		{
			if(panel.graphicsMap == null)
				return;
			
			synchronized (panel)
			{
				panel.graphicsMap.onMouseMove(e);
			}
		}
	}
}