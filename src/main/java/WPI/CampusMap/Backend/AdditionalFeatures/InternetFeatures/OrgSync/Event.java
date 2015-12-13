package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.OrgSync;

import java.util.Date;

public class Event {
	String title;
	String description;
	String link;
	String location;
	String start;
	String end;
	String organizer;
	String type;
	
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
	
	
	
}
