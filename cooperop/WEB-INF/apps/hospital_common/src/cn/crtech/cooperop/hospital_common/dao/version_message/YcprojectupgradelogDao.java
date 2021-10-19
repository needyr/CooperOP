package cn.crtech.cooperop.hospital_common.dao.version_message;

import java.util.Map;

import cn.crtech.choho.authresource.util.CommonFun;
import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class YcprojectupgradelogDao extends BaseDao{
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select                                       ");
		sql.append("a.upgrade_id,                                ");
		sql.append("a.product_name,                              ");
		sql.append("a.time,                                      ");
		sql.append("a.version,                                   ");
		sql.append("a.update_content                             ");
		sql.append("from YC_project_upgrade_log(nolock) a        ");
		sql.append("where 1=1                                    ");
		if (!(CommonFun.isNe(params.get("version")))) {
			params.put("version", "%"+params.get("version")+"%");
			sql.append(" and a.version like :version");
		}
		if (!(CommonFun.isNe(params.get("product_name")))) {
			params.put("product_name", "%"+params.get("product_name")+"%");
			sql.append(" and a.product_name like :product_name");
		}
		if(!(CommonFun.isNe(params.get("start_time"))) && !(CommonFun.isNe(params.get("stop_time")))) {
			sql.append(" and time between :start_time and :stop_time");
		}
		if(!(CommonFun.isNe(params.get("start_time"))) && (CommonFun.isNe(params.get("stop_time")))) {
			sql.append(" and time > :start_time ");
		}
		if((CommonFun.isNe(params.get("start_time"))) && !(CommonFun.isNe(params.get("stop_time")))) {
			sql.append(" and time < :stop_time ");
		}
		if(cn.crtech.cooperop.bus.util.CommonFun.isNe(params.get("sort"))) {
			params.put("sort", "time desc ");
		}
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryVersion(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct version from YC_project_upgrade_log(nolock) where 1=1  ");
		return executeQueryLimit(sql.toString(), params);
	}

	
	public void insert(Map<String, Object> params) throws Exception {
		executeInsert("YC_project_upgrade_log", params);
	}
	
	public Record getById(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select                                       ");
		sql.append("a.upgrade_id,                                ");
		sql.append("a.product_name ,                             ");
		sql.append("a.time,                                      ");
		sql.append("a.version,                                   ");
		sql.append("a.update_content                             ");
		sql.append("from YC_project_upgrade_log(nolock) a        ");
		sql.append("where a.upgrade_id=:upgrade_id               ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public void update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("upgrade_id", params.remove("upgrade_id"));
		executeUpdate("YC_project_upgrade_log", params, r);
	}
	
	public Record getupatemg(Map<String, Object> params) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select                                       ");
		sql.append("upgrade_id,                                  ");
//		sql.append("b.pro_name as p_name,                        ");
//		sql.append("c.type_name as t_name,                       ");
//		sql.append("a.time,                                      ");
//		sql.append("d.module_name as m_name,                     ");
//		sql.append("a.version,                                   ");
//		sql.append("a.adjusted_by,                               ");
//		sql.append("a.imp_location,                              ");
//		sql.append("a.imp_project,                               ");
//		sql.append("a.imp_by,                                    ");
//		sql.append("a.issued_by,                                 ");
		sql.append("update_content                               ");
		sql.append("from YC_project_upgrade_log(nolock)          ");
//		sql.append("left join YC_product_dict(nolock) b on       ");
//		sql.append("a.product_id = b.product_ID                  ");
//		sql.append("left join YC_upgrade_type_dict(nolock) c on  ");
//		sql.append("a.type_id = c.type_ID                        ");
//		sql.append("left join YC_module_dict(nolock) d on        ");
//		sql.append("a.module_id = d.module_ID                    ");
		sql.append("where upgrade_id=:upgrade_id               ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
