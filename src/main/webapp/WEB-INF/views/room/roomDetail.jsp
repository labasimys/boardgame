<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방 정보</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_hoon.css">
<script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/3.1.4/js/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/3.1.4/css/bootstrap-datetimepicker.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.number.min.js"></script>
<script type="text/javascript">
	$(function(){ //이거 쓸거면 위에 jquery링크 걸어야함 꼭
		$('#write_form').submit(function(){
			if($('#res_date').val().trim()==''){ //비어있는거 인정X, 공백은 가능
				alert('날짜 입력하세요');
				$('#res_date').val('').focus();
				return false;
			}
			if($('#res_time').val()==''){ //공백을 인정하지 않기 때문에 trim 안씀
				alert('시간을 입력하세요');
				$('#res_time').focus();
				return false;
			}
			if($('#res_count').val().trim()==''){ //비어있는거 인정X, 공백은 가능
				alert('인원을 입력하세요');
				$('#res_count').val('').focus();
				return false;
			}
		});
	});
</script>

</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="containerrr" id="container">
	<div class="wrap_inn">
		<div class="wrap_left">
			<h2 class="align-center">${room.room_name}</h2>
			<div class="room-image">
				<img src="${pageContext.request.contextPath}/upload/${room.photo1}" width="400">
			</div>
			<div class="room-detail">
				<form id="room_reserve" method="post">
					<input type="hidden" name="room_num" value="${room.room_num}" id="room_num">
					<input type="hidden" name="room_detail" value="${room.room_detail}" id="room_detail">
					<input type="hidden" name="room_detail2" value="${room.room_detail2}" id="room_detail2">
					<ul class="dsccc">
						<li><b>${room.room_detail}</b></li>		
						${room.room_detail2}				
					</ul>
				</form>
			</div>
			
		</div>
		<div class="wrap_right">
			<div class="section_booking">
				<p class="in_info btm">
					<em class="in_info_tit">
						<i class="spr_book ico_i_red2"></i>
						<span>※ 알립니다</span>
					</em>
					<span class="in_info_dsc">
					[예약안내]
					<br>
					교실은 예약상황에 따라 임의배정됩니다.
					<br>
					시간추가를 원하시는 경우에는 반드시 카운터에 문의바랍니다.
					<br>
					1시간 단위로 연장이 가능합니다.
					<br>
					요금은 예약된 시간을 기준으로 정산됩니다.
					<br>
					<br>
					[교실이용안내]
					<br>
					다음 이용자들을 위해 깨끗하게 사용하시고, 5분 전 미리 퇴실바랍니다.
					<br>
					외부음료 및 외부음식 반입금지되어있습니다.
					<br>
					룸내 음식물 섭취 불가합니다.(물 및 내부 카페음료는 가능합니다.)
					<br> 
					교실 및 건물내 흡연 및 음주는 금지되어 있습니다.
					<br>
					<br>
					[취소/변경 안내]
					<br>
					시간 및 인원 변동이 있을 시 가능한 한 빠른 시일 내에 부탁드립니다.
					</span>
				</p>
				
						
			</div><!-- end of section_booking -->
		
		<div class="wrap">
					<div class="pop_inner">
						<div class="page-main">
							<div class="content-main">
								<h2 class="res-title">예약하기</h2>
								<form action="reserve.do" method="post" id="write_form">
									<input type="hidden" name="mem_num" id="mem_num" value="${detail.mem_num}">
									<input type="hidden" name="room_num" id="room_num" value="${room.room_num}">
									<ul>
										<li>
											<label>예약자 이름</label>
											<input type="text" name="name" id="name" maxlength="50" value="${detail.mem_name}" readonly>
										</li>
										<li>
											<label>예약날짜</label>
											<input type="text" name="res_date" id="res_date" maxlength="10">
											<span>입력 예)23/01/13</span>
										</li>
										<li>
											<label>예약시간</label>
											<input type="text" name="res_time" id="res_time" maxlength="10">
											<span>입력 예)2-3</span>
										</li>
										<li>
											<label>인원수</label>
											<input type="number" name="res_count" id="res_count" min="1" max="30">
										</li>
										<li>
											<label>방 번호</label>
											<input type="text" name="room" id="room" value="${room.room_num}" readonly>
										</li>
										<input type="submit" value="예약완료" class="confirm">
									</ul>
									
								</form>
							</div>
						</div>
 					</div><!-- end of inner -->
				</div>
			</div>
		</div><!-- end of wrap_right -->
		
	</div><!-- end of wrap_inn -->
</div><!-- end of container -->
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>