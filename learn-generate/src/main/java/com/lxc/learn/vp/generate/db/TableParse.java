package com.lxc.learn.vp.generate.db;

import com.lxc.learn.vp.generate.model.Table;

import java.util.List;

public interface TableParse {
	public List<String> getAllTables();
	
	public Table getTable(String tableName);
}
