<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="callout alert">
	<c:choose>
		<c:when test="${i404 && url != null }">
			<h6>
				Requested resource <strong>${url }</strong> not found!
			</h6>
		</c:when>
		<c:when test="${i404 }">
			<h6>Requested resource not found!</h6>
		</c:when>

		<c:otherwise>Error, please try again later...</c:otherwise>
	</c:choose>
</div>