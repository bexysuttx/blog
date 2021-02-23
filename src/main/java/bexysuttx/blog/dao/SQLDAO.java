package bexysuttx.blog.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;

import bexysuttx.blog.dao.mapper.MapCategoryMapper;
import bexysuttx.blog.entity.Category;

public final class SQLDAO {
	private final QueryRunner sql = new QueryRunner();

	public Map<Integer, Category> mapCategories(Connection c) throws SQLException {
		return sql.query(c, "select * from category", new MapCategoryMapper());
	}

}
