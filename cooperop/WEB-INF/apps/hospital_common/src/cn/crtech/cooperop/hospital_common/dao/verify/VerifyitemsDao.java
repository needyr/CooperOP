package cn.crtech.cooperop.hospital_common.dao.verify;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;

public class VerifyitemsDao extends BaseDao{
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT                       ");
		sql.append("id,                          ");
		sql.append("field,                       ");
		sql.append("table_name,                  ");
		sql.append("field_type,                  ");
		sql.append("time_format,                 ");
		sql.append("float_num,                   ");
		sql.append("is_unique,                   ");
		sql.append("is_null,                     ");
		sql.append("parent_bh,                   ");
		sql.append("is_union,                    ");
		sql.append("product                      ");
		sql.append("from verify_items(nolock) a  ");
		sql.append("where 1=1                    ");
		sql.append("and parent_bh = :parent_bh   ");
		sql.append("and exists (select 1 from iadscp..system_product where a.product=code and is_active=1) ");
		return executeQuery(sql.toString(), params);
	}
}
