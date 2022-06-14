<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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