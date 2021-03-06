package bexysuttx.blog.controller.ajax;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bexysuttx.blog.controller.AbstractController;
import bexysuttx.blog.entity.Comment;
import bexysuttx.blog.exception.ApplicationException;
import bexysuttx.blog.exception.ValidateException;
import bexysuttx.blog.form.CommentForm;

@WebServlet("/ajax/comment")
public class NewCommentController extends AbstractController {
	private static final long serialVersionUID = -5926266725626355301L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			CommentForm form = createForm(req, CommentForm.class);
			Comment comment = getBusinessService().createComment(form);
			req.setAttribute("comments", Collections.singleton(comment));
			forwardToFragment("comments.jsp", req, resp);
		} catch (ValidateException e) {
			throw new ApplicationException("Invalid created comment try: " + e.getMessage(), e);
		}
	}
}
