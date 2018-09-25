package com.fafasoft.flow.ui.widget;

import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.StockDAOImpl;
import com.fafasoft.flow.pojo.Stock;
import com.fafasoft.flow.ui.ActionView;
import com.fafasoft.flow.ui.LimitedDocument;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;

public class SellDialog extends JDialog {
    // 数量
	private JTextField textField;
	// 售价
	private JTextField textField_1;
	private JLabel label_7;
	// 折扣
	private JTextField textField_2 = new JTextField();

	public SellDialog(Component owner, JTable jTablex,final ActionView actionView) {
		setTitle("销售修改");
		setResizable(false);
		setSize(new Dimension(294, 299));
		setLocationRelativeTo(owner);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		int row = jTablex.getSelectedRow();
		JPanel panel = new JPanel();
		Border loweredetched = BorderFactory
				.createEtchedBorder(EtchedBorder.RAISED);
		panel.setBorder(loweredetched);
		panel.setPreferredSize(new Dimension(10, 90));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(null);

		JLabel label = new JLabel("货号");
		label.setFont(new Font("宋体", Font.PLAIN, 16));
		label.setBounds(70, 11, 32, 15);
		panel.add(label);
		final String cartno = String.valueOf(jTablex.getValueAt(row, 1));
        final String stockId = String.valueOf(jTablex.getValueAt(row, 0));
		JLabel label_1 = new JLabel(cartno);
		label_1.setBounds(112, 11, 147, 15);
		panel.add(label_1);

		JLabel label_2 = new JLabel("名称");
		label_2.setFont(new Font("宋体", Font.PLAIN, 15));
		label_2.setBounds(70, 36, 32, 15);
		panel.add(label_2);
		String name = String.valueOf(jTablex.getValueAt(row, 2));
		JLabel label_3 = new JLabel(name);
		
		label_3.setBounds(112, 36, 147, 15);
		panel.add(label_3);
		
		JLabel label_6 = new JLabel("原售价");
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setFont(new Font("宋体", Font.PLAIN, 15));
		label_6.setBounds(34, 62, 68, 18);
		panel.add(label_6);
		
		//
	    StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
		Stock stock = stockMoudle.getStockByID(stockId);
		
		label_7 = new JLabel(String.valueOf(stock.getSellprice()));
		label_7.setBounds(112, 65, 147, 15);
		panel.add(label_7);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);

		JLabel label_4 = new JLabel("数量");
		label_4.setFont(new Font("宋体", Font.PLAIN, 20));
		label_4.setBounds(40, 24, 40, 20);
		panel_1.add(label_4);

		JLabel label_5 = new JLabel("售价");
		label_5.setFont(new Font("宋体", Font.PLAIN, 20));
		label_5.setBounds(40, 57, 40, 29);
		panel_1.add(label_5);
		String shul = String.valueOf(jTablex.getValueAt(row,3));
		textField = new JTextField();
		textField.setBorder(UIManager.getBorder("ComboBox.border"));
        String dubString = "1234567890.";
        textField.setDocument(new LimitedDocument(20, dubString));
		textField.setFont(new Font("宋体", Font.PLAIN, 15));
		textField.setText(shul);
		textField.setBounds(94, 18, 136, 29);
		panel_1.add(textField);
		textField.setColumns(10);
		// 售价
		String shoujia = String.valueOf(jTablex.getValueAt(row, 4));
		textField_1 = new JTextField();
		textField_1.setBorder(UIManager.getBorder("ComboBox.border"));
		textField_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				try {
				   	BigDecimal orgSellPrice = new BigDecimal(label_7.getText());
					BigDecimal nowSellPrice = new BigDecimal(textField_1.getText());
					
					BigDecimal zhekou = nowSellPrice.divide(orgSellPrice,4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("10")).setScale(2, BigDecimal.ROUND_HALF_UP);
					textField_2.setText(String.valueOf(zhekou));
				} catch(NumberFormatException e){
					
				}
			}
		});


		textField_1.setDocument(new LimitedDocument(20, dubString));
		textField_1.setFont(new Font("宋体", Font.PLAIN, 15));
		textField_1.setBounds(94, 57, 136, 29);
		textField_1.setText(shoujia);
		panel_1.add(textField_1);
		textField_1.setColumns(10);

		// 折扣
		String zhekou = String.valueOf(jTablex.getValueAt(row, 5));
		textField_2.setBorder(UIManager.getBorder("ComboBox.border"));
		textField_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				try {
				   	BigDecimal orgSellPrice = new BigDecimal(label_7.getText());
					BigDecimal zhekou = new BigDecimal(textField_2.getText());
					
					BigDecimal sellPrice = orgSellPrice.multiply(zhekou).divide(new BigDecimal("10"), 2, BigDecimal.ROUND_HALF_UP);
					textField_1.setText(String.valueOf(sellPrice));
				} catch(NumberFormatException e){
					
				}
				
			}
		});
		
		textField_2.setDocument(new LimitedDocument(20, dubString));
		textField_2.setFont(new Font("宋体", Font.PLAIN, 15));
		textField_2.setColumns(4);
		textField_2.setBounds(94, 97, 136, 29);
		panel_1.add(textField_2);
		textField_2.setText(zhekou);
		
		JButton button = new JButton("修改");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Object[] arg = new Object[5];
			
				// 修改的数量
				String sltext = textField.getText().trim();
				if (sltext.length() == 0) {
					sltext = "0";
				}
				arg[0]=sltext;
				// 售价
				String sj = textField_1.getText().trim();
				if (sj.length() == 0) {
					sj = "0";
				}
				arg[1]=sj;
				// 折扣
				String zj = textField_2.getText().trim();
				if (zj.length() == 0) {
					zj = "10";
				}
				arg[2]=zj;
				arg[3]=stockId;
				arg[4]=cartno;
				actionView.actionView(arg);
				dispose();
			}
		});
		button.setFont(new Font("新宋体", Font.PLAIN, 12));
		button.setBounds(94, 136, 63, 29);
		panel_1.add(button);

		JButton button_1 = new JButton("放弃");

		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		button_1.setBounds(167, 136, 63, 29);
		panel_1.add(button_1);
		
		JLabel label_8 = new JLabel("折扣");
		label_8.setFont(new Font("宋体", Font.PLAIN, 20));
		label_8.setBounds(40, 97, 40, 29);
		panel_1.add(label_8);
		

	}
}
