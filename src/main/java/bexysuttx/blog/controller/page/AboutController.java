package bexysuttx.blog.controller.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bexysuttx.blog.controller.AbstractController;

@WebServlet("/about")
public class AboutController extends AbstractController{
	private static final long serialVersionUID = -8074597209389708107L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forwardToPage("about.jsp", req, resp);
	}

}
