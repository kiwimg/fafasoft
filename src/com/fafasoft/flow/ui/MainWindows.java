package com.fafasoft.flow.ui;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.SysEnv;
import com.fafasoft.flow.Version;
import com.fafasoft.flow.action.ActionManager;
import com.fafasoft.flow.action.MaijiaAction;
import com.fafasoft.flow.action.PasswordAction;
import com.fafasoft.flow.action.SynAction;
import com.fafasoft.flow.dao.JdbcConnectionPoolHelper;
import com.fafasoft.flow.ui.panel.AuthorityPanel;
import com.fafasoft.flow.ui.panel.BaseFrame;
import com.fafasoft.flow.ui.panel.CgthPane;
import com.fafasoft.flow.ui.panel.CustomPanel;
import com.fafasoft.flow.ui.panel.CustomersAccountPanel;
import com.fafasoft.flow.ui.panel.EveryDayFlowPanel;
import com.fafasoft.flow.ui.panel.GkJLPanel;
import com.fafasoft.flow.ui.panel.GkthPane;
import com.fafasoft.flow.ui.panel.KuCTjPanel;
import com.fafasoft.flow.ui.panel.LazyPageContent;
import com.fafasoft.flow.ui.panel.OptionsTypePanel;
import com.fafasoft.flow.ui.panel.RcZcPanel;
import com.fafasoft.flow.ui.panel.SellHelperPanel;
import com.fafasoft.flow.ui.panel.SellOrderpane;
import com.fafasoft.flow.ui.panel.SettiingPanel;
import com.fafasoft.flow.ui.panel.StockPanel;
import com.fafasoft.flow.ui.panel.StockQueryPanel;
import com.fafasoft.flow.ui.panel.SuppliersAccountPanel;
import com.fafasoft.flow.ui.panel.SuppliersPanel;
import com.fafasoft.flow.ui.panel.YyeTjPanel;
import com.fafasoft.flow.ui.panel.ZcTjPanel;
import com.fafasoft.flow.ui.skin.ImageManager;
import com.fafasoft.flow.ui.widget.JColseTabbedPane;
import com.fafasoft.flow.util.GuiUtils;
import com.sun.awt.AWTUtilities;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MainWindows extends BaseFrame {
	private static MainWindows mainWindows;
	private JColseTabbedPane tabbedPane;
	private boolean isRelogin = false;

    private class DisableMouseDraggedListener implements AWTEventListener{

        private Object source;

        private DisableMouseDraggedListener(Object source) {
            this.source = source;
        }

        @Override
        public void eventDispatched(AWTEvent e) {
            MouseEvent event = (MouseEvent) e;

            if(event.getID() == MouseEvent.MOUSE_DRAGGED && event.getSource()==source){
                event.consume();
            }
        }
    }

	private MainWindows() {
		super();
		
        getToolkit().addAWTEventListener(new DisableMouseDraggedListener(this),AWTEvent.MOUSE_MOTION_EVENT_MASK);

		SystemTray systemTray = null;// 系统托盘
		PopupMenu popup;//
		String title = new StringBuilder().append("发发流水账系统V")
				.append(Version.getInstance().getVersion())
				.append("        生意兴隆     财源滚滚").toString();
		try {
			setTitle(new String(title.getBytes("utf-8"), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		if (SystemTray.isSupported()) {
			systemTray = SystemTray.getSystemTray();// 获得系统托盘的实例
			// 为这个托盘加一个弹出菜单
			popup = new PopupMenu();

			MenuItem show = null;
			show = new MenuItem("显示界面       ");

			ActionListener showListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (LoginFrame.getInstance().isVisible()) {
						setVisible(false);
					} else {
						setVisible(true);
					}
				}
			};
			MenuItem logout = null;
			logout = new MenuItem("注销");

			ActionListener logoutListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					LoginFrame login = LoginFrame.getInstance();
					login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					login.setVisible(true);
				}
			};
			MenuItem shop = new MenuItem("应用商店");
			ActionListener shopListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Properties properties = SysEnv.getInstance()
							.getRemoteProperties();
					String taobao = properties.getProperty("taobao");
					if (taobao != null && taobao.trim().length() > 0) {
						GuiUtils.browser(taobao.trim());
					} else {
						GuiUtils.browser("http://fafasoft.taobao.com/");
					}
				}
			};

			MenuItem exit = new MenuItem("退出");
			ActionListener exitListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JdbcConnectionPoolHelper.getInstance().closeConnection();
					System.exit(0);
				}
			};
			shop.addActionListener(shopListener);
			show.addActionListener(showListener);
			logout.addActionListener(logoutListener);
			exit.addActionListener(exitListener);

			popup.add(show);
			popup.addSeparator();
			popup.add(shop);
			popup.addSeparator();
			popup.add(logout);
			popup.addSeparator();
			popup.add(exit);

			// 为这个托盘加一个提示信息
			try {
				final TrayIcon trayIcon1 = new TrayIcon(ImageManager
						.getImageIconByShortName("yygl.png").getImage(),
						"发发流水帐", popup);
				trayIcon1.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						setVisible(true);
					}
				});
				try {
					systemTray.add(trayIcon1);// 设置托盘的图标，VIPPrivilege.gif与该类文件同一目录
				} catch (AWTException e) {
					// System.out.println("无法向这个托盘添加新项：" + e);
				}
				this.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						if (SystemTray.isSupported()) {
							trayIcon1.displayMessage("发发流水系统提示",
									"您可以双击图标打开主界面!", TrayIcon.MessageType.INFO);
							setVisible(false);
							// minimizeToTray();
						} else {
							System.exit(0);
						}
					}
				});
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		addWindowStateListener(new WindowStateListener() {

			public void windowStateChanged(WindowEvent state) {
				if (state.getNewState() == 0) {
					CustomTitlePanel.reSetBounds(mainWindows.getSize());
				} else if (state.getNewState() == 6) {
					CustomTitlePanel.reSetBounds(mainWindows.getSize());
				}
				Shape shape = new RoundRectangle2D.Double(0, 0, mainWindows
						.getSize().getWidth(), mainWindows.getSize()
						.getHeight(), 5.5D, 5.5D);
				AWTUtilities.setWindowShape(mainWindows, shape);
			}
		});

		paintTitleBar();
		pack();
	}

	public static MainWindows getInstance() {
		if (mainWindows == null) {
			mainWindows = new MainWindows();
		}

		return mainWindows;
	}

	public void openPanel() {
	     EventQueue.invokeLater(new Runnable() {
             public void run() {
            		JPanel jPanel = getWindow();

            		mainWindows.setContentPane(jPanel); 
             }});
	     

	
	}

	public void paintTitleBar() {
		CustomTitlePanel.editTitleBar(this);
	}

	private JPanel getWindow() {
		JPanel mainPanel = new JPanel();
		mainPanel.setSize(800, 600);
		mainPanel.setLayout(new BorderLayout(0, 0));

		tabbedPane = new JColseTabbedPane();
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		tabbedPane.setFont(new Font("宋体", Font.BOLD, 13));
		tabbedPane.addTab("功能导航",
				ImageManager.getImageIconByShortName("home.png"),
				getCurtainPane(), null);
		tabbedPane.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent arg0) {
				JColseTabbedPane tab = (JColseTabbedPane)arg0.getSource();
				if(tab.getSelectedComponent() instanceof EveryDayFlowPanel ) {
					EveryDayFlowPanel ef = (EveryDayFlowPanel)tab.getSelectedComponent();	
					if(ef.isRload()) {
						ef.refresh();
					}
				}
			} 
			
		});
		mainPanel.add(tabbedPane, BorderLayout.CENTER);
		/** set statusBar. */
		mainPanel.add(getAppStatusBar(), BorderLayout.SOUTH);

		return mainPanel;
	}

	public JColseTabbedPane getTabbedPane() {
		return tabbedPane;
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
		setSize(800, 600);
	}

	public AppStatusBar getAppStatusBar() {
		return AppStatusBar.getInstance();
	}

	public boolean isRelogin() {
		return isRelogin;
	}

	public void relogin() {
		isRelogin = true;
		if (mainWindows.getContentPane().getComponentCount() > 0) {
			mainWindows.getContentPane().removeAll();
		}
		PanelCache.getInstance().removeAll();
		setVisible(false);
	}

	private JPanel getCurtainPane() {
		// JPanel p = new JPanel();
		// p.setLayout(new BorderLayout());
		//
		NavgationIndex fp = new NavgationIndex();
		String[] right = SysEnv.getInstance().getRight();

		boolean isxiaos = ("true".equals(right[0]) || "true".equals(right[1])
				|| "true".equals(right[2]) || "true".equals(right[11]));
		int row=1;
		int col=0;
		if (isxiaos) {
			getSellPane(fp, right,row,col);
			col = col+2;
		}
		
		boolean isjinh = ("true".equals(right[3]) || "true".equals(right[4])
				|| (right.length >= 16 && "true".equals(right[15])) || (right.length >= 19 && "true"
				.equals(right[18])));
	
		if (isjinh) {
			getCgjhPane(fp, right,row,col);
			col = col+2;
		}
		
		boolean iszhicu = ("true".equals(right[5]) || "true".equals(right[6]) || (right.length >= 17 && "true"
				.equals(right[16])));
		if (iszhicu) {
			getZCPane(fp, right,row,col);
			col = col+2;
		}
		boolean istongj = ("true".equals(right[7]) || "true".equals(right[8])
				|| "true".equals(right[9]) || "true".equals(right[10]) || (right.length > 18 && "true"
				.equals(right[17])));
		if (istongj) {
			getTJPane(fp, right,row,col);	col = col+2;
		}
		getSetinngPane(fp, right,row,col);

		return fp;
	}

	private void getSellPane(NavgationIndex fp, String[] right,int row,int col) {
		fp.addTitle("销售",
				ImageManager.getImageIconByShortName("money_dollar.png"), row, col);
		int cell = 1;
		if("true".equals(right[0])) {
			fp.addButton("售货帮手",
					ImageManager.getImageIconByShortName("kplato.png"),cell,col+1,
					SellHelperPanel.class, "true".equals(right[0]));
			cell = cell+2;
		}
		if("true".equals(right[1])) {
			fp.addButton("每天流水", ImageManager.getImageIconByShortName("a1.png"),cell,col+1, EveryDayFlowPanel.class, "true".equals(right[1]));
			cell = cell+2;
		}
		if("true".equals(right[2])) {
			fp.addButton("顾客退货", ImageManager.getImageIconByShortName("gtuih.png"),
					cell,col+1, GkthPane.class, "true".equals(right[2]));
			cell = cell+2;
		}
		if("true".equals(right[11])) {
			fp.addButton("客户资料",
					ImageManager.getImageIconByShortName("emesene.png"), cell,col+1,
					CustomPanel.class, "true".equals(right[11]));
		}
	

	}

	private void getCgjhPane(NavgationIndex fp, String[] right,int row,int col) {
		fp.addTitle("进货",
				ImageManager.getImageIconByShortName("lorry_add.png"), row, col);
		int cell = 1;
		if("true".equals(right[3])) {
			fp.addButton("采购进货", ImageManager.getImageIconByShortName("a5.png"), cell,col+1, StockPanel.class, "true".equals(right[3]));

			cell = cell+2;
		}
		if("true".equals(right[4])) {
			fp.addButton("采购退货", ImageManager.getImageIconByShortName("a2.png"), cell,col+1, CgthPane.class, "true".equals(right[4]));

			cell = cell+2;
		}
		if("true".equals(right[15])) {
			fp.addButton("供应商资料", ImageManager.getImageIconByShortName("a7.png"),cell,col+1, SuppliersPanel.class, "true".equals(right[15]));
			
			cell = cell+2;
		}
		if("true".equals(right[18])) {
			fp.addButton("库存查询",
					ImageManager.getImageIconByShortName("zoom-3.png"), cell,col+1,
					StockQueryPanel.class,
					(right.length >= 19 && "true".equals(right[18])));
		}
		
		

	}

	private void getZCPane(NavgationIndex fp, String[] right,int row,int col) {
		fp.addTitle("收支",
				ImageManager.getImageIconByShortName("coins_delete.png"), row, col);
		int cell = 1;
		if("true".equals(right[5])) {
			fp.addButton("日常收支",
					ImageManager.getImageIconByShortName("income.png"), cell,col+1,
					RcZcPanel.class, "true".equals(right[5]));
			cell = cell+2;
		}
	
		if("true".equals(right[6])) {
			fp.addButton("客户往来帐",
					ImageManager.getImageIconByShortName("korganizer-2.png"),cell,col+1,
					CustomersAccountPanel.class, "true".equals(right[6]));

			cell = cell+2;
		}
		if("true".equals(right[16])) {
			fp.addButton("供应商往来帐",
					ImageManager.getImageIconByShortName("payment.png"),cell,col+1,
					SuppliersAccountPanel.class, "true".equals(right[16]));
		}
	}

	private void getTJPane(NavgationIndex fp, String[] right,int row,int col) {
		fp.addTitle("统计", ImageManager.getImageIconByShortName("tj.png"), row, col);
		int cell = 1;
		if("true".equals(right[7])) {
			fp.addButton("库存统计",
					ImageManager.getImageIconByShortName("kchart.png"), cell,col+1,
					KuCTjPanel.class, "true".equals(right[7]));	
			cell = cell+2;
		}

         if("true".equals(right[8])) {
        	 fp.addButton("收支统计",
     				ImageManager.getImageIconByShortName("onebit_15.png"), cell,col+1,
     				ZcTjPanel.class, "true".equals(right[8]));
        	 cell = cell+2;
         }
		if("true".equals(right[9])) {
			fp.addButton("营业额统计",
					ImageManager.getImageIconByShortName("gnucash.png"), cell,col+1,
					YyeTjPanel.class, "true".equals(right[9]));
			 cell = cell+2;
	
		}
		if("true".equals(right[10])) {
			fp.addButton("销售排行统计",
					ImageManager.getImageIconByShortName("onebit_16.png"), cell,col+1,
					SellOrderpane.class, "true".equals(right[10]));
			 cell = cell+2;
		}
		
		if("true".equals(right[17])) {
			fp.addButton("客户购买统计",
					ImageManager.getImageIconByShortName("userGm.png"),cell,col+1,
					GkJLPanel.class,
					(right.length > 18 && "true".equals(right[17])));
			cell = cell+1;
		}
		
		fp.addButton("查看更多统计",
				ImageManager.getImageIconByShortName("a12.png"),cell,col+1,
				ActionManager.getAction(MaijiaAction.class),
				true);

	}

	private void getSetinngPane(NavgationIndex fp, String[] right,int row,int col) {
		fp.addTitle("设置", ImageManager.getImageIconByShortName("set.png"), row, col);
		int cell = 1;
        if("true".equals(right[12])) {
        	fp.addButton("系统设置", ImageManager.getImageIconByShortName("a6.png"), cell,col+1, SettiingPanel.class, "true".equals(right[12]));
        	cell = cell+2;
        }
        if("true".equals(right[13])) {
    		fp.addButton("用户权限", ImageManager.getImageIconByShortName("a4.png"), 3,col+1, AuthorityPanel.class, "true".equals(right[13]));
    		cell = cell+2;
        }
		
		fp.addButton("密码修改", ImageManager.getImageIconByShortName("a3.png"), cell,col+1, ActionManager.getAction(PasswordAction.class), true);
		cell = cell+2;
		if("true".equals(right[14])) {
			fp.addButton("分类设置", ImageManager.getImageIconByShortName("a8.png"), 7,col+1, OptionsTypePanel.class, "true".equals(right[14]));
			cell = cell+2;
		}
	
		String longinname = SysEnv.getInstance().getLoginUserId();

		fp.addButton("云端备份设置", ImageManager.getImageIconByShortName("a9.png"), cell,col+1, ActionManager.getAction(SynAction.class), Constant.ADMIN.equals(longinname));
	}

}
