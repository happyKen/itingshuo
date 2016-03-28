package com.speak;

import java.util.List;

import com.example.itingshuo.MovieActivity;
import com.example.itingshuo.R;
import com.example.itingshuo.SpeakActivity;
import com.example.itingshuo.SpeakListActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class JuziListAdapter extends ArrayAdapter<JuziList> {
    private int resource;
	public JuziListAdapter(Context context, int resource, List<JuziList> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.resource = resource;//resource为listView的每个子项的布局id
		
	}
	//getView为listView的每个子项的布局设置内容
	//convertView用于将之前加载好的布局进行缓存
	//设置一个viewHolder对控件进行缓存
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//return super.getView(position, convertView, parent);
		
//		Fruit fruit = getItem(position);//获得实例
//		View view;
//		if(convertView==null){
//		 view = LayoutInflater.from(getContext()).inflate(resource, null);
//		}else {
//			view = convertView;
//		}
//		ImageView fruitImage =  (ImageView) view.findViewById(R.id.fruit_image);
//		TextView fruitName = (TextView) view.findViewById(R.id.fruit_name);
//		fruitImage.setImageResource(fruit.getImageId());
//		fruitName.setText(fruit.getName());
//		return view;
		
		final JuziList JuziList = getItem(position);//获得实例
		View view;
		ViewHolder viewHolder;
		if(convertView==null){
		 view = LayoutInflater.from(getContext()).inflate(resource, null);
		 viewHolder = new ViewHolder();
		 viewHolder.juziTitle =(TextView) view.findViewById(R.id.tv_speak_title);
		 viewHolder.juziContent =(TextView) view.findViewById(R.id.tv_speak_content);
		 viewHolder.juziTime = (TextView) view.findViewById(R.id.tv_speak_time);
		 viewHolder.movieBeginStudy = (ImageView) view.findViewById(R.id.img_speak_beginStudy);
		 view.setTag(viewHolder);
		}else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.juziTitle.setText(JuziList.getTitle());
		viewHolder.juziContent.setText(JuziList.getContent());
		viewHolder.juziTime.setText(JuziList.getTime());
		//进入学习监听器
		viewHolder.movieBeginStudy.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(getContext(), movie.getTitle()+"begin study", Toast.LENGTH_SHORT).show();
				mySendIntent(JuziList.getCourseid(),JuziList.getSentenceid());
			}
		});
		return view;
		
	}
class ViewHolder{
	TextView juziContent;
	TextView juziTitle;
	TextView juziTime;
	ImageView movieBeginStudy;
}
public void mySendIntent(String courseid,String sentenceid){
	Intent intent = new Intent(getContext(), SpeakActivity.class);
//    //new一个Bundle对象，并将要传递的数据传入
  Bundle bundle = new Bundle();
    bundle.putString("courseid", courseid);
    bundle.putString("sentenceid", sentenceid);  
    Log.d("sendbundle","courseid: "+courseid);
    Log.d("sendbundle","sentenceid: "+sentenceid);
    //将bundle对象assign给Intent
    intent.putExtras(bundle);
//    //开启跳转
    getContext().startActivity(intent);
}
}
