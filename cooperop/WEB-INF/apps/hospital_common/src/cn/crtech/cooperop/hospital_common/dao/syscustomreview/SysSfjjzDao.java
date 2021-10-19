package cn.crtech.cooperop.hospital_common.dao.syscustomreview;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ProductmanagetService;

public class SysSfjjzDao extends BaseDao{

	private final static String TABLE_NAME = "sys_shengfangzl_jjz";
	private final static String TABLE_NAME_MX = "sys_shengfangzl_jjz_diagnosis";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select sf.*,dsc.level_name sys_check_level_name,acs.sort_name");
		sql.append("	 from "+TABLE_NAME + " (nolock) sf");
		sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
		sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
		sql.append(" where 1 = 1");
		if(CommonFun.isNe(params.get("drug_code"))){
			params.put("drug_code", "0");
		}
		sql.append(" and SYS_P_KEY = :drug_code");
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
	
}
