package WPI.CampusMap.Backend.Core.Map;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;

import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.Core.Point.ProxyPoint;

public class RedoSerialization {
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
	        
	        for(IPoint point : map.getAllPoints()){
	        	Collection<IPoint> neighbors = point.getNeighborsP();	
	        	point.removeAllNeighbors();
	        	for(IPoint neighbor: neighbors){
	        		String fullName = neighbor.toString();
	        		if(neighbor.getMap().equals("CampusMap")) fullName = "Campus_Map/"+neighbor.toString().split("/")[1];
	        		point.addNeighbor(new ProxyPoint(fullName));
	        	}
	        }

        	map.save();
	      } 
	    }
	    
	}

}
