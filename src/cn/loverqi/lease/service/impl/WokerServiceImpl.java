package cn.loverqi.lease.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.loverqi.lease.mapper.WokerMapper;
import cn.loverqi.lease.pojo.Woker;
import cn.loverqi.lease.pojo.WokerExample;
import cn.loverqi.lease.pojo.WokerExample.Criteria;
import cn.loverqi.lease.service.WokerService;

/**
 * 项目名称：lease
 * 类名称：WokerServiceImpl 
 * 创建人：loverqi
 * 创建时间：2017-8-29
 * 类描述： WokerService的实现类
 */
@Service
public class WokerServiceImpl implements WokerService {

	@Autowired
	private WokerMapper wokerMapper;

	/* 
	 * findWokerByPsw()方法的实现
	 */
	@Override
	public boolean findWokerByPsw(String username, String password) {
		WokerExample example = new WokerExample();
		example.createCriteria().andUsernameEqualTo(username.trim()).andPasswordEqualTo(password).andStateGreaterThanOrEqualTo(0);
		List<Woker> wokers = wokerMapper.selectByExample(example);
		return wokers.size() > 0;
	}

	/* 
	 * findWokers()方法的实现
	 */
	@Override
	public List<Woker> findWokers(Integer shopId, String username) {
		WokerExample example = new WokerExample();
		Criteria createCriteria = example.createCriteria();
		example.setOrderByClause("state asc");
		
		createCriteria.andStateGreaterThanOrEqualTo(0);
		if (shopId != null) {
			createCriteria.andShopIdEqualTo(shopId);
		}
		if (username != null && !"".equals(username.trim())) {
			createCriteria.andUsernameLike("%" + username.trim() + "%");
		}
		return wokerMapper.selectByExample(example);
	}

	/* 
	 * createOrUpdateWoker()方法的实现
	 */
	@Override
	public boolean createOrUpdateWoker(Woker woker) {
		int update = wokerMapper.updateByPrimaryKeySelective(woker);
		if (update <= 0) {
			update = wokerMapper.insertSelective(woker);
		}
		return update > 0;
	}

	/* 
	 * updateWokerPsw()方法的实现
	 */
	@Override
	public boolean updateWokerPsw(String username, String password) {
		Woker woker = new Woker();
		woker.setUsername(username);
		woker.setPassword(password);
		int update = wokerMapper.updateByPrimaryKeySelective(woker);
		return update > 0;
	}

	/* 
	 * deleteWoker()方法的实现
	 */
	@Override
	public boolean deleteWoker(String username) {
		Woker woker = new Woker();
		woker.setUsername(username);
		woker.setState(-1);
		int update = wokerMapper.updateByPrimaryKeySelective(woker);
		return update > 0;
	}

	/* 
	 * findWokerByUse()方法的实现
	 */
	@Override
	public Woker findWokerByUse(String username) {
		if (username == null || "".equals(username.trim())) {
			return null;
		}
		WokerExample example = new WokerExample();
		example.createCriteria().andUsernameEqualTo(username.trim()).andStateGreaterThanOrEqualTo(0);
		List<Woker> wokers = wokerMapper.selectByExample(example);
		return wokers.size() > 0 ? wokers.get(0) : null;
	}

}
