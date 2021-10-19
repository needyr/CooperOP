package cn.crtech.cooperop.ipc.dao;


import java.util.Date;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ProductmanagetService;

public class CheckResultDao extends BaseDao {
	private final static String TABLE_NAME = "check_result";//检查结果
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		/*sql.append(" select s.sort_id as sort,s.sort_name,COUNT(1) as child_num from check_result_info(nolock) info "); 
		sql.append(" inner join check_result r on r.id=info.parent_id                                               ");
		sql.append(" inner join check_result_sort s on r.keyword=s.type                                             ");
		sql.append(" inner join auto_audit_orders ads on info.order_id+',' like '%'+ads.P_KEY+',%'                  ");
		sql.append(" and ads.ORDER_STATUS = 0 and ads.auto_audit_id=:auto_audit_id where 1=1                                  ");
		sql.append(" and info.auto_audit_id=:auto_audit_id                                                                   ");
		sql.append(" group by s.sort_id,s.sort_name                                                                 ");
		sql.append(" order by s.sort_id                                                                             ");*/
		    sql.append(" select sort,sort_name,count(1) num from (select info.id,acs.sort_id as sort,acs.sort_name, "); 
			sql.append(" info.level,acs.interceptor_level,acs.info_level ");
			sql.append(" from check_result_info(nolock) info   ");
			sql.append(" left join auto_audit(nolock) aat on aat.id=info.auto_audit_id ");
			sql.append(" inner join check_result(nolock) r on r.id=info.parent_id  ");
			sql.append(" inner join map_common_regulation(nolock) s on r.keyword=s.thirdt_code  and r.type = s.check_type              ");
			sql.append(" and s.product_code = '"+ProductmanagetService.IPC+"'");
			sql.append(" inner join auto_audit_orders(nolock) ads on ");
			sql.append(" (info.order_id+',' like '%'+ads.P_KEY+',%'  and ads.sys_order_status = 0 and ads.auto_audit_id=:auto_audit_id) ");
			sql.append(" or info.order_id is null ");
			sql.append("  left join sys_common_regulation(nolock) acs on  ");
			sql.append(" 	     (acs.p_key = s.sys_p_key  ");
			sql.append(" 			  and  acs.state='1' and  ");
			sql.append(" 	 ( ");
			sql.append(" 	 (aat.d_type='1' and acs.hospitalization_check='1') or  ");
			sql.append(" 	 (aat.d_type='2' and acs.outpatient_check = '1') or   ");
			sql.append(" 	 (aat.d_type='3' and acs.emergency_check = '1') ");
			sql.append(" 	 ))            ");
			sql.append(" where 1=1  ");
			sql.append(" and info.auto_audit_id=:auto_audit_id ");
			sql.append(" and (acs.interceptor_level is not null and  acs.info_level is not null ) ");
			sql.append(" 		and (info.level >= acs.interceptor_level or info.level >= acs.info_level  ) ");
			sql.append(" group by info.id, s.sort_id,s.sort_name,info.level,acs.interceptor_level,acs.info_level ");
			sql.append(" ) cu ");
			sql.append(" group by sort,sort_name ");
			sql.append(" order by sort ");
		/*sql.asql.append("ppend(" select s.sort_id as sort,s.sort_name,COUNT(1) as child_num from check_result r     ");
		sql.append(" inner join check_result_info i on i.parent_id=r.id                      ");
		sql.append(" inner join check_result_sort s on s.type=r.keyword                      ");
		sql.append(" where 1=1                                              ");
		setParameter(params, "auto_audit_id", " and r.auto_audit_id=:auto_audit_id", sql);
		sql.append(" GROUP BY s.sort_id,s.sort_name                                             ");
		sql.append("order by s.sort_id");*/
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryTypeNum(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select acs.sort_id,acs.sort_name,count(0) num,crs.product_code,min(cri.check_result_state) check_result_state from check_result_info(nolock) cri ");
		sql.append(" inner join check_result(nolock) cr on cr.id = cri.parent_id                      ");
		sql.append(" inner join map_common_regulation(nolock) crs on cr.keyword = crs.thirdt_code and cr.type = crs.check_type                  ");
		sql.append("  left join sys_common_regulation(nolock) acs on      acs.p_key = crs.sys_p_key                                  ");
		sql.append(" where cri.is_new = 1                                                     ");
		sql.append(" and (cri.check_result_state = 'N'                                        ");
		sql.append(" or cri.check_result_state = 'T' or cri.check_result_state = 'B')                                         ");
		sql.append(" and cri.auto_audit_id = :auto_audit_id                                   ");
		sql.append(" group by acs.sort_id,acs.sort_name,crs.product_code                     ");
		sql.append(" order by acs.sort_id                                                     ");
		return executeQuery(sql.toString(), params);                                          
	}
	
