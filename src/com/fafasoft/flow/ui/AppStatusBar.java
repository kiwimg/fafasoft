package com.fafasoft.flow.ui;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.SysEnv;
import com.fafasoft.flow.Version;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.pojo.Config;
import com.fafasoft.flow.ui.widget.tip.BalloonTip;

import com.fafasoft.flow.util.GuiUtils;
import com.fafasoft.flow.util.RemoteMessage;
import com.fafasoft.flow.util.SynchronousTools;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AppStatusBar extends javax.swing.JPanel {
	private static AppStatusBar thisPage;
	private JLabel userlabel = null;
	private JLabel sysMessage;
	private JLabel tipsMessage;
	private BalloonTip balloonTip = null;
	private JProgressBar progressBar;

	public static AppStatusBar getInstance() {
		if (thisPage == null) {
			thisPage = new AppStatusBar();
		}
		return thisPage;
	}

	private AppStatusBar() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 20, 290, 45, 0, 72, 56, 0, 80,
				0 };
		gridBagLayout.rowHeights = new int[] { 16, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0,
				0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		userlabel = new JLabel();
		userlabel.setIcon(new ImageIcon(AppStatusBar.class
				.getResource("/com/fafasoft/flow/resource/image/gajim.png")));
		userlabel.setIconTextGap(1);

		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		add(userlabel, gbc_lblNewLabel);
		tipsMessage = new JLabel("");

		tipsMessage.setForeground(new Color(64, 64, 64));
		sysMessage = new JLabel("有0条新消息");
		sysMessage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		sysMessage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						getRemoteMessage();
					}
				});
				showMessage();
			}

			@Override
			public void mouseEntered(MouseEvent e) {

				sysMessage.setForeground(new Color(255, 102, 0));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				sysMessage.setForeground(null);

			}
		});

		final JButton label = new JButton("计算器");
		label.setIconTextGap(1);
		label.setFocusable(false);
		label.setPreferredSize(new Dimension(60, 23));
		label.setBorderPainted(false);
		label.setContentAreaFilled(false);
		label.setBorder(null);
		label.setMargin(new Insets(0, 2, 0, 2));
		label.setIcon(new ImageIcon(
				AppStatusBar.class
						.getResource("/com/fafasoft/flow/resource/image/calculator.png")));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				label.setBorderPainted(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				label.setBorderPainted(false);
			}

			public void mouseClicked(MouseEvent e) {
				try {
					Runtime.getRuntime().exec("calc.exe");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		JLabel label_time = new JLabel("");
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		label_time.setText(hour + ":" + minute);

		GridBagConstraints gbc_label_t = new GridBagConstraints();
		gbc_label_t.anchor = GridBagConstraints.WEST;
		gbc_label_t.insets = new Insets(0, 0, 0, 5);
		gbc_label_t.gridx = 2;
		gbc_label_t.gridy = 0;
		add(label_time, gbc_label_t);

		final JButton backupButton = new JButton("数据备份");
		backupButton.setIcon(new ImageIcon(AppStatusBar.class
				.getResource("/com/fafasoft/flow/resource/image/syn.png")));
		backupButton.setIconTextGap(1);
		backupButton.setFocusable(false);
		backupButton.setPreferredSize(new Dimension(80, 23));
		backupButton.setBorderPainted(false);
		backupButton.setContentAreaFilled(false);

		backupButton.setMargin(new Insets(0, 2, 0, 2));

		backupButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				backupButton.setBorderPainted(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				backupButton.setBorderPainted(false);
			}

			public void mouseClicked(MouseEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						SynchronousTools.getInstance().synData();

					}
				});
			}
		});

		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		progressBar.setMinimumSize(new Dimension(100, 15));
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.anchor = GridBagConstraints.EAST;
		gbc_progressBar.insets = new Insets(0, 0, 0, 5);
		gbc_progressBar.gridx = 3;
		gbc_progressBar.gridy = 0;
		add(progressBar, gbc_progressBar);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 4;
		gbc_btnNewButton.gridy = 0;
		add(backupButton, gbc_btnNewButton);

		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 5;
		gbc_label.gridy = 0;
		add(label, gbc_label);

		final JButton lblNewLabel = new JButton("应用商店");
		lblNewLabel.setIcon(new ImageIcon(AppStatusBar.class
				.getResource("/com/fafasoft/flow/resource/image/shop.png")));
		lblNewLabel.setIconTextGap(1);
		lblNewLabel.setFocusable(false);
		lblNewLabel.setPreferredSize(new Dimension(80, 23));
		lblNewLabel.setBorderPainted(false);
		lblNewLabel.setContentAreaFilled(false);

		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel.setBorderPainted(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel.setBorderPainted(false);
			}

			public void mouseClicked(MouseEvent e) {
				Properties properties = SysEnv.getInstance()
						.getRemoteProperties();
				String taobao = properties.getProperty("taobao");
				if (taobao != null && taobao.trim().length() > 0) {
					GuiUtils.browser(taobao.trim());
				} else {
					GuiUtils.browser("http://fafasoft.taobao.com/");
				}
			}
		});

		GridBagConstraints gbc_lblNewLabel1 = new GridBagConstraints();
		gbc_lblNewLabel1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel1.gridx = 6;
		gbc_lblNewLabel1.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel1);

		sysMessage
				.setIcon(new ImageIcon(
						AppStatusBar.class
								.getResource("/com/fafasoft/flow/resource/image/date-add.png")));
		sysMessage.setIconTextGap(1);
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.EAST;
		gbc_label_2.gridx = 7;
		gbc_label_2.gridy = 0;
		add(sysMessage, gbc_label_2);
		new TimeElapsed(label_time).start();

	}

	public void resetLoginUserName() {
		Calendar cal = Calendar.getInstance();
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		userlabel.setText(SysEnv.getInstance().getloginUserName() + "已登录 今天是 "
				+ cal.get(Calendar.YEAR) + "年" + (cal.get(Calendar.MONTH) + 1)
				+ "月" + cal.get(Calendar.DATE) + "日 " + weekDays[w] + "");
	}

	public void setSysMessage(String text) {
		if (!text.equals("0")) {
			sysMessage.setForeground(new Color(255, 102, 0));

		}
		sysMessage.setText("有" + text + "条新消息");
	}

	public void setTipsMessage(String text) {
		tipsMessage.setText(text);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				showMessage();
			}
		});

	}

	public void setProgress(int flag) {
		if(!progressBar.isVisible()) {
			progressBar.setVisible(true);
		}
		
		progressBar.setValue(flag);
	}

	class TimeElapsed extends Thread {
		private JLabel time;

		public TimeElapsed(JLabel time) {
			this.time = time;
		}

		@Override
		public void run() {
			try {
				while (!interrupted()) {
					sleep(1000);
					Calendar cal = Calendar.getInstance();
					int hour = cal.get(Calendar.HOUR_OF_DAY);
					int minute = cal.get(Calendar.MINUTE);

					this.time.setText(hour + ":" + minute);
					this.time.setVisible(true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void showMessage() {
		if (balloonTip != null) {
			if (balloonTip.isVisible()) {
				return;
			}
		}

		if (tipsMessage.getText().length() > 0) {
			balloonTip = GuiUtils.createTips(sysMessage, tipsMessage);
		} else {
			JLabel temp = new JLabel("<html>欢迎使用发发流水记账系统</html>");
			temp.setForeground(new Color(64, 64, 64));
			balloonTip = GuiUtils.createTips(sysMessage, temp);
		}
		GuiUtils.toolTips(balloonTip, new Color(103, 103, 103), 4000, "right",
				false, true);
	}

	public void getRemoteMessage() {
		Properties remoteProp = RemoteMessage
				.getRemoteProperties(Constant.MESSAGE_V);
		if (remoteProp != null) {
			SysEnv.getInstance().setRemoteProperties(remoteProp);
			float newVersion = Float.parseFloat(remoteProp
					.getProperty("version"));
			if (newVersion > Version.getInstance().getFVersion()) {
				setSysMessage("1");
				setTipsMessage("<HTML>发发流水记账系统V" + newVersion
						+ "已发布,欢迎升级<br>请点击帮助-软件升级</HTML>");
			} else {
				boolean isupdate = false;
				long newMVersion = Long.parseLong(remoteProp.getProperty(
						"messagev").trim());
				long cversion = SysEnv.getInstance().getMessageVersion();
				isupdate = newMVersion <= cversion;

				if (!isupdate) {
					setSysMessage("1");
					Config configMesaageVserion = new Config();
					configMesaageVserion.setKey(Constant.MESSAGE_VERSION);
					configMesaageVserion.setValue(String.valueOf(newMVersion));
					DAOContentFactriy.getConfigDAO().saveOrUpdateConfig(
							configMesaageVserion);
					SysEnv.getInstance().setMessageVersion(newMVersion);
				} else {
					setSysMessage("0");
					setTipsMessage("");
				}

				String message = remoteProp.getProperty("message");
				if (message != null) {
					setTipsMessage("<HTML>" + message.trim() + "</HTML>");
				} else {
					setTipsMessage("");
				}
			}
		}
	}
}
