package cn.loverqi.lease.mapper;

import cn.loverqi.lease.pojo.Dictionary;
import cn.loverqi.lease.pojo.DictionaryExample;
import cn.loverqi.lease.pojo.DictionaryKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

/**
 * 项目名称：lease
 * 类名称：DictionaryMapper 
 * 创建人：loverqi
 * 创建时间：2017-8-31
 * 类描述： 
 */
public interface DictionaryMapper {
    int countByExample(DictionaryExample example);

    int deleteByExample(DictionaryExample example);

    int deleteByPrimaryKey(DictionaryKey key);

    int insert(Dictionary record);

    int insertSelective(Dictionary record);

    List<Dictionary> selectByExampleWithRowbounds(DictionaryExample example, RowBounds rowBounds);

    List<Dictionary> selectByExample(DictionaryExample example);

    Dictionary selectByPrimaryKey(DictionaryKey key);

    int updateByExampleSelective(@Param("record") Dictionary record, @Param("example") DictionaryExample example);

    int updateByExample(@Param("record") Dictionary record, @Param("example") DictionaryExample example);

    int updateByPrimaryKeySelective(Dictionary record);

    int updateByPrimaryKey(Dictionary record);
}