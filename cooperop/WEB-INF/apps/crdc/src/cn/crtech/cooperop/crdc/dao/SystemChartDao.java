package cn.crtech.cooperop.crdc.dao;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class SystemChartDao extends BaseDao {
	private final static String TABLE_NAME = "system_charts";
	private final static String TABLE_NAME_SCHEME = "system_charts_scheme";
	private final static String TABLE_NAME_PLOT = "system_charts_PLOT";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select f.* from "+ TABLE_NAME +"(nolock) f ");
		sql.append(" where 1 = 1 ");
		setParameter(params, "filter", " and upper(id) like '%' + upper(:filter) + '%' ", sql);
		setParameter(params, "flag", " and view_flag = :flag ", sql);
		setParameter(params, "view_id", " and view_id = :view_id ", sql);
		setParameter(params, "viewid", " and view_id = :viewid ", sql);
		if(!"a".equals(params.get("state"))){
			setParameter(params, "state", " and state = :state ", sql);
		}
		setParameter(params, "id", " and id = :id ", sql);
		setParameter(params, "system_product_code", " and system_product_code = :system_product_code ", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select f.* from "+ TABLE_NAME +"(nolock) f ");
		sql.append(" where f.id=:id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	public int insert(Map<String, Object> params) throws Exception {
		executeInsert(TABLE_NAME, params);
		return getSeqVal(TABLE_NAME);
	}
	public int update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params, r);
	}
	public int delete(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from  "+ TABLE_NAME);
		sql.append(" where id=:id ");
		return executeUpdate(sql.toString(), params);
	}
	public int deleteByDJ(String view_flag, String view_id, String system_product_code) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from  "+ TABLE_NAME);
		sql.append(" where view_id=:view_id and view_flag=:view_flag and system_product_code=:system_product_code ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("view_flag", view_flag);
		params.put("view_id", view_id);
		params.put("system_product_code", system_product_code);
		return executeUpdate(sql.toString(), params);
	}
	
	public Result querySchemes(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select f.* from "+ TABLE_NAME_SCHEME +"(nolock) f ");
		sql.append(" where 1 = 1 ");
		setParameter(params, "nosql", " and nosql = :nosql ", sql);
		setParameter(params, "id", " and id = :id ", sql);
		setParameter(params, "system_charts_id", " and system_charts_id = :system_charts_id ", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	public Record getScheme(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select f.* from "+ TABLE_NAME_SCHEME +"(nolock) f ");
		sql.append(" where f.id=:id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	public int insertScheme(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME_SCHEME, params);
	}
	public int insertPlot(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME_PLOT, params);
	}
	public int updateScheme(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME_SCHEME, params, r);
	}
	public int deleteScheme(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from  "+ TABLE_NAME_SCHEME);
		sql.append(" where id=:id ");
		return executeUpdate(sql.toString(), params);
	}
	public int deleteSchemeByDJ(String view_flag, String view_id, String system_product_code) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from  "+ TABLE_NAME_SCHEME);
		sql.append(" where system_charts_id in (select id from "+ TABLE_NAME +" where view_id=:view_id and view_flag=:view_flag and system_product_code=:system_product_code) ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("view_flag", view_flag);
		params.put("view_id", view_id);
		params.put("system_product_code", system_product_code);
		return executeUpdate(sql.toString(), params);
	}
	public int deletePlotByDJ(String view_flag, String view_id, String system_product_code) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from  "+ TABLE_NAME_PLOT);
		sql.append(" where system_charts_id in (select id from "+ TABLE_NAME +" where view_id=:view_id and view_flag=:view_flag and system_product_code=:system_product_code) ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("view_flag", view_flag);
		params.put("view_id", view_id);
		params.put("system_product_code", system_product_code);
		return executeUpdate(sql.toString(), params);
	}
	
	public Result queryPlots(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select f.* from "+ TABLE_NAME_PLOT +"(nolock) f ");
		sql.append(" where 1 = 1 ");
		setParameter(params, "system_charts_id", " and system_charts_id = :system_charts_id ", sql);
		return executeQueryLimit(sql.toString(), params);
	}
}
