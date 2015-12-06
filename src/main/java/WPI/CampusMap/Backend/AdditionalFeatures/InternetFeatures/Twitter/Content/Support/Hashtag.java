package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Content.Support;

public class Hashtag {
	private String building;
	private String tag;
	
	public Hashtag(String building, String tag) {
		this.building = building;
		this.tag = tag;
	}

	/**
	 * @return the building
	 */
	public String getBuilding() {
		return building;
	}

	/**
	 * @param building the building to set
	 */
	public void setBuilding(String building) {
		this.building = building;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
