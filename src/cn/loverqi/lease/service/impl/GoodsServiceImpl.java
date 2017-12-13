package cn.loverqi.lease.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.loverqi.lease.mapper.GoodsMapper;
import cn.loverqi.lease.pojo.Goods;
import cn.loverqi.lease.pojo.GoodsExample;
import cn.loverqi.lease.pojo.GoodsExample.Criteria;
import cn.loverqi.lease.service.CategoryService;
import cn.loverqi.lease.service.GoodsService;

/**
 * 项目名称：lease
 * 类名称：GoodsServiceImpl 
 * 创建人：loverqi
 * 创建时间：2017-8-31
 * 类描述： 
 */
@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private CategoryService categoryService;

	/* 
	 * createOrUpdateGoods()方法的实现
	 */
	@Override
	public Integer createOrUpdateGoods(Goods goods) {
		String bar = goods.getBar();
		if (bar != null && bar.length() > 2) {
			goods.setBar(bar.substring(2, bar.length()));
		}
		int update = goodsMapper.updateByPrimaryKeySelective(goods);
		if (update <= 0) {
			update = goodsMapper.insertSelective(goods);
		}
		return update > 0 ? goods.getGoodsId() : null;
	}

	/* 
	 * delete()方法的实现
	 */
	@Override
	public boolean delete(Integer goodsId) {
		return goodsMapper.deleteByPrimaryKey(goodsId) > 0;
	}

	/* 
	 * findGoodsEs()方法的实现
	 */
	@Override
	public List<Goods> findList(Map<String, Object> examplesMap) {
		List<Goods> list = null;
		GoodsExample example = new GoodsExample();
		Criteria createCriteria = example.createCriteria();

		Object categoryId = examplesMap.get("categoryId");
		if (categoryId != null && !"".equals(categoryId.toString().trim())) {
			createCriteria.andCategoryIdEqualTo(Integer.parseInt((String) categoryId));
		}
		Object categoryName = examplesMap.get("categoryName");
		if (categoryName != null && !"".equals(categoryName.toString().trim())) {
			List<Integer> idLists = categoryService.findIdListByName(((String) categoryName).trim());
			createCriteria.andCategoryIdIn(idLists);
		}
		Object goodsId = examplesMap.get("goodsId");
		if (goodsId != null && !"".equals(goodsId.toString().trim())) {
			createCriteria.andGoodsIdEqualTo(Integer.parseInt((String) goodsId));
		}
		Object goodsName = examplesMap.get("goodsName");
		if (goodsName != null && !"".equals(goodsName.toString().trim())) {
			createCriteria.andGoodsNameLike("%" + ((String) goodsName).trim() + "%");
		}
		Object bar = examplesMap.get("bar");
		if (bar != null && !"".equals(bar.toString().trim())) {
			String barStr = bar.toString();
			barStr = barStr.length() > 2 ? barStr.substring(2, barStr.length()) : barStr;
			createCriteria.andBarEqualTo(barStr);
		}

		Object page = examplesMap.get("page");
		Object pageSize = examplesMap.get("pageSize");

		if (page != null && pageSize != null) {
			int pageI = Integer.parseInt((String) page) - 1;
			int pageSizeI = Integer.parseInt((String) pageSize);
			pageI = pageI < 0 ? 0 : pageI;
			pageSizeI = pageSizeI < 0 ? 0 : pageSizeI;
			RowBounds rowBounds = new RowBounds(pageI * pageSizeI, pageSizeI == 0 ? Integer.MAX_VALUE : pageSizeI);
			list = goodsMapper.selectByExampleWithRowbounds(example, rowBounds);
		} else {
			list = goodsMapper.selectByExample(example);
		}

		return list;
	}

	/* 
	 * findGoods()方法的实现
	 */
	@Override
	public Goods find(Integer goodsId) {
		return goodsMapper.selectByPrimaryKey(goodsId);
	}

	/* 
	 * findIdList()方法的实现
	 */
	@Override
	public List<Integer> findIdList(List<Integer> category) {
		List<Integer> list = new ArrayList<Integer>();
		GoodsExample example = new GoodsExample();
		example.createCriteria().andCategoryIdIn(category);
		List<Goods> goodsList = goodsMapper.selectByExample(example);
		if (goodsList != null && goodsList.size() > 0) {
			for (Goods goods : goodsList) {
				list.add(goods.getGoodsId());
			}
		}
		return list;
	}

	/* 
	 * find()方法的实现
	 */
	@Override
	public Goods find(String bar) {
		List<Goods> goodss = null;
		if (bar != null && !"".equals(bar)) {
			GoodsExample example = new GoodsExample();
			example.createCriteria().andBarEqualTo(bar);
			goodss = goodsMapper.selectByExample(example);
		}

		return goodss != null && goodss.size() > 0 ? goodss.get(0) : null;
	}

	/* 
	 * findListCount()方法的实现
	 */
	@Override
	public int findListCount(Map<String, Object> examplesMap) {
		GoodsExample example = new GoodsExample();
		Criteria createCriteria = example.createCriteria();

		Object categoryId = examplesMap.get("categoryId");
		if (categoryId != null && !"".equals(categoryId.toString().trim())) {
			createCriteria.andCategoryIdEqualTo(Integer.parseInt((String) categoryId));
		}
		Object categoryName = examplesMap.get("categoryName");
		if (categoryName != null && !"".equals(categoryName.toString().trim())) {
			List<Integer> idLists = categoryService.findIdListByName(((String) categoryName).trim());
			createCriteria.andCategoryIdIn(idLists);
		}
		Object goodsId = examplesMap.get("goodsId");
		if (goodsId != null && !"".equals(goodsId.toString().trim())) {
			createCriteria.andGoodsIdEqualTo(Integer.parseInt((String) goodsId));
		}
		Object goodsName = examplesMap.get("goodsName");
		if (goodsName != null && !"".equals(goodsName.toString().trim())) {
			createCriteria.andGoodsNameLike("%" + ((String) goodsName).trim() + "%");
		}
		Object bar = examplesMap.get("bar");
		if (bar != null && !"".equals(bar.toString().trim())) {
			String barStr = bar.toString();
			barStr = barStr.length() > 2 ? barStr.substring(2, barStr.length()) : barStr;
			createCriteria.andBarEqualTo(barStr);
		}

		return goodsMapper.countByExample(example);
	}
}
