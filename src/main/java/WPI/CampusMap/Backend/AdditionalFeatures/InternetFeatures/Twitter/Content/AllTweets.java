package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Content;

import java.util.LinkedList;

public class AllTweets {
	@SuppressWarnings("unused")
	private LinkedList<Tweet> alltweets;
	@SuppressWarnings("unused")
	private LinkedList<Tweet> validtweets;
	
	public AllTweets() {
		alltweets = new LinkedList<Tweet>();
		validtweets = new LinkedList<Tweet>();
	}
	
	public LinkedList<Tweet> getTweets() {
		throw new UnsupportedOperationException("getTweets not yet implemented.");
	}

}
