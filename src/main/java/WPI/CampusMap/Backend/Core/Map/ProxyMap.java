package WPI.CampusMap.Backend.Core.Map;

import javax.swing.ImageIcon;

import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Recording.Serialization.Serializer;

public class ProxyMap implements IMap {
	String mapName;
	RealMap realMap;
	
	public ProxyMap(String name){
		this.mapName = name;
	}
	
	private void load(){
		if(realMap == null){
			realMap = Serializer.realLoad(mapName);
			
			//incase the map has not been created, create a new one
			if(realMap == null){
				realMap = new RealMap(mapName);
			}else{
				realMap.validatePoints();
			}
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
			realMap.validatePoints();
			realMap.save();
			// TODO Add methods to save metadata
		}

	}
	
	@Override
	public int hashCode(){
		return mapName.hashCode();
	}

}
