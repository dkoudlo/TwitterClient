package com.codepath.apps.mytwitterapp;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeTweetActivity extends Activity {

	private TextView tvTweet;
	private TextView tvCharsLeft;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);

		tvTweet = (TextView) findViewById(R.id.etTweet);
		tvCharsLeft = (TextView) findViewById(R.id.tvCharsLeft);
		tvTweet.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				String str = s.toString();
//				if(str.length() >= 140){
//					tvTweet.setText(str.substring(0, 140));
//				}
				tvCharsLeft.setText( "Characters Left: " + (140 - s.toString().length()) + "" );
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.compose_tweet, menu);
		return true;
	}
	
	public void onPostTweet(View v) {

		if(tvTweet.getText().toString().isEmpty()) {
			Toast.makeText(getApplicationContext(), "Please type something in.", Toast.LENGTH_SHORT).show();
			
		} else {
			MyTwitterApp.getRestClient().postTweet(tvTweet.getText().toString(), new JsonHttpResponseHandler() {

				@Override
				public void onSuccess(int code, JSONObject jsonResponce) {
					Intent i = new Intent();
					Tweet newTweet = Tweet.fromJson(jsonResponce);
					i.putExtra("newTweet", newTweet);
					setResult(RESULT_OK, i);
					finish();
				}
			});
		}
	}
	
	public void onBack(View v){
		finish();
	}
	
}
