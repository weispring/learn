package com.lxc.learn.generate.db;

import com.lxc.learn.generate.model.Table;

import java.util.List;

public interface TableParse {
	public List<String> getAllTables();
	
	public Table getTable(String tableName);
}
