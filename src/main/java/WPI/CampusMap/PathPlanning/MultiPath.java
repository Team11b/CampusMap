package WPI.CampusMap.PathPlanning;

import java.util.LinkedList;

/**
 * 
 * @author Jacob Zizmor
 *
 */
public class MultiPath {
	LinkedList<Path> mp;
	
	public MultiPath () {
		mp = new LinkedList<Path>();
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

}
