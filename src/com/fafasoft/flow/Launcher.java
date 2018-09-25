package com.fafasoft.flow;

import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.JdbcConnectionPoolHelper;
import com.fafasoft.flow.dao.impl.ConfigDAOImpl;
import com.fafasoft.flow.pojo.Config;
import com.fafasoft.flow.ui.LoginFrame;
import com.fafasoft.flow.ui.skin.LookAndFeelSelector;
import com.fafasoft.flow.util.GuiUtils;
import com.sun.awt.AWTUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;

public class Launcher {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if ( GuiUtils.isupdate()) {
					//ffflow88s
					JdbcConnectionPoolHelper.getInstance();
				
					float vnew = Float.parseFloat(Version.getInstance()
							.getVersion());
					
				
					float vold = Float.parseFloat(SysEnv.getInstance()
							.getVersion());
					if(vold < vnew) {
						Config config = new Config();
						config.setKey(Constant.VERSION);
						config.setValue(Version.getInstance().getVersion());
						DAOContentFactriy.getConfigDAO().saveOrUpdateConfig(
								config);
						GuiUtils.update(new File("./update.exe").getAbsolutePath());
					} else {
						new File("./update.exe").delete();start();
					}
					
				}else {
					start();
				}
			}
		});
		
	}

	public static void start() {
		System.setProperty("java.awt.im.style", "on-the-spot");
		System.setProperty("sun.awt.noerasebackground", "true");
		// 检测系统是否只启动一个实
		final WelcomeSplashEx splash = new WelcomeSplashEx();
		final SplashManager manager = new SplashManager(splash);
		manager.repaint();
		splash.setMessage("正在准备初始环境....");
		manager.repaint();
		// import chrriis.dj.nativeswing.swtimpl.NativeInterface;
		// NativeInterface.open();
		splash.setMessage("正在加载系统数据....");
		manager.repaint();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//ffflow88s
				JdbcConnectionPoolHelper.getInstance();
			}
		});

		try {
			splash.setMessage("系统加载完成....");
			manager.repaint();
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					String skin = SysEnv.getInstance().getSkin();
					LookAndFeelSelector.setLookAndFeel(skin);

					float vnew = Float.parseFloat(Version.getInstance()
							.getVersion());
					float vold = Float.parseFloat(SysEnv.getInstance()
							.getVersion());
					if (vold < vnew) {
						Patch.pachExexcute(vold, vnew);
						Config config = new Config();
						config.setKey(Constant.VERSION);
						config.setValue(Version.getInstance().getVersion());
						DAOContentFactriy.getConfigDAO().saveOrUpdateConfig(
								config);
					}

					LoginFrame login = LoginFrame.getInstance();

					login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					manager.closeSplash();
					// login.setUndecorated(true);
					login.setSize();
					login.setCenter();
					login.setVisible(true);
					Shape shape = new RoundRectangle2D.Double(0, 0, login
							.getSize().getWidth(), login.getSize().getHeight(),
							3.5D, 3.5D);
					AWTUtilities.setWindowShape(login, shape);
					// AWTUtilities.setWindowOpacity(login, 1f);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
