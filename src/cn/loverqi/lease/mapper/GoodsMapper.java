package cn.loverqi.lease.mapper;

import cn.loverqi.lease.pojo.Goods;
import cn.loverqi.lease.pojo.GoodsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

/**
 * 项目名称：generatorSqlmapCustom
 * 类名称：GoodsMapper 
 * 创建人：loverqi
 * 创建时间：2017-9-6
 * 类描述： 
 */
public interface GoodsMapper {
    int countByExample(GoodsExample example);

    int deleteByExample(GoodsExample example);

    int deleteByPrimaryKey(Integer goodsId);

    int insert(Goods record);

    int insertSelective(Goods record);

    List<Goods> selectByExampleWithRowbounds(GoodsExample example, RowBounds rowBounds);

    List<Goods> selectByExample(GoodsExample example);

    Goods selectByPrimaryKey(Integer goodsId);

    int updateByExampleSelective(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByExample(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);
}