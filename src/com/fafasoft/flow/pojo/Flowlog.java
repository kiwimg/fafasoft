package com.fafasoft.flow.pojo;

import java.math.BigDecimal;

public class Flowlog {
	private String flowno;
	private String catno;
	private BigDecimal sellprice;
	private double amount;
	private BigDecimal costprice = new BigDecimal(0);
	private BigDecimal lrprice = new BigDecimal(0);
	private String type;
	private String date;
	private String stockname;
	private String flowflag;
	private String recorddate;
	private String customName = "";
	private String customNo = "";
	private String userId = "";
	private String remarks = "";
	private String stockId = "";
	private long serialNumber = 0;
	private String tempColor = "";
	private String tempSpecif = "";

	public String getUserId() {

		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFlowno() {
		return flowno;
	}

	public void setFlowno(String flowno) {
		this.flowno = flowno;
	}

	public String getCatno() {
		return catno;
	}

	public void setCatno(String catno) {
		this.catno = catno;
	}

	public BigDecimal getSellprice() {

		if (sellprice == null) {
			return new BigDecimal(0.00);
		}
		return sellprice.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setSellprice(BigDecimal sellprice) {
		this.sellprice = sellprice;
	}

	public BigDecimal getLrprice() {
		return lrprice;
	}

	public void setLrprice(BigDecimal lrprice) {

		this.lrprice = lrprice.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public BigDecimal getCostprice() {
		return costprice.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setCostprice(BigDecimal costprice) {
		this.costprice = costprice.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public String getStockname() {
		return stockname;
	}

	public void setStockname(String stockname) {
		if ("null".equals(stockname)) {
			this.stockname = "";
		} else {
			this.stockname = stockname;
		}
	}

	public String getFlowflag() {
		return flowflag;
	}

	public void setFlowflag(String flowflag) {
		this.flowflag = flowflag;
	}

	public String getRecorddate() {
		return recorddate;
	}

	public void setRecorddate(String recorddate) {
		this.recorddate = recorddate;
	}

	public String getCustomNo() {
		return customNo;
	}

	public void setCustomNo(String customNo) {
		if ("null".equals(stockname)) {
			this.customNo = "";
		} else {
			this.customNo = customNo;
		}
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		if ("null".equals(customName)) {
			this.customName = customName;
		} else {
			this.customName = customName;
		}
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		if (remarks == null) {
			remarks = "";
		}
		this.remarks = remarks;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getTempColor() {
		return tempColor;
	}

	public void setTempColor(String tempColor) {
		this.tempColor = tempColor;
	}

	public String getTempSpecif() {
		return tempSpecif;
	}

	public void setTempSpecif(String tempSpecif) {
		this.tempSpecif = tempSpecif;
	}

}
