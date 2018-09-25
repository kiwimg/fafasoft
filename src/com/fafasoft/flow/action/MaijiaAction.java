package com.fafasoft.flow.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.fafasoft.flow.util.GuiUtils;

public class MaijiaAction extends AbstractAction {
    public void actionPerformed(ActionEvent e) {
    	GuiUtils.browser("http://www.ymaijia.com/");
    }

}
