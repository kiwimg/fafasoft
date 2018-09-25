package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.OptionDAOImpl;
import com.fafasoft.flow.pojo.Option;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class BaseJPanel extends JPanel {
	public BaseJPanel() {
	}
	public JTree getJTree() {

		// java.util.List list = optionMoudle.getOption(Constant.TYPE_HW);
		Option rootOption = new Option();
		rootOption.setText("货物类别");
		rootOption.setNodeId("0");
		rootOption.setParentId("0");
		DefaultMutableTreeNode topNode = new DefaultMutableTreeNode(rootOption);
		createNodes(topNode, "0", Constant.TYPE_HW);

		return new JTree(topNode);
	}

	private void createNodes(DefaultMutableTreeNode node, String nodeId,
			String optype) {
		OptionDAOImpl optionMoudle = DAOContentFactriy.getOptionDAO();
		java.util.List list = optionMoudle.getOption(optype, nodeId);

		for (int i = 0; i < list.size(); i++) {
			Option op = (Option) list.get(i);
			if (op.getParentId().equals(nodeId)) {
				DefaultMutableTreeNode category = new DefaultMutableTreeNode(op);
				node.add(category);
				createNodes(category, op.getNodeId(), optype);
			}
		}
	}
}
