package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.SysEnv;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.ConfigDAOImpl;
import com.fafasoft.flow.dao.impl.FlowLogDAOImpl;
import com.fafasoft.flow.dao.impl.StockDAOImpl;
import com.fafasoft.flow.pojo.Config;
import com.fafasoft.flow.pojo.Flowlog;
import com.fafasoft.flow.pojo.Option;
import com.fafasoft.flow.pojo.Stock;
import com.fafasoft.flow.ui.ActionView;
import com.fafasoft.flow.ui.LimitedDocument;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.widget.*;
import com.fafasoft.flow.util.DateHelper;
import com.fafasoft.flow.util.GuiUtils;
import com.fafasoft.flow.util.NumberUtils;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class EveryDayFlowPanel extends BaseJPanel implements LazyPageContent {
	private JTable table;
	private String isAddOrUpdate = "add";
	private JLabel xiaoshoulabel;
	private JCheckBox zHcheckBox = null;
	private JCheckBox xiaoJHcheckBox = null;
	private JLabel chengBeZJlabel;
	private JLabel liRZjlabel;
	private JXDatePicker datePicker;
	private JLabel lbshultj;
	private SuggestTextFieldJTable textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JLabel label_7;
	private JLabel label_8;
	private String tempstockID = "";
	private JLabel label_tip;
	private String tempflowNo;
	private JPanel tilepanel;
	private JTree jtree_1;
    private boolean isLoad =false;
	public EveryDayFlowPanel() {
		setLayout(new BorderLayout(0, 0));

	}

	public void initPanel() {
		ConfigDAOImpl configDAO = DAOContentFactriy.getConfigDAO();
		Config FLOW_INPUT_WAY = configDAO.getConfig(Constant.FLOW_INPUT_WAY);
		tilepanel = new JPanel();
		long intdate = flowDanNo();
		tempflowNo = String.valueOf(intdate);
		TitledBorder flowtoalBorder = new TitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"\u6D41\u6C34\u5355\u7EDF\u8BA1  [流水单号 " + intdate + "]",
				TitledBorder.LEADING, TitledBorder.TOP, null, null);
		tilepanel.setBorder(flowtoalBorder);
		tilepanel.setPreferredSize(new Dimension(10, 80));
		add(tilepanel, BorderLayout.NORTH);
		tilepanel.setLayout(null);
		JLabel label = new JLabel("数量总计");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(14, 20, 58, 17);
		tilepanel.add(label);
		lbshultj = new JLabel("0");
		lbshultj.setFont(new Font("宋体", Font.BOLD, 12));
		lbshultj.setBounds(82, 20, 85, 17);
		tilepanel.add(lbshultj);
		JLabel label_2 = new JLabel("流水总额");
		label_2.setBounds(181, 20, 58, 17);
		tilepanel.add(label_2);

		xiaoshoulabel = new JLabel("0");
		xiaoshoulabel.setFont(new Font("宋体", Font.BOLD, 12));
		xiaoshoulabel.setBounds(240, 17, 116, 23);
		tilepanel.add(xiaoshoulabel);

		JLabel label_3 = new JLabel("销售日期");
		label_3.setBounds(368, 20, 58, 17);
		tilepanel.add(label_3);

		datePicker = new JXDatePicker();
		String cdate = DateHelper.getNowDateTime();
		datePicker.getEditor().setText(cdate);
		datePicker.setFont(new Font("宋体", Font.BOLD, 12));
		datePicker.getEditor().setEditable(false);
		datePicker.setFormats("yyyy-MM-dd");
		datePicker.setDate(DateHelper.currentDate());
		datePicker.setBounds(422, 17, 105, 23);
		// datePicker.getEditor().getDocument().addDocumentListener(new
		// DocumentListener() {
		//
		// @Override
		// public void changedUpdate(DocumentEvent e) {
		//
		// System.out.println("changedUpdate=="+e.getDocument());
		// }
		//
		// @Override
		// public void insertUpdate(DocumentEvent e) {
		// try {
		// System.out.println("getDocument insertUpdate=="+e.getDocument().getText(0,e.getDocument().getLength()));
		// } catch (BadLocationException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// //
		// System.out.println("insertUpdate=="+datePicker.getEditor().getText());
		//
		// }
		//
		// @Override
		// public void removeUpdate(DocumentEvent e) {
		//
		// try {
		// System.out.println("getDocument removeUpdate=="+e.getDocument().getText(0,e.getDocument().getLength()));
		// } catch (BadLocationException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		//
		// }
		//
		// });

		tilepanel.add(datePicker);

		JLabel label_1 = new JLabel("成本总计");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setFont(new Font("宋体", Font.PLAIN, 12));
		label_1.setBounds(14, 51, 58, 17);
		tilepanel.add(label_1);

		chengBeZJlabel = new JLabel("0");
		chengBeZJlabel.setFont(new Font("宋体", Font.BOLD, 12));
		chengBeZJlabel.setBounds(82, 51, 75, 17);
		tilepanel.add(chengBeZJlabel);

		JLabel label_5 = new JLabel("利润总计");

		label_5.setBounds(181, 51, 58, 17);
		tilepanel.add(label_5);

		final JButton button = new JButton("设置为一单");
		button.setToolTipText("点击此按钮保存此流水单");
		button.setMargin(new Insets(2, 2, 2, 2));
		button.setBorderPainted(true);
		button.setContentAreaFilled(false);

		button.setIconTextGap(2);
		button.setIcon(new ImageIcon(EveryDayFlowPanel.class
				.getResource("/com/fafasoft/flow/resource/image/save.gif")));
		button.setBounds(545, 17, 105, 23);
		tilepanel.add(button);

		liRZjlabel = new JLabel("0");
		liRZjlabel.setFont(new Font("宋体", Font.BOLD, 12));
		liRZjlabel.setBounds(240, 52, 116, 15);
		tilepanel.add(liRZjlabel);
		ButtonGroup bg = new ButtonGroup();
		xiaoJHcheckBox = new JCheckBox("按单品销售金额记账");
		xiaoJHcheckBox.setBounds(368, 51, 143, 23);
		tilepanel.add(xiaoJHcheckBox);
		xiaoJHcheckBox.setSelected(true);
		xiaoJHcheckBox.setToolTipText("按每个货物销售价格，系统自动计算流水总额");

		xiaoJHcheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rows = table.getRowCount();
				if (rows > 0) {
					JOptionPane.showMessageDialog(MainWindows.getInstance(),
							"现在是查看流水单状态,如果你想录入新数据\n点击设置为一单按钮录入新单", "每天流水销售",
							JOptionPane.INFORMATION_MESSAGE);
					zHcheckBox.setSelected(true);
					xiaoJHcheckBox.setSelected(false);
				} else {
					int n = JOptionPane.showConfirmDialog(
							MainWindows.getInstance(), "要切换到按单品销售金额记账?",
							"切换记账方式", JOptionPane.YES_NO_OPTION);

					if (n == JOptionPane.YES_OPTION) {
						columnAction("实际销售单价", "进货成本价", 132, true);
						sumAllRow();
						danPinSelect();// 单品
						Config config2 = new Config();
						config2.setKey(Constant.FLOW_INPUT_WAY);
						config2.setValue(String
								.valueOf(Constant.FLOW_INPUT_WAY_JINQUE));
						Config[] configs = new Config[1];
						configs[0] = config2;
						DAOContentFactriy.getConfigDAO().addConfigs(configs);
					} else {
						zHcheckBox.setSelected(true);
						xiaoJHcheckBox.setSelected(false);
					}
				}
			}
		});

		bg.add(xiaoJHcheckBox);
		zHcheckBox = new JCheckBox("按单笔流水总额记账");
		zHcheckBox.setBounds(517, 51, 143, 23);
		tilepanel.add(zHcheckBox);
		zHcheckBox.setToolTipText("按每笔销售的总账，忽略每个货物的销售价格");
		zHcheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rows = table.getRowCount();
				if (rows > 0) {
					JOptionPane.showMessageDialog(MainWindows.getInstance(),
							"现在是查看流水单状态,如果你想录入新数据\n点击设置为一单按钮录入新单", "每天流水销售",
							JOptionPane.INFORMATION_MESSAGE);
					zHcheckBox.setSelected(false);
					xiaoJHcheckBox.setSelected(true);
				} else {
					int n = JOptionPane.showConfirmDialog(
							MainWindows.getInstance(), "要切换到单笔流水总额记账?",
							"切换记账方式", JOptionPane.YES_NO_OPTION);

					if (n == JOptionPane.YES_OPTION) {
						columnAction("销售总额", "成本总额", 0, false);
						sumAllRow();
						danbiSelect();// 单笔
						Config config2 = new Config();
						config2.setKey(Constant.FLOW_INPUT_WAY);
						config2.setValue(String
								.valueOf(Constant.FLOW_INPUT_WAY_PL));
						Config[] configs = new Config[1];
						configs[0] = config2;
						DAOContentFactriy.getConfigDAO().addConfigs(configs);
					} else {
						zHcheckBox.setSelected(false);
						xiaoJHcheckBox.setSelected(true);
					}
				}
			}
		});

		bg.add(zHcheckBox);

		button.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				button.setBorderPainted(true);
			}

			public void mouseClicked(MouseEvent e) {
				submmit();// 结单
			}
		});

		final JScrollPane scrollPane_1 = new JScrollPane(
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_1.setAutoscrolls(true);
		scrollPane_1.setPreferredSize(new Dimension(120, 2));
		jtree_1 = getXTree();
		scrollPane_1.setViewportView(jtree_1);
		add(scrollPane_1, BorderLayout.EAST);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "流水明细列表 [鼠标右键选中行删除选中行数据]",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setViewportBorder(new EmptyBorder(1, 0, 0, 0));
		add(scrollPane, BorderLayout.CENTER);

		table = new JTable();

		table.getTableHeader().setReorderingAllowed(false);
		table.setBorder(new EmptyBorder(1, 0, 0, 0));

		DefaultTableModel defaultTableModel = new DefaultTableModel(null,
				new String[] { "stockid", "flowid", "类型", "货号", "销售数量",
						"实际销售单价", "进货成本价", "名称", "颜色", "规格", "流水合计", "成本合计",
						"利润合计" }) {
			Class[] columnTypes = new Class[] { String.class, String.class,
					Object.class, String.class, Double.class, Double.class,
					Double.class, String.class, String.class, String.class,
					String.class, String.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, true,
					true, true, true, true, true, true, true, false, false,
					false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

		};

		table.setComponentPopupMenu(new TablePopupMenu());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		table.setModel(defaultTableModel);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(0);
		table.getColumnModel().getColumn(1).setMinWidth(0);
		table.getColumnModel().getColumn(1).setMaxWidth(0);
		// 类型
		table.getColumnModel().getColumn(2).setPreferredWidth(190);
		// 货号
		table.getColumnModel().getColumn(3).setPreferredWidth(190);
		// 销售数量
		table.getColumnModel().getColumn(4).setPreferredWidth(140);
		table.getColumnModel().getColumn(5).setPreferredWidth(162);
		table.getColumnModel().getColumn(6).setPreferredWidth(132);
		table.getColumnModel().getColumn(7).setPreferredWidth(132);
		table.getColumnModel().getColumn(10).setPreferredWidth(132);
		table.getColumnModel().getColumn(11).setPreferredWidth(132);
		DefaultTableCellRenderer fontColor = new DefaultTableCellRenderer() {
			public void setValue(Object value) { // 重写setValue方法，从而可以动态设置列单元字体颜色
				BigDecimal bigDecimal = new BigDecimal(String.valueOf(value));
				if (bigDecimal.compareTo(new BigDecimal(0)) == -1) {
					setForeground(Color.green);
				} else {
					setForeground(Color.red);

				}
				setText((value == null) ? "" : value.toString());
			}
		};
		table.getColumnModel().getColumn(12).setPreferredWidth(132);
		table.getColumn("利润合计").setCellRenderer(fontColor);
		final JTree tree = getJTree();

		final JTreeComboBox jtreeComboBox = new JTreeComboBox(tree, false);//

		jtreeComboBox.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTree tree = getJTree();
				jtreeComboBox.removeAll();
				jtreeComboBox.setTree(tree);
				jtreeComboBox.repaint();
				jtreeComboBox.updateUI();
			}
		});

		table.getColumnModel().getColumn(2)
				.setCellRenderer(new ComboCellRenderer());
		table.getColumnModel().getColumn(2)
				.setCellEditor(new TreeComboBoxCellEditor(jtreeComboBox));
		table.getColumnModel()
				.getColumn(3)
				.setCellEditor(
						new CustomComponentCellEditor2(new GoodsNoComponent()));

		((DefaultCellEditor) table.getDefaultEditor(Double.class))
				.setClickCountToStart(1);
		table.getColumnModel().getColumn(4)
				.setCellRenderer(new DoubleCellRenderer());
		table.getColumnModel().getColumn(4)
				.setCellEditor(new CustomCellEditor(new JTextField()));
		table.getColumnModel().getColumn(5)
				.setCellRenderer(new DoubleCellRenderer());
		table.getColumnModel().getColumn(5)
				.setCellEditor(new CustomCellEditor(new JTextField()));
		table.getColumnModel().getColumn(6)
				.setCellRenderer(new DoubleCellRenderer());
		table.getColumnModel().getColumn(6)
				.setCellEditor(new CustomCellEditor(new JTextField()));
		table.getColumnModel().getColumn(7)
				.setCellEditor(new CustomCellEditor(new JTextField()));
		table.getColumnModel().getColumn(8)
				.setCellEditor(new CustomCellEditor(new JTextField()));
		table.getColumnModel().getColumn(9)
				.setCellEditor(new CustomCellEditor(new JTextField()));

		table.setRowHeight(30);

		scrollPane.setViewportView(table);
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"),
				"\u5F55\u5165\u9500\u552E\u6D41\u6C34", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_2.setPreferredSize(new Dimension(10, 90));
		add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(null);

		JLabel label_4 = new JLabel("货号");
		label_4.setBounds(18, 38, 29, 15);
		panel_2.add(label_4);
		textField = new SuggestTextFieldJTable(new StockSuggestDataJTableImpl(
				true), new SuggestTextFieldJTable.MUIManager() {
			public void updateView(String[] args) {
				if (args != null && args.length > 0) {
					tempstockID = args[0];
					textField_1.requestFocus();
				} else {
					tempstockID = "";
				}
			}
		}, 500, new SuggestTextFieldJTable.MUIKeyEvent() {
			public void vkEnterENTER() {
				textField_1.requestFocus();
			}
		});
		textField.setFont(new Font("宋体", Font.BOLD, 12));

		textField.setColumns(10);
		ImageIcon image = new ImageIcon(
				SellHelperPanel.class
						.getResource("/com/fafasoft/flow/resource/image/zoom-s.png"));
		JTextFieldWithButton textFieldWithButton = new JTextFieldWithButton(
				textField, image);

		textFieldWithButton.setBounds(49, 33, 161, 25);
		textFieldWithButton.addButtonActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StockQueryDialog stockQueryDialog = new StockQueryDialog(
						MainWindows.getInstance(), "查询货物", true, true,
						new ActionView() {

							public void actionView(Object[] arg) {
								if (arg != null && arg.length > 0) {
									tempstockID = String.valueOf(arg[0]);
									textField.setText(String.valueOf(arg[1]));
									textField_1.requestFocus();
								}
							}
						}, null);
				stockQueryDialog.setVisible(true);

			}
		});
		panel_2.add(textFieldWithButton);
		JLabel label_6 = new JLabel("数量");
		label_6.setBounds(220, 38, 34, 15);
		panel_2.add(label_6);

		textField_1 = new JTextField("1");
		String dubString = "1234567890.";
		textField_1.setDocument(new LimitedDocument(20, dubString));
		textField_1.setText("1");
		textField_1.setFont(new Font("宋体", Font.BOLD, 12));
		textField_1.setBounds(251, 33, 66, 25);
		panel_2.add(textField_1);
		textField_1.setColumns(10);

		label_7 = new JLabel("售货单价");
		label_7.setBounds(329, 38, 54, 15);
		panel_2.add(label_7);

		textField_2 = new JTextField("0");
		textField_2.setDocument(new LimitedDocument(20, dubString));
		textField_2.setText("0.00");
		textField_2.setFont(new Font("宋体", Font.BOLD, 12));
		textField_2.setBounds(382, 33, 66, 25);
		panel_2.add(textField_2);
		textField_2.setColumns(10);

		label_8 = new JLabel("成本总额");
		label_8.setBounds(460, 38, 54, 15);
		panel_2.add(label_8);

		textField_3 = new JTextField("0");
		textField_3.setDocument(new LimitedDocument(20, dubString));
		textField_3.setText("0.00");
		textField_3.setFont(new Font("宋体", Font.BOLD, 12));
		textField_3.setBounds(515, 33, 66, 25);
		panel_2.add(textField_3);
		textField_3.setColumns(10);

		label_tip = new JLabel("");
		label_tip.setBounds(20, 63, 502, 21);
		panel_2.add(label_tip);

		if (FLOW_INPUT_WAY != null) {
			if (FLOW_INPUT_WAY.getValue()
					.equals(Constant.FLOW_INPUT_WAY_JINQUE)) {
				zHcheckBox.setSelected(false);
				xiaoJHcheckBox.setSelected(true);
				label_7.setText("售货单价");
				textField_3.setVisible(false);
				label_8.setVisible(false);
				columnAction("实际销售单价", "进货成本价", 132, true);
				label_tip.setText("输入销售单价后按回车键提交");
			} else {
				zHcheckBox.setSelected(true);
				xiaoJHcheckBox.setSelected(false);
				label_7.setText("销售总额");
				textField_3.setVisible(true);
				label_8.setVisible(true);
				columnAction("销售总额", "成本总额", 0, false);
				label_tip.setText("输入成本总额后按回车键提交");
			}
		} else {
			zHcheckBox.setSelected(false);
			xiaoJHcheckBox.setSelected(true);
			label_7.setText("售货单价");
			textField_3.setVisible(false);
			label_8.setVisible(false);
			columnAction("实际销售单价", "进货成本价", 132, true);
			label_tip.setText("输入销售单价后按回车键提交");
		}
		AbstractAction enterForward = new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				final String vno = textField.getText();
				if (vno.trim().length() == 0) {
					GuiUtils.toolTips(textField, "请输入货物号码", true);
					return;
				}
				final String shul = textField_1.getText();
				if (shul.trim().length() == 0 || Double.parseDouble(shul) == 0) {
					GuiUtils.toolTips(textField_1, "请输入销售货物数量", true);
					return;
				}
				if (tempflowNo == null && table.getRowCount() > 0) {
					JOptionPane
							.showMessageDialog(
									MainWindows.getInstance(),
									"现在是查看全部流水单状态,如果你想录入新数据\n您可以选择一个流水单或者点击设置为一单按钮录入新单",
									"每天流水销售", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (table.getRowCount() == 0 && tempflowNo == null) {
					long intdate = flowDanNo();
					tempflowNo = String.valueOf(intdate);
					resetTitle(tempflowNo);
				}
				Stock stock = null;
				final StockDAOImpl stockDAO = DAOContentFactriy.getStockDAO();
				if (tempstockID.trim().length() > 0) {
					stock = stockDAO.getStockByID(tempstockID.trim());
					sigleStock(stock);
				} else {
					List list = stockDAO.getStockByCatNo(vno, 0, 100);
					if (list == null || list.size() == 0) {
						StockPropertiesDialog stockPropertiesDialog = new StockPropertiesDialog(
								MainWindows.getInstance(), "补填货物属性", vno,
								xiaoJHcheckBox.isSelected(), new ActionView() {
									public void actionView(Object[] arg) {

										double danjia = NumberUtils
												.dormatDouble(textField_2
														.getText());
										double shuliang = NumberUtils
												.dormatDouble(shul);
										double chengben = NumberUtils
												.dormatDouble(String
														.valueOf(arg[2]));

										Stock stock = new Stock();
										stock.setSyamount(shuliang * -1);
										stock.setAmount(shuliang * -1);
										stock.setCatno(vno);
										stock.setType(String.valueOf(arg[1]));
										stock.setDate(DateHelper
												.getNowDateTime());
										stock.setSpecif(String.valueOf(arg[4]));
										stock.setColor(String.valueOf(arg[3]));
										stock.setStockname(String
												.valueOf(arg[0]));
										stock.setStockFlag(Constant.STCOK_TYPE_JINHUO);
										stock.setSellprice(BigDecimal
												.valueOf(danjia));
										stock.setCostprice(BigDecimal
												.valueOf(chengben));
										stock.setTotal(BigDecimal
												.valueOf(chengben * shuliang
														* -1));
										stock.setRecorddate(DateHelper
												.getNowDateTime());
										stock.setSuppliers("");
										stock.setGkThamount(0);
										stockDAO.add(stock);
										sigleStock(stock);
									}
								});
						stockPropertiesDialog.setVisible(true);
					} else {
						if (list.size() == 1) {
							sigleStock((Stock) list.get(0));
						} else {
							StockListDialog stockListDialog = new StockListDialog(
									MainWindows.getInstance(), "选择一个出售的货物",
									vno, true, list, "销售", new ActionView() {
										public void actionView(Object[] arg) {
											if (arg != null && arg.length > 0) {
												Stock stock = stockDAO.getStockByID(String
														.valueOf(arg[0]));
												sigleStock(stock);

											}
										}
									});
							stockListDialog.setVisible(true);
						}
					}
				}

			}
		};
		AbstractAction moveForward = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				boolean ifNext = true;
				if (table.isEditing()) {
					ifNext = table.getCellEditor().stopCellEditing();
				}
				if (ifNext) {
					int rowCount = table.getRowCount();
					int colCount = table.getColumnCount();

					int selRow = table.getSelectedRow();
					int selCol = table.getSelectedColumn();
					int nextcol = 0;

					do {
						if (selCol + nextcol == colCount - 1) // if last column
						{
							if (selRow == rowCount - 1) // if last row
							{
								selCol = -1;
								selRow = 0;
								selRow++;
							} else {
								selCol = -1;
								selRow++;
							}
						}
						nextcol = nextcol + 1;

					} while (!(table.getModel().isCellEditable(selRow, selCol
							+ nextcol))); // find next editable cell

					table.changeSelection(selRow, selCol + nextcol, true, false);
					table.editCellAt(selRow, selCol + nextcol);
				}
			}
		};
		textField_2.getInputMap().put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterForward");
		textField_2.getActionMap().put("enterForward", enterForward);
		textField_3.getInputMap().put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterForward");
		textField_3.getActionMap().put("enterForward", enterForward);

		table.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0),
				"moveForward");
		table.getActionMap().put("moveForward", moveForward);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				reload();
			}
		});
	}

	private void clear() {
		textField.setText("");
		textField_1.setText("1");
		textField_2.setText("0.00");
		textField_3.setText("0.00");
		tempstockID = "";
		sumAllRow();
	}

	private void sigleStock(Stock stock) {
		int rowCount = table.getRowCount();
		if (rowCount == 0) {
			DefaultTreeModel model = (DefaultTreeModel) jtree_1.getModel();
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) model
					.getRoot();
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(
					String.valueOf(tempflowNo));

			model.insertNodeInto(newNode, root, root.getChildCount());
			jtree_1.expandPath(new TreePath(root));
		}
		String vno = textField.getText();
		String shul = textField_1.getText();
		double shuliang = NumberUtils.dormatDouble(shul);

		if (xiaoJHcheckBox.isSelected() && shuliang > stock.getSyamount()) {
			GuiUtils.toolTips(textField_1, "", true);
			int response = JOptionPane.showConfirmDialog(this,
					"输入的数量大于您选择出售货物的剩余数量\n如果库存中中存在相同货号自动消减吗？?", "每天流水销售",
					JOptionPane.YES_NO_OPTION);
			if (response == JOptionPane.NO_OPTION) {
				return;
			}
		}

		String flowid = UUID.randomUUID().toString().replaceAll("-", "");
		double danjia = NumberUtils.dormatDouble(textField_2.getText());

		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

		if (xiaoJHcheckBox.isSelected()) {// 按单品销售金额记账
			double lr = (danjia * shuliang)
					- (shuliang * stock.getCostprice().doubleValue());
			Object[] rowData = new Object[] {
					stock.getId(),// "stockid"
					flowid,// "flowid"
					stock.getType(),// "类型"
					vno,// "货号"
					shuliang,// "销售数量"
					danjia,// "实际销售单价"
					stock.getCostprice().doubleValue(),// "进货成本价"
					stock.getStockname(),// "名称"
					stock.getColor(),// "颜色"
					stock.getSpecif(),// "规格"
					danjia * shuliang,// "流水合计"
					shuliang * stock.getCostprice().doubleValue(),
					String.format("%.2f", lr) // "利润合计"
			};
			tableModel.insertRow(0, rowData);
			addRow(stock, flowid);
			clear();
		} else if (zHcheckBox.isSelected()) {// 按每笔销售的总账，忽略每个货物的销售价格
			double chengbnze = NumberUtils.dormatDouble(textField_3.getText());
			Object[] rowData = new Object[] { stock.getId(),// "stockid"
					flowid,// "flowid"
					stock.getType(),// "类型"
					vno,// "货号"
					shuliang,// "销售数量"
					danjia,// "实际销总额"
					chengbnze,// "成本总额""
					stock.getStockname(),// "名称"
					stock.getColor(),// "颜色"
					stock.getSpecif(),// "规格"
					danjia,// "流水合计"
					chengbnze, String.format("%.2f", danjia - chengbnze),// "利润合计"
			};
			tableModel.insertRow(0, rowData);
			addRow(stock, flowid);
			clear();
		}
		
	}

	private void columnAction(String xiaosCname, String chengbName, int width,
			boolean resizable) {
		table.getColumnModel().getColumn(5).setHeaderValue(xiaosCname);
		table.getColumnModel().getColumn(6).setHeaderValue(chengbName);
		table.getColumnModel().getColumn(10).setResizable(resizable);
		if (width > 0) {
			table.getColumnModel().getColumn(10).setMinWidth(10);
			table.getColumnModel().getColumn(10).setMaxWidth(111111);
		} else {
			table.getColumnModel().getColumn(10).setMinWidth(width);
			table.getColumnModel().getColumn(10).setMaxWidth(width);
		}
		table.getColumnModel().getColumn(10).setPreferredWidth(width);
		table.repaint();
		table.updateUI();
	}

	public void sumAllRow() {
		int r = table.getRowCount();
		boolean isadd = true;
		double shultj = 0;// 成本总计
		double cbzj = 0;// 成本总计
		double lzj = 0;// 利润总计
		double liushuizj = 0;// 利润总计
		for (int i = 0; i < r; i++) {
			String type = String.valueOf(table.getValueAt(i, 2));
			String catno = String.valueOf(table.getValueAt(i, 3));
			Double shul = Double.parseDouble(String.valueOf(table.getValueAt(i,
					4)));
			double xiaosjia = NumberUtils.dormatDouble(String.valueOf(String
					.valueOf(table.getValueAt(i, 5))));
			if (type == null || "".equals(type) || "null".equals(type)
					|| catno == null || "".equals(catno.trim())
					|| "null".equals(catno)) {
				isadd = false;
			} else {
				Double jinhJia = NumberUtils.dormatDouble(String.valueOf(String
						.valueOf(table.getValueAt(i, 6)))); // 进货价格

				if (xiaoJHcheckBox.isSelected()) {// 按单品销售金额记账
					Double xiaoshjiaze = Double.parseDouble(String
							.valueOf(table.getValueAt(i, 5))); // 销售价格
					liushuizj = liushuizj + (shul * xiaoshjiaze);
					cbzj = cbzj + (shul * jinhJia);
				} else {
					cbzj = cbzj + jinhJia;
					liushuizj = liushuizj + xiaosjia;
				}
				shultj = shultj + shul;
			}
		}
		lbshultj.setText(String.format("%.2f", shultj));
		xiaoshoulabel.setText(String.format("%.2f", liushuizj));
		// 成本总计
		chengBeZJlabel.setText(String.format("%.2f", cbzj));
		lzj = liushuizj - cbzj;
		// 利润总计
		if (lzj < 0) {
			liRZjlabel.setForeground(Color.green);
		} else {
			liRZjlabel.setForeground(Color.red);

		}
		liRZjlabel.setText(String.format("%.2f", lzj));
	}

	private boolean checkRow(int selectedRow) {
		String stockid = String.valueOf(table.getValueAt(selectedRow, 0));
		String type = String.valueOf(table.getValueAt(selectedRow, 2));
		String catno = String.valueOf(table.getValueAt(selectedRow, 3));
		final String tempColor = String.valueOf(table
				.getValueAt(selectedRow, 8));
		final String tempSpecif = String.valueOf(table.getValueAt(selectedRow,
				9));
		// 按单品销售金额记账
		Object danjiaOb = table.getValueAt(selectedRow, 5);

		double danjia = NumberUtils.dormatDouble(String.valueOf(danjiaOb));
		double shul = NumberUtils.dormatDouble(String.valueOf(table.getValueAt(
				selectedRow, 4)));

		double jinhuojia = NumberUtils.dormatDouble(String.valueOf(table
				.getValueAt(selectedRow, 6)));
		if (type == null || "".equals(type) || "null".equals(type)) {

			GuiUtils.toolTipsForTable(table, selectedRow, 2, "请选择销售货物类型", true);
			return false;
		}
		StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
		Stock stockOld = stockMoudle.getStockByID(stockid);
		if (catno != null && !"".equals(catno.trim())
				&& !"null".equals(catno.trim())) {
			if (stockOld != null) {
				if (!catno.equals(stockOld.getCatno())
						|| !type.equals(stockOld.getType())) {
					List list = stockMoudle.getStockByNoAndType(catno, type);
					if (!list.isEmpty()) {
						Stock stock = (Stock) list.get(0);
						table.setValueAt(stock.getId(), selectedRow, 0);
						table.setValueAt(stock.getCostprice().doubleValue(),
								selectedRow, 6);
						table.setValueAt(stock.getStockname(), selectedRow, 7);
						table.setValueAt(stock.getColor(), selectedRow, 8);
						table.setValueAt(stock.getSpecif(), selectedRow, 9);
					}
				} else if (!tempColor.equals(stockOld.getCatno())
						|| !tempSpecif.equals(stockOld.getType())) {

				}
			}
			// table.editCellAt(selectedRow, 4);
		} else {

			GuiUtils.toolTipsForTable(table, selectedRow, 3, "请填写销售货号", true);
			return false;
		}

		if (xiaoJHcheckBox.isSelected()) {// 按单品销售金额记账
			double liuzj = danjia * shul;
			double lrzj = liuzj - (jinhuojia * shul);
			table.setValueAt(liuzj, selectedRow, 10);
			table.setValueAt(jinhuojia * shul, selectedRow, 12);
			table.setValueAt(lrzj, selectedRow, 12);
		} else if (zHcheckBox.isSelected()) {
			double lrzj = danjia - jinhuojia;
			table.setValueAt(jinhuojia, selectedRow, 12);
			table.setValueAt(lrzj, selectedRow, 12);
		}
		String flowid = String.valueOf(table.getValueAt(selectedRow, 1));
		if (flowid == null || "".equals(flowid.trim())
				|| flowid.equalsIgnoreCase("null")) {
			flowid = String.valueOf(UUID.randomUUID().toString()
					.replaceAll("-", ""));
			table.setValueAt(flowid, selectedRow, 1);
		}

		modifyRow(selectedRow);
		return true;
	}

	private void addRow(Stock stock, String flowid) {
		String stockId = stock.getId();
		String type = stock.getType();

		String catno = stock.getCatno();
		String vno = textField.getText();
		String shul = textField_1.getText();

		double amount = NumberUtils.dormatDouble(shul);

		String stockname = stock.getStockname();
		String tempColor = stock.getColor();
		String tempSpecif = stock.getSpecif();
		Flowlog flowlog = new Flowlog();
		double selljia = NumberUtils.dormatDouble(textField_2.getText());

		flowlog.setFlowno(flowid);
		flowlog.setCatno(catno);
		flowlog.setAmount(amount);
		flowlog.setType(type);
		if (stockId == null || stockId.trim().length() == 0) {
			stockId = UUID.randomUUID().toString().replaceAll("-", "");
		}
		flowlog.setStockId(stockId);

		flowlog.setRecorddate(DateHelper.getNowDateTime());
		flowlog.setStockname(stockname);
		flowlog.setUserId(SysEnv.getInstance().getLoginUserId());
		flowlog.setDate(datePicker.getEditor().getText());
		flowlog.setTempColor(tempColor);
		flowlog.setTempSpecif(tempSpecif);
		if (zHcheckBox.isSelected()) {// 按单笔流水总额记账
			double chengbnze = NumberUtils.dormatDouble(textField_3.getText());
			flowlog.setFlowflag(Constant.FLOW_TYPE_PLSELL);
			flowlog.setCostprice(BigDecimal.valueOf(chengbnze));
			flowlog.setSellprice(BigDecimal.valueOf(selljia));
			flowlog.setLrprice(BigDecimal.valueOf(selljia - chengbnze));
		} else if (xiaoJHcheckBox.isSelected()) {// 按单品销售金额记账
			flowlog.setFlowflag(Constant.FLOW_TYPE_SELL);
			double costprice = stock.getCostprice().doubleValue();
			flowlog.setCostprice(BigDecimal.valueOf(costprice));
			flowlog.setSellprice(BigDecimal.valueOf(selljia));
			double sellLr = (selljia * amount) - (costprice * amount);
			flowlog.setLrprice(BigDecimal.valueOf(sellLr));
		}
		flowlog.setSerialNumber(Long.parseLong(tempflowNo));
		FlowLogDAOImpl flowLogMoudle = DAOContentFactriy.getFlowLogDAO();
		StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
		flowLogMoudle.add(flowlog);

		stockMoudle.updateSyAmount(flowlog.getStockId(), flowlog.getAmount(),
				"-");

	}

	private void modifyRow(int nRow) {
		String flowNo = String.valueOf(table.getValueAt(nRow, 1));
		FlowLogDAOImpl flowLogMoudle = DAOContentFactriy.getFlowLogDAO();
		final Flowlog flowLog = flowLogMoudle.getFlowByflowno(flowNo);
		if (flowLog != null) {
			String stockId = String.valueOf(table.getValueAt(nRow, 0));
			final String type = String.valueOf(table.getValueAt(nRow, 2));
			final String catno = String.valueOf(table.getValueAt(nRow, 3));
			final String tempColor = String.valueOf(table.getValueAt(nRow, 8));
			final String tempSpecif = String.valueOf(table.getValueAt(nRow, 9));
			String stockname = String.valueOf(table.getValueAt(nRow, 7));
			final double amount = NumberUtils.dormatDouble(String.valueOf(table
					.getValueAt(nRow, 4)));

			final double selljia = NumberUtils.dormatDouble(String
					.valueOf(table.getValueAt(nRow, 5)));

			final double costprice = NumberUtils.dormatDouble(String
					.valueOf(table.getValueAt(nRow, 6)));

			final StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
			flowLogMoudle.deleteByFlowno(flowNo);

			Stock stock = stockMoudle.getStockByID(flowLog.getStockId());
			// if(!stock.getCatno().equals(catno) ||
			// !stock.getType().equals(type) ||
			// !stock.getColor().equals(tempColor)
			// || !stock.getSpecif().equals(tempSpecif)) {
			// List list = stockMoudle.getStock(catno, type, tempColor,
			// tempSpecif);
			// if(list != null && list.size() ==1) {
			// stock=(Stock)list.get(0);
			// }
			// if(stock != null) {
			// table.setValueAt(stock.getId(), nRow, 0);
			// }
			// }

			if (stock != null) {
				stockname = stock.getStockname();
			} else {
				stock = new Stock();
				stock.setId(String.valueOf(UUID.randomUUID().toString()
						.replaceAll("-", "")));
				stock.setCatno(catno);
				stock.setType(type);
			}

			flowLog.setAmount(amount);
			flowLog.setCatno(catno);
			flowLog.setStockId(stock.getId());
			flowLog.setCostprice(BigDecimal.valueOf(costprice));
			flowLog.setSellprice(BigDecimal.valueOf(selljia));
			flowLog.setRecorddate(DateHelper.getNowDateTime());
			flowLog.setStockname(stockname);
			flowLog.setUserId(SysEnv.getInstance().getLoginUserId());
			flowLog.setDate(datePicker.getEditor().getText());
			flowLog.setTempColor(tempColor);
			flowLog.setTempSpecif(tempSpecif);
			flowLog.setType(stock.getType());
			if (flowLog.getFlowflag().equals(Constant.FLOW_TYPE_PLSELL)) {// 按单笔流水总额记账
				flowLog.setLrprice(BigDecimal.valueOf(selljia - costprice));
			} else if (flowLog.getFlowflag().equals(Constant.FLOW_TYPE_SELL)) {// 按单品销售金额记账
				double sellLr = (selljia * amount) - (costprice * amount);
				flowLog.setLrprice(BigDecimal.valueOf(sellLr));
			}
			flowLogMoudle.add(flowLog);

			if (stockId.equals(stock.getId())) {
				if (flowLog.getAmount() != amount
						|| selljia != flowLog.getSellprice().doubleValue()
						|| costprice != flowLog.getCostprice().doubleValue()) {
					double flowNum = flowLog.getAmount() - amount;
					if (flowNum > 0) {
						stockMoudle.updateSyAmount(flowLog.getStockId(),
								flowNum, "-");
					} else {
						stockMoudle.updateSyAmount(flowLog.getStockId(),
								flowNum * -1, "+");
					}
				}
			} else {
				System.out.println("出现货号不同的修改");
				stockMoudle.updateSyAmount(stock.getId(), amount, "+");
				stockMoudle.updateSyAmount(stockId, amount, "-");
			}

			// if (type.equals(stock.getType()) &&
			// catno.equals(stock.getCatno())) {
			//
			// } else {
			// List list = stockMoudle.getStock(catno, type, tempColor,
			// tempSpecif);
			// if(stockId.equals(stock.getId())){
			// if (list != null && list.size() == 1) {
			// stockMoudle.updateSyAmount(flowLog.getStockId(), flowLog
			// .getAmount(), "-");
			// }
			// }else {
			//
			// }
			// }
			// SwingUtilities.invokeLater(new Runnable() {
			// public void run() {
			//
			// }
			// });
		}
	}

	private void submmit() {
		int r = table.getRowCount();
		if (r > 0) {
			int response = JOptionPane.showConfirmDialog(this, "确认结单,建立新的一单吗?",
					"每天流水销售", JOptionPane.YES_NO_OPTION);
			if (response == JOptionPane.YES_OPTION) {
				initView();
				isAddOrUpdate = "add";
			}
		} else {
			JOptionPane.showMessageDialog(MainWindows.getInstance(),
					"您还没有录入销售单相关数据,无法结单", "每天流水销售",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void initView() {

		long intdate = flowDanNo();
		tempflowNo = String.valueOf(intdate); // 初始化货重新录入新单
		resetTitle(String.valueOf(intdate));
		// 成本总计
		chengBeZJlabel.setText("0");
		xiaoshoulabel.setText("0");
		// 利润总计
		liRZjlabel.setText("0");
		DefaultTableModel defaultTableModel = (DefaultTableModel) table
				.getModel();
		defaultTableModel.setRowCount(0);
		sumAllRow();
	}

	private void resetTitle(String snumber) {
		TitledBorder titledBorder = (TitledBorder) tilepanel.getBorder();
		if (snumber == null) {
			titledBorder.setTitle("\u6D41\u6C34\u5355\u7EDF\u8BA1  [ 全部流水单 ]");
		} else {
			titledBorder.setTitle("\u6D41\u6C34\u5355\u7EDF\u8BA1  [流水单号 "
					+ snumber + "]");
		}
		tilepanel.setBorder(titledBorder);
		tilepanel.updateUI();
	}

	private void modify(String snumber) {
		FlowLogDAOImpl flowLogMoudle = DAOContentFactriy.getFlowLogDAO();

		List list = flowLogMoudle.getFlowlogBySnumber(snumber);
		DefaultTableModel defaultTableModel = (DefaultTableModel) table
				.getModel();
		defaultTableModel.setRowCount(0);
		double shultj = 0;// 数量总计
		double cbzj = 0;// 成本总计
		double lzj = 0;// 利润总计
		double liushuizj = 0;// 利润总计
		if (list != null && list.size() > 0) {
			int r = list.size();
			isAddOrUpdate = "modify";
			tempflowNo = snumber;
			resetTitle(snumber);
			StockDAOImpl stockDAO = DAOContentFactriy.getStockDAO();
			DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			for (int i = 0; i < r; i++) {
				Flowlog flowlog = (Flowlog) list.get(i);
				if (i == 0) {
					if (snumber == null) {
						zHcheckBox.setSelected(false);
						xiaoJHcheckBox.setSelected(false);
						columnAction("销售价或总额", "成本价或总额", 0, true);
						danPinSelect();// 单品
					} else {
						if (flowlog.getFlowflag().equalsIgnoreCase(
								Constant.FLOW_TYPE_PLSELL)) {
							danbiSelect();// 单笔
						} else {
							danPinSelect();// 单品
						}
					}
					datePicker.getEditor().setText(flowlog.getDate());
				}
				Stock stock = null;
				if (flowlog.getStockId() == null
						|| flowlog.getStockId().equalsIgnoreCase("null")) {
					stock = stockDAO.getStockByNo(flowlog.getCatno());
				} else {
					stock = stockDAO.getStockByID(flowlog.getStockId());
				}
				if (stock == null) {
					stock = new Stock();
				}
				shultj = shultj + flowlog.getAmount();
				if (flowlog.getFlowflag().equalsIgnoreCase(
						Constant.FLOW_TYPE_PLSELL)) {
					Object[] rowData = new Object[] {
							flowlog.getStockId(),
							flowlog.getFlowno(),
							flowlog.getType(),
							flowlog.getCatno(),
							flowlog.getAmount(),
							flowlog.getSellprice().doubleValue(),
							flowlog.getCostprice().doubleValue(),
							flowlog.getStockname(),
							stock.getColor(),
							stock.getSpecif(),
							flowlog.getSellprice().doubleValue(),
							flowlog.getCostprice().doubleValue(),
							flowlog.getSellprice().doubleValue()
									- flowlog.getCostprice().doubleValue() };
					cbzj = cbzj + flowlog.getCostprice().doubleValue();
					liushuizj = liushuizj
							+ flowlog.getSellprice().doubleValue();
					tableModel.insertRow(0, rowData);
				} else if (flowlog.getFlowflag().equalsIgnoreCase(
						Constant.FLOW_TYPE_SELL)) {
					Object[] rowData = new Object[] {
							flowlog.getStockId(),
							flowlog.getFlowno(),
							flowlog.getType(),
							flowlog.getCatno(),
							flowlog.getAmount(),
							flowlog.getSellprice().doubleValue(),

							flowlog.getCostprice().doubleValue(),
							flowlog.getStockname(),
							stock.getColor(),
							stock.getSpecif(),
							flowlog.getAmount()
									* flowlog.getSellprice().doubleValue(),
							flowlog.getAmount()
									* flowlog.getCostprice().doubleValue(),
							flowlog.getAmount()
									* flowlog.getSellprice().doubleValue()
									- flowlog.getAmount()
									* flowlog.getCostprice().doubleValue()

					};

					liushuizj = liushuizj
							+ (flowlog.getAmount() * flowlog.getSellprice()
									.doubleValue());
					cbzj = cbzj
							+ (flowlog.getAmount() * flowlog.getCostprice()
									.doubleValue());
					tableModel.insertRow(0, rowData);
				}
			}

			lbshultj.setText(String.format("%.2f", shultj));
			xiaoshoulabel.setText(String.format("%.2f", liushuizj));
			// 成本总计
			chengBeZJlabel.setText(String.format("%.2f", cbzj));
			lzj = liushuizj - cbzj;
			// 利润总计
			liRZjlabel.setText(String.format("%.2f", lzj));
		}
	}

	private void deleteRow() {
		int nRow = table.getSelectedRow();
		if (nRow > -1) {
			DefaultTableModel defaultTableModel = (DefaultTableModel) table
					.getModel();
			String flowNo = String.valueOf(defaultTableModel
					.getValueAt(nRow, 1));
			String stockId = String.valueOf(defaultTableModel.getValueAt(nRow,
					0));
			FlowLogDAOImpl flowLogMoudle = DAOContentFactriy.getFlowLogDAO();

			Flowlog flowLog = flowLogMoudle.getFlowByflowno(flowNo);
			String sNumber = String.valueOf(flowLog.getSerialNumber());

			if (table.getRowCount() == 1) {
				DefaultTreeModel model = (DefaultTreeModel) jtree_1.getModel();
				DefaultMutableTreeNode root = (DefaultMutableTreeNode) model
						.getRoot();
				for (int i = 0; i < root.getChildCount(); i++) {
					DefaultMutableTreeNode deNode = (DefaultMutableTreeNode) root
							.getChildAt(i);
					Object snob = deNode.getUserObject();
					if (snob instanceof String
							&& String.valueOf(snob).equals(sNumber)) {
						model.removeNodeFromParent(deNode);
						break;
					}
				}
			}
			defaultTableModel.removeRow(nRow);
			flowLogMoudle.deleteByFlowno(flowNo);
			StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
			stockMoudle.updateSyAmount(stockId, flowLog.getAmount(), "+");
			sumAllRow();
		}
	}

	private void danbiSelect() {
		zHcheckBox.setSelected(true);
		xiaoJHcheckBox.setSelected(false);
		columnAction("销售总额", "成本总额", 0, false);
		label_7.setText("销售总额");
		textField_3.setVisible(true);
		label_8.setVisible(true);
		label_tip.setText("输入成本总额后按回车键提交");
	}

	private void danPinSelect() {
		zHcheckBox.setSelected(false);
		xiaoJHcheckBox.setSelected(true);
		label_7.setText("销售单价");
		textField_3.setVisible(false);
		label_8.setVisible(false);
		label_tip.setText("输入销售单价后按回车键提交");
		columnAction("实际销售单价", "进货成本价", 132, true);
	}

	class CustomComponentCellEditor2 extends DefaultCellEditor {
		private final GoodsNoComponent component;

		public CustomComponentCellEditor2(GoodsNoComponent component) {
			super(component.field);
			setClickCountToStart(1);

			this.component = component;
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			component.field.setText(value != null ? value.toString() : "");
			return this.component;
		}
	}

	class GoodsNoComponent extends JPanel {
		public final JTextField field = new JTextField();
		protected JButton button;

		public GoodsNoComponent() {
			super(new BorderLayout(0, 0));
			button = new JButton();
			button.setIcon(new ImageIcon(
					EveryDayFlowPanel.class
							.getResource("/com/fafasoft/flow/resource/image/zoom-s.png")));
			this.add(field);
			this.add(button, BorderLayout.EAST);

			button.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					DefaultTableModel defaultTableModel = (DefaultTableModel) table
							.getModel();
					int nCol = table.getSelectedColumn();
					final int nRow = table.getSelectedRow();
					if (nCol != -1 && nRow != -1) {
						String catnotype = String.valueOf(table.getValueAt(
								nRow, 2));
						String catno = String.valueOf(table.getValueAt(nRow,
								nCol));
						StockSuggestDataJTableImpl stockSuggestDataJTableImpl = new StockSuggestDataJTableImpl();
						final JTable jTable = stockSuggestDataJTableImpl
								.getStockData(catnotype);
						if (jTable != null) {

							int size = jTable.getRowCount() + 1;
							JScrollPane tipFrame = new JScrollPane();
							tipFrame.setViewportView(jTable);
							final JPopupMenu jPopupMenu = new JPopupMenu();
							jPopupMenu.add(tipFrame);
							jPopupMenu.setFocusable(false);
							int h = (size * 20);
							tipFrame.setPreferredSize(new Dimension(340, h));

							jPopupMenu.setBorder(new TitledBorder("输入提示"));
							jPopupMenu.show(field, 0, field.getY() + 30);
							jTable.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent arg0) {
									int nRowx = jTable.getSelectedRow();
									if (nRowx > -1) {
										Object objSel = jTable.getValueAt(
												nRowx, 0);
										Object catno = jTable.getValueAt(nRowx,
												1);
										Object name = jTable.getValueAt(nRowx,
												2);
										Object guige = jTable.getValueAt(nRowx,
												5);
										Object color = jTable.getValueAt(nRowx,
												6);
										jPopupMenu.setVisible(false);
										field.setText(String.valueOf(catno));
										table.setValueAt(name, nRow, 7);
										table.setValueAt(color, nRow, 8);
										table.setValueAt(guige, nRow, 9);
										sumAllRow();
									}

									field.requestFocus();
								}
							});
						}
					}
				}
			});
			field.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {
				}

				public void focusLost(FocusEvent e) {
					int row = table.getSelectedRow();
					if (row > -1) {
						boolean dd = checkRow(row);
						if (dd) {
							sumAllRow();
						}
					}
				}

			});
		}

		protected boolean processKeyBinding(final KeyStroke ks,
				final KeyEvent e, int condition, boolean pressed) {
			if (!field.isFocusOwner() && !pressed) {
				field.requestFocusInWindow();
				SwingUtilities.invokeLater(new Runnable() {

					public void run() {
						KeyboardFocusManager.getCurrentKeyboardFocusManager()
								.redispatchEvent(field, e);
					}
				});
			}
			return super.processKeyBinding(ks, e, condition, pressed);
		}
	}

	class CustomCellEditor extends DefaultCellEditor {
		private final JTextField component;

		public CustomCellEditor(final JTextField field) {
			super(field);
			this.component = field;
			setClickCountToStart(1);
			field.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {

				}

				public void focusLost(FocusEvent e) {

					int row = table.getSelectedRow();
					if (row > -1) {
						boolean dd = checkRow(row);
						if (dd) {
							sumAllRow();
						}
					}
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			component.setText(value != null ? value.toString() : "");
			return this.component;
		}
	}

	class TreeComboBoxCellEditor extends DefaultCellEditor {
		private static final int BUTTON_WIDTH = 20;
		private JComboBox treecomboBox = null;

		public TreeComboBoxCellEditor(final JComboBox comboBox) {
			super(comboBox);
			treecomboBox = comboBox;
			comboBox.putClientProperty("JComboBox.isTableCellEditor",
					Boolean.TRUE);

			delegate = new EditorDelegate() {
				public void setValue(Object value) {
					comboBox.setSelectedItem(value);
				}

				public Object getCellEditorValue() {
					Object va = comboBox.getSelectedItem();
					if (va != null && va instanceof TreePath) {
						TreePath treePath = (TreePath) va;
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath
								.getLastPathComponent();
						va = node.getUserObject();
					}
					return va;
				}
			};
			comboBox.addActionListener(delegate);
		}
	}

	class ComboCellRenderer extends JComboBox implements TableCellRenderer {
		private final JTextField editor;

		public ComboCellRenderer() {
			super();
			setEditable(true);
			// setBorder(BorderFactory.createEmptyBorder());
			editor = (JTextField) getEditor().getEditorComponent();
			// editor.setBorder(BorderFactory.createEmptyBorder());
			editor.setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			removeAllItems();
			addItem((value == null) ? "" : value.toString());
			return this;
		}

		public boolean isOpaque() {
			Color back = getBackground();
			Component p = getParent();
			if (p != null) {
				p = p.getParent();
			} // p should now be the JTable.
			boolean colorMatch = (back != null) && (p != null)
					&& back.equals(p.getBackground()) && p.isOpaque();
			return !colorMatch && super.isOpaque();
		}
	}

	class DoubleCellRenderer extends DefaultTableCellRenderer {
		public void setValue(Object value) {
			setText(String.valueOf(value));
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			this.setValue(value);
			return this;
		}

	}

	class DeleteAction extends AbstractAction {
		public DeleteAction(String label, Icon icon) {
			super(label, icon);
		}

		public void actionPerformed(ActionEvent evt) {
			int response = JOptionPane.showConfirmDialog(
					MainWindows.getInstance(), "确定删除此条销售单数据?", "删除库存数据",
					JOptionPane.YES_NO_OPTION);
			if (response == JOptionPane.YES_OPTION) {
				deleteRow();
			}
		}
	}

	private class TablePopupMenu extends JPopupMenu {

		private static final long serialVersionUID = 1L;

		private final Action deleteAction = new DeleteAction("删除选中行", null);

		public TablePopupMenu() {
			super();
			add(deleteAction);
			super.setPopupSize(90, 25);
		}

		public void show(Component c, int x, int y) {
			int[] l = table.getSelectedRows();
			int r = table.getSelectedRow();
			if (r < 0) {
				return;
			}
			String type = String.valueOf(table.getValueAt(r, 2));
			String catno = String.valueOf(table.getValueAt(r, 3));

			double amount = NumberUtils.dormatDouble(String.valueOf(table
					.getValueAt(r, 4)));
			Object sellOb = table.getValueAt(r, 5);

			double costprice = NumberUtils.dormatDouble(String.valueOf(table
					.getValueAt(r, 6)));

			String stockname = String.valueOf(table.getValueAt(r, 7));

			if (type != null && !"".equals(type) && !"null".equals(type)
					&& catno != null && !"".equals(catno.trim())
					&& !"null".equals(catno) && amount != 0 && costprice != 0
					&& stockname.trim().length() > 0 && l != null
					&& l.length > 0) {
				deleteAction.setEnabled(true);
			} else {
				deleteAction.setEnabled(false);
			}

			super.show(c, x, y);
		}
	}

	private JTree getXTree() {
		Option op = new Option();
		op.setText("今日流水单列表");
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(op);
		final JTree tree = new JTree(top);

		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
						.getLastSelectedPathComponent();
				if (node != null) {
					Object ob = node.getUserObject();
					if (ob != null) {
						if (ob instanceof Option) {

							modify(null);
						} else {
							String tr = String.valueOf(ob);
							modify(tr);
						}
					}
				}

			}
		});

		tree.setBorder(new EmptyBorder(0, 0, 0, 0));
		tree.setRowHeight(22);

		DefaultTreeCellRenderer r = new DefaultTreeCellRenderer();
		r.setBackgroundNonSelectionColor(null);
		tree.setAutoscrolls(true);
		tree.setCellRenderer(r);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		ImageIcon openIco = new ImageIcon(
				EveryDayFlowPanel.class
						.getResource("/com/fafasoft/flow/resource/image/pers.png"));
		ImageIcon closeIco = new ImageIcon(
				EveryDayFlowPanel.class
						.getResource("/com/fafasoft/flow/resource/image/group.png"));
		r.setOpenIcon(openIco);
		r.setClosedIcon(closeIco);
		r.setLeafIcon(closeIco);

		tree.setScrollsOnExpand(true);
		tree.setComponentPopupMenu(new TreePopupMenu());
		return tree;
	}

	private void reload() {
		
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) jtree_1
				.getModel().getRoot();
		if(root.getChildCount() >0) {
			root.removeAllChildren();
			root.removeFromParent();
		}
		
		FlowLogDAOImpl flowLogMoudle = DAOContentFactriy.getFlowLogDAO();

		String date = DateHelper.getNowDateTime();
		List danBilist = flowLogMoudle.getSNumberList(date);
		if (!danBilist.isEmpty()) {
			for (int i = 0; i < danBilist.size(); i++) {
				Flowlog flow = (Flowlog) danBilist.get(i);
				DefaultMutableTreeNode category = new DefaultMutableTreeNode(
						String.valueOf(flow.getSerialNumber()));
				root.add(category);
			}
		}
		if (jtree_1.getModel().getChildCount(root) > 0) {
			jtree_1.setSelectionPath(new TreePath(root.getFirstChild()));
			jtree_1.setSelectionRow(1);
		}
		
		jtree_1.expandPath(new TreePath(root));
		
		modify(null);
		isLoad= true;
	}
