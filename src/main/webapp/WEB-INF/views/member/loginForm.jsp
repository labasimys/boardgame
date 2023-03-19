<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_hapalpal.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#login-form').submit(function(){
			if($('#mem_id').val().trim()==''){
				alert('아이디를 입력하세요');
				$('#mem_id').val('').focus();
				return false;
			}
			if($('#mem_pw').val().trim()==''){
				alert('비밀번호를 입력하세요');
				$('#mem_pw').val('').focus();
				return false;
			}
		});
	});
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<div class="content-main">
		<h2>LOGIN</h2>
		<form id="login-form" action="login.do" method="post">
			<div class="input-box">
		 			<label for="id">아이디</label>
		 			<input type="text" name="mem_id" id="mem_id" maxlength="12" placeholder="아이디" autocomplete="off">
		 	</div>
		 	<div class="input-box">	
		 			<label for="passwd">비밀번호</label>
		 			<input type="password" name="mem_pw" id="mem_pw" placeholder="비밀번호" maxlength="12">
		 	</div>
		 	<div class="align-center">
		 		<input id="pointer" type="submit"  onclick="location='/boardGameCafe/main/intro.do'" value="로그인" >
		 		<input id="pointer1" type="button" onclick="location='/boardGameCafe/member/registerUserForm.do'" value="회원가입">
		 	</div>
		</form>
	</div> 
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>