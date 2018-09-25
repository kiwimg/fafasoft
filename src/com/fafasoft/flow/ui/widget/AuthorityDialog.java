package com.fafasoft.flow.ui.widget;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.UserDAOImpl;
import com.fafasoft.flow.dao.impl.UserRightDAOImpl;
import com.fafasoft.flow.pojo.User;
import com.fafasoft.flow.pojo.UserRight;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-6-16
 * Time: 12:15:18
 * To change this template use File | Settings | File Templates.
 */
public class AuthorityDialog extends JDialog {
    private boolean isDefaultPage = false;
    private JTextField textField;
    private JPasswordField passwordField;
    private JCheckBox xiaohelper;
    private JCheckBox flow;
    private JCheckBox guketh;
    private JCheckBox caigjh;
    private JCheckBox caigth;
    private JCheckBox riczc;
    private JCheckBox gudzc;
    private JCheckBox guctj;
    private JCheckBox zhictj;
    private JCheckBox yingyeetj;
    private JCheckBox xiaoshph;
    private JLabel label_8;
    private JCheckBox xtsz;
    private JCheckBox quanx;
    private JCheckBox leixsz;
    private JTable tablex;
    private JCheckBox kehugmtj;
    private JCheckBox kehzl;
    private JCheckBox gyszl;
    private JCheckBox rcsr;
    private JCheckBox kucunchax;
    private String userID;
    public AuthorityDialog(Component owner, String tilte, String id,String name, String ps, JTable table) {
        setResizable(false);
        setTitle(tilte);
        setSize(new Dimension(543, 507));
        setLocationRelativeTo(owner);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.tablex = table;
        getContentPane().setLayout(new BorderLayout(0, 0));
        JPanel panel_4 = new JPanel();
        getContentPane().add(panel_4, BorderLayout.NORTH);
        panel_4.setPreferredSize(new Dimension(250, 100));
        panel_4.setBorder(new TitledBorder(null, "\u7528\u6237\u8BBE\u7F6E", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_4.setLayout(null);
        this.userID= id;
        JLabel label_4 = new JLabel("用户名");
        label_4.setBounds(26, 57, 54, 15);
        panel_4.add(label_4);

        JLabel label = new JLabel("密码");
        label.setBounds(247, 57, 54, 15);
        panel_4.add(label);

        textField = new JTextField();
        if (name != null) {
            textField.setText(name);
        }
        textField.setBounds(72, 57, 142, 21);
        panel_4.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        if (name != null) {
            passwordField.setText(ps);
        }
        passwordField.setBounds(290, 57, 142, 21);
        panel_4.add(passwordField);

        label_8 = new JLabel("");
        label_8.setForeground(Color.RED);
        label_8.setBounds(25, 26, 473, 21);
        panel_4.add(label_8);

        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new TitledBorder(null, "\u64CD\u4F5C\u6743\u9650", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        getContentPane().add(panel_3, BorderLayout.CENTER);
        panel_3.setLayout(null);

        JLabel label_2 = new JLabel("销售");
        label_2.setBounds(42, 26, 54, 15);
        panel_3.add(label_2);

        xiaohelper = new JCheckBox("销售帮手");

        xiaohelper.setBounds(42, 47, 103, 23);
        panel_3.add(xiaohelper);

        flow = new JCheckBox("每天流水账");

        flow.setBounds(163, 47, 103, 23);
        panel_3.add(flow);

        guketh = new JCheckBox("顾客退货");  

        guketh.setBounds(268, 47, 103, 23);
        panel_3.add(guketh);

        JLabel label_3 = new JLabel("进货");

        label_3.setBounds(42, 77, 54, 15);
        panel_3.add(label_3);

        caigjh = new JCheckBox("采购进货");

        caigjh.setBounds(42, 98, 103, 23);
        panel_3.add(caigjh);

        caigth = new JCheckBox("采购退货");
        caigth.setBounds(163, 98, 103, 23);
        panel_3.add(caigth);

        JLabel label_5 = new JLabel("支出");
        label_5.setBounds(42, 123, 54, 15);
        panel_3.add(label_5);
        riczc = new JCheckBox("日常支出");
        riczc.setBounds(42, 145, 103, 23);
        panel_3.add(riczc);

        gudzc = new JCheckBox("客户往来帐");
        gudzc.setBounds(163, 145, 103, 23);
        panel_3.add(gudzc);

        JLabel label_6 = new JLabel("统计");
        label_6.setBounds(42, 174, 54, 15);
        panel_3.add(label_6);

        guctj = new JCheckBox("库存统计");
        guctj.setBounds(42, 195, 103, 23);
        panel_3.add(guctj);

        zhictj = new JCheckBox("收支统计");
        zhictj.setBounds(163, 195, 103, 23);
        panel_3.add(zhictj);

        yingyeetj = new JCheckBox("营业额统计");
        yingyeetj.setBounds(268, 195, 103, 23);
        panel_3.add(yingyeetj);

        xiaoshph = new JCheckBox("销售排行统计");
        xiaoshph.setBounds(384, 195, 103, 23);
        panel_3.add(xiaoshph);

        JLabel label_9 = new JLabel("设置");
        label_9.setBounds(42, 253, 54, 15);
        panel_3.add(label_9);

        xtsz = new JCheckBox("系统设置");
        xtsz.setBounds(42, 274, 103, 23);
        panel_3.add(xtsz);

        quanx = new JCheckBox("用户权限");
        quanx.setBounds(163, 274, 103, 23);
        panel_3.add(quanx);

        leixsz = new JCheckBox("类型设置");
        leixsz.setBounds(268, 274, 103, 23);
        panel_3.add(leixsz);

        JButton button_1 = new JButton("保存");

        button_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                subbmmit();
            }
        });
        button_1.setBounds(150, 318, 93, 33);
        panel_3.add(button_1);

        JButton button_2 = new JButton("取消");

        button_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        button_2.setBounds(285, 318, 93, 33);
        panel_3.add(button_2);
        
        kehugmtj = new JCheckBox("客户购买统计");
        kehugmtj.setBounds(42, 222, 115, 25);
        panel_3.add(kehugmtj);
        
        kehzl = new JCheckBox("客户资料");
        kehzl.setBounds(384, 47, 103, 23);
        panel_3.add(kehzl);
        
        gyszl = new JCheckBox("供应商资料");
        gyszl.setBounds(268, 98, 103, 23);
        panel_3.add(gyszl);
        
        rcsr = new JCheckBox("供应商往来帐");
        rcsr.setBounds(268, 145, 103, 23);
        panel_3.add(rcsr);
        
        kucunchax = new JCheckBox("库存查询");
        kucunchax.setBounds(384, 98, 103, 23);
        panel_3.add(kucunchax);

        if (Constant.ADMIN.equals(id)) {
            seletAll();
            seletAllEnabled(false);
        } else {
            if (id != null) {
            	UserRightDAOImpl rightMoudle =DAOContentFactriy.getUserRightDAO();
                UserRight userRight = rightMoudle.get(id);
                if (userRight != null) {
                    seletAll(userRight.getRight());
                }
            }
        }
    }

