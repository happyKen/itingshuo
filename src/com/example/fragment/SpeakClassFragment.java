package com.example.fragment;

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
import com.entity.Jmovie;
import com.example.itingshuo.R;
import com.movie.Movie;
import com.speak.ClassList;
import com.speak.ClassListAdapter;

import android.R.anim;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;//不知为什么要加这个？
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.os.Handler;
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

public class SpeakClassFragment extends LazyFragment implements SwipeRefreshLayout.OnRefreshListener{
	  // 标志位，标志已经初始化完成。
    private boolean isPrepared;
	  private ClassListAdapter adapter = null;  
      private List<ClassList> classList;
      private ListView classListView;
      private SwipeRefreshLayout mSwiperefreshlayout;
      public static final String TAG = "SpeakClassFragment";
   	  private String username;
   	  private String password;
	    @Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        
	      
	    }  
	    @Override  
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {
	    	Log.d("oncreate", "onCreateView");
	        View v = inflater.inflate(R.layout.speak_class_fragment,null);  
	        classListView = (ListView) v.findViewById(R.id.lv_speak_class);
	        mSwiperefreshlayout = (SwipeRefreshLayout) v.findViewById(R.id.swiperefreshlayout);
			 mSwiperefreshlayout.setColorSchemeResources(
		                R.color.colorPrimary,
		                R.color.colorPrimaryDark,
		                R.color.colorAccent,
		                R.color.colorAccent//多一个？
		        );
	        //XXX初始化view的各控件
	        classList = new ArrayList<ClassList>();
	        isPrepared = true;
	        lazyLoad();	        
	        return v;  
	    }  
	    //懒加载
	    @Override
	    protected void lazyLoad() {
	    	// TODO Auto-generated method stub
	    	 if(!isPrepared || !isVisible) {
	             return;
	         }
	         //填充各控件的数据
	    	 mSwiperefreshlayout.setOnRefreshListener(this);	 
		        requestDataFromServer();
		        adapter = new ClassListAdapter(getActivity(), R.layout.speak_class_item, classList);  
		        classListView.setLayoutAnimation(new ListAnim().getListAnim());
		        classListView.setAdapter(adapter);
	    }
	    
	    /*
		 * 向服务器请求数据
		 */
		private void requestDataFromServer(){
			classList.clear();
			 new Handler().post(new Runnable() {

		            @Override
		            public void run() {
		                mSwiperefreshlayout.setRefreshing(true);
		            }
		        });
						Map<String,String> map = new HashMap<String,String>();
				        map.put("requestclass", "1");
				        
				       VolleyManager.newInstance().GsonPostRequest(TAG, map, Urls.COURSE_URL, JCourse.class, new Response.Listener<JCourse>() {
				           @Override
				           public void onResponse(JCourse jcourse) {
				          //    Log.d("111111111111111111111", "ok" +  jmovie.getData().getMovie().get(0).getMovie_name());
				        	//   Log.d("111111111111111111111", "ok" +  jmovie.getData().getMovie().get(0).getCover_addr());   
				        	   int length = 0;
				        	   if(jcourse.getData().getStatus()!=0 && jcourse.getData().getCourse()!=null){
				        	   length = jcourse.getData().getCourse().size();
				        	   for(int i=0;i<length;i++){ 
				        		   ClassList course  = new ClassList();
				        		   course.setTitle(jcourse.getData().getCourse().get(i).getCourse_name());
				        		   course.setContent(jcourse.getData().getCourse().get(i).getCourse_introduction());
				        		   course.setCount(jcourse.getData().getCourse().get(i).getCourse_num());
				        		   course.setCourseid(jcourse.getData().getCourse().get(i).getCourse_id());
				        		   classList.add(course);
				        	   }
				        	   
				        	   adapter.notifyDataSetChanged();
				                if (mSwiperefreshlayout != null)
				                    mSwiperefreshlayout.setRefreshing(false);
				        	   Log.d("success", "ok" +  jcourse.getData().getCourse().get(0).getCourse_introduction());
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
	    @Override
	    public void onRefresh() {
	    	requestDataFromServer();
	    }  
	    
	   
}
