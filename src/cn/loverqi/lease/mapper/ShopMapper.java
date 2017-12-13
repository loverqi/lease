package cn.loverqi.lease.mapper;

import cn.loverqi.lease.pojo.Shop;
import cn.loverqi.lease.pojo.ShopExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

/**
 * 项目名称：lease
 * 类名称：ShopMapper 
 * 创建人：loverqi
 * 创建时间：2017-8-31
 * 类描述： 
 */
public interface ShopMapper {
    int countByExample(ShopExample example);

    int deleteByExample(ShopExample example);

    int deleteByPrimaryKey(Integer shopId);

    int insert(Shop record);

    int insertSelective(Shop record);

    List<Shop> selectByExampleWithRowbounds(ShopExample example, RowBounds rowBounds);

    List<Shop> selectByExample(ShopExample example);

    Shop selectByPrimaryKey(Integer shopId);

    int updateByExampleSelective(@Param("record") Shop record, @Param("example") ShopExample example);

    int updateByExample(@Param("record") Shop record, @Param("example") ShopExample example);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);
}