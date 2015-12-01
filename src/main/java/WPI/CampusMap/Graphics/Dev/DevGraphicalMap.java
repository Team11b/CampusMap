package WPI.CampusMap.Graphics.Dev;

import WPI.CampusMap.Backend.ConnectionPoint;
import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.Dev.EditorToolMode;
import WPI.CampusMap.Graphics.GraphicalMap;
import WPI.CampusMap.Graphics.RealMouseEvent;
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
		DevPointGraphicsObject.clearSelection();
	}
	
	@Override
	public boolean onMouseClick(RealMouseEvent e) {
		if (mode == EditorToolMode.Point && getHoverObject() == null) {
			Point newPoint = new Point(getMap().getName());
			newPoint.setCoord(getWorldCoord((int) e.getX(), (int) e.getY()));
			getMap().addPoint(newPoint);

			// Update with point name(id)
			getUI().setNodeTextField(newPoint.getId());

			DevPointGraphicsObject go = new DevPointGraphicsObject(newPoint, this);
			addGraphicalObject(go);

			go.select();

			return true;
		} else if (getHoverObject() == null) {
			DevPointGraphicsObject.clearSelection();
		} else if ((getHoverObject() != null) && (mode == EditorToolMode.None)) {

			// Copy the textbook to the type
			if (DevPointGraphicsObject.getSelected() != null)
				DevPointGraphicsObject.getSelected().updatePoint();

			// Update UI
			DevPointGraphicsObject hoverObject = (DevPointGraphicsObject) getHoverObject();
			hoverObject.select();
			Point thePoint = ((Point) getHoverObject().getRepresentedObject());
			getUI().setNodeTextField(thePoint.getId());
			if (getHoverObject().getRepresentedObject().getClass() == ConnectionPoint.class) {
				System.out.println("Is a connection node");
				ConnectionPoint theConnectionPoint = (ConnectionPoint) getHoverObject().getRepresentedObject();
				getUI().setPointConnectorText(theConnectionPoint.getLinkedMap());
				getUI().setPointConnectorText(theConnectionPoint.getLinkedPoint());
				getUI().setMapConnectionTextFieldEditable(true);
				getUI().setMapConnectionTextFieldEditable(true);
			} else {
				getUI().setPointConnectorText("");
				getUI().setPointConnectorText("");
				getUI().setMapConnectionTextFieldEditable(false);
				getUI().setMapConnectionTextFieldEditable(false);
			}
			System.out.println("The type is " + thePoint.getType());
			switch (thePoint.getType()) {
			case "":
			case Point.HALLWAY:
				getUI().setMapConnectionTextFieldEditable(false);
				getUI().setMapConnectionTextFieldEditable(false);
				getUI().setTypeSelector(0);
				break;
			case Point.STAIRS:
				getUI().setMapConnectionTextFieldEditable(true);
				getUI().setMapConnectionTextFieldEditable(true);
				getUI().setTypeSelector(1);
				break;
			case Point.ELEVATOR:
				getUI().setMapConnectionTextFieldEditable(true);
				getUI().setMapConnectionTextFieldEditable(true);
				getUI().setTypeSelector(2);
				break;
			case Point.OUT_DOOR:
				getUI().setMapConnectionTextFieldEditable(true);
				getUI().setMapConnectionTextFieldEditable(true);
				getUI().setTypeSelector(3);
				break;
			default:
				System.out.println("Type is invalid");
				break;
			}

		}

		return false;
	}
}
