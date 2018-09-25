package com.fafasoft.flow.ui.widget;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.dao.impl.AccountsDaoImpl;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.pojo.Accounts;
import com.fafasoft.flow.ui.LimitedDocument;
import com.fafasoft.flow.util.DateHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;
import org.jdesktop.swingx.JXDatePicker;

public class AccountDialog extends JDialog {
    private JTextField textField;
	private JTextField textField_1;

	public AccountDialog(Component owner, String title,
			final Accounts accounts, final String flag, final String customId,
			final String name, final JTable viewGrid) {

		setIconImage(Toolkit.getDefaultToolkit().getImage(
				AccountDialog.class
						.getResource("/com/fafasoft/flow/resource/image/yygl.png")));

		setSize(new Dimension(375, 320));
		setResizable(false);
		setTitle(title);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(owner);
		getContentPane().setLayout(null);

		final JCheckBox checkBox = new JCheckBox("录入完成继续");

		checkBox.setBounds(33, 230, 115, 25);
		getContentPane().add(checkBox);
		checkBox.setVisible(true);
		if (accounts != null) {
			checkBox.setVisible(false);
		}

		final JLabel label_6 = new JLabel("");
		label_6.setForeground(Color.RED);

		label_6.setBounds(62, 2, 242, 25);
		getContentPane().add(label_6);
		JLabel label = new JLabel("往来帐类别");

		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(33, 66, 75, 17);
		getContentPane().add(label);

		final JComboBox comboBox = new JComboBox();

		Object[] obs = SelectType
				.getCustomersAccountTypes(Constant.SUPPLIERS_ACCOUNT
						.equals(flag) ? "供应商" : "客户");
		comboBox.setModel(new DefaultComboBoxModel(obs));
		if (accounts != null) {
			for (int m = 0; m < obs.length; m++) {
				Options options = (Options) obs[m];
				if (options != null) {
					if (options.getKey().equals(accounts.getCategories())) {
						comboBox.setSelectedItem(options);
						break;
					}
				}
			}
		}
		comboBox.setBounds(120, 61, 153, 25);
		getContentPane().add(comboBox);

		JLabel label_1 = new JLabel("金额");

		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(50, 98, 58, 17);
		getContentPane().add(label_1);

		textField = new JTextField();
        String dubString = "1234567890.";
        textField.setDocument(new LimitedDocument(20, dubString));
		textField.setBounds(120, 94, 153, 25);
		getContentPane().add(textField);
		textField.setColumns(10);
		if (accounts != null) {
			textField.setText(String.valueOf(accounts.getAmount()));
		}

		JLabel label_2 = new JLabel("备注");

		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(50, 186, 58, 17);
		getContentPane().add(label_2);

		textField_1 = new JTextField();
		textField_1.setBounds(120, 183, 153, 25);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		if (accounts != null) {
			textField_1.setText(accounts.getNote());
		}

		JLabel label_3 = new JLabel(
				Constant.SUPPLIERS_ACCOUNT.equals(flag) ? "供应商名称" : "客户名称");

		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setBounds(33, 33, 75, 17);
		getContentPane().add(label_3);

		JLabel label_4 = new JLabel(name);
		label_4.setFont(new Font("宋体", Font.BOLD, 12));
		label_4.setBounds(120, 30, 153, 23);
		getContentPane().add(label_4);

		JLabel label_5 = new JLabel("状态");

		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setBounds(50, 130, 58, 17);
		getContentPane().add(label_5);
		ButtonGroup bg = new ButtonGroup();

		final JCheckBox radioButton = new JCheckBox("未处理");
		radioButton.setSelected(true);

		radioButton.setBounds(120, 127, 75, 25);
		bg.add(radioButton);
		getContentPane().add(radioButton);

		JCheckBox radioButton_1 = new JCheckBox("已处理");

		radioButton_1.setBounds(196, 127, 66, 25);
		bg.add(radioButton_1);
		getContentPane().add(radioButton_1);
		if (accounts != null) {
			radioButton.setSelected(accounts.getStatus().equals(
                    Constant.ACCOUNT_STATUS_UN));
			radioButton_1.setSelected(accounts.getStatus().equals(
                    Constant.ACCOUNT_STATUS_PR));
		}
		JButton button = new JButton("保存");
	
		button.setBounds(156, 230, 86, 25);
		getContentPane().add(button);

		JButton button_1 = new JButton("取消");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});

		button_1.setBounds(261, 230, 81, 25);
		getContentPane().add(button_1);
		
		JLabel lblNewLabel = new JLabel("日期");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(54, 157, 54, 15);
		getContentPane().add(lblNewLabel);
		
		final JXDatePicker datePicker = new JXDatePicker();
		datePicker.setFormats(new String[] {"yyyy-MM-dd"});
		datePicker.getEditor().setEditable(false);
		datePicker.setBounds(120, 153, 153, 23);
		datePicker.setDate(DateHelper.currentDate());
		getContentPane().add(datePicker);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Object ob = comboBox.getSelectedItem();
				if (ob == null) {
					label_6.setText("请选择往来账务分类");
					return;
				}

				String je = textField.getText();
				if (je.trim().length() == 0) {
					label_6.setText("请填写往来账务金额");
					return;
				}

				Accounts newaccounts = new Accounts();
				if (accounts != null) {
					newaccounts.setId(accounts.getId());
				} else {
					newaccounts.setId((String.valueOf(UUID.randomUUID()
							.toString().replaceAll("-", ""))));
				}

				newaccounts.setTargetId(customId);
				newaccounts.setCategories(((Options) ob).getKey());
				newaccounts.setAmount(Double.parseDouble(je));

				newaccounts
						.setStatus(radioButton.isSelected() ? Constant.ACCOUNT_STATUS_UN
								: Constant.ACCOUNT_STATUS_PR);
				newaccounts.setNote(textField_1.getText());
				newaccounts.setDate(datePicker.getEditor().getText());
				newaccounts.setFlag(flag);
				AccountsDaoImpl daoContentFactriy = DAOContentFactriy
						.getAccountsDao();
				Object[] rowData = new Object[] { newaccounts.getId(),
						customId, name, String.valueOf(ob),
						newaccounts.getAmount(),
						radioButton.isSelected() ? "未处理" : "已处理",
						newaccounts.getNote(),newaccounts.getDate(), "删除"

				};
				DefaultTableModel tableModel = (DefaultTableModel) viewGrid
						.getModel();

				if (accounts != null) {
					int sel = viewGrid.getSelectedRow();
					daoContentFactriy.update(newaccounts);
					tableModel.setValueAt(rowData[0], sel, 0);
					tableModel.setValueAt(rowData[1], sel, 1);
					tableModel.setValueAt(rowData[2], sel, 2);
					tableModel.setValueAt(rowData[3], sel, 3);
					tableModel.setValueAt(rowData[4], sel, 4);
					tableModel.setValueAt(rowData[5], sel, 5);
					tableModel.setValueAt(rowData[6], sel, 6);
					tableModel.setValueAt(rowData[7], sel, 7);
				} else {

					daoContentFactriy.add(newaccounts);
					tableModel.insertRow(0, rowData);
				}
				//System.out.println("checkBox.isSelected()=="
				//		+ checkBox.isSelected());
				if (!checkBox.isSelected()) {
					//System.out.println("dispose()==");
					dispose();
				} else {
					comboBox.setSelectedIndex(0);
					textField.setText("");
					radioButton.setSelected(true);
					textField_1.setText("");
				}

			}
		});


	}
}
