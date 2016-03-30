package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import com.anim.ListAnim;
import com.example.itingshuo.R;
import com.example.itingshuo.R.drawable;
import com.movie.MovieAdapter;
import com.tone.ToneList;
import com.tone.ToneListAdapter;
import com.tone.ToneType;
import com.tone.ToneTypeAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ToneTypeFragment extends LazyFragment {
	 private boolean isPrepared;
	 private ToneTypeAdapter adapter = null;  
     private List<ToneType> toneTypes;
     private ListView toneTypeView;
     private ListAnim listAnim;
	    @Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	       listAnim = new ListAnim();
	      
	    }  
	  
	    @Override  
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {      	
	        View v = inflater.inflate(R.layout.tone_type_fragment,null);  
	        toneTypeView = (ListView) v.findViewById(R.id.lv_tone_type);
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
	    	 toneTypeinit();
		        adapter = new ToneTypeAdapter(getActivity(), R.layout.tone_type_item, toneTypes);  
		        toneTypeView.setAdapter(adapter);
		        toneTypeView.setLayoutAnimation(listAnim.getListAnim());
	    }
	    
	    /*
	       * 文章数据的读入
	       * 
	       */
	    public void toneTypeinit(){
	    	toneTypes = new ArrayList<ToneType>();
	    	ToneType toneType = new ToneType();
	    	toneType.setToneTypeText("演讲");
	    	toneType.setToneTypeImg(R.drawable.yanjiang);
	    	toneTypes.add(toneType);
	    	ToneType toneType2 = new ToneType();
	    	toneType2.setToneTypeText("散文");
	    	toneType2.setToneTypeImg(R.drawable.sanwen);
	    	toneTypes.add(toneType2);
	    }
}
