package com.fafasoft.flow.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>

 * @author liyc
 * @version 1.0
 * @since JDK1.5
 */
public class DateHelper {
    private static SimpleDateFormat dateFormat;
    private static SimpleDateFormat yyyyMMdd;
    private static SimpleDateFormat dt;
    static {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        dt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    public static String getNowDateTime() {
        return dateFormat.format(currentDate());
    }
    public static long getNowDateTimeLong() {
    	String l = yyyyMMdd.format(currentDate());
        return Long.parseLong(l);
    }
    public static String getNowTime() {
    	return dt.format(currentDate()); 
    }
    public static Date currentDate() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    public static boolean isDate(String date) {
       try {
           // 如果输入日期不是8位的,判定为false.
           
            if (null == date || "".equals(date)) {
                return false;
            }
            Date d= dateFormat.parse(date);
        } catch (Exception e) {
           e.printStackTrace();
            return false;
        }
        return true;
    }
}
