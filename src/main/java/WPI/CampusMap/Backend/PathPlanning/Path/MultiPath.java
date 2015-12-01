package WPI.CampusMap.Backend.PathPlanning.Path;

import java.util.ArrayList;
import java.util.LinkedList;

import WPI.CampusMap.Backend.Core.Point.ConnectionPoint;
import WPI.CampusMap.Backend.PathPlanning.Node;

/**
 * 
 * @author Jacob Zizmor
 *
 */
public class MultiPath {
	LinkedList<Path> mp;

	public MultiPath() {
		mp = new LinkedList<Path>();
	}

	public MultiPath(Path start) {
		mp = new LinkedList<Path>();
		parse(start);
	}

	public LinkedList<Path> getMp() {
		return mp;
	}

	public void setMp(LinkedList<Path> mp) {
		this.mp = mp;
	}

	/**
	 * Adds a path to the MultiPath
	 * 
	 * @param p
	 *            a Path to add
	 */
	public void add(Path p) {
		this.mp.add(p);
	}

	public Path get(int i) {
		return this.mp.get(i);
	}

	/**
	 * Parses an input path and splits it into multiple paths Each sub-path is
	 * added to this.mp A path is separated between two adjacent Nodes with
	 * ConnectionPoints
	 * 
	 * @param start
	 *            a path to be split
	 */
	public void parse(Path start) {
		ArrayList<Node> bigPath = start.getPath();
		Path part = new Path();
		Node node = new Node(null, null);
		int count = 0;
		int index = 0;

		if(!(bigPath.get(0).getPoint() instanceof ConnectionPoint)){
			count++;
		}
		
		while (index < bigPath.size()) {
			part = new Path();
			node = new Node(null, null);

			while ((index < bigPath.size())) {
				node = new Node(null, null);
				node = bigPath.get(index);

				if ((node.getPoint() instanceof ConnectionPoint)) {
					part.addNode(node);
					index++;
					count++;
					if(count == 2){
						count = 0;
						break;
					}
					
				} else {
					part.addNode(node);
					index++;
				}
			}
			this.mp.add(part);
		}
	}

	public int size() {
		return this.mp.size();
	}

}
