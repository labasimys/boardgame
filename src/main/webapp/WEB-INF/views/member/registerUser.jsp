<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 완료</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_hapalpal.css">
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<div class="content-main">
		<h2>회원가입 완료</h2>
		<form id="login-form1" action="login.do" method="post">
			<div class="input-box">
		 	</div>
		 	<div class="input-box">	
		 	</div>
		 	<div class="align-center">
		 		<input id="pointer1" type="button" onclick="location='/boardGameCafe/member/loginForm.do'" value="로그인하러 가기">
		 		<input id="pointer1" type="button" onclick="location='/boardGameCafe/main/intro.do'" value="메인 화면으로">
		 	</div>
		</form>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>