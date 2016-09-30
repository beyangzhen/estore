package com.wxhledu.cn.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wxhledu.cn.dao.CategoryDao;
import com.wxhledu.cn.dao.impl.CategoryDaoImpl;
import com.wxhledu.cn.domain.Category;
import com.wxhledu.cn.service.CategorySerivce;

public class CategoryServiceImpl implements CategorySerivce {

	private CategoryDao dao = new CategoryDaoImpl();

	
	/* 
	 * 查询所有父目录
	 */
	@Override
	public List<Category> findParentCategory() throws SQLException {
		List<Category> list = dao.findAll();

		if (null == list) {
			return null;
		}
		
		/** 
		 *  树形结构：1.递归[n*n] 2.map[n] 
		 *  
		 *  --> 这里使用：map[n]方式
		 */
		// 获取所有目录
		Map<Integer, Category> map = new HashMap<>();
		for (Category category : list) {
			map.put(category.getId(), category);
		}

		List<Category> result = new ArrayList<>();
		for (Map.Entry<Integer, Category> m : map.entrySet()) {
			// 获取所有category
			Category category = m.getValue();
			
			// 查出对应的pId
			int pid = category.getPid(); 
			if (0 == pid) { // 父目录时，存入result列表
				result.add(category);
			} else {        // 子目录时，存入children列表
				Category parentCategory = map.get(pid); 
				List<Category> children = parentCategory.getChildren();
				if (null == children) {
					children = new ArrayList<>();
				}
				children.add(category);
				
				// TODO 进行排序
				// 组装父子关系
				parentCategory.setChildren(children);
			}
		}
		
		return result;
	}

}
