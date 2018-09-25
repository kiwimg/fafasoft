package com.fafasoft.flow.ui.widget;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MenuToggleButton extends JToggleButton {
    private static final Icon i = new MenuArrowIcon();
    public MenuToggleButton(String text, Icon icon) {
        super();
        Action a = new AbstractAction(text) {
           public void actionPerformed(ActionEvent ae) {
                MenuToggleButton b = (MenuToggleButton)ae.getSource();
                if(pop!=null) pop.show(b, 0, b.getHeight());
            }
        };
        a.putValue(Action.SMALL_ICON, icon);
        setAction(a);
        setFocusable(false);
        setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4+i.getIconWidth()));
    }
    protected JPopupMenu pop;
    public void setPopupMenu(final JPopupMenu pop) {
        this.pop = pop;
        pop.addPopupMenuListener(new PopupMenuListener() {
             public void popupMenuCanceled(PopupMenuEvent e) {}
             public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                setSelected(false);
            }
        });
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension dim = getSize();
        Insets ins = getInsets();
        int x = dim.width-ins.right;
        int y = ins.top+(dim.height-ins.top-ins.bottom-i.getIconHeight())/2;
        i.paintIcon(this, g, x, y);
    }
 
}
 class MenuArrowIcon implements Icon {
     public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setPaint(Color.BLACK);
        g2.translate(x,y);
        g2.drawLine( 2, 3, 6, 3 );
        g2.drawLine( 3, 4, 5, 4 );
        g2.drawLine( 4, 5, 4, 5 );
        g2.translate(-x,-y);
    }
    public int getIconWidth()  { return 9; }
    public int getIconHeight() { return 9; }
}
