package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Resources;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class TwitterImage {
	private static TwitterImage ti;
	private ImageIcon icon;
	
	public static final int width = 16;
	public static final int height = 16;

	private TwitterImage() {
		icon = new ImageIcon();
		this.loadIcon();
	}
	
	private void loadIcon() {
		try {
			BufferedImage buffer = ImageIO.read(new File("src/main/java/WPI/CampusMap/Backend/AdditionalFeatures/InternetFeatures/Twitter/Resources/TwitterLogo.png"));
			this.icon = new ImageIcon(buffer.getScaledInstance(width, height, Image.SCALE_SMOOTH));
		}
		catch (IOException i) {
			i.printStackTrace();
		}		
	}
	
	public static TwitterImage getInstance() {
		if (TwitterImage.ti == null) {
			TwitterImage.ti = new TwitterImage();
		}
		return TwitterImage.ti;
	}
	
	public ImageIcon getIcon() {
		return this.icon;
	}

}
