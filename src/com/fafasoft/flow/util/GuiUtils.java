package com.fafasoft.flow.util;


import com.fafasoft.flow.Constant;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.JdbcConnectionPoolHelper;

import com.fafasoft.flow.dao.impl.CustomDAOImpl;
import com.fafasoft.flow.dao.impl.SuppliersDAOImpl;
import com.fafasoft.flow.pojo.Accounts;
import com.fafasoft.flow.pojo.Custom;
import com.fafasoft.flow.pojo.Suppliers;
import com.fafasoft.flow.ui.widget.JTreeComboBox;
import com.fafasoft.flow.ui.widget.Options;
import com.fafasoft.flow.ui.widget.SelectType;
import com.fafasoft.flow.ui.widget.tip.BalloonTip;
import com.fafasoft.flow.ui.widget.tip.positioners.LeftBelowPositioner;
import com.fafasoft.flow.ui.widget.tip.positioners.RightAbovePositioner;
import com.fafasoft.flow.ui.widget.tip.styles.RoundedBalloonStyle;
import com.fafasoft.flow.ui.widget.tip.utils.TimingUtils;
import com.fafasoft.flow.ui.widget.tip.positioners.BasicBalloonTipPositioner;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;

import java.util.Enumeration;
import java.util.HashMap;

/**
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * @author liyc
 * @version 1.0
 * @since JDK1.5
 */
public class GuiUtils {
	private static HashMap<String, Color> lookAndFeelColorRegister = new HashMap<String, Color>();
	private static RoundedBalloonStyle roundedBalloonStyle = new RoundedBalloonStyle(
			5, 5, Color.WHITE, Color.RED);

	private static LeftBelowPositioner positioner = new LeftBelowPositioner(40,
			15);
	private static RightAbovePositioner positionerRight = new RightAbovePositioner(
			40, 15);
	
	public static BalloonTip createTips(JComponent attachedComponent, JLabel text) {

		return new BalloonTip(attachedComponent, text);
	}
	public static void toolTips(JComponent attachedComponent, String text) {
	
		toolTips(attachedComponent, text, true);
	}

	public static void toolTips(JComponent attachedComponent, String text,
			boolean tFocus) {
		BalloonTip balloonTip = createTips(attachedComponent,new JLabel(text));
		toolTips(balloonTip,null,2000,null,tFocus,false);
	}

	public static void toolLeftBelowTips(JComponent attachedComponent,
			String text) {
		BalloonTip balloonTip = createTips(attachedComponent,new JLabel(text));
		toolTips(balloonTip,null,2000,"left",true,false);
	}
	

	public static void toolTips(BalloonTip balloonTip,
			Color color, int timed, String isleft, boolean tFocus,
			boolean enableOffset) {
		balloonTip.setFont(new Font("宋体", Font.PLAIN, 12));
		balloonTip.setVisible(true);
		balloonTip.setCloseButton(null);
		
		// RoundedBalloonStyle roundedBalloonStyle = new RoundedBalloonStyle(5,
		// 5, Color.WHITE,
		// new Color(103, 103, 103));
		balloonTip.setPadding(5);
		if ("left".equals(isleft)) {
			balloonTip.setPositioner(positioner);
		} else if ("right".equals(isleft)) {
			balloonTip.setPositioner(positionerRight);
		}
        if(tFocus) {
            balloonTip.getAttachedComponent().requestFocus();
        }
		if (color != null) {
			RoundedBalloonStyle roundedBalloonStylec = new RoundedBalloonStyle(
					5, 5, Color.WHITE, color);
			balloonTip.setStyle(roundedBalloonStylec);
		} else {
			balloonTip.setStyle(roundedBalloonStyle);
		}
		if (enableOffset) {
			((BasicBalloonTipPositioner) balloonTip.getPositioner())
					.enableOffsetCorrection(false);
			
		}
		TimingUtils.showTimedBalloon(balloonTip, timed);
	}

	public static void toolTipsForTable(JTable jtable, int row, int column,
			String text, boolean tFocus) {
		TableCellEditor tableCellEditor = jtable.getCellEditor(row, column);

		Object value = null;
		Component component = tableCellEditor.getTableCellEditorComponent(
				jtable, value, true, row, column);
		if (tFocus) {
			jtable.editCellAt(row, column);
		}

		if (component instanceof JTreeComboBox) {
			component.requestFocus();
			toolTips((JTreeComboBox) component, text, true);

		} else if (component instanceof JComponent) {
			component.requestFocus();
			toolTips((JComponent) component, text, true);
		}

	}

