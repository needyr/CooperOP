package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class DictHisDeptDao extends BaseDao {
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select a.* from dict_his_deptment (nolock) a where 1=1");
		setParameter(params, "filter", " and (dept_code = :filter or dept_name like '%'+'"+params.get("filter")+"'+'%')", sql);
		if("0".equals(params.get("is_wh_filter"))) {
			sql.append(" and (is_wh = 0 or isnull(is_wh,'')='' )");
		}else if("1".equals(params.get("is_wh_filter"))) {
			sql.append(" and is_wh = 1");
		}
		return executeQueryLimit(sql.toString(), params);
	}

	public void updateByCode(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("dept_code", params.remove("dept_code"));
		executeUpdate("dict_his_deptment", params, r);
	}
}
