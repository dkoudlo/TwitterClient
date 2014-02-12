package com.codepath.apps.mytwitterapp;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "Ghp3f2oKuY4x6NN1mEVt9Q";       // Change this
    public static final String REST_CONSUMER_SECRET = "xkFEzumyb9S5NH4o4esNnZF0ZBugywGDWW2Bi8yWs"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://mytwitterapp"; // Change this (here and in manifest)
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getMyInfo(AsyncHttpResponseHandler handler) {
    	String url = "account/verify_credentials.json";
    	String apiUrl = getApiUrl(url);
    	client.get(apiUrl,null,handler);
    }
    
    public void getUserTimeline(AsyncHttpResponseHandler handler){
    	String url = getApiUrl("statuses/user_timeline.json");
    	client.get(url, null, handler);
    }
    
    public void getUser(AsyncHttpResponseHandler handler, String urlAppend) {
    	String url = getApiUrl("users/show.json" + urlAppend);
    	client.get(url, null, handler);
    }
    
    public void getHomeTimeLine(AsyncHttpResponseHandler handler){
    	String url = getApiUrl("statuses/home_timeline.json");
    	client.get(url, null, handler);
    }
    
    public void getMentions(AsyncHttpResponseHandler handler){
    	String url = getApiUrl("statuses/mentions_timeline.json");
    	client.get(url, null, handler);
    }

	public void postTweet(String payload, JsonHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("status", payload);
		client.post(getApiUrl("statuses/update.json"), params, handler);
	}

	public void getMoreTweets(Long max_id, String url, AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("max_id", String.valueOf(max_id - 1L));
		client.get(getApiUrl(url), params, handler);
	}
}