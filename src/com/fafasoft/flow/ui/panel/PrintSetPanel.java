package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.SysEnv;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.ConfigDAOImpl;
import com.fafasoft.flow.pojo.Config;
import com.fafasoft.flow.ui.widget.Options;
import com.fafasoft.flow.ui.widget.SelectType;
import com.fafasoft.flow.util.GuiUtils;
import com.fafasoft.plugin.pos.ticket.SmallTicket;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Properties;
import java.util.Vector;


public class PrintSetPanel extends JPanel {
	private JTextField titletextField;
	private JTextField jiaozhutextField;
	private JLabel message;
	private JComboBox printModecomboBox;
	private JComboBox printObcomboBox_1;
	private JTextField textFieldaddr;
	private JTextField textFieldphone;

	public PrintSetPanel() {
		boolean isprint = true;
		setLayout(null);
		message = new JLabel("请配置打印小票选项");
		message.setForeground(new Color(178, 34, 34));

		message.setBounds(75, 10, 427, 25);
		add(message);
		JLabel label = new JLabel("打印模式");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(55, 45, 72, 15);
		add(label);

		printModecomboBox = new JComboBox();

		printModecomboBox.setModel(new DefaultComboBoxModel(SelectType
				.getPrintMode()));
		printModecomboBox.setBounds(137, 40, 163, 25);
		add(printModecomboBox);

		JLabel label_1 = new JLabel("小票打印机");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(55, 80, 72, 15);
		add(label_1);
		printObcomboBox_1 = new JComboBox();
		printObcomboBox_1.setModel(new DefaultComboBoxModel(getPrintList()));

		printObcomboBox_1.setBounds(137, 75, 298, 25);
		add(printObcomboBox_1);

		JLabel label_2 = new JLabel("小票标题");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(55, 115, 72, 15);
		add(label_2);

		titletextField = new JTextField();

		titletextField.setBounds(137, 110, 298, 25);
		add(titletextField);
		titletextField.setColumns(10);

		JButton button = new JButton("保存");
		button.setEnabled(isprint);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Object printmOde = printModecomboBox.getSelectedItem();
				Object printName = printObcomboBox_1.getSelectedItem();

				String tile = titletextField.getText();
				String jiaozhu = jiaozhutextField.getText();
				Config[] configs = new Config[6];
				Config config1 = new Config();
				config1.setKey(Constant.PRINT_MODE);
				config1.setValue(((Options) printmOde).getKey());
				configs[0] = config1;

				Config config2 = new Config();
				config2.setKey(Constant.PRINT_NAME);
				config2.setValue(String.valueOf(printName));
				configs[1] = config2;

				Config config3 = new Config();
				config3.setKey(Constant.PRINT_TITLE);
				config3.setValue(tile);
				configs[2] = config3;

				Config config4 = new Config();
				config4.setKey(Constant.PRINT_SHEET);
				config4.setValue(jiaozhu);
				configs[3] = config4;

				Config config5 = new Config();
				config5.setKey(Constant.PRINT_ADDRESS);
				config5.setValue(textFieldaddr.getText());
				configs[4] = config5;

				Config config6 = new Config();
				config6.setKey(Constant.PRINT_phone);
				config6.setValue(textFieldphone.getText());
				configs[5] = config6;
				DAOContentFactriy.getConfigDAO().addConfigs(configs);

				message.setText("保存成功");
			}
		});
		button.setBounds(237, 246, 93, 31);
		add(button);

		JLabel label_3 = new JLabel("小票脚注");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setBounds(55, 215, 72, 15);
		add(label_3);

		jiaozhutextField = new JTextField();

		jiaozhutextField.setText("谢谢惠顾，请保管好您的小票");
		jiaozhutextField.setColumns(10);
		jiaozhutextField.setBounds(137, 209, 300, 25);
		add(jiaozhutextField);
	
		JLabel label_4 = new JLabel("<html><u>购买小票打印机</u></html>");
		label_4.setForeground(new Color(210, 105, 30));
		label_4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		label_4.setBounds(310, 42, 116, 20);
		label_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Properties properties = SysEnv.getInstance().getRemoteProperties();
				 String taobao = properties.getProperty("taobao");
				if(taobao!=null && taobao.trim().length()>0) {
					GuiUtils.browser(taobao.trim());	
				}else {
					GuiUtils.browser("http://fafasoft.taobao.com/");
				}	
			}
		});
		add(label_4);

		JLabel lblusb = new JLabel("目前打印小票只支持USB接口驱动打印，如果需要串口和并口打印请咨询软件开发商");
		lblusb.setFont(new Font("宋体", Font.BOLD, 12));
		lblusb.setBounds(75, 315, 496, 25);
		add(lblusb);

		JButton btnNewButton = new JButton("测试打印");

		btnNewButton.setEnabled(isprint);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Vector vv = new Vector();
				Vector vv2 = new Vector(7);
				vv.add(vv2);
				vv2.add("货物名称");
				vv2.add("货物号码");
				vv2.add("货物名称");
				vv2.add(1D);
				vv2.add(20D);
				vv2.add(20D);
				vv2.add(20D);
				
				Vector vv3 = new Vector(7);
				vv.add(vv3);
				vv3.add("货物名称");
				vv3.add("货物号码");
				vv3.add("货物名称");
				vv3.add(1D);
				vv3.add(20D);
				vv3.add(20D);
				vv3.add(20D);
                SmallTicket smallTicket = new SmallTicket(vv, "0.00", "0.00",
						"0.00", null);
                //smallTicket.setPrintData();
				smallTicket.print();
			}
		});
		btnNewButton.setBounds(344, 246, 93, 31);
		add(btnNewButton);

		JLabel lblNewLabel = new JLabel("地址");
		lblNewLabel.setBounds(103, 148, 24, 15);
		add(lblNewLabel);

		textFieldaddr = new JTextField();
		textFieldaddr.setBounds(137, 145, 298, 21);
		add(textFieldaddr);
		textFieldaddr.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("电话");
		lblNewLabel_1.setBounds(103, 181, 29, 15);
		add(lblNewLabel_1);

		textFieldphone = new JTextField();
		textFieldphone.setBounds(136, 178, 299, 21);
		add(textFieldphone);
		textFieldphone.setColumns(10);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initView();
			}
		});
	}

	private void initView() {
		ConfigDAOImpl configDAO = DAOContentFactriy.getConfigDAO();
		Config configMOde = configDAO.getConfig(Constant.PRINT_MODE);
		if (configMOde != null) {
			for (int i = 0; i < printModecomboBox.getItemCount(); i++) {
				Options options = (Options) printModecomboBox.getItemAt(i);
				if (options.getKey().equals(configMOde.getValue())) {
					printModecomboBox.setSelectedIndex(i);
					break;
				}
			}
			printModecomboBox.getEditor().setItem(configMOde.getValue());
		}

		Config configname = configDAO.getConfig(Constant.PRINT_NAME);
		if (configname != null) {
			printObcomboBox_1.getEditor().setItem(configname.getValue());
			for (int i = 0; i < printObcomboBox_1.getItemCount(); i++) {
				Object nameob = printObcomboBox_1.getItemAt(i);
				if (nameob.equals(configname.getValue())) {
					printObcomboBox_1.setSelectedIndex(i);
					break;
				}
			}
		}

		Config configTile = configDAO.getConfig(Constant.PRINT_TITLE);
		if (configTile != null) {
			titletextField.setText(configTile.getValue());
		}
		Config configaddr = configDAO.getConfig(Constant.PRINT_ADDRESS);
		if (configaddr != null) {
			textFieldaddr.setText(configaddr.getValue());
		}

		Config configphone = configDAO.getConfig(Constant.PRINT_phone);
		if (configphone != null) {
			textFieldphone.setText(configphone.getValue());
		}
		Config configsheet = configDAO.getConfig(Constant.PRINT_SHEET);
		if (configsheet != null) {
			jiaozhutextField.setText(configsheet.getValue());
		}

	}

	public static Object[] getPrintList() {
		PrintService[] pservices = PrintServiceLookup.lookupPrintServices(
				DocFlavor.SERVICE_FORMATTED.PRINTABLE, null);
		Object[] object = new Object[] {};
		if (pservices != null && pservices.length > 0) {
			object = new Object[pservices.length];
			for (int i = 0; i < pservices.length; i++) {
				object[i] = pservices[i].getName();
			}
		}
		return object;
	}
}
