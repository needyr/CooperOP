package cn.crtech.cooperop.application.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import cn.crtech.cooperop.bus.cache.Dictionary;
import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Connection;
import cn.crtech.cooperop.bus.rdms.ConnectionPool;
import cn.crtech.cooperop.bus.rdms.DataBase;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.LocalThreadMap;

public class SchemeDao extends BaseDao {
	
	public final static String TABLE_QUERY_SCHEME = "cr_zl_sel_fa";
	public final static String TABLE_DJSELECT_SCHEME = "cr_djselefa";
	public final static String TABLE_DELETE_SCHEME = "cr_djdelfa";
	public final static String TABLE_UPDATE_SCHEME = "cr_ym_up_fa";
	public final static String TABLE_NAME_TEMP_HZ = "tmp_djhz";
	public final static String TABLE_NAME_TEMP_MX = "cr_dj_";
	
	public Record queryField(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_QUERY_SCHEME + "(nolock)");
		sql.append(" where fangalx = :fangalx and fangabh = :fangabh");
		Record conditions = new Record();
		conditions.put("fangalx", params.get("fangalx"));
		conditions.put("fangabh", params.get("fangabh"));
		return executeQuerySingleRecord(sql.toString(), conditions );
	}
	
	public Record getScheme(Map<String, Object> params,String schemetype) throws Exception {
		StringBuffer sql = new StringBuffer();
//		sql.append(" select * from (");
//		sql.append(" select fangalx,'ym_up_'+rtrim(fangabh) as fanganbh,fangamch,'1' as type ,zdysqls from cr_ym_up_fa       ");
//		sql.append(" union                                                      ");
//		sql.append(" select fangalx,'zl_select_'+rtrim(fangabh) as fanganbh,fangamch,'2' as type,zdysqls from cr_zl_sel_fa      ");
//		sql.append(" union                                                      ");
//		sql.append(" select fangalx,'dj_select_'+rtrim(fangabh) as fanganbh,fangamch,'3' as type,'' as zdysqls from cr_djselefa       ");
//		sql.append(" 	union                                                            ");
//		sql.append("   select '' as fangalx ,rtrim(functionname) as fangalx,functitle as fangamch ,'4' as type ,'' as zdysqls from cr_funclist  ");
//		sql.append(" ) fa where 1=1      ");
		
		sql.append(" select * from "+ schemetype+"(nolock) where 1=1 ");
		
		sql.append(" and rtrim(fangalx) = :fangalx and rtrim(fangabh) = :fangabh ");
		sql.append(" and rtrim(system_product_code)=:system_product_code");
		Record conditions = new Record();
		conditions.put("fangalx", params.get("fangalx"));
		conditions.put("fangabh", params.get("fangabh"));
		conditions.put("system_product_code", params.get("system_product_code"));
		return executeQuerySingleRecord(sql.toString(), conditions );
	}
	
	public Result getQuerySchemeMetaData(String sql, Map<String, Object> params, Record scheme) throws Exception {
		String tsql = sql;
		StringBuffer ssql = new StringBuffer();
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys, new KeyComparator());
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String key = it.next();
			tsql = tsql.replaceAll(":" + key, "'" + params.get(key) + "'");
			tsql = tsql.replaceAll(":hz." + key, "'" + params.get(key) + "'");
			if (key.equals("_filter") && !CommonFun.isNe(params.get(key)) && !CommonFun.isNe(scheme.get("filterflds"))) {
				String[] fields = scheme.getString("filterflds").split(",");
				ssql.append(" and ( 1 = 2 ");
				for (String field : fields) {
					if ("PartialMatch".equals(scheme.getString("searchtype"))) {
						ssql.append(" or upper(" + field + ") like '%' + upper(:" + key + ") + '%' ");
					} else {
						ssql.append(" or " + field + " = upper(:" + key + ") ");
					}
				}
				ssql.append(" )");
			}
		}
		DataBase d = ConnectionPool.databases.get(conn.getDBConfigId());
		String s = d.getConnectString();
		String server = s.substring(s.indexOf("//")+2, s.indexOf(";"));
		server = server.replace(":", ",");
		tsql = tsql.replaceAll("'", "''");
		StringBuffer psql = new StringBuffer();
        psql.append(" EXEC sp_configure 'show advanced options', 1;       "); 
        psql.append(" RECONFIGURE;                                        "); 
        psql.append(" EXEC sp_configure 'Ad Hoc Distributed Queries', 1   "); 
        psql.append(" RECONFIGURE;                                        "); 
        executeUpdate(psql.toString(), null);
        psql = new StringBuffer();
        psql.append(" select * from openrowset('sqloledb', 'server="+server+";database=" + conn.getCatalog() + 
        		";Trusted_Connection=yes', 'SET FMTONLY OFF ;SET NOCOUNT ON; ");
        psql.append(tsql);
	    psql.append(" ') where 1 = 1");
	    psql.append(ssql);
        return getMetaData(psql.toString(), params);
	}
	
	public Result executeQueryScheme(String sql, Map<String, Object> params, Record scheme) throws Exception {
		String tsql = sql;
		StringBuffer ssql = new StringBuffer();
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys, new KeyComparator());
		Iterator<String> it = keys.iterator();
		DataBase d = ConnectionPool.databases.get(conn.getDBConfigId());
		String s = d.getConnectString();
		String server = s.substring(s.indexOf("//")+2, s.indexOf(";"));
		server = server.replace(":", ",");
		while (it.hasNext()) {
			String key = it.next();
			tsql = tsql.replaceAll(":" + key, "'" + params.get(key) + "'");
			tsql = tsql.replaceAll(":hz." + key, "'" + params.get(key) + "'");
			if (key.equals("_filter") && !CommonFun.isNe(params.get(key)) && !CommonFun.isNe(scheme.get("filterflds"))) {
				String[] fields = scheme.getString("filterflds").split(",");
				ssql.append(" and ( 1 = 2 ");
				for (String field : fields) {
					if ("PartialMatch".equals(scheme.getString("searchtype"))) {
						ssql.append(" or upper(" + field + ") like '%' + upper(:" + key + ") + '%' ");
					} else {
						ssql.append(" or " + field + " = upper(:" + key + ") ");
					}
				}
				ssql.append(" )");
			}
		}
		tsql = tsql.replaceAll("'", "''");
		
		StringBuffer psql = new StringBuffer();
        psql.append(" EXEC sp_configure 'show advanced options', 1;       "); 
        psql.append(" RECONFIGURE;                                        "); 
        psql.append(" EXEC sp_configure 'Ad Hoc Distributed Queries', 1   "); 
        psql.append(" RECONFIGURE;                                        "); 
        executeUpdate(psql.toString(), null);
		
        psql = new StringBuffer();
        psql.append(" select * from openrowset('sqloledb', 'server="+server+";database=" + conn.getCatalog() + 
        		";Trusted_Connection=yes', 'SET FMTONLY OFF ;SET NOCOUNT ON; ");
        psql.append(tsql);
	    psql.append(" ') where 1 = 1");
	    psql.append(ssql);
	    

	    long start = CommonFun.isNe(params.get("start")) ? 1 : Long.parseLong((String)params.get("start"));
	    int limit = CommonFun.isNe(params.get("limit")) ? 0 : Integer.parseInt((String)params.get("limit"));
	    Result rs = executeQueryLimitTotal(psql.toString(), params, (String)params.remove("totals"), start, limit);
        return rs;
	}
	
	public String executeScheme(String sql, Map<String, Object> params) throws Exception {
		Record d = new Record(params);
		Iterator<String> it = params.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			d.put("hz." + key, params.get(key));
		}
	    String rs = execute(sql.toString(), d);
        return rs;
	}
	public Result executeQuerySchemeList(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from (");
		sql.append(" select view_id as fangalx,case when(after_save = 'Y') then 'da_save_yin_' else 'da_yin_' end+no as fanganbh, description as fangamch ,'4' as type,system_product_code from system_view_jasper(nolock)");
		sql.append(" union ");
		
		sql.append(" select fangalx,'ym_up_'+rtrim(fangabh)+(case when(is_close='1') then '_closeModal' else '' end) as fanganbh ,fangamch,'1' as type,system_product_code from cr_ym_up_fa(nolock)       ");
		sql.append(" union                                                      ");
		sql.append(" select fangalx,'zl_select_'+rtrim(fangabh) as fanganbh,fangamch,'2' as type,system_product_code from cr_zl_sel_fa(nolock)      ");
		sql.append(" union                                                      ");
		sql.append(" select fangalx,'dj_select_'+rtrim(fangabh) as fanganbh,fangamch,'3' as type,system_product_code from cr_djselefa(nolock)       ");
		sql.append(" 	union                                                            ");
		sql.append("   select '' as fangalx ,rtrim(functionname) as fangalx,functitle as fangamch ,'4' as type,'' as system_product_code from cr_funclist(nolock)  ");
		sql.append(" ) fa where 1=1      ");
		setParameter(params, "system_product_code", " and (system_product_code=:system_product_code or system_product_code ='') ", sql);
		setParameter(params, "fanganlx", " and (fangalx = :fangalx or fangalx <>'') ", sql);
		
		setParameter(params, "djlx", " and (fangalx=:djlx or fangalx ='' or fangalx is null) ", sql);
		long start = CommonFun.isNe(params.get("start")) ? 1 : Long.parseLong((String)params.get("start"));
	    int limit = CommonFun.isNe(params.get("limit")) ? 0 : Integer.parseInt((String)params.get("limit"));
	    Result rs = executeQueryLimitTotal(sql.toString(), params, (String)params.remove("totals"), start, limit);
	    return rs;
	}
	public int updateCacheHZ(String gzid, Map<String, Object> params) throws Exception {
		Record condition = new Record();
		condition.put("gzid", gzid);
		executeDelete(TABLE_NAME_TEMP_HZ, condition);
		int i = 0;
		Iterator<String> keys = params.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			Record field = new Record();
			field.put("gzid", gzid);
			field.put("fieldname", key);
			field.put("fieldvalue", (String) params.get(key));
			if(Dictionary.getField(key) == null){
				throw new Exception("字段【"+key+"】未定义，请在菜单【开发中心/字段列表】下核实，若有字段调整请点击【重新加载】按钮！");
			}
			field.put("chnname", Dictionary.getField(key).get("chnname"));
			field.put("fieldtype", Dictionary.getField(key).get("fdtype"));
			field.put("fieldlength", Dictionary.getField(key).get("fdsize"));
			field.put("fielddot", Dictionary.getField(key).get("fddec"));
			field.put("readflag", "N");
			field.put("indjfa", "N");
			i += executeInsert(TABLE_NAME_TEMP_HZ, field);
		}
		return i;
	}
	public int updateCacheMX(String flag, String id, String tableid, Map<String, Object> params) throws Exception {
		Record condition = new Record();
		condition.put("gzid", params.remove("gzid"));
		condition.put("dj_sn", params.remove("dj_sn"));
		condition.put("dj_sort", params.remove("dj_sort"));
		return executeUpdate(TABLE_NAME_TEMP_MX + flag + id + (CommonFun.isNe(tableid) ? "" : tableid), params,condition);
	}
	public Record queryCacheMX_maxSN(String flag, String id, String tableid, String gzid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select max(dj_sn) as max_sn,max(dj_sort) as max_sort from " + TABLE_NAME_TEMP_MX + flag + id + (CommonFun.isNe(tableid) ? "" :tableid) + "(nolock)");
		sql.append(" where gzid = :gzid ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("gzid", gzid);
	    return executeQuerySingleRecord(sql.toString(), params);
	}
	public int insertCacheMX(String flag, String id, String tableid, Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME_TEMP_MX + flag + id + (CommonFun.isNe(tableid) ? "" : tableid), params);
	}
	public int delecteCacheMX(String flag, String id, String tableid,String gzid) throws Exception {
		Record condition = new Record();
		condition.put("gzid", gzid);
		return executeDelete(TABLE_NAME_TEMP_MX + flag + id + (CommonFun.isNe(tableid) ? "" : tableid), condition);
	}
