package com.fafasoft.flow.ui.widget;

import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.CustomDAOImpl;
import com.fafasoft.flow.pojo.Custom;
import com.fafasoft.flow.ui.LimitedDocument;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-6-15
 * Time: 12:02:44
 * To change this template use File | Settings | File Templates.
 */
public class CustomDialog extends JDialog {
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_4;
    private JTextField textField_2;
    private JTextField textField_3;
    private JLabel label_8;
    private JLabel label_9;
    private JLabel label_11;
    private JCheckBox checkBox;
    private JCheckBox checkBox_1;
    private JCheckBox checkBox_2;
    private JComboBox comboBox;
    private JTextArea textArea;
    private JTable jTable;
    private Custom customold;

    public CustomDialog(Component owner, String tilte, Custom custom, JTable table) {
        setResizable(false);
        setTitle(tilte);
        this.customold = custom;
        this.jTable = table;
        setSize(new Dimension(455, 372));
        setLocationRelativeTo(owner);

        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "\u5BA2\u6237\u8D44\u6599", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JLabel label = new JLabel("客户号");
        label.setBounds(38, 24, 36, 15);
        panel.add(label);

        textField = new JTextField();
        textField.setBounds(79, 21, 156, 20);
        panel.add(textField);
        textField.setColumns(10);

        JLabel label_1 = new JLabel("姓名");
        label_1.setBounds(50, 50, 24, 15);
        panel.add(label_1);

        textField_1 = new JTextField();
        textField_1.setBounds(79, 47, 156, 21);
        panel.add(textField_1);
        textField_1.setColumns(10);

        JLabel label_2 = new JLabel("级别");
        label_2.setBounds(50, 75, 24, 15);
        panel.add(label_2);

        JLabel label_3 = new JLabel("性别");
        label_3.setBounds(50, 103, 24, 15);
        panel.add(label_3);
        ButtonGroup bg = new ButtonGroup();
        checkBox = new JCheckBox("男");
        checkBox.setBounds(79, 99, 51, 23);
        bg.add(checkBox);
        panel.add(checkBox);

        checkBox_1 = new JCheckBox("女");

        checkBox_1.setBounds(132, 99, 51, 23);
        bg.add(checkBox_1);
        panel.add(checkBox_1);

        JLabel label_4 = new JLabel("生日");
        label_4.setBounds(50, 130, 24, 15);
        panel.add(label_4);

        textField_4 = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));

        textField_4.setBounds(79, 128, 156, 21);
        panel.add(textField_4);
        textField_4.setColumns(10);

        comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(SelectType.getCustomType()));
        comboBox.setEditable(false);
        comboBox.setBounds(79, 73, 156, 21);
        panel.add(comboBox);

        JLabel label_5 = new JLabel("电话");
        label_5.setBounds(50, 155, 24, 15);
        panel.add(label_5);

        JLabel label_6 = new JLabel("联系地址");
        label_6.setBounds(26, 180, 48, 15);
        panel.add(label_6);

        textField_2 = new JTextField();
        textField_2.setBounds(79, 177, 314, 21);
        panel.add(textField_2);
        textField_2.setColumns(10);

        JLabel label_7 = new JLabel("备注");
        label_7.setBounds(50, 205, 24, 15);
        panel.add(label_7);

        textArea = new JTextArea();
        textArea.setBorder(textField_2.getBorder());
        textArea.setBounds(79, 205, 314, 63);
        panel.add(textArea);

        textField_3 = new JTextField();
        String intString = "1234567890";
        textField_3.setDocument(new LimitedDocument(20, intString));
        textField_3.setBounds(79, 152, 156, 21);
        panel.add(textField_3);
        textField_3.setColumns(10);

        checkBox_2 = new JCheckBox("录入完成后继续录入");
        checkBox_2.setBounds(74, 286, 156, 23);
        panel.add(checkBox_2);

        JButton button = new JButton("保存");
        button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                submmit();
            }
        });
        button.setBounds(248, 286, 66, 23);
        panel.add(button);
        JButton button_1 = new JButton("取消");
        button_1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                dispose();
            }
        });
        button_1.setBounds(324, 286, 69, 23);
        panel.add(button_1);

        label_8 = new JLabel("");
        label_8.setForeground(Color.RED);
        label_8.setBounds(245, 24, 194, 15);
        panel.add(label_8);

        label_9 = new JLabel("");
        label_9.setForeground(Color.RED);
        label_9.setBounds(245, 50, 194, 15);
        panel.add(label_9);

        label_11 = new JLabel("");
        label_11.setForeground(Color.RED);
        label_11.setBounds(245, 103, 194, 15);
        panel.add(label_11);

        JLabel label_12 = new JLabel("输入格式1988-08-08");
        label_12.setBounds(245, 130, 178, 15);
        panel.add(label_12);

        final JButton button_2 = new JButton("客户级别设置");

        button_2.setIconTextGap(1);
        button_2.setFocusable(false);
        button_2.setPreferredSize(new Dimension(80, 23));
        button_2.setBorderPainted(false);
        button_2.setContentAreaFilled(false);
        button_2.setMargin(new Insets(2, 5, 2, 5));
        button_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
            	CustomTypeDialog customTypeDialog = new CustomTypeDialog(arg0.getComponent().getParent(),comboBox);
                customTypeDialog.setVisible(true);
            }
            @Override
			public void mouseEntered(MouseEvent e) {
            	button_2.setBorderPainted(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button_2.setBorderPainted(false);
			}
        });
      
        button_2.setIcon(new ImageIcon(CustomDialog.class.getResource("/com/fafasoft/flow/resource/image/list-add.png")));
        button_2.setBounds(255, 71, 107, 23);
        panel.add(button_2);
        if (custom != null) {
            update();
        }
    }

    private void update() {
        textField.setEditable(false);
        textField.setText(customold.getId());
        textField_1.setText(customold.getName());

        if ("男".equals(customold.getSex())) {
            checkBox.setSelected(true);
        } else {
            checkBox_1.setSelected(true);
        }
        textField_4.setText(customold.getBirthday());
        textField_3.setText(customold.getTelephone());
        textField_2.setText(customold.getAddress());
        textArea.setText(customold.getNote());
        comboBox.getEditor().setItem(customold.getType());
    }

    private void submmit() {
        String id = textField.getText();
        if (id.trim().length() == 0) {
            label_8.setText("请输入客户号");
            textField.requestFocus();
            return;
        }
        label_8.setText("");
        String name = textField_1.getText();
        if (name.trim().length() == 0) {
            label_9.setText("请输入客户姓名");
            textField_1.requestFocus();
            return;
        }
        label_9.setText("");
        String sex = "男";
        if (!checkBox.isSelected() && !checkBox_1.isSelected()) {
            label_11.setText("请选择性别");
            return;
        } else {
            if (checkBox.isSelected()) {
                sex = "男";
            } else if (checkBox_1.isSelected()) {
                sex = "女";
            }
        }
        label_11.setText("");

        CustomDAOImpl customMoudle = DAOContentFactriy.getCustomDAO();
        if (customold == null) {
            Custom customis = customMoudle.getCustomById(id);
            if (customis != null) {
                label_8.setText("客户号[" + id + "]已经存在,请重新设定");
                return;
            }
        }
        Custom custom= null;

        if (customold != null) {
        	custom =customold;
            custom.setId(customold.getId());
        } else {
        	custom = new Custom();
            custom.setId(id);
            
        }

        custom.setName(name);
        custom.setSex(sex);
        if (comboBox.getItemCount() > 0 && comboBox.getSelectedItem() != null) {
            String type = comboBox.getSelectedItem().toString();
            if (type.trim().length() > 0) {
                custom.setType(type);
            }
        }

        String birthday = textField_4.getText();
        if (birthday.trim().length() > 0) {
            custom.setBirthday(birthday);
        }
        String telephone = textField_3.getText();
        if (telephone.trim().length() > 0) {
            custom.setTelephone(telephone);
        }
        String addres = textField_2.getText();
        if (addres.trim().length() > 0) {
            custom.setAddress(addres);
        }
        String note = textArea.getText();
        if (note.trim().length() > 0) {
            custom.setNote(note);
        }
        if (customold != null) {
            customMoudle.update(custom);
            int row = this.jTable.getSelectedRow();
            DefaultTableModel tableModel = (DefaultTableModel) this.jTable.getModel();
            tableModel.removeRow(row);
            insertRow(custom, row);
        } else {
            customMoudle.add(custom);
            int rows = this.jTable.getRowCount();
            insertRow(custom, rows);
        }


        if (checkBox_2.isSelected()) {
            clear();
        } else {
            dispose();
        }
    }

    private void clear() {
        textField.setText("");
        label_8.setText("");
        textField_1.setText("");
        label_9.setText("");
        label_11.setText("");
        checkBox.setSelected(false);
        checkBox_1.setSelected(false);
        textField_4.setText("");
        textField_3.setText("");
        textField_2.setText("");
        textArea.setText("");
        comboBox.getEditor().setItem("");
    }


    private void insertRow(Custom custom, int row) {
        DefaultTableModel tableModel = (DefaultTableModel) this.jTable.getModel();
        Object[] rowData = new Object[]{
                custom.getId(),
                custom.getName(),
                custom.getType(),
                custom.getIntegration(),
                custom.getAmount(),
                custom.getFrequency(),
                custom.getSex(),
                custom.getBirthday(),
                custom.getTelephone(),
                custom.getAddress(),
                custom.getNote(),
        };
        tableModel.insertRow(row, rowData);
        this.jTable.setRowSelectionInterval(row, row);
    }
}
