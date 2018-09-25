package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.action.PageAction;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.AccountsDaoImpl;
import com.fafasoft.flow.dao.impl.CustomDAOImpl;
import com.fafasoft.flow.dao.impl.DailyExpensesDAOImpl;
import com.fafasoft.flow.dao.impl.SuppliersDAOImpl;
import com.fafasoft.flow.pojo.Accounts;
import com.fafasoft.flow.pojo.Custom;
import com.fafasoft.flow.pojo.DailyExpenses;
import com.fafasoft.flow.pojo.Suppliers;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.widget.AccountDialog;
import com.fafasoft.flow.ui.widget.Options;
import com.fafasoft.flow.ui.widget.Page;
import com.fafasoft.flow.ui.widget.SelectType;
import com.fafasoft.flow.util.GuiUtils;
import org.h2.engine.Constants;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: 2010-6-1 Time: 12:29:15
 * To change this template use File | Settings | File Templates.
 */
public class ZcTjPanel extends BaseJPanel implements LazyPageContent {

	private JXDatePicker datePicker_1;
	private JXDatePicker datePicker;
	private JXDatePicker datePicker_2;
	private JXDatePicker datePicker_3;
	private JTable table;
	private JComboBox comboBox;
    private JLabel label_4;
	private String stime;
	private String etime;
	private String stype;
	private String shourustime;
	private String shouruetime;
	private String shourustype;
	private JTable table_1;
	private JTextField textField;

	public ZcTjPanel() {
		setLayout(new BorderLayout(0, 0));
		
	}
	public void initPanel() {
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("宋体", Font.PLAIN, 12));
		add(tabbedPane, BorderLayout.CENTER);
		ImageIcon imageIcon1 = new ImageIcon(OptionsTypePanel.class
				.getResource("/com/fafasoft/flow/resource/image/table_go.png"));
		JPanel panel = new JPanel();
		tabbedPane.addTab("日常支出统计", imageIcon1, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		zhichuPanal(panel);

		final JPanel panel_4 = new JPanel();
		tabbedPane.addTab("其它收入统计", imageIcon1, panel_4, null);
		panel_4.setLayout(new BorderLayout(0, 0));

		final JPanel panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout(0, 0));

		tabbedPane.addTab("客户往来帐统计", imageIcon1, panel_2, null);
		//keHuAccounPanel(panel_2,Constant.SUPPLIERS_ACCOUNT,"供应商");

