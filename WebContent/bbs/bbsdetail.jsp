<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="Content-Type" content="text/html;charset=UTF-8">
<meta name="viewport" content="width=device-width" initial-scale="1">
<link rel="stylesheet" href="css/bootstrap.css">
<title>글 열람</title>
<style>
*{margin 4px 0;}
.controller { padding:25px 0; margin: auto; width:840px;}
#wriTitle{text-align: center; background-color: #2b2b2b; width:840px; height: 25px; padding:12px 0; color: white;}
table { width:840px; margin:15px 0; padding:15px; border-collapse:collapse;}
th {background-color : #2b2b2b; color : white; height : 45px;}
#title{width : 840px; height: 24px;}
textarea{width: 840px; height: 650px;}
.button {margin : 4px 0; padding: 10px 0; width: 100px; background-color: #6bb086; color : white; border: none;}
button {align: center; margin : 4px 0; padding: 10px 0; width: 100px; background-color: #a34654; color : white; border: none;}
textarea {resize:none;}
.menu a{cursor:pointer;}
.menu .hide{display:none;}
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
						<a>권한 : ${user.u_class}</a>
						</div>
					</li>
				</ul>
				</c:otherwise>
				</c:choose>
		</nav>
	<div class = "controller">
		<div class ="row">
		<div id = "bbsTitle">
			<table class="table table-striped" style="text-align:left;">
				<thead>
					<tr>
						<th colspan="2" align="center" >${bbs.bbsTitle}</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width: 20%"><strong>작성자</strong></td>
						<td colspan="2">${bbs.userID}</td>
					</tr>
					<tr>
						<td style="width: 20%" ><strong>작성일</strong></td>
						<td colspan="2">${bbs.bbsDate}</td>
					</tr>
					<tr>
						<td style="width: 20%"><strong>조회수</strong></td>
						<td colspan="2">${bbs.bbsView}</td>
					</tr>
					<tr id="content" valign="top" style="border-top-color: rgb(100, 100, 100); border-top-width: 5px">
						<td style="width: 20%"><strong>내용</strong></td>
						<td colspan="3">${bbs.bbsContent }</td>
					</tr>
					<c:if test="${not empty file.bbsNo}">		
						<tr>
							<td>
								<br><br><img src ="/bbs/img/${file.fileRealName}"><br><br>
							</td>
						</tr>
					</c:if>
					<c:if test="${not empty file.fileRealName2}">
						<tr>
							<td>
								<br><br><img src ="/bbs/img/${file.fileRealName2}"><br><br>
							</td>
						</tr>
					</c:if>
			</table>
			<div class="container">
				<div class="form-group">
					<form method="post">
						<input type="hidden" name="bbsNo" value="${bbs.bbsNo}">
						<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
							<tr>
								<td align = "center" style = "border-bottom:none; text-align: center;">${user.u_name}</td>
								<td><input type="text" style="width:600px; height:25px;" class="form-control" placeholder="Comment" name="commentText" id="commentText"></td>
								<td><input type="button" style="width: 100px; height: 30px; background-color : #ffffff" class="btn-primary pull" value="댓글 등록" id="btnComment"></td>
							</tr>
						</table>
					</form>	
				</div>	
			</div>
			<div class="container" id="commentList">
				<div class="row">
					<table class="table table-striped" style="text-align :center; border: 1px solid #dddddd">
						<tbody>
							<tr>
								<td style="width: 20%" align="center"><strong>Comment</strong></td>
							</tr>
							<tr>
								<td>
									<div class = "container">
										<div class="row">
											<table class="table table-striped" style="text-align:center; border: 1px solid #dddddd">
												<c:forEach items="${list1}" var="item">
													<c:if test="${not empty list1}">
														<tbody>
															<tr>
																<td align="left" nowrap><strong>
																	<c:if test="${item.commentDepth > 0}">
																		<c:forEach begin="1" end="${item.commentDepth}">
										 									&nbsp; &nbsp; &nbsp; RE:
										 								</c:forEach>
																	</c:if>
																${item.userID}</strong></td>
																<td colspan="2" nowrap></td>
																<td align="right" class="region" nowrap>
																	<a class="show">답글</a>
																	<c:if test="${user.u_name == item.userID}">
																		<a class="show1">수정</a>
																	</c:if>
																	<c:if test="${user.u_name == item.userID or user.u_class == 'admin'}">
																		<a class="commentTextDelete" cno="${item.commentNo }">삭제</a>
																	</c:if>
																</td>
															</tr>
															<tr style="display:none;">
																<td><input type="text" style="width:600px; height:25px;" class="commentTextReply" placeholder="Comment" name="commentTextReply"></td>
																<td><input type="button" style="width: 50px; height: 30px; background-color : #ffffff" class="btn-primary pull btnCommentReply" value="등록" cno="${item.commentNo }" group="${item.commentGroup}" order="${item.commentOrder }" depth="${item.commentDepth }"></td>
															</tr>
															<tr style="display:none;">
																<td><input type="text" style="width:600px; height:25px;" class="commentTextUpdate" placeholder="Comment" name="commentTextUpdate"></td>
																<td><input type="button" style="width: 50px; height: 30px; background-color : #ffffff" class="btn-primary pull btnCommentUpdate" value="수정" cno="${item.commentNo }"></td>
															</tr>
															<tr>
																<td colspan="5" align="left">
																	<c:if test="${item.commentDepth > 0}">
																		<c:forEach begin="1" end="${item.commentDepth}">
										 									 &nbsp; &nbsp; &nbsp;
										 								</c:forEach>
																	</c:if>
																${item.commentText}</td>
															</tr>
														</tbody>
													</c:if>
												</c:forEach>
											</table>
										</div>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<c:if test="${user.u_name == bbs.userID}">
			<button style="background-color : #696969" id="bbsUpdate">수정</button>
			</c:if>
			<c:if test="${user.u_name == bbs.userID || user.u_class == 'admin'}">
			<button style="background-color : #911616" id="bbsDelete">삭제</button>
			</c:if>
			<button style="background-color : #000000" id="bbsReply">답글</button>
			<div id="btnCon" align="center">
				<a href="bbs.do">
					<button class="btn2" style="background-color : #000000">목록</button>
				</a>
			</div>
		</div>
	</div>
</div>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script>
$(document).on('click', '#btnComment', function () {
	let contents = $(this).parent().prev().find('input[name="commentText"]').val();
	
	$.ajax({
		method: "POST",
		url: "bbscommentprocess.do",
		data: { commentText:$("#commentText").val(), bbsNo: '${bbs.bbsNo}'}
	})
	.done(function( html ) {
		$('#commentList').html(html);
	});
});

$(document).on('click', '.btnCommentReply', function () {
	let cno = $(this).attr('cno');
	let group = $(this).attr('group');
	let order = $(this).attr('order');
	let depth = $(this).attr('depth');
	let contents = $(this).parent().prev().find('input[name="commentTextReply"]').val();
	
	$.ajax({
		method: "POST",
		url: "bbscommentreplyprocess.do",
		data: { commentText:contents, bbsNo: '${bbs.bbsNo}', commentNo: cno, commentGroup: group, commentOrder: order, commentDepth: depth}
	})
	.done(function( html ) {
		$('#commentList').html(html);
	});
	
	console.log(false == '0');
});
$(document).on('click', '.btnCommentUpdate', function () {
	let cno = $(this).attr('cno');
	let contents = $(this).parent().prev().find('input[name="commentTextUpdate"]').val();
	
	$.ajax({
		method: "POST",
		url: "bbscommentupdateprocess.do",
		data: { commentText:contents, bbsNo: '${bbs.bbsNo}', commentNo: cno}
	})
	.done(function( html ) {
		$('#commentList').html(html);
	});
	
	console.log(false == '0');
});

$(document).on('click', '.commentTextDelete', function () {
	let cno = $(this).attr('cno');
	$.ajax({
		method: "POST",
		url: "bbscommentdelete.do",
		data: {bbsNo: '${bbs.bbsNo}', commentNo: cno}
	})
	.done(function( html ) {
		$('#commentList').html(html);
	});
	
	console.log(false == '0');
});
$(document).on('click', '#bbsUpdate', function () {
	location.href = "bbsupdate.do?bbsNo=${bbs.bbsNo}";
});

$(document).on('click', '#bbsDelete', function () {
	location.href = "bbsdeleteprocess.do?bbsNo=${bbs.bbsNo}";
});

$(document).on('click', '#bbsReply', function() {
	location.href = "bbsreply.do?bbsNo=${bbs.bbsNo}&bbsGroup=${bbs.bbsGroup}&bbsOrder=${bbs.bbsOrder}&bbsDepth=${bbs.bbsDepth}";
});

$(document).on('click', '.show', function () {
	let $dis = $(this).parent().parent().next(); 
	
    if($dis.css('display') == 'none'){
        $dis.show();
    }else{
        $dis.hide();
    }
});
$(document).on('click', '.show1', function () {
	let $dis = $(this).parent().parent().next().next(); 
	
    if($dis.css('display') == 'none'){
        $dis.show();
    }else{
        $dis.hide();
    }
});
</script>
</body>
</html>