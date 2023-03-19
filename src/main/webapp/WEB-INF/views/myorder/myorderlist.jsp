<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>구매목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_ddk.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="page-main">
	<h2>구매내역</h2>
	<hr size="1" noshade="noshade" width="100%">
	<c:if test="${count == 0}">
		<div class="result-display-x">
			구매내역 없음.
		</div>
	</c:if>
	<c:if test="${count > 0}">
	<table>
		<tr class="border-bot">
			<th>번호</th>
			<th>배송상태</th>
			<th>구매목록</th>
			<th>총가격</th>
			<th>구매날짜</th>
			<th class="myorder-6-size">수정 및 취소</th>
		</tr>
		<c:forEach var="myorder" items="${list}">
		<tr class="table-content-bot">
			<td>${myorder.order_main_num}</td>
			<c:if test="${myorder.status==1}"><td>배송대기(결제완료)</td></c:if>
			<c:if test="${myorder.status==2}"><td>배송준비중</td></c:if>
			<c:if test="${myorder.status==3}"><td>배송중</td></c:if>
			<c:if test="${myorder.status==4}"><td>배송완료</td></c:if>
			<c:if test="${myorder.status==5}"><td>주문취소</td></c:if>
			<td>${myorder.order_main_name}</td>
			<td><fmt:formatNumber value="${myorder.order_main_total}"/>원</td>
			<td>${myorder.order_main_date}</td>
			<td><a href="${pageContext.request.contextPath}/order/orderModifyForm.do?order_main_num=${myorder.order_main_num}"><img src="${pageContext.request.contextPath}/images/soon/modify.png" width="10%"></a></td>
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