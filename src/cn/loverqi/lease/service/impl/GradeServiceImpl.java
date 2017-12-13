package cn.loverqi.lease.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.loverqi.lease.mapper.GradeMapper;
import cn.loverqi.lease.pojo.Grade;
import cn.loverqi.lease.pojo.GradeExample;
import cn.loverqi.lease.service.GradeService;

/**
 * 项目名称：lease
 * 类名称：GradeServiceImpl 
 * 创建人：loverqi
 * 创建时间：2017-8-31
 * 类描述： 
 */
@Service
public class GradeServiceImpl implements GradeService {

	@Autowired
	private GradeMapper gradeMapper;

	/* 
	 * createOrUpdateGrade()方法的实现
	 */
	@Override
	public Integer createOrUpdateGrade(Grade grade) {
		int update = gradeMapper.updateByPrimaryKeySelective(grade);
		if (update <= 0) {
			update = gradeMapper.insertSelective(grade);
		}
		return update > 0 ? grade.getGradeId() : -1;
	}

	/* 
	 * delete()方法的实现
	 */
	@Override
	public boolean delete(Integer gradeId) {
		return gradeMapper.deleteByPrimaryKey(gradeId) > 0;
	}

	/* 
	 * findList()方法的实现
	 */
	@Override
	public List<Grade> findList() {
		GradeExample example = new GradeExample();
		example.setOrderByClause("consumption asc");
		return gradeMapper.selectByExample(example);
	}

	/* 
	 * findListDesc()方法的实现
	 */
	@Override
	public List<Grade> findListDesc() {
		GradeExample example = new GradeExample();
		example.setOrderByClause("consumption desc");
		return gradeMapper.selectByExample(example);
	}

	/* 
	 * findTheOne()方法的实现
	 */
	@Override
	public List<Grade> findTheOne(Integer gradeId) {
		GradeExample example = new GradeExample();
		example.createCriteria().andGradeIdEqualTo(gradeId);
		return gradeMapper.selectByExample(example);
	}

	/* 
	 * getLevelByid()方法的实现
	 */
	@Override
	public String getLevelByid(Integer leve) {
		Grade grade = gradeMapper.selectByPrimaryKey(leve);
		return grade != null ? grade.getGradeName() : null;
	}

	/* 
	 * getLevelObjByid()方法的实现
	 */
	@Override
	public Grade getLevelObjByid(Integer leve) {
		return gradeMapper.selectByPrimaryKey(leve);
	}
}
