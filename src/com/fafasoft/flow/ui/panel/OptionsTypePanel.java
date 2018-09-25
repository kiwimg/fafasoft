package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.OptionDAOImpl;
import com.fafasoft.flow.pojo.Option;
import com.fafasoft.flow.ui.widget.OptionsTree;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class OptionsTypePanel extends JPanel  implements LazyPageContent  {

    private JTextField textField_2;
	private JTable table_2;
	private JTable table;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public OptionsTypePanel() {
		setLayout(new BorderLayout(0, 0));
	}


	public void initPanel() {
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
                TitledBorder.TOP, null, null));
		tabbedPane.setRequestFocusEnabled(false);
		
		add(tabbedPane, BorderLayout.CENTER);

		JPanel panel_6 = new JPanel();
		ImageIcon imageIcon1 = new ImageIcon(OptionsTypePanel.class
				.getResource("/com/fafasoft/flow/resource/image/tab.png"));
		tabbedPane.addTab("货物类型", imageIcon1, panel_6, null);
		panel_6.setLayout(new BorderLayout(0, 0));
		

		//tabbedPane.addTab("New tab", null, panel_6, null);
		panel_6.setLayout(new BorderLayout(0, 0));

		JPanel panel_7 = new JPanel();
		panel_6.add(panel_7, BorderLayout.NORTH);
		GridBagLayout gbl_panel_7 = new GridBagLayout();
		gbl_panel_7.columnWidths = new int[] { 0, 232, 0 };
		gbl_panel_7.rowHeights = new int[] { 35, 0 };
		gbl_panel_7.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_7.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_7.setLayout(gbl_panel_7);

		JLabel label_3 = new JLabel("提示:用鼠标选中后点击鼠标右键进行相应操作");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.anchor = GridBagConstraints.WEST;
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 0;
		panel_7.add(label_3, gbc_label_3);
		//货物类型树形结构控制
		Option rootOption = new Option();
		rootOption.setText("货物类别");
		rootOption.setNodeId("0");
		rootOption.setParentId("0");
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(rootOption);
	
		final OptionsTree tree = new OptionsTree(top, Constant.TYPE_HW);
		
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				DefaultMutableTreeNode note = (DefaultMutableTreeNode) tree
						.getLastSelectedPathComponent(); // 返回最后选中的结点
				if(note != null) {
					if (note.getChildCount() == 0) {
						Option op = (Option) note.getUserObject();
						tree.createNodes(note, op.getNodeId(), Constant.TYPE_HW);
					}
				}
			}
		});
		JScrollPane scrollPane_3 = new JScrollPane(tree);
		scrollPane_3.setBorder(null);

		panel_6.add(scrollPane_3, BorderLayout.CENTER);
		

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("支出项目", imageIcon1, panel_1, null);
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.NORTH);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 83, 0, 148, 0, 0 };
		gbl_panel_3.rowHeights = new int[] { 42, 0 };
		gbl_panel_3.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);

		JLabel label_1 = new JLabel("支出类型");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 0, 5);
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 0;
		panel_3.add(label_1, gbc_label_1);

		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 0, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 2;
		gbc_textField_2.gridy = 0;
		panel_3.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);

		JButton button_1 = new JButton("   保 存   ");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				submmit_2();
			}
		});
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.gridx = 3;
		gbc_button_1.gridy = 0;
		panel_3.add(button_1, gbc_button_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1, BorderLayout.CENTER);

		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(null, new String[] { "ID",
				"\u652F\u51FA\u7C7B\u578B\u540D\u79F0", "\u64CD\u4F5C" }) {
			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_2.getColumnModel().getColumn(0).setResizable(false);
		table_2.getColumnModel().getColumn(0).setPreferredWidth(110);
		table_2.getColumnModel().getColumn(0).setMinWidth(80);
		table_2.getColumnModel().getColumn(0).setMaxWidth(200);
		table_2.getColumnModel().getColumn(1).setResizable(false);
		table_2.getColumnModel().getColumn(1).setPreferredWidth(403);
		table_2.getColumnModel().getColumn(2).setResizable(false);
		table_2.getColumnModel().getColumn(2).setPreferredWidth(60);
		table_2.getColumnModel().getColumn(2).setMinWidth(60);
		table_2.getColumnModel().getColumn(2).setMaxWidth(60);
		table_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				clickedTable("确定删除此条数据?", "删除支出类型数据", table_2);
			}
		});
		table_2.getTableHeader().setReorderingAllowed(false);

		TableColumn tcs = table_2.getColumn("操作");
		tcs.setPreferredWidth(60);
		tcs.setCellRenderer(renderer);
		scrollPane_1.setViewportView(table_2);

		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("收入项目", imageIcon1, panel_4, null);
		panel_4.setLayout(new BorderLayout(0, 0));

		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5, BorderLayout.NORTH);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[] { 83, 0, 0, 0, 0 };
		gbl_panel_5.rowHeights = new int[] { 42, 0 };
		gbl_panel_5.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_5.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_5.setLayout(gbl_panel_5);

		JLabel label_2 = new JLabel("收入类型");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 0, 5);
		gbc_label_2.anchor = GridBagConstraints.EAST;
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 0;
		panel_5.add(label_2, gbc_label_2);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 0;
		panel_5.add(textField, gbc_textField);
		textField.setColumns(10);

		JButton button_2 = new JButton("   保 存   ");
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				submmit_3();
			}
		});
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.gridx = 3;
		gbc_button_2.gridy = 0;
		panel_5.add(button_2, gbc_button_2);

		JScrollPane scrollPane_2 = new JScrollPane();
		panel_4.add(scrollPane_2, BorderLayout.CENTER);

		table = new JTable();
		table.setModel(new DefaultTableModel(null, new String[] { "ID",
				"\u7C7B\u578B", "\u64CD\u4F5C" }) {
			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(110);
		table.getColumnModel().getColumn(0).setMinWidth(80);
		table.getColumnModel().getColumn(0).setMaxWidth(200);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(403);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setMinWidth(60);
		table.getColumnModel().getColumn(2).setMaxWidth(60);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				clickedTable("确定删除此条数据?", "删除收入类型数据", table);
			}
		});
		table.getTableHeader().setReorderingAllowed(false);
		TableColumn tcss = table.getColumn("操作");
		tcss.setPreferredWidth(60);
		tcss.setCellRenderer(renderer);
		scrollPane_2.setViewportView(table);

		initData();
		
	}
	private void initData() {
		clear();
		OptionDAOImpl optionMoudle = DAOContentFactriy.getOptionDAO();
		List list = optionMoudle.getOption();
		if (list != null && list.size() > 0) {
		
			DefaultTableModel tableModel_2 = (DefaultTableModel) table_2
					.getModel();
			DefaultTableModel tableModel_3 = (DefaultTableModel) table
					.getModel();
			for (int i = 0; i < list.size(); i++) {
				Option option = (Option) list.get(i);
				 if (Constant.TYPE_ZC.equals(option.getType())) {
					Object[] rowData = new Object[] { option.getId(),
							option.getText(), "删除" };
					tableModel_2.insertRow(0, rowData);
				} else if (Constant.TYPE_SR.equals(option.getType())) {
					Object[] rowData = new Object[] { option.getId(),
							option.getText(), "删除" };
					tableModel_3.insertRow(0, rowData);
				}

			}
		}
	}

	private void submmit_2() {
		if (textField_2.getText().trim().length() > 0) {
			String text = textField_2.getText();
			OptionDAOImpl optionMoudle = DAOContentFactriy.getOptionDAO();
			Option option = new Option();
			option.setId(String.valueOf(System.currentTimeMillis()));
			option.setType(Constant.TYPE_ZC);
			option.setText(text);
			boolean isd = optionMoudle.addOption(option);
			if (isd) {
				DefaultTableModel tableModel = (DefaultTableModel) table_2
						.getModel();
				Object[] rowData = new Object[] { option.getId(),
						option.getText(), "删除" };
				tableModel.insertRow(0, rowData);
			}
		}
	}

	private void submmit_3() {
		if (textField.getText().trim().length() > 0) {
			String text = textField.getText();
			OptionDAOImpl optionMoudle = DAOContentFactriy.getOptionDAO();
			Option option = new Option();
			option.setId(String.valueOf(System.currentTimeMillis()));
			option.setType(Constant.TYPE_SR);
			option.setText(text);
			boolean isd = optionMoudle.addOption(option);
			if (isd) {
				DefaultTableModel tableModel = (DefaultTableModel) table
						.getModel();
				Object[] rowData = new Object[] { option.getId(),
						option.getText(), "删除" };
				tableModel.insertRow(0, rowData);
			}
		}
	}

	private void clear() {
		
		DefaultTableModel tableModel2 = (DefaultTableModel) table_2.getModel();
		if (tableModel2.getRowCount() > 0) {
			int rows = tableModel2.getRowCount();
			for (int i = 0; i < rows; i++) {
				tableModel2.removeRow(0);
			}
		}
	}

	private void clickedTable(String message, String title, JTable tableObj) {
		int nCol = tableObj.getSelectedColumn();
		int nRow = tableObj.getSelectedRow();
		Object objSel = tableObj.getValueAt(nRow, nCol);

		if (objSel != null && objSel instanceof String) {
			if ("删除".equals(String.valueOf(objSel))) {
				int response = JOptionPane.showConfirmDialog(null, message,
						title, JOptionPane.YES_NO_OPTION);
				switch (response) {
				case JOptionPane.YES_OPTION:
					DefaultTableModel tableModel = (DefaultTableModel) tableObj
							.getModel();
					String catno = String.valueOf(tableModel
							.getValueAt(nRow, 0));
					OptionDAOImpl optionMoudle = DAOContentFactriy.getOptionDAO();
					boolean isd = optionMoudle.deleteOption(catno);
					if (isd) {
						tableModel.removeRow(nRow);
					}
				case JOptionPane.NO_OPTION:
				case JOptionPane.CLOSED_OPTION:
				}
			}
		}
	}

}
