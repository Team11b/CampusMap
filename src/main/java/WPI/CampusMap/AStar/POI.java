package WPI.CampusMap.AStar;

/**
 * 
 * @author Max Stenke
 *
 */
public class POI {
	private Map linkedMap;
	
	/**
	 * Point of Interest constructor
	 * @param linkedMap additional Map this POI connects to
	 */
	public POI(Map linkedMap){
		this.linkedMap = linkedMap;
	}

	public Map getLinkedMap() {
		return linkedMap;
	}

	public void setLinkedMap(Map linkedMap) {
		this.linkedMap = linkedMap;
	}
	
	
}
