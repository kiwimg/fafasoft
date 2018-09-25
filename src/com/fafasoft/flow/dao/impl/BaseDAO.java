package com.fafasoft.flow.dao.impl;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.dao.BatchUpdateStatementCallback;
import com.fafasoft.flow.dao.JdbcConnectionPoolHelper;
import com.fafasoft.flow.dao.RowMapper;
import com.fafasoft.flow.dao.StatementCallback;
import com.fafasoft.flow.pojo.Config;
import com.fafasoft.flow.ui.AppStatusBar;
import com.fafasoft.flow.util.SynAuthentication;
import com.fafasoft.flow.util.SynchronousTools;
import com.fafasoft.flow.util.ZipCompressor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

public class BaseDAO {

	public Connection getConnection() {
		return JdbcConnectionPoolHelper.getInstance().getConnection();
	}

	public String sqlStr(String value) {
		if (value == null) {
			return null;
		}
		StringBuffer ans = new StringBuffer();
		String ch = "";
		for (int cnt = 0; cnt < value.length(); cnt++) {
			ch = value.substring(cnt, cnt + 1);
			if (ch.equals("'")) {
				ans.append("''");
			} else {
				ans.append(ch);
			}
		}
		return ans.toString();
	}

	public int[] batchUpdate(final String[] sql, final boolean isWrite)
			throws SQLException {
		class BatchUpdateStatementCallbackImpl implements
				BatchUpdateStatementCallback<int[]> {
			private String currSql;

			public int[] doInStatement(Statement stmt) throws SQLException {
				int[] rowsAffected = new int[sql.length];
				for (int i = 0; i < sql.length; i++) {
					this.currSql = sql[i];
					if (this.currSql != null) {
						if (!stmt.execute(sql[i])) {
							rowsAffected[i] = stmt.getUpdateCount();
						} else {
							throw new SQLException(
									"Invalid batch SQL statement: " + sql[i]);
						}
					}
				}
				return rowsAffected;
			}

			public String[] getSqlArray() {
				return sql;
			}

			@Override
			public String getSql() {
				// TODO Auto-generated method stub
				return null;
			}

			public boolean isWrite() {
				return isWrite;
			}
			public String getAction() {
				return "U";
			}
		}
		return execute(new BatchUpdateStatementCallbackImpl());
	}

	/**
	 * @param action
	 *            处理 Statement
	 * @param <T>
	 *            StatementCallback 实现类
	 * @return 返回 StatementCallback 处理结果
	 * @throws SQLException
	 *             如果发生数据库访问错误或者给定的 SQL 语句生成 ResultSet 对象
	 */
	public <T> T execute(final StatementCallback<T> action) throws SQLException {
		T result = null;
		Statement stmt = null;
		Connection conn = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			result = action.doInStatement(stmt);
			conn.commit();
			if (action.isWrite()) {
				Thread dd = new Thread(new SynDataThread(action));
				dd.start();
			}

			return result;
		} catch (SQLException ex) {
			if (conn != null) {
				conn.rollback();
			}
			throw new SQLException(ex);
		} finally {
			close(stmt, null, conn);
		}
	}

	/**
	 * @param @param sql
	 * @param @return
	 * @return 返回更新记录条数
	 * @throws
	 * @Title: update
	 * @Description: 更新数据库表中记录，并返回一个整数
	 */
	public int update(final String sql, final boolean isWrite)
			throws SQLException {
		class UpdateStatementCallback implements StatementCallback<Integer> {
			public Integer doInStatement(Statement stmt) throws SQLException {
				return stmt.executeUpdate(sql);
			}

			public String getSql() {
				return sql;
			}

			public boolean isWrite() {
				return isWrite;
			}

			public String getAction() {
				return "U";
			}
		}
		return execute(new UpdateStatementCallback());
	}

	public int insert(final String sql, final boolean isWrite)
			throws SQLException {
		class InsertStatementCallback implements StatementCallback<Integer> {
			public Integer doInStatement(Statement stmt) throws SQLException {
				return stmt.executeUpdate(sql);
			}

			public String getSql() {
				return sql;
			}

			public boolean isWrite() {
				return isWrite;
			}
			public String getAction() {
				return "I";
			}
		}
		return execute(new InsertStatementCallback());

	}

	public int delete(final String sql) throws SQLException {
		class DeletetStatementCallback implements StatementCallback<Integer> {
			public Integer doInStatement(Statement stmt) throws SQLException {
				return stmt.executeUpdate(sql);
			}

			public String getSql() {
				return sql;
			}

			public boolean isWrite() {
				return true;
			}
			public String getAction() {
				return "D";
			}
		}

		return execute(new DeletetStatementCallback());
	}

	/**
	 * @param sql
	 * @param rowMapper
	 * @param <T>
	 * @throws SQLException
	 */
	public <T> T queryForObject(final String sql, final RowMapper<T> rowMapper)
			throws SQLException {
		class QqueryStatementCallback implements StatementCallback<T> {
			public T doInStatement(Statement stmt) throws SQLException {
				ResultSet rs = null;
				try {
					rs = stmt.executeQuery(sql);
					int rowNum = 0;
					if (rs.next()) {
						return rowMapper.mapRow(rs, rowNum);
					} else {
						return null;
					}
				} finally {
					close(rs);
				}
			}

			public String getSql() {
				return sql;
			}

			public boolean isWrite() {
				return false;
			}
			public String getAction() {
				return "S";
			}
		}

		return execute(new QqueryStatementCallback());
	}

	/**
	 * @param @param sql
	 * @param @return
	 * @return 如果存在返回true 否则返回false
	 * @throws
	 * @Title: query
	 * @Description: 判断数据库表中是否存在某一个字段
	 */
	public boolean query(final String sql) throws SQLException {

		class QqueryStatementCallback implements StatementCallback<Boolean> {
			public Boolean doInStatement(Statement stmt) throws SQLException {
				ResultSet rs = stmt.executeQuery(sql);
				return rs.next();
			}

			public String getSql() {
				return sql;
			}

			public boolean isWrite() {
				return false;
			}
			public String getAction() {
				return "S";
			}
		}
		Boolean aBoolean = execute(new QqueryStatementCallback());
		return aBoolean.booleanValue();
	}

	/**
	 * @param @param sql
	 * @param @return
	 * @return 返回一个整型，代表数据库表中记录条数
	 * @throws
	 * @Title: getTotalRow
	 * @Description: 获取数据库表中的记录数量
	 */
	public int getTotalRow(final String sql) throws SQLException {
		RowMapper totalRow = new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt(1);
			}
		};
		Object number = queryForObject(sql, totalRow);

		return (number != null ? ((Integer) number).intValue() : 0);
	}

	public long getTotalRowForLong(final String sql) throws SQLException {
		RowMapper totalRow = new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong(1);
			}
		};
		Object number = queryForObject(sql, totalRow);

		return (number != null ? ((Long) number).longValue() : 0);
	}

	/**
	 * @param @param sql
	 * @param @return
	 * @return 返回一个整型，代表数据库表中记录条数
	 * @throws
	 * @Title: getTotalRow
	 * @Description: 获取数据库表中的记录数量
	 */
	public double getTotalRowForDouble(final String sql) throws SQLException {
		RowMapper totalRow = new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getDouble(1);
			}
		};
		Object number = queryForObject(sql, totalRow);

		return (number != null ? ((Double) number).doubleValue() : 0);
	}

	/**
	 * @param @param sql
	 * @param @param rowMapper 用于封装数据的回调函数接口
	 * @param @return
	 * @param @throws EmptyResultDataAccessException
	 * @return List<Object>
	 * @throws
	 * @Title: queryForList
	 * @Description: 从数据库中查找多条数据，用回调函数RowMapper 进行封装，并返回一个List值
	 */
	public List queryForList(final String sql, final RowMapper rowMapper)
			throws SQLException {

		class QqueryStatementCallback implements StatementCallback<List> {
			public List doInStatement(Statement stmt) throws SQLException {
				List results = new ArrayList();
				ResultSet rs = stmt.executeQuery(sql);
				int rowNum = 0;
				while (rs.next()) {
					results.add(rowMapper.mapRow(rs, rowNum++));
				}
				return results;
			}

			public String getSql() {
				return sql;
			}

			public boolean isWrite() {
				return false;
			}
			public String getAction() {
				return "s";
			}
		}
		return execute(new QqueryStatementCallback());

	}

	/**
	 * 关闭 Statement ResultSet Connection
	 * 
	 * @param ps
	 * @param rs
	 * @param conn
	 */
	public void close(Statement ps, ResultSet rs, Connection conn) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		close(rs, conn);
	}

	/**
	 * 关闭 ResultSet Connection
	 * 
	 * @param rs
	 * @param conn
	 */
	public void close(ResultSet rs, Connection conn) {

		close(rs);
	}

	public void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

class SynDataThread<T>  implements Runnable {
	public StatementCallback<T>  action;
	public SynDataThread(StatementCallback<T>  action){
		this.action=action; 
	}
	
	public void run() {
		boolean issyn = SynAuthentication.getInstance().isSyn();
		System.out.println("issyn=="+issyn);
		if (issyn) {
			String[] sqlarray = null;
			String sql = action.getSql();
			System.out.println("sql===" + sql);
			if (sql != null) {
				sqlarray = new String[1];
				sqlarray[0] = sql;
			} else if (action instanceof BatchUpdateStatementCallback) {
				BatchUpdateStatementCallback dd = (BatchUpdateStatementCallback) action;
				sqlarray = dd.getSqlArray();
			}
			if (sqlarray != null && sqlarray.length > 0) {
				byte[] syndata=ZipCompressor.getInstance().compress(sqlarray);
				SynDAO.getInstance().insert(action.getAction(), syndata);
				ConfigDAOImpl configDao = new ConfigDAOImpl();
				Config config = new Config();
				config.setKey(Constant.LAST_SYN_TIME);
				config.setValue(new Timestamp(System.currentTimeMillis()).toString());
				configDao.saveOrUpdateConfig(config);
			}
		
		} else {
			String initMacSerial = SynAuthentication
					.getInstance().getInitMacSerial();
			String authenticationCode = SynAuthentication
					.getInstance().getAuthenticationCode();
			String message = null;
			System.out.println("initMacSerial=="+initMacSerial);
			System.out.println("authenticationCode=="+authenticationCode);
			if (initMacSerial == null
					|| authenticationCode == null) {
				message = "注册www.ymaijia.com会员，享受数据安全云备份，数据不在丢失";
			} else {
				message = "只能在一台机器上使用备份功能，如果想要多台机器使用请在ymaijia网站开通";
			}
			AppStatusBar.getInstance().setSysMessage("1");
			AppStatusBar.getInstance().setTipsMessage(message);
		}

	}
}
