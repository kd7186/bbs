package com.bbs.testmvc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.bbs.testmvc.database.DBConnection;
import com.bbs.testmvc.vo.File;

public class FileDAO {
	private static FileDAO dao = null;
	
	public FileDAO() {
	}
	
	public static FileDAO getInstance() {
		if (dao == null) {
			dao = new FileDAO();
		}
		return dao;
	}
	
	public int upload(String fileName, String fileRealName, String fileName2, String fileRealName2, int bbsNo) {
		Connection conn = null;
		String SQL = "INSERT INTO bbs_file VALUES (?,?,?,?,?)";
		try {
			conn = DBConnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, fileName);
			pstmt.setString(2, fileRealName);
			pstmt.setString(3, fileName2);
			pstmt.setString(4, fileRealName2);
			pstmt.setInt(5,  bbsNo);
			pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public File getFile(int bbsNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM bbs_file WHERE bbsNo = ?";
		File file = null;
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				file = new File();
				file.setBbsNo(rs.getInt("bbsNo"));
				file.setFileName(rs.getString("fileName"));
				file.setFileRealName(rs.getString("fileRealName"));
				file.setFileName2(rs.getString("fileName2"));
				file.setFileRealName2(rs.getString("fileRealName2"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return file;
	}

}
