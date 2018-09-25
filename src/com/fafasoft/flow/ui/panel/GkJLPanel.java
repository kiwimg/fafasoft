package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.action.PageAction;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.FlowLogDAOImpl;
import com.fafasoft.flow.pojo.Flowlog;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.widget.Page;
import com.fafasoft.flow.ui.widget.table.ImageRenderer;
import com.fafasoft.flow.util.DateHelper;
import org.h2.engine.Constants;
import org.jdesktop.swingx.JXDatePicker;
import com.fafasoft.flow.util.GuiUtils;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class GkJLPanel extends BaseJPanel  implements LazyPageContent{
 
    private JTextField textField;
    private JTextField textField_1;
    private JTable table;
    private JXDatePicker datePicker;
    private JXDatePicker datePicker_1;
    private Page page;

    public GkJLPanel() {
        setLayout(new BorderLayout(0, 0));
    }

	public void initPanel() {

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(10, 105));
        panel.setBorder(new TitledBorder(null, "\u67E5\u8BE2", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        add(panel, BorderLayout.NORTH);
        panel.setLayout(null);

        JLabel label = new JLabel("开始时间");
        label.setBounds(40, 22, 48, 15);
        panel.add(label);

        datePicker = new JXDatePicker();
        datePicker.setBounds(93, 18, 114, 23);
        datePicker.getEditor().setEditable(false);
        datePicker.setFormats("yyyy-MM-dd");
        datePicker.getEditor().setColumns(10);
        datePicker.getEditor().setFont(new Font("宋体", Font.PLAIN, 12));
        datePicker.setFont(new Font("宋体", Font.BOLD, 12));
        panel.add(datePicker);

        JLabel label_2 = new JLabel("结束时间");
        label_2.setBounds(236, 22, 48, 15);
        panel.add(label_2);

        datePicker_1 = new JXDatePicker();
        datePicker_1.setBounds(289, 18, 114, 23);
        datePicker_1.getEditor().setEditable(false);
        datePicker_1.setFormats("yyyy-MM-dd");
        datePicker_1.getEditor().setColumns(10);

        datePicker_1.setFont(new Font("宋体", Font.BOLD, 12));
        panel.add(datePicker_1);

        JLabel label_1 = new JLabel("客户号码");
        label_1.setBounds(40, 46, 48, 21);
        panel.add(label_1);

        textField = new JTextField();
        textField.setBounds(93, 46, 114, 21);

        panel.add(textField);
        textField.setColumns(10);

        JLabel label_3 = new JLabel("客户名称");
        label_3.setBounds(236, 49, 48, 15);
        panel.add(label_3);

        textField_1 = new JTextField();
        textField_1.setBounds(289, 46, 114, 21);

        panel.add(textField_1);
        textField_1.setColumns(10);

        JButton button = new JButton(" 查 询 ");
        button.setBounds(328, 72, 75, 23);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String starttime = datePicker.getEditor().getText();
                String endtime = datePicker_1.getEditor().getText();
                  // msg.setText("");
                if (starttime == null || starttime.trim().length() == 0) {
                   // msg.setText("请输入开始时间");
                	GuiUtils.toolTips(datePicker.getEditor(),"请选择开始时间");
                    
                    return;
                }
                if (endtime == null || endtime.trim().length() == 0) {
                
                	GuiUtils.toolTips(datePicker_1.getEditor(),"请选择结束时间");
                   // datePicker_1.getEditor().requestFocus();
                    return;
                }
                String nows = DateHelper.getNowDateTime();
                long now = Long.parseLong(nows.replaceAll("-", ""));
                long s = Long.parseLong(starttime.replaceAll("-", ""));
                long end = Long.parseLong(endtime.replaceAll("-", ""));
                if (end > now) {
                   
                	GuiUtils.toolTips(datePicker_1.getEditor(),"输入时间范围错误！只能查看今天以前的购买记录");
                    return;
                }
                if (s > end) {
                    
                	GuiUtils.toolTips(datePicker.getEditor(),"输入时间范围错误！开始时间应该小于结束时间");
                    return;
                }
                String cno = textField.getText();
                String cnName = textField_1.getText();
                FlowLogDAOImpl flowLogMoudle =DAOContentFactriy.getFlowLogDAO();
        		
                int size = flowLogMoudle.getFlowLogKeHLogSize(starttime, endtime, cno, cnName);
                java.util.List list = flowLogMoudle.getFlowLogKeHLog(starttime, endtime, cno, cnName, 0, 20);
                page.setPageInfo(size);
                pageData(list);
            }
        });

        panel.add(button);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null,
                "\u5BA2\u6237\u8D2D\u4E70\u660E\u7EC6", TitledBorder.LEADING,
                TitledBorder.TOP, null, null));

        add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        panel_1.add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        table.setModel(new DefaultTableModel(
        	new Object[][] {
        	},
        	new String[] {
        		"id", "\u5BA2\u6237\u53F7", "\u5BA2\u6237\u540D\u79F0", "\u8D2D\u4E70\u65F6\u95F4", "\u8D27\u7269\u540D\u79F0", "\u8D27\u53F7", "\u7C7B\u578B", "\u8D2D\u4E70\u6570\u91CF", "\u8D2D\u4E70\u4EF7\u683C"
        	}
        ) {
        	boolean[] columnEditables = new boolean[] {
        		false, false, false, false, false, false, false, false, false
        	};
        	public boolean isCellEditable(int row, int column) {
        		return columnEditables[column];
        	}
        });
        table.getColumnModel().getColumn(0).setPreferredWidth(0);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(1)
		.setCellRenderer(new ImageRenderer());
        scrollPane.setViewportView(table);

        page = new Page(new PageAction() {
            public void pageFirst() {
                String starttime = datePicker.getEditor().getText();
                String endtime = datePicker_1.getEditor().getText();
                String cno = textField.getText();
                String cnName = textField_1.getText();
                FlowLogDAOImpl flowLogMoudle =DAOContentFactriy.getFlowLogDAO();
        		
                java.util.List list = flowLogMoudle.getFlowLogKeHLog(starttime, endtime, cno, cnName, 0, 20);
                pageData(list);
            }

            public void pagePrev(int pagenum) {
                String starttime = datePicker.getEditor().getText();
                String endtime = datePicker_1.getEditor().getText();
                String cno = textField.getText();
                String cnName = textField_1.getText();
                FlowLogDAOImpl flowLogMoudle =DAOContentFactriy.getFlowLogDAO();
        		
                java.util.List list = flowLogMoudle.getFlowLogKeHLog(starttime, endtime, cno, cnName, pagenum, 20);
                pageData(list);
            }

            public void pageNext(int pagenum) {
                String starttime = datePicker.getEditor().getText();
                String endtime = datePicker_1.getEditor().getText();
                String cno = textField.getText();
                String cnName = textField_1.getText();
                FlowLogDAOImpl flowLogMoudle =DAOContentFactriy.getFlowLogDAO();
        		
                java.util.List list = flowLogMoudle.getFlowLogKeHLog(starttime, endtime, cno, cnName, pagenum, 20);
                pageData(list);
            }

            public void pageLast(int pagenum) {
                String starttime = datePicker.getEditor().getText();
                String endtime = datePicker_1.getEditor().getText();
                String cno = textField.getText();
                String cnName = textField_1.getText();
                FlowLogDAOImpl flowLogMoudle =DAOContentFactriy.getFlowLogDAO();
        		
                java.util.List list = flowLogMoudle.getFlowLogKeHLog(starttime, endtime, cno, cnName, pagenum, 20);
                pageData(list);
            }

            public void itemStateChanged(int pagenum) {
                String starttime = datePicker.getEditor().getText();
                String endtime = datePicker_1.getEditor().getText();
                String cno = textField.getText();
                String cnName = textField_1.getText();
                FlowLogDAOImpl flowLogMoudle =DAOContentFactriy.getFlowLogDAO();
        		
                java.util.List list = flowLogMoudle.getFlowLogKeHLog(starttime, endtime, cno, cnName, pagenum, 20);
                pageData(list);
            }

            public void export(MouseEvent e) {
                try {
                	FlowLogDAOImpl flowLogMoudle =DAOContentFactriy.getFlowLogDAO();
            		
                    String starttime = datePicker.getEditor().getText();
                    String endtime = datePicker_1.getEditor().getText();
                    String cno = textField.getText();
                    String cnName = textField_1.getText();
                    java.util.List list = flowLogMoudle.getFlowLogKeHLog(starttime, endtime, cno, cnName,0,200000);
                    if (list != null && list.size() > 0) {
                        JFileChooser jfc = new JFileChooser("d:/");
                        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                        File fileff = new File(starttime + "--" + endtime + "客户购买明细.csv");
                        jfc.setSelectedFile(fileff);
                        int result = jfc.showSaveDialog(table);
                        if (result == JFileChooser.CANCEL_OPTION) return;
                        File savedFile = jfc.getSelectedFile();
                        if (savedFile.exists()) {
                            int overwriteSelect = JOptionPane.showConfirmDialog(table, "<html><font size=3>文件" + savedFile.getName() + "已存在，是否覆盖?</font><html>", "是否覆盖?",
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
                        stringBuffer.append("客户名称");
                        stringBuffer.append(",");
                        stringBuffer.append("货物名称");
                        stringBuffer.append(",");
                        stringBuffer.append("购买时间");
                        stringBuffer.append(",");
                        stringBuffer.append("购买价格");
                        stringBuffer.append("\r\n");
                        output.write(String.valueOf(stringBuffer));
                        for (int i = 0; i < list.size(); i++) {
                            Flowlog flowlog = (Flowlog) list.get(i);
                            StringBuilder sb = new StringBuilder(128);
                            sb.append(flowlog.getCustomNo());
                            sb.append(",");
                            sb.append(flowlog.getCustomName());
                            sb.append(",");
                            sb.append(flowlog.getStockname());
                            sb.append(",");
                            sb.append(flowlog.getDate());
                            sb.append(",");
                            sb.append(flowlog.getSellprice());
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
		
	}

    private void pageData(java.util.List list) {
        cleartable();
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Flowlog flowlog = (Flowlog) list.get(i);
                Object[] rowData = new Object[]{
                		flowlog.getFlowno(), 
                		flowlog.getCustomNo(), 
                		flowlog.getCustomName(),
                		flowlog.getDate(), 
                		flowlog.getStockname(), 
                		flowlog.getCatno(),
                		flowlog.getType(),
                		flowlog.getAmount(),
                		flowlog.getSellprice()};
                defaultTableModel.insertRow(0, rowData);
            }
        }
    }

    public void cleartable() {
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        if (defaultTableModel.getRowCount() > 0) {
            int rows = defaultTableModel.getRowCount();
            for (int i = 0; i < rows; i++) {
                defaultTableModel.removeRow(0);
            }
        }
    }

}
