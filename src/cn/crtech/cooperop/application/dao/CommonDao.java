package cn.crtech.cooperop.application.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class CommonDao extends BaseDao {
	public Result listAllProducts(Map<String, Object> req) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from system_product" + "(nolock)" + " where 1 = 1");
		setParameter(req, "filter", " and upper(code + ',' + name) like '%' + upper(:filter) + '%'", sql);
		
	    long start = CommonFun.isNe(req.get("start")) ? 1 : Long.parseLong((String)req.get("start"));
	    int limit = CommonFun.isNe(req.get("limit")) ? 0 : Integer.parseInt((String)req.get("limit"));
	    Result rs = executeQueryLimitTotal(sql.toString(), req, (String)req.remove("totals"), start, limit);
        return rs;
	}
	public Result getJasperTemp(Map<String, Object> req) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from system_view_jasper" + "(nolock)" + " where 1 = 1");
		setParameter(req, "no", " and no=:no", sql);
		setParameter(req, "view_id", " and view_id=:id", sql);
        return executeQuery(sql.toString(), req);
	}
	
	public Record getSystemView(Map<String, Object> conditions) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from system_view ");
		sql.append(" where type = :type and id = :id and flag=:flag and system_product_code=:system_product_code");
		return executeQuerySingleRecord(sql.toString(), conditions);
	}
	
	public void deleteNormalPopedom(Map<String, Object> conditions) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from system_popedom where custom_popedom != '1'  ");
		execute(sql.toString(), conditions);
	}
	
	public void insertNormalPopedom(Map<String, Object> params) throws Exception {
		executeInsert("system_popedom", params);
	}
	
	public Result queryDefaultFields(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		params.put("system_user_id", user().getId());
		sql.append("select * from system_datatable_fields(nolock) where 1 = 1");
		sql.append(" and pageid=:pageid and tableid=:tableid and system_user_id=:system_user_id ");
		setParameter(params, "is_default", " and is_default=:is_default", sql);
        return executeQuery(sql.toString(), params);
	}
	
}
