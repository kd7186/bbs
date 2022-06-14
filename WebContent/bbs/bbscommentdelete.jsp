<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>처리 중</title>
</head>
<body>
<script>
setTimeout(function () {
	window.location.href = "bbsdetail.do?bbsNo=${bbs.bbsNo}";
},100);
</script>
</body>
</html>