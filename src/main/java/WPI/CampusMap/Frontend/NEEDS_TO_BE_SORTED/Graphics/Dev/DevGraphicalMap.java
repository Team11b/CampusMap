package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.Dev;

import WPI.CampusMap.Backend.Core.Map.IMap;
import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Dev.EditorToolMode;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.GraphicalMap;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.RealMouseEvent;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI.DevMode;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.UI_OLD.AppUIObject;

public class DevGraphicalMap extends GraphicalMap
{
	private EditorToolMode mode = EditorToolMode.None;	
	public DevGraphicalMap(String map, DevMode mode)
	{		
		super(map, mode);	
		System.out.println("Dev mode entered.");
	}

	@Override
	public void spawnMap(IMap map)
	{
		DevEdgeGraphicsObject.cleanupEdges();
		
		for(IPoint p : map.getAllPoints())
		{
			new DevPointGraphicsObject((RealPoint)p, this);
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
			RealPoint newPoint = new RealPoint(getMap().getName());
			System.out.println(newPoint.getMap());
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
