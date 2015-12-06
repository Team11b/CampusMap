package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Content.Tweets;

import java.util.LinkedList;

public class AllTweets {
	private LinkedList<Tweet> alltweets;
	private LinkedList<Tweet> validtweets;
	
	public AllTweets() {
		alltweets = new LinkedList<Tweet>();
		validtweets = new LinkedList<Tweet>();
	}
	
	public LinkedList<Tweet> getTweets() {
		throw new UnsupportedOperationException("getTweets not yet implemented.");
	}

}
