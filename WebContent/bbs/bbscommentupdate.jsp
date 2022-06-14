<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글 수정</title>
</head>
<body>
	<div class="controller">
	<div id="wriTitle"><b>댓글 수정</b></div>
	<form action="bbscommentupdateprocess.do" method="post">
		<input type="hidden" name="bbsNo" value="${comment.bbsNo }">
		<input type="hidden" name="commentNo" value="${comment.commentNo }">
		<table>
			<tr>
				<td colspan="2" align="center"><textarea rows="12" cols="50" name="commentText" required>${comment.commentText}</textarea></td>
			</tr>
			<tr>
				<td align="center"><input type="submit" value="수정" class="button"></td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>