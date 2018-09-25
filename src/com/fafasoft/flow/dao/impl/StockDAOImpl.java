package com.fafasoft.flow.dao.impl;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.dao.RowMapper;
import com.fafasoft.flow.pojo.Stock;
import com.fafasoft.flow.pojo.StockTj;
import com.fafasoft.flow.util.DateHelper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StockDAOImpl extends BaseDAO{

	public boolean deleteById(String stockid) {
		String sql = "DELETE FROM STOCK WHERE ID='" + stockid + "'";
		boolean isin = true;
		try {
			delete(sql);
		} catch (SQLException e) {
			isin = false;
			e.printStackTrace();
		}
		return isin;
	}

	/**
	 * 库存统计
	 * 
	 * @param stockType
	 * @param date
	 * @param dateTo
	 * @return
	 */
	public Object[] sumStocksByType(String stockType, String date,
			String dateTo, String catno) {
		Connection conn = super.getConnection(); // start
		StringBuffer jinhuosql = new StringBuffer();
		jinhuosql
				.append("SELECT SUM (AMOUNT),SUM(AMOUNT*COSTPRICE) FROM STOCK WHERE  STOCKFLAG ='");
		jinhuosql.append(Constant.STCOK_TYPE_JINHUO);
		jinhuosql.append("'");
		if (catno != null && catno.trim().length() > 0) {

			jinhuosql.append(" AND  CATNO='");
			jinhuosql.append(catno);
			jinhuosql.append("'");
		}
		if (stockType != null && stockType.trim().length() > 0) {

			jinhuosql.append(" AND  STOCKTYPE='");
			jinhuosql.append(stockType);
			jinhuosql.append("'");
		}
		if (date != null && date.length() > 0 && dateTo != null
				&& dateTo.length() > 0) {
			jinhuosql.append(" AND DATETIMED between '");
			jinhuosql.append(date);
			jinhuosql.append("' AND ");
			jinhuosql.append("'");
			jinhuosql.append(dateTo);
			jinhuosql.append("' ");
		}

		StringBuffer sysql = new StringBuffer();
		sysql
				.append("SELECT SUM (SYAMOUNT),SUM(SYAMOUNT*COSTPRICE)  FROM STOCK WHERE  STOCKFLAG ='");
		sysql.append(Constant.STCOK_TYPE_JINHUO);
		sysql.append("'");
		if (catno != null && catno.trim().length() > 0) {

			sysql.append(" AND  CATNO='");
			sysql.append(catno);
			sysql.append("'");
		}
		if (stockType != null && stockType.trim().length() > 0) {

			sysql.append(" AND  STOCKTYPE='");
			sysql.append(stockType);
			sysql.append("'");
		}
		if (date != null && date.length() > 0 && dateTo != null
				&& dateTo.length() > 0) {
			sysql.append(" AND DATETIMED between '");
			sysql.append(date);
			sysql.append("' AND ");
			sysql.append("'");
			sysql.append(dateTo);
			sysql.append("' ");
		}

		StringBuffer gukeThql = new StringBuffer();
		gukeThql.append("SELECT SUM (AMOUNT),SUM(AMOUNT*COSTPRICE)  FROM STOCK WHERE  STOCKFLAG ='");
		gukeThql.append(Constant.STCOK_TYPE_GUKETH);
		gukeThql.append("'");
		if (catno != null && catno.trim().length() > 0) {

			gukeThql.append(" AND  CATNO='");
			gukeThql.append(catno);
			gukeThql.append("'");
		}
		if (stockType != null && stockType.trim().length() > 0) {

			gukeThql.append(" AND  STOCKTYPE='");
			gukeThql.append(stockType);
			gukeThql.append("'");
		}
		if (date != null && date.length() > 0 && dateTo != null
				&& dateTo.length() > 0) {
			gukeThql.append(" AND DATETIMED between '");
			gukeThql.append(date);
			gukeThql.append("' AND ");
			gukeThql.append("'");
			gukeThql.append(dateTo);
			gukeThql.append("' ");
		}

		StringBuffer caigouThql = new StringBuffer();
		caigouThql
				.append("SELECT SUM (AMOUNT),SUM(AMOUNT*COSTPRICE)  FROM STOCK WHERE  STOCKFLAG ='");
		caigouThql.append(Constant.STCOK_TYPE_GUKETH);
		caigouThql.append("'");
		if (catno != null && catno.trim().length() > 0) {

			caigouThql.append(" AND  CATNO='");
			caigouThql.append(catno);
			caigouThql.append("'");
		}
		if (date != null && date.length() > 0 && dateTo != null
				&& dateTo.length() > 0) {
			caigouThql.append("AND DATETIMED between '");
			caigouThql.append(date);
			caigouThql.append("' AND ");
			caigouThql.append("'");
			caigouThql.append(dateTo);
			caigouThql.append("' ");
		}

		Object[] object = new Object[8];
		Statement stat = null;
		ResultSet rs = null;
		List<String> list = null;
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery(String.valueOf(jinhuosql));

			// list = new ArrayList<String>();
			// 进货
			if (rs.next()) {
				object[0] = rs.getDouble(1);
				object[1] = rs.getDouble(2);
			} else {
				object[0] = 0;
				object[1] = 0;
			}

			close(rs);
			// 顾客退货
			rs = stat.executeQuery(String.valueOf(gukeThql));

			if (rs.next()) {
				object[2] = rs.getDouble(1);
				object[3] = rs.getDouble(2);
			} else {
				object[2] = 0;
				object[3] = 0;
			}

			close(rs);
			// 采购退货
			rs = stat.executeQuery(String.valueOf(caigouThql));
			if (rs.next()) {
				object[4] = rs.getDouble(1);
				object[5] = rs.getDouble(2);
			} else {
				object[4] = 0;
				object[5] = 0;
			}
			close(rs);
			// 剩余退货
			rs = stat.executeQuery(String.valueOf(sysql));
			if (rs.next()) {
				object[6] = rs.getDouble(1);
				object[7] = rs.getDouble(2);
			} else {
				object[6] = 0;
				object[7] = 0;
			}
			// rs.getDouble(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stat, rs, conn);
		}
		return object;
	}

	public int getStockTjListSize( String stockType, final String date,
			final String dateTo,  String catno) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) FROM  (");
		sql.append("SELECT CATNO FROM  STOCK WHERE 1=1 ");
		if (stockType != null && stockType.length() > 0) {
			sql.append("AND STOCKTYPE='");
			sql.append(stockType);
			sql.append("'");
		}
		if (catno != null && catno.length() > 0) {
			sql.append("AND CATNO='");
			sql.append(catno);
			sql.append("'");
		}
		if (date != null && date.length() > 0 && dateTo != null
				&& dateTo.length() > 0) {
			sql.append(" AND DATETIMED between '");
			sql.append(date);
			sql.append("' AND ");
			sql.append("'");
			sql.append(dateTo);
			sql.append("' ");
		}
		sql.append(" GROUP BY  COLOR, SPECIF ,CATNO,STOCKTYPE ");
		sql.append(")");
		int rows = 0;
	
		try {
			rows = getTotalRow(String.valueOf(sql));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows;
	}
	
	public List getStockTjList( String stockType, final String date,
			final String dateTo,  String catno,int start, int max) {
		StringBuffer sql = new StringBuffer();
		sql
				.append("SELECT STOCKTYPE,CATNO ,COLOR,SPECIF, SUM(AMOUNT ) as AMOUNT  ,SUM(AMOUNT*COSTPRICE) as COSTPRICE,SUM(SYAMOUNT ) as SYAMOUNT  ,SUM(SYAMOUNT *COSTPRICE) as SYCOSTPRICE  FROM  STOCK WHERE 1=1 ");
		if (stockType != null && stockType.length() > 0) {
			sql.append("AND STOCKTYPE='");
			sql.append(stockType);
			sql.append("'");
		}
		if (catno != null && catno.length() > 0) {
			sql.append("AND CATNO='");
			sql.append(catno);
			sql.append("'");
		}
		if (date != null && date.length() > 0 && dateTo != null
				&& dateTo.length() > 0) {
			sql.append(" AND DATETIMED between '");
			sql.append(date);
			sql.append("' AND ");
			sql.append("'");
			sql.append(dateTo);
			sql.append("' ");
		}
		sql.append("AND STOCKFLAG='");
		sql.append(Constant.STCOK_TYPE_JINHUO);
		sql.append("'");
		sql.append(" GROUP BY  COLOR, SPECIF,STOCKTYPE ,CATNO ORDER BY STOCKTYPE DESC");
		if(start !=-1 && max !=-1 ) {
			sql.append(" LIMIT ");
			sql.append(max);
			sql.append("  OFFSET ");
			sql.append(start);
		}
		
	
		List list = null;
		try {
		
			list = queryForList(String.valueOf(sql), new RowMapper() {
				public StockTj mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					StockTj stock = new StockTj();
					String catno = rs.getString("CATNO");
					String stocktype = rs.getString("STOCKTYPE");
					String amount = rs.getString("AMOUNT");
					String syamount = rs.getString("SYAMOUNT");
					String costprice = rs.getString("COSTPRICE");
					String sycostprice = rs.getString("SYCOSTPRICE");
					stock.setCatno(catno);
			
					String COLOR = rs.getString("COLOR");
					if (amount != null) {
						stock.setAmount(Double.parseDouble(amount));
					}
					stock.setStocktype(stocktype);
					if (costprice != null) {
						stock.setCostprice(new BigDecimal(costprice));
					}
					if (syamount != null) {
						stock.setSyamount(Double.parseDouble(syamount));
					}
					if (sycostprice != null) {
						stock.setSycostprice(new BigDecimal(sycostprice));
					}
					String SPECIF = rs.getString("SPECIF");
					if (COLOR != null) {
						stock.setColor(COLOR);
					}
					if (SPECIF != null) {
						stock.setSpecif(SPECIF);
					}
					
					stock = getStockTjList(stocktype, date, dateTo, catno,
							COLOR, SPECIF, Constant.STCOK_TYPE_CAIGOUTH, stock);
					stock = getStockTjList(stocktype, date, dateTo, catno,
							COLOR, SPECIF, Constant.STCOK_TYPE_GUKETH, stock);
					return stock;
				}
			});
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	//
	private StockTj getStockTjList(String stockType, String date,
			String dateTo, String catno, String color, String specif,
			String flag, StockTj stock) {
		Connection conn = super.getConnection(); // start
		StringBuffer sql = new StringBuffer();
		sql
				.append("SELECT SUM(AMOUNT) as AMOUNT ,SUM(AMOUNT*COSTPRICE) as COSTPRICE FROM  STOCK WHERE 1=1 ");
		sql.append("AND STOCKTYPE='");
		sql.append(stockType);
		sql.append("'");
		sql.append(" AND CATNO='");
		sql.append(catno);
		sql.append("'");
		sql.append(" AND COLOR='");
		sql.append(color);
		sql.append("'");
		sql.append(" AND SPECIF='");
		sql.append(specif);
		sql.append("'");

		if (date != null && date.length() > 0 && dateTo != null
				&& dateTo.length() > 0) {
			sql.append("AND DATETIMED between '");
			sql.append(date);
			sql.append("' AND ");
			sql.append("'");
			sql.append(dateTo);
			sql.append("' ");
		}
		sql.append("AND STOCKFLAG='");
		sql.append(flag);
		sql.append("'");
		sql.append(" GROUP BY STOCKTYPE, COLOR, SPECIF ,CATNO");

		Statement stat = null;
		ResultSet rs = null;
		List<String> list = null;
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery(String.valueOf(sql));
			if (flag.equals(Constant.STCOK_TYPE_CAIGOUTH)) {
				// 进货
				double cgThamount = 0;
				BigDecimal costprice = new BigDecimal(0);
				if (rs.next()) {
					cgThamount = rs.getDouble(1);
					costprice = rs.getBigDecimal(2);
				}
				stock.setCgThamount(cgThamount);
				stock.setCgThcostprice(costprice);
			} else if (flag.equals(Constant.STCOK_TYPE_GUKETH)) {
				// 进货
				double cgThamount = 0;
				BigDecimal costprice = new BigDecimal(0);
				if (rs.next()) {
					cgThamount = rs.getDouble(1);
					costprice = rs.getBigDecimal(2);
				}
				stock.setGkThamount(cgThamount);
				stock.setGkThcostprice(costprice);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stat, rs, conn);
		}
		return stock;

	}

	public List getStockTypes() {
		Connection conn = super.getConnection(); // start
		String sql = "SELECT DISTINCT STOCKTYPE FROM STOCK ";
		Statement stat = null;
		ResultSet rs = null;
		List<String> list = null;
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			list = new ArrayList<String>();
			while (rs.next()) {
				String catnoname = rs.getString("STOCKTYPE");
				list.add(catnoname);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stat, rs, conn);
		}

		return list;
	}

	public boolean updateStock(Stock stock) {
		boolean isin = false;
		String sql = "UPDATE STOCK SET CATNO='" + stock.getCatno() + "',"
				+ "AMOUNT='" + stock.getAmount() + "'," + "COSTPRICE ='"
				+ stock.getCostprice() + "'," + "SELLPRICE ='"
				+ stock.getSellprice() + "'," + "DATETIMED ='"
				+ stock.getDate() + "'," + "COSTTOTAL ='" + stock.getTotal()
				+ "'," + "SYAMOUNT ='" + stock.getSyamount() + "',"
				+ "STOCKTYPE ='" + stock.getType() + "'," + "STOCKFLAG ='"
				+ stock.getStockFlag() + "'," + "COLOR ='" + stock.getColor()
				+ "'," + "SPECIF ='" + stock.getSpecif() + "'," + "RECORD ='"
				+ stock.getRecorddate() + "'," + "SUPPLIERSNAME ='"
				+ stock.getSuppliers() + "'" + " WHERE ID='" + stock.getId()
				+ "'";
		try {
			update(sql,true);
		} catch (SQLException e) {
			isin = false;
			e.printStackTrace();
		}
		return isin;
	}

	// 更新库存
	public boolean updateSyAmount(String stockid, double ssAmount, String type) {
		Stock stock = getStockByID(stockid);
		if (stock != null) {
			double syAmount = 0;
			String sql = null;
			if ("+".equals(type)) {
				syAmount = stock.getSyamount() + ssAmount;
				if (syAmount > stock.getAmount()) {
					double am = stock.getAmount() + ssAmount;
					// AMOUNT
					sql = "UPDATE STOCK SET SYAMOUNT ='" + syAmount
							+ "', AMOUNT='" + am + "' WHERE ID='"
							+ stock.getId() + "'";
				} else {
					sql = "UPDATE STOCK SET SYAMOUNT ='" + syAmount
							+ "' WHERE ID='" + stock.getId() + "'";
				}
			} else {
				if (stock.getSyamount() > 0) {
					syAmount = stock.getSyamount() - ssAmount;
					if (syAmount < 0) {
						Stock stockold=	getStockByCatNo(stock.getCatno(),stock.getId(),stock.getType());
						if(stockold != null) {
							updateSyAmount(stockold.getId(),syAmount*-1,"-");
						}
						syAmount = 0;
					}
				}
				sql = "UPDATE STOCK SET SYAMOUNT ='" + syAmount
						+ "' WHERE ID='" + stock.getId() + "'";
			}
			boolean isin = false;
			try {
				update(sql,true);
				isin = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return isin;
		}
		return true;
	}
    private Stock getStockByCatNo(String catno, String id,String type){
    	catno = sqlStr(catno);
		String sql = "SELECT * FROM STOCK WHERE CATNO='" + catno
				+ "' AND STOCKFLAG ='" + Constant.STCOK_TYPE_JINHUO + "' AND SYAMOUNT >0 AND ID !='"+id+"' AND STOCKTYPE='"+type+"'";
		Stock stock = null;
		try {
			stock = (Stock) queryForObject(sql, new StockRowMapper());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stock;
    }
	public List<Stock> getStock(String catno, String type, String color,
			String specif) {
		return getStock(catno, type, color, specif, null);
	}

	public List<Stock> getStock(String catno, String type, String color,
			String specif, String date) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM STOCK WHERE SYAMOUNT >0 ");
		if (catno != null && catno.trim().length() > 0) {
			sql.append(" and CATNO='");
			sql.append(catno);
			sql.append("'");
		}
		 if (type != null && type.trim().length() > 0) {
			sql.append(" and STOCKTYPE='");
			sql.append(type);
			sql.append("'");
		}
		if (color != null && color.trim().length() > 0) {
			sql.append(" and COLOR='");
			sql.append(color);
			sql.append("'");
		}
		if (specif != null && specif.trim().length() > 0) {
			sql.append(" and SPECIF='");
			sql.append(specif);
			sql.append("'");
		}
		if (date != null && date.trim().length() > 0) {
			sql.append(" and DATETIMED='");
			sql.append(date);
			sql.append("'");
		}
		sql.append(" and STOCKFLAG !='");
		sql.append(Constant.STCOK_TYPE_CAIGOUTH);
		sql.append("'");
		List<Stock> list = null;

		try {
			list = queryForList(String.valueOf(sql), new StockRowMapper());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 库存
	 */
	public List getStock(String type) {
		String sql = "SELECT * FROM STOCK WHERE STOCKFLAG ='" + type
				+ "' AND SYAMOUNT > 0";
		List list = null;
		try {
			list = queryForList(sql, new StockRowMapper());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List getStock(int start, int max) {
		String sql = "SELECT * FROM STOCK WHERE STOCKFLAG ='"
				+ Constant.STCOK_TYPE_JINHUO + "' LIMIT  "
				+ max + " OFFSET " + start;
		List list = null;
		try {
			
			list = queryForList(sql, new StockRowMapper());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Stock getStockByNo(String catno) {
		catno = sqlStr(catno);
		String sql = "SELECT * FROM STOCK WHERE CATNO='" + catno
				+ "' AND STOCKFLAG ='" + Constant.STCOK_TYPE_JINHUO + "'";
		Stock stock = null;
		try {
			stock = (Stock) queryForObject(sql, new StockRowMapper());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stock;
	}

	public Stock getStockByID(String id) {
		String sql = "SELECT * FROM STOCK WHERE ID='" + id + "';";
		Stock stock = null;
		try {
			stock = (Stock) queryForObject(sql, new StockRowMapper());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stock;
	}

	// 获取最时间最长的库存
	public Stock getLastStockByNo(String catno) {
		catno = sqlStr(catno);
		String sql = "SELECT * FROM STOCK WHERE CATNO='" + catno
				+ "' AND SYAMOUNT >0 AND STOCKFLAG ='"
				+ Constant.STCOK_TYPE_JINHUO + "' ORDER BY  DATETIMED ASC ;";
		Stock stock = null;
		try {
			stock = (Stock) queryForObject(sql, new StockRowMapper());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stock;
	}

	public List<String> getStockSuggestByCatNo(String catno, int max) {
		catno = sqlStr(catno);
		Connection conn = super.getConnection(); // start
		catno = sqlStr(catno);
		String sql = "SELECT * FROM STOCK WHERE STOCKFLAG ='"
				+ Constant.STCOK_TYPE_JINHUO + "' AND CATNO LIKE '" + catno
				+ "%' LIMIT " + max;
		Statement stat = null;
		ResultSet rs = null;
		List<String> list = null;
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			list = new ArrayList<String>();
			while (rs.next()) {
				String catnoname = rs.getString("CATNO");
				list.add(catnoname);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stat, rs, conn);
		}
		return list;
	}

	public List<Stock> getStockSuggest(String key, String type, int max) {
		key = sqlStr(key);
		String sql = sql = "SELECT CATNO,STOCKNAME FROM STOCK WHERE STOCKFLAG ='"
				+ Constant.STCOK_TYPE_JINHUO
				+ "' AND STOCKTYPE ='"
				+ type
				+ "' AND CATNO ='"
				+ key
				+ "' GROUP BY CATNO,STOCKNAME LIMIT " + max;

		List<Stock> list = null;
		try {
			list = queryForList(sql, new RowMapper() {
				public Stock mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					String catno = rs.getString("CATNO");
					String STOCKNAME = rs.getString("STOCKNAME");
					Stock stock = new Stock();
					if (catno != null) {
						stock.setCatno(catno);
					}
					if (STOCKNAME != null) {
						stock.setStockname(STOCKNAME);
					}
					return stock;
				}
			});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Stock> getStockSuggest(String key, boolean is, int max) {
		key = sqlStr(key);
		StringBuffer sqltemp = new StringBuffer();
		sqltemp.append("SELECT * FROM STOCK WHERE ");
		sqltemp.append("(STOCKFLAG ='");
		sqltemp.append(Constant.STCOK_TYPE_JINHUO);
		sqltemp.append("'");
		if (!is) {
			sqltemp.append(" AND ");
			sqltemp.append("SYAMOUNT > 0 ");
		}
		sqltemp.append(") AND (");
		sqltemp.append(" CATNO LIKE '");
		sqltemp.append(key);
		sqltemp.append("%' or STOCKTYPE LIKE '");
		sqltemp.append(key);
		sqltemp.append("%' or STOCKNAME LIKE '");
		sqltemp.append(key);
		sqltemp.append("%' ) LIMIT ");
		sqltemp.append(max);
		List<Stock> list = null;
		try {
			list = queryForList(String.valueOf(sqltemp), new StockRowMapper());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List getStockByType(String type, int max) {
		String sql = "SELECT * FROM STOCK  WHERE STOCKFLAG ='"
				+ Constant.STCOK_TYPE_JINHUO + "' AND STOCKTYPE = '" + type
				+ "' LIMIT " + max;
		List list = null;
		try {
			list = queryForList(sql, new StockRowMapper());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List getStockByNoAndType(String catno, String type) {

		String sql = "SELECT * FROM STOCK  WHERE STOCKFLAG ='"
				+ Constant.STCOK_TYPE_JINHUO + "' AND CATNO='" + catno
				+ "' AND STOCKTYPE = '" + type + "' LIMIT 20";
		List list = null;
		try {
			list = queryForList(sql, new StockRowMapper());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public double sumStockSyAmount(String catno, String cost, String type,
			String date, String dateTo) {

		StringBuffer sqltemp = new StringBuffer();
		sqltemp
				.append("SELECT SUM (SYAMOUNT) FROM STOCK WHERE 1=1 AND STOCKFLAG ='"
						+ Constant.STCOK_TYPE_JINHUO + "' ");
		if (catno != null) {
			sqltemp.append(" and CATNO='");
			sqltemp.append(catno);
			sqltemp.append("'");
		}
		if (cost != null) {
			sqltemp.append(" and COSTPRICE='");
			sqltemp.append(cost);
			sqltemp.append("'");
		}
		if (type != null) {
			sqltemp.append(" and STOCKTYPE='");
			sqltemp.append(type);
			sqltemp.append("'");
		}
		if (date != null) {
			sqltemp.append(" and DATETIMED>='");
			sqltemp.append(date);
			sqltemp.append("'");
		}
		if (dateTo != null) {
			sqltemp.append(" and DATETIMED<='");
			sqltemp.append(dateTo);
			sqltemp.append("'");
		}
		sqltemp.append(" and  SYAMOUNT > 0");
		double r = 0;
		try {
			r = getTotalRowForDouble(String.valueOf(sqltemp));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	/**
	 * 统计顾客退货
	 * 
	 * @param catno
	 * @param type
	 * @return
	 */
	public double sumStockGuKeTuiHuoAmount(String catno, String type) {
		StringBuffer sqltemp = new StringBuffer();
		sqltemp.append("SELECT SUM (AMOUNT) FROM STOCK WHERE  CATNO='").append(
				catno).append("' AND STOCKTYPE='").append(type).append(
				"' AND STOCKFLAG='").append(Constant.STCOK_TYPE_GUKETH).append(
				"' ");

		double r = 0;
		try {
			r = getTotalRowForDouble(String.valueOf(sqltemp));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	/**
	 * 统计采购进货数量总计
	 * 
	 * @param catno
	 * @return
	 */
	public double sumStockAmount(String catno) {
		String sql = "SELECT SUM(AMOUNT)  FROM STOCK  WHERE STOCKFLAG ='"
				+ Constant.STCOK_TYPE_JINHUO + "' AND CATNO ='" + catno + "'";

		double r = 0;
		Connection conn = super.getConnection();
		Statement stat = null;
		ResultSet rs = null;
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			rs.next();
			r = rs.getDouble(1);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stat, rs, conn);
		}
		return r;

	}

	/**
	 * 根据货号统计采购进货剩余数量总计
	 * 
	 * @param catno
	 * @return
	 */
	public double sumStockSyAmount(String catno) {
		String sql = "SELECT SUM(SYAMOUNT)  FROM STOCK  WHERE STOCKFLAG ='"
				+ Constant.STCOK_TYPE_JINHUO + "' AND CATNO ='" + catno + "'";
		double r = 0;
		Connection conn = super.getConnection();
		Statement stat = null;
		ResultSet rs = null;
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			rs.next();
			r = rs.getDouble(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stat, rs, conn);
		}
		return r;
	}

	public boolean add(Stock stock) {
		boolean isin = false;
		if (stock.getId() == null) {
			String id = String.valueOf(UUID.randomUUID().toString().replaceAll(
					"-", ""));
			stock.setId(id);
		}
		String sql = "INSERT INTO STOCK VALUES ('" + stock.getId() + "'," + "'"
				+ stock.getCatno() + "'," + "'" + stock.getAmount() + "',"
				+ "'" + stock.getSyamount() + "'," + "'" + stock.getCostprice()
				+ "'," + "'" + stock.getSellprice() + "'," + "'"
				+ stock.getType() + "'," + "'" + stock.getDate() + "'," + "'"
				+ stock.getTotal() + "'," + "'" + stock.getStockname() + "',"
				+ "'" + stock.getStockFlag() + "'," + "'" + stock.getColor()
				+ "'," + "'" + stock.getSpecif() + "'," + "'"
				+ stock.getRecorddate() + "'," + "'" + stock.getSuppliers()
				+ "'," + "'" + stock.getRemarks() + "'" + ");";

		try {
			insert(sql,true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isin;

	}

	public List getStockByCatNo(String catno, int start, int max) {
		String sql = "SELECT * FROM STOCK WHERE STOCKFLAG ='"
				+ Constant.STCOK_TYPE_JINHUO + "' AND CATNO ='" + catno
				+ "' LIMIT " + max + " OFFSET " + start;

		List list = null;
		try {
			list = queryForList(sql, new StockRowMapper());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int getStockByParmSize(String catno, String cost, String type,
			String kcType, String date, String dateTo) {
		StringBuffer sqltemp = new StringBuffer();
		sqltemp.append("SELECT COUNT(*) FROM STOCK WHERE 1=1 AND STOCKFLAG ='")
				.append(kcType).append("'");

		if (catno != null && catno.length()>0) {
			sqltemp.append(" and CATNO='");
			sqltemp.append(catno);
			sqltemp.append("'");
		}
		if (cost != null && cost.trim().length()>0) {
			sqltemp.append(" and COSTPRICE='");
			sqltemp.append(cost);
			sqltemp.append("'");
		}
		if (type != null  && type.trim().length()>0) {
			sqltemp.append(" and STOCKTYPE='");
			sqltemp.append(type);
			sqltemp.append("'");
		}
		if (date != null && date.trim().length()>0) {
			sqltemp.append(" and DATETIMED>='");
			sqltemp.append(date);
			sqltemp.append("'");
		}
		if (dateTo != null  && dateTo.trim().length()>0) {
			sqltemp.append(" and DATETIMED<='");
			sqltemp.append(dateTo);
			sqltemp.append("'");
		}
		int list = 0;

		try {
			list = getTotalRow(String.valueOf(sqltemp));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List getStockByParm(String catno, String cost, String type,
			String kcType, String date, String dateTo, int start, int max) {

		StringBuffer sqltemp = new StringBuffer();
		sqltemp.append("SELECT * FROM STOCK WHERE 1=1 ");
		sqltemp.append(" and STOCKFLAG='");
		sqltemp.append(kcType);
		sqltemp.append("'");
		if (catno != null) {
			sqltemp.append(" and CATNO='");
			sqltemp.append(catno);
			sqltemp.append("'");
		}
		if (cost != null) {
			sqltemp.append(" and COSTPRICE='");
			sqltemp.append(cost);
			sqltemp.append("'");
		}
		if (type != null) {
			sqltemp.append(" and STOCKTYPE='");
			sqltemp.append(type);
			sqltemp.append("'");
		}
		if (date != null) {
			sqltemp.append(" and DATETIMED>='");
			sqltemp.append(date);
			sqltemp.append("'");
		}

		if (dateTo != null) {
			sqltemp.append(" and DATETIMED<='");
			sqltemp.append(dateTo);
			sqltemp.append("'");
		}

		String sql = String.valueOf(sqltemp.append(
				" ORDER BY DATETIMED DESC LIMIT ").append(max).append(
				" OFFSET ").append(start));
		List list = null;
		try {
			list = queryForList(sql, new StockRowMapper());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 取得今天的采购列表
	 */
	public List getStockByTaday() {
		StringBuffer sqltemp = new StringBuffer();
		sqltemp.append("SELECT * FROM STOCK WHERE DATETIMED ='");
		sqltemp.append(DateHelper.getNowDateTime());
		sqltemp.append("'");
		sqltemp.append(" and STOCKFLAG='");
		sqltemp.append(Constant.STCOK_TYPE_JINHUO);
		sqltemp.append("'");

		List list = null;
		try {
			list = queryForList(String.valueOf(sqltemp), new StockRowMapper());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List getCaiGThStockByTaday() {

		StringBuffer sqltemp = new StringBuffer();
		sqltemp.append("SELECT * FROM STOCK WHERE DATETIMED ='");
		sqltemp.append(DateHelper.getNowDateTime());
		sqltemp.append("'");
		sqltemp.append(" and STOCKFLAG='");
		sqltemp.append(Constant.STCOK_TYPE_CAIGOUTH);
		sqltemp.append("'");

		List list = null;
		try {
			list = queryForList(String.valueOf(sqltemp), new StockRowMapper());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int getStockAlarmSize(String alarmType, double num) {

		String sql = "SELECT  COUNT(*)  from (SELECT CATNO,sum(SYAMOUNT)  SYAMOUNT ,STOCKNAME,COSTPRICE ,STOCKTYPE, DATETIMED ,RECORD ,STOCKFLAG ,COLOR ,SPECIF FROM STOCK   GROUP BY CATNO,STOCKNAME,STOCKTYPE,COSTPRICE,DATETIMED,COLOR,SPECIF,RECORD) where  STOCKFLAG ='"
				+ Constant.STCOK_TYPE_JINHUO
				+ "' AND SYAMOUNT "
				+ alarmType
				+ num;
		int totalRow = 0;

		try {
			totalRow = getTotalRow(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalRow;
	}

	public List getStockAlarm(String alarmType, double num, int start, int max) {
		String sql = "SELECT *  from (SELECT CATNO,sum(SYAMOUNT)  SYAMOUNT,STOCKNAME,COSTPRICE ,STOCKTYPE, DATETIMED ,RECORD ,STOCKFLAG ,COLOR ,SPECIF,SUPPLIERSNAME FROM STOCK   GROUP BY CATNO,STOCKNAME,SUPPLIERSNAME,STOCKTYPE,COSTPRICE,DATETIMED,COLOR,SPECIF,RECORD) where  STOCKFLAG ='"
				+ Constant.STCOK_TYPE_JINHUO
				+ "' AND SYAMOUNT "
				+ alarmType
				+ num
				+ " ORDER BY DATETIMED DESC LIMIT "
				+ max
				+ " OFFSET "
				+ start;
		List list = null;
		try {
			list = queryForList(sql, new StockRowAlarmMapper());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	private static class StockRowMapper implements RowMapper {
		public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {
			String id = rs.getString("ID");
			String catno = rs.getString("CATNO");
			String amount = rs.getString("AMOUNT");
			String syamount = rs.getString("SYAMOUNT");
			String costprice = rs.getString("COSTPRICE");
			String sellprice = rs.getString("SELLPRICE");
			String stockytpe = rs.getString("STOCKTYPE");
			String datetime = rs.getString("DATETIMED");
			String costtotal = rs.getString("COSTTOTAL");
			String STOCKNAME = rs.getString("STOCKNAME");

			Stock stock = new Stock();
			if (id != null) {
				stock.setId(id);
			}
			if (catno != null) {
				stock.setCatno(catno);
			}
			if (amount != null) {
				stock.setAmount(Double.parseDouble(amount));
			}
			if (syamount != null) {
				stock.setSyamount(Double.parseDouble(syamount));
			}
			if (costprice != null) {
				stock.setCostprice(new BigDecimal(costprice));
			}
			if (sellprice != null) {
				stock.setSellprice(new BigDecimal(sellprice));

			}
			if (stockytpe != null) {
				stock.setType(stockytpe);
			}
			if (datetime != null) {
				stock.setDate(datetime);
			}
			if (costtotal != null) {
				stock.setTotal(new BigDecimal(costtotal));
			}
			if (STOCKNAME != null) {
				stock.setStockname(STOCKNAME);
			}
			try {
				String COLOR = rs.getString("COLOR");
				String SPECIF = rs.getString("SPECIF");
				String STOCKFLAG = rs.getString("STOCKFLAG");
				String RECORD = rs.getString("RECORD");

				String SUPPLIERSNAME = rs.getString("SUPPLIERSNAME");

				String REMARKS = rs.getString("REMARKS");
				if (RECORD != null) {
					stock.setRecorddate(RECORD);
				}
				if (STOCKFLAG != null) {
					stock.setStockFlag(STOCKFLAG);
				}
				if (COLOR != null) {
					stock.setColor(COLOR);
				}
				if (SPECIF != null) {
					stock.setSpecif(SPECIF);
				}
				if (RECORD != null) {
					stock.setRecorddate(RECORD);
				}
				if (SUPPLIERSNAME != null) {
					stock.setSuppliers(SUPPLIERSNAME);
				}
				if (REMARKS != null) {
					stock.setRemarks(REMARKS);
				}
			} catch (org.h2.jdbc.JdbcSQLException e) {
			}
			return stock;
		}
	}

	private static class StockRowAlarmMapper implements RowMapper {
		public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {
			String catno = rs.getString("CATNO");
			String syamount = rs.getString("SYAMOUNT");
			String costprice = rs.getString("COSTPRICE");
			String stockytpe = rs.getString("STOCKTYPE");
			String datetime = rs.getString("DATETIMED");
			String STOCKNAME = rs.getString("STOCKNAME");
			Stock stock = new Stock();
			if (catno != null) {
				stock.setCatno(catno);
			}

			if (syamount != null) {
				stock.setSyamount(Double.parseDouble(syamount));
			}
			if (costprice != null) {
				stock.setCostprice(new BigDecimal(costprice));
			}

			if (stockytpe != null) {
				stock.setType(stockytpe);
			}
			if (datetime != null) {
				stock.setDate(datetime);
			}

			if (STOCKNAME != null) {
				stock.setStockname(STOCKNAME);
			}
			try {
				String COLOR = rs.getString("COLOR");

				String SPECIF = rs.getString("SPECIF");
				String STOCKFLAG = rs.getString("STOCKFLAG");
				String RECORD = rs.getString("RECORD");

				String SUPPLIERSNAME = rs.getString("SUPPLIERSNAME");
				if (RECORD != null) {
					stock.setRecorddate(RECORD);
				}

				if (STOCKFLAG != null) {
					stock.setStockFlag(STOCKFLAG);
				}
				if (COLOR != null) {
					stock.setColor(COLOR);
				}
				if (SPECIF != null) {
					stock.setSpecif(SPECIF);
				}
				if (SUPPLIERSNAME != null) {
					stock.setSuppliers(SUPPLIERSNAME);
				}
			} catch (org.h2.jdbc.JdbcSQLException e) {
			}
			return stock;
		}
	}
}
