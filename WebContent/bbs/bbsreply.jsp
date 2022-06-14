<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="text/html;charset=UTF-8">
<link rel="stylesheet" href="css/bootstrap.css">
<title>답글 작성</title>
<style>
*{margin 4px 0;}
.controller { padding:25px 0; margin: auto; width:840px;}
#wriTitle{text-align: center; background-color: #2b2b2b; width:850px; height: 25px; padding:12px 0; color: white;}
table { width:840px; margin:25px 0; padding:25px; border-collapse:collapse;}
#title{width : 840px; height: 24px;}
textarea{width: 840px; height: 650px;}
.button {margin : 4px 0; padding: 10px 0; width: 100px; background-color: #000000; color : white; border: none;}
button {align: center; margin : 4px 0; padding: 10px 0; width: 100px; background-color: #a34654; color : white; border: none;}
textarea {resize:none;}
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
	<div id="wriTitle"><b>답글 작성</b></div>
	<form action="bbsreplyprocess.do" method="post">
		<input type="hidden" name="bbsNo" value="${bbs.bbsNo }">
		<input type="hidden" name="bbsGroup" value="${bbs.bbsGroup }">
		<input type="hidden" name="bbsOrder" value="${bbs.bbsOrder }">
		<input type="hidden" name="bbsDepth" value="${bbs.bbsDepth }">
		<table>
			<tr>
				<td><input type="text" name="title" required id = "title" />
				</td>
			</tr>
			<tr>
				<td colspan="2"><textarea rows="12" cols="50" name="content" required></textarea></td>
			</tr>
			<tr>
				<td align="center"><input type="submit" value="등록" class="button"></td>
			</tr>
		</table>
	</form>
		<div align = "center">
			<a href="bbs.do">
			<button>취소</button>
			</a>
		</div>
	</div>
</body>
</html>