package cn.crtech.cooperop.hospital_common.dao.rule_maintenance;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class VerifymasterDao extends BaseDao{
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select                              ");
		sql.append("id,                                 ");
		sql.append("description,                        ");
		sql.append("bh                                  ");
		sql.append("from verify_master(nolock)          ");
		sql.append("where 1=1                           ");
		if (!CommonFun.isNe(params.get("sousuo"))) {
			params.put("sousuo","%"+params.get("sousuo")+"%");
			sql.append(" and (id like :sousuo or description like :sousuo or bh like :sousuo)");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
}
