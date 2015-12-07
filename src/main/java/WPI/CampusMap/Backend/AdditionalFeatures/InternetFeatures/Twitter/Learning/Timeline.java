package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Learning;

import java.util.List;

import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Information.TwitterInformation;
import WPI.CampusMap.Backend.Exceptions.MissingKeyException;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Timeline {
	static String consumerKeyStr;
	static String consumerSecretStr;
	static String accessTokenStr;
	static String accessTokenSecretStr;

	public static void main(String[] args) throws MissingKeyException {
		consumerKeyStr = TwitterInformation.getPublicKey();
		consumerSecretStr = TwitterInformation.getPrivateKey();
		accessTokenStr = TwitterInformation.getPublicAccessToken();
		accessTokenSecretStr = TwitterInformation.getPrivateAccessToken();
		
		try {
			Twitter twitter = new TwitterFactory().getInstance();

			twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
			AccessToken accessToken = new AccessToken(accessTokenStr, accessTokenSecretStr);

			twitter.setOAuthAccessToken(accessToken);

			List<Status> statuses = twitter.getMentionsTimeline();
			System.out.println("Showing home timeline.");
			for (Status status : statuses) {
				System.out.println(status.getUser().getName() + ": @" + status.getUser().getScreenName() + " : " + "#" + status.getHashtagEntities()[0].getText() + " #" + status.getHashtagEntities()[1].getText());
			}

		} catch (TwitterException te) {
			te.printStackTrace();
		}
	}
}
