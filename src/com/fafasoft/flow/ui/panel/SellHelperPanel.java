package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.SysEnv;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.ConfigDAOImpl;
import com.fafasoft.flow.dao.impl.CustomDAOImpl;
import com.fafasoft.flow.dao.impl.CustomtTypeDAOImpl;
import com.fafasoft.flow.dao.impl.FlowLogDAOImpl;
import com.fafasoft.flow.dao.impl.StockDAOImpl;
import com.fafasoft.flow.pojo.Config;
import com.fafasoft.flow.pojo.Custom;
import com.fafasoft.flow.pojo.CustomType;
import com.fafasoft.flow.pojo.Flowlog;
import com.fafasoft.flow.pojo.RetailBill;
import com.fafasoft.flow.pojo.Stock;
import com.fafasoft.flow.ui.ActionView;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.widget.CashDialog;
import com.fafasoft.flow.ui.widget.JTextFieldWithButton;
import com.fafasoft.flow.ui.widget.SellDialog;
import com.fafasoft.flow.ui.widget.ShortcutManager;
import com.fafasoft.flow.ui.widget.StockListDialog;
import com.fafasoft.flow.ui.widget.StockQueryDialog;
import com.fafasoft.flow.ui.widget.SuggestTextField;
import com.fafasoft.flow.ui.widget.SuggestTextFieldJTable;
import com.fafasoft.flow.ui.widget.table.ImageRenderer;
import com.fafasoft.flow.util.DateHelper;
import com.fafasoft.flow.util.NumberUtils;
import com.fafasoft.flow.util.PendingOrder;
import com.fafasoft.plugin.pos.ticket.SmallTicket;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.UUID;
import java.util.Vector;

public class SellHelperPanel extends JPanel implements LazyPageContent {
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel label_8;
	private JLabel label_11;
	private JLabel label_13;
	private JLabel sumSellprice_label_2;
	private JLabel sumSellprice_label;
	private JLabel sumAmount_label_4;
	private JLabel sumAmount_label;
	private JXDatePicker datePicker;
	// 金额总计
	private JLabel label_16;
	private JLabel label_1;
	private JLabel cdate;
	// 数量总计
	private JLabel lbll;
	private JLabel favorablelabel;// 优惠
	private String discountHiddenTextFiled = "";
	private JTextField textField_2;
	private ActionView callBack;
	private String tempStockId = "";
	private String tempguaDanNo = "";
	private double tempTheTotalAmount = 0;
	private JLabel favorablabelT;

	public SellHelperPanel() {
		super();
	}

