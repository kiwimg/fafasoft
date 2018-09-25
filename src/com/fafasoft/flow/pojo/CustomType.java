package com.fafasoft.flow.pojo;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-6-16
 * Time: 10:08:21
 * To change this template use File | Settings | File Templates.
 */
public class CustomType {
    private String typename;
    private double integration = 0;
    private double discount = 0;

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        if("null".equals(typename)) {
             this.typename = "";
        } else {
             this.typename = typename;
        }

    }

    public double getIntegration() {
        return integration;
    }

    public void setIntegration(double integration) {
        this.integration = integration;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
