package WPI.CampusMap.PathPlanning.AStar;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.PathPlanning.Node;

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