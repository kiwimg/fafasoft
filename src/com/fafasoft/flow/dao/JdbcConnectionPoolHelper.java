package com.fafasoft.flow.dao;

import com.fafasoft.flow.Constant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnectionPoolHelper {
//jdbc:h2:file:/E:/yygl2/yygl2/config/data/yygl
	
	private static JdbcConnectionPoolHelper  jdbcConnectionPool = null;
	private Connection conn = null;

	static{
		jdbcConnectionPool = new JdbcConnectionPoolHelper();
	}
	
	public static JdbcConnectionPoolHelper getInstance(){
		return jdbcConnectionPool;
	}

    private JdbcConnectionPoolHelper(){
        try {
            initJDBCConnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private  void initJDBCConnect() throws SQLException {
        String url = Constant.DB_PATH+";TRACE_LEVEL_FILE=0";
        Connection conn = null;
        org.h2.Driver.load();
        byte[] s1 = {115,97};
        byte[] s2 = {102,102,102,108,111,119,56,56,115};
        try {
        	System.out.print(url);
            conn = DriverManager.getConnection(url, new String(s1), new String(s2));
            setConnection(conn);
        } catch (SQLException e) {
            conn = DriverManager.getConnection(url, new String(s1), new String(s1));
            setConnection(conn);
        }
    }
	public  Connection getConnection() {
	
		return this.conn;
	}	
	public void closeConnection(){
		try {
			this.conn.close();
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}
	public void setConnection(Connection connection){
		this.conn = connection;
	}
}
