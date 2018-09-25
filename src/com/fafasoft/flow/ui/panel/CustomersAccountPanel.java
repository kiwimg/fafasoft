package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.AccountsDaoImpl;
import com.fafasoft.flow.dao.impl.CustomDAOImpl;
import com.fafasoft.flow.pojo.Accounts;
import com.fafasoft.flow.pojo.Custom;
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

public class CustomersAccountPanel extends BaseJPanel implements LazyPageContent{
	private JTextField textField;
	private JTable table;
	private JTable table_1;

	public CustomersAccountPanel() {
		setLayout(new BorderLayout(0, 0));
		//initPanel();
	}
	public void initPanel() {
		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(10, 30));
		panel.setPreferredSize(new Dimension(10, 30));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(null);

		JLabel label = new JLabel("客户号/姓名/电话");
		label.setBounds(12, 8, 107, 16);
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
		textField.setBounds(121, 5, 147, 23);
		panel.add(textField);
		textField.setColumns(10);

		JButton button = new JButton("查询");
	
		button.setBounds(280, 4, 88, 25);
		panel.add(button);
		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setBorder(new TitledBorder(null, "客户信息[双击行增加客户往来帐]",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(scrollPane, BorderLayout.CENTER);

		table = new JTable();

		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(25);
		table.setModel(new DefaultTableModel(null, new String[] {
				"\u5BA2\u6237\u53F7", "\u5BA2\u6237\u59D3\u540D",
				"\u8054\u7CFB\u7535\u8BDD", "客户类型",
				"\u79EF\u5206", "\u6D88\u8D39\u91D1\u989D",
				"\u6D88\u8D39\u6B21\u6570" }) {
			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class, String.class, String.class, String.class,
					String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false, false, false};

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		scrollPane.setViewportView(table);
	
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "客户往来帐[双击行修改客户往来帐]",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane_2.setPreferredSize(new Dimension(10, 300));
		add(scrollPane_2, BorderLayout.SOUTH);

		table_1 = new JTable();
		table_1.getTableHeader().setReorderingAllowed(false);
		table_1.setRowHeight(25);
		
		table_1.setModel(new DefaultTableModel(null, new String[] { "Cid","customId",
				"\u5BA2\u6237\u540D\u79F0", "\u8D26\u52A1\u5206\u7C7B",
				"\u91D1\u989D", "\u72B6\u6001", "\u5907\u6CE8", "日期",  "操作"}) {
			Class[] columnTypes = new Class[] {String.class, String.class, String.class,
					String.class, Double.class, String.class, String.class, String.class , String.class  };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false,false, false, false,
					false, false, false, false, false };

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
		CustomDAOImpl customMoudle = DAOContentFactriy.getCustomDAO();
		List list = customMoudle.getCustoms();
		pageData(list);
	
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int res  = table.getSelectedRow();
					String no = String.valueOf(table.getValueAt(res, 0));
					String name =String.valueOf(table.getValueAt(res, 1));
				  	AccountDialog customDialog = new AccountDialog(MainWindows.getInstance(), "增加客户往来帐",
				  			null,Constant.CUSTOMER_ACCOUNT,no,name,table_1);
	                customDialog.setVisible(true);
				}else {
					int res  = table.getSelectedRow();
					if(res >-1) {
						AccountsDaoImpl accountsDao =DAOContentFactriy.getAccountsDao();
						String no = String.valueOf(table.getValueAt(res, 0));
						List list = accountsDao.getByTargetId(no,Constant.CUSTOMER_ACCOUNT);
                        GuiUtils.apageData(list,table_1,Constant.CUSTOMERS);
					}
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
								"确定删除此客户往来帐?", "删除客户往来帐数据",
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
					  			"修改客户往来帐",accounts,Constant.CUSTOMER_ACCOUNT,no,name,table_1);
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
		CustomDAOImpl customMoudle = DAOContentFactriy.getCustomDAO();
		java.util.List list = null;
		if (cu.trim().length() > 0) {
			list = customMoudle.getCustom(cu, cu, cu);

		} else {
			list = customMoudle.getCustoms();
		}
		if (list == null || list.size() == 0) {
	     	JOptionPane.showMessageDialog(null,"没有查询到此客户信息", "查询客户",JOptionPane.WARNING_MESSAGE);
			
		} else {
			if (list != null && list.size() == 1) {
				Custom custom = (Custom) list.get(0);
				AccountsDaoImpl accountsDao = DAOContentFactriy.getAccountsDao();
				List alist = accountsDao.getByTargetId(custom.getId(),Constant.CUSTOMER_ACCOUNT);
                GuiUtils.apageData(alist,table_1,"客户");
			}
            GuiUtils.clear(table_1);
			pageData(list);
		}
	}

	private void pageData(List list) {
		GuiUtils.clear(table);
		if (list != null && list.size() > 0) {
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			for (int i = 0; i < list.size(); i++) {
				Custom custom = (Custom) list.get(i);

				Object[] rowData = new Object[] { custom.getId(),
						custom.getName(), custom.getTelephone(),
						custom.getType(), custom.getIntegration(),
						custom.getAmount(), custom.getFrequency(),

				};
				tableModel.insertRow(0, rowData);
			}
		
		}
	}
}
