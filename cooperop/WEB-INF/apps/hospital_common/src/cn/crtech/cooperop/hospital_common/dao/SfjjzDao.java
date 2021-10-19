package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import org.snaker.engine.scheduling.IScheduler;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ProductmanagetService;

public class SfjjzDao extends BaseDao{

	private final static String TABLE_NAME = "shengfangzl_jjz";
	private final static String TABLE_NAME_MX = "shengfangzl_jjz_diagnosis";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select sf.*,dsc.level_name sys_check_level_name,acs.sort_name");
		sql.append("	 from "+TABLE_NAME + " (nolock) sf");
		sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
		sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
		sql.append(" where 1 = 1");
		if(CommonFun.isNe(params.get("spbh"))){
			params.put("spbh", "0");
		}
		sql.append(" and spbh = :spbh");
		params.put("sort", "id asc ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result query_mx(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select sf.*,");
		sql.append("case when Formul='>' then '大于'              ");
		sql.append("		 when Formul='<' then '小于'          ");
		sql.append("		  when Formul='<>' then '不等于'      ");
		sql.append("			 when Formul='like' then '类似'   ");
		sql.append("		when Formul='not like' then '不类似'  ");
		sql.append("		 when Formul='=' then '等于'          ");
		sql.append("		end as Formul_name                    ");
		sql.append("	 from "+TABLE_NAME_MX + " (nolock) sf");
		sql.append(" where 1 = 1");
		sql.append(" and  parent_id= :parent_id");
		params.put("sort", "id asc ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params, r);
	}
	
