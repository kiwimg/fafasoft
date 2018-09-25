package com.fafasoft.flow.dao;

public interface BatchUpdateStatementCallback<T> extends StatementCallback<T> {
	   
	   String[] getSqlArray();
}
