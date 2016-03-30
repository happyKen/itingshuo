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
import com.entity.JToneList;
import com.entity.Jmovie;
import com.movie.Movie;
import com.movie.MovieAdapter;
import com.tone.ToneList;
import com.tone.ToneListAdapter;

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

public class ToneListActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {
   private ToneListAdapter adapter = null;  
   private List<ToneList> toneLists;
   private ListView movieListView;
   private SwipeRefreshLayout mSwiperefreshlayout;
   public static final String TAG = "ToneListActivity";
   private String typeid="-1";
	private String username;
	private String password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
		 getmIntent();//获取intent
		 setContentView(R.layout.tone_list_fragment);
		 movieListView = (ListView) findViewById(R.id.lv_tone_list); 
		 toneLists = new ArrayList<ToneList>();
		 mSwiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
		 mSwiperefreshlayout.setColorSchemeResources(
	                R.color.colorPrimary,
	                R.color.colorPrimaryDark,
	                R.color.colorAccent,
	                R.color.colorAccent//多一个？
	        );
	        mSwiperefreshlayout.setOnRefreshListener(this);	 
	        requestDataFromServer();
		 adapter = new ToneListAdapter(ToneListActivity.this, R.layout.tone_item, toneLists);  
		 movieListView.setAdapter(adapter);
		 movieListView.setLayoutAnimation(new ListAnim().getListAnim());
	}

	 /*
	 * 模拟向服务器请求数据
	 */
	private void requestDataFromServer(){
		toneLists.clear();
		 new Handler().post(new Runnable() {

	            @Override
	            public void run() {
	                mSwiperefreshlayout.setRefreshing(true);
	            }
	        });
					Map<String,String> map = new HashMap<String,String>();
			        map.put("typeid", typeid);
			        
			       VolleyManager.newInstance().GsonPostRequest(TAG, map, Urls.TONELIST, JToneList.class, new Response.Listener<JToneList>() {
			           @Override
			           public void onResponse(JToneList jToneList) {
			          //    Log.d("111111111111111111111", "ok" +  jmovie.getData().getMovie().get(0).getMovie_name());
			        	//   Log.d("111111111111111111111", "ok" +  jmovie.getData().getMovie().get(0).getCover_addr());   
			        	   int length = 0;
			        	   if(jToneList.getData().getStatus()!=0 && jToneList.getData().getText()!=null){
			        	   length = jToneList.getData().getText().size();
			        	   for(int i=0;i<length;i++){ 
			        		   ToneList tone = new ToneList();
			        		   tone.setTitle(jToneList.getData().getText().get(i).getText_name());
			        		   tone.setTime(jToneList.getData().getText().get(i).getCreate_time());
			        		   tone.setTypeid(jToneList.getData().getText().get(i).getType_id());
			        		   tone.setToneid(jToneList.getData().getText().get(i).getText_id());
			        		   tone.setFinish(jToneList.getData().getText().get(i).getFinish());
			       	    	   toneLists.add(tone);
			        	   }
			        	   
			        	   adapter.notifyDataSetChanged();
			                if (mSwiperefreshlayout != null)
			                    mSwiperefreshlayout.setRefreshing(false);
			        	   Log.d("success", "ok" +  jToneList.getData().getText().get(0).getText_name());
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
    
    
    //get intent
    public void getmIntent(){
    	Bundle bundle1 = getIntent().getExtras();
    	if(bundle1.getInt("typeid")!=0){
		typeid = String.valueOf(bundle1.getInt("typeid"));
    	}
		Log.d("bundle","typeid: "+typeid);
    	
    }
    @Override
    public void onRefresh() {
    	requestDataFromServer();
    }
}
