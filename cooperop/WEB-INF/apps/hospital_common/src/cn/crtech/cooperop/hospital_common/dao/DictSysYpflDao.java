package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;

public class DictSysYpflDao extends BaseDao {

	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from DICT_SYS_YPFL (nolock)");
		return executeQuery(sql.toString(), params);
	}
	
}
