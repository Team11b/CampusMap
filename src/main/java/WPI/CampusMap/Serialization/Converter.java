package WPI.CampusMap.Serialization;

import java.io.File;
import java.util.Arrays;

import javax.xml.stream.XMLStreamException;

import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.XML.XML;

/**
 * 
 * @author Jacob Zizmor
 *
 */
public class Converter {
	public static String[] ignore = {"5x5Test.xml", "5x5Test2.xml", "AK.xml", "borked.xml", "testOutput.xml"};
	
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
		String[] files = Converter.getFileNames();
		
		for (int j = 0; j < files.length; j++) {
			if (!(Arrays.asList(Converter.ignore).contains(files[j]))) {
				temp = new Map();
				temp.setXML("XML/" + files[j]);
				
				try {
					temp.setAllPoints(XML.parseXML(temp));
				} catch (XMLStreamException e) {
					e.printStackTrace();
				}
				System.out.println(temp.getName());
				
				Serialization.write(temp);
			}
		}
	}
}
