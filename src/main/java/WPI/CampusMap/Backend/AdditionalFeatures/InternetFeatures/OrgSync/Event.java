package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.OrgSync;

public class Event {
	private String title;
	private String description;
	private String link;
	private String location;
	private String start;
	private String end;
	private String organizer;
	private String type;
	
	public Event(String title, String description, String link, String location, String start, String end, String organizer){
		this.title = title;
		this.description = description;
		this.link = link;
		this.location = location;
		this.start = start;
		this.end = end;
		this.organizer = organizer;
		
//		System.out.printf("Created new event. Title: %s, Link: %s, \n\tLocation:%s, start: %s, end: %s, organizer:%s\n",
//				 title, link, location, start, end, organizer, type);
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @return the end
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * @return the organizer
	 */
	public String getOrganizer() {
		return organizer;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	
	
	
}
