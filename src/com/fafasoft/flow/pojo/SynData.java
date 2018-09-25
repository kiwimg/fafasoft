package com.fafasoft.flow.pojo;

import java.util.Date;

public class SynData {
	private String id;
	private byte[] datas;
	private Date date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getDatas() {
		return datas;
	}

	public void setDatas(byte[] datas) {
		this.datas = datas;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
