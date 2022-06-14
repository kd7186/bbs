package com.bbs.testmvc.dao;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.bbs.testmvc.database.DBConnection;
import com.bbs.testmvc.vo.Bbs;
import com.bbs.testmvc.vo.Pagination;
import com.bbs.testmvc.vo.Search;

public class BbsDAO {
	private static BbsDAO dao = null;
	
	public BbsDAO() {
		
	}
	
	public static BbsDAO getInstance() {
		if (dao == null) {
			dao = new BbsDAO();
		}
		return dao;
	}
	
	public ArrayList<Bbs> getBbs(Pagination pagination) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Bbs> list = null;
		Search search = pagination.getSearch();
		int pageNum = pagination.getPageNum();
		try {
			conn = DBConnection.getConnection();
			if (search.getFind() != null && search.getSearch() != null) {
			String query = new StringBuilder()
					.append("SELECT 		@ROWNUM := @ROWNUM - 1 AS ROWNUM,\n")
					.append("				ta.*\n")
					.append("FROM 			bbs ta\n")
					.append("INNER JOIN			(SELECT @rownum := (SELECT	COUNT(*)-?+1 FROM bbs ta)) tb\n")
					.append("WHERE "+search.getFind()+" like ?\n")
					.append("ORDER BY       bbsNo desc\n")
					.append("LIMIT			?, ?\n")
					.toString();
	       	pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, pageNum);
			pstmt.setString(2, "%"+search.getSearch()+"%");
			pstmt.setInt(3, pageNum);
			pstmt.setInt(4, pagination.getPerPage());
	        rs = pstmt.executeQuery();
	        list = new ArrayList<Bbs>();
			}
			else {
				String query = new StringBuilder()
						.append("SELECT 		@ROWNUM := @ROWNUM - 1 AS ROWNUM,\n")
						.append("				ta.*\n")
						.append("FROM 			bbs ta\n")
						.append("INNER JOIN			(SELECT @rownum := (SELECT	COUNT(*)-?+1 FROM bbs ta)) tb\n")
						.append("ORDER BY       bbsGroup desc, bbsOrder asc, bbsDepth asc, bbsNo desc\n")
						.append("LIMIT			?, ?\n")
						.toString();
		       	pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, pageNum);
				pstmt.setInt(2, pageNum);
				pstmt.setInt(3, pagination.getPerPage());
		        rs = pstmt.executeQuery();
		        