	public void initPanel() {
		discountHiddenTextFiled = "";
		tempStockId = "";
		tempguaDanNo = "";
		tempTheTotalAmount = 0;
		setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 30));
		panel.setMinimumSize(new Dimension(10, 40));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 1));
		ConfigDAOImpl configDAO = DAOContentFactriy.getConfigDAO();
		Config config = configDAO.getConfig(Constant.VIEW_SHOP_NAME);
		String name = "点击这里设置您的店名称";
		if (config != null) {
			name = config.getValue();
		}
		final JLabel label_2 = new JLabel(name);
		final ImageIcon imageIcon = new ImageIcon(SellHelperPanel.class
				.getResource("/com/fafasoft/flow/resource/image/edittitle.png"));

		textField_2 = new JTextField();
		textField_2.setMargin(new Insets(0, 2, 2, 2));

		textField_2.setVisible(false);
		textField_2.setFont(new Font("宋体", Font.BOLD, 16));
		panel.add(textField_2);
		textField_2.setColumns(30);
		label_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				label_2.setIcon(imageIcon);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				label_2.setIcon(null);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				label_2.setVisible(false);
				textField_2.setVisible(true);
				textField_2.requestFocus();
			}
		});
		textField_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				textField_2.setVisible(false);
				String name = textField_2.getText();
				if (name.trim().length() > 0) {
					label_2.setText(name);
					Config[] configs = new Config[1];

					Config config2 = new Config();
					config2.setKey(Constant.VIEW_SHOP_NAME);
					config2.setValue(name);
					configs[0] = config2;
					ConfigDAOImpl configDAO = DAOContentFactriy.getConfigDAO();
					configDAO.addConfigs(configs);
				}
				label_2.setVisible(true);
			}
		});
		label_2.setFont(new Font("宋体", Font.BOLD, 25));
		label_2.setVerticalAlignment(SwingConstants.CENTER);
		label_2.setVerticalTextPosition(SwingConstants.BOTTOM);
		label_2.setHorizontalAlignment(SwingConstants.TRAILING);
		label_2.setHorizontalTextPosition(SwingConstants.LEADING);

		panel.add(label_2);
		JPanel tabbedPane = new JPanel();

		tabbedPane.setPreferredSize(new Dimension(5, 15));
		tabbedPane.setMinimumSize(new Dimension(5, 15));
		add(tabbedPane, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		panel_1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panel_1.setPreferredSize(new Dimension(10, 60));
		panel_1.setSize(new Dimension(0, 110));
		tabbedPane.setLayout(new BorderLayout(0, 0));
		panel_1.setBorder(BorderFactory.createTitledBorder("客户信息"));
		tabbedPane.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(null);

		JLabel label_10 = new JLabel("");
		label_10.setBounds(0, 0, 0, 0);
		panel_1.add(label_10);

		JLabel label_6 = new JLabel("客户号:");

		label_6.setBounds(10, 27, 42, 15);
		panel_1.add(label_6);

		// 客户号输入及其事件
		textField = new SuggestTextField(new CustomSuggestDataImpl(),
				new SuggestTextField.MUIManager() {
					public void updateView(String[] args) {
						String discount = null;
						if (args != null && args.length > 0) {
							if (args[1] != null && !"null".equals(args[1])) {
								label_8.setText(args[1]);
							}
							if (args[3] != null && !"null".equals(args[3])) {
								String customType = args[3];

								label_11.setText(customType);

								CustomtTypeDAOImpl customtTypeMoudle = DAOContentFactriy
										.getCustomtTypeDAO();

								CustomType customTypeBean = customtTypeMoudle
										.get(customType);

                                if(customTypeBean!=null){

                                    discount = String.valueOf(customTypeBean
                                            .getDiscount());
                                }
								
							}

							if (args[4] != null && !"null".equals(args[4])) {
								label_13.setText(args[4]);
							}
						}

						if (discount == null && "".equals(discount)) {
							discountHiddenTextFiled = "10";
						} else {
							discountHiddenTextFiled = discount;
						}

						updateViewByCustomId();
					}
				}, new Dimension(250, 200), null);
		String toolTip = "<html>提示&nbsp;&nbsp;&nbsp;<br>输入客户号/姓名/电话号码 按回车键<br>&nbsp;&nbsp;&nbsp;</html>";
		textField.setToolTipText(toolTip);
		textField.setFont(new Font("宋体", Font.PLAIN, 14));
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setColumns(10);
		textField.setBounds(52, 22, 125, 25);
		panel_1.add(textField);

		JLabel label_7 = new JLabel("姓名：");

		label_7.setBounds(197, 27, 36, 15);
		panel_1.add(label_7);

		label_8 = new JLabel("");
		label_8.setBounds(233, 27, 54, 15);
		panel_1.add(label_8);

		JLabel label_9 = new JLabel("级别：");

		label_9.setBounds(286, 27, 42, 15);
		panel_1.add(label_9);

		label_11 = new JLabel("");
		label_11.setBounds(321, 27, 79, 15);
		panel_1.add(label_11);

		JLabel label_12 = new JLabel("积分：");
		label_12.setBounds(398, 27, 36, 15);
		panel_1.add(label_12);

		label_13 = new JLabel("");
		label_13.setBounds(431, 27, 42, 15);
		panel_1.add(label_13);

		JLabel label = new JLabel("日期：");
		label.setBounds(475, 27, 36, 15);
		panel_1.add(label);
		if (SysEnv.getInstance().getLoginUserId().equals(Constant.ADMIN)) {
			datePicker = new JXDatePicker();
			datePicker.getEditor().setFont(new Font("宋体", Font.BOLD, 12));
			datePicker.getEditor().setEditable(false);
			datePicker.setFormats("yyyy-MM-dd");
			datePicker.setDate(DateHelper.currentDate());
			datePicker.setBounds(512, 22, 116, 25);
			datePicker.getEditor().setColumns(22);
			panel_1.add(datePicker);
		} else {
			cdate = new JLabel("");
			cdate.setFont(new Font("宋体", Font.BOLD, 12));
			cdate.setBounds(512, 27, 125, 15);
			cdate.setText(DateHelper.getNowDateTime());
			panel_1.add(cdate);
		}
		JPanel panel_2 = new JPanel();
		tabbedPane.add(panel_2);
		panel_2.setFont(new Font("宋体", Font.PLAIN, 12));
		panel_2.setLayout(new BorderLayout(0, 0));
		JPanel panel_45 = new JPanel();
		panel_45.setBorder(new TitledBorder(new CompoundBorder(null, new EmptyBorder(1, 1, 1, 1)), "\u5546\u54C1\u5217\u8868", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_45.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("宋体", Font.PLAIN, 12));

		scrollPane.setBorder(null);
		panel_45.add(scrollPane, BorderLayout.CENTER);

		JPanel panel_46 = new JPanel();
		panel_46.setFont(new Font("宋体", Font.PLAIN, 12));
		panel_46.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_46.setPreferredSize(new Dimension(10, 25));
		panel_46.setMinimumSize(new Dimension(10, 130));
		panel_2.add(panel_45, BorderLayout.CENTER);
		panel_45.add(panel_46, BorderLayout.SOUTH);
		panel_46.setLayout(null);
		ImageIcon delrowBtIco = new ImageIcon(SellHelperPanel.class
				.getResource("/com/fafasoft/flow/resource/image/dddelete.png"));
		JButton delrowBt = createToolButtn("删单[F8]", delrowBtIco, 2, 2,
				new ActionView() {

					public void actionView(Object[] arg) {
						deleAll();
					}
				});
		panel_46.add(delrowBt);

		ImageIcon delallBtIco = new ImageIcon(SellHelperPanel.class
				.getResource("/com/fafasoft/flow/resource/image/derow.png"));
		JButton delallBt = createToolButtn("删行[Del]", delallBtIco, 88, 2,
				new ActionView() {

					public void actionView(Object[] arg) {
						deleteRow();
					}
				});

		panel_46.add(delallBt);

		ImageIcon modiftBtBtIco = new ImageIcon(SellHelperPanel.class
				.getResource("/com/fafasoft/flow/resource/image/edittitle.png"));
		JButton modiftBt = createToolButtn("修改[F9]", modiftBtBtIco, 174, 2,
				new ActionView() {
					public void actionView(Object[] arg) {
						modify();
					}
				});

		panel_46.add(modiftBt);

		ImageIcon guadanBtIco = new ImageIcon(SellHelperPanel.class
				.getResource("/com/fafasoft/flow/resource/image/get.png"));
		final JButton guadanBt = createToolButtn("挂单[F11]", guadanBtIco, 260,
				2, new ActionView() {
					public void actionView(Object[] arg) {
						guaDan();
					}
				});
		guadanBt.setToolTipText("挂单只是暂时性保存");
		panel_46.add(guadanBt);

		ImageIcon getDanBtIco = new ImageIcon(SellHelperPanel.class
				.getResource("/com/fafasoft/flow/resource/image/go.png"));
		JButton getDanBt = createToolButtn("取单[F12]", getDanBtIco, 346, 2,
				new ActionView() {

					public void actionView(Object[] arg) {
						quDan(guadanBt);
					}
				});
		panel_46.add(getDanBt);

		table = new JTable();
		table.setRowHeight(25);

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		table.setModel(new DefaultTableModel(null, new String[] { "stockid",
				"\u8D27\u53F7", "\u540D\u79F0", "\u6570\u91CF", "\u552E\u4EF7",
				"\u6298\u6263", "\u5408\u8BA1", "cost", "sell", "类型", "规格",
				"颜色" }) {
			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class, Double.class, BigDecimal.class, Float.class,
					BigDecimal.class, BigDecimal.class, BigDecimal.class,
					String.class, String.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false, false, false, false, false, false, false,
					false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(1).setPreferredWidth(112);
		table.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());
		table.getColumnModel().getColumn(2).setPreferredWidth(155);
		table.getColumnModel().getColumn(2).setMinWidth(80);
		table.getColumnModel().getColumn(6).setPreferredWidth(94);
		table.getColumnModel().getColumn(7).setResizable(false);
		table.getColumnModel().getColumn(7).setPreferredWidth(0);
		table.getColumnModel().getColumn(7).setMinWidth(0);
		table.getColumnModel().getColumn(7).setMaxWidth(0);
		table.getColumnModel().getColumn(8).setResizable(false);
		table.getColumnModel().getColumn(8).setPreferredWidth(0);
		table.getColumnModel().getColumn(8).setMinWidth(0);
		table.getColumnModel().getColumn(8).setMaxWidth(0);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int clicked = e.getClickCount();
				if (clicked == 2) {
					modify();
				}
			}
		});
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoCreateRowSorter(true);

		scrollPane.setViewportView(table);
		table.getSelectionModel().setSelectionInterval(0, 0);
		JPanel panel_4 = new JPanel();

		panel_4.setFont(new Font("新宋体", Font.PLAIN, 12));
		panel_4.setBorder(new TitledBorder(null, "\u552E\u8D27\u64CD\u4F5C",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setPreferredSize(new Dimension(10, 100));
		panel_2.add(panel_4, BorderLayout.SOUTH);
		panel_4.setLayout(null);

		JLabel label_14 = new JLabel("货 号:");
		label_14.setFont(new Font("宋体", Font.PLAIN, 13));
		label_14.setBounds(0, 28, 51, 31);
		label_14.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_4.add(label_14);
		callBack= getCallBack();
		textField_1 = new SuggestTextFieldJTable(
				new StockSuggestDataJTableImpl(),
				new SuggestTextFieldJTable.MUIManager() {
					public void updateView(String[] args) {
						if (args != null) {
							tempStockId = args[0];
						}
					}
				}, -1, new SuggestTextFieldJTable.MUIKeyEvent() {
					public void vkEnterENTER() {
						final String catno = textField_1.getText();
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								int rows = table.getRowCount();
								if (catno.trim().length() > 0) {
									StockDAOImpl stockMoudle = DAOContentFactriy
											.getStockDAO();
									Stock stock = null;

									if (tempStockId.length() > 0) {
										stock = stockMoudle
												.getStockByID(tempStockId);
										submmitGood(stock, catno);
										tempStockId = "";
									} else {
										java.util.List stockList = stockMoudle
												.getStockByCatNo(catno, 0, 500);

										if (stockList != null
												&& stockList.size() > 0) {
											if (stockList.size() == 1) {
												stock = (Stock) stockList
														.get(0);
												submmitGood(stock, catno);
											} else {
												StockListDialog stockListDialog = new StockListDialog(
														table.getParent(),
														"选择一个需要出售的货物", catno,
														false, stockList,"出售",
														new ActionView() {

															public void actionView(
																	Object[] arg) {
																Stock stock = new Stock();
																stock
																		.setId(String
																				.valueOf(arg[0]));
																stock
																		.setCatno(String
																				.valueOf(arg[1]));
																stock
																		.setStockname(String
																				.valueOf(arg[2]));
																stock
																		.setType(String
																				.valueOf(arg[3]));
																stock
																		.setColor(String
																				.valueOf(arg[7]));
																stock
																		.setSpecif(String
																				.valueOf(arg[8]));
																stock
																		.setSyamount(NumberUtils
																				.dormatDouble(String
																						.valueOf(arg[6])));
																stock
																		.setSellprice(BigDecimal
																				.valueOf(NumberUtils
																						.dormatDouble(String
																								.valueOf(arg[4]))));
																stock
																		.setCostprice(BigDecimal
																				.valueOf(NumberUtils
																						.dormatDouble(String
																								.valueOf(arg[5]))));

																submmitGood(
																		stock,
																		stock
																				.getCatno());
															}

														});
												stockListDialog
														.setVisible(true);
											}
										} else {
											submmitGood(stock, catno);
										}
									}

								} else if (rows > 0) {
									cashRegister();
								}
								tempStockId = "";
								tempguaDanNo = "";
							}
						});
					}
				});

		textField_1.setFocusable(true);
		String toolTipt = "<html>提示&nbsp;&nbsp;<br>输入货号  按回车键<br>&nbsp;&nbsp;</html>";
		textField_1.setToolTipText(toolTipt);
		textField_1.setFont(new Font("宋体", Font.BOLD, 20));

		textField_1.setColumns(10);

		ImageIcon image = new ImageIcon(SellHelperPanel.class
				.getResource("/com/fafasoft/flow/resource/image/zoom-s.png"));
		JTextFieldWithButton textFieldWithButton = new JTextFieldWithButton(
				textField_1, image);
		textFieldWithButton.addButtonActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StockQueryDialog stockQueryDialog = new StockQueryDialog(
						MainWindows.getInstance(), "查询库存货物", true, false,
						new ActionView() {

							public void actionView(Object[] arg) {
								Stock stock = new Stock();
								stock.setId(String.valueOf(arg[0]));
								stock.setCatno(String.valueOf(arg[1]));
								stock.setStockname(String.valueOf(arg[2]));
								stock.setType(String.valueOf(arg[3]));
								stock.setSpecif(String.valueOf(arg[4]));
								stock.setColor(String.valueOf(arg[5]));
								stock.setSyamount(NumberUtils
										.dormatDouble(String.valueOf(arg[6])));
								stock.setSellprice(BigDecimal
										.valueOf(NumberUtils
												.dormatDouble(String
														.valueOf(arg[7]))));
								stock.setCostprice(BigDecimal
										.valueOf(NumberUtils
												.dormatDouble(String
														.valueOf(arg[8]))));
								submmitGood(stock, stock.getCatno());
							}
						}, null);
				stockQueryDialog.setVisible(true);

			}
		});
		textFieldWithButton.setBounds(51, 23, 197, 40);
		panel_4.add(textFieldWithButton);
		JButton btne = new JButton("收银[Enter]");
		btne.setIcon(new ImageIcon(SellHelperPanel.class
				.getResource("/com/fafasoft/flow/resource/image/money_dollar.png")));
		btne.setHorizontalTextPosition(SwingConstants.RIGHT);


		btne.setBounds(260, 22, 105, 43);
		btne.setBorderPainted(true);
		btne.setContentAreaFilled(false);
		btne.setPreferredSize(new Dimension(57, 50));
		btne.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btne.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cashRegister();
			}
		});
		panel_4.add(btne);
		JLabel label_15 = new JLabel("总计:");
		label_15.setBounds(406, 23, 68, 28);
		label_15.setFont(new Font("宋体", Font.BOLD, 25));
		panel_4.add(label_15);
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		label_16 = new JLabel(currency.format(0));
		label_16.setBounds(480, 23, 156, 28);
		label_16.setFont(new Font("宋体", Font.BOLD, 24));
		panel_4.add(label_16);

		label_1 = new JLabel("");
		label_1.setBounds(51, 62, 334, 22);
		label_1.setForeground(Color.RED);

		panel_4.add(label_1);

		JLabel label_18 = new JLabel("数量:");
		label_18.setBounds(406, 58, 68, 33);
		label_18.setVerticalAlignment(SwingConstants.TOP);
		label_18.setFont(new Font("宋体", Font.BOLD, 25));
		panel_4.add(label_18);

		lbll = new JLabel("0");
		lbll.setBounds(480, 59, 156, 32);
		lbll.setVerticalAlignment(SwingConstants.TOP);
		lbll.setFont(new Font("宋体", Font.BOLD, 25));
		panel_4.add(lbll);

		favorablabelT = new JLabel("优惠");
		favorablabelT.setVisible(false);
		favorablabelT.setFont(new Font("宋体", Font.BOLD, 16));
		favorablabelT.setBounds(650, 66, 37, 25);
		panel_4.add(favorablabelT);

		favorablelabel = new JLabel("0");
		favorablelabel.setVisible(false);
		favorablelabel.setFont(new Font("宋体", Font.BOLD, 16));
		favorablelabel.setBounds(690, 62, 150, 32);
		panel_4.add(favorablelabel);

		addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent evt) {
				textField_1.requestFocus();
			}
			public void ancestorRemoved(AncestorEvent evt) {
			}
			public void ancestorMoved(AncestorEvent evt) {
			}
		});

		JPanel dataypanel_3 = new JPanel();

		dataypanel_3.setBorder(BorderFactory
				.createTitledBorder("\u4ECA\u65E5\u552E\u8D27"));
		add(dataypanel_3, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 26, 72, 81, 73, 74, 101, 0, 0 };
		gbl_panel_3.rowHeights = new int[] { 10, 15, 0 };
		gbl_panel_3.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		dataypanel_3.setLayout(gbl_panel_3);

		JLabel label_19 = new JLabel("当前售货员");

		GridBagConstraints gbc_label_19 = new GridBagConstraints();
		gbc_label_19.anchor = GridBagConstraints.WEST;
		gbc_label_19.insets = new Insets(0, 0, 5, 5);
		gbc_label_19.gridx = 1;
		gbc_label_19.gridy = 0;
		dataypanel_3.add(label_19, gbc_label_19);

		JLabel cuser_label = new JLabel("");
		GridBagConstraints gbc_label_20 = new GridBagConstraints();
		gbc_label_20.anchor = GridBagConstraints.WEST;
		gbc_label_20.insets = new Insets(0, 0, 5, 5);
		gbc_label_20.gridx = 2;
		gbc_label_20.gridy = 0;
		dataypanel_3.add(cuser_label, gbc_label_20);

		sumSellprice_label_2 = new JLabel("日流水总计");

		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.NORTHWEST;
		gbc_label_2.insets = new Insets(0, 0, 0, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 1;
		dataypanel_3.add(sumSellprice_label_2, gbc_label_2);

		sumSellprice_label = new JLabel("0");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.anchor = GridBagConstraints.NORTHWEST;
		gbc_label_3.insets = new Insets(0, 0, 0, 5);
		gbc_label_3.gridx = 2;
		gbc_label_3.gridy = 1;
		dataypanel_3.add(sumSellprice_label, gbc_label_3);

		sumAmount_label_4 = new JLabel("日销量总计");

		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.anchor = GridBagConstraints.NORTHWEST;
		gbc_label_4.insets = new Insets(0, 0, 0, 5);
		gbc_label_4.gridx = 3;
		gbc_label_4.gridy = 1;
		dataypanel_3.add(sumAmount_label_4, gbc_label_4);

		sumAmount_label = new JLabel("0");
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.anchor = GridBagConstraints.NORTHWEST;
		gbc_label_5.insets = new Insets(0, 0, 0, 5);
		gbc_label_5.gridx = 4;
		gbc_label_5.gridy = 1;
		dataypanel_3.add(sumAmount_label, gbc_label_5);

		JLabel lblqq = new JLabel("发发软件 ");
		lblqq.setFont(new Font("微软雅黑", Font.BOLD, 12));
		GridBagConstraints gbc_lblqq = new GridBagConstraints();
		gbc_lblqq.anchor = GridBagConstraints.EAST;
		gbc_lblqq.insets = new Insets(0, 0, 5, 0);
		gbc_lblqq.gridx = 6;
		gbc_lblqq.gridy = 0;
		dataypanel_3.add(lblqq, gbc_lblqq);

		JLabel label_17 = new JLabel("免费、好用、灵巧");
		label_17.setFont(new Font("微软雅黑", Font.BOLD, 12));
		GridBagConstraints gbc_label_17 = new GridBagConstraints();
		gbc_label_17.anchor = GridBagConstraints.NORTHEAST;
		gbc_label_17.gridx = 6;
		gbc_label_17.gridy = 1;
		dataypanel_3.add(label_17, gbc_label_17);

		ShortcutManager.getInstance().addShortcutListener(
				new ShortcutManager.ShortcutListener() {
					public void handle() {
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								deleAll();
							}
						});
					}
				}, KeyEvent.VK_F8);
		ShortcutManager.getInstance().addShortcutListener(
				new ShortcutManager.ShortcutListener() {
					public void handle() {
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								deleteRow();
							}
						});
					}
				}, KeyEvent.VK_DELETE);
		ShortcutManager.getInstance().addShortcutListener(
				new ShortcutManager.ShortcutListener() {
					public void handle() {
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								modify();
							}
						});
					}
				}, KeyEvent.VK_F9);
		ShortcutManager.getInstance().addShortcutListener(
				new ShortcutManager.ShortcutListener() {
					public void handle() {
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								guaDan();
							}
						});
					}
				}, KeyEvent.VK_F11);
		ShortcutManager.getInstance().addShortcutListener(
				new ShortcutManager.ShortcutListener() {
					public void handle() {
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								quDan(guadanBt);
							}
						});
					}
				}, KeyEvent.VK_F12);

		AbstractAction enterForward = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				cashRegister();
			}
		};
		btne.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				"enterForward");
		btne.getActionMap().put("enterForward", enterForward);
		cuser_label.setText(SysEnv.getInstance().getloginUserName());
		getTotalByUserId();
	}

	private void reSet() {
		textField.setText("");
		discountHiddenTextFiled = "10";
		label_8.setText("");
		label_11.setText("");
		label_13.setText("");
		textField_1.setText("");
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		label_16.setText(currency.format(0));
		lbll.setText("0");
		tempStockId = "";
		tempguaDanNo = "";
		tempTheTotalAmount = 0;
		favorablabelT.setVisible(false);
		favorablelabel.setVisible(false);
		favorablelabel.setText(currency.format(0));
	}

	private void modify() {
		int rows = table.getRowCount();
		if (rows > 0) {
			int srow = table.getSelectedRow();
			if (srow == -1) {
				JOptionPane.showMessageDialog(table.getParent(),
						"请选择一行需要修改的货物数据", "修改通知", JOptionPane.WARNING_MESSAGE);
			} else if (srow > -1) {
				SellDialog sellDialog = new SellDialog(table.getParent(),
						table, new ActionView() {
							public void actionView(Object[] arg) {
								modifyRow(arg);
							}
						});
				sellDialog.setVisible(true);
			}

		}
	}

	private void deleAll() {
		int rows = table.getRowCount();
		if (rows > 0) {
			int n = JOptionPane.showConfirmDialog(table.getParent(),
					"确定删除此笔销售单?", "删除销售单", JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION) {
				DefaultTableModel tableModel = (DefaultTableModel) table
						.getModel();
				tableModel.setRowCount(0);
				reSet();
			}
		}
	}

	private void deleteRow() {
		int rows = table.getRowCount();
		if (rows > 0) {
			int nRow = table.getSelectedRow();
			if (nRow > -1) {
				int n = JOptionPane.showConfirmDialog(table.getParent(),
						"确定删除此笔销售单第" + (nRow + 1) + "行?", "删除销售单",
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					DefaultTableModel tableModel = (DefaultTableModel) table
							.getModel();
					// 总计再算
					double amount = (Double) tableModel.getValueAt(nRow, 3);
					BigDecimal sellSum = ((BigDecimal) tableModel.getValueAt(
							nRow, 6)).setScale(2, BigDecimal.ROUND_HALF_UP);
					
					// 视图删除
					tableModel.removeRow(nRow);
					int newrowCount = table.getRowCount();
					System.out.println(newrowCount);
					table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					if (newrowCount == 1) {
						table.setRowSelectionInterval(0, 0);
					} else if(newrowCount >0) {
						table.setRowSelectionInterval(nRow - 1, nRow - 1);
					}
					toalView();
				}
			}
		}
	}

	private void cashRegister() {
		int rows = table.getRowCount();
		if (rows > 0) {
			String f = String.valueOf(tempTheTotalAmount);
			CashDialog cashDialog = new CashDialog(table.getParent(), f, f,
					callBack);
			cashDialog.setVisible(true);

		}
	}

	private void guaDan() {
		int rows = table.getRowCount();
		if (rows > 0) {
			if (PendingOrder.getInstance().getSize() <= 10) {
				RetailBill retailBill = new RetailBill();
				if (tempguaDanNo.length() > 0) {
					retailBill.setId(tempguaDanNo);
				} else {
					retailBill
							.setId(String.valueOf(System.currentTimeMillis()));
				}
				retailBill.setTime(DateHelper.getNowTime());
				retailBill.setCustomFlag(textField.getText());

				if (SysEnv.getInstance().getLoginUserId()
						.equals(Constant.ADMIN)) {
					retailBill.setDate(datePicker.getEditor().getText());
				} else {
					retailBill.setDate(cdate.getText());
				}
				DefaultTableModel tableModel = (DefaultTableModel) table
						.getModel();
				Vector vector = tableModel.getDataVector();
				Vector vectorc = (Vector) vector.clone();

				retailBill.setData(vectorc);
				PendingOrder.getInstance().add(retailBill.getId(), retailBill);
				tableModel.setRowCount(0);
				reSet();
			} else {
				JOptionPane.showMessageDialog(table.getParent(), "最多只能挂10单",
						"挂单通知", JOptionPane.WARNING_MESSAGE);
			}

		}
	}

	private void quDan(JButton invoker) {
		int rows = table.getRowCount();
		if (rows > 0 && PendingOrder.getInstance().getSize() > 0) {
			JOptionPane.showMessageDialog(table.getParent(), "请先结算此单，然后取单",
					"挂单通知", JOptionPane.WARNING_MESSAGE);
		} else {
			if (PendingOrder.getInstance().getSize() > 0) {
				final JTable jTable = PendingOrder.getInstance().getData();
				if (jTable != null) {
					int size = jTable.getRowCount() + 1;
					int h = (size * 20) + 24;
					JScrollPane tipFrame = new JScrollPane();
					tipFrame.setViewportView(jTable);
					final JPopupMenu jPopupMenu = new JPopupMenu();
					jPopupMenu.add(tipFrame);
					jPopupMenu.setFocusable(false);

					tipFrame.setPreferredSize(new Dimension(180, h));
					jPopupMenu.show(invoker, 0 + 86, invoker.getHeight() - h
							- 30);
					jPopupMenu.setBorder(new TitledBorder("已挂起的销售单"));
					jTable.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent arg0) {
							int nRow = jTable.getSelectedRow();
							if (nRow > -1) {
								tempguaDanNo = String.valueOf(jTable
										.getValueAt(nRow, 0));

								RetailBill object = (RetailBill) PendingOrder
										.getInstance().remove(tempguaDanNo);
								textField.setText(object.getCustomFlag());

								if (SysEnv.getInstance().getLoginUserId()
										.equals(Constant.ADMIN)) {
									datePicker.getEditor().setText(
											object.getDate());
								} else {
									cdate.setText(object.getDate());
								}
								DefaultTableModel tableModel = (DefaultTableModel) table
										.getModel();
								Vector vectorc = object.getData();
								for (int i = 0; i < vectorc.size(); i++) {
									tableModel.addRow((Vector) vectorc.get(i));
								}
								toalView();
								jPopupMenu.setVisible(false);
							} else {
								tempguaDanNo = "";
							}

						}
					});
				}
			}
		}
	}

	private double maxStock(String stockId) {
		int row = table.getRowCount();
		double a = 0;
		if (row > 0) {
			for (int i = 0; i < row; i++) {
				String ccno = String.valueOf(table.getValueAt(i, 0));
				if (ccno.equals(stockId)) {
					a = a + (Double) table.getValueAt(i, 3);
				}
			}
		}
		return a;
	}

	private void modifyRow(Object[] arg) {
		if (arg == null || arg.length == 0) {
			return;
		}
		int row = table.getSelectedRow();

		DefaultTableModel defaultTableModel = (DefaultTableModel) table
				.getModel();
		StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
		Stock stock = stockMoudle.getStockByID(String.valueOf(arg[3]));
		// 目前已选择的购物数量
		double oldsl = (Double) table.getValueAt(row, 3);
	
		Double d = maxStock(String.valueOf(arg[0])) - oldsl;
		double sl = Double.parseDouble(String.valueOf(arg[0]));
		if (d < stock.getSyamount()) {
			if (sl > stock.getSyamount()) {
				sl = (stock.getSyamount() - d);
			} else {
				if (sl + d > stock.getSyamount()) {
					sl = stock.getSyamount() - d;
				} else {
					sl = sl;
				}
			}
		}

		// 售价
		BigDecimal sell = new BigDecimal(String.valueOf(arg[1])).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		// 折扣
		BigDecimal discount = new BigDecimal(String.valueOf(arg[2]));

		// 合计
		BigDecimal sum = sell.multiply(new BigDecimal(sl));
		sum = sum.setScale(2, BigDecimal.ROUND_HALF_UP);

		defaultTableModel.setValueAt(Double.parseDouble(String.format("%.2f",sl)), row, 3);
		defaultTableModel.setValueAt(sell, row, 4);
		defaultTableModel.setValueAt(discount, row, 5);
		defaultTableModel.setValueAt(sum, row, 6);
		toalView();
	}

	private void toalView() {
		int rowCount = table.getRowCount();
		DefaultTableModel defaultTableModel = (DefaultTableModel) table
				.getModel();
		double ashul = 0;
		BigDecimal hezji = new BigDecimal(0);

		hezji = hezji.setScale(2, BigDecimal.ROUND_HALF_UP);
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		BigDecimal rowyuhui = BigDecimal.valueOf(0);

		for (int i = 0; i < rowCount; i++) {
			Double rowshul = (Double) defaultTableModel.getValueAt(i, 3);
			BigDecimal rowheji = (BigDecimal) defaultTableModel
					.getValueAt(i, 6);
			ashul = ashul + rowshul;

			hezji = hezji.add(rowheji);
			BigDecimal cost = (BigDecimal) defaultTableModel.getValueAt(i, 7);
			BigDecimal sell = (BigDecimal) defaultTableModel.getValueAt(i, 8);
			rowyuhui = rowyuhui.add(sell.multiply(BigDecimal.valueOf(rowshul))
					.subtract(rowheji));

		}
		tempTheTotalAmount = hezji.doubleValue();
		label_16.setText(currency.format(hezji));
		lbll.setText(String.format("%.2f", ashul));
		if (rowyuhui.compareTo(BigDecimal.valueOf(0)) > 0) {
			favorablabelT.setVisible(true);
			favorablelabel.setVisible(true);
			favorablelabel.setText(currency.format(rowyuhui));
		} else {
			favorablabelT.setVisible(false);
			favorablelabel.setVisible(false);
			favorablelabel.setText(currency.format(0));
		}
	}

	private void submmitGood(Stock stock, String catno) {
		int rows = table.getRowCount();
		if (stock == null) {
			textField_1.setText("");
			label_1.setText("库存中没有[" + catno + "]货物");
			return;
		} else {
			if (stock.getSyamount() <= 0) {
				label_1.setText("货物[" + catno + "]库存为零，此货物不能出售");
				return;
			}
			label_1.setText("");
		}
		// 计算库存剩余
		double suma = maxStock(stock.getId());
		double sl = 1;
		if ((suma + 1) > stock.getSyamount()) {
			double tempsy = suma + 1 - stock.getSyamount();
			if ((tempsy + suma) == stock.getSyamount()) {
				sl = tempsy;
			} else {
				label_1.setText("货物[" + catno + "]库存为零，此货物不能出售");
				return;
			}
		}
		if (stock.getSellprice().compareTo(stock.getCostprice()) < 0) {
			int response = JOptionPane.showConfirmDialog(null,
					"你输入的售价已经赔钱了，确认销售?", "销售数据", JOptionPane.YES_NO_OPTION);
			if (response == JOptionPane.NO_OPTION
					|| response == JOptionPane.CLOSED_OPTION) {
				return;
			}
		}
		// 折扣
		BigDecimal discount;
		if (discountHiddenTextFiled != null
				&& !"".equals(discountHiddenTextFiled.trim())) {
			discount = new BigDecimal(discountHiddenTextFiled);
		} else {
			discount = new BigDecimal("10");
		}

		textField_1.setText("");

		DefaultTableModel defaultTableModel = (DefaultTableModel) table
				.getModel();
		boolean isadd = true;
		double shul = 0;
		for(int i=0;i<table.getRowCount();i++) {
			String id = String.valueOf(table.getValueAt(i, 0));
			if(id.equals(stock.getId())) {
			    shul = (Double)table.getValueAt(i, 3);
				BigDecimal danjian = (BigDecimal)table.getValueAt(i, 4);
				table.setValueAt(shul+1, i, 3);
				BigDecimal zj =danjian.multiply(BigDecimal.valueOf(shul+1)).setScale(2, BigDecimal.ROUND_HALF_UP);
				table.setValueAt(zj, i, 6);
				
				isadd = false;
				break;
			}
		}
		if(isadd) {
			// 售价
			BigDecimal sell = stock.getSellprice().multiply(discount).divide(
					new BigDecimal("10")).setScale(2, BigDecimal.ROUND_HALF_UP);
			// 合计
			BigDecimal sum = (sell.multiply(new BigDecimal(1))).setScale(2,
					BigDecimal.ROUND_HALF_UP);

			Object[] rowData = new Object[] { stock.getId(), catno,
					stock.getStockname(), sl, sell, discount, sum,
					stock.getCostprice(), stock.getSellprice(), stock.getType(),
					stock.getSpecif(), stock.getColor() };
			defaultTableModel.insertRow(rows, rowData);
			table.setRowSelectionInterval(rows, rows);
		
		}
		toalView() ;
	}
    


	private void updateViewByCustomId() {
		DefaultTableModel defaultTableModel = (DefaultTableModel) table
				.getModel();
		int rowcount = table.getRowCount();
		StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
		BigDecimal sumAmount = new BigDecimal("0");
		BigDecimal sumSell = new BigDecimal("0");

		for (int i = 0; i < rowcount; i++) {
			String catno = String.valueOf(defaultTableModel.getValueAt(i, 1));

			double amount = (Double) defaultTableModel.getValueAt(i, 3);

			Stock stock = stockMoudle.getLastStockByNo(catno);

			// 折扣
			BigDecimal discount = new BigDecimal("10");
			if (discountHiddenTextFiled != null
					&& !"".equals(discountHiddenTextFiled)) {
				discount = new BigDecimal(discountHiddenTextFiled);
			}

			// 售价
			BigDecimal sell = stock.getSellprice().multiply(discount).divide(
					new BigDecimal("10")).setScale(2, BigDecimal.ROUND_HALF_UP);

			// 合计
			BigDecimal sum = sell.multiply(new BigDecimal(amount));

			defaultTableModel.setValueAt(sell, i, 4);
			defaultTableModel.setValueAt(discount, i, 5);
			defaultTableModel.setValueAt(sum, i, 6);

			sumAmount = sumAmount.add(new BigDecimal(amount));
			sumSell = sumSell.add(sum);
		}
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		label_16.setText(currency.format(sumSell.setScale(2)));
		lbll.setText(String.valueOf(sumAmount));
		toalView() ;
	}

	private void getTotalByUserId() {
		Config configViewSellHelp = DAOContentFactriy.getConfigDAO().getConfig(
				Constant.VIEW_FLOWINFO_SELHELPER);

		if (configViewSellHelp != null
				&& "true".equals(configViewSellHelp.getValue())) {

			sumSellprice_label_2.setVisible(true);
			sumSellprice_label.setVisible(true);
			sumAmount_label_4.setVisible(true);
			sumAmount_label.setVisible(true);
			FlowLogDAOImpl flowLogMoudle = DAOContentFactriy.getFlowLogDAO();

			Flowlog flowlog = flowLogMoudle.getFlowlogTodayByUserName(
					DateHelper.getNowDateTime(), SysEnv.getInstance()
							.getLoginUserId());

			if (flowlog != null) {
				sumSellprice_label.setText(String.valueOf(flowlog
						.getSellprice().doubleValue()));
				sumAmount_label.setText(String.valueOf(flowlog.getAmount()));
			}

		} else {
			sumSellprice_label_2.setVisible(false);
			sumSellprice_label.setVisible(false);
			sumAmount_label_4.setVisible(false);
			sumAmount_label.setVisible(false);
		}
	}

	private JButton createToolButtn(String name, ImageIcon icon, int x, int y,
			final ActionView ac) {
		final JButton delrowBt = new JButton(name);
		delrowBt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				delrowBt.setBorderPainted(true);
				delrowBt.setContentAreaFilled(false);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				delrowBt.setBorderPainted(false);
				delrowBt.setContentAreaFilled(false);
			}

			public void mouseClicked(MouseEvent e) {
				ac.actionView(null);
			}
		});

		delrowBt.setFont(new Font("宋体", Font.PLAIN, 12));
		delrowBt.setBorder(null);
		delrowBt.setContentAreaFilled(false);
		delrowBt.setBorderPainted(false);
		delrowBt.setMargin(new Insets(2, 2, 2, 5));
		delrowBt.setIconTextGap(0);
		delrowBt.setIcon(icon);
		delrowBt.setBounds(x, y, 85, 22);
		return delrowBt;
	}

	private ActionView getCallBack() {
		ActionView	callBack = new ActionView() {
			public void actionView(Object[] args) {
				long ndate = DateHelper.getNowDateTimeLong();
				String nowdate = DateHelper.getNowDateTime();
				long intdate = DAOContentFactriy.getFlowLogDAO().getMaxSNumber(
						nowdate);
				if (intdate == 0) {
					intdate = Long.parseLong(String.valueOf(ndate) + "0001");
				} else {
					intdate = intdate + 1;
				}
				DefaultTableModel defaultTableModel = (DefaultTableModel) table
						.getModel();
				int rowcount = table.getRowCount();

				CustomDAOImpl customMoudle = DAOContentFactriy.getCustomDAO();
				String cno = textField.getText();
				final Custom custom = customMoudle.getCustomById(cno);
				// 折扣
				BigDecimal temp = new BigDecimal(0);
				// NumberFormat format = NumberFormat.getInstance();
				FlowLogDAOImpl flowLogMoudle = DAOContentFactriy.getFlowLogDAO();
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				 Vector vector = tableModel.getDataVector();
				
				 final  Vector vectorc = (Vector) vector.clone();
				StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
				for (int i = 0; i < rowcount; i++) {
					String stockId = String.valueOf(defaultTableModel
							.getValueAt(0, 0));
					String catno = String.valueOf(defaultTableModel.getValueAt(
							0, 1));
					String name = String.valueOf(defaultTableModel.getValueAt(
							0, 2));
					double amount = (Double) defaultTableModel.getValueAt(0, 3);
					BigDecimal sell = (BigDecimal) defaultTableModel
							.getValueAt(0, 4);
					BigDecimal costprice = (BigDecimal) defaultTableModel
							.getValueAt(0, 7);
					String type = String.valueOf(defaultTableModel.getValueAt(
							0, 9));
					Flowlog flowlog = new Flowlog();
					temp = temp.add(sell.multiply(new BigDecimal(amount)));
					String id = String.valueOf(UUID.randomUUID().toString()
							.replaceAll("-", ""));
					flowlog.setFlowno(id);
					flowlog.setCatno(catno);
					flowlog.setAmount(amount);
					BigDecimal dd = sell.subtract(costprice).multiply(
							new BigDecimal(amount));
					flowlog.setLrprice(dd);
					flowlog.setType(type);
					flowlog.setStockId(stockId);
					if (SysEnv.getInstance().getLoginUserId().equals(
							Constant.ADMIN)) {
						flowlog.setDate(datePicker.getEditor().getText());
					} else {
						flowlog.setDate(cdate.getText());
					}
					flowlog.setCostprice(costprice);
					flowlog.setSellprice(sell);
					flowlog.setRecorddate(DateHelper.getNowDateTime());
					flowlog.setFlowflag(Constant.FLOW_TYPE_SELL);
					flowlog.setStockname(name);
					flowlog.setUserId(SysEnv.getInstance().getLoginUserId());
					// 客户是否存在
					if (custom != null) {
						flowlog.setCustomNo(custom.getId());
						flowlog.setCustomName(custom.getName());
					}
					flowlog.setSerialNumber(intdate);
					String sumsellText = sumSellprice_label.getText();
					String sumAmountText = sumAmount_label.getText();
					sumSellprice_label.setText(String.valueOf(sell
							.add(new BigDecimal(sumsellText))));
					sumAmount_label.setText(String.valueOf(amount
							+ Double.parseDouble((sumAmountText))));
					flowLogMoudle.add(flowlog);

					stockMoudle.updateSyAmount(flowlog.getStockId(), flowlog
							.getAmount(), "-");
					defaultTableModel.removeRow(0);
				}
				if (custom != null) {
					Config config = DAOContentFactriy.getConfigDAO().getConfig(Constant.JIFEN_BILV);
					if(config != null && config.getValue().length() >0) {
						BigDecimal dd =	temp.divide(BigDecimal.valueOf(Long.parseLong(config.getValue())));
						custom.setIntegration(custom.getIntegration()+dd.longValue());
					}
					BigDecimal dd = custom.getAmount().add(temp);
					custom.setAmount(dd);
					custom.setFrequency(custom.getFrequency() + 1);
					customMoudle.update(custom);

				}
				reSet();
				final String yinfu =String.valueOf(args[0]);
				final String shishou =String.valueOf(args[1]);
				final String zhaoling =String.valueOf(args[2]);
				ConfigDAOImpl configDAO = DAOContentFactriy.getConfigDAO();
				Config configMOde = configDAO.getConfig(Constant.PRINT_MODE);
				String pmode = Constant.PRINT_MODE_NO;
				if (configMOde != null) {
					pmode = configMOde.getValue();
				}
				boolean isprint = false;
				if (pmode.equals(Constant.PRINT_MODE_AUTO)) {
					isprint = true;
				} else if (pmode.equals(Constant.PRINT_MODE_YESNO)) {
					int n = JOptionPane.showConfirmDialog(MainWindows.getInstance(),
							"确认打印吗", "打印通知", JOptionPane.YES_NO_OPTION);
					isprint = (n == JOptionPane.YES_OPTION);
				}
				if (isprint) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							try {
								SmallTicket smallTicket = new SmallTicket(vectorc,yinfu,shishou,zhaoling,custom);
								smallTicket.print();
							}catch(Throwable e)  {
								
							}
						}
					});	
				}
			}
		};
		return callBack;
	}
}
