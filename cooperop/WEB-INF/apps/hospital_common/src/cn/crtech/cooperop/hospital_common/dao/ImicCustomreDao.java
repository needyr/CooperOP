package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;


public class ImicCustomreDao extends BaseDao{

	
	public Result queryzdysc(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select                                                                    ");
		sql.append(" b.class_name,                                                             ");
		sql.append(" a.item_code, a.item_name, a.item_spec,                                    ");
		sql.append(" a.units, a.price, a.start_date, a.stop_date                               ");
		sql.append(" from dict_his_pricelist a(nolock)                                         ");
		sql.append(" left join dict_his_billitemclass b(nolock) on a.item_class=b.class_code   ");
		sql.append("  where a.item_code in ( ");
		if(!CommonFun.isNe(params.get("hasset"))) {
			if(params.get("hasset") instanceof java.lang.String) {
				sql.append(" select item_code from "+ params.get("hasset") +" (nolock) ");
			}else {
				String[] tabs= (String[]) params.get("hasset");
				for (int i = 0; i < tabs.length; i++) {
					if(i == 0) {
						sql.append(" select item_code from " + tabs[i] + " (nolock)");
					}else {
						sql.append(" union select item_code from " + tabs[i] + " (nolock)");
					}
				}
			}
		}else {
			return null;
		}
		sql.append(" ) ");
		if(CommonFun.isNe(params.get("sort"))) {
			params.put("sort", "a.item_code");
		}
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
		}
		setParameter(params, "key", " and (a.item_name like :key or b.class_name like :key)", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	
	
	public Result querynotimicsc(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		/*sql.append("select a.* from V_dict_his_clinicitem (nolock) a where 1=1 ");
		sql.append("and a.ITEM_CODE not in ( ");
		sql.append(" select ITEM_CODE from YB_shengfangzl_XIANZ (nolock) where ITEM_CODE is not null union ");
		sql.append(" select ITEM_CODE from YB_shengfangzl_ITEM (nolock) where ITEM_CODE is not null  ");
		sql.append(")");*/
		
		sql.append(" select                                                                   ");
		sql.append(" a.p_key, b.class_name, a.item_code, a.item_name,                         ");
		sql.append(" a.item_spec, a.units, a.price, a.start_date,                             ");
		sql.append(" a.stop_date                                                              ");
		sql.append(" from dict_his_pricelist a(nolock)                                        ");
		sql.append(" left join dict_his_billitemclass b(nolock) on a.item_class=b.class_code  ");
		sql.append(" where 1=1 ");
		sql.append(" and a.item_code not in (select item_code from yb_shengfangzl_xianz ");
		sql.append(" union ");
		sql.append(" select item_code from yb_shengfangzl_item) ");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("filter", "%"+params.get("filter")+"%");
			sql.append("and (a.item_name like :filter or a.item_code like :filter )");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result dictYBType(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select '全部' interface_type, '全部' interface_type_name union ");
		sql.append(" select interface_type, interface_type_name from dict_his_interface order by interface_type desc  ");
		return executeQuery(sql.toString(), params);
	}


	public Record getforinsurvsprice(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select                                                                   ");
		sql.append(" a.p_key, b.class_name, a.item_code, a.item_name,                         ");
		sql.append(" a.item_spec, a.units, a.price, a.start_date,                             ");
		sql.append(" a.stop_date                                                              ");
		sql.append(" from dict_his_pricelist a(nolock)                                        ");
		sql.append(" left join dict_his_billitemclass b(nolock) on a.item_class=b.class_code  ");
		sql.append(" where  a.item_code = :item_code" );
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
}
