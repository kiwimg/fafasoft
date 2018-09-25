package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.SysEnv;
import com.fafasoft.flow.Version;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.pojo.Config;
import com.fafasoft.flow.pojo.User;
import com.fafasoft.flow.ui.AppStatusBar;
import com.fafasoft.flow.ui.LoginFrame;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.widget.SelectType;
import com.fafasoft.flow.ui.widget.TCPopupEventQueue;
import com.fafasoft.flow.util.RemoteMessage;
import com.sun.awt.AWTUtilities;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-5-13
 * Time: 15:21:08
 * To change this template use File | Settings | File Templates.
 */
public class LoginPane extends JPanel {
    private JPasswordField passwordField;
    private JComboBox comboBox;
    private JLabel messagealert;

    public LoginPane() {
        setBorder(null);
        setLayout(null);
        JLabel label_2 = new JLabel("用户名:");

        label_2.setBounds(32, 78, 47, 22);
        add(label_2);
        comboBox = new JComboBox();
        comboBox.setBounds(79, 78, 183, 22);
        comboBox.setModel(new DefaultComboBoxModel(SelectType.getUserList()));
        add(comboBox);

        JLabel label = new JLabel("密  码:");
        label.setFont(new Font("宋体", Font.PLAIN, 12));
        label.setBounds(32, 117, 47, 15);
        add(label);

        passwordField = new JPasswordField();
        passwordField.setBounds(79, 114, 183, 21);

        passwordField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {// 回车
                	  EventQueue.invokeLater(new Runnable() {
                		  public void run() {
                			  submmit();
                		  }
                  });                   
                }
            }
        });
        add(passwordField);
        messagealert = new JLabel("输入密码完成按回车键登录");

        messagealert.setBounds(78, 140, 246, 22);
        add(messagealert);

        JLabel label_3 = new JLabel("");
        label_3.setIcon(new ImageIcon(LoginPane.class.getResource("/com/fafasoft/flow/resource/image/fafa.png")));
        label_3.setBounds(0, 0, 338, 71);
        add(label_3);

        addAncestorListener(new AncestorListener() {
            public void ancestorAdded(AncestorEvent evt) {
                passwordField.requestFocus();
            }

            public void ancestorRemoved(AncestorEvent evt) {
            }

            public void ancestorMoved(AncestorEvent evt) {
            }
        });
    }

    private void submmit() {
        String pass = String.valueOf(passwordField.getPassword());
        final User option = (User) comboBox.getSelectedItem();
        String passh = option.getPassword();
   
        if (passh.equals(pass)) {
            passwordField.setText("");
            SysEnv.getInstance().setLoginUserId(option.getId());
            SysEnv.getInstance().setloginUserName(option.getUsernmae());
            String[] rights =SysEnv.getInstance().getRight();
            if(rights== null || rights.length ==0) {
            	messagealert.setForeground(Color.RED);
                messagealert.setText("对不起,此用户没有设置系统使用功能");
            }
            LoginFrame.getInstance().setVisible(false);
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                	MainWindows frame = MainWindows.getInstance();
                    frame.openPanel();
                    frame.getAppStatusBar().resetLoginUserName();
                    frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                    if (!frame.isRelogin()) {
                        frame.setSize();
                        frame.setCenter();
                        frame.setVisible(true);
                        if ("true".equals(SysEnv.getInstance().getFrameMaxPolicy())) {
                            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                            frame.setBounds(0, 0, screenSize.width, screenSize.height);
                        }
                        Toolkit.getDefaultToolkit().getSystemEventQueue().push(new TCPopupEventQueue());
                    }else {
                    	frame.setVisible(true);
                    }
                    Shape shape  = new RoundRectangle2D.Double(0, 0, frame.getSize().getWidth(),frame.getSize().getHeight(), 3.5D, 3.5D);  
					AWTUtilities.setWindowShape(frame, shape); 
                    final AppStatusBar appStatusBar = frame.getAppStatusBar();
                    new Thread(new Runnable() {
                        public void run() {
                        	appStatusBar.getRemoteMessage();
                        }
                    }).start();
                }
            });
        } else {
            passwordField.setText("");
            messagealert.setForeground(Color.RED);
            messagealert.setText("对不起,输入密码错误,请重新输入");
        }
    }
}
