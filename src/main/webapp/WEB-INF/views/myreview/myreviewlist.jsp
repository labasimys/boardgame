<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내가 작성한 댓글</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_ddk.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="page-main">
	<h2>내가 작성한 댓글</h2>
	<hr size="1" noshade="noshade" width="100%">
	<c:if test="${count == 0}">
		<div class="result-display-x">
			댓글이 없습니다.
		</div>
	</c:if>
	<c:if test="${count > 0}">
	<table>
		<tr class="border-bot">
			<th class="myreview-1-size">번호</th>
			<th class="myreview-2-size">게임이름</th>
			<th class="myreview-3-size">리뷰내용</th>
			<th>작성일</th>
		</tr>
		<c:forEach var="myreview" items="${list}">
		<tr class="table-content-bot">
			<td>${myreview.rev_num}</td>
			<td><a href="${pageContext.request.contextPath}/game/gameDetail.do?pro_num=${myreview.pro_num}">${fn:substring(myreview.pro_name,0,15)}</a></td>
			<td>${fn:substring(myreview.rev_content,0,35)}</td>
			<td>${myreview.rev_date}</td>
		</tr>
		</c:forEach>
	</table>
	<hr size="1" noshade="noshade" width="100%">
	<div class="align-center paging">${page}</div>
	<div class="pagebottom"></div>
	</c:if>
	</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>	
</body>
</html>