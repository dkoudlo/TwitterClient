package com.codepath.apps.mytwitterapp;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {

	public static final int REQUEST_CODE = 1;
	protected ListView lvTweets;
	protected TweetsAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		MyTwitterApp.getRestClient().getHomeTimeLine(
		new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);

				lvTweets = (ListView) findViewById(R.id.lvTweets);
				adapter = new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);
				lvTweets.setOnScrollListener(new EndlessScrollListener() {
					@Override
					public void onLoadMore(int page, int totalItemsCount) {
						Tweet lastItem = adapter.getItem(adapter.getCount() - 1);
						MyTwitterApp.getRestClient().getMoreTweets( lastItem.getUid(),
							new JsonHttpResponseHandler() {
								@Override
								public void onSuccess(JSONArray jsonTweets) {
									adapter.addAll(Tweet.fromJson(jsonTweets));
								}
							}
						);
					}
				});

				Log.d("DEBUG", jsonTweets.toString());
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	public void onComposeTweet(){
		Intent i = new Intent(this, ComposeTweetActivity.class);
		startActivityForResult(i, REQUEST_CODE);
	}
	
	public void onRefresh() {
		adapter.clear();
		MyTwitterApp.getRestClient().getHomeTimeLine(new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				adapter.addAll(Tweet.fromJson(jsonTweets));
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_compose:
			onComposeTweet();
			return true;
		case R.id.action_refresh:
			onRefresh();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent i) {
		if (requestCode == REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Tweet tweet = (Tweet) i.getSerializableExtra("newTweet");
				adapter.insert(tweet, 0);
			}
		}
		super.onActivityResult(requestCode, resultCode, i);
	}
}