	public Result queryTypeNum_v(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select acs.sort_id,acs.sort_name,count(0) num,crs.product_code,min(cri.check_result_state) check_result_state from v_check_result_info(nolock) cri ");
		sql.append(" inner join v_check_result(nolock) cr on cr.id = cri.parent_id                      ");
		sql.append(" inner join map_common_regulation(nolock) crs on cr.keyword = crs.thirdt_code and cr.type = crs.check_type                  ");
		sql.append("  left join sys_common_regulation(nolock) acs on      acs.p_key = crs.sys_p_key                                  ");
		sql.append(" where cri.is_new = 1                                                     ");
		sql.append(" and (cri.check_result_state = 'N'                                        ");
		sql.append(" or cri.check_result_state = 'T' or cri.check_result_state = 'B')                                         ");
		sql.append(" and cri.auto_audit_id = :auto_audit_id                                   ");
		sql.append(" group by acs.sort_id,acs.sort_name,crs.product_code                     ");
		sql.append(" order by acs.sort_id                                                     ");
		return executeQuery(sql.toString(), params);                                          
	}
	
	/**
	 * 查询问题类型数量、类型{视图版}
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Result queryTypeNum_view(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select acs.sort_id,acs.sort_name,count(0) num,crs.product_code,min(cri.check_result_state) check_result_state from V_check_result_info(nolock) cri ");
		sql.append(" inner join V_check_result(nolock) cr on cr.id = cri.parent_id                      ");
		sql.append(" inner join map_common_regulation(nolock) crs on cr.keyword = crs.thirdt_code and cr.type = crs.check_type                  ");
		sql.append("  left join sys_common_regulation(nolock) acs on      acs.p_key = crs.sys_p_key                                  ");
		sql.append(" where cri.is_new = 1                                                     ");
		sql.append(" and (cri.check_result_state = 'N'                                        ");
		sql.append(" or cri.check_result_state = 'T' or cri.check_result_state = 'B')                                         ");
		sql.append(" and cri.auto_audit_id = :auto_audit_id                                   ");
		sql.append(" group by acs.sort_id,acs.sort_name,crs.product_code                     ");
		sql.append(" order by acs.sort_id                                                     ");
		return executeQuery(sql.toString(), params);                                          
	}
	
	public Result queryTypeNumByYwlsb(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select cri.sort_id sort,cri.sort_name,count(0) num from YWLSB_AUDIT_ORDERS_check_result(nolock) cri ");
		sql.append(" where is_new=1                                                   ");
		sql.append(" and (cri.check_result_state = 'N'                                        ");
		sql.append(" or cri.check_result_state = 'T')                                         ");
		sql.append(" and cri.auto_audit_id = :auto_audit_id                                   ");
		sql.append(" group by cri.sort_id,cri.sort_name                                       ");
		sql.append(" order by cri.sort_id                                                     ");
		return executeQuery(sql.toString(), params);                                          
	}
	
	public Result queryForYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append("c.id,             ");
		sql.append("c.keyword,        ");
		sql.append("c.keytitle,       ");
		sql.append("c.type,           ");
		sql.append("c.auto_audit_id,  ");
		sql.append("c.create_time,    ");
		sql.append("c.doc_advice_id,  ");
		sql.append("c.doc_advice,     ");
		sql.append("c.doc_other_advice");
		sql.append(" from "+TABLE_NAME+"(nolock) c ");
		sql.append(" where 1=1  ");
		setParameter(params, "auto_audit_id", " and c.auto_audit_id=:auto_audit_id", sql);
		return executeQuery(sql.toString(), params);
	}
	
	public Record get(String id) throws Exception {
		Record params = new Record();
		params.put("id", id);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from "+TABLE_NAME+"(nolock) where id = :id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public String insert(Map<String, Object> params) throws Exception {
		params.remove("id");
		String id = CommonFun.getITEMID();
		params.put("id", id);
		params.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		executeInsert(TABLE_NAME, params);
		return id;
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params,r);
	}
	/*public int delete(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.get("id"));
		return executeDelete(TABLE_NAME, r);
	}*/
	public void updateCheckResult(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" update t ");
		sql.append(" SET t.doc_advice_id =:doc_advice_id,");
		sql.append(" t.doc_advice =:doc_advice,");
		sql.append(" t.doc_other_advice =:doc_other_advice");
		sql.append(" from "+TABLE_NAME+ " t inner join map_common_regulation(nolock)  s on t.keyword=s.thirdt_code and t.type = s.check_type   ");
		sql.append(" and s.product_code = '"+ProductmanagetService.IPC+"'");
		sql.append(" inner join sys_common_regulation(nolock) acs on acs.p_key = s.sys_p_key ");
		sql.append(" where t.auto_audit_id =:auto_audit_id and acs.sort_id=:sort_id");
		execute(sql.toString(), params);
	}
	
	//规则拦截、提示
	public Result guizelj(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select crs.thirdt_code as type, acs.interceptor_level lanjie,acs.info_level tishi,crs.check_type from ");
		sql.append(" sys_common_regulation(nolock) acs ");
		sql.append(" left join  map_common_regulation(nolock) crs on acs.p_key = crs.sys_p_key where acs.state='1'");
		sql.append(" and ((:d_type='1' and hospitalization_check='1') or  (:d_type='2' and  outpatient_check='1') or  (:d_type='3' and emergency_check='1'))");
		sql.append(" and acs.product_code = '"+ProductmanagetService.IPC+"'");
		//sql.append(" and crs.type in (:strtype)");
		return executeQuery(sql.toString(), params);
	}
}
