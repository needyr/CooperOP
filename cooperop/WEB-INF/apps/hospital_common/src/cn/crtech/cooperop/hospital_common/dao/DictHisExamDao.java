package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DictHisExamDao extends BaseDao {
	public Result searchCheck(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * ");
		sql.append("FROM dict_his_exam_item(NOLOCK) ");
		sql.append(" where item_code in(:code)");
		params.put("sort", "item_code");
		return executeQuery(sql.toString(), params);
	}
	
	public Result search(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * ");
		sql.append("FROM dict_his_exam_item(NOLOCK) where 1=1 ");
		if (!CommonFun.isNe(params.get("data"))) {
			params.put("filter2", "%"+params.get("data")+"%");
			sql.append(" and (item_code = :data or item_name like :filter2 or input_code like :filter2) ");
		}
		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("filter", "%"+params.get("filter")+"%");
			sql.append(" and (item_code = :filter or item_name like :filter or input_code like :filter)");
		}
		params.put("sort", "item_code");
		return executeQuery(sql.toString(), params);
	}
}
