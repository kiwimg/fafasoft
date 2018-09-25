package com.fafasoft.flow.ui.widget;

import com.fafasoft.flow.ui.ActionView;
import com.fafasoft.flow.ui.LimitedDocument;
import com.fafasoft.flow.util.NumberUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StockPropertiesDialog  extends JDialog {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JLabel label_4;
	private JTextField textField_4;

    public StockPropertiesDialog(Component owner, String tilte,String catno,  final boolean isDanpininput,final ActionView actionView) {
	    setResizable(false);
        setTitle(tilte);
        if(isDanpininput) {
        	 setSize(new Dimension(351, 273));
        }else {
        	 setSize(new Dimension(351, 248));
        }
       
        setLocationRelativeTo(owner);

        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        
        JLabel label = new JLabel("名称");
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        label.setBounds(25, 58, 54, 15);
        getContentPane().add(label);
        
        textField = new JTextField();

        textField.setBounds(89, 55, 195, 25);
        getContentPane().add(textField);
        textField.setColumns(10);
        
        JLabel label_1 = new JLabel("类型");
        label_1.setHorizontalAlignment(SwingConstants.RIGHT);
        label_1.setBounds(25, 91, 54, 15);
        getContentPane().add(label_1);
        
        textField_1 = new JTextField();

        textField_1.setBounds(89, 88, 195, 25);
        getContentPane().add(textField_1);
        textField_1.setColumns(10);
        
        JLabel label_2 = new JLabel("颜色");
        label_2.setHorizontalAlignment(SwingConstants.RIGHT);

        label_2.setBounds(25, isDanpininput?162:162-35, 54, 15);
        getContentPane().add(label_2);
        
        textField_2 = new JTextField();

        textField_2.setBounds(89, isDanpininput?156:156-35, 66, 25);
        getContentPane().add(textField_2);
        textField_2.setColumns(10);
        
        JLabel label_3 = new JLabel("规格");
        label_3.setHorizontalAlignment(SwingConstants.RIGHT);
        label_3.setBounds(177, isDanpininput?162:162-35, 31, 15);
        getContentPane().add(label_3);
        
        textField_3 = new JTextField();

        textField_3.setBounds(218, isDanpininput?156:156-35, 66, 25);
        getContentPane().add(textField_3);
        textField_3.setColumns(10);
        
        label_4  = new JLabel("<html>货物["+catno+"]在库存中不存在，补填一次货物属性，以备下次使用</html>");
        label_4.setForeground(Color.RED);

        label_4.setBounds(24, 10, 312, 38);
        getContentPane().add(label_4);
       if(isDanpininput) {
        JLabel label_5 = new JLabel("成本价");
        label_5.setHorizontalAlignment(SwingConstants.RIGHT);

        label_5.setBounds(25, 126, 54, 15);
        getContentPane().add(label_5);
        
        textField_4 = new JTextField();
        String dubString = "1234567890.";
        textField_4.setDocument(new LimitedDocument(20, dubString));
        textField_4.setText("0.00");

        textField_4.setBounds(89, 121, 195, 25);
        getContentPane().add(textField_4);
        textField_4.setColumns(10);
       }
        
        JButton button = new JButton("确定");
        button.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		if(textField.getText().trim().length() ==0) {
        			label_4.setText("请给这个货物起个名称吧");
        			return;
        		}
        		if(textField_1.getText().trim().length() ==0) {
        			label_4.setText("请给这个货物给个类型吧");
        			return;
        		}
        		  if(isDanpininput) {
        			  double chengbenjia= NumberUtils.dormatDouble(textField_4.getText());
              		if(chengbenjia==0	) {
              			label_4.setText("请给这个货物给成本价吧");
              			return;
              		} 
        		  }
        		
        		Object[] args = new Object[]{
        				textField.getText(),//名称
        				textField_1.getText(),//类型
        				isDanpininput?textField_4.getText():"0",//成本价
        				textField_2.getText(),//颜色
        				textField_3.getText(),//规格
        		};
        		dispose();
        		actionView.actionView(args);
        	}
        });
        button.setBounds(72, isDanpininput?191:191-35, 93, 33);
        getContentPane().add(button);
        
        JButton button_1 = new JButton("取消");
        button_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		  dispose();
        	}
        });
        button_1.setBounds(191, isDanpininput?191:191-35, 93, 33);
        getContentPane().add(button_1);
      
	}
}
