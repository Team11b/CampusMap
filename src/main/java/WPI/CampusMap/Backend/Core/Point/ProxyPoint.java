package WPI.CampusMap.Backend.Core.Point;

import java.io.Serializable;
import java.util.ArrayList;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.TravelPaths.PathFinding.AStar.Frontier;
import WPI.CampusMap.Backend.TravelPaths.PathFinding.Node.Node;

public class ProxyPoint implements IPoint, Serializable {

	private static final long serialVersionUID = 5426983372569624985L;

	@Override
	public double distance(IPoint other) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Coord getCoord() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCoord(Coord coord) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<IPoint> getNeighborsP() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void buildFrontier(Frontier frontier, Node fromNode, IPoint goal) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<IPoint> getValidNeighbors(ArrayList<String> whitelist) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeNeighbor(IPoint point) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeNeighbor(String pointId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeAllNeighbors() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean addNeighbor(IPoint point) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getMap() {
		// TODO Auto-generated method stub
		return null;
	}

}
