<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html> 
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_hyem.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
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
			location.href="noticeDelete.do?noti_num=" + noti_num;
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
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<h2>NOTICE</h2>
	<hr size="1" noshade="noshade" width="100%">
	<div class="content-main">
		<div class="inqu-content-title">
			<div class="mem-id">
				${notice.noti_num}
			</div>
			<div class="inqu-title">
				${notice.noti_title}
			</div>
			<div class="inqu-reg-date">
				${notice.noti_hit}
			</div>
		</div>
		<hr size="1" noshade="noshade" width="100%">
		<div class="inqu-content">
			<c:if test="${!empty notice.noti_file}">
				<img src="${pageContext.request.contextPath}/upload/${notice.noti_file}" class="detail-img" width="500">
			</c:if>
			<p>
				${notice.noti_content}
			</p>		
		</div>
		<hr size="1" noshade="noshade" width="100%">
	</div>
	<div class="align-right">
		<c:if test="${!empty user_num && user_auth == 9}">
			<input type="button" class="btn" value="수정" onclick="location.href='noticeUpdateForm.do?noti_num=${notice.noti_num}'">
			<input type="button" class="btn" value="삭제" id="delete_btn">
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
		<input type="button" class="btn" value="목록" onclick="location.href='noticeList.do'">
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>