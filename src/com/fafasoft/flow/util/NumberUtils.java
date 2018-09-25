package com.fafasoft.flow.util;

/**
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>

 * @author liyc
 * @version 1.0
 * @since JDK1.5
 */
public class NumberUtils {
    public static boolean isNumber(String str) {
        if (str == null || str.trim().length() == 0)
            return false;
        try {
            Integer.parseInt(str.trim());
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public static  boolean isNumeric(String str){
 	   for(int i=str.length();--i>=0;){
 	      int chr=str.charAt(i);
 	      if(chr<48 || chr>57)
 	         return false;
 	   }
 	   return true;
 	}
    public static double dormatDouble(String str) {
        if (str == null || str.trim().length() == 0 || "null".equals(str))
            return 0;
        double r= 0;
        try {
           r= Double.parseDouble(str.trim());
        } catch (NumberFormatException nfe) {
            return 0;
        }
        return r;
    }
    
    public static String formatRates(double val) {
		Double ret = null;
		val = val * 100;
		int precision = 2;
		try {
			double factor = Math.pow(10, precision);
			ret = new Double(Math.floor(val * factor + 0.5) / factor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String tmp = String.valueOf(ret);
		if (tmp.substring(tmp.indexOf('.') + 1).length() < 2) {
			tmp = tmp + "0";
		}
		return tmp;
	}
}
