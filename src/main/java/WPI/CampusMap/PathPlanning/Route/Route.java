package WPI.CampusMap.PathPlanning.Route;

import java.util.LinkedList;

public class Route {
	private LinkedList<Instruction> route;

	public Route(LinkedList<Instruction> route) {
		this.route = route;
	}
	
	public Route() {
		this.route = new LinkedList<Instruction>();
	}

	public LinkedList<Instruction> getRoute() {
		return route;
	}

	public void setRoute(LinkedList<Instruction> route) {
		this.route = route;
	}

}
