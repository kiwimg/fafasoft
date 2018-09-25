package com.fafasoft.flow;
import javax.swing.*;
import java.awt.*;

public class WelcomeSplashEx extends javax.swing.JPanel{
	private javax.swing.JLabel lblMessage;
	public WelcomeSplashEx() {
		setBackground(Color.WHITE);
		//getContentPane().setBorder(new LineBorder(new Color(255, 255, 204)));
		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 177, 54, 147, 0};
		gridBagLayout.rowHeights = new int[]{107, 30, 68, 24, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblMessage = new JLabel("正在启动中.....");
		lblMessage.setFont(new Font("宋体", Font.BOLD, 12));
		lblMessage.setForeground(new Color(22, 178, 234));
		GridBagConstraints gbc_lblMessage = new GridBagConstraints();
		gbc_lblMessage.fill = GridBagConstraints.BOTH;
		gbc_lblMessage.insets = new Insets(0, 0, 0, 5);
		gbc_lblMessage.gridx = 1;
		gbc_lblMessage.gridy = 3;
		add(lblMessage, gbc_lblMessage);
		//pack();
        Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(395, 227);
        Dimension labelSize = this.getSize();
        setLocation(screenSize.width/2 - (labelSize.width/2),
                    screenSize.height/2 - (labelSize.height/2));
        setVisible(true);
        screenSize = null;
	}
	public void setMessage(String info) {
        lblMessage.setText(info);
    }
}