public boolean isRload() {
	return isLoad;
}

	private long flowDanNo() {
		String cdate = DateHelper.getNowDateTime();
		long intdate = DAOContentFactriy.getFlowLogDAO().getMaxSNumber(cdate);
		if (intdate == 0) {
			long ndate = DateHelper.getNowDateTimeLong();
			intdate = Long.parseLong(String.valueOf(ndate) + "0001");
		} else {
			intdate = intdate + 1;
		}
		return intdate;
	}

	class TreePopupMenu extends JPopupMenu {
		private JMenuItem viewItem;
		private JMenuItem deleItem;
		private JMenuItem modifyItem;

		public TreePopupMenu() {
			viewItem = new JMenuItem(new AbstractAction(" 查看此单   ") {
				public void actionPerformed(ActionEvent e) {
					JTree tree = (JTree) getInvoker();
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
							.getLastSelectedPathComponent();
					Object ob = node.getUserObject();
					if (ob != null) {
						if (ob instanceof Option) {

							modify(null);
						} else {
							String tr = String.valueOf(ob);
							modify(tr);
						}
					}

				}
			});
			add(viewItem);
			addSeparator();
			modifyItem = new JMenuItem(new AbstractAction(" 修改此单   ") {
				public void actionPerformed(ActionEvent e) {
					JTree tree = (JTree) getInvoker();
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
							.getLastSelectedPathComponent();
					Object ob = node.getUserObject();
					if (ob != null) {
						if (ob instanceof Option) {

							modify(null);
						} else {
							String tr = String.valueOf(ob);
							modify(tr);
						}
					}
				}
			});
			add(modifyItem);
			addSeparator();
			deleItem = new JMenuItem(new AbstractAction(" 删除此单    ") {
				public void actionPerformed(ActionEvent e) {
					JTree tree = (JTree) getInvoker();
					FlowLogDAOImpl flowLogMoudle = DAOContentFactriy
							.getFlowLogDAO();
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
							.getLastSelectedPathComponent();
					Object ob = node.getUserObject();
					if (ob != null) {
						if (ob instanceof String) {
							int response = JOptionPane.showConfirmDialog(
									MainWindows.getInstance(), "确定删除此单吗?",
									"每天流水销售", JOptionPane.YES_NO_OPTION);
							if (response == JOptionPane.YES_OPTION) {
								flowLogMoudle.deleteBySNumber(String
										.valueOf(ob));
							}

						}
					}
				}
			});
			add(deleItem);
		}

		public void show(Component c, int x, int y) {

			JTree tree = (JTree) c;
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
					.getLastSelectedPathComponent();

			if (node.isRoot()) {
				deleItem.setEnabled(false);
			} else {
				deleItem.setEnabled(true);
			}

			super.show(c, x, y);
		}
	}

	public void refresh() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				reload();
				//tree 刷新
				DefaultTreeModel model = (DefaultTreeModel) jtree_1.getModel();
				model.reload();
			}
		});
	}

}
