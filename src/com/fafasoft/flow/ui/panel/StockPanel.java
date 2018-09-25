package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.SysEnv;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.StockDAOImpl;
import com.fafasoft.flow.dao.impl.SuppliersDAOImpl;
import com.fafasoft.flow.pojo.Option;
import com.fafasoft.flow.pojo.Stock;
import com.fafasoft.flow.pojo.Suppliers;
import com.fafasoft.flow.service.StockImageService;
import com.fafasoft.flow.ui.LimitedDocument;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.skin.image.ImageFilter;
import com.fafasoft.flow.ui.skin.image.ImagePreview;
import com.fafasoft.flow.ui.widget.JTreeComboBox;
import com.fafasoft.flow.ui.widget.StockTypeDialog;
import com.fafasoft.flow.ui.widget.table.ImageRenderer;
import com.fafasoft.flow.util.DateHelper;
import com.fafasoft.flow.util.GuiUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.jdesktop.swingx.JXDatePicker;
import sun.awt.image.ToolkitImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

/**
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * @author liyc
 * @version 1.0
 * @since JDK1.5
 */
public class StockPanel extends BaseJPanel implements LazyPageContent {

    private StockImageService stockImageService= StockImageService.getInstance();

	private JTable table;
	private JLabel label_1;
	private JLabel label_3;
	private JXDatePicker datePicker;
	private JTreeComboBox jtreeComboBox;
	private JTextField textColor;
	private JTextField textGuige;
	private JTextField jinHuoQiange;
	private JTextField xiaoshoujiage;
	private JTextField shuliang;
	private JTextField gongyinshang;
	private JTextField huohao;
	private JTextField huowuName;
	private JButton cancleButton;
	private JLabel modifyUid;//
	private JTextField kucunshuliang;
	private JLabel addOldStockIDlable;//
	private JLabel hidcataNo;
	private JCheckBox isAddcheckBox;
	private JLabel lblNewLabelIco;

	public StockPanel() {
		super();
		this.setLayout(new BorderLayout());
	
	}

