package WPI.CampusMap.PathPlanning;

import WPI.CampusMap.Backend.Point;

public class ConnectionNode extends Node {

	private boolean entryPoint;
	
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
