package WPI.CampusMap.Backend.Core.Map;

import javax.swing.ImageIcon;

import WPI.CampusMap.Backend.Core.Point.IPoint;

public class ProxyMap implements IMap {

	@Override
	public float getScale() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setScale(float f) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImageIcon getLoadedImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPoint getPoint(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removePoint(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removePoint(IPoint point) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addPoint(IPoint point) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addEdge(IPoint point, IPoint other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeEdge(IPoint point, IPoint other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void renamePoint(IPoint p, String newName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

}
