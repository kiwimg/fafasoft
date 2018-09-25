package com.fafasoft.flow.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpClient {
	private static HttpClient httpClient = null;

	static {
		httpClient = new HttpClient();
	}

	public static HttpClient getInstance() {
		return httpClient;
	}

	private HttpClient() {
	}

	public HttpURLConnection getHttpURLConnection(String url)
			throws IOException {

		URL urlStr = null;
		try {
			urlStr = new URL(url);
		} catch (Exception e1) {
			throw new IOException();
		}
		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) urlStr.openConnection();
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			String succ = connection.getURL().toString();

			if (!succ.equals(url)) {
				connection.disconnect();
				throw new IOException();
			}

		} catch (IOException e1) {
			throw new IOException();
		}

		return connection;
	}

	public byte[] post(String url, Map headerMap, byte[] datas)
			throws IOException {

		byte[] redata = null;
		HttpURLConnection httpurlconnection = null;
		try {
			httpurlconnection = getHttpURLConnection(url);

			httpurlconnection.setDoOutput(true);
			httpurlconnection.setUseCaches(false);
			httpurlconnection.setRequestMethod("POST");

			if (headerMap != null && headerMap.size() > 0) {
				Set set = headerMap.keySet();
				Iterator it = set.iterator();
				while (it.hasNext()) {
					String key = String.valueOf(it.next());
					httpurlconnection.addRequestProperty(key,
							String.valueOf(headerMap.get(key)));
				}

			}
			httpurlconnection.connect();
			// Send request
			if(datas != null && datas.length >0) {
				httpurlconnection.getOutputStream().write(datas);	
			}
			
			httpurlconnection.getOutputStream().flush();
			httpurlconnection.getOutputStream().close();

			// Get Response
			InputStream input = httpurlconnection.getInputStream();
			// code = httpurlconnection.getResponseCode();
			redata = responseData(input);

		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			if (httpurlconnection != null)
				httpurlconnection.disconnect();
		}

		return redata;
	}

	private byte[] responseData(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024 * 4];

		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);

		}
		return output.toByteArray();
	}

	public byte[] upload(String urlstr, Map headerMap, File file,UploadPr uploadPr)
			throws IOException {
		byte[] redata = null;
		
		HttpURLConnection conn = getHttpURLConnection(urlstr);
		try {

			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setChunkedStreamingMode(1024 * 1024 * 1024);
			conn.setRequestMethod("POST");

			conn.setConnectTimeout(10000); // 连接超时为10秒

			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			// conn.setRequestProperty("Cache-Control", "no-cache");
			// 设置内容长度 Get the number of bytes in the file
			conn.setRequestProperty("Content-Length",
					String.valueOf(file.length()));
			conn.setRequestProperty("filename", file.getName());
			conn.setRequestProperty("Content-Type", "application/octet-stream");
			if (headerMap != null && headerMap.size() > 0) {
				Set set = headerMap.keySet();
				Iterator it = set.iterator();
				while (it.hasNext()) {
					String key = String.valueOf(it.next());
					conn.setRequestProperty(key,
							String.valueOf(headerMap.get(key)));

				}
			}
			conn.connect();
			OutputStream out = new DataOutputStream(conn.getOutputStream());

			InputStream in = new FileInputStream(file);
			uploadPr.uplod(out,in,file.length());

			InputStream input = conn.getInputStream();
			redata = responseData(input);
		} finally {
			if (conn != null)
				conn.disconnect();
		}
		return redata;

	}
}
