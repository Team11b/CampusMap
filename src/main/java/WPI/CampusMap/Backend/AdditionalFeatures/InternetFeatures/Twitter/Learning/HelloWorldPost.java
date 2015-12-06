package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Learning;
//package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Learning.Twitter4J;
//
//import org.apache.commons.io.IOUtils;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//
//import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Information.TwitterInformation;
//import oauth.signpost.OAuthConsumer;
//import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
//
//// http://javapapers.com/core-java/post-to-twitter-using-java/
//// the issue is that Twitter now authenticates with SSL
//public class HelloWorldPost {
//	static String consumerKeyStr = TwitterInformation.getPublicKey();
//	static String consumerSecretStr = TwitterInformation.getPrivateKey();
//	static String accessTokenStr = TwitterInformation.getPublicAccessToken();
//	static String accessTokenSecretStr = TwitterInformation.getPrivateAccessToken();
//
//	public static void main(String[] args) throws Exception {
//		OAuthConsumer oAuthConsumer = new CommonsHttpOAuthConsumer(consumerKeyStr, consumerSecretStr);
//		oAuthConsumer.setTokenWithSecret(accessTokenStr, accessTokenSecretStr);
//
//		HttpPost httpPost = new HttpPost(
//				"http://api.twitter.com/1.1/statuses/update.json?status=Hello%20Twitter%20World.");
//
//		oAuthConsumer.sign(httpPost);
//
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpResponse httpResponse = httpClient.execute(httpPost);
//
//		int statusCode = httpResponse.getStatusLine().getStatusCode();
//		System.out.println(statusCode + ':' + httpResponse.getStatusLine().getReasonPhrase());
//		System.out.println(IOUtils.toString(httpResponse.getEntity().getContent()));
//
//	}
//}
