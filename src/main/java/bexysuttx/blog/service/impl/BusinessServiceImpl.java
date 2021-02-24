package bexysuttx.blog.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.Map;

import javax.sql.DataSource;

import bexysuttx.blog.dao.SQLDAO;
import bexysuttx.blog.entity.Article;
import bexysuttx.blog.entity.Category;
import bexysuttx.blog.exception.ApplicationException;
import bexysuttx.blog.model.Items;
import bexysuttx.blog.service.BusinessService;

class BusinessServiceImpl implements BusinessService {
	private final DataSource dataSource;
	private final SQLDAO sql;
	
	BusinessServiceImpl(ServiceManager serviceManager) {
		super();
		this.dataSource = serviceManager.basicDataSource;
		this.sql = new SQLDAO();
	}

	@Override
	public Map<Integer, Category> mapCategories() {
		try (Connection c = dataSource.getConnection()) {
			return sql.mapCategories(c);
		} catch (SQLException e) {
			throw new ApplicationException("Can't execute db command: " + e.getMessage(), e);
		}
	}
	
	@Override
	public Items<Article> listArticles(int offset, int limit) {
		try (Connection c = dataSource.getConnection()) {
			Items<Article> items = new Items<>();
			items.setItems(sql.listArticles(c, offset, limit));
			items.setCount(sql.countArticles(c));
			return items;
		} catch (SQLException e) {
			throw new ApplicationException("Can't execute db command: " + e.getMessage(), e);
		}
	}
}