	public void initPanel() {
		setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u8FDB\u8D27\u7EDF\u8BA1",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 50, 0, 0, 0, 0, 0, 43, 17, 0,
				0 };
		gridBagLayout.rowHeights = new int[] { 24, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 1.0E-4 };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0E-4 };
		panel.setLayout(gridBagLayout);
		add(panel, BorderLayout.NORTH);

		JLabel label = new JLabel("\u6570\u91CF\u603B\u8BA1\uFF1A");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.gridx = 1;
		gbc.gridy = 0;
		panel.add(label, gbc);

		label_1 = new JLabel("0");
		GridBagConstraints gbc_1 = new GridBagConstraints();
		gbc_1.insets = new Insets(0, 0, 0, 5);
		gbc_1.gridx = 2;
		gbc_1.gridy = 0;
		panel.add(label_1, gbc_1);

		JLabel label_2 = new JLabel("\u8D27\u6B3E\u603B\u8BA1\uFF1A");
		GridBagConstraints gbc_2 = new GridBagConstraints();
		gbc_2.insets = new Insets(0, 0, 0, 5);
		gbc_2.gridx = 4;
		gbc_2.gridy = 0;
		panel.add(label_2, gbc_2);

		label_3 = new JLabel("0");
		GridBagConstraints gbc_3 = new GridBagConstraints();
		gbc_3.insets = new Insets(0, 0, 0, 5);
		gbc_3.gridx = 5;
		gbc_3.gridy = 0;
		panel.add(label_3, gbc_3);

		JLabel label_6 = new JLabel("");
		label_6.setForeground(Color.RED);
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.insets = new Insets(0, 0, 0, 5);
		gbc_label_6.gridx = 7;
		gbc_label_6.gridy = 0;
		panel.add(label_6, gbc_label_6);

		JLabel label_8 = new JLabel("查询以往进货数据，可在库存统计中查看");
		GridBagConstraints gbc_label_8 = new GridBagConstraints();
		gbc_label_8.gridx = 8;
		gbc_label_8.gridy = 0;
		panel.add(label_8, gbc_label_8);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "\u8FDB\u8D27\u5217\u8868",
				TitledBorder.LEADING, TitledBorder.TOP, null));
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		table = new JTable();
		table.setRowHeight(30);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int clicked = e.getClickCount();
				int nRow = table.getSelectedRow();
				String id = String.valueOf(table.getValueAt(nRow, 0));
				int nCol = table.getSelectedColumn();
				if (nCol != -1 && nRow != -1) {
					Object objSel = table.getValueAt(nRow, nCol);
					if (clicked == 2) {
						if (objSel != null && objSel instanceof String) {
							if ("删除".equals(String.valueOf(objSel))) {
								clickedTable();
							}
						} else {
							StockDAOImpl stockMoudle = DAOContentFactriy
									.getStockDAO();
							Stock stockold = stockMoudle.getStockByID(id);
//							System.out.println("id==" + id + "  stockold="
//									+ stockold);
							modify(stockold);
						}
					} else {
						clickedTable();
					}
				}
			}
		});

		table.setModel(new DefaultTableModel(null, new String[] { "id", "货号",
				"\u6570\u91CF", "进货价", "\u603B\u8BA1", "\u552E\u4EF7",
				"\u7C7B\u578B", "名称", "规格", "颜色", "时间", "操作" }) {
			boolean[] columnEditable = new boolean[] { false, false, false,
					false, false, false, false, false, false, false, false,
					false };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return columnEditable[columnIndex];
			}

			Class[] columnTypes = new Class[] { String.class, String.class,
					Double.class, BigDecimal.class, BigDecimal.class,
					BigDecimal.class, String.class, String.class, String.class,
					String.class, String.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setComponentPopupMenu(new TablePopupMenu());
		table.getColumnModel().getColumn(1)
				.setCellRenderer(new ImageRenderer());
		table.getSelectionModel().setSelectionInterval(0, 0);
		TableColumn tcflow = table.getColumn("id");
		tcflow.setResizable(false);
		tcflow.setPreferredWidth(0);
		tcflow.setWidth(0);
		tcflow.setMinWidth(0);
		tcflow.setMaxWidth(0);
		TableColumn tcflowc = table.getColumn("货号");
		tcflowc.setPreferredWidth(100);
		tcflowc.setWidth(100);
		tcflowc.setMinWidth(100);

		table.getTableHeader().setReorderingAllowed(false);
		// DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		// renderer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

		GuiUtils.columnCenter(table, "操作", 60);
		scrollPane.setViewportView(table);

		JPanel panel1 = new JPanel();
		panel1.setPreferredSize(new Dimension(10, 180));
		{
			panel1.setBorder(new CompoundBorder(new TitledBorder(
					"\u5f55\u5165\u8fdb\u8d27\u6570\u636e"), new EmptyBorder(5,
					5, 5, 5)));
		}
		add(panel1, BorderLayout.SOUTH);
		panel1.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(10, 140));
		panel1.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);

		JLabel label_5 = new JLabel("进货日期");
		label_5.setBounds(12, 3, 48, 17);
		panel_2.add(label_5);

		datePicker = new JXDatePicker();
		datePicker.getEditor().setFont(new Font("宋体", Font.BOLD, 12));
		datePicker.getEditor().setEditable(false);
		datePicker.setFormats("yyyy-MM-dd");
		datePicker.setDate(DateHelper.currentDate());

		datePicker.setBounds(64, 0, 122, 23);
		datePicker.getEditor().setColumns(22);
		panel_2.add(datePicker);

		JLabel label_7 = new JLabel("类型");

		label_7.setBounds(217, 3, 24, 17);
		panel_2.add(label_7);

		JLabel label_9 = new JLabel("货号");
		label_9.setBounds(425, 3, 24, 17);
		panel_2.add(label_9);

		JLabel label_10 = new JLabel("名称");
		label_10.setBounds(32, 83, 24, 17);
		panel_2.add(label_10);

		final JTree tree = getJTree();

		jtreeComboBox = new JTreeComboBox(tree, true);//
		jtreeComboBox.setBounds(245, 3, 152, 21);
		panel_2.add(jtreeComboBox);

		JLabel label_11 = new JLabel("颜色");
		label_11.setBounds(32, 56, 24, 17);
		panel_2.add(label_11);

		textColor = new JTextField();

		textColor.setBounds(64, 53, 122, 23);
		panel_2.add(textColor);
		textColor.setColumns(10);

		JLabel label_12 = new JLabel("规格");
		label_12.setBounds(217, 56, 24, 17);
		panel_2.add(label_12);

		textGuige = new JTextField();

		textGuige.setBounds(245, 53, 124, 23);
		panel_2.add(textGuige);
		textGuige.setColumns(10);

		JLabel label_13 = new JLabel("进货价");
		label_13.setBounds(22, 30, 36, 17);
		panel_2.add(label_13);

		jinHuoQiange = new JTextField();
		String dubString = "1234567890.";
		jinHuoQiange.setDocument(new LimitedDocument(20, dubString));
		jinHuoQiange.setBounds(64, 27, 122, 23);
		panel_2.add(jinHuoQiange);
		jinHuoQiange.setColumns(10);

		JLabel label_14 = new JLabel("销售价");
		label_14.setBounds(205, 30, 36, 17);
		panel_2.add(label_14);

		xiaoshoujiage = new JTextField();
		xiaoshoujiage.setDocument(new LimitedDocument(20, dubString));
		xiaoshoujiage.setBounds(245, 27, 124, 23);
		panel_2.add(xiaoshoujiage);
		xiaoshoujiage.setColumns(10);

		JLabel label_15 = new JLabel("进货数量");

		label_15.setBounds(401, 30, 48, 17);
		panel_2.add(label_15);

		shuliang = new JTextField();
		shuliang.setDocument(new LimitedDocument(20, dubString));
		shuliang.setBounds(459, 27, 124, 23);
		panel_2.add(shuliang);
		shuliang.setColumns(10);

		JLabel label_16 = new JLabel("供应商");
		label_16.setHorizontalAlignment(SwingConstants.RIGHT);
		label_16.setBounds(278, 83, 58, 17);
		panel_2.add(label_16);

		gongyinshang = new JTextField();

		gongyinshang.addFocusListener(new FocusAdapter() {

			ActionListener serviceListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// jPopupMenu.setVisible(false);
					gongyinshang.setText(((JMenuCatNoItem) e.getSource())
							.getText());
				}
			};

			class JMenuCatNoItem extends javax.swing.JMenuItem {
				JMenuCatNoItem(String text) {
					super(text);
					addActionListener(serviceListener);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				boolean issuggest = SysEnv.getInstance().isSuggest();
				if (issuggest) {
					SuppliersDAOImpl suppliersMoudle = DAOContentFactriy
							.getSuppliersDAO();

					java.util.List list = suppliersMoudle.getSuppliers();
					if (!list.isEmpty()) {
						JPopupMenu jPopupMenu = new JPopupMenu();
						// jPopupMenu.show(gongyinshang, 0,
						// gongyinshang.getY());
						int height = 0;
						for (int i = 0; i < list.size(); i++) {
							Suppliers suppliers = (Suppliers) list.get(i);

							JMenuItem jMenuItem = new JMenuCatNoItem(suppliers
									.getSuppliersName());
							height = height + 25;
							jPopupMenu.add(jMenuItem);
						}

						jPopupMenu.show(gongyinshang, 0,
								gongyinshang.getY() - 135);
						jPopupMenu.setPopupSize(305, height);
						gongyinshang.requestFocus();
					}
				}

			}

		});
		gongyinshang.setBounds(337, 80, 246, 23);
		panel_2.add(gongyinshang);
		gongyinshang.setColumns(10);

		isAddcheckBox = new JCheckBox("添加的新货是否累加在已有货物库存中");

		isAddcheckBox.setBounds(59, 109, 310, 23);
		panel_2.add(isAddcheckBox);
		huohao = new JTextField();
		huohao.addFocusListener(new FocusAdapter() {
			StockSuggestDataJTableImpl stockSuggestDataJTableImpl = new StockSuggestDataJTableImpl();

			public void focusLost(FocusEvent e) {
				getOldData();
			}

			@Override
			public void focusGained(FocusEvent e) {
				Object ob = jtreeComboBox.getSelectedItemValue();
				if (ob != null) {
					Option op = (Option) ob;

					final JTable jTable = stockSuggestDataJTableImpl
							.getStockData(op.getText());
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

						jPopupMenu.show(huohao, 0, huohao.getY() - h - 35);
						jTable.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent arg0) {
								int nRow = jTable.getSelectedRow();
								Object objSel = jTable.getValueAt(nRow, 0);
								Object catno = jTable.getValueAt(nRow, 1);
								jPopupMenu.setVisible(false);
								huohao.setText(String.valueOf(catno));
								addOldStockIDlable.setText(String
										.valueOf(objSel));
								hidcataNo.setText(String.valueOf(catno));
								shuliang.requestFocus();
								setLableIco(String.valueOf(catno));
							}
						});

					}
				}
			}
		});
		huohao.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					shuliang.requestFocus();
					getOldData();
				}
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		huohao.setBounds(459, 0, 124, 23);
		panel_2.add(huohao);
		huohao.setColumns(10);

		huowuName = new JTextField();

		huowuName.setBounds(64, 80, 228, 23);
		panel_2.add(huowuName);
		huowuName.setColumns(10);

		JButton button_2 = new JButton("保  存");

		button_2.setBounds(498, 106, 85, 26);
		panel_2.add(button_2);

		modifyUid = new JLabel("");
		modifyUid.setVisible(false);
		modifyUid.setBounds(603, 78, 61, 28);
		panel_2.add(modifyUid);

		JLabel label_4 = new JLabel("当前库存");
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setBounds(390, 57, 59, 15);
		panel_2.add(label_4);

		kucunshuliang = new JTextField();
		kucunshuliang.setEditable(false);
		kucunshuliang.setBackground(new Color(255, 255, 153));
		kucunshuliang.setText("0");

		kucunshuliang.setBounds(459, 54, 124, 21);
		panel_2.add(kucunshuliang);
		kucunshuliang.setColumns(10);

		cancleButton = new JButton("放  弃");
		cancleButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cancleButton.setVisible(false);
				clearInput(true);
			}
		});
		cancleButton.setVisible(false);
		cancleButton.setBounds(395, 106, 85, 26);

		panel_2.add(cancleButton);

		JButton button = new JButton("");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				StockTypeDialog stockTypeDialog = new StockTypeDialog(table
						.getParent(), new StockTypeDialog.CallBack() {
					public void updateView() {
						jtreeComboBox.setTree(getJTree());
					}
				});
				stockTypeDialog.setVisible(true);
			}
		});
		button.setBorder(null);
		button.setIconTextGap(0);
		button.setIcon(new ImageIcon(StockPanel.class
				.getResource("/com/fafasoft/flow/resource/image/list-add.png")));
		button.setBounds(398, 3, 21, 21);
		panel_2.add(button);

		addOldStockIDlable = new JLabel("");
		addOldStockIDlable.setVisible(false);
		addOldStockIDlable.setBounds(593, 31, 54, 15);
		panel_2.add(addOldStockIDlable);

		hidcataNo = new JLabel("");
		hidcataNo.setVisible(false);

		final JButton btnNewButton = new JButton("");
		btnNewButton.setIconTextGap(0);
		btnNewButton.setFocusable(false);
		// btnNewButton.setPreferredSize(new Dimension(60, 23));
		btnNewButton.setBorderPainted(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorder(null);
		btnNewButton.setMargin(new Insets(0, 2, 0, 2));
		btnNewButton.setIcon(new ImageIcon(StockPanel.class
				.getResource("/com/fafasoft/flow/resource/image/addpic.png")));
		btnNewButton.setBounds(710, 110, 18, 18);
		btnNewButton.setToolTipText("添加图片");
		panel_2.add(btnNewButton);

		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton.setBorderPainted(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton.setBorderPainted(false);
			}

			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("选择图片文件");
                chooser.setLocale( Locale.SIMPLIFIED_CHINESE);
            	chooser.setAcceptAllFileFilterUsed(false);
				// Add the preview pane.
				chooser.setAccessory(new ImagePreview(chooser));
				chooser.setFileFilter(new ImageFilter());
				int returnVal = chooser.showOpenDialog(MainWindows
						.getInstance());
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					try {
						File outfile = new File("./config/images/");

						if (outfile != null && !outfile.exists()) {
							outfile.mkdirs();
						}

						BufferedImage originalImage = ImageIO.read(chooser
								.getSelectedFile());

						BufferedImage thumbnail = Thumbnails.of(originalImage)
								.size(110, 113).asBufferedImage();
						Image img = Toolkit.getDefaultToolkit().createImage(
								thumbnail.getSource());
						lblNewLabelIco.setIcon(new ImageIcon(img));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		hidcataNo.setBounds(593, 56, 54, 15);
		panel_2.add(hidcataNo);

		final JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(StockPanel.class
				.getResource("/com/fafasoft/flow/resource/image/delpic.png")));
		btnNewButton_1.setBounds(688, 110, 18, 18);
		btnNewButton_1.setIconTextGap(0);
		btnNewButton_1.setFocusable(false);
		// btnNewButton.setPreferredSize(new Dimension(60, 23));
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorder(null);
		btnNewButton_1.setToolTipText("清除图片");
		panel_2.add(btnNewButton_1);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton_1.setBorderPainted(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton_1.setBorderPainted(false);
			}
			public void mouseClicked(MouseEvent e) {
				lblNewLabelIco.setIcon(null);
			}
		});

		lblNewLabelIco = new JLabel("");
		lblNewLabelIco.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabelIco.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabelIco.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabelIco.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u8D27\u7269\u56FE\u7247", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		lblNewLabelIco.setIcon(null);
		lblNewLabelIco.setBounds(613, 0, 122, 136);
		panel_2.add(lblNewLabelIco);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addStock();
			}
		});

		initTadayFow();
	}

	public void clickedTable() {
		int nCol = table.getSelectedColumn();
		int nRow = table.getSelectedRow();
		Object objSel = table.getValueAt(nRow, nCol);
		DefaultTableModel defaultTableModel = (DefaultTableModel) table
				.getModel();
		if (objSel != null && objSel instanceof String) {
			if ("删除".equals(String.valueOf(objSel))) {
				int response = JOptionPane.showConfirmDialog(null, "确定删除此条数据?",
						"删除库存数据", JOptionPane.YES_NO_OPTION);
				switch (response) {
				case JOptionPane.YES_OPTION:
					deleteRow();
				case JOptionPane.NO_OPTION:
				case JOptionPane.CLOSED_OPTION:
				}
			}
		}
	}

	private void initTadayFow() {
		DefaultTableModel defaultTableModel = (DefaultTableModel) table
				.getModel();
		if (defaultTableModel.getRowCount() == 0) {
			StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
			java.util.List list = stockMoudle.getStockByTaday();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Stock formdata = (Stock) list.get(i);
					Object[] rowData = new Object[] { formdata.getId(),
							formdata.getCatno(), formdata.getAmount(),
							formdata.getCostprice(), formdata.getTotal(),
							formdata.getSellprice(), formdata.getType(),
							formdata.getStockname(), formdata.getSpecif(),
							formdata.getColor(), formdata.getDate(), "删除" };
					defaultTableModel.insertRow(0, rowData);
				}
				table.setRowSelectionInterval(0, 0);
			}
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			deltail();
		}
	}

	private void deltail() {
		BigDecimal lrtotalcost = new BigDecimal(0);
		double num = 0;
		DefaultTableModel defaultTableModel = (DefaultTableModel) table
				.getModel();
		int rows = defaultTableModel.getRowCount();
		for (int i = 0; i < rows; i++) {
			// 数量
			double amounts = (Double) defaultTableModel.getValueAt(i, 2);
			num = num + amounts;
			// 总计
			BigDecimal lr = (BigDecimal) defaultTableModel.getValueAt(i, 4);
			lrtotalcost = lrtotalcost.add(lr);
		}
		label_1.setText(String.valueOf(num));
		label_3.setText(lrtotalcost.toString());
	}

	private void modify(Stock stockold) {
		modifyUid.setText(stockold.getId());
		TreePath treePath = jtreeComboBox.findInTree(stockold.getType());

		if (treePath != null) {
			jtreeComboBox.setSelectedItem(treePath);
		}
		datePicker.getEditor().setText(stockold.getDate());
		huohao.setText(stockold.getCatno());
		huowuName.setText(stockold.getStockname());
		jinHuoQiange.setText(stockold.getCostprice().toString());
        xiaoshoujiage.setText(stockold.getSellprice().toString());
		shuliang.setText(String.valueOf(stockold.getAmount()));
		textColor.setText(stockold.getColor());
		textGuige.setText(stockold.getSpecif());
		gongyinshang.setText(stockold.getSuppliers());
		cancleButton.setVisible(true);
	
		setLableIco(stockold.getCatno());
	}

	private void addStock() {
		String catno = huohao.getText();
		String name = huowuName.getText();

		String color = textColor.getText();
		String specif = textGuige.getText();
		String costprice = jinHuoQiange.getText();
		String amount = shuliang.getText();
		String sellprice = xiaoshoujiage.getText();
		String suppliersName = gongyinshang.getText();
		String date = datePicker.getEditor().getText();
		String nows = DateHelper.getNowDateTime();
		if (date == null || date.trim().length() == 0) {
			date = DateHelper.getNowDateTime();
		}
		long now = Long.parseLong(nows.replaceAll("-", ""));
		long e = Long.parseLong(date.replaceAll("-", ""));

		if (e > now) {
			GuiUtils.toolTips(datePicker.getEditor(), "输入时间错误！只能录入今天以前的进货数据");
			return;
		}

		if (jtreeComboBox.getSelectedItemValue() == null) {
			GuiUtils.toolTips(jtreeComboBox, "请设置货物类型!");
			return;
		}
		if (catno == null || catno.trim().length() == 0) {
			GuiUtils.toolTips(huohao, "请输入货物号码");
			return;
		}

		if (costprice == null || costprice.trim().length() == 0) {
			GuiUtils.toolTips(jinHuoQiange, "请设置进货价");
			return;
		}
		if (sellprice == null || sellprice.trim().length() == 0) {
			GuiUtils.toolTips(xiaoshoujiage, "请输入货物销售价格");
			return;
		}
		if (amount == null || amount.trim().length() == 0) {
			GuiUtils.toolTips(shuliang, "请输入进货数量");
			return;
		}
		if (name == null || name.trim().length() == 0) {
			GuiUtils.toolTips(huowuName, "请输入货物名称");
			return;
		}
		BigDecimal bigDecostprice = new BigDecimal(costprice);
		BigDecimal bigDesellprice = new BigDecimal(sellprice);
		// if (bigDecostprice.compareTo(new BigDecimal(0)) == 0) {
		// jinHuoQiange.setText("");
		// toolTips(jinHuoQiange, "进货价应该大于零,请重新输入!");
		// return;
		// }
		if (bigDesellprice.compareTo(bigDecostprice) == -1) {
			// xiaoshoujiage.setText("");
			GuiUtils.toolTips(xiaoshoujiage, "输入货物销售价格小于进货价,请重新输入!");
			return;
		}
		double amounts = Double.parseDouble(amount);
		if (amounts == 0) {
			shuliang.setText("");
			GuiUtils.toolTips(shuliang, "输入进货数量应该大于零！");
			return;
		}
		Option type = (Option) jtreeComboBox.getSelectedItemValue();
		BigDecimal costps = bigDecostprice
				.multiply(BigDecimal.valueOf(amounts));
		Stock stock = null;
		String uid = modifyUid.getText().trim();
		StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
		if (uid.length() > 0) {
			stock = stockMoudle.getStockByID(uid);
		} else {
			stock = new Stock();
			String id = String.valueOf(UUID.randomUUID().toString()
					.replaceAll("-", ""));
			stock.setId(id);
		}
		stock.setSyamount(amounts);
		stock.setAmount(amounts);
		stock.setCatno(catno);
		stock.setCostprice(bigDecostprice);
		stock.setSellprice(bigDesellprice);
		stock.setTotal(costps);
		stock.setType(type.getText());
		stock.setDate(date);
		if (specif.trim().length() > 0) {
			stock.setSpecif(specif);
		}
		if (color.trim().length() > 0) {
			stock.setColor(color);
		}
		stock.setStockname(name);
		stock.setStockFlag(Constant.STCOK_TYPE_JINHUO);
		stock.setRecorddate(DateHelper.getNowDateTime());
		stock.setSuppliers(suppliersName);

		// System.out.println("uid===="+uid+"stock.getId()=="+stock.getId());
		Object[] rowData = new Object[] { stock.getId(), stock.getCatno(),
				stock.getAmount(), stock.getCostprice(), stock.getTotal(),
				stock.getSellprice(), stock.getType(), stock.getStockname(),
				stock.getSpecif(), stock.getColor(), stock.getDate(), "删除" };

		if (uid.length() > 0) {
			stockMoudle.updateStock(stock);
			int row = table.getSelectedRow();
			DefaultTableModel tableModel = (DefaultTableModel) this.table
					.getModel();
			tableModel.removeRow(row);
			tableModel.insertRow(row, rowData);
		} else {
			if (isAddcheckBox.isSelected()) {
				String oldStockid = addOldStockIDlable.getText();
				Stock oldStock = null;
				// System.out.println("oldStockid=="+oldStockid+"---stock.getCatno()---"+stock.getCatno()+"====hidcataNo.getText()==="+hidcataNo.getText());
				if (oldStockid.length() > 0
						&& hidcataNo.getText().equals(stock.getCatno())) {
					oldStock = stockMoudle.getStockByID(oldStockid);
				} else {
					oldStock = stockMoudle.getStockByNo(stock.getCatno());
				}
				// System.out.println("---stock---"+oldStock.getId());
				if (oldStock != null) {
					oldStock.setAmount(stock.getAmount() + oldStock.getAmount());
					double sy = stock.getSyamount() + oldStock.getSyamount();
					if (stock.getCostprice().compareTo(oldStock.getCostprice()) != 0) {// 平均价格
						BigDecimal jinhuoZe = stock.getCostprice().multiply(
								BigDecimal.valueOf(stock.getAmount()));
						BigDecimal cunkunZe = oldStock.getCostprice().multiply(
								BigDecimal.valueOf(oldStock.getSyamount()));
						BigDecimal junjia = (jinhuoZe.add(cunkunZe)).divide(
								BigDecimal.valueOf(sy), 2,
								BigDecimal.ROUND_HALF_EVEN);
						oldStock.setCostprice(junjia);
					}
					oldStock.setSyamount(sy);
					stockMoudle.updateStock(oldStock);
					rowData[0] = oldStock.getId();
				} else {
					stockMoudle.add(stock);
				}
			} else {
				stockMoudle.add(stock);
			}

			DefaultTableModel defaultTableModel = (DefaultTableModel) table
					.getModel();
			int rows = defaultTableModel.getRowCount();
			if (rows == 0) {
				defaultTableModel.insertRow(0, rowData);
			} else if (rows > 0) {
				defaultTableModel.insertRow(0, rowData);
			}
			int nRow = table.getSelectedRow();
			int rowCount = table.getRowCount();
			if (rowCount == 0 || nRow == -1) {
				table.setRowSelectionInterval(0, 0);
			} else {
				table.setRowSelectionInterval(nRow - 1, nRow - 1);
			}
		}

		if (lblNewLabelIco.getIcon() != null && lblNewLabelIco.getIcon().getIconWidth()>-1 &&
				lblNewLabelIco.getIcon().getIconHeight() >-1) {
			ImageIcon imageIcon = (ImageIcon) lblNewLabelIco.getIcon();
			//System.out.println("imageIcon="+imageIcon);
			BufferedImage originalImage = GuiUtils.getBufferedImageFromImage(imageIcon
					.getImage(),imageIcon.getIconWidth(),imageIcon.getIconHeight());

			File outFile = new File("./config/images",
					String.valueOf(rowData[1]) + ".png");
			try {
				Thumbnails
						.of(originalImage)
						.size(imageIcon.getIconWidth(),
								imageIcon.getIconHeight()).toFile(outFile);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else if(uid.length()>0){  //图片为null，并且是修改操作
            stockImageService.deleteImageFile(String.valueOf(rowData[1]));
        }

		hidcataNo.setText("");
		addOldStockIDlable.setText("");
		lblNewLabelIco.setIcon(null);
		clearInput(false);
		deltail();
	}

	private void clearInput(boolean isClearType) {
		if (isClearType) {
			jtreeComboBox.setSelectedItem(null);
		}
		textColor.setText("");
		textGuige.setText("");
		jinHuoQiange.setText("");
		xiaoshoujiage.setText("");
		shuliang.setText("");
		gongyinshang.setText("");
		huowuName.setText("");
		modifyUid.setText("");
		kucunshuliang.setText(String.valueOf("0"));
		
	}

	private void getOldData() {
		String catno = huohao.getText();
		if (catno != null && catno.trim().length() > 0) {
			StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
			Stock stock = stockMoudle.getStockByNo(catno);
			if (stock != null) {
				huowuName.setText(stock.getStockname());
				jinHuoQiange.setText(stock.getCostprice().toString());
				xiaoshoujiage.setText(stock.getSellprice().toString());
				String type = stock.getType();
				TreePath treePath = jtreeComboBox.findInTree(type);

				if (treePath != null) {
					jtreeComboBox.setSelectedItem(treePath);
				}

				textColor.setText(stock.getColor());
				textGuige.setText(stock.getSpecif());
				gongyinshang.setText(stock.getSuppliers());
				double kucunshl = stockMoudle.sumStockSyAmount(catno);
				kucunshuliang.setText(String.valueOf(kucunshl));
				isAddcheckBox.setSelected(true);
				setLableIco(stock.getCatno());
			} else {
				isAddcheckBox.setSelected(false);
				String uid = modifyUid.getText().trim();
				if (uid.length() == 0) {
					clearInput(false);
				}
			}
			
		}
	}
    private void setLableIco(String catno){
    	lblNewLabelIco.setIcon(stockImageService.getImageIcon(catno));
    }

	private void deleteRow() {
		int nRow = table.getSelectedRow();
		DefaultTableModel defaultTableModel = (DefaultTableModel) table
				.getModel();
		String id = String.valueOf(defaultTableModel.getValueAt(nRow, 0));
		final String catno = String.valueOf(defaultTableModel.getValueAt(nRow, 1));
		//System.out.println("catno==" + catno);
		final StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();

		stockMoudle.deleteById(id);
		defaultTableModel.removeRow(nRow);
		
		deltail();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				java.util.List stocks = stockMoudle.getStockByCatNo(catno, 1, 3);
				if (stocks == null || stocks.size() == 0) {
                    stockImageService.deleteImageFile(catno);
				}
			}
		});
	}

	class DeleteAction extends AbstractAction {
		public DeleteAction(String label, Icon icon) {
			super(label, icon);
		}

		public void actionPerformed(ActionEvent evt) {

			int response = JOptionPane.showConfirmDialog(null, "确定删除此条数据?",
					"删除库存数据", JOptionPane.YES_NO_OPTION);
			switch (response) {
			case JOptionPane.YES_OPTION:
				deleteRow();
				break;

			}
		}
	}

	class ModifyAction extends AbstractAction {
		public ModifyAction(String label, Icon icon) {
			super(label, icon);
		}

		public void actionPerformed(ActionEvent evt) {
			int nRow = table.getSelectedRow();

			String id = String.valueOf(table.getValueAt(nRow, 0));

			StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();

			Stock stockold = stockMoudle.getStockByID(id);
			modify(stockold);

		}
	}

	private class TablePopupMenu extends JPopupMenu {
		private final Action deleteAction = new DeleteAction("  删除", null);

		public TablePopupMenu() {
			super();
			add(new ModifyAction("  修改", null));
			// add(new ClearAction("clearSelection", null));
			addSeparator();
			add(deleteAction);
			super.setPopupSize(80, 40);
		}

		@Override
		public void show(Component c, int x, int y) {
			int[] l = table.getSelectedRows();
			deleteAction.setEnabled(l != null && l.length > 0);
			super.show(c, x, y);
		}
	}
}
