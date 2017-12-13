package cn.loverqi.lease.mapper;

import cn.loverqi.lease.pojo.Grade;
import cn.loverqi.lease.pojo.GradeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

/**
 * 项目名称：lease
 * 类名称：GradeMapper 
 * 创建人：loverqi
 * 创建时间：2017-8-31
 * 类描述： 
 */
public interface GradeMapper {
    int countByExample(GradeExample example);

    int deleteByExample(GradeExample example);

    int deleteByPrimaryKey(Integer gradeId);

    int insert(Grade record);

    int insertSelective(Grade record);

    List<Grade> selectByExampleWithRowbounds(GradeExample example, RowBounds rowBounds);

    List<Grade> selectByExample(GradeExample example);

    Grade selectByPrimaryKey(Integer gradeId);

    int updateByExampleSelective(@Param("record") Grade record, @Param("example") GradeExample example);

    int updateByExample(@Param("record") Grade record, @Param("example") GradeExample example);

    int updateByPrimaryKeySelective(Grade record);

    int updateByPrimaryKey(Grade record);
}