<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
													<a onclick="window.open('bbscommentupdate.do?bbsNo=${item.bbsNo}&commentNo=${item.commentNo}','댓글 수정','width=700,height=450, location=no, status=no, scrollbars=no');" class="btn-primary">수정</a>
													<a onclick="return confirm('정말로 삭제하시겠습니까?')" href="bbscommentdelete.do?bbsNo=${item.bbsNo}&commentNo=${item.commentNo}" class="btn-secondary">삭제</a>
												</c:if>
											</td>
										</tr>
										<tr style="display:none;">
											<td><input type="text" style="width:600px; height:25px;" class="commentTextReply" placeholder="Comment" name="commentTextReply"></td>
											<td><input type="button" style="width: 50px; height: 30px; background-color : #ffffff" class="btn-primary pull" value="등록" class="btnCommentReply" cno="${item.commentNo }" group="${item.commentGroup}" order="${item.commentOrder }" depth="${item.commentDepth }"></td>
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