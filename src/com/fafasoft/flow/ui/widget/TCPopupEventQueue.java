package com.fafasoft.flow.ui.widget;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class TCPopupEventQueue extends EventQueue {
    public JPopupMenu popup;
    public BasicAction cut, copy, paste, selectAll;

    public TCPopupEventQueue() {
    }

    public void createPopupMenu(JTextComponent text) {
        cut = new CutAction("剪切", null);
        copy = new CopyAction("复制", null);
        paste = new PasteAction("粘贴", null);
        selectAll = new SelectAllAction("全选", null);
        cut.setTextComponent(text);
        copy.setTextComponent(text);
        paste.setTextComponent(text);
        selectAll.setTextComponent(text);
        popup = new JPopupMenu();
        popup.add(cut);
        popup.add(copy);
        popup.add(paste);
        popup.addSeparator();
        popup.add(selectAll);
    }

    public void showPopup(Component parent, MouseEvent me) {
        popup.validate();
        popup.show(parent, me.getX(), me.getY());
    }

    protected void dispatchEvent(AWTEvent event) {
        super.dispatchEvent(event);
        if (!(event instanceof MouseEvent)) {
            return;
        }
        MouseEvent me = (MouseEvent) event;
        if (!me.isPopupTrigger()) {

            return;
        }
        if (!(me.getSource() instanceof Component)) {
            return;
        }
        Component comp = SwingUtilities.getDeepestComponentAt((Component)
                me.getSource(), me.getX(), me.getY());
        if (!(comp instanceof JTextComponent)) {
            return;
        }
        if (MenuSelectionManager.defaultManager().getSelectedPath().length > 0) {
            return;
        }
        createPopupMenu((JTextComponent) comp);
        showPopup((Component) me.getSource(), me);
    }

    public abstract class BasicAction extends AbstractAction {
        JTextComponent comp;

        public BasicAction(String text, Icon icon) {
            super(text, icon);
            putValue(Action.SHORT_DESCRIPTION, text);
        }

        public void setTextComponent(JTextComponent comp) {
            this.comp = comp;
        }

        public abstract void actionPerformed(ActionEvent e);
    }

    public class CutAction extends BasicAction {
        public CutAction(String text, Icon icon) {
            super(text, icon);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl X"));
        }

        public void actionPerformed(ActionEvent e) {
            comp.cut();
        }

        public boolean isEnabled() {
            return comp != null && comp.isEditable() && comp.getSelectedText() != null;
        }
    }

    public class CopyAction extends BasicAction {
        public CopyAction(String text, Icon icon) {
            super(text, icon);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl C"));
        }

        public void actionPerformed(ActionEvent e) {
            comp.copy();
        }

        public boolean isEnabled() {
            return comp != null && comp.getSelectedText() != null;
        }
    }

    public class PasteAction extends BasicAction {
        public PasteAction(String text, Icon icon) {
            super(text, icon);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl V"));
        }

        public void actionPerformed(ActionEvent e) {
            comp.paste();
        }

        public boolean isEnabled() {
            Transferable content = Toolkit.getDefaultToolkit().getSystemClipboard()
                    .getContents(null);
            return comp != null && comp.isEnabled() && comp.isEditable()
                    && content.isDataFlavorSupported(DataFlavor.stringFlavor);
        }
    }

    public class SelectAllAction extends BasicAction {
        public SelectAllAction(String text, Icon icon) {
            super(text, icon);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl A"));
        }

        public void actionPerformed(ActionEvent e) {
            comp.selectAll();
        }

        public boolean isEnabled() {
            return comp != null && comp.isEnabled() && comp.getText().length() > 0
                    && (comp.getSelectedText() == null ||
                    comp.getSelectedText().length() < comp.getText().length());
        }
    }
}
