package com.fafasoft.flow.dao.impl;

import com.fafasoft.flow.dao.RowMapper;
import com.fafasoft.flow.pojo.UserRight;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-6-20
 * Time: 13:01:22

 */
public class UserRightDAOImpl extends BaseDAO  {
    public void add(UserRight userRight) {
         String sql = "INSERT INTO USERRIGHTS VALUES ('" + userRight.getUserId() + "','" + userRight.getRight() + "');";
            boolean isin = false;
            Statement stat = null;

            try {
               insert(sql,false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public UserRight get(String username) {
        String sql = "SELECT * FROM USERRIGHTS WHERE USERNAME ='" + username + "'";
        UserRight users = null;

        try {
             users = (UserRight)queryForObject(sql,new UserRightRowMapper());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void update(UserRight userRight) {
        String sql = "UPDATE USERRIGHTS SET RIGHTS ='" + userRight.getRight() + "' WHERE USERNAME  ='"+ userRight.getUserId() + "'";
        try {
           update(sql,false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(String username) {
       String sql = "DELETE FROM USERRIGHTS WHERE USERNAME='" + username + "'";
        try {
            delete(sql);
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    private static class UserRightRowMapper implements RowMapper{
        public UserRight mapRow(ResultSet rs, int rowNum) throws SQLException {
            String USERNAME = rs.getString("USERNAME");
            String RIGHTS = rs.getString("RIGHTS");

            UserRight user = new UserRight();

            if (USERNAME != null) {
                user.setUserId(USERNAME);
            }
            if (RIGHTS != null) {
                user.setRight(RIGHTS);
            }
            return user;
        }
    }
}
