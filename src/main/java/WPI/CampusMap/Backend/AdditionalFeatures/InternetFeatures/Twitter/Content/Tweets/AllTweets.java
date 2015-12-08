package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Content.Tweets;

import java.util.LinkedList;
import java.util.List;

import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Information.TwitterInformation;
import WPI.CampusMap.Backend.Exceptions.MissingKeyException;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class AllTweets {
	private LinkedList<Tweet> validtweets;
	private LinkedList<Tweet> newtweets;
	
	
	static String consumerKeyStr;
	static String consumerSecretStr;
	static String accessTokenStr;
	static String accessTokenSecretStr;
	
	
	public AllTweets() {
		validtweets = new LinkedList<Tweet>();
		newtweets = new LinkedList<Tweet>();
	}
	
	public LinkedList<Tweet> getValidTweets() {
		update();
		return this.validtweets;
	}
	
	public LinkedList<Tweet> getNewTweets() {
		update();
		LinkedList<Tweet> copy = (LinkedList<Tweet>) this.newtweets.clone();
		this.newtweets = new LinkedList<Tweet>();
		return copy;
	}

	private void update() {
		consumerKeyStr = TwitterInformation.getPublicKey();
		
		try {
			consumerSecretStr = TwitterInformation.getPrivateKey();
			accessTokenStr = TwitterInformation.getPublicAccessToken();
			accessTokenSecretStr = TwitterInformation.getPrivateAccessToken();
		} catch (MissingKeyException e) {
			e.printStackTrace();
		}
		
		try {
			Twitter twitter = new TwitterFactory().getInstance();

			twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
			AccessToken accessToken = new AccessToken(accessTokenStr, accessTokenSecretStr);

			twitter.setOAuthAccessToken(accessToken);

			List<Status> statuses = twitter.getMentionsTimeline();
			Tweet newTweet = new Tweet();
			
			for (Status status : statuses) {
				newTweet = new Tweet(status);
				
				if (newTweet.getValid()) {
					if (!(validtweets.contains(newTweet))) {
						newtweets.add(newTweet);
						validtweets.add(newTweet);
					}
				}
			}

		} catch (TwitterException te) {
			te.printStackTrace();
		}
		
	}
	
	

}
