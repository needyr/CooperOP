package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ProductmanagetService;

public class SfYonglChildDao extends BaseDao{

	private final static String TABLE_NAME = "shengfangzl_yongl_child";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select sf.*,dsc.level_name sys_check_level_name,acs.sort_name,");
		sql.append("case when Formul='>' then '大于'              ");
		sql.append("		 when Formul='<' then '小于'          ");
		sql.append("		  when Formul='<>' then '不等于'      ");
		sql.append("			 when Formul='like' then '类似'   ");
		sql.append("		when Formul='not like' then '不类似'  ");
		sql.append("		 when Formul='=' then '等于'          ");
		sql.append("		end as Formul_name                    ");
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
	
	public Result shengfangzl_xm(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();   
		sql.append("select xmid,xmbh,xmmch,fdname               ");
		sql.append("from shengfangzl_xm where xmlx = '儿童浓度问题' ");
		/*if(!CommonFun.isNe(params.get("filter"))) {
			sql.append(" and (xmmch like '%"+params.get("filter")+"%' or fdname like '%"+params.get("filter")+"%') ");
		}*/
		if(!CommonFun.isNe(params.get("xmbh"))) {
			sql.append(" and xmbh= :xmbh ");
		}
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
		sql.append("select sf.*,dsc.level_name sys_check_level_name,acs.sort_name,");
		sql.append("case when Formul='>' then '大于'              ");
		sql.append("		 when Formul='<' then '小于'          ");
		sql.append("		  when Formul='<>' then '不等于'      ");
		sql.append("			 when Formul='like' then '类似'   ");
		sql.append("		when Formul='not like' then '不类似'  ");
		sql.append("		 when Formul='=' then '等于'          ");
		sql.append("		end as Formul_name                    ");
		sql.append("	 from "+TABLE_NAME + " (nolock) sf");
		sql.append(" left join sys_check_level (nolock) dsc on dsc.level_code=sf.sys_check_level and dsc.product_code='"+ProductmanagetService.IPC+"' ");
		sql.append(" left join sys_common_regulation (nolock) acs on acs.sort_id=sf.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IPC+"' ");
		sql.append(" where 1 = 1");
		setParameter(params, "id", " and id =:id ", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result queryLook(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select sf.id,dsc.level_name sys_check_level_name,acs.sort_name,sf.nwarn_message,");
		sql.append(" sf.tiaojian,sf.xmmch,sf.value,drug.drug_name,drug.drug_unit,'"+params.get("table_name")+"' table_name, ");
		sql.append(" drug.shengccj,drug.druggg,drug.drug_code, ");
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
}
