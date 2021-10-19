package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

//apa_check_sorts 标准定义管理
public class CheckSortDao_del extends BaseDao{

	private final static String TABLE_NAME = "sys_common_regulation";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT a.*,ISNULL(b.SYS_Check_level_name, '不拦截') interceptor_name,ISNULL(c.SYS_Check_level_name, '不提示') info_name ");
		sql.append("FROM apa_check_sorts (nolock) a left JOIN DICT_SYS_CHECKLEVEL (nolock) b on a.interceptor_level = b.SYS_Check_level ");
		sql.append("left join DICT_SYS_CHECKLEVEL (nolock) c ON a.info_level = c.SYS_Check_level ");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
		}
		setParameter(params, "filter", " where (sort_name like :key )", sql);
		params.put("sort", "sort_num asc");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		params.remove("sort_id");
		return executeInsert(TABLE_NAME, params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from "+TABLE_NAME+" (nolock) where 1 = 1");
		setParameter(params, "sort_id", " and sort_id =:sort_id ", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("sort_id", params.get("sort_id"));
		params.remove("sort_id");
		return executeUpdate(TABLE_NAME, params, r);
	}

	public Result queryLevel(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from DICT_SYS_CHECKLEVEL (nolock) where system_product_code = 'ipc' order by SYS_Check_level");
		return executeQuery(sql.toString(), params);
	}
}
