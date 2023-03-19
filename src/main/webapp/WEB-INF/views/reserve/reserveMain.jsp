<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_hoon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$(".btn_open").click(function(){
			$(".containerr").css({
				"height":"200vh"
			});
		});
	});
</script>
</head>
<body>
	<div class="wrap" id="wrap">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<c:if test="${!empty user_num && user_auth == 9}">
		<li class="li">
			<input type="button" value="방 등록"  class="roomroom" onclick="location.href='${pageContext.request.contextPath}/room/roomWriteForm.do'">
		</li>
		</c:if>
	
		<div class="containerr" id="container">
			<div class="containerr-lst">
				<div class="wrap_inn">
					<div class="wrap_main_info">
						<div class="section_visual section_visual_main">
							<div class="group_visual">
								<div class="container_visual">
									<ul class="visual_img">
										<li class="item">
										 	<img src="../images/hoon/room1.jpg" class="img_thumb" width="490px;" height="490px;">
										</li>
									</ul>
								</div><!-- end of container_visual -->
							</div><!-- end of group_visual -->
						</div><!-- end of section_visual section_visual_main -->
						<div class="section_contents">
						 	<div class="contents_txt">
						 		<h2 class="contents_txt_tit">
						 			<span>퀵보드</span>
						 		</h2>
						 		<p>예약신청 후 확정/취소여부를 반드시 확인해주세요.</p>
						 	</div><!-- end of contents_txt -->
						 	<div class="contents_store_details _site_desc_con">
						 		<div class="store_details view_more">
							 		<p class="dsc">
								 	*전화예약불가합니다. 네이버예약 부탁드립니다.(당일예약만 전화 혹은 현장에서 가능합니다)
								 	<br>
								 	*1월은 1일, 21~24일 휴무입니다.
								 	<br>
								 	*예약은 최소 1시간 이상, 정시단위로 가능합니다.
								 	<br> 
								 	(30분씩 예약은 불가합니다.)
								 	<br>
								 	*방 형태는 상이하며 예약 상황에 따라 공간이 변경될 수 있습니다(반드시 선호하시는 룸이 있다면 사전요청 부탁드립니다)
								 	<br>
								 	<br>
								 	*예약 확정/취소여부를 신청 후 반드시 확인하고 와주세요.
								 	<br>
								 	*외부음료 및 음식반입 금지되어 있습니다.
								 	<br>
								 	*취소는 전날저녁6시까지만 가능하며 6시이후~당일취소(및 변경)시 위약금발생됩니다(룸별상이 하단참조)
								 	<br>
								 	*코로나로인한 노쇼 및 당일취소 및 변경도 위약금 동일하게 발생됩니다.
								 	<br>
								 	*전날18시이후 취소시 위약금
								 	<br>
								 	*2~3시간/4~6시간/7~12시간*:
								 	<br>
								 	최소1인실 10,000원/12,000원/18,000원
								 	<br>
								 	최소4,5인실: 14,400원/16,000원, 24,000원
								 	<br>
								 	최소6인실 16,000원/24,000원/36,000원
								 	<br>
								 	최소12인실:36,000원/40,000원/60,000원
								 	<br>
									</p>
								 	<ul class="store_features">
								 	</ul>
								</div><!-- end of store_details view_more -->
							</div><!-- end of contents_store_details _site_desc_con -->
								<span>
								<a href="#pop_info_1" class="btn_open">방 검색하기</a>
								</span>
						</div><!-- end of section_contents -->
					</div><!-- end of wrap_main_info -->
					
				</div><!-- end of wrap_inn -->
					<div id="pop_info_1" class="pop_wrap" style="display:none;">
							<div class="section_item_lst">
									<div class="wrap_lst_item_box">
										<div class="image-space">
											<c:forEach var="room" items="${roomList}">
												<div class="horizontal-area">
													<a class="lnk_item_info" href="${pageContext.request.contextPath}/room/roomDetail.do?room_num=${room.room_num}">
														<img src="${pageContext.request.contextPath}/upload/${room.photo1}">
														<span class="name_title">${room.room_name}</span>
														<p class="detail">${room.room_detail}</p>
													</a>
												</div>
												
											</c:forEach>
										</div>
									</div>
							</div>
	 					
	 					</div>
					<script>
					var target = document.querySelectorAll('.btn_open');
					var btnPopClose = document.querySelectorAll('.pop_wrap .btn_close');
					var targetID;
			
					// 팝업 열기
					for(var i = 0; i < target.length; i++){
					  target[i].addEventListener('click', function(){
					    targetID = this.getAttribute('href');
					    document.querySelector(targetID).style.display = 'block';
					  });
					}
			
					</script>
			</div>
		</div><!-- end of container -->
	</div><!-- end of wrap -->
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>