package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.action.PageAction;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.StockDAOImpl;
import com.fafasoft.flow.pojo.StockTj;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.widget.Page;
import com.fafasoft.flow.ui.widget.SelectType;
import com.fafasoft.flow.ui.widget.table.ColumnGroup;
import com.fafasoft.flow.ui.widget.table.GroupableTableHeader;
import org.h2.engine.Constants;
import org.jdesktop.swingx.JXDatePicker;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.HashMap;
import java.util.List;

public class KuCTjPanel extends BaseJPanel implements LazyPageContent {
	private JTable tableExt;
	private JTextField huohaotextField;
	private Page kucuntjPage;
	private String[] tianj;
	private JComboBox comboBox_StockKCType;

	public KuCTjPanel() {
		setLayout(new BorderLayout(0, 0));
	}
	public void initPanel() {
		tianj = null;
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		Border loweredetched = BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED);
		tabbedPane.setBorder(loweredetched);
		add(tabbedPane, BorderLayout.CENTER);
		JPanel panel_6 = new JPanel();
		ImageIcon imageIcon1 = new ImageIcon(
				KuCTjPanel.class
						.getResource("/com/fafasoft/flow/resource/image/office-chart-pie.png"));
		tabbedPane.addTab("库存统计", imageIcon1, panel_6, null);
		panel_6.setLayout(new BorderLayout(0, 0));

		JPanel panel_7 = new JPanel();
		panel_7.setPreferredSize(new Dimension(10, 50));
		panel_6.add(panel_7, BorderLayout.NORTH);
		panel_7.setLayout(null);

		JLabel label_16 = new JLabel("货物类型");
		label_16.setHorizontalAlignment(SwingConstants.RIGHT);
		label_16.setBounds(358, 15, 48, 17);
		panel_7.add(label_16);

		JTree tree = getJTree();
		comboBox_StockKCType = new JComboBox();//
		comboBox_StockKCType.setModel(new DefaultComboBoxModel(SelectType
				.getStockTypeList()));
		comboBox_StockKCType.setBounds(414, 11, 124, 23);
		panel_7.add(comboBox_StockKCType);

		JLabel label_17 = new JLabel("开始时间");
		label_17.setHorizontalAlignment(SwingConstants.RIGHT);
		label_17.setBounds(10, 16, 54, 17);
		panel_7.add(label_17);

		final JXDatePicker datePicker_2 = new JXDatePicker();
		datePicker_2.getEditor().setEditable(false);
		datePicker_2.setFormats("yyyy-MM-dd");
		datePicker_2.getEditor().setFont(new Font("宋体", Font.BOLD, 12));
		datePicker_2.setBounds(69, 13, 109, 23);
		panel_7.add(datePicker_2);

		JLabel label_18 = new JLabel("结束时间");
		label_18.setHorizontalAlignment(SwingConstants.RIGHT);
		label_18.setBounds(185, 15, 48, 17);
		panel_7.add(label_18);

		final JXDatePicker datePicker_3 = new JXDatePicker();
		datePicker_3.getEditor().setEditable(false);
		datePicker_3.setFormats("yyyy-MM-dd");
		datePicker_3.setFont(new Font("宋体", Font.BOLD, 12));
		datePicker_3.setBounds(239, 11, 109, 23);
		panel_7.add(datePicker_3);

		JButton button_2 = new JButton("统  计");

		button_2.setBounds(694, 11, 75, 25);
		panel_7.add(button_2);

		JLabel label_1 = new JLabel("货号");

		label_1.setBounds(548, 16, 33, 15);
		panel_7.add(label_1);

		huohaotextField = new JTextField();
		huohaotextField.setBounds(577, 13, 101, 21);
		panel_7.add(huohaotextField);
		huohaotextField.setColumns(10);

		JPanel panel_8 = new JPanel();

		panel_6.add(panel_8, BorderLayout.CENTER);

