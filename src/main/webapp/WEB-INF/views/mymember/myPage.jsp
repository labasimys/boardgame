<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_ddk.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
   <jsp:include page="/WEB-INF/views/common/header.jsp" />
   
         <div class="mypage-div">
         <div class="allbox1">
            <!--프로필 사진 시작 -->
            	<div id="div01">
	               <c:if test="${empty member.mem_photo}">
	                  <img src="${pageContext.request.contextPath}/images/face.png"
	                     width="200" height="200" class="my-photo">
	               </c:if>
	               <c:if test="${!empty member.mem_photo}">
	                  <img src="${pageContext.request.contextPath}/upload/${member.mem_photo}"
	                     width="200" height="200" class="my-photo">
	               </c:if>
	             </div>
               <div id="div02">
	               <ul>
	                  <li>
	                  	 <p id="myname"><b>${member.mem_id}</b></p>
	                  </li>
	                  <li>
	                     <button type="button" class="bluebtn-mypage" onclick="location.href='modifyUserForm.do'">회원정보수정</button>
	                  </li>
	               </ul>
               </div>
            <!-- 프로필 사진 옆 ID/버튼 끝 -->
            </div>
            <!-- allbox1 끝 -->
            <div class="float-end"></div>
<div class="page-main">    
            <!-- allbox2 시작 -->
            <div class="allbox2">
	            <div class="mypageblock01">
	            	<div id="mypage01" onclick="location.href='myInquiryList.do'" style="cursor:pointer">
	            		<div class="mypage-line1-a">
	                        <img src="${pageContext.request.contextPath}/images/dongk/문의.png" width="50" height="50" class="">
	                    </div>
	                    <div class="mypage-line1-a"><b>작성한 문의</b></div>
	                    <div class="mypage-line1-a bluefont"><b>${count1}</b></div>
	                        
	                </div>
	                <div id="mypage01" onclick="location.href='myReviewList.do'" style="cursor:pointer">
	                    <div class="mypage-line1-a">
	                        <img src="${pageContext.request.contextPath}/images/dongk/댓글.png" width="50" height="50" class="">
	                    </div>
	                    <div class="mypage-line1-a"><b>작성한 댓글</b></div>
	                    <div class="mypage-line1-a bluefont"><b>${count2}</b></div>
	                        
	                </div>
	                <div id="mypage02" onclick="location.href='${pageContext.request.contextPath}/cart/cart.do'" style="cursor:pointer">
	                    <div class="mypage-line1-a">
	                        <img src="${pageContext.request.contextPath}/images/dongk/장바구니.png" width="50" height="50" class="">
	                    </div>
	                    <div class="mypage-line1-a"><b>장바구니</b></div>
	                    <div class="mypage-line1-a redfont"><b>${count3}</b></div>  
	                </div>
	            </div>
	            <div class="mypageblock02">
	            	<ul id="mypageul">
	                  <li>
	                     <div class="mypage-line2"  onclick="location.href='myReserve.do'" style="cursor:pointer"><b>예약내역</b></div>
	                  </li>
	                  <li>
	                     <div class="mypage-line2"  onclick="location.href='myOrderList.do'" style="cursor:pointer"><b>내가 구매한 보드게임</b></div>
	                  </li>
	               </ul>
	            </div>
            </div>
            <!-- allbox 끝 -->
            <!-- 회원탈퇴버튼 시작 -->
            <div class="Withdrawal">
               <button type="button" class="redbtn" onclick="location.href='deleteUserForm.do'">회원탈퇴</button>
            </div>
            <div class="float-end"></div>
            <!-- 회원탈퇴버튼 끝 --> 
            
         </div>
   </div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>   
</body>
</html>