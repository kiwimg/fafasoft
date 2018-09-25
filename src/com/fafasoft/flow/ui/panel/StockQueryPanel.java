package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.action.PageAction;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.StockDAOImpl;
import com.fafasoft.flow.pojo.Option;
import com.fafasoft.flow.pojo.Stock;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.progressbar.GyrJProgressBar;
import com.fafasoft.flow.ui.widget.*;
import com.fafasoft.flow.ui.widget.table.ImageRenderer;
import com.fafasoft.flow.util.GuiUtils;
import org.h2.engine.Constants;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.math.BigDecimal;
import java.util.List;

public class StockQueryPanel extends BaseJPanel implements LazyPageContent {

	private JTable table;
	private Page page;
	private JTextField textField_1;
	private JTreeComboBox jTreeComboBoxtype;
	private JXDatePicker datePicker;
	private SuggestTextField suggestTextField;
	private String catno = null;
	private String cost = null;
	private String type = null;
	private String date = null;
	private String dateTo = null;
	private JXDatePicker datePicker_1;
	private JLabel lblDdd;

	private JComboBox comboBox_StockKCType;
	private String kucunType = null;

	public StockQueryPanel() {
		setLayout(new BorderLayout(0, 0));
	}


	public void initPanel() {
		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(10, 130));
		panel_2.setMinimumSize(new Dimension(10, 50));
		add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(null);

		JLabel label_1 = new JLabel("货号");
		label_1.setBounds(33, 13, 24, 17);
		panel_2.add(label_1);

		suggestTextField = new SuggestTextField(new StockSuggestDataImpl(),
				null, null, null);
		suggestTextField.setBounds(62, 10, 127, 23);
		panel_2.add(suggestTextField);

		JLabel label_2 = new JLabel("成本价格");
		label_2.setBounds(234, 11, 48, 17);
		panel_2.add(label_2);

		textField_1 = new JTextField();
		textField_1.setBounds(287, 9, 127, 21);
		textField_1.setFont(new Font("宋体", Font.BOLD, 12));
		panel_2.add(textField_1);

		JLabel label_5 = new JLabel("货物类型");
		label_5.setBounds(10, 44, 48, 15);

		panel_2.add(label_5);

		jTreeComboBoxtype = new JTreeComboBox(getJTree(), false);
		jTreeComboBoxtype.setBounds(63, 40, 127, 23);
		panel_2.add(jTreeComboBoxtype);

		JLabel label_15 = new JLabel("库存类别");
		label_15.setBounds(234, 41, 48, 17);
		panel_2.add(label_15);

		comboBox_StockKCType = new JComboBox();
		comboBox_StockKCType.setBounds(287, 38, 127, 23);
		comboBox_StockKCType.setModel(new DefaultComboBoxModel(SelectType
				.getStockTypes()));
		panel_2.add(comboBox_StockKCType);

		JLabel label_8 = new JLabel("开始时间");
		label_8.setBounds(10, 73, 48, 15);

		panel_2.add(label_8);

		datePicker = new JXDatePicker();
		datePicker.getEditor().setEditable(false);
		datePicker.setBounds(63, 69, 128, 23);
		datePicker.getEditor().setFont(new Font("宋体", Font.BOLD, 12));
		datePicker.getEditor().setColumns(10);
		datePicker.setFormats("yyyy-MM-dd");
		panel_2.add(datePicker);

		JLabel label_9 = new JLabel("结束时间");
		label_9.setBounds(233, 71, 48, 17);
		panel_2.add(label_9);

		datePicker_1 = new JXDatePicker();
		datePicker_1.getEditor().setEditable(false);
		datePicker_1.setBounds(287, 69, 127, 23);
		datePicker_1.getEditor().setFont(new Font("宋体", Font.BOLD, 12));
		datePicker_1.getEditor().setColumns(10);
		datePicker_1.setFormats("yyyy-MM-dd");
		panel_2.add(datePicker_1);

		JButton button = new JButton("查 询");
		button.setBounds(338, 98, 78, 25);

