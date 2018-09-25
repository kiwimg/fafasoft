package com.fafasoft.flow.pojo;

import java.math.BigDecimal;

public class StockTj {
	
	    private String catno;
	    private String stocktype;
	    private String stockname;
	    private double amount;
	    private double syamount;
	    private double gkThamount;
	    private double cgThamount;
	    private BigDecimal costprice = new BigDecimal(0);
	    private BigDecimal sycostprice = new BigDecimal(0);
	    private BigDecimal gkThcostprice = new BigDecimal(0);
	    private BigDecimal cgThcostprice = new BigDecimal(0);
	    private String color = "";
	    private String specif = "";
		
		public String getStocktype() {
			return stocktype;
		}
		public void setStocktype(String stocktype) {
			this.stocktype = stocktype;
		}
		public String getCatno() {
			return catno;
		}
		public void setCatno(String catno) {
			this.catno = catno;
		}
		public String getStockname() {
			return stockname;
		}
		public void setStockname(String stockname) {
			this.stockname = stockname;
		}
		public double getAmount() {
			return amount;
		}
		public void setAmount(double amount) {
			this.amount = amount;
		}
		public double getSyamount() {
			return syamount;
		}
		public void setSyamount(double syamount) {
			this.syamount = syamount;
		}
		public double getGkThamount() {
			return gkThamount;
		}
		public void setGkThamount(double gkThamount) {
			this.gkThamount = gkThamount;
		}
		public double getCgThamount() {
			return cgThamount;
		}
		public void setCgThamount(double cgThamount) {
			this.cgThamount = cgThamount;
		}
		public BigDecimal getCostprice() {
			return costprice;
		}
		public void setCostprice(BigDecimal costprice) {
			this.costprice = costprice;
		}
		public BigDecimal getSycostprice() {
			return sycostprice;
		}
		public void setSycostprice(BigDecimal sycostprice) {
			this.sycostprice = sycostprice;
		}
		public BigDecimal getGkThcostprice() {
			return gkThcostprice;
		}
		public void setGkThcostprice(BigDecimal gkThcostprice) {
			this.gkThcostprice = gkThcostprice;
		}
		public BigDecimal getCgThcostprice() {
			return cgThcostprice;
		}
		public void setCgThcostprice(BigDecimal cgThcostprice) {
			this.cgThcostprice = cgThcostprice;
		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		public String getSpecif() {
			return specif;
		}
		public void setSpecif(String specif) {
			this.specif = specif;
		}


}
