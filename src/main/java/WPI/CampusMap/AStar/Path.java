package WPI.CampusMap.AStar;

public class Path {

	private Node path;

	public Path(Node path) {
		this.path = path;
	}

	public Path getTurns() {
		return new Path(null); // TODO: getTurns()
	}

	public Node getPath() {
		return path;
	}

	public void setPath(Node path) {
		this.path = path;
	}

}
