package cn.crtech.cooperop.ipc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class BadpostDao extends BaseDao{
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select b.DRUG_name,e.level_name as sys_level_name,f.name as doctor_name,c.dept_name ,a.* from YWLSB_AUDIT_ORDERS_check_result a "); 
		sql.append("inner join dict_his_drug b on b.DRUG_CODE = a.ORDER_CODE ");
		sql.append("inner join YWLSB_AUTO_AUDIT c on a.auto_audit_id = c.id and c.is_after is null "); 
		sql.append("left join system_user_cooperop f on f.no=c.doctor_no  "); 
		sql.append("LEFT JOIN map_check_level d ON a.auto_audit_level = d.thirdt_code "); 
		sql.append("AND d.product_code = 'ipc' ");
		sql.append("LEFT JOIN sys_check_level e ON d.sys_p_key = e.p_key ");
		sql.append("where a.good_reputation = 2 ");
		
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
			sql.append(" and (b.DRUG_name like :key )");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
}
