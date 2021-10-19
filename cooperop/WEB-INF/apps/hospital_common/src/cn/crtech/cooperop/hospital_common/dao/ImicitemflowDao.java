package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;


public class ImicitemflowDao extends BaseDao{
	public static final String TABLE_NAME = "YB_shengfangzl_ITEM_flow";
	public static final String TABLE_NAME_MX = "YB_shengfangzl_ITEM_flow_MX";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT  a.* , wf.display_name , b.ITEM_NAME  ");
		sql.append("FROM   ");
		sql.append("YB_shengfangzl_ITEM_flow  a  ");
		sql.append("left join V_dict_his_clinicitem b on a.ITEM_CODE = b.ITEM_CODE  "); 
		sql.append("and a.INTERFACE_TYPE = b.INTERFACE_TYPE  ");
		sql.append("LEFT JOIN IADSCP..wf_process ( nolock ) wf on a.wf_process_id = wf.id  ");
		sql.append("where 1=1 ");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("filter", "%"+params.get("filter")+"%");
			sql.append("and (b.item_name like :filter or a.interface_type_name like :filter or wf.display_name like :filter  ");
			sql.append(" or a.charge_type_name like :filter or a.dept_name like :filter or a.doctor_name like :filter or a.patient_name like :filter )");
		}
		if(!CommonFun.isNe(params.get("is_to_zf"))){
			sql.append("and is_to_zf = :is_to_zf ");
		}
		params.put("sort", " id desc ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result querymx(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT  ");
		sql.append("	dsc.thirdt_name sys_check_level_name,  ");
		sql.append("	acs.thirdt_name,  ");
		sql.append("CASE  ");
		sql.append("	WHEN b.Formul = '>' THEN  ");
		sql.append("	'大于'   ");
		sql.append("	WHEN b.Formul = '<' THEN  ");
		sql.append("	'小于'   ");
		sql.append("	WHEN b.Formul = '<>' THEN  ");
		sql.append("	'不等于'   ");
		sql.append("	WHEN b.Formul = 'like' THEN  ");
		sql.append("	'类似'   ");
		sql.append("	WHEN b.Formul = 'not like' THEN  ");
		sql.append("	'不类似'   ");
		sql.append("	WHEN b.Formul = '=' THEN  ");
		sql.append("	'等于'   ");
		sql.append("	END AS Formul_name,  ");
		sql.append("CASE  ");
		sql.append("		WHEN b.d_type = '1' THEN  ");
		sql.append("		'住院'  ");
		sql.append("		WHEN b.d_type = '2' THEN  ");
		sql.append("		'门诊'   ");
		sql.append("	END AS d_type_name,  ");
		sql.append("	b.*   ");
		sql.append("FROM  ");
		sql.append(" YB_shengfangzl_ITEM_flow_MX b   ");
		sql.append("	LEFT JOIN map_check_level ( nolock ) dsc ON dsc.thirdt_code = b.sys_check_level  "); 
		sql.append("	AND dsc.product_code = 'hospital_imic'  ");
		sql.append("	LEFT JOIN map_common_regulation ( nolock ) acs ON acs.thirdt_code = b.apa_check_sorts_id  ");
		sql.append("	AND acs.product_code = 'hospital_imic'  ");
		sql.append("WHERE  ");
		sql.append(" parent_id = :parent_id ");
		params.put("sort", " id desc ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public int addflow(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}
	public int updateflow(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params, r);
	}
	public int insertmx(Map<String, Object> params) throws Exception {
		params.remove("id");
		return executeInsert(TABLE_NAME_MX, params);
	}
	public int updatemx(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME_MX, params, r);
	}
	public int deletemx(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME_MX, params);
	}
	public Record getItem(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from YB_shengfangzl_ITEM_flow (nolock) where  ITEM_CODE = :item_code" );
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  a.* , wf.display_name , b.ITEM_NAME , b.item_spec , b.item_units, b.shengccj, b.pizhwh  ");
		sql.append("FROM   ");
		sql.append("YB_shengfangzl_ITEM_flow  a  ");
		sql.append("left join V_dict_his_clinicitem b on a.ITEM_CODE = b.ITEM_CODE  "); 
		sql.append("and a.INTERFACE_TYPE = b.INTERFACE_TYPE  ");
		sql.append("LEFT JOIN IADSCP..wf_process ( nolock ) wf on a.wf_process_id = wf.id  ");
		sql.append(" where  a.id = :id" );
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getmx(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  ");
		sql.append("	dsc.thirdt_name sys_check_level_name, ");
		sql.append("	acs.thirdt_name,  ");
		sql.append("CASE  ");
		sql.append("	WHEN b.Formul = '>' THEN  ");
		sql.append("	'大于'   ");
		sql.append("	WHEN b.Formul = '<' THEN  ");
		sql.append("	'小于'   ");
		sql.append("	WHEN b.Formul = '<>' THEN  ");
		sql.append("	'不等于'   ");
		sql.append("	WHEN b.Formul = 'like' THEN  ");
		sql.append("	'类似'   ");
		sql.append("	WHEN b.Formul = 'not like' THEN  ");
		sql.append("	'不类似'   ");
		sql.append("	WHEN b.Formul = '=' THEN  ");
		sql.append("	'等于'   ");
		sql.append("	END AS Formul_name,  ");
		sql.append("CASE  ");
		sql.append("		WHEN b.d_type = '1' THEN  ");
		sql.append("		'住院'  ");
		sql.append("		WHEN b.d_type = '2' THEN  ");
		sql.append("		'门诊'   ");
		sql.append("	END AS d_type_name,  ");
		sql.append("	b.*   ");
		sql.append("FROM  ");
		sql.append(" YB_shengfangzl_ITEM_flow_MX b   ");
		sql.append("	LEFT JOIN map_check_level ( nolock ) dsc ON dsc.thirdt_code	 = b.sys_check_level  "); 
		sql.append("	AND dsc.product_code = 'hospital_imic'  ");
		sql.append("	LEFT JOIN map_common_regulation ( nolock ) acs ON acs.thirdt_code = b.apa_check_sorts_id  ");
		sql.append("	AND acs.product_code = 'hospital_imic'  ");
		sql.append(" where  b.id = :id" );
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
}
