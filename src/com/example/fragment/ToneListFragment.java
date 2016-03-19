package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import com.anim.ListAnim;
import com.example.itingshuo.R;
import com.movie.MovieAdapter;
import com.tone.ToneList;
import com.tone.ToneListAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ToneListFragment extends LazyFragment {
	 private boolean isPrepared;
	 private ToneListAdapter adapter = null;  
     private List<ToneList> tonelists;
     private ListView toneListView;
     private ListAnim listAnim;
	    @Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	       listAnim = new ListAnim();
	      
	    }  
	  
	    @Override  
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {      	
	        View v = inflater.inflate(R.layout.tone_list_fragment,null);  
	        toneListView = (ListView) v.findViewById(R.id.lv_tone_list);
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
	    	    toneinit();
		        adapter = new ToneListAdapter(getActivity(), R.layout.tone_item, tonelists);  
		        toneListView.setAdapter(adapter);
		        toneListView.setLayoutAnimation(listAnim.getListAnim());
	    }
	    
	    /*
	       * 文章数据的读入
	       * 需要服务器接口
	       */
	    public void toneinit(){
	    	tonelists = new ArrayList<ToneList>();
	    	ToneList tone1 = new ToneList();
	    	tone1.setTitle("tone1");
	    	tone1.setTime("2016-12-12");
	    	tone1.setFinish(1);
	    	tonelists.add(tone1);
	    	ToneList tone2 = new ToneList();
	    	tone2.setTitle("tone2");
	    	tone2.setTime("2016-12-12");
	    	tone2.setFinish(0);
	    	tonelists.add(tone2);
	    	ToneList tone3 = new ToneList();
	    	tone3.setTitle("tone3");
	    	tone3.setTime("2016-12-12");
	    	tone3.setFinish(0);
	    	tonelists.add(tone3);
	    	ToneList tone4 = new ToneList();
	    	tone4.setTitle("tone4");
	    	tone4.setTime("2016-12-12");
	    	tone4.setFinish(0);
	    	tonelists.add(tone4);
	    	ToneList tone5 = new ToneList();
	    	tone5.setTitle("tone1");
	    	tone5.setTime("2016-12-12");
	    	tone5.setFinish(0);
	    	tonelists.add(tone5);
	    	ToneList tone6 = new ToneList();
	    	tone6.setTitle("tone6");
	    	tone6.setTime("2016-12-12");
	    	tone6.setFinish(0);
	    	tonelists.add(tone6);
	    	ToneList tone7 = new ToneList();
	    	tone7.setTitle("tone7");
	    	tone7.setTime("2016-12-12");
	    	tone7.setFinish(0);
	    	tonelists.add(tone7);
	    }
}