//	public Result executeQueryScheme(String sql, Map<String, Object> params) throws Exception {
//		String tsql = sql;
//		Iterator<String> it = params.keySet().iterator();
//		while (it.hasNext()) {
//			String key = it.next();
//			tsql = tsql.replaceAll(":" + key, "'" + params.get(key) + "'");
//		}
//		tsql = tsql.replaceAll("'", "''");
//		
//		String tmp_table = "tempdb..tb" + CommonFun.getSSID();
//		StringBuffer psql = new StringBuffer();
//        psql.append(" IF OBJECT_ID('" + tmp_table + "') IS NOT NULL                           ");
//        psql.append(" 		DROP TABLE " + tmp_table + ";                                     ");
//        psql.append(" select * into " + tmp_table + " from openrowset('sqloledb', 'Trusted_Connection=yes', '");
//        psql.append(tsql);
//	    psql.append(" ') ");
//		executeUpdate(psql.toString(), params);
//		Result rs = executeQueryLimit("select * from " + tmp_table, params);
//		executeUpdate(" 		DROP TABLE " + tmp_table + "                                     ", null);
//        return rs;
//	}
	private class KeyComparator implements Comparator<Object> {
		@Override
		public int compare(Object o1, Object o2) throws ClassCastException {
			String p1 = (String) o1;
			String p2 = (String) o2;
			int l1 = p1 == null ? 0 : p1.length();
			int l2 = p2 == null ? 0 : p2.length();
			return l2 - l1;
		}
	}
}
