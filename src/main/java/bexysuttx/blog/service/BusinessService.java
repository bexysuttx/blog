package bexysuttx.blog.service;

import java.util.Map;

import bexysuttx.blog.entity.Article;
import bexysuttx.blog.entity.Category;
import bexysuttx.blog.model.Items;

public interface BusinessService {

	Map<Integer, Category> mapCategories();
	
	Items<Article> listArticles(int limit, int offset);
}
