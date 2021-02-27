package bexysuttx.blog.controller.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import bexysuttx.blog.controller.AbstractController;
import bexysuttx.blog.entity.Article;
import bexysuttx.blog.exception.RedirectToValidUrlException;

@WebServlet("/article/*")
public class ArticleController extends AbstractController {
	private static final long serialVersionUID = 853423043681640058L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestUrl = req.getRequestURI();
		try {
			String id = StringUtils.split(requestUrl, "/")[1];
			Article article = getBusinessService().viewArticle(Long.parseLong(id), requestUrl);
			if (article == null) {
				resp.sendRedirect("/404?url=" + requestUrl);
			} else {
				req.setAttribute("article", article);
				forwardToPage("article.jsp", req, resp);
			}
		} catch (RedirectToValidUrlException e) {
			resp.sendRedirect(e.getUrl());
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			resp.sendRedirect("/news");
		}
	}
}
