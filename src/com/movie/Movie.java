package com.movie;

import com.config.Urls;
import com.tool.StringTool;

public class Movie {
	private String title;
	private String content;
	private String time;
	private String imgSrc;
	private String movieid;
	private String emotionid;
	public void setTitle(String title){
		this.title = title;
	}
	public void setContent(String content){
		String substr ="";
		if(content.length()>20)
		substr =StringTool.getSubString(content, 24)+"...";
		this.content = substr;
	}
	public void setTime(String time){
		this.time = time;
	}
	public void setImgSrc(String imgSrc){
		this.imgSrc = Urls.ROOT+imgSrc;
	}
	public String getTitle(){
		return title;
	}
	public String getContent(){
		return content;
	}
	public String getTime(){
		return time;
	}
	public String getImgSrc(){
		return imgSrc;
	}
	public String getEmotionid(){
		return emotionid;
	}
	public String getMovieid(){
		return movieid;
	}
	public void setEmotionid(String emotionid){
		this.emotionid = emotionid;
	}
	public void setMovieid(String movieid){
		this.movieid = movieid;
	}
	

}
