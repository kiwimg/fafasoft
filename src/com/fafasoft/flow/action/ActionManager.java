package com.fafasoft.flow.action;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author liyc
 * @version 1.0
 * @since JDK1.5
 */
public class ActionManager {
    private static Map<Class<? extends Action>, Action> map = new HashMap<Class<? extends Action>, Action>();

    public static Action getAction(Class<? extends Action> clazz) {
        Action action = map.get(clazz);
        if (action == null) {
            try {
                action = clazz.newInstance();
                map.put(clazz, action);
            }catch (Exception e) {
				e.printStackTrace();
			}
        }
        return action;
    }


    public static void setEnabledActions(boolean enable, Class<? extends Action>... actions) {
        for (Class<? extends Action> action : actions) {
            getAction(action).setEnabled(enable);
        }
    }
    
}