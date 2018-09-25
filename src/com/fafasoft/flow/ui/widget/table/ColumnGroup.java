package com.fafasoft.flow.ui.widget.table;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.*;

/**
 * ColumnGroup
 * 
 * @version 1.0 10/20/98
 * @author Nobuo Tamemasa
 */

public class ColumnGroup {
	protected TableCellRenderer renderer;
	protected Vector v;
	protected String text;
	protected int margin = 0;

	public ColumnGroup(String text) {
		this(null, text);
	}

	public ColumnGroup(TableCellRenderer renderer, String text) {
		if (renderer == null) {
			this.renderer = new DefaultTableCellRenderer() {
				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					JTableHeader header = table.getTableHeader();
					if (header != null) {
						setForeground(header.getForeground());
						setBackground(header.getBackground());
						setFont(header.getFont());
					}
					setHorizontalAlignment(JLabel.CENTER);
					setText((value == null) ? "" : value.toString());
					final Color color = UIManager.getColor("Table.gridColor");

				    
					setBorder(BorderFactory.createLineBorder(color));
					return this;
				}
			};
		} else {
			this.renderer = renderer;
		}
		this.text = text;
		v = new Vector();
	}

	/**
	 * @param obj
	 *            TableColumn or ColumnGroup
	 */
	public void add(Object obj) {
		if (obj == null) {
			return;
		}
		v.addElement(obj);
	}

	/**
	 * @param c
	 *            TableColumn
	 * @param v
	 *            ColumnGroups
	 */
	public Vector getColumnGroups(TableColumn c, Vector g) {
		g.addElement(this);
		if (v.contains(c))
			return g;
		Enumeration e = v.elements();
		while (e.hasMoreElements()) {
			Object obj = e.nextElement();
			if (obj instanceof ColumnGroup) {
				Vector groups = ((ColumnGroup) obj).getColumnGroups(c,
						(Vector) g.clone());
				if (groups != null)
					return groups;
			}
		}
		return null;
	}

	public TableCellRenderer getHeaderRenderer() {
		return renderer;
	}

	public void setHeaderRenderer(TableCellRenderer renderer) {
		if (renderer != null) {
			this.renderer = renderer;
		}
	}

	public Object getHeaderValue() {
		return text;
	}

	public Dimension getSize(JTable table) {
		Component comp = renderer.getTableCellRendererComponent(table,
				getHeaderValue(), false, false, -1, -1);
		int height = 20;
		int width = 0;
		Enumeration e = v.elements();
		while (e.hasMoreElements()) {
			Object obj = e.nextElement();
			if (obj instanceof TableColumn) {
				TableColumn aColumn = (TableColumn) obj;
				width += aColumn.getWidth();
				width += margin;
			} else {
				width += ((ColumnGroup) obj).getSize(table).width;
			}
		}
		return new Dimension(width, height);
	}

	public void setColumnMargin(int margin) {
		this.margin = margin;
		Enumeration e = v.elements();
		while (e.hasMoreElements()) {
			Object obj = e.nextElement();
			if (obj instanceof ColumnGroup) {
				((ColumnGroup) obj).setColumnMargin(margin);
			}
		}
	}
}
