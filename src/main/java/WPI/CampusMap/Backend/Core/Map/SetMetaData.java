package WPI.CampusMap.Backend.Core.Map;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

public class SetMetaData {

	public static void main(String[] args) {
		//get all the files in the directory
		File[] listOfFiles = new File("maps/").listFiles((new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(".png");
		    }
		}));
		System.out.println("Done creating list files, saving metadata");
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {					        
	        int ext = listOfFiles[i].getName().lastIndexOf(".png"); //snip extension     
	        String mapName = listOfFiles[i].getName().substring(0, ext);
	        ProxyMap map = new ProxyMap(mapName);
	        map.getScale();
	        map.save();
	        System.out.println();
	        System.out.println(map.getName());
	        System.out.println("Connected Maps: "+Arrays.toString(map.getConnectedMaps()));
	        System.out.println("Named Points: "+Arrays.toString(map.getNamedPoints()));
	      } 
	    }
	    
	}

}
