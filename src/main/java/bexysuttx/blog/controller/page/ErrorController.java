package bexysuttx.blog.controller.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bexysuttx.blog.controller.AbstractController;

@WebServlet({"/error", "/404"})
public class ErrorController extends AbstractController {
	private static final long serialVersionUID = -6350950464997810953L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean i404= "/404".equals(req.getRequestURI());
		req.setAttribute("i404", i404);
		req.setAttribute("url", req.getParameter("url")); 
		forwardToPage("error.jsp", req, resp);
	}
}
