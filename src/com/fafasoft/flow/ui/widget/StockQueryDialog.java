package com.fafasoft.flow.ui.widget;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.FlowLogDAOImpl;
import com.fafasoft.flow.dao.impl.StockDAOImpl;
import com.fafasoft.flow.dao.impl.UserDAOImpl;
import com.fafasoft.flow.pojo.Flowlog;
import com.fafasoft.flow.pojo.Stock;
import com.fafasoft.flow.pojo.User;
import com.fafasoft.flow.ui.ActionView;
import com.fafasoft.flow.ui.widget.table.ImageRenderer;
import com.fafasoft.flow.util.DateHelper;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import org.jdesktop.swingx.JXDatePicker;

public class StockQueryDialog extends JDialog {
	private JTextField textField;
	private JTable table;
	private JTextField textField_1;
	private JTextField textField_2;
	private final JTree tree;
	private JXDatePicker datePicker;
	public StockQueryDialog(Component owner, String tilte,
			final boolean isstock, boolean isshowCost,
			final ActionView actionView, final String[] qcondition) {
		setTitle(tilte);
		setSize(new Dimension(716, 419));
		super.setMaximumSize(new Dimension(716, 419));
		super.setMinimumSize(new Dimension(716, 419));
		setLocationRelativeTo(owner);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		super.setResizable(false);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 30));
		panel.setMinimumSize(new Dimension(10, 30));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(null);

		JLabel label = new JLabel("货号");
		label.setBounds(12, 6, 31, 17);
		panel.add(label);

		textField = new JTextField();
		textField.setBounds(41, 4, 116, 23);
		panel.add(textField);
		textField.setColumns(10);

		JLabel label_1 = new JLabel("颜色");
		label_1.setBounds(169, 6, 31, 17);
		panel.add(label_1);

		textField_1 = new JTextField();
		textField_1.setBounds(197, 4, 116, 23);
		panel.add(textField_1);
		textField_1.setColumns(10);

		JLabel label_2 = new JLabel("规格");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(319, 6, 24, 17);
		panel.add(label_2);

		textField_2 = new JTextField();
		textField_2.setBounds(349, 4, 116, 23);
		panel.add(textField_2);
		textField_2.setColumns(10);
		JButton button = new JButton("查询");
		button.setBounds(624, 2, 78, 25);
		panel.add(button);
		JLabel label_3 ;
		if(isstock) {
			label_3 = new JLabel("进货日期");
		}else {
			label_3 = new JLabel("销售日期");
		}

		label_3.setBounds(470, 7, 54, 15);
		panel.add(label_3);
		datePicker = new JXDatePicker();
		datePicker.setFormats("yyyy-MM-dd");
		datePicker.getEditor().setFont(new Font("宋体", Font.BOLD, 12));
		datePicker.setBounds(522, 3, 100, 23);
		panel.add(datePicker);
		tree =getTree(isstock);
		JScrollPane scrollPane_3 = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_3.setAutoscrolls(true);
		scrollPane_3.setBorder(null);
		scrollPane_3.setPreferredSize(new Dimension(128, 2));
		scrollPane_3.setViewportView(tree);
		getContentPane().add(scrollPane_3, BorderLayout.WEST);
		JScrollPane scrollPane = new JScrollPane();
	
		scrollPane.setBorder(new TitledBorder(null, "鼠标双击行选择出售货物",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(scrollPane, BorderLayout.CENTER);
	
		if (isstock) {
			table = getStockJTable(isshowCost);
		} else {
			table = getFlowJTable();
		}
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);
		table.setRowHeight(25);
		table.setAutoscrolls(true);
		//table.setPreferredScrollableViewportSize(new Dimension(564,300));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.getViewport().setView(table);

		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						find(isstock);
					}
				});
			}
		});
		button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						find(isstock);
					}
				});

			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					int r = table.getSelectedRow();
					if (r > -1) {
						dispose();
						Object[] arg = null;
						if (isstock) {
							arg = new Object[] { table.getValueAt(r, 0),
									table.getValueAt(r, 1),
									table.getValueAt(r, 2),
									table.getValueAt(r, 3),
									table.getValueAt(r, 4),
									table.getValueAt(r, 5),
									table.getValueAt(r, 6),
									table.getValueAt(r, 7),
									table.getValueAt(r, 8) };
						} else {
							arg = new Object[] { table.getValueAt(r, 0),
									table.getValueAt(r, 1),
									table.getValueAt(r, 2),
									table.getValueAt(r, 3),
									table.getValueAt(r, 4),
									table.getValueAt(r, 5),
									table.getValueAt(r, 6) };
						}

						actionView.actionView(arg);
					}

				}
			}
		});
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				findInit(isstock,qcondition);
			}
		});
		table.setVisible(true);
	}

	private JTable getFlowJTable() {
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(null, new String[] { "id",
				"stockid", "货号", "名称", "类型", "规格", "颜色", "销售价", "客户名称", "销售日期",
				"销售人" }) {
			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class, String.class, String.class, String.class,
					String.class, Double.class, String.class, String.class,
					String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false, false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(0);
		table.getColumnModel().getColumn(1).setMinWidth(0);
		table.getColumnModel().getColumn(1).setMaxWidth(0);
		table.getColumnModel().getColumn(2).setCellRenderer(new ImageRenderer());
		table.getColumnModel().getColumn(2).setPreferredWidth(90);
		table.getColumnModel().getColumn(3).setPreferredWidth(90);
		table.getColumnModel().getColumn(4).setPreferredWidth(50);
		
		table.getColumnModel().getColumn(5).setPreferredWidth(40);
		table.getColumnModel().getColumn(6).setPreferredWidth(40);
		table.getColumnModel().getColumn(7).setPreferredWidth(40);
		table.getColumnModel().getColumn(9).setPreferredWidth(65);
		
		table.getColumnModel().getColumn(10).setMinWidth(40);
		return table;
	}

	private JTable getStockJTable(boolean isshowCost) {
		JTable table = new JTable();

		table.setModel(new DefaultTableModel(null, new String[] { "id",
				"\u8D27\u53F7", "\u540D\u79F0", "\u7C7B\u578B", "\u89C4\u683C",
				"\u989C\u8272", "\u5269\u4F59\u6570\u91CF",
				"\u9500\u552E\u4EF7", "进货价", "进货日期" }) {
			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class, String.class, String.class, String.class,
					Double.class, Double.class, Double.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());
		table.getColumnModel().getColumn(3).setPreferredWidth(70);
		table.getColumnModel().getColumn(4).setPreferredWidth(40);
		table.getColumnModel().getColumn(5).setPreferredWidth(40);
		table.getColumnModel().getColumn(6).setPreferredWidth(56);
		table.getColumnModel().getColumn(7).setPreferredWidth(46);
	
		if (!isshowCost) {
			table.getColumnModel().getColumn(1).setPreferredWidth(120);
			
			table.getColumnModel().getColumn(2).setPreferredWidth(110);
			table.getColumnModel().getColumn(8).setResizable(false);
			table.getColumnModel().getColumn(8).setPreferredWidth(0);
			table.getColumnModel().getColumn(8).setMinWidth(0);
			table.getColumnModel().getColumn(8).setMaxWidth(0);
		}else {
			table.getColumnModel().getColumn(1).setPreferredWidth(90);
			
			table.getColumnModel().getColumn(2).setPreferredWidth(100);
			
			table.getColumnModel().getColumn(8).setPreferredWidth(46);
		}
		return table;
	}

	private void findInit(boolean isstock, String[] qcondition) {
        if(qcondition == null) {
        	qcondition = new String[] {null,null,null,null,null};
		}
		if (qcondition[1] == null) {
			DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode) tree
					.getLastSelectedPathComponent(); // 返回最后选中的结点
			String selectNodeInfo = null;
			if (selectNode != null) {
				selectNodeInfo = (String) selectNode.getUserObject();
			}
			qcondition[1] = selectNodeInfo;
		}
		textField.setText(qcondition[0]==null?"":qcondition[0]);
		textField_1.setText(qcondition[2]==null?"":qcondition[2]);
		textField_2.setText(qcondition[3]==null?"":qcondition[3]);
		if (isstock) {
			findStock( qcondition);
		} else {
			findFlow( qcondition);
		}
	}

	private void find(boolean isstock) {
		DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent(); // 返回最后选中的结点
		String selectNodeInfo = null;
		if (selectNode != null) {
			selectNodeInfo = (String) selectNode.getUserObject();
		}
		String[] qcondition = new String[] { textField.getText(),
				selectNodeInfo, textField_1.getText(), textField_2.getText() };
		if (isstock) {
			findStock(qcondition);
		} else {
			findFlow(qcondition);
		}
	}

	private void findFlow(String[] qcondition) {

		StockDAOImpl stockDAO = DAOContentFactriy.getStockDAO();
		UserDAOImpl userDAO = DAOContentFactriy.getUserDAO();
		FlowLogDAOImpl flowLog = DAOContentFactriy.getFlowLogDAO();
		DefaultTableModel tModel = (DefaultTableModel) table.getModel();
		String date = datePicker.getEditor().getText();
		List list = flowLog.getSellFlowlog(qcondition[0], qcondition[1],date, 500);
		tModel.setRowCount(0);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Flowlog flowlog = (Flowlog) list.get(i);
				Stock stock = stockDAO.getStockByID(flowlog.getStockId());
				if (stock == null) {
					stock = new Stock();
				}
				if (textField_1.getText().trim().length() > 0
						&& textField_2.getText().trim().length() > 0) {
					if (textField_1.getText()
							.equalsIgnoreCase(stock.getColor())
							&& textField_2.getText().equalsIgnoreCase(
									stock.getSpecif())) {
						User user = userDAO.getUserByid(flowlog.getUserId());

						Object[] rowData = new Object[] { flowlog.getFlowno(),
								flowlog.getStockId(), flowlog.getCatno(),
								flowlog.getStockname(), flowlog.getType(),
								stock.getSpecif(), stock.getColor(),
								flowlog.getSellprice().doubleValue(),
								flowlog.getCustomName(), flowlog.getDate(),
								user.getUsernmae() };
						tModel.insertRow(0, rowData);
					}
				} else if (textField_1.getText().trim().length() > 0) {
					if (textField_1.getText()
							.equalsIgnoreCase(stock.getColor())) {
						User user = userDAO.getUserByid(flowlog.getUserId());

						Object[] rowData = new Object[] { flowlog.getFlowno(),
								flowlog.getStockId(), flowlog.getCatno(),
								flowlog.getStockname(), flowlog.getType(),
								stock.getSpecif(), stock.getColor(),
								flowlog.getSellprice().doubleValue(),
								flowlog.getCustomName(), flowlog.getDate(),
								user.getUsernmae() };
						tModel.insertRow(0, rowData);
					}
				} else if (textField_2.getText().trim().length() > 0) {
					if (textField_2.getText().equalsIgnoreCase(
							stock.getSpecif())) {
						User user = userDAO.getUserByid(flowlog.getUserId());

						Object[] rowData = new Object[] { flowlog.getFlowno(),
								flowlog.getStockId(), flowlog.getCatno(),
								flowlog.getStockname(), flowlog.getType(),
								stock.getSpecif(), stock.getColor(),
								flowlog.getSellprice().doubleValue(),
								flowlog.getCustomName(), flowlog.getDate(),
								user.getUsernmae() };
					}
				} else {
					User user = userDAO.getUserByid(flowlog.getUserId());

					Object[] rowData = new Object[] { flowlog.getFlowno(),
							flowlog.getStockId(), flowlog.getCatno(),
							flowlog.getStockname(), flowlog.getType(),
							stock.getSpecif(), stock.getColor(),
							flowlog.getSellprice().doubleValue(),
							flowlog.getCustomName(), flowlog.getDate(),
							user.getUsernmae() };
					tModel.insertRow(0, rowData);
				}
			}
		}

	}

	private void findStock(String[] qcondition) {
		StockDAOImpl stockDAO = DAOContentFactriy.getStockDAO();
		DefaultTableModel tModel = (DefaultTableModel) table.getModel();
		String date = datePicker.getEditor().getText();
		List list = stockDAO.getStock(qcondition[0], qcondition[1],qcondition[2], qcondition[3],date);
		tModel.setRowCount(0);
		for (int i = 0; i < list.size(); i++) {
			Stock stock = (Stock) list.get(i);
			Object[] rowData = new Object[] { stock.getId(), stock.getCatno(),
					stock.getStockname(), stock.getType(), stock.getSpecif(),
					stock.getColor(), stock.getSyamount(),
					stock.getSellprice().doubleValue(),
					stock.getCostprice().doubleValue(), stock.getDate() };
			tModel.insertRow(0, rowData);
		}
	}
	private JTree getTree(boolean isstock){
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("货物类别");
	
		JTree tree = new JTree(top);

		tree.setBorder(UIManager.getBorder("ComboBox.border"));
		
		tree.setRowHeight(22);
		DefaultTreeCellRenderer r = new DefaultTreeCellRenderer();
		r.setBackgroundNonSelectionColor(null);        
		tree.setAutoscrolls(true);
		tree.setCellRenderer(r);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		ImageIcon openIco = new ImageIcon(StockQueryDialog.class
				.getResource("/com/fafasoft/flow/resource/image/pers.png"));
		ImageIcon closeIco = new ImageIcon(StockQueryDialog.class
				.getResource("/com/fafasoft/flow/resource/image/group.png"));
		r.setOpenIcon(openIco);
		r.setClosedIcon(closeIco);
		r.setLeafIcon(closeIco);
		if(isstock) {
			getStockJTree(top);
		}else {
			getFlowJTree(top);
		}
		TreeNode root = (TreeNode) tree.getModel().getRoot();
		tree.expandPath(new TreePath(root));
		
		if(tree.getModel().getChildCount(root) >0 ){
			tree.setSelectionRow(0);
			tree.setSelectionPath(new TreePath(top.getFirstChild()));
		}
		tree.setScrollsOnExpand(true);

		return tree;
    }
	private void getStockJTree(DefaultMutableTreeNode top) {
		StockDAOImpl stockDAO = DAOContentFactriy.getStockDAO();
		List list = stockDAO.getStockTypes();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String stock = (String) list.get(i);
				if(stock != null && stock.trim().length() >0) {
					DefaultMutableTreeNode category = new DefaultMutableTreeNode(
							stock);
					top.add(category);
				}
			}
		}
	}

	private void getFlowJTree(DefaultMutableTreeNode top) {
		FlowLogDAOImpl flowLogDAO = DAOContentFactriy.getFlowLogDAO();
		List list = flowLogDAO.getStockTypes(Constant.FLOW_TYPE_SELL);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String stock = (String) list.get(i);
				DefaultMutableTreeNode category = new DefaultMutableTreeNode(
						stock);
				top.add(category);

			}
	
		}
	
	}
}
