package WPI.CampusMap.AStar;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Frontier<Item> {
	private PriorityQueue<Item> pq;

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

	public Frontier(Comparator<Item> comp) {
		this.pq = new PriorityQueue<Item>(comp);
	}

	public PriorityQueue<Item> getPQ() {
		return this.pq;
	}

	public void setTree(PriorityQueue<Item> pq) {
		this.pq = pq;
	}

	public void add(Item newItem) {
		this.pq.add(newItem);
	}

	public Item getNext() {
		return this.pq.poll();
	}

	public boolean isEmpty() {
		return this.pq.isEmpty();
	}

	public int size() {
		return this.pq.size();
	}

}
