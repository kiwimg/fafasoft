package com.fafasoft.flow.ui.widget;

import javax.swing.*;

import com.fafasoft.flow.SysEnv;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.UserDAOImpl;
import com.fafasoft.flow.pojo.User;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PasswordDialog extends JDialog {
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JLabel label_2;

	public PasswordDialog(Component owner) {
		setResizable(false);
		setTitle("密码设置");
		setSize(new Dimension(338, 210));
		setLocationRelativeTo(owner);

		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		JLabel label = new JLabel("新密码");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(36, 50, 61, 15);
		getContentPane().add(label);

		passwordField = new JPasswordField();
		passwordField.setBounds(104, 44, 156, 25);
		getContentPane().add(passwordField);
		passwordField.setColumns(10);

		JLabel label_1 = new JLabel("确认密码");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(15, 87, 82, 15);
		getContentPane().add(label_1);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(104, 82, 156, 25);
		getContentPane().add(passwordField_1);
		passwordField_1.setColumns(10);

		JButton button = new JButton("确认");
	
		button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String p1 = String.valueOf(passwordField.getPassword());
				String p2 = String.valueOf(passwordField_1.getPassword());
		
				if ("".equals(p1.trim())) {
					label_2.setText("请输入新密码");
					passwordField.requestFocus();
					return;
				}
				if ("".equals(p2.trim())) {
					label_2.setText("请输入确认密码");
					passwordField_1.requestFocus();
					return;
				}
				if (!p1.equals(p2)) {
					label_2.setText("两次输入的密码不一致！请重新输入");
					passwordField.setText("");
					passwordField_1.setText("");
					passwordField.requestFocus();
					return;
				}
				if (p1.length() >10) {
					label_2.setText("设置用户密码长度应该小于10位");
					passwordField.setText("");
					passwordField_1.setText("");
					passwordField.requestFocus();
					return;
				}
				
				UserDAOImpl serMoudle = DAOContentFactriy.getUserDAO();
				String id = SysEnv.getInstance().getLoginUserId();
				User user = serMoudle.getUserByid(id);
				user.setPassword(p2);
				serMoudle.updateUser(user);
				label_2.setText("密码修改成功，请牢记");
			}
		});
		button.setBounds(88, 128, 81, 30);
		getContentPane().add(button);

		JButton button_1 = new JButton("取消");
		button_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		button_1.setBounds(179, 128, 81, 30);
		getContentPane().add(button_1);

		label_2 = new JLabel("设置用户密码长度应该小于10位");

		label_2.setForeground(Color.RED);
		label_2.setBounds(26, 10, 273, 27);
		getContentPane().add(label_2);

	}
}
