package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.ui.skin.ImageManager;

import org.jdesktop.swingx.JXFrame;


/**
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>

 * @author liyc
 * @version 1.0
 * @since JDK1.5
 */
public abstract class BaseFrame extends JXFrame {
    private static final long serialVersionUID = -3172874700605827735L;
    public BaseFrame() {
      super();
      setIconImage(ImageManager.getImage(Constant.APP_ICON));
    }
}
   