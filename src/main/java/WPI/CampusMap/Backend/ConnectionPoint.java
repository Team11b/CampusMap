package WPI.CampusMap.Backend;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * 
 * @author Max Stenke
 * @author Jacob Zizmor
 *
 */
public class ConnectionPoint extends Point {

	private static final long serialVersionUID = 8498467892896790681L;
	private LinkedList<String> linkedMaps;
	private HashMap<String, String> linkedPoints;
	private HashMap<String, ConnectionPoint> connPoints; // this assumes that
															// there is only one
															// connectionpoint
															// for each distinct
															// map which
															// corresponds to
															// this
															// connectionpoint
	private LinkedList<Map> connMaps;

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
		this.linkedMaps = new LinkedList<String>();
		this.linkedPoints = new HashMap<String, String>();
		this.connMaps = new LinkedList<Map>();
		this.connPoints = new HashMap<String, ConnectionPoint>();
		this.linkedMaps.add(linkedMap);
		this.linkedPoints.put(linkedMap, linkedPoint);
	}

	public ConnectionPoint(Coord coord, String type, String id, String mapName, LinkedList<String> linkedMap,
			HashMap<String, String> linkedPoint) {
		super(coord, type, id, mapName);
		this.linkedMaps = linkedMap;
		this.linkedPoints = linkedPoint;
		this.connMaps = new LinkedList<Map>();
		this.connPoints = new HashMap<String, ConnectionPoint>();
	}

	public LinkedList<String> getLinkedMaps() {
		return linkedMaps;
	}

	public void setLinkedMaps(LinkedList<String> linkedMaps) {
		this.linkedMaps = linkedMaps;
	}

	public void addLinkedMap(String linkedMap) {
		this.linkedMaps.add(linkedMap);
	}

	public HashMap<String, String> getLinkedPoints() {
		return linkedPoints;
	}

	public void setLinkedPoints(HashMap<String, String> linkedPoints) {
		this.linkedPoints = linkedPoints;
	}

	public void addLinkedPoint(String mapName, String linkedPoint) {
		this.linkedPoints.put(mapName, linkedPoint);
	}

	public String getSpecLinkedPoint(String map) {
		return this.linkedPoints.get(map);
	}

	public HashMap<String, ConnectionPoint> getConnPoints() {
		return connPoints;
	}

	public void setConnPoints(HashMap<String, ConnectionPoint> connPoints) {
		this.connPoints = connPoints;
	}

	public void addConnPoint(String mapName, ConnectionPoint connPoint) {
		this.connPoints.put(mapName, connPoint);
	}
	
	public Point getSpecConnPoint(String mapName) {
		return this.connPoints.get(mapName);
	}

	public LinkedList<Map> getConnMaps() {
		return connMaps;
	}

	public void setConnMaps(LinkedList<Map> connMaps) {
		this.connMaps = connMaps;
	}

	public void addConnMap(Map connMap) {
		this.connMaps.add(connMap);
	}

	/**
	 * Converts this connection point to a Point
	 * 
	 * @return The new connection Point
	 */
	@Override
	public ConnectionPoint getConnectionPoint() {
		return this;
	}

	/**
	 * Converts this connection point
	 * 
	 * @return The new connection point
	 */
	@Override
	public Point getNormalPoint() {
		Point temp = new Point(this.getCoord(), this.getType(), this.getId(), this.getMap());
		for (Point point : this.getNeighborsP()) {
			temp.addNeighbor(point);
		}
		return temp;
	}

}
