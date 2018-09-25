package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.action.PageAction;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.DailyExpensesDAOImpl;
import com.fafasoft.flow.dao.impl.FlowLogDAOImpl;
import com.fafasoft.flow.pojo.Config;
import com.fafasoft.flow.pojo.Flowlog;
import com.fafasoft.flow.pojo.Option;
import com.fafasoft.flow.pojo.User;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.widget.JTreeComboBox;
import com.fafasoft.flow.ui.widget.Options;
import com.fafasoft.flow.ui.widget.Page;
import com.fafasoft.flow.ui.widget.SelectType;
import com.fafasoft.flow.ui.widget.table.ImageRenderer;
import com.fafasoft.flow.util.DateHelper;
import com.fafasoft.flow.util.GuiUtils;

import org.h2.engine.Constants;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class YyeTjPanel extends BaseJPanel  implements LazyPageContent {
    private String starttime = null;
    private String endtime = null;
    private String huohaoM = null;
    private JXDatePicker datePicker;
    private JXDatePicker datePicker_1;
    private JLabel timeslice;
    private JLabel flowlog;
    private JLabel profits;
    private JLabel costs;
    private JTable table;
    private DefaultTableModel defaultTableModel;
    private Page page;
    private JLabel label_1;
    private JLabel label_3;
    private JTreeComboBox jTreeComboBox;
    private JTextField wohao;
    private JComboBox selUsercomboBox_1=null;
    private JComboBox xiaoshoucomboBox;
    public YyeTjPanel() {
    	
    }

	public void initPanel() {
		GridBagLayout gridBagLayout_1 = new GridBagLayout();
        gridBagLayout_1.columnWidths = new int[]{565, 0};
        gridBagLayout_1.rowHeights = new int[]{86, 130, 270, 0};
        gridBagLayout_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout_1.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout_1);
        {
            JPanel panel = new JPanel();
            panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u67E5\u8BE2", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            GridBagLayout gridBagLayout = new GridBagLayout();
            gridBagLayout.columnWidths = new int[]{42, 61, 105, 0, 121, 0, 136, 0, 0};
            gridBagLayout.rowHeights = new int[]{19, 32, 0};
            gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
            gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0E-4};
            panel.setLayout(gridBagLayout);
            GridBagConstraints gbc_panel = new GridBagConstraints();
            gbc_panel.anchor = GridBagConstraints.NORTH;
            gbc_panel.fill = GridBagConstraints.HORIZONTAL;
            gbc_panel.insets = new Insets(0, 0, 5, 0);
            gbc_panel.gridx = 0;
            gbc_panel.gridy = 0;
            add(panel, gbc_panel);
            JLabel label_2 = new JLabel("开始时间");
            GridBagConstraints gbc_label_2 = new GridBagConstraints();
            gbc_label_2.insets = new Insets(0, 0, 5, 5);
            gbc_label_2.gridx = 1;
            gbc_label_2.gridy = 0;
            panel.add(label_2, gbc_label_2);

            datePicker = new JXDatePicker();
            datePicker.getEditor().setEnabled(false);
            datePicker.setFormats("yyyy-MM-dd");
            datePicker.getEditor().setFont(new Font("宋体", Font.BOLD, 12));
            datePicker.setDate(DateHelper.currentDate());
            GridBagConstraints gbc_datePicker = new GridBagConstraints();
            gbc_datePicker.fill = GridBagConstraints.HORIZONTAL;
            gbc_datePicker.insets = new Insets(0, 0, 5, 5);
            gbc_datePicker.gridx = 2;
            gbc_datePicker.gridy = 0;
            panel.add(datePicker, gbc_datePicker);
            {
                JLabel label = new JLabel("\u7ED3\u675F\u65F6\u95F4");
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.anchor = GridBagConstraints.EAST;
                gbc.insets = new Insets(0, 5, 5, 5);
                gbc.gridx = 3;
                gbc.gridy = 0;
                panel.add(label, gbc);
            }

            datePicker_1 = new JXDatePicker();
            datePicker_1.setPreferredSize(new Dimension(25, 23));
            datePicker_1.getEditor().setEnabled(false);
            datePicker_1.setFormats("yyyy-MM-dd");
            datePicker_1.getEditor().setFont(new Font("宋体", Font.BOLD, 12));
            datePicker_1.setDate(DateHelper.currentDate());
            GridBagConstraints gbc_datePicker_1 = new GridBagConstraints();
            gbc_datePicker_1.fill = GridBagConstraints.HORIZONTAL;
            gbc_datePicker_1.insets = new Insets(0, 0, 5, 5);
            gbc_datePicker_1.gridx = 4;
            gbc_datePicker_1.gridy = 0;
            panel.add(datePicker_1, gbc_datePicker_1);
            {
                            JLabel label_4 = new JLabel("货物类型");

                            GridBagConstraints gbc_label_4 = new GridBagConstraints();
                            gbc_label_4.anchor = GridBagConstraints.EAST;
                            gbc_label_4.insets = new Insets(0, 0, 5, 5);
                            gbc_label_4.gridx = 5;
                            gbc_label_4.gridy = 0;
                            panel.add(label_4, gbc_label_4);
            }
            jTreeComboBox = new JTreeComboBox(getJTree(), false);
          
            GridBagConstraints gbc_comboBox = new GridBagConstraints();
            gbc_comboBox.insets = new Insets(0, 0, 5, 5);
            gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
            gbc_comboBox.gridx = 6;
            gbc_comboBox.gridy = 0;
            panel.add(jTreeComboBox, gbc_comboBox);
            
                        JLabel label = new JLabel("货号");
                        GridBagConstraints gbc_label = new GridBagConstraints();
                        gbc_label.anchor = GridBagConstraints.EAST;
                        gbc_label.insets = new Insets(0, 0, 0, 5);
                        gbc_label.gridx = 1;
                        gbc_label.gridy = 1;
                        panel.add(label, gbc_label);
            
                        wohao = new JTextField();
                        GridBagConstraints gbc_textField = new GridBagConstraints();
                        gbc_textField.insets = new Insets(0, 0, 0, 5);
                        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
                        gbc_textField.gridx = 2;
                        gbc_textField.gridy = 1;
                        panel.add(wohao, gbc_textField);
                        wohao.setColumns(10);
            {
            	JLabel label_4 = new JLabel("销售人员");
            	GridBagConstraints gbc_label_4 = new GridBagConstraints();
            	gbc_label_4.anchor = GridBagConstraints.EAST;
            	gbc_label_4.insets = new Insets(0, 0, 0, 5);
            	gbc_label_4.gridx = 3;
            	gbc_label_4.gridy = 1;
            	panel.add(label_4, gbc_label_4);
            }
            {
            	selUsercomboBox_1 = new JComboBox();
            	selUsercomboBox_1.setModel(new DefaultComboBoxModel(SelectType.getUserLists()));
            	GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
            	gbc_comboBox_1.insets = new Insets(0, 0, 0, 5);
            	gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
            	gbc_comboBox_1.gridx = 4;
            	gbc_comboBox_1.gridy = 1;
            	panel.add(selUsercomboBox_1, gbc_comboBox_1);
            }
            JButton button = new JButton("\u67E5  \u8BE2");
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    submmit();
                }
            });
            
            JLabel label_4 = new JLabel("销售类型");
            label_4.setFont(new Font("宋体", Font.PLAIN, 12));
            GridBagConstraints gbc_label_4 = new GridBagConstraints();
            gbc_label_4.anchor = GridBagConstraints.EAST;
            gbc_label_4.insets = new Insets(0, 0, 0, 5);
            gbc_label_4.gridx = 5;
            gbc_label_4.gridy = 1;
            panel.add(label_4, gbc_label_4);
            gbc_comboBox.insets = new Insets(0, 0, 0, 5);
            gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
            gbc_comboBox.gridx = 6;
            gbc_comboBox.gridy = 1;
            
            xiaoshoucomboBox= new JComboBox();
            xiaoshoucomboBox.setModel(new DefaultComboBoxModel(SelectType
    				.getSellTypes()));
            GridBagConstraints gbc_comboBoxxs = new GridBagConstraints();
            gbc_comboBoxxs.fill = GridBagConstraints.HORIZONTAL;
            gbc_comboBoxxs.insets = new Insets(0, 0, 0, 5);
            gbc_comboBoxxs.gridx = 6;
            gbc_comboBoxxs.gridy = 1;
            panel.add(xiaoshoucomboBox, gbc_comboBoxxs);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.EAST;
            gbc.gridx = 7;
            gbc.gridy = 1;
            panel.add(button, gbc);
        }
        {
            JPanel panel = new JPanel();
            panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u8425\u4E1A\u989D\u7EDF\u8BA1", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            panel.setLayout(null);
            GridBagConstraints gbc_panel = new GridBagConstraints();
            gbc_panel.fill = GridBagConstraints.BOTH;
            gbc_panel.insets = new Insets(0, 0, 5, 0);
            gbc_panel.gridx = 0;
            gbc_panel.gridy = 1;
            add(panel, gbc_panel);
            {
                JLabel label = new JLabel("\u65F6\u95F4\u6BB5\uFF1A");
                label.setBounds(72, 16, 48, 15);
                panel.add(label);
            }
            {
                JLabel label = new JLabel("流水总计：");
                label.setBounds(60, 41, 60, 17);
                panel.add(label);
            }
            {
                flowlog = new JLabel("0");
                flowlog.setBounds(125, 41, 70, 17);
                flowlog.setFont(new Font("宋体", Font.BOLD, 12));
                flowlog.setBorder(UIManager.getBorder("MenuBar.border"));
                panel.add(flowlog);
            }
            {
                JLabel label = new JLabel("成本总计：");
                label.setBounds(288, 41, 60, 17);
                panel.add(label);
            }
            {
                costs = new JLabel("0");
                costs.setBounds(351, 42, 97, 17);
                costs.setFont(new Font("宋体", Font.BOLD, 12));
                costs.setBorder(UIManager.getBorder("MenuBar.border"));
                panel.add(costs);
            }
            {
                JLabel label = new JLabel("利润总计：");
                label.setBounds(60, 94, 60, 17);
                panel.add(label);
            }
            {
                profits = new JLabel("0");
                profits.setBounds(125, 94, 70, 17);
                profits.setFont(new Font("宋体", Font.BOLD, 12));
                profits.setBorder(UIManager.getBorder("MenuBar.border"));
                panel.add(profits);
            }
            {
                timeslice = new JLabel("");
                timeslice.setFont(new Font("宋体", Font.BOLD, 12));
                timeslice.setBounds(125, 16, 335, 15);
                panel.add(timeslice);
            }

            JLabel label = new JLabel("日常支出总计：");
            label.setBounds(36, 68, 84, 15);
            panel.add(label);

            label_1 = new JLabel("0");
            label_1.setFont(new Font("宋体", Font.BOLD, 12));
            label_1.setBorder(UIManager.getBorder("MenuBar.border"));
            label_1.setBounds(125, 68, 70, 15);
            panel.add(label_1);

            JLabel label_2 = new JLabel("每月固定支出总计：");
            label_2.setBounds(240, 68, 108, 15);
            panel.add(label_2);

            label_3 = new JLabel("0");
            label_3.setFont(new Font("宋体", Font.BOLD, 12));
            label_3.setBorder(UIManager.getBorder("MenuBar.border"));
            label_3.setBounds(351, 69, 97, 15);
            panel.add(label_3);
            {
                JLabel label_4 = new JLabel("利润总计=流水总计 - 成本总计 - 日常支出总计");
                label_4.setBounds(351, 95, 289, 15);
                panel.add(label_4);
            }
        }
        {
            JPanel panel = new JPanel();
            panel.setBorder(new TitledBorder(null, "\u6D41\u6C34\u660E\u7EC6", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            GridBagConstraints gbc_panel = new GridBagConstraints();
            gbc_panel.fill = GridBagConstraints.BOTH;
            gbc_panel.gridx = 0;
            gbc_panel.gridy = 2;
            add(panel, gbc_panel);
            GridBagLayout gbl_panel = new GridBagLayout();
            gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
            gbl_panel.rowHeights = new int[]{221, 0, 0};
            gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
            gbl_panel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
            panel.setLayout(gbl_panel);
            {
                JScrollPane scrollPane = new JScrollPane();
                GridBagConstraints gbc_scrollPane = new GridBagConstraints();
                gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
                gbc_scrollPane.fill = GridBagConstraints.BOTH;
                gbc_scrollPane.gridx = 1;
                gbc_scrollPane.gridy = 0;
                panel.add(scrollPane, gbc_scrollPane);
                {
                    table = new JTable();
                    table.setRowHeight(21);
                    defaultTableModel = new DefaultTableModel(
                            new Object[][]{
                            },
                            new String[]{
                                    "\u8D27\u53F7", "\u552E\u4EF7", "数量", "\u5229\u6DA6", "进货价", "\u7C7B\u578B", "\u540D\u79F0","销售人员", "\u65F6\u95F4","销售类型"
                            }
                    ) {
                        boolean[] columnEditables = new boolean[]{
                                false, false, false, false, false, false, false, false, false, false
                        };
                        Class[] columnTypes = new Class[]{
                                String.class, BigDecimal.class, Integer.class, BigDecimal.class, BigDecimal.class, String.class, String.class, String.class,String.class,String.class
                        };

                        public Class getColumnClass(int columnIndex) {
                            return columnTypes[columnIndex];
                        }

                        public boolean isCellEditable(int row, int column) {
                            return columnEditables[column];
                        }
                    };
                    table.setAutoCreateRowSorter(true);
                    table.setModel(defaultTableModel);
                    scrollPane.setViewportView(table);
                }
                DefaultTableCellRenderer fontColor = new DefaultTableCellRenderer() {
                    public void setValue(Object value) { //重写setValue方法，从而可以动态设置列单元字体颜色
                        BigDecimal bigDecimal = new BigDecimal(String.valueOf(value));
                        if (bigDecimal.compareTo(new BigDecimal(0)) == -1) {
                            setForeground(Color.green);
                        } else {
                            setForeground(Color.red);

                        }
                        setText((value == null) ? "" : value.toString());
                    }
                };

                table.getColumn("利润").setCellRenderer(fontColor);
                table.getColumn("售价").setCellRenderer(fontColor);
                table.getColumn("数量").setCellRenderer(fontColor);
            	table.getColumnModel().getColumn(0)
				.setCellRenderer(new ImageRenderer());
            }
            {
                page = new Page(new PageAction() {

                    public void pageFirst() {
                      
                        pageView(0, 20);
                    }

                    public void pagePrev(int pagenum) {
                      
                        pageView(pagenum, 20);
                    }

                    public void pageNext(int pagenum) {

                        pageView(pagenum, 20);
                    }

                    public void pageLast(int pagenum) {
                        pageView(pagenum, 20);
                    }

                    public void itemStateChanged(int pagenum) {
                    	pageView(pagenum, 20);
                    }
                    private void pageView( int pagenum ,int pagemax ){
                    	  if (starttime != null && endtime != null) {
                          	  FlowLogDAOImpl flowLogMoudle = DAOContentFactriy.getFlowLogDAO();
                              String selvalue = getType();
                              User user = (User)selUsercomboBox_1.getSelectedItem();
                              String usId =user.getId();
                              Options options =(Options)xiaoshoucomboBox.getSelectedItem();
                              List list = flowLogMoudle.getStatistical(starttime, endtime, selvalue,huohaoM,usId,options.getKey(), pagenum, pagemax);
                      
                              pageData(list);
                          }
                    }
                    public void export(MouseEvent e) {
                        try {
                            //  String filepath = file.getAbsolutePath();
                        	FlowLogDAOImpl flowLogMoudle = DAOContentFactriy.getFlowLogDAO();
                            List list = flowLogMoudle.getStatistical(starttime, endtime, getType());
                            if (list != null && list.size() > 0) {
                                JFileChooser jfc = new JFileChooser("d:/");
                                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                                File fileff = new File(starttime + "--" + endtime + "营业额明细.csv");
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
                                stringBuffer.append("货号");
                                stringBuffer.append(",");
                                stringBuffer.append("售价");
                                stringBuffer.append(",");
                                stringBuffer.append("数量");
                                stringBuffer.append(",");
                                stringBuffer.append("利润");
                                stringBuffer.append(",");
                                stringBuffer.append("成本");
                                stringBuffer.append(",");
                                stringBuffer.append("类型");
                                stringBuffer.append(",");
                                stringBuffer.append("名称");
                                stringBuffer.append(",");
                                stringBuffer.append("销售时间");
                                stringBuffer.append("\r\n");
                                output.write(String.valueOf(stringBuffer));
                                for (int i = 0; i < list.size(); i++) {
                                    Flowlog flowlog = (Flowlog) list.get(i);
                                    StringBuilder sb = new StringBuilder(128);
                                    sb.append(flowlog.getCatno());
                                    sb.append(",");
                                    sb.append(flowlog.getSellprice());
                                    sb.append(",");
                                    sb.append(flowlog.getAmount());
                                    sb.append(",");
                                    sb.append(flowlog.getLrprice());
                                    sb.append(",");
                                    sb.append(flowlog.getCostprice());
                                    sb.append(",");
                                    sb.append(flowlog.getType());
                                    sb.append(",");
                                    sb.append(flowlog.getStockname());
                                    sb.append(",");
                                    sb.append(flowlog.getDate());
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
                GridBagConstraints gbc_page = new GridBagConstraints();
                gbc_page.insets = new Insets(0, 0, 0, 5);
                gbc_page.fill = GridBagConstraints.BOTH;
                gbc_page.gridx = 1;
                gbc_page.gridy = 1;
                panel.add(page, gbc_page);
            }
        }
        jTreeComboBox.setTree(getJTree());
		
	}
    private void submmit() {
        starttime = datePicker.getEditor().getText();
        endtime = datePicker_1.getEditor().getText();

        if (starttime == null || starttime.trim().length() == 0) {
           // msg.setText();
        	GuiUtils.toolTips(datePicker.getEditor(),"请输入开始时间");
          
            return;
        }
        if (endtime == null || endtime.trim().length() == 0) {
            
        	GuiUtils.toolTips( datePicker_1.getEditor(),"请输入结束时间");
           //.requestFocus();
            return;
        }
        String nows = DateHelper.getNowDateTime();
        long now = Long.parseLong(nows.replaceAll("-", ""));
        long s = Long.parseLong(starttime.replaceAll("-", ""));
        long e = Long.parseLong(endtime.replaceAll("-", ""));

        if (e > now) {
           // msg.setText("");
        	GuiUtils.toolTips( datePicker_1.getEditor(),"输入时间范围错误！只能统计今天以前的营业额");
            return;
        }
        if (s > e) {
        	GuiUtils.toolTips( datePicker.getEditor(),"输入时间范围错误！开始时间应该小于结束时间");
           
            return;
        }
        //货物类型
        String selvalue = getType();
        
        huohaoM = wohao.getText();

        timeslice.setText(starttime + " 到 " + endtime);
        FlowLogDAOImpl flowLogMoudle = DAOContentFactriy.getFlowLogDAO();
        User user = (User)selUsercomboBox_1.getSelectedItem();
        String usId =user.getId();
        Options options =(Options)xiaoshoucomboBox.getSelectedItem();
        int toltal = flowLogMoudle.getStatisticalSize(starttime, endtime, selvalue,huohaoM,usId,options.getKey());
        List list = flowLogMoudle.getStatistical(starttime, endtime, selvalue,huohaoM,usId,options.getKey(), 0, 20);
        
        page.setPageInfo(toltal);
        
        //流水总计
        BigDecimal flowprice = flowLogMoudle.sumFlowPrice(starttime, endtime, selvalue,huohaoM,usId,options.getKey());
        
        //利润总计
        BigDecimal lrprice = flowLogMoudle.sumLrPrice(starttime, endtime, selvalue,huohaoM,usId,options.getKey());
        if (lrprice == null) {
            lrprice = new BigDecimal(0);
        }
        //成本总计
        BigDecimal costprice = flowLogMoudle.sumCostPrice(starttime, endtime, selvalue,huohaoM,usId,options.getKey());
        flowlog.setText(String.valueOf(flowprice));
        costs.setText(String.valueOf(costprice)); 
     	  DailyExpensesDAOImpl expensesMoudle = DAOContentFactriy.getDailyExpensesDAO();
        //日常支出
        BigDecimal zczj = expensesMoudle.sumDailyExpensesPay(starttime, endtime);
        if (zczj != null) {
            label_1.setText(String.valueOf(zczj));
        } else {
            zczj = new BigDecimal(0);
        }

        profits.setText(String.valueOf(lrprice.subtract(zczj)));

        label_3.setText(String.valueOf(getTotal()));
       // java.util.List list = flowLogMoudle.getFlowlogByToday(Constant.FLOW_TYPE_SELL);
       
        pageData(list);
    }

    private double getTotal() {
        double total = 0;
        Config configmygdzczh =DAOContentFactriy.getConfigDAO().getConfig(Constant.MYGDZCZH);
        if (configmygdzczh != null) {
            total = Double.parseDouble(configmygdzczh.getValue());
        }
        return total;
    }

    private void pageData(List list) {
        cleartable();
        if (list != null && list.size() > 0) {
            HashMap userMap = DAOContentFactriy.getUserDAO().getUserMap();
            for (int i = 0; i < list.size(); i++) {
                Flowlog flowlog = (Flowlog) list.get(i);
                String usid = flowlog.getUserId();
                String uname="";
                if(usid != null && usid.length() >0) {
                	uname = String.valueOf(userMap.get(usid));
                }
                //
                String sellTye = "";
                if(Constant.FLOW_TYPE_SELL.equals(flowlog.getFlowflag()) || Constant.FLOW_TYPE_PLSELL.equals(flowlog.getFlowflag())) {
                	sellTye ="销售";
                }else if(Constant.FLOW_TYPE_TH.equals(flowlog.getFlowflag())) {
                	sellTye ="退货";
                }
                Object[] rowData = new Object[]{flowlog.getCatno(), flowlog.getSellprice(), flowlog.getAmount(), flowlog.getLrprice(), 
                		flowlog.getCostprice(), flowlog.getType(), 
                		flowlog.getStockname(), uname,flowlog.getDate(),sellTye
                		};
                defaultTableModel.insertRow(0, rowData);
            }
        } else {
            clear();
        }
    }

    public void cleartable() {

        if (defaultTableModel.getRowCount() > 0) {
            int rows = defaultTableModel.getRowCount();
            for (int i = 0; i < rows; i++) {
                defaultTableModel.removeRow(0);
            }
        }
    }

    private void clear() {

        flowlog.setText("0");
        profits.setText("0");
        costs.setText("0");
    }

    private String getType() {
    	Option options = (Option) jTreeComboBox.getSelectedItemValue();
        if(options != null) {
            return options.getText();
        }else {
        	 return null;
        }
    }
}
