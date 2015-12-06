package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Information;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class TwitterInformation {
	private static final String publicKey = "FCK8MC0jOQza3FfE4zq8GPgsv";
	private static final String privateKey = null;
	private static final String publicAccessToken = null;
	private static final String privateAccessToken = null;
	private static final String username = "team11b";
	private static LinkedList<String> buildingHashtags;
	private static final String buildingHashtagsPath = "src/main/java/WPI/CampusMap/Backend/AdditionalFeatures/InternetFeatures/Twitter/Information/LocationNames.txt";
	private static final String noBuildingFound = "nobuilding";

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

	public static String getPublicAccessToken() throws UnsupportedOperationException {
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

	public static String getUsername() {
		return TwitterInformation.username;
	}

	public static String getBuilding(String hastag) {
		if (TwitterInformation.buildingHashtags == null) {
			TwitterInformation.loadBuildingHashtags();
		}

		return TwitterInformation.noBuildingFound;
	}

	private static void loadBuildingHashtags() {
		try {
			FileReader fileReader = new FileReader(TwitterInformation.buildingHashtagsPath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line = bufferedReader.readLine();
			LinkedList<String> lines = new LinkedList<String>();

			do {
					line.replaceAll("\\s","");
					line.replaceAll("[^A-Za-z0-9]", "");
					
					if (line.length() > 0) {
						lines.add(line);
					}
					
					line = bufferedReader.readLine();
			} while (line != null);
			
			bufferedReader.close();
			fileReader.close();
			
			TwitterInformation.buildingHashtags = lines;
		}
		catch (FileNotFoundException f) {
			f.printStackTrace();
		}
		catch (IOException i) {
			i.printStackTrace();
		}
	}

}
