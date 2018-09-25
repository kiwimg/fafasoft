package com.fafasoft.flow.ui.widget;

import com.fafasoft.flow.action.PageAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Page extends JPanel {
	private JLabel currentPage;
	private JLabel totalPage;
	private PageInfo pageInfo;
	private DefaultComboBoxModel defaultComboBoxModel;
	private PageAction pageAction;
	private JLabel totalrow_la;

	/**
	 * Create the panel.
	 */
	public Page(PageAction pageAction) {
		this.pageAction = pageAction;
		pageInfo = new PageInfo();

		initLayout();
	}

	private void initLayout() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 15, 15, 15, 15, 15, 15, 15, 0,
				52, 139, 0, 17, 0, 0, 0, 19, 0, 0, 0, 29, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 23, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel firstlabel_1 = new JLabel("");
		firstlabel_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				pageAction.pageFirst();
				pageInfo.setCurrentPage(1);
				currentPage.setText("1");

			}
		});
		firstlabel_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		firstlabel_1.setToolTipText("第一页");
		firstlabel_1.setIcon(new ImageIcon(Page.class
				.getResource("/com/fafasoft/flow/resource/image/page_first.gif")));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		add(firstlabel_1, gbc_label);

		JLabel lastlabel = new JLabel("");
		lastlabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int c = pageInfo.getCurrentPage();
				if (c > 1) {
					int prev = (c - 1);
					pageInfo.setCurrentPage(prev);
					currentPage.setText(String.valueOf(prev));
					int offset = (c - 2) * pageInfo.getRowPerPage();
					pageAction.pagePrev(offset);
				}

			}
		});
		lastlabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lastlabel.setToolTipText("上一页");
		lastlabel.setIcon(new ImageIcon(Page.class
				.getResource("/com/fafasoft/flow/resource/image/page_prev.gif")));
		GridBagConstraints lastgbc_label = new GridBagConstraints();
		lastgbc_label.insets = new Insets(0, 0, 0, 5);
		lastgbc_label.gridx = 1;
		lastgbc_label.gridy = 0;
		add(lastlabel, lastgbc_label);

		JLabel nextlabel = new JLabel("");
		nextlabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int c = pageInfo.getCurrentPage();
				int next = (c + 1);
				if (next <= pageInfo.getTotalPage()) {
					pageInfo.setCurrentPage(next);
					currentPage.setText(String.valueOf(next));
					int offset = c * pageInfo.getRowPerPage();
					pageAction.pageNext(offset);
				}

			}
		});
		nextlabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		nextlabel.setToolTipText("下一页");
		nextlabel.setIcon(new ImageIcon(Page.class
				.getResource("/com/fafasoft/flow/resource/image/page_next.gif")));
		GridBagConstraints nextgbc_label = new GridBagConstraints();
		nextgbc_label.insets = new Insets(0, 0, 0, 5);
		nextgbc_label.gridx = 2;
		nextgbc_label.gridy = 0;
		add(nextlabel, nextgbc_label);

		JLabel endlabel = new JLabel("");
		endlabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int totalpage = pageInfo.getTotalPage();
				pageInfo.setCurrentPage(totalpage);
				int offset = (totalpage - 1) * pageInfo.getRowPerPage();
				pageAction.pageLast(offset);

				currentPage.setText(String.valueOf(pageInfo.getTotalPage()));
			}
		});
		endlabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		endlabel.setToolTipText("最后一页");
		endlabel.setIcon(new ImageIcon(Page.class
				.getResource("/com/fafasoft/flow/resource/image/page_last.gif")));
		GridBagConstraints endgbc_label = new GridBagConstraints();
		endgbc_label.insets = new Insets(0, 0, 0, 5);
		endgbc_label.gridx = 3;
		endgbc_label.gridy = 0;
		add(endlabel, endgbc_label);

		JLabel dilabel = new JLabel("第");
		GridBagConstraints digbc_label = new GridBagConstraints();
		digbc_label.anchor = GridBagConstraints.EAST;
		digbc_label.insets = new Insets(0, 0, 0, 5);
		digbc_label.gridx = 5;
		digbc_label.gridy = 0;
		add(dilabel, digbc_label);
		defaultComboBoxModel = new DefaultComboBoxModel();
		JComboBox comboBoxPage = new JComboBox();
		comboBoxPage.setPreferredSize(new Dimension(52, 21));
		comboBoxPage.setFont(new Font("宋体", Font.BOLD, 12));
		comboBoxPage.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				int pager = Integer.parseInt(String.valueOf(arg0.getItem()));
				pageInfo.setCurrentPage(pager);
				currentPage.setText(String.valueOf(pager));
				int offset = (pager - 1) * pageInfo.getRowPerPage();
				pageAction.itemStateChanged(offset);
			}
		});
		comboBoxPage.setModel(defaultComboBoxModel);

		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox.anchor = GridBagConstraints.WEST;
		gbc_comboBox.gridx = 6;
		gbc_comboBox.gridy = 0;
		add(comboBoxPage, gbc_comboBox);

		JLabel pointlabel_1 = new JLabel("页");
		GridBagConstraints pointgbc_label = new GridBagConstraints();
		pointgbc_label.insets = new Insets(0, 0, 0, 5);
		pointgbc_label.gridx = 7;
		pointgbc_label.gridy = 0;
		add(pointlabel_1, pointgbc_label);

		JLabel exportlabel = new JLabel("导出");
		exportlabel.setIconTextGap(1);
		exportlabel.setFont(new Font("宋体", Font.PLAIN, 12));
		exportlabel.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				pageAction.export(e);
			}
		});
		exportlabel.setToolTipText("导出");
		exportlabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		exportlabel.setIcon(new ImageIcon(Page.class
				.getResource("/com/fafasoft/flow/resource/image/save.gif")));
		GridBagConstraints exportgbc_label = new GridBagConstraints();
		exportgbc_label.anchor = GridBagConstraints.WEST;
		exportgbc_label.insets = new Insets(0, 0, 0, 5);
		exportgbc_label.gridx = 8;
		exportgbc_label.gridy = 0;
		gbc_label.gridx = 9;
		gbc_label.gridy = 0;
		add(exportlabel, exportgbc_label);

		JLabel labelc1 = new JLabel("当前第");
		GridBagConstraints gbc_labelc1 = new GridBagConstraints();
		gbc_labelc1.insets = new Insets(0, 0, 0, 5);
		gbc_labelc1.gridx = 10;
		gbc_labelc1.gridy = 0;
		gbc_label.gridx = 10;
		gbc_label.gridy = 0;
		add(labelc1, gbc_labelc1);

		currentPage = new JLabel("0");
		currentPage.setFont(new Font("宋体", Font.BOLD, 12));
		GridBagConstraints currentgbc_label = new GridBagConstraints();
		currentgbc_label.insets = new Insets(0, 0, 0, 5);
		currentgbc_label.gridx = 11;
		currentgbc_label.gridy = 0;
		add(currentPage, currentgbc_label);

		JLabel labelyec = new JLabel("页");
		GridBagConstraints gbc_labelyec = new GridBagConstraints();
		gbc_labelyec.insets = new Insets(0, 0, 0, 5);
		gbc_labelyec.gridx = 12;
		gbc_labelyec.gridy = 0;
		add(labelyec, gbc_labelyec);

		JLabel labelg = new JLabel("共");
		GridBagConstraints gbc_labelg = new GridBagConstraints();
		gbc_labelg.insets = new Insets(0, 0, 0, 5);
		gbc_labelg.gridx = 14;
		gbc_labelg.gridy = 0;
		add(labelg, gbc_labelg);

		totalPage = new JLabel("0");
		totalPage.setFont(new Font("宋体", Font.BOLD, 12));
		GridBagConstraints gbc_labeltotalPage = new GridBagConstraints();
		gbc_labeltotalPage.insets = new Insets(0, 0, 0, 5);
		gbc_labeltotalPage.gridx = 15;
		gbc_labeltotalPage.gridy = 0;
		add(totalPage, gbc_labeltotalPage);

		JLabel label_1ye22 = new JLabel("页");
		GridBagConstraints gbc_label_122 = new GridBagConstraints();
		gbc_label_122.insets = new Insets(0, 0, 0, 5);
		gbc_label_122.gridx = 16;
		gbc_label_122.gridy = 0;
		add(label_1ye22, gbc_label_122);

		JLabel zglabel = new JLabel("总共");
		GridBagConstraints gbc_labelzglabel = new GridBagConstraints();
		gbc_labelzglabel.insets = new Insets(0, 0, 0, 5);
		gbc_labelzglabel.gridx = 18;
		gbc_labelzglabel.gridy = 0;
		add(zglabel, gbc_labelzglabel);

		totalrow_la = new JLabel("0");
		totalrow_la.setFont(new Font("宋体", Font.BOLD, 12));
		GridBagConstraints gbc_labelto = new GridBagConstraints();
		gbc_labelto.insets = new Insets(0, 0, 0, 5);
		gbc_labelto.gridx = 19;
		gbc_labelto.gridy = 0;
		add(totalrow_la, gbc_labelto);

		JLabel labeltiao = new JLabel("条记录");
		GridBagConstraints gbc_labeltiao = new GridBagConstraints();
		gbc_labeltiao.gridx = 20;
		gbc_labeltiao.gridy = 0;
		add(labeltiao, gbc_labeltiao);


	}

	public void setPageInfo(int totalrows) {

		int rowPerPage = pageInfo.getRowPerPage();
		// 总页数：
		int totalpagenum = totalrows / rowPerPage;
		// 如果总的记录数和每页记录数的余数大于零，
		// 那么总的页数为他们的整除结果加一
		if (totalrows % rowPerPage > 0) {
			totalpagenum = totalpagenum + 1;
		}
		pageInfo.setCurrentPage(1);
		pageInfo.setTotalPage(totalpagenum);
		setPageList(totalpagenum);
		currentPage.setText("1");
		totalPage.setText(String.valueOf(totalpagenum));
		totalrow_la.setText(String.valueOf(totalrows));
	}

	private void setPageList(int pagesize) {
		if (defaultComboBoxModel.getSize() > 0) {
			defaultComboBoxModel.removeAllElements();
		}
		if (pagesize > 0) {
			for (int i = 0; i < pagesize; i++) {
				defaultComboBoxModel.insertElementAt(pagesize - i, 0);
			}
			defaultComboBoxModel.setSelectedItem(1);
		}
	}

	public static class PageInfo {
		private int currentPage = 1; // 当前页码数

		private int totalPage = 0; // 总页数

		private int rowPerPage = 20; // 每页的记录数

		private int totalRowCount = 0;// 结果集中的记录行总数

		private Object[][] resultDate;// 存储结果集

		private int restDate;// 记录中最后页的总数

		public int getCurrentPage() {
			return currentPage;
		}

		public void setCurrentPage(int currentPage) {
			this.currentPage = currentPage;

		}

		public int getTotalPage() {
			return totalPage;
		}

		public void setTotalPage(int totalPage) {
			this.totalPage = totalPage;
		}

		public int getRowPerPage() {
			return rowPerPage;
		}

		public void setRowPerPage(int rowPerPage) {
			this.rowPerPage = rowPerPage;
		}

		public int getTotalRowCount() {
			return totalRowCount;
		}

		public void setTotalRowCount(int totalRowCount) {
			this.totalRowCount = totalRowCount;
		}

		public Object[][] getResultDate() {
			return resultDate;
		}

		public void setResultDate(Object[][] resultDate) {
			this.resultDate = resultDate;
		}

		public int getRestDate() {
			return restDate;
		}

		public void setRestDate(int restDate) {
			this.restDate = restDate;
		}

	}
}
