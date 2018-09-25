package com.fafasoft.flow.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Properties;

import com.fafasoft.flow.Constant;

public class RemoteMessage {
	
	public static String getRemoteMessage(String url) {
		String strMsg =null;
		HttpURLConnection connection;
		try {
			connection = HttpClient.getInstance().getHttpURLConnection(url);
			if (connection != null) {
				long nFileLength = -1;
				String headelength = connection.getHeaderField("Content-Length");
				if(headelength !=null && headelength.trim().length() >0) {
					nFileLength = Long.parseLong(headelength);
				}
				if (nFileLength > 0) {
					InputStream mianinput = null;
					ByteArrayOutputStream outputStream = null;
					byte message[] = null;
					try {
						mianinput = connection.getInputStream();
						outputStream = new ByteArrayOutputStream();
						byte[] buffer = new byte[64 * 1024];
						for (;;) {
							int count = mianinput.read(buffer);
							if (count < 0)
								break;
							outputStream.write(buffer, 0, count);
						}
						message = outputStream.toByteArray();
					} catch (IOException e) {

					} finally {
						try {
							if (mianinput != null) {
								mianinput.close();
							}
							if (outputStream != null) {
								outputStream.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						connection.disconnect();
					}
					if (message != null && message.length == nFileLength) {
						try {
							strMsg = new String(message, "UTF-8");
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return strMsg;
	}
	
	public static Properties getRemoteProperties(String url) {
		Properties properties =null;
		HttpURLConnection connection;
		try {
			connection = HttpClient.getInstance().getHttpURLConnection(url);
			if (connection != null) {
				long nFileLength = -1;
				String headelength = connection.getHeaderField("Content-Length");
				if(headelength !=null && headelength.trim().length() >0) {
					nFileLength = Long.parseLong(headelength);
				}
				if (nFileLength > 0) {
					InputStream mianinput = null;
					try {
						mianinput = connection.getInputStream();
						properties = new Properties();
						properties.load(mianinput);
					} catch (IOException e) {

					} finally {
						try {
							if (mianinput != null) {
								mianinput.close();
							}
						
						} catch (IOException e) {
							e.printStackTrace();
						}
						connection.disconnect();
					}
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return properties;
	}
	public static Properties getRemoteProperties(HttpURLConnection connection) {
		Properties properties =null;
		
		if (connection != null) {
			long nFileLength = -1;
			String headelength = connection.getHeaderField("Content-Length");
			if(headelength !=null && headelength.trim().length() >0) {
				nFileLength = Long.parseLong(headelength);
			}
			if (nFileLength > 0) {
				InputStream mianinput = null;
				try {
					mianinput = connection.getInputStream();
					properties = new Properties();
					properties.load(mianinput);
				} catch (IOException e) {

				} finally {
					try {
						if (mianinput != null) {
							mianinput.close();
						}
					
					} catch (IOException e) {
						e.printStackTrace();
					}
					connection.disconnect();
				}
			}
		}
		return properties;
	}
	
}
