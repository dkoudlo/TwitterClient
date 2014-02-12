package com.codepath.apps.mytwitterapp;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.mytwitterapp.fragments.HomeTimelineFragment;
import com.codepath.apps.mytwitterapp.fragments.MentionsFragment;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends FragmentActivity {

	public static final int REQUEST_CODE = 1;
	protected ListView lvTweets;
	protected TweetsAdapter adapter;
	SmartFragmentStatePagerAdapter adapterViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        
        vpPager.setOnPageChangeListener(new OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(TimelineActivity.this, 
                            "Selected page position: " + position, Toast.LENGTH_SHORT).show();
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes: 
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
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
//		case R.id.action_profile:
//			onRefresh();
//			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onProfileView(MenuItem mi){
//		Intent i = new Intent(this, ProfileActivity.class)
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
	
	public static class MyPagerAdapter extends SmartFragmentStatePagerAdapter {
	    private static int NUM_ITEMS = 3;

	        public MyPagerAdapter(FragmentManager fragmentManager) {
	            super(fragmentManager);
	        }

	        // Returns total number of pages
	        @Override
	        public int getCount() {
	            return NUM_ITEMS;
	        }

	        // Returns the fragment to display for that page
	        @Override
	        public Fragment getItem(int position) {
	            switch (position) {
	            case 0: 
	                return HomeTimelineFragment.newInstance("statuses/home_timeline.json");
	            case 1: 
	                return MentionsFragment.newInstance("statuses/mentions_timeline.json");
	            case 2: 
//	                return SecondFragment.newInstance(2, "Page # 3");
	            default:
	                return null;
	            }
	        }

	        // Returns the page title for the top indicator
	        @Override
	        public CharSequence getPageTitle(int position) {
	            return "Page " + position;
	        }

	    }
}
