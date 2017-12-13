package cn.loverqi.lease.mapper;

import cn.loverqi.lease.pojo.Price;
import cn.loverqi.lease.pojo.PriceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

/**
 * 项目名称：generatorSqlmapCustom
 * 类名称：PriceMapper 
 * 创建人：loverqi
 * 创建时间：2017-9-8
 * 类描述： 
 */
public interface PriceMapper {
    int countByExample(PriceExample example);

    int deleteByExample(PriceExample example);

    int deleteByPrimaryKey(Integer priceId);

    int insert(Price record);

    int insertSelective(Price record);

    List<Price> selectByExampleWithRowbounds(PriceExample example, RowBounds rowBounds);

    List<Price> selectByExample(PriceExample example);

    Price selectByPrimaryKey(Integer priceId);

    int updateByExampleSelective(@Param("record") Price record, @Param("example") PriceExample example);

    int updateByExample(@Param("record") Price record, @Param("example") PriceExample example);

    int updateByPrimaryKeySelective(Price record);

    int updateByPrimaryKey(Price record);
}