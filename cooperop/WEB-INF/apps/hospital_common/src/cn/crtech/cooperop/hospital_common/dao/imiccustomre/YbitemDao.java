package cn.crtech.cooperop.hospital_common.dao.imiccustomre;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.ProductmanagetService;

public class YbitemDao extends BaseDao{

	public static final String TABLE_NAME = "YB_shengfangzl_ITEM";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT                                    ");
		sql.append("xz.*,                                     ");
		sql.append("dsc.thirdt_name sys_check_level_name,      ");
		sql.append("acs.thirdt_name,                          ");
		sql.append("CASE                                      ");
		sql.append("	WHEN xz.Formul = '>' THEN             ");
		sql.append("	'大于'                                ");
		sql.append("	WHEN xz.Formul = '<' THEN             ");
		sql.append("	'小于'                                ");
		sql.append("	WHEN xz.Formul = '<>' THEN            ");
		sql.append("	'不等于'                              ");
		sql.append("	WHEN xz.Formul = 'like' THEN          ");
		sql.append("	'类似'                                ");
		sql.append("	WHEN xz.Formul = 'not like' THEN      ");
		sql.append("	'不类似'                              ");
		sql.append("	WHEN xz.Formul = '=' THEN             ");
		sql.append("	'等于'                                ");
		sql.append("	END AS Formul_name ,                  ");
		sql.append("CASE	                                  ");
		sql.append("		WHEN xz.d_type = '1' THEN         ");
		sql.append("		'住院'                            ");
		sql.append("		WHEN xz.d_type = '2' THEN         ");
		sql.append("		'门诊'                            ");
		sql.append("	END AS d_type_name,                   ");
		sql.append("CASE                                      ");
		sql.append("		WHEN xz.is_to_zf = '1' THEN       ");
		sql.append("		'是'                              ");
		sql.append("		WHEN xz.is_to_zf = '0' THEN       ");
		sql.append("		'否'                              ");
		sql.append("	END AS is_to_zf_name                  ");
		sql.append("	 from "+TABLE_NAME + " (nolock) xz");
		sql.append("	LEFT JOIN map_check_level ( nolock ) dsc ON dsc.thirdt_code= xz.sys_check_level             ");
		sql.append("	AND dsc.product_code= 'hospital_imic'                                                      ");
		sql.append("	LEFT JOIN map_common_regulation ( nolock ) acs ON acs.thirdt_code= xz.apa_check_sorts_id   ");
		sql.append("	AND acs.product_code= 'hospital_imic'  ");
		sql.append("WHERE                                      ");
		sql.append("	1 = 1                                  ");
		sql.append(" and xz.ITEM_CODE = :item_code");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select xz.*,dsc.thirdt_name sys_check_level_name,acs.thirdt_name,");
		sql.append("case when Formul='>' then '大于'              ");
		sql.append("		 when Formul='<' then '小于'          ");
		sql.append("		  when Formul='<>' then '不等于'      ");
		sql.append("			 when Formul='like' then '类似'   ");
		sql.append("		when Formul='not like' then '不类似'  ");
		sql.append("		 when Formul='=' then '等于'          ");
		sql.append("		end as formul_name                    ");
		sql.append("	 from "+TABLE_NAME + " (nolock) xz");
		sql.append(" left join map_check_level (nolock) dsc on dsc.thirdt_code=xz.sys_check_level and dsc.product_code='"+ProductmanagetService.IMIC+"' ");
		sql.append(" left join map_common_regulation (nolock) acs on acs.thirdt_code=xz.apa_check_sorts_id and acs.product_code='"+ProductmanagetService.IMIC+"' ");
		sql.append(" where 1 = 1");
		setParameter(params, "id", " and id =:id ", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params, r);
	}
}
