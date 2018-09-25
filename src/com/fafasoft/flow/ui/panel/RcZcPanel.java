package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.DailyExpensesDAOImpl;
import com.fafasoft.flow.dao.impl.OptionDAOImpl;
import com.fafasoft.flow.pojo.DailyExpenses;
import com.fafasoft.flow.pojo.Option;
import com.fafasoft.flow.ui.LimitedDocument;
import com.fafasoft.flow.ui.widget.SelectType;
import com.fafasoft.flow.util.DateHelper;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.UUID;

public class RcZcPanel extends JPanel  implements LazyPageContent{
 
    private JTextField textField;
    private JComboBox comboBox;
    private JXDatePicker datePicker;
    private final JPanel panel_2 = new JPanel();
    private JLabel label_7;
    private JLabel label_6;
    private JCheckBox zhichu;
    private JCheckBox shouru;
    private JTable table;
    private JTable table_1;
    private JLabel label_9;
    private JTabbedPane tabbedPane;


    public RcZcPanel() {
        setLayout(new BorderLayout(0, 0));       
    }

	public void initPanel() {
		 JPanel panel = new JPanel();
	        panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u5F55\u5165\u6536\u652F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	        add(panel, BorderLayout.NORTH);
	        GridBagLayout gbl_panel = new GridBagLayout();
	        gbl_panel.columnWidths = new int[]{37, 0, 50, 37, 100, 46, 0, 0, 96, 0};
	        gbl_panel.rowHeights = new int[]{27, 0, 34, 0};
	        gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
	        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
	        panel.setLayout(gbl_panel);
	        ButtonGroup bg = new ButtonGroup();
	        label_7 = new JLabel("");
	        label_7.setForeground(Color.RED);

	        GridBagConstraints gbc_label_7 = new GridBagConstraints();
	        gbc_label_7.anchor = GridBagConstraints.WEST;
	        gbc_label_7.gridwidth = 7;
	        gbc_label_7.insets = new Insets(0, 0, 5, 5);
	        gbc_label_7.gridx = 1;
	        gbc_label_7.gridy = 0;
	        panel.add(label_7, gbc_label_7);
	        zhichu = new JCheckBox("支出");
	        zhichu.setSelected(true);
	        zhichu.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                tabbedPane.setSelectedIndex(0);
	                comboBox.setModel(new DefaultComboBoxModel(SelectType.getOptions(Constant.TYPE_ZC)));
	            }
	        });
	        GridBagConstraints gbc_checkBox = new GridBagConstraints();
	        gbc_checkBox.anchor = GridBagConstraints.WEST;
	        gbc_checkBox.insets = new Insets(0, 0, 5, 5);
	        gbc_checkBox.gridx = 2;
	        gbc_checkBox.gridy = 1;
	        panel.add(zhichu, gbc_checkBox);
	        bg.add(zhichu);
	        JLabel label_t = new JLabel("时间");
	        GridBagConstraints gbc_label = new GridBagConstraints();
	        gbc_label.anchor = GridBagConstraints.EAST;
	        gbc_label.insets = new Insets(0, 0, 5, 5);
	        gbc_label.gridx = 3;
	        gbc_label.gridy = 1;
	        panel.add(label_t, gbc_label);
	        gbc_label.gridx = 7;
	        gbc_label.gridy = 0;

	        datePicker = new JXDatePicker();
	        datePicker.getEditor().setEditable(false);
	        datePicker.getEditor().setFont(new Font("宋体", Font.BOLD, 12));
	        datePicker.getEditor().setColumns(10);
	        datePicker.setFormats("yyyy-MM-dd");
	        datePicker.setDate(DateHelper.currentDate());
	        GridBagConstraints gbc_datePicker = new GridBagConstraints();
	        gbc_datePicker.fill = GridBagConstraints.HORIZONTAL;
	        gbc_datePicker.insets = new Insets(0, 0, 5, 5);
	        gbc_datePicker.gridx = 4;
	        gbc_datePicker.gridy = 1;
	        panel.add(datePicker, gbc_datePicker);

	        JLabel label_2 = new JLabel("金额");
	        GridBagConstraints gbc_label_2 = new GridBagConstraints();
	        gbc_label_2.insets = new Insets(0, 0, 5, 5);
	        gbc_label_2.anchor = GridBagConstraints.EAST;
	        gbc_label_2.gridx = 5;
	        gbc_label_2.gridy = 1;
	        panel.add(label_2, gbc_label_2);

	        textField = new JTextField();
        String dubString = "1234567890.";
        textField.setDocument(new LimitedDocument(30, dubString));
	        GridBagConstraints gbc_textField = new GridBagConstraints();
	        gbc_textField.insets = new Insets(0, 0, 5, 5);
	        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
	        gbc_textField.gridx = 6;
	        gbc_textField.gridy = 1;
	        panel.add(textField, gbc_textField);
	        textField.setColumns(10);
	    	
	        shouru = new JCheckBox("收入");

	        shouru.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                tabbedPane.setSelectedIndex(1);
	                comboBox.setModel(new DefaultComboBoxModel(SelectType.getOptions(Constant.TYPE_SR)));
	            }
	        });
	        GridBagConstraints gbc_checkBox_1 = new GridBagConstraints();
	        gbc_checkBox_1.anchor = GridBagConstraints.WEST;
	        gbc_checkBox_1.insets = new Insets(0, 0, 0, 5);
	        gbc_checkBox_1.gridx = 2;
	        gbc_checkBox_1.gridy = 2;
	        panel.add(shouru, gbc_checkBox_1);
	        bg.add(shouru);
	        JLabel label_1 = new JLabel("项目");
	        GridBagConstraints gbc_label_1 = new GridBagConstraints();
	        gbc_label_1.anchor = GridBagConstraints.EAST;
	        gbc_label_1.insets = new Insets(0, 0, 0, 5);
	        gbc_label_1.gridx = 3;
	        gbc_label_1.gridy = 2;
	        panel.add(label_1, gbc_label_1);

	        comboBox = new JComboBox();
	        comboBox.setMaximumSize(new Dimension(27, 21));
	        comboBox.setMinimumSize(new Dimension(27, 21));
	        comboBox.setEditable(true);


	        GridBagConstraints gbc_comboBox = new GridBagConstraints();
	        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
	        gbc_comboBox.insets = new Insets(0, 0, 0, 5);
	        gbc_comboBox.gridx = 4;
	        gbc_comboBox.gridy = 2;
	        panel.add(comboBox, gbc_comboBox);

	        JButton button = new JButton("  保存  ");


	        button.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent e) {
	                submmit();
	            }
	        });
	        GridBagConstraints gbc_button = new GridBagConstraints();
	        gbc_button.insets = new Insets(0, 0, 0, 5);
	        gbc_button.anchor = GridBagConstraints.EAST;
	        gbc_button.gridx = 6;
	        gbc_button.gridy = 2;
	        panel.add(button, gbc_button);


	        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	        tabbedPane.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseClicked(MouseEvent e) {
	        		int index = tabbedPane.getSelectedIndex();

	                zhichu.setSelected(index ==0);
	                shouru.setSelected(index == 1);
	        		//tabbedPane.setSelectedIndex(index == 0? 0:1);
	        	}
	        });
	        tabbedPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	        add(tabbedPane, BorderLayout.CENTER);

	        JPanel panel_1 = new JPanel();
	        panel_1.setBorder(null);
	        //zhichu.png
	        ImageIcon imageIcon1 = new ImageIcon(OptionsTypePanel.class.getResource("/com/fafasoft/flow/resource/image/zhichu.png"));
	        tabbedPane.addTab("支出", imageIcon1, panel_1, null);
	        panel_1.setLayout(new BorderLayout(0, 0));

	        JScrollPane scrollPane = new JScrollPane();
	        scrollPane.setBorder(null);
	        panel_1.add(scrollPane, BorderLayout.CENTER);

	        table = new JTable();
	    	table.getTableHeader().setReorderingAllowed(false);
	        table.setRowHeight(21);
	        table.setBorder(null);
	        table.setModel(new DefaultTableModel(
	                null,
	                new String[]{
	                        "id", "\u652F\u51FA\u65E5\u671F", "\u652F\u51FA\u9879\u76EE", "\u652F\u51FA\u91D1\u989D", "\u64CD\u4F5C"
	                }
	        ) {
	            boolean[] columnEditables = new boolean[]{
	                    false, false, false, false, false
	            };

	            public boolean isCellEditable(int row, int column) {
	                return columnEditables[column];
	            }
	        });
	        table.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent e) {
	                clickedTable("确定删除此条数据?", "删除支出数据", table);
	            }
	        });
	        table.getColumnModel().getColumn(0).setResizable(false);
	        table.getColumnModel().getColumn(0).setPreferredWidth(0);
	        table.getColumnModel().getColumn(0).setMinWidth(0);
	        table.getColumnModel().getColumn(0).setMaxWidth(0);
	        table.getColumnModel().getColumn(2).setPreferredWidth(257);
	        table.getColumnModel().getColumn(3).setPreferredWidth(107);
	        table.getColumnModel().getColumn(4).setPreferredWidth(60);
	        table.getColumnModel().getColumn(4).setMaxWidth(60);
	        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	        renderer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        TableColumn tc = table.getColumn("操作");
	        tc.setPreferredWidth(60);
	        tc.setCellRenderer(renderer);
	        scrollPane.setViewportView(table);

	        JPanel panel_3 = new JPanel();
	        panel_3.setBorder(null);
	        ImageIcon imageIcon2 = new ImageIcon(OptionsTypePanel.class.getResource("/com/fafasoft/flow/resource/image/shouru.png"));
	        tabbedPane.addTab("收入", imageIcon2, panel_3, null);
	        panel_3.setLayout(new BorderLayout(0, 0));

	        JScrollPane scrollPane_1 = new JScrollPane();
	        scrollPane_1.setBorder(null);
	        panel_3.add(scrollPane_1, BorderLayout.CENTER);

	        table_1 = new JTable();
	        table_1.setRowHeight(21);
	        table_1.setBorder(null);
	        table_1.setModel(new DefaultTableModel(
	                null,
	                new String[]{
	                        "id", "\u6536\u5165\u65E5\u671F", "\u6536\u5165\u9879\u76EE", "\u6536\u5165\u91D1\u989D", "\u64CD\u4F5C"
	                }
	        ) {
	            boolean[] columnEditables = new boolean[]{
	                    false, false, false, false, false
	            };

	            public boolean isCellEditable(int row, int column) {
	                return columnEditables[column];
	            }
	        });
	        table_1.getColumnModel().getColumn(0).setResizable(false);
	        table_1.getColumnModel().getColumn(0).setPreferredWidth(0);
	        table_1.getColumnModel().getColumn(0).setMinWidth(0);
	        table_1.getColumnModel().getColumn(0).setMaxWidth(0);
	        table_1.getColumnModel().getColumn(2).setPreferredWidth(259);
	        table_1.getColumnModel().getColumn(3).setPreferredWidth(107);
	        table_1.getColumnModel().getColumn(4).setPreferredWidth(60);
	        table_1.getColumnModel().getColumn(4).setMaxWidth(60);
	        table_1.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent e) {
	                clickedTable("确定删除此条数据?", "删除收入数据", table_1);
	            }
	        });
	        TableColumn tcs = table_1.getColumn("操作");
	        tcs.setPreferredWidth(60);
	        tcs.setCellRenderer(renderer);
	        scrollPane_1.setViewportView(table_1);
	        panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	        add(panel_2, BorderLayout.SOUTH);
	        GridBagLayout gbl_panel_2 = new GridBagLayout();
	        gbl_panel_2.columnWidths = new int[]{0, 53, 0, 0, 72, 0, 70, 104, 0};
	        gbl_panel_2.rowHeights = new int[]{0, 0};
	        gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
	        gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
	        panel_2.setLayout(gbl_panel_2);

	        JLabel label_3 = new JLabel("时间：");
	        GridBagConstraints gbc_label_3 = new GridBagConstraints();
	        gbc_label_3.insets = new Insets(0, 0, 0, 5);
	        gbc_label_3.gridx = 0;
	        gbc_label_3.gridy = 0;
	        panel_2.add(label_3, gbc_label_3);

	        JLabel label_4 = new JLabel("<html><u>" + DateHelper.getNowDateTime() + "</u></html>");
	        label_4.setHorizontalAlignment(SwingConstants.LEFT);
	        label_4.setVerticalAlignment(SwingConstants.TOP);
	        label_4.setFont(new Font("宋体", Font.BOLD, 12));
	        GridBagConstraints gbc_label_4 = new GridBagConstraints();
	        gbc_label_4.anchor = GridBagConstraints.WEST;
	        gbc_label_4.insets = new Insets(0, 0, 0, 5);
	        gbc_label_4.gridx = 1;
	        gbc_label_4.gridy = 0;
	        panel_2.add(label_4, gbc_label_4);

	        JLabel label_5 = new JLabel("支出总金额：");
	        GridBagConstraints gbc_label_5 = new GridBagConstraints();
	        gbc_label_5.insets = new Insets(0, 0, 0, 5);
	        gbc_label_5.gridx = 3;
	        gbc_label_5.gridy = 0;
	        panel_2.add(label_5, gbc_label_5);

	        label_6 = new JLabel("<html><u>0</u></html>");
	        label_6.setFont(new Font("宋体", Font.BOLD, 12));
	        GridBagConstraints gbc_label_6 = new GridBagConstraints();
	        gbc_label_6.insets = new Insets(0, 0, 0, 5);
	        gbc_label_6.gridx = 4;
	        gbc_label_6.gridy = 0;
	        panel_2.add(label_6, gbc_label_6);

	        JLabel label = new JLabel("收入总金额");
	        GridBagConstraints gbc_label1 = new GridBagConstraints();
	        gbc_label.insets = new Insets(0, 0, 0, 5);
	        gbc_label.gridx = 5;
	        gbc_label.gridy = 0;
	        panel_2.add(label, gbc_label1);

	        label_9 = new JLabel("0");
	        GridBagConstraints gbc_label_9 = new GridBagConstraints();
	        gbc_label_9.insets = new Insets(0, 0, 0, 5);
	        gbc_label_9.gridx = 6;
	        gbc_label_9.gridy = 0;
	        panel_2.add(label_9, gbc_label_9);

        JLabel label_8 = new JLabel("");
	        label_8.setFont(new Font("宋体", Font.BOLD, 12));
	        label_8.setForeground(Color.RED);
	        GridBagConstraints gbc_label11 = new GridBagConstraints();
	        gbc_label1.anchor = GridBagConstraints.WEST;
	        gbc_label1.gridx = 7;
	        gbc_label1.gridy = 0;
	        panel_2.add(label_8, gbc_label11);
	        initTadayFow();
	        DefaultComboBoxModel comboBoxModel = (DefaultComboBoxModel) comboBox.getModel();
	        if (comboBoxModel.getSize() > 0) {
	            comboBoxModel.removeAllElements();
	        }
	        comboBox.setModel(new DefaultComboBoxModel(SelectType.getOptions(Constant.TYPE_ZC)));
	        if (comboBox.getItemCount() > 0) {
	            comboBox.setSelectedIndex(0);
	        }

	        JComboBox comboBox1 = new JComboBox();
	        comboBox1.setModel(new DefaultComboBoxModel(SelectType.getOptions(Constant.TYPE_ZC)));
	        table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboBox1));
		
	}
    private void submmit() {
        DailyExpenses dailyExpenses = getFormdata();
        if (dailyExpenses != null) {
        	DailyExpensesDAOImpl dailyExpensesMoudle =DAOContentFactriy.getDailyExpensesDAO();
            dailyExpensesMoudle.add(dailyExpenses);
            Object[] rowData = new Object[]{
                    dailyExpenses.getId(),
                    dailyExpenses.getDate(),
                    dailyExpenses.getType(),
                    dailyExpenses.getPay(),
                    "删除"
            };

            if (zhichu.isSelected()) {
                JComboBox comboBox1 = new JComboBox();
                comboBox1.setModel(new DefaultComboBoxModel(SelectType.getOptions(Constant.TYPE_ZC)));
                table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboBox1));
                DefaultTableCellRenderer renderer1 =
                        new DefaultTableCellRenderer();
                renderer1.setToolTipText("点击选择下列表");
                table.getColumnModel().getColumn(2).setCellRenderer(renderer1);
                DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                tableModel.insertRow(0, rowData);
            } else if (shouru.isSelected()) {
                DefaultTableModel tableModel = (DefaultTableModel) table_1.getModel();
                tableModel.insertRow(0, rowData);
            }
            deltail();
        }
    }

    public DailyExpenses getFormdata() {
        String date = datePicker.getEditor().getText();
        String type = comboBox.getEditor().getItem().toString();
        String je = textField.getText();
        if (!zhichu.isSelected() && !shouru.isSelected()) {
            label_7.setText("请先选择支出或收入类型");
            return null;
        }
        DailyExpenses dailyExpenses = null;
        if (zhichu.isSelected()) {
            boolean isCheck = check("支出");
            if (!isCheck) {
                return null;
            }
            dailyExpenses = new DailyExpenses();
            dailyExpenses.setMode(Constant.EXPENSES);
        } else if (shouru.isSelected()) {
            boolean isCheck = check("收入");
            if (!isCheck) {
                return null;
            }
            dailyExpenses = new DailyExpenses();
            dailyExpenses.setMode(Constant.INCOME);
        }

        label_7.setText("");
        dailyExpenses.setId(UUID.randomUUID().toString().replace("-", ""));
        dailyExpenses.setDate(date);
        dailyExpenses.setPay(new BigDecimal(je));
        dailyExpenses.setRecorddate(DateHelper.getNowDateTime());
        dailyExpenses.setType(type);
        return dailyExpenses;
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
                        DefaultTableModel tableModel = (DefaultTableModel) tableObj.getModel();
                        String catno = String.valueOf(tableModel.getValueAt(nRow, 0));
                        DailyExpensesDAOImpl dailyExpensesMoudle = DAOContentFactriy.getDailyExpensesDAO();
                        dailyExpensesMoudle.deleteById(catno);
                        tableModel.removeRow(nRow);
                    case JOptionPane.NO_OPTION:
                    case JOptionPane.CLOSED_OPTION:
                }
            }
        }
    }

    private boolean check(String typename) {

        String date = datePicker.getEditor().getText();
        String type = comboBox.getEditor().getItem().toString();
        String je = textField.getText();
        if (date == null || date.trim().length() == 0) {
            label_7.setText("请输" + typename + "时间");
            return false;
        }
        if (type.trim().length() == 0 && comboBox.getItemCount() == 0) {
            label_7.setText("请设置" + typename + "类型!");
            return false;
        } else {
            if (type == null || type.trim().length() == 0) {
                label_7.setText("请设置" + typename + "类型!");
                return false;
            } else {
                if (zhichu.isSelected()) {
                    boolean is = SelectType.isequals(type, Constant.TYPE_ZC);
                    if (!is) {
                		OptionDAOImpl optionMoudle = DAOContentFactriy.getOptionDAO();
                        Option option = new Option();
                        option.setId(String.valueOf(System.currentTimeMillis()));
                        option.setText(type);
                        option.setType(Constant.TYPE_ZC);
                        boolean isd = optionMoudle.addOption(option);
                        if (isd) {
                            DefaultComboBoxModel comboBoxModel = (DefaultComboBoxModel) comboBox.getModel();
                            comboBoxModel.insertElementAt(type, 0);
                        }
                    }
                }
                if (shouru.isSelected()) {
                    boolean is = SelectType.isequals(type, Constant.TYPE_SR);
                    if (!is) {
                		OptionDAOImpl optionMoudle = DAOContentFactriy.getOptionDAO();
                        Option option = new Option();
                        option.setId(String.valueOf(System.currentTimeMillis()));
                        option.setText(type);
                        option.setType(Constant.TYPE_SR);
                        boolean isd = optionMoudle.addOption(option);
                        if (isd) {
                            DefaultComboBoxModel comboBoxModel = (DefaultComboBoxModel) comboBox.getModel();
                            comboBoxModel.insertElementAt(type, 0);
                        }
                    }
                }
            }
        }

        if (je == null || je.trim().length() == 0) {
            label_7.setText("请输入" + typename + "金额");
            return false;
        }

        double amounts =  Double.parseDouble(je);
        if (amounts == 0) {
            label_7.setText("输入" + typename + "金额大于零！");
            textField.setText("");
            textField.requestFocus();
            return false;
        }

        String nows = DateHelper.getNowDateTime();
        if (date == null || date.trim().length() == 0) {
            date = DateHelper.getNowDateTime();
        }
        long now = Long.parseLong(nows.replaceAll("-", ""));
        long e = Long.parseLong(date.replaceAll("-", ""));

        if (e > now) {
            label_7.setText("输入时间错误！只能录入今天以前的" + typename + "金额");
            return false;
        }
        return true;
    }

    public void deltail() {
        BigDecimal totalcost = new BigDecimal(0);
        int rows = table.getModel().getRowCount();
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        for (int i = 0; i < rows; i++) {
            //总金额
            Object sell = tableModel.getValueAt(i, 3);
            BigDecimal pay = sell instanceof Double ? new BigDecimal((Double) sell) : (BigDecimal) sell;
            totalcost = totalcost.add(pay);
        }
        label_6.setText(totalcost.toString());
        BigDecimal totalcost_1 = new BigDecimal(0);
        int rowss = table_1.getModel().getRowCount();
        DefaultTableModel tableMode_2 = (DefaultTableModel) table_1.getModel();
        for (int i = 0; i < rowss; i++) {
            //总金额
            Object sell = tableMode_2.getValueAt(i, 3);
            BigDecimal pay = sell instanceof Double ? new BigDecimal((Double) sell) : (BigDecimal) sell;
            totalcost_1 = totalcost_1.add(pay);
        }
        label_9.setText(totalcost_1.toString());
    }

    private void initTadayFow() {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        DefaultTableModel tableModel_1 = (DefaultTableModel) table_1.getModel();
        if (tableModel.getRowCount() == 0) {
        	  DailyExpensesDAOImpl dailyExpensesMoudle = DAOContentFactriy.getDailyExpensesDAO();
            java.util.List list = dailyExpensesMoudle.getDailyExpensesByTaday();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    DailyExpenses dailyExpenses = (DailyExpenses) list.get(i);
                    Object[] rowData = new Object[]{
                            dailyExpenses.getId(),
                            dailyExpenses.getDate(),
                            dailyExpenses.getType(),
                            dailyExpenses.getPay(),
                            "删除"
                    };
                    if (dailyExpenses.getMode() == null || Constant.EXPENSES.equals(dailyExpenses.getMode()) ||
                            "null".equalsIgnoreCase(dailyExpenses.getMode())) {
                        tableModel.insertRow(0, rowData);
                    } else if (Constant.INCOME.equals(dailyExpenses.getMode())) {
                        tableModel_1.insertRow(0, rowData);
                    }

                }
            }
            deltail();
        }
    }



}
