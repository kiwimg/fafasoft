package com.fafasoft.flow.ui.widget;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class SuggestTextField extends JTextField {

    private JPopupMenu popupMenu;
    private JList cacheKeywordList;
    //private Set<String> matchResultSet = new HashSet<String>();
    private DefaultListModel resultListModel;
    private ActionListener listListener;
    private SuggestData suggestData;
    private MUIManager uiManager;
    private Dimension dimension;
    private MUIKeyEvent mUIKeyEvent;

    public SuggestTextField(SuggestData suggestData, MUIManager uiManagerss, Dimension dimensionx, MUIKeyEvent mUIKeyEvents) {
        this.suggestData = suggestData;
        this.uiManager = uiManagerss;
        this.dimension = dimensionx;
        this.mUIKeyEvent = mUIKeyEvents;
        Listener listener = new Listener();
        KeyDisposer keyListener = new KeyDisposer();
        this.registerKeyboardAction(listener, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
        resultListModel = new DefaultListModel();
        cacheKeywordList = new JList(resultListModel);
        cacheKeywordList.setBackground(Color.WHITE);
        cacheKeywordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listListener = new ListListener();
        cacheKeywordList.registerKeyboardAction(listListener, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
        cacheKeywordList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                if (uiManager != null) {
                    String[] arg = cacheKeywordList.getSelectedValue().toString().split(",");
                    setText(arg[0]);
                    popupMenu.setVisible(false);
                    uiManager.updateView(arg);
                } else {
                    setText(cacheKeywordList.getSelectedValue().toString());
                    popupMenu.setVisible(false);
                }
                requestFocus();
            }
        });
        popupMenu = new JPopupMenu();
        popupMenu.setBorder(new LineBorder(null, 0));
        this.addKeyListener(keyListener);
        setVisible(true);
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

        List<String> list = this.suggestData.getData(keyWord);
        if (list != null && list.size() > 0) {
            int size = list.size();
           
            JComponent tipFrame = new JScrollPane(cacheKeywordList);
            tipFrame.setBackground(Color.WHITE);
            if (dimension != null) {
                dimension.setSize(dimension.getWidth(), size * 21);
                tipFrame.setPreferredSize(dimension);
            } else {
                tipFrame.setPreferredSize(new Dimension(super.getWidth() + 20, size * 21));
            }

            int c = popupMenu.getComponentCount();
            if (c == 0) {
                popupMenu.add(tipFrame);
            } else {
                popupMenu.setVisible(false);
                popupMenu.remove(0);
                popupMenu.add(tipFrame);
                popupMenu.setVisible(true);
            }
            resultListModel.removeAllElements();
            for (String result : list)
                resultListModel.addElement(result);
            popupMenu.show(this, 0, this.getHeight());
            cacheKeywordList.setSelectedIndex(0);
            this.requestFocus();
        } else {
            popupMenu.setVisible(false);
            popupMenu.removeAll();
        }
    }


    private class KeyDisposer extends KeyAdapter {
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == 27) {
                popupMenu.setVisible(false);
            } else if (e.getKeyCode() == 40) {
                popupMenu.setVisible(true);
                cacheKeywordList.requestFocus();
                if (resultListModel.size() >= 2)
                    cacheKeywordList.setSelectedIndex(1);
            } else if (e.getKeyCode() == 38) {
                popupMenu.setVisible(true);
                cacheKeywordList.requestFocus();
                cacheKeywordList.setSelectedIndex(resultListModel.size() - 1);
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (mUIKeyEvent != null) {
                    mUIKeyEvent.vkEnterENTER();
                }
            } else {
                showMatch();
            }
        }
    }

    class Listener extends MouseAdapter implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (popupMenu.isShowing())
                listListener.actionPerformed(e);
        }
    }

    private class ListListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (uiManager != null) {
                String[] arg = cacheKeywordList.getSelectedValue().toString().split(",");
                setText(arg[0]);
                popupMenu.setVisible(false);
                uiManager.updateView(arg);
            } else {
                setText(cacheKeywordList.getSelectedValue().toString());
                popupMenu.setVisible(false);
            }
            requestFocus();
        }
    }

    public interface SuggestData {
        public List<String> getData(String value);
    }

    public interface MUIManager {
        public void updateView(String[] args);
    }

    public interface MUIKeyEvent {
        public void vkEnterENTER();
    }
}