    private void seletAll(String right) {
        if (right != null) {
            String[] aa = right.split(",");
            xiaohelper.setSelected(aa[0].equals("true"));
            flow.setSelected(aa[1].equals("true"));
            guketh.setSelected(aa[2].equals("true"));
            caigjh.setSelected(aa[3].equals("true"));
            caigth.setSelected(aa[4].equals("true"));
            riczc.setSelected(aa[5].equals("true"));
            gudzc.setSelected(aa[6].equals("true"));
            guctj.setSelected(aa[7].equals("true"));
            zhictj.setSelected(aa[8].equals("true"));
            yingyeetj.setSelected(aa[9].equals("true"));
            xiaoshph.setSelected(aa[10].equals("true"));
            kehzl.setSelected(aa[11].equals("true"));
            xtsz.setSelected(aa[12].equals("true"));
            quanx.setSelected(aa[13].equals("true"));
            leixsz.setSelected(aa[14].equals("true"));
            if(aa.length >= 16) {
            	gyszl.setSelected(aa[15].equals("true"));//供应商资料
            }
            if(aa.length >= 17) {
            	rcsr.setSelected(aa[16].equals("true"));//日常收入
            }
            if(aa.length >= 18) {
            	kehugmtj.setSelected(aa[17].equals("true"));//客户购买统计
            }
            if(aa.length >= 19) {
            	kehugmtj.setSelected(aa[18].equals("true"));//客户购买统计
            }

        }
    }

