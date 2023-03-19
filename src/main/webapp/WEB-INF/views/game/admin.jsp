<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게임 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>

</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	
	<div class="content-main">
		<h2>???게임 목록???</h2>
		<div class="list-space align-right">
			<input type="button" value="게임등록" onclick="location.href='gameWriteForm.do'">
		</div>
		<div >
			<input type="button" value="게임삭제" onclick="location.href='gameDelete.do'">
		</div>
	</div>
</div>
</body>
</html>
<!-- 게임 목록 페이지. 게임 등록 버튼 누르면 게임등록 폼으로 보내줌. -->