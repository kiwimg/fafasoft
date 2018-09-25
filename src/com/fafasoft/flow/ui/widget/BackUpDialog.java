package com.fafasoft.flow.ui.widget;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.JButton;

import com.fafasoft.flow.ui.AppStatusBar;
import com.fafasoft.flow.util.GuiUtils;
import com.fafasoft.flow.util.SynAuthentication;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Dialog.ModalityType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Color;

public class BackUpDialog extends JDialog {
	private JTextField textField;
	private JPasswordField passwordField;
	private JTextField textField_1;

	public BackUpDialog(Component owner) {
		//setLocation(-16, -28);
		setResizable(false);
		setIconImage(Toolkit
				.getDefaultToolkit()
				.getImage(
						BackUpDialog.class
								.getResource("/com/fafasoft/flow/resource/image/yygl.png")));

		getContentPane().setLayout(null);

		setTitle("数据备份设置");
		setSize(new Dimension(599, 404));
		setLocationRelativeTo(owner);
		//setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		JLabel lblNewLabel = new JLabel("请输入www.ymaijia.com注册账号和密码");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 14));
		lblNewLabel.setBounds(62, 60, 265, 25);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("账  户");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(89, 102, 54, 15);
		getContentPane().add(lblNewLabel_1);

		textField = new JTextField();
		textField.setBorder(null);
		String userId = SynAuthentication.getInstance().getUserId();
		if(userId != null) {
			textField.setText(userId);
			textField.setEditable(false);
		}else {
			textField.setEditable(true);	
		}
		textField.setFont(new Font("宋体", Font.PLAIN, 20));
		textField.setBounds(153, 95, 283, 29);
		getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("密  码");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(89, 145, 54, 15);
		getContentPane().add(lblNewLabel_2);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("宋体", Font.PLAIN, 20));
		passwordField.setBounds(153, 138, 283, 29);
		getContentPane().add(passwordField);

		JLabel lblNewLabel_3 = new JLabel("店铺授权码");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3.setBounds(72, 187, 71, 15);
		getContentPane().add(lblNewLabel_3);

		textField_1 = new JTextField();
		String code = SynAuthentication.getInstance().getAuthenticationCode();
		if(code != null) {
			textField_1.setText(code);
			textField_1.setEditable(false);
		}else {
			textField_1.setEditable(true);
		}
		textField_1.setFont(new Font("宋体", Font.PLAIN, 20));
		textField_1.setBounds(153, 180, 283, 29);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel(
				"<html><body>&nbsp;&nbsp;数据云备份存储，数据安全、永不丢失<br><br>&nbsp;&nbsp;随时随地掌握店铺经营情况，各种统计报表应有尽有</body></html>");
		lblNewLabel_4.setBorder(new TitledBorder(null, "数据同步备份的好处",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lblNewLabel_4.setBounds(28, 264, 531, 91);
		getContentPane().add(lblNewLabel_4);

		final JButton btnNewButton = new JButton("立即免费注册");
		btnNewButton.setForeground(new Color(255, 69, 0));
		btnNewButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnNewButton.setBorderPainted(true);
			}

			public void mouseExited(MouseEvent e) {
				btnNewButton.setBorderPainted(false);
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				GuiUtils.browser("http://www.ymaijia.com/register.do");
			}
		});
		btnNewButton.setBorderPainted(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorder(null);
		btnNewButton.setBounds(343, 58, 93, 29);

		getContentPane().add(btnNewButton);

		final JLabel mesageLabel = new JLabel("");
		mesageLabel.setForeground(Color.RED);
		mesageLabel.setFont(new Font("宋体", Font.PLAIN, 14));
		mesageLabel.setBounds(23, 219, 304, 35);
		getContentPane().add(mesageLabel);
		JButton btnNewButton_1 = new JButton("确认");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				String code = SynAuthentication.getInstance().getAuthenticationCode();
				String message = "<html>恭喜老板！云数据备份授权成功，你现在可以享受云数据备份！</html>";
				if(code != null || code.trim().length() >0) {
					message = "<html>已经在此客户端设置过授权信息，不能重复设置，请检查！</html>";
				}else {
					String username = textField.getText();
					String password = String.valueOf(passwordField.getPassword());
					String authcode = textField_1.getText();
					int recode = SynAuthentication.getInstance().authenticate(
							username, password, authcode);
					if (recode == -100) {
						message = "<html>用户和密码输入错误!注册www.ymaijia.com会员</html>";
					} else if (recode == -500) {
						message = "<html>网络不通，请检查！请先检查是否能上网</html>";
					} else if (recode == -101) {
						message = "<html>您还没有未你的店面授权！请登录www.ymaijia.com设置</html>";
					}  
				}
				
				mesageLabel.setText(message);
			}
		});
		btnNewButton_1.setBounds(343, 219, 93, 35);
		getContentPane().add(btnNewButton_1);
	
		if(code != null || code.trim().length() >0) {
			JLabel lblNewLabel_5 = new JLabel("当前授权状态:");
			lblNewLabel_5.setFont(new Font("宋体", Font.BOLD, 20));
			lblNewLabel_5.setBounds(62, 26, 137, 22);
			getContentPane().add(lblNewLabel_5);
			
			JLabel lblNewLabel_6 = new JLabel("已经授权，可以使用云备份啦！");
			lblNewLabel_6.setFont(new Font("宋体", Font.BOLD, 16));
			lblNewLabel_6.setBounds(209, 18, 298, 40);
			getContentPane().add(lblNewLabel_6);
		}
	
		
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				textField.setRequestFocusEnabled(true);
//				
//				textField.requestFocus();
//			}
//		});
	}
}
