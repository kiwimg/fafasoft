package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.AccountsDaoImpl;
import com.fafasoft.flow.dao.impl.SuppliersDAOImpl;
import com.fafasoft.flow.pojo.Accounts;
import com.fafasoft.flow.pojo.Suppliers;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.widget.AccountDialog;
import com.fafasoft.flow.util.GuiUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
public class SuppliersAccountPanel extends JPanel implements LazyPageContent {

	private JTextField textField;
	private JTable table;
	private JTable table_1;

	public SuppliersAccountPanel() {
		setLayout(new BorderLayout(0, 0));
		//initPanel();
	}

	public void initPanel() {

		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(10, 30));
		panel.setPreferredSize(new Dimension(10, 30));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(null);

		JLabel label = new JLabel("供应商名称/电话/联系人");
		label.setBounds(12, 8, 139, 16);
		panel.add(label);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					query();
				}
			}
		});
		textField.setFont(new Font("宋体", Font.BOLD, 12));
		textField.setBounds(151, 5, 147, 23);
		panel.add(textField);
		textField.setColumns(10);

		JButton button = new JButton("查询");
	
		button.setBounds(310, 4, 88, 25);
		panel.add(button);
		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setBorder(new TitledBorder(null, "供应商信息[双击行增加供应商往来帐]",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
    	table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(25);
      
        table.setModel(new DefaultTableModel(
                null,
                new String[]{
                        "no", "\u4F9B\u5E94\u5546\u540D\u79F0", "\u5730\u5740", "\u8054\u7CFB\u4EBA", "\u8054\u7CFB\u7535\u8BDD", "QQ", "\u4F20\u771F", "\u90AE\u7BB1", "\u90AE\u653F\u7F16\u7801", "\u5907\u6CE8"
                }
        ) {
            boolean[] columnEditables = new boolean[]{
                    false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        table.getColumnModel().getColumn(0).setResizable(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
		scrollPane.setViewportView(table);
	
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "供应商往来帐[双击行修改供应商往来帐]",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane_2.setPreferredSize(new Dimension(10, 300));
		add(scrollPane_2, BorderLayout.SOUTH);

		table_1 = new JTable();
		table_1.getTableHeader().setReorderingAllowed(false);
		table_1.setRowHeight(25);
		
		table_1.setModel(new DefaultTableModel(null, new String[] { "Cid","customId",
				"\u5BA2\u6237\u540D\u79F0", "\u8D26\u52A1\u5206\u7C7B",
				"\u91D1\u989D", "\u72B6\u6001", "\u5907\u6CE8","日期",  "操作"}) {
			Class[] columnTypes = new Class[] {String.class, String.class, String.class,
					String.class, Double.class, String.class, String.class, String.class  , String.class  };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false,false, false, false,
					false, false, false, false, false  };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_1.getColumnModel().getColumn(0).setResizable(false);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(0);
		table_1.getColumnModel().getColumn(0).setMinWidth(0);
		table_1.getColumnModel().getColumn(0).setMaxWidth(0);
		table_1.getColumnModel().getColumn(1).setResizable(false);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(0);
		table_1.getColumnModel().getColumn(1).setMinWidth(0);
		table_1.getColumnModel().getColumn(1).setMaxWidth(0);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(135);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(119);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(88);
		table_1.getColumnModel().getColumn(5).setPreferredWidth(40);
		table_1.getColumnModel().getColumn(6).setPreferredWidth(241);
		table_1.getColumnModel().getColumn(7).setPreferredWidth(60);
		table_1.getColumnModel().getColumn(8).setPreferredWidth(20);
		scrollPane_2.setViewportView(table_1);
		SuppliersDAOImpl suppliersDAO = DAOContentFactriy.getSuppliersDAO();
		
		List list = suppliersDAO.getSuppliers();
		pageData(list);
	
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int res  = table.getSelectedRow();
					String no = String.valueOf(table.getValueAt(res, 0));
					String name =String.valueOf(table.getValueAt(res, 1));
				  	AccountDialog customDialog = new AccountDialog(MainWindows.getInstance(), "增加供应商往来帐",
				  			null,Constant.SUPPLIERS_ACCOUNT,no,name,table_1);
	                customDialog.setVisible(true);
				}else {
					int res  = table.getSelectedRow();
					AccountsDaoImpl accountsDao =DAOContentFactriy.getAccountsDao();
					String no = String.valueOf(table.getValueAt(res, 0));
				
					List list = accountsDao.getByTargetId(no,Constant.SUPPLIERS_ACCOUNT);

                    GuiUtils.apageData(list, table_1,Constant.SUPPLIERS);
				}

			}
		});
		
		table_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				int selrow = table_1.getSelectedRow();
			
				if(selrow >-1 && e.getButton()==1) {
					String id = String.valueOf(table_1.getValueAt(selrow, 0));
					String no = String.valueOf(table_1.getValueAt(selrow, 1));
					String name =String.valueOf(table_1.getValueAt(selrow, 2));
					AccountsDaoImpl accountsDao = DAOContentFactriy.getAccountsDao();
					
	                
	                int nCol = table_1.getSelectedColumn();
	                Object objSel = table_1.getValueAt(selrow, nCol);
	                if ("删除".equals(String.valueOf(objSel).trim())) {
						int response = JOptionPane.showConfirmDialog(null,
								"确定删除此供应商往来帐?", "删除供应商往来帐数据",
								JOptionPane.YES_NO_OPTION);
						switch (response) {
						case JOptionPane.YES_OPTION:
							DefaultTableModel tableModel = (DefaultTableModel) table_1
									.getModel();						
						     accountsDao.deleteById(id);
						     int nselrow = selrow-1>0?selrow-1:0;
						     tableModel.removeRow(selrow);
						    
						     table_1.setColumnSelectionInterval(nselrow, nselrow);
						case JOptionPane.NO_OPTION:

						}
					}else{
						Accounts accounts = accountsDao.getById(id);
					  	AccountDialog customDialog = new AccountDialog(MainWindows.getInstance(), 
					  			"修改供应商往来帐",accounts,Constant.SUPPLIERS_ACCOUNT,no,name,table_1);
		                customDialog.setVisible(true);
					}
				}
				
			}
		});
		button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				query();
			}
		});
		
	}
	private void query() {
		String cu = textField.getText();
		SuppliersDAOImpl suppliersDAO = DAOContentFactriy.getSuppliersDAO();
		
		java.util.List list = null;
		if (cu.trim().length() > 0) {
			list = suppliersDAO.getSuppliers(cu, cu, cu, 0, 10000);

		} else {
			list = suppliersDAO.getSuppliers();
		}
		if (list == null || list.size() == 0) {
	     	JOptionPane.showMessageDialog(MainWindows.getInstance(),"没有查询到此供应商信息", "查询供应商",JOptionPane.WARNING_MESSAGE);
			
		} else {

			if (list != null && list.size() == 1) {
				Suppliers suppliers = (Suppliers) list.get(0);
				AccountsDaoImpl accountsDao = DAOContentFactriy.getAccountsDao();

				List alist = accountsDao.getByTargetId(suppliers.getSuppliersno(),Constant.SUPPLIERS_ACCOUNT);
                GuiUtils.apageData(alist, table_1,"供应商");
			}		clear(table_1);
			pageData(list);
		}

	}

	private void pageData(List list) {
		clear(table);
		if (list != null && list.size() > 0) {
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			for (int i = 0; i < list.size(); i++) {
				 Suppliers suppliers = (Suppliers) list.get(i);
	                Object[] rowData = new Object[]{
	                        suppliers.getSuppliersno(),
	                        suppliers.getSuppliersName(),
	                        suppliers.getAddress(),
	                        suppliers.getContact(),
	                        suppliers.getPhone(),
	                        suppliers.getQq(),
	                        suppliers.getFax(),
	                        suppliers.getEmail(),
	                        suppliers.getZipcode(),
	                        suppliers.getRemarks(),
	                };
				tableModel.insertRow(0, rowData);
			}
		
		}
	}

	public void clear(JTable tablex) {
		DefaultTableModel tableModel = (DefaultTableModel) tablex.getModel();
		if (tableModel.getRowCount() > 0) {
			int rows = tableModel.getRowCount();
			for (int i = 0; i < rows; i++) {
				tableModel.removeRow(0);
			}
		}
	}

}
