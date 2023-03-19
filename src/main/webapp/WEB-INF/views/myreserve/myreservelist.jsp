<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약내역</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_ddk.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
<h2>예약내역</h2>
	<hr size="1" noshade="noshade" width="100%">
	<c:if test="${count == 0}">
		<div class="result-display-x">
			예약내역이 없습니다.
		</div>
	</c:if>
	
	<c:if test="${count > 0}">
	<div class="text-align-center"><h2>오늘 예약된 방</h2></div> 
		<c:if test="${todaycount == 0}">
			<div class="result-display-x">
				오늘 예약 비어있음.
			</div>
		</c:if>
			
		<c:if test="${todaycount > 0}">
		<div class="todayreserve">
				<div class="todayreserve-imgbox">
					<img src="${pageContext.request.contextPath}/upload/${MyRe.photo1}"
							width="400" height="300">					
				</div>			
				<div class="todayreserve-detailbox">
				<b>
					${MyRe.room_name}(최대 ${MyRe.room_size}명)<br>
					${MyRe.room_detail} <br>
					예약 날짜: <a class="redfont-reserve">${MyRe.res_date}</a><br>
					예약 시간: ${MyRe.res_time}시
				</b>	 
				</div>
		</div>		
		</c:if>
	<br><br>	
	<h2>전체 예약 목록</h2>
	<hr size="1" noshade="noshade" width="100%">
	<table>
		<tr class="border-bot">
			<th>방 이름</th>
			<th>방 소개</th>
			<th>예약 날짜</th>
			<th>예약 시간</th>
		</tr>
		<c:forEach var="myreserve" items="${list}">
		<tr class="table-content-bot">
			<td>${myreserve.room_name}</td>
			<td>${myreserve.room_detail}</td>
			<td>${myreserve.res_date}</td>
			<td>${myreserve.res_time}</td>
		</tr>
		</c:forEach>
	</table>
	<hr size="1" noshade="noshade" width="100%">
	<div class="align-center paging">${page}</div>
	<div class="pagebottom"></div>
	</c:if>
	</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>	
</body>
</html>