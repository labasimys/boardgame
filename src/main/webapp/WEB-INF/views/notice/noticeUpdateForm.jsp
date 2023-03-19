<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html> 
<head>
<meta charset="UTF-8">
<title>공지 글 수정</title>
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
		
		let modal = document.getElementById("modal");
		
		//삭제 버튼 눌렀을 때
        let delete_btn = document.getElementById("delete_btn");
		delete_btn.addEventListener("click", e => {			
	        $(".modal-overlay").css("display", "block");
	        $(".modal-window").css("display", "block");
	        $("body").css("overflow", "hidden");
		});
		
		//모달창에서 삭제 버튼 눌렀을 때
		let del = document.getElementById("delete");
		let noti_num = document.getElementById("noti_num").value;
		del.addEventListener("click", e => {
			$.ajax({
				url:'deleteNoticeFile.do',
				type:'post',
				data:{noti_num:noti_num},
				dataType:'json',
				success:function(param){
					if(param.result == 'logout'){
						alert('로그인 후 사용하세요!');
					}else if(param.result == 'success'){
						$('#file_detail').hide();
			            $(".modal-overlay").css("display", "none");
			            $(".modal-window").css("display", "none");
			            $("body").css("overflow", "auto");
					}else if(param.result == 'wrongAccess'){
						alert('잘못된 접속입니다.');
					}else{
						alert('파일 삭제 오류 발생');
					}
				},
				error:function(){
					alert('네트워크 오류 발생');
				}
			});
		});
		
		//모달창에서 취소 버튼 눌렀을 때
		let cancel = document.getElementById("cancel");
		cancel.addEventListener("click", e => {		    
            $(".modal-overlay").css("display", "none");
            $(".modal-window").css("display", "none");
            $("body").css("overflow", "auto");
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
		<form action="noticeUpdate.do" method="post" id="write_form" enctype="multipart/form-data">
			<h2 style="margin-left: 0;">MODIFY</h2>
			<input type="hidden" name="noti_num" value="${notice.noti_num}">
			<ul>
				<li>
					<label for="noti_title">제목</label>
					<input type="text" name="noti_title" id="noti_title" value="${notice.noti_title}" maxlength="50">
				</li>
				<li>
					<label for="noti_content">내용</label>
					<textarea rows="5" cols="30" name="noti_content" id="noti_content">${notice.noti_content}</textarea>
				</li>
				<li>
					<label>파일</label>
					<c:if test="${empty notice.noti_file}">
						<div class="filebox">
							<label for="noti_file">파일선택</label>
							<input type="file" name="noti_file" id="noti_file" accept="image/gif, image/png, image/jpeg">
							<input class="inqu-filename" value="선택된 파일 없음">
						</div>					
					</c:if>
					
					<c:if test="${!empty notice.noti_file}">
						<div class="file">
							<div class="filebox">
								<label for="noti_file">파일선택</label>
								<input type="file" name="noti_file" id="noti_file" accept="image/gif, image/png, image/jpeg">
								<input class="inqu-filename" value="선택된 파일 없음" readonly>
							</div>					
							<div id="file_detail">
								<input type="button" class="btn" value="파일삭제" id="delete_btn" style="margin-left: 0;">
								(${notice.noti_file})파일이 등록되어 있습니다.
							</div>						
						</div>
						<div id="modal" class="modal-overlay">
							<div class="modal-window">
								<div class="modal-content">
									<p>삭제하시겠습니까?</p>
									<input type="hidden" id="noti_num" value="${notice.noti_num}">
									<div class="confirm-btn">
							            <input type="button" value="확인" class="btn" id="delete">
										<input type="button" value="취소" class="btn" id="cancel">
									</div>
								</div>
						    </div>
						</div>
					</c:if>
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" class="btn" value="수정">
				<input type="button" class="btn" value="취소" onclick="location.href='noticeDetail.do?noti_num=${notice.noti_num}'">
			</div>
		</form>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>