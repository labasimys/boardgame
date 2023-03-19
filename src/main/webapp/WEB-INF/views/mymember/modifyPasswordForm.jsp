<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_ddk.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		//비밀번호 수정 체크
		$('#password_form').submit(function(){
			if($('#origin_passwd').val().trim()==''){
				alert('현재 비밀번호를 입력하세요!');
				$('#origin_passwd').val('').focus();
				return false;
			}
			if($('#passwd2').val().trim()==''){
				alert('새비밀번호를 입력하세요!');
				$('#passwd2').val('').focus();
				return false;
			}
			if($('#cpasswd2').val().trim()==''){
				alert('새비밀번호 확인을 입력하세요!');
				$('#cpasswd2').val('').focus();
				return false;
			}
			if($('#passwd2').val()!=$('#cpasswd2').val()){
				alert('새비밀번호와 새비밀번호 확인이 불일치합니다.');
				$('#passwd2').val('').focus();
				$('#cpasswd2').val('');
				return false;
			}
		});//end of submit
	});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<!-- 프로필 사진 시작 -->
		<div class="photo-box-delete">
		<div class="photo-box-delete-a">
			<c:if test="${empty member.mem_photo}">
				<img src="${pageContext.request.contextPath}/images/face.png"
					width="200" height="200" class="my-photo">
			</c:if>
			<c:if test="${!empty member.mem_photo}">
				<img
					src="${pageContext.request.contextPath}/upload/${member.mem_photo}"
					width="200" height="200" class="my-photo">
			</c:if>
		</div>	
		</div>
		<!--프로필 사진 끝  -->
	<div class="page-main">	
  		<!--비번변경 폼 시작 -->
		<form id="password_form" action="modifyPassword.do" method="post">
			<div class="modifypw_form-list">
			<ul>
				<li>
					<label for="origin_passwd"><b>현재 비밀번호</b></label>
					<input type="password" name="origin_passwd" 
					            id="origin_passwd" maxlength="12">
				</li>
				<li>
					<label for="passwd"><b>새비밀번호</b></label>
					<input type="password" name="passwd" 
					            id="passwd2" maxlength="12">
				</li>
				<li>
					<label for="cpasswd"><b>새비밀번호 확인</b></label>
					<input type="password" name="cpasswd" 
					            id="cpasswd2" maxlength="12">
				</li>
			</ul>
			</div>
			<div class="align-center">
				<input class="redbtn-modify" type="submit" value="비밀번호 수정">
			</div>                                   
			<div class="helpbox-pw">
			<a href="${pageContext.request.contextPath}/mymember/myPage.do"><ins>My페이지로 가기</ins></a>
			</div>
			<div class="float-end"></div>
		</form>
			<div class="pagebottom"></div>
		
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>






