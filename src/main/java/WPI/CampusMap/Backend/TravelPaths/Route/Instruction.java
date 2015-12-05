package WPI.CampusMap.Backend.TravelPaths.Route;

import WPI.CampusMap.Backend.TravelPaths.PathFinding.Node.Node;

/**
 * 
 * @author Jacob Zizmor
 *
 */
public class Instruction {
	private String instruction;
	private double distance;
	private Node start;
	private String map;
	private int index;

	public Instruction(String instruction, double distance, Node start, int index) {
		this.instruction = instruction;
		this.distance = distance;
		this.start = start;
		this.map = this.start.getPoint().getMap();
		this.index = index;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Node getStart() {
		return start;
	}

	public void setStart(Node start) {
		this.start = start;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public String toString() {
		return this.instruction + " @ " + this.map;
	}

}
