package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.xml.stream.XMLStreamException;

import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Map.IMap;
import WPI.CampusMap.Backend.Core.Map.ProxyMap;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Dev.EditorToolMode;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.GraphicalMap;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.Dev.DevGraphicalMap;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.User.UserGraphicalMap;

/**
 * Map Panel Class Contains the graphics and UI components of the app
 */
@SuppressWarnings("serial")
public class MapPanel extends JPanel implements Runnable{
	protected IMap currentMap;
	
	private Thread renderingThread;
	
	private GraphicalMap graphicsMap;
	
	private boolean isDevMode;
	public AppUIObject uiObject;

	MapPanel(AppUIObject uiObject) {
		MapPannelMouseListener mouse = new MapPannelMouseListener(this);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
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
		
		synchronized(this)
		{
			IMap newMap = AllMaps.getInstance().getMap(mapName);
			if(newMap == null){
				newMap = new ProxyMap(mapName);
			}
			currentMap = newMap;
			
			if(graphicsMap != null)
				graphicsMap.unload();
			
			if(isDevMode)
			{
				onEnterDevMode();
			}
			else
			{
				onEnterUserMode();
			}
		}
	}
	
	protected void onEnterUserMode()
	{
		synchronized (this)
		{
			if(currentMap != null)
				graphicsMap = UserGraphicalMap.loadGraphicalMap(currentMap, this);
			
			isDevMode = false;
		}
	}
	
	protected void onEnterDevMode()
	{
		synchronized (this)
		{
			if(currentMap != null)
				graphicsMap = new DevGraphicalMap(currentMap, this);
			
			isDevMode = true;
		}
	}
	
	protected void setDevMode(EditorToolMode mode)
	{
		synchronized(this)
		{
			((DevGraphicalMap)graphicsMap).setMode(mode);
		}
	}
	
	protected EditorToolMode getDevMode()
	{
		synchronized(this)
		{
			return ((DevGraphicalMap)graphicsMap).getMode();
		}
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
				panel.graphicsMap.mouseClick(e);
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
				panel.graphicsMap.mouseExit(e);
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
				panel.graphicsMap.mouseDrag(e);
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) 
		{
			if(panel.graphicsMap == null)
				return;
			
			synchronized (panel)
			{
				panel.graphicsMap.mouseMove(e);
			}
		}
	}
}