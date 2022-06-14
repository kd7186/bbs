<%@page import="javax.security.auth.callback.ConfirmationCallback"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import = "java.io.PrintWriter" %>
 <%@ page import="java.util.ArrayList"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name = "viewport" content="width=device-width" initial-scale="1.0">
<link
      href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;700&display=swap"
      rel="stylesheet"
    />
<link rel= "stylesheet" href="styles.css">
<title>글 목록</title>
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
	border-collapse: collapse;
	}
	th {
	background-color : rgb(100,100,100);
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
	<th width="40px">번호</th>
	<th width="510px">제목</th>
	<th width="120px">작성자</th>
	<th width="120px">날짜</th>
	<th width="50px">조회수</th>
	</tr>
		<c:forEach items="${list}" var="item">
			<tr>
			<td>${item.bbsNo}</td>
			<td>
				<c:if test="${item.bbsDepth > 0}">
					<c:forEach begin="1" end="${item.bbsDepth}">
					 	RE:
					 </c:forEach>
				</c:if>
				<b><a href="bbsdetail.do?bbsNo=${item.bbsNo}">${item.bbsTitle}</a></b>
			</td>
			<td>${item.userID}</td>
			<td>${item.bbsDate}</td>
			<td>${item.bbsView}</td>
			<tr>
			</c:forEach>
		</table>
		</div>
		<div style="display:inline">
					<ul style="width:400px; height:50px; margin:10px auto;" >
				<c:choose>
					<c:when test="${pagination.prevPage > 0}">
						<c:if test="${pagination.search.search eq null}">
						<li style="list-style:none; width:50px; line-height:50px; border:1px solid #ededed; float:left; text-align:center; margin:0 5px; border-radius:5px;">
							<a style="text-decoration:none; color:#000; font-weight:700;" href="bbs.do?page=${pagination.prevPage}">
								PREV
							</a>
						</li>
						</c:if>
						<c:if test="${pagination.search.search ne null}">
						<li style="list-style:none; width:50px; line-height:50px; border:1px solid #ededed; float:left; text-align:center; margin:0 5px; border-radius:5px;">
							<a style="text-decoration:none; color:#000; font-weight:700;" href="bbs.do?page=${pagination.prevPage}&find=${pagination.search.find}&search=${pagination.search.search}">
								PREV
							</a>
						</li>
						</c:if>
					</c:when>
				</c:choose>
				<c:forEach var="i" begin="${pagination.startPage}" end="${pagination.endPage}" step="1">	
					<c:choose>
						<c:when test="${ pagination.page eq i }">
							<li style="list-style:none; width:50px; line-height:50px; border:1px solid #ededed; float:left; text-align:center; margin:0 5px; border-radius:5px;">
								<span>${i}</span>
							</li>
						</c:when>
						<c:when test="${ pagination.page ne i }">
							<c:if test="${pagination.search.search eq null}">
							<li style="list-style:none; width:50px; line-height:50px; border:1px solid #ededed; float:left; text-align:center; margin:0 5px; border-radius:5px;">	
								<a style="text-decoration:none; color:#000; font-weight:700;" href="bbs.do?page=${i}">${i}</a>
							</li>
							</c:if>
							<c:if test="${pagination.search.search ne null}">
							<li style="list-style:none; width:50px; line-height:50px; border:1px solid #ededed; float:left; text-align:center; margin:0 5px; border-radius:5px;">	
								<a style="text-decoration:none; color:#000; font-weight:700;" href="bbs.do?page=${i}&find=${pagination.search.find}&search=${pagination.search.search}">${i}</a>
							</li>
							</c:if>
						</c:when>
					</c:choose>
				</c:forEach>
				<c:choose>
					<c:when test="${ pagination.nextPage <= pagination.lastPage }">
					<c:if test="${pagination.search.search eq null}">
						<li style="list-style:none; width:50px; line-height:50px; border:1px solid #ededed; float:left; text-align:center; margin:0 5px; border-radius:5px;">
							<a style="text-decoration:none; color:#000; font-weight:700;" href="bbs.do?page=${pagination.nextPage}">NEXT</a>
						</li>
					</c:if>
					<c:if test="${pagination.search.search ne null}">
						<li style="list-style:none; width:50px; line-height:50px; border:1px solid #ededed; float:left; text-align:center; margin:0 5px; border-radius:5px;">
							<a style="text-decoration:none; color:#000; font-weight:700;" href="bbs.do?page=${pagination.nextPage}&find=${pagination.search.find}&search=${pagination.search.search}">NEXT</a>
						</li>
					</c:if>
					</c:when>
				</c:choose>
			</ul>
		</div>
		<div align="center" style="width:100%; word-break:break-all; word-wrap:break-word;">
			<form name="sform" method="get" action="bbs.do">
    			<select name="find">
        			<option value="userID">작성자</option>
        			<option value="bbsTitle">제목</option>
       				<option value="bbsContent">내용</option>
    			</select>
    			<input name="search" type="text" class="form-control" size="20" placeholder="검색어를 입력하세요.">
    			<input type="submit" value="찾기">
			</form>
		</div>
		<div align="center">
	<p>
		<a href="bbsinsert.do"><button style="background-color:#000000">글쓰기</button></a><br />
	</p>
		</div>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.js"></script>
</body>
</html>