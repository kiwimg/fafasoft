package com.fafasoft.flow.ui.panel;

import com.fafasoft.flow.dao.DAOContentFactriy;
import com.fafasoft.flow.dao.impl.StockDAOImpl;
import com.fafasoft.flow.ui.widget.SuggestTextField;

import java.util.List;

/**
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>

 *
 * @author liyc
 * @version 1.0
 * @since JDK1.5
 */
public class StockSuggestDataImpl implements SuggestTextField.SuggestData {
    public List<String> getData(String value) {
    	 StockDAOImpl stockMoudle = DAOContentFactriy.getStockDAO();
        return stockMoudle.getStockSuggestByCatNo(value, 10);
    }
}
