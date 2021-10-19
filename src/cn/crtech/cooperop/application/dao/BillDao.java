package cn.crtech.cooperop.application.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cn.crtech.cooperop.bus.cache.Dictionary;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.ConnectionPool;
import cn.crtech.cooperop.bus.rdms.DataBase;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class BillDao extends BaseDao {

	public final static String TABLE_NAME = "system_view";
	public final static String TABLE_NAME_DETAIL = "system_view_table";
	public final static String TABLE_NAME_TEMP_HZ = "tmp_djhz";
	public final static String TABLE_NAME_TEMP_MX = "cr_dj_";
	public final static String PROCEDUE_GZID = "scr_get_maxbh";
	public final static String PROCEDUE_DJBH = "scr_get_maxdjbh";
	public final static String PROCEDUE_SAVE_DJ = "scr_save_dj_bs";

	public String getGZID(String flag, String jgid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("{call " + PROCEDUE_GZID + "(:flag, :add_flag, :maxbh, :jgid)}");
		Record ins = new Record();
		ins.put("flag", "LGZ");
		ins.put("add_flag", 3);
		ins.put("jgid", jgid==null?"000":jgid);
		Map<String, Integer> outs = new HashMap<String, Integer>();
		outs.put("maxbh", java.sql.Types.VARCHAR);
		Record rtn = executeCall(sql.toString(), ins, outs);
		return rtn.getString("maxbh");
	}
	public String getMAXBH(String flag, String jgid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("{call " + PROCEDUE_GZID + "(:flag, :add_flag, :maxbh, :jgid)}");
		Record ins = new Record();
		ins.put("flag", flag);
		ins.put("add_flag", 3);
		ins.put("jgid", jgid==null?"000":jgid);
		Map<String, Integer> outs = new HashMap<String, Integer>();
		outs.put("maxbh", java.sql.Types.VARCHAR);
		Record rtn = executeCall(sql.toString(), ins, outs);
		return rtn.getString("maxbh");
	}
	public String getDJBH(String flag, int add_flag, String jgid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("{call " + PROCEDUE_DJBH + "(:jgid, :flag, :add_flag, :maxbh)}");
		Record ins = new Record();
		ins.put("flag", flag);
		ins.put("add_flag", add_flag);
		ins.put("jgid", jgid==null?"":jgid);
		Map<String, Integer> outs = new HashMap<String, Integer>();
		outs.put("maxbh", java.sql.Types.VARCHAR);
		Record rtn = executeCall(sql.toString(), ins, outs);
		return rtn.getString("maxbh");
	}

	public Record getDJ(String system_product_code, String type, String id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME + "(nolock)");
		sql.append(" where system_product_code = :system_product_code ");
		sql.append("   and type = :type ");
		sql.append("   and id = :id ");
		Record params = new Record();
		params.put("system_product_code", system_product_code);
		params.put("type", type);
		params.put("id", id);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	public Record getDJMX(String system_product_code, String type, String id, String tableid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from system_view_table " + "(nolock)");
		sql.append(" where system_product_code = :system_product_code ");
		sql.append("   and viewtype = :type ");
		sql.append("   and viewid = :id ");
		sql.append("   and tableid = :tableid ");
		Record params = new Record();
		params.put("system_product_code", system_product_code);
		params.put("type", type);
		params.put("id", id);
		params.put("tableid", tableid);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	public Result listDJMX(String system_product_code, String viewtype, String viewid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME_DETAIL + "(nolock)");
		sql.append(" where system_product_code = :system_product_code ");
		sql.append("   and viewtype = :viewtype ");
		sql.append("   and viewid = :viewid ");
		Record params = new Record();
		params.put("system_product_code", system_product_code);
		params.put("viewtype", viewtype);
		params.put("viewid", viewid);
		return executeQuery(sql.toString(), params);
	}

	public Record getCacheHZ(String gzid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME_TEMP_HZ + "(nolock)");
		sql.append(" where gzid = :gzid ");
		Record params = new Record();
		params.put("gzid", gzid);
		Result hz = executeQuery(sql.toString(), params);
		Record thz = new Record();
		for (Record h :hz.getResultset()) {
			thz.put(h.getString("fieldname"), h.get("fieldvalue"));
		}
		return thz;
	}
	public int saveMaterials(Map<String, Object> params) throws Exception {
		String o = (String) params.remove("tablename");
		executeInsert(o, params);
		return getSeqVal(o);
	}
	public int updateMaterials(Map<String, Object> params) throws Exception {
		String o = (String) params.remove("tablename");
		Record cond = new Record();
		cond.put((String)params.remove("idname"), params.remove("id"));
		executeUpdate(o, params, cond);
		return getSeqVal(o);
	}
	public Record getMaterialsTable(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from [system_view](nolock) where type=:type and id=:id and flag=:flag and system_product_code=:system_product_code");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	public Record getMaterials(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from "+params.get("tablename")+"(nolock) where "+params.get("tablekey")+"='"+params.get("id")+"'");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	public int insertCacheHZ(Map<String, Object> params) throws Exception {
		int i = 0;
		Iterator<String> keys = params.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			Record field = new Record();
			field.put("gzid", params.get("gzid"));
			field.put("fieldname", key);
			if(params.get(key) instanceof List){
				List<String> l = (List<String>) params.get(key);
				params.put(key, StringUtils.join(l, ","));
			}
			field.put("fieldvalue", params.get(key));
			Record f = Dictionary.getField(key);
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
			if(params.get(key) instanceof List){
				List<String> l = (List<String>) params.get(key);
				params.put(key, StringUtils.join(l, ","));
			}
			if(Dictionary.getField(key) == null){
				throw new Exception("字段【"+key+"】未定义，请在菜单【开发中心/字段列表】下核实，若有字段调整请点击【重新加载】按钮！");
			}
			field.put("chnname", Dictionary.getField(key).get("chnname"));
			field.put("fieldtype", Dictionary.getField(key).get("fdtype"));
			field.put("fieldlength", Dictionary.getField(key).get("fdsize"));
			field.put("fielddot", Dictionary.getField(key).get("fddec"));
			field.put("readflag", "N");
			field.put("indjfa", "N");
			field.put("fieldvalue", (String) params.get(key));
			i += executeInsert(TABLE_NAME_TEMP_HZ, field);
		}
		return i;
	}

	public int deleteCacheHZ(String gzid) throws Exception {
		Record condition = new Record();
		condition.put("gzid", gzid);
		int i = executeDelete(TABLE_NAME_TEMP_HZ, condition);
		return i;
	}

	public Result queryCacheMX(String flag, String id, String tableid, String gzid, Map<String, Object> cond) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME_TEMP_MX + flag + id + (CommonFun.isNe(tableid) ? "" :tableid) + "(nolock)");
		sql.append(" where gzid = :gzid ");
		Record params = new Record();
		params.put("gzid", gzid);
		params.put("sort", "dj_sn");
		long start = CommonFun.isNe(cond.get("start")) ? 1 : Long.parseLong((String)cond.get("start"));
	    int limit = CommonFun.isNe(cond.get("limit")) ? 0 : Integer.parseInt((String)cond.get("limit"));
	    Result rs = executeQueryLimitTotal(sql.toString(), params, (String)cond.get("totals"), start, limit);
	    return rs;
	}
	public Record queryCacheMX_maxSN(String flag, String id, String tableid, String gzid, Map<String, Object> cond) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select max(dj_sn) as max_sn from " + TABLE_NAME_TEMP_MX + flag + id + (CommonFun.isNe(tableid) ? "" :tableid) + "(nolock)");
		sql.append(" where gzid = :gzid ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("gzid", gzid);
	    return executeQuerySingleRecord(sql.toString(), params);
	}
	public Result getMXMetaData(String flag, String id, String tableid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME_TEMP_MX + flag + id + (CommonFun.isNe(tableid) ? "" :tableid) + "(nolock)");
		sql.append(" where 1 = 2 ");
		return getMetaData(sql.toString(), new Record());
	}
	
	public Record getCacheMX(String flag, String id, String tableid,String gzid,String dj_sn,String dj_sort) throws Exception {
		Record condition = new Record();
		condition.put("gzid", gzid);
		condition.put("dj_sn", dj_sn);
		condition.put("dj_sort", dj_sort);
		return executeQuerySingleRecord("select * from " + 
		TABLE_NAME_TEMP_MX + flag + id + (CommonFun.isNe(tableid) ? "" : tableid) + "(nolock) where gzid=:gzid"
				+ " and dj_sn=:dj_sn and dj_sort=:dj_sort",condition);
	}
	
	public int insertCacheMX(String flag, String id, String tableid, Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME_TEMP_MX + flag + id + (CommonFun.isNe(tableid) ? "" :tableid), params);
	}

	public int updateCacheMX(String flag, String id, String tableid, Map<String, Object> params) throws Exception {
		Record condition = new Record();
		condition.put("gzid", params.remove("gzid"));
		condition.put("dj_sn", params.remove("dj_sn"));
		condition.put("dj_sort", params.remove("dj_sort"));
		return executeUpdate(TABLE_NAME_TEMP_MX + flag + id + (CommonFun.isNe(tableid) ? "" : tableid), params,condition);
	}

	public void deleteMX(Map<String, Object> params, String tabname) throws Exception {
		executeDelete(tabname, params);
	}
	public void deleteMX1(Map<String, Object> parameters, Map<String, Object> params, String tabname) throws Exception {
		executeUpdate(tabname, parameters, params);
	}
	public int deleteCacheMX(String flag, String id, String tableid,String gzid,String dj_sn,String dj_sort) throws Exception {
		Record condition = new Record();
		condition.put("gzid", gzid);
		if(dj_sn != null){
			condition.put("dj_sn", dj_sn);
		}
		if(dj_sort != null){
			condition.put("dj_sort", dj_sort);
		}
		return executeDelete(TABLE_NAME_TEMP_MX + flag + id + (CommonFun.isNe(tableid) ? "" : tableid),condition);
	}
	public Record getMXSnAndSort(String flag, String id, String tableid,String gzid) throws Exception {
		Record condition = new Record();
		condition.put("gzid", gzid);
		return executeQuerySingleRecord("select max(dj_sn)+1 as dj_sn,max(dj_sort)+1 as dj_sort from " + 
		TABLE_NAME_TEMP_MX + flag + id + (CommonFun.isNe(tableid) ? "" : tableid) + "(nolock) where gzid=:gzid",condition);
	}

	public String saveBill(String gzid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("{:return_value = call " + PROCEDUE_SAVE_DJ + "(:gzid, :djbh)}");
		Record ins = new Record();
		ins.put("gzid", gzid);
		Map<String, Integer> outs = new HashMap<String, Integer>();
		outs.put("return_value", java.sql.Types.INTEGER);
		outs.put("djbh", java.sql.Types.VARCHAR);
		Record rtn = executeCall(sql.toString(), ins, outs);
		int i = rtn.getInt("return_value");
		if (i != 0) {
			throw new Exception("执行数据转存存储过程出错，错误编码：" + i);
		}
		return rtn.getString("djbh");
	}
	public Result executeQueryScheme(String sql, String countsql, Map<String, Object> params) throws Exception {
		String tsql = sql;
		DataBase d = ConnectionPool.databases.get(conn.getDBConfigId());
		String s = d.getConnectString();
		String server = s.substring(s.indexOf("//")+2, s.indexOf(";"));
		server = server.replace(":", ",");
		/*List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys, new KeyComparator());
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String key = it.next();
			if(params.get(key).getClass().isArray()){
				String[] v = (String[])params.get(key);
				String keyvalue = "";
				for(int i=0;i<v.length;i++){
					if(keyvalue==""){
						keyvalue += "'"+v[i]+"'";
					}else{
						keyvalue += ",'"+v[i]+"'";
					}
				}
				params.put(key,keyvalue);
				tsql = tsql.replaceAll("':" + key+"'", (String)params.get(key));
				tsql = tsql.replaceAll("':hz." + key+"'", (String)params.get(key));
				tsql = tsql.replaceAll(":" + key, (String)params.get(key));
				tsql = tsql.replaceAll(":hz." + key,(String)params.get(key));
			}else{
				tsql = tsql.replaceAll("':" + key+"'", "'"+(String)params.get(key)+"'");
				tsql = tsql.replaceAll("':hz." + key+"'", "'"+(String)params.get(key)+"'");
				tsql = tsql.replaceAll(":" + key, "'"+(String)params.get(key)+"'");
				tsql = tsql.replaceAll(":hz." + key, "'"+(String)params.get(key)+"'");
			}
		}*/
		tsql = tsql.replaceAll("'", "''");
		
		StringBuffer psql = new StringBuffer();
		StringBuffer csql = new StringBuffer();
        psql.append(" EXEC sp_configure 'show advanced options', 1;       "); 
        psql.append(" RECONFIGURE;                                        "); 
        psql.append(" EXEC sp_configure 'Ad Hoc Distributed Queries', 1   "); 
        psql.append(" RECONFIGURE;                                        "); 
        executeUpdate(psql.toString(), null);
        psql = new StringBuffer();
        psql.append(" select * from openrowset('sqloledb', 'server="+server+";database=" + conn.getCatalog() + ";Trusted_Connection=yes', 'SET FMTONLY OFF;SET NOCOUNT ON; ");
        psql.append(tsql);
	    psql.append(" ') where 1 = 1");
	    
	    if(countsql != null){
	    	countsql = countsql.replaceAll("'", "''");
	    	csql = new StringBuffer();
	    	csql.append(" select * from openrowset('sqloledb', 'server="+server+";database=" + conn.getCatalog() + ";Trusted_Connection=yes', 'SET FMTONLY OFF;SET NOCOUNT ON; ");
	    	csql.append(countsql);
	    	csql.append(" ') where 1 = 1");
	    }

	    long start = CommonFun.isNe(params.get("start")) ? 1 : Long.parseLong((String)params.get("start"));
	    int limit = CommonFun.isNe(params.get("limit")) ? 0 : Integer.parseInt((String)params.get("limit"));
	    Result rs = executeQueryLimitTotal(psql.toString(), params, (String)params.remove("totals"), start, limit, csql.toString());
        return rs;
	}
	
	public Result queryGZByGZID(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from clientdj(nolock) where gzid =:gzid and zhiyid=:zhiyid");
		return executeQuery(sql.toString(), params);
	}
	public void updateGZ(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("gzid", params.remove("gzid"));
		r.put("zhiyid", params.remove("zhiyid"));
		executeUpdate("clientdj", params, r);
	}
	public void insertGZ(Map<String, Object> params) throws Exception {
		executeInsert("clientdj", params);
	}
	public Result querygz(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from clientdj(nolock) where djbhbs =:djbhbs and zhiyid=:zhiyid");
		return executeQueryLimit(sql.toString(), params);
	}
	public Record getGZField(String gzid, String field)throws Exception {
		Map<String ,Object> p = new HashMap<String ,Object>();
		p.put("gzid", gzid);
		p.put("field", field);
		StringBuffer sql = new StringBuffer();
		sql.append(" select rtrim(fieldvalue) as fieldvalue from "+TABLE_NAME_TEMP_HZ+"(nolock) where rtrim(gzid) =:gzid and rtrim(fieldname) =:field");
		return executeQuerySingleRecord(sql.toString(), p);
	}
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
	public String relpaceParams(String tsql, Map<String, Object> params){
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys, new KeyComparator());
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String key = it.next();
			if(params.get(key) != null && (params.get(key).getClass().isArray() || params.get(key) instanceof List)){
				//String[] v = (String[])params.get(key);
				Object[] v = null;
				if(params.get(key).getClass().isArray()){
					v = (String[])params.get(key);
				}else{
					List<String> ls = (List<String>) params.get(key);
					v = ls.toArray();
				}

				String keyvalue = "";
				for(int i=0;i<v.length;i++){
					if(keyvalue==""){
						keyvalue += "'"+v[i]+"'";
					}else{
						keyvalue += ",'"+v[i]+"'";
					}
				}
				params.put(key, keyvalue);
				while(tsql.indexOf("':" + key+"'") > -1){
					tsql = tsql.replace("':" + key+"'", (String)params.get(key));
				}
				while(tsql.indexOf("':hz." + key+"'") > -1){
					tsql = tsql.replace("':hz." + key+"'", (String)params.get(key));
				}
				while(tsql.indexOf(":" + key) > -1){
					tsql = tsql.replace(":" + key, (String)params.get(key));
				}
				while(tsql.indexOf(":hz." + key) > -1){
					tsql = tsql.replace(":hz." + key, (String)params.get(key));
				}
			}else{
				while(tsql.indexOf("':" + key+"'") > -1){
					tsql = tsql.replace("':" + key+"'", "'"+(String)params.get(key)+"'");
				}
				while(tsql.indexOf("':hz." + key+"'") > -1){
					tsql = tsql.replace("':hz." + key+"'", "'"+(String)params.get(key)+"'");
				}
				while(tsql.indexOf(":" + key) > -1){
					tsql = tsql.replace(":" + key, "'"+(String)params.get(key)+"'");
				}
				while(tsql.indexOf(":hz." + key) > -1){
					tsql = tsql.replace(":hz." + key, "'"+(String)params.get(key)+"'");
				}
			}
		}
	//	tsql = tsql.replaceAll("'", "''");
		tsql = tsql.replaceAll("&", ":");
		return tsql;
	}
	
	public Record getPYM(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select dbo.fun_getPY(:v_get) as pym ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
