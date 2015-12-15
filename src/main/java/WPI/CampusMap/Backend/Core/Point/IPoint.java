package WPI.CampusMap.Backend.Core.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;

public interface IPoint extends Serializable {
	public double distance(IPoint other);
	public Coord getCoord();
	public void setCoord(Coord coord);
	public String getType();
	public void setType(String type);
	public String getId();
	public String getDisplayName();
	public void setId(String id);
	public ArrayList<IPoint> getNeighborsP();
	public ArrayList<IPoint> getValidNeighbors(HashSet<String> whitelist);
	public boolean removeNeighbor(IPoint point);
	public boolean removeNeighbor(String pointId);
	public void removeAllNeighbors();
	public boolean addNeighbor(IPoint point);
	public String getMap();
	public String getMapDisplayName();
	public String toString();
	public boolean exists();
	public HashMap<String, ArrayList<String>> getNeighborPointsOnOtherMaps();
	public boolean connectToCampus();
	public String getBuilding();
}
