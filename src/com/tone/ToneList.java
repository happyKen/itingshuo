package com.tone;

public class ToneList {
	private String title;
	private String time;
	private String finish;
	private String typeid;
	private String toneid;
	
	public void setTitle(String title){
		this.title = title;
	}
	public void setTypeid(String typeid){
		this.typeid = typeid;
	}
	public void setToneid(String toneid){
		this.toneid = toneid;
	}
	public void setTime(String time){
		this.time = "布置时间： "+time;
	}
	public void setFinish(int finish){
		//判断是否完成，1为已完成，0为未完成
		if(finish==0){
			this.finish="未完成";
		}else{
			this.finish="已完成";
		}
	
	}
	public String getTitle(){
		return title;
	}
	public String getTime(){
		return time;
	}
	public String getFinish(){
		return finish;
	}
	public String getToneid(){
		return toneid;
	}
	public String getTypeid(){
		return typeid;
	}
	

	

}
