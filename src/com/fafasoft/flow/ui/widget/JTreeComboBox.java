package com.fafasoft.flow.ui.widget;

import org.jvnet.substance.SubstanceComboBoxUI;

import javax.swing.*;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;

public class JTreeComboBox extends JComboBox {
	private static final long serialVersionUID = 859080208518322532L;
	private boolean istop = false;
	/**
	 * 显示用的树
	 */
	private JTree tree;

	public JTreeComboBox(JTree tree) {
		this.setTree(tree);
	}

	public JTreeComboBox(JTree tree, boolean type) {
		this.istop = type;
		this.setTree(tree);
	}

	/**
	 * 设置树
	 * 
	 * @param tree
	 *            JTree
	 */
	public void setTree(JTree tree) {
		this.tree = tree;
		if (tree != null) {
			this.setSelectedItem(tree.getSelectionPath());
			this.setRenderer(new JTreeComboBoxRenderer());
		}
		this.updateUI();
	}

	/**
	 * 取得树
	 * 
	 * @return JTree
	 */
	public JTree getTree() {
		return this.tree;
	}

	/**
	 * 设置当前选择的树路径
	 * 
	 * @param o
	 *            Object
	 */
	public void setSelectedItem(Object o) {
		if (o instanceof TreePath) {
			this.tree.setSelectionPath((TreePath) o);
		}
		getModel().setSelectedItem(o);

	}

	public Object getSelectedItemValue() {
		TreePath path = this.tree.getSelectionPath();
		if (path != null) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
					.getLastPathComponent();
			return node.getUserObject();
		} else {
			return null;
		}
	}

	public TreePath findInTree(String str) {
		Object root = this.tree.getModel().getRoot();
		TreePath treePath = new TreePath(root);
		treePath = findInPath(treePath, str);
		if (treePath != null) {
			this.tree.setSelectionPath(treePath);
			this.tree.scrollPathToVisible(treePath);
		}
		return treePath;
	}

	private TreePath findInPath(TreePath treePath, String str) {
		Object object = treePath.getLastPathComponent();
		if (object == null) {
			return null;
		}
		String value = object.toString();
		if (str.equals(value)) {
			return treePath;
		} else {
			TreeModel model = this.tree.getModel();
			int n = model.getChildCount(object);
			for (int i = 0; i < n; i++) {
				Object child = model.getChild(object, i);
				TreePath path = treePath.pathByAddingChild(child);

				path = findInPath(path, str);
				if (path != null) {
					return path;
				}
			}
			return null;
		}
	}

	public void updateUI() {
		setUI(new WindowsJTreeComboBoxUI());
	}

	class WindowsJTreeComboBoxUI extends SubstanceComboBoxUI {
		protected ComboPopup createPopup() {
			return new TreePopup(comboBox);
		}
	}

	/**
	 * Description: 树形结构而来的DefaultListCellRenderer
	 */
	class JTreeComboBoxRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			JLabel lb = new JLabel();
			if (value != null) {

				if (value instanceof TreePath) {
					TreePath path = (TreePath) value;
					TreeNode node = (TreeNode) path.getLastPathComponent();
					value = node;
					TreeCellRenderer r = tree.getCellRenderer();
					lb = (JLabel) r.getTreeCellRendererComponent(tree, value,
							isSelected, false, node.isLeaf(), index,
							cellHasFocus);
				}

				return lb;
			}
			return super.getListCellRendererComponent(list, value, index,
					isSelected, cellHasFocus);
		}
	}

	class TreePopup extends JPopupMenu implements ComboPopup {
		protected JTreeComboBox comboBox;
		protected JScrollPane scrollPane;
		protected JList list = new JList();
		protected MouseMotionListener mouseMotionListener;
		protected MouseListener mouseListener;
		private MouseListener treeSelectListener = new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				JTree tree = (JTree) e.getSource();
				TreePath tp = tree.getSelectionPath();
				// System.out.println("tp=="+tp);
				if (tp == null) {
					return;
				}

				TreePath parentPath = tree.getSelectionPath();
				DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) (parentPath
						.getLastPathComponent());
				if (!parentNode.isRoot()) {

					comboBox.setSelectedItem(tp);
				} else {
					comboBox.setSelectedItem(null);
				}
				togglePopup(e);
				MenuSelectionManager.defaultManager().clearSelectedPath();
			}
		};

		public TreePopup(JComboBox comboBox) {
			this.comboBox = (JTreeComboBox) comboBox;
			// setBorder(BorderFactory.createLineBorder(Color.black));
			setLayout(new BorderLayout());
			setLightWeightPopupEnabled(comboBox.isLightWeightPopupEnabled());
			JTree tree = this.comboBox.getTree();
			if (tree != null) {
				scrollPane = new JScrollPane(tree);
				scrollPane.setBorder(null);
				add(scrollPane, BorderLayout.CENTER);
				tree.addMouseListener(treeSelectListener);
			}
		}

		// public void setVisible(boolean isvisible){
		// updatePopup();
		//
		// if (istop) {
		// show(comboBox, 0, -200);
		// } else {
		// show(comboBox, 0, 25);
		// }
		// comboBox.getTree().requestFocus();
		// }

		public JList getList() {
			return list;
		}

		public MouseMotionListener getMouseMotionListener() {
			if (mouseMotionListener == null) {
				mouseMotionListener = new MouseMotionAdapter() {
				};
			}
			return mouseMotionListener;
		}

		public KeyListener getKeyListener() {
			return null;
		}

		public void uninstallingUI() {
		}

		/**
		 * Implementation of ComboPopup.getMouseListener().
		 * 
		 * @return a <code>MouseListener</code> or null
		 * @see ComboPopup#getMouseListener
		 */
		public MouseListener getMouseListener() {
			if (mouseListener == null) {
				mouseListener = new InvocationMouseHandler();
			}
			return mouseListener;
		}

		protected void togglePopup(MouseEvent e) {
			if (isVisible()) {
				 hide();
			} else {
				if (e.MOUSE_RELEASED != e.getID()) {
					show();
				}
			}
		}

		protected void updatePopup() {
			JTree tree = this.comboBox.getTree();

			setPreferredSize(new Dimension(comboBox.getSize().width, 200));
			Object selectedObj = comboBox.getSelectedItem();
			if (selectedObj != null) {
				if (selectedObj instanceof TreePath) {
					TreePath tp = (TreePath) selectedObj;
					comboBox.getTree().setSelectionPath(tp);
				}
			}
		}

		public void show() {
			updatePopup();
			if (istop)
				show(this.comboBox, 0, -200);
			else {
				show(this.comboBox, 0, 25);
			}

			this.comboBox.getTree().requestFocus();
		}

		public void hide() {
			setVisible(false);
			this.comboBox.firePropertyChange("popupVisible", true, false);
		}

		protected class InvocationMouseHandler extends MouseAdapter {
			public void mousePressed(MouseEvent e) {
				if (!SwingUtilities.isLeftMouseButton(e)
						|| !comboBox.isEnabled()) {
					return;
				}
				if (comboBox.isEditable()) {
					Component comp = comboBox.getEditor().getEditorComponent();
					if ((!(comp instanceof JComponent))
							|| ((JComponent) comp).isRequestFocusEnabled()) {
						comp.requestFocus();
					}
				} else if (comboBox.isRequestFocusEnabled()) {
					comboBox.requestFocus();
				}
				togglePopup(e);
			}
		}
	}

}
