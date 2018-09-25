package com.fafasoft.flow.ui;

import com.fafasoft.flow.SysEnv;
import com.fafasoft.flow.ui.panel.BaseFrame;
import com.fafasoft.flow.ui.panel.LoginPane;
import com.fafasoft.flow.ui.panel.ModifyPassPanel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class LoginFrame extends BaseFrame {
	private static LoginFrame mainWindows;
	static {
		mainWindows = new LoginFrame();
	}

	public static LoginFrame getInstance() {
		if (mainWindows != null) {
			if (!SysEnv.getInstance().isSetInitAdminPass()) {
				mainWindows.setTitle("设置发发流水账系统管理员密码");
				mainWindows.setSize(336, 267);
				mainWindows.setContentPane(new ModifyPassPanel());
			}
		}
		return mainWindows;
	}

	private LoginFrame() {
		super();
		setAlwaysOnTop(true);
		setTitle("登录发发流水账系统");
		setContentPane(new LoginPane());
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void reLogin() {
		if (getContentPane().getComponentCount() > 0) {
			getContentPane().removeAll();
		}
		setContentPane(new LoginPane());
		setVisible(true);
	}

	public void setCenter() {
		Dimension screSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screSize.width;
		int y = screSize.height;
		int appW = (int) getSize().getWidth();
		int appH = (int) getSize().getHeight();
		// display center
		setLocation((x - appW) / 2, (y - appH) / 2);
	}

	public void setSize() {
		if (SysEnv.getInstance().isSetInitAdminPass()) {
			setSize(336, 205);
		} else {
			setSize(336, 267);
		}
	}
}
