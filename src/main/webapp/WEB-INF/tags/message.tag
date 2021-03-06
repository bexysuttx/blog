<%@ tag import="bexysuttx.blog.service.I18nService"%>
<%@ tag import="bexysuttx.blog.service.impl.ServiceManager"%>
<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ attribute name="key" required="true" type="java.lang.String"%>
<%@ attribute name="args" required="false" type="java.lang.String"%>

<%
I18nService i18nService = ServiceManager.getInstance(request.getServletContext()).getI18nService();
String value = null;
if (args != null) {
	String argument[] = args.split(",");
	value = i18nService.getMessage(key, request.getLocale(), (Object[]) argument);
} else {
	value = i18nService.getMessage(key, request.getLocale());
}
%>
<%=value%>