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
	private static AllTweets instance;
	private LinkedList<Tweet> tweets;
	private LinkedList<Tweet> newtweets;

	static String consumerKeyStr;
	static String consumerSecretStr;
	static String accessTokenStr;
	static String accessTokenSecretStr;

	private AllTweets() {
		tweets = new LinkedList<Tweet>();
		newtweets = new LinkedList<Tweet>();
	}

	public AllTweets getInstance() {
		if (AllTweets.instance == null) {
			synchronized (AllTweets.class) {
				if (AllTweets.instance == null) {
					instance = new AllTweets();
				}
			}
		}
		return AllTweets.instance;
	}

	public LinkedList<Tweet> getAllTweets() {
		update();
		return this.tweets;
	}

	public LinkedList<Tweet> getNewTweets() {
		update();
		return this.newtweets;
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
					if (!(tweets.contains(newTweet))) {
						newtweets.add(newTweet);
						tweets.add(newTweet);
					}
				}
			}

		} catch (TwitterException te) {
			te.printStackTrace();
		}

	}

}
