package com.fafasoft.flow.pojo;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-5-30
 * Time: 10:23:28
 * To change this template use File | Settings | File Templates.
 */
public class DailyExpenses {
    private String id;
    private String type;
    private String date;
    private String recorddate;
    private BigDecimal pay;
    private String mode;

    public BigDecimal getPay() {
        return pay;
    }

    public void setPay(BigDecimal pay) {
        this.pay = pay;
    }

    public String getRecorddate() {
        return recorddate;
    }

    public void setRecorddate(String recorddate) {
        this.recorddate = recorddate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
