<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게임 등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_seul.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function(){	
	$('#write_form').submit(function(){
		/* if($('input[type=radio]:checked').length<1){
			alert('상품표시여부를 지정하세요!');
			return false;ㄴ
		} */
		if($('#pro_name').val().trim()==''){
			alert('상품명을 입력하세요');
			$('#pro_name').val('').focus();
			return false;
		}
		if($('#pro_price').val()==''){
			alert('가격을 입력하세요');
			$('#pro_price').focus();
			return false;
		}
		if($('#pro_picture').val()==''){
			alert('게임사진을 등록하세요');
			$('#pro_picture').focus();
			return false;
		}
		if($('#pro_level').val().trim()==''){
			alert('난이도를 입력하세요');
			$('#pro_level').val('').focus();
			return false;
		}
		if($('#person').val()==''){
			alert('게임 추천 인원을 입력하세요');
			$('#person').focus();
			return false;
		}
		if($('#pro_count').val()==''){
			alert('수량을 입력하세요');
			$('#pro_count').focus();
			return false;
		}
		if($('#explanation').val().trim()==''){
			alert('상품설명을 입력하세요');
			$('#explanation').val('').focus();
			return false;
		}
	});
});	
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
<div class="page-main1">
	<h2>게임 등록</h2>
	<form action="write.do" method="post" id="write_form" enctype="multipart/form-data">
		<ul class="form-admin">
			<!-- <li>
				<label>상품표시여부</label>
				<input type="radio" name="pro_status" value="1"
				    id="status1">미표시
				<input type="radio" name="pro_status" value="2"
				    id="status2">표시    
			</li> -->
			<li>
				<label for="name">게임이름</label>
				<input type="text" name="pro_name" id="pro_name" maxlength="20">
			</li>
			<li>
				<label for="price">가격</label>
				<input type="number" name="pro_price" id="pro_price" min="1" max="99999">
			</li>
			<li>
				<label for="picture">게임 이미지</label>
				<input type="file" name="pro_picture" id="pro_picture" 
												accept="image/gif,image/pnp,image/jpeg,image/jfif,image/png">
			</li>
			<li><!-- 라디오 박스..? -> int로 변경 필요-->
				<label for="pro_level">게임 난이도</label>
				<input type="text" name="pro_level" id="pro_level" maxlength="5">
			</li>
			<li>
				<label for="person">게임 추천 인원</label>
				<input type="number" name="person" id="person" min="1" max="20">
			</li>
			<li>
				<label for="pro_count">게임 수량</label>
				<input type="number" name="pro_count" id="pro_count" min="1" max="30">
			</li>
			<li><!-- text타입...? -> String으로 변경필요 -->
				<label for="explanation">게임 설명</label>
				<textarea rows="5" cols="30" name="explanation" id="explanation"></textarea>
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="등록">
			<input type="button" value="목록으로" onclick="location.href='gameList.do'">
		</div>
	</form>
</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>

