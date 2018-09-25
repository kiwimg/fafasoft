package com.fafasoft.flow.ui.widget;

import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.OptionDAOImpl;
import com.fafasoft.flow.pojo.Option;
import com.fafasoft.flow.ui.panel.OptionsTypePanel;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Enumeration;

public class OptionsTree extends JTree {
    private boolean isall = false;
    private boolean ismodify = true;
	public OptionsTree(DefaultMutableTreeNode top, final String optionType) {
		this(top,optionType,false,true);
	}
	public OptionsTree(DefaultMutableTreeNode top, final String optionType,
			boolean isall,boolean ismodify) {
		super(top);
		setBorder(null);
		this.isall =isall;
		this.ismodify =ismodify;
		init(top,optionType);
	}
	
	public OptionsTree(DefaultMutableTreeNode top, final String optionType,
			boolean isall) {
		this(top,optionType,isall,true);
	}
	
    private void init(DefaultMutableTreeNode top,final String optionType) {

		createNodes(top, "0", optionType);
		setRowHeight(22);
		DefaultTreeCellRenderer r = new DefaultTreeCellRenderer();
	
		r.setBackgroundNonSelectionColor(null);

		setAutoscrolls(true);
		setCellRenderer(r);
		getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		final ImageIcon openIco = new ImageIcon(OptionsTypePanel.class
				.getResource("/com/fafasoft/flow/resource/image/pers.png"));
		final ImageIcon closeIco = new ImageIcon(OptionsTypePanel.class
				.getResource("/com/fafasoft/flow/resource/image/group.png"));
		r.setOpenIcon(openIco);
		r.setClosedIcon(closeIco);
		r.setLeafIcon(closeIco);
		if(ismodify) {
			setComponentPopupMenu(new TreePopupMenu(optionType));
		}

		TreeNode root = (TreeNode) getModel().getRoot();
		expandPath(new TreePath(root));
    }
	

	private void expandAll(JTree tree, TreePath parent, boolean expand) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();

		if (node.getChildCount() > 0) {
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path, expand);
			}
		}
		if (expand) {
			tree.expandPath(parent);
		} else {
			tree.collapsePath(parent);
		}
	}

	public void createNodes(DefaultMutableTreeNode node, String nodeId,
			String optype) {
		   OptionDAOImpl optionMoudle = DAOContentFactriy.getOptionDAO();
		java.util.List list = optionMoudle.getOption(optype, nodeId);

		for (int i = 0; i < list.size(); i++) {
			Option op = (Option) list.get(i);
			if (op.getParentId().equals(nodeId)) {
				DefaultMutableTreeNode category = new DefaultMutableTreeNode(op);
				node.add(category);
				if(this.isall ) {
					createNodes(category, op.getNodeId(), optype);
				}
			}
		}
	}
	class TreePopupMenu extends JPopupMenu {
		private TreePath path;
		private JMenuItem addItem;
		private JMenuItem modifyItem;
		private JMenuItem deleItem;
		private String type;

		public TreePopupMenu(final String type) {
			super();
			addItem = new JMenuItem(new AbstractAction(" 增加类别  ") {
				public void actionPerformed(ActionEvent e) {
					JTree tree = (JTree) getInvoker();
					if (path != null)
						tree.startEditingAtPath(path);
					OptionsDialog optionsDialog = new OptionsDialog("增加类别",
							tree.getParent().getParent().getParent(), type,
							tree,false);
					optionsDialog.setVisible(true);
				}
			});
			add(addItem);
			modifyItem = new JMenuItem(new AbstractAction(" 修改类别   ") {
				public void actionPerformed(ActionEvent e) {
					JTree tree = (JTree) getInvoker();
					if (path != null)
						tree.startEditingAtPath(path);
					OptionsDialog optionsDialog = new OptionsDialog("修改类别",
							tree.getParent().getParent().getParent(), type, tree,true);
					optionsDialog.setVisible(true);
				}
			});
			add(modifyItem);
			deleItem = new JMenuItem(new AbstractAction(" 删除类别    ") {
				public void actionPerformed(ActionEvent e) {
					JTree tree = (JTree) getInvoker();
					if (path != null) {

						DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
								.getLastSelectedPathComponent();

						if (!node.isRoot()) {

							int n = JOptionPane.showConfirmDialog(tree,
									"您确认删除当前选中的类别吗?", "删除类别提示",
									JOptionPane.YES_NO_OPTION);
							if (n == JOptionPane.YES_OPTION) {
								((DefaultTreeModel) tree.getModel())
										.removeNodeFromParent(node);
								   OptionDAOImpl optionMoudle = DAOContentFactriy.getOptionDAO();
								Option tr = (Option) node.getUserObject();
								if (!node.isLeaf()) {
									optionMoudle.deleteOptionByParentId(type,
											tr.getNodeId());
								}
								optionMoudle.deleteOption(tr.getId());
							}
						}
					}
				}
			});
			add(deleItem);
		}

		public void show(Component c, int x, int y) {
			JTree tree = (JTree) c;
			TreePath[] tsp = tree.getSelectionPaths();

			if (tsp != null) {
				TreePath parentPath = tree.getSelectionPath();
				DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) (parentPath
						.getLastPathComponent());
				path = tree.getPathForLocation(x, y); // path =
				// tree.getClosestPathForLocation(x,
				// y);
				if (parentNode.isRoot()) {
					deleItem.setVisible(false);
					modifyItem.setVisible(false);
				} else {
					deleItem.setVisible(true);
					modifyItem.setVisible(true);
				}

				if (tsp[tsp.length - 1].equals(path)) {
					super.show(c, x, y);
				}
			}
		}
	}
}
