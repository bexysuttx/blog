package bexysuttx.blog.controller.page;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import bexysuttx.blog.Constants;
import bexysuttx.blog.controller.AbstractController;
import bexysuttx.blog.entity.Article;
import bexysuttx.blog.model.Items;
import bexysuttx.blog.model.Pagination;

@WebServlet("/search")
public class SearchController extends AbstractController {
	private static final long serialVersionUID = 4496474703229174274L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String query = req.getParameter("query");
		if (StringUtils.isNotBlank(query)) {
			int offset = getOffset(req, Constants.LIMIT_ARTICLES_PER_PAGE);
			Items<Article> items = getBusinessService().listArticlesBySearchQuery(query, offset,
					Constants.LIMIT_ARTICLES_PER_PAGE);
			Pagination pagination = new Pagination.Builder("/search?query=" + URLEncoder.encode(query, "utf8")+"&", offset,
					items.getCount()).build();
			req.setAttribute("pagination", pagination);
			req.setAttribute("list", items.getItems());
			req.setAttribute("count", items.getCount());
			req.setAttribute("searchQuery", query);
			forwardToPage("search.jsp", req, resp);
		} else {
			resp.sendRedirect("/news");
		}
	}

}
