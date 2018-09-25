package com.fafasoft.flow.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.pojo.SynData;

public class SynDAO {
	private static SynDAO synDao = null;
	private Connection conn = null;
	static {
		synDao = new SynDAO();
	}

	public static SynDAO getInstance() {
		return synDao;
	}

	private SynDAO() {
		try {
			initJDBCConnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void initJDBCConnect() throws SQLException {
		String url = Constant.SYNDB_PATH;

		Connection conn = null;
		org.h2.Driver.load();
		byte[] s1 = { 115, 97 };
		byte[] s2 = { 102, 102, 102, 108, 111, 119, 56, 56, 115 };
		try {
			
			conn = DriverManager.getConnection(url, new String(s1), new String(
					s2));
			setConnection(conn);
		} catch (SQLException e) {
			conn = DriverManager.getConnection(url, new String(s1), new String(
					s1));
			setConnection(conn);
		}
	}

	private Connection getConnection() {
		return this.conn;
	}

	private void closeConnection() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setConnection(Connection connection) {
		this.conn = connection;
	}

	public void insert(String action, byte[] syndata) {
		PreparedStatement prep = null;
		try {
			prep = conn
					.prepareStatement("insert into SYNCHRONOUS values(?, ?, ? ,?)");

			prep.setString(1, UUID.randomUUID().toString());

			prep.setBytes(2, syndata);
			prep.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			prep.setString(4, action);
			prep.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (prep != null) {
					prep.close();
				}

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	public PreparedStatement getDeletePreparedStatement() {
		String sql = "DELETE FROM SYNCHRONOUS WHERE id=?";
		PreparedStatement prep = null;
		try {
			prep = conn.prepareStatement(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prep;
	}

//	public List getSqls(int max, int start) {
//
//		String sql = "SELECT id,SYNDATA,SYNTIME FROM SYNCHRONOUS ORDER BY SYNTIME ASC LIMIT "
//				+ max + " OFFSET " + start;
//		Statement stmt = null;
//		Connection connection = getConnection();
//		List results = new ArrayList();
//		ResultSet rs = null;
//		try {
//			stmt = connection.createStatement();
//			connection.setAutoCommit(false);
//		
//			rs = stmt.executeQuery(sql);
//
//			while (rs.next()) {
//				SynData synData = new SynData();
//				synData.setId(rs.getString(1));
//
//				synData.setDatas(rs.getBytes(2));
//				synData.setDate(rs.getDate(3));
//				results.add(synData);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				rs.close();
//				stmt.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return results;
//	}

	public int getSqlsSize() {
		String sql = "SELECT count(id) FROM SYNCHRONOUS";
		Statement stmt = null;
		Connection connection = getConnection();
		int size = 0;
		ResultSet rs = null;
		try {
			stmt = connection.createStatement();
			connection.setAutoCommit(false);

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return size;
	}

	public void create() {
		String sql = "CREATE CACHED TABLE PUBLIC.SYNCHRONOUS(\n"
				+ "    ID VARCHAR2(50) NOT NULL,\n" + "    SYNDATA BLOB,\n"
				+ "    SYNTIME TIMESTAMP,\n" + "    ACTION VARCHAR(10)\n"
				+ ");";
		Statement stat2 = null;

		Connection connection = getConnection();
		try {
			stat2 = connection.createStatement();
			stat2.execute(sql);
			stat2.close();
			connection.commit();
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

	public void clear(String date) {
		String sql = "delete FROM SYNCHRONOUS where SYNTIME <'"+date+"'";
		
		Statement stmt = null;
		Connection connection = getConnection();

		try {
			stmt = connection.createStatement();
			connection.setAutoCommit(false);

			stmt.executeUpdate(sql);
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] arg) {
		SynDAO synDAO = SynDAO.getInstance();
		byte[] s2 = { 102, 102, 102, 108, 111, 119, 56, 56, 115 };
		System.out.print(new String(s2));
//		synDAO.insert("I", "ddd".getBytes());
//		synDAO.insert("I", "ddd".getBytes());
//		synDAO.insert("I", "ddd".getBytes());
//		synDAO.insert("I", "ddd".getBytes());
//		synDAO.insert("I", "ddd".getBytes());
//		synDAO.insert("I", "ddd".getBytes());
//		synDAO.insert("I", "ddd".getBytes());
//		synDAO.insert("I", "ddd".getBytes());
//		synDAO.insert("I", "ddd".getBytes());
		String time =String.valueOf(System.currentTimeMillis());
		synDAO.clear(time);
		// List list = synDAO.getSqls(20,0);
		// synDAO.delete("9ebb21d4-3bf8-430b-b2c7-c5a1426357f5");
	}
}
