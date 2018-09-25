package com.fafasoft.flow.pojo;

import java.math.BigDecimal;

public class Custom {
    private String id;
    private String name;
    private String type="";
    private String sex;
    private String birthday="0000-01-01";
    private BigDecimal amount = new BigDecimal(0);
    private int frequency;
    private String telephone;
    private String address;
    private String note;
    private String regDate;
    private double integration = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
    	if(type ==null || "null".equals(type) ) {
    		type= "";
    	}
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        if ("null".equals(birthday)) {
            this.birthday = "";
        } else {
            this.birthday = birthday;
        }
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getTelephone() {

        return telephone;
    }

    public void setTelephone(String telephone) {
        if ("null".equals(telephone)) {
            this.telephone = "";
        } else {
            this.telephone = telephone;
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if ("null".equals(address)) {
            this.address = "";
        } else {
            this.address = address;
        }
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        if ("null".equals(note)) {
            this.note = "";
        } else {
            this.note = note;
        }
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
        if ("null".equals(regDate)) {
            this.regDate = "";
        } else {
            this.regDate = regDate;
        }
    }

    public double getIntegration() {
        return integration;
    }

    public void setIntegration(double integration) {
        this.integration = integration;
    }

    public String toString(){
        return getId()+","+getName()+","+getTelephone()+","+getType()+","+getIntegration();
    }
}