		DefaultTableModel dm = new DefaultTableModel() {
			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class, String.class, Double.class, Double.class,
					Double.class, Double.class, Double.class, Double.class,
					Double.class, Double.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false, false, false, false, false, false, false,
					false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		dm.setDataVector(null, new Object[] { "类型", "货号", "颜色", "规格", "数量",
				"金额", "数量", "金额", "数量", "金额", "数量", "金额" });

		tableExt = new JTable(dm) {
			protected JTableHeader createDefaultTableHeader() {
				return new GroupableTableHeader(columnModel);
			}
		};

		tableExt.getTableHeader().setReorderingAllowed(false);
		tableExt.setRowHeight(25);
		TableColumnModel cm = tableExt.getColumnModel();
		ColumnGroup g_name = new ColumnGroup("进货总计");
		g_name.add(cm.getColumn(4));
		g_name.add(cm.getColumn(5));
		ColumnGroup g_gukth = new ColumnGroup("顾客退货总计");
		g_gukth.add(cm.getColumn(6));
		g_gukth.add(cm.getColumn(7));
		ColumnGroup g_cgkth = new ColumnGroup("采购退货总计");
		g_cgkth.add(cm.getColumn(8));
		g_cgkth.add(cm.getColumn(9));
		ColumnGroup g_other = new ColumnGroup("剩余总计");
		g_other.add(cm.getColumn(10));
		g_other.add(cm.getColumn(11));

		GroupableTableHeader header = (GroupableTableHeader) tableExt
				.getTableHeader();
		header.addColumnGroup(g_name);
		header.addColumnGroup(g_gukth);
		header.addColumnGroup(g_cgkth);

		header.addColumnGroup(g_other);
		panel_8.setLayout(new BorderLayout(0, 0));
		JScrollPane scroll = new JScrollPane(tableExt);
		panel_8.add(scroll);
		kucuntjPage = getPage();
		panel_6.add(kucuntjPage, BorderLayout.SOUTH);

		KunAlermPanel panel_1 = new KunAlermPanel();
		ImageIcon alertimageIcon = new ImageIcon(KuCTjPanel.class
				.getResource("/com/fafasoft/flow/resource/image/alert.png"));
		tabbedPane.addTab("库存报警", alertimageIcon, panel_1, null);
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						String startx = datePicker_2.getEditor().getText();
						String endx = datePicker_3.getEditor().getText();

						String typpe = (String) comboBox_StockKCType
								.getSelectedItem();
						StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
						int totalrows = stockMoudle.getStockTjListSize(typpe,
								startx, endx, huohaotextField.getText());

						tianj = new String[] { typpe, startx, endx,
								huohaotextField.getText() };
						Object[] totals = stockMoudle.sumStocksByType(typpe,
								endx, endx, huohaotextField.getText());
						Object[] rowData = new Object[] { "总计", "", "", "",
								totals[0], totals[1], totals[2], totals[3],
								totals[4], totals[5], totals[6], totals[7] };
						List pageList = stockMoudle.getStockTjList(typpe,
								startx, endx, huohaotextField.getText(), 0, 20);
						kucuntjPage.setPageInfo(totalrows + 1);

						pageData(pageList, rowData);
					}
				});
			}
		});
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initdata();
			}
		});

	}

	private void initdata() {

		StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
		int totalrows = stockMoudle.getStockTjListSize(null, null, null, null);
		tianj = new String[] { null, null, null, null };
		Object[] totals = stockMoudle.sumStocksByType(null, null, null, null);
		Object[] rowData = new Object[] { "总计", "", "", "", totals[0],
				totals[1], totals[2], totals[3], totals[4], totals[5],
				totals[6], totals[7] };
		List pageList = stockMoudle.getStockTjList(null, null, null, null, 0,
				20);
		kucuntjPage.setPageInfo(totalrows + 1);

		pageData(pageList, rowData);
	}

	public void clearTj() {
		DefaultTableModel tableModel = (DefaultTableModel) tableExt.getModel();
		if (tableModel.getRowCount() > 0) {
			tableModel.setRowCount(0);
		}
	}

	private void pageData(List pageList, Object[] totals) {

		clearTj();
		DefaultTableModel tableModel = (DefaultTableModel) tableExt.getModel();
		HashMap temp = new HashMap();
		for (int i = 0; i < pageList.size(); i++) {
			StockTj stockTj = (StockTj) pageList.get(i);
			String type = stockTj.getStocktype();
			if (!temp.containsKey(type)) {
				temp.put(stockTj.getStocktype(), stockTj.getStocktype());
			} else {
				type = "";
			}

			Object[] rowData = new Object[] { type, stockTj.getCatno(),
					stockTj.getColor(), stockTj.getSpecif(),
					stockTj.getAmount(), stockTj.getCostprice(),
					stockTj.getGkThamount(), stockTj.getGkThcostprice(),
					stockTj.getCgThamount(), stockTj.getCgThcostprice(),
					stockTj.getSyamount(), stockTj.getSycostprice() };
			tableModel.addRow(rowData);
		}

		tableModel.insertRow(0, totals);
	}

	private Page getPage() {

		Page page = new Page(new PageAction() {

			public void pageFirst() {
				pageView(0);
			}

			public void pageLast(int pagenum) {
				pageView(pagenum);
			}

			public void pageNext(int pagenum) {
				pageView(pagenum);
			}

			public void pagePrev(int pagenum) {
				pageView(pagenum);
			}

			public void itemStateChanged(int pagenum) {
				pageView(pagenum);
			}

			private void pageView(int pagenum) {
				StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
				Object[] totals = stockMoudle.sumStocksByType(tianj[0],
						tianj[1], tianj[2], tianj[3]);
				Object[] rowData = new Object[] { "总计", "", "", "", totals[0],
						totals[1], totals[2], totals[3], totals[4], totals[5],
						totals[6], totals[7] };
				List pageList = stockMoudle.getStockTjList(tianj[0], tianj[1],
						tianj[2], tianj[3], pagenum, 20);

				pageData(pageList, rowData);
			}

			public void export(MouseEvent e) {

				StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
				List pageList = stockMoudle.getStockTjList(null, null, null,
						null, -1, -1);
				if (pageList != null && pageList.size() > 0) {
					JFileChooser jfc = new JFileChooser("d:/");
					jfc
							.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					File fileff = fileff = new File("库存统计清单.csv");

					jfc.setSelectedFile(fileff);
					int result = jfc.showSaveDialog(tableExt);
					if (result == JFileChooser.CANCEL_OPTION)
						return;
					File savedFile = jfc.getSelectedFile();
					if (savedFile.exists()) {
						int overwriteSelect = JOptionPane.showConfirmDialog(
								tableExt, "<html><font size=3>文件"
										+ savedFile.getName()
										+ "已存在，是否覆盖?</font><html>", "是否覆盖?",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE);
						if (overwriteSelect != JOptionPane.YES_OPTION) {
							return;
						}
					}
					try {
						OutputStream out = new FileOutputStream(savedFile);
						out = new BufferedOutputStream(out,
								Constants.IO_BUFFER_SIZE);
						BufferedWriter output = new BufferedWriter(
								new OutputStreamWriter(out, "gbk"));

						StringBuffer stringBuffer = new StringBuffer();
						stringBuffer.append("类型").append(",").append("货号")
								.append(",").append("颜色").append(",").append(
										"规格").append(",").append(",").append("进货总计")
								.append(",").append(",").append("顾客退货总计").append(",").append(",");
						stringBuffer.append("采购退货总计").append(",").append(",")
								.append("剩余统计").append("").append("\r\n");
						stringBuffer.append(",").append(",").append(",")
								.append(",").append("数量").append(",").append(
										"金额").append(",").append("数量").append(",").append(
										"金额").append(",").append("数量").append(",").append(
										"金额").append(",").append("数量").append(",").append(
										"金额");
						stringBuffer.append("").append("\r\n");

						output.write(String.valueOf(stringBuffer));

						for (int i = 0; i < pageList.size(); i++) {
							StockTj stock = (StockTj) pageList.get(i);
							StringBuilder sb = new StringBuilder(128);
							sb.append(stock.getStocktype());
							sb.append(",");
							sb.append(stock.getCatno());
							sb.append(",");
							sb.append(stock.getColor());
							sb.append(",");
							sb.append(stock.getSpecif());
							sb.append(",");
							sb.append(stock.getAmount());
							sb.append(",");
							sb.append(stock.getCostprice());
							sb.append(",");
							sb.append(stock.getGkThamount());
							sb.append(",");
							sb.append(stock.getGkThcostprice());
							sb.append(",");
							sb.append(stock.getCgThamount());
							sb.append(",");
							sb.append(stock.getCgThcostprice());
							sb.append(",");
							sb.append(stock.getSyamount());
							sb.append(",");
							sb.append(stock.getSycostprice());
							sb.append("\r\n");
							output.write(String.valueOf(sb));
						}
						output.close();
						out.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					JOptionPane.showMessageDialog(MainWindows.getInstance(),
							"文件导出成功");
				} else {
					JOptionPane.showMessageDialog(MainWindows.getInstance(),
							"没数据导出", "数据导出通知", JOptionPane.WARNING_MESSAGE);
				}
			}

		});
		return page;
	}
}
