<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_main.css">
</head>
<body class="main-body">
<div class="container">
	<div class="main-logo">
		<div class="main-logoBox">
			<a href="${pageContext.request.contextPath}/main/intro.do">
				<img src="${pageContext.request.contextPath}/images/hyem/logo_black.png" width="120">
			</a>
		</div>
	</div>
	<div class="main-background">
		<div class="start">
			<div class="start-a">
				<a href="${pageContext.request.contextPath}/main/intro.do">START</a>
				<c:if test="${empty user_num}">
					<a href="${pageContext.request.contextPath}/member/loginForm.do">LOGIN</a>
					<br>
					<a href="${pageContext.request.contextPath}/member/registerUserForm.do">SIGN IN</a>
				</c:if>
				<c:if test="${!empty user_num && user_auth == 2}">
					<a href="${pageContext.request.contextPath}/mymember/myPage.do">MY PAGE</a>
					<br>
					<a href="${pageContext.request.contextPath}/member/logout.do">LOGOUT</a>
				</c:if>
				<c:if test="${!empty user_num && user_auth == 9}">
					<a href="${pageContext.request.contextPath}/myadmin/mymyPage.do">ADMIN</a>
					<br>
					<a href="${pageContext.request.contextPath}/member/logout.do">LOGOUT</a>
				</c:if>
			</div>
		</div>

		<img src="${pageContext.request.contextPath}/images/hyem/mainImg.png">
	</div>
	<div class="main-menu">
		<ul>
			<li><a href="${pageContext.request.contextPath}/game/gameList.do">BOARDGAME</a></li>
			<li><a href="${pageContext.request.contextPath}/reserve/reserveMain.do">RESERVATION</a></li>
			<li><a href="${pageContext.request.contextPath}/notice/noticeList.do">NOTICE</a></li>
			<li><a href="${pageContext.request.contextPath}/inquiry/inquiryList.do">QUESTIONS</a></li>
		</ul>
	</div>
</div>
</body>
</html>