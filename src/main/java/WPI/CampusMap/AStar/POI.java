package WPI.CampusMap.AStar;

/**
 * 
 * @author Max Stenke
 *
 */
public class POI extends Point {
	private Map linkedMap;

	/**
	 * Point of Interest constructor
	 * 
	 * @param linkedMap
	 *            additional Map this POI connects to
	 */
	public POI(Coord coord, String type, String id, Map linkedMap) {
		super(coord, type, id);
		this.linkedMap = linkedMap;
	}

	public Map getLinkedMap() {
		return linkedMap;
	}

	public void setLinkedMap(Map linkedMap) {
		this.linkedMap = linkedMap;
	}

}
