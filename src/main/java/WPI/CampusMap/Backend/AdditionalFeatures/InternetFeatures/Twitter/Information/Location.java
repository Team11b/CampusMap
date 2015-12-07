package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Information;

import java.util.LinkedList;

public class Location {
	private static final String buildingHashtagsPath = "src/main/java/WPI/CampusMap/Backend/AdditionalFeatures/InternetFeatures/Twitter/Information/LocationNames.txt";
	private static final String noBuildingFound = "nobuilding";
	private static LinkedList<String> buildingHashtags; // lines starts at index 1 to ease copy over from the txt file
	
	// Buildings
	public static final String CC = "CC";
	public static final String FL = "FL";
	public static final String AK = "AK";
	public static final String PG = "PG";
	public static final String BH = "BH";
	public static final String GL = "GL";
	public static final String HH = "HH";
	public static final String PC = "PC";
	public static final String SH = "SH";
	public static final String SDCC = "SDCC";
	public static final String AH = "AH";
	public static final String AG = "AG";
	public static final String BC = "BC";
	public static final String HA = "HA";
	public static final String SL = "SL";
	public static final String WS = "WS";
	public static final String SR = "SR";
	public static final String GP = "GP";
	public static final String p85 = "85P";
	
	private Location() {
	}

}
