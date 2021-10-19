package cn.crtech.cooperop.hospital_common.dao.dict;

import java.util.Map;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
public class DictAllDao extends BaseDao{
	
	private static final String PROC = "update_mapnums_proc";
	
	private static final String TABLE = "maptables (nolock)";

	//查询已经映射的表数据
	public Result querynot(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select ");
		sql.append(" 	a.*, ");
		sql.append(" 	a." +params.get("clname")+ " hisname, ");
		sql.append(" 	b." +params.get("clname")+ " sysname ");
		sql.append(" from ");
		sql.append(params.get("histname") +" (nolock) a ");
		sql.append(" 	inner join "+ params.get("systname") +" (nolock) b on a.sys_p_key = b.p_key ");
		sql.append(" where ");
		sql.append(" 	a.sys_p_key is not null ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	//查询没有映射的表数据
	public Result queryis(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select *, "+params.get("clname") + " hisname");
		sql.append(" from "+params.get("histname")+" (nolock)");
		sql.append(" where sys_p_key is null or sys_p_key = '' ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	//全查询sys字典表
	public Result querysys(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		String table=((String) params.get("systname"));
		String clname=(String) params.get("clname");
			
			sql.append("select *,"+clname+" sysname from "+table+" (nolock)");
		
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
		}
		setParameter(params, "filter", " where ( "+clname+" like :key )", sql);
		params.remove("tablename");
		params.remove("clname");
		log.debug(sql.toString());
		return executeQueryLimit(sql.toString(), params);
	}
	
	//全查询maptables表中的数据
	public Result querymap(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from maptables (nolock)");
		sql.append("where 1=1");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("mapcn", "%" +params.get("filter")+ "%");
			//sql.append(" and mapcn like :mapcn "); @yanguozhi 2019-05-04 :暂不筛选
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("p_key", params.remove("p_key"));
		String table = (String) params.remove("tablename");
		return executeUpdate(table, params, r);
	}
	
	/*public int get(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("p_key", params.remove("p_key"));
		String table=(String) params.remove("tablename");
		return executeUpdate(table, params, r);
	}*/
	
	public Result queryFields(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		/*if(CommonFun.isNe(params.get("tname"))){
			sql.append("SELECT A.name AS table_name,B.name AS column_name,cast(C.value as varchar(100)) AS column_description FROM sys.tables (nolock) A INNER JOIN sys.columns (nolock) B ON B.object_id = A.object_id LEFT JOIN sys.extended_properties (nolock) C ON C.major_id = B.object_id AND C.minor_id = B.column_id WHERE A.name = 'dict_his_bill'");
		}else {
			sql.append("SELECT A.name AS table_name,B.name AS column_name,cast(C.value as varchar(100)) AS column_description FROM sys.tables (nolock) A INNER JOIN sys.columns (nolock) B ON B.object_id = A.object_id LEFT JOIN sys.extended_properties (nolock) C ON C.major_id = B.object_id AND C.minor_id = B.column_id WHERE A.name = '"+params.get("tname")+"'");
		}
		log.debug(sql.toString());
		params.remove("tname");*/
		sql.append(" select ");
		sql.append(" 	a.name as table_name, ");
		sql.append(" 	b.name as column_name, ");
		sql.append(" 	cast( c.value as varchar ( 100 ) ) as column_description ");
		sql.append(" from ");
		sql.append(" 	sys.tables ( nolock ) a ");
		sql.append(" 	inner join sys.columns ( nolock ) b on b.object_id = a.object_id ");
		sql.append(" 	left join sys.extended_properties ( nolock ) c on c.major_id = b.object_id ");
		sql.append(" 	and c.minor_id = b.column_id ");
		sql.append(" where ");
		sql.append(" 	a.name = :histname ");
		return executeQuery(sql.toString(), params);
	}
	
	//查询已经映射的表数据
	public Record edit(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		String systable = ((String) params.get("systname"));
		String clname = (String) params.get("clname");
		//修改匹配时，sysname有值，推荐匹配时没有值
		if(!CommonFun.isNe(params.get("sysname"))) {
			sql.append(" select "+clname+" sys_name, p_key from " + systable + " (nolock) where " +clname+ " = :sysname");
		}else {
			sql.append(" select top 1 "+ clname +" sys_name, p_key from " + systable + " (nolock) ");
			sql.append(" where "+clname+" like '%"+((String)params.get("hisname")).trim()+"%' ");
			params.put("sort", "sys_name");
		}
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	//@yanguozhi 2019-04-04 刷新未配对数目
	public String reMapNum(Map<String, Object> params) throws Exception {
		return execute(" exec " + PROC, params);
	}
	
	public Record getBytb(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from " + TABLE);
		sql.append(" where 1 = 1");
		sql.append(" and histname = :histname ");
		sql.append(" and systname = :systname ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
