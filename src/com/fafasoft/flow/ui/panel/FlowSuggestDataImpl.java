package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.FlowLogDAOImpl;
import com.fafasoft.flow.ui.widget.SuggestTextField;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-6-2
 * Time: 8:57:14
 * To change this template use File | Settings | File Templates.
 */
public class FlowSuggestDataImpl implements SuggestTextField.SuggestData {
    public List<String> getData(String value) {
    	FlowLogDAOImpl flowLogMoudle =DAOContentFactriy.getFlowLogDAO();
		
        return flowLogMoudle.getFlowlog(value, 10);
    }
}
