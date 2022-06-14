package com.bbs.testmvc.service;

import java.util.ArrayList;
import com.bbs.testmvc.dao.UserDAO;
import com.bbs.testmvc.vo.Bbs;
import com.bbs.testmvc.vo.User;

public class UserService {
	
	private static UserService service = null;
	private static UserDAO dao = null;
	
	private UserService() {
		
	}
	
	public static UserService getInstance() {
		if(service == null) {
			service = new UserService();
			dao = UserDAO.getInstance();
		}
		return service;
	}
	
	public ArrayList<User> getUsers() {
		return dao.getUsers();
	}
	
	public void joinUser(User user) {
		dao.joinUser(user);
	}
	
	public User loginUser(String id, String pw) {
		return dao.loginUser(id,pw);
	}
	
	public void updateClassAdmin(User user) {
		dao.updateClassAdmin(user);
	}
	
	public void updateClassNormal(User user) {
		dao.updateClassNormal(user);
	}
}
