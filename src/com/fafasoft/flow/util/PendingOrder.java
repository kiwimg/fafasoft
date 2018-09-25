package com.fafasoft.flow.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.fafasoft.flow.pojo.RetailBill;

public class PendingOrder {
	private static PendingOrder pendingOrder;
	private  LinkedHashMap  datainkedList;
	private PendingOrder() {
		datainkedList = new LinkedHashMap();
	}
	
	public static PendingOrder getInstance() {
		if (pendingOrder == null) {
			pendingOrder = new PendingOrder();
		}
		return pendingOrder;
	}
	public void add(String key,RetailBill retailBill)  {
		datainkedList.put(key,retailBill);
	}
    public boolean ff(String key) {
    	return datainkedList.containsKey(key);
    }
	public Object remove(String key)  {
		return datainkedList.remove(key);
	}
	public void removeAll()  {
		 datainkedList.clear() ;
	}
	public int  getSize()  {
		return datainkedList.size();
	}
	public Object  get(String key)  {
		return datainkedList.get(key);
	}
	public LinkedHashMap  getList()  {
		return datainkedList;
	}
	
	public JTable getData() {

		LinkedHashMap  datalist= getList();
		JTable table = null;
		if (datalist != null && datalist.size() > 0) {
			table = new JTable();
			table.setRowHeight(20);
			DefaultTableModel mode = new DefaultTableModel(null, new String[] {"id", "序号","挂单时间" }) {
				boolean[] columnEditable = new boolean[] { false, false, false};

				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return columnEditable[columnIndex];
				}

				Class[] columnTypes = new Class[] { String.class, String.class,String.class};

				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}

			};
			table.setModel(mode);
			table.getTableHeader().setReorderingAllowed(false);
		
			table.getColumnModel().getColumn(0).setPreferredWidth(0);
			table.getColumnModel().getColumn(0).setMinWidth(0);
			table.getColumnModel().getColumn(0).setMaxWidth(0);
			table.getColumnModel().getColumn(1).setPreferredWidth(30);
			table.getColumnModel().getColumn(1).setMinWidth(30);
			table.getColumnModel().getColumn(1).setMaxWidth(30);
	
			table.getColumnModel().getColumn(1).setPreferredWidth(100);
			table.getColumnModel().getColumn(1).setMinWidth(100);
			table.getColumnModel().getColumn(1).setMaxWidth(100);
			Set set = datalist.keySet();
			Iterator it =set.iterator();
			int r= 0;
			while(it.hasNext()){
				Object key = it.next();
				RetailBill formdata =(RetailBill)datalist.get(key);
				Object[] rowData = new Object[] {formdata.getId(),String.valueOf(r+1),formdata.getTime()};
				mode.addRow(rowData);
				r = r+1;
			}
	
		}
		return table;
	}
}
