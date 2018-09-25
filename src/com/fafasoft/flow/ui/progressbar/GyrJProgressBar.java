package com.fafasoft.flow.ui.progressbar;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: noah
 * Date: 7/24/12
 * Time: 8:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class GyrJProgressBar extends JProgressBar {

    private class GyrProgressUI extends BasicProgressBarUI {

        private double greenOverPercent=(2d/3d)*100d;

        private double yellowOverPercent=(1d/3d)*100d;

        private JProgressBar jProgressBar;

        private GyrProgressUI(JProgressBar jProgressBar) {
            this.jProgressBar = jProgressBar;
        }

        @Override
        protected void paintDeterminate(Graphics g, JComponent c) {


            double percent=100d*this.jProgressBar.getValue()/(this.jProgressBar.getMaximum()-this.jProgressBar.getMinimum());

            if (percent > this.greenOverPercent) {
                this.jProgressBar.setForeground(Color.green);
            } else if (percent > this.yellowOverPercent) {
                this.jProgressBar.setForeground(Color.yellow);
            } else {
                this.jProgressBar.setForeground(Color.red);
            }
         
            super.paintDeterminate(g, c);
        }

    }

    public GyrJProgressBar() {
        init();
    }

    public GyrJProgressBar(int orient) {
        super(orient);
        init();
    }

    public GyrJProgressBar(int min, int max) {
        super(min, max);
        init();
    }

    public GyrJProgressBar(int orient, int min, int max) {
        super(orient, min, max);
        init();
    }

    public GyrJProgressBar(BoundedRangeModel newModel) {
        super(newModel);
        init();
    }

    private void init(){
        setBorderPainted(false); 
        setBorder(null);   
        setUI(new GyrProgressUI(this));
    }
}
