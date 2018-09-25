package com.fafasoft.flow.ui.widget;

import com.fafasoft.flow.pojo.Stock;
import com.fafasoft.flow.ui.ActionView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class StockListDialog  extends JDialog {
	private JTable table;
	public StockListDialog(final Component owner, String tilte,String catno, boolean isshowCost,List list,final String name,final ActionView actionView) {
	    setResizable(false);
        setTitle(tilte);
        setSize(new Dimension(621, 379));
        setLocationRelativeTo(owner);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 25));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(null);
		
		JLabel label = new JLabel("由于货号["+catno+"]库中存在多个货物，请选择一个需要"+name+"的货物");
		label.setForeground(Color.RED);
		label.setBounds(25, 5, 416, 15);
		panel.add(label);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoCreateRowSorter(true);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int column =table.getSelectedColumn();
				int row = table.getSelectedRow();
				int rc = table.getRowCount();
				if(row >-1) {
					for(int i=0 ;i <rc;i++) {
						table.setValueAt(false, i, 1);
					}
					table.setValueAt(true, row, 1);
				}
			}
		});
		table.setRowHeight(25);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(null
			,
			new String[] {
				"\u8D27\u53F7", "\u9009\u62E9", "\u8D27\u53F7", "\u540D\u79F0", "\u7C7B\u578B", "\u9500\u552E\u4EF7", "\u8FDB\u8D27\u4EF7", "\u5269\u4F59\u6570\u91CF", "\u989C\u8272", "\u89C4\u683C"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Boolean.class, String.class, String.class, String.class, Double.class, Double.class, Double.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false, false, false, false, false , false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setMaxWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(137);
		table.getColumnModel().getColumn(3).setPreferredWidth(138);
		if(!isshowCost)  {
			table.getColumnModel().getColumn(6).setResizable(false);
			table.getColumnModel().getColumn(6).setPreferredWidth(0);
			table.getColumnModel().getColumn(6).setMinWidth(0);
			table.getColumnModel().getColumn(6).setMaxWidth(0);
		}
		scrollPane.setViewportView(table);
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(10, 30));
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(null);
		
		JButton button = new JButton("确定");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int rc = table.getRowCount();
				int selr = -1;
				for(int i=0 ;i <rc;i++) {
				   Object isb =table.getValueAt(i, 1);
				   if(Boolean.parseBoolean(String.valueOf(isb))) {
					   selr = i;
					   break;
				   }
				}
				if(selr ==-1) {
					JOptionPane.showMessageDialog(table.getParent(),
						    "请选择一个"+name+"的货物",
						    "没有选择"+name+"的货物",
						    JOptionPane.WARNING_MESSAGE);

				}else {
					 dispose();
			        DefaultTableModel defaultTableModel=(DefaultTableModel)  table.getModel();
			        defaultTableModel.getValueAt(selr, 0);
					Object[] args = new Object[]{ 
			    			table.getValueAt(selr, 0), //"stockid"
			    			table.getValueAt(selr, 2),//"货号"
							table.getValueAt(selr, 3),//"名称"
							table.getValueAt(selr, 4),//"类型"
							table.getValueAt(selr, 5),//"销售价"
							table.getValueAt(selr, 6),//"进货成本价"
							table.getValueAt(selr, 7),//"剩余数量"
							table.getValueAt(selr, 8),//颜色"
							table.getValueAt(selr, 9)//"规格"
			    	};
	        		actionView.actionView(args);
				}
			
			}
		});

		button.setBounds(176, 3, 93, 23);
		panel_1.add(button);
		
		JButton button_1 = new JButton("取消");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				  dispose();
			}
		});

		button_1.setBounds(343, 3, 93, 23);
		panel_1.add(button_1);
		DefaultTableModel defaultTableModel  = (DefaultTableModel)table.getModel();
		for(int i=0;i<list.size();i++) {
			Stock stock =(Stock)list.get(i);
			Object[] rowData = new Object[] { stock.getId(),false, stock.getCatno(), stock.getStockname(), stock.getType(), stock.getSellprice().doubleValue(),
					stock.getCostprice().doubleValue(), stock.getSyamount(), stock.getColor(), stock.getSpecif(),
					};
			defaultTableModel.insertRow(0, rowData);
		}
	}
}
