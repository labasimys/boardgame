<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의사항</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_hyem.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui.min.js"></script>
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
		let inqu_num = document.getElementById("inqu_num").value;
		del.addEventListener("click", e => {
			location.href="inquiryDelete.do?inqu_num=" + inqu_num;
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
			<div class="inqu-cate">
				${inquiry.inqu_cate}
			</div>
			<hr size="1" noshade="noshade" width="100%">
			<div class="content-main">
				<div class="inqu-content-title">
					<div class="mem-id">
						${inquiry.mem_id}
					</div>
					<div class="inqu-title">
						${inquiry.inqu_title}
					</div>
					<div class="inqu-reg-date">
						${inquiry.inqu_reg_date}
					</div>
				</div>
				<hr size="1" noshade="noshade" width="100%">
				<div class="inqu-content">
					<c:if test="${!empty inquiry.inqu_file}">
						<img src="${pageContext.request.contextPath}/upload/${inquiry.inqu_file}" class="detail-img" width="500">
					</c:if>
					<p>
						${inquiry.inqu_content}
					</p>				
				</div>
				<hr size="1" noshade="noshade" width="100%">
			</div>
			<div class="align-right">
				<c:if test="${inquiry.mem_num == user_num}">
					<input type="button" value="수정" class="btn" onclick="location.href='inquiryUpdateForm.do?inqu_num=${inquiry.inqu_num}'">
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
				<c:if test="${user_auth == 9}">
					<input type="button" value="답글" class="btn" onclick="location.href='inquiryReplyForm.do?inqu_num=${inquiry.inqu_num}'">
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
		<div class="inqu-cate">
			${inquiry.inqu_cate}
		</div>
		<hr size="1" noshade="noshade" width="100%">
		<div class="content-main">
			<div class="inqu-content-title">
				<div class="mem-id">
					${inquiry.mem_id}
				</div>
				<div class="inqu-title">
					${inquiry.inqu_title}
				</div>
				<div class="inqu-reg-date">
					${inquiry.inqu_reg_date}
				</div>
			</div>
			<hr size="1" noshade="noshade" width="100%">
			<div class="inqu-content">
				<c:if test="${!empty inquiry.inqu_file}">
					<img src="${pageContext.request.contextPath}/upload/${inquiry.inqu_file}" class="detail-img" width="500">
				</c:if>
				<p>
					${inquiry.inqu_content}
				</p>				
			</div>
			<hr size="1" noshade="noshade" width="100%">
		</div>
		<div class="align-right">
			<c:if test="${inquiry.mem_num == user_num}">
				<input type="button" value="수정" class="btn" onclick="location.href='inquiryUpdateForm.do?inqu_num=${inquiry.inqu_num}'">
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
			<c:if test="${user_auth == 9}">
				<input type="button" value="답글" class="btn" onclick="location.href='inquiryReplyForm.do?inqu_num=${inquiry.inqu_num}'">
			</c:if>
			<input type="button" value="목록" class="btn" onclick="location.href='inquiryList.do'">
		</div>
	</div>
</c:if>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>