<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>회원가입</title>
 <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
 <style>
 	.container {
 	width: 840px;
 	margin: 40px auto;
 	line-height: 16px;
 	}
 	h3 { text-align: center;}
 	#signup {background-color: #000000; color: white; border: 0; border-radius: 5px; padding: 10px 50px;}
 	.bottom input {background-color : white; border: 0; color: teal; font-size: 16px;}
 	span{ color:lightgray;}
 	#iperson1{position : absolute; top: 200px; margin:0 640px;}
 	#iperson2{position : absolute; top: 250px; margin:0 640px;}
 	#ipw{position : absolute; top: 300px; margin:0 640px;}
 	input{ border: 1px solid lightgray; border-radius: 3px;}
 	table { width:840px; margin:15px 0; padding:15px; border-collapse:collapse;}
 	th {background-color : #2b2b2b; color : white; height : 45px;}
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
	<div class="container">
		<div id="iperson1">
			<span class="material-icons">account_circle</span>
		</div>
		<div id="iperson2">
			<span class="material-icons">account_circle</span>
		</div>
		<div id="ipw">
			<span class="material-icons">lock</span>
		</div>
		<table class="table table-striped" style="text-align:left;">
				<thead>
					<tr>
						<th colspan="2" align="center" >회원가입</th>
					</tr>
				</thead>
		</table>
		<hr /><br />
	<form action="joinsuccess.do" name="user" method="post" align="center">
		<input type="text" placeholder="ID" name="id" required style="height:30px; width: 500px" /><br /><br />
		<input type="text" placeholder="이름" name="name" required style="height:30px; width: 500px" /><br /><br />
		<input type="password" placeholder="비밀번호" name="pw" required style="height:30px; width: 500px" /><br /><br />
		<p>
		<input type="submit" value="가입" id="signup" /><br/><br/>
		</p>
	</form>
	<hr/>
	</div>
</body>
</html>