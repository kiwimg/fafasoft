package com.fafasoft.flow.util;

import com.fafasoft.flow.Constant;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>

 * @author liyc
 * @version 1.0
 * @since JDK1.5
 */
public class ResouceLoader {
    public static URL getFileURL(String url_name) {
        try {
            return new URL(Constant.PROTOCOL_FILE + url_name);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Malformed URL " + url_name, e);
        }
    }

    public static String getFilePath(String filePath) {
        return getFileURL(filePath).getPath();
    }

    /**
     * @param url_name
     * @return url
     */
    public static URL getResouce(String url_name) {
        return ClassLoader.getSystemResource(url_name);
	}
}
   