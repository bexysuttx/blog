package bexysuttx.blog.controller.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bexysuttx.blog.controller.AbstractController;

@WebServlet({"/news", "/news/*"})
public class newsController extends AbstractController {
	private static final long serialVersionUID = 2617306785433062648L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forwardToPage("news.jsp", req, resp);
	}

}
