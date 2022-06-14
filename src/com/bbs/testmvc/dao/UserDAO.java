package com.bbs.testmvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.bbs.testmvc.database.DBConnection;
import com.bbs.testmvc.vo.Bbs;
import com.bbs.testmvc.vo.User;

public class UserDAO {
	
	private static UserDAO dao = null;
	
	private UserDAO() {
		
	}
	
	public static UserDAO getInstance() {
		if(dao == null) {
			dao = new UserDAO();
		}
		return dao;
	}
	
	public ArrayList<User> getUsers() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<User> list = null;try {
			conn = DBConnection.getConnection();
			String query = "select * from user";
	       	pstmt = conn.prepareStatement(query);
	        rs = pstmt.executeQuery();
	        list = new ArrayList<User>();

	        while(rs.next()){     
	        	User user = new User();
       	       	user.setU_id(rs.getString("u_id"));
       	       	user.setU_pw(rs.getString("u_pw"));
       	       	user.setU_name(rs.getString("u_name"));
       	       	user.setU_class(rs.getString("u_class"));
       	       	list.add(user);
	        }
		} catch (Exception e) {
			
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;



	}
	
	public void joinUser (User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;
			
		try {
			conn = DBConnection.getConnection();
			String sql = "insert into user(u_id,u_pw) values(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getU_id());
			pstmt.setString(2, user.getU_pw());
			pstmt.setString(3, user.getU_name());
			pstmt.executeUpdate();
		} catch(Exception ex) {
			System.out.println("SQLException : " + ex.getMessage());
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public User loginUser(String id,String pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			conn = DBConnection.getConnection();
			String sql = "SELECT * FROM user WHERE u_id = ? AND u_pw = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				user = new User();
				user.setU_id(rs.getString("u_id"));
				user.setU_pw(rs.getString("u_pw"));
				user.setU_name(rs.getString("u_name"));
				user.setU_class(rs.getString("u_class"));
			}
		} catch (Exception ex) {
			System.out.println("SQLException : " + ex.getMessage());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}
	
	public void updateClassAdmin(User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;	
		try {
			conn = DBConnection.getConnection();
			String sql = "update user set u_class='admin' where u_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getU_id());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateClassNormal(User user) {
		Connection conn = null;
		PreparedStatement pstmt = null;	
		try {
			conn = DBConnection.getConnection();
			String sql = "update user set u_class='normal' where u_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getU_id());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