    private void seletAll() {
        xiaohelper.setSelected(true);
        flow.setSelected(true);
        guketh.setSelected(true);
        caigjh.setSelected(true);
        caigth.setSelected(true);
        riczc.setSelected(true);
        gudzc.setSelected(true);
        guctj.setSelected(true);
        zhictj.setSelected(true);
        yingyeetj.setSelected(true);
        xiaoshph.setSelected(true);
        kehzl.setSelected(true);
        xtsz.setSelected(true);
        leixsz.setSelected(true);
        quanx.setSelected(true);
        gyszl.setSelected(true);
        rcsr.setSelected(true);
        kehugmtj.setSelected(true);
        kucunchax.setSelected(true);
    }

    private boolean isSelect() {
        boolean iss = false;
        if (xiaohelper.isSelected()
                || flow.isSelected()
                || guketh.isSelected()
                || caigjh.isSelected()
                || caigth.isSelected()
                || riczc.isSelected()
                || gudzc.isSelected()
                || guctj.isSelected()
                || zhictj.isSelected()
                || yingyeetj.isSelected()
                || xiaoshph.isSelected()
                || kehzl.isSelected()
                || xtsz.isSelected()
                || quanx.isSelected()
                || leixsz.isSelected()
                || kehugmtj.isSelected()
                 || gyszl.isSelected()
                  || rcsr.isSelected()
                  ||kucunchax.isSelected()
                ) {
            iss = true;

        }

        return iss;
    }

    private void seletAllEnabled(boolean isEnabled) {
        xiaohelper.setEnabled(isEnabled);
        flow.setEnabled(isEnabled);
        guketh.setEnabled(isEnabled);
        caigjh.setEnabled(isEnabled);
        caigth.setEnabled(isEnabled);
        riczc.setEnabled(isEnabled);
        gudzc.setEnabled(isEnabled);
        guctj.setEnabled(isEnabled);
        zhictj.setEnabled(isEnabled);
        yingyeetj.setEnabled(isEnabled);
        xiaoshph.setEnabled(isEnabled);
        kehzl.setEnabled(isEnabled);
        xtsz.setEnabled(isEnabled);
        quanx.setEnabled(isEnabled);
        leixsz.setEnabled(isEnabled);
        gyszl.setEnabled(isEnabled);
        rcsr.setEnabled(isEnabled);
        kehugmtj.setEnabled(isEnabled);
        kucunchax.setEnabled(isEnabled);
    }

