package cn.loverqi.lease.mapper;

import cn.loverqi.lease.pojo.Flowing;
import cn.loverqi.lease.pojo.FlowingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

/**
 * 项目名称：generatorSqlmapCustom
 * 类名称：FlowingMapper 
 * 创建人：loverqi
 * 创建时间：2017-9-1
 * 类描述： 
 */
public interface FlowingMapper {
    int countByExample(FlowingExample example);

    int deleteByExample(FlowingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Flowing record);

    int insertSelective(Flowing record);

    List<Flowing> selectByExampleWithRowbounds(FlowingExample example, RowBounds rowBounds);

    List<Flowing> selectByExample(FlowingExample example);

    Flowing selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Flowing record, @Param("example") FlowingExample example);

    int updateByExample(@Param("record") Flowing record, @Param("example") FlowingExample example);

    int updateByPrimaryKeySelective(Flowing record);

    int updateByPrimaryKey(Flowing record);
}