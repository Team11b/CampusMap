package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Learning;

import java.util.List;

import javax.swing.JOptionPane;

import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Information.TwitterInformation;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Timeline {
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

			List<Status> statuses = twitter.getMentionsTimeline();
			System.out.println("Showing home timeline.");
			for (Status status : statuses) {
				System.out.println(status.getUser().getName() + " : " + "#" + status.getHashtagEntities()[0].getText()
						+ " #" + status.getHashtagEntities()[1].getText());
			}

		} catch (TwitterException te) {
			JOptionPane.showMessageDialog(null, te.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			te.printStackTrace();
		}
	}
}