		final JPanel panel_6 = new JPanel();
		panel_6.setLayout(new BorderLayout(0, 0));
		tabbedPane.addTab("供应商往来帐统计", imageIcon1, panel_6, null);
		tabbedPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = tabbedPane.getSelectedIndex();
				if (index == 1 && panel_4.getComponentCount() == 0) {

					qiTaShouRuPanel(panel_4);
				} else if (index == 2 && panel_2.getComponentCount() == 0) {
					keHuAccounPanel(panel_2,Constant.CUSTOMER_ACCOUNT,"客户");
				} else if (index == 3 && panel_6.getComponentCount() == 0) {
					keHuAccounPanel(panel_6,Constant.SUPPLIERS_ACCOUNT,"供应商");
				}
			}
		});
		
	}
	// 日常支出
	private void zhichuPanal(JPanel panel) {
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(10, 75));
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(null);

		JLabel label = new JLabel("开始时间");
		label.setBounds(34, 14, 48, 15);
		panel_1.add(label);

		datePicker = new JXDatePicker();
		datePicker.setBounds(87, 10, 109, 23);
		datePicker.getEditor().setEditable(false);
		datePicker.getEditor().setColumns(10);
		datePicker.setFormats("yyyy-MM-dd");
		datePicker.getEditor().setFont(new Font("宋体", Font.BOLD, 12));
		panel_1.add(datePicker);

		JLabel label_1 = new JLabel("结束时间");
		label_1.setBounds(230, 14, 48, 15);
		panel_1.add(label_1);

		datePicker_1 = new JXDatePicker();
		datePicker_1.setBounds(283, 10, 109, 23);
		datePicker_1.getEditor().setEditable(false);
		datePicker_1.setFormats("yyyy-MM-dd");
		datePicker_1.getEditor().setFont(new Font("宋体", Font.BOLD, 12));
		datePicker_1.getEditor().setColumns(10);
		panel_1.add(datePicker_1);

		JLabel label_2 = new JLabel("支出类型");
		label_2.setBounds(34, 42, 48, 15);
		panel_1.add(label_2);

		comboBox = new JComboBox();
		comboBox.setBounds(87, 39, 109, 21);
		panel_1.add(comboBox);

		final Page page = new Page(new PageAction() {
			public void pageFirst() {

				DailyExpensesDAOImpl expensesMoudle = DAOContentFactriy
						.getDailyExpensesDAO();
				List list = null;
				if (stime != null || etime != null || stype != null) {
					list = expensesMoudle.getList(Constant.EXPENSES, stype,
							stime, etime, 0, 20);
				} else {
					list = expensesMoudle.getList(Constant.EXPENSES, null,
							null, null, 0, 20);
				}
				pageData(list, table);
			}

			public void pagePrev(int pagenum) {
				DailyExpensesDAOImpl expensesMoudle = DAOContentFactriy
						.getDailyExpensesDAO();
				List list = null;
				if (stime != null || etime != null || stype != null) {
					list = expensesMoudle.getList(Constant.EXPENSES, stype,
							stime, etime, pagenum, 20);
				} else {
					list = expensesMoudle.getList(Constant.EXPENSES, null,
							null, null, pagenum, 20);
				}
				pageData(list, table);
			}

			public void pageNext(int pagenum) {
				DailyExpensesDAOImpl expensesMoudle = DAOContentFactriy
						.getDailyExpensesDAO();
				List list = null;
				if (stime != null || etime != null || stype != null) {
					list = expensesMoudle.getList(Constant.EXPENSES, stype,
							stime, etime, pagenum, 20);
				} else {
					list = expensesMoudle.getList(Constant.EXPENSES, null,
							null, null, pagenum, 20);
				}
				pageData(list, table);
			}

			public void pageLast(int pagenum) {
				DailyExpensesDAOImpl expensesMoudle = DAOContentFactriy
						.getDailyExpensesDAO();
				List list = null;
				if (stime != null || etime != null || stype != null) {
					list = expensesMoudle.getList(Constant.EXPENSES, stype,
							stime, etime, pagenum, 20);
				} else {
					list = expensesMoudle.getList(Constant.EXPENSES, null,
							null, null, pagenum, 20);
				}
				pageData(list, table);
			}

			public void itemStateChanged(int pagenum) {
				DailyExpensesDAOImpl expensesMoudle = DAOContentFactriy
						.getDailyExpensesDAO();
				List list = null;
				if (stime != null || etime != null || stype != null) {
					list = expensesMoudle.getList(Constant.EXPENSES, stype,
							stime, etime, pagenum, 20);
				} else {
					list = expensesMoudle.getList(Constant.EXPENSES, null,
							null, null, pagenum, 20);
				}
				pageData(list, table);
			}

			public void export(MouseEvent e) {
				try {
					DailyExpensesDAOImpl expensesMoudle = DAOContentFactriy
							.getDailyExpensesDAO();
					List list = null;
					if (stime != null || etime != null || stype != null) {
						list = expensesMoudle.getList(Constant.EXPENSES, stype,
								stime, etime);
					} else {
						list = expensesMoudle.getList(Constant.EXPENSES, null,
								null, null);
					}
					if (list != null && list.size() > 0) {
						JFileChooser jfc = new JFileChooser("d:/");
						jfc
								.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
						File fileff = new File("日常支出清单.csv");
						jfc.setSelectedFile(fileff);
						int result = jfc.showSaveDialog(table);
						if (result == JFileChooser.CANCEL_OPTION)
							return;
						File savedFile = jfc.getSelectedFile();
						if (savedFile.exists()) {
							int overwriteSelect = JOptionPane
									.showConfirmDialog(table,
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
						stringBuffer.append("支出日期");
						stringBuffer.append(",");
						stringBuffer.append("支出类型");
						stringBuffer.append(",");
						stringBuffer.append("支出金额");
						stringBuffer.append("\r\n");
						output.write(String.valueOf(stringBuffer));
						for (int i = 0; i < list.size(); i++) {
							DailyExpenses dailyExpenses = (DailyExpenses) list
									.get(i);
							StringBuilder sb = new StringBuilder(128);
							sb.append(dailyExpenses.getDate());
							sb.append(",");
							sb.append(dailyExpenses.getType());
							sb.append(",");
							sb.append(dailyExpenses.getPay());
							sb.append("\r\n");
							output.write(String.valueOf(sb));
						}
						output.close();

						JOptionPane.showMessageDialog(MainWindows.getInstance(), "文件导出成功");
					} else {
						JOptionPane.showMessageDialog(MainWindows.getInstance(), "没数据导出", "导出数据通知",
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		});
		panel.add(page, BorderLayout.SOUTH);

		JButton button = new JButton("查 询");
		button.setBounds(323, 38, 69, 23);
		button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				String type = comboBox.getEditor().getItem().toString();
				if ("all".equals(type)) {
					stype = null;
					type = null;
				}
				String starttime = datePicker.getEditor().getText();
				String endtime = datePicker_1.getEditor().getText();
				stime = starttime;
				etime = endtime;
				stype = type;
				submmit(datePicker, datePicker_1, Constant.EXPENSES, table,
						page, type, label_4);
			}
		});
		panel_1.add(button);

		JLabel label_3 = new JLabel("支出金额总计");
		label_3.setBounds(413, 42, 72, 15);
		panel_1.add(label_3);

		label_4 = new JLabel("0");
		label_4.setBounds(495, 41, 95, 16);
		label_4.setFont(new Font("宋体", Font.BOLD, 14));
		panel_1.add(label_4);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(null);
		panel.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_3.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setRowHeight(21);
		table.setAutoCreateRowSorter(true);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				clickedTable(table, "删除日常支出数据", Constant.EXPENSES);
			}
		});
		table.setModel(new DefaultTableModel(null, new String[] { "id",
				"\u652F\u51FA\u65E5\u671F", "\u652F\u51FA\u7C7B\u578B",
				"\u652F\u51FA\u91D1\u989D", "\u64CD\u4F5C" }) {
			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class, Double.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { true, false, false,
					false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		table.getTableHeader().setReorderingAllowed(false);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setMinWidth(100);
		table.getColumnModel().getColumn(1).setMaxWidth(100);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(4).setPreferredWidth(60);
		table.getColumnModel().getColumn(4).setMaxWidth(60);

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		TableColumn tc = table.getColumn("操作");
		tc.setPreferredWidth(60);
		tc.setCellRenderer(renderer);
		scrollPane.setViewportView(table);
	}

	// 其他收入
	private void qiTaShouRuPanel(JPanel panel_4) {
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		final Page page_1 = new Page(new PageAction() {
			private void pageP(int pagenum) {
				DailyExpensesDAOImpl expensesMoudle = DAOContentFactriy
						.getDailyExpensesDAO();
				List list = null;
				if (shourustime != null || shouruetime != null
						|| shourustype != null) {
					list = expensesMoudle.getList(Constant.INCOME, shourustype,
							shourustime, shouruetime, pagenum, 20);
				} else {
					list = expensesMoudle.getList(Constant.INCOME, null, null,
							null, pagenum, 20);
				}
				pageData(list, table_1);
			}

			public void pageFirst() {
				pageP(0);
			}

			public void pagePrev(int pagenum) {
				pageP(pagenum);
			}

			public void pageNext(int pagenum) {
				pageP(pagenum);
			}

			public void pageLast(int pagenum) {
				pageP(pagenum);
			}

			public void itemStateChanged(int pagenum) {
				pageP(pagenum);
			}

			public void export(MouseEvent e) {
				try {
					DailyExpensesDAOImpl expensesMoudle = DAOContentFactriy
							.getDailyExpensesDAO();
					List list = null;
					if (shourustime != null || shouruetime != null
							|| shourustype != null) {
						list = expensesMoudle.getList(Constant.INCOME,
								shourustype, shourustime, shouruetime);
					} else {
						list = expensesMoudle.getList(Constant.INCOME, null,
								null, null);
					}
					if (list != null && list.size() > 0) {
						JFileChooser jfc = new JFileChooser("d:/");
						jfc
								.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
						File fileff = new File("日常收入清单.csv");
						jfc.setSelectedFile(fileff);
						int result = jfc.showSaveDialog(table);
						if (result == JFileChooser.CANCEL_OPTION)
							return;
						File savedFile = jfc.getSelectedFile();
						if (savedFile.exists()) {
							int overwriteSelect = JOptionPane
									.showConfirmDialog(table,
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
						stringBuffer.append("收入日期");
						stringBuffer.append(",");
						stringBuffer.append("收入类型");
						stringBuffer.append(",");
						stringBuffer.append("收入金额");
						stringBuffer.append("\r\n");
						output.write(String.valueOf(stringBuffer));
						for (int i = 0; i < list.size(); i++) {
							DailyExpenses dailyExpenses = (DailyExpenses) list
									.get(i);
							StringBuilder sb = new StringBuilder(128);
							sb.append(dailyExpenses.getDate());
							sb.append(",");
							sb.append(dailyExpenses.getType());
							sb.append(",");
							sb.append(dailyExpenses.getPay());
							sb.append("\r\n");
							output.write(String.valueOf(sb));
						}
						output.close();

						JOptionPane.showMessageDialog(null, "文件导出成功");
					} else {
						JOptionPane.showMessageDialog(null, "没数据导出", null,
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		panel_4.add(page_1, BorderLayout.SOUTH);

		JPanel panel_5 = new JPanel();
		panel_5.setPreferredSize(new Dimension(10, 75));
		panel_5.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_4.add(panel_5, BorderLayout.NORTH);
		panel_5.setLayout(null);

		final JLabel label_24 = new JLabel("");
		label_24.setBounds(0, 0, 0, 0);
		label_24.setForeground(Color.RED);
		panel_5.add(label_24);

		JLabel label_20 = new JLabel("开始时间");
		label_20.setBounds(34, 14, 48, 15);
		panel_5.add(label_20);

		datePicker_2 = new JXDatePicker();
		datePicker_2.setBounds(87, 10, 109, 23);
		datePicker_2.getEditor().setColumns(12);
		datePicker_2.getEditor().setEditable(false);

		datePicker_2.setFormats("yyyy-MM-dd");
		datePicker_2.getEditor().setFont(new Font("宋体", Font.BOLD, 12));
		panel_5.add(datePicker_2);

		JLabel label_21 = new JLabel("结束时间");
		label_21.setBounds(226, 14, 48, 15);
		panel_5.add(label_21);

		datePicker_3 = new JXDatePicker();
		datePicker_3.setBounds(279, 10, 109, 23);
		datePicker_3.getEditor().setColumns(12);
		datePicker_3.getEditor().setEditable(false);
		datePicker_3.setFormats("yyyy-MM-dd");
		datePicker_3.getEditor().setFont(new Font("宋体", Font.BOLD, 12));
		panel_5.add(datePicker_3);

		JLabel lblel = new JLabel("收入项目");
		lblel.setBounds(34, 42, 48, 15);
		panel_5.add(lblel);

        JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(87, 39, 109, 21);
		panel_5.add(comboBox_1);

		JButton button_1 = new JButton("查 询");
		button_1.setBounds(316, 38, 72, 23);
		panel_5.add(button_1);

		JLabel label_22 = new JLabel("收入金额总计");
		label_22.setBounds(430, 42, 72, 15);
		panel_5.add(label_22);

		final JLabel label_23 = new JLabel("0");
		label_23.setBounds(512, 42, 109, 15);
		label_23.setFont(new Font("宋体", Font.BOLD, 12));
		panel_5.add(label_23);
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String type = comboBox.getEditor().getItem().toString();
				if ("all".equals(type)) {
					shourustype = null;
					type = null;
				}
				String starttime = datePicker.getEditor().getText();
				String endtime = datePicker_1.getEditor().getText();
				shourustime = starttime;
				shouruetime = endtime;
				shourustype = type;
				submmit(datePicker_2, datePicker_3, Constant.INCOME, table_1,
						page_1, type, label_23);
			}
		});
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_4.add(scrollPane_1, BorderLayout.CENTER);

		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(null, new String[] { "ID",
				"\u6536\u5165\u65E5\u671F", "\u6536\u5165\u9879\u76EE",
				"\u6536\u5165\u91D1\u989D", "\u64CD\u4F5C" }) {
			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_1.getColumnModel().getColumn(0).setResizable(false);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(0);
		table_1.getColumnModel().getColumn(0).setMinWidth(0);
		table_1.getColumnModel().getColumn(0).setMaxWidth(0);
		table_1.getColumnModel().getColumn(1).setResizable(false);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(238);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(248);
		table_1.getColumnModel().getColumn(4).setResizable(false);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(60);
		table_1.getColumnModel().getColumn(4).setMaxWidth(60);
		TableColumn tcs = table_1.getColumn("操作");
		tcs.setPreferredWidth(60);
		tcs.setCellRenderer(renderer);
		table_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				clickedTable(table_1, "删除日常收入数据", Constant.INCOME);
			}
		});
		scrollPane_1.setViewportView(table_1);
		comboBox.setModel(new DefaultComboBoxModel(SelectType
				.getOptionsWithAll(Constant.TYPE_ZC)));
		comboBox_1.setModel(new DefaultComboBoxModel(SelectType
                .getOptionsWithAll(Constant.TYPE_SR)));

	}

	// 客户往来帐
	private void keHuAccounPanel(JPanel panel_2,final String flag,final String name) {

		JPanel panel_8 = new JPanel();
		panel_8.setPreferredSize(new Dimension(10, 70));
		panel_2.add(panel_8, BorderLayout.NORTH);
		panel_8.setLayout(null);
		JLabel label_5;
        if(flag.equals(Constant.CUSTOMER_ACCOUNT)) {
        	label_5 = new JLabel("客户号码");
        }else {
        	label_5 = new JLabel("供应商名称");
        }

		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setBounds(10, 14, 70, 17);
		panel_8.add(label_5);

		textField = new JTextField();

		textField.setBounds(94, 11, 116, 23);
		panel_8.add(textField);
		textField.setColumns(10);

		JLabel label_6 = new JLabel("往来帐分类");

		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setBounds(222, 14, 69, 17);
		panel_8.add(label_6);

		final JComboBox comboBox_2 = new JComboBox();
		Object[] obs = SelectType.getCustomersAccountTypes(name);
		comboBox_2.setModel(new DefaultComboBoxModel(obs));

		comboBox_2.setBounds(303, 11, 130, 23);
		panel_8.add(comboBox_2);

		final JCheckBox checkBox = new JCheckBox("未处理");

		checkBox.setSelected(true);
		checkBox.setBounds(90, 38, 67, 25);
		panel_8.add(checkBox);

		final JCheckBox checkBox_1 = new JCheckBox("已处理");

		checkBox_1.setBounds(155, 38, 67, 25);
		panel_8.add(checkBox_1);

		JLabel label_7 = new JLabel("状态");
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		label_7.setHorizontalTextPosition(SwingConstants.RIGHT);
		label_7.setBounds(22, 42, 58, 17);
		panel_8.add(label_7);

		JButton button_2 = new JButton("统计");

		button_2.setBounds(343, 38, 90, 25);
		panel_8.add(button_2);

		JLabel label = new JLabel("应收");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(455, 14, 58, 17);
		panel_8.add(label);

		JLabel label_1 = new JLabel("预收");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(624, 14, 39, 17);
		panel_8.add(label_1);

		JLabel label_2 = new JLabel("应付");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(455, 42, 58, 17);
		panel_8.add(label_2);

		JLabel label_3 = new JLabel("预付");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setBounds(624, 42, 39, 17);
		panel_8.add(label_3);

		final JLabel yingshouL = new JLabel("0");
		yingshouL.setFont(new Font("宋体", Font.BOLD, 12));
		yingshouL.setHorizontalAlignment(SwingConstants.LEFT);
		yingshouL.setBounds(525, 14, 87, 17);
		panel_8.add(yingshouL);

		final JLabel yingfuL = new JLabel("0");
		yingfuL.setFont(new Font("宋体", Font.BOLD, 12));
		yingfuL.setHorizontalAlignment(SwingConstants.LEFT);
		yingfuL.setBounds(525, 42, 87, 17);
		panel_8.add(yingfuL);

		final JLabel yushouL = new JLabel("0");
		yushouL.setFont(new Font("宋体", Font.BOLD, 12));
		yushouL.setHorizontalAlignment(SwingConstants.LEFT);
		yushouL.setBounds(675, 14, 74, 17);
		panel_8.add(yushouL);

		final JLabel yufuL = new JLabel("0");
		yufuL.setFont(new Font("宋体", Font.BOLD, 12));
		yufuL.setHorizontalAlignment(SwingConstants.LEFT);
		yufuL.setBounds(675, 42, 74, 17);
		panel_8.add(yufuL);

		JScrollPane scrollPane_3 = new JScrollPane();
		panel_2.add(scrollPane_3, BorderLayout.CENTER);

		final JTable table_2 = new JTable();
		table_2.setAutoCreateRowSorter(true);
		table_2.getTableHeader().setReorderingAllowed(false);
		table_2.setRowHeight(25);

		table_2.setModel(new DefaultTableModel(null, new String[] { "Cid",
				"customId", name+"名称",
				"\u8D26\u52A1\u5206\u7C7B", "\u91D1\u989D", "\u72B6\u6001",
				"\u5907\u6CE8","日期", "操作" }) {
			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class, String.class, Double.class, String.class,
					String.class, String.class , String.class};

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_2.getColumnModel().getColumn(0).setResizable(false);
		table_2.getColumnModel().getColumn(0).setPreferredWidth(0);
		table_2.getColumnModel().getColumn(0).setMinWidth(0);
		table_2.getColumnModel().getColumn(0).setMaxWidth(0);
		table_2.getColumnModel().getColumn(1).setResizable(false);
		table_2.getColumnModel().getColumn(1).setPreferredWidth(0);
		table_2.getColumnModel().getColumn(1).setMinWidth(0);
		table_2.getColumnModel().getColumn(1).setMaxWidth(0);

		table_2.getColumnModel().getColumn(2).setPreferredWidth(135);

		table_2.getColumnModel().getColumn(3).setPreferredWidth(119);

		table_2.getColumnModel().getColumn(4).setPreferredWidth(88);

		table_2.getColumnModel().getColumn(5).setPreferredWidth(40);

		table_2.getColumnModel().getColumn(6).setPreferredWidth(241);
		table_2.getColumnModel().getColumn(7).setPreferredWidth(60);
		//table_2.getColumnModel().getColumn(8).setPreferredWidth(40);
		GuiUtils.columnCenter(table_2,"操作",40);
		scrollPane_3.setViewportView(table_2);

		table_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				int selrow = table_2.getSelectedRow();

				if (selrow > -1 && e.getButton() == 1) {
					String id = String.valueOf(table_2.getValueAt(selrow, 0));
					String no = String.valueOf(table_2.getValueAt(selrow, 1));
					String name = String.valueOf(table_2.getValueAt(selrow, 2));
					AccountsDaoImpl accountsDao = DAOContentFactriy
							.getAccountsDao();

					int nCol = table_2.getSelectedColumn();
					Object objSel = table_2.getValueAt(selrow, nCol);
					if ("删除".equals(String.valueOf(objSel).trim())) {
						int response = JOptionPane.showConfirmDialog(null,
								"确定删除此"+name+"往来帐?", "删除"+name+"往来帐数据",
								JOptionPane.YES_NO_OPTION);
						switch (response) {
						case JOptionPane.YES_OPTION:
							DefaultTableModel tableModel = (DefaultTableModel) table_2
									.getModel();
							accountsDao.deleteById(id);
							int nselrow = selrow - 1 > 0 ? selrow - 1 : 0;
							tableModel.removeRow(selrow);

							table_2
									.setColumnSelectionInterval(nselrow,
											nselrow);
						case JOptionPane.NO_OPTION:

						}
					} else {
						Accounts accounts = accountsDao.getById(id);
						AccountDialog customDialog = new AccountDialog(
								MainWindows.getInstance(), "修改"+name+"往来帐", accounts,
								flag, no, name, table_2);
						customDialog.setVisible(true);
					}
				}

			}
		});
		final Page page_xx = new Page(new PageAction() {
			public void pageP(int pagenum) {
				AccountsDaoImpl AccountsDao = DAOContentFactriy.getAccountsDao();
				String cutomid = textField.getText();
				Custom customs = DAOContentFactriy.getCustomDAO()
						.getCustomById(cutomid);
				String targetId = null;
				if (customs != null) {
					targetId = customs.getId();
				}
				String staus = null;
				if (checkBox.isSelected() && checkBox_1.isSelected()) {
					staus = null;
				} else if (!checkBox.isSelected() && !checkBox_1.isSelected()) {
					staus = null;
				} else if (checkBox.isSelected()) {
					staus = Constant.ACCOUNT_STATUS_UN;
				} else {
					staus = Constant.ACCOUNT_STATUS_PR;
				}
				Object ov = comboBox_2.getSelectedItem();
				String type = null;
				if (ov != null) {
					Options options = (Options) ov;
					type = options.getKey();
				}

				List list = AccountsDao.getAccountsList(
						flag, targetId, staus, type,
						pagenum, 20);
				accountPageData(list, table_2, name,flag);
			}
			public void export(MouseEvent e) {
				try {
					AccountsDaoImpl AccountsDao = DAOContentFactriy.getAccountsDao();
					List list = null;
					String cutomid = textField.getText();
					Custom customs = DAOContentFactriy.getCustomDAO()
							.getCustomById(cutomid);
					String targetId = null;
					if (customs != null) {
						targetId = customs.getId();
					}
					String staus = null;
					if (checkBox.isSelected() && checkBox_1.isSelected()) {
						staus = null;
					} else if (!checkBox.isSelected() && !checkBox_1.isSelected()) {
						staus = null;
					} else if (checkBox.isSelected()) {
						staus = Constant.ACCOUNT_STATUS_UN;
					} else {
						staus = Constant.ACCOUNT_STATUS_PR;
					}
					Object ov = comboBox_2.getSelectedItem();
					String type = null;
					if (ov != null) {
						Options options = (Options) ov;
						type = options.getKey();
					}
					list = AccountsDao.getAccountsList(flag, targetId, staus, type,0,-1);
					if (list != null && list.size() > 0) {
						JFileChooser jfc = new JFileChooser("d:/");
						jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
						File fileff = new File(name+"往来帐清单.csv");
						jfc.setSelectedFile(fileff);
						int result = jfc.showSaveDialog(table);
						if (result == JFileChooser.CANCEL_OPTION)
							return;
						File savedFile = jfc.getSelectedFile();
						if (savedFile.exists()) {
							int overwriteSelect = JOptionPane
									.showConfirmDialog(table,
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
                        stringBuffer.append(name).append("名称");
						stringBuffer.append(",");
						stringBuffer.append("账务分类");
						stringBuffer.append(",");
						stringBuffer.append("金额");
						stringBuffer.append(",");
						stringBuffer.append("状态");
						stringBuffer.append(",");
						stringBuffer.append("备注");
						stringBuffer.append("\r\n");
						output.write(String.valueOf(stringBuffer));
						for (int i = 0; i < list.size(); i++) {
							Accounts accounts = (Accounts) list
									.get(i);

							CustomDAOImpl customMoudle = DAOContentFactriy.getCustomDAO();
							Custom custom = customMoudle.getCustomById(accounts
									.getTargetId());
							String categor = "";
							Object[] obs = SelectType.getCustomersAccountTypes(name);
							for (int m = 0; m < obs.length; m++) {
								Options options = (Options) obs[m];
								if (options != null) {
									
									if (options.getKey().equals(accounts.getCategories())) {
										categor = options.getText();
										break;
									}
								}

							}
							StringBuilder sb = new StringBuilder(128);
							sb.append(custom.getName());
							sb.append(",");
							sb.append(categor);
							sb.append(",");
							sb.append(accounts.getAmount());
							sb.append(",");
							sb.append(accounts.getStatus().equals("Untreated")?"未处理":"已处理");
							sb.append(",");
							sb.append(accounts.getNote());
							sb.append("\r\n");
							output.write(String.valueOf(sb));
						}
						output.close();

						JOptionPane.showMessageDialog(MainWindows.getInstance(), "文件导出成功");
					} else {
						JOptionPane.showMessageDialog(MainWindows.getInstance(), "没数据导出", "数据导出通知",
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			public void itemStateChanged(int pagenum) {
				pageP(pagenum);
			}

			public void pageFirst() {
				pageP(0);
			}

			public void pageLast(int pagenum) {
				pageP(pagenum);

			}

			public void pageNext(int pagenum) {
				pageP(pagenum);
			}

			public void pagePrev(int pagenum) {
				pageP(pagenum);

			}

		});
		panel_2.add(page_xx, BorderLayout.SOUTH);
		button_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				AccountsDaoImpl AccountsDao = DAOContentFactriy.getAccountsDao();
				String cutomid = textField.getText();
				Custom customs = DAOContentFactriy.getCustomDAO()
						.getCustomById(cutomid);
				String targetId = null;
				if (customs != null) {
					targetId = customs.getId();
				}
				String staus = null;
				if (checkBox.isSelected() && checkBox_1.isSelected()) {
					staus = null;
				} else if (!checkBox.isSelected() && !checkBox_1.isSelected()) {
					staus = null;
				} else if (checkBox.isSelected()) {
					staus = Constant.ACCOUNT_STATUS_UN;
				} else {
					staus = Constant.ACCOUNT_STATUS_PR;
				}
				Object ov = comboBox_2.getSelectedItem();
				String type = null;
				if (ov != null) {
					Options options = (Options) ov;
					type = options.getKey();
				}

				int size = AccountsDao.getAccountsSize(
						flag, targetId, staus, type);
				List list = AccountsDao
						.getAccountsList(flag, targetId,
								staus, type, 0, 20);

				page_xx.setPageInfo(size);
				accountPageData(list, table_2, name,flag);
				double yingshou = AccountsDao.sumAccounts(
						flag, targetId, staus,
						Constant.CUSTOMER_ACCOUNT_YINGS);
				double yingfu = AccountsDao.sumAccounts(
						flag, targetId, staus,
						Constant.CUSTOMER_ACCOUNT_YINGF);
				double yushou = AccountsDao.sumAccounts(
						flag, targetId, staus,
						Constant.CUSTOMER_ACCOUNT_YUS);
				double yufu = AccountsDao.sumAccounts(
						flag, targetId, staus,
						Constant.CUSTOMER_ACCOUNT_YUF);
				yingshouL.setText(String.valueOf(yingshou));
				yingfuL.setText(String.valueOf(yingfu));
				yushouL.setText(String.valueOf(yushou));
				yufuL.setText(String.valueOf(yufu));

			}
		});
	}


	public void submmit(JXDatePicker startObj, JXDatePicker endObj,
			String mode, JTable jTableObj, Page page, String type,
			JLabel totalLabel) {
		// MoudleContentFactry.getDailyExpensesMoudle().getListSize()
		String starttime = startObj.getEditor().getText();
		String endtime = endObj.getEditor().getText();
		if (starttime.length() == 0 && endtime.length() > 0) {
			// msg.setText("请选择开始时间");
			GuiUtils.toolTips(startObj.getEditor(), "请选择开始时间");
			return;
		}
		if (endtime.length() == 0 && starttime.length() > 0) {
			GuiUtils.toolTips(endObj.getEditor(), "请选择结束时间");
			return;
		}
		if (starttime.length() > 0 && endtime.length() > 0) {
			long s = Long.parseLong(starttime.replaceAll("-", ""));
			long e = Long.parseLong(endtime.replaceAll("-", ""));
			if (s > e) {
				GuiUtils.toolTips(startObj.getEditor(), "输入时间错误！开始时间应该小于结束时间");

				return;
			}
		}

		DailyExpensesDAOImpl expensesMoudle = DAOContentFactriy
				.getDailyExpensesDAO();
		BigDecimal bigDecimal = expensesMoudle.sumDailyExpensesPay(mode, type,
				starttime, endtime);
		if (bigDecimal != null) {
			totalLabel.setText(bigDecimal.toString());
		}
		int total = expensesMoudle.getListSize(mode, type, starttime, endtime);
		page.setPageInfo(total);
		List list = expensesMoudle.getList(mode, type, starttime, endtime, 0,
				20);
		pageData(list, jTableObj);
	}

	private void pageData(List list, JTable jTableObj) {
		if (list != null && list.size() > 0) {
            GuiUtils.clear(jTableObj);
			DefaultTableModel tableModel = (DefaultTableModel) jTableObj
					.getModel();
			for (int i = 0; i < list.size(); i++) {
				DailyExpenses dailyExpenses = (DailyExpenses) list.get(i);
				Object[] rowData = new Object[] { dailyExpenses.getId(),
						dailyExpenses.getDate(), dailyExpenses.getType(),
						dailyExpenses.getPay(), "删除" };
				tableModel.insertRow(i, rowData);
			}
		}
	}

	private void accountPageData(List list, JTable table, String name,String flag) {
        GuiUtils.clear(table);
		if (list != null && list.size() > 0) {
			DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			for (int i = 0; i < list.size(); i++) {
				Accounts accounts = (Accounts) list.get(i);
                String targetName;
                if(Constant.CUSTOMER_ACCOUNT.equals(flag)){
                	CustomDAOImpl customMoudle = DAOContentFactriy.getCustomDAO();
    				Custom custom = customMoudle.getCustomById(accounts
    						.getTargetId());
    				targetName = custom.getName();
                }else {
                	SuppliersDAOImpl suppliersDAO = DAOContentFactriy.getSuppliersDAO();
                	Suppliers suppliers = suppliersDAO.getSuppliersByNo(accounts
    						.getTargetId());
                	targetName = suppliers.getSuppliersName();
                }
				
				String categor = "";
				Object[] obs = SelectType.getCustomersAccountTypes(name);
				for (int m = 0; m < obs.length; m++) {
					Options options = (Options) obs[m];
					if (options != null) {
					
						if (options.getKey().equals(accounts.getCategories())) {
							categor = options.getText();
							break;
						}
					}

				}
				Object[] rowData = new Object[] {
						accounts.getId(),
						accounts.getTargetId(),
						targetName,
						categor,
						accounts.getAmount(),
						accounts.getStatus().equals("Untreated") ? "未处理"
								: "已处理", accounts.getNote(), accounts.getDate(),"删除" };
				tableModel.insertRow(0, rowData);
			}

		}

	}

	private void clickedTable(JTable jTableObj, String title, String mode) {
		int nCol = jTableObj.getSelectedColumn();
		int nRow = jTableObj.getSelectedRow();
		Object objSel = jTableObj.getValueAt(nRow, nCol);
		if (objSel != null && objSel instanceof String) {
			if ("删除".equals(String.valueOf(objSel))) {
				int response = JOptionPane.showConfirmDialog(null, "确定删除此条数据?",
						title, JOptionPane.YES_NO_OPTION);
				switch (response) {
				case JOptionPane.YES_OPTION:
					DailyExpensesDAOImpl expensesMoudle = DAOContentFactriy
							.getDailyExpensesDAO();
					DefaultTableModel tableModel = (DefaultTableModel) jTableObj
							.getModel();
					String id = String.valueOf(tableModel.getValueAt(nRow, 0));
					expensesMoudle.deleteById(id);
					tableModel.removeRow(nRow);

					BigDecimal bigDecimal = expensesMoudle.sumDailyExpensesPay(
							mode, stype, stime, etime);
					if (bigDecimal != null) {
						label_4.setText(bigDecimal.toString());
					}
				case JOptionPane.NO_OPTION:
				case JOptionPane.CLOSED_OPTION:

				}
			}
		}
	}

}
