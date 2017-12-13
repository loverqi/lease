package cn.loverqi.lease.service;

import java.util.List;

import cn.loverqi.lease.pojo.Grade;

/**
 * 项目名称：lease
 * 类名称：GradeService 
 * 创建人：loverqi
 * 创建时间：2017-8-31
 * 类描述： 
 */
public interface GradeService {
	/**
	 * 修改用户等级或者创建用户等级
	 */
	public Integer createOrUpdateGrade(Grade grade);

	/**
	 * 删除用户等级
	 */
	public boolean delete(Integer gradeId);

	/**
	 * 查询用户等级
	 */
	public List<Grade> findList();

	/**
	 * 查询用户等级
	 */
	public List<Grade> findListDesc();

	/**
	 * 用户等级的精确查询方法
	 */
	public List<Grade> findTheOne(Integer gradeId);

	/**
	 * 根据级别ID获取级别名称的方法
	 */
	public String getLevelByid(Integer leve);

	/**
	 * 根据级别ID获取级别名称的方法
	 */
	public Grade getLevelObjByid(Integer leve);

}
