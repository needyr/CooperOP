package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ProductmanagetService;

public class SfSyzDao extends BaseDao{

	private final static String TABLE_NAME = "shengfangzl_syz";
	private final static String TABLE_NAME_MX = "shengfangzl_syz_diagnosis";
	private final static String HOSPITAL_DIAGNOSIS_USE_DRUG = "hospital_diagnosis_use_drug";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select sf.*,dsc.level_name sys_check_level_name,acs.sort_name");
		sql.append("	 from "+TABLE_NAME + " (nolock) sf");
		sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"'");
		sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id");
		sql.append(" where 1 = 1");
		if(CommonFun.isNe(params.get("spbh"))){
			params.put("spbh", "0");
		}
		sql.append(" and spbh = :spbh");
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
	
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select sf.*,dsc.level_name sys_check_level_name,acs.sort_name");
		sql.append("	 from "+TABLE_NAME + " (nolock) sf");
		sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"'");
		sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id");
		sql.append(" where 1 = 1");
		setParameter(params, "id", " and id =:id ", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result queryLook(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select sf.id,dsc.level_name sys_check_level_name,acs.sort_name,sf.nwarn_message,");
		sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+params.get("table_name")+"' table_name, ");
		sql.append(" drug.shengccj,drug.druggg,drug.drug_code,drug.zdy_cz, ");
		sql.append("case when Formul='>' then '大于'              ");
		sql.append("		 when Formul='<' then '小于'          ");
		sql.append("		  when Formul='<>' then '不等于'      ");
		sql.append("			 when Formul='like' then '类似'   ");
		sql.append("		when Formul='not like' then '不类似'  ");
		sql.append("		 when Formul='=' then '等于'          ");
		sql.append("		end as Formul_name                    ");
		sql.append("	 from "+TABLE_NAME + " (nolock) sf");
		sql.append(" left join dict_his_drug (nolock) drug on sf.spbh = drug.drug_code ");
		sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
		sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
		sql.append(" where 1 = 1");
		if (!CommonFun.isNe(params.get("filter"))) {
			params.put("key", "%"+params.get("filter")+"%");
			sql.append(" and (sf.spbh= :filter or drug.drug_name like :key ) ");
		}
		if (!CommonFun.isNe(params.get("patient"))) {
			params.put("patient", "%"+params.get("patient")+"%");
			sql.append(" and drug.zdy_cz like :patient ");
		}
		if (!CommonFun.isNe(params.get("mintime"))) {
			sql.append(" and substring(zdy_cz,0,20) >= :mintime ");
		}
		if (!CommonFun.isNe(params.get("maxtime"))) {
			sql.append(" and substring(zdy_cz,0,20) <= :maxtime ");
		}
		params.put("sort", "id asc ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record getSingle(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * ");
		sql.append(" from "+TABLE_NAME + " (nolock)");
		sql.append(" where 1 = 1");
		setParameter(params, "id", " and id =:id ", sql);
		return executeQuerySingleRecord(sql.toString(), params);
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
	
	public int delete_mx(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME_MX, params);
	}
	
	public int insert_mx(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME_MX, params);
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
	
	public Result query_mx_byParentId(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,b.spbh ");
		sql.append("	 from "+TABLE_NAME_MX + " (nolock) a ");
		sql.append(" left join  "+TABLE_NAME+" (nolock) b ");
		sql.append(" on a.parent_id = b.id ");
		sql.append(" where 1 = 1");
		setParameter(params, "id", " and a.parent_id =:parent_id ", sql);
		return executeQuery(sql.toString(), params);
	}
	
	public int update_mx(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME_MX, params, r);
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
	
	public Result batchQuery(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("{ call "+HOSPITAL_DIAGNOSIS_USE_DRUG+" (:drug_code,:start_time,:end_time,:is_n,:is_zdy)}");
		Record ins = new Record();
		ins.put("drug_code", params.get("drug_code"));
		ins.put("start_time", params.get("start_time"));
		ins.put("end_time", params.get("end_time"));
		if(CommonFun.isNe(params.get("is_n"))) {
			params.put("is_n", 0);
		}
		if(CommonFun.isNe(params.get("is_zdy"))) {
			params.put("is_zdy", 0);
		}
		ins.put("is_n", params.get("is_n"));
		ins.put("is_zdy", params.get("is_zdy"));
		return executeCallQuery(sql.toString(), ins);
	}
	
	public int batchInsert(Map<String, Object> params) throws Exception {
		//params.remove("spbh");
		return executeInsert(TABLE_NAME_MX, params);
	}
}
