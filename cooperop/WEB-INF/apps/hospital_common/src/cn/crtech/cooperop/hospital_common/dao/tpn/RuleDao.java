package cn.crtech.cooperop.hospital_common.dao.tpn;

import java.util.HashMap;
import java.util.Map;


import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class RuleDao extends BaseDao{

	private final static String TPNZL = "TPNZL";
	private final static String TPNZL_RULE="tpnzl_rule";
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();		
		sql.append(" SELECT TPNZL_ID,TPNZL_Name, ");
		sql.append(" TPNZL_Nianl_Start,TPNZL_Nianl_End,");
		sql.append(" TPNZL_LX,state,sort,beizhu");
		sql.append(" FROM "+TPNZL);
		sql.append(" WHERE 1 =1 ");

		/* 模糊查询 */
		if (!CommonFun.isNe(params.get("query_name"))) {
			params.put("condition", "%" + params.get("query_name") + "%");
			sql.append(" and TPNZL_Name LIKE :condition ");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result queryTpnzlRule(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();	
		sql.append(" SELECT ID,TPNZL_ID,XMID,XMBH,");
		sql.append(" XMMCH,fdname,FORMUL,[VALUE],unit,routename,");
		sql.append(" groupsn,state");
		sql.append("  FROM "+TPNZL_RULE);
		sql.append("  where TPNZL_ID = :tpnzl_id");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();	
		sql.append(" SELECT TPNZL_ID,TPNZL_Name, ");
		sql.append(" TPNZL_Nianl_Start,TPNZL_Nianl_End,");
		sql.append(" TPNZL_LX,state,sort,beizhu");
		sql.append(" FROM "+TPNZL);
		sql.append("  where TPNZL_ID = :tpnzl_id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result getXmmch(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();	
		sql.append(" SELECT * from shengfangzl_xm(nolock) where xmlx='TPN规则定义'  and BEACTIVE='是'  ");
		setParameter(params, "filter", " and XMMCH like '%'+'"+params.get("filter")+"'+'%'", sql);
		return executeQueryLimit(sql.toString(), params);
	}

	//添加
	public int insert(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT * FROM " + TPNZL + " (nolock) WHERE 1=1 ");
		sql.append(" and TPNZL_ID='"+params.get("TPNZL_ID")+"'");
		Record re = executeQuerySingleRecord(sql.toString(), null);	
		if (!CommonFun.isNe(re)) {
			return 0;
		}
		return executeInsert(TPNZL, params);
	}
	
	public int insertTpnMX(Map<String, Object> params) throws Exception {
		return executeInsert(TPNZL_RULE, params);
	}

	//修改
	public int updateByState(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("tpnzl_id", params.remove("tpnzl_id"));
		return executeUpdate(TPNZL, params,r);
	}
	public int updateByState2(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TPNZL_RULE, params,r);
	}

	//删除
	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("tpnzl_id", params.get("tpnzl_id"));
		return executeDelete(TPNZL, conditions);
	}
	
	public int deleteRuleMX(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", params.get("id"));
		return executeDelete(TPNZL_RULE, conditions);
	}
	

	

}
