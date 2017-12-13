package cn.loverqi.lease.mapper;

import cn.loverqi.lease.pojo.Category;
import cn.loverqi.lease.pojo.CategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

/**
 * 项目名称：generatorSqlmapCustom
 * 类名称：CategoryMapper 
 * 创建人：loverqi
 * 创建时间：2017-9-6
 * 类描述： 
 */
public interface CategoryMapper {
    int countByExample(CategoryExample example);

    int deleteByExample(CategoryExample example);

    int deleteByPrimaryKey(Integer categoryId);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExampleWithRowbounds(CategoryExample example, RowBounds rowBounds);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Integer categoryId);

    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByExample(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}