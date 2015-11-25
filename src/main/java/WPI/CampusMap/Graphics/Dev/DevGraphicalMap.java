package WPI.CampusMap.Graphics.Dev;

import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.Dev.EditorToolMode;
import WPI.CampusMap.Graphics.GraphicalMap;
import WPI.CampusMap.UI.MapPanel;

public class DevGraphicalMap extends GraphicalMap
{
	private EditorToolMode mode;
	
	public DevGraphicalMap(Map map, MapPanel panel) {
		super(map, panel);
		System.out.println("Dev mode entered.");
	}

	@Override
	public void spawnMap(Map map)
	{
		DevEdgeGraphicsObject.cleanupEdges();
		
		for(Point p : map.getAllPoints().values())
		{
			addGraphicalObject(new DevPointGraphicsObject(p, this));
		}
	}
	
	/**
	 * Get what mode the editor is in.
	 * @return The editor mode.
	 */
	public final EditorToolMode getMode()
	{
		return mode;
	}
	
	/**
	 * Sets the editor mode.
	 * @param mode The new mode for the editor.
	 */
	public final void setMode(EditorToolMode mode)
	{
		this.mode = mode;
	}
}
