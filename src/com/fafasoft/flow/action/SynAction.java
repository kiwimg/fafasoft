package com.fafasoft.flow.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.widget.BackUpDialog;

public class SynAction extends AbstractAction {
    public void actionPerformed(ActionEvent e) {
    	BackUpDialog backUpDialog = new BackUpDialog(MainWindows.getInstance());
    	backUpDialog.setVisible(true);
    	
    }
}
