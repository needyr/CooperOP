package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

//check_result_sort 中间表（审查结果分类与标准分类）维护
public class CheckResultSortDao_del extends BaseDao{

	private final static String TABLE_NAME = "check_result_sort";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from "+TABLE_NAME+" (nolock) where 1=1");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
		}
		setParameter(params, "filter", " and (sort_name like :key )", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from "+TABLE_NAME+" (nolock) where 1 = 1");
		setParameter(params, "sort_id", " and sort_id =:sort_id ", sql);
		setParameter(params, "type", " and type =:type ", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("sort_id", params.remove("sort_id_old"));
		r.put("type", params.remove("type_old"));
		return executeUpdate(TABLE_NAME, params, r);
	}
}
