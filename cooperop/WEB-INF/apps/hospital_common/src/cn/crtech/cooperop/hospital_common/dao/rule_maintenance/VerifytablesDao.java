package cn.crtech.cooperop.hospital_common.dao.rule_maintenance;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;

public class VerifytablesDao extends BaseDao{
	public Result queryAllName(Map<String, Object> params) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT name,product from verify_tables(nolock) ");
		sql.append("where 1=1                              ");
		sql.append(" and is_active = 1                     ");
		setParameter(params,"filter","and name like '%'+:filter+'%'",sql);
		return executeQuery(sql.toString(), params);
	}
}
