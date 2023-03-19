<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>카페소개</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_hyem.css">
<style>
/* -------카카오 지도 api---------- */
#map {
	margin: 0 auto;
	position: relative;
	width: 50%;
	left: 50px;
	border-radius: 20px;
	border: 2px #000000 solid;
	z-index: 2;
}

.slide-container {
	position: relative;
	width: 100%;
	margin: 0 auto;
}

/* ----------w3h 이미지 슬라이드------------ */

/* Hide the images by default */
.mySlides {
	display: none;
}
/* Add a pointer when hovering over the thumbnail images */
.cursor {
	cursor: pointer;
}
/* Number text (1/3 etc) */
.numbertext {
	color: #f2f2f2;
	font-size: 12px;
	padding: 8px 12px;
	position: absolute;
	top: 0;
}
/* Container for image text */
.caption-container {
	text-align: center;
	background-color: #222;
	padding: 2px 16px;
	color: white;
}

.row:after {
	content: "";
	display: table;
	clear: both;
}
/* Six columns side by side */
.column {
	float: left;
	width: 16.66%;
}
/* Add a transparency effect for thumnbail images */
.demo {
	opacity: 0.6;
}

.active, .demo:hover {
	opacity: 1;
}

.location {
	position: relative;
	float: left;
	left: 80px;
	top: 100px;
}

/* -----------페이지 마진-------------- */
.page-content {
	margin: 0 0 80px 0;
} 
.container-label{
	margin: 0 0 60px 0;
}
.hr-bottom{
	margin: 60px 0 60px 0;
}
</style>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="page-main">
		<div class="page-content">
			<div class="container-label">
				<div class="content-label">
					<h2>ROOM</h2>
				</div>
				<div class="row">
					<div class="column">
						<img class="demo cursor" src="../images/hoon/room1.jpg"
							style="width: 100%" onclick="currentSlide(1)" alt="2~4인">
					</div>
					<div class="column">
						<img class="demo cursor" src="../images/hoon/room2.jpg"
							style="width: 100%" onclick="currentSlide(2)" alt="4~6인">
					</div>
					<div class="column">
						<img class="demo cursor" src="../images/hoon/room3.jpg"
							style="width: 100%" onclick="currentSlide(3)" alt="6~8인">
					</div>
					<div class="column">
						<img class="demo cursor" src="../images/hoon/studyroom1.jpg"
							style="width: 100%" onclick="currentSlide(4)" alt="8~10인">
					</div>
					<div class="column">
						<img class="demo cursor" src="../images/hoon/studyroom2.jpg"
							style="width: 100%" onclick="currentSlide(5)" alt="10~12인">
					</div>
					<div class="column">
						<img class="demo cursor" src="../images/hoon/studyroom4.jpg"
							style="width: 100%" onclick="currentSlide(6)" alt="12인 이상">
					</div>
				</div>
				<div class="caption-container"></div>
				<div class="slide-container">
					<div class="mySlides">
						<div class="numbertext">1 / 6</div>
						<img src="../images/hoon/room1.jpg"
							style="display: block; margin: 0 auto; width: 100%; height: 500px;">
					</div>

					<div class="mySlides">
						<div class="numbertext">2 / 6</div>
						<img src="../images/hoon/room2.jpg"
							style="display: block; margin: 0 auto; width: 100%; height: 500px;">
					</div>

					<div class="mySlides">
						<div class="numbertext">3 / 6</div>
						<img src="../images/hoon/room3.jpg"
							style="display: block; margin: 0 auto; width: 100%; height: 500px;">
					</div>

					<div class="mySlides">
						<div class="numbertext">4 / 6</div>
						<img src="../images/hoon/studyroom1.jpg"
							style="display: block; margin: 0 auto; width: 100%; height: 500px;">
					</div>

					<div class="mySlides">
						<div class="numbertext">5 / 6</div>
						<img src="../images/hoon/studyroom2.jpg"
							style="display: block; margin: 0 auto; width: 100%; height: 500px;">
					</div>

					<div class="mySlides">
						<div class="numbertext">6 / 6</div>
						<img src="../images/hoon/studyroom4.jpg"
							style="display: block; margin: 0 auto; width: 100%; height: 500px;">
					</div>
				</div>
			</div>

			<hr class="hr-bottom" size="1" noshade="noshade" width="100%">

			<h2>NOTICE</h2>
			
			<hr size="1" noshade="noshade" width="100%">
			<table>
				<tr class="border-bot">
					<th>글 번호</th>
					<th>제목</th>
					<th>작성일</th>
					<th>조회수</th>
				</tr>
				<c:forEach var="notice" items="${noticeList}">
					<tr class="table-content-bot">
						<td>${notice.noti_num}</td>
						<td><a
							href="${pageContext.request.contextPath}/notice/noticeDetail.do?noti_num=${notice.noti_num}">${notice.noti_title}</a></td>
						<td>${notice.noti_reg_date}</td>
						<td>${notice.noti_hit}</td>
					</tr>
				</c:forEach>
			</table>
			<hr size="1" noshade="noshade" width="100%"><br><br>
			<hr class="hr-bottom" size="1" noshade="noshade" width="100%">
			<h2>LOCATION</h2>
			<br>
			<div class="location"><br>
				<h3 style="text-align: center;">서울 강남구 테헤란로 132 한독약품빌딩 8층</h3>
				<h3 style="text-align: center;">(우) 06235 (지번) 역삼동 735-1</h3>
			</div>
			<div id="map" style="width: 300px; height: 300px;"></div>
			<script type="text/javascript"
				src="//dapi.kakao.com/v2/maps/sdk.js?appkey=e8262126fa9b69618f5829c9fcf52dc7">
				
			</script>
			<script>
				var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
				mapOption = {
					center : new kakao.maps.LatLng(37.499190489487255,
							127.03332488938678), // 지도의 중심좌표
					level : 3
				// 지도의 확대 레벨
				};
				var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
				// 마커가 표시될 위치입니다
				var markerPosition = new kakao.maps.LatLng(37.499190489487255,
						127.03332488938678);
				// 마커를 생성합니다
				var marker = new kakao.maps.Marker({
					position : markerPosition
				});
				// 마커가 지도 위에 표시되도록 설정합니다
				marker.setMap(map);
				// 아래 코드는 지도 위의 마커를 제거하는 코드입니다
				// marker.setMap(null);
			</script>
			<script>
				let slideIndex = 1;
				showSlides(slideIndex);
				function plusSlides(n) {
					showSlides(slideIndex += n);
				}
				function currentSlide(n) {
					showSlides(slideIndex = n);
				}
				function showSlides(n) {
					let i;
					let slides = document.getElementsByClassName("mySlides");
					let dots = document.getElementsByClassName("demo");
					let captionText = document.getElementById("caption");
					if (n > slides.length) {
						slideIndex = 1
					}
					if (n < 1) {
						slideIndex = slides.length
					}
					for (i = 0; i < slides.length; i++) {
						slides[i].style.display = "none";
					}
					for (i = 0; i < dots.length; i++) {
						dots[i].className = dots[i].className.replace(
								" active", "");
					}
					slides[slideIndex - 1].style.display = "block";
					dots[slideIndex - 1].className += " active";
					captionText.innerHTML = dots[slideIndex - 1].alt;
				}
			</script>
		</div>
	</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>	
</body>
</html>

