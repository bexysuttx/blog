package bexysuttx.blog.service;

import java.util.Map;

import bexysuttx.blog.entity.Article;
import bexysuttx.blog.entity.Category;
import bexysuttx.blog.exception.RedirectToValidUrlException;
import bexysuttx.blog.model.Items;

public interface BusinessService {

	Map<Integer, Category> mapCategories();
	
	Items<Article> listArticles(int offset, int limit);
	
	Items<Article> listArticlesByCategory(String categoryUrl, int offset, int limit);
	
	Category findCategoryByUrl(String categoryUrl);
	
	Items<Article> listArticlesBySearchQuery(String query, int offset, int limit);
	
	Article viewArticle(Long idArticle, String requestUrl) throws RedirectToValidUrlException;
}
