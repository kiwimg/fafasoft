package com.fafasoft.flow.ui.skin;

import com.fafasoft.flow.ui.substance.MyRavenGraphiteLookAndFeel;
import com.fafasoft.flow.util.GuiUtils;
import org.jvnet.lafwidget.LafWidget;
import org.jvnet.lafwidget.utils.LafConstants;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.api.SubstanceConstants;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * @author liyc
 * @version 1.0
 * @since JDK1.5
 */
public class LookAndFeelSelector {

    /**
     * The skins.
     */
    private static Map<String, String> skins = setListOfSkins();

    /**
     * The Constant DEFAULT_SKIN.
     */
    public static final String DEFAULT_SKIN = "aTunes Dark";

//    /**
//     * Gets the list of skins.
//     *
//     * @return the list of skins
//     */
//    public static List<String> getListOfSkins() {
//        List<String> result = new ArrayList<String>(skins.keySet());
//        Collections.sort(result, new Comparator<String>() {
//
//            public int compare(String o1, String o2) {
//                return o1.toLowerCase().compareTo(o2.toLowerCase());
//            }
//        });
//        return result;
//    }

    /**
     * Sets the list of skins.
     *
     * @return the map< string, string>
     */
    private static Map<String, String> setListOfSkins() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("NebulaBrickWall", "org.jvnet.substance.skin.SubstanceNebulaBrickWallLookAndFeel");
        result.put("Creme", "org.jvnet.substance.skin.SubstanceCremeLookAndFeel");
        result.put("Sahara", "org.jvnet.substance.skin.SubstanceSaharaLookAndFeel");
        result.put("OfficeBlue2007", "org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel");
      
        return result;
    }

    /**
     * Sets the look and feel.
     *
     * @param theme the new look and feel
     */
    public static void setLookAndFeel(String theme) {
        try {
            //logger.info(theme);
            if (skins.containsKey(theme)) {
                UIManager.setLookAndFeel(skins.get(theme));
            } else {
                UIManager.setLookAndFeel(new MyRavenGraphiteLookAndFeel());
            }
            /** fix font bug start */
            if (SwingUtilities.isEventDispatchThread()) {
                fixFontBug();
            } else {
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            fixFontBug();
                        }
                    });
                } catch (Exception e) {
                    // logger.error(e.getMessage(), e);
                }
            }
            /** fix font bug end */
            if (!isDefaultLookAndFeel(theme)) {
                // Get border color
                try {   GuiUtils.putLookAndFeelColor("darkColor",
                        SubstanceLookAndFeel.getCurrentSkin()
                        .getMainActiveColorScheme().getDarkColor());
//                    GuiUtils.putLookAndFeelColor("borderColor",
//                            SubstanceLookAndFeel.getCurrentSkin().getMainActiveColorScheme().getMidColor());
//                    GuiUtils.putLookAndFeelColor("lightColor",
//                            SubstanceLookAndFeel.getCurrentSkin()
//                                    .getMainActiveColorScheme()
//                                    .getLightColor());
//                    GuiUtils.putLookAndFeelColor("lightBackgroundFillColor",
//                            SubstanceLookAndFeel.getCurrentSkin()
//                                    .getMainActiveColorScheme().getLightColor()
//                                   );
//                 
//                    GuiUtils.putLookAndFeelColor("backgroundFillColor",
//                            SubstanceLookAndFeel.getCurrentSkin()
//                                    .getMainActiveColorScheme()
//                                    .getBackgroundFillColor());
//                    GuiUtils.putLookAndFeelColor("lineColor",
//                            SubstanceLookAndFeel.getCurrentSkin()
//                                    .getMainActiveColorScheme().getLineColor());
//                    GuiUtils.putLookAndFeelColor("selectionForegroundColor",
//                            SubstanceLookAndFeel.getCurrentSkin()
//                                    .getMainActiveColorScheme()
//                                    .getSelectionForegroundColor());
//                    GuiUtils.putLookAndFeelColor("selectionBackgroundColor",
//                            SubstanceLookAndFeel.getCurrentSkin()
//                                    .getMainActiveColorScheme()
//                                    .getSelectionBackgroundColor());
//                    GuiUtils.putLookAndFeelColor("foregroundColor",
//                            SubstanceLookAndFeel.getCurrentSkin()
//                                    .getMainActiveColorScheme()
//                                    .getForegroundColor());
//                    GuiUtils.putLookAndFeelColor("focusRingColor",
//                            SubstanceLookAndFeel.getCurrentSkin()
//                                    .getMainActiveColorScheme()
//                                    .getFocusRingColor());

                } catch (Exception e) {
                    e.printStackTrace();
                }
//
                UIManager.put(LafWidget.ANIMATION_KIND,
                        LafConstants.AnimationKind.NONE);
                UIManager.put(SubstanceLookAndFeel.TABBED_PANE_CONTENT_BORDER_KIND,
                        SubstanceConstants.TabContentPaneBorderKind.SINGLE_FULL);

                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
                initFonts();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isDefaultLookAndFeel(String theme) {
        boolean defaultLF = false;
        for (UIManager.LookAndFeelInfo lf : UIManager.getInstalledLookAndFeels()) {
            // not default lookAndFeel
            if (theme.equals(lf.getName())) {
                defaultLF = true;
            }
        }
        return defaultLF;
    }

    private static void fixFontBug() {
        int sizeOffset = 0;
        Enumeration keys = UIManager.getLookAndFeelDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof Font) {
                Font oldFont = (Font) value;
                // logger.info(oldFont.getName());
                Font newFont = new Font("Dialog", oldFont.getStyle(), oldFont
                        .getSize()
                        + sizeOffset);
                UIManager.put(key, newFont);
            }
        }
    }
    
    public static void initFonts() {
        Font font = new Font("宋体", 0, 12);
        UIManager.put("ToolTip.font", font);
        UIManager.put("PasswordField.font", font);
        UIManager.put("Frame.titleFont", font);
        UIManager.put("RadioButton.font", font);
        UIManager.put("InternalFrame.titleFont", font);
        UIManager.put("TabbedPane.font", font);
        UIManager.put("PopupMenu.font", font);
        UIManager.put("Frame.font", font);
        UIManager.put("Menu.font", font);
        UIManager.put("Panel.font", font);
        UIManager.put("Dialog.font", font);
        UIManager.put("MenuItem.font", font);
        UIManager.put("MenuItem.font", font);
        UIManager.put("TitledBorder.font", font);
        UIManager.put("InternalFrame.font", font);
        UIManager.put("InternalFrame.titleFont", font);
        UIManager.put("Table.font", font);
        UIManager.put("TableHeader.font", font);
        UIManager.put("ToggleButton.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("Label.font", font);
        UIManager.put("FormattedTextField.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("CheckBox.font", font);
        UIManager.put("ComboBox.font", font);
        UIManager.put("OptionPane.font", font);
        UIManager.put("Tree.font", font);
        UIManager.put("ToolBar.font", font);
        UIManager.put("OptionPane.messageFont", font);
        UIManager.put("OptionPane.buttonFont", font);
    }
}
   