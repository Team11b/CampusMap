package WPI.CampusMap.Backend.Core.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Map.IMap;

public class ProxyPoint implements IPoint {

	private static final long serialVersionUID = 4456203165550908105L;
	String pointId, mapName;
	RealPoint realPoint;
	
	protected ProxyPoint(String fullName){
		String[] splitName = fullName.split("/");
		if(splitName.length == 1){
			this.pointId = splitName[0];
		}else if(splitName.length == 2){
			this.pointId = splitName[1];
			this.mapName = splitName[0];
		}
	}

	private void load(){
		if(realPoint == null){
			IMap temp = AllMaps.getInstance().getMap(mapName);
			if(temp != null){
				realPoint = (RealPoint) temp.getPoint(pointId);
				if(realPoint == null){
					System.out.println("Point not found: "+pointId);
					System.out.println(temp.getAllPoints());
				}else{
					realPoint.constructNeighbors();
				}
			}else{
				System.out.println("Map "+ this.mapName+" does not exist, cannot get real point");
				System.out.println(AllMaps.getInstance().getAllMaps().keySet());
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
		if(realPoint != null) pointId= realPoint.getId();
		return pointId;
	}

	@Override
	public void setId(String id) {
		load();
		pointId = id;
		realPoint.setId(id);
	}

	@Override
	public ArrayList<IPoint> getNeighborsP() {
		load();
		return realPoint.getNeighborsP();
	}

	@Override
	public ArrayList<IPoint> getValidNeighbors(HashSet<String> whitelist) {
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
		if(mapName == null) load();
		if(realPoint != null) mapName = realPoint.getMap();
		return mapName;
	}
	
	@Override
	public int hashCode() {
		return (this.getMap() + "/" + getId()).hashCode();
	}

	@Override
	public boolean exists() {
		load();
		return realPoint != null;
	}

	@Override
	public boolean equals(Object other) {
		load();
		return realPoint.equals(other);
	}
	
	@Override
	public String toString() {
		return getMap()+"/"+getId();
	}

	@Override
	public HashMap<String, ArrayList<String>> getNeighborPointsOnOtherMaps() {
		load();
		return realPoint.getNeighborPointsOnOtherMaps();
	}

	@Override
	public boolean connectToCampus() {
		throw new UnsupportedOperationException("connectToCampus not yet implemented.");
	}

	@Override
	public String getBuilding() {
		return this.getMap().split("-")[0];
	}
}
