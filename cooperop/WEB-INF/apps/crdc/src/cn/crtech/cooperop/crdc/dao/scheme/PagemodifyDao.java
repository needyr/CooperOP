package cn.crtech.cooperop.crdc.dao.scheme;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class PagemodifyDao extends BaseDao {
	public void save(Map<String, Object> map) throws Exception {
		this.executeInsert("cr_ym_up_fa", map);
	}
	public Record querySingle(Map<String, Object> map) throws Exception {
		return executeQuerySingleRecord("select * from cr_ym_up_fa where fangalx=:fangalx and fangabh=:fangabh ", map);
	}
	public void update(Map<String, Object> map) throws Exception {
		executeUpdate("update cr_ym_up_fa set  system_product_code=:system_product_code,fangamch=:fangamch,zdysqls=:zdysqls"
				+ ",batch_exe=:batch_exe,is_close=:is_close where fangabh=:fangabh and fangalx=:fangalx", map);
	}
	public Result query(Map<String, Object> map) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select system_product_code,fangalx,fangabh,fangamch,batch_exe,is_close from cr_ym_up_fa f where 1=1 ");
		if(!CommonFun.isNe(map.get("keyword"))){
			map.put("keyword", "%"+map.get("keyword")+"%");
		}
		setParameter(map, "system_product_code", " and f.system_product_code=:system_product_code", sql);
		setParameter(map, "keyword", " and (f.fangabh like :keyword or f.fangalx like :keyword or f.fangamch like :keyword) ", sql);
		setParameter(map, "fangalx", " and f.fangalx=:fangalx", sql);
		return executeQueryLimit(sql.toString(), map);
	}
	public void delete(Map<String, Object> map) throws Exception { 
		executeDelete("cr_ym_up_fa", map);
	}
}
