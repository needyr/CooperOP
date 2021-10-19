package cn.crtech.cooperop.hospital_common.dao.imiccustomre;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.ProductmanagetService;

public class ViorulDao extends BaseDao{

	public static final String TABLE_NAME = "yb_shengfangzl_reject";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT                                    ");
		sql.append("xz.*,                                     ");
		sql.append("dsc.thirdt_name sys_check_level_name,      ");
		sql.append("acs.thirdt_name                      ");
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
		sql.append("select xz.*,dsc.thirdt_name sys_check_level_name,acs.thirdt_name");
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
