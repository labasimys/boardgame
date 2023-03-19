<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- header 시작 -->
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_main.css">
</head>
<body>
<div class="header">
	<div class="menubar">
		<img src="${pageContext.request.contextPath}/images/hyem/menubar.png" onclick="openNav()" width="70">
	</div>
	<div class="header-logo">
		<a href="${pageContext.request.contextPath}/main/main.do"><img src="${pageContext.request.contextPath}/images/hyem/logo_white.png" width="120"></a>
	</div>
	<div class="login-part">
		<c:if test="${empty user_num}">
			<a href="${pageContext.request.contextPath}/member/loginForm.do">Login</a>&nbsp;&nbsp;
			<a href="${pageContext.request.contextPath}/member/registerUserForm.do">Sign in</a>
		</c:if>
		
		<c:if test="${!empty user_num && user_auth == 2}">
			<a href="${pageContext.request.contextPath}/cart/cart.do">Cart</a>&nbsp;&nbsp;
			<a href="${pageContext.request.contextPath}/mymember/myPage.do">My Page</a>&nbsp;&nbsp;
		</c:if>
		
		<c:if test="${!empty user_num && user_auth == 9}">
			<a href="${pageContext.request.contextPath}/myadmin/mymyPage.do">Admin</a>&nbsp;&nbsp;
		</c:if>
		
		<c:if test="${!empty user_num}">
			<a href="${pageContext.request.contextPath}/member/logout.do">Logout</a>
		</c:if>
	</div>
</div>
<div id="myNav" class="overlay">
	<a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
	<div class="overlay-content">
		<a href="${pageContext.request.contextPath}/main/intro.do">INTRO</a>
		<a href="${pageContext.request.contextPath}/game/gameList.do">BOARDGAME</a>
   	 	<a href="${pageContext.request.contextPath}/reserve/reserveMain.do">RESERVATION</a>
    	<a href="${pageContext.request.contextPath}/notice/noticeList.do">NOTICE</a>
    	<a href="${pageContext.request.contextPath}/inquiry/inquiryList.do">QUESTIONS</a>
  	</div> 
</div>
<script>
function openNav() {
  document.getElementById("myNav").style.width = "17%";
}

function closeNav() {
  document.getElementById("myNav").style.width = "0%";
}
</script>
</body>
</html>