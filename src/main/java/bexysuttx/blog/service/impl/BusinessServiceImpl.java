package bexysuttx.blog.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import bexysuttx.blog.dao.SQLDAO;
import bexysuttx.blog.entity.Account;
import bexysuttx.blog.entity.Article;
import bexysuttx.blog.entity.Category;
import bexysuttx.blog.entity.Comment;
import bexysuttx.blog.exception.ApplicationException;
import bexysuttx.blog.exception.RedirectToValidUrlException;
import bexysuttx.blog.form.CommentForm;
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

	@Override
	public Items<Article> listArticlesByCategory(String categoryUrl, int offset, int limit) {
		try (Connection c = dataSource.getConnection()) {
			Items<Article> items = new Items<Article>();
			items.setItems(sql.listArticlesByCategory(c, categoryUrl, offset, limit));
			items.setCount(sql.countArticlesByCategory(c, categoryUrl));
			return items;
		} catch (SQLException e) {
			throw new ApplicationException("Can't execute db command: " + e.getMessage(), e);
		}
	}

	@Override
	public Category findCategoryByUrl(String categoryUrl) {
		try (Connection c = dataSource.getConnection()) {
			return sql.findCategoryByUrl(c, categoryUrl);
		} catch (SQLException e) {
			throw new ApplicationException("Can't execute db command: " + e.getMessage(), e);
		}

	}

	@Override
	public Items<Article> listArticlesBySearchQuery(String query, int offset, int limit) {
		try (Connection c = dataSource.getConnection()) {
			Items<Article> items = new Items<Article>();
			items.setItems(sql.listArticlesBySearchQuery(c, query, offset, limit));
			items.setCount(sql.countArticleBySearchQuery(c, query));
			return items;
		} catch (SQLException e) {
			throw new ApplicationException("Can't execute db command: " + e.getMessage(), e);
		}
	}

	@Override
	public Article viewArticle(Long idArticle, String requestUrl) throws RedirectToValidUrlException {
		try (Connection c = dataSource.getConnection()) {
			Article article = sql.viewArticle(c, idArticle);
			if (article == null) {
				return null;
			}
			if (!article.getArticleLink().equals(requestUrl)) {
				throw new RedirectToValidUrlException(article.getArticleLink());
			} else {
				article.setViews(article.getViews() + 1);
				sql.updateArticle(c, article);
				c.commit();
				return article;
			}
		} catch (SQLException e) {
			throw new ApplicationException("Can't execute db command: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Comment> listComments(long idArticle, int offset, int limit) {
		try (Connection c = dataSource.getConnection()) {
			return sql.listComments(c, idArticle, offset, limit);
		} catch (SQLException e) {
			throw new ApplicationException("Can't execute db command:" + e.getMessage(), e);
		}

	}

	@Override
	public Comment createComment(CommentForm form) {
		Comment comment = new Comment();
		comment.setContent("test content");
		comment.setCreated(new Timestamp(System.currentTimeMillis()));
		Account a = new Account();
		a.setName("test_account");
		comment.setAccount(a);
		comment.setId(0L);
		return comment;
	}
}
