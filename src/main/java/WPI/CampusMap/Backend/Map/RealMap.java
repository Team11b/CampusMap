package WPI.CampusMap.Backend.Map;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import WPI.CampusMap.Backend.Coord;
import WPI.CampusMap.Backend.Point.IPoint;
import WPI.CampusMap.Backend.Point.Point;
import WPI.CampusMap.Serialization.Serializer;

public class RealMap implements IMap, java.io.Serializable {

	private static final long serialVersionUID = 3434772073791894710L;
	private static final String pngLocation = "maps/";
	private float scale;
	private String name;
	private HashMap<String, IPoint> allPoints;
	private ImageIcon loadedImage;
	private static HashMap<String, Map> allMaps = new HashMap<String, Map>();
	
	/**
	 * Creates a map from an xml file. Default values are used if the xml cannot
	 * be parsed.
	 * 
	 * @param name
	 *            The name of the map to be created.
	 */
	public RealMap(String name){
		this.scale = 100;
		this.name = name;
		this.allPoints = new HashMap<String, IPoint>();
		
		//TODO implement new serialize read
//		Map testMap = Serializer.read(name);
//		if(testMap != null){
//			setScale(testMap.getScale());
//			setAllPoints(testMap.getAllPoints());
//		}
//		Map.allMaps.remove(name);
//		Map.allMaps.put(name,this);
	}

	/**
	 * Creates a new default map.
	 */
	public RealMap() {
		this.scale = 0;
		this.name = "new_map";

		this.allPoints = new HashMap<String, IPoint>();
	}
	
	
	@Override
	public float getScale() {
		return this.scale;
	}

	@Override
	public void setScale(float scale) {
		float oldScale = this.scale;
		this.scale = scale;

		float ratio = (float) scale / (float) oldScale;

		if (this.allPoints != null) {
			String[] keys = this.allPoints.keySet().toArray(new String[this.allPoints.size()]);
			for (String p : keys) {
				Coord oldCoord = this.allPoints.get(p).getCoord();
				oldCoord.setX(oldCoord.getX() / ratio);
				oldCoord.setY(oldCoord.getY() / ratio);
			}
		}

	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public ImageIcon getLoadedImage() {
		if(loadedImage == null)
		{
			try {
				loadImage();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return loadedImage;
	}
	
	private void loadImage() throws IOException {
		try {
			BufferedImage buffer = ImageIO.read(new File(pngLocation + this.getName() + ".png"));
			loadedImage = new ImageIcon(buffer.getScaledInstance(1000, 660, Image.SCALE_SMOOTH));
		} catch (Exception e) {

		}
	}

	@Override
	public IPoint getPoint(String id) {
		return this.allPoints.get(id);
	}

	@Override
	public boolean removePoint(String id) {
		IPoint point = allPoints.get(id);
		if (point != null) {
			for (IPoint pointN : point.getNeighborsP()) {
				if (!pointN.removeNeighbor(point))
					return false;
			}
			point.removeAllNeighbors();
			allPoints.remove(point.getId());
			return true;
		}
		return false;
	}

	@Override
	public boolean removePoint(IPoint point) {
		//System.out.println("Remove: " + point.getId());
		ArrayList<IPoint> neighbors = point.getNeighborsP();
		for (IPoint pointN : neighbors) {
			if (!pointN.removeNeighbor(point))
				return false;
		}
		point.removeAllNeighbors();
		allPoints.remove(point.getId());
		
		return true;
	}

	@Override
	public boolean addPoint(IPoint point) {
		if (this.allPoints.containsKey(point.getId()))
			return false;

		this.allPoints.put(point.getId(), point);
		return true;
	}

	@Override
	public boolean addEdge(IPoint point, IPoint other) {
		if (point.equals(other)) {
			return false;
		}
		if (this.allPoints.containsKey(point.getId()) && this.allPoints.containsKey(other.getId())) {
			boolean adder = point.addNeighbor(other);
			if (!(adder)) {
				return false;
			}
			other.addNeighbor(point);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeEdge(IPoint point, IPoint other) {
		if (this.allPoints.containsKey(point.getId()) && (this.allPoints.containsKey(other.getId()))) {
			boolean remover = point.removeNeighbor(other);
			if (!(remover)) {
				return false;
			}
			other.removeNeighbor(point);
			return true;
		}
		return false;
	}

	@Override
	public void renamePoint(IPoint p, String newName) {
		allPoints.remove(p.getId());
		allPoints.put(newName, p);
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

}
