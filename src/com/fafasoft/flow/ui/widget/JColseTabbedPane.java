package com.fafasoft.flow.ui.widget;

import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.PanelCache;
import com.fafasoft.flow.ui.panel.EveryDayFlowPanel;
import com.fafasoft.flow.ui.panel.LazyPageContent;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JColseTabbedPane extends JTabbedPane {

	public JColseTabbedPane() {
		super(JColseTabbedPane.TOP);
		setFont(new Font("宋体", Font.PLAIN, 12));
	}
	
	public void addTab(String title, final Component content) {
		final JPanel tab = new JPanel(new BorderLayout());
		tab.setOpaque(false);
		JLabel label = new JLabel(title);

		label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		if (super.getTabCount() > 0) {
			final JToggleButton toggleButton_de = new JToggleButton("");
			toggleButton_de.setIcon(new ImageIcon(JColseTabbedPane.class
					.getResource("/com/fafasoft/flow/resource/image/delete0.png")));
			toggleButton_de.setFocusable(false);
			toggleButton_de.setBorderPainted(false);
			toggleButton_de.setContentAreaFilled(false);
			toggleButton_de.setMargin(new Insets(0, 0, 0, 0));

			toggleButton_de.setBorder(BorderFactory.createEmptyBorder());
			toggleButton_de.setIconTextGap(0);
			toggleButton_de.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					toggleButton_de.setIcon(new ImageIcon(JColseTabbedPane.class
							.getResource("/com/fafasoft/flow/resource/image/delete1.png")));
					//toggleButton_de.setBorderPainted(true);
				}

				public void mouseExited(MouseEvent e) {
					toggleButton_de.setIcon(new ImageIcon(JColseTabbedPane.class
							.getResource("/com/fafasoft/flow/resource/image/delete0.png")));
					toggleButton_de.setBorderPainted(false);
				}

				public void mouseClicked(MouseEvent e) {
					removeTabAt(indexOfComponent(content));
					PanelCache.getInstance().remove(
							content.getClass().getName());

				}
			});
			tab.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					JColseTabbedPane jColseTabbedPane = MainWindows
							.getInstance().getTabbedPane();
					jColseTabbedPane.dispatchEvent(SwingUtilities
							.convertMouseEvent(tab, e, jColseTabbedPane));
				}
			});
			tab.add(label, BorderLayout.CENTER);
			tab.add(toggleButton_de, BorderLayout.EAST);
			tab.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		}
		super.addTab(title, content);
		tab.setComponentPopupMenu(new TabClosePopupMenu(content.getClass().getName()));
	  
		setTabComponentAt(getTabCount() - 1, tab);
		setSelectedIndex(getTabCount() - 1);
	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (content instanceof LazyPageContent) {
					LazyPageContent lazy = (LazyPageContent)content;
					lazy.initPanel();
				}
			}
		});	
	}

	class TabClosePopupMenu extends JPopupMenu {
		private String content;
		private JMenuItem closeItem = new JMenuItem("  关闭  ");
		private JMenuItem closeOtherItem = new JMenuItem("  关闭其他  ");
		private JMenuItem closeAllItem = new JMenuItem("  全部关闭  ");

		private final Action closeAction = new AbstractAction() {
			public void actionPerformed(ActionEvent evt) {
				JColseTabbedPane jColseTabbedPane = MainWindows.getInstance()
						.getTabbedPane();
				jColseTabbedPane.removeTabAt(jColseTabbedPane
						.getSelectedIndex());
				PanelCache.getInstance().remove(content);
			}
		};
		private final Action closeOtherAction = new AbstractAction() {

			public void actionPerformed(ActionEvent evt) {
				JColseTabbedPane jColseTabbedPane = MainWindows.getInstance()
						.getTabbedPane();
				jColseTabbedPane.getSelectedComponent();

				while (jColseTabbedPane.getTabCount() > 2) {
					int selindx = jColseTabbedPane.getSelectedIndex();
					if (selindx > 1) {
						jColseTabbedPane.removeTabAt(1);
					} else if (selindx == 1) {
						jColseTabbedPane.removeTabAt(2);
					}
				}

				PanelCache.getInstance().removeOther(content);
			}
		};
		private final Action closeAllAction = new AbstractAction() {
			
			public void actionPerformed(ActionEvent evt) {
				JColseTabbedPane jColseTabbedPane = MainWindows.getInstance()
						.getTabbedPane();

				while (jColseTabbedPane.getTabCount() > 1) {
					jColseTabbedPane.removeTabAt(1);
				}
				PanelCache.getInstance().removeAll();
			}
		};

		public TabClosePopupMenu(final String content) {
			super();
			this.content = content;

			add(closeItem);
			closeItem.addActionListener(closeAction);
			addSeparator();
			add(closeOtherItem);
			closeOtherItem.addActionListener(closeOtherAction);
			addSeparator();
			add(closeAllItem);
			closeAllItem.addActionListener(closeAllAction);
			setFont(new Font("宋体", Font.PLAIN, 12));
		}

		public void show(Component c, int x, int y) {
			JColseTabbedPane jColseTabbedPane = MainWindows.getInstance()
					.getTabbedPane();
			if (jColseTabbedPane.getTabCount() > 2) {
				closeOtherItem.setEnabled(true);
			} else {
				closeOtherItem.setEnabled(false);
			}
			super.show(c, x, y);
		}
	}
}