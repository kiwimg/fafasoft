package com.fafasoft.flow.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.HashMap;
import java.util.Map;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.dao.impl.ConfigDAOImpl;
import com.fafasoft.flow.pojo.Config;

public class SynAuthentication {
	private static SynAuthentication synAuthentication = null;

	static {
		synAuthentication = new SynAuthentication();
	}

	public static SynAuthentication getInstance() {
		return synAuthentication;
	}

	private SynAuthentication() {
	}

	public void saveInitMacSerial(String macSerial) {
		ConfigDAOImpl configDao = new ConfigDAOImpl();
		Config config = new Config();
		config.setKey(Constant.MACHINE_MAC);
		config.setValue(macSerial);
		configDao.addConfig(config);
	}
	public void saveInitShopId(String shopId) {
		ConfigDAOImpl configDao = new ConfigDAOImpl();
		Config config = new Config();
		config.setKey(Constant.SHOP_ID);
		config.setValue(shopId);
		configDao.addConfig(config);
	}

	public void saveAuthenticationCode(String code) {
		ConfigDAOImpl configDao = new ConfigDAOImpl();
		Config config = new Config();
		config.setKey(Constant.Authentication_CODE);
		config.setValue(code);
		configDao.addConfig(config);
	}

	public boolean isSyn() {
	
		return getMacSerial().equals(getInitMacSerial())
				&& getAuthenticationCode() != null;
	}

	public String getAuthenticationCode() {
		ConfigDAOImpl configDao = new ConfigDAOImpl();
		Config config = configDao.getConfig(Constant.Authentication_CODE);
		String serial = null;
		if (config != null) {
			serial = config.getValue();
		}
		System.out.println("getAuthenticationCode=="+serial);
		return serial;
	}
	public String getShopId() {
		ConfigDAOImpl configDao = new ConfigDAOImpl();
		Config config = configDao.getConfig(Constant.SHOP_ID);
		String serial = null;
		if (config != null) {
			serial = config.getValue();
		}
		return serial;
	}
	public String getUserId() {
		ConfigDAOImpl configDao = new ConfigDAOImpl();
		Config config = configDao.getConfig(Constant.USERID);
		String serial = null;
		if (config != null) {
			serial = config.getValue();
		}
		return serial;
	}
	public String getInitMacSerial() {
		ConfigDAOImpl configDao = new ConfigDAOImpl();
		Config config = configDao.getConfig(Constant.MACHINE_MAC);
		String serial = null;
		if (config != null) {
			serial = config.getValue();
		}
		System.out.println("getInitMacSerial serial=="+serial);
		return serial;
	}

	private String getMacSerial() {
		String serial = null;
		try {
			InetAddress ip = InetAddress.getLocalHost();

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i],
						(i < mac.length - 1) ? "-" : ""));
			}
			serial = String.valueOf(sb);
            System.out.println("serial=="+serial);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serial;
	}

	public int authenticate(String username, String password,String authcode) {
		int code = 200;
		StringBuffer paramData = new StringBuffer();

		String initMacSerial = getInitMacSerial();
		paramData.append("username").append("=").append(username).append("&");
		paramData.append("password").append("=").append(password).append("&");
		paramData.append("mac").append("=").append(initMacSerial).append("&");
		paramData.append("authcode").append("=").append(authcode);
		
		Map headerMap = new HashMap();
		headerMap.put("Content-type", "application/x-www-form-urlencoded");
		String url = "http://127.0.0.1:80/fafasyn/authentication.do";
		byte[] results = null;
		try {System.out.println("paramData=="+paramData);
			results = HttpClient.getInstance().post(url, headerMap,
					paramData.toString().getBytes());
			String result = new String(results);
            System.out.println("result=="+result);
			if (result == null || result.equals("-100")) {
				code = -100; //"注册www.ymaijia.com会员，享受数据安全云备份，数据不在丢失";
			} else if (result.equals("-101")) {
				code = -101; //未授权;
			} else {
				String[] st = result.split(":");
				ConfigDAOImpl configDao = new ConfigDAOImpl();
				if(configDao.getConfig(Constant.SHOP_ID) == null) {
					saveInitShopId(st[0]);
				}
				if(configDao.getConfig(Constant.Authentication_CODE) == null) {
					saveAuthenticationCode(st[1]);
				}
			
				
				// 网卡序列号
				if (initMacSerial == null) {
					String macSerial = getMacSerial();
					saveInitMacSerial(macSerial);
				}
				
				if(configDao.getConfig(Constant.USERID) == null) {
					Config config = new Config();
					config.setKey(Constant.USERID);
					config.setValue(username);
					configDao.addConfig(config);	
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			code = -500;//"注册www.ymaijia.com会员，享受数据安全云备份，数据不在丢失";
		}

		return code;
	}
	   public static String getMD5(String source) {
	        String s = null;
	        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
	                'a', 'b', 'c', 'd', 'e', 'f' };// 用来将字节转换成16进制表示的字符
	        try {
	            java.security.MessageDigest md = java.security.MessageDigest
	                    .getInstance("MD5");
	            md.update(source.getBytes());
	            byte tmp[] = md.digest();// MD5 的计算结果是一个 128 位的长整数，
	            // 用字节表示就是 16 个字节
	            char str[] = new char[16 * 2];// 每个字节用 16 进制表示的话，使用两个字符， 所以表示成 16
	            // 进制需要 32 个字符
	            int k = 0;// 表示转换结果中对应的字符位置
	            for (int i = 0; i < 16; i++) {// 从第一个字节开始，对 MD5 的每一个字节// 转换成 16
	                // 进制字符的转换
	                byte byte0 = tmp[i];// 取第 i 个字节
	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];// 取字节中高 4 位的数字转换,// >>>
	                // 为逻辑右移，将符号位一起右移
	                str[k++] = hexDigits[byte0 & 0xf];// 取字节中低 4 位的数字转换

	            }
	            s = new String(str);// 换后的结果转换为字符串

	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return s;
	    }
	public static void main(String[] arg) {
		String[] d = new String[3];
		d[0] = "UPDATE CONFIG SET TEXTVALUE ";
		d[1] = "delete CONFIG SET TEXTVALUE ";
		d[2] = "insert CONFIG SET TEXTVALUE ";

		// byte[] dddd = compress(d);
		SynAuthentication synAuthentication = SynAuthentication.getInstance();
		//synAuthentication.authenticate("liyongchun", "password");
		// decompress(dddd);
	}
}
