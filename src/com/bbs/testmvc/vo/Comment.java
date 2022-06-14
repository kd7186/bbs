package com.bbs.testmvc.vo;

public class Comment {
	private int bbsNo;
	private int commentNo;
	private String userID;
	private String commentText;
	private int commentAvailable;
	private int commentGroup;
	private int commentOrder;
	private int commentDepth;
	
	
	public int getBbsNo() {
		return bbsNo;
	}
	public void setBbsNo(int bbsNo) {
		this.bbsNo = bbsNo;
	}
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public int getCommentAvailable() {
		return commentAvailable;
	}
	public void setCommentAvailable(int commentAvailable) {
		this.commentAvailable = commentAvailable;
	}
	public int getCommentGroup() {
		return commentGroup;
	}
	public void setCommentGroup(int commentGroup) {
		this.commentGroup = commentGroup;
	}
	public int getCommentOrder() {
		return commentOrder;
	}
	public void setCommentOrder(int commentOrder) {
		this.commentOrder = commentOrder;
	}
	public int getCommentDepth() {
		return commentDepth;
	}
	public void setCommentDepth(int commentDepth) {
		this.commentDepth = commentDepth;
	}
}
