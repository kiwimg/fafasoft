package com.fafasoft.flow.ui.widget;

import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.OptionDAOImpl;
import com.fafasoft.flow.pojo.Option;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OptionsDialog extends JDialog {
    private JTextField textField;

    public OptionsDialog(String title, Component owner, final String optiontype,
                         final JTree tree, final boolean ismodify) {
        setResizable(false);
        setSize(new Dimension(288, 155));
        setLocationRelativeTo(owner);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        final DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        final Option selectNodeInfo = (Option) selectNode.getUserObject();
        setTitle(title);
        getContentPane().setLayout(null);

        JLabel label = new JLabel("上级类别");
        label.setBounds(12, 26, 54, 15);
        getContentPane().add(label);

        JLabel label_1 = new JLabel("货物类别");
        label_1.setBounds(12, 58, 54, 15);
        getContentPane().add(label_1);

        textField = new JTextField();
        textField.setBounds(76, 55, 174, 21);

        getContentPane().add(textField);
        textField.setColumns(10);

        JLabel label_2 = new JLabel(selectNodeInfo.getText());
        label_2.setBounds(76, 26, 174, 15);
        getContentPane().add(label_2);
        if (ismodify) {
            textField.setText(selectNodeInfo.getText());
            label_2.setText(selectNode.getParent().toString());
        }
        final JButton button = new JButton("保存");
        button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                button.setEnabled(false);
                String v = textField.getText();
                if (v.trim().length() > 0) {
                    int childCount = selectNode.getChildCount();
                    String newTid;
                    int len = selectNode.getPath().length;
                    if (childCount > 0) {
                        DefaultMutableTreeNode childAtNode = (DefaultMutableTreeNode) selectNode
                                .getChildAt(childCount - 1);
                        Option childtr = (Option) childAtNode.getUserObject();
                        String id = childtr.getNodeId();
                        if(id == null || "null".equalsIgnoreCase(id)) {
                        	if(childtr.getParentId().equals("0")){
                        		newTid =String.valueOf(childCount + 1);
                        	}else{
                        		newTid = String.valueOf(Long.parseLong(id) + 1);
                        	}
                        } else {
                            newTid = String.valueOf(Long.parseLong(id) + 1);
                        }
                    
                    } else {
                        if (selectNodeInfo.getNodeId() == null) {
                            newTid = Long.parseLong(selectNodeInfo.getId()) + String.valueOf(len) + "1";
                        } else {
                            newTid = Long.parseLong(selectNodeInfo.getNodeId()) + String.valueOf(len) + "1";
                        }
                    }

                    DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

                    OptionDAOImpl optionMoudle = DAOContentFactriy.getOptionDAO();
                    Option option = new Option();


                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(option);

                    if (ismodify) {
                        option.setId(selectNodeInfo.getId());
                        option.setType(optiontype);
                        option.setText(v);
                        option.setParentId(selectNodeInfo.getParentId());
                        option.setNodeId(newTid);
                        optionMoudle.updateOptionTextValue(option);

                        tree.getModel().valueForPathChanged(tree.getSelectionPath(), v);

                    } else {
                        option.setId(String.valueOf(System.currentTimeMillis()));
                        option.setType(optiontype);
                        option.setText(v);
                        option.setParentId(selectNodeInfo.getNodeId());
                        option.setNodeId(newTid);
                        optionMoudle.addOption(option);
                        model.insertNodeInto(newNode, selectNode, selectNode.getChildCount());
                        TreePath treePath = new TreePath(selectNode);
                    if (!tree.isExpanded(treePath)) {
                        tree.expandPath(treePath);
                    }
                    }



                }
                button.setEnabled(true);
                dispose();
            }
        });
        button.setBounds(76, 90, 71, 23);
        getContentPane().add(button);

        JButton button_1 = new JButton("取消");
        button_1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        button_1.setBounds(180, 90, 71, 23);
        getContentPane().add(button_1);
    }
}
