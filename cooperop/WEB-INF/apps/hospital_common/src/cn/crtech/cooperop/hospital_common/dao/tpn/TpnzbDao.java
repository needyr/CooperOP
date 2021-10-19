package cn.crtech.cooperop.hospital_common.dao.tpn;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class TpnzbDao extends BaseDao {
	private final static String SHENGFANGZL_TPNZB = "shengfangzl_TPNZB";
	private final static String SHENGFANGZL_TPNZB_MX = "shengfangzl_TPNZB_MX";

	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * from " + SHENGFANGZL_TPNZB);
		return executeQueryLimit(sql.toString(), params);
	}

	public Result queryTpnzbMX(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * from " + SHENGFANGZL_TPNZB_MX);
		sql.append(" where shengfang_TPNZBID = '"+params.get("shengfang_tpnzbid")+"'");
		return executeQueryLimit(sql.toString(), params);
	}

	public int insert(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT * FROM " + SHENGFANGZL_TPNZB  + " (nolock) WHERE 1=1 ");
		sql.append(" and shengfang_tpnzbid='"+params.get("shengfang_tpnzbid")+"'");
		Record re = executeQuerySingleRecord(sql.toString(), null);	
		if (!CommonFun.isNe(re)) {
			return 0;
		}
		return executeInsert(SHENGFANGZL_TPNZB, params);
	}

	public int insertZbmx(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT * FROM " + SHENGFANGZL_TPNZB_MX  + " (nolock) WHERE 1=1 ");
		sql.append(" and tpnzb_name='"+params.get("tpnzb_name")+"'");
		sql.append(" and shengfang_tpnzbid='"+params.get("shengfang_tpnzbid")+"'");
		Record re = executeQuerySingleRecord(sql.toString(), null);	
		if (!CommonFun.isNe(re)) {
			return 0;
		}
		return executeInsert(SHENGFANGZL_TPNZB_MX, params);
	}

	public int update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("shengfang_tpnzbid", params.remove("shengfang_tpnzbid"));
		return executeUpdate(SHENGFANGZL_TPNZB, params, r);
	}

	public int updateZbmx(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(SHENGFANGZL_TPNZB_MX, params, r);
	}

	// 删除
	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		/* 根据表实际主键删除 */
		conditions.put("shengfang_tpnzbid", params.get("shengfang_tpnzbid"));
		return executeDelete(SHENGFANGZL_TPNZB, conditions);
	}

	public int deleteZbmx(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", params.get("id"));
		return executeDelete(SHENGFANGZL_TPNZB_MX, conditions);
	}

	public Record edit(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT * FROM " + SHENGFANGZL_TPNZB + " (nolock) WHERE 1=1 ");
		setParameter(params, "shengfang_tpnzbid", " and shengfang_tpnzbid =:shengfang_tpnzbid ", sql);		
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record zbmxEdit(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT * FROM " + SHENGFANGZL_TPNZB_MX + " (nolock) WHERE 1=1 ");
		setParameter(params, "id", " and id =:id ", sql);		
		return executeQuerySingleRecord(sql.toString(), params);
	}

}
