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

	/**
	 * Adds a path to the MultiPath
	 * @param p a Path to add
	 */
	public void add(Path p) {
		this.mp.add(p);
	}

	public Path get(int i) {
		return null;
	}

	/**
	 * Parses an input path and splits it into multiple paths
	 * Each sub-path is added to this.mp
	 * A path is separated between two adjacent Nodes with ConnectionPoints
	 * @param start a path to be split
	 */
	public void parse(Path start) {
	}
	
	public int size() {
		return this.mp.size();
	}

}