	/**
	 * Adds the close action with escape key.
	 * 
	 * @param dialog
	 *            the dialog
	 */
	public static void addCloseActionWithEscapeKey(final JDialog dialog) {
		// Handle escape key to close the dialog
		KeyStroke escape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		Action escapeAction = new AbstractAction() {
			private static final long serialVersionUID = 0L;

			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		};
		dialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(escape, "ESCAPE");
		dialog.getRootPane().getActionMap().put("ESCAPE", escapeAction);
	}

	/**
	 * Adds the close action with escape key.
	 * 
	 * @param frame
	 *            the frame
	 */
	public static void addCloseActionWithEscapeKey(final JFrame frame) {
		// Handle escape key to close the dialog

		KeyStroke escape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		Action escapeAction = new AbstractAction() {
			private static final long serialVersionUID = 0L;

			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		};
		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(escape, "ESCAPE");
		frame.getRootPane().getActionMap().put("ESCAPE", escapeAction);
	}

	/**
	 * Adds the dispose action with escape key.
	 * 
	 * @param dialog
	 *            the dialog
	 */
	public static void addDisposeActionWithEscapeKey(final JDialog dialog) {
		// Handle escape key to close the dialog

		KeyStroke escape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		Action disposeAction = new AbstractAction() {
			private static final long serialVersionUID = 0L;

			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		};
		dialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(escape, "ESCAPE");
		dialog.getRootPane().getActionMap().put("ESCAPE", disposeAction);
	}

	/**
	 * Adds the dispose action with escape key.
	 * 
	 * @param frame
	 *            the frame
	 */
	public static void addDisposeActionWithEscapeKey(final JFrame frame) {
		// Handle escape key to close the dialog

		KeyStroke escape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		Action disposeAction = new AbstractAction() {
			private static final long serialVersionUID = 0L;

			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		};
		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(escape, "ESCAPE");
		frame.getRootPane().getActionMap().put("ESCAPE", disposeAction);
	}

	/**
	 * Collapses all nodes in a tree.
	 * 
	 * @param tree
	 *            the tree
	 */
	public static void collapseTree(JTree tree) {
		for (int i = tree.getRowCount() - 1; i > 0; i--) {
			tree.collapseRow(i);
		}
		tree.setSelectionRow(0);
	}

	/**
	 * Expands all nodes in a tree.
	 * 
	 * @param tree
	 *            A tree
	 */
	public static void expandTree(JTree tree) {
		for (int i = 1; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}
		tree.setSelectionRow(0);
	}

	/**
	 * Returns background color for panels, as set by Look And Feel.
	 * 
	 * @return the background color
	 */
	public static Color getBackgroundColor() {
		return (Color) UIManager.get("Panel.background");
	}

	/**
	 * Returns foreground color for labels, as set by Look And Feel
	 * 
	 * @return the forefround color
	 */
	public static Color getForegroundColor() {
		return (Color) UIManager.get("Label.foreground");
	}

	/** store lookAndFeelColor. */
	public static void putLookAndFeelColor(String colorName, Color c) {
		lookAndFeelColorRegister.put(colorName, c);
	}

