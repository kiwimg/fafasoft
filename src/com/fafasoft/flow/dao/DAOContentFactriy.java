package com.fafasoft.flow.dao;

import com.fafasoft.flow.dao.impl.AccountsDaoImpl;
import com.fafasoft.flow.dao.impl.ClearDataDaoImpl;
import com.fafasoft.flow.dao.impl.ConfigDAOImpl;
import com.fafasoft.flow.dao.impl.CustomDAOImpl;
import com.fafasoft.flow.dao.impl.CustomtTypeDAOImpl;
import com.fafasoft.flow.dao.impl.DailyExpensesDAOImpl;
import com.fafasoft.flow.dao.impl.FlowLogDAOImpl;
import com.fafasoft.flow.dao.impl.OptionDAOImpl;
import com.fafasoft.flow.dao.impl.PatchDAOImpl;
import com.fafasoft.flow.dao.impl.StockDAOImpl;
import com.fafasoft.flow.dao.impl.SuppliersDAOImpl;
import com.fafasoft.flow.dao.impl.UserDAOImpl;
import com.fafasoft.flow.dao.impl.UserRightDAOImpl;

public class DAOContentFactriy {
    public static StockDAOImpl getStockDAO() {

        return new StockDAOImpl();
    }
    public static FlowLogDAOImpl getFlowLogDAO() {

        return new FlowLogDAOImpl();
    }
    public static AccountsDaoImpl getAccountsDao() {

        return new AccountsDaoImpl();
    }

    public static ConfigDAOImpl getConfigDAO() {

        return new ConfigDAOImpl();
    }

    public static OptionDAOImpl getOptionDAO() {

        return new OptionDAOImpl();
    }

    public static PatchDAOImpl getPatchDAO() {

        return new PatchDAOImpl();
    }

    public static DailyExpensesDAOImpl getDailyExpensesDAO() {

        return new DailyExpensesDAOImpl();
    }

    public static CustomDAOImpl getCustomDAO() {

        return new CustomDAOImpl();
    }

    public static CustomtTypeDAOImpl getCustomtTypeDAO() {

        return new CustomtTypeDAOImpl();
    }

    public static UserDAOImpl getUserDAO() {

        return new UserDAOImpl();
    }

    public static UserRightDAOImpl getUserRightDAO() {

        return new UserRightDAOImpl();
    } 
    
    public static SuppliersDAOImpl getSuppliersDAO() {

        return new SuppliersDAOImpl();
    }
    
    public static ClearDataDaoImpl getClearDataDao() {

        return new ClearDataDaoImpl();
    }
}
