 package bexysuttx.blog.service;

import java.util.List;
import java.util.Map;

import bexysuttx.blog.entity.Article;
import bexysuttx.blog.entity.Category;
import bexysuttx.blog.entity.Comment;
import bexysuttx.blog.exception.RedirectToValidUrlException;
import bexysuttx.blog.exception.ValidateException;
import bexysuttx.blog.form.CommentForm;
import bexysuttx.blog.model.Items;

public interface BusinessService {

	Map<Integer, Category> mapCategories();
	
	Items<Article> listArticles(int offset, int limit);
	
	Items<Article> listArticlesByCategory(String categoryUrl, int offset, int limit);
	
	Category findCategoryByUrl(String categoryUrl);
	
	Items<Article> listArticlesBySearchQuery(String query, int offset, int limit);
	
	Article viewArticle(Long idArticle, String requestUrl) throws RedirectToValidUrlException;
	
	List<Comment> listComments(long idArticle, int offset, int limit) ;
	
	Comment createComment(CommentForm form) throws ValidateException;
}