	public int insert_mx(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME_MX, params);
	}
	
	public int update_mx(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME_MX, params, r);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}
	
	public int delete_mx(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME_MX, params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select sf.*,dsc.level_name sys_check_level_name,acs.sort_name");
		sql.append("	 from "+TABLE_NAME + " (nolock) sf");
		sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
		sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
		sql.append(" where 1 = 1");
		setParameter(params, "id", " and id =:id ", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	public Record getShengfangzl_jjz(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * ");
		sql.append(" from "+TABLE_NAME + " (nolock) ");
		sql.append(" where 1 = 1");
		setParameter(params, "id", " and id =:id ", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	public Record get_mx_byId(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,b.spbh ");
		sql.append("	 from "+TABLE_NAME_MX + " (nolock) a ");
		sql.append(" left join  "+TABLE_NAME+" (nolock) b ");
		sql.append(" on a.parent_id = b.id ");
		sql.append(" where 1 = 1");
		setParameter(params, "id", " and a.id =:id ", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	public Result query_mx_all(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select a.*,b.spbh ");
		sql.append("	 from "+TABLE_NAME_MX + " (nolock) a ");
		sql.append(" left join  "+TABLE_NAME+" (nolock) b ");
		sql.append(" on a.parent_id = b.id ");
		sql.append(" where 1 = 1");
		sql.append(" and  parent_id= :parent_id");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record get_mx(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select sf.*, ");
		sql.append("case when Formul='>' then '大于'              ");
		sql.append("		 when Formul='<' then '小于'          ");
		sql.append("		  when Formul='<>' then '不等于'      ");
		sql.append("			 when Formul='like' then '类似'   ");
		sql.append("		when Formul='not like' then '不类似'  ");
		sql.append("		 when Formul='=' then '等于'          ");
		sql.append("		end as Formul_name                    ");
		sql.append("	 from "+TABLE_NAME_MX + " (nolock) sf");
		sql.append(" where 1 = 1");
		setParameter(params, "id", " and id =:id ", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result queryFirstItem(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		/*sql.append(" select diagnosis_code,diagnosis_name ");
		sql.append(" from dict_his_diagnosis (nolock) ");
		sql.append(" where (len(diagnosis_CODE)=5 or SUBSTRING(diagnosis_CODE,6,6) = '+') ");
		if (!CommonFun.isNe(params.get("search"))) {
			params.put("key", "%"+params.get("search")+"%");
			 sql.append(" and diagnosis_CODE in (             ");
			 sql.append(" select left(diagnosis_CODE,5) from  ");
			 sql.append(" dict_his_diagnosis (nolock)         ");
			 sql.append(" where                               ");
			 sql.append(" (INPUT_CODE LIKE :key or      ");
			 sql.append("  diagnosis_CODE LIKE :key or  "); 
			 sql.append("  diagnosis_NAME LIKE :key )   ");
			 sql.append("  )                                  ");
		}*/
		sql.append(" SELECT                                                                 ");
		sql.append(" 	diagnosis_code,                                                     ");
		sql.append(" 	diagnosis_name                                                      ");
		sql.append(" FROM                                                                   ");
		sql.append(" 	dict_his_diagnosis ( nolock )                                       ");
		sql.append(" WHERE                                                                  ");
		sql.append(" 	diagnosis_CODE IN                                                   ");
		sql.append(" 	( SELECT left(diagnosis_CODE,5) FROM dict_his_diagnosis ( nolock )  ");
		sql.append(" 	WHERE   1=1                                                         ");
		if (!CommonFun.isNe(params.get("search"))) {
			params.put("key", "%"+params.get("search")+"%");
			sql.append(" 	and ( INPUT_CODE LIKE :key  OR                                ");
			sql.append(" 	diagnosis_CODE LIKE :key OR                                   ");
			sql.append(" 	diagnosis_NAME LIKE  :key                                     ");
			sql.append(" 	)                                                             ");
		}
		sql.append(" )                                                                      ");
		sql.append(" or                                                                     ");
		sql.append(" diagnosis_CODE IN                                                      ");
		sql.append(" 	( SELECT left(diagnosis_CODE,5)+'+' FROM dict_his_diagnosis ( nolock )  ");
		sql.append(" 	WHERE   1=1                                                            ");
		if (!CommonFun.isNe(params.get("search"))) {
			sql.append(" 	and ( INPUT_CODE LIKE :key  OR                                ");
			sql.append(" 	diagnosis_CODE LIKE :key OR                                   ");
			sql.append(" 	diagnosis_NAME LIKE  :key                                     ");
			sql.append(" 	)                                                             ");
		}
		sql.append(" )                                                                      ");
		params.put("sort", "diagnosis_CODE");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryChild(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select diagnosis_CODE, ");
		sql.append(" diagnosis_NAME,left(diagnosis_CODE, 5) as parent_code");
		sql.append(" from dict_his_diagnosis (nolock) "); 
		sql.append(" where len(REPLACE(diagnosis_CODE, '+', ''))=7 ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryOrther(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select                                  ");
		sql.append(" diagnosis_CODE,diagnosis_NAME           ");
		sql.append(" from dict_his_diagnosis (nolock) where           ");
		sql.append("  (PATINDEX('%.%',diagnosis_CODE)=0      ");
		sql.append("  or  PATINDEX('%*%',diagnosis_CODE)>0  ");
		sql.append("  or  PATINDEX('%.x%',diagnosis_CODE)>0)  ");
		if (!CommonFun.isNe(params.get("search"))) {
			params.put("key", "%"+params.get("search")+"%");
			sql.append(" 	and ( INPUT_CODE LIKE :key  OR                                ");
			sql.append(" 	diagnosis_CODE LIKE :key OR                                   ");
			sql.append(" 	diagnosis_NAME LIKE  :key                                     ");
			sql.append(" 	)                                                             ");
		}
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryDiagnisis(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		if (!CommonFun.isNe(params.get("code"))) {
			sql.append("select diagnosis_CODE,diagnosis_NAME ");
			sql.append("from dict_his_diagnosis (nolock) where diagnosis_CODE<>:code ");
			params.put("fifter", params.get("code")+"%");
			sql.append("AND diagnosis_CODE LIKE :fifter  ");
			if (!CommonFun.isNe(params.get("search"))) {
				params.put("key", "%"+params.get("search")+"%");
				sql.append(" AND (INPUT_CODE LIKE :key or ");
				sql.append(" diagnosis_CODE LIKE :key or ");
				sql.append(" diagnosis_NAME LIKE :key ) ");
			}
			return executeQuery(sql.toString(), params);
		}else {
			return null;
		}
	}
	
	public Result queryJjzZd(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select diagnosis_name,diagnosis_code ");
		sql.append(" from dict_his_diagnosis (nolock) where 1=1 "); 
		if (!CommonFun.isNe(params.get("data"))) {
			params.put("key", "%"+params.get("data")+"%");
			sql.append(" AND (INPUT_CODE LIKE :key or ");
			sql.append(" diagnosis_CODE LIKE :key or ");
			sql.append(" diagnosis_NAME LIKE :key ) ");
		}
		return executeQuery(sql.toString(), params);
	}
	
	public Record getDia(String code) throws Exception {
		StringBuffer sql = new StringBuffer();
		Record params = new Record();
		params.put("code", code);
		sql.append(" select diagnosis_NAME,diagnosis_CODE ");
		sql.append(" from dict_his_diagnosis (nolock) "); 
		sql.append(" where diagnosis_CODE = :code ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result queryLook(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select sf.id,dsc.level_name sys_check_level_name,acs.sort_name,sf.nwarn_message,");
		sql.append(" sf.tiaojian,sf.xmmch,drug.drug_name,drug.drug_unit,'"+params.get("table_name")+"' table_name, ");
		sql.append(" drug.shengccj,drug.druggg,drug.drug_code");
		sql.append("	 from "+TABLE_NAME + " (nolock) sf");
		sql.append(" left join dict_his_drug (nolock) drug on sf.spbh = drug.drug_code ");
		sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
		sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
		sql.append(" where 1 = 1");
		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("key", "%"+params.get("filter")+"%");
			sql.append(" and (sf.spbh= :filter or drug.drug_name like :key ) ");
		}
		params.put("sort", "id asc ");
		return executeQueryLimit(sql.toString(), params);
	}
}
