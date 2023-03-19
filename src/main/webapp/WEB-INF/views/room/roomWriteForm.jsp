<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방 등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_hoon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){ //이거 쓸거면 위에 jquery링크 걸어야함 꼭
		$('#write_form').submit(function(){
			if($('#name').val().trim()==''){ //비어있는거 인정X, 공백은 가능
				alert('방 이름을 입력하세요');
				$('#name').val('').focus();
				return false;
			}
			if($('#size').val()==''){ //공백을 인정하지 않기 때문에 trim 안씀
				alert('최대인원을 입력하세요');
				$('#size').focus();
				return false;
			}
			if($('#detail').val().trim()==''){ //비어있는거 인정X, 공백은 가능
				alert('방 간단설명을 입력하세요');
				$('#detail').val('').focus();
				return false;
			}
			if($('#detail2').val().trim()==''){ //비어있는거 인정X, 공백은 가능
				alert('방 상세설명을 입력하세요');
				$('#detail2').val('').focus();
				return false;
			}
			if($('#photo1').val()==''){ //공백을 인정하지 않기 때문에 trim 안씀
				alert('방사진1을 입력하세요');
				$('#photo1').focus();
				return false;
			}
		});
	});
</script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<div class="content-mainn">
			<h2>방 등록</h2>
			<form action="write.do" method="post" id="write_form" enctype="multipart/form-data">
				<ul class="form-room">
					<li>
						<label>방 이름</label>
						<input type="text" class="name" name="name" id="name" maxlength="10">
					</li>
					<li>
						<label>최대 인원 수</label>
						<input type="number" name="size" id="size" min="1" max="99">
					</li>
					<li>
						<label>방 간단설명</label>
						<textarea rows="5" cols="30" name="detail" id="detail"></textarea>
					</li>
					<li>
						<label>방 상세설명</label>
						<textarea rows="5" cols="30" name="detail2" id="detail2"></textarea>
					</li>
					<li>
						<label>방 사진1</label>
						<input type="file" name="photo1" id="photo1" class="photo1"accept="image/gif,image/png,image/jpeg">
					</li>
					<li>
						<label>방 사진2</label>
						<input type="file" name="photo2" id="photo2" accept="image/gif,image/png,image/jpeg">
					</li>
					<li>
						<label>방 사진3</label>
						<input type="file" name="photo3" id="photo3" accept="image/gif,image/png,image/jpeg">
					</li>
					
				</ul>
				<div class="align-centerr">
					<input type="submit" value="등록" class="btn-b">
					<input type="button" value="목록으로" class="btn-b" onclick="location.href='${pageContext.request.contextPath}/reserve/reserveMain.do'">
				</div>
			</form>
		</div>
	</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>