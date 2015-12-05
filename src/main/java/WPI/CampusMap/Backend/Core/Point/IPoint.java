package WPI.CampusMap.Backend.Core.Point;

import java.util.ArrayList;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.TravelPaths.PathFinding.AStar.Frontier;
import WPI.CampusMap.Backend.TravelPaths.PathFinding.Node.Node;

public interface IPoint {
	public double distance(IPoint other);
	public Coord getCoord();
	public void setCoord(Coord coord);
	public String getType();
	public void setType(String type);
	public String getId();
	public void setId(String id);
	public ArrayList<IPoint> getNeighborsP();
	public void buildFrontier(Frontier frontier, Node fromNode, IPoint goal);
	public ArrayList<IPoint> getValidNeighbors(ArrayList<String> whitelist);
	public boolean removeNeighbor(IPoint point);
	public boolean removeNeighbor(String pointId);
	public void removeAllNeighbors();
	public boolean addNeighbor(IPoint point);
	public String getMap();
	public String toString();
}
