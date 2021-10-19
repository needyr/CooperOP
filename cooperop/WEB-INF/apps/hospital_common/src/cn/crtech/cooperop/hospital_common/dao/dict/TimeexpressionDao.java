package cn.crtech.cooperop.hospital_common.dao.dict;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class TimeexpressionDao extends BaseDao{
	
	private static final String TABLENAME = "sys_time_expression";
	
	//初始化数据
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLENAME + "(nolock) ");
		sql.append(" where 1=1 ");
		if(!CommonFun.isNe(params.get("filter"))) {
			params.put("filter", "%" +params.get("filter")+ "%");
			sql.append(" and remark like :filter or name like :filter");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	
	public int insert(Map<String, Object> params) throws Exception {

		return executeInsert(TABLENAME, params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("p_key", params.remove("p_key"));
		return executeUpdate(TABLENAME, params, r);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		
		return executeDelete(TABLENAME, params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLENAME);
		if(!CommonFun.isNe(params.get("p_key"))) {
			if(!CommonFun.isNe(params.get("code"))) {
				//修改唯一判断
				sql.append(" (nolock) where  code = :code and p_key <> :p_key ");
			}else {
				//修改查询
				sql.append(" (nolock) where  p_key = :p_key ");
			}
		}else {
			//添加唯一判断
			sql.append(" (nolock) where  code = :code ");
		}
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	
}
