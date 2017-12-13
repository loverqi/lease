package cn.loverqi.lease.cache.impl;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import cn.loverqi.lease.cache.RedisCatch;
import cn.loverqi.lease.cache.Token;

/**
 * 项目名称：lease
 * 类名称：RedisDaoImpl 
 * 创建人：loverqi
 * 创建时间：2017-8-27
 * 类描述： Redis缓存操作的实现类
 */
@Repository
public class RedisCatchImpl implements RedisCatch {

	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;

	/* 
	 * setToken()方法的实现
	 */
	@Override
	public String setToken(Token token) {
		String tokenId = token.getTokenId();
		long inactiveInterval = token.getMaxInactiveInterval();

		// 如果失效了将立即删除对象
		if (inactiveInterval == 0) {
			delete(tokenId);
			return null;
		}

		// 将token存入redis数据库
		redisTemplate.opsForValue().set(tokenId, token);

		// 判断当前是否需要设置失效时间

		if (inactiveInterval == Token.ACQUIESCENCE) {
			// 设置为默认时间
			redisTemplate.expire(tokenId, Token.DEFAULTINACTIVEINTERVAL, TimeUnit.SECONDS);
		} else if (inactiveInterval > 0) {
			// 设置为自定义时间
			redisTemplate.expire(tokenId, token.getMaxInactiveInterval(), TimeUnit.SECONDS);
		} else {
			// 设置为不失效
			redisTemplate.persist(tokenId);
		}

		return tokenId;
	}

	/* 
	 * getToken()方法的实现
	 */
	@Override
	public Token getToken(String tokenId) {
		Serializable token = redisTemplate.opsForValue().get(tokenId);
		return token == null ? new Token() : (Token) token;
	}

	/* 
	 * getToken()方法的实现
	 */
	@Override
	public Token getToken() {
		return new Token();
	}

	/* 
	 * existsToken()方法的实现
	 */
	@Override
	public boolean existsToken(final String tokenId) {
		return redisTemplate.opsForValue().get(tokenId) != null;
	}

	/* 
	 * delete()方法的实现
	 */
	@Override
	public void delete(String tokenId) {
		redisTemplate.delete(tokenId);
	}

}
