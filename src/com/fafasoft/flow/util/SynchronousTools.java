package com.fafasoft.flow.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.dao.impl.SynDAO;
import com.fafasoft.flow.ui.AppStatusBar;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.widget.BackUpDialog;

public class SynchronousTools {
	private static final String sysnulr = "http://localhost/fafasyn/uploadSyn.do";
	private static final String issysnulr = "http://localhost/fafasyn/isSyn.do";
	private static final String authulr = "http://localhost/fafasyn/auth.do";
	private static SynchronousTools synchronousTools = null;
	private static int synFinish = 0;
	static {
		synchronousTools = new SynchronousTools();
	}

	public static SynchronousTools getInstance() {
		return synchronousTools;
	}

	private SynchronousTools() {
	}

	public void synData() {
		if (synFinish == 1) {
			AppStatusBar.getInstance().setSysMessage("1");
			AppStatusBar.getInstance().setTipsMessage(
					"<HTML>" + "正在同步数据中，请耐心等待！" + "</HTML>");
			return;
		}
		
		final SynAuthentication synAuthentication = SynAuthentication
				.getInstance();

		if (!synAuthentication.isSyn()) {
			BackUpDialog backUpDialog = new BackUpDialog(
					MainWindows.getInstance());
			backUpDialog.setVisible(true);
			return;
		}
		// 网络是否正常
		// 是否注册成功、用户信息合法校验
		// 是否建立基线
		// 校验数据完整性
		synFinish = 1;
		Map headerMap = new HashMap();
		headerMap.put("shopid", synAuthentication.getShopId());
		headerMap.put("username", synAuthentication.getUserId());
		headerMap.put("mac", synAuthentication.getInitMacSerial());
		headerMap.put("authcode", synAuthentication.getAuthenticationCode());
		try {
			String issyn = null;
			byte[] rebyte = HttpClient.getInstance().post(issysnulr, headerMap,
					null);
			issyn = new String(rebyte);
			String re = "0";
			byte[] remain = HttpClient.getInstance().post(authulr, headerMap,
					null);
			re = new String(remain, "utf-8");
			if (re.equalsIgnoreCase("success")) {
				AppStatusBar.getInstance().setProgress(0);
				
				UploadPr upload = new UploadPrImpl();
				if (!"true".equalsIgnoreCase(issyn)) {// 是否建立基线
					headerMap.put("syn", "0");
					re = buildBaseline(headerMap,upload);
				} else {
					// 复制基线 synback.h2.db
					// 上传成功 建立增量库

					// 如果建立基线 则同步增量
					// 增量库是否存在 及正常
					// 如果有数据则同步
					// 复制数据
					// 上传数据
					// 上传成功则删除复制文件
					// 清除增量数据
					headerMap.put("syn", "1");
					re = buildIncrement(headerMap,upload);
				}
			}

			String message = "<HTML>" + "恭喜！数据备份成功,可以随时随地查看数据了" + "</HTML>";
			if (Integer.parseInt(re) == -100) {
				message = "<html>用户信息未认证，不能同步备份数据!,请先注册www.ymaijia.com会员</html>";
			} else if (Integer.parseInt(re) == -500) {
				message = "<html>网络不通，请检查！请先检查是否能上网</html>";
			} else if (Integer.parseInt(re) == -101) {
				message = "<html>您还没有未你的店面授权！请登录www.ymaijia.com设置</html>";
			}
			AppStatusBar.getInstance().setSysMessage("1");
			AppStatusBar.getInstance().setTipsMessage(message);
		} catch (IOException e1) {
			e1.printStackTrace();
			AppStatusBar.getInstance().setSysMessage("1");
			AppStatusBar.getInstance().setTipsMessage(
					"<HTML>" + "网络不通，无法备份数据" + "</HTML>");
		}
		synFinish = 0;
	}
    private String buildBaseline(Map headerMap,UploadPr upload ) {
    	String re = "0";
    	File mainFile = new File(Constant.CONFIG_PATH
				+ "yygl.h2.db");
		long size = mainFile.length();
		double kiloByte = size / 1024;
		double megaByte = kiloByte / 1024;
		double gigaByte = megaByte / 1024;
		if (gigaByte > 300) {
			JOptionPane.showMessageDialog(
					MainWindows.getInstance(), "你很久没有备份了。数据太大了",
					"备份数据提示", JOptionPane.WARNING_MESSAGE);
			return "0";
		}
		File targetFile = copyFile(mainFile.getAbsolutePath(),
				Constant.CONFIG_PATH + "mainback.h2.db");
		if (mainFile == null || !mainFile.exists()
				|| targetFile == null || !targetFile.exists()) {
			return "0";
		}
		File mainZipfile = new File(Constant.CONFIG_PATH
				+ "mainback.zip");
		ZipCompressor zc = new ZipCompressor(
				mainZipfile.getAbsolutePath());
		zc.compress(targetFile.getAbsolutePath());
		try {

			byte[] remain = HttpClient.getInstance().upload(sysnulr,
					headerMap, mainZipfile, upload);
			re = new String(remain, "utf-8");

			if (re.equalsIgnoreCase("success")) {
				mainZipfile.delete();
				targetFile.delete();
			}
		} catch (IOException e) {
			e.printStackTrace();
			AppStatusBar.getInstance().setSysMessage("1");
			AppStatusBar.getInstance().setTipsMessage(
					"<HTML>" + "网络不通，无法备份数据" + "</HTML>");
		}
		return re;
    }
    
    private String buildIncrement(Map headerMap,UploadPr upload) throws IOException {
    	String re = "0";
    	File synFile = new File(Constant.CONFIG_PATH + "syn.h2.db");
		if (synFile.exists()) {
			int size = SynDAO.getInstance().getSqlsSize();
			if (size > 0) {
			
				Timestamp time = new Timestamp(
						System.currentTimeMillis());
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss.SSS");
				File zipFile = new File(Constant.CONFIG_PATH
						+ "syn.zip");
				ZipCompressor zc = new ZipCompressor(
						zipFile.getAbsolutePath());
				zc.compress(synFile.getAbsolutePath());

				byte[] ddd = HttpClient.getInstance().upload(sysnulr, headerMap, zipFile, upload);
				re = new String(ddd, "utf-8");
				// System.out.println("=============================="
				// +
				// re);
				if (re.equalsIgnoreCase("success")) {
					SynDAO.getInstance().clear(df.format(time));
					zipFile.deleteOnExit();
				}
			}
		}
		return re;
    }
	public File copyFile(String oldPath, String newPath) {
		File newFile = null;
		try {
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[8192];
				int bytesum = 0;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
				newFile = new File(newPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newFile;
	}

	class UploadPrImpl implements UploadPr {
		public void uplod(OutputStream out, InputStream in, long nFileLength)
				throws IOException {
			int nStartPos = 0;
			int nRead = 0;
			try {
				byte[] bufferOut = new byte[8024];
				while ((nRead = in.read(bufferOut)) != -1) {
					out.write(bufferOut, 0, nRead);
					nStartPos += nRead;
					double z = ((double) nStartPos / nFileLength);
					double d = Double.parseDouble(NumberUtils.formatRates(z));
					AppStatusBar.getInstance().setProgress((int) d);
				}

			} finally {
				in.close();
				out.flush();
				out.close();

			}
		}
	}
}
