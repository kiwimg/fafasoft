package com.fafasoft.flow.pojo;

import java.util.Vector;

public class RetailBill {
    private String customFlag;
    private String id;
    private String time;
    private Vector data=null;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String date;
   
	public String getCustomFlag() {
		return customFlag;
	}

	public void setCustomFlag(String customFlag) {
		this.customFlag = customFlag;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Vector getData() {
		return data;
	}

	public void setData(Vector data) {
		this.data = data;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	} 
}