	/**
	 * Sets the default font for all Swing components.
	 * 
	 * @param f
	 *            the f
	 */
	public static void setUIFont(FontUIResource f) {
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				UIManager.put(key, f);
			}
		}
	}


	public static BufferedImage getBufferedImageFromImage(Image img,int width,int height) {
		// This line is important, this makes sure that the image is
		// loaded fully
		img = new ImageIcon(img).getImage();

		// Create the BufferedImage object with the width and height of the
		// Image
		BufferedImage bufferedImage = new BufferedImage(width,
				height, BufferedImage.TYPE_INT_RGB);

		// Create the graphics object from the BufferedImage
		Graphics g = bufferedImage.createGraphics();

		// Draw the image on the graphics of the BufferedImage
		g.drawImage(img, 0, 0, null);

		// Dispose the Graphics
		g.dispose();

		// return the BufferedImage
		return bufferedImage;
	}

	public static void browser(String ulr) {
		if (ulr == null || ulr.trim().length() == 0) {
			return;
		}
		Desktop desktop = Desktop.getDesktop();
		URI netSite = null;
		try {
			netSite = new URI(ulr);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			desktop.browse(netSite);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void apageData(java.util.List list, JTable table_1,
			String typename) {
		clear(table_1);
		if (list != null && list.size() > 0) {
			DefaultTableModel tableModel = (DefaultTableModel) table_1
					.getModel();
			for (int i = 0; i < list.size(); i++) {
				Accounts accounts = (Accounts) list.get(i);
	
			
				String categor = "";
				Object[] obs = SelectType.getCustomersAccountTypes(typename);
				for (int m = 0; m < obs.length; m++) {
					Options options = (Options) obs[m];
					if (options != null) {
						if (options.getKey().equals(accounts.getCategories())) {
							categor = options.getText();
							break;
						}
					}
				}
				String name = "";
				if (typename.equals(Constant.CUSTOMERS))  {
					CustomDAOImpl customMoudle = DAOContentFactriy.getCustomDAO();
					Custom custom = customMoudle.getCustomById(accounts
							.getTargetId());
					name = custom.getName();
				}else if(typename.equals(Constant.SUPPLIERS)) {
					SuppliersDAOImpl suppliersDAOImpl = DAOContentFactriy.getSuppliersDAO();
					Suppliers suppliers = suppliersDAOImpl.getSuppliersByNo(accounts
							.getTargetId());
					name = suppliers.getSuppliersName();
				}
				Object[] rowData = new Object[] {
						accounts.getId(),
						accounts.getTargetId(),
						name,
						categor,
						accounts.getAmount(),
						accounts.getStatus().equals("Untreated") ? "未处理"
								: "已处理", accounts.getNote(),accounts.getDate(), "删除" };
				tableModel.insertRow(0, rowData);
			}
		}
	}

	public static void clear(JTable jTableObj) {
		DefaultTableModel tableModel = (DefaultTableModel) jTableObj.getModel();
		if (tableModel.getRowCount() > 0) {
			int rows = tableModel.getRowCount();
			for (int i = 0; i < rows; i++) {
				tableModel.removeRow(0);
			}
		}
	}
	
	public static void columnCenter(JTable jTableObj,String columnnane,int width) {
		 DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	        renderer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        TableColumn tc = jTableObj.getColumn(columnnane);
	        tc.setPreferredWidth(width);
	        tc.setCellRenderer(renderer);
	     
	}

	 public static boolean isupdate() {
	    File file = new File("./update.exe");
		return file.exists();
					
	 }
	 public static void update(String ffjzxtpath) {
		 try {
				Process process = Runtime.getRuntime().exec(ffjzxtpath);
				
			} catch (Throwable t) {
				System.out.print(t.getMessage());
			}
	 }

    public static int autoResizeColWidth(JTable table){
        return autoResizeColWidth(table,0);
    }

    /**
     *
     * 根据header与col中找到最宽preferredSize，并自动设置col宽
     *
     * @param table
     * @param margin
     * @return
     */
    public static int autoResizeColWidth(JTable table,int margin) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);

        int sumWidth=0;

        for (int i = 0; i < table.getColumnCount(); i++) {
            int vColIndex = i;
            DefaultTableColumnModel colModel = (DefaultTableColumnModel) table.getColumnModel();
            TableColumn col = colModel.getColumn(vColIndex);
            int width = 0;

            // Get width of column header
            TableCellRenderer renderer = col.getHeaderRenderer();

            if (renderer == null) {
                renderer = table.getTableHeader().getDefaultRenderer();
            }

            Component comp = renderer.getTableCellRendererComponent(table, col.getHeaderValue(), false, false, 0, 0);

            width = comp.getPreferredSize().width;


            // Get maximum width of column data
            for (int r = 0; r < table.getRowCount(); r++) {
                renderer = table.getCellRenderer(r, vColIndex);
                comp = renderer.getTableCellRendererComponent(table, table.getValueAt(r, vColIndex), false, false,
                        r, vColIndex);
                width = Math.max(width, comp.getPreferredSize().width);
            }

            // Add margin
            width += 2 * margin;

            // Set the width
            col.setPreferredWidth(width);

            sumWidth+=width;
        }

        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(
                SwingConstants.LEFT);

        // table.setAutoCreateRowSorter(true);
        table.getTableHeader().setReorderingAllowed(false);

        //table.getSize().setSize(sumWidth,table.getSize().height);

        return sumWidth;
    }
}
