package com.bbs.testmvc.service;

import java.util.ArrayList;

import com.bbs.testmvc.dao.CommentDAO;
import com.bbs.testmvc.vo.Bbs;
import com.bbs.testmvc.vo.Comment;

public class CommentService {
	

	private static CommentService service = null;
	private static CommentDAO dao = null;
	
	private CommentService() {
		
	}
	
	public static CommentService getInstance() {
		if(service == null) {
			service = new CommentService();
			dao = CommentDAO.getInstance();
		}
		return service;
	}
	
	public ArrayList<Comment> getList(int bbsNo) {
		return dao.getList(bbsNo);
	}
	
	public int write(Comment comment) {
		return dao.write(comment);
	}
	
	public Comment getComment(int commentNo) {
		return dao.getComment(commentNo);
	}
	
	public void delete(int bbsNo, int commentNo) {
		dao.delete(bbsNo, commentNo);
	}
	
	public Comment selectComment(int commentNo) {
		return dao.selectComment(commentNo);	
	}
	
	public void updateComment(Comment comment) {
		dao.updateComment(comment);
	}
	
	public static void replyComment(Comment comment) {
		dao.replyComment(comment);
	}
	
	public static void updateOrder(int commentGroup, int commentOrder) {
		dao.updateOrder(commentGroup, commentOrder);
	}
}
