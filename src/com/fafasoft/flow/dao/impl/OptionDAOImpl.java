package com.fafasoft.flow.dao.impl;

import com.fafasoft.flow.dao.RowMapper;
import com.fafasoft.flow.pojo.Option;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: wangwei
 * Date: 2010-1-2
 * Time: 11:01:01
 */
public class OptionDAOImpl extends BaseDAO {
    public boolean updateOptionTextValue(Option option) {
        String sql = "UPDATE OPTIONS SET TEXTVALUE='" + option.getText() + "'  where KEYID ='" + option.getId() + "'";
        boolean isin = true;

        try {
            update(sql,true);
        } catch (SQLException e) {
            isin = false;
            e.printStackTrace();
        }
        return isin;
    }

    public boolean addOption(Option option) {
        String sql = "INSERT INTO OPTIONS VALUES ('" + option.getId() + "'," +
                "'" + option.getText() + "',"
                + "'" + option.getType() + "',"
                + "'" + option.getParentId() + "',"
                + "'" + option.getNodeId() + "'" +
                ");";

        boolean isin = true;

        try {
            insert(sql,false);
        } catch (SQLException e) {
            isin = false;
            e.printStackTrace();
        }
        return isin;
    }

    public boolean deleteOptionByParentId(String type, String parentId) {
        List list = getOption(type, parentId);
        String sql = "DELETE FROM OPTIONS WHERE OTYPE='" + type + "' AND PARENTID='" + parentId + "'";
        boolean isin = true;
        try {
            delete(sql);
        } catch (SQLException e) {
            isin = false;
            e.printStackTrace();
        }

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Option op = (Option) list.get(i);
                deleteOptionByParentId(type, op.getNodeId());
            }
        }
        return isin;
    }

    public boolean deleteOption(String id) {
        String sql = "DELETE FROM OPTIONS WHERE KEYID='" + id + "'";
        boolean isin = true;
        try {
            delete(sql);
        } catch (SQLException e) {
            isin = false;
            e.printStackTrace();
        }
        return isin;
    }


    public List getOption() {
        String sql = "SELECT * FROM OPTIONS ";

        List list = null;
        try {
            list = queryForList(sql, new OptionRowMapper());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getOption(String type) {
        String sql = "SELECT * FROM OPTIONS WHERE OTYPE='" + type + "'";
        List list = null;
        try {
            list = queryForList(sql, new OptionRowMapper());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getOption(String type, String parentId) {
        String sql = "SELECT * FROM OPTIONS WHERE OTYPE='" + type + "' AND PARENTID='" + parentId + "' ORDER BY TEXTVALUE ASC";
        List list = null;
        try {
            list = queryForList(sql, new OptionRowMapper());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static class OptionRowMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Option stock = new Option();
            String id = rs.getString("KEYID");
            String text = rs.getString("TEXTVALUE");
            try {
                String OTYPE = rs.getString("OTYPE");
                if (OTYPE != null) {
                    stock.setType(OTYPE);
                }

                String PARENTID = rs.getString("PARENTID");
                if (PARENTID != null) {

                    stock.setParentId(PARENTID);
                }
                String NODEID = rs.getString("NODEID");
                if (NODEID != null) {
                    stock.setNodeId(NODEID);
                }
            } catch (org.h2.jdbc.JdbcSQLException e) {
            }
            if (id != null) {
                stock.setId(id);
            }
            if (text != null) {
                stock.setText(text);
            }
            return stock;
        }
    }
}
