package cn.crtech.cooperop.setting.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class OrganizationDao extends BaseDao {
	
	public final static String TABLE_NAME = "system_organization";
	
	public int insert(Map<String, Object> params) throws Exception {
		//String id = getSeqNextVal(TABLE_NAME);
		//params.put("id", id);
		return executeInsert(TABLE_NAME, params);
		//return id;
	}
	
	public int update(String id, Map<String, Object> params) throws Exception {
		if (CommonFun.isNe(id)) {
			return 0;
		}
		Record conditions = new Record();
		conditions.put("id", id);
		return executeUpdate(TABLE_NAME, params, conditions);
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT                         ");
		sql.append(" 	id,                         ");
		sql.append(" 	jigname,                    ");
		sql.append(" 	jigid,                      ");
		sql.append(" 	company_id,                 ");
		sql.append(" 	image,                      ");
		sql.append(" 	jigaddress,                 ");
		sql.append(" 	jigtel,                     ");
		sql.append(" 	state,                      ");
		sql.append(" 	pym                         ");
		sql.append(" FROM                           ");
		sql.append(TABLE_NAME);
		sql.append(" 	WHERE 1=1         ");
		setParameter(params, "state", " and state=:state ", sql);
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("filter", "%" + params.remove("filter") + "%");
			sql.append(" and (jigid like :filter or jigname like :filter or pym like :filter) ");
		}
		
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record get(String id, String jigid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT                    ");
		sql.append(" 	id,                    ");
		sql.append(" 	jigname,               ");
		sql.append(" 	jigid,                 ");
		sql.append(" 	company_id,            ");
		sql.append(" 	image,                 ");
		sql.append(" 	jigaddress,            ");
		sql.append(" 	jigtel,                ");
		sql.append(" 	state,                 ");
		sql.append(" 	pym                    ");
		sql.append(" FROM                      ");
		sql.append(" 	system_organization    ");
		sql.append(" WHERE                     ");
		sql.append(" 	1 = 1                  ");
		
		Record params = new Record();
		
		if (!CommonFun.isNe(id)) {
			params.put("id", id);
			sql.append(" and id=:id ");
		}
		if (!CommonFun.isNe(jigid)) {
			params.put("jigid", jigid);
			sql.append(" and jigid=:jigid ");
		}
		if (params.isEmpty()) {
			return null;
		}
		
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
}
