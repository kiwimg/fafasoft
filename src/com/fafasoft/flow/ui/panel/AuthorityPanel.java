package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.UserDAOImpl;
import com.fafasoft.flow.dao.impl.UserRightDAOImpl;
import com.fafasoft.flow.pojo.User;
import com.fafasoft.flow.pojo.UserRight;
import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.widget.AuthorityDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: 2010-6-16 Time: 12:15:18
 * To change this template use File | Settings | File Templates.
 */
public class AuthorityPanel extends BaseJPanel implements LazyPageContent {
	private JTable table;
	private JCheckBox xiaohelper;
	private JCheckBox flow;
	private JCheckBox guketh;
	private JCheckBox caigjh;
	private JCheckBox caigth;
	private JCheckBox riczc;
	private JCheckBox gudzc;
	private JCheckBox guctj;
	private JCheckBox zhictj;
	private JCheckBox yingyeetj;
	private JCheckBox xiaoshph;
	private JCheckBox kehzl;
	private JCheckBox xtsz;
	private JCheckBox quanx;
	private JCheckBox leixsz;
	private JCheckBox gyszl;
	private JCheckBox rcsr;
	private JCheckBox kucunchax;
	private JCheckBox kehugmtj;

	public AuthorityPanel() {
		setLayout(new BorderLayout(0, 0));
		// initPanel();
	}

	public void initPanel() {

		final JPanel panel = new JPanel();
		add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "\u7528\u6237\u5217\u8868",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4, BorderLayout.SOUTH);
		panel_4.setPreferredSize(new Dimension(250, 40));
		panel_4.setBorder(new EmptyBorder(1, 1, 1, 1));
		panel_4.setLayout(null);

