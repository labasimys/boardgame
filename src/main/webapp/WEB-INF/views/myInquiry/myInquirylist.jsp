<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내가 작성한 문의</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_ddk.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<h2>내가 작성한 문의</h2>
	<hr size="1" noshade="noshade" width="100%">
	<c:if test="${count == 0}">
		<div class="result-display-x">
			문의한 글이 없습니다.
		</div>
	</c:if>
	<c:if test="${count > 0}">
	<table>
		<tr class="border-bot">
			<th class="inqutable-1">번호</th>
			<th class="inqutable-2">문의 제목</th>
			<th class="inqutable-3">문의 내용</th>
			<th class="inqutable-4">작성일</th>
		</tr>
		<c:forEach var="myinqu" items="${list}">
		<tr class="table-content-bot">
			<td>${myinqu.inqu_num}</td>
			<td><a href="${pageContext.request.contextPath}/inquiry/inquiryDetail.do?inqu_num=${myinqu.inqu_num}">${fn:substring(myinqu.inqu_title,0,13)}</a></td>
			<td>${fn:substring(myinqu.inqu_content,0,35)}</td>
			<td>${myinqu.inqu_reg_date}</td>
		</tr>
		</c:forEach>
	</table>
	<hr size="1" noshade="noshade" width="100%">
	<div class="align-center paging">${page}</div>
	</c:if>
	<div class="align-right">
		<input type="button" class="btn" value="글쓰기" onclick="location.href='${pageContext.request.contextPath}/inquiry/inquiryWriteForm.do'">
	</div>
	<div class="pagebottom"></div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>