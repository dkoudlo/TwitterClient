package com.codepath.apps.mytwitterapp.fragments;

import org.json.JSONObject;

import android.os.Bundle;

import com.codepath.apps.mytwitterapp.MyTwitterApp;
import com.codepath.apps.mytwitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {


//	String 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyTwitterApp.getRestClient().getUserTimeline(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, JSONObject json) {
				User user = User.fromJson(json);
				getActivity().setTitle("@" + user.getScreenName());
//				initProfile(user);
			}
		});
	}
	
	
	
	public static UserTimelineFragment newInstance(String userName) {
		UserTimelineFragment utlFragment = new UserTimelineFragment();
		Bundle args = new Bundle();
		args.putString("userName", userName);
		utlFragment.setArguments(args);
		return utlFragment;
	}

//
//	private void initProfile(User user) {
////		TextView tvName = (TextView) getActivity().findViewById(R.id.tvName);
////		TextView tvTagline = (TextView) getActivity().findViewById(
////				R.id.tvTagline);
////		TextView tvFollowers = (TextView) getActivity().findViewById(
////				R.id.tvFollowers);
////		TextView tvFollowing = (TextView) getActivity().findViewById(
////				R.id.tvFollowing);
////		ImageView ivProfileImage = (ImageView) getActivity().findViewById(
////				R.id.ivProfileImage);
////		tvName.setText(user.getName());
////		tvFollowers.setText(user.getFollowersCount() + "followers");
////		tvFollowing.setText(user.getFriendsCount() + "following");
////		tvTagline.setText(user.getTagline());
////		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(),
////				ivProfileImage);
//	}

	@Override
	void loadMore(long max_id) {
		// TODO Auto-generated method stub
		
	}
}
