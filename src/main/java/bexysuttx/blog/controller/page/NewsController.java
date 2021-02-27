package bexysuttx.blog.controller.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bexysuttx.blog.Constants;
import bexysuttx.blog.controller.AbstractController;
import bexysuttx.blog.entity.Article;
import bexysuttx.blog.entity.Category;
import bexysuttx.blog.model.Items;
import bexysuttx.blog.model.Pagination;

@WebServlet({ "/news", "/news/*" })
public class NewsController extends AbstractController {
	private static final long serialVersionUID = 216595477139640552L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int offset = getOffset(req, Constants.LIMIT_ARTICLES_PER_PAGE);
		String requestUrl = req.getRequestURI();
		Items<Article> items = null;
		if (requestUrl.endsWith("/news") || requestUrl.endsWith("/news/")) {
			items = getBusinessService().listArticles(offset, Constants.LIMIT_ARTICLES_PER_PAGE);
			req.setAttribute("isNewsPage", Boolean.TRUE);
		} else {
			String categoryUrl = requestUrl.replace("/news", "");
			Category category = getBusinessService().findCategoryByUrl(categoryUrl);
			if (category == null) {
				resp.sendRedirect("/404?url=" + requestUrl);
				return;
			}
			items = getBusinessService().listArticlesByCategory(categoryUrl, offset, Constants.LIMIT_ARTICLES_PER_PAGE);
			req.setAttribute("selectedCategory", category);
		}
		req.setAttribute("list", items.getItems());
		Pagination pagination = new Pagination.Builder(requestUrl + "?", offset, items.getCount())
				.withLimit(Constants.LIMIT_ARTICLES_PER_PAGE).build();
		req.setAttribute("pagination", pagination);
		forwardToPage("news.jsp", req, resp);
	}
}
