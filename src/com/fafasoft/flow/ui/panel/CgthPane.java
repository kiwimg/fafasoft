package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.SysEnv;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.StockDAOImpl;
import com.fafasoft.flow.pojo.Option;
import com.fafasoft.flow.pojo.Stock;
import com.fafasoft.flow.ui.ActionView;
import com.fafasoft.flow.ui.LimitedDocument;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.widget.JTextFieldWithButton;
import com.fafasoft.flow.ui.widget.JTreeComboBox;
import com.fafasoft.flow.ui.widget.StockListDialog;
import com.fafasoft.flow.ui.widget.StockQueryDialog;
import com.fafasoft.flow.util.DateHelper;
import com.fafasoft.flow.util.GuiUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.UUID;

public class CgthPane extends BaseJPanel implements LazyPageContent {

    private JTextField suggestTextField;

	private JTable table;
	private JTextField textField_1;
	private JTextField textField;
	private JTreeComboBox jtreeComboBox;
	private String tempStockId = "";
	private JLabel tuihjeTJ; //采购退货金额
	private JLabel tuihslTJ; //采购退货数量

	public CgthPane() {
		setLayout(new BorderLayout(0, 0));
	}

	public void initPanel() {
		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(10, 60));
		panel_2.setBorder(new TitledBorder(null,
				"\u91C7\u8D2D\u9000\u8D27\u7EDF\u8BA1", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(null);

		JLabel label_1 = new JLabel("采购退货金额");
		label_1.setBounds(12, 29, 80, 17);
		panel_2.add(label_1);

		tuihjeTJ= new JLabel("0");
		tuihjeTJ.setBounds(93, 29, 73, 17);
		panel_2.add(tuihjeTJ);

		JLabel label_7 = new JLabel("退货数量");
		label_7.setBounds(178, 29, 58, 17);
		panel_2.add(label_7);

		tuihslTJ= new JLabel("0");
		tuihslTJ.setBounds(237, 29, 80, 17);
		panel_2.add(tuihslTJ);

		JLabel label_9 = new JLabel("查询以往采购退货记录请在库存查询中查询");
		label_9.setBounds(360, 29, 298, 17);
		panel_2.add(label_9);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 100));
		panel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"),
				"\u8F93\u5165\u91C7\u8D2D\u9000\u8D27\u4FE1\u606F",
				TitledBorder.LEADING, TitledBorder.TOP, null));
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(null);

		JLabel label = new JLabel("货号");
		label.setBounds(222, 30, 24, 15);
		panel.add(label);

		suggestTextField = new JTextField();

		suggestTextField.setFont(new Font("宋体", Font.BOLD, 12));
		suggestTextField.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				boolean issuggest = SysEnv.getInstance().isSuggest();
				if (issuggest) {
					Object ob = jtreeComboBox.getSelectedItemValue();
					if (ob != null) {
						Option op = (Option) ob;
						StockSuggestDataJTableImpl stockSuggestDataJTableImpl = new StockSuggestDataJTableImpl();
						String ds = suggestTextField.getText();
						final JTable jTable = stockSuggestDataJTableImpl
								.getData(ds, op.getText());

						if (jTable != null) {
							int size = jTable.getRowCount() + 1;
							JScrollPane tipFrame = new JScrollPane();
							tipFrame.setViewportView(jTable);
							final JPopupMenu jPopupMenu = new JPopupMenu();
							jPopupMenu.setFocusable(false);

							jPopupMenu.add(tipFrame);
							int h = (size * 20) + 24;

							tipFrame.setPreferredSize(new Dimension(300, h));
							jPopupMenu.show(suggestTextField, 0,suggestTextField.getHeight() - h - 35);
							jPopupMenu.setBorder(new TitledBorder("输入提示"));
							jTable.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent arg0) {
									int nRow = jTable.getSelectedRow();
									Object objSel = jTable.getValueAt(nRow, 1);
									tempStockId = String.valueOf(jTable.getValueAt(nRow, 0));
									suggestTextField.setText(String
											.valueOf(objSel));
									textField_1.requestFocus();
									jPopupMenu.setVisible(false);
								}
							});
						}

					}
				}
			}
		});

		ImageIcon image = new ImageIcon(SellHelperPanel.class
				.getResource("/com/fafasoft/flow/resource/image/zoom-s.png"));
		JTextFieldWithButton jtWithButton = new JTextFieldWithButton(
				suggestTextField, image);
		jtWithButton.addButtonActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StockQueryDialog stockQueryDialog = new StockQueryDialog(
						MainWindows.getInstance(), "查询库存货物", true, true,
						new ActionView() {
							public void actionView(Object[] arg) {
								if (arg != null) {
									tempStockId = String.valueOf(arg[0]);
									suggestTextField.setText(String
											.valueOf(arg[1]));
									textField_1.requestFocus();
								}
							}
						}, null);
				stockQueryDialog.setVisible(true);
			}
		});
		jtWithButton.setBounds(250, 27, 121, 21);
		panel.add(jtWithButton);
		JLabel label_2 = new JLabel("数量");
		label_2.setBounds(383, 30, 24, 15);
		panel.add(label_2);

		textField_1 = new JTextField();

		textField_1.setBounds(410, 27, 76, 21);
		textField_1.setText("1");
		textField_1.setFont(new Font("宋体", Font.BOLD, 12));
        String intString = "1234567890.";
        textField_1.setDocument(new LimitedDocument(10, intString));
		panel.add(textField_1);
		textField_1.setColumns(10);

		JButton button = new JButton(" 退  货  ");
		button.setBounds(407, 57, 81, 23);
		button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				submmit();
			}
		});
		panel.add(button);

		JLabel label_3 = new JLabel("");
		label_3.setBounds(0, 0, 0, 0);
		panel.add(label_3);

		JLabel label_4 = new JLabel("货物类别");
		label_4.setBounds(29, 30, 58, 17);
		panel.add(label_4);

		final JTree tree = getJTree();

		jtreeComboBox = new JTreeComboBox(tree);// ;
		jtreeComboBox.setBounds(82, 26, 130, 23);
		panel.add(jtreeComboBox);

		JLabel label_5 = new JLabel("退货原因");
		label_5.setBounds(29, 61, 58, 17);
		panel.add(label_5);

		textField = new JTextField();
		textField.setBounds(82, 57, 289, 23);
		panel.add(textField);
		textField.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null,
				"\u91C7\u8D2D\u9000\u8D27\u5217\u8868", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoCreateRowSorter(true);
		table.setRowHeight(21);
		table.setModel(new DefaultTableModel(null,
			new String[] {
				"ID", "\u8D27\u53F7", "\u9000\u8D27\u6570\u91CF", "\u8FDB\u8D27\u4EF7", "\u7C7B\u578B", "\u540D\u79F0", "\u9000\u8D27\u539F\u56E0", "\u989C\u8272", "\u89C4\u683C", "\u9000\u8D27\u65F6\u95F4", "\u64CD\u4F5C"
			}
		) {
			Class[] columnTypes = new Class[] {
				Object.class, String.class, Double.class, Double.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(1).setMaxWidth(75);
		table.getColumnModel().getColumn(2).setPreferredWidth(62);
		table.getColumnModel().getColumn(2).setMaxWidth(62);
		table.getColumnModel().getColumn(3).setPreferredWidth(62);
		table.getColumnModel().getColumn(3).setMaxWidth(62);
		table.getColumnModel().getColumn(4).setPreferredWidth(120);
		table.getColumnModel().getColumn(4).setMaxWidth(180);
		table.getColumnModel().getColumn(5).setPreferredWidth(129);
		table.getColumnModel().getColumn(5).setMaxWidth(150);
		table.getColumnModel().getColumn(6).setPreferredWidth(106);
		table.getColumnModel().getColumn(7).setPreferredWidth(40);
		table.getColumnModel().getColumn(8).setPreferredWidth(40);
		table.getColumnModel().getColumn(9).setPreferredWidth(66);
		table.getColumnModel().getColumn(10).setPreferredWidth(60);
		table.getColumnModel().getColumn(10).setMaxWidth(60);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		GuiUtils.columnCenter(table,"操作",60);

	
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				clickedTable("确定删除此条数据?", "删除采购退货数据", table);
			}
		});
		scrollPane.setViewportView(table);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initview();
			}
		});
		
	}

	private void submmit() {
		Object t0 = jtreeComboBox.getSelectedItemValue();
		if (t0 == null) {
			GuiUtils.toolTips(jtreeComboBox, "请选择退货类型!");
			return;
		}
		String catno = suggestTextField.getText();
		if (catno.trim().length() == 0) {
			tempStockId = "";
			GuiUtils.toolLeftBelowTips(suggestTextField, "请输入货物号码!");
			return;
		}
		String num = textField_1.getText();
		if (num.trim().length() == 0) {
			GuiUtils.toolTips(textField_1, "请输入退货数量!");
			return;
		}
		StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
		Stock tStock = null;
		if(tempStockId.length() >0) {
			tStock=stockMoudle.getStockByID(tempStockId);
			cgTuiHuo(tStock, num, stockMoudle );
		}else {
			java.util.List slist = stockMoudle.getStockSuggest(catno, ((Option)t0).getText(),50);
			if(slist != null && slist.size() >0) {
				if(slist.size() ==1) {
					tStock = (Stock)slist.get(0);
					cgTuiHuo(tStock, num, stockMoudle );
				}else {
					StockListDialog stockQueryDialog = new StockListDialog(
							MainWindows.getInstance(), "选择一个库存货物进行采购退货处理", catno,true,slist,"采购退货",
							new ActionView() {
								public void actionView(Object[] arg) {
									if (arg != null) {
										tempStockId = String.valueOf(arg[0]);
										suggestTextField.setText(String
												.valueOf(arg[1]));
										textField_1.requestFocus();
									}
								}
							});
					stockQueryDialog.setVisible(true);
				}
			}
		}
	}
	
	private void cgTuiHuo(Stock tStock,String num,StockDAOImpl stockMoudle ) {
		if (tStock == null) {
			GuiUtils.toolLeftBelowTips(suggestTextField, "输入货号库存中不存在!请检查货物类型和货号是否输入正确!");
			return;
		} else {
			double amount = stockMoudle.sumStockAmount(tStock.getCatno());
			double syamount = stockMoudle.sumStockSyAmount(tStock.getCatno());
			if (Double.parseDouble(num) > amount
					|| Double.parseDouble(num) > syamount) {

				GuiUtils.toolTips(textField_1, "输入退货数量大于库存货物数量！ 请重新输入!");
				return;
			}
		}
		int response = JOptionPane.showConfirmDialog(MainWindows.getInstance(),
				"确定退货[" + tStock.getCatno() + "]数据?", "采购退货", JOptionPane.YES_NO_OPTION);
		if(response==JOptionPane.YES_OPTION) {
			Object[] rowData = new Object[] { tStock.getId(),
					tStock.getCatno(),
					Double.parseDouble(num),
					tStock.getCostprice().doubleValue(), tStock.getType(),
					tStock.getStockname(),tStock.getColor(),tStock.getSpecif(), textField.getText(),
					tStock.getDate(), "删除" };
			DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			tableModel.insertRow(0, rowData);
			tStock.setAmount(tStock.getAmount() - Double.parseDouble(num));
			double sy = tStock.getSyamount() - Double.parseDouble(num);
			tStock.setSyamount(sy);
			tStock.setTotal(tStock.getCostprice().multiply(
					BigDecimal.valueOf(sy)));
			stockMoudle.updateStock(tStock);
			// label_1.setText("退货入库成功");
			String id = String.valueOf(UUID.randomUUID().toString().replaceAll(
					"-", ""));

			tStock.setId(id);
			tStock.setAmount(Double.parseDouble(num));
			tStock.setSyamount(Double.parseDouble(num));
			tStock.setStockFlag(Constant.STCOK_TYPE_CAIGOUTH);
			tStock.setRemarks(textField.getText());
			tStock.setDate(DateHelper.getNowDateTime());
			//
			tStock.setTotal(tStock.getCostprice().multiply(
					BigDecimal.valueOf(Double.parseDouble(num))));
			stockMoudle.add(tStock);
			sumAll();
			clear();
		}
	}
	public void clear() {
		suggestTextField.setText("");
		textField_1.setText("");
		tempStockId = "";
	}
   
    private void initview() {
    	StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
    	java.util.List list = stockMoudle.getCaiGThStockByTaday();
    	DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
    	tableModel.setRowCount(0);
    	clear();
    	if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Stock tStock = (Stock) list.get(i);
				Object[] rowData = new Object[] { tStock.getId(),
						tStock.getCatno(),
						tStock.getAmount(),
						tStock.getCostprice().doubleValue(), tStock.getType(),
						tStock.getStockname(),tStock.getColor(),tStock.getSpecif(), tStock.getRemarks(),
						tStock.getDate(), "删除" };
				tableModel.insertRow(0,rowData);
			}
		}
    	sumAll();
    }
	private void sumAll() {
		DefaultTableModel defaultTableModel = (DefaultTableModel) table
				.getModel();
		double je =0;
		double shl=0;
		for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
			shl = shl+(Double)table.getValueAt(i, 2);
			je = je +(Double)table.getValueAt(i, 3);
		}
		tuihjeTJ.setText(String.valueOf(je)); //采购退货金额
		tuihslTJ.setText(String.valueOf(shl)); //采购退货数量
	}

	private void clickedTable(String message, String title, JTable tableObj) {
		int nCol = tableObj.getSelectedColumn();
		int nRow = tableObj.getSelectedRow();
		Object objSel = tableObj.getValueAt(nRow, nCol);

		if (objSel != null && objSel instanceof String) {
			if ("删除".equals(String.valueOf(objSel))) {
				int response = JOptionPane.showConfirmDialog(MainWindows.getInstance(), message,
						title, JOptionPane.YES_NO_OPTION);
				switch (response) {
				case JOptionPane.YES_OPTION:
					DefaultTableModel tableModel = (DefaultTableModel) tableObj
							.getModel();
					String stockId = String.valueOf(tableModel.getValueAt(nRow,
							0));

					tableModel.removeRow(nRow);
					StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
					stockMoudle.deleteById(stockId);
					Stock tStock = stockMoudle.getStockByID(stockId);
					stockMoudle.updateSyAmount(stockId, tStock.getAmount(), "+");
				case JOptionPane.NO_OPTION:
				case JOptionPane.CLOSED_OPTION:
				}
			}
		}
	}
}
