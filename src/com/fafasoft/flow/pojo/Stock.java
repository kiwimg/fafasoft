package com.fafasoft.flow.pojo;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 中州软讯</p>
 *
 * @author liyc
 * @version 1.0
 * @since JDK1.5
 */
public class Stock {
    private String id;
    private String catno;
    private String stockname;
    private double amount;
    private double syamount;
    private double gkThamount;
    private double cgThamount;
    private BigDecimal costprice = new BigDecimal(0);
    private BigDecimal sellprice = new BigDecimal(0);
    private String type;
    private String date;
    private BigDecimal total = new BigDecimal(0);
    private String stockFlag;
    private String recorddate;
    private String color = "";
    private String specif = "";
    private String suppliers = null;
    private String remarks= "";
    private String imagesName;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatno() {
        return catno;
    }

    public void setCatno(String catno) {
        this.catno = catno;
    }

    public BigDecimal getTotal() {
        return total.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setTotal(BigDecimal total) {
        this.total = total.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getSellprice() {
        return sellprice.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setSellprice(BigDecimal sellprice) {
        this.sellprice = sellprice.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getCostprice() {
        return costprice.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setCostprice(BigDecimal costprice) {
        this.costprice = costprice.setScale(2, BigDecimal.ROUND_HALF_UP);
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

    public String getStockFlag() {
        return stockFlag;
    }

    public void setStockFlag(String stockFlag) {
        this.stockFlag = stockFlag;
    }

    public String getRecorddate() {
        return recorddate;
    }

    public void setRecorddate(String recorddate) {
        this.recorddate = recorddate;
    }

    public String getColor() {

        return this.color;
    }

    public void setColor(String color) {
        if ("null".equals(color)) {
            this.color = "";
        } else {
            this.color = color;
        }
    }

    public String getSpecif() {
        return this.specif;
      
    }

    public String getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(String suppliers) {
        if ("null".equals(suppliers)) {
            this.suppliers = "";
        } else {
            this.suppliers = suppliers;
        }
    }

    public void setSpecif(String specif) {
        if ("null".equals(specif)) {
            this.specif = "";
        } else {
            this.specif = specif;
        }

    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

	public String getImagesName() {
		return imagesName;
	}

	public void setImagesName(String imagesName) {
		this.imagesName = imagesName;
	}
    
}
