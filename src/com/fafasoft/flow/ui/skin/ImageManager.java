package com.fafasoft.flow.ui.skin;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.util.ResouceLoader;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;


/**
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>

 *
 * @author liyc
 * @version 1.0
 * @since JDK1.5
 */
public class ImageManager {
  private static HashMap<String, ImageIcon> imageRegistry = new HashMap<String, ImageIcon>();


	public static ImageIcon getImageIcon(String imageName) {
		return getImageIcon(imageName, null);
	}

	public static ImageIcon getImageIcon(String imageName, String description) {
		ImageIcon getImageIcon = imageRegistry.get(imageName);
		if (getImageIcon == null) {
			getImageIcon = description == null ? new ImageIcon(ResouceLoader
					.getResouce(imageName)) : new ImageIcon(ResouceLoader
					.getResouce(imageName), description);
			imageRegistry.put(imageName, getImageIcon);
		}
		return getImageIcon;
	}

	public static Image getImage(String imageName) {
		return getImageIcon(imageName).getImage();
	}


	public static ImageIcon getImageIconByShortName(String imageName) {

		return getImageIcon(Constant.ICON_DIR  + imageName);
	}

}
   