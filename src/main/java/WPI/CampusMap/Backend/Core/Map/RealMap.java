package WPI.CampusMap.Backend.Core.Map;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.apache.commons.lang3.text.WordUtils;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Recording.Serialization.Serializer;

public class RealMap implements IMap, java.io.Serializable {

	private static final long serialVersionUID = 3434772073791894710L;
	private static final String pngLocation = "maps/";
	private float scale;
	private String name;
	private HashMap<String, RealPoint> allPoints;
	private transient ImageIcon loadedImage;
	private transient boolean unsavedChanges;

	/**
	 * Creates a map with the given name and default values
	 * 
	 * @param name
	 *            The name of the map to be created.
	 */
	public RealMap(String name) {
		this.scale = 100;
		this.name = name;
		this.allPoints = new HashMap<String, RealPoint>();
	}

	/**
	 * Makes sure that none of the points in the given map are null.
	 */
	public void validatePoints() {
		for (String key : allPoints.keySet()) {
			RealPoint point = allPoints.get(key);
			if (point != null) {
				point.constructNeighbors();
			} else {
				allPoints.remove(key);
			}
		}
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
		if (loadedImage == null) {
			loadImage();
		}
		return loadedImage;
	}

	/**
	 * loads the image of the map
	 */
	private void loadImage() {
		try {
			BufferedImage buffer = ImageIO.read(new File(pngLocation + this.getName() + ".png"));
			loadedImage = new ImageIcon(buffer.getScaledInstance(1000, 660, Image.SCALE_SMOOTH));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	@Override
	public RealPoint getPoint(String id) {
		return this.allPoints.get(id);
	}

	@Override
	public boolean removePoint(String id) {
		IPoint point = allPoints.get(id);
		return removePoint(point);
	}

	/**
	 * Removes the given point from the map array, and from the neighbor arrays
	 * of all points on the map
	 * 
	 * @param point
	 *            The point to be removed
	 * @return True if point is successfully removed, False if specified point
	 *         does note exist
	 */
	@Override
	public boolean removePoint(IPoint point) {
		// System.out.println("Remove: " + point.getId());
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
	public IPoint createPoint(Coord location)
	{
		RealPoint point = new RealPoint(location, RealPoint.HALLWAY, UUID.randomUUID().toString(), getName());
		addPoint(point);
		
		return point;
	}

	@Override
	public boolean addPoint(RealPoint point) {
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
		boolean adder = point.addNeighbor(other);
		if (!(adder)) {
			return false;
		}
		other.addNeighbor(point);
		return true;
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
	public void renamePoint(RealPoint p, String newName)
	{
		allPoints.remove(p.getId());
		allPoints.put(newName, p);
	}

	@Override
	public void save() {
		for (RealPoint point : allPoints.values()) {
			point.validateNeighbors();
		}
		Serializer.save(this);
	}

	/**
	 * Returns the hashCode of the map
	 */
	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	@Override
	public Collection<RealPoint> getAllPoints() {
		return allPoints.values();
	}

	@Override
	public boolean connectedToCampus() {
		String campusMap = AllMaps.getInstance().CampusMap;
		if(getName() == campusMap) return true;
		return Arrays.asList(((ProxyMap) AllMaps.getInstance().getMap(campusMap)).getConnectedMaps()).contains(getName());
	}

	@Override
	public String getBuilding() {
		return getName().split("-")[0];
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof IMap) {
			IMap that = (IMap) other;
			boolean result = getName().equals(that.getName());
			return result;
		}
		return false;
	}

	@Override
	public ArrayList<IPoint> pointsConnectedToOtherMaps() {
		ArrayList<IPoint> points = new ArrayList<IPoint>();
		for (IPoint point : getAllPoints()) {
			if (!point.getNeighborPointsOnOtherMaps().isEmpty()) {
				points.add(point);
			}
		}

		return points;
	}

	@Override
	public boolean unsavedChanged() {
		if(unsavedChanges){
			unsavedChanges = false;
			return true;
		}
		return false;
	}
	
	public void changed(){
		unsavedChanges = true;
	}
	
	@Override
	public String getDisplayName() {
		
		String building = getBuilding().replace("_", " ");
		if(getName().equals("Campus_Map")){
			return "Campus Map";
		}
		return building +" "+ getFloorName();
	}
	
	@Override
	public String getFloorName(){
        String floorName = getName();
		if(!getName().equals(AllMaps.getInstance().CampusMap)){
			floorName = floorName.split("-")[1].trim();
		}
		
		try{
			floorName = "Floor " + Integer.parseInt(floorName);
		}catch(NumberFormatException e){
			floorName = WordUtils.capitalizeFully(floorName.replace("_", "  ")).replace("  ", "-");
		}
		
		return floorName;
		
	}

}
