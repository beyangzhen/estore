package com.wxhledu.cn.service;

import java.sql.SQLException;

import com.wxhledu.cn.domain.Book;
import com.wxhledu.cn.domain.PageBean;

public interface BookService {

	/**
	 * 按分类找到分页图书信息
	 * @param categoryid  分页编号
	 * @param page 
	 * @return
	 * @throws SQLException 
	 */
	PageBean<Book> getBooks(int categoryid, int page) throws SQLException;

}
