package WPI.CampusMap.Backend.Core.Map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.swing.ImageIcon;

import WPI.CampusMap.Backend.Core.Point.AllPoints;
import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Recording.Serialization.Serializer;

public class ProxyMap implements IMap, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8163839906797395312L;
	private String mapName;
	private transient RealMap realMap;
	private String[] namedPoints;
	private String[] connectedMaps;
	
	public ProxyMap(String name){
		this.mapName = name;
        namedPoints = new String[0];
        connectedMaps = new String[0]; 

	}
	
	private void load(){
		if(realMap == null){
//			System.out.println("Connected Map:" + Arrays.toString(connectedMaps));
			realMap = Serializer.realLoad(mapName);
			
			//incase the map has not been created, create a new one
			if(realMap == null){
				//System.out.printf("Real Map (%s) not found, making a new one.\n",mapName);
				realMap = new RealMap(mapName);
			}
			realMap.validatePoints();
		}
	}

	@Override
	public float getScale() {
		load();
		return realMap.getScale();
	}

	@Override
	public void setScale(float scale) {
		load();
		realMap.setScale(scale);
	}

	@Override
	public String getName() {
		return this.mapName;
	}

	@Override
	public ImageIcon getLoadedImage() {
		load();
		return realMap.getLoadedImage();
	}

	@Override
	public RealPoint getPoint(String id) {
		load();
		return realMap.getPoint(id);
	}

	@Override
	public boolean removePoint(String id) {
		load();
		return realMap.removePoint(id);
	}

	@Override
	public boolean removePoint(IPoint point) {
		load();
		return realMap.removePoint(point);
	}

	@Override
	public boolean addPoint(RealPoint point) {
		load();
		return realMap.addPoint(point);
	}

	@Override
	public boolean addEdge(IPoint point, IPoint other) {
		load();
		return realMap.addEdge(point, other);
	}

	@Override
	public boolean removeEdge(IPoint point, IPoint other) {
		load();
		return realMap.removeEdge(point, other);
	}

	@Override
	public void renamePoint(RealPoint p, String newName) {
		load();
		realMap.renamePoint(p, newName);
	}

	@Override
	public void save() {
		if(realMap != null){
//			System.out.println(this.getName());
			realMap.save();
			
			ArrayList<String> tempNamedPoints = new ArrayList<String>(8);
			HashSet<String> tempConnectedMaps = new HashSet<String>(6);
			
			for(RealPoint point: realMap.getAllPoints()){
				String type = point.getType();
				if(!point.getId().contains("-")){
//					System.out.println("Named Point");
					tempNamedPoints.add(point.toString());
				}
				for(String connectedMap: point.getNeighborPointsOnOtherMaps().keySet()){
//					System.out.println("Connecting map: " + connectedMap);
					tempConnectedMaps.add(connectedMap);
				}
			}
			namedPoints = tempNamedPoints.toArray(new String[tempNamedPoints.size()]);
			connectedMaps = tempConnectedMaps.toArray(new String[tempConnectedMaps.size()]);
			
			Serializer.save(this);
		}

	}
	
	@Override
	public int hashCode(){
		return mapName.hashCode();
	}

	@Override
	public Collection<RealPoint> getAllPoints() {
		load();
		return realMap.getAllPoints();
	}
	
	public String[] getConnectedMaps() {
		return connectedMaps;
	}
	
	public String[] getNamedPoints() {
		return namedPoints;
	}
	
	public void onLoad(){
        AllPoints.getInstance().addAllPoints(namedPoints);
    }

	@Override
	public boolean connectedToCampus() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString(){
		return mapName;
	}

}
