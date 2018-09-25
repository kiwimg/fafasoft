package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.action.PageAction;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.FlowLogDAOImpl;
import com.fafasoft.flow.pojo.Flowlog;
import com.fafasoft.flow.ui.widget.Page;
import com.fafasoft.flow.ui.widget.table.ImageRenderer;
import com.fafasoft.flow.util.GuiUtils;

import org.h2.engine.Constants;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.List;

public class SellOrderpane extends BaseJPanel implements LazyPageContent {
 
    private JTable table;
    private JXDatePicker datePicker;
    private JXDatePicker datePicker_1;
    private DefaultTableModel defaultTableModel;

    private String starttime = null;
    private String endtime = null;
    private Page page;


    public SellOrderpane() {
        setLayout(new BorderLayout(0, 0));
      
    }

	public void initPanel() {
		  {
	            JPanel panel = new JPanel();
	            panel.setPreferredSize(new Dimension(10, 80));
	            panel.setBorder(new TitledBorder(null, "\u67E5\u8BE2", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	            add(panel, BorderLayout.NORTH);
	            panel.setLayout(null);
	            {
	                JLabel label = new JLabel("\u5F00\u59CB\u65F6\u95F4");
	                label.setBounds(30, 36, 48, 15);
	                panel.add(label);
	            }
	            {
	                datePicker = new JXDatePicker();
	                datePicker.setBounds(83, 32, 121, 23);
	                datePicker.getEditor().setEnabled(false);

	                datePicker.getEditor().setFont(new Font("宋体", Font.BOLD, 12));
	                datePicker.getEditor().setColumns(10);
	                datePicker.setFormats("yyyy-MM-dd");
	                panel.add(datePicker);
	            }
	            {
	                JLabel label = new JLabel("\u7ED3\u675F\u65F6\u95F4");
	                label.setBounds(214, 36, 48, 15);
	                panel.add(label);
	            }
	            {
	                {
	                    datePicker_1 = new JXDatePicker();
	                    datePicker_1.setBounds(267, 32, 121, 23);
	                    datePicker_1.getEditor().setEnabled(false);
	                    datePicker_1.setFocusCycleRoot(true);
	                    datePicker_1.getEditor().setFont(new Font("宋体", Font.BOLD, 12));
	                    datePicker_1.setFormats("yyyy-MM-dd");
	                    panel.add(datePicker_1);
	                }
	            }
	            JButton button = new JButton("\u67E5  \u8BE2");
	            button.setBounds(406, 32, 69, 23);
	            button.addActionListener(new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	                    submmit();
	                }
	            });
	            panel.add(button);
	        }
	        {
	            JPanel panel = new JPanel();
	            panel.setBorder(new TitledBorder(null, "\u67E5\u8BE2\u5217\u8868", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	            add(panel, BorderLayout.CENTER);
	            panel.setLayout(new BorderLayout(0, 0));
	            {
	                {
	                    defaultTableModel = new DefaultTableModel(null,
	                            new String[]{
	                                    "货号", "销售总数量","销售总利润", "类型", "名称"
	                            }
	                    ) {
	                        boolean[] columnEditables = new boolean[]{
	                                false, false, false,false,false
	                        };

	                        public boolean isCellEditable(int row, int column) {
	                            return columnEditables[column];
	                        }
	                    };
	                }
	            }
	            JScrollPane scrollPane = new JScrollPane();
	            panel.add(scrollPane, BorderLayout.CENTER);
	            table = new JTable();
	            table.setRowHeight(21);
	            table.setModel(defaultTableModel);
	        	table.getColumnModel().getColumn(0)
	    				.setCellRenderer(new ImageRenderer());
	            scrollPane.setViewportView(table);
	            {
	                page = new Page(new PageAction() {
	                    public void pageFirst() {
	                        if (starttime != null && endtime != null) {
	                            List list = DAOContentFactriy.getFlowLogDAO().getFlowLogOrderBy(starttime, endtime, 0, 23);

	                            pageData(list);
	                        }

	                    }

	                    public void pagePrev(int pagenum) {
	                        if (starttime != null && endtime != null) {
	                            List list = DAOContentFactriy.getFlowLogDAO().getFlowLogOrderBy(starttime, endtime, pagenum, 23);

	                            pageData(list);
	                        }
	                    }

	                    public void pageNext(int pagenum) {
	                        if (starttime != null && endtime != null) {
	                            List list = DAOContentFactriy.getFlowLogDAO().getFlowLogOrderBy(starttime, endtime, pagenum, 23);

	                            pageData(list);
	                        }
	                    }

	                    public void pageLast(int pagenum) {
	                        if (starttime != null && endtime != null) {
	                            List list = DAOContentFactriy.getFlowLogDAO().getFlowLogOrderBy(starttime, endtime, pagenum, 23);

	                            pageData(list);
	                        }
	                    }

	                    public void itemStateChanged(int pagenum) {
	                        if (starttime != null && endtime != null) {
	                            List list = DAOContentFactriy.getFlowLogDAO().getFlowLogOrderBy(starttime, endtime, pagenum, 23);

	                            pageData(list);
	                        }
	                    }

	                    public void export(MouseEvent e) {
	                        try { //     //  "货号", "销售总数量","销售总利润", "类型", "名称"
	                            //  String filepath = file.getAbsolutePath();
	                        	  FlowLogDAOImpl flowLogMoudle =DAOContentFactriy.getFlowLogDAO();
	                      		
	                            List list = flowLogMoudle.getFlowLogOrderBy(starttime, endtime);
	                            if (list != null && list.size() > 0) {
	                                JFileChooser jfc = new JFileChooser("d:/");
	                                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	                                File fileff = new File("销售排行.csv");
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
	                                stringBuffer.append("序号");
	                                stringBuffer.append(",");
	                                stringBuffer.append("货号");
	                                stringBuffer.append(",");
	                                stringBuffer.append("销售总数量");
	                                stringBuffer.append(",");
	                                stringBuffer.append("销售总利润");
	                                stringBuffer.append(",");
	                                stringBuffer.append("类型");
	                                stringBuffer.append(",");
	                                stringBuffer.append("名称");

	                                stringBuffer.append("\r\n");
	                                output.write(String.valueOf(stringBuffer));
	                                for (int i = 0; i < list.size(); i++) {
	                                  Flowlog flowLog = (Flowlog) list.get(i);
	                                    StringBuilder sb = new StringBuilder(128);
	                                    sb.append(i + 1);
	                                    sb.append(",");
	                                    sb.append(flowLog.getCatno());
	                                    sb.append(",");
	                                    sb.append(flowLog.getAmount());
	                                    sb.append(",");
	                                    sb.append(flowLog.getLrprice());
	                                    sb.append(",");
	                                    sb.append(flowLog.getStockname());
	                                    sb.append(",");
	                                    sb.append(flowLog.getType());

	                                    sb.append("\r\n");
	                                    output.write(String.valueOf(sb));
	                                }
	                                output.close();

	                                JOptionPane.showMessageDialog(null, "文件导出成功");
	                            } else {
	                                JOptionPane.showMessageDialog(null, "没数据导出", null, JOptionPane.WARNING_MESSAGE);
	                            }
	                        } catch (Exception ex) {
	                            ex.printStackTrace();
	                        }
	                    }
	                });
	                panel.add(page, BorderLayout.SOUTH);
	            }
	        }
		
	}

    private void submmit() {
        starttime = datePicker.getEditor().getText();
        endtime = datePicker_1.getEditor().getText();
     
        if (starttime == null || starttime.trim().length() == 0) {
        	GuiUtils.toolTips(datePicker.getEditor(),"请输入开始时间");
            return;
        }
        if (endtime == null || endtime.trim().length() == 0) {
        	GuiUtils.toolTips(datePicker_1.getEditor(),"请输入结束时间");
            return;
        }
        long s = Long.parseLong(starttime.replaceAll("-", ""));
        long e = Long.parseLong(endtime.replaceAll("-", ""));


        if (s > e) {
        	GuiUtils.toolTips(datePicker.getEditor(),"输入时间范围错错误！开始时间应该小于结束时间");
        	return;
        }
        FlowLogDAOImpl flowLogMoudle =DAOContentFactriy.getFlowLogDAO();
		
        int toltal = flowLogMoudle.getFlowLogOrderSize(starttime, endtime);
        List list = flowLogMoudle.getFlowLogOrderBy(starttime, endtime, 0, 23);
        page.setPageInfo(toltal);
        pageData(list);
    }

    private void pageData(List list) {
        if (list != null && list.size() > 0) {
            clear();
            for (int i = 0; i < list.size(); i++) {
                Flowlog flowLog = (Flowlog) list.get(i);
                 //  "货号", "销售总数量","销售总利润", "类型", "名称"
                Object[] rowData = new Object[]{flowLog.getCatno(), flowLog.getAmount(),flowLog.getLrprice(), flowLog.getType(),flowLog.getStockname()};
                defaultTableModel.insertRow(i, rowData);
            }
        } else {
        	GuiUtils.toolTips(table,"没有销售排行，可能你没有出售货物！");
           // msg.setText("");
            clear();
        }
    }

    public void clear() {

        if (defaultTableModel.getRowCount() > 0) {
            int rows = defaultTableModel.getRowCount();
            for (int i = 0; i < rows; i++) {
                defaultTableModel.removeRow(0);
            }
        }
    }



}