		        list = new ArrayList<Bbs>();
			}
	        while(rs.next()){     
	        	Bbs bbs = new Bbs();
	        	bbs.setRownum(rs.getInt("ROWNUM"));
       	       	bbs.setBbsNo(rs.getInt("bbsNO"));
       	       	bbs.setBbsTitle(rs.getString("bbsTitle"));
       	       	bbs.setUserID(rs.getString("userID"));
       	       	bbs.setBbsDate(rs.getString("bbsDate"));
       	       	bbs.setBbsContent(rs.getString("bbsContent"));
       	       	bbs.setBbsView(rs.getInt("bbsView"));
       	       	bbs.setBbsDepth(rs.getInt("bbsDepth"));
       	       	list.add(bbs);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
		public int getNext() {
			String sql = "select bbsNo from bbs order by bbsNo desc" ;
			try {
					Connection conn = DBConnection.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();
					if(rs.next()) {
						return rs.getInt(1) + 1;
					}
					return 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
				return -1;
		}
	
		public void insertBbs(Bbs bbs) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			int a = getNext();
			try {
				conn = DBConnection.getConnection();
				String sql = "insert into bbs(bbsNo, bbsTitle,bbsContent,userID,bbsView,bbsGroup,bbsOrder,bbsDepth) values(?,?,?,?,?,?,?,?)";
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1,a);
						pstmt.setString(2, bbs.getBbsTitle());
						pstmt.setString(3, bbs.getBbsContent());
						pstmt.setString(4, bbs.getUserID());
						pstmt.setInt(5,1);
						pstmt.setInt(6,a);
						pstmt.setInt(7,1);
						pstmt.setInt(8,0);
						pstmt.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if(pstmt != null) pstmt.close();
						if(conn != null) conn.close();
					} catch(SQLException e) {
						e.printStackTrace();
					}
				}
			}
		
		public void replyBbs(Bbs bbs) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			
			try {
				conn = DBConnection.getConnection();
				String sql = "insert into bbs(bbsNo, bbsTitle,bbsContent,userID,bbsView,bbsGroup,bbsDepth,bbsOrder) values(?,?,?,?,?,?,?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,  getNext());
				pstmt.setString(2, bbs.getBbsTitle());
				pstmt.setString(3, bbs.getBbsContent());
				pstmt.setString(4, bbs.getUserID());
				pstmt.setInt(5,1);
				pstmt.setInt(6, bbs.getBbsGroup());
				pstmt.setInt(7, bbs.getBbsDepth() + 1);
				pstmt.setInt(8, bbs.getBbsOrder() + 1);
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
		
		public void updateOrder(int bbsGroup, int bbsOrder) {
			String sql = "update bbs set bbs.bbsOrder = bbs.bbsOrder + 1 where bbs.bbsGroup = ? and bbs.bbsOrder > ?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = DBConnection.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bbsGroup);
				pstmt.setInt(2, bbsOrder);
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
		
		public Bbs viewBbs(int bbsNo) {
			String sql = "select * from bbs where bbsNo = ?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Bbs bbs = null;
			try {
				conn = DBConnection.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,  bbsNo);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					bbs =new Bbs();
					bbs.setBbsNo(rs.getInt("bbsNo"));
					bbs.setBbsTitle(rs.getString("bbsTitle"));
					bbs.setBbsDate(rs.getString("bbsDate"));
					bbs.setUserID(rs.getString("userID"));
					bbs.setBbsContent(rs.getString("bbsContent"));
					bbs.setBbsView(rs.getInt("bbsView"));
					bbs.setBbsOrder(rs.getInt("bbsOrder"));
					bbs.setBbsDepth(rs.getInt("bbsDepth"));
					bbs.setBbsGroup(rs.getInt("bbsGroup"));
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
			return bbs;
		}
		
		public Bbs selectBbs(int bbsNo) {
			String sql = "select * from bbs where bbsNo = ?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Bbs bbs = null;
			try {
				conn = DBConnection.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,  bbsNo);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					bbs =new Bbs();
					bbs.setBbsNo(rs.getInt("bbsNo"));
					bbs.setBbsTitle(rs.getString("bbsTitle"));
					bbs.setBbsContent(rs.getString("bbsContent"));
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
			return bbs;
		}
		
		public void updateBbs(Bbs bbs) {
			Connection conn = null;
			PreparedStatement pstmt = null;	
			try {
				conn = DBConnection.getConnection();
				String sql = "update bbs set bbsTitle = ?, bbsContent = ? where bbsNo=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, bbs.getBbsTitle());
				pstmt.setString(2, bbs.getBbsContent());
				pstmt.setInt(3, bbs.getBbsNo());
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

		public Bbs countview(int bbsNo) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			Bbs bbs = null;
			try {
				String query = "update bbs set bbsView = bbsView + 1 where bbsNo = ?";
				conn = DBConnection.getConnection();
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, bbsNo);
				pstmt.executeUpdate();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(pstmt != null) pstmt.close();
					if(conn != null) conn.close();
				} catch(Exception e2) {
					e2.printStackTrace();
				}
			}
		return bbs;
		}
		
		public int deleteBbs(int bbsNo) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			int result = 0;
			try {
				String sql = "delete from bbs WHERE bbsNo = ?";
				conn = DBConnection.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bbsNo);
				result = pstmt.executeUpdate();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				try {
					pstmt.close();
					conn.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return result;
		}
		
		public int getCount(Search search) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int count = 0;
			String query = null;
			try {
				conn = DBConnection.getConnection();
				if(search.getFind() != null && search.getSearch() != null) {
					query = "SELECT COUNT(*) count FROM bbs WHERE "+search.getFind()+" like ?";
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, "%"+search.getSearch()+"%");
					rs = pstmt.executeQuery();
				} else {
				query = "SELECT COUNT(*) count FROM bbs";
		       	pstmt = conn.prepareStatement(query);
		        rs = pstmt.executeQuery();
				}
		        while(rs.next()) {
		        	count = rs.getInt("count");
		        }
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) rs.close();
					if (pstmt != null) pstmt.close();
					if (conn != null) conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return count;
		}

}


