package WPI.CampusMap.Graphics.Dev;

import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.Dev.EditorToolMode;
import WPI.CampusMap.Graphics.GraphicalMap;
import WPI.CampusMap.Graphics.RealMouseEvent;
import WPI.CampusMap.UI.AppUIObject;
import WPI.CampusMap.UI.MapPanel;

public class DevGraphicalMap extends GraphicalMap
{
	private EditorToolMode mode = EditorToolMode.None;	
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
			new DevPointGraphicsObject(p, this);
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
		DevPointGraphicsObject.clearSelection();
	}
	
	@Override
	public boolean onMouseClick(RealMouseEvent e) {
		if (mode == EditorToolMode.Point && getHoverObject() == null)
		{
			Point newPoint = new Point(getMap().getName());
			newPoint.setCoord(getWorldCoord((int) e.getX(), (int) e.getY()));
			getMap().addPoint(newPoint);

			// Update with point name(id)
			getUI().setNodeTextField(newPoint.getId());

			DevPointGraphicsObject go = new DevPointGraphicsObject(newPoint, this);
			addGraphicalObject(go);

			go.select();

			return true;
		} 
		else if (getHoverObject() == null) {
			DevPointGraphicsObject.clearSelection();
			AppUIObject.getInstance().onPointSelected(null);
			return true;
		}

		return false;
	}
}
