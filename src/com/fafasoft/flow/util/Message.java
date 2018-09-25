package com.fafasoft.flow.util;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-5-29
 * Time: 20:29:46
 * To change this template use File | Settings | File Templates.
 */
public class Message {
    private static Message ourInstance =null;
     static {
       ourInstance = new Message();
    }
    public static Message getInstance() {
        if(ourInstance == null){
            ourInstance = new Message();   
        }
        return ourInstance;
    }
    private String code="";
    private String text="";
    private Message() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
