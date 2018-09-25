package com.fafasoft.flow.ui.widget;

import com.fafasoft.flow.SysEnv;
import com.fafasoft.flow.util.GuiUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class SuggestTextFieldJTable extends JTextField {
    private JPopupMenu popupMenu;
    private JTable cacheJTable;
    //private Set<String> matchResultSet = new HashSet<String>();
    private DefaultListModel resultListModel;
    private ActionListener listListener;
    private SuggestDataJTable suggestData;
    private MUIManager uiManager;
    private int width;
    private MUIKeyEvent mUIKeyEvent;

    /**
     * @param suggestData
     * @param uiManagerss
     * @param width        width>0为实际设置宽度值,width=0为super.width,width<0为自动调整宽度
     * @param mUIKeyEvents
     */
    public SuggestTextFieldJTable(SuggestDataJTable suggestData, MUIManager uiManagerss, int width, MUIKeyEvent mUIKeyEvents) {
        this.suggestData = suggestData;
        this.uiManager = uiManagerss;
        this.width = width;
        this.mUIKeyEvent = mUIKeyEvents;
        Listener listener = new Listener();
        KeyDisposer keyListener = new KeyDisposer();
        this.registerKeyboardAction(listener, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
        resultListModel = new DefaultListModel();
        cacheJTable = new JTable();
        listListener = new ListListener();
        cacheJTable.registerKeyboardAction(listListener, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
        cacheJTable.registerKeyboardAction(new VKUPListener(), KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), JComponent.WHEN_FOCUSED);
        cacheJTable.registerKeyboardAction(new VKDOWNListener(), KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), JComponent.WHEN_FOCUSED);

        cacheJTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                int nRow = cacheJTable.getSelectedRow();

                Object objSel = cacheJTable.getValueAt(nRow, 1);
                if (uiManager != null) {
                    String stockID = (String) cacheJTable.getValueAt(nRow, 0);
                    Object sell = cacheJTable.getValueAt(nRow, 4);
                    String[] arg = {stockID, String.valueOf(sell)};
                    setText(String.valueOf(objSel));
                    popupMenu.setVisible(false);
                    uiManager.updateView(arg);
                } else {
                    setText(String.valueOf(objSel));
                    popupMenu.setVisible(false);
                }
                requestFocus();
            }
        });

        popupMenu = new JPopupMenu();
        popupMenu.setBorder(new TitledBorder("输入提示"));
        popupMenu.setFocusable(false);
        this.addKeyListener(keyListener);
        //setVisible(true);
    }


    public void showMatch() {
        String keyWord = this.getText().trim();
        if ("".equals(keyWord)) {
            popupMenu.removeAll();
            popupMenu.setVisible(false);
            this.requestFocus();
            if (uiManager != null) {
                uiManager.updateView(null);
            }
            return;
        }

        JTable temp = this.suggestData.getData(keyWord);

        if (temp != null && temp.getRowCount() > 0) {
            // cacheJTable.requestFocus();
            cacheJTable.setModel(temp.getModel());
            cacheJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            cacheJTable.setRowSelectionInterval(0, 0);
            cacheJTable.setTableHeader(temp.getTableHeader());
            cacheJTable.getTableHeader().setReorderingAllowed(false);
            cacheJTable.setColumnModel(temp.getColumnModel());

            //计算出的表格需要的总总宽总是便大，设置margin为负值修正
            int tableWidth = GuiUtils.autoResizeColWidth(cacheJTable, -7);

            int size = cacheJTable.getRowCount() + 1;

            JScrollPane tipFrame = new JScrollPane();
            tipFrame.setViewportView(cacheJTable);
            tipFrame.setBackground(Color.WHITE);
            if (this.width > 0) {
                tipFrame.setPreferredSize(new Dimension(width, size * 18));
            } else if (this.width == 0) {
                tipFrame.setPreferredSize(new Dimension(super.getWidth() + 20, size * 18));
            } else {
                tipFrame.setPreferredSize(new Dimension(tableWidth, size * 18));
            }
            double jScrollPaneHeight = tipFrame.getPreferredSize().getHeight();
            double jScrollPaneWidth = tipFrame.getPreferredSize().getWidth();
            int c = popupMenu.getComponentCount();
            if (c == 0) {
                popupMenu.add(tipFrame);
            } else {
                popupMenu.setVisible(false);
                popupMenu.remove(0);
                popupMenu.add(tipFrame);
                popupMenu.setVisible(true);
                this.requestFocus();
            }

            popupMenu.show(this, 0, this.getHeight());

            Point point = getPoint();
            popupMenu.setLocation((int) point.getX(), (int) (point.getY() - jScrollPaneHeight - 30));
            requestFocus();

        } else {
            popupMenu.setVisible(false);
            popupMenu.removeAll();
        }
    }

    private Point getPoint() {
        Point invokerOrigin = this.getLocationOnScreen();
        // To avoid integer overflow
        long lx, ly;
        lx = ((long) invokerOrigin.x);

        ly = ((long) invokerOrigin.y);

        if (lx > Integer.MAX_VALUE) lx = Integer.MAX_VALUE;
        if (lx < Integer.MIN_VALUE) lx = Integer.MIN_VALUE;
        if (ly > Integer.MAX_VALUE) ly = Integer.MAX_VALUE;
        if (ly < Integer.MIN_VALUE) ly = Integer.MIN_VALUE;

        return new Point((int) lx, (int) ly);
    }

    private class KeyDisposer extends KeyAdapter {
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == 27) {
                popupMenu.setVisible(false);
            } else if (e.getKeyCode() == 40) {
                popupMenu.setVisible(true);
                requestFocus();
                if (resultListModel.size() >= 2)
                    cacheJTable.setRowSelectionInterval(0, 0);
            } else if (e.getKeyCode() == 38) {
                popupMenu.setVisible(true);
                requestFocus();
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (mUIKeyEvent != null) {
                    mUIKeyEvent.vkEnterENTER();
                }
            } else {
                boolean issuggest = SysEnv.getInstance().isSuggest();
                if (issuggest) {
                    showMatch();
                }
            }
        }
    }

    class Listener extends MouseAdapter implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (popupMenu.isShowing())
                listListener.actionPerformed(e);
        }
    }

    class VKUPListener extends MouseAdapter implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (popupMenu.isShowing()) {
                int nRow = cacheJTable.getSelectedRow();
                int rowCount = cacheJTable.getRowCount();
                if (nRow > 0) {
                    cacheJTable.setRowSelectionInterval(nRow - 1, nRow - 1);
                }

            }
        }
    }

    class VKDOWNListener extends MouseAdapter implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (popupMenu.isShowing()) {
                int nRow = cacheJTable.getSelectedRow();
                int rowCount = cacheJTable.getRowCount();
                if (nRow + 1 == rowCount) {
                    cacheJTable.setRowSelectionInterval(0, 0);
                } else {
                    cacheJTable.setRowSelectionInterval(nRow + 1, nRow + 1);
                }
            }
        }
    }

    private class ListListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int nRow = cacheJTable.getSelectedRow();

            Object objSel = cacheJTable.getValueAt(nRow, 1);
            if (uiManager != null) {
                String stockID = (String) cacheJTable.getValueAt(nRow, 0);
                Object sell = cacheJTable.getValueAt(nRow, 4);
                String[] arg = {stockID, String.valueOf(sell)};
                setText(String.valueOf(objSel));
                popupMenu.setVisible(false);
                uiManager.updateView(arg);
            } else {
                setText(String.valueOf(objSel));
                popupMenu.setVisible(false);
            }

            requestFocus();
        }
    }

    public interface SuggestDataJTable {
        public JTable getData(String value);
    }

    public interface MUIManager {
        public void updateView(String[] args);
    }

    public interface MUIKeyEvent {
        public void vkEnterENTER();
    }
}
