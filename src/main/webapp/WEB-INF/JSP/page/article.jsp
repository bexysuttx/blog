<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="https://apis.google.com/js/platform.js" async defer></script>
<!-- Go to www.addthis.com/dashboard to customize your tools --> 
<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-6043fa1967654439"></script>

<div class="article thumbnail">
<c:set var="category" value="${CATEGORY_LIST[article.idCategory] }"></c:set>
	<img src="${article.logo }" alt="${article.title }">
	<div class="data">
		<h3>${article.title }</h3>
		<ul class="vertical large-horizontal menu">
			<li><i class="fi-folder"></i><a href="/news${category.url }">${category.name }</a></li>
			<li><i class="fi-comments">${article.comments }</i> comments</li>
			<li><i class="fi-clock"></i>${article.created }</li>
			<li><i class="fi-eye"></i>Hits: ${article.views }</li>
		</ul>
		<hr>
		<div class="content">
			${article.content }
		</div>
		<div class="row columns social">
			<!-- Go to www.addthis.com/dashboard to customize your tools --> 
			<div class="addthis_inline_share_toolbox"></div>
		</div>
		<br>
		<div class="comments">
			
				<jsp:include page="../fragment/new-comment.jsp"></jsp:include>
			
		<div id="comments-list-container" data-comments-count ="${article.comments }" data-id-article="${article.id }">
	<jsp:include page="../fragment/comments.jsp" />
			</div>
			<div id="comments-load-more-ctrl" class="row column text-center">
				<a href="javascript:moreComments();" class="button hollow expanded load-more-btn" ${article.comments > fn:length(comments) ? '' : 'style="display:none"' }>Load More</a>
			<img alt="Loading..." src="/static/img/loading.gif" class="loading-indicator" />
			</div>
		</div>
	</div>
</div>