package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Content.Tweets;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Content.Support.Hashtag;
import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Content.Support.ILocation;
import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Content.Support.Location;
import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Resources.TwitterImage;
import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.User;

public class Tweet {
	private Status status;
	private boolean isValid;
	private TwitterImage twitterIcon;
	private User user;
	private ImageIcon profileImage;
	private LinkedList<Hashtag> hashtags;

	private static final int width = 100;
	private static final int height = 100;

	public Tweet(Status status) {
		this.status = status;
		this.user = this.status.getUser();
		this.twitterIcon = TwitterImage.getInstance();
		this.hashtags = parseValidHashtags();
		saveProfileImage();
		setValid();
	}

	private LinkedList<Hashtag> parseValidHashtags() {
		HashtagEntity[] tags = this.status.getHashtagEntities();
		LinkedList<Hashtag> validtags = new LinkedList<Hashtag>();
		
		ILocation loc;
		
		for (int j = 0; j < tags.length; j++) {
			loc = Location.getBuilding(tags[j]);
			
			if (loc.locationFound()) {
				validtags.add(new Hashtag((Location) loc));
			}
		}
		
		return validtags;
	}

	public Tweet() {
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
	
	public void addHashtag(Hashtag tag) {
		this.hashtags.add(tag);
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
	
	public boolean getValid() {
		return this.isValid;
	}

	private boolean hasLocation() {
		throw new UnsupportedOperationException("hasLocation() not yet implemented.");
	}
}
