package WPI.CampusMap.Backend.TravelPaths_DEPRECATED.PathFinding.Node;

import WPI.CampusMap.Backend.Core.Point.RealPoint;

@Deprecated
public class ConnectionNode extends Node {

	private boolean entryPoint;
	public static final int travelCost = 500;
	
	public ConnectionNode(RealPoint point, Node parent, boolean entryPoint) {
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
