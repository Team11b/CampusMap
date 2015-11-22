package WPI.CampusMap.PathPlanning.AStar;

import java.util.LinkedList;

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
		for (int i = 0; i < this.explored.size(); i++) {
			if (other.getPoint().equals(this.explored.get(i).getPoint())) {
				return true;
			}
		}
		return false;
	}

	public boolean isBetter(Node other) {
		if (!(this.containsSamePoint(other))) {
			return false;
		}

		for (int j = 0; j < this.explored.size(); j++) {
			if (other.getPoint().equals(this.explored.get(j).getPoint())) {
				if (other.getCurrentScore() < this.explored.get(j).getCurrentScore()) {
					return true;
				} else {
					return false;
				}
			}
		}

		return false;
	}
}
