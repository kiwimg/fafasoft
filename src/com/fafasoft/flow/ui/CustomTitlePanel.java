package com.fafasoft.flow.ui;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.SysEnv;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.JdbcConnectionPoolHelper;
import com.fafasoft.flow.pojo.Config;
import com.fafasoft.flow.ui.skin.ImageManager;
import com.fafasoft.flow.ui.skin.LookAndFeelSelector;
import com.fafasoft.flow.ui.widget.AboutDialog;
import com.fafasoft.flow.ui.widget.MenuToggleButton;
import com.fafasoft.flow.ui.widget.ServicesDialog;
import com.fafasoft.flow.ui.widget.UpdateDialog;
import com.fafasoft.flow.util.GuiUtils;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.SubstanceRootPaneUI;
import org.jvnet.substance.utils.SubstanceTitlePane;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class CustomTitlePanel extends SubstanceTitlePane {
	private static final long serialVersionUID = 8768676999999999991L;
	private static MenuToggleButton logoutButton = null;
    private static MenuToggleButton skinButton = null;
    private static MenuToggleButton helpButton = null;
    private static MenuToggleButton serviceButton = null;
    private static MenuToggleButton feedback = null;
    
    private static  MainWindows mainJFrame = null;
	public CustomTitlePanel(JRootPane root, SubstanceRootPaneUI ui) {
		super(root, ui);
	}

	public static void editTitleBar(MainWindows frame) {
		mainJFrame =frame;
		SubstanceTitlePane title = (SubstanceTitlePane)SubstanceLookAndFeel.getTitlePaneComponent(frame);
	
		
		logoutButton = new MenuToggleButton("注销",
				new ImageIcon(CustomTitlePanel.class
						.getResource("/com/fafasoft/flow/resource/image/lock-go.png")));
		addtitlebar(logoutButton);
		logoutButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				logoutButton.setBorderPainted(true);
			}
			public void mouseExited(MouseEvent e) {
				logoutButton.setBorderPainted(false);
			}
			public void mouseClicked(MouseEvent e) {
                mainJFrame.relogin();
                LoginFrame.getInstance().reLogin();
				
			}
		});
		logoutButton.setBounds(470, 0, 50, 20);
		title.add(logoutButton, 2);
		
				
		skinButton = new MenuToggleButton("换肤",
				new ImageIcon(CustomTitlePanel.class
						.getResource("/com/fafasoft/flow/resource/image/user-green.png")));
		addtitlebar(skinButton);
		skinButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				skinButton.setBorderPainted(true);
			}
			public void mouseExited(MouseEvent e) {
				skinButton.setBorderPainted(false);
			}
		});
		// group.png

		ImageIcon mImageIcon = ImageManager
				.getImageIconByShortName("table_go.png");
		final JPopupMenu skinPopupMenu = getSkinPopupMenu(mImageIcon);
		skinButton.setPopupMenu(skinPopupMenu);
		skinButton.setBounds(520, 0, 50, 20);
	
		title.add(skinButton, 2);
		
		feedback = new MenuToggleButton("反馈",
				new ImageIcon(CustomTitlePanel.class
						.getResource("/com/fafasoft/flow/resource/image/question.png")));
		
		addtitlebar(feedback);
		feedback.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				feedback.setBorderPainted(true);
			}

			public void mouseExited(MouseEvent e) {
				feedback.setBorderPainted(false);
			}
			public void mouseClicked(MouseEvent e) {
				GuiUtils.browser("http://www.88sys.com/dis/forum.php?mod=forumdisplay&fid=37");
				
			}
		});

		feedback.setBounds(570, 0, 50, 20);
		title.add(feedback, 2);
		
		serviceButton = new MenuToggleButton("咨询",
				new ImageIcon(CustomTitlePanel.class
						.getResource("/com/fafasoft/flow/resource/image/qq.png")));
		
		serviceButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				serviceButton.setBorderPainted(true);
			}

			public void mouseExited(MouseEvent e) {
				serviceButton.setBorderPainted(false);
			}
			public void mouseClicked(MouseEvent e) {
		
				GuiUtils.browser("http://sighttp.qq.com/cgi-bin/check?sigkey=7273ed799e78a75c85319187c24a3be76e0bc7de593f8148554c34d42c69587d");
			}
		});
		addtitlebar(serviceButton);
		serviceButton.setBounds(620, 0, 50, 20);	
		title.add(serviceButton, 2);
		final JPopupMenu helpPopupMenu = getHelpPopupMenu(mImageIcon);
		helpButton = new MenuToggleButton("帮助",
				new ImageIcon(CustomTitlePanel.class
						.getResource("/com/fafasoft/flow/resource/image/help.png")));
	
		helpButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				helpButton.setBorderPainted(true);
			}
			public void mouseExited(MouseEvent e) {
				helpButton.setBorderPainted(false);
			}
		});
		addtitlebar(helpButton);
		helpButton.setPopupMenu(helpPopupMenu);
		helpButton.setBounds(670, 0, 50, 20);
	
		title.add(helpButton, 2);
	}

	private static JPopupMenu getHelpPopupMenu(Icon mImageIcon) {
		JPopupMenu jPopupMenu = new JPopupMenu();
		JMenuItem update = new JMenuItem("软件升级", mImageIcon);
		JMenuItem version = new JMenuItem("新版特性", mImageIcon);
		ActionListener updateListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				UpdateDialog sDialog = new UpdateDialog(MainWindows.getInstance());
				sDialog.setVisible(true);
				
				
				sDialog.addWindowListener(new   WindowAdapter(){
					public void  windowClosing(WindowEvent   e)   { 
						if(	GuiUtils.isupdate() ){
							int response = JOptionPane.showConfirmDialog(MainWindows.getInstance(),
									"现在重新启动发发记账系统 ，进行升级安装？", "软件升级成功完成！",
									JOptionPane.YES_NO_OPTION);	 
							switch (response) {
							case JOptionPane.YES_OPTION:
									if(	GuiUtils.isupdate() ) {
										GuiUtils.update(new File("./update.exe").getAbsolutePath());
										JdbcConnectionPoolHelper.getInstance().closeConnection();
										System.exit(0);
									}
								break;
							}
						}
						
					} 
				});
			}
		};
		update.addActionListener(updateListener);
		ActionListener versionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiUtils.browser("http://www.88sys.com/update.htm");
				
			}
		};
		version.addActionListener(versionListener);
		JMenuItem userfafa = new JMenuItem("如何使用", mImageIcon);
		JMenuItem service = new JMenuItem("服务支持", mImageIcon);
		ActionListener serviceListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServicesDialog sDialog = new ServicesDialog(MainWindows.getInstance());
				sDialog.setVisible(true);
			}
		};

		service.addActionListener(serviceListener);
		ActionListener userfafaListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				GuiUtils.browser("http://www.88sys.com/help.htm");
			}
		};
		userfafa.addActionListener(userfafaListener);
		JMenuItem about = new JMenuItem("关于发发", mImageIcon);
		ActionListener aboutListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				AboutDialog sDialog = new AboutDialog(MainWindows.getInstance());
				sDialog.setVisible(true);
			}
		};
		about.addActionListener(aboutListener);
		JMenuItem netsys = new JMenuItem("发发网站", mImageIcon);
		ActionListener netSysListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiUtils.browser("http://www.88sys.com");
			}
		};
		netsys.addActionListener(netSysListener);
		jPopupMenu.add(update);
		jPopupMenu.add(version);
		jPopupMenu.addSeparator();
		jPopupMenu.add(userfafa);
		jPopupMenu.add(service);
		jPopupMenu.addSeparator();
		jPopupMenu.add(netsys);
		jPopupMenu.add(about);
		return jPopupMenu;
	}

	private static JPopupMenu getSkinPopupMenu(Icon mImageIcon) {
		JPopupMenu jPopupMenu = new JPopupMenu();
		jPopupMenu.setPopupSize(110, 128);
		JMenuItem sahara = new JMenuItem();
		JLabel sahara_label = new JLabel("");
		sahara_label.setSize(110, 22);
		sahara_label.setIcon(new ImageIcon(CustomTitlePanel.class
				.getResource("/com/fafasoft/flow/resource/image/skin2.png")));
		sahara.add(sahara_label);
		ActionListener saharaListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				shangeSkin("Sahara");
			}
		};
		sahara.addActionListener(saharaListener);
		jPopupMenu.add(sahara);

		JLabel aTunesDark_label = new JLabel("");
		aTunesDark_label.setSize(110, 22);
		aTunesDark_label.setIcon(new ImageIcon(CustomTitlePanel.class
				.getResource("/com/fafasoft/flow/resource/image/skin1.png")));
		// aTunes Dark
		JMenuItem aTunesDark = new JMenuItem();
		aTunesDark.add(aTunesDark_label);
		ActionListener aTunesDarkListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
	
				shangeSkin("aTunes Dark");
			}
		};
		aTunesDark.addActionListener(aTunesDarkListener);
		jPopupMenu.add(aTunesDark);

		JMenuItem officeBlue2007 = new JMenuItem();
		JLabel officeBlue2007_label = new JLabel("");
		officeBlue2007_label.setSize(110, 22);
		officeBlue2007_label.setIcon(new ImageIcon(CustomTitlePanel.class
				.getResource("/com/fafasoft/flow/resource/image/skin4.png")));

		officeBlue2007.add(officeBlue2007_label);
		ActionListener officeBlue2007Listener = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				shangeSkin("OfficeBlue2007");
			}
		};
		officeBlue2007.addActionListener(officeBlue2007Listener);
		jPopupMenu.add(officeBlue2007);

		JMenuItem creme = new JMenuItem();
		JLabel creme_label = new JLabel("");
		creme_label.setSize(110, 22);
		creme_label.setIcon(new ImageIcon(CustomTitlePanel.class
				.getResource("/com/fafasoft/flow/resource/image/skin5.png")));

		creme.add(creme_label);
		ActionListener cremeListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				shangeSkin("Creme");
			}
		};
		creme.addActionListener(cremeListener);
		jPopupMenu.add(creme);

		JMenuItem NebulaBrickWall = new JMenuItem();
		JLabel NebulaBrickWall_label = new JLabel("");
		NebulaBrickWall_label.setSize(110, 22);
		NebulaBrickWall_label.setIcon(new ImageIcon(CustomTitlePanel.class
				.getResource("/com/fafasoft/flow/resource/image/skin6.png")));

		NebulaBrickWall.add(NebulaBrickWall_label);
		ActionListener NebulaBrickWallListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				shangeSkin("NebulaBrickWall");
			}
		};
		NebulaBrickWall.addActionListener(NebulaBrickWallListener);
		jPopupMenu.add(NebulaBrickWall);
		return jPopupMenu;
	}

	private static void addtitlebar(MenuToggleButton menuToggleButton) {

		menuToggleButton.setBorderPainted(false);
		menuToggleButton.setContentAreaFilled(false);
		menuToggleButton.setBorder(null);
		menuToggleButton.setIconTextGap(1);
		menuToggleButton.setPreferredSize(new Dimension(50, 20));
		menuToggleButton.setMargin(new Insets(2, 0, 2, 5));
		menuToggleButton.putClientProperty(
				"substancelaf.internal.titlePane.extraComponentKind",ExtraComponentKind.TRAILING);

	}
	private static void shangeSkin(String skinName) {
		Config[] configs = new Config[1];
		Config config2 = new Config();
		config2.setKey(Constant.SKIN);
		config2.setValue(skinName);
		SysEnv.getInstance().setSkin(config2.getValue());
		LookAndFeelSelector.setLookAndFeel(config2.getValue());
		SwingUtilities.updateComponentTreeUI(MainWindows.getInstance());
		MainWindows.getInstance().paintTitleBar();
		configs[0] = config2;
		reSetBounds(mainJFrame.getSize());
    	DAOContentFactriy.getConfigDAO().addConfigs(configs);
	}
	public static void reSetBounds(Dimension dimension) {
		helpButton.setBounds(dimension.width-130, 0, 50, 20);
		serviceButton.setBounds(dimension.width-180, 0, 50, 20);
		feedback.setBounds(dimension.width-230, 0, 50, 20);
		skinButton.setBounds(dimension.width-280, 0, 50, 20);
		logoutButton.setBounds(dimension.width-330, 0, 50, 20);
		
	}
}
