package WPI.CampusMap.Backend.TravelPaths_DEPRECATED.PathFinding.AStar;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.PathPlanning.Node;

/**
 * 
 * @author Jacob Zizmor
 *
 */
public class Frontier {
	private PriorityQueue<Node> pq;
	private HashMap<IPoint, Node> visited;
	private HashMap<IPoint, Node> frontierSet;
	

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

	public void addToFrontier(Node beta) 
	{
		if(frontierSet.containsKey(beta.getPoint()))
		{
			Node old = frontierSet.get(beta.getPoint());
			if(beta.getCurrentScore() < old.getCurrentScore())
			{
				frontierSet.put(beta.getPoint(), beta);
				this.pq.remove(old);
				this.pq.add(beta);
			}
		}
		else if(!visited.containsKey(beta.getPoint()))
		{
			this.pq.add(beta);
			this.frontierSet.put((IPoint) beta.getPoint(), beta);
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

	public Node getNext() {
		//needed for frontier test
		// TODO Auto-generated method stub
		return null;
	}

	public boolean contains(Node eta) {
		//needed for frontier test
		// TODO Auto-generated method stub
		return false;
	}

}