package com.fafasoft.flow.ui.widget;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import java.awt.event.ActionListener;

public class JTextFieldWithButton extends JPanel {
	private JButton button;
	private JTextField textField;
	private JTextField textField_1;

	public JTextFieldWithButton(JTextField field, ImageIcon ico) {
		setAlignmentY(0.0f);
		setAlignmentX(0.0f);
		setBorder(UIManager.getBorder("ComboBox.border"));
		setLayout(new BorderLayout(0, 0));
		button = new JButton();
		button.setIcon(ico);
		textField = field;
		field.setBorder(new EmptyBorder(0, 0, 0, 0));
		button.setPreferredSize(new Dimension(30, 0));
		add(field);
		add(button, BorderLayout.EAST);
		setFocusable(false);
		
	}

	public JTextField getJTextField() {
		return textField;
	}

	public String getInputValue() {
		return textField.getText();
	}
	public void addButtonActionListener(ActionListener actionListener) {
		button.addActionListener(actionListener);
	}
}
