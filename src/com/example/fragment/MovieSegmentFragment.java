package com.example.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import volley.VolleyManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.anim.ListAnim;
import com.config.Urls;
import com.entity.JShowMovie;
import com.entity.Jmovie;
import com.example.itingshuo.MovieActivity;
import com.example.itingshuo.R;
import com.movie.Movie;
import com.movie.MovieAdapter;
import com.movie.MovieSegmentAdapter;
import com.speak.ClassListAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.app.Fragment;//不知为什么要加这个？
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;

public class MovieSegmentFragment extends Fragment {
	  //电影片段
	private MovieSegmentAdapter adapter = null;  
      private List<JShowMovie.DataEntity.MovieEntity> movieEntity;
      private ListView movieListView;
	    @Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	       
	      
	    }  
	  
	    @Override  
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {      	
	        View v = inflater.inflate(R.layout.movie_segment_list_fragment,null);  
	        movieListView = (ListView) v.findViewById(R.id.lv_movie); 
	        movieEntity = ((MovieActivity)getActivity()).getmovieEntity();
	        adapter = new MovieSegmentAdapter(getActivity(), R.layout.movie_item, movieEntity);  
		    movieListView.setAdapter(adapter);
		    movieListView.setLayoutAnimation(new ListAnim().getListAnim());
	        return v;  
	    }      
	    
	       
}
