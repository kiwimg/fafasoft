package com.fafasoft.flow.ui.widget;

import com.fafasoft.flow.Version;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog  {
	public AboutDialog(Component owner) {
		//setAlwaysOnTop(true);
		setResizable(false);
		setTitle("关于发发");
		setSize(new Dimension(336, 219));
		setIconImage(Toolkit.getDefaultToolkit().getImage(AboutDialog.class.getResource("/com/fafasoft/flow/resource/image/yygl.png")));
		setLocationRelativeTo(owner);
	
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(AboutDialog.class.getResource("/com/fafasoft/flow/resource/image/fafa.png")));
		getContentPane().add(label, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		getContentPane().add(panel, BorderLayout.CENTER);
		
		JLabel label_1 = new JLabel(" 本软件版本"+ Version.getInstance().getVersion()+Version.getInstance().getBuild());

		label_1.setBounds(21, 21, 431, 17);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel(Version.getInstance().getCopyright());

		label_2.setBounds(30, 73, 346, 15);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("88sys.com版权所有");
		label_3.setBounds(31, 48, 135, 15);
		panel.add(label_3);
	}

}
