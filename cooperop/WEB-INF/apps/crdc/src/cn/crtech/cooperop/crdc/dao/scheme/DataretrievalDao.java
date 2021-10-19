package cn.crtech.cooperop.crdc.dao.scheme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DataretrievalDao extends BaseDao {
	public List queryHzfa_hzcode() throws Exception {
		Result rs = this.executeQuery("select hzcode from hzfa");
		return null;
	}

	public void save(Map<String, Object> map) throws Exception {
		this.executeInsert("cr_zl_sel_fa", map);
	}

	public Record querySingle(Map<String, Object> map) throws Exception {
		return executeQuerySingleRecord("select * from cr_zl_sel_fa where fangalx=:fangalx and fangabh=:fangabh ", map);
	}

	public void update(Map<String, Object> map) throws Exception {
		executeUpdate(
				"update cr_zl_sel_fa set system_product_code=:system_product_code, fangamch=:fangamch,dialog_cap=:dialog_cap,dialog_hei=:dialog_hei,dialog_wid=:dialog_wid,focusto=:focusto,tree_visib=:tree_visib,hzcode=:hzcode,tree_cap=:tree_cap,multisel=:multisel,retu_one=:retu_one,foneretu=:foneretu,zyfilter_beactive=:zyfilter_beactive,isrefresh=:isrefresh,filterflds=:filterflds,searchtype=:searchtype,undispflds=:undispflds,displyflds=:displyflds,editflds=:editflds,subgrid=:subgrid,unsubdisflds=:unsubdisflds,subdisflds=:subdisflds,islink=:islink,zdysqls=:zdysqls,zyfilter_zxsql=:zyfilter_zxsql,zyfilter_fxsql=:zyfilter_fxsql,subdatasql=:subdatasql "
				+ " ,app_fields=:app_fields where fangabh=:fangabh and fangalx=:fangalx",
				map);
	}

	public Result query(Map<String, Object> map) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select system_product_code,fangalx,fangabh,fangamch from cr_zl_sel_fa f where 1=1 ");
		if(!CommonFun.isNe(map.get("keyword"))){
			map.put("keyword", "%"+map.get("keyword")+"%");
		}
		setParameter(map, "system_product_code", " and f.system_product_code=:system_product_code", sql);
		setParameter(map, "keyword", " and (f.fangabh like :keyword or f.fangalx like :keyword or f.fangamch like :keyword) ", sql);
		setParameter(map, "fangalx", " and f.fangalx=:fangalx", sql);
		return executeQueryLimit(sql.toString(), map);
	}

	public void delete(Map<String, Object> map) throws Exception {
		System.out.println(map.get("fangalx") + "--" + map.get("fangabh"));
		executeDelete("cr_zl_sel_fa", map);
	}
}
