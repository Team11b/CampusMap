package WPI.CampusMap.Backend.TravelPaths.PathFinding.Node;

import WPI.CampusMap.Backend.Core.Point.Point;

@Deprecated
public class ConnectionNode extends Node {

	private boolean entryPoint;
	public static final int travelCost = 500;
	
	public ConnectionNode(Point point, Node parent, boolean entryPoint) {
		super(point, parent, null);
		this.entryPoint = entryPoint;
	}
	
	public boolean getEntryPoint() {
		return this.entryPoint;
	}
	
	public void setEntryPoint(boolean ep) {
		this.entryPoint = ep;
	}

}
