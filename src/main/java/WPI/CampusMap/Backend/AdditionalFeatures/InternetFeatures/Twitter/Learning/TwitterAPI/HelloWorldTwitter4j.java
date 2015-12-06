package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Learning.TwitterAPI;

import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Information.TwitterInformation;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class HelloWorldTwitter4j {
	static String consumerKeyStr = TwitterInformation.getPublicKey();
	static String consumerSecretStr = TwitterInformation.getPrivateKey();
	static String accessTokenStr = TwitterInformation.getPublicAccessToken();
	static String accessTokenSecretStr = TwitterInformation.getPrivateAccessToken();

	public static void main(String[] args) {

		try {
			Twitter twitter = new TwitterFactory().getInstance();

			twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
			AccessToken accessToken = new AccessToken(accessTokenStr, accessTokenSecretStr);

			twitter.setOAuthAccessToken(accessToken);

			twitter.updateStatus("Post using Twitter4J!");

			System.out.println("Successfully updated the status in Twitter.");
		} catch (TwitterException te) {
			te.printStackTrace();
		}
	}
}
