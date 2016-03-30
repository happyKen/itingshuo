package com.movie;

import java.util.List;

import volley.VolleyManager;

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
import android.widget.Toast;

import com.config.Urls;
import com.entity.JShowMovie;
import com.entity.JShowMovie.DataEntity.MovieEntity;
import com.example.itingshuo.MovieActivity;
import com.example.itingshuo.MovieListActivity;
import com.example.itingshuo.R;
import com.tool.StringTool;

public class MovieSegmentAdapter extends ArrayAdapter<JShowMovie.DataEntity.MovieEntity> {
    private int resource;
	public MovieSegmentAdapter(Context context, int resource, List<JShowMovie.DataEntity.MovieEntity> objects) {
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
		
		final MovieEntity movie = getItem(position);//获得实例
		View view;
		ViewHolder viewHolder;
		if(convertView==null){
		 view = LayoutInflater.from(getContext()).inflate(resource, null);
		 viewHolder = new ViewHolder();
		 viewHolder.movieImage= (ImageView) view.findViewById(R.id.img_movie);
		 viewHolder.movieTitle =(TextView) view.findViewById(R.id.tv_title);
		 viewHolder.movieContent =(TextView) view.findViewById(R.id.tv_content);
		 viewHolder.movieTime = (TextView) view.findViewById(R.id.tv_time);
	     viewHolder.movieBeginStudy = (ImageView) view.findViewById(R.id.img_movie_beginStudy);
		 view.setTag(viewHolder);
		}else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		 VolleyManager.newInstance().ImageLoaderRequest(viewHolder.movieImage, Urls.ROOT+movie.getSegment_cover(),
	                R.drawable.ic_default, R.drawable.ic_error, 80, 80);
		viewHolder.movieTitle.setText(movie.getSegment_name());
		String substr ="";
		if(movie.getContent().length()>20)
		substr =StringTool.getSubString(movie.getContent(), 24)+"...";
		viewHolder.movieContent.setText(substr);
		viewHolder.movieTime.setText(movie.getSegment_time());
	
		//进入学习监听器
				viewHolder.movieBeginStudy.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//Toast.makeText(getContext(), movie.getTitle()+"begin study", Toast.LENGTH_SHORT).show();
						//mySendIntent(movie.getMovie_id(),movie.getEmotion_id(),movie.getSegment_addr(),movie.getContent());
						((MovieActivity)getContext()).refresh(movie.getMovie_id(),movie.getEmotion_id(),Urls.ROOT+movie.getSegment_addr(),movie.getContent());
					}
				});
		return view;
		
	}
class ViewHolder{
	TextView movieContent;
	ImageView movieImage;
	TextView movieTitle;
	TextView movieTime;
	ImageView movieBeginStudy;
}
public void mySendIntent(String movieid,String emotionid,String movieSrc,String taici){
	Intent intent = new Intent(getContext(), MovieActivity.class);
//    //new一个Bundle对象，并将要传递的数据传入
  Bundle bundle = new Bundle();
    bundle.putString("movieid", movieid);
    bundle.putString("emotionid", emotionid);
    bundle.putString("movieSrc", movieSrc);
    bundle.putString("taici", taici);
   
    //将bundle对象assign给Intent
    intent.putExtras(bundle);
//    //开启跳转
    Log.d("sendbundle", movieid+emotionid+movieSrc+taici);
    getContext().startActivity(intent);
}
}
