package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Content.Tweets;

import java.util.LinkedList;

import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Content.Support.Hashtag;
import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Resources.TwitterImage;
import twitter4j.Status;
import twitter4j.User;

public class Tweet {
	private Status status;
	private LinkedList<Hashtag> hashtags;
	private boolean hasLocation;
	private TwitterImage twitterIcon;
	private User user;
	
	public Tweet(Status status, String userID) {
		this.status = status;
		this.hashtags = parseForHashtags();
		this.twitterIcon = new TwitterImage();
		this.user = this.status.getUser();
	}
	
	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the hashtags
	 */
	public LinkedList<Hashtag> getHashtags() {
		return hashtags;
	}

	/**
	 * @param hashtags the hashtags to set
	 */
	public void setHashtags(LinkedList<Hashtag> hashtags) {
		this.hashtags = hashtags;
	}

	/**
	 * @param hasBuilding the hasBuilding to set
	 */
	private void setHasBuilding(boolean hasBuilding) {
		this.hasLocation = hasBuilding;
	}

	private LinkedList<Hashtag> parseForHashtags() {
		throw new UnsupportedOperationException("Parse not yet implemented.");
	}
	
	public boolean hasLocation() {
		return this.hasLocation;
	}
}
