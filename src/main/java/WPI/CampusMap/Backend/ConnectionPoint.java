package WPI.CampusMap.Backend;

/**
 * 
 * @author Max Stenke
 * @author Jacob Zizmor
 *
 */
public class ConnectionPoint extends Point {

	private static final long serialVersionUID = 8498467892896790681L;
	private String linkedMap;
	private String linkedPoint;
	private ConnectionPoint connPoint;
	private Map connMap;

	private static final int connectionCost = 1;

	/**
	 * ConnectionPoint constructor
	 * 
	 * @param coord
	 *            Coordinates of the Point of interest
	 * @param type
	 *            Type of Point
	 * @param id
	 *            ID of Point
	 * @param mapName
	 *            Name of Map where this Point is located
	 * @param linkedMap
	 *            additional Map this ConnectionPoint connects to
	 * @param linkedPoint
	 *            additional ConnectionPoint this ConnectionPoint connects to
	 */
	public ConnectionPoint(Coord coord, String type, String id, String mapName, String linkedMap, String linkedPoint) {
		super(coord, type, id, mapName);
		this.linkedMap = linkedMap;
		this.linkedPoint = linkedPoint;
		this.connMap = null;
		this.connPoint = null;
	}

	public String getLinkedMap() {
		return linkedMap;
	}

	public void setLinkedMap(String linkedMap) {
		this.linkedMap = linkedMap;
	}

	public String getLinkedPoint() {
		return linkedPoint;
	}

	public void setLinkedPoint(String linkedPoint) {
		this.linkedPoint = linkedPoint;
	}

	public ConnectionPoint getConnPoint() {
		return connPoint;
	}

	public void setConnPoint(ConnectionPoint connPoint) {
		this.connPoint = connPoint;
	}

	public Map getConnMap() {
		return connMap;
	}

	public void setConnMap(Map connMap) {
		this.connMap = connMap;
	}

	public static int getConnectioncost() {
		return connectionCost;
	}

}
