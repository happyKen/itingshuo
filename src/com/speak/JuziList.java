package com.speak;

import com.config.Urls;
import com.tool.StringTool;

public class JuziList {
	private String title;
	private String content;
	private String time;
	private String courseid;
	private String sentenceid;
	private String sentenceSrc;

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		String substr ="";
		if(content.length()>20)
		substr =StringTool.getSubString(content, 24)+"...";
		this.content = substr;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getTime() {
		return time;
	}

	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}

	public String getCourseid() {
		return courseid;
	}

	public void setSentenceid(String sentenceid) {
		this.sentenceid = sentenceid;
	}

	public String getSentenceid() {
		return sentenceid;
	}

	public void setSentenceSrc(String sentenceSrc) {
		this.sentenceSrc = Urls.ROOT+sentenceSrc;
	}

	public String setSentenceSrc() {
		return sentenceSrc;
	}

}
