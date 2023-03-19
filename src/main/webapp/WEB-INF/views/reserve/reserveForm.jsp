<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="content-main">
			<h2>예약하기</h2>
			<form action="reserve.do" method="post">
				<input type="hidden" name="mem_num" id="mem_num" value="${detail.mem_num}">
				<ul>
					<li>
						<label>예약자 이름</label>
						<input type="text" name="name" id="name" maxlength="50" value="${detail.mem_name}" readonly>
					</li>
					<li>
						<label>예약날짜</label>
						<input type="text" name="res_date" id="res_date" maxlength="10">
					</li>
					<li>
						<label>예약시간</label>
						<input type="text" name="res_time" id="res_time" maxlength="10">
					</li>
					<li>
						<label>인원수</label>
						<input type="number" name="res_count" id="res_count" min="1" max="30">
					</li>
					<li>
						<label>방 번호</label>
						<input type="text" name="room" id="room" value="${room.room_num}">
					</li>
				</ul>
				<input type="submit" id="submit_reserve" value="예약"/>
			</form>
		</div>
	</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>