		JButton button = new JButton("增加");
		button.setIconTextGap(1);
		button.setMargin(new Insets(2, 0, 2, 0));
		button.setIcon(new ImageIcon(AuthorityPanel.class
				.getResource("/com/fafasoft/flow/resource/image/list-add.png")));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AuthorityDialog authorityDialog = new AuthorityDialog(panel
						.getParent(), "增加用户", null, null, null, table);
				authorityDialog.setVisible(true);
			}
		});
		button.setBounds(10, 10, 57, 25);
		panel_4.add(button);

		JButton button_1 = new JButton("修改");
		button_1.setMargin(new Insets(2, 0, 2, 0));
		button_1.setIconTextGap(1);
		button_1.setIcon(new ImageIcon(AuthorityPanel.class
				.getResource("/com/fafasoft/flow/resource/image/edittitle.png")));
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				modifyRow();
			}
		});
		button_1.setBounds(77, 10, 57, 25);
		panel_4.add(button_1);

		JButton button_2 = new JButton("删除");
		button_2.setIconTextGap(1);
		button_2.setMargin(new Insets(2, 0, 2, 0));
		button_2.setIcon(new ImageIcon(AuthorityPanel.class
				.getResource("/com/fafasoft/flow/resource/image/derow.png")));
		button_2.setBounds(146, 10, 57, 25);
		panel_4.add(button_2);
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int srow = table.getSelectedRow();
				if (srow > -1) {
					deleteRow();
				} else {
					JOptionPane
							.showMessageDialog(panel.getParent(),
									"请选择一行需要删除的用户", "删除通知",
									JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		panel_1.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setShowVerticalLines(false);
		table.setPreferredScrollableViewportSize(new Dimension(122, 400));

		table.setModel(new DefaultTableModel(null, new String[] { "id",
				"password", "\u7528\u6237\u540D", "\u5220\u9664" }) {
			Class[] columnTypes = new Class[] { String.class, String.class,
					ImageIcon.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false,
					false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getTableHeader().setVisible(false);
		table.setFocusable(false);
		table.setShowGrid(false);
		table.setRowHeight(35);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(0);
		table.getColumnModel().getColumn(1).setMinWidth(0);
		table.getColumnModel().getColumn(1).setMaxWidth(0);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setMaxWidth(50);
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				int nRow = table.getSelectedRow();
				int nCol = table.getSelectedColumn();

				if (nRow > -1 && nCol > -1) {
					String id = String.valueOf(table.getValueAt(nRow, 0));
					int c = e.getClickCount();
					if (c == 2) {
						modifyRow();
					} else {
						if (nRow > -1 && nCol > -1) {
							if (Constant.ADMIN.equals(id)) {
								String[] rights = Constant.RIGHT;
								seletAll(rights);
							} else {
								UserRightDAOImpl rightMoudle = DAOContentFactriy
										.getUserRightDAO();
								UserRight userRight = rightMoudle.get(id);
								if (userRight != null) {
									seletAll(userRight.getRight().split(","));

								}
							}
						}
					}
				}
			}
		});
		scrollPane.setViewportView(table);
		scrollPane.getColumnHeader().setVisible(false);
		scrollPane.revalidate();
		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(10, 50));
		add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(null);

		JLabel label_1 = new JLabel("用户权限：可以设置不同用户使用系统的操作功能。");
		label_1.setBounds(31, 10, 567, 30);
		panel_2.add(label_1);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "\u64CD\u4F5C\u6743\u9650",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(null);
		JLabel label_2 = new JLabel("销售");
		label_2.setBounds(42, 26, 54, 15);
		panel_3.add(label_2);
		xiaohelper = new JCheckBox("销售帮手");
		xiaohelper.setBounds(42, 47, 103, 23);
		panel_3.add(xiaohelper);

		flow = new JCheckBox("每天流水账");
		flow.setBounds(163, 47, 103, 23);
		panel_3.add(flow);

		guketh = new JCheckBox("顾客退货");
		guketh.setBounds(275, 47, 103, 23);
		panel_3.add(guketh);

		JLabel label_3 = new JLabel("进货");
		label_3.setBounds(42, 101, 54, 15);
		panel_3.add(label_3);

		caigjh = new JCheckBox("采购进货");
		caigjh.setBounds(42, 122, 103, 23);
		panel_3.add(caigjh);

		caigth = new JCheckBox("采购退货");
		caigth.setBounds(163, 122, 103, 23);
		panel_3.add(caigth);

		JLabel label_5 = new JLabel("支出");
		label_5.setBounds(42, 174, 54, 15);
		panel_3.add(label_5);
		riczc = new JCheckBox("日常支出");
		riczc.setBounds(42, 195, 103, 23);
		panel_3.add(riczc);

		gudzc = new JCheckBox("客户往来帐");
		gudzc.setBounds(163, 195, 103, 23);
		panel_3.add(gudzc);

		JLabel label_6 = new JLabel("统计");
		label_6.setBounds(42, 224, 54, 15);
		panel_3.add(label_6);

		guctj = new JCheckBox("库存统计");
		guctj.setBounds(42, 245, 103, 23);
		panel_3.add(guctj);

		zhictj = new JCheckBox("收支统计");
		zhictj.setBounds(163, 245, 103, 23);
		panel_3.add(zhictj);

		yingyeetj = new JCheckBox("营业额统计");
		yingyeetj.setBounds(275, 245, 92, 23);
		panel_3.add(yingyeetj);

		xiaoshph = new JCheckBox("销售排行统计");
		xiaoshph.setBounds(42, 270, 103, 23);
		panel_3.add(xiaoshph);

		kehzl = new JCheckBox("客户资料");
		kehzl.setBounds(42, 72, 103, 23);
		panel_3.add(kehzl);

		JLabel label_9 = new JLabel("设置");
		label_9.setBounds(42, 299, 54, 15);
		panel_3.add(label_9);

		xtsz = new JCheckBox("系统设置");
		xtsz.setBounds(42, 320, 103, 23);
		panel_3.add(xtsz);

		quanx = new JCheckBox("用户权限");
		quanx.setBounds(163, 320, 103, 23);
		panel_3.add(quanx);

		leixsz = new JCheckBox("类型设置");
		leixsz.setBounds(275, 320, 103, 23);
		panel_3.add(leixsz);
		kehugmtj = new JCheckBox("客户购买统计");
		kehugmtj.setBounds(163, 270, 115, 25);
		panel_3.add(kehugmtj);

		rcsr = new JCheckBox("供应商往来帐");
		rcsr.setBounds(275, 195, 103, 23);
		panel_3.add(rcsr);

		gyszl = new JCheckBox("供应商资料");
		gyszl.setBounds(275, 122, 103, 23);
		panel_3.add(gyszl);

		kucunchax = new JCheckBox("库存查询");
		kucunchax.setBounds(42, 147, 103, 23);
		panel_3.add(kucunchax);
		init();

	}

	private void init() {
		clearTABLE();
		UserDAOImpl userMoudle = DAOContentFactriy.getUserDAO();
		List list = userMoudle.getUser(Constant.USER_TYPE_M);
		if (!list.isEmpty()) {
			User t = null;
			for (int i = 0; i < list.size(); i++) {
				User user = (User) list.get(i);
				if (Constant.ADMIN.equals(user.getId())) {
					t = user;
				} else {
					insertRow(user);
				}
			}
			insertRow(t);
			table.setRowSelectionInterval(0, 0);
			String[] rights = Constant.RIGHT;
			seletAll(rights);
			seletAllEnabled(false);
		}
	}

	public void clearTABLE() {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		if (tableModel.getRowCount() > 0) {
			tableModel.setRowCount(0);
		}
	}

	private void modifyRow() {
		int srow = table.getSelectedRow();
		if (srow > -1) {
			String id = String.valueOf(table.getValueAt(srow, 0));
			String pass = String.valueOf(table.getValueAt(srow, 1));
			String name = String.valueOf(table.getValueAt(srow, 3));
			AuthorityDialog authorityDialog = new AuthorityDialog(MainWindows
					.getInstance(), "修改用户", id, name, pass, table);
			authorityDialog.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(MainWindows.getInstance(),
					"请选择一行需要修改的用户", "修改通知", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void insertRow(User user) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		Object[] rewdata;
		if (user.getId().equals(Constant.ADMIN)) {
			rewdata = new Object[] {
					user.getId(),
					user.getPassword(),
					new ImageIcon(
							AuthorityPanel.class
									.getResource("/com/fafasoft/flow/resource/image/nan.png")),
					user.getUsernmae() };
			tableModel.insertRow(0, rewdata);
		} else {
			rewdata = new Object[] {
					user.getId(),
					user.getPassword(),
					new ImageIcon(
							AuthorityPanel.class
									.getResource("/com/fafasoft/flow/resource/image/nan.png")),
					user.getUsernmae(), };
			tableModel.insertRow(table.getRowCount(), rewdata);
		}
	}

	private void seletAll(String[] aa) {
		if (aa != null) {
			xiaohelper.setSelected(aa[0].equals("true"));
			flow.setSelected(aa[1].equals("true"));
			guketh.setSelected(aa[2].equals("true"));
			caigjh.setSelected(aa[3].equals("true"));
			caigth.setSelected(aa[4].equals("true"));
			riczc.setSelected(aa[5].equals("true"));
			gudzc.setSelected(aa[6].equals("true"));
			guctj.setSelected(aa[7].equals("true"));
			zhictj.setSelected(aa[8].equals("true"));
			yingyeetj.setSelected(aa[9].equals("true"));
			xiaoshph.setSelected(aa[10].equals("true"));
			kehzl.setSelected(aa[11].equals("true"));
			xtsz.setSelected(aa[12].equals("true"));
			quanx.setSelected(aa[13].equals("true"));
			leixsz.setSelected(aa[14].equals("true"));

			if (aa.length >= 16) {
				gyszl.setSelected(aa[15].equals("true"));// 供应商资料
			} else {
				gyszl.setSelected(false);
			}
			if (aa.length >= 17) {
				rcsr.setSelected(aa[16].equals("true"));// 供应商往来帐
			} else {
				rcsr.setSelected(false);
			}
			if (aa.length >= 18) {
				kehugmtj.setSelected(aa[17].equals("true"));// 客户购买统计
			} else {
				kehugmtj.setSelected(false);
			}

			if (aa.length >= 19) {
				kucunchax.setSelected(aa[18].equals("true"));// 库存查询
			} else {
				kucunchax.setSelected(false);
			}
		}
	}

	private void deleteRow() {
		int nRow = table.getSelectedRow();
		DefaultTableModel defaultTableModel = (DefaultTableModel) table
				.getModel();
		String id = String.valueOf(defaultTableModel.getValueAt(nRow, 0));
		if (Constant.ADMIN.equals(id)) {
			JOptionPane.showMessageDialog(MainWindows.getInstance(),
					"管理员不能删除，你可以修改名称", "删除通知", JOptionPane.WARNING_MESSAGE);
		} else {
			int response = JOptionPane.showConfirmDialog(MainWindows
					.getInstance(), "确定删除此用户?", "删除用户",
					JOptionPane.YES_NO_OPTION);
			if (response == JOptionPane.YES_OPTION) {
				if (id == Constant.ADMIN) {
					JOptionPane.showMessageDialog(MainWindows.getInstance(),
							"管理员不能删除，你可以修改名称", "删除通知",
							JOptionPane.WARNING_MESSAGE);
				} else {
					UserDAOImpl userMoudle = DAOContentFactriy.getUserDAO();
					userMoudle.deleteUser(id);
					UserRightDAOImpl rightMoudle = DAOContentFactriy
							.getUserRightDAO();
					rightMoudle.deleteById(id);
					defaultTableModel.removeRow(nRow);
				}
			}
		}
	}

	private void seletAllEnabled(boolean isEnabled) {
		xiaohelper.setEnabled(isEnabled);
		flow.setEnabled(isEnabled);
		guketh.setEnabled(isEnabled);
		caigjh.setEnabled(isEnabled);
		caigth.setEnabled(isEnabled);
		riczc.setEnabled(isEnabled);
		gudzc.setEnabled(isEnabled);
		guctj.setEnabled(isEnabled);
		zhictj.setEnabled(isEnabled);
		yingyeetj.setEnabled(isEnabled);
		xiaoshph.setEnabled(isEnabled);
		kehzl.setEnabled(isEnabled);
		xtsz.setEnabled(isEnabled);
		quanx.setEnabled(isEnabled);
		leixsz.setEnabled(isEnabled);
		gyszl.setEnabled(isEnabled);
		rcsr.setEnabled(isEnabled);
		kehugmtj.setEnabled(isEnabled);
		kucunchax.setEnabled(isEnabled);
	}
}
