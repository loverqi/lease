package cn.loverqi.lease.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.loverqi.lease.mapper.CategoryMapper;
import cn.loverqi.lease.pojo.Category;
import cn.loverqi.lease.pojo.CategoryExample;
import cn.loverqi.lease.service.CategoryService;

/**
 * 项目名称：lease
 * 类名称：CategoryServiceImpl 
 * 创建人：loverqi
 * 创建时间：2017-8-31
 * 类描述： 
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;

	/* 
	 * createOrUpdateCategory()方法的实现
	 */
	@Override
	public Integer createOrUpdateCategory(Category category) {
		int update = categoryMapper.updateByPrimaryKeySelective(category);
		if (update <= 0) {
			update = categoryMapper.insertSelective(category);
		}
		return update > 0 ? category.getCategoryId() : null;
	}

	/* 
	 * delete()方法的实现
	 */
	@Override
	public boolean delete(Integer categoryId) {
		return categoryMapper.deleteByPrimaryKey(categoryId) > 0;
	}

	/* 
	 * findList()方法的实现
	 */
	@Override
	public List<Category> findList(Integer page, Integer pageSize, String categoryName) {
		List<Category> list = null;
		CategoryExample example = new CategoryExample();
		if (categoryName != null && !"".equals(categoryName)) {
			example.createCriteria().andCategoryNameLike("%" + categoryName + "%");
		}
		if (page != null && pageSize != null) {
			page = page < 0 ? 0 : page - 1;
			pageSize = pageSize < 0 ? 0 : pageSize;
			RowBounds rowBounds = new RowBounds(page * pageSize, pageSize == 0 ? Integer.MAX_VALUE : pageSize);
			list = categoryMapper.selectByExampleWithRowbounds(example, rowBounds);
		} else {
			list = categoryMapper.selectByExample(example);
		}
		return list;
	}

	/* 
	 * findTheOne()方法的实现
	 */
	@Override
	public Category findTheOne(Integer categoryId) {
		return categoryMapper.selectByPrimaryKey(categoryId);
	}

	/* 
	 * findIdListByName()方法的实现
	 */
	@Override
	public List<Integer> findIdListByName(String categoryName) {
		List<Category> categorys = null;
		List<Integer> list = new ArrayList<Integer>();
		if (categoryName != null && !"".equals(categoryName.trim())) {
			CategoryExample example = new CategoryExample();
			example.createCriteria().andCategoryNameLike("%" + categoryName.trim() + "%");
			categorys = categoryMapper.selectByExample(example);
		}
		if (categorys != null && categorys.size() > 0) {
			for (Category category : categorys) {
				list.add(category.getCategoryId());
			}
		}

		return list;
	}

	/* 
	 * findListCount()方法的实现
	 */
	@Override
	public int findListCount(String categoryName) {
		CategoryExample example = new CategoryExample();
		if (categoryName != null && !"".equals(categoryName)) {
			example.createCriteria().andCategoryNameLike("%" + categoryName + "%");
		}
		
		return categoryMapper.countByExample(example);
	}

}
