package com.fafasoft.flow.ui.widget;

import com.fafasoft.flow.ui.ActionView;
import com.fafasoft.flow.ui.LimitedDocument;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;

public class CashDialog extends JDialog {
    private JFormattedTextField textField;
    private JFormattedTextField textField_1;
    private JLabel textField_2;
    private ActionView callBack;

    public CashDialog(Component owner, String ys, String ss, ActionView callBackx) {
        setResizable(false);

        setSize(new Dimension(325, 253));
        setLocationRelativeTo(owner);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        setTitle("结算");
        getContentPane().setLayout(null);
        this.callBack = callBackx;
        JLabel label = new JLabel("应收");
        label.setFont(new Font("宋体", Font.PLAIN, 25));
        label.setBounds(33, 28, 54, 28);
        getContentPane().add(label);

        JLabel label_1 = new JLabel("实收");
        label_1.setFont(new Font("宋体", Font.PLAIN, 25));
        label_1.setBounds(33, 79, 54, 31);
        getContentPane().add(label_1);

        JLabel label_2 = new JLabel("找零");
        label_2.setFont(new Font("宋体", Font.PLAIN, 25));
        label_2.setBounds(33, 131, 54, 28);
        getContentPane().add(label_2);

        NumberFormat amountFormat = NumberFormat.getNumberInstance();
        amountFormat.setMaximumFractionDigits(2); 
        textField = new JFormattedTextField(amountFormat);
        String dubString = "1234567890.";
        textField.setDocument(new LimitedDocument(20, dubString));
        textField.setFocusLostBehavior(JFormattedTextField.COMMIT);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
            	if(textField.getText().trim().length() ==0) {
            		textField.setText("0");
            	}
                change();
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submmit();
                }
            }
        });
        textField.setText(ys);
        //textField.setBorder(UIManager.getBorder("ComboBox.border"));
        textField.setFont(new Font("宋体", Font.BOLD, 25));
        textField.setBorder(new EmptyBorder(0, 0, 1, 0));
        textField.setBounds(97, 26, 183, 31);
        getContentPane().add(textField);
        textField.setColumns(10);
        
        textField_1 = new JFormattedTextField(amountFormat);
        textField.setFocusLostBehavior(JFormattedTextField.COMMIT);
        textField_1.setBorder(new EmptyBorder(0, 0, 1, 0));
       // textField_1.setDocument(new LimitedDocument(20, dubString));
        textField_1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
            	if(textField_1.getText().trim().length() ==0) {
            		textField_1.setText("0");
            	}
                change();
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submmit();
                }
            }
        });
        //  textField_1.setBorder(UIManager.getBorder("ComboBox.border"));
        textField_1.setFont(new Font("宋体", Font.BOLD, 25));
        textField_1.setBounds(97, 78, 183, 31);
        getContentPane().add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JLabel();
        textField_2.setBorder(null);
        NumberFormat currency = NumberFormat.getCurrencyInstance(); 
        
        textField_2.setText(currency.format(0));
        textField_2.setForeground(Color.RED);
   
        textField_2.setFont(new Font("宋体", Font.BOLD, 25));
        textField_2.setBounds(97, 129, 183, 31);
   
        
        getContentPane().add(textField_2);
 

        final JToggleButton button = new JToggleButton("确定");
        button.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        button.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submmit();
                }
            }
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                submmit();
            }
        });

        button.setBounds(72, 179, 93, 31);
        getContentPane().add(button);

        JToggleButton button_1 = new JToggleButton("取消");
        button_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        button_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        button_1.setBounds(187, 179, 93, 31);
        getContentPane().add(button_1);
        addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                textField_1.requestFocus();
                textField_1.selectAll();
            }
        });
        textField_1.setText(ss);
    }

    private void submmit() {
        dispose();
    	try {
			textField.commitEdit();
			textField_1.commitEdit();
	        String ystext = String.valueOf(textField.getValue());
	        String shishoutext = String.valueOf(textField_1.getValue());
	        BigDecimal ys = new BigDecimal(ystext).setScale(2, BigDecimal.ROUND_HALF_UP);
	        BigDecimal shishou = new BigDecimal(shishoutext).setScale(2, BigDecimal.ROUND_HALF_UP);
	        callBack.actionView(new Object[]{ystext,shishoutext,String.valueOf(shishou.subtract(ys))});
	        
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }
    private void change() {
    	try {
			textField.commitEdit();
			textField_1.commitEdit();
	        String ystext = String.valueOf(textField.getValue());
	        String shishoutext = String.valueOf(textField_1.getValue());
	        BigDecimal ys = new BigDecimal(ystext).setScale(2, BigDecimal.ROUND_HALF_UP);
	        BigDecimal shishou = new BigDecimal(shishoutext).setScale(2, BigDecimal.ROUND_HALF_UP);
	        NumberFormat currency = NumberFormat.getCurrencyInstance(); 
	        
	        textField_2.setText(currency.format(shishou.subtract(ys)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
