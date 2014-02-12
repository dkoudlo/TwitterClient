package com.codepath.apps.mytwitterapp.models;

import java.io.Serializable;

import org.json.JSONObject;

public class User extends BaseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2502747785623338902L;
	private String name;
	private long uid;
	private String screenName;
	private String profileImageUrl;
	private String profileBgImageUrl;
	private int numTweets;
	private int followersCount;
	private int friendsCount;
	private boolean me;
	private String tagline;
	
	public String getName() {
		return name;
	}
	
	public long getUid() {
		return uid;
	}
	
	public String getTagline() {
		return tagline;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public String getProfileBgImageUrl() {
		return profileBgImageUrl;
	}

	public int getNumTweets() {
		return numTweets;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public int getFriendsCount() {
		return friendsCount;
	}

	public static User fromJson(JSONObject jo) {
		User user = new User();
		try {
			user.name = jo.getString("name");
			user.uid = jo.getLong("id");
			user.screenName = jo.getString("screen_name");
			user.profileImageUrl = jo.getString("profile_image_url");
			user.profileBgImageUrl = jo.getString("profile_background_image_url");
			user.numTweets = jo.getInt("statuses_count");
			user.followersCount = jo.getInt("followers_count");
			user.friendsCount = jo.getInt("friends_count");
			user.tagline= jo.getString("description");
			user.me = true;
//			user.save();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return user;

	}

}