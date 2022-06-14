package com.bbs.testmvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bbs.testmvc.database.DBConnection;
import com.bbs.testmvc.vo.Bbs;
import com.bbs.testmvc.vo.Comment;

public class CommentDAO {
	private static CommentDAO dao = null;
	
	public CommentDAO() {
		
	}
	
	public static CommentDAO getInstance() {
		if (dao == null) {
			dao = new CommentDAO();
		}
		return dao;
	}
	
	public ArrayList<Comment> getList(int bbsNo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//String SQL = "SELECT * FROM bbs_comment WHERE bbsNo = ? AND commentAvailable = 1 ORDER BY bbsNo DESC";
		ArrayList<Comment> list1 = null;
		try {
			conn = DBConnection.getConnection();
			String SQL = "SELECT * FROM bbs_comment WHERE bbsNo = ? AND commentAvailable = 1 ORDER BY bbsNo DESC, commentGroup DESC, commentOrder ASC, commentDepth ASC ";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsNo);
			rs = pstmt.executeQuery();
			list1 = new ArrayList<Comment>();
			while (rs.next()) {
				Comment comment = new Comment();
				comment.setBbsNo(rs.getInt("bbsNo"));
				comment.setCommentNo(rs.getInt("commentNo"));
				comment.setUserID(rs.getString("userID"));
				comment.setCommentText(rs.getString("commentText"));
				comment.setCommentAvailable(rs.getInt("CommentAvailable"));
				comment.setCommentGroup(rs.getInt("commentGroup"));
				comment.setCommentOrder(rs.getInt("commentOrder"));
				comment.setCommentDepth(rs.getInt("commentDepth"));
				list1.add(comment);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} 
		return list1;
	}
	
	public int getNext() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL = "SELECT commentNo FROM bbs_comment ORDER BY commentNo DESC";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) + 1;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} 
		return 1;
	}
	
	public int write(Comment comment) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String SQL = "INSERT INTO bbs_comment VALUES(?,?,?,?,?,?,?,?)";
		int a = getNext();
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, comment.getBbsNo());
			pstmt.setInt(2, a);
			pstmt.setString(3, comment.getUserID());
			pstmt.setString(4, comment.getCommentText());
			pstmt.setInt(5, 1);
			pstmt.setInt(6, a);
			pstmt.setInt(7, 0);
			pstmt.setInt(8, 0);
			pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} return -1;
	}
	
	public Comment getComment(int commentNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM bbs_comment WHERE commentNo = ? ORDER BY commentNo DESC";
		Comment comment = null;
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,  commentNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				comment = new Comment();
				comment.setBbsNo(rs.getInt("bbsNo"));
				comment.setCommentNo(rs.getInt("commentNo"));
				comment.setUserID(rs.getString("userID"));
				comment.setCommentText(rs.getString("commentText"));
				comment.setCommentAvailable(rs.getInt("commentAvailable"));
				return comment;
			}
		} catch(Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	
	public void delete(int bbsNo, int commentNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String SQL = "DELETE FROM bbs_comment WHERE bbsNo = ? AND commentNo = ?";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,  bbsNo);
			pstmt.setInt(2,  commentNo);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Comment selectComment(int commentNo) {
		String SQL = "SELECT * FROM bbs_comment WHERE commentNo = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Comment comment = null;
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, commentNo);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				comment = new Comment();
				comment.setBbsNo(rs.getInt("bbsNo"));
				comment.setCommentNo(rs.getInt("commentNo"));
				comment.setUserID(rs.getString("userID"));
				comment.setCommentText(rs.getString("commentText"));
				comment.setCommentAvailable(rs.getInt("commentAvailable"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return comment;
	}
	
	public void updateComment(Comment comment) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.getConnection();
			String sql = "UPDATE bbs_comment set commentText = ? WHERE bbsNo=? AND commentNo=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comment.getCommentText());
			pstmt.setInt(2, comment.getBbsNo());
			pstmt.setInt(3, comment.getCommentNo());
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
	
	public void replyComment(Comment comment) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String sql = "insert into bbs_comment(bbsNo,commentNo,commentText,userID,commentAvailable,commentGroup,commentDepth,commentOrder) values(?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,  comment.getBbsNo());
			pstmt.setInt(2, getNext());
			pstmt.setString(3, comment.getCommentText());
			pstmt.setString(4, comment.getUserID());
			pstmt.setInt(5,1);
			pstmt.setInt(6, comment.getCommentGroup());
			pstmt.setInt(7, comment.getCommentDepth() + 1);
			pstmt.setInt(8, comment.getCommentOrder() + 1);
			pstmt.executeUpdate();
		} catch(Exception ex) {
			System.out.println("SQLException : " + ex.getMessage());
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateOrder(int commentGroup, int commentOrder) {
		String sql = "update bbs_comment set bbs_comment.commentOrder = bbs_comment.commentOrder + 1 where bbs_comment.commentGroup = ? and bbs_comment.commentOrder > ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, commentGroup);
			pstmt.setInt(2, commentOrder);
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