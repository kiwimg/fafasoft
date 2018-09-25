package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.action.PageAction;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.StockDAOImpl;
import com.fafasoft.flow.pojo.Stock;
import com.fafasoft.flow.ui.LimitedDocument;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.widget.Options;
import com.fafasoft.flow.ui.widget.Page;
import com.fafasoft.flow.ui.widget.SelectType;
import com.fafasoft.flow.util.GuiUtils;


import org.h2.engine.Constants;


import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class KunAlermPanel extends BaseJPanel {
	private JTextField stockAlarmNum;
	private JComboBox stockAlarmTypes;
	private JTable table_1;
	private double alarmNum = 20;

	public KunAlermPanel() {
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 60));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(null);

		JLabel label = new JLabel("报警类型");
		label.setBounds(32, 21, 54, 15);
		panel.add(label);

		JLabel label_1 = new JLabel("警戒线");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(204, 21, 54, 15);
		panel.add(label_1);

		stockAlarmNum = new JTextField();
		String dubString = "1234567890.";
		stockAlarmNum.setDocument(new LimitedDocument(20, dubString));
		stockAlarmNum.setBounds(266, 18, 106, 21);
		panel.add(stockAlarmNum);
		stockAlarmNum.setColumns(10);

		JButton button = new JButton("查询");
		button.setBounds(382, 17, 74, 23);
		panel.add(button);

		stockAlarmTypes = new JComboBox();
		stockAlarmTypes.setModel(new DefaultComboBoxModel(SelectType
				.getStockAlarmTypes()));
		stockAlarmTypes.setBounds(87, 18, 107, 21);
		panel.add(stockAlarmTypes);

		final Page pageAlarm = new Page(new PageAction() {
			public void pageFirst() {
				StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
				Options options = (Options) stockAlarmTypes.getSelectedItem();
				String key = options.getKey();
				List pageList = stockMoudle.getStockAlarm(key, alarmNum, 0, 20);
				pageAlarmData(pageList);
			}

			public void pagePrev(int pagenum) {
				StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
				Options options = (Options) stockAlarmTypes.getSelectedItem();
				String key = options.getKey();
				List pageList = stockMoudle.getStockAlarm(key, alarmNum,
						pagenum, 20);
				pageAlarmData(pageList);
			}

			public void pageNext(int pagenum) {
				StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();

				Options options = (Options) stockAlarmTypes.getSelectedItem();
				String key = options.getKey();

				List pageList = stockMoudle.getStockAlarm(key, alarmNum,
						pagenum, 20);
				pageAlarmData(pageList);
			}

			public void pageLast(int pagenum) {
				StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();

				Options options = (Options) stockAlarmTypes.getSelectedItem();
				String key = options.getKey();

				List pageList = stockMoudle.getStockAlarm(key, alarmNum,
						pagenum, 20);
				pageAlarmData(pageList);
			}

			public void itemStateChanged(int pagenum) {
				StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();

				Options options = (Options) stockAlarmTypes.getSelectedItem();
				String key = options.getKey();

				List pageList = stockMoudle.getStockAlarm(key, alarmNum,
						pagenum, 20);
				pageAlarmData(pageList);
			}

			public void export(MouseEvent e) {
				try {
					// String filepath = file.getAbsolutePath();
					StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
					Options options = (Options) stockAlarmTypes
							.getSelectedItem();
					String key = options.getKey();
					List list = stockMoudle.getStockAlarm(key, alarmNum, 0,
							2000000000);
					if (list != null && list.size() > 0) {
						JFileChooser jfc = new JFileChooser("d:/");
						jfc
								.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
						File fileff = new File("库存报警清单.csv");
						jfc.setSelectedFile(fileff);
						int result = jfc.showSaveDialog(MainWindows
								.getInstance());
						if (result == JFileChooser.CANCEL_OPTION)
							return;
						File savedFile = jfc.getSelectedFile();
						if (savedFile.exists()) {
							int overwriteSelect = JOptionPane
									.showConfirmDialog(MainWindows
											.getInstance(),
											"<html><font size=3>文件"
													+ savedFile.getName()
													+ "已存在，是否覆盖?</font><html>",
											"是否覆盖?", JOptionPane.YES_NO_OPTION,
											JOptionPane.WARNING_MESSAGE);
							if (overwriteSelect != JOptionPane.YES_OPTION) {
								return;
							}
						}
						OutputStream out = new FileOutputStream(savedFile);
						out = new BufferedOutputStream(out,
								Constants.IO_BUFFER_SIZE);
						BufferedWriter output = new BufferedWriter(
								new OutputStreamWriter(out, "gbk"));
						StringBuffer stringBuffer = new StringBuffer();
						stringBuffer.append("货号");
						stringBuffer.append(",");
						stringBuffer.append("剩余数量");
						stringBuffer.append(",");
						stringBuffer.append("类型");
						stringBuffer.append(",");
						stringBuffer.append("成本");
						stringBuffer.append(",");
						stringBuffer.append("名称");
						stringBuffer.append(",");
						stringBuffer.append("颜色");
						stringBuffer.append(",");
						stringBuffer.append("规格");

						stringBuffer.append(",");
						stringBuffer.append("进货时间");
						stringBuffer.append("\r\n");
						output.write(String.valueOf(stringBuffer));
						for (int i = 0; i < list.size(); i++) {
							Stock stock = (Stock) list.get(i);
							StringBuilder sb = new StringBuilder(128);
							sb.append(stock.getCatno());
							sb.append(",");
							sb.append(stock.getSyamount());
							sb.append(",");
							sb.append(stock.getType());
							sb.append(",");
							sb.append(stock.getCostprice());
							sb.append(",");
							sb.append(stock.getStockname());
							sb.append(",");
							sb.append(stock.getColor());
							sb.append(",");
							sb.append(stock.getSpecif());
							sb.append(",");
							sb.append(stock.getDate());
							sb.append("\r\n");
							output.write(String.valueOf(sb));
						}
						output.close();
						JOptionPane.showMessageDialog(
								MainWindows.getInstance(), "文件导出成功");
					} else {
						JOptionPane.showMessageDialog(
								MainWindows.getInstance(), "没数据导出", "数据导出通知",
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		add(pageAlarm, BorderLayout.SOUTH);

		button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String num = stockAlarmNum.getText();
				Options options = (Options) stockAlarmTypes.getSelectedItem();
				String key = options.getKey();
				String text = options.getText();;
				if ("".equals(num.trim())) {
	
					GuiUtils.toolTips(stockAlarmNum,"请输入" + text + "数量");
					return;
				}

				StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
				try {
					double sl = Double.parseDouble(num);
					alarmNum = sl;
					int size = stockMoudle.getStockAlarmSize(key, sl);
					pageAlarm.setPageInfo(size);
					List pageList = stockMoudle.getStockAlarm(key, sl, 0, 20);
	
					pageAlarmData(pageList);
				} catch (NumberFormatException ex) {
					GuiUtils.toolTips(stockAlarmNum,"请输入" + text + "数量不是数字");

				}
			}
		});
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		table_1 = new JTable();

		table_1 = new JTable();
		table_1.setRowHeight(23);
		table_1.setModel(new DefaultTableModel(null, new String[] {
				"\u8D27\u53F7", "\u5269\u4F59\u6570\u91CF", "\u7C7B\u578B",
				"\u6210\u672C", "\u540D\u79F0", "\u989C\u8272", "\u89C4\u683C",
				"\u5165\u5E93\u65F6\u95F4", "供应商" }) {
			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table_1);

	}

	private void pageAlarmData(List list) {
		clearAlarm();
		if (list != null && list.size() > 0) {
			DefaultTableModel tableModel = (DefaultTableModel) table_1
					.getModel();
			for (int i = 0; i < list.size(); i++) {
				Stock stock = (Stock) list.get(i);
				Object[] rowData = new Object[] { stock.getCatno(),
						stock.getSyamount(), stock.getType(),
						stock.getCostprice(), stock.getStockname(),
						stock.getColor(), stock.getSpecif(), stock.getDate(),
						stock.getSuppliers() };
				tableModel.insertRow(i, rowData);
			}

		}
	}

	public void clearAlarm() {
		DefaultTableModel tableModel = (DefaultTableModel) table_1.getModel();
		if (tableModel.getRowCount() > 0) {
			tableModel.setRowCount(0);
		}
	}
}
