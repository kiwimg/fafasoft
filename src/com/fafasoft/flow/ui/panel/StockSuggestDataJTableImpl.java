package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.FlowLogDAOImpl;
import com.fafasoft.flow.dao.impl.StockDAOImpl;
import com.fafasoft.flow.pojo.Flowlog;
import com.fafasoft.flow.pojo.Stock;
import com.fafasoft.flow.ui.widget.SuggestTextFieldJTable;
import com.fafasoft.flow.ui.widget.table.ImageRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company: 中州软讯
 * </p>
 * User: Administrator
 * 
 * @author liyc
 * @version 1.0
 * @since JDK1.6 Date: 2010-9-27
 */
public class StockSuggestDataJTableImpl implements
		SuggestTextFieldJTable.SuggestDataJTable {
	private boolean is1ontainessThan = false;
	public StockSuggestDataJTableImpl() {
		
	}
    public StockSuggestDataJTableImpl(boolean is1ontainessThan) {
    	this.is1ontainessThan  =is1ontainessThan;
	}
	public JTable getData(String value) {
		StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
		List list = stockMoudle.getStockSuggest(value, is1ontainessThan,20);
		JTable table = null;
		if (list != null && list.size() > 0) {
			table = new JTable();table.setRowHeight(20);
			table.setModel(new DefaultTableModel(null, new String[] { "id",
					"货号", "名称", "剩余数量", "售价", "类型", "规格", "颜色","进货日期" }) {
				boolean[] columnEditable = new boolean[] { false, false, false,
						false, false, false, false, false , false};

				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return columnEditable[columnIndex];
				}

				Class[] columnTypes = new Class[] { String.class, String.class,
						String.class, Double.class, BigDecimal.class,
						String.class, String.class, String.class , String.class };

				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}

			});
			table.getTableHeader().setReorderingAllowed(false);
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(0).setPreferredWidth(0);
			table.getColumnModel().getColumn(0).setMinWidth(0);
			table.getColumnModel().getColumn(0).setMaxWidth(0);
			table.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());
			DefaultTableModel defaultTableModel = (DefaultTableModel) table
					.getModel();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Stock formdata = (Stock) list.get(i);
					// "id", "货号", "名称", "剩余数量","售价", "类型", "规格", "颜色"
					Object[] rowData = new Object[] { formdata.getId(),
							formdata.getCatno(), formdata.getStockname(),
							formdata.getSyamount(), formdata.getSellprice(),
							formdata.getType(), formdata.getSpecif(),
							formdata.getColor(), formdata.getDate()};

					defaultTableModel.insertRow(0, rowData);
				}
			}
		}
		return table;
	}
	public JTable getStockData(String value) {
		 StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
		List list = stockMoudle.getStockByType(value, 20);
		JTable table = null;
		if (list != null && list.size() > 0) {
			table = new JTable();
			table.setRowHeight(20);
			table.setModel(new DefaultTableModel(null, new String[] { "id",
					"货号", "名称", "剩余数量","进货价", "规格", "颜色" }) {
				boolean[] columnEditable = new boolean[] { false, false, false,
						false, false, false, false};

				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return columnEditable[columnIndex];
				}

				Class[] columnTypes = new Class[] { String.class, String.class,
						String.class, Double.class, BigDecimal.class,
						String.class, String.class };

				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}

			});
			table.getTableHeader().setReorderingAllowed(false);
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(0).setPreferredWidth(0);
			table.getColumnModel().getColumn(0).setMinWidth(0);
			table.getColumnModel().getColumn(0).setMaxWidth(0);

			table.getColumnModel().getColumn(1).setPreferredWidth(105);
			table.getColumnModel().getColumn(1).setMaxWidth(200);
			table.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());
		
			table.getColumnModel().getColumn(2).setPreferredWidth(105);
			table.getColumnModel().getColumn(2).setMaxWidth(200);

			table.getColumnModel().getColumn(3).setPreferredWidth(100);
			table.getColumnModel().getColumn(3).setMaxWidth(100);
			table.getColumnModel().getColumn(4).setPreferredWidth(100);
			table.getColumnModel().getColumn(4).setMaxWidth(200);
			table.getColumnModel().getColumn(5).setPreferredWidth(100);
			table.getColumnModel().getColumn(5).setMaxWidth(200);
			DefaultTableModel defaultTableModel = (DefaultTableModel) table
					.getModel();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Stock formdata = (Stock) list.get(i);
					// "id", "货号", "名称", "剩余数量","售价", "类型", "规格", "颜色"
					Object[] rowData = new Object[] { formdata.getId(),
							formdata.getCatno(), formdata.getStockname(),
							formdata.getSyamount(), formdata.getCostprice(),
							formdata.getSpecif(),
							formdata.getColor() };

					defaultTableModel.insertRow(0, rowData);
				}
			}
		}
		return table;
	}
	public JTable getData(String value, String type) {
		 StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
		List list = stockMoudle.getStockSuggest(value, type, 20);
		JTable table = null;
		if (list != null && list.size() > 0) {
			table = new JTable();table.setRowHeight(20);
			table.setModel(new DefaultTableModel(null, new String[] { "id","货号",
					"名称", "类型" ,"颜色","规格"}) {
				boolean[] columnEditable = new boolean[] { false, false, false, false , false, false};

				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return columnEditable[columnIndex];
				}

				Class[] columnTypes = new Class[] { String.class, String.class, String.class,
						String.class, String.class, String.class };

				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}

			});
			table.getTableHeader().setReorderingAllowed(false);
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(0).setPreferredWidth(0);
			table.getColumnModel().getColumn(0).setMinWidth(0);
			table.getColumnModel().getColumn(0).setMaxWidth(0);
			table.getColumnModel().getColumn(1).setCellRenderer(new ImageRenderer());
			DefaultTableModel defaultTableModel = (DefaultTableModel) table
					.getModel();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Stock formdata = (Stock) list.get(i);
					// "id", "货号", "名称", "剩余数量","售价", "类型", "规格", "颜色"
					Object[] rowData = new Object[] {formdata.getId(), formdata.getCatno(),
							formdata.getStockname(), type, formdata.getColor(),formdata.getSpecif()};

					defaultTableModel.insertRow(0, rowData);
				}
			}
			
		}
		return table;
	}

	public JTable getFlowData(String value, String type) {
		FlowLogDAOImpl flowLogMoudle = DAOContentFactriy.getFlowLogDAO();
		StockDAOImpl stockDAO = DAOContentFactriy.getStockDAO();
		List list = flowLogMoudle.getFlowSuggest(value, type, 25);
		//System.out.println("list==="+list.size());
		JTable table = null;
		if (list != null && list.size() > 0) {
			table = new JTable();
			table.setRowHeight(20);
			table.setModel(new DefaultTableModel(null, new String[] { "ID",
					"stockId", "货号", "类型","规格" ,"颜色","售价","销售日期" }) {
				boolean[] columnEditable = new boolean[] { false, false, false,
						false, false , false};

				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return columnEditable[columnIndex];
				}

				Class[] columnTypes = new Class[] { String.class, String.class,
						String.class, String.class,String.class, String.class,BigDecimal.class, String.class };

				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}

			});
			table.getTableHeader().setReorderingAllowed(false);
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(0).setPreferredWidth(0);
			table.getColumnModel().getColumn(0).setMinWidth(0);
			table.getColumnModel().getColumn(0).setMaxWidth(0);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(1).setPreferredWidth(0);
			table.getColumnModel().getColumn(1).setMinWidth(0);
			table.getColumnModel().getColumn(1).setMaxWidth(0);
			table.getColumnModel().getColumn(2).setPreferredWidth(105);
			table.getColumnModel().getColumn(2).setMaxWidth(200);
			table.getColumnModel().getColumn(2).setCellRenderer(new ImageRenderer());
			table.getColumnModel().getColumn(3).setPreferredWidth(105);
			table.getColumnModel().getColumn(3).setMaxWidth(200);

			table.getColumnModel().getColumn(4).setPreferredWidth(65);
			table.getColumnModel().getColumn(4).setMaxWidth(200);
			table.getColumnModel().getColumn(5).setPreferredWidth(65);
			table.getColumnModel().getColumn(5).setMaxWidth(200);
			table.getColumnModel().getColumn(6).setPreferredWidth(65);
			table.getColumnModel().getColumn(6).setMaxWidth(200);
			DefaultTableModel defaultTableModel = (DefaultTableModel) table
					.getModel();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Flowlog formdata = (Flowlog) list.get(i);
					Stock stock = stockDAO.getStockByID(formdata.getStockId());
					if(stock == null) {
						stock = new Stock();
					}
					// "id", "货号", "名称", "剩余数量","售价", "类型", "规格", "颜色"
					Object[] rowData = new Object[] {formdata.getFlowno(),formdata.getStockId(),
							formdata.getCatno(), formdata.getStockname(), type,stock.getSpecif(),stock.getColor(),
							formdata.getDate() };

					defaultTableModel.insertRow(0, rowData);
				}
			}
		}
		return table;
	}
}
