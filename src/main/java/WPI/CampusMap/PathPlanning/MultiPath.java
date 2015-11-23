package WPI.CampusMap.PathPlanning;

import java.util.ArrayList;
import java.util.LinkedList;

import WPI.CampusMap.Backend.ConnectionPoint;

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

	public void add(Path p) {
		this.mp.add(p);
	}

	public Path get(int i) {
		return this.mp.get(i);
	}

	public void parse(Path start) {
		ArrayList<Node> bigPath = start.getPath();
		Path part = new Path();
		Node node = new Node(null, null);
		int count = 0;
		int index = 0;

		while (index < bigPath.size()) {
			part = new Path();
			node = new Node(null, null);
			count = 0;

			while ((index < bigPath.size()) && (count < 2)) {
				node = new Node(null, null);
				node = bigPath.get(index);

				if ((node.getPoint() instanceof ConnectionPoint)) {
					if ((count < 2)) {
						part.addNode(node);
						index++;
					}
					count++;
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
