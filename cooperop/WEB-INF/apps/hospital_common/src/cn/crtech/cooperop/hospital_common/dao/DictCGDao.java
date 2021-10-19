package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DictCGDao extends BaseDao {

	/*
	 * 临床指南
	 */
	private final static String DICT_SYS_CG = "dict_sys_cg";
	
	public Record getByName(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " +DICT_SYS_CG+" (nolock)");
		sql.append(" where cg_name = :cg_name");
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public int insert(Map<String, Object> params) throws Exception {
		executeInsert(DICT_SYS_CG, params);
		return getSeqVal(DICT_SYS_CG);
	}
	
	public void updateByName(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("cg_name", params.remove("cg_name"));
		executeUpdate(DICT_SYS_CG, params, r);
	}

	public Result queryByLike(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select cg_name,id from " +DICT_SYS_CG+" (nolock)");
		sql.append(" where 1=1 ");
		if(CommonFun.isNe(params.get("cg_query"))) {
			sql.append(" and 1=0 ");
		}else {
			String cg_query = "%"+(String) params.get("cg_query")+"%";
			sql.append(" and (cg_name like '"+cg_query+"' or cg_description like '"+cg_query+"')");
		}
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryAll(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " +DICT_SYS_CG+" (nolock)");
		return executeQuery(sql.toString(), params);
	}
}
