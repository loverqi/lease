package cn.loverqi.lease.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.loverqi.lease.pojo.Grade;
import cn.loverqi.lease.pojo.util.RequestDate;
import cn.loverqi.lease.service.GradeService;
import cn.loverqi.lease.util.RequestParamsToMapUtil;

/**
 * 项目名称：lease 类名称：PriceCotroller 创建人：loverqi 创建时间：2017-8-27 类描述： 用户等级的维护
 */
@RestController
@RequestMapping("grade")
public class GradeCotroller {

	@Autowired
	private GradeService gradeService;

	/**
	 * 查询用户等级优惠信息列表
	 */
	@RequestMapping(value = "/find.action", method = { RequestMethod.POST,
			RequestMethod.GET })
	public RequestDate find(Integer id) {
		RequestDate requestDate = new RequestDate();
		List<Grade> list = null;
		if (id == null) {
			list = gradeService.findList();
		} else {
			list = gradeService.findTheOne(id);
		}
		requestDate.setCodeAndMessage(list != null);
		requestDate.setData(list);

		return requestDate;
	}

	/**
	 * 用户等级优惠增加/修改
	 */
	@RequestMapping(value = "/update.action", method = { RequestMethod.POST,
			RequestMethod.GET })
	public RequestDate update(@RequestParam Map<String, Object> mapsToMap) {
		Grade grade = RequestParamsToMapUtil.getMapObj(mapsToMap, Grade.class);
		RequestDate requestDate = new RequestDate();
		Object gradeId = mapsToMap.get("gradeId");
		if (gradeId != null && Integer.parseInt(gradeId.toString()) == 0) {
			requestDate.setCode(1003);
			requestDate.setMessage("该条目不支持编辑");
		}else {
			Integer createOrUpdateGrade = gradeService.createOrUpdateGrade(grade);
			if (createOrUpdateGrade != null) {
				requestDate.setCodeAndMessage(true);
				requestDate.setOnecData("gradeId", createOrUpdateGrade);
			} else {
				requestDate.setCodeAndMessage(false);
			}
		}


		return requestDate;
	}

	/**
	 * 用户等级优惠删除
	 */
	@RequestMapping(value = "/delete.action", method = { RequestMethod.POST,
			RequestMethod.GET })
	public RequestDate delete(Integer gradeId) {
		RequestDate requestDate = new RequestDate();

		if (gradeId != null && gradeId.equals(0)) {
			requestDate.setCode(1003);
			requestDate.setMessage("该条目禁止删除");
		}else if (gradeId != null && gradeId.equals(1)) {
			requestDate.setCode(1003);
			requestDate.setMessage("请至少保留一项用户级别");
		} else {
			boolean delete = gradeService.delete(gradeId);
			requestDate.setCodeAndMessage(delete);
		}

		return requestDate;
	}

}
