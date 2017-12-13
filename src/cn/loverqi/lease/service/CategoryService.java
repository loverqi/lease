package cn.loverqi.lease.service;

import java.util.List;

import cn.loverqi.lease.pojo.Category;

/**
 * 项目名称：lease
 * 类名称：CategoryService 
 * 创建人：loverqi
 * 创建时间：2017-8-31
 * 类描述： 
 */
public interface CategoryService {

	/**
	 * 产品类别新建/维护
	 */
	public Integer createOrUpdateCategory(Category category);

	/**
	 * 删除产品类别
	 */
	public boolean delete(Integer categoryId);

	/**
	 * 查询商品的所有价格
	 */
	public List<Category> findList(Integer page, Integer pageSize, String categoryName);
	
	public int findListCount(String categoryName);

	/**
	 * 价格的精确查询方法
	 */
	public Category findTheOne(Integer categoryId);

	/**
	 * 根据name模糊查询对象的id列表的方法
	 */
	public List<Integer> findIdListByName(String categoryName);
}
