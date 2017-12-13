package cn.loverqi.lease.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.loverqi.lease.mapper.ShopMapper;
import cn.loverqi.lease.pojo.Shop;
import cn.loverqi.lease.pojo.ShopExample;
import cn.loverqi.lease.pojo.ShopExample.Criteria;
import cn.loverqi.lease.service.ShopService;

/**
 * 项目名称：lease
 * 类名称：ShopServiceImpl 
 * 创建人：loverqi
 * 创建时间：2017-8-31
 * 类描述： 
 */
@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopMapper shopMapper;

	/* 
	 * createOrUpdateShop()方法的实现
	 */
	@Override
	public Integer createOrUpdateShop(Shop shop) {
		int update = shopMapper.updateByPrimaryKeySelective(shop);
		if (update <= 0) {
			update = shopMapper.insertSelective(shop);
		}
		return update > 0 ? shop.getShopId() : null;
	}

	/* 
	 * delete()方法的实现
	 */
	@Override
	public boolean delete(Integer shopId) {
		return shopMapper.deleteByPrimaryKey(shopId) > 0;
	}

	/* 
	 * findList()方法的实现
	 */
	@Override
	public List<Shop> findList(Integer shopId, String name) {

		ShopExample example = new ShopExample();
		Criteria createCriteria = example.createCriteria();
		
		if (shopId != null) {
			createCriteria.andShopIdEqualTo(shopId);
		}
		if (name != null && !"".equals(name.trim())) {
			createCriteria.andNameLike("%" + name.trim() + "%");
		}
		return shopMapper.selectByExample(example);
	}

	/* 
	 * findTheOne()方法的实现
	 */
	@Override
	public Shop findTheOne(Integer shopId) {
		return shopMapper.selectByPrimaryKey(shopId);
	}

}
