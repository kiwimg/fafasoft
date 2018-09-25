package com.fafasoft.flow.ui;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.fafasoft.flow.ui.widget.JColseTabbedPane;

public class NavgationIndex extends JPanel{
	public NavgationIndex() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{22, 86, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0};
		gridBagLayout.rowHeights = new int[]{20, 55, 20, 55, 20, 55, 20, 55, 20, 55, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
	}
	public void addTitle(String tile,ImageIcon ico,int gridx , int gridy){
		JLabel lblNewLabel = new JLabel(tile);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setIcon(ico);
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = gridx;
		gbc_lblNewLabel.gridy = gridy;
		add(lblNewLabel, gbc_lblNewLabel);
	}
	public void addButton(String text, ImageIcon icon,int gridx , int gridy, final Action action,
			boolean isVisible) {
		JButton button = createButton(text, icon);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (action != null) {
					action.actionPerformed(event);
				} else {
				}
			}
		});
	
		button.setVisible(isVisible);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = gridx;
		gbc_btnNewButton.gridy = gridy;
		add(button, gbc_btnNewButton);

	}
	public void addButton(String tile,ImageIcon ico,int gridx , int gridy,final Class classPanel, boolean isVisible){
		final JButton btnNewButton =createButton(tile,ico);
		
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = gridx;
		gbc_btnNewButton.gridy = gridy;
		btnNewButton.setVisible(isVisible);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						JColseTabbedPane jColseTabbedPane = MainWindows
								.getInstance().getTabbedPane();
						Component ob = (Component) PanelCache.getInstance()
								.get(classPanel.getName());
						if (ob == null) {
							try {
								ob = (Component) classPanel.newInstance();
								PanelCache.getInstance().put(
										classPanel.getName(), ob);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
							jColseTabbedPane.add(btnNewButton.getText(), ob);
						} else {

							int zOrder = jColseTabbedPane.indexOfComponent(ob);
							if (zOrder > 1) {
								jColseTabbedPane.setSelectedIndex(zOrder);
							} else if (zOrder >= 0) {
								jColseTabbedPane.setSelectedIndex(1);
								
							}
							if (zOrder < 0) {
								jColseTabbedPane.add(btnNewButton.getText(), ob);
							}
						}
					}
				});
			}
		});

		add(btnNewButton, gbc_btnNewButton);
	}

	private JButton createButton(String text, Icon icon) {
		final JButton button = new JButton(text);
		button.setMargin(new Insets(2, 0, 2, 0));
		button.setVerticalAlignment(SwingConstants.TOP);
		button.setToolTipText(text);
		button.setIconTextGap(2);
		button.setFocusable(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);

		button.setIcon(icon);
		button.setPreferredSize(new Dimension(87, 70));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setContentAreaFilled(true);
				button.setBorderPainted(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setContentAreaFilled(false);
				button.setBorderPainted(false);
			}
		});
		return button;
	}
}
