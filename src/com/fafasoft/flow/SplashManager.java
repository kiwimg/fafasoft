package com.fafasoft.flow;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.SplashScreen;
import javax.swing.JComponent;

public class SplashManager{
    //This transparent color is used for wipe out background for repainting.
    private static Color transparentColor=new Color(0,0,0,0);
    //Graphics object obtained from splash screen
    private Graphics2D graphics;
    //The Swing component you are about to render on splash screen.
    private JComponent component;
    //The splash instance obtained from system.
    private SplashScreen splash=SplashScreen.getSplashScreen();
    //The splash bounds, which is identical to that of splash image.
    private Rectangle bounds;
    /** Creates a new instance of SplashManager */
    public SplashManager(JComponent comp) {
        if(splash==null)
            throw new IllegalArgumentException("Splash Screen not set!");
        //Initialize these fields
        bounds=splash.getBounds();
        graphics=splash.createGraphics();
        //Set the background to transparent so that later you can refresh your rendering.
        graphics.setBackground(transparentColor);
        component=comp;
        //Note you must disable the double-buffer feature of the component,
        //or else double-buffer image might
        //overwrite the splash image background.
        component.setDoubleBuffered(false);
        //Note this container component must be transparent so that the image is not covered.
        component.setOpaque(false);
        //Resize the container component to the exact size of the splash image.
        component.setSize(splash.getSize());
        //This is crucial. For a container usually use layout manager to layout their components.
        //Without swing system, you have to manually layout the children components.
        LayoutManager layout=component.getLayout();
        layout.layoutContainer(component);
        repaint();
    }
    /**
     * This method is counterpart of swing component's repaint.
     * The difference is that you have to call this method manually without
     * swing event system.
     */
    public void repaint(){
        //First only when splash is visible
        if(splash.isVisible()){
            //Wipe out background, note this is only to wipe out
            //the background of overlay buffered image, not that you
            //wipe out the splash image.
            graphics.clearRect(0, 0, bounds.width, bounds.height);
            //Delegate painting to swing component's painting method
            component.paint(graphics);
            //Again, you have to manually update the splash. All the above
            //work is down in background image, not directly to the screen.
            //update method flush these changes to the screen.
            splash.update();
        }
    }
    /**
     * Close the splash
     */
    public void closeSplash(){
        if(splash.isVisible())
            splash.close();
    }
}
