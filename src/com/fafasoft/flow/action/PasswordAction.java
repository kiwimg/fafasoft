package com.fafasoft.flow.action;

import com.fafasoft.flow.ui.MainWindows;
import com.fafasoft.flow.ui.widget.PasswordDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PasswordAction extends AbstractAction {
    public void actionPerformed(ActionEvent e) {
        PasswordDialog passwordDialog = new PasswordDialog(MainWindows.getInstance());
        passwordDialog.setVisible(true);
    }
}
