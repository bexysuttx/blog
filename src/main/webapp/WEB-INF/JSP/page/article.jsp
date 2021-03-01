<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
			<img src="http://placehold.it/32x32?text=f" alt="social" />
			<img src="http://placehold.it/32x32?text=t" alt="social" />
			<img src="http://placehold.it/32x32?text=g" alt="social" />
			<img src="http://placehold.it/32x32?text=f" alt="social" />
			<img src="http://placehold.it/32x32?text=t" alt="social" />
			<img src="http://placehold.it/32x32?text=g" alt="social" />
		</div>
		<br>
		<div class="comments">
			<div id="new-comment-container" class="media-object comment-item new-comment">
				<jsp:include page="../fragment/new-comment.jsp"></jsp:include>
			</div>
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