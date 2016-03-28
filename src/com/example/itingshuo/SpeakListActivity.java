package com.example.itingshuo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import volley.VolleyManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.anim.ListAnim;
import com.config.Urls;
import com.entity.JCourse;
import com.entity.JSentenceList;
import com.speak.ClassList;
import com.speak.ClassListAdapter;
import com.speak.JuziList;
import com.speak.JuziListAdapter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

public class SpeakListActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener{
	  private JuziListAdapter adapter = null;  
      private List<JuziList> juziList;
      private ListView juziListView;
      private String courseid;
      private SwipeRefreshLayout mSwiperefreshlayout;
      public static final String TAG = "SpeakClassFragment";
   	  private String username;
   	  private String password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.speak_juzi_fragment);  
		getmIntent();
		juziListView = (ListView) findViewById(R.id.lv_speak_juzi);
		 mSwiperefreshlayout = (SwipeRefreshLayout)findViewById(R.id.swiperefreshlayout);
		 mSwiperefreshlayout.setColorSchemeResources(
	                R.color.colorPrimary,
	                R.color.colorPrimaryDark,
	                R.color.colorAccent,
	                R.color.colorAccent//多一个？
	        );
        juziList = new ArrayList<JuziList>();
        mSwiperefreshlayout.setOnRefreshListener(this);	 
        requestDataFromServer();
        adapter = new JuziListAdapter(SpeakListActivity.this, R.layout.speak_juzi_item, juziList);
        juziListView.setLayoutAnimation(new ListAnim().getListAnim());
        juziListView.setAdapter(adapter);
	}
  /*
	 * 向服务器请求数据
	 */
	private void requestDataFromServer(){
		juziList.clear();
		 new Handler().post(new Runnable() {

	            @Override
	            public void run() {
	                mSwiperefreshlayout.setRefreshing(true);
	            }
	        });
					Map<String,String> map = new HashMap<String,String>();
			        map.put("courseid", courseid);
			        
			       VolleyManager.newInstance().GsonPostRequest(TAG, map, Urls.SENTENCELIST_URL, JSentenceList.class, new Response.Listener<JSentenceList>() {
			           @Override
			           public void onResponse(JSentenceList sentenceList) {
			          //    Log.d("111111111111111111111", "ok" +  jmovie.getData().getMovie().get(0).getMovie_name());
			        	//   Log.d("111111111111111111111", "ok" +  jmovie.getData().getMovie().get(0).getCover_addr());   
			        	   int length = 0;
			        	   if(sentenceList.getData().getStatus()!=0 && sentenceList.getData().getSentence()!=null){
			        	   length = sentenceList.getData().getSentence().size();
			        	   for(int i=0;i<length;i++){ 
			        		   JuziList sentence  = new JuziList();
			        		   sentence.setTitle("服务器没有");
			        		   sentence.setContent(sentenceList.getData().getSentence().get(i).getContent());
			        		   sentence.setTime("0:30");
			        		   sentence.setCourseid(sentenceList.getData().getSentence().get(i).getCourse_id());
			        		   sentence.setSentenceSrc(sentenceList.getData().getSentence().get(i).getSen_addr());
			        		   sentence.setSentenceid(sentenceList.getData().getSentence().get(i).getSentence_id());
			        		   juziList.add(sentence);
			        	   }
			        	   
			        	   adapter.notifyDataSetChanged();
			                if (mSwiperefreshlayout != null)
			                    mSwiperefreshlayout.setRefreshing(false);
			        	   Log.d("success", "ok" +  sentenceList.getData().getSentence().get(0).getContent());
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
    	courseid = bundle1.getString("courseid");
		Log.d("bundle","courseid: "+courseid); 	
    }
  @Override
  public void onRefresh() {
  	requestDataFromServer();
  }  
  
}
