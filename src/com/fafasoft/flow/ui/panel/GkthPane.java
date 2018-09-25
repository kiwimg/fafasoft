package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.SysEnv;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.FlowLogDAOImpl;
import com.fafasoft.flow.dao.impl.StockDAOImpl;
import com.fafasoft.flow.pojo.Flowlog;
import com.fafasoft.flow.pojo.Option;
import com.fafasoft.flow.pojo.Stock;
import com.fafasoft.flow.ui.ActionView;
import com.fafasoft.flow.ui.LimitedDocument;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.widget.JTextFieldWithButton;
import com.fafasoft.flow.ui.widget.JTreeComboBox;
import com.fafasoft.flow.ui.widget.StockQueryDialog;
import com.fafasoft.flow.util.DateHelper;
import com.fafasoft.flow.util.GuiUtils;
import com.sun.awt.AWTUtilities;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class GkthPane extends BaseJPanel implements LazyPageContent {
	private JTextField suggestTextField;
    private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel label_6;
	private JLabel label_8;
	private JTextField textField_2;
	private JTreeComboBox jtreeComboBox = null;
	private String tempFlowNO = "";
	private String tempStockId = "";

	public GkthPane() {
		setLayout(new BorderLayout(0, 0));
	}

	public void initPanel() {
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"),
				"\u987E\u5BA2\u9000\u8D27\u5217\u8868", TitledBorder.LEADING,
				TitledBorder.TOP, null));
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setRowHeight(21);
		table.setModel(new DefaultTableModel(null, new String[] { "ID",
				"\u8D27\u53F7", "\u552E\u4EF7", "\u6570\u91CF", "\u7C7B\u578B",
				"\u540D\u79F0", "\u9000\u8D27\u539F\u56E0","颜色","规格",
				"\u9000\u8D27\u65F6\u95F4", "\u64CD\u4F5C" }) {
			Class[] columnTypes = new Class[] { String.class, String.class,
					BigDecimal.class, Double.class, String.class, String.class,
					Object.class, String.class, String.class, String.class, String.class };
			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false, false, false, false, false, false, false };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(1).setMaxWidth(100);
		table.getColumnModel().getColumn(3).setMaxWidth(75);
		table.getColumnModel().getColumn(4).setPreferredWidth(105);
		table.getColumnModel().getColumn(4).setMaxWidth(200);
		table.getColumnModel().getColumn(5).setPreferredWidth(150);
		table.getColumnModel().getColumn(6).setPreferredWidth(120);
		table.getColumnModel().getColumn(7).setPreferredWidth(75);
		table.getColumnModel().getColumn(8).setPreferredWidth(40);
		table.getColumnModel().getColumn(9).setPreferredWidth(70);
		table.getColumnModel().getColumn(10).setPreferredWidth(80);
		table.getColumnModel().getColumn(10).setMinWidth(80);
		table.getColumnModel().getColumn(10).setMaxWidth(80);
		DefaultTableCellRenderer fontColor = new DefaultTableCellRenderer() {
			public void setValue(Object value) { // 重写setValue方法，从而可以动态设置列单元字体颜色
				setForeground(Color.green);
				setText((value == null) ? "" : value.toString());
			}
		};
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				clickedTable();
			}
		});
		table.getColumn("售价").setCellRenderer(fontColor);
		// table.getColumn("利润").setCellRenderer(fontColor);
		table.getColumn("数量").setCellRenderer(fontColor);

		table.getTableHeader().setReorderingAllowed(false);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		TableColumn tc = table.getColumn("操作");
		tc.setPreferredWidth(60);
		tc.setCellRenderer(renderer);
		
		scrollPane.setViewportView(table);
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null,
				"\u987E\u5BA2\u9000\u8D27\u7EDF\u8BA1", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		add(panel_2, BorderLayout.NORTH);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 26, 54, 52, 0, 49, 0, 0, 0, 0, 0 };
		gbl_panel_2.rowHeights = new int[] { 30, 0 };
		gbl_panel_2.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		JLabel label_5 = new JLabel("今日退货金额");
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.fill = GridBagConstraints.VERTICAL;
		gbc_label_5.insets = new Insets(0, 0, 0, 5);
		gbc_label_5.anchor = GridBagConstraints.WEST;
		gbc_label_5.gridx = 1;
		gbc_label_5.gridy = 0;
		panel_2.add(label_5, gbc_label_5);

		label_6 = new JLabel("0");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.fill = GridBagConstraints.VERTICAL;
		gbc_label_6.insets = new Insets(0, 0, 0, 5);
		gbc_label_6.gridx = 2;
		gbc_label_6.gridy = 0;
		panel_2.add(label_6, gbc_label_6);

		JLabel label_7 = new JLabel("今日退货数量");
		GridBagConstraints gbc_label_7 = new GridBagConstraints();
		gbc_label_7.fill = GridBagConstraints.VERTICAL;
		gbc_label_7.insets = new Insets(0, 0, 0, 5);
		gbc_label_7.gridx = 3;
		gbc_label_7.gridy = 0;
		panel_2.add(label_7, gbc_label_7);

		label_8 = new JLabel("0");
		GridBagConstraints gbc_label_8 = new GridBagConstraints();
		gbc_label_8.insets = new Insets(0, 0, 0, 5);
		gbc_label_8.fill = GridBagConstraints.VERTICAL;
		gbc_label_8.gridx = 4;
		gbc_label_8.gridy = 0;
		panel_2.add(label_8, gbc_label_8);

		JLabel label_9 = new JLabel("");
		GridBagConstraints gbc_label_9 = new GridBagConstraints();
		gbc_label_9.insets = new Insets(0, 0, 0, 5);
		gbc_label_9.gridwidth = 3;
		gbc_label_9.gridx = 6;
		gbc_label_9.gridy = 0;
		panel_2.add(label_9, gbc_label_9);
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 110));
		panel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"),
				"\u8F93\u5165\u987E\u5BA2\u9000\u8D27\u4FE1\u606F",
				TitledBorder.LEADING, TitledBorder.TOP, null));
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(null);

		JLabel label = new JLabel("货物类别");
		label.setBounds(30, 32, 48, 17);
		panel.add(label);
		final JTree tree = getJTree();

		jtreeComboBox = new JTreeComboBox(tree);//
		jtreeComboBox.setBounds(80, 30, 136, 23);
		panel.add(jtreeComboBox);

		JLabel label_1 = new JLabel("货号");
		label_1.setBounds(220, 32, 24, 17);
		panel.add(label_1);

		suggestTextField = new JTextField();

		suggestTextField.setFont(new Font("宋体", Font.BOLD, 12));
		suggestTextField.setColumns(7);
		ImageIcon image = new ImageIcon(SellHelperPanel.class
				.getResource("/com/fafasoft/flow/resource/image/zoom-s.png"));
		JTextFieldWithButton jtWithButton = new JTextFieldWithButton(
				suggestTextField, image);
		jtWithButton.addButtonActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object ob = jtreeComboBox.getSelectedItemValue();
				String type = ob == null ? null : ((Option) ob).getText();
				Option op = (Option) ob;
				StockQueryDialog stockQueryDialog = new StockQueryDialog(
						MainWindows.getInstance(), "查询已售出货物", false, false,
						new ActionView() {

							public void actionView(Object[] arg) {
								if (arg != null) {
									suggestTextField.setText(String
											.valueOf(arg[2]));// 货号
									tempFlowNO = String.valueOf(arg[0]);// flowno
																		// 流水ID
									tempStockId = String.valueOf(arg[1]);// 货物ID
									textField.requestFocus();
								}
							}
						}, new String[] { suggestTextField.getText(), type,
								null, null, null });
				stockQueryDialog.setVisible(true);
			}
		});
		jtWithButton.setBounds(245, 30, 117, 23);

		jtWithButton.getJTextField().addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				boolean issuggest = SysEnv.getInstance().isSuggest();

				if (issuggest) {
					Object ob = jtreeComboBox.getSelectedItemValue();
					if (ob != null) {
						suggestTextField.requestFocus();
						Option op = (Option) ob;
						StockSuggestDataJTableImpl stockSuggestDataJTableImpl = new StockSuggestDataJTableImpl();
						String ds = suggestTextField.getText();
						final JTable jTable = stockSuggestDataJTableImpl
								.getFlowData(ds, op.getText());
						if (jTable != null) {
							int size = jTable.getRowCount() + 1;
							JScrollPane tipFrame = new JScrollPane();
							tipFrame.setViewportView(jTable);
							
							final JPopupMenu jPopupMenu = new JPopupMenu();
							
							jPopupMenu.add(tipFrame);
							jPopupMenu.setFocusable(false);
							int h = (size * 20) + 24;
							tipFrame.setPreferredSize(new Dimension(540, h));
							jPopupMenu.show(suggestTextField, 0,
									suggestTextField.getHeight() - h - 30);
										 
							jPopupMenu.setBorder(new TitledBorder("输入提示"));
							jTable.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent arg0) {

									int nRow = jTable.getSelectedRow();
									String flowNo = String.valueOf(jTable
											.getValueAt(nRow, 0));
									String stockId = String.valueOf(jTable
											.getValueAt(nRow, 1));
									Object catno = jTable.getValueAt(nRow, 2);
									tempFlowNO = flowNo;// flowno 流水ID
									tempStockId = stockId;// 货物ID
									jPopupMenu.setVisible(false);
									suggestTextField.setText(String
											.valueOf(catno));
									textField.requestFocus();
								}
							});
						}
					}
				}
			}
		});
		panel.add(jtWithButton);

		JLabel label_2 = new JLabel("数量");
		label_2.setBounds(372, 32, 24, 15);

		panel.add(label_2);

		textField = new JTextField();
		textField.setBounds(401, 30, 35, 23);
		textField.setFont(new Font("宋体", Font.BOLD, 12));
        String dubString = "-1234567890.";
        textField.setDocument(new LimitedDocument(20, dubString));
		textField.setText("1");
		panel.add(textField);
		textField.setColumns(3);

		JLabel label_3 = new JLabel("退货售价");
		label_3.setBounds(441, 32, 48, 17);
		panel.add(label_3);

		textField_1 = new JTextField();
		textField_1.setBounds(492, 30, 58, 23);
		textField_1.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {// 回车
					submmit();
				}
			}
		});
		textField_1.setFont(new Font("宋体", Font.BOLD, 12));
		textField_1.setDocument(new LimitedDocument(20, dubString));
		panel.add(textField_1);
		textField_1.setColumns(5);

		JLabel label_4 = new JLabel("退货原因");
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setBounds(20, 64, 58, 23);
		panel.add(label_4);

		textField_2 = new JTextField();
		textField_2.setBounds(80, 61, 282, 23);
		panel.add(textField_2);
		textField_2.setColumns(10);

		JButton button = new JButton("退  货");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				submmit();
			}
		});
		button.setBounds(473, 59, 79, 23);
		panel.add(button);
		init();

	}

	private void init() {
		clear();
		DefaultTableModel defaultTableModel = (DefaultTableModel) table
				.getModel();
		defaultTableModel.setRowCount(0);
		FlowLogDAOImpl flowLogMoudle = DAOContentFactriy.getFlowLogDAO();
		StockDAOImpl stockDAO = DAOContentFactriy.getStockDAO();
		List list = flowLogMoudle.getFlowlogByToday(Constant.FLOW_TYPE_TH);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Flowlog flowlog = (Flowlog) list.get(i);
				Stock stock = stockDAO.getStockByID(flowlog.getStockId());
				if(stock == null) {
					stock = new Stock();
				}
				Object[] rowData = new Object[] { flowlog.getFlowno(),
						flowlog.getCatno(), flowlog.getSellprice(),
						flowlog.getAmount(), flowlog.getType(),
						flowlog.getStockname(), stock.getColor(),stock.getSpecif(),flowlog.getRemarks(),
						flowlog.getDate(), "删除" };
				defaultTableModel.insertRow(0, rowData);
			}
		}
		deltail();
	}

	private void clickedTable() {
		int nCol = table.getSelectedColumn();
		int nRow = table.getSelectedRow();
		Object objSel = table.getValueAt(nRow, nCol);
		if (objSel != null && objSel instanceof String) {
			if ("删除".equals(String.valueOf(objSel))) {
				DefaultTableModel defaultTableModel = (DefaultTableModel) table
						.getModel();
				int response = JOptionPane.showConfirmDialog(null, "确定删除此条数据?",
						"删除退货数据", JOptionPane.YES_NO_OPTION);
				switch (response) {
				case JOptionPane.YES_OPTION:
					// todo
					String flowno = String.valueOf(defaultTableModel
							.getValueAt(nRow, 0));
					String catno = String.valueOf(defaultTableModel.getValueAt(
							nRow, 1));
					Double sl = (Double) defaultTableModel.getValueAt(nRow, 3);
					FlowLogDAOImpl flowLogMoudle = DAOContentFactriy
							.getFlowLogDAO();
					Flowlog flowlog = flowLogMoudle.getFlowByflowno(flowno);
					flowLogMoudle.deleteByFlowno(flowno);
					StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
					stockMoudle.deleteById(flowlog.getStockId());
					defaultTableModel.removeRow(nRow);
					Stock stock = stockMoudle.getStockByNo(catno);
					if (stock != null) {
						stockMoudle.updateSyAmount(stock.getId(), sl, "+");
					}

					deltail();
				case JOptionPane.NO_OPTION:
					// case JOptionPane.CANCEL_OPTION:

				case JOptionPane.CLOSED_OPTION:

				}
			}
		}
	}

	public void submmit() {
		Flowlog flowlog = getFormData();
		if (flowlog != null) {
			int response = JOptionPane.showConfirmDialog(null, "确定货物["
					+ flowlog.getCatno() + "]退货处理?,请确保退货售价正确", "顾客退货",
					JOptionPane.YES_NO_OPTION);
			switch (response) {
			case JOptionPane.YES_OPTION:
				setFormData(flowlog);
				
				clear();
			case JOptionPane.NO_OPTION:
			case JOptionPane.CLOSED_OPTION:
			}

		}
	}

	public Flowlog getFormData() {
		Object t0 = jtreeComboBox.getSelectedItemValue();
		if (t0 == null) {
			GuiUtils.toolTips(jtreeComboBox, "请选择退货类型!");
			return null;
		}
		String t1 = suggestTextField.getText();
		if (t1 == null || t1.trim().length() == 0) {
			tempFlowNO = "";
			GuiUtils.toolLeftBelowTips(suggestTextField, "请输入货物号码!");
			return null;
		}
		String t2 = textField.getText();
		if (t2 == null || t2.trim().length() == 0) {
			GuiUtils.toolTips(textField, "请输入退货货物数量!");
			return null;
		}
		String thjg = textField_1.getText();
		if (thjg == null || thjg.trim().length() == 0) {
			GuiUtils.toolTips(textField_1, "请输入货物销售价格!");
			return null;
		}
		BigDecimal sell = new BigDecimal(thjg);
		BigDecimal selltemp = new BigDecimal(0);
		if (sell.compareTo(new BigDecimal("0")) == 0) {
			GuiUtils.toolTips(textField_1, "输入货物销售价格错误!");
			return null;
		} else if (sell.compareTo(new BigDecimal(-1)) == 1) {// 退货转换为负数
			selltemp = sell.multiply(new BigDecimal(-1));
		}
		// tempFlowNO= flowNo;//flowno 流水ID
		// tempStockId= stockId;//货物ID
		FlowLogDAOImpl flowLogMoudle = DAOContentFactriy.getFlowLogDAO();
		Flowlog oldflowLog = null;
		Option op = (Option) t0;
		if (tempFlowNO.length() > 0) {
			oldflowLog = flowLogMoudle.getFlowByflowno(tempFlowNO);
		} else {
			List flowlist = flowLogMoudle.getSellFlowlog(t1, op.getText(), 10);
			if (flowlist != null && flowlist.size() > 0) {
				if (flowlist.size() == 1) {
					oldflowLog = (Flowlog) flowlist.get(0);
				} else {
					StockQueryDialog stockQueryDialog = new StockQueryDialog(
							MainWindows.getInstance(), "选择一个已售出货物进行退货处理", false, false,
							new ActionView() {
								public void actionView(Object[] arg) {
									if (arg != null) {
										suggestTextField.setText(String
												.valueOf(arg[2]));// 货号
										tempFlowNO = String.valueOf(arg[0]);// flowno	// 流水ID								
										tempStockId = String.valueOf(arg[1]);// 货物ID
										textField.requestFocus();
									}
								}
							}, new String[] { suggestTextField.getText(),
									op.getText(), null, null, null });
					stockQueryDialog.setVisible(true);
				}
			}
		}
		if (oldflowLog == null) {
			tempFlowNO = "";
			GuiUtils.toolLeftBelowTips(suggestTextField, "你选的退货货物好像没有出售过,请重新输入正确的货物类型和货号!");
			return null;
		}

		StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();

		double flow_shul = flowLogMoudle.sumFlowAmount(t1, op.getText(),
				Constant.FLOW_TYPE_SELL);
		if (Double.parseDouble(t2) > flow_shul) {
			GuiUtils.toolTips(textField, "输入的此退货数量大于实际销售数量!");
			return null;
		}
		double kc_tuihuoshul = stockMoudle.sumStockGuKeTuiHuoAmount(t1, op
				.getText());
		double tt = kc_tuihuoshul + Double.parseDouble(t2);
		if (tt > flow_shul) {
			GuiUtils.toolTips(textField, "输入的此退货数量大于实际销售数量!");
			return null;
		}

		double th = flowLogMoudle.sumFlow(t1, Constant.FLOW_TYPE_TH)
				+ Double.parseDouble(t2);

		double s1 = stockMoudle.sumStockAmount(t1);
		if (th > s1) {
			GuiUtils.toolTips(textField, "退货数量超出库存数量!");
			return null;
		}

		Flowlog flowlog = new Flowlog();
		String id = String.valueOf(UUID.randomUUID().toString().replaceAll("-",
				""));
		flowlog.setFlowno(id);
		flowlog.setCatno(t1);
		flowlog.setSellprice(selltemp);
		flowlog.setLrprice(oldflowLog.getCostprice().subtract(sell).multiply(
				new BigDecimal(Double.parseDouble(t2))));
		flowlog.setType(oldflowLog.getType());
		flowlog.setDate(DateHelper.getNowDateTime());
		flowlog.setCostprice(oldflowLog.getCostprice());
		flowlog.setRecorddate(DateHelper.getNowDateTime());
		flowlog.setStockname(oldflowLog.getStockname());
		flowlog.setFlowflag(Constant.FLOW_TYPE_TH);
		flowlog.setAmount(Double.parseDouble(t2));
		flowlog.setRemarks(textField_2.getText().trim());
		flowlog.setUserId(SysEnv.getInstance().getLoginUserId());
		flowlog.setStockId(oldflowLog.getStockId());
		return flowlog;
	}

	/**
	 * 顾客退货流程 销售流水表中增加一条，然后价格为负数，库存中增加退货流水， 类型为顾客退货。
	 * 删除顾客退货流水，删除退货流水，删除库存退货流水。
	 * 
	 * @param flowlog
	 */
	public void setFormData(Flowlog flowlog) {
		StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
		Stock stock = stockMoudle.getStockByID(flowlog.getStockId());
		if (stock == null) {
			List stockList = stockMoudle.getStockSuggest(flowlog.getCatno(), flowlog.getType(), 2);
			if(stockList != null &&stockList.size()>0) {
				stock = (Stock)stockList.get(0);
			}
		}
		
		if(stock !=null) {
		      double sy = stock.getSyamount() + flowlog.getAmount();
		      if (sy > stock.getAmount()) {
		    	  stock.setAmount(sy);
		      }
		      stock.setSyamount(stock.getSyamount() + flowlog.getAmount());
		      stockMoudle.updateStock(stock);	
		}
		Stock tuihuoStock = new  Stock();
		tuihuoStock.setId(stock==null?flowlog.getFlowno():stock.getId());
		tuihuoStock.setCatno(flowlog.getCatno());
		tuihuoStock.setType(flowlog.getType());
		tuihuoStock.setStockname(flowlog.getStockname());
		tuihuoStock.setRecorddate(DateHelper.getNowDateTime());
		tuihuoStock.setDate(DateHelper.getNowDateTime());
		tuihuoStock.setTotal(flowlog.getCostprice().multiply(
				BigDecimal.valueOf(flowlog.getAmount())));
		tuihuoStock.setAmount(flowlog.getAmount());
		tuihuoStock.setSyamount(flowlog.getAmount());
		tuihuoStock.setStockFlag(Constant.STCOK_TYPE_GUKETH);
		tuihuoStock.setRemarks(flowlog.getRemarks());
		tuihuoStock.setCostprice(flowlog.getCostprice());
		tuihuoStock.setSellprice(flowlog.getSellprice());
		
		FlowLogDAOImpl flowLogMoudle = DAOContentFactriy.getFlowLogDAO();
		flowlog.setAmount(flowlog.getAmount() * -1.0D);
		flowlog.setStockId(stock==null?flowlog.getFlowno():stock.getId());
		Object[] rowData = { flowlog.getFlowno(), flowlog.getCatno(),
				flowlog.getSellprice(),
				Double.valueOf(flowlog.getAmount() * -1.0D),
				flowlog.getType(), flowlog.getStockname(),flowlog.getRemarks(),stock==null?"":stock.getColor(),stock==null?"":stock.getSpecif(),
				flowlog.getDate(), "删除" };
		DefaultTableModel defaultTableModel = (DefaultTableModel) this.table
				.getModel();
		defaultTableModel.insertRow(0, rowData);
		deltail();
		stockMoudle.add(tuihuoStock);
		flowLogMoudle.add(flowlog);
		clear();
	}

	public void clear() {
		suggestTextField.setText("");
		textField.setText("");
		textField_1.setText("");
		tempFlowNO = "";
	}

	private void deltail() {
		BigDecimal flowtotalcost = new BigDecimal(0);
		double num = 0;
		DefaultTableModel defaultTableModel = (DefaultTableModel) table
				.getModel();
		int rows = defaultTableModel.getRowCount();
		for (int i = 0; i < rows; i++) {
			// 售价
			BigDecimal sell = (BigDecimal) defaultTableModel.getValueAt(i, 2);
			// 数量
			Double amounts = (Double) defaultTableModel.getValueAt(i, 3);
			BigDecimal amount = BigDecimal.valueOf(amounts);
			// 退货总金额
			flowtotalcost = (sell.multiply(amount)).add(flowtotalcost);

			num = num + amounts;
		}
		label_6.setText(flowtotalcost.toString());
		label_8.setText(String.valueOf(num));

	}

}