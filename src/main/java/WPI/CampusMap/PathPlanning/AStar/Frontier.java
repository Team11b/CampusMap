package WPI.CampusMap.PathPlanning.AStar;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeSet;

import WPI.CampusMap.PathPlanning.Node;

/**
 * 
 * @author Jacob Zizmor
 *
 */
public class Frontier {
	private PriorityQueue<Node> pq;

	public static final Comparator<Node> stdNodeComp = new Comparator<Node>() {
		public int compare(Node n1, Node n2) {
			if (n1.getCurrentScore() < n2.getCurrentScore()) {
				return -1;
			} else if (n1.getCurrentScore() > n2.getCurrentScore()) {
				return 1;
			} else {
				return 0;
			}
		}
	};

	public Frontier(Comparator<Node> comp) {
		this.pq = new PriorityQueue<Node>(comp);
	}

	public PriorityQueue<Node> getPQ() {
		return this.pq;
	}

	public void setTree(PriorityQueue<Node> pq) {
		this.pq = pq;
	}

	public void add(Node newItem) {
		this.pq.add(newItem);
	}

	public Node getNext() {
		return this.pq.poll();
	}

	public boolean isEmpty() {
		return this.pq.isEmpty();
	}

	public int size() {
		return this.pq.size();
	}

	public boolean contains(Node other) {
		return this.pq.contains(other);
	}

	public boolean isBetter(Node other) {
		return false;
	}

}
