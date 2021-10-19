package cn.crtech.cooperop.crdc.dao.scheme;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DistillDao extends BaseDao {
	public void save(Map<String, Object> map) throws Exception {
		this.executeInsert("cr_djselefa", map);
	}
	public Record querySingle(Map<String, Object> map) throws Exception {
		return executeQuerySingleRecord("select searchtype,filterflds,system_product_code,fangalx,fangabh,fangamch,jilubaohu,emptylyb,selectmx,isLink,sql_text,m_sql,d_sql,app_fields from cr_djselefa where fangalx=:fangalx and fangabh=:fangabh ", map);
	}
	public void update(Map<String, Object> map) throws Exception {
		executeUpdate("update  cr_djselefa set searchtype=:searchtype,system_product_code=:system_product_code,fangamch=:fangamch,jilubaohu=:jilubaohu,emptylyb=:emptylyb,selectmx=:selectmx,islink=:islink,sql_text=:sql_text,m_sql=:m_sql,d_sql=:d_sql,filterflds=:filterflds"
				+ ",app_fields=:app_fields where fangabh=:fangabh and fangalx=:fangalx", map);
	}
	public Result query(Map<String, Object> map) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select system_product_code,fangalx,fangabh,fangamch from cr_djselefa f where 1=1 ");
		if(!CommonFun.isNe(map.get("keyword"))){
			map.put("keyword", "%"+map.get("keyword")+"%");
		}
		setParameter(map, "system_product_code", "and f.system_product_code=:system_product_code", sql);
		setParameter(map, "keyword", " and (f.fangabh like :keyword or f.fangalx like :keyword or f.fangamch like :keyword) ", sql);
		setParameter(map, "fangalx", " and f.fangalx=:fangalx", sql);
		return executeQueryLimit(sql.toString(), map);
	}
	public void delete(Map<String, Object> map) throws Exception { 
		executeDelete("cr_djselefa", map);
	}
}
