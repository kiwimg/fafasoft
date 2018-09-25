package com.fafasoft.flow.ui.widget;

import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.SuppliersDAOImpl;
import com.fafasoft.flow.pojo.Suppliers;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;

public class SuppliersDialog extends JDialog {
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    private JTextField textField_6;
    private JTextField textField_7;
    private JTextField textField_8;
    private JLabel msg;
    private JTable jTable;

    public SuppliersDialog(Component owner, String tilte, final Suppliers suppliers, JTable table) {
        setResizable(false);
        setTitle(tilte);
        setSize(new Dimension(464, 364));
        setLocationRelativeTo(owner);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));
        this.jTable = table;
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "\u4F9B\u5E94\u5546\u8D44\u6599", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JLabel label = new JLabel("供应商名称");
        label.setBounds(28, 47, 71, 17);
        panel.add(label);

        textField = new JTextField();
        textField.setBounds(100, 44, 309, 23);
        panel.add(textField);
        textField.setColumns(10);

        JLabel label_1 = new JLabel("供应商地址");
        label_1.setBounds(28, 78, 71, 17);
        panel.add(label_1);

        textField_1 = new JTextField();
        textField_1.setBounds(100, 75, 309, 23);
        panel.add(textField_1);
        textField_1.setColumns(10);

        JLabel label_2 = new JLabel("联系人");
        label_2.setBounds(52, 109, 47, 17);
        panel.add(label_2);

        textField_2 = new JTextField();
        textField_2.setBounds(100, 106, 116, 23);
        panel.add(textField_2);
        textField_2.setColumns(10);

        JLabel label_3 = new JLabel("联系电话");
        label_3.setBounds(239, 109, 58, 17);
        panel.add(label_3);

        textField_3 = new JTextField();
        textField_3.setBounds(293, 106, 116, 23);
        panel.add(textField_3);
        textField_3.setColumns(10);

        JLabel lblQq = new JLabel("QQ");
        lblQq.setBounds(65, 143, 34, 17);
        panel.add(lblQq);

        textField_4 = new JTextField();
        textField_4.setBounds(100, 140, 116, 23);
        panel.add(textField_4);
        textField_4.setColumns(10);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setBounds(256, 143, 41, 17);
        panel.add(lblEmail);

        textField_5 = new JTextField();
        textField_5.setBounds(293, 143, 116, 23);
        panel.add(textField_5);
        textField_5.setColumns(10);

        JLabel label_4 = new JLabel("传真");
        label_4.setHorizontalTextPosition(SwingConstants.RIGHT);
        label_4.setBounds(65, 180, 34, 17);
        panel.add(label_4);

        textField_6 = new JTextField();
        textField_6.setBounds(100, 177, 116, 23);
        panel.add(textField_6);
        textField_6.setColumns(10);

        JLabel label_5 = new JLabel("邮政编码");
        label_5.setBounds(239, 180, 58, 17);
        panel.add(label_5);

        textField_7 = new JTextField();
        textField_7.setBounds(293, 178, 116, 23);
        panel.add(textField_7);
        textField_7.setColumns(10);

        JLabel label_6 = new JLabel("备注");
        label_6.setBounds(65, 217, 34, 17);
        panel.add(label_6);

        textField_8 = new JTextField();
        textField_8.setBounds(100, 214, 309, 23);
        panel.add(textField_8);
        textField_8.setColumns(10);

        JButton button = new JButton("保存");
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                submmit(suppliers);
            }
        });
        button.setBounds(231, 268, 80, 25);
        panel.add(button);

        JButton button_1 = new JButton("取消");
        button_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        button_1.setBounds(329, 268, 80, 25);
        panel.add(button_1);
        msg = new JLabel("");
        msg.setForeground(Color.RED);
        msg.setBounds(68, 16, 341, 17);
        panel.add(msg);
        if( suppliers != null){
           setValue(suppliers); 
        }
    }

    private void submmit(Suppliers oldsuppliers) {
        String suppliersName = textField.getText();//供应商名称
        String address = textField_1.getText();//地址
        String ccontact = textField_2.getText();//联系人
        String phone = textField_3.getText();//联系电话
        String qq = textField_4.getText();//QQ
        String email = textField_5.getText();//email
        String fax = textField_6.getText();//传真
        String zipcode = textField_7.getText();//邮政编码
        String note = textField_8.getText();//备注
        if ("".equals(suppliersName.trim())) {
            msg.setText("请输入供应商名称");
            return;
        }
        if ("".equals(address.trim())) {
            msg.setText("请输入供应商地址");
            return;
        }
        Suppliers suppliers = null;

        if (oldsuppliers == null) {
            suppliers = new Suppliers();
        } else {
            suppliers = oldsuppliers;
        }
        suppliers.setSuppliersName(suppliersName);
        suppliers.setAddress(address);
        suppliers.setContact(ccontact);
        suppliers.setPhone(phone);
        suppliers.setQq(qq);
        suppliers.setEmail(email);
        suppliers.setFax(fax);
        suppliers.setZipcode(zipcode);
        suppliers.setRemarks(note);
        SuppliersDAOImpl suppliersMoudle =DAOContentFactriy.getSuppliersDAO();
        if (oldsuppliers == null) {

            suppliers.setSuppliersno(UUID.randomUUID().toString().replaceAll("-", ""));
            suppliersMoudle.insertSuppliers(suppliers);
            int rows = this.jTable.getRowCount();
            insertRow(suppliers, rows);
            clear();
            msg.setText("添加供应商资料成功");
        } else {
            suppliersMoudle.updateSuppliers(oldsuppliers);
            int rows = this.jTable.getSelectedRow();
            DefaultTableModel tableModel = (DefaultTableModel) this.jTable.getModel();
            tableModel.removeRow(rows);
            insertRow(suppliers, rows);
            msg.setText("修改供应商资料成功");
        }
    }
    private void  setValue(Suppliers suppliers){
        textField.setText(suppliers.getSuppliersName());//供应商名称
        textField_1.setText(suppliers.getAddress());//地址
        textField_2.setText(suppliers.getContact());//联系人
        textField_3.setText(suppliers.getPhone());//联系电话
        textField_4.setText(suppliers.getQq());//QQ
        textField_5.setText(suppliers.getEmail());//email
        textField_6.setText(suppliers.getFax());//传真
        textField_7.setText(suppliers.getZipcode());//邮政编码
        textField_8.setText(suppliers.getRemarks());//备注
    }
    private void insertRow(Suppliers suppliers, int row) {
        DefaultTableModel tableModel = (DefaultTableModel) this.jTable.getModel();
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
        tableModel.insertRow(row, rowData);
        this.jTable.setRowSelectionInterval(row, row);
    }

    private void clear() {
        textField.setText("");//供应商名称
        textField_1.setText("");//地址
        textField_2.setText("");//联系人
        textField_3.setText("");//联系电话
        textField_4.setText("");//QQ
        textField_5.setText("");//email
        textField_6.setText("");//传真
        textField_7.setText("");//邮政编码
        textField_8.setText("");//备注
    }
}
