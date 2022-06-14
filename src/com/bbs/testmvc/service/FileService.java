package com.bbs.testmvc.service;

import java.util.ArrayList;

import com.bbs.testmvc.dao.FileDAO;
import com.bbs.testmvc.vo.Bbs;
import com.bbs.testmvc.vo.File;

public class FileService {
	

	private static FileService service = null;
	private static FileDAO dao = null;
	
	private FileService() {
		
	}
	
	public static FileService getInstance() {
		if(service == null) {
			service = new FileService();
			dao = FileDAO.getInstance();
		}
		return service;
	}
	
	public File getFile(int bbsNo) {
		return dao.getFile(bbsNo);
	}
	
	public int upload(String fileName, String fileRealName, String fileName2, String fileRealName2, int bbsNo) {
		return dao.upload(fileName, fileRealName, fileName2, fileRealName2, bbsNo);
	}
}
