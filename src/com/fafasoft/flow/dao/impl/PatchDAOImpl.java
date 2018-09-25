package com.fafasoft.flow.dao.impl;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.dao.RowMapper;
import com.fafasoft.flow.pojo.Option;
import com.fafasoft.flow.pojo.Stock;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class PatchDAOImpl extends BaseDAO{

	public void patch(float oldversion, float newversion) {
		if (oldversion == 1.4f) {
			path1_5(oldversion, newversion);
			path1_53();
			path1_535();
			path1_54();
		} else if (oldversion == 1.5f || oldversion == 1.51f
				|| oldversion == 1.52f) {
			path1_53();
			path1_535();
			path1_54();
		} else if (oldversion == 1.53f || oldversion == 1.531f
				|| oldversion == 1.532f || oldversion == 1.533f
				|| oldversion == 1.534f) {
			path1_531();
			path1_535();
			path1_54();
		} else if (oldversion == 1.535f) {
			path1_54();
		} else if (oldversion == 1.54f || oldversion == 1.541f) {
			path1_541();
		} 
	}
	
	private void path1_541() {
		String sql4 = "ALTER TABLE FLOW_LOG ALTER COLUMN SNUMBER BIGINT";
		Statement stat2 = null;

		Connection conn = super.getConnection();
		try {
			stat2 = conn.createStatement();
			stat2.execute(sql4);
			stat2.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stat2 != null) {
				try {
					stat2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void path1_5(float oldversion, float newversion) {
		String crsql = "CREATE  TABLE SUPPLIERS(\n"
				+ "suppliersno VARCHAR(50) NOT NULL,\n"
				+ "suppliersName VARCHAR(1000),\n" + "contact VARCHAR(100),\n"
				+ "phone VARCHAR(50),\n" + "email VARCHAR(50),\n"
				+ "qq VARCHAR(50),\n" + "address VARCHAR(2000),\n"
				+ "fax VARCHAR(50),\n" + "zipcode VARCHAR(50),\n"
				+ "remarks VARCHAR(2000)\n" + ");";
		String sql1 = "ALTER TABLE FLOW_LOG ALTER COLUMN AMOUNT DOUBLE";
		String sql2 = "ALTER TABLE STOCK ALTER COLUMN AMOUNT DOUBLE";
		String sql3 = "ALTER TABLE STOCK ALTER COLUMN SYAMOUNT DOUBLE";
		String sql4 = "ALTER TABLE STOCK ADD \"SUPPLIERSNAME\"  VARCHAR (1000)";
		String sql5 = "ALTER TABLE DAILYEXPENSES ADD \"MODE\" VARCHAR (20)";
		String sql6 = "UPDATE DAILYEXPENSES SET MODE='expenses'";
		Statement stat2 = null;
		Statement stat1 = null;
		Connection conn = super.getConnection();
		try {
			stat2 = conn.createStatement();
			stat2.execute(sql1);
			stat2.execute(sql2);
			stat2.execute(sql3);
			stat2.execute(sql4);
			stat2.execute(sql5);
			stat2.execute(sql6);
			stat2.execute(crsql);
			stat2.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stat2 != null) {
				try {
					stat2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stat1 != null) {
				try {
					stat1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void path1_54() {
		String crsql = "CREATE TABLE ACCOUNTS (\n"
				+ " AID VARCHAR(50) NOT NULL,  \n"
				+ "TARGETID VARCHAR(40) NOT NULL, \n"
				+ "CATEGORIES  VARCHAR(20) NOT NULL,\n"
				+ "AMOUNT DOUBLE(17),\n" + "STATUS VARCHAR(10),\n"
				+ "NOTE VARCHAR(200),\n" + "ADATE  DATE(8),\n"
				+ "FLAG VARCHAR(10)\n" + ");";
		String sql4 = "ALTER TABLE FLOW_LOG ADD SNUMBER BIGINT ;";
		Statement stat2 = null;

		Connection conn = super.getConnection();
		try {
			stat2 = conn.createStatement();

			stat2.execute(sql4);

			stat2.execute(crsql);
			stat2.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stat2 != null) {
				try {
					stat2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void path1_535() {
		String sql = "SELECT * FROM STOCK WHERE STOCKFLAG ='"
				+ Constant.STCOK_TYPE_GUKETH + "'";
		List list = null;
		try {
			list = queryForList(sql, new RowMapper() {
				public Stock mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					String amount = rs.getString("AMOUNT");
					String syamount = rs.getString("SYAMOUNT");

					Stock stock = new Stock();
					if (syamount != null) {
						stock.setSyamount(Double.parseDouble(syamount));
					}
					if (amount != null) {
						stock.setAmount(Double.parseDouble(amount));
					}

					return stock;

				}
			});
			if (!list.isEmpty()) {
				StockDAOImpl stockDAO = new StockDAOImpl();
				for (int i = 0; i < list.size(); i++) {
					Stock stock = (Stock) list.get(i);
					if(stock != null) {
						Stock stockOld = stockDAO.getStockByNo(stock.getCatno());
						if(stockOld != null) {
							double sys = stock.getSyamount() + stockOld.getSyamount();
							stockOld.setSyamount(sys);
							if (sys > stockOld.getAmount()) {
								stockOld.setAmount(sys);
							}
							stockDAO.updateStock(stockOld);		
						}
						
					}
				
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void path1_531() {
		String sql4 = "ALTER TABLE FLOW_LOG ALTER COLUMN SELLWHO VARCHAR(20)";
		Statement stat2 = null;

		Connection conn = super.getConnection();
		try {
			stat2 = conn.createStatement();

			stat2.execute(sql4);

			stat2.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stat2 != null) {
				try {
					stat2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
	}

	private void path1_53() {

		String sql1 = "ALTER TABLE OPTIONS ADD \"PARENTID\" VARCHAR (30)";
		String sql2 = "ALTER TABLE OPTIONS ADD \"NODEID\" VARCHAR (30)";
		String sql3 = "ALTER TABLE STOCK ADD \"REMARKS\" VARCHAR (500)";
		String sql4 = "ALTER TABLE FLOW_LOG ADD \"SELLWHO\" VARCHAR (20)";
		String sql5 = "ALTER TABLE FLOW_LOG ADD \"STOCKID\" VARCHAR (50)";
		String sql6 = "ALTER TABLE FLOW_LOG ADD \"REMARKS\" VARCHAR (500)";
		// String sql8 = "ALTER TABLE FLOW_LOG ADD \"SNUMBER\" INTEGER";

		Statement stat2 = null;
		Statement stat1 = null;
		Connection conn = super.getConnection();
		try {
			stat2 = conn.createStatement();
			stat2.execute(sql1);
			stat2.execute(sql2);
			stat2.execute(sql3);
			stat2.execute(sql4);
			stat2.execute(sql5);
			stat2.execute(sql6);
			// stat2.execute(sql8);
			OptionDAOImpl optionDAOImpl = new OptionDAOImpl();
			java.util.List list = optionDAOImpl.getOption();
			if (!list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					Option op = (Option) list.get(i);
					String sql7 = "UPDATE OPTIONS SET PARENTID='0' ,NODEID='"
							+ i + 1 + "'  where KEYID ='" + op.getId() + "'";
					stat2.execute(sql7);
				}
			}
			stat2.close();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stat2 != null) {
				try {
					stat2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stat1 != null) {
				try {
					stat1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
