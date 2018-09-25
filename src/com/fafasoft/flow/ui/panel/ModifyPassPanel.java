package com.fafasoft.flow.ui.panel;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.UserDAOImpl;
import com.fafasoft.flow.pojo.Config;
import com.fafasoft.flow.pojo.User;
import com.fafasoft.flow.ui.LoginFrame;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ModifyPassPanel extends JPanel {
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	public ModifyPassPanel() {
		setLayout(null);

		JLabel lbln = new JLabel("");
		lbln.setIcon(new ImageIcon(ModifyPassPanel.class
				.getResource("/com/fafasoft/flow/resource/image/fafa.png")));
		lbln.setBounds(0, 0, 336, 71);
		add(lbln);

		JLabel label = new JLabel(
				"由于初次使用流水记账系统,请先设置管理员密码");
		label.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, Color.RED));
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label.setBounds(0, 74, 336, 30);
		add(label);

		JLabel label_1 = new JLabel("用户名:");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_1.setBounds(20, 115, 54, 15);
		add(label_1);

		JLabel label_2 = new JLabel("新密码");
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_2.setBounds(20, 140, 54, 15);
		add(label_2);

		JLabel label_3 = new JLabel("确认密码");
		label_3.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_3.setBounds(13, 168, 61, 15);
		add(label_3);

		JLabel label_4 = new JLabel("管理员");
		label_4.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_4.setBounds(81, 115, 182, 15);
		add(label_4);

		JButton button = new JButton("确  认");

		button.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		button.setBounds(185, 197, 92, 30);
		add(button);

		passwordField = new JPasswordField();
		passwordField.setBounds(81, 135, 170, 25);
		add(passwordField);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(81, 163, 170, 25);
		add(passwordField_1);

		final JLabel label_5 = new JLabel("");
		label_5.setForeground(Color.RED);
		label_5.setBounds(12, 197, 206, 21);
		add(label_5);

		button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				String pass = String.valueOf(passwordField.getPassword());
				String againpass = String
						.valueOf(passwordField_1.getPassword());
				if (!pass.equals(againpass)) {
					label_5.setText("两次输入密码不一样,请重新输入");

				} else if (pass.length() >= 10) {
					label_5.setText("密码长度应小于10位,请重新输入");
				} else {
					Config config = new Config();
					config.setKey(Constant.IS_SETADMIN_PASS);
					config.setValue("true");
					 DAOContentFactriy.getConfigDAO().saveOrUpdateConfig(
							config);
					// ADMIN
				    UserDAOImpl serMoudle = DAOContentFactriy.getUserDAO();
					User user = serMoudle.getUserByid(Constant.ADMIN);
					user.setPassword(againpass);
					serMoudle.updateUser(user);
					LoginFrame login = LoginFrame.getInstance();
					login.setSize(336, 205);
					login.setTitle("登录发发流水账系统");
					login.setContentPane(new LoginPane());
				}
			}
		});
	}
}
