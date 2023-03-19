<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html> 
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_hyem.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<h2>NOTICE</h2>
	<hr size="1" noshade="noshade" width="100%">
	<c:if test="${count == 0}">
		<div class="result-display">
			등록된 공지가 없습니다.
		</div>
	</c:if>
	
	<c:if test="${count > 0}">	
		<table>
			<tr class="border-bot">
				<th style="width: 70px;">번호</th>
				<th>제목</th>
				<th style="width: 150px;">작성일</th>
				<th style="width: 100px;">조회수</th>
			</tr>
			<c:forEach var="notice" items="${list}">
				<tr class="table-content-bot">
					<td>${notice.rownum}</td>
					<td style="padding-left: 50px;text-align: left;"><a href="noticeDetail.do?noti_num=${notice.noti_num}">${notice.noti_title}</a></td>
					<td>${notice.noti_reg_date}</td>
					<td>${notice.noti_hit}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<hr size="1" noshade="noshade" width="100%">
	<c:if test="${count > 0}">
		<div class="align-center paging">
			${pagingHtml}
		</div>
	</c:if>
	<c:if test="${!empty user_num && user_auth == 9}">
		<div class="align-right">
			<input type="button" class="btn" value="글쓰기" onclick="location.href='noticeWriteForm.do'">
		</div>
	</c:if>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>