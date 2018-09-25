package com.fafasoft.flow.dao;


import java.sql.SQLException;
import java.sql.Statement;


public interface StatementCallback<T> {
   T doInStatement(Statement stmt) throws SQLException;
   String getSql();
   boolean isWrite();
   String getAction();
}
