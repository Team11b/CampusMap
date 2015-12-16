package WPI.CampusMap.Frontend.Graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.ImageIcon;

import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Map.IMap;
import WPI.CampusMap.Backend.PathPlanning.Path;
import WPI.CampusMap.Frontend.Graphics.Print.PrintGraphicalMap;

public class RouteImageCreator
{
	private Path path;
	private BufferedImage[] images;
	
	public RouteImageCreator(Path path)
	{
		this.path = path;
		render();
	}
	
	public BufferedImage[] getImages()
	{
		return images;
	}
	
	private void render()
	{
		LinkedList<BufferedImage> imageList = new LinkedList<>();
		
		for(Path.Section section : path)
		{
			IMap map = AllMaps.getInstance().getMap(section.getMap());
			ImageIcon mapImage = map.getLoadedImage();
			
			BufferedImage image = new BufferedImage(mapImage.getIconWidth(), mapImage.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			
			Graphics2D graphics = image.createGraphics();
			graphics.setClip(0, 0, image.getWidth(), image.getHeight());
			
			PrintGraphicalMap gfxMap = new PrintGraphicalMap(map.getName(), section);
			gfxMap.spawnMap();
			gfxMap.onDraw(graphics);
			
			imageList.add(image);
		}
		
		images = new BufferedImage[imageList.size()];
		imageList.toArray(images);
	}
}
