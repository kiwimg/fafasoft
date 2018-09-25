package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.action.PageAction;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.SuppliersDAOImpl;
import com.fafasoft.flow.pojo.Suppliers;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.widget.Page;
import com.fafasoft.flow.ui.widget.SuppliersDialog;

import org.h2.engine.Constants;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class SuppliersPanel extends JPanel implements LazyPageContent {

    private JTable table;
    private JTextField textField;
    private Page page;

    public SuppliersPanel() {
        setLayout(new BorderLayout(0, 0));

    }

    public void initPanel() {

        final JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        add(panel, BorderLayout.NORTH);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0, 14, 0, 15, 304, 0};
        gbl_panel.rowHeights = new int[]{0, 0};
        gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        JButton button = new JButton("增加");
        button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                SuppliersDialog suppliersDialog = new SuppliersDialog(panel.getParent(), "增加供应商资料", null, table);
                suppliersDialog.setVisible(true);
            }
        });
        GridBagConstraints gbc_button = new GridBagConstraints();
        gbc_button.insets = new Insets(0, 0, 0, 5);
        gbc_button.gridx = 1;
        gbc_button.gridy = 0;
        panel.add(button, gbc_button);

        JButton button_1 = new JButton("修改");
        button_1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int nRow = table.getSelectedRow();
                if (nRow > -1 && table.getRowCount() > 0) {
                    String id = String.valueOf(table.getValueAt(nRow, 0));
                    SuppliersDAOImpl suppliersMoudle = DAOContentFactriy.getSuppliersDAO();
                    Suppliers suppliers = suppliersMoudle.getSuppliersByNo(id);
                    SuppliersDialog suppliersDialog = new SuppliersDialog(panel.getParent(), "修改供应商资料", suppliers, table);
                    suppliersDialog.setVisible(true);
                }
            }
        });
        GridBagConstraints gbc_button_1 = new GridBagConstraints();
        gbc_button_1.insets = new Insets(0, 0, 0, 5);
        gbc_button_1.gridx = 2;
        gbc_button_1.gridy = 0;
        panel.add(button_1, gbc_button_1);

        JButton button_2 = new JButton("删除");
        button_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {

                int nRow = table.getSelectedRow();
                int rowCount = table.getRowCount();

                if (nRow > -1 && rowCount > 0) {
                    int response = JOptionPane.showConfirmDialog(null, "确定删除此条数据?",
                            "删除供应商数据", JOptionPane.YES_NO_OPTION);
                    switch (response) {
                        case JOptionPane.YES_OPTION:
                            String id = String.valueOf(table.getValueAt(nRow, 0));
                            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                            tableModel.removeRow(nRow);
                            int newrowCount = table.getRowCount();
                            SuppliersDAOImpl suppliersMoudle = DAOContentFactriy.getSuppliersDAO();
                            suppliersMoudle.deleteSuppliers(id);
                            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                            if (newrowCount == 1) {
                                table.setRowSelectionInterval(0, 0);
                            } else {
                                table.setRowSelectionInterval(nRow - 1, nRow - 1);
                            }
                        case JOptionPane.NO_OPTION:
                        case JOptionPane.CLOSED_OPTION:
                    }

                }
            }
        });
        GridBagConstraints gbc_button_2 = new GridBagConstraints();
        gbc_button_2.insets = new Insets(0, 0, 0, 5);
        gbc_button_2.gridx = 3;
        gbc_button_2.gridy = 0;
        panel.add(button_2, gbc_button_2);

        JLabel label_1 = new JLabel("");
        label_1.setBackground(Color.RED);
        GridBagConstraints gbc_label_1 = new GridBagConstraints();
        gbc_label_1.anchor = GridBagConstraints.WEST;
        gbc_label_1.gridwidth = 2;
        gbc_label_1.insets = new Insets(0, 0, 0, 5);
        gbc_label_1.gridx = 4;
        gbc_label_1.gridy = 0;
        panel.add(label_1, gbc_label_1);

        page = new Page(new PageAction() {
            public void pageFirst() {
                SuppliersDAOImpl suppliersMoudle = DAOContentFactriy.getSuppliersDAO();

                java.util.List list = suppliersMoudle.getSuppliers(0, 20);
                pageData(list);

            }

            public void pagePrev(int pagenum) {
                SuppliersDAOImpl suppliersMoudle = DAOContentFactriy.getSuppliersDAO();

                java.util.List list = suppliersMoudle.getSuppliers(pagenum, 20);
                pageData(list);
            }

            public void pageNext(int pagenum) {
                SuppliersDAOImpl suppliersMoudle = DAOContentFactriy.getSuppliersDAO();
                java.util.List list = suppliersMoudle.getSuppliers(pagenum, 20);
                pageData(list);
            }

            public void pageLast(int pagenum) {
                SuppliersDAOImpl suppliersMoudle = DAOContentFactriy.getSuppliersDAO();

                java.util.List list = suppliersMoudle.getSuppliers(pagenum, 20);
                pageData(list);
            }

            public void itemStateChanged(int pagenum) {
                String v = textField.getText(); //名称/电话/联系人
                SuppliersDAOImpl suppliersMoudle = DAOContentFactriy.getSuppliersDAO();
                if (!"".equals(v)) {
                    java.util.List list = suppliersMoudle.getSuppliers(v, v, v, 0, 20);
                    pageData(list);
                } else {
                    java.util.List list = null;
                    int totalrow = suppliersMoudle.getSuppliersSize();
                    list = suppliersMoudle.getSuppliers(pagenum, 20);
                    pageData(list);
                }
            }

            public void export(MouseEvent e) {
                try {
                    //  String filepath = file.getAbsolutePath();
                    SuppliersDAOImpl suppliersMoudle = DAOContentFactriy.getSuppliersDAO();
                    java.util.List list = suppliersMoudle.getSuppliers();
                    if (list != null && list.size() > 0) {
                        JFileChooser jfc = new JFileChooser("d:/");
                        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                        File fileff = new File("供应商资料.csv");
                        jfc.setSelectedFile(fileff);
                        int result = jfc.showSaveDialog(panel);
                        if (result == JFileChooser.CANCEL_OPTION) return;
                        File savedFile = jfc.getSelectedFile();
                        if (savedFile.exists()) {
                            int overwriteSelect = JOptionPane.showConfirmDialog(panel, "<html><font size=3>文件" + savedFile.getName() + "已存在，是否覆盖?</font><html>", "是否覆盖?",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.WARNING_MESSAGE);
                            if (overwriteSelect != JOptionPane.YES_OPTION) {
                                return;
                            }
                        }
                        OutputStream out = new FileOutputStream(savedFile);
                        out = new BufferedOutputStream(out, Constants.IO_BUFFER_SIZE);
                        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(out, "gbk"));
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("供应商名称");
                        stringBuffer.append(",");
                        stringBuffer.append("地址");
                        stringBuffer.append(",");
                        stringBuffer.append("联系人");
                        stringBuffer.append(",");
                        stringBuffer.append("联系电话");
                        stringBuffer.append(",");
                        stringBuffer.append("QQ");
                        stringBuffer.append(",");
                        stringBuffer.append("传真");
                        stringBuffer.append(",");
                        stringBuffer.append("邮箱");
                        stringBuffer.append(",");
                        stringBuffer.append("邮政编码");
                        stringBuffer.append(",");
                        stringBuffer.append("备注");
                        stringBuffer.append("\r\n");
                        output.write(String.valueOf(stringBuffer));
                        for (int i = 0; i < list.size(); i++) {
                            Suppliers suppliers = (Suppliers) list.get(i);
                            StringBuilder sb = new StringBuilder(128);
                            sb.append(suppliers.getSuppliersName());
                            sb.append(",");
                            sb.append(suppliers.getAddress());
                            sb.append(",");
                            sb.append(suppliers.getContact());
                            sb.append(",");
                            sb.append(suppliers.getPhone());
                            sb.append(",");
                            sb.append(suppliers.getQq());
                            sb.append(",");
                            sb.append(suppliers.getFax());
                            sb.append(",");
                            sb.append(suppliers.getEmail());
                            sb.append(",");
                            sb.append(suppliers.getZipcode());
                            sb.append(",");
                            sb.append(suppliers.getRemarks());

                            sb.append("\r\n");
                            output.write(String.valueOf(sb));
                        }
                        output.close();

                        JOptionPane.showMessageDialog(MainWindows.getInstance(), "文件导出成功");
                    } else {
                        JOptionPane.showMessageDialog(MainWindows.getInstance(), "没数据导出", "数据导出通知", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(page, BorderLayout.SOUTH);

        JPanel panel_1 = new JPanel();
        add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(new BorderLayout(0, 0));

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.add(panel_2, BorderLayout.NORTH);
        GridBagLayout gbl_panel_2 = new GridBagLayout();
        gbl_panel_2.columnWidths = new int[]{0, 0, 233, 0, 0, 0};
        gbl_panel_2.rowHeights = new int[]{0, 0};
        gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panel_2.setLayout(gbl_panel_2);

        JLabel label = new JLabel("名称/电话/联系人");
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.insets = new Insets(0, 0, 0, 5);
        gbc_label.anchor = GridBagConstraints.EAST;
        gbc_label.gridx = 1;
        gbc_label.gridy = 0;
        panel_2.add(label, gbc_label);

        textField = new JTextField();
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.insets = new Insets(0, 0, 0, 5);
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField.gridx = 2;
        gbc_textField.gridy = 0;
        panel_2.add(textField, gbc_textField);
        textField.setColumns(10);

        JButton button_3 = new JButton("查询");
        button_3.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                SuppliersDAOImpl suppliersMoudle = DAOContentFactriy.getSuppliersDAO();
                String v = textField.getText(); //名称/电话/联系人
//	                if("".equals(v.trim())) {
//	                	label_1.setText("请输入供应商名称或电话或联系人");
//	                	return;
//	                }
                java.util.List list = suppliersMoudle.getSuppliers(v, v, v, 0, 20);
                int totalrow = suppliersMoudle.getSuppliersSize(v, v, v);
                pageData(list);
                page.setPageInfo(totalrow);
            }
        });
        GridBagConstraints gbc_button_3 = new GridBagConstraints();
        gbc_button_3.insets = new Insets(0, 0, 0, 5);
        gbc_button_3.gridx = 3;
        gbc_button_3.gridy = 0;
        panel_2.add(button_3, gbc_button_3);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(new TitledBorder(null, "\u4F9B\u5E94\u5546\u5217\u8868", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoCreateRowSorter(true);
        table.setRowHeight(21);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int clicked = e.getClickCount();
                int nRow = table.getSelectedRow();
                String id = String.valueOf(table.getValueAt(nRow, 0));
                if (clicked == 2) {
                    SuppliersDAOImpl suppliersMoudle = DAOContentFactriy.getSuppliersDAO();
                    Suppliers suppliers = suppliersMoudle.getSuppliersByNo(id);
                    SuppliersDialog suppliersDialog = new SuppliersDialog(MainWindows.getInstance(), "修改供应商资料", suppliers, table);
                    suppliersDialog.setVisible(true);
                }
            }
        });
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
        table.getColumnModel().getColumn(1).setPreferredWidth(329);
        table.getColumnModel().getColumn(2).setPreferredWidth(224);
        table.getColumnModel().getColumn(2).setMinWidth(50);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().setSelectionInterval(0, 0);
        scrollPane.setViewportView(table);
        initdata();

    }

    private void initdata() {
        SuppliersDAOImpl suppliersMoudle = DAOContentFactriy.getSuppliersDAO();
        int totalrow = suppliersMoudle.getSuppliersSize();
        java.util.List list = suppliersMoudle.getSuppliers(0, 20);
        pageData(list);
        page.setPageInfo(totalrow);
    }

    private void pageData(java.util.List list) {
        if (list != null && list.size() > 0) {
            clear();
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
            table.setRowSelectionInterval(0, 0);
        }
    }

    public void clear() {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        if (tableModel.getRowCount() > 0) {
            int rows = tableModel.getRowCount();
            for (int i = 0; i < rows; i++) {
                tableModel.removeRow(0);
            }
        }

    }


}
