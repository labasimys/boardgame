<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html> 
<head>
<meta charset="UTF-8">
<title>공지 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_hyem.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#write_form').submit(function(){
			if($('#noti_title').val().trim() == ''){
				alert('제목을 입력하세요');
				$('#noti_title').val('').focus();
				
				return false;
			}
			
			if($('#noti_content').val().trim() == ''){
				alert('내용을 입력하세요');
				$('#noti_content').val('').focus();
				
				return false;
			}
		});
		
		$("#noti_file").on('change', function(){
			var noti_fileName = $("#noti_file").val();
			$(".inqu-filename").val(noti_fileName);
		});
	});
</script>
</head>
<body>
<c:if test="${user_auth != 9}">
	<script>
		alert('관리자만 접근할 수 있습니다');
		history.go(-1);
	</script>
</c:if>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<div class="content-main">
		<form action="noticeWrite.do" method="post" id="write_form" enctype="multipart/form-data">
			<h2 style="margin-left: 0;">WRITE</h2>
			<ul>
				<li>
					<label for="noti_title">제목</label>
					<input type="text" name="noti_title" id="noti_title" maxlength="20">
				</li>
				<li>
					<label for="noti_content">내용</label>
					<textarea rows="5" cols="30" name="noti_content" id="noti_content"></textarea>
				</li>
				<li>
					<label>파일</label>
					<div class="filebox">
						<label for="noti_file">파일선택</label>
						<input type="file" name="noti_file" id="noti_file" accept="image/gif, image/png, image/jpeg">
						<input class="inqu-filename" value="선택된 파일 없음" readonly>
					</div>		
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" class="btn" value="등록">
				<input type="button" class="btn" value="목록" onclick="location.href='noticeList.do'">
			</div>
		</form>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>