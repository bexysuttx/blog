package bexysuttx.blog.controller.page;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import bexysuttx.blog.Constants;
import bexysuttx.blog.controller.AbstractController;
import bexysuttx.blog.entity.Article;
import bexysuttx.blog.entity.Comment;
import bexysuttx.blog.exception.RedirectToValidUrlException;

@WebServlet("/article/*")
public class ArticleController extends AbstractController {
	private static final long serialVersionUID = 853423043681640058L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestUrl = req.getRequestURI();
		try {
			viewArticle(requestUrl, req, resp);
		} catch (RedirectToValidUrlException e) {
			resp.sendRedirect(e.getUrl());
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			resp.sendRedirect("/news");
		}
	}

	private void viewArticle(String requestUrl, HttpServletRequest req, HttpServletResponse resp)
			throws NumberFormatException, RedirectToValidUrlException, ArrayIndexOutOfBoundsException, IOException,
			ServletException {
		String id = StringUtils.split(requestUrl, "/")[1];
		Article article = getBusinessService().viewArticle(Long.parseLong(id), requestUrl);
		if (article == null) {
			resp.sendRedirect("/404?url=" + requestUrl);
		} else {
			req.setAttribute("article", article);
			List<Comment> comments = getBusinessService().listComments(article.getId(), 0,
					Constants.LIMIT_COMMENTS_PER_PAGE);
			req.setAttribute("comments", comments);
			forwardToPage("article.jsp", req, resp);
		}
	}
}
