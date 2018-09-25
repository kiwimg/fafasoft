package com.fafasoft.flow.dao.impl;

import com.fafasoft.flow.dao.RowMapper;
import com.fafasoft.flow.pojo.Suppliers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 中州软讯</p>
 * User: Administrator
 *
 * @author liyc
 * @version 1.0
 * @since JDK1.6
 *        Date: 2010-9-21
 */
public class SuppliersDAOImpl extends BaseDAO{
    public void insertSuppliers(Suppliers suppliers) {
        String sql = "INSERT INTO SUPPLIERS ( SUPPLIERSNO, SUPPLIERSNAME , CONTACT , ADDRESS , PHONE , EMAIL , FAX , QQ , REMARKS , ZIPCODE ) VALUES (" +
                "'" + suppliers.getSuppliersno() + "'," +
                "'" + suppliers.getSuppliersName() + "'," +
                "'" + suppliers.getContact() + "'," +
                "'" + suppliers.getAddress() + "'," +
                "'" + suppliers.getPhone() + "'," +
                "'" + suppliers.getEmail() + "'," +
                "'" + suppliers.getFax() + "'," +
                "'" + suppliers.getQq() + "'," +
                "'" + suppliers.getRemarks() + "'," +
                "'" + suppliers.getZipcode() + "'" +
                " );";

        try {
            super.insert(sql,true);
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public void deleteSuppliers(String suppliersno) {
        String sql = "DELETE FROM SUPPLIERS WHERE SUPPLIERSNO = '" + suppliersno + "'";
        try {
            super.delete(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSuppliers(Suppliers suppliers) {
        String sql = "UPDATE SUPPLIERS SET " +
                "SUPPLIERSNAME ='" + suppliers.getSuppliersName() + "'," +
                "CONTACT ='" + suppliers.getContact() + "'," +
                "ADDRESS ='" + suppliers.getAddress() + "'," +
                "PHONE ='" + suppliers.getPhone() + "'," +
                "EMAIL ='" + suppliers.getEmail() + "'," +
                "FAX ='" + suppliers.getFax() + "'," +
                "QQ ='" + suppliers.getQq() + "'," +
                "REMARKS ='" + suppliers.getRemarks() + "'" +
                " WHERE SUPPLIERSNO='" + suppliers.getSuppliersno() + "'";
        try {
            update(sql,true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Suppliers getSuppliersByNo(String no) {
        String sql = "SELECT * FROM SUPPLIERS  WHERE SUPPLIERSNO='" + no + "'";
        Suppliers suppliers = null;
        try {
            suppliers = (Suppliers) queryForObject(sql, new SuppliersRowMapper());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    public List getSuppliers() {
        String sql = "SELECT * FROM SUPPLIERS ";
        List list = null;
        try {
            list = queryForList(sql, new SuppliersRowMapper());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List getSuppliers(int start, int max) {
        String sql = "SELECT * FROM SUPPLIERS  LIMIT " + max + " OFFSET " + start;
        List list = null;
        try {
            list = queryForList(sql, new SuppliersRowMapper());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getSuppliersSize() {
        String sql = "SELECT COUNT(*)  FROM SUPPLIERS ";
        int r = 0;
        try {
            r = getTotalRow(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }

    public List getSuppliers(String name, String concat, String phone, int start, int max) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM SUPPLIERS ");
        if ((name != null && !"".equals(name.trim())) || (concat != null && !"".equals(concat.trim()))
                || (phone != null && !"".equals(phone.trim()))) {
            sql.append(" WHERE ");
        }
        if (name != null && !"".equals(name.trim())) {
            sql.append(" SUPPLIERSNAME='").append(name).append("'");
        }
        if (concat != null && !"".equals(concat.trim())) {
            sql.append(" or ");
            sql.append(" CONTACT='").append(concat).append("'");
        }
        if (phone != null && !"".equals(phone.trim())) {
            sql.append(" or ");
            sql.append(" PHONE='").append(phone).append("'");
        }
        sql.append(" LIMIT ").append(max).append(" OFFSET ").append(start);
        List list = null;
        try {
            list = queryForList(String.valueOf(sql), new SuppliersRowMapper());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getSuppliersSize(String name, String concat, String phone) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT COUNT(*)  FROM SUPPLIERS ");
        if ((name != null && !"".equals(name.trim())) || (concat != null && !"".equals(concat.trim()))
                || (phone != null && !"".equals(phone.trim()))) {
            sql.append(" WHERE ");
        }
        if (name != null && !"".equals(name.trim())) {
            sql.append(" SUPPLIERSNAME='").append(name).append("'");
        }
        if (concat != null && !"".equals(concat.trim())) {
            sql.append(" or ");
            sql.append(" CONTACT='").append(concat).append("'");
        }
        if (phone != null && !"".equals(phone.trim())) {
            sql.append(" or ");
            sql.append(" PHONE='").append(phone).append("'");
        }
        int r = 0;
        try {
            r = getTotalRow(String.valueOf(sql));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }

    private static class SuppliersRowMapper implements RowMapper {

        public Suppliers mapRow(ResultSet rs, int rowNum) throws SQLException {
            Suppliers suppliers = new Suppliers();
            String SUPPLIERSNO = rs.getString("SUPPLIERSNO");
            String SUPPLIERSNAME = rs.getString("SUPPLIERSNAME");
            String CONTACT = rs.getString("CONTACT");
            String ADDRESS = rs.getString("ADDRESS");
            String PHONE = rs.getString("PHONE");
            String EMAIL = rs.getString("EMAIL");
            String FAX = rs.getString("FAX");
            String QQ = rs.getString("QQ");
            String REMARKS = rs.getString("REMARKS");
            String ZIPCODE = rs.getString("ZIPCODE");
            suppliers.setSuppliersno(SUPPLIERSNO);
            suppliers.setSuppliersName(SUPPLIERSNAME);
            suppliers.setContact(CONTACT);
            suppliers.setAddress(ADDRESS);
            suppliers.setPhone(PHONE);
            suppliers.setEmail(EMAIL);
            suppliers.setFax(FAX);
            suppliers.setQq(QQ);
            suppliers.setRemarks(REMARKS);
            suppliers.setZipcode(ZIPCODE);
            return suppliers;
        }
    }
}
