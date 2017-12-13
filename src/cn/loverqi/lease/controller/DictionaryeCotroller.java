package cn.loverqi.lease.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.loverqi.lease.pojo.Dictionary;
import cn.loverqi.lease.pojo.util.RequestDate;
import cn.loverqi.lease.service.DictionaryeService;

/**
 * 项目名称：lease
 * 类名称：DictionaryeCotroller 
 * 创建人：loverqi
 * 创建时间：2017-8-27
 * 类描述： 字典值的维护
 */
@RestController
@RequestMapping("dictionary")
public class DictionaryeCotroller {

	@Autowired
	private DictionaryeService dictionaryeService;

	/**
	 * 根据字典值获取字典信息
	 */
	@RequestMapping(value = "/find.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate find(Integer id, Integer defaultStr, boolean require) {
		RequestDate requestDate = new RequestDate();

		if (id == null) {
			requestDate.setCodeAndMessage(false);
		} else {
			if (defaultStr != null) {
				Dictionary dictionary = dictionaryeService.findDictionartById(id, defaultStr);
				requestDate.setOnecData("name", dictionary == null ? null : dictionary.getName());
				if (require) {
					requestDate.setOnecData("list", dictionaryeService.findDictionarts(id));
				}
			}else {
				requestDate.setOnecData("list", dictionaryeService.findDictionarts(id));
			}
			requestDate.setCodeAndMessage(true);
		}

		return requestDate;
	}

}
