package cn.loverqi.lease.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.loverqi.lease.pojo.Flowing;

/**
 * 项目名称：lease
 * 类名称：FlowingCotrollerUtil 
 * 创建人：loverqi
 * 创建时间：2017-9-1
 * 类描述： 
 */
public class FlowingCotrollerUtil {
	
	private FlowingCotrollerUtil(){
		throw new AssertionError();
	}
	
	/**
	 * 合计信息的封装方法
	 */
	public static Map<String, Object> getConutToMap(List<Flowing> list) {
		Map<String, Object> map = new HashMap<String, Object>();

		int count = 0;
		double income = 0;
		double consumption = 0;
		double arrears = 0;

		if (list != null && list.size() > 0) {
			lableFor: // for循环结束的标签
			for (Flowing flowing : list) {
				switch (flowing.getState()) {
				case 0:
					income += flowing.getCost();
					break;
				case 1:
					consumption += flowing.getCost();
					break;
				case 2:
					arrears += flowing.getCost();
					break;
				default:
					count = 0;
					income = 0;
					consumption = 0;
					arrears = 0;
					break lableFor;
				}
				count = list.size();
			}
		}

		map.put("count", count);
		map.put("income", income);
		map.put("consumption", consumption);
		map.put("arrears", arrears);

		return map;
	}

}
