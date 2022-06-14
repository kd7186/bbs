<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 성공</title>
</head>
<body>
<script>
alert("${sessionScope.user.u_name}님 안녕하세요.")
setTimeout(function () {
	window.location.href = "bbs.do";
},300);
</script>
</head>
<body>
</body>
</html>