package com.fafasoft.flow.ui.widget;

import javax.swing.JDialog;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.SysEnv;
import com.fafasoft.flow.Version;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.util.GuiUtils;
import com.fafasoft.flow.util.HttpClient;
import com.fafasoft.flow.util.NumberUtils;
import com.fafasoft.flow.util.RemoteMessage;
import com.fafasoft.flow.util.Message;

import java.awt.Color;

public class UpdateDialog extends JDialog {
	public UpdateDialog(Component owner) {
		setResizable(false);
		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						UpdateDialog.class
								.getResource("/com/fafasoft/flow/resource/image/yygl.png")));
		setTitle("发发软件升级");
		setSize(new Dimension(340, 245));
		setLocationRelativeTo(owner);
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(UpdateDialog.class
				.getResource("/com/fafasoft/flow/resource/image/fafa.png")));
		getContentPane().add(label_2, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		panel.setMaximumSize(new Dimension(32767, 100));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel label = new JLabel("<html>您的当前版本:<b>"
				+ Version.getInstance().getVersion() + "</b></html>");
		label.setBounds(37, 14, 201, 17);
		panel.add(label);

		JLabel label_1 = new JLabel("你可以立即检查更新，升级到最新版本。");
		label_1.setBounds(37, 32, 216, 17);
		panel.add(label_1);

		final JButton button = new JButton("开始升级 ");

		button.setBounds(219, 96, 85, 25);
		button.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(button);

		final JLabel updateMessage = new JLabel("");
		updateMessage.setForeground(Color.RED);
		updateMessage.setBounds(37, 54, 285, 17);
		panel.add(updateMessage);

		JLabel lblhttpwwwsyscom = new JLabel(
				"<html>查看更多信息请访问<u>http://www.88sys.com</u></html>");
		lblhttpwwwsyscom.setBounds(12, 124, 310, 17);
		panel.add(lblhttpwwwsyscom);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				String mess = "正在获取发发记账软件版本信息，请稍候..........";
				updateMessage.setText(mess);

				final Thread thread1 = new Thread(new Runnable() {
					public void run() {
						updateMessage.setText("正在连接网络 请稍候.........");
						HttpURLConnection connection= null;
						try {
							connection = HttpClient.getInstance().getHttpURLConnection(Constant.MESSAGE_V);
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						if (connection == null) {
							updateMessage.setText("无法连接升级服务器,原因可能网络不通");
							return;
						}
						Properties remoteProp = RemoteMessage
								.getRemoteProperties(connection);
						SysEnv.getInstance().setRemoteProperties(remoteProp);
						long newversion = Long.parseLong(remoteProp
								.getProperty("messagev").trim());
						long cversion = SysEnv.getInstance()
								.getMessageVersion();
						boolean isupdate = false;
						isupdate = newversion <= cversion;
						if (isupdate) {
							updateMessage.setText("当前版本已是最新,无需更新");
						} else {
							updateMessage.setText("正在下载升级程序中.........");
							HttpURLConnection updateconnection=null;
							try {
								updateconnection = HttpClient.getInstance().getHttpURLConnection(Constant.UPDATE_URL);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if (updateconnection == null) {
								updateMessage.setText("无法连接升级服务器,原因可能网络不通");
								return;
							}
							long nFileLength = -1;
							String headelength = updateconnection
									.getHeaderField("Content-Length");
							if (headelength != null
									&& headelength.trim().length() > 0) {
								nFileLength = Long.parseLong(headelength);
							}

							if (nFileLength > 0) {
								File oSavedFile = new File("./update.exe");
								FileOutputStream fileOutputStream = null;
								try {
									fileOutputStream = new FileOutputStream(
											oSavedFile);
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								}
								int nStartPos = 0;
								int nRead = 0;
								InputStream mianinput = null;
								try {
									mianinput = updateconnection
											.getInputStream();

									byte[] maindata = new byte[1024 * 5];
									while ((nRead = mianinput.read(maindata, 0,
											1024 * 5)) > 0
											&& nStartPos < nFileLength) {
										if (!isVisible()) {
											updateMessage
													.setText("正在取消中........");
											if (Thread.currentThread()
													.isAlive()) {
												Thread.currentThread()
														.interrupt();
											}
											break;
										}
										fileOutputStream.write(maindata, 0,
nRead);
										nStartPos += nRead;
										double z = ((double) nStartPos / nFileLength);

										updateMessage.setText("正在下载升级程序,当前已完成"
												+ NumberUtils.formatRates(z)+ "%");
									}

								} catch (IOException e) {
									e.printStackTrace();

									updateMessage.setText("下载升级程序出错,请重新下载");
								} finally {
									try {
										if (mianinput != null) {
											fileOutputStream.close();
											mianinput.close();
										}
									} catch (IOException e) {
										e.printStackTrace();
									}
									updateconnection.disconnect();
								}

								if (nStartPos != nFileLength) {
									oSavedFile.delete();
									updateMessage.setText("软件升级未完成");
								} else if (nStartPos == nFileLength) {
									updateMessage
											.setText("软件升级成功完成！请重新启动发发记账系统");
								}
							}
						}
					}
				}, "updatefafa");
				thread1.start();
			}
		}

		);
	}

	
}
