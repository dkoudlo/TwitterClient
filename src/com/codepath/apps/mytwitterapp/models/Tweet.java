package com.codepath.apps.mytwitterapp.models;


import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;

public class Tweet extends Model implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7625940712608996885L;
	
	private User user;
	private String body;
	private long uid;
	private boolean favorited;
	private boolean retweeted;
	
	public String getBody() {
		return body;
	}

	public long getUid() {
		return uid;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public boolean isRetweeted() {
		return retweeted;
	}

	public User getUser() {
		return user;
	}

	public static Tweet fromJson(JSONObject jo) {
		Tweet tweet = new Tweet();
		try {
			tweet.body = jo.getString("text");
        	tweet.uid = jo.getLong("id");
        	tweet.favorited = jo.getBoolean("favorited");
        	tweet.retweeted = jo.getBoolean("retweeted");
			tweet.user = User.fromJson(jo.getJSONObject("user"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return tweet;
	}

	public static ArrayList<Tweet> fromJson(JSONArray ja) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();		
		for (int i = 0; i < ja.length(); i++) {
			JSONObject jo = null;
			try {
				jo = ja.getJSONObject(i);
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
			Tweet tweet = Tweet.fromJson(jo);
			tweets.add(tweet);
		}
		return tweets;
	}
}