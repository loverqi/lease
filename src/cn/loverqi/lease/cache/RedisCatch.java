package cn.loverqi.lease.cache;

/**
 * 项目名称：lease
 * 类名称：RedisDao 
 * 创建人：loverqi
 * 创建时间：2017-8-27
 * 类描述： Redis缓存操作的接口
 */
public interface RedisCatch {

	/**
	 * 设置参数并返回tokenId的方法
	 * */
	public String setToken(Token token);

	/**
	 * 根据tokenid获取token的方法
	 * */
	public Token getToken(String tokenId);

	public Token getToken();

	/**
	 * 删除某个token的方法
	 * */
	public void delete(String tokenId);

	/**
	 * 判断token是否存在
	 * */
	public boolean existsToken(String tokenId);
}
