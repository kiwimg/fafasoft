package com.fafasoft.flow.dao.impl;

import com.fafasoft.flow.dao.RowMapper;
import com.fafasoft.flow.pojo.Accounts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;


public class AccountsDaoImpl extends BaseDAO {


	public void add(Accounts accounts) {
		if (accounts.getId() == null) {
			accounts.setId((String.valueOf(UUID.randomUUID().toString()
					.replaceAll("-", ""))));
		}
		String sql = "INSERT INTO ACCOUNTS  VALUES ('" + accounts.getId()
				+ "','" + accounts.getTargetId() + "','"
				+ accounts.getCategories() + "','" + accounts.getAmount()
				+ "','" + accounts.getStatus() + "','" + accounts.getNote()
				+ "','" + accounts.getDate() + "','" + accounts.getFlag() + "'"
				+ ");";
	
		try {
			insert(sql,true);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public void deleteById(String id) {
		String sql = "DELETE FROM ACCOUNTS WHERE AID='" + id + "';";
		boolean isin = false;
		try {
			delete(sql);
			isin = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	public List getAccountsList() {
		List list = null;
		String sql = "SELECT * FROM ACCOUNTS";
		try {
			list = queryForList(sql, new AccountsRowMapper());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List getByTargetId(String customId, String flag) {
		List list = null;
		String sql = "SELECT * FROM ACCOUNTS WHERE TARGETID='" + customId
				+ "' AND FLAG='" + flag + "';";
		try {
			list = queryForList(sql, new AccountsRowMapper());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}


	public int getAccountsSize() {
		StringBuffer sqlbur = new StringBuffer();
		sqlbur.append("SELECT  COUNT(*) FROM ACCOUNTS");

		int list = 0;
		try {

			list = getTotalRow(String.valueOf(sqlbur));
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return list;
	}


	public List getAccountsList(int start, int max) {
		List list = null;

		StringBuffer sqlbur = new StringBuffer();
		sqlbur.append("SELECT * FROM ACCOUNTS ");

		if (max > 0) {
			sqlbur.append(" LIMIT ").append(max).append(" OFFSET ").append(
					start);
		}
		try {
			list = queryForList(String.valueOf(sqlbur), new AccountsRowMapper());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public int getAccountsSize(String flag,String targetId, String staus, String type) {
		StringBuffer sqlbur = new StringBuffer();
		sqlbur.append("SELECT  COUNT(*) FROM ACCOUNTS WHERE 1=1");
		if (flag != null) {
			sqlbur.append(" AND FLAG='").append(flag).append("'");
		}
	
		if (targetId != null) {
			sqlbur.append(" AND TARGETID='").append(targetId).append("'");
		}
		if (staus != null) {
			sqlbur.append(" AND STATUS='").append(staus).append("'");
		}
		if (type != null) {
			sqlbur.append(" AND CATEGORIES='").append(type).append("'");
		}

		int list = 0;
		try {

			list = getTotalRow(String.valueOf(sqlbur));
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return list;
	}
	
	public List getAccountsList(String flag,String targetId, String staus, String type,
			int start, int max) {
		StringBuffer sqlbur = new StringBuffer();
		sqlbur.append("SELECT  * FROM ACCOUNTS WHERE 1=1");
		if (flag != null) {
			sqlbur.append(" AND FLAG='").append(flag).append("'");
		}
		if (targetId != null) {
			sqlbur.append(" AND TARGETID='").append(targetId).append("'");
		}
		if (staus != null) {
			sqlbur.append(" AND STATUS='").append(staus).append("'");
		}
		if (type != null) {
			sqlbur.append(" AND CATEGORIES='").append(type).append("'");
		}

		if (max > 0) {
			sqlbur.append(" LIMIT ").append(max).append(" OFFSET ").append(
					start);
		}
		List list = null;
		try {
			list = queryForList(String.valueOf(sqlbur), new AccountsRowMapper());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public double sumAccounts(String flag,String targetId, String staus, String type) {
		StringBuffer sql1 = new StringBuffer();
		sql1.append("SELECT SUM (AMOUNT) FROM ACCOUNTS WHERE 1=1");
		if (flag != null) {
			sql1.append(" AND FLAG='").append(flag).append("'");
		}
		double a =0;
		if (targetId != null) {
			sql1.append(" AND TARGETID='").append(targetId).append("'");
		}
		if (staus != null) {
			sql1.append(" AND STATUS='").append(staus).append("'");
		}
		if (type != null) {
			sql1.append(" AND CATEGORIES='").append(type).append("'");
		}
		try {

			a = super.getTotalRowForDouble(String.valueOf(sql1));
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return a;
	}

	public Accounts getById(String id) {
		String sql = "SELECT * FROM ACCOUNTS WHERE AID='" + id + "';";
		Accounts sccounts = null;

		try {
			sccounts = (Accounts) queryForObject(sql, new AccountsRowMapper());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sccounts;
	}


	public void update(Accounts accounts) {
		boolean isin = false;
		// AID CUSTOMID CATEGORIES AMOUNT STATUS NOTE ADATE
		String sql = "UPDATE ACCOUNTS SET ADATE ='" + accounts.getDate()
				+ "', TARGETID='" + accounts.getTargetId() + "',CATEGORIES ='"
				+ accounts.getCategories() + "',AMOUNT ='"
				+ accounts.getAmount() + "',STATUS ='" + accounts.getStatus()
				+ "',NOTE ='" + accounts.getNote() + "',FLAG='"
				+ accounts.getFlag() + "' WHERE AID='" + accounts.getId() + "'";

		try {
			update(sql,false);
			isin = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static class  AccountsRowMapper implements RowMapper {
		public Accounts mapRow(ResultSet rs, int rowNum) throws SQLException {
			String id = rs.getString("AID");
			String CUSTOMID = rs.getString("TARGETID");
			String CATEGORIES = rs.getString("CATEGORIES");
			String AMOUNT = rs.getString("AMOUNT");
			String STATUS = rs.getString("STATUS");
			String NOTE = rs.getString("NOTE");
			String ADATE = rs.getString("ADATE");
			String FLAG = rs.getString("FLAG");
			Accounts accounts = new Accounts();
			accounts.setId(id);
			accounts.setTargetId(CUSTOMID);
			accounts.setCategories(CATEGORIES);
			accounts.setFlag(FLAG);
			accounts.setStatus(STATUS);
			if (AMOUNT != null) {
				accounts.setAmount(Double.parseDouble(AMOUNT));

			}
			accounts.setDate(ADATE);
			accounts.setNote(NOTE);
			return accounts;
		}
	}

}
