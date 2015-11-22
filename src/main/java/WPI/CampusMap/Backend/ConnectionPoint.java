package WPI.CampusMap.Backend;

/**
 * 
 * @author Max Stenke
 * @author Jacob Zizmor
 *
 */
public class ConnectionPoint extends Point {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8498467892896790681L;
	private String linkedMap;
	private String linkedPoint;

	/**
	 * ConnectionPoint constructor
	 * 
	 * @param coord
	 *            Coordinates of the Point of interest
	 * @param type
	 *            Type of Point
	 * @param id
	 * 			  ID of Point
	 * @param linkedMap
	 *            additional Map this ConnectionPoint connects to
	 */
	public ConnectionPoint(Coord coord, String type, String id, String linkedMap, String linkedPoint) {
		super(coord, type, id);
		this.linkedMap = linkedMap;
		this.linkedPoint = linkedPoint;
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

}
