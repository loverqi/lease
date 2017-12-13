package cn.loverqi.lease.mapper;

import cn.loverqi.lease.pojo.Woker;
import cn.loverqi.lease.pojo.WokerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

/**
 * 项目名称：lease
 * 类名称：WokerMapper 
 * 创建人：loverqi
 * 创建时间：2017-8-30
 * 类描述： 
 */
public interface WokerMapper {
	int countByExample(WokerExample example);

	int deleteByExample(WokerExample example);

	int deleteByPrimaryKey(String username);

	int insert(Woker record);

	int insertSelective(Woker record);

	List<Woker> selectByExampleWithRowbounds(WokerExample example,
			RowBounds rowBounds);

	List<Woker> selectByExample(WokerExample example);

	Woker selectByPrimaryKey(String username);

	int updateByExampleSelective(@Param("record") Woker record,
			@Param("example") WokerExample example);

	int updateByExample(@Param("record") Woker record,
			@Param("example") WokerExample example);

	int updateByPrimaryKeySelective(Woker record);

	int updateByPrimaryKey(Woker record);
}