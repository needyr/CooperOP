package cn.crtech.cooperop.crdc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.dao.BillDao;
import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.LocalThreadMap;

public class DesignerDao extends BaseDao {
	private final static String TABLE_NAME = "system_view";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" where 1 = 1 and state != -1 and state !=-3");
		setParameter(params, "description", " and upper(description) like '%' + upper(:description) + '%' ", sql);
		setParameter(params, "filter", " and (upper(id) like '%' + upper(:filter) + '%' or upper(description) like '%' + upper(:filter) + '%')  ", sql);
		setParameter(params, "keyword", " and (upper(flag) like '%' + upper(:keyword) + '%' or upper(id) like '%' + upper(:keyword) + '%' or upper(description) like '%' + upper(:keyword) + '%')  ", sql);
		setParameter(params, "flag", " and flag = :flag ", sql);
		setParameter(params, "type", " and type = :type ", sql);
		if(!"a".equals(params.get("state"))){
			setParameter(params, "state", " and state = :state ", sql);
		}
		setParameter(params, "id", " and id = :id ", sql);
		setParameter(params, "djlx", " and id = :djlx ", sql);
		setParameter(params, "system_product_code", " and system_product_code = :system_product_code ", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	public Result queryAll(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" where 1 = 1 and state != -1 and state !=-3");
		setParameter(params, "system_product_code", " and system_product_code = :system_product_code ", sql);
		return executeQuery(sql.toString(), params);
	}
	public Result queryPageCount(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" where is_use = 1 or state =1");
		sql.append(" and  type != 'document' ");
		return executeQuery(sql.toString());
	}
	public Record get(String type, String id, String flag, String system_product_code) throws Exception {
		Record conditions = new Record();
		conditions.put("type", type);
		conditions.put("id", id);
		conditions.put("flag", flag);
		conditions.put("system_product_code", system_product_code);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" where type = :type and id = :id and flag= :flag and system_product_code=:system_product_code");
		return executeQuerySingleRecord(sql.toString(), conditions);
	}	
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int update(String type, String id, String flag,String system_product_code, Map<String, Object> params) throws Exception {
		Record conditions = new Record();
		conditions.put("type", type);
		conditions.put("id", id);
		conditions.put("flag", flag);
		conditions.put("system_product_code", system_product_code);
		params.remove("type");
		params.remove("id");
		params.remove("system_product_code");
		params.remove("flag");
		return executeUpdate(TABLE_NAME, params, conditions);
	}

	public int delete(String type, String id, String flag,String system_product_code) throws Exception {
		Record conditions = new Record();
		conditions.put("type", type);
		conditions.put("id", id);
		conditions.put("flag", flag);
		conditions.put("system_product_code", system_product_code);
		return executeDelete(TABLE_NAME, conditions);
	}

	
	public Result queryJGlist(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from cr_jglist");
		sql.append(" where 1 = 1 ");
		setParameter(params, "filter", " and upper(jigid + ',' + jigname + ',' + jigbh) like '%' + upper(:filter) + '%' ", sql);
		setParameter(params, "jigname", " and jigname = :jigname ", sql);
		setParameter(params, "jigbh", " and jigbh = :jigbh ", sql);
		setParameter(params, "jigid", " and jigid = :jigid ", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	
	public void createmxtable(Map<String, Object> params) throws Exception {
		List<Map<String, Object>> trlist = (List<Map<String, Object>>) params.get("tr");
		if(!CommonFun.isNe(params.get("hastable"))){
			executeUpdate("drop table "+BillDao.TABLE_NAME_TEMP_MX+params.get("flag")+params.get("schemeid")
			+(CommonFun.isNe(params.get("tableid"))?"":params.get("tableid")), null);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("create table "+BillDao.TABLE_NAME_TEMP_MX+params.get("flag")+params.get("schemeid")+params.get("tableid")+" (");
		sql.append("gzid varchar(11),");
		sql.append("dj_sn integer,");
		sql.append("dj_sort integer null default 0,");
		for(int i =0;i<trlist.size(); i++){
			if("字符".equals(trlist.get(i).get("fdtype"))){
				trlist.get(i).put("type", " varchar("+trlist.get(i).get("size")+") null default '' ");
			}else if("整数".equals(trlist.get(i).get("fdtype"))){
				trlist.get(i).put("type", " integer null default 0 ");
			}else if("实数".equals(trlist.get(i).get("fdtype"))){
				trlist.get(i).put("type"," decimal("+trlist.get(i).get("size")+","
			+(CommonFun.isNe(trlist.get(i).get("digitsize"))? "2" :trlist.get(i).get("digitsize"))
						+") null default 0");
			}else if("位图".equals(trlist.get(i).get("fdtype")) || "二进制".equals(trlist.get(i).get("fdtype"))){
				trlist.get(i).put("type", " image null");
			}else if("文本".equals(trlist.get(i).get("fdtype"))){
				trlist.get(i).put("type", " text null");
			}else{
				trlist.get(i).put("type", " varchar("+trlist.get(i).get("size")+")");
			}
			if(i>0){
				sql.append(",");
			}
			sql.append(""+trlist.get(i).get("name") +trlist.get(i).get("type"));
		}
		sql.append(" CONSTRAINT "+BillDao.TABLE_NAME_TEMP_MX + params.get("flag") + params.get("schemeid")
				+ (CommonFun.isNe(params.get("tableid")) ? "" : params.get("tableid"))
				+ "_PK PRIMARY KEY NONCLUSTERED (gzid,dj_sn))");
		executeUpdate(sql.toString(), null);
	}
	
	public Record querymxtable(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) as c from sys.objects where name ='"+BillDao.TABLE_NAME_TEMP_MX+params.get("flag")
		+params.get("schemeid")+(CommonFun.isNe(params.get("tableid"))?"":params.get("tableid"))+"'");
		return executeQuerySingleRecord(sql.toString(), null);
	}
	public Record queryHZtable(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) as c from sys.objects where name ='"+params.get("tname")+"'");
		return executeQuerySingleRecord(sql.toString(), null);
	}
	public void createHZTable(String table, Map<String, Object> params, List<Map<String, Object>> fields) throws Exception {
		if(!CommonFun.isNe(params.get("hastable"))){
			executeUpdate("drop table "+table, null);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("create table "+table+" (");
		sql.append(" [djbh] [varchar](16) NOT NULL , ");
		sql.append(" [jigid] [varchar](16) NOT NULL , ");
		sql.append(" [djbs] [varchar](16) NULL , ");
		sql.append(" [djlx] [varchar](16) NULL, ");
		sql.append(" [rq] [varchar](16) NULL , ");
		sql.append(" [state] [varchar](16) NULL , ");
		sql.append(" [zhiyid] [varchar](16) NULL , ");
		sql.append(" [username] [varchar](32) NULL , ");
		sql.append(" [dep_code] [varchar](16) NULL , ");
		sql.append(" [dep_name] [varchar](32) NULL ");
		String default_fields = ",djbh,jigid,djbs,djlx,rq,state,zhiyid,username,dep_code,dep_name,";
		for(int i =0;i<fields.size(); i++){
			Object field = fields.get(i).get("name");
			if(default_fields.indexOf(","+field+",") < 0){
				sql.append(",");
				sql.append(""+ field +fields.get(i).get("type"));
				
			}
		}
		sql.append(" CONSTRAINT "+table+ "_PK PRIMARY KEY NONCLUSTERED (djbh))");
		executeUpdate(sql.toString(), new HashMap<String, Object>());
	}
	public void createMXYWTable(String table, Map<String, Object> params, List<Map<String, Object>> fields) throws Exception {
		Record r = executeQuerySingleRecord("select count(1) as c from sys.objects where name ='"+table+"'",null);
		if(r.getInt("c") > 0){
			executeUpdate("drop table "+table, null);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("create table "+table+" (");
		sql.append(" gzid varchar(16) NOT NULL, ");
		sql.append(" dj_sn int NOT NULL, ");
		sql.append(" dj_sort int NULL DEFAULT ((0)), ");
		sql.append(" djbh varchar(16), ");
		for(int i =0;i<fields.size(); i++){
			if(i>0){
				sql.append(",");
			}
			sql.append(""+fields.get(i).get("name") +fields.get(i).get("type"));
		}
		sql.append(" CONSTRAINT "+table+ "_PK PRIMARY KEY NONCLUSTERED ");
		sql.append(" ( ");
		sql.append(" 	gzid ASC, ");
		sql.append(" 	dj_sn ASC ");
		sql.append(" )) ");
		executeUpdate(sql.toString(), null);
	}
	
	public void savehist(String type, String id, String flag, String system_product_code) throws Exception {
		Record conditions = new Record();
		conditions.put("type", type);
		conditions.put("id", id);
		conditions.put("flag", flag);
		conditions.put("system_product_code", system_product_code);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from system_view_hist ");
		sql.append(" where type = :type and id = :id and flag= :flag and system_product_code=:system_product_code");
		Result res = executeQuery(sql.toString(), conditions);
		if(res != null && res.getResultset().size() >= 20){
			StringBuffer deletesql = new StringBuffer();
			deletesql.append(" delete from system_view_hist where no = ");
			deletesql.append(" (select min(no) from system_view_hist where type = :type and id = :id and flag= :flag and system_product_code=:system_product_code) ");
			execute(deletesql.toString(), conditions);
		}
		StringBuffer savehistsql = new StringBuffer();
		savehistsql.append("insert into system_view_hist select type,");
		savehistsql.append("	flag,");
		savehistsql.append("	id,");
		savehistsql.append("	description,");
		savehistsql.append("	title,");
		savehistsql.append("	remark,");
		savehistsql.append("content,");
		savehistsql.append("state,");
		savehistsql.append("last_modify_time,");
		savehistsql.append("last_modifier,");
		savehistsql.append("system_product_code,");
		savehistsql.append("initdjsql,");
		savehistsql.append("djprintsql,");
		savehistsql.append("othermxsql,");
		savehistsql.append("tablename,");
		savehistsql.append("tablekey,");
		savehistsql.append("sub,");
		savehistsql.append("getdate() from system_view where type = :type and id = :id and flag= :flag and system_product_code=:system_product_code ");
		execute(savehistsql.toString(), conditions);
	}
	
	public Result queryHist(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from system_view_hist ");
		sql.append(" where 1 = 1 ");
		setParameter(params, "flag", " and flag = :flag ", sql);
		setParameter(params, "type", " and type = :type ", sql);
		setParameter(params, "id", " and id = :id ", sql);
		setParameter(params, "system_product_code", " and system_product_code = :system_product_code ", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	public void reset(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update t set t.content = t1.content,t.initdjsql=t1.initdjsql,t.sub=t1.sub");
		sql.append(" from system_view t,(select * from system_view_hist where no = :no) t1 ");
		sql.append(" where t1.flag = t.flag ");
		sql.append(" and t1.type = t.type ");
		sql.append(" and t1.id = t.id ");
		execute(sql.toString(), params);
	}
}
