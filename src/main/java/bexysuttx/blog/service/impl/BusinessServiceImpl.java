package bexysuttx.blog.service.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bexysuttx.blog.dao.SQLDAO;
import bexysuttx.blog.entity.Account;
import bexysuttx.blog.entity.Article;
import bexysuttx.blog.entity.Category;
import bexysuttx.blog.entity.Comment;
import bexysuttx.blog.exception.ApplicationException;
import bexysuttx.blog.exception.RedirectToValidUrlException;
import bexysuttx.blog.exception.ValidateException;
import bexysuttx.blog.form.CommentForm;
import bexysuttx.blog.model.Items;
import bexysuttx.blog.model.SocialAccount;
import bexysuttx.blog.service.AvatarService;
import bexysuttx.blog.service.BusinessService;
import bexysuttx.blog.service.I18nService;
import bexysuttx.blog.service.SocialService;

class BusinessServiceImpl implements BusinessService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BusinessServiceImpl.class);
	private final DataSource dataSource;
	private final SQLDAO sql;
	private final SocialService socialService;
	private final AvatarService avatarService;
	private final I18nService i18nService;

	BusinessServiceImpl(ServiceManager serviceManager) {
		super();
		this.dataSource = serviceManager.basicDataSource;
		this.sql = new SQLDAO();
		this.socialService = serviceManager.socialService;
		this.avatarService = serviceManager.avararService;
		this.i18nService = serviceManager.i18nService;
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
	public Comment createComment(CommentForm form) throws ValidateException {
		form.validate(i18nService);
		String newAvatarPath = null;
		try (Connection c = dataSource.getConnection()) {
			SocialAccount socialAccount = socialService.getSocialAccount(form.getAuthToken());
			Account account = sql.findAccountByEmail(c, socialAccount.getEmail());
			if (account == null) {
				newAvatarPath = avatarService.downloadAvatar(socialAccount.getAvatar());
				account = sql.createNewAccount(c, socialAccount.getEmail(), socialAccount.getName(), newAvatarPath);
			}
			Comment comment = sql.createComment(c, form, account.getId());
			comment.setAccount(account);
			Article article = sql.findArticleForNewCommentNotification(c, form.getIdArticle());
			article.setComments(sql.countComments(c, article.getId()));
			sql.updateArticleComments(c, article);
			c.commit();
			// TODO

			return comment;
		} catch (RuntimeException | SQLException | IOException e) {
			if (avatarService.deleteAvatarIfExists(newAvatarPath)) {
				LOGGER.info("Avatar" + newAvatarPath + "deleted");
			}
			throw new ApplicationException("Can't create new comment: " + e.getMessage(), e);
		}
	}
}
