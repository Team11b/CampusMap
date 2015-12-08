package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Content.Tweets;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Content.Support.Hashtag;
import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Content.Support.Location;
import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Resources.TwitterImage;
import twitter4j.Status;
import twitter4j.User;

public class Tweet {
	private Status status;
	private boolean isValid;
	private TwitterImage twitterIcon;
	private User user;
	private ImageIcon profileImage;
	private LinkedList<Location> locations;

	private static final int width = 100;
	private static final int height = 100;

	public Tweet(Status status, String userID) {
		this.status = status;
		this.user = this.status.getUser();
		this.twitterIcon = TwitterImage.getInstance();
		this.locations = new LinkedList<Location>();
		setValid();
	}

	public String getScreenName() {
		return this.user.getScreenName();
	}

	public String getName() {
		return this.user.getName();
	}

	public ImageIcon getProfileIcon() {
		return this.profileImage;
	}

	public TwitterImage getTwitterIcon() {
		return this.twitterIcon;
	}

	public String getStatusText() {
		return this.status.getText();
	}
	
	public void addLocation(Location loc) {
		this.locations.add(loc);
	}

	private boolean sensitivity() {
		return this.status.isPossiblySensitive();
	}

	private void saveProfileImage() {
		try {
			BufferedImage buffer = ImageIO.read(new URL(this.user.getMiniProfileImageURL()));
			this.profileImage = new ImageIcon(
					buffer.getScaledInstance(buffer.getWidth(), buffer.getHeight(), Image.SCALE_SMOOTH));
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	private void setValid() {
		this.isValid = ((!(sensitivity()) && (hasLocation())));
	}

	private boolean hasLocation() {
		throw new UnsupportedOperationException("hasLocation() not yet implemented.");
	}
}
