package com.fafasoft.flow.ui.widget;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.pojo.Option;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;

public class StockTypeDialog extends JDialog {
	public StockTypeDialog(Component owner,final CallBack callBack) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(StockTypeDialog.class.getResource("/com/fafasoft/flow/resource/image/yygl.png")));
		setTitle("设置货物类型");
		
		setResizable(false);
		 setLocationRelativeTo(owner);
        setSize(new Dimension(272, 323));
        setLocationRelativeTo(owner);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        Option rootOption = new Option();
		rootOption.setText("货物类别");
		rootOption.setNodeId("0");
		rootOption.setParentId("0");
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(rootOption);

		final OptionsTree tree = new OptionsTree(top,Constant.TYPE_HW,true);
	      
        JScrollPane scrollPane = new JScrollPane(tree);
        tree.setAutoscrolls(true);
        tree.setScrollsOnExpand(true);
        tree.setShowsRootHandles(true);
        tree.setRootVisible(true);
        tree.setFont(new Font("宋体", Font.PLAIN, 12));
	
        scrollPane.setPreferredSize(new Dimension(150, 64));
        
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 20));
		scrollPane.setColumnHeaderView(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("提示:用鼠标选中后点击鼠标右键进行相应操作");
		label.setBounds(10, 0, 258, 17);
		panel.add(label);
		addWindowListener(new   WindowAdapter()   { 
			public   void   windowClosing(WindowEvent   e)   { 
				callBack.updateView();
			} 
		}); 

		
	}
	
	  public interface CallBack {
	        public void updateView();
	    }
}
