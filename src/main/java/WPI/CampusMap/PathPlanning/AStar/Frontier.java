package WPI.CampusMap.PathPlanning.AStar;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.TreeSet;

import WPI.CampusMap.Backend.*;
import WPI.CampusMap.PathPlanning.*;

/**
 * 
 * @author Jacob Zizmor
 *
 */
public class Frontier {
	private PriorityQueue<Node> pq;
	private HashMap<Point, Node> visited;
	private HashMap<Point, Node> frontierSet;
	

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

	public Frontier() {
		this.pq = new PriorityQueue<Node>(stdNodeComp);
		this.visited = new HashMap<>();
		this.frontierSet = new HashMap<>();
	}

	public void addToFrontier(Node newItem) 
	{
		if(frontierSet.containsKey(newItem.getPoint()))
		{
			Node old = frontierSet.get(newItem.getPoint());
			if(newItem.getCurrentScore() < old.getCurrentScore())
			{
				frontierSet.put(newItem.getPoint(), newItem);
				this.pq.remove(old);
				this.pq.add(newItem);
			}
		}
		else if(!visited.containsKey(newItem.getPoint()))
		{
			this.pq.add(newItem);
			this.frontierSet.put(newItem.getPoint(), newItem);
		}
	}
	
	public void addToVisited(Node node)
	{
		if(node == null || visited == null || node.getPoint() == null)
			System.out.println("T");
		visited.put(node.getPoint(), node);
	}

	public Node visitFront()
	{
		//System.out.println(pq);
		
		Node front = this.pq.poll();
		/*if(front == null)
			return null;*/
		
		frontierSet.remove(front.getPoint());
		addToVisited(front);
		
		return front;
	}

	public boolean isEmpty() {
		return this.pq.isEmpty();
	}

	public int size() {
		return this.pq.size();
	}

<<<<<<< HEAD
	public boolean contains(Node other) {
		return this.pq.contains(other);
	}

	public Node find(Node other) {
		Node[] temp = this.pq.toArray(new Node[this.pq.size()]);

//		System.out.println("temp.length " + temp.length);
		for (int j = 0; j < temp.length; j++) {
//			System.out.println("tempj  " + temp[j] + "other " + other);
//			System.out.println("temp[j].equals(other) " + temp[j].equals(other));
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

=======
>>>>>>> UI-Branch
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