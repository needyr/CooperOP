package cn.crtech.cooperop.hospital_common.dao.verify;

import java.util.Map;


import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class VerifylogDao extends BaseDao{
	
	public void insert(Map<String, Object> params) throws Exception {
		executeInsert("verify_log", params);
	}
	
	public void update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("log_bh", params.remove("log_bh"));
		executeUpdate("verify_log", params, r);
	}
	
	public Result queryQuestion(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,                         ");
		sql.append("b.table_name as i_table_name,       ");
		sql.append("b.field as i_field,                 ");
		sql.append("b.field_type,                       ");
		sql.append("b.time_format,                      ");
		sql.append("b.float_num,                        ");
		sql.append("b.is_unique,                        ");
		sql.append("b.is_null,                          ");
		sql.append("b.is_union,                         ");
		sql.append("c.table_name as c_table_name,       ");
		sql.append("c.field as c_field                  ");
		sql.append("from verify_abnormal (nolock) a     ");
		sql.append("inner join verify_items (nolock) b  ");
		sql.append("on a.verify_items_id=b.id           ");
		sql.append("left join verify_item_child (nolock) c   ");
		sql.append("on a.verify_item_child_id=c.id    ");
		sql.append("where a.log_bh =                  ");
		sql.append("(select top 1 log_bh from verify_log (nolock)   ");
		sql.append("where              ");
		//sql.append(" state='0' and      ");
		sql.append(" master_bh=:master_bh        ");
		sql.append("order by check_time desc)   ");
		setParameter(params, "table_name", " and a.table_name like '%'+'"+params.get("table_name")+"'+'%' ", sql);
		setParameter(params, "is_deal", " and a.is_deal=:is_deal ", sql);
		sql.append("order by a.is_deal,a.table_name ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryLogMx(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,                         ");
		sql.append("b.table_name as i_table_name,       ");
		sql.append("b.field as i_field,                 ");
		sql.append("b.field_type,                       ");
		sql.append("b.time_format,                      ");
		sql.append("b.float_num,                        ");
		sql.append("b.is_unique,                        ");
		sql.append("b.is_null,                          ");
		sql.append("b.is_union,                         ");
		sql.append("c.table_name as c_table_name,       ");
		sql.append("c.field as c_field                  ");
		sql.append("from verify_abnormal (nolock) a     ");
		sql.append("inner join verify_items (nolock) b  ");
		sql.append("on a.verify_items_id=b.id           ");
		sql.append("left join verify_item_child (nolock) c   ");
		sql.append("on a.verify_item_child_id=c.id    ");
		sql.append("where a.log_bh = :log_bh      ");
		setParameter(params, "table_name", " and a.table_name like '%'+'"+params.get("table_name")+"'+'%' ", sql);
		setParameter(params, "is_deal", " and a.is_deal=:is_deal ", sql);
		params.put("sort","is_deal,table_name");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from verify_log (nolock) a where 1=1 ");
		setParameter(params, "table_name", " and a.table_name like '%'+'"+params.get("table_name")+"'+'%' ", sql);
		setParameter(params, "is_deal", " and a.is_deal=:is_deal ", sql);
		setParameter(params, "master_bh", " and a.master_bh=:master_bh ", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result queryLog(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,b.description from verify_log (nolock) a left join verify_master (nolock) b on a.master_bh=b.bh where 1=1 ");
		setParameter(params, "table_name", " and a.table_name like '%'+'"+params.get("table_name")+"'+'%' ", sql);
		setParameter(params, "is_deal", " and a.is_deal=:is_deal ", sql);
		setParameter(params, "master_bh", " and a.master_bh=:master_bh ", sql);
		setParameter(params, "start_time", " and a.check_time >= :start_time ", sql);
		setParameter(params, "end_time", " and a.check_time <= :end_time ", sql);
		return executeQueryLimit(sql.toString(), params);
	}
}
