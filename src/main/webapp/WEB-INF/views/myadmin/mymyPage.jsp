<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_0g.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="page-main">
		<div class="main-title">
			<h2>관리자 마이페이지</h2>
		</div>
		<div class="main-menu00">
			<button id="btn00" value="주문조회" onclick="location.href='orderList.do'">주문조회</button>
			<button id="btn00" value="예약조회" onclick="location.href='resList.do'">예약조회</button>
			<button id="btn00" value="회원정보" onclick="location.href='memList.do'">회원정보</button>
		</div>
	</div>
</body>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</html>