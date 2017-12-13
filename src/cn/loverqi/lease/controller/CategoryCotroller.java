package cn.loverqi.lease.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.loverqi.lease.pojo.Category;
import cn.loverqi.lease.pojo.util.RequestDate;
import cn.loverqi.lease.service.CategoryService;
import cn.loverqi.lease.util.RequestParamsToMapUtil;

/**
 * 项目名称：lease
 * 类名称：CategoryCotroller 
 * 创建人：loverqi
 * 创建时间：2017-8-27
 * 类描述： 与产品类别有关的操作
 */
@RestController
@RequestMapping("category")
public class CategoryCotroller {

	@Autowired
	private CategoryService categoryService;

	/**
	 * 产品类别获取
	 */
	@RequestMapping(value = "/find.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate find(Integer page, Integer pageSize, String categoryName) {
		RequestDate requestDate = new RequestDate();

		List<Category> list = categoryService.findList(page, pageSize, categoryName);
		Map<String, Object> resposeMap = new HashMap<String, Object>();
		
		resposeMap.put("body", list);
		resposeMap.put("count", categoryService.findListCount(categoryName));
		requestDate.setCodeAndMessage(list != null);
		requestDate.setData(resposeMap);

		return requestDate;
	}

	/**
	 * 产品类别新建/维护
	 */
	@RequestMapping(value = "/update.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate update(@RequestParam Map<String, Object> mapsToMap) {
		Category category = RequestParamsToMapUtil.getMapObj(mapsToMap, Category.class);
		RequestDate requestDate = new RequestDate();

		Integer createOrUpdateCategory = categoryService.createOrUpdateCategory(category);
		if (createOrUpdateCategory != null) {
			requestDate.setCodeAndMessage(true);
			requestDate.setOnecData("gradeId", createOrUpdateCategory);
		} else {
			requestDate.setCodeAndMessage(false);
		}

		return requestDate;
	}

	/**
	 * 产品类别删除
	 */
	@RequestMapping(value = "/delete.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate delete(Integer categoryId) {
		RequestDate requestDate = new RequestDate();

		boolean delete = categoryService.delete(categoryId);
		requestDate.setCodeAndMessage(delete);

		return requestDate;
	}

}
