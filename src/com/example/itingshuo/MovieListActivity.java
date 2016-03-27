package com.example.itingshuo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import volley.VolleyManager;
import android.support.v4.widget.SwipeRefreshLayout;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.anim.ListAnim;
import com.config.Urls;
import com.entity.JLogin;
import com.entity.Jmovie;
import com.movie.Movie;
import com.movie.MovieAdapter;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

public class MovieListActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {
   private MovieAdapter adapter = null;  
   private List<Movie> movieList;
   private ListView movieListView;
   private SwipeRefreshLayout mSwiperefreshlayout;
   public static final String TAG = "MovieListActivity";
   private String emotion="-1";
	private String username;
	private String password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
		 getmIntent();//获取intent
		 setContentView(R.layout.movie_list_fragment);
		 movieListView = (ListView) findViewById(R.id.lv_movie); 
		 movieInit();
		 mSwiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
		 mSwiperefreshlayout.setColorSchemeResources(
	                R.color.colorPrimary,
	                R.color.colorPrimaryDark,
	                R.color.colorAccent,
	                R.color.colorAccent//多一个？
	        );
	        mSwiperefreshlayout.setOnRefreshListener(this);	 
	        requestDataFromServer();
		 adapter = new MovieAdapter(MovieListActivity.this, R.layout.movie_item, movieList);  
		 movieListView.setAdapter(adapter);
		 movieListView.setLayoutAnimation(new ListAnim().getListAnim());
	}

	 /*
	 * 模拟向服务器请求数据
	 */
	private void requestDataFromServer(){
		movieList.clear();
		 new Handler().post(new Runnable() {

	            @Override
	            public void run() {
	                mSwiperefreshlayout.setRefreshing(true);
	            }
	        });
					Map<String,String> map = new HashMap<String,String>();
			        map.put("emotionid", emotion);
			        
			       VolleyManager.newInstance().GsonPostRequest(TAG, map, Urls.MOVIELIST_URL, Jmovie.class, new Response.Listener<Jmovie>() {
			           @Override
			           public void onResponse(Jmovie jmovie) {
			          //    Log.d("111111111111111111111", "ok" +  jmovie.getData().getMovie().get(0).getMovie_name());
			        	//   Log.d("111111111111111111111", "ok" +  jmovie.getData().getMovie().get(0).getCover_addr());   
			        	   int length = 0;
			        	   if(jmovie.getData().getStatus()!=0 && jmovie.getData().getMovie()!=null){
			        	   length = jmovie.getData().getMovie().size();
			        	   for(int i=0;i<length;i++){ 
			        		   Movie movie = new Movie();
			        		   movie.setTitle(jmovie.getData().getMovie().get(i).getMovie_name());
			        	       movie.setContent(jmovie.getData().getMovie().get(i).getMovie_introduction());
			        	       movie.setTime("7:30");
			       	    	   movie.setImgSrc(jmovie.getData().getMovie().get(i).getCover_addr());
			       	    	   movie.setEmotionid(emotion);
			       	    	   movie.setMovieid(jmovie.getData().getMovie().get(i).getMovie_id());
			       	    	   movieList.add(movie);
			        	   }
			        	   
			        	   adapter.notifyDataSetChanged();
			                if (mSwiperefreshlayout != null)
			                    mSwiperefreshlayout.setRefreshing(false);
			        	   Log.d("success", "ok" +  jmovie.getData().getMovie().get(0).getMovie_introduction());
			        	   }
			           }
			       }, new Response.ErrorListener() {
			           @Override
			           public void onErrorResponse(VolleyError error) {
			               Log.d("fail", "connect fail");
			               mSwiperefreshlayout.setRefreshing(false);

			           }
			       });
			        Log.d(TAG, "finish");
	}
    
      /*
       * movie数据的初始化
       * 
       */
    public void movieInit(){
    	movieList= new ArrayList<Movie>();

    	
    }
    
    //get intent
    public void getmIntent(){
    	Bundle bundle1 = getIntent().getExtras();
    	if(bundle1.getString("emotion")!=null){
		emotion = bundle1.getString("emotion");
    	}
		Log.d("bundle","emotion: "+emotion);
    	
    }
    @Override
    public void onRefresh() {
    	requestDataFromServer();
    }
}
