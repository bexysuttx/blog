package bexysuttx.blog.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.RowProcessor;

import bexysuttx.blog.entity.Category;

public class MapCategoryMapper implements ResultSetHandler<Map<Integer, Category>> {
	RowProcessor convert = new BasicRowProcessor();

	@Override
	public Map<Integer, Category> handle(ResultSet rs) throws SQLException {
		Map<Integer, Category> map = new HashMap<Integer, Category>();
		while (rs.next()) {
			Category category = convert.toBean(rs, Category.class);
			map.put(category.getId(), category);
		}
		return map;
	}

}
