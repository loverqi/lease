package cn.loverqi.lease.mapper;

import cn.loverqi.lease.pojo.Leasemain;
import cn.loverqi.lease.pojo.LeasemainExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

/**
 * 项目名称：lease
 * 类名称：LeasemainMapper 
 * 创建人：loverqi
 * 创建时间：2017-9-1
 * 类描述： 
 */
public interface LeasemainMapper {
    int countByExample(LeasemainExample example);

    int deleteByExample(LeasemainExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Leasemain record);

    int insertSelective(Leasemain record);

    List<Leasemain> selectByExampleWithRowbounds(LeasemainExample example, RowBounds rowBounds);

    List<Leasemain> selectByExample(LeasemainExample example);

    Leasemain selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Leasemain record, @Param("example") LeasemainExample example);

    int updateByExample(@Param("record") Leasemain record, @Param("example") LeasemainExample example);

    int updateByPrimaryKeySelective(Leasemain record);

    int updateByPrimaryKey(Leasemain record);
}