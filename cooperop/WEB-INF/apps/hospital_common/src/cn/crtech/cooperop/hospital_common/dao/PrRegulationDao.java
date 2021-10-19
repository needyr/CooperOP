package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class PrRegulationDao extends BaseDao {
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select  a.id,                                 "); 
		sql.append("b.thirdt_name,                                "); 
		sql.append("a.comment_result,                             "); 
		/*sql.append("stuff((select ','+comment_name                "); 
		sql.append("from dict_sys_comment(nolock)                 "); 
		sql.append("where charindex(system_code,                  "); 
		sql.append("a.comment_content_code)>0                     "); 
		sql.append("group by comment_name                         "); 
		sql.append("for xml path('')),1,1,'')                     "); 
		sql.append("as comment_content                            ");*/ 
		sql.append("a.comment_content                             ");
		sql.append("from pr_map_common_regulation (nolock) a      "); 
		sql.append("left join map_common_regulation (nolock) b on "); 
		sql.append("a.thirdt_code=b.thirdt_code                   "); 
		sql.append("and a.check_type=b.check_type                 "); 
		sql.append("and a.product_code=b.product_code             "); 
		sql.append("where 1=1                                     "); 
		if(!CommonFun.isNe(params.get("filter"))) {
			params.put("filter", "%" + params.get("filter") + "%");
			sql.append("and b.thirdt_name like :filter "); 
		}
		return executeQueryLimit(sql.toString(), params);
	}

	public void delete(Map<String, Object> params) throws Exception  {
		executeDelete("pr_map_common_regulation", params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select                                        ");
		sql.append("a.id,                                         ");
		sql.append("b.thirdt_name,                                ");
		sql.append("a.comment_result,                             ");
		sql.append("a.thirdt_code,                                ");
		sql.append("a.product_code,                               ");
		sql.append("a.check_type,                                 ");
		sql.append("b.p_key type_p_key,                           ");
		sql.append("a.comment_content_code,                       ");
		/*sql.append("stuff((select ','+comment_name                "); 
		sql.append("from dict_sys_comment(nolock)                 "); 
		sql.append("where charindex(system_code,                  "); 
		sql.append("a.comment_content_code)>0                     "); 
		sql.append("group by comment_name                         "); 
		sql.append("for xml path('')),1,1,'')                     "); 
		sql.append("as comment_content                            "); */
		sql.append("a.comment_content                             ");
		sql.append("from pr_map_common_regulation (nolock) a      "); 
		sql.append("left join map_common_regulation (nolock) b on "); 
		sql.append("a.thirdt_code=b.thirdt_code                   "); 
		sql.append("and a.check_type=b.check_type                 "); 
		sql.append("and a.product_code=b.product_code             "); 
		sql.append("where a.id=:id                                "); 
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public void update(Map<String, Object> params) throws Exception  {
		Record r = new Record();
		r.put("id", params.remove("id"));
		executeUpdate("pr_map_common_regulation", params, r);
	}
	
	public void insert(Map<String, Object> params) throws Exception  {
		executeInsert("pr_map_common_regulation", params);
	}

	public Result queryAuditType(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from map_common_regulation(nolock) where 1=1 ");
		if(!CommonFun.isNe(params.get("filter"))) {
			params.put("filter2", params.get("filter"));
			params.put("filter", "%" + params.get("filter") + "%");
			sql.append("and (thirdt_name like :filter or cast(p_key as varchar) = :filter2)"); 
		}
		return executeQueryLimit(sql.toString(), params);
	}

	public Record getQuestion(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from map_common_regulation(nolock) where p_key = :type_p_key ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
