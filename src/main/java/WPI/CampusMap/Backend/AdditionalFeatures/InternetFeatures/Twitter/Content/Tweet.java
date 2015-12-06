package WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Twitter.Content;

import java.util.LinkedList;

import twitter4j.Status;

public class Tweet {
	private Status status;
	private LinkedList<Hashtag> hashtags;
	private String userID;
	private boolean hasBuilding;
	
	public Tweet(Status status, String userID) {
		this.status = status;
		this.userID = userID;
		this.hashtags = parseForHashtags();
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
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * @return the hasBuilding
	 */
	public boolean isHasBuilding() {
		return hasBuilding;
	}

	/**
	 * @param hasBuilding the hasBuilding to set
	 */
	public void setHasBuilding(boolean hasBuilding) {
		this.hasBuilding = hasBuilding;
	}

	private LinkedList<Hashtag> parseForHashtags() {
		throw new UnsupportedOperationException("Parse not yet implemented.");
	}
	
	public boolean hasBuilding() {
		return this.hasBuilding;
	}
}
