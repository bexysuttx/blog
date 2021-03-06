package bexysuttx.blog.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import bexysuttx.blog.dao.mapper.ArticleMapper;
import bexysuttx.blog.dao.mapper.CommentMapper;
import bexysuttx.blog.dao.mapper.ListMapper;
import bexysuttx.blog.dao.mapper.MapCategoryMapper;
import bexysuttx.blog.entity.Account;
import bexysuttx.blog.entity.Article;
import bexysuttx.blog.entity.Category;
import bexysuttx.blog.entity.Comment;
import bexysuttx.blog.form.CommentForm;

public final class SQLDAO {
	private final QueryRunner sql = new QueryRunner();

	public Map<Integer, Category> mapCategories(Connection c) throws SQLException {
		return sql.query(c, "select * from category", new MapCategoryMapper());
	}

	public List<Article> listArticles(Connection c, int offset, int limit) throws SQLException {
		return sql.query(c, "select * from article a order by a.id desc limit ? offset ?",
				new ListMapper<>(new ArticleMapper()), limit, offset);
	}

	public int countArticles(Connection c) throws SQLException {
		return sql.query(c, "select count(*) from article a", new ScalarHandler<Number>()).intValue();
	}

	public List<Article> listArticlesByCategory(Connection c, String categoryUrl, int offset, int limit)
			throws SQLException {
		return sql.query(c,
				"select a.* from article a, category c where c.id=a.id_category and c.url=? order by a.id desc limit ? offset ?",
				new ListMapper<>(new ArticleMapper()), categoryUrl, limit, offset);
	}

	public int countArticlesByCategory(Connection c, String categoryUrl) throws SQLException {
		return sql.query(c, "select count(a.id) from article a, category c where c.id=a.id_category and c.url=?",
				new ScalarHandler<Number>(), categoryUrl).intValue();
	}

	public Category findCategoryByUrl(Connection c, String categoryUrl) throws SQLException {
		return sql.query(c, "select * from category c where c.url=?", new BeanHandler<>(Category.class), categoryUrl);
	}

	public List<Article> listArticlesBySearchQuery(Connection c, String query, int offset, int limit)
			throws SQLException {
		String q = "%" + query + "%";
		return sql.query(c,
				"select a.* from article a, category c where (a.title ilike ? or a.content ilike ?) and c.id=a.id_category order by a.id desc limit ? offset ?",
				new ListMapper<>(new ArticleMapper()), q, q, limit, offset);
	}

	public int countArticleBySearchQuery(Connection c, String query) throws SQLException {
		String q = "%" + query + "%";
		return sql.query(c,
				"select count(a.*) from article a, category c where (a.title ilike ? or a.content ilike ?) and c.id=a.id_category",
				new ScalarHandler<Number>(), q, q).intValue();
	}

	public Article viewArticle(Connection c, Long idArticle) throws SQLException {
		return sql.query(c, "select * from article a where  a.id=?", new ArticleMapper(), idArticle);
	}

	public void updateArticle(Connection c, Article article) throws SQLException {
		sql.update(c, "update article set views=? where id=?", article.getViews(), article.getId());
	}

	public List<Comment> listComments(Connection c, long idArticle, int offset, int limit) throws SQLException {
		return sql.query(c,
				"select c.*, a.name, a.email, a.avatar, a.created as accountCreated  from comment c, account a where c.id_article=? and c.id_account=a.id order by c.id desc limit ? offset ?",
				new ListMapper<>(new CommentMapper(true)), idArticle, limit, offset);
	}

	public Account findAccountByEmail(Connection c, String email) throws SQLException {
		return sql.query(c, "select * from account a where a.email=?", new BeanHandler<>(Account.class), email);
	}

	public Account createNewAccount(Connection c, String email, String name, String avatar) throws SQLException {
		return sql.insert(c, "insert into account(id,email,name,avatar) values(nextval('account_seq'),?,?,?)",
				new BeanHandler<>(Account.class), email, name, avatar);
	}

	public Comment createComment(Connection c, CommentForm form, long idAccount) throws SQLException {
		return sql.insert(c,
				"insert into comment(id,id_account, id_article,content) values(nextval('comment_seq'),?,?,?)",
				new CommentMapper(false), idAccount, form.getIdArticle(), form.getContent());
	}

	public Article findArticleForNewCommentNotification(Connection c, long id) throws SQLException {
		return sql.query(c, "select a.id, a.id_category, a.url, a.title from article a where a.id=? ",
				new ArticleMapper(), id);
	}

	public int countComments(Connection c, long id) throws SQLException {
		return sql.query(c, "select count(*) from comment where id_article=?", new ScalarHandler<Number>(), id)
				.intValue();
	}

	public void updateArticleComments(Connection c, Article article) throws SQLException {
		 sql.update(c, "update article set comments=? where id=?", article.getComments(), article.getId());
	}
}
