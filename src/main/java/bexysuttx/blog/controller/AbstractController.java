package bexysuttx.blog.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bexysuttx.blog.exception.ApplicationException;
import bexysuttx.blog.form.AbstractForm;
import bexysuttx.blog.service.BusinessService;
import bexysuttx.blog.service.impl.ServiceManager;

public abstract class AbstractController extends HttpServlet {
	private static final long serialVersionUID = 2401122740963576989L;
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private BusinessService businessService;

	public final BusinessService getBusinessService() {
		return businessService;
	}

	public int getOffset(HttpServletRequest req, int limit) {
		String val = req.getParameter("page");
		if (val != null) {
			int offset = (Integer.parseInt(val) - 1) * limit;
			return offset;
		} else {
			return 0;
		}

	}

	@Override
	public void init() throws ServletException {
		businessService = ServiceManager.getInstance(getServletContext()).getBusinessService();
	}

	public final void forwardToPage(String jspPage, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("currentPage", "page/" + jspPage);
		req.getRequestDispatcher("/WEB-INF/JSP/page-template.jsp").forward(req, resp);
	}

	public final void forwardToFragment(String fragment, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/JSP/fragment/" + fragment).forward(req, resp);
	}

	public final <T extends AbstractForm> T createForm(HttpServletRequest req, Class<T> formClass)
			throws ServletException {
		try {
			T form = formClass.newInstance();
			form.setLocale(req.getLocale());
			BeanUtils.populate(form, req.getParameterMap());
			return form;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new ApplicationException("Can't create form " + formClass + " for request: " + e.getMessage(), e);
		}
	}
}
