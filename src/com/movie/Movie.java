package com.movie;

public class Movie {
	private String title;
	private String content;
	private String time;
	private int imgSrc;
	public void setTitle(String title){
		this.title = title;
	}
	public void setContent(String content){
		this.content = content;
	}
	public void setTime(String time){
		this.time = time;
	}
	public void setImgSrc(int imgSrc){
		this.imgSrc = imgSrc;
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
	public int getImgSrc(){
		return imgSrc;
	}
	

}
