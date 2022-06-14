package com.bbs.testmvc.service;

import java.util.ArrayList;
import com.bbs.testmvc.dao.BbsDAO;
import com.bbs.testmvc.vo.Bbs;
import com.bbs.testmvc.vo.Pagination;
import com.bbs.testmvc.vo.Search;

public class BbsService {
	private static BbsService service = null;
	private static BbsDAO dao = null;
	
	private BbsService() {
		
		
	}
	
	public static BbsService getInstance() {
		if(service == null) {
			service = new BbsService();
			dao = BbsDAO.getInstance();
		}
		return service;
	}
	public ArrayList<Bbs> getBbs(Pagination pagination) {
		return dao.getBbs(pagination);
	}
	
	public void insertBbs(Bbs bbs) {
		dao.insertBbs(bbs);
	}
	
	public Bbs viewBbs(int bbsNo) {
		return dao.viewBbs(bbsNo);
	}
	
	public Bbs countview(int bbsNo) {
		return dao.countview(bbsNo);
	}
	
	public void updateBbs(Bbs bbs) {
		dao.updateBbs(bbs);
	}

	public Bbs selectBbs(int bbsNo) {
		return dao.selectBbs(bbsNo);	
	}
	
	public int deleteBbs(int bbsNo) {
		return dao.deleteBbs(bbsNo);
	}
	
	public int getCount(Search search) {
		return dao.getCount(search);
	}
	
	public ArrayList<Bbs> searchBbs(Pagination pagination) {
		return dao.getBbs(pagination);
	}
	
	public void replyBbs(Bbs bbs) {
		dao.replyBbs(bbs);
	}
	
	public void updateOrder(int bbsGroup, int bbsOrder) {
		dao.updateOrder(bbsGroup, bbsOrder);
	}
}
