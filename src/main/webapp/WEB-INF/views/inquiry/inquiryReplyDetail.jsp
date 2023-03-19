<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의 답변 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_hyem.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui.min.js"></script>
<script type="text/javascript">
	$(function(){
		let modal = document.getElementById("modal");
		
		let delete_btn = document.getElementById("delete_btn");
		delete_btn.addEventListener("click", e => {
	        $(".modal-overlay").css("display", "block");
	        $(".modal-window").css("display", "block");
	        $("body").css("overflow", "hidden");
		});
		
		let del = document.getElementById("delete");
		let inqu_num = document.getElementById("inqu_num").value;
		del.addEventListener("click", e => {
			location.href="inquiryReplyDelete.do?inqu_num=" + inqu_num;
		});
		
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
<c:if test="${inquiry.inqu_check == 1}">
	<c:if test="${inquiry.mem_num != user_num && user_auth != 9}">
		<script>
			alert('비밀글입니다');
			history.go(-1);
		</script>
	</c:if>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<c:if test="${inquiry.mem_num == user_num || user_auth == 9}">
		<div class="page-main">			
			<h2>Q & A</h2>
			<hr size="1" noshade="noshade" width="100%">
			<div class="content-main">
				<div class="inqu-content-title">
					<div class="mem-id">
						관리자
					</div>
					<div class="inqu-title">
						<i>Re : </i>${inquiry.inqu_title}
					</div>
					<div class="inqu-reg-date">
						${inquiry.inqu_reg_date} 
					</div>
				</div>
				<hr size="1" noshade="noshade" width="100%">
				<div class="inqu-content">
						<i>문의하신 내용</i>
						<div class="origin-inquiry">
							${inquiryContent}
						</div>
						<i>Reply</i>
						<div class="reply-inquiry">
							${inquiry.inqu_content}
						</div>
				</div>
				<hr size="1" noshade="noshade" width="100%">
			</div>
			<div class="align-right">
				<c:if test="${user_auth == 9}">
					<input type="button" value="수정" class="btn" onclick="location.href='inquiryReplyUpdateForm.do?inqu_num=${inquiry.inqu_num}'">
					<input type="button" value="삭제" class="btn" id="delete_btn">
					<div id="modal" class="modal-overlay">
						<div class="modal-window">
							<div class="modal-content">
								<p>삭제하시겠습니까?</p>
					            <input type="hidden" id="inqu_num" value="${inquiry.inqu_num}">
					            <div class="confirm-btn">
					                <input type="button" value="확인" class="btn" id="delete">
					                <input type="button" value="취소" class="btn" id="cancel">
					            </div>
					        </div>
					    </div>
					</div>
				</c:if>
				<input type="button" value="목록" class="btn" onclick="location.href='inquiryList.do'">
			</div>
		</div>
	</c:if>
</c:if>
<c:if test="${inquiry.inqu_check == 0}">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="page-main">			
		<h2>Q & A</h2>
			<hr size="1" noshade="noshade" width="100%">
			<div class="content-main">
				<div class="inqu-content-title">
					<div class="mem-id">
						관리자
					</div>
					<div class="inqu-title">
						<i>Re : </i>${inquiry.inqu_title}
					</div>
					<div class="inqu-reg-date">
						${inquiry.inqu_reg_date} 
					</div>
				</div>
			<hr size="1" noshade="noshade" width="100%">
			<div class="inqu-content">
					<i>문의하신 내용</i>
					<div class="origin-inquiry">
						${inquiryContent}
					</div>
					<i>Reply</i>
					<div class="reply-inquiry">
						${inquiry.inqu_content}
					</div>
			</div>
			<hr size="1" noshade="noshade" width="100%">
		</div>
		<div class="align-right">
			<c:if test="${user_auth == 9}">
				<input type="button" value="수정" class="btn" onclick="location.href='inquiryReplyUpdateForm.do?inqu_num=${inquiry.inqu_num}'">
				<input type="button" value="삭제" class="btn" id="delete_btn">
				<div id="modal" class="modal-overlay">
					<div class="modal-window">
						<div class="modal-content">
							<p>삭제하시겠습니까?</p>
							<input type="hidden" id="inqu_num" value="${inquiry.inqu_num}">
							<div class="confirm-btn">
								<input type="button" value="확인" class="btn" id="delete">
								<input type="button" value="취소" class="btn" id="cancel">
							</div>
				        </div>
				    </div>
				</div>
			</c:if>
			<input type="button" value="목록" class="btn" onclick="location.href='inquiryList.do'">
		</div>
	</div>
</c:if>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>