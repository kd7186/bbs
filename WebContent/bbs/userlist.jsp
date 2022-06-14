<%@page import="javax.security.auth.callback.ConfirmationCallback"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import = "java.io.PrintWriter" %>
 <%@ page import="java.util.ArrayList"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name = "viewport" content="width=device-width" initial-scale="1.0">
<link
      href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;700&display=swap"
      rel="stylesheet"
    />
<link rel= "stylesheet" href="styles.css">
<title>회원 목록</title>
<style>
.controller {
	padding: 25px 0;
	margin : auto;
	width : 840px;
	text-align: center;
	}
table {
	width: 840px
	padding : 10px 0;
	border-collapse: separate;
	}
	th {
	background-color : rgb(100,100,100);
	border-bottom: 1px solid #444444;
	color : white;
	}
	button {
	margin : 4px 0;
	padding: 10px 0;
	width: 100px;
	background-color: #1595a3;
	color : white;
	border: none;
}
	a{
	text-decoration: none;
	color: black;
	}
	a:hover {
		color: #000000;
		text-decoration-line: underline;
		}
		
	nav {
  display: flex;
 justify-content: space-around;
  align-items: center;
  background-color: #404040;
  color: white;
  margin : 0;
  padding : 0;
}

	nav ul li{
		display : inline-block;
	}
	
	.logo {
	letter-spacing:2px;
	font-size : 15px;
	text-align : left;
	float : center;
	}
	
	.logo a {
	color:white;
	padding : 16px 16px;
	}
	
	.dropdown {
		position : relative;
	}
	
	.dropdown-menu {
		color : white;
		font-size: 16px;
	}
	
	.dropdown-content {
			position: absolute;
   			 background-color: #f9f9f9;
    		min-width: 160px;
    		box-shadow: 0px 8px 24px 0px rgba(0,0,0,0.2);
    		display: none;
	}
	
	.dropdown-content a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
}   

	.dropdown-menu:hover:not(.home){
    background-color:#7a7a7a ;
    color: white;
}

	.dropdown:hover .dropdown-content {
    display: block;
}

	.dropdown-content a:hover {background-color: #f1f1f1}
	
</style>
</head>
<body>
			<nav>
			<div class="logo">
				<h3><a href="bbs.do">게시판</a></h3>
			</div>
				<c:choose>
				<c:when test="${sessionScope.user == null}">
				<ul>
					<li class="dropdown">
						<div class="dropdown-menu">메뉴</div>
						<div class="dropdown-content">
							<a href="login.do">로그인</a>
							<a href="join.do">회원가입</a>
						</div>
					</li>
				</ul>
				</c:when>
				<c:otherwise>
				<ul>
					<li class="dropdown">
					<div class="dropdown-menu">${sessionScope.user.u_name}님</div>
					<div class="dropdown-content">
						<a href="userlist.do">회원목록</a>
						<a href="logout.do">로그아웃</a>
						</div>
					</li>
				</ul>
				</c:otherwise>
			</c:choose>
		</nav>
		<div class="controller">
			<table>
				<tr>
				<th width="250px">ID</th>
				<th width="300px">이름</th>
				<th width="250px">권한</th>
				</tr>
				<c:forEach items="${userlist}" var="item" varStatus="status">
			<tr>
				<td>${item.u_id}</td>
				<td>${item.u_name}</td>
				<td>
				<c:choose>
					<c:when test="${item.u_class == 'normal'}">
						<a onclick="adminCheck1('${item.u_id}')" >${item.u_class}</a>
					</c:when>
					<c:otherwise>
						<a onclick="adminCheck2('${item.u_id}')">${item.u_class}</a>
					</c:otherwise>
					</c:choose>
					</td>
			</tr>
			</c:forEach>
			</table>

		</div>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.js"></script>	
<script> function adminCheck1(u_id) {
	if(confirm("관리자로 변경하시겠습니까?")) {
		location.href="userclassadmin.do?id="+u_id
	}
}
</script>
<script> function adminCheck2(u_id) {
	if(confirm("일반 사용자로 변경하시겠습니까?"))
		{location.href="userclassnormal.do?id="+u_id
	}
}</script>
</body>
</html>