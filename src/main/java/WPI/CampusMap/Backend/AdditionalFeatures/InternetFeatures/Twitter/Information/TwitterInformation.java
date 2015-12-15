package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Information;

import java.util.LinkedList;

import WPI.CampusMap.Backend.Exceptions.MissingKeyException;

public class TwitterInformation {
	private static final String publicKey = "FCK8MC0jOQza3FfE4zq8GPgsv";
	private static final String privateKey = null;
	private static final String publicAccessToken = null;
	private static final String privateAccessToken = null;
	private static final String username = "team11b";
	private static final String buildingHashtagsPath = "src/main/java/WPI/CampusMap/Backend/AdditionalFeatures/InternetFeatures/Twitter/Information/LocationNames.txt";
	private static final String noBuildingFound = "nobuilding";
	private static LinkedList<String> buildingHashtags; // lines starts at index
														// 1 to ease copy over
														// from the txt file

	// Buildings
	public static final String CC = "Campus_Center";
	public static final String FL = "Fuller_Labs";
	public static final String AK = "Atwater_Kent";
	public static final String PG = "PG";
	public static final String BH = "Boyton_Hall";
	public static final String GL = "Gordon_Library";
	public static final String HH = "Higgins_House";
	public static final String PC = "Project_Center";
	public static final String SH = "Stratton_Hall";
	public static final String SDCC = "SDCC";
	public static final String AH = "Alden_Hall";
	public static final String AG = "AG";
	public static final String BC = "BC";
	public static final String HA = "Harrington_Auditorium";
	public static final String SL = "Salisbury_Labs";
	public static final String WS = "Washburn_Shops";
	public static final String SR = "SR";
	public static final String GP = "GP";
	public static final String p85 = "85P";

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

	public static String getBuilding(String hashtag) {
		throw new UnsupportedOperationException("getBuilding not yet implemented.");
	}

	private static void loadBuildingHashtags() {
		throw new UnsupportedOperationException("loadBuildingHashtags not yet implemented.");
	}

	private static String fixHashtag(String hashtag) {
		throw new UnsupportedOperationException("fixHashtag not yet implemented.");
	}

}
