package WPI.CampusMap.Backend.Core.Map;

import java.io.File;
import java.io.FilenameFilter;

public class SetMetaData {

	public static void main(String[] args) {
		//get all the files in the directory
		File[] listOfFiles = new File("maps/").listFiles((new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(".png");
		    }
		}));
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {					        
	        int ext = listOfFiles[i].getName().lastIndexOf(".png"); //snip extension     
	        String mapName = listOfFiles[i].getName().substring(0, ext);
	        ProxyMap map = new ProxyMap(mapName);
	        map.save();
	        System.out.println(map.getName());
	        System.out.println(map.getConnectedMaps());
	        System.out.println(map.getNamedPoints());
	      } 
	    }
	    
	}

}
