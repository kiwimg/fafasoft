package com.fafasoft.flow.ui.panel;
import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.CustomDAOImpl;
import com.fafasoft.flow.pojo.Custom;
import com.fafasoft.flow.ui.widget.SuggestTextField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-6-16
 * Time: 21:19:58
 * To change this template use File | Settings | File Templates.
 */
public class CustomSuggestDataImpl implements SuggestTextField.SuggestData {
    public List<String> getData(String value) {
    	  CustomDAOImpl customMoudle =  DAOContentFactriy.getCustomDAO();
        //
        List list = customMoudle.getCustoms(value, 10);
        if (list != null) {
            ArrayList s = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                Custom custom  =(Custom)list.get(i);
                s.add(custom.toString());
            }
            list = s;
        }

        return list;
    }
}