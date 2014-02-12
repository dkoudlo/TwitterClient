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

public abstract class TweetsListFragment extends Fragment {
	protected ArrayList<Tweet> tweets;
	protected ListView lvTweets;
	protected TweetsAdapter tadapter;
	protected String url;
//	public long max_id = 0;
	
	public static Bundle getArgs(String url) {
        Bundle args = new Bundle();
        args.putString("url", url);
        return args;
    }

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url");
    }
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initList();
	}
	
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent,
			Bundle savedInstanceState) {
		return inf.inflate(R.layout.fragment_tweets_list,parent,false);
	}
	
	public void initList(){
		tweets = new ArrayList<Tweet>();
		lvTweets = (ListView) getActivity().findViewById(R.id.lvTweets);
		tadapter = new TweetsAdapter(getActivity(), tweets);
		
		tadapter.addAll(tweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				Tweet lastItem = tadapter.getItem(tadapter.getCount() - 1);
				loadMore(lastItem.getUid());
			}
		});
		
		lvTweets.setAdapter(tadapter);
	}
	
	void setTweets(JSONArray jsonTweets) {
		ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
		tadapter.addAll(tweets);
		Log.d("DEBUG", jsonTweets.toString());
	}
	
	abstract void loadMore(long max_id);
	
	public TweetsAdapter getAdapter() {
		return tadapter;
	}
}