package WPI.CampusMap.PathPlanning.AStar;

import java.util.LinkedList;

import WPI.CampusMap.PathPlanning.Node;

/**
 * 
 * @author Jacob Zizmor
 *
 */
public class Explored {

	private LinkedList<Node> explored;

	public Explored() {
		this.explored = new LinkedList<Node>();
	}

	public void add(Node item) {
		this.explored.add(item);
	}

	public int size() {
		return this.explored.size();
	}

	public Node getLast() {
		return this.explored.get(this.explored.size() - 1);
	}

	public LinkedList<Node> getExplored() {
		return this.explored;
	}

	public void setExplored(LinkedList<Node> newList) {
		this.explored = newList;
	}

	public boolean containsSamePoint(Node other) {
		return false;
	}

	public boolean isBetter(Node other) {
		return false;
	}
}
