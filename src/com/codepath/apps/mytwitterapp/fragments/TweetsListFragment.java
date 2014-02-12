package com.codepath.apps.mytwitterapp.fragments;
import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.mytwitterapp.EndlessScrollListener;
import com.codepath.apps.mytwitterapp.MyTwitterApp;
import com.codepath.apps.mytwitterapp.R;
import com.codepath.apps.mytwitterapp.TweetsAdapter;
import com.codepath.apps.mytwitterapp.models.Tweet;
//import com.dev.apps.devtwitterapp.EndlessScrollListener;
//import com.dev.apps.devtwitterapp.MyTwitterApp;
//import com.dev.apps.devtwitterapp.R;
//import com.dev.apps.devtwitterapp.TweetsAdapter;
//import com.dev.apps.devtwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TweetsListFragment extends Fragment {
	ArrayList<Tweet> tweets;
	ListView lvTweets;
	TweetsAdapter adapter;
	protected String url;
	
	
	public static Bundle getArgs(String url) {
        Bundle args = new Bundle();
        args.putString("url", url);
        return args;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url");
    }
	
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent,
			Bundle savedInstanceState) {
		return inf.inflate(R.layout.fragment_tweets_list,parent,false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initList();
	}
	
	public void initList(){
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();

		lvTweets = (ListView) getActivity().findViewById(R.id.lvTweets);
		adapter = new TweetsAdapter(getActivity(), tweets);
		lvTweets.setAdapter(adapter);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				Tweet lastItem = adapter.getItem(adapter.getCount() - 1);
				MyTwitterApp.getRestClient().getMoreTweets( lastItem.getUid(), url,
					new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONArray jsonTweets) {
							adapter.addAll(Tweet.fromJson(jsonTweets));
							Log.d("DEBUG", jsonTweets.toString());
						}
					}
				);
			}
		});
	}
	
	public TweetsAdapter getAdapter() {
		return adapter;
	}
}