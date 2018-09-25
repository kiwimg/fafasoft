package com.fafasoft.flow.dao.impl;

import com.fafasoft.flow.Constant;
import com.fafasoft.flow.dao.RowMapper;
import com.fafasoft.flow.dao.StatementCallback;
import com.fafasoft.flow.pojo.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

public class UserDAOImpl extends BaseDAO {
	public void add(User user) {
		User userOld = getUserByid(user.getUsernmae());
		if (userOld != null) {
			String sql = "UPDATE USERS SET USERNAME='" + user.getUsernmae()
					+ "', PASSWORD='" + user.getPassword() + "', USERTYPE='"
					+ user.getType() + "' WHERE USERID  ='" + user.getId()
					+ "'";
			try {
				update(sql, false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			String sql = "INSERT INTO USERS VALUES ('" + user.getId() + "','"
					+ user.getUsernmae() + "', '" + user.getType() + "'," + "'"
					+ user.getPassword() + "'" + ");";

			try {
				insert(sql, false);
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	public void deleteUser(String usrid) {
		String sql = "UPDATE USERS SET USERTYPE ='" + Constant.USER_TYPE_F
				+ "' WHERE USERID='" + usrid + "'";
		try {
			delete(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List getUser(String type) {
		String sql = "SELECT * FROM USERS WHERE USERTYPE ='" + type + "'";
		List list = null;
		try {
			list = queryForList(sql, new UserRowMapper());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public HashMap getUserMap() {
		final String sql = "SELECT * FROM USERS";
		class QqueryStatementCallback implements StatementCallback<HashMap> {
			public HashMap doInStatement(Statement stmt) throws SQLException {
				HashMap results = new HashMap();
				ResultSet rs = stmt.executeQuery(sql);
				int rowNum = 0;
				while (rs.next()) {
					User user = new UserRowMapper().mapRow(rs, rowNum++);
					results.put(user.getId(), user);
				}
				return results;
			}

			public String getSql() {
				return null;
			}

			public boolean isWrite() {
				return false;
			}

			public String getAction() {
				return "S";
			}
		}
		try {
			return execute(new QqueryStatementCallback());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public User getUserByName(String username) {
		String sql = "SELECT * FROM USERS WHERE USERNAME ='" + username + "'";

		User users = null;
		try {
			users = (User) queryForObject(sql, new UserRowMapper());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	public User getUserByid(String id) {
		String sql = "SELECT * FROM USERS WHERE USERID ='" + id + "'";
		User users = null;
		try {
			users = (User) queryForObject(sql, new UserRowMapper());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	public void updateUser(User user) {
		String sql = "UPDATE USERS SET USERNAME='" + user.getUsernmae()
				+ "',PASSWORD ='" + user.getPassword() + "' WHERE USERID  ='"
				+ user.getId() + "'";

		try {
			update(sql, false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static class UserRowMapper implements RowMapper {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			String USERID = rs.getString("USERID");
			String USERNAME = rs.getString("USERNAME");
			String PASSWORD = rs.getString("PASSWORD");
			String USERTYPE = rs.getString("USERTYPE");

			User user = new User();

			if (USERID != null) {
				user.setId(USERID);
			}
			if (USERNAME != null) {
				user.setUsernmae(USERNAME);
			}
			if (PASSWORD != null) {
				user.setPassword(PASSWORD);
			}
			if (USERTYPE != null) {
				user.setType(USERTYPE);
			}
			return user;
		}
	}
}
