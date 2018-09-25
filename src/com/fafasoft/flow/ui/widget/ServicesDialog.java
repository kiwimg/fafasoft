package com.fafasoft.flow.ui.widget;

import javax.swing.*;

import com.fafasoft.flow.util.GuiUtils;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URISyntaxException;

public class ServicesDialog  extends JDialog {
	public ServicesDialog(Component owner) {
		setResizable(false);
		setTitle("服务支持");
		setSize(new Dimension(336, 269));
		setLocationRelativeTo(owner);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

	        JLabel lblQqlycvipgmailcom = new JLabel("QQ群:112353661(满)  21941225(满) 126901767");

	        lblQqlycvipgmailcom.setBounds(30, 84, 300, 17);
	        getContentPane().add(lblQqlycvipgmailcom);

	        JLabel lblwwwsyscom = new JLabel("<html>网址:<u>www.88sys.com</u></html>");
	       lblwwwsyscom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        lblwwwsyscom.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseClicked(MouseEvent e) {
                    Desktop desktop = Desktop.getDesktop();
                    URI netSite = null;
                    try {
                        netSite = new URI("http://www.88sys.com");
                    } catch (URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        desktop.browse(netSite);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
	        	}
	        });


	        lblwwwsyscom.setBounds(30, 123, 300, 17);
	        getContentPane().add(lblwwwsyscom);

	        JLabel lblQqlycvipgmailcom_1 = new JLabel("邮箱:support@88sys.com     ");

	        lblQqlycvipgmailcom_1.setBounds(30, 104, 300, 17);
	        getContentPane().add(lblQqlycvipgmailcom_1);

	        JLabel label = new JLabel("<html>微薄:<u>http://weibo.com/fafasoft</u></html>");
	        label.setBounds(30, 143, 247, 24);
	        getContentPane().add(label);
	        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        label.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseClicked(MouseEvent e) {
                    Desktop desktop = Desktop.getDesktop();
                    URI netSite = null;
                    try {
                        netSite = new URI("http://t.sina.com.cn/fafasoft");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    try {
                        desktop.browse(netSite);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
	        	}
	        });
	        //http://sighttp.qq.com/cgi-bin/check?sigkey=7273ed799e78a75c85319187c24a3be76e0bc7de593f8148554c34d42c69587d
	        JLabel label_1 = new JLabel("<html>在线客服:<u>与客服对话</u></html>");
	        label_1.setBounds(30, 186, 247, 21);
	        getContentPane().add(label_1);
	        label_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

	        JLabel label_2 = new JLabel("");
	        label_2.setIcon(new ImageIcon(ServicesDialog.class.getResource("/com/fafasoft/flow/resource/image/fafa.png")));
	        label_2.setBounds(0, 0, 422, 70);
	        getContentPane().add(label_2);
	        label_1.addMouseListener(new MouseAdapter() {

	        	public void mouseClicked(MouseEvent e) {
	        		GuiUtils.browser("http://sighttp.qq.com/cgi-bin/check?sigkey=7273ed799e78a75c85319187c24a3be76e0bc7de593f8148554c34d42c69587d");
	        	}
	        });
	}

}
