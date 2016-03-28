package com.speak;

public class ClassList {
	private String title;
	private String content;
	private String count;
	private String courseid;
	public void setTitle(String title){
		this.title = title;
	}
	public void setContent(String content){
		this.content = content;
	}
	public void setCount(String count){
		this.count = "一共有"+count+"个句子";
	}
	public String getTitle(){
		return title;
	}
	public String getContent(){
		return content;
	}
	public String getCount(){
		return count;
	}
	public void setCourseid(String courseid){
	 this.courseid = courseid;
	}
	public String getCourseid(){
		return courseid;
	}
	
}
