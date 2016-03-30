package com.tone;

import android.R.integer;

public class ToneType {
	private String toneTypeText;
	private int toneTypeImg;
	public void setToneTypeText(String toneTypeText){
		this.toneTypeText = toneTypeText;
	}
	public void setToneTypeImg(int toneTypeImg){
		this.toneTypeImg = toneTypeImg;
	}
	public int getToneTypeImg(){
		return toneTypeImg;
	}
	public String getToneTypeText(){
		return toneTypeText;
	}
}
