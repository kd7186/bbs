package com.bbs.testmvc.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.Enumeration;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bbs.testmvc.dao.BbsDAO;
import com.bbs.testmvc.dao.CommentDAO;
import com.bbs.testmvc.service.BbsService;
import com.bbs.testmvc.service.CommentService;
import com.bbs.testmvc.service.FileService;
import com.bbs.testmvc.service.UserService;
import com.bbs.testmvc.vo.Bbs;
import com.bbs.testmvc.vo.Comment;
import com.bbs.testmvc.vo.Pagination;
import com.bbs.testmvc.vo.Search;
import com.bbs.testmvc.vo.User;
import com.bbs.testmvc.dao.FileDAO;
import com.bbs.testmvc.dao.UserDAO;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("*.do")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String uploadDir = this.getClass().getResource("").getPath();
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		String view = null;
		int bbsNo = 0;
		int page = 1;
		String bbsTitle = request.getParameter("bbsTitle");
		String bbsContent = request.getParameter("bbsContent");
		int count = 0;
		int commentNo = 0;
		Bbs bbs = null;
		com.bbs.testmvc.vo.File file = null;
		String pw = null;
		String id = null;
		User user = null;
		Search search = null;
		int bbsGroup = 0;
		int bbsOrder = 0;
		int bbsDepth = 0;
		int commentGroup = 0;
		int commentOrder = 0;
		int commentDepth = 0;
		String directory = null;
		String encoding = null;
		int maxSize = 0;
		MultipartRequest multipartRequest = null;
		HttpSession session = null;
		command = checkSession(request, response, command);
		
		switch (command) {
			case "/bbs.do":
				String reqPage = request.getParameter("page");
				if (reqPage != null) {
					page = Integer.parseInt(reqPage);
				}
				BbsService bbsService = BbsService.getInstance();
				Pagination pagination = new Pagination();
				
				search = new Search();
				search.setFind(request.getParameter("find"));
				search.setSearch(request.getParameter("search"));
				
				count = bbsService.getCount(search);
				pagination.setPage(page);
				pagination.setCount(count);
				pagination.setSearch(search);
				pagination.init();
				
				ArrayList<Bbs> list = bbsService.getBbs(pagination);
				view = "bbs/bbs";
				request.setAttribute("list", list);
				request.setAttribute("pagination", pagination);
				break;
				
			case "/bbsinsert.do":
				view="bbs/bbsinsert";
				break;
			
			case "/bbsinsertprocess.do":
				session = request.getSession();
				user = (User)session.getAttribute("user");
				uploadDir = uploadDir.substring(1,uploadDir.indexOf(".metadata"))+"bbs/WebContent/img";
				
				maxSize = 1024*1024*500;
				encoding = "UTF-8";
				multipartRequest = new MultipartRequest(request, uploadDir, maxSize, encoding, new DefaultFileRenamePolicy());
				bbs = new Bbs();
				bbs.setUserID(user.getU_name());
				bbs.setBbsTitle(multipartRequest.getParameter("title"));
				bbs.setBbsContent(multipartRequest.getParameter("content"));
				String fileName = multipartRequest.getOriginalFileName("file1");
				String fileRealName = multipartRequest.getFilesystemName("file1");
				String fileName2 = multipartRequest.getOriginalFileName("file2");
				String fileRealName2 = multipartRequest.getFilesystemName("file2");
				bbsService = BbsService.getInstance();
				bbsService.insertBbs(bbs);
				BbsDAO bbsDAO = new BbsDAO();
				bbs.setBbsNo(bbsDAO.getNext());
				FileService fileService = FileService.getInstance();
				fileService.upload(fileName, fileRealName, fileName2, fileRealName2, bbs.getBbsNo() - 1);
				view = "bbs/bbsinsertprocess";
				break;
				
			case "/bbsdetail.do":
				session = request.getSession();
				user = (User)session.getAttribute("user");
				request.setAttribute("user", user);
				bbsNo = Integer.parseInt(request.getParameter("bbsNo"));
				BbsDAO dao = BbsDAO.getInstance();
				bbsService = BbsService.getInstance();
				bbs = bbsService.viewBbs(bbsNo);
				dao.countview(bbsNo);
				request.setAttribute("bbs", bbs);
				fileService = FileService.getInstance();
				file = fileService.getFile(bbsNo);
				request.setAttribute("file",file);
				CommentService commentService = CommentService.getInstance();
				ArrayList<Comment> list1 = commentService.getList(bbsNo);
				request.setAttribute("list1", list1);
				view="bbs/bbsdetail";
				break;
				
			case "/join.do":
				view = "bbs/signup";
				break;
				
			case"/joinsuccess.do":
				user = new User();
				user.setU_id(request.getParameter("id"));
				user.setU_pw(request.getParameter("pw"));
				user.setU_name(request.getParameter("name"));
				
				UserService userService = UserService.getInstance();
				userService.joinUser(user);
				
				view = "bbs/signup-result";
				break;
				
			case "/login.do":
				view = "bbs/login";
				break;
			
			case "/login-process.do":
				id = request.getParameter("id");
				pw = request.getParameter("pw");
				
				userService = UserService.getInstance();
				user = userService.loginUser(id,pw);
				
				if(user != null) {
					session = request.getSession();
					session.setAttribute("user", user);
					
					view = "bbs/login-result";
				} else {
					view = "bbs/login-fail";
				}
				break;
				
			case "/logout.do" :
				session = request.getSession();
				session.invalidate();
				view = "bbs/login";
				break;
				
			case "/access-denied.do":
			view = "bbs/access-denied";
			break;
			
			case "/bbsupdate.do":
				bbsNo = Integer.parseInt(request.getParameter("bbsNo"));
				bbs = new Bbs();
				bbsService = BbsService.getInstance();
				bbs = bbsService.selectBbs(bbsNo);
				request.setAttribute("bbs", bbs);
				view="bbs/bbsedit";
				break;
				
			case "/bbsupdateprocess.do":
				session = request.getSession();
				user = (User)session.getAttribute("user");
				
				bbs = new Bbs();
				bbs.setBbsNo(Integer.parseInt(request.getParameter("bbsNo")));
				bbs.setBbsTitle(request.getParameter("title"));
				bbs.setBbsContent(request.getParameter("content"));
				bbsService = BbsService.getInstance();
				bbsService.updateBbs(bbs);
				
				view = "bbs/bbseditprocess";
				break;
				
			case "/bbsdeleteprocess.do" :
				session = request.getSession();
				user = (User)session.getAttribute("user");
				
				bbsNo = Integer.parseInt(request.getParameter("bbsNo"));
				bbsService = BbsService.getInstance();
				bbsService.deleteBbs(bbsNo);
				
				view = "bbs/bbsdeleteprocess";
				break;
				
			case "/bbsreply.do" :
				bbsNo = Integer.parseInt(request.getParameter("bbsNo"));
				bbsGroup = Integer.parseInt(request.getParameter("bbsGroup"));
				bbsOrder = Integer.parseInt(request.getParameter("bbsOrder"));
				bbsDepth = Integer.parseInt(request.getParameter("bbsDepth"));
				bbs = new Bbs();
				bbs.setBbsNo(bbsNo);
				bbs.setBbsGroup(bbsGroup);
				bbs.setBbsOrder(bbsOrder);
				bbs.setBbsDepth(bbsDepth);
				bbsService = BbsService.getInstance();
				request.setAttribute("bbs", bbs);
				view = "bbs/bbsreply";
				break;		
				
			case "/bbsreplyprocess.do" :
				session = request.getSession();
				user = (User)session.getAttribute("user");
				
				bbs = new Bbs();
				bbs.setUserID(user.getU_name());
				bbs.setBbsTitle(request.getParameter("title"));
				bbs.setBbsContent(request.getParameter("content"));
				bbs.setBbsGroup(Integer.parseInt(request.getParameter("bbsGroup")));
				bbs.setBbsOrder(Integer.parseInt(request.getParameter("bbsOrder")));
				bbs.setBbsDepth(Integer.parseInt(request.getParameter("bbsDepth")));
				bbsService = BbsService.getInstance();
				bbsService.updateOrder(bbsGroup, bbsOrder);
				bbsService.replyBbs(bbs);
				
				view = "bbs/bbsreplyprocess";
				break;
				
			case "/bbscommentprocess.do" :
				bbsNo = Integer.parseInt(request.getParameter("bbsNo"));
				session = request.getSession();
				bbs = new Bbs();
				bbsService = BbsService.getInstance();
				bbs = bbsService.selectBbs(bbsNo);
				request.setAttribute("bbs", bbs);
				user = (User)session.getAttribute("user");
				
				Comment comment = new Comment();
				comment.setBbsNo(Integer.parseInt(request.getParameter("bbsNo")));
				comment.setUserID(user.getU_name());
				comment.setCommentText(request.getParameter("commentText"));
				commentService = CommentService.getInstance();
				commentService.write(comment);
				list1 = commentService.getList(bbsNo);
				request.setAttribute("list1", list1);
				view = "bbs/bbscommentprocess";
				break;
				
			case "/bbscommentdelete.do" :
				session = request.getSession();
				bbsNo = Integer.parseInt(request.getParameter("bbsNo"));
				bbs = new Bbs();
				bbsService = BbsService.getInstance();
				bbs = bbsService.selectBbs(bbsNo);
				request.setAttribute("bbs", bbs);
				user = (User)session.getAttribute("user");
				
				comment = new Comment();
				commentNo = Integer.parseInt(request.getParameter("commentNo"));
				commentService = CommentService.getInstance();
				commentService.delete(bbsNo,commentNo);
				list1 = commentService.getList(bbsNo);
				request.setAttribute("list1", list1);
				view = "bbs/bbscommentprocess";
				break;
			
			case "/bbscommentupdate.do" :
				bbsNo = Integer.parseInt(request.getParameter("bbsNo"));
				commentNo = Integer.parseInt(request.getParameter("commentNo"));
				comment = new Comment();
				commentService = CommentService.getInstance();
				comment = commentService.selectComment(commentNo);
				request.setAttribute("comment", comment);
				view="bbs/bbscommentupdate";
				break;
			
			case "/bbscommentupdateprocess.do":
				session = request.getSession();
				bbs = new Bbs();
				bbsNo = Integer.parseInt(request.getParameter("bbsNo"));
				bbsService = BbsService.getInstance();
				bbs = bbsService.selectBbs(bbsNo);
				request.setAttribute("bbs", bbs);
				user = (User)session.getAttribute("user");
				comment = new Comment();
				comment.setBbsNo(Integer.parseInt(request.getParameter("bbsNo")));
				comment.setUserID(user.getU_name());
				comment.setCommentNo(Integer.parseInt(request.getParameter("commentNo")));
				comment.setCommentText(request.getParameter("commentText"));
				commentService = CommentService.getInstance();
				commentService.updateComment(comment);
				list1 = commentService.getList(bbsNo);
				request.setAttribute("list1", list1);
				view = "bbs/bbscommentprocess";
				break;
				
			case "/bbscommentreply.do" :
				bbsNo = Integer.parseInt(request.getParameter("bbsNo"));
				commentGroup = Integer.parseInt(request.getParameter("commentGroup"));
				commentOrder = Integer.parseInt(request.getParameter("commentOrder"));
				commentDepth = Integer.parseInt(request.getParameter("commentDepth"));
				comment = new Comment();
				comment.setBbsNo(bbsNo);
				comment.setCommentNo(commentNo);
				comment.setCommentGroup(commentGroup);
				comment.setCommentOrder(commentOrder);
				comment.setCommentDepth(commentDepth);
				commentService = CommentService.getInstance();
				request.setAttribute("comment", comment);
				view = "bbs/bbscommentreply";
				break;
				
			case "/bbscommentreplyprocess.do" :
				session = request.getSession();
				bbs = new Bbs();
				bbsNo = Integer.parseInt(request.getParameter("bbsNo"));
				bbsService = BbsService.getInstance();
				bbs = bbsService.selectBbs(bbsNo);
				request.setAttribute("bbs", bbs);
				user = (User)session.getAttribute("user");
				
				comment = new Comment();
				comment.setBbsNo(Integer.parseInt(request.getParameter("bbsNo")));
				comment.setCommentNo(Integer.parseInt(request.getParameter("commentNo")));
				comment.setUserID(user.getU_name());
				comment.setCommentText(request.getParameter("commentText"));
				comment.setCommentGroup(Integer.parseInt(request.getParameter("commentGroup")));
				comment.setCommentOrder(Integer.parseInt(request.getParameter("commentOrder")));
				comment.setCommentDepth(Integer.parseInt(request.getParameter("commentDepth")));
				commentService = CommentService.getInstance();
				CommentService.updateOrder(commentGroup, commentOrder);
				CommentService.replyComment(comment);
				list1 = commentService.getList(bbsNo);
				request.setAttribute("list1", list1);
				view = "bbs/bbscommentprocess";
				break;
				
			case "/userlist.do" :
				userService = UserService.getInstance();
				ArrayList<User> userlist = userService.getUsers();
				view = "bbs/userlist";
				request.setAttribute("userlist", userlist);
				break;
				
			case "/userclassadmin.do" :
				user = new User();
				userService = UserService.getInstance();
				user.setU_id(request.getParameter("id"));
				userService.updateClassAdmin(user);
				
				view = "bbs/userclassprocess";
				break;
				
			case "/userclassnormal.do" :
				user = new User();
				userService = UserService.getInstance();
				user.setU_id(request.getParameter("id"));
				userService.updateClassNormal(user);
				
				view = "bbs/userclassprocess";
				break;
		}
			
		RequestDispatcher rd = request.getRequestDispatcher(view+".jsp");
		rd.forward(request, response);
	}
	String checkSession(HttpServletRequest request, HttpServletResponse response, String command) {
		HttpSession session = request.getSession();
		
		String[] authList = {
				"/bbsinsert.do"
				,"/bbsinsertprocess.do"
				,"/bbsdetail.do"
				,"/bbsupdate.do"
				,"/logout.do"
				,"/bbsupdateprocess.do"
				,"/bbsdeleteprocess.do"
				,"/bbsreply.do"
				,"/bbsreplyprocess.do"
		};
		
		for (String item : authList) {
			if (item.equals(command)) {
				if(session.getAttribute("user") == null) {
					command = "/access-denied.do";
				}
			}
		}
		return command;
	}
}