		button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
				catno = suggestTextField.getText().trim().equals("") ? null
						: suggestTextField.getText().trim();
				cost = textField_1.getText().trim().equals("") ? null
						: textField_1.getText().trim();
				Option options = (Option) jTreeComboBoxtype
						.getSelectedItemValue();
				Options kucunTypes = (Options) comboBox_StockKCType
						.getSelectedItem();
				kucunType = kucunTypes.getText();
				tableViewChange(kucunTypes.getText());
				if (options != null) {
					String key = options.getText();
					String text = options.getText();
					if ("all".equals(key)) {
						type = null;
					} else {
						type = text;
					}
				} else {
					type = null;
				}
				tableViewChange(kucunTypes.getKey());
				date = datePicker.getEditor().getText().trim().equals("") ? null
						: datePicker.getEditor().getText();
				dateTo = datePicker_1.getEditor().getText().trim().equals("") ? null
						: datePicker_1.getEditor().getText();
				int total = stockMoudle.getStockByParmSize(catno, cost, type,
						kucunTypes.getKey(), date, dateTo);
				List list = stockMoudle.getStockByParm(catno, cost, type,
						kucunTypes.getKey(), date, dateTo, 0, 20);
				lblDdd.setText("");
				if(total==0) {
					lblDdd.setText("库存中没有货物数据!");
				}
				page.setPageInfo(total);
				pageData(list);

			}
		});
		panel_2.add(button);

		lblDdd = new JLabel("");
		lblDdd.setForeground(Color.RED);

		lblDdd.setBounds(57, 98, 254, 24);
		panel_2.add(lblDdd);

		JPanel panel_3 = new JPanel();
		add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new TitledBorder(null, "\u5E93\u5B58\u5217\u8868  [\u9F20\u6807\u53CC\u51FB\u9009\u4E2D\u8FDB\u884C\u4FEE\u6539]", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setAutoCreateRowSorter(true);
		table.setRowHeight(30);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				clickedTable(arg0.getClickCount());
			}
		});

		table.setModel(new DefaultTableModel(null, new String[] { "id",
				"\u8D27\u53F7", "\u5269\u4F59\u6570\u91CF",
				"\u8FDB\u8D27\u6570\u91CF", "\u7C7B\u578B", "\u552E\u4EF7",
				"进货价", "\u540D\u79F0", "\u89C4\u683C", "\u989C\u8272",
				"\u5165\u5E93\u65F6\u95F4", "退货原因","库存状态", "\u64CD\u4F5C" }) {
			Class[] columnTypes = new Class[] { String.class, String.class,
					Long.class, Long.class, Object.class, BigDecimal.class,
					BigDecimal.class, String.class, String.class, String.class,
					String.class, String.class,Double.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false, false, false, false, false, false, false,
					false, false ,false};

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
	
		tableViewChange(Constant.STCOK_TYPE_JINHUO);
	
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
		
		page = new Page(new PageAction() {
			public void pageFirst() {
				StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
				List list;
				if (catno != null || cost != null || type != null
						|| date != null || dateTo != null) {
					Options kucunType = (Options) comboBox_StockKCType
							.getSelectedItem();
					list = stockMoudle.getStockByParm(catno, cost, type,
							kucunType.getKey(), date, dateTo, 0, 20);
				} else {
					list = stockMoudle.getStock(0, 20);
				}
				pageData(list);
			}

			public void pagePrev(int pagenum) {
				StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
				List list;
				if (catno != null || cost != null || type != null
						|| date != null || dateTo != null) {
					Options kucunType = (Options) comboBox_StockKCType
							.getSelectedItem();
					list = stockMoudle.getStockByParm(catno, cost, type,
							kucunType.getKey(), date, dateTo, pagenum, 20);
				} else {
					list = stockMoudle.getStock(pagenum, 20);
				}
				pageData(list);
			}

			public void pageNext(int pagenum) {
				StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();

				List list;
				if (catno != null || cost != null || type != null
						|| date != null || dateTo != null) {
					Options kucunType = (Options) comboBox_StockKCType
							.getSelectedItem();
					list = stockMoudle.getStockByParm(catno, cost, type,
							kucunType.getKey(), date, dateTo, pagenum, 20);
				} else {
					list = stockMoudle.getStock(pagenum, 20);
				}
				pageData(list);
			}

			public void pageLast(int pagenum) {
				StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
				List list;
				if (catno != null || cost != null || type != null
						|| date != null) {
					Options kucunType = (Options) comboBox_StockKCType
							.getSelectedItem();
					list = stockMoudle.getStockByParm(catno, cost, type,
							kucunType.getKey(), date, dateTo, pagenum, 20);
				} else {
					list = stockMoudle.getStock(pagenum, 20);
				}
				pageData(list);
			}

			public void itemStateChanged(int pagenum) {
				StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
				List list;
				if (catno != null || cost != null || type != null
						|| date != null) {
					Options kucunType = (Options) comboBox_StockKCType
							.getSelectedItem();
					list = stockMoudle.getStockByParm(catno, cost, type,
							kucunType.getKey(), date, dateTo, pagenum, 20);
				} else {
					list = stockMoudle.getStock(pagenum, 20);
				}

				pageData(list);
			}

			public void export(MouseEvent e) {
				try {
					Options kucunTypes = (Options) comboBox_StockKCType
							.getSelectedItem();
					kucunType = kucunTypes.getKey();
					int rows = table.getRowCount();
					if(rows >0) {
						
						exportKC(kucunType);	
					}else{
						JOptionPane.showMessageDialog(table.getParent(), "没数据导出", "数据导出通知",
								JOptionPane.WARNING_MESSAGE);
					}
					

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		});
		panel_3.add(page, BorderLayout.SOUTH);
		
	}

	private void exportKC(String type) throws IOException {

		StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
		List list = stockMoudle.getStock(type);
		if (list != null && list.size() > 0) {
			JFileChooser jfc = new JFileChooser("d:/");
			jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			File fileff = null;
			if (Constant.STCOK_TYPE_JINHUO.equals(type)) {
				fileff = new File("库存清单.csv");
			} else if (Constant.STCOK_TYPE_CAIGOUTH.equals(type)) {
				fileff = new File("采购退货库存清单.csv");
			} else if (Constant.STCOK_TYPE_GUKETH.equals(type)) {
				fileff = new File("顾客退货库存清单.csv");
			}

			jfc.setSelectedFile(fileff);
			int result = jfc.showSaveDialog(table);
			if (result == JFileChooser.CANCEL_OPTION)
				return;
			File savedFile = jfc.getSelectedFile();
			if (savedFile.exists()) {
				int overwriteSelect = JOptionPane.showConfirmDialog(table,
						"<html><font size=3>文件" + savedFile.getName()
								+ "已存在，是否覆盖?</font><html>", "是否覆盖?",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (overwriteSelect != JOptionPane.YES_OPTION) {
					return;
				}
			}
			OutputStream out = new FileOutputStream(savedFile);
			out = new BufferedOutputStream(out, Constants.IO_BUFFER_SIZE);
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(
					out, "gbk"));

			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("货号");
			stringBuffer.append(",");
			stringBuffer.append("售价");

			if (Constant.STCOK_TYPE_JINHUO.equals(type)) {
				stringBuffer.append(",");
				stringBuffer.append("剩余数量");
				stringBuffer.append(",");
				stringBuffer.append("进货数量");
				stringBuffer.append(",");
			} else if (Constant.STCOK_TYPE_CAIGOUTH.equals(type)) {
				stringBuffer.append(",");
				stringBuffer.append("退货数量");
				stringBuffer.append(",");
			} else if (Constant.STCOK_TYPE_GUKETH.equals(type)) {
				stringBuffer.append(",");
				stringBuffer.append("退货数量");
				stringBuffer.append(",");
			}

			stringBuffer.append("成本");
			stringBuffer.append(",");
			stringBuffer.append("类型");
			stringBuffer.append(",");
			stringBuffer.append("名称");
			stringBuffer.append(",");
			stringBuffer.append("规格");
			stringBuffer.append(",");
			stringBuffer.append("颜色");
			if (Constant.STCOK_TYPE_JINHUO.equals(type)) {
				stringBuffer.append(",");
				stringBuffer.append("进货时间");
				stringBuffer.append(",");
			} else if (Constant.STCOK_TYPE_CAIGOUTH.equals(type)) {
				stringBuffer.append(",");
				stringBuffer.append("退货时间");
				stringBuffer.append(",");
				stringBuffer.append("退货原因");
				stringBuffer.append(",");
			} else if (Constant.STCOK_TYPE_GUKETH.equals(type)) {
				stringBuffer.append(",");
				stringBuffer.append("退货时间");
				stringBuffer.append(",");
				stringBuffer.append("退货原因");
				stringBuffer.append(",");
			}
			stringBuffer.append("");
			stringBuffer.append("\r\n");
			output.write(String.valueOf(stringBuffer));
			for (int i = 0; i < list.size(); i++) {
				Stock stock = (Stock) list.get(i);
				StringBuilder sb = new StringBuilder(128);
				sb.append(stock.getCatno());
				sb.append(",");
				sb.append(stock.getSellprice());
				sb.append(",");
				if (Constant.STCOK_TYPE_JINHUO.equals(type)) {
					sb.append(stock.getSyamount());
					sb.append(",");
				}

				sb.append(stock.getAmount());
				sb.append(",");
				sb.append(stock.getCostprice());
				sb.append(",");
				sb.append(stock.getType());
				sb.append(",");
				sb.append(stock.getStockname());
				sb.append(",");
				sb.append(stock.getSpecif());
				sb.append(",");
				sb.append(stock.getColor());
				sb.append(",");
				sb.append(stock.getDate());
				if (Constant.STCOK_TYPE_CAIGOUTH.equals(type)
						|| Constant.STCOK_TYPE_GUKETH.equals(type)) {
					sb.append(",");
					sb.append(stock.getRemarks());
				}
				sb.append("\r\n");
				output.write(String.valueOf(sb));
			}
			output.close();
			out.close();
			JOptionPane.showMessageDialog(MainWindows.getInstance(), "文件导出成功");
		} else {
			JOptionPane.showMessageDialog(MainWindows.getInstance(), "没数据导出", "数据导出通知",
					JOptionPane.WARNING_MESSAGE);
		}

	}

	public void clear() {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
	}

	private void pageData(List list) {
		clear();
		if (list != null && list.size() > 0) {

			DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			for (int i = 0; i < list.size(); i++) {
				Stock stock = (Stock) list.get(i);
				Object[] rowData = new Object[] { stock.getId(),
						stock.getCatno(), stock.getSyamount(),
						stock.getAmount(), stock.getType(),
						stock.getSellprice(), stock.getCostprice(),
						stock.getStockname(), stock.getSpecif(),
						stock.getColor(), stock.getDate(), stock.getRemarks(),100d*stock.getSyamount()/stock.getAmount(),
						"删除" };
				tableModel.insertRow(i, rowData);
			}

		}
	}

	private void clickedTable(int clickCount) {
		int nCol = table.getSelectedColumn();
		int nRow = table.getSelectedRow();
		
		if (nCol != -1 && nRow != -1) {
			Object objSel = table.getValueAt(nRow, nCol);
			String id = String.valueOf(table.getValueAt(nRow, 0));
			if (clickCount == 2) {
				StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
				Stock stockold = stockMoudle.getStockByID(id);
				KuCunDialog stockDialog = new KuCunDialog(this, "修改库存货物信息",
						stockold, table, new KuCunDialog.CallBack() {
							public void updateView() {
							}
						});
				stockDialog.setVisible(true);
			} else {
				if (objSel != null && objSel instanceof String) {
					if ("删除".equals(String.valueOf(objSel).trim())) {
						int response = JOptionPane.showConfirmDialog(null,
								"确定删除此条数据?", "删除库存数据",
								JOptionPane.YES_NO_OPTION);
						switch (response) {
						case JOptionPane.YES_OPTION:
							DefaultTableModel tableModel = (DefaultTableModel) table
									.getModel();

							StockDAOImpl stockMoudle = DAOContentFactriy
									.getStockDAO();
							boolean isd = stockMoudle.deleteById(id);
							if (isd) {
								tableModel.removeRow(nRow);
								double lcsl = stockMoudle.sumStockSyAmount(
										catno, cost, type, date, dateTo);
								lblDdd.setText("");
								if (lcsl <= 0) {
									lblDdd.setText("当前没有库存了,请赶快进货吧!");
									clear();
								}

							}
						case JOptionPane.NO_OPTION:

						}
					}
				}
			}
		}

	}

	private void tableViewChange(String type) {
	
		if (Constant.STCOK_TYPE_JINHUO.equals(type)) {
			DefaultTableModel defaultTableModel = (DefaultTableModel) table
					.getModel();
			String[] c = new String[] { "id", "\u8D27\u53F7",
					"\u5269\u4F59\u6570\u91CF", "\u8FDB\u8D27\u6570\u91CF",
					"\u7C7B\u578B", "\u552E\u4EF7", "\u6210\u672C",
					"\u540D\u79F0", "\u89C4\u683C", "\u989C\u8272",
					"\u5165\u5E93\u65F6\u95F4", "退货原因","库存状态", "\u64CD\u4F5C" };
			defaultTableModel.setColumnIdentifiers(c);
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(0).setPreferredWidth(0);
			table.getColumnModel().getColumn(0).setMinWidth(0);
			table.getColumnModel().getColumn(0).setMaxWidth(0);
			table.getColumnModel().getColumn(1).setPreferredWidth(101);
			table.getColumnModel().getColumn(2).setPreferredWidth(85);
			table.getColumnModel().getColumn(3).setPreferredWidth(85);
			table.getColumnModel().getColumn(4).setPreferredWidth(85);
			table.getColumnModel().getColumn(9).setResizable(false);
			table.getColumnModel().getColumn(10).setMinWidth(75);
			table.getColumnModel().getColumn(10).setMaxWidth(75);
			table.getColumnModel().getColumn(11).setResizable(false);
			table.getColumnModel().getColumn(11).setPreferredWidth(0);
			table.getColumnModel().getColumn(11).setMinWidth(0);
			table.getColumnModel().getColumn(11).setMaxWidth(0);
			DefaultTableCellRenderer fontColor = new DefaultTableCellRenderer() {
				public void setValue(Object value) { // 重写setValue方法，从而可以动态设置列单元字体颜色
					BigDecimal bigDecimal = new BigDecimal(String.valueOf(value));
					if (bigDecimal.compareTo(new BigDecimal(0)) < 0) {
						setForeground(Color.red);
					}
					setText((value == null) ? "0" : value.toString());
				}
			};

			table.getColumn("剩余数量").setCellRenderer(fontColor);
			table.getColumn("进货数量").setCellRenderer(fontColor);

		
			GuiUtils.columnCenter(table,"操作",60);

            table.getColumn("库存状态").setCellRenderer(new ProgressRenderer());
		} else {
			DefaultTableModel defaultTableModel = (DefaultTableModel) table
					.getModel();
			String[] c = new String[] { "id", "\u8D27\u53F7",
					"\u5269\u4F59\u6570\u91CF", "退货数量", "\u7C7B\u578B",
					"\u552E\u4EF7", "\u6210\u672C", "\u540D\u79F0",
					"\u89C4\u683C", "\u989C\u8272", "退货日期", "退货原因",
					"\u64CD\u4F5C" };
			defaultTableModel.setColumnIdentifiers(c);
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(0).setPreferredWidth(0);
			table.getColumnModel().getColumn(0).setMinWidth(0);
			table.getColumnModel().getColumn(0).setMaxWidth(0);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setPreferredWidth(0);
			table.getColumnModel().getColumn(2).setMinWidth(0);
			table.getColumnModel().getColumn(2).setMaxWidth(0);
			table.getColumnModel().getColumn(11).setResizable(false);
			table.getColumnModel().getColumn(11).setPreferredWidth(60);
			table.getColumnModel().getColumn(11).setMinWidth(60);
			table.getColumnModel().getColumn(11).setMaxWidth(60);
		
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			TableColumn tc = table.getColumn("操作");
			tc.setPreferredWidth(40);
			tc.setCellRenderer(renderer);
		
		}
		table.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());
	}

    class ProgressRenderer extends GyrJProgressBar implements TableCellRenderer {

        public ProgressRenderer() {
            super();
            this.setStringPainted(true);
            this.setBorderPainted(false);
            this.setBorder(null);
            this.setFont(new Font("宋体", Font.BOLD, 12));
            setBackground(UIManager.getColor("Table.background"));
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
        	int intPercent = 0;
            if(!"NaN".equals(String.valueOf(value))) {
            	 Double dPercent = (Double) value;
                 intPercent=new BigDecimal(dPercent).setScale(0,BigDecimal.ROUND_HALF_UP).intValue();  //四舍五入
            }
           
            this.setValue(intPercent);
            this.setString(intPercent+"%");
            this.setForeground(Color.decode("#FFFFFF"));
            return this;
        }

        public boolean isDisplayable() {
            return true;
        }

    }

}
