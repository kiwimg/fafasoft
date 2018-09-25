package com.fafasoft.flow.ui.widget;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.ConfigDAOImpl;
import com.fafasoft.flow.dao.impl.CustomtTypeDAOImpl;
import com.fafasoft.flow.pojo.Config;
import com.fafasoft.flow.pojo.CustomType;
import com.fafasoft.flow.ui.LimitedDocument;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.util.GuiUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;


public class CustomTypeDialog extends JDialog {
	private JPanel panel;
	private JTextField textField;
	private JFormattedTextField formattedTextField;
	private JTextField textField_3;
	private JTable table;
	private JFormattedTextField formattedTextField_1;


	public CustomTypeDialog(Component owner, final JComboBox comboBox) {
		setResizable(false);
		setTitle("客户级别设置");
		setResizable(false);

		setSize(new Dimension(493, 396));
		setLocationRelativeTo(owner);

		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		addWindowListener(new  WindowAdapter()   { 
			 public void windowClosed(java.awt.event.WindowEvent arg0){
				 comboBox.setModel(new DefaultComboBoxModel(SelectType.getCustomType()));
			 }
		});
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 105));
		panel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"),
				"\u589E\u52A0\u5BA2\u6237\u7EA7\u522B", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("会员级别");
		lblNewLabel.setBounds(22, 26, 54, 15);
		panel.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(80, 23, 112, 21);
		panel.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("积分额度");
		lblNewLabel_1.setBounds(215, 26, 54, 15);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("享受折扣");
		lblNewLabel_2.setBounds(22, 54, 54, 15);
		panel.add(lblNewLabel_2);

		JButton btnNewButton = new JButton("增加");

		btnNewButton.setBounds(291, 54, 93, 23);
		panel.add(btnNewButton);

		JLabel lblNewLabel_5 = new JLabel("10表示没有折扣，8表示八折，8.5表示八五折");
		lblNewLabel_5.setBounds(22, 80, 281, 15);
		panel.add(lblNewLabel_5);
		String dubString = "1234567890.";

		formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(269, 23, 112, 21);
		formattedTextField.setDocument( new LimitedDocument(20, dubString));
		panel.add(formattedTextField);

		formattedTextField_1 = new JFormattedTextField();
		formattedTextField_1.setBounds(80, 54, 112, 21);
		formattedTextField_1.setDocument( new LimitedDocument(20, dubString));
		panel.add(formattedTextField_1);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "\u79EF\u5206\u8BBE\u7F6E",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setPreferredSize(new Dimension(10, 40));
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("消费");
		lblNewLabel_3.setBounds(47, 15, 35, 15);
		panel_1.add(lblNewLabel_3);

		textField_3 = new JTextField();
		textField_3.setDocument( new LimitedDocument(20, dubString));
		textField_3.setBounds(80, 12, 109, 21);
		panel_1.add(textField_3);
		textField_3.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("元积 1 分");
		lblNewLabel_4.setBounds(197, 15, 54, 15);
		panel_1.add(lblNewLabel_4);

		JButton btnNewButton_1 = new JButton("设置");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				String jifen = textField_3.getText();
				if(jifen.trim().length()==0) {
					GuiUtils.toolTips(textField_3, "请输入积分比例");
					return;
				}
				ConfigDAOImpl configDAOImpl = DAOContentFactriy.getConfigDAO();
				Config config = new Config();
				config.setKey(Constant.JIFEN_BILV);
				config.setValue(jifen.trim());
				configDAOImpl.addConfigs(new Config[]{config});
			}
		});
		btnNewButton_1.setBounds(291, 11, 93, 23);
		panel_1.add(btnNewButton_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new TitledBorder(null,
				"\u5BA2\u6237\u7EA7\u522B\u5217\u8868", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setModel(new DefaultTableModel(null, new String[] { "\u5BA2\u6237\u7EA7\u522B",
				"\u79EF\u5206\u989D\u5EA6", "\u9500\u552E\u6298\u6263",
				"\u64CD\u4F5C" }) {
			Class[] columnTypes = new Class[] { String.class, Double.class,
					Double.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(3).setMinWidth(75);
		table.getColumnModel().getColumn(3).setMaxWidth(75);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				clickedTable("确定删除此条数据?", "删除会议类型数据", table);
			}
		});
		GuiUtils.columnCenter(table,"操作",60);
		scrollPane.setViewportView(table);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String customtype = textField.getText();
				if (customtype.trim().length() == 0) {
					GuiUtils.toolTips(textField, "请输入会员类型");
					return;
				}
				String jifengedu = formattedTextField.getText();
				if (jifengedu.trim().length() == 0) {
					GuiUtils.toolTips(formattedTextField, "请输入积分额度");
					return;
				}
				String zhekou = formattedTextField_1.getText();

				if (zhekou.trim().length() == 0) {
					GuiUtils.toolTips(formattedTextField_1, "请输入享受折扣");
					return;
				}
				if (Double.parseDouble(zhekou) > 10
						|| Double.parseDouble(zhekou) <= 0) {
					GuiUtils.toolTips(formattedTextField_1,
							"请输入正确的享受折扣,折扣范围在10-1之间");
					return;
				}
				CustomType customtypeObj = new CustomType();
				customtypeObj.setTypename(customtype);
				customtypeObj.setDiscount(Double.parseDouble(jifengedu));
				customtypeObj.setIntegration(Double.parseDouble(zhekou));
				CustomType[] customTypes = new CustomType[1];
				customTypes[0] = customtypeObj;
				CustomtTypeDAOImpl typeMoudle = DAOContentFactriy
						.getCustomtTypeDAO();
				typeMoudle.addCustomTypes(customTypes);
				Object[] rowdata = new Object[] { customtype,
						Double.parseDouble(jifengedu),
						Double.parseDouble(zhekou), "删除" };
				DefaultTableModel tableModel = (DefaultTableModel) table
						.getModel();
				tableModel.addRow(rowdata);
			   
			}
		});
		init();
	}

	private void clickedTable(String message, String title, JTable tableObj) {
		int nCol = tableObj.getSelectedColumn();
		int nRow = tableObj.getSelectedRow();
		Object objSel = tableObj.getValueAt(nRow, nCol);

		if (objSel != null && objSel instanceof String) {
			if ("删除".equals(String.valueOf(objSel))) {
				int response = JOptionPane.showConfirmDialog(
						MainWindows.getInstance(), message, title,
						JOptionPane.YES_NO_OPTION);
				switch (response) {
				case JOptionPane.YES_OPTION:
					DefaultTableModel tableModel = (DefaultTableModel) tableObj
							.getModel();
					String cuname = String.valueOf(tableModel.getValueAt(nRow,
							0));

					tableModel.removeRow(nRow);
					CustomtTypeDAOImpl typeMoudle = DAOContentFactriy
							.getCustomtTypeDAO();
					typeMoudle.deleteByName(cuname);
		
				case JOptionPane.NO_OPTION:
				case JOptionPane.CLOSED_OPTION:
				}
			}
		}
	}

	private void init() {
		CustomtTypeDAOImpl typeMoudle = DAOContentFactriy.getCustomtTypeDAO();
		java.util.List list = typeMoudle.getCustomtTypes();
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		for (int i = 0; i < list.size(); i++) {
			CustomType dd = (CustomType) list.get(i);
			Object[] objects = new Object[] { dd.getTypename(),
					dd.getIntegration(), dd.getDiscount(), "删除" };
			tableModel.insertRow(0, objects);
		}
	
		ConfigDAOImpl configDAOImpl = DAOContentFactriy.getConfigDAO();
		Config config = configDAOImpl.getConfig(Constant.JIFEN_BILV);
	    if(config != null){
	    	textField_3.setText(config.getValue());
	    }
	
	}
}
