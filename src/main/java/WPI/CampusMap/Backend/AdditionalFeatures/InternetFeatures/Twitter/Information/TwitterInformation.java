package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Information;

import WPI.CampusMap.Backend.Exceptions.MissingKeyException;

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

	public static String getPrivateKey() throws MissingKeyException {
		if (TwitterInformation.privateKey == null) {
			throw new MissingKeyException("No Private Key Entered.");
		} else {
			return TwitterInformation.privateKey;
		}
	}

	public static String getPublicAccessToken() throws MissingKeyException {
		if (TwitterInformation.publicAccessToken == null) {
			throw new MissingKeyException("No Public Access Token Entered.");
		} else {
			return TwitterInformation.publicAccessToken;
		}
	}

	public static String getPrivateAccessToken() throws MissingKeyException {
		if (TwitterInformation.privateAccessToken == null) {
			throw new MissingKeyException("No Private Access Token Entered.");
		} else {
			return TwitterInformation.privateAccessToken;
		}
	}

	public static String getUsername() {
		return TwitterInformation.username;
	}

}
