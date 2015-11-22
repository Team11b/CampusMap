package WPI.CampusMap.AStar;

import WPI.CampusMap.Backend.Coord;
import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.Backend.Point;

/**
 * 
 * @author Max Stenke
 *
 */
public class ConnectionPoint extends Point {
	private Map linkedMap;

	/**
	 * Point of Interest constructor
	 * 
	 * @param coord
	 *            Coordinates of the Point of interest
	 * @param type
	 *            Type of Point
	 * @param id
	 * 			  ID of Point
	 * @param linkedMap
	 *            additional Map this POI connects to
	 */
	public ConnectionPoint(Coord coord, String type, String id, Map linkedMap) {
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
