<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html> 
<html>
<head>
<meta charset="UTF-8">
<title>문의사항</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_hyem.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<h2>Q & A</h2>
	<hr size="1" noshade="noshade" width="100%">
	<c:if test="${count == 0}">
		<div class="result-display">
			등록된 문의가 없습니다.
		</div>
	</c:if>
	
	<c:if test="${count > 0}">	
		<table>
			<tr class="border-bot">
				<th style="width: 70px;">번호</th>
				<th style="width: 180px;"></th>
				<th>제목</th>
				<th style="width: 150px;">작성자</th>
				<th style="width: 150px;">작성일</th>
				<th style="width: 100px;">조회수</th>
			</tr>
			<c:forEach var="inquiry" items="${list}" varStatus="status">
				<tr class="table-content-bot">
					<td>${inquiry.rownum}</td>
					<c:if test="${inquiry.inqu_rpl == 0}"><td style="color: #b1b1b1">${inquiry.inqu_cate}</td></c:if>
					<c:if test="${inquiry.inqu_rpl != 0}"><td></td></c:if>
					<td style="text-align: left;">
						<c:if test="${inquiry.inqu_rpl == 0}">
							<a href="inquiryDetail.do?inqu_num=${inquiry.inqu_num}">
								${inquiry.inqu_title}
								<c:if test="${inquiry.inqu_check == 1}">
									<img src="${pageContext.request.contextPath}/images/hyem/lock.png" width="10">
								</c:if>
							</a>
						</c:if>
						<c:if test="${inquiry.inqu_rpl != 0}">
							<a href="inquiryReplyDetail.do?inqu_num=${inquiry.inqu_num}">
								<img src="${pageContext.request.contextPath}/images/hyem/reply.png" width="10">
								<i>Re : </i>${inquiry.inqu_title}
								<c:if test="${inquiry.inqu_check == 1}">
									<img src="${pageContext.request.contextPath}/images/hyem/lock.png" width="10">
								</c:if>
							</a>
						</c:if>
					</td>
					<c:if test="${inquiry.mem_auth == 9}">
						<td>관리자</td>
					</c:if>
					<c:if test="${inquiry.mem_auth != 9}">
						<td>${inquiry.mem_id}</td>			
					</c:if>
					<td>${inquiry.inqu_reg_date}</td>
					<td>${inquiry.inqu_hit}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<hr size="1" noshade="noshade" width="100%">
	<c:if test="${count > 0}">
		<div class="align-center paging">
			${pagingHtml}
		</div>	
	</c:if>
	
	<c:if test="${!empty user_num && user_auth == 2}">
		<div class="align-right">
			<input type="button" class="btn" value="글쓰기" onclick="location.href='inquiryWriteForm.do'">
		</div>
	</c:if>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>