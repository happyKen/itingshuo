package com.example.fragment;

import com.example.itingshuo.LoginActivity;
import com.example.itingshuo.MainActivity;
import com.example.itingshuo.MovieListActivity;
import com.example.itingshuo.R;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MovieEmotionFragment extends Fragment implements OnClickListener{	
	LinearLayout happiness;
	LinearLayout sadness;
	LinearLayout anger;
	LinearLayout surprise;
//	private OnHappinessClick onHappinessClick;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.movie_emotion_fragment, null);
		 happiness = (LinearLayout) v.findViewById(R.id.happiness);
		 sadness = (LinearLayout) v.findViewById(R.id.sadness);
		 anger = (LinearLayout) v.findViewById(R.id.anger);
		 surprise = (LinearLayout) v.findViewById(R.id.surprise);
		happiness.setOnClickListener(this);
		sadness.setOnClickListener(this);
		anger.setOnClickListener(this);
		surprise.setOnClickListener(this);
		return v;
		// return View.inflate(getActivity(), R.layout.movie_emotion_fragment,
		// null);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.happiness:
			 mySendIntent("1");
			 break;
		case R.id.sadness:
			 mySendIntent("2");
			 break;
		case R.id.anger:
			 mySendIntent("3");
			 break;
		case R.id.surprise:
			 mySendIntent("4");
			 break;
		default:
			break;
		}
		
	}
	public void mySendIntent(String emotion){
		Intent intent = new Intent(getActivity(), MovieListActivity.class);
        //new一个Bundle对象，并将要传递的数据传入
        Bundle bundle = new Bundle();
        bundle.putString("emotion", emotion);
        //将bundle对象assign给Intent
        intent.putExtras(bundle);
        //开启跳转
        startActivity(intent);
	}

}
