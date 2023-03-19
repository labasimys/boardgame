<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html> 
<head>
<meta charset="UTF-8">
<title>문의 답변</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_hyem.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#write_form').submit(function(){
			if($('#inqu_title').val().trim() == ''){
				alert('제목을 입력하세요');
				$('#inqu_title').val('').focus();
				
				return false;
			}
			
			if($('#inqu_content').val().trim() == ''){
				alert('내용을 입력하세요');
				$('#inqu_content').val('').focus();
				
				return false;
			}
			
			var check = $('#inqu_checkbox').prop('checked')? 1 : 0;
			$('#inqu_check').val(check);
		});
	});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<div class="content-main">
		<form action="inquiryReply.do" method="post" id="write_form" enctype="multipart/form-data">
			<h2 style="margin-left: 0;">REPLY</h2>
			<input type="hidden" name="inqu_num" value="${inquiry.inqu_num}">
			<c:if test="${inquiry.inqu_check == 1}">
				<input type="hidden" name="inqu_checkbox" id="inqu_checkbox" checked disabled>
				<input type="hidden" name="inqu_check" id="inqu_check">
			</c:if>
			<c:if test="${inquiry.inqu_check == 0}">
				<input type="hidden" name="inqu_checkbox" id="inqu_checkbox" disabled>
				<input type="hidden" name="inqu_check" id="inqu_check">
			</c:if>
			<ul>
				<li>
					<label for="inqu_title">제목</label>
					<input type="text" name="inqu_title" id="inqu_title" value="${inquiry.inqu_title}" maxlength="50" readonly>
				</li>
				<li>
					<label for="inqu_content_origin">문의 내용</label>
					<textarea rows="5" cols="30" name="inqu_content_origin" id="inqu_content_origin" readonly>${inquiry.inqu_content}</textarea>
				</li>
				<li>
					<label for="inqu_content">문의 답변</label>
					<textarea rows="5" cols="30" name="inqu_content" id="inqu_content"></textarea>
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" class="btn" value="등록">
				<input type="button" class="btn" value="목록" onclick="location.href='inquiryList.do'">
			</div>
		</form>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>