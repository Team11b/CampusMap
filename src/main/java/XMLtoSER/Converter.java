package XMLtoSER;

import java.io.File;

import WPI.CampusMap.AStar.Map;

public class Converter {
	String[] ignore = {"5x5Test.xml", "5x5Test2.xml", "AK.xml", "borked.xml", "testOutput.xml"};
	
	public static String[] getFileNames() {
		File folder = new File("XML/");
		File[] listOfFiles = folder.listFiles();
		String[] listOfNames = new String[listOfFiles.length];
		
		for (int j = 0; j < listOfFiles.length; j++) {
			listOfNames[j] = listOfFiles[j].getName();
		}		
		
		return listOfNames;
	}
	
	public static void main(String[] args) {
		Map temp = new Map();		
	}
}
