package com.fafasoft.flow.action;

import java.awt.event.MouseEvent;

public interface PageAction {
    public void pageFirst();
    public void pagePrev(int pagenum);
    public void pageNext(int pagenum);
    public void pageLast(int pagenum);
    public void itemStateChanged(int pagenum);
    public void export(MouseEvent e);
}
