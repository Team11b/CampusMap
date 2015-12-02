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

	public Node find(Node other) {
		Node[] temp = this.pq.toArray(new Node[this.pq.size()]);

		System.out.println("temp.length " + temp.length);
		for (int j = 0; j < temp.length; j++) {
			System.out.println("tempj  " + temp[j] + "other " + other);
			System.out.println("temp[j].equals(other) " + temp[j].equals(other));
			if (temp[j].equals(other)) {
				return temp[j];
			}
		}
		return null;
	}

	public boolean isBetterOLD(Node other) {
		if (!(this.contains(other))) {
			this.pq.add(other);
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
	
	public void isBetter(Node other) {
		if (!(this.pq.contains(other))) {
			this.pq.add(other);
		}
		
		Node[] orig = this.pq.toArray(new Node[this.pq.size()]);
		for (int i = 0; i < orig.length; i++) {
			if (orig[i].equals(other) && (orig[i].getCurrentScore() > other.getCurrentScore())) {
				orig[i] = other;
			}
		}
		
		this.pq.clear();
		
		for (int j = 0; j < orig.length; j++) {
			this.pq.add(orig[j]);
		}
	}

	public String toString() {
		String response = "";

		Node[] temp = this.pq.toArray(new Node[this.pq.size()]);

		for (int j = 0; j < temp.length; j++) {
			response += temp[j].toString();
			response += "\n";
		}
		return response;
	}

}