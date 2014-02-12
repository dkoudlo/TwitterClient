package com.codepath.apps.mytwitterapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.mytwitterapp.EndlessScrollListener;
import com.codepath.apps.mytwitterapp.MyTwitterApp;
import com.codepath.apps.mytwitterapp.R;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetsListFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		
		MyTwitterApp.getRestClient().getHomeTimeLine(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						setTweets(jsonTweets);
					}
				});

		return super.onCreateView(inf, parent, savedInstanceState);
	}
	
	@Override
	void loadMore(final long max_id){
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				MyTwitterApp.getRestClient().getMoreTweets( max_id, "statuses/home_timeline.json",
					new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONArray jsonTweets) {
							setTweets(jsonTweets);
							Log.d("DEBUG", jsonTweets.toString());
						}
					}
				);
			}
		});
	}
	
	public static HomeTimelineFragment newInstance(String url) {
		HomeTimelineFragment homeTweets = new HomeTimelineFragment();

		homeTweets.setArguments(getArgs(url));

		return homeTweets;
	}
}
