package WPI.CampusMap.Backend.Point;

import java.util.ArrayList;

import WPI.CampusMap.Backend.Coord;
import WPI.CampusMap.PathPlanning.Node;
import WPI.CampusMap.PathPlanning.AStar.Frontier;

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
