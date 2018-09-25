package com.fafasoft.flow.dao.impl;

import java.sql.SQLException;

public class ClearDataDaoImpl  extends BaseDAO {
 
	public void clearData() throws SQLException{
		String sql1 = "DELETE FROM STOCK";
		String sql2 = "DELETE FROM FLOW_LOG ";
		String sql3 = "DELETE FROM DAILYEXPENSES ";
		String sql4 = "DELETE FROM ACCOUNTS ";
		delete(sql1);
		delete(sql2);
		delete(sql3);
		delete(sql4);
	}
}
