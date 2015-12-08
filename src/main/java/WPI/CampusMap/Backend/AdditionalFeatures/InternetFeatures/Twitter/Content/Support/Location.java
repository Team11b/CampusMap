package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Content.Support;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Location implements ILocation {

	private String name;

	private static final String locationsPath = "src/main/java/WPI/CampusMap/Backend/AdditionalFeatures/InternetFeatures/Twitter/Information/";

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

	// Possible Tags
	private static boolean filesFound = false;
	private static ArrayList<String> p85list = new ArrayList<String>();
	private static ArrayList<String> AGlist = new ArrayList<String>();
	private static ArrayList<String> AHlist = new ArrayList<String>();
	private static ArrayList<String> AKlist = new ArrayList<String>();
	private static ArrayList<String> BClist = new ArrayList<String>();
	private static ArrayList<String> BHlist = new ArrayList<String>();
	private static ArrayList<String> CClist = new ArrayList<String>();
	private static ArrayList<String> FLlist = new ArrayList<String>();
	private static ArrayList<String> GLlist = new ArrayList<String>();
	private static ArrayList<String> GPlist = new ArrayList<String>();
	private static ArrayList<String> HAlist = new ArrayList<String>();
	private static ArrayList<String> HHlist = new ArrayList<String>();
	private static ArrayList<String> PClist = new ArrayList<String>();
	private static ArrayList<String> PGlist = new ArrayList<String>();
	private static ArrayList<String> SDCClist = new ArrayList<String>();
	private static ArrayList<String> SHlist = new ArrayList<String>();
	private static ArrayList<String> SLlist = new ArrayList<String>();
	private static ArrayList<String> SRlist = new ArrayList<String>();
	private static ArrayList<String> WSlist = new ArrayList<String>();

	private Location(String name) {
		this.name = name;
	}

	@Override
	public boolean locationFound() {
		return true;
	}
	
	public String getName() {
		return this.name;
	}

	public static ILocation getBuilding(Hashtag hashtag) {

		if (!(filesFound)) {
			getPossibleTags();
		}

		String tag = hashtag.getTag();
		tag = Location.processString(tag);

		if (Location.p85list.contains(tag)) {
			return new Location(Location.p85);
		} else if (Location.AGlist.contains(tag)) {
			return new Location(Location.AG);
		} else if (Location.AHlist.contains(tag)) {
			return new Location(Location.AH);
		} else if (Location.AKlist.contains(tag)) {
			return new Location(Location.AK);
		} else if (Location.BClist.contains(tag)) {
			return new Location(Location.BC);
		} else if (Location.BHlist.contains(tag)) {
			return new Location(Location.BH);
		} else if (Location.CClist.contains(tag)) {
			return new Location(Location.CC);
		} else if (Location.FLlist.contains(tag)) {
			return new Location(Location.FL);
		} else if (Location.GLlist.contains(tag)) {
			return new Location(Location.GL);
		} else if (Location.GPlist.contains(tag)) {
			return new Location(Location.GP);
		} else if (Location.HAlist.contains(tag)) {
			return new Location(Location.HA);
		} else if (Location.HHlist.contains(tag)) {
			return new Location(Location.HH);
		} else if (Location.PClist.contains(tag)) {
			return new Location(Location.PC);
		} else if (Location.PGlist.contains(tag)) {
			return new Location(Location.PG);
		} else if (Location.SDCClist.contains(tag)) {
			return new Location(Location.SDCC);
		} else if (Location.SHlist.contains(tag)) {
			return new Location(Location.SH);
		} else if (Location.SLlist.contains(tag)) {
			return new Location(Location.SL);
		} else if (Location.SRlist.contains(tag)) {
			return new Location(Location.SR);
		} else if (Location.WSlist.contains(tag)) {
			return new Location(Location.WS);
		} else {
			return new NoLocation();
		}

	}

	private static void getPossibleTags() {
		File folder = new File(Location.locationsPath);
		File[] listOfFiles = folder.listFiles();
		String fileName, fileCore;

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				fileName = listOfFiles[i].getName();
				fileCore = fileName.substring(0, fileName.length() - 4);
				if (fileName.substring(fileName.length() - 4, fileName.length()).equals(".txt")) {
					switch (fileCore) {
					case Location.p85:
						Location.p85list = addAll(listOfFiles[i]);
						break;
					case Location.AG:
						Location.AGlist = addAll(listOfFiles[i]);
						break;
					case Location.AH:
						Location.AHlist = addAll(listOfFiles[i]);
						break;
					case Location.AK:
						Location.AKlist = addAll(listOfFiles[i]);
						break;
					case Location.BC:
						Location.BClist = addAll(listOfFiles[i]);
						break;
					case Location.BH:
						Location.BHlist = addAll(listOfFiles[i]);
						break;
					case Location.CC:
						Location.CClist = addAll(listOfFiles[i]);
						break;
					case Location.FL:
						Location.FLlist = addAll(listOfFiles[i]);
						break;
					case Location.GL:
						Location.GLlist = addAll(listOfFiles[i]);
						break;
					case Location.GP:
						Location.GPlist = addAll(listOfFiles[i]);
						break;
					case Location.HA:
						Location.HAlist = addAll(listOfFiles[i]);
						break;
					case Location.HH:
						Location.HHlist = addAll(listOfFiles[i]);
						break;
					case Location.PC:
						Location.PClist = addAll(listOfFiles[i]);
						break;
					case Location.PG:
						Location.PGlist = addAll(listOfFiles[i]);
						break;
					case Location.SDCC:
						Location.SDCClist = addAll(listOfFiles[i]);
						break;
					case Location.SH:
						Location.SHlist = addAll(listOfFiles[i]);
						break;
					case Location.SL:
						Location.SLlist = addAll(listOfFiles[i]);
						break;
					case Location.SR:
						Location.SRlist = addAll(listOfFiles[i]);
						break;
					case Location.WS:
						Location.WSlist = addAll(listOfFiles[i]);
						break;
					default:
						break;
					}
				}
			}
			Location.filesFound = true;
		}

	}

	private static ArrayList<String> addAll(File file) {
		ArrayList<String> options = new ArrayList<String>();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			for (String line; (line = br.readLine()) != null;) {
				line = processString(line);
				options.add(line);
			}
		} catch (FileNotFoundException f) {
			f.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return options;
	}

	private static String processString(String line) {
		line.replaceAll("\\s+", "");
		line.replaceAll("[^A-Za-z0-9]", "");
		return line.toLowerCase();
	}

}
