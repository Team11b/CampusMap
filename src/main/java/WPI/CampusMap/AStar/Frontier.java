package WPI.CampusMap.AStar;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeSet;

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
		if (!(this.contains(other))) {
			return false;
		}

		TreeSet<Node> allHolding = new TreeSet<Node>(Frontier.stdNodeComp);
		Node holding = new Node(null, null);
		boolean found = false;
		int size = pq.size();

		for (int j = 0; j < size; j++) {
			holding = new Node(null, null);
			holding = (Node) this.pq.poll();
			if (holding.equals(other)) {
				if (holding.getCurrentScore() > other.getCurrentScore()) {
					holding = other;
					found = true;
					break;
				}
			}
			allHolding.add(holding);
		}

		if (found) {
			this.pq.add(holding);
		}

		while (!(allHolding.isEmpty())) {
			holding = new Node(null, null);
			holding = allHolding.first();
			this.pq.add(holding);
			allHolding.remove(holding);
		}

		return found;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.print(i);
			if (i == 5) {
				break;
			}
			System.out.println(" f");
		}
	}

}
