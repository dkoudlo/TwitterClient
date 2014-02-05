package com.codepath.apps.mytwitterapp;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeTweetActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.compose_tweet, menu);
		return true;
	}
	
	public void onPostTweet(View v) {
		TextView tvTweet = (TextView) findViewById(R.id.etTweet);

		if(tvTweet.getText().toString().isEmpty()) {
			Toast.makeText(getApplicationContext(), "Please type something in.", Toast.LENGTH_SHORT).show();
			
		} else {
			MyTwitterApp.getRestClient().postUpdate(tvTweet.getText().toString(), new JsonHttpResponseHandler() {

				@Override
				public void onSuccess(int arg0, JSONObject arg1) {
					Intent returnIntent = new Intent();
					setResult(RESULT_OK, returnIntent);
					finish();
				}
			});
		}
	}
	
	public void onBack(View v){
		finish();
	}
	
}
