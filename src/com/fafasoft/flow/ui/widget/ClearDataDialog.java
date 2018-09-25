package com.fafasoft.flow.ui.widget;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.WindowConstants;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.SysEnv;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.pojo.User;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Dialog.ModalityType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class ClearDataDialog extends JDialog {
	private JPasswordField passwordField;

	public ClearDataDialog(final Component owner) {
		setResizable(false);
		setTitle("清除数据操作");
		
		setSize(new Dimension(332, 213));
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(owner);
		getContentPane().setLayout(null);
		final JLabel lblNewLabel = new JLabel("清除数据，请先输入"
				+ SysEnv.getInstance().getAdminUser().getUsernmae() + "密码");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 13));
		lblNewLabel.setBounds(45, 29, 260, 15);
		getContentPane().add(lblNewLabel);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("宋体", Font.PLAIN, 14));
		passwordField.setBounds(45, 64, 217, 30);
		getContentPane().add(passwordField);

		JButton btnNewButton = new JButton("确认");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				User user = SysEnv.getInstance().getAdminUser();
				String pass = String.valueOf(passwordField.getPassword());
				
				if (pass.equals(user.getPassword())) {
					try {
						DAOContentFactriy.getClearDataDao().clearData();
					} catch (SQLException e1) {
					
						e1.printStackTrace();
					}
					dispose();
					JOptionPane.showMessageDialog(owner,
						    "数据清除完成！");
				} else {
					lblNewLabel.setText("输入密码错误！请重新输入");
					return;
				}

			}
		});
		btnNewButton.setBounds(85, 127, 93, 30);
		getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("取消");

		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}
		});
		btnNewButton_1.setBounds(201, 127, 93, 30);
		getContentPane().add(btnNewButton_1);
	}


}