    public void subbmmit() {
        String username = textField.getText();
        if (username.trim().length() == 0) {
            label_8.setText("请输入用户名称");
            return;
        }
        if (!isSelect()) {
            label_8.setText("请设置用户操作权限");
            return;
        }
        String pas = String.valueOf(passwordField.getPassword());
        if(pas.length()>10) {
        	label_8.setText("设置用户密码长度应该小于10位");
            return;
        }
        UserDAOImpl userMoudle =DAOContentFactriy.getUserDAO();
    	UserRightDAOImpl rightMoudle =DAOContentFactriy.getUserRightDAO();
    	   User userold = null;
    	if(this.userID != null) {
    		userold = userMoudle.getUserByid(this.userID);
    	}
   
        UserRight userRight = new UserRight();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.valueOf(xiaohelper.isSelected()));
        stringBuffer.append(",");
        stringBuffer.append(String.valueOf(flow.isSelected()));
        stringBuffer.append(",");
        stringBuffer.append(String.valueOf(guketh.isSelected()));
        stringBuffer.append(",");
        stringBuffer.append(String.valueOf(caigjh.isSelected()));
        stringBuffer.append(",");
        stringBuffer.append(String.valueOf(caigth.isSelected()));
        stringBuffer.append(",");
        stringBuffer.append(String.valueOf(riczc.isSelected()));
        stringBuffer.append(",");
        stringBuffer.append(String.valueOf(gudzc.isSelected()));
        stringBuffer.append(",");
        stringBuffer.append(String.valueOf(guctj.isSelected()));
        stringBuffer.append(",");
        stringBuffer.append(String.valueOf(zhictj.isSelected()));
        stringBuffer.append(",");
        stringBuffer.append(String.valueOf(yingyeetj.isSelected()));
        stringBuffer.append(",");
        stringBuffer.append(String.valueOf(xiaoshph.isSelected()));
        stringBuffer.append(",");
        stringBuffer.append(String.valueOf(kehzl.isSelected()));
        stringBuffer.append(",");
        stringBuffer.append(String.valueOf(xtsz.isSelected()));
        stringBuffer.append(",");
        stringBuffer.append(String.valueOf(quanx.isSelected()));
        stringBuffer.append(",");
        stringBuffer.append(String.valueOf(leixsz.isSelected()));
        stringBuffer.append(",");
        stringBuffer.append(String.valueOf(gyszl.isSelected()));
        stringBuffer.append(",");
        stringBuffer.append(String.valueOf(rcsr.isSelected()));
        stringBuffer.append(",");
        stringBuffer.append(String.valueOf(kehugmtj.isSelected()));
        stringBuffer.append(",");
        stringBuffer.append(String.valueOf(kucunchax.isSelected()));
        
        String dd = String.valueOf(stringBuffer);
     
        if (userold != null) {
            userold.setPassword(pas);
            userold.setUsernmae(username.trim());
            userMoudle.updateUser(userold);
            DefaultTableModel tableModel = (DefaultTableModel) tablex.getModel();

            int r = tablex.getSelectedRow();
            tableModel.removeRow(r);
            insertRow(userold);
            userRight.setUserId(userold.getId());
            userRight.setRight(dd);
            label_8.setText("用户[" + userold.getUsernmae() + "] 更新成功");
        } else {
        	 User usern = userMoudle.getUserByName(username.trim());
        	 if(usern == null) {
        		 String userId = String.valueOf(System.currentTimeMillis());
                 User user = new User();
                 user.setId(userId);
                 user.setUsernmae(username.trim());
                 user.setPassword(pas.trim());
                 user.setType(Constant.USER_TYPE_M);
                 userMoudle.add(user);
                 insertRow(user);
                 userRight.setUserId(userId);
                 userRight.setRight(dd);
                 label_8.setText("用户[" + user.getUsernmae() + "] 添加成功"); 
        	 }else {
        		 label_8.setText("已经存在["+username+"]用户，请重新添加");  
        	 }
        	

        }
        if (!Constant.ADMIN.equals(userID)) {
            UserRight userRights = rightMoudle.get(userID);
            if (userRights != null) {
                rightMoudle.update(userRight);
            } else {
                rightMoudle.add(userRight);
            }
        }
    }

    private void insertRow(User user) {
        DefaultTableModel tableModel = (DefaultTableModel) tablex.getModel();
        Object[] rewdata;
        if (user.getId().equals(Constant.ADMIN)) {
            rewdata = new Object[]{
                    user.getId(),
                    user.getPassword(),
                    new ImageIcon(AuthorityDialog.class.getResource("/com/fafasoft/flow/resource/image/nan.png")),
                    user.getUsernmae(),
            };

            tableModel.insertRow(0, rewdata);
            int r = tablex.getRowCount();
            
            tablex.setRowSelectionInterval( 0 ,  0 );
      
        } else {
            rewdata = new Object[]{
                    user.getId(),
                    user.getPassword(),
                    new ImageIcon(AuthorityDialog.class.getResource("/com/fafasoft/flow/resource/image/nan.png")),
                    user.getUsernmae(),
            };
            int r = tablex.getRowCount();
    
            tableModel.insertRow( r , rewdata);
            //setRowSelectionInterval(0,   0); 

            tablex.setRowSelectionInterval( r ,  r );
     }
        
    }
}