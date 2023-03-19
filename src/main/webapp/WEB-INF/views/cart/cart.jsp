<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>장바구니</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_ahn.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function() {
		//장바구니 상품 삭제
		$('.cart-del').on('click', function() {
			$.ajax({
				url:'deleteCart.do',
				type:'post',
				data:{cart_num:$(this).attr('data-cartnum')},
				dataType:'json',
				success:function(param){
					if(param.result == 'logout'){
						alert('로그인 후 사용하세요!');
					}else if(param.result == 'success'){
						alert('선택하신 상품을 삭제했습니다.');
						location.href='cart.do';
					}else{
						alert('장바구니 상품 삭제 오류!');
					}
				},
				error:function(){
					alert('네트워크 오류 발생!');
				}
			});
		});//end of click - 장바구니 삭제
		//장바구니 상품 수량 변경
		$('.cart-modify').on('click',function(){
			let input_count = 
				$(this).parent().find('input[name="cart_count"]');
			if(input_count.val()==''){
				alert('수량을 입력하세요!');
				input_count.focus();
				return;
			}
			if(isNaN(input_count.val())){
				input_count.val(input_count.attr('value'));
				return;
			}
			if(input_count.val()<1){
				alert('상품의 최소 수량은 1입니다.');
				input_count.val(input_count.attr('value'));
				return;
			}
			
			//서버와 통신
			$.ajax({
				url:'modifyCart.do',
				type:'post',
				data:{cart_num:$(this).attr('data-cartnum'),pro_num:$(this).attr('data-pronum'),cart_count:input_count.val()},
				dataType:'json',
				success:function(param){
					if(param.result == 'logout'){
						alert('로그인 후 사용하세요!');
					}else if(param.result == 'noSale'){
						alert('판매가 중지되었습니다.');
						location.href='cart.do';
					}else if(param.result == 'noQuantity'){
						alert('상품의 수량이 부족합니다.');
						location.href='cart.do';
					}else if(param.result == 'success'){
						alert('상품의 개수가 수정되었습니다.');
						location.href='cart.do';
					}else{
						alert('장바구니 상품 개수 수정 오류');
					}
				},
				error:function(){
					alert('네트워크 오류 발생');
				}
			});
			
		});
	});
</script>
</head>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<body>
<div class="page-main">
	<div class="content-photo">
		<c:if test="${empty cart}">
		<div class="result-display">
			장바구니에 담은 상품이 없습니다.
		</div>
		</c:if>
		<c:if test="${!empty cart}">
		<div class="result-name">
			<h2><span class="result-display">${user_id}</span>님의 장바구니</h2>
		</div>
		<div class="result">
			<form id="cart_order" method="post"
			action="${pageContext.request.contextPath}/order/orderForm.do">
				<div class="cart-main"  style="height:300px; overflow:auto;">
				<table style="width:95%;">
					<c:forEach var="cart" items="${cart}">
					<tr>
						<td colspan="6"><br></td>
					</tr>
					<tr>
						<td class="align-center" width="82">
							<a href="${pageContext.request.contextPath}/game/gameDetail.do?pro_num=${cart.pro_num}">
								<img src="${pageContext.request.contextPath}/upload/${cart.listVo.pro_picture}" width="80">
							</a>
						</td>
						<td class="align-center">
							<a href="${pageContext.request.contextPath}/game/gameDetail.do?pro_num=${cart.pro_num}">
								${cart.listVo.pro_name}
							</a>
						</td>
						<td class="align-center">
							<fmt:formatNumber value="${cart.listVo.pro_price}"/>원
						</td>
						<td class="align-center">
							<c:if test="${cart.listVo.pro_status==1 or cart.listVo.pro_count < cart.cart_count}">[판매중지]</c:if>
							<c:if test="${cart.listVo.pro_status!=1 and cart.listVo.pro_count >= cart.cart_count}">
							<input type="number" name="cart_count" min="1" max="${cart.listVo.pro_count}" 
									value="${cart.cart_count }" class="count_width"style=" width:40px;">
							<br>
							<input type="button" value="변경" class="cart-modify btn"
											data-cartnum="${cart.cart_num}" 
											data-pronum="${cart.pro_num}">
							</c:if>
						</td>
						<td class="align-center">
							합계: <fmt:formatNumber value="${cart.sub_total}"/>원
						</td>
						<td>						
							<input type="button" value=" Ｘ " class="cart-del"
												data-cartnum="${cart.cart_num}">
						</td>
					</tr>
					<tr>
						<td colspan="6"><br><hr size="1px" width="90%"></td>
					</tr>
					</c:forEach>
				</table>
				</div><!--end of cart main-->
			<div class="price-total align-center"  style="height:50px;"><br><b>총구매금액:</b>
					<fmt:formatNumber value="${all_total}"/>원
					</div>				
					<input class="btn" type="submit" value="구매하기" style="height:25px;">
					<input class="shopping btn"  type="button" value="쇼핑계속하기" onclick=
						"location.href='${pageContext.request.contextPath}/game/gameList.do'" style="height:25px;">
			</form>
			</div>
		</c:if>
	</div><!--end of content main-->
</div><!--end of page main-->
</body>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</html>