<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	alert('배송상태를 수정했습니다.');
	location.href='orderdetail.do?order_main_num='+${upstatus.order_main_num};
</script>