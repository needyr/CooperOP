package cn.crtech.cooperop.hospital_common.dao.verify;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;

public class VerifyitemchildDao extends BaseDao{
	
	public Result querybypid(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("");
		sql.append("SELECT                                 ");
		sql.append("id,                                    ");
		sql.append("field,                                 ");
		sql.append("table_name,                            ");
		sql.append("parent_id,                             ");
		sql.append("product                                ");
		sql.append("FROM verify_item_child(nolock) a       ");
		sql.append("where                                  ");
		sql.append("parent_id = :id                        ");
		sql.append("and exists (select 1 from iadscp..system_product where a.product=code and is_active=1) ");
		return executeQuery(sql.toString(), params);
	}
}
