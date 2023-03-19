<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 상세정보</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_0g.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function(){
	$('#update_auth').click(function(){
		$('#submit_auth').show();
		$('#select_auth').show();
		$('#auth').hide();  //등급 input 감추기
		$(this).hide();  //수정 버튼 감추기
	});
	$('#submit_auth').click(function(){
		console.log(this.mem_num.value);
		console.log(this.mem_auth.value);
	});
});
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="page-main">
		<div class="main-title">
			<h2>관리자 마이페이지</h2>
		</div>
		<div class="main-menu01">
			<button id="btn01" value="주문조회" onclick="location.href='orderList.do'">주문조회</button>
			<button id="btn01" value="예약조회" onclick="location.href='resList.do'">예약조회</button>
			<button id="btn02" value="회원정보" onclick="location.href='memList.do'">회원정보</button>
		</div>
		<div class="main-content">
			<div id="title-count">
		  		<p id="title">회원 상세정보</p>
		  	</div>
		  	<hr size="1" noshade="noshade" width="100%">
			<ul class="memdet_ul">
				<li>
					<label for="id">아이디</label>
					ㅣ <input type="text" name="id" id="id" value="${detail.mem_id}" readonly/>
				</li>
				<li>
					<label for="name">이름</label>
					ㅣ <input type="text" name="name" id="name" value="${detail.mem_name}" readonly/>
				</li>
				<li>
					<form action="updateMemAuth.do" method="post">
						<input type="hidden" id="num" name="mem_num" value="${detail.mem_num}"/>
						<label for="auth">등급</label>
						<c:if test="${detail.mem_auth == 2}">
							ㅣ <input type="text" name="auth" id="auth" value="일반회원" readonly/>
						</c:if>
						<c:if test="${detail.mem_auth == 0}">
							ㅣ <input type="text" name="auth" id="auth" value="탈퇴회원" readonly/>
						</c:if>
						<c:if test="${detail.mem_auth == 1}">
							ㅣ <input type="text" name="auth" id="auth" value="정지회원" readonly/>
						</c:if>
						<c:if test="${detail.mem_auth == 9}">
							ㅣ <input type="text" name="auth" id="auth" value="관리자" readonly/>
						</c:if>
						<select name="mem_auth" id="select_auth" style="display:none;">
							<option value=2 <c:if test="${detail.mem_auth == 2}">selected</c:if>>일반회원</option>
							<option value=0 <c:if test="${detail.mem_auth == 0}">selected</c:if>>탈퇴회원</option>
							<option value=1 <c:if test="${detail.mem_auth == 1}">selected</c:if>>정지회원</option>
							<option value=9 <c:if test="${detail.mem_auth == 9}">selected</c:if>>관리자</option>
						</select>
						<input type="button" id="update_auth" value="수정"/>
						<input type="submit" id="submit_auth" style="display:none" value="완료"/>
					</form>
				</li>
				<li>
					<label for="phone">전화번호</label>
					ㅣ <input type="text" name="phone" id="phone" value="${detail.mem_phone}" readonly/>
				</li>
				<li>
					<label for="email">이메일</label>
					ㅣ <input type="text" name="email" id="email" value="${detail.mem_email}" readonly/>
				</li>
				<li>
					<label for="zipcode">우편번호</label>
					ㅣ <input type="text" name="zipcode" id="zipcode" value="${detail.mem_zipcode}" readonly/>
				</li>
				<li>
					<label for="address1">주소</label>
					ㅣ <input type="text" name="address1" id="address1" value="${detail.mem_address1}" readonly/>
				</li>
				<c:if test="${!empty detail.mem_address2}">
					<li>
						<label for="address2">상세주소</label>
						ㅣ <input type="text" name="address2" id="address2" value="${detail.mem_address2}" readonly/></li>
				</c:if>
			</ul>
			<div class="buttons">
				<input type="button" value="취소" onclick="window.location.reload()">
				<input type="button" value="목록으로" onclick="location.href='memList.do'">
			</div>
			<hr size="1" noshade="noshade" width="100%">
		</div>
	</div>
</body>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</html>