package WPI.CampusMap.Backend.PathPlanning.Route;

import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.PathPlanning.Node;

/**
 * 
 * @author Jacob Zizmor
 *
 */
public class Instruction {
	private String instruction;
	private double distance;
	private IPoint start;
	private String map;
	private int index;

	public Instruction(String instruction, double distance, IPoint start, int index) {
		this.instruction = instruction;
		this.distance = distance;
		this.start = start;
		this.map = this.start.getMap();
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

	public IPoint getStart() {
		return start;
	}

	public void setStart(IPoint start) {
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
