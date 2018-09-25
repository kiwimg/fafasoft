package com.fafasoft.flow.dao.impl;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.dao.RowMapper;
import com.fafasoft.flow.pojo.Flowlog;
import com.fafasoft.flow.util.DateHelper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FlowLogDAOImpl extends BaseDAO{

	public long getMaxSNumber(String date){
		  String sql = "SELECT SNUMBER FROM FLOW_LOG WHERE  RECORD ='"+date+"' ORDER BY SNUMBER DESC NULLS FIRST";
		  long list = 0;
	        try {
	            list = getTotalRowForLong(sql);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return list;
	}
    public List getFlowlogBySnumber(String snumber){
    	   List list = null;
    	   StringBuffer sql = new StringBuffer();
    	   sql.append("SELECT * FROM FLOW_LOG WHERE 1=1 ");
    	   if(snumber != null && snumber.trim().length() >0) {
    		   sql.append(" AND SNUMBER='");
    		   sql.append(snumber);
    		   sql.append("'");
    	   }

    	   sql.append(" AND (FLOWFLAG='");
		   sql.append( Constant.FLOW_TYPE_SELL);
		   sql.append("' OR ");
		   sql.append(" FLOWFLAG='");
		   sql.append( Constant.FLOW_TYPE_PLSELL);
		   sql.append("')");
    	   sql.append(" AND RECORD='");
		   sql.append( DateHelper.getNowDateTime());
		   sql.append("'");
           try {
        	
               list = queryForList(String.valueOf(sql), new FlowlogRowMapper());
           } catch (SQLException e) {
               e.printStackTrace();
           }

           return list;
    }
    public boolean deleteByFlowno(String flowno) {
        String sql = "DELETE FROM FLOW_LOG WHERE FLOWNO='" + flowno + "';";
        boolean isin = false;
        try {
            delete(sql);
            isin = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isin;
    }
    public boolean deleteBySNumber(String sNumber){
        String sql = "DELETE FROM FLOW_LOG WHERE SNUMBER='" + sNumber + "';";
        boolean isin = false;
        try {
            delete(sql);
            isin = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isin;
    }
    public boolean add(Flowlog flowlog) {
        //+ "'" + flowlog.getSerialNumber() + "'"
        boolean isin = false;
        try {
            insert(buildInsertSql(flowlog),true);
            isin = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isin;
    }

    public boolean add(Flowlog[] flowlogs) {
        boolean isin = false;
        try {
            String[] strings = new String[flowlogs.length];
            for (int i = 0; i < flowlogs.length; i++) {
                Flowlog flowlog = flowlogs[i];
                if(flowlog != null) {
                	 strings[i] = buildInsertSql(flowlog);
                }
            }
            batchUpdate(strings,true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isin;
    }
    private String buildInsertSql(Flowlog flowlog) {
        if (flowlog.getFlowno() == null) {
            flowlog.setFlowno(String.valueOf(UUID.randomUUID().toString().replaceAll("-", "")));
        }
        String sql = "INSERT INTO FLOW_LOG VALUES (" +
                  "'" + flowlog.getFlowno() + "',"
                + "'" + flowlog.getCatno() + "',"
                + "'" + flowlog.getAmount() + "',"
                + "'" + flowlog.getSellprice() + "',"
                + "'" + flowlog.getLrprice() + "',"
                + "'" + flowlog.getCostprice() + "',"
                + "'" + flowlog.getType() + "',"
                + "'" + flowlog.getDate() + "',"
                + "'" + flowlog.getStockname() + "',"
                + "'" + flowlog.getFlowflag() + "',"
                + "'" + flowlog.getRecorddate() + "',"
                + "'" + flowlog.getCustomNo() + "',"
                + "'" + flowlog.getCustomName() + "',"
                + "'" + flowlog.getUserId() + "',"
                + "'" + flowlog.getStockId() + "',"
                + "'" + flowlog.getRemarks() + "',"
                + "'" + flowlog.getSerialNumber() + "'"
                + ");";
        return sql;
    }
    public Flowlog getFlowByflowno(String flowno) {
        String sql = "SELECT * FROM FLOW_LOG WHERE FLOWNO='" + flowno + "';";
        Flowlog stock = null;

        try {
            stock = (Flowlog) queryForObject(sql, new FlowlogRowMapper());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stock;
    }

    public boolean update(Flowlog flowlog) {
        boolean isin = false;
        String sql = "UPDATE FLOW_LOG SET CATNO ='" + flowlog.getCatno() + "', COSTPRICE='" + flowlog.getCostprice() + "',AMOUNT ='" + flowlog.getAmount() + "',SELLPRICE ='" + flowlog.getSellprice()
                + "',LRPRICE ='" + flowlog.getLrprice()
                + "' WHERE FLOWNO='" + flowlog.getFlowno() + "'";

        try {
            update(sql,true);
            isin = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isin;
    }
    public boolean updateCatno(String newCatNo,String oldCatno,String newStockType,String oldstockType) {
        boolean isin = false;
        String sql = "UPDATE FLOW_LOG SET CATNO='" + newCatNo+ "', STOCKTYPE='"+newStockType+"' WHERE STOCKTYPE='"+oldstockType+"' and CATNO='" + oldCatno + "'";
        try {
            update(sql,true);
            isin = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isin;
    }
    public List getStatistical(String starttime, String endtime, String type) {
        List list = null;
        if (starttime != null && endtime != null) {
            String sql = "SELECT * FROM FLOW_LOG WHERE DATETIMED  between '" + starttime + "' AND '" + endtime + "'";

            if (!"".equals(type) && type != null) {
                sql = sql + " and STOCKTYPE='" + type + "'";
            }
            try {

                list = queryForList(sql, new FlowlogRowMapper());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public List getFlowlogByToday(String type) {
    	
        String sql = "SELECT * FROM FLOW_LOG WHERE RECORD ='" + DateHelper.getNowDateTime() + "' AND FLOWFLAG='" + type + "'";

        List list = null;
        try {

            list = queryForList(sql, new FlowlogRowMapper());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List getStockTypes(String flag) {
        Connection conn = super.getConnection(); // start
      
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT DISTINCT STOCKTYPE FROM FLOW_LOG ");
        if(flag !=null && flag.length() >0) {
        	sql.append("WHERE  FLOWFLAG ='");
          	  sql.append(flag);
        	  sql.append("'");
        }
        Statement stat = null;
        ResultSet rs = null;
        List<String> list = null;
        try {
            stat = conn.createStatement();
            rs = stat.executeQuery(String.valueOf(sql));
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
    public List getSellFlowlog(String cartno,String type,int size){
    	 return  getSellFlowlog( cartno, type, null , size);
    }
    public List getSellFlowlog(String cartno,String type,String date ,int size){
        cartno = sqlStr(cartno);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM FLOW_LOG WHERE 1=1");
        if(type != null && type.trim().length() >0) {
        	  sql.append(" AND STOCKTYPE ='");
        	  sql.append(type);
        	  sql.append("'");
        }
        if(cartno != null && cartno.trim().length() >0) {
      	  sql.append(" AND CATNO ='");
      	  sql.append(cartno);
      	  sql.append("'");
        }
        if(date != null && date.trim().length() >0) {
        	  sql.append(" AND DATETIMED ='");
        	  sql.append(date);
        	  sql.append("'");
          }
        sql.append(" AND FLOWFLAG ='");
    	sql.append(Constant.FLOW_TYPE_SELL);
    	sql.append("'");
        sql.append(" LIMIT ");
        sql.append(size);
     
        List list = null;
        try {
            list = queryForList(String.valueOf(sql), new FlowlogRowMapper());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
       
    }
    public List getFlowSuggest(String cartno,String type, int size){
    	  Connection conn = super.getConnection(); // LIMIT " + max + " OFFSET " +
          // start
          cartno = sqlStr(cartno);

          String sql = "SELECT * FROM FLOW_LOG WHERE STOCKTYPE='"+type+"' and FLOWFLAG ='"+Constant.FLOW_TYPE_SELL+"' and CATNO LIKE '" + cartno
                  + "%' LIMIT " + size;

          List list = null;
          try {

              list = queryForList(sql, new FlowlogRowMapper());
          } catch (SQLException e) {
              e.printStackTrace();
          }
          return list;
    }
    public List getFlowlog(String cartno, int size) {
        Connection conn = super.getConnection(); // LIMIT " + max + " OFFSET " +
        // start
        cartno = sqlStr(cartno);

        String sql = "SELECT * FROM FLOW_LOG WHERE CATNO LIKE '" + cartno
                + "%' LIMIT " + size;

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
            super.close(stat, rs, conn);
        }
        return list;
    }

    public int getStatisticalSize(String starttime, String endtime, String type, String catno, String userID,String sellType) {
        String sql = "SELECT   COUNT(*) FROM FLOW_LOG WHERE DATETIMED  between '" + starttime + "' AND '" + endtime + "'";

        if (!"".equals(type) && type != null) {
            sql = sql + " and STOCKTYPE='" + type + "'";
        }
        if(userID != null) {
        	sql = sql +" AND SELLWHO='"+userID+"'";
        }
        if (!"".equals(catno) && catno != null) {
            sql = sql +" AND CATNO='"+catno+"'";
        }
        if (!"".equals(sellType) && sellType != null) {
            sql = sql +" AND FLOWFLAG='"+sellType+"'";
        }
        int list = 0;
      
        try {

            list = getTotalRow(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getStatistical(String starttime, String endtime, String type, String catno, String userID,String sellType,int start, int max) {
    	catno = sqlStr(catno);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM FLOW_LOG WHERE DATETIMED");
        sql.append("  between '").append(starttime).append("' AND '").append(endtime).append("'");
        if (!"".equals(type) && type != null) {
            sql.append(" AND ").append("STOCKTYPE='").append(type).append("'");
        }
        if (!"".equals(catno) && catno != null) {
            sql.append(" AND ").append("CATNO='").append(catno).append("'");
        }
        if(userID != null) {
        	sql.append(" AND ").append("SELLWHO='").append(userID).append("'");
        	
        }
        if (!"".equals(sellType) && sellType != null) {
        	sql.append(" AND ").append("FLOWFLAG='").append(sellType).append("'");
        }
        sql.append(" LIMIT ").append(max).append(" OFFSET ").append(start);
 
        List list = null;
        try {

            list = queryForList(String.valueOf(sql), new FlowlogRowMapper());
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return list;
    }
    //成本总计
    public BigDecimal sumCostPrice(String starttime, String endtime, String type, String catno,String userid ,String sellType) {  
    	catno = sqlStr(catno);
        Connection conn = super.getConnection();

        BigDecimal list = new BigDecimal(0);
        Statement stat = null;
        ResultSet resultSet = null;
        try {
            stat = conn.createStatement();
            if(sellType == null || Constant.FLOW_TYPE_SELL.equals(sellType)) {
                StringBuffer sql = new StringBuffer();
                sql.append("SELECT SUM(COSTPRICE * AMOUNT) FROM FLOW_LOG  WHERE DATETIMED ");
                sql.append("  between '").append(starttime).append("' AND '").append(endtime).append("'");
                if (!"".equals(type) && type != null) {
                    sql.append(" AND ").append("STOCKTYPE='").append(type).append("'");
                }
                if (!"".equals(catno) && catno != null) {
                    sql.append(" AND ").append("CATNO='").append(catno).append("'");
                }
                if (!"".equals(userid) && userid != null) {
                    sql.append(" AND ").append("SELLWHO='").append(userid).append("'");
                }
                sql.append(" AND ").append("FLOWFLAG='").append(Constant.FLOW_TYPE_SELL).append("'");
                
                StringBuffer sql_1 = new StringBuffer();
                sql_1.append("SELECT SUM(COSTPRICE) FROM FLOW_LOG  WHERE DATETIMED ");
                sql_1.append("  between '").append(starttime).append("' AND '").append(endtime).append("'");
                if (!"".equals(type) && type != null) {
                	sql_1.append(" AND ").append("STOCKTYPE='").append(type).append("'");
                }
                if (!"".equals(catno) && catno != null) {
                	sql_1.append(" AND ").append("CATNO='").append(catno).append("'");
                }
                if (!"".equals(userid) && userid != null) {
                	sql_1.append(" AND ").append("SELLWHO='").append(userid).append("'");
                }
              
                sql_1.append(" AND ").append("FLOWFLAG='").append(Constant.FLOW_TYPE_PLSELL).append("'");
                
                resultSet = stat.executeQuery(String.valueOf(sql));
                resultSet.next();
                BigDecimal qlist = resultSet.getBigDecimal(1);
                if (qlist != null) {
                    list = qlist.setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                resultSet = stat.executeQuery(String.valueOf(sql_1));
                resultSet.next();
                BigDecimal list2 = new BigDecimal(0);
                BigDecimal q1list = resultSet.getBigDecimal(1);
                if (q1list != null) {
                	list2 = q1list.setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                list = list.add(list2).setScale(2, BigDecimal.ROUND_HALF_UP);
                
            }
            if(!Constant.FLOW_TYPE_SELL.equals(sellType)) {
            	 StringBuffer sql = new StringBuffer();
                 sql.append("SELECT SUM(COSTPRICE * AMOUNT) FROM FLOW_LOG  WHERE DATETIMED ");
                 sql.append("  between '").append(starttime).append("' AND '").append(endtime).append("'").append(" and AMOUNT <0");
                 if (!"".equals(type) && type != null) {
                     sql.append(" AND ").append("STOCKTYPE='").append(type).append("'");
                 }
                 if (!"".equals(catno) && catno != null) {
                     sql.append(" AND ").append("CATNO='").append(catno).append("'");
                 }
                 if (!"".equals(catno) && catno != null) {
                     sql.append(" AND ").append("CATNO='").append(catno).append("'");
                 }
                 if (!"".equals(userid) && userid != null) {
                     sql.append(" AND ").append("SELLWHO='").append(userid).append("'");
                 }
                 sql.append(" AND ").append("FLOWFLAG='").append(Constant.FLOW_TYPE_TH).append("'");
                 resultSet = stat.executeQuery(String.valueOf(sql));
                 resultSet.next();
                 BigDecimal tuihuo = resultSet.getBigDecimal(1);
                 if (tuihuo != null) {
                	 list = list.add(tuihuo.setScale(2, BigDecimal.ROUND_HALF_UP));
                 }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(stat, resultSet, conn);
        }
        return list;
    }
    //利润总计
    public BigDecimal sumLrPrice(String starttime, String endtime, String type, String catno,String userid,String sellType ) {
    	catno = sqlStr(catno);
        Connection conn = super.getConnection();
       
        BigDecimal sum = new BigDecimal(0);
        Statement stat = null;
        ResultSet resultSet = null;
        try {
            stat = conn.createStatement();
            if(sellType == null || Constant.FLOW_TYPE_SELL.equals(sellType)) {
            	  StringBuffer sql = new StringBuffer();
                  sql.append("SELECT SUM(LRPRICE) FROM FLOW_LOG  WHERE DATETIMED ");
                  sql.append("  between '").append(starttime).append("' AND '").append(endtime).append("'");
                  if (!"".equals(type) && type != null) {
                      sql.append(" AND ").append("STOCKTYPE='").append(type).append("'");
                  }
                  if (!"".equals(catno) && catno != null) {
                      sql.append(" AND ").append("CATNO='").append(catno).append("'");
                  }
                  if (!"".equals(userid) && userid != null) {
                      sql.append(" AND ").append("SELLWHO='").append(userid).append("'");
                  }
                  sql.append(" AND ").append("FLOWFLAG='").append(Constant.FLOW_TYPE_SELL).append("'");
                  
                  
                  StringBuffer sql1 = new StringBuffer();
                  sql1.append("SELECT SUM(LRPRICE) FROM FLOW_LOG  WHERE DATETIMED ");
                  sql1.append("  between '").append(starttime).append("' AND '").append(endtime).append("'");
                  if (!"".equals(type) && type != null) {
                  	sql1.append(" AND ").append("STOCKTYPE='").append(type).append("'");
                  }
                  if (!"".equals(catno) && catno != null) {
                  	sql1.append(" AND ").append("CATNO='").append(catno).append("'");
                  }
                  if (!"".equals(userid) && userid != null) {
                  	sql1.append(" AND ").append("SELLWHO='").append(userid).append("'");
                  }
                  sql1.append(" AND ").append("FLOWFLAG='").append(Constant.FLOW_TYPE_PLSELL).append("'");
                 
 
                  resultSet = stat.executeQuery(String.valueOf(sql));
                  resultSet.next();
                  BigDecimal danping = resultSet.getBigDecimal(1);
                  if (danping != null) {
                	  sum = danping.setScale(2, BigDecimal.ROUND_HALF_UP);
                  } else {
                	  sum = new BigDecimal(0);
                  }
                  resultSet = stat.executeQuery(String.valueOf(sql1));
                  resultSet.next();
                  BigDecimal danbi = resultSet.getBigDecimal(1);
                  if (danbi != null) {
                	  sum = danbi.add(sum).setScale(2, BigDecimal.ROUND_HALF_UP);
                  	 
                  }
            }
            if(! Constant.FLOW_TYPE_SELL.equals(sellType)) {
            	 StringBuffer sql = new StringBuffer();
                 sql.append("SELECT SUM(LRPRICE) FROM FLOW_LOG  WHERE DATETIMED ");
                 sql.append("  between '").append(starttime).append("' AND '").append(endtime).append("'").append(" and AMOUNT < 0");
                 if (!"".equals(type) && type != null) {
                     sql.append(" AND ").append("STOCKTYPE='").append(type).append("'");
                 }
                 if (!"".equals(catno) && catno != null) {
                     sql.append(" AND ").append("CATNO='").append(catno).append("'");
                 }
                 if (!"".equals(userid) && userid != null) {
                     sql.append(" AND ").append("SELLWHO='").append(userid).append("'");
                 }
                 sql.append(" AND ").append("FLOWFLAG='").append(Constant.FLOW_TYPE_TH).append("'");
                 resultSet = stat.executeQuery(String.valueOf(sql));
                 resultSet.next();
                 BigDecimal tuihuo = resultSet.getBigDecimal(1);
            
                 if (tuihuo != null) {
                     sum =sum.add(tuihuo);
                     sum = sum.setScale(2, BigDecimal.ROUND_HALF_UP);
                 }
            }
           
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            close(stat, resultSet, conn);
        }
        return sum;
    }
    //流水总计统计
    public BigDecimal sumFlowPrice(String starttime, String endtime, String type, String catno,String userid,String sellType) {
    	catno = sqlStr(catno);
        Connection conn = super.getConnection();
        BigDecimal sum = new BigDecimal(0);
        Statement stat = null;
        ResultSet resultSet = null;
        try {
            stat = conn.createStatement();
            if (sellType == null || Constant.FLOW_TYPE_SELL.equals(sellType)) {
           	 // 单品销售流水总计
               StringBuffer sql = new StringBuffer();
               sql.append("SELECT SUM(SELLPRICE * AMOUNT) FROM FLOW_LOG  WHERE DATETIMED ");
               sql.append("  between '").append(starttime).append("' AND '").append(endtime).append("'").append(" and AMOUNT >0");
               if (!"".equals(type) && type != null) {
                   sql.append(" AND ").append("STOCKTYPE='").append(type).append("'");
               }
               if (!"".equals(catno) && catno != null) {
                   sql.append(" AND ").append("CATNO='").append(catno).append("'");
               }
               if (!"".equals(catno) && catno != null) {
                   sql.append(" AND ").append("CATNO='").append(catno).append("'");
               }
               if (!"".equals(userid) && userid != null) {
                   sql.append(" AND ").append("SELLWHO='").append(userid).append("'");
               }
               sql.append(" AND ").append("FLOWFLAG='").append(Constant.FLOW_TYPE_SELL).append("'"); 
               
               // 单笔销售流水总计
               
               StringBuffer sql_banbi = new StringBuffer();
               sql_banbi.append("SELECT SUM(SELLPRICE) FROM FLOW_LOG  WHERE DATETIMED ");
               sql_banbi.append("  between '").append(starttime).append("' AND '").append(endtime).append("'").append(" and AMOUNT>0");
               if (!"".equals(type) && type != null) {
               	sql_banbi.append(" AND ").append("STOCKTYPE='").append(type).append("'");
               }
               if (!"".equals(catno) && catno != null) {
               	sql_banbi.append(" AND ").append("CATNO='").append(catno).append("'");
               }
               if (!"".equals(catno) && catno != null) {
               	sql_banbi.append(" AND ").append("CATNO='").append(catno).append("'");
               }
               if (!"".equals(userid) && userid != null) {
               	sql_banbi.append(" AND ").append("SELLWHO='").append(userid).append("'");
               }
                sql_banbi.append(" AND ").append("FLOWFLAG='").append(Constant.FLOW_TYPE_PLSELL).append("'"); 
               
                resultSet = stat.executeQuery(String.valueOf(sql));
                resultSet.next();
                BigDecimal qsum = resultSet.getBigDecimal(1);
                if (qsum != null) {
                    sum = qsum.setScale(2, BigDecimal.ROUND_HALF_UP);
                }
             
                resultSet = stat.executeQuery(String.valueOf(sql_banbi));
                resultSet.next();
                BigDecimal danbisum = new BigDecimal(0); 
                BigDecimal danbiqsum = resultSet.getBigDecimal(1);
                if (danbiqsum != null) {
                	danbisum = danbiqsum.setScale(2, BigDecimal.ROUND_HALF_UP);
                }             
                sum = sum.add(danbisum);    
           }
         
            if(!Constant.FLOW_TYPE_SELL.equals(sellType)) {
            	  StringBuffer sql = new StringBuffer();
                  sql.append("SELECT SUM(SELLPRICE * AMOUNT) FROM FLOW_LOG  WHERE DATETIMED ");
                  sql.append("  between '").append(starttime).append("' AND '").append(endtime).append("'").append(" and AMOUNT <0");
                  if (!"".equals(type) && type != null) {
                      sql.append(" AND ").append("STOCKTYPE='").append(type).append("'");
                  }
                  if (!"".equals(catno) && catno != null) {
                      sql.append(" AND ").append("CATNO='").append(catno).append("'");
                  }
                  if (!"".equals(userid) && userid != null) {
                      sql.append(" AND ").append("SELLWHO='").append(userid).append("'");
                  }
                  sql.append(" AND ").append("FLOWFLAG='").append(Constant.FLOW_TYPE_TH).append("'");
                  resultSet = stat.executeQuery(String.valueOf(sql));
                  resultSet.next();
                  BigDecimal sumb = resultSet.getBigDecimal(1);

                  if (sumb != null && sum != null) {
                      sum =sum.subtract(sumb);
                      sum = sum.setScale(2, BigDecimal.ROUND_HALF_UP);
                  }
                
            }
          
        
           
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(stat, resultSet, conn);
        }
        return sum;
    }

    public double sumFlow(String catno, String flowflag) {
        String sql = "SELECT SUM(AMOUNT)  FROM FLOW_LOG  WHERE CATNO ='" + catno + "' and FLOWFLAG='" + flowflag + "'";
        double r = 0;
        try {
            r = getTotalRowForDouble(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }
    
    public double sumFlowAmount(String catno,String type,String flowflag){
    	catno = sqlStr(catno);
    	  String sql = "SELECT SUM(AMOUNT)  FROM FLOW_LOG  WHERE CATNO ='" + catno + "' and STOCKTYPE='" + type + "' and FLOWFLAG='"+flowflag+"'";
    	  double r = 0;
          try {
              r = getTotalRowForDouble(sql);
          } catch (SQLException e) {
              e.printStackTrace();
          }
          return r;
    }
    public List getSNumberList(String date) {
   	 String sql ="SELECT DiSTINCT SNUMBER FROM FLOW_LOG WHERE  RECORD ='"+date+"' and FLOWFLAG !='"+Constant.FLOW_TYPE_TH+"' ORDER BY SNUMBER DESC;";
     List list = null;
     try {
         list = queryForList(sql,  new  RowMapper() {
             public Flowlog mapRow(ResultSet rs, int rowNum) throws SQLException {
                 Flowlog flowlog = new Flowlog();
                 try {
                     long serialNumber = rs.getLong("SNUMBER");
                     flowlog.setSerialNumber(serialNumber);
                 } catch (org.h2.jdbc.JdbcSQLException e) {
                 }
                 return flowlog;
             }
         });
     } catch (SQLException e) {
         e.printStackTrace();
     }
     return list;
    }
 
    private List getResultset(ResultSet rs) throws SQLException {
        List list = new ArrayList();

        while (rs.next()) {
            Flowlog flowlog = new Flowlog();
            try {
                String flowno = rs.getString("FLOWNO");

                flowlog.setFlowno(flowno);
                String CUSTOMID = rs.getString("CUSTOMID");

                flowlog.setCustomNo(CUSTOMID);
                String CUSTOMNAME = rs.getString("CUSTOMNAME");
                flowlog.setCustomName(CUSTOMNAME);
                String SNUMBER = rs.getString("SNUMBER");
                flowlog.setSerialNumber(Integer.parseInt(SNUMBER));
                
            } catch (org.h2.jdbc.JdbcSQLException e) {
            }

            String catno = rs.getString("CATNO");
            String amount = rs.getString("AMOUNT");

            String sellprice = rs.getString("SELLPRICE");
            String lrprice = rs.getString("LRPRICE");
            String costprice = rs.getString("COSTPRICE");
            String stockytpe = rs.getString("STOCKTYPE");
            String datetime = rs.getString("DATETIMED");
            String STOCKNAME = rs.getString("STOCKNAME");


            if (catno != null) {
                flowlog.setCatno(catno);
            }
            if (amount != null) {
                flowlog.setAmount(Double.parseDouble(amount));
            }

            if (sellprice != null) {
                flowlog.setSellprice(new BigDecimal(sellprice));

            }
            if (lrprice != null) {
                flowlog.setLrprice(new BigDecimal(lrprice));

            }
            if (costprice != null) {
                flowlog.setCostprice(new BigDecimal(costprice));

            }
            if (stockytpe != null) {
                flowlog.setType(stockytpe);
            }
            if (datetime != null) {
                flowlog.setDate(datetime);
            }
            if (STOCKNAME != null) {
                flowlog.setStockname(STOCKNAME);
            }

            try {
                String FLOWFLAG = rs.getString("FLOWFLAG");
                String RECORD = rs.getString("RECORD");

                if (FLOWFLAG != null) {
                    flowlog.setFlowflag(FLOWFLAG);
                }
                if (RECORD != null) {
                    flowlog.setRecorddate(RECORD);
                }
            } catch (org.h2.jdbc.JdbcSQLException e) {
            }


            list.add(flowlog);
        }
        return list;
    }

    public int getFlowLogOrderSize(String starttime, String endtime) {
        String sql = "SELECT CATNO ,sum(AMOUNT) AS AMOUNT,sum(LRPRICE ) AS LRPRICE ,STOCKNAME ,STOCKTYPE FROM FLOW_LOG WHERE DATETIMED  between '" + starttime + "' AND '" + endtime + "' GROUP BY CATNO, STOCKNAME ,STOCKTYPE ORDER BY sum(AMOUNT)  DESC";

        List list = new ArrayList();
        try {

            list = queryForList(sql, new FlowOrderRowMapper());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.size();
    }

    public List getFlowLogOrderBy(String starttime, String endtime, int start, int max) {
        String sql = "SELECT CATNO ,sum(AMOUNT) AS AMOUNT,sum(LRPRICE ) AS LRPRICE ,STOCKNAME ,STOCKTYPE FROM FLOW_LOG WHERE DATETIMED  between '" + starttime + "' AND '" + endtime + "' GROUP BY CATNO, STOCKNAME ,STOCKTYPE ORDER BY sum(AMOUNT) DESC LIMIT "
                + max + " OFFSET " + start;

        List list = null;
        try {

            list = queryForList(sql, new FlowOrderRowMapper());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getFlowLogOrderBy(String starttime, String endtime) {
        String sql = "SELECT CATNO ,sum(AMOUNT) AS AMOUNT,sum(LRPRICE ) AS LRPRICE ,STOCKNAME ,STOCKTYPE FROM FLOW_LOG GROUP BY CATNO ORDER BY sum(AMOUNT) DESC  ";
        List list = null;
        try {

            list = queryForList(sql, new FlowlogRowMapper());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getFlowLogKeHLogSize(String starttime, String endtime, String cno, String cName) {
        StringBuffer sqlbur = new StringBuffer();
        sqlbur.append("SELECT  COUNT(*) FROM FLOW_LOG WHERE 1=1 ");

        if (starttime != null && !"".equals(starttime) && endtime != null && !"".equals(endtime)) {
            sqlbur.append(" AND ");
            sqlbur.append(" DATETIMED  between '").append(starttime).append("' AND '").append(endtime).append("'");
        }
        if (cno != null && !"".equals(cno)) {
            sqlbur.append(" AND ");
            sqlbur.append(" CUSTOMID='");
            sqlbur.append(cno);
            sqlbur.append("'");
        }
        if (cName != null && !"".equals(cName)) {
            sqlbur.append(" AND ");
            sqlbur.append(" CUSTOMNAME !='");
            sqlbur.append(cName);
            sqlbur.append("'");
        }

        sqlbur.append(" AND ");
        sqlbur.append(" CUSTOMID !=''");
        //System.out.println("sql:" + sql);
        int list = 0;

        try {

            list = getTotalRow(String.valueOf(sqlbur));
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return list;

    }

    public List getFlowLogKeHLog(String starttime, String endtime, String cno, String cName, int start, int max) {
        StringBuffer sqlbur = new StringBuffer();
        sqlbur.append("SELECT * FROM FLOW_LOG WHERE 1=1 ");

        if (starttime != null && !"".equals(starttime) && endtime != null && !"".equals(endtime)) {
            sqlbur.append(" AND ");
            sqlbur.append(" DATETIMED  between '").append(starttime).append("' AND '").append(endtime).append("'");
        }
        if (cno != null && !"".equals(cno)) {
            sqlbur.append(" AND ");
            sqlbur.append(" CUSTOMID='");
            sqlbur.append(cno);
            sqlbur.append("'");
        }
        if (cName != null && !"".equals(cName)) {
            sqlbur.append(" AND ");
            sqlbur.append(" CUSTOMNAME !='");
            sqlbur.append(cName);
            sqlbur.append("'");
        }

        sqlbur.append(" AND ");
        sqlbur.append(" CUSTOMID !=''");
        if (max > 0) {
            sqlbur.append(" LIMIT ").append(max).append(" OFFSET ").append(start);
        }
        List list = null;
        try {

            list = queryForList(String.valueOf(sqlbur), new FlowlogRowMapper());
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return list;
    }
    public Flowlog getFlowlogTodayByUserName(String date,String username){
    	String sql = "SELECT sum(AMOUNT) as AMOUNT , sum(SELLPRICE*AMOUNT) as SELLPRICE FROM FLOW_LOG WHERE FLOWFLAG='"+Constant.FLOW_TYPE_SELL+"' AND AMOUNT >0 AND SELLWHO='"+username+"' and DATETIMED ='"+date+"'";
    	  List list = null;
          try {

              list = queryForList(sql, new FlowLogByUser());
          } catch (SQLException e) {
              e.printStackTrace();
          }
          Flowlog flowlog = null;
          if(list != null && list.size() ==1) {
        	  flowlog = (Flowlog)list.get(0);
          }
          return flowlog;
    }
    private static class FlowLogByUser implements RowMapper{
    	public Flowlog mapRow(ResultSet rs, int rowNum) throws SQLException {
            Flowlog flowlog = new Flowlog();
            try {
            	double amount = rs.getDouble("AMOUNT");

                flowlog.setAmount(amount);
                BigDecimal sellprice = rs.getBigDecimal("SELLPRICE");

                flowlog.setSellprice(sellprice);
               
            } catch (org.h2.jdbc.JdbcSQLException e) {
            }
            return flowlog;
        }
    }
    private static class FlowlogRowMapper implements RowMapper {
    	public Flowlog mapRow(ResultSet rs, int rowNum)
        throws SQLException {
        Flowlog flowlog = new Flowlog();
        try {
          String flowno = rs.getString("FLOWNO");

          flowlog.setFlowno(flowno);
          String CUSTOMID = rs.getString("CUSTOMID");

          flowlog.setCustomNo(CUSTOMID);
          String CUSTOMNAME = rs.getString("CUSTOMNAME");
          flowlog.setCustomName(CUSTOMNAME);
          String SNUMBER = rs.getString("SNUMBER");
          if(SNUMBER != null && SNUMBER.length() >0){
        	  flowlog.setSerialNumber(Long.parseLong(SNUMBER));  
          }
        }
        catch (SQLException localJdbcSQLException) {
        }
        String catno = rs.getString("CATNO");
        String amount = rs.getString("AMOUNT");

        String sellprice = rs.getString("SELLPRICE");
        String lrprice = rs.getString("LRPRICE");
        String costprice = rs.getString("COSTPRICE");
        String stockytpe = rs.getString("STOCKTYPE");
        String datetime = rs.getString("DATETIMED");
        String STOCKNAME = rs.getString("STOCKNAME");
       
        if (catno != null) {
          flowlog.setCatno(catno);
        }
        if (amount != null) {
          flowlog.setAmount(Double.parseDouble(amount));
        }

        if (sellprice != null) {
          flowlog.setSellprice(new BigDecimal(sellprice));
        }

        if (lrprice != null) {
          flowlog.setLrprice(new BigDecimal(lrprice));
        }

        if (costprice != null) {
          flowlog.setCostprice(new BigDecimal(costprice));
        }

        if (stockytpe != null) {
          flowlog.setType(stockytpe);
        }
        if (datetime != null) {
          flowlog.setDate(datetime);
        }
        if (STOCKNAME != null) {
          flowlog.setStockname(STOCKNAME);
        }
        try
        {
          String FLOWFLAG = rs.getString("FLOWFLAG");
          String RECORD = rs.getString("RECORD");

          String STOCKID = rs.getString("STOCKID");
          String SELLWHO = rs.getString("SELLWHO");
          if (FLOWFLAG != null) {
            flowlog.setFlowflag(FLOWFLAG);
          }
          if (RECORD != null){
            flowlog.setRecorddate(RECORD);
          }
          if (STOCKID != null) {
              flowlog.setStockId(STOCKID);
          }
          if (SELLWHO != null) {
              flowlog.setUserId(SELLWHO);
          }
        }
        catch (SQLException localJdbcSQLException1)
        {
        }
        return flowlog;
      }
    }

    private static class FlowOrderRowMapper implements RowMapper {
        public Flowlog mapRow(ResultSet rs, int rowNum) throws SQLException {
            Flowlog flowlog = new Flowlog();
            try {
                String flowno = rs.getString("FLOWNO");

                flowlog.setFlowno(flowno);
                String CUSTOMID = rs.getString("CUSTOMID");

                flowlog.setCustomNo(CUSTOMID);
                String CUSTOMNAME = rs.getString("CUSTOMNAME");
                flowlog.setCustomName(CUSTOMNAME);
                String SNUMBER = rs.getString("SNUMBER");
                flowlog.setSerialNumber(Integer.parseInt(SNUMBER));
                
            } catch (org.h2.jdbc.JdbcSQLException e) {
            }

            String catno = rs.getString("CATNO");
            String amount = rs.getString("AMOUNT");

            String lrprice = rs.getString("LRPRICE");

            String stockytpe = rs.getString("STOCKTYPE");

            String STOCKNAME = rs.getString("STOCKNAME");

            if (catno != null) {
                flowlog.setCatno(catno);
            }
            if (amount != null) {
                flowlog.setAmount(Double.parseDouble(amount));
            }

            if (lrprice != null) {
                flowlog.setLrprice(new BigDecimal(lrprice));

            }

            if (stockytpe != null) {
                flowlog.setType(stockytpe);
            }

            if (STOCKNAME != null) {
                flowlog.setStockname(STOCKNAME);
            }

            return flowlog;
        }
    }
}
