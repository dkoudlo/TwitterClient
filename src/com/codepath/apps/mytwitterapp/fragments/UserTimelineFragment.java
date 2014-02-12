package com.codepath.apps.mytwitterapp.fragments;

import org.json.JSONObject;

import android.os.Bundle;

import com.codepath.apps.mytwitterapp.MyTwitterApp;
import com.codepath.apps.mytwitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
	User me;
	//ActionBar actionBar;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyTwitterApp.getRestClient().getUserTimeline(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, JSONObject json) {
				User user = User.fromJson(json);
				getActivity().setTitle("@" + user.getScreenName());
				initProfile(user);
			}
		});
	}

	private void initProfile(User user) {
//		TextView tvName = (TextView) getActivity().findViewById(R.id.tvName);
//		TextView tvTagline = (TextView) getActivity().findViewById(
//				R.id.tvTagline);
//		TextView tvFollowers = (TextView) getActivity().findViewById(
//				R.id.tvFollowers);
//		TextView tvFollowing = (TextView) getActivity().findViewById(
//				R.id.tvFollowing);
//		ImageView ivProfileImage = (ImageView) getActivity().findViewById(
//				R.id.ivProfileImage);
//		tvName.setText(user.getName());
//		tvFollowers.setText(user.getFollowersCount() + "followers");
//		tvFollowing.setText(user.getFriendsCount() + "following");
//		tvTagline.setText(user.getTagline());
//		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(),
//				ivProfileImage);
	}
}
