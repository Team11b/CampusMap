package WPI.CampusMap.Backend.Core.Point;

import java.util.ArrayList;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Map.IMap;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.PathFinding.AStar.Frontier;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.PathFinding.Node.Node;

public class ProxyPoint implements IPoint {
	String pointName, mapName;
	RealPoint realPoint;
	
	protected ProxyPoint(String fullName){
		String[] splitName = fullName.split("/");
		if(splitName.length == 1){
			this.pointName = splitName[0];
		}else if(splitName.length == 2){
			this.pointName = splitName[1];
			this.mapName = splitName[0];
			
		}
	}

	private void load(){
		if(realPoint == null){
			IMap temp = AllMaps.getMap(mapName);
			if(temp != null){
				realPoint = (RealPoint) temp.getPoint(pointName);
			}
		}
	}
	
	@Override
	public double distance(IPoint other) {
		load();
		return realPoint.distance(other);
	}

	@Override
	public Coord getCoord() {
		load();
		return realPoint.getCoord();
	}

	@Override
	public void setCoord(Coord coord) {
		load();
		realPoint.setCoord(coord);
	}

	@Override
	public String getType() {
		load();
		return realPoint.getType();
	}

	@Override
	public void setType(String type) {
		load();
		realPoint.setType(type);
	}

	@Override
	public String getId() {
		load();
		return pointName;
	}

	@Override
	public void setId(String id) {
		load();
		realPoint.setId(id);
	}

	@Override
	public ArrayList<IPoint> getNeighborsP() {
		load();
		return realPoint.getNeighborsP();
	}

	@Override
	public void buildFrontier(Frontier frontier, Node fromNode, IPoint goal) {
		load();
		realPoint.buildFrontier(frontier, fromNode, goal);

	}

	@Override
	public ArrayList<IPoint> getValidNeighbors(ArrayList<String> whitelist) {
		load();
		return realPoint.getValidNeighbors(whitelist);
	}

	@Override
	public boolean removeNeighbor(IPoint point) {
		load();
		return realPoint.removeNeighbor(point);
	}

	@Override
	public boolean removeNeighbor(String pointId) {
		load();
		return realPoint.removeNeighbor(pointId);
	}

	@Override
	public void removeAllNeighbors() {
		load();
		realPoint.removeAllNeighbors();
	}

	@Override
	public boolean addNeighbor(IPoint point) {
		load();
		return realPoint.addNeighbor(point);
	}

	@Override
	public String getMap() {
		load();
		return realPoint.getMap();
	}
	
	@Override
	public int hashCode() {
		return (this.getMap() + "/" + getId()).hashCode();
	}

	@Override
	public boolean exists() {
		load();
		return realPoint == null;
	}

}
