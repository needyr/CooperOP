package cn.crtech.cooperop.crdc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class FieldDao extends BaseDao {
	private final static String TABLE_NAME = "cr_fldlist";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select f.* from cr_fldlist f ");
		sql.append(" where 1 = 1 ");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
		}
		setParameter(params, "filter", " and (f.fdname like :key or f.chnname like :key) ", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select f.* from cr_fldlist f ");
		sql.append(" where f.fdname=:fdname ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	public int update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("fdname", params.remove("fdname"));
		return executeUpdate(TABLE_NAME, params, r);
	}
	public int delete(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from  "+ TABLE_NAME);
		sql.append(" where fdname=:fdname ");
		return executeUpdate(sql.toString(), params);
	}
}
