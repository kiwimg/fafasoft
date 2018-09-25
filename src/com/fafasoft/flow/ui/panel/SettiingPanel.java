package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.SysEnv;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.ConfigDAOImpl;
import com.fafasoft.flow.pojo.Config;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.widget.ClearDataDialog;
import com.fafasoft.flow.util.GuiUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettiingPanel extends JPanel implements LazyPageContent {
    private JCheckBox maxcheckBox = null;
    private JCheckBox isViewFlowcheckBox = null;
    private JCheckBox suggestCheckBox;
    private boolean isviewTips = false;
    public SettiingPanel() {
        setLayout(new BorderLayout(0, 0));
        //initPanel();
    }

    public void initPanel() {
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        add(tabbedPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setBorder(null);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{589, 0};
        gbl_panel.rowHeights = new int[]{130, 55, 0};
        gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);
        tabbedPane.addTab("常规设置", null, panel, null);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "\u57FA\u7840\u8BBE\u7F6E", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setMinimumSize(new Dimension(10, 120));
        panel_1.setLayout(null);
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.insets = new Insets(0, 0, 5, 0);
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 0;
        panel.add(panel_1, gbc_panel_1);
        commConfig(panel_1);

        try {
            PrintSetPanel panel_2 = new PrintSetPanel();
            tabbedPane.addTab("打印设置", null, panel_2, null);
    		
    		JPanel panel_4 = new JPanel();
    		tabbedPane.addTab("数据清空", null, panel_4, null);
    		panel_4.setLayout(null);
    		
    		JLabel lblNewLabel = new JLabel("数据清空将删除以下数据");
    		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 16));
    		lblNewLabel.setBounds(40, 40, 222, 15);
    		panel_4.add(lblNewLabel);
    		
    		JLabel lblNewLabel_1 = new JLabel("营业额数据");
    		lblNewLabel_1.setFont(new Font("宋体", Font.BOLD, 14));
    		lblNewLabel_1.setBounds(76, 79, 155, 15);
    		panel_4.add(lblNewLabel_1);
    		
    		JLabel lblNewLabel_2 = new JLabel("库存数据");
    		lblNewLabel_2.setFont(new Font("宋体", Font.BOLD, 14));
    		lblNewLabel_2.setBounds(76, 114, 82, 15);
    		panel_4.add(lblNewLabel_2);
    		
    		JLabel label_2 = new JLabel("客户往来账数据");
    		label_2.setFont(new Font("宋体", Font.BOLD, 14));
    		label_2.setBounds(285, 79, 122, 15);
    		panel_4.add(label_2);
    		
    		JLabel label_3 = new JLabel("供应商往来账数据");
    		label_3.setFont(new Font("宋体", Font.BOLD, 14));
    		label_3.setBounds(285, 114, 122, 15);
    		panel_4.add(label_3);
    		
    		JLabel label_4 = new JLabel("日常收支数据");
    		label_4.setFont(new Font("宋体", Font.BOLD, 14));
    		label_4.setBounds(76, 146, 166, 15);
    		panel_4.add(label_4);
    		
    		JLabel label_5 = new JLabel("数据清空将保留以下数据");
    		label_5.setFont(new Font("宋体", Font.BOLD, 16));
    		label_5.setBounds(40, 201, 202, 15);
    		panel_4.add(label_5);
    		
    		JLabel label_6 = new JLabel("系统用户数据");
    		label_6.setFont(new Font("宋体", Font.BOLD, 14));
    		label_6.setBounds(76, 237, 155, 15);
    		panel_4.add(label_6);
    		
    		JLabel label_7 = new JLabel("客户数据");
    		label_7.setFont(new Font("宋体", Font.BOLD, 14));
    		label_7.setBounds(76, 272, 155, 15);
    		panel_4.add(label_7);
    		
    		JLabel label_8 = new JLabel("供应商数据");
    		label_8.setFont(new Font("宋体", Font.BOLD, 14));
    		label_8.setBounds(252, 237, 155, 15);
    		panel_4.add(label_8);
    		
    		JLabel label_9 = new JLabel("类型(货物类型、收支类型)数据");
    		label_9.setFont(new Font("宋体", Font.BOLD, 14));
    		label_9.setBounds(252, 272, 260, 15);
    		panel_4.add(label_9);
    		
    		JButton btnNewButton = new JButton("清空数据");
    		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 12));
    		btnNewButton.addMouseListener(new MouseAdapter() {
    			@Override
    			public void mouseClicked(MouseEvent arg0) {
    				ClearDataDialog clearDataDialog = new ClearDataDialog(MainWindows.getInstance());
    				clearDataDialog.setVisible(true);
    			}
    		});
    		
    		btnNewButton.setBounds(409, 338, 124, 37);
    		panel_4.add(btnNewButton);

        } catch (Throwable e) {
        }
    }

    private void commConfig(JPanel panel_1) {
        maxcheckBox = new JCheckBox("登陆后自动全屏(窗口最大化)");

        maxcheckBox.setFont(new Font("宋体", Font.PLAIN, 12));
        maxcheckBox.setBounds(46, 15, 216, 23);
        panel_1.add(maxcheckBox);

        isViewFlowcheckBox = new JCheckBox("是否在销售帮手中显示今日流水以及销售人员流水统计");

        isViewFlowcheckBox.setBounds(46, 53, 349, 23);
        isViewFlowcheckBox.setFont(new Font("宋体", Font.PLAIN, 12));
        panel_1.add(isViewFlowcheckBox);

        suggestCheckBox = new JCheckBox("是否使用货号提示功能");


        suggestCheckBox.setFont(new Font("宋体", Font.PLAIN, 12));
        suggestCheckBox.setBounds(46, 91, 299, 23);
        panel_1.add(suggestCheckBox);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initView();
            }
        });
        maxcheckBox.addItemListener(
                new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        // Set "ignore" whenever box is checked or unchecked.
                        boolean ignore = (e.getStateChange() == ItemEvent.SELECTED);
                        SysEnv.getInstance().setFrameMaxPolicy(String.valueOf(ignore));
                        saveConfig(maxcheckBox, Constant.MAIN_IFRAME_MAX, ignore);
                    }
                }
        );
        isViewFlowcheckBox.addItemListener(
                new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        // Set "ignore" whenever box is checked or unchecked.
                        boolean ignore = (e.getStateChange() == ItemEvent.SELECTED);
                        SysEnv.getInstance().setFrameMaxPolicy(String.valueOf(ignore));

                        saveConfig(isViewFlowcheckBox, Constant.VIEW_FLOWINFO_SELHELPER, ignore);

                    }
                }
        );
        suggestCheckBox.addItemListener(
                new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        // Set "ignore" whenever box is checked or unchecked.
                        boolean ignore = (e.getStateChange() == ItemEvent.SELECTED);
                        SysEnv.getInstance().setSuggest(ignore);
                        saveConfig(suggestCheckBox, Constant.VIEW_SET_SUGGEST, ignore);
                    }
                }
        );
    }

    private void initView() {

        ConfigDAOImpl configDAO = DAOContentFactriy.getConfigDAO();
        Config configIFRAME_MAX = configDAO.getConfig(Constant.MAIN_IFRAME_MAX);

        if (configIFRAME_MAX != null) {

            maxcheckBox.setSelected(Boolean.valueOf(configIFRAME_MAX.getValue()));
        } else {
            maxcheckBox.setSelected(false);
        }
        Config configViewSellHelp = configDAO.getConfig(Constant.VIEW_FLOWINFO_SELHELPER);

        if (configViewSellHelp != null) {
            isViewFlowcheckBox.setSelected(Boolean.valueOf(configViewSellHelp.getValue()));
        } else {
            isViewFlowcheckBox.setSelected(false);
        }
        Config suggest = configDAO.getConfig(Constant.VIEW_SET_SUGGEST);
        if (suggest != null) {
            suggestCheckBox.setSelected(Boolean.valueOf(suggest.getValue()));
        } else {
            suggestCheckBox.setSelected(false);
        }
        isviewTips= true;
    }

    private void saveConfig(JComponent attachedComponent, String key, boolean value) {
        Config[] configs = new Config[1];
        Config config2 = new Config();
        config2.setKey(key);
        config2.setValue(String.valueOf(value));
        configs[0] = config2;
        DAOContentFactriy.getConfigDAO().addConfigs(configs);
        if (isviewTips){
            GuiUtils.toolTips(attachedComponent, "设置已保存");
        }
    }
}
