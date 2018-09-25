package com.fafasoft.flow.dao.impl;

import com.fafasoft.flow.dao.RowMapper;
import com.fafasoft.flow.pojo.Config;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * @author liyc
 * @version 1.0
 * @since JDK1.5
 */
public class ConfigDAOImpl extends BaseDAO {

    public boolean addConfigs(Config[] configs) {
        boolean isin = false;
        String[] strings = new String[configs.length];
        for (int i = 0; i < configs.length; i++) {
            Config config = configs[i];
            if (config != null) {
                String sql = "INSERT INTO CONFIG VALUES ('" + config.getKey() + "'," +
                        "'" + config.getValue() + "'" +
                        ");";
                Config configold = getConfig(config.getKey());
                if (configold != null) {
                    sql = " UPDATE CONFIG SET  TEXTVALUE ='" + config.getValue() + "'  WHERE KEYID ='" + config.getKey() + "'";
                }
                strings[i] = sql;
            }
        }
        try {
            batchUpdate(strings,false);
            isin = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isin;
    }

    public boolean saveOrUpdateConfig(Config config) {
        Config oldconf = getConfig(config.getKey());
        if (oldconf != null) {
            updateConfig(config);
        } else {
            addConfig(config);
        }
        return true;
    }

    public boolean updateConfig(Config config) {
        String sql = "UPDATE CONFIG SET TEXTVALUE ='" + config.getValue() + "' WHERE KEYID='" + config.getKey() + "'";
        boolean isin = false;
        try {
            super.update(sql,false);
            isin = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isin;
    }

    public boolean addConfig(Config config) {
        String sql = "INSERT INTO CONFIG VALUES ('" + config.getKey() + "'," +
                "'" + config.getValue() + "'" +
                ");";
        boolean isin = false;

        try {
            super.insert(sql,false);
            isin = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isin;
    }

    public Config getConfig(String key) {

        String sql = "SELECT * FROM CONFIG WHERE KEYID ='" + key + "'";

        Config config = null;
        try {
            config = (Config) super.queryForObject(sql, new ConfigRowMapper());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return config;
    }

    private static class ConfigRowMapper implements RowMapper {
        public Config mapRow(ResultSet rs, int rowNum) throws SQLException {
            String catno = rs.getString("KEYID");
            String amount = rs.getString("TEXTVALUE");

            Config config = new Config();
            if (catno != null) {
                config.setKey(catno);
            }
            if (amount != null) {
                config.setValue(amount);
            }
            return config;
        }
    }
}