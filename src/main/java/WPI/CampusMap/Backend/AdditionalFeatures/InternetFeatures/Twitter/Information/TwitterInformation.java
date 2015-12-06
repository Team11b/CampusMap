package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Information;

public class TwitterInformation {
	private static final String publicKey = "FCK8MC0jOQza3FfE4zq8GPgsv";
	private static final String privateKey = null;
	private static final String publicAccessToken = null;
	private static final String privateAccessToken = null;
	private static final String username = "team11b";

	private TwitterInformation() {

	}

	public static String getPublicKey() {
		return TwitterInformation.publicKey;
	}

	public static String getPrivateKey() throws UnsupportedOperationException {
		if (TwitterInformation.privateKey == null) {
			throw new UnsupportedOperationException("No Private Key Entered.");
		} else {
			return TwitterInformation.privateKey;
		}
	}
	
	public static String getPublicAccessToken() throws UnsupportedOperationException{
		if (TwitterInformation.publicAccessToken == null) {
			throw new UnsupportedOperationException("No Public Access Token Entered.");
		} else {
			return TwitterInformation.publicAccessToken;
		}
	}
	
	public static String getPrivateAccessToken() throws UnsupportedOperationException {
		if (TwitterInformation.privateAccessToken == null) {
			throw new UnsupportedOperationException("No Private Access Token Entered.");
		} else {
			return TwitterInformation.privateAccessToken;
		}
	}

	/**
	 * @return the username
	 */
	public static String getUsername() {
		return TwitterInformation.username;
	}

}
