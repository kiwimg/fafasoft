package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.action.PageAction;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.CustomDAOImpl;
import com.fafasoft.flow.pojo.Custom;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.widget.CustomDialog;
import com.fafasoft.flow.ui.widget.Page;

import org.h2.engine.Constants;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-6-15
 * Time: 11:03:31
 * To change this template use File | Settings | File Templates.
 */
public class CustomPanel extends JPanel  implements LazyPageContent {


    private JTextField textField;
    private JTable table;
    private Page cpage;

    public CustomPanel() {
        setLayout(new BorderLayout(0, 0));
       
    }


    public void initPanel() {
    	 final JPanel panel = new JPanel();
         
         panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
         add(panel, BorderLayout.NORTH);
         GridBagLayout gbl_panel = new GridBagLayout();
         gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
         gbl_panel.rowHeights = new int[]{0, 0};
         gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
         gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
         panel.setLayout(gbl_panel);

         JButton button = new JButton("增加");
         button.addMouseListener(new MouseAdapter() {
             public void mouseClicked(MouseEvent arg0) {
                 CustomDialog customDialog = new CustomDialog( panel.getParent(), "增加客户资料", null, table);
                 customDialog.setVisible(true);
             }
         });
         GridBagConstraints gbc_button = new GridBagConstraints();
         gbc_button.insets = new Insets(0, 0, 0, 5);
         gbc_button.gridx = 0;
         gbc_button.gridy = 0;
         panel.add(button, gbc_button);

         JButton button_1 = new JButton("修改");
         button_1.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseClicked(MouseEvent arg0) {
                 int nRow = table.getSelectedRow();
                 if (nRow > -1 && table.getRowCount() > 0) {
                     String id = String.valueOf(table.getValueAt(nRow, 0));
                   
                     CustomDAOImpl customMoudle =  DAOContentFactriy.getCustomDAO();
                     Custom custom = customMoudle.getCustomById(id);
                     CustomDialog customDialog = new CustomDialog(panel.getParent(), "修改客户资料", custom, table);
                     customDialog.setVisible(true);
                 }
             }
         });
         GridBagConstraints gbc_button_1 = new GridBagConstraints();
         gbc_button_1.insets = new Insets(0, 0, 0, 5);
         gbc_button_1.gridx = 1;
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
                             "删除库存数据", JOptionPane.YES_NO_OPTION);
                     switch (response) {
                         case JOptionPane.YES_OPTION:
                             String id = String.valueOf(table.getValueAt(nRow, 0));
                             DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                             tableModel.removeRow(nRow);
                             int newrowCount = table.getRowCount();
                             CustomDAOImpl customMoudle =  DAOContentFactriy.getCustomDAO();
                             customMoudle.deleteById(id);
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
         gbc_button_2.gridx = 2;
         gbc_button_2.gridy = 0;
         panel.add(button_2, gbc_button_2);

         JPanel panel_1 = new JPanel();
         add(panel_1, BorderLayout.CENTER);
         panel_1.setLayout(new BorderLayout(0, 0));

         JPanel panel_2 = new JPanel();
         panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
         panel_1.add(panel_2, BorderLayout.NORTH);
         GridBagLayout gbl_panel_2 = new GridBagLayout();
         gbl_panel_2.columnWidths = new int[]{0, 0, 188, 0, 0};
         gbl_panel_2.rowHeights = new int[]{0, 0};
         gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
         gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
         panel_2.setLayout(gbl_panel_2);

         JLabel label = new JLabel("客户号/姓名/电话");
         GridBagConstraints gbc_label = new GridBagConstraints();
         gbc_label.insets = new Insets(0, 0, 0, 5);
         gbc_label.anchor = GridBagConstraints.EAST;
         gbc_label.gridx = 1;
         gbc_label.gridy = 0;
         panel_2.add(label, gbc_label);

         textField = new JTextField();
         textField.addKeyListener(new KeyAdapter() {
             @Override
             public void keyPressed(KeyEvent e) {
                 if (e.getKeyCode() == 10) {// 回车
                     submmit();
                 }
             }
         });
         GridBagConstraints gbc_textField = new GridBagConstraints();
         gbc_textField.insets = new Insets(0, 0, 0, 5);
         gbc_textField.fill = GridBagConstraints.HORIZONTAL;
         gbc_textField.gridx = 2;
         gbc_textField.gridy = 0;
         panel_2.add(textField, gbc_textField);
         textField.setColumns(10);

         JButton button_3 = new JButton("查询");
         button_3.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseClicked(MouseEvent arg0) {
                 submmit();
             }
         });
         GridBagConstraints gbc_button_3 = new GridBagConstraints();
         gbc_button_3.gridx = 3;
         gbc_button_3.gridy = 0;
         panel_2.add(button_3, gbc_button_3);

         JPanel panel_3 = new JPanel();
         panel_3.setBorder(new TitledBorder(null, "\u5BA2\u6237\u8D44\u6599\u5217\u8868", TitledBorder.LEADING, TitledBorder.TOP, null, null));
         panel_1.add(panel_3, BorderLayout.CENTER);
         panel_3.setLayout(new BorderLayout(0, 0));

         JScrollPane scrollPane = new JScrollPane();
         panel_3.add(scrollPane, BorderLayout.CENTER);

         table = new JTable();
         table.setRowHeight(21);
     	 table.setAutoCreateRowSorter(true);
         table.addMouseListener(new MouseAdapter() {
             public void mouseClicked(MouseEvent e) {
                 int clicked = e.getClickCount();
                 int nRow = table.getSelectedRow();
                 String id = String.valueOf(table.getValueAt(nRow, 0));
                 if (clicked == 2) {
                    
                     CustomDAOImpl customMoudle =  DAOContentFactriy.getCustomDAO();
                     Custom custom = customMoudle.getCustomById(id);
                     CustomDialog customDialog = new CustomDialog(MainWindows.getInstance(), "修改客户资料", custom, table);
                     customDialog.setVisible(true);
                 }
             }
         });
         table.setModel(new DefaultTableModel(
                 null,
                 new String[]{
                         "\u5BA2\u6237\u53F7", "\u59D3\u540D", "级别", "\u79EF\u5206", "\u6D88\u8D39\u91D1\u989D", "\u6D88\u8D39\u6B21\u6570", "\u6027\u522B", "\u751F\u65E5", "\u7535\u8BDD", "\u8054\u7CFB\u5730\u5740", "\u5907\u6CE8"
                 }
         ) {
             Class[] columnTypes = new Class[]{
                     String.class, String.class, String.class, Object.class, Object.class, Integer.class, String.class, String.class, String.class, Object.class, Object.class
             };

             public Class getColumnClass(int columnIndex) {
                 return columnTypes[columnIndex];
             }

             boolean[] columnEditables = new boolean[]{
                     false, false, false, false, false, false, false, false, false, false, false
             };

             public boolean isCellEditable(int row, int column) {
                 return columnEditables[column];
             }
         });
         table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
         table.getTableHeader().setReorderingAllowed(false);


         scrollPane.setViewportView(table);

         cpage = new Page(new PageAction() {
             public void pageFirst() {
                 java.util.List list = null;
                 CustomDAOImpl customMoudle =  DAOContentFactriy.getCustomDAO();
                 String cu = textField.getText();
                 if (cu.trim().length() > 0) {
                     list = customMoudle.getCustom(cu, cu, cu);

                 } else {
                     list = customMoudle.getCustoms(0, 20);
                 }
                 pageData(list);
             }

             public void pagePrev(int pagenum) {
                 java.util.List list = null;
                 CustomDAOImpl customMoudle =  DAOContentFactriy.getCustomDAO();
                 String cu = textField.getText();
                 if (cu.trim().length() > 0) {
                     list = customMoudle.getCustom(cu, cu, cu);

                 } else {
                     list = customMoudle.getCustoms(pagenum, 20);
                 }
                 pageData(list);
             }

             public void pageNext(int pagenum) {
                 java.util.List list = null;
                 CustomDAOImpl customMoudle =  DAOContentFactriy.getCustomDAO();
                 String cu = textField.getText();
                 if (cu.trim().length() > 0) {
                     list = customMoudle.getCustom(cu, cu, cu);

                 } else {
                     list = customMoudle.getCustoms(pagenum, 20);
                 }
                 pageData(list);
             }

             public void pageLast(int pagenum) {
                 java.util.List list = null;
                 CustomDAOImpl customMoudle =  DAOContentFactriy.getCustomDAO();
                 String cu = textField.getText();
                 if (cu.trim().length() > 0) {
                     list = customMoudle.getCustom(cu, cu, cu);

                 } else {
                     list = customMoudle.getCustoms(pagenum, 20);
                 }
                 pageData(list);
             }

             public void itemStateChanged(int pagenum) {
                 java.util.List list = null;
                 CustomDAOImpl customMoudle =  DAOContentFactriy.getCustomDAO();
                 String cu = textField.getText();
                 if (cu.trim().length() > 0) {
                     list = customMoudle.getCustom(cu, cu, cu);
                 } else {
                     list = customMoudle.getCustoms(pagenum, 20);
                 }
                 pageData(list);
             }

             public void export(MouseEvent e) {
                 try {
                     //  String filepath = file.getAbsolutePath();
                 	  CustomDAOImpl customMoudle =  DAOContentFactriy.getCustomDAO();
                     java.util.List list = customMoudle.getCustoms();
                     if (list != null && list.size() > 0) {
                         JFileChooser jfc = new JFileChooser("d:/");
                         jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                         File fileff = new File("客户资料.csv");
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
                         stringBuffer.append("客户号");
                         stringBuffer.append(",");
                         stringBuffer.append("姓名");
                         stringBuffer.append(",");
                         stringBuffer.append("级别");
                         stringBuffer.append(",");
                         stringBuffer.append("积分");
                         stringBuffer.append(",");
                         stringBuffer.append("缴费金额");
                         stringBuffer.append(",");
                         stringBuffer.append("消费次数");
                         stringBuffer.append(",");
                         stringBuffer.append("性别");
                         stringBuffer.append(",");
                         stringBuffer.append("生日");
                         stringBuffer.append(",");
                         stringBuffer.append("电话");
                         stringBuffer.append(",");
                         stringBuffer.append("联系地址");
                         stringBuffer.append(",");
                         stringBuffer.append("备注");
                         stringBuffer.append("\r\n");
                         output.write(String.valueOf(stringBuffer));
                         for (int i = 0; i < list.size(); i++) {
                             Custom custom = (Custom) list.get(i);
                             StringBuilder sb = new StringBuilder(128);
                             sb.append(custom.getId());
                             sb.append(",");
                             sb.append(custom.getName());
                             sb.append(",");
                             sb.append(custom.getType());
                             sb.append(",");
                             sb.append(custom.getType());
                             sb.append(",");
                             sb.append(custom.getAmount());
                             sb.append(",");
                             sb.append(custom.getFrequency());
                             sb.append(",");
                             sb.append(custom.getSex());
                             sb.append(",");
                             sb.append(custom.getBirthday());
                             sb.append(",");
                             sb.append(custom.getTelephone());
                             sb.append(",");
                             sb.append(custom.getAddress());
                             sb.append(",");
                             sb.append(custom.getNote());
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
         add(cpage, BorderLayout.SOUTH);
         initdata();
    }

    private void initdata() {
    	  CustomDAOImpl customMoudle =  DAOContentFactriy.getCustomDAO();
        int totalrow = customMoudle.getCustomsSize();
        java.util.List list = customMoudle.getCustoms(0, 20);
        pageData(list);
        cpage.setPageInfo(totalrow);
    }

    private void submmit() {
        String cu = textField.getText();
        CustomDAOImpl customMoudle =  DAOContentFactriy.getCustomDAO();
        if (cu.trim().length() > 0) {
            java.util.List list = customMoudle.getCustom(cu, cu, cu);
            pageData(list);
            cpage.setPageInfo(list.size());
        } else {
            int rt = customMoudle.getCustomsSize();
            java.util.List list = customMoudle.getCustoms(0, 20);
            pageData(list);
            cpage.setPageInfo(rt);
        }
    }

    private void pageData(java.util.List list) {
        if (list != null && list.size() > 0) {
            clear();
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            for (int i = 0; i < list.size(); i++) {
                Custom custom = (Custom) list.get(i);

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
