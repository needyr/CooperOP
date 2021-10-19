package cn.crtech.cooperop.ipc.dao;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;

import java.util.Date;
import java.util.Map;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ProductmanagetService;

public class CheckResultInfoDao extends BaseDao {
	private final static String TABLE_NAME = "check_result_info";//检查结果详情
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();		                                                                                                                   
		 sql.append(" select cri.*,cr.keyword,acs.sort_id,acs.sort_name,acs.interceptor_level, ");
		 sql.append("  acs.info_level from check_result_info(nolock) cri                                   ");
		 sql.append("  left join auto_audit(nolock) aat on aat.id=cri.auto_audit_id                    ");
		 sql.append("  inner join check_result(nolock) cr on cri.parent_id = cr.id                     ");
		 sql.append("  inner join map_common_regulation(nolock) crs on cr.keyword = crs.thirdt_code and cr.type = crs.check_type      ");
		 sql.append(" and crs.product_code = '"+ProductmanagetService.IPC+"'");
		 sql.append("  left join sys_common_regulation(nolock) acs on                                        ");
		 sql.append("      (acs.p_key = crs.sys_p_key                                          ");
		 sql.append(" 		  and  acs.state='1' and                                           ");
		 sql.append("  (                                                                       ");
		 sql.append("  (aat.d_type='1' and acs.hospitalization_check='1') or                        ");
		 sql.append("  (aat.d_type='2' and acs.outpatient_check ='1') or                         ");
		 sql.append("  (aat.d_type='3' and acs.emergency_check='1')                      ");
		 sql.append("  ))                                                                      ");
		 sql.append("  where cri.auto_audit_id = :auto_audit_id                                ");
		 sql.append("  and (acs.interceptor_level is not null and  acs.info_level is not null )");
		 sql.append("  and (acs.interceptor_level <> '99' or  acs.info_level <> '99'  ) ");
		 sql.append("  order by acs.sort_id                                                    ");
		return executeQuery(sql.toString(), params);                                           
	}
	
	public Result queryCheckResultInfo(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();		                                                                                                                   
		sql.append(" select acs.sort_id,acs.sort_name,dsc.level_code as sys_check_level, cr.doc_advice,doc_other_advice, ");
		sql.append(" dsc.level_name as sys_check_level_name,dsc.level_star as star_level,cri.*,cr.keyword                                   ");
		sql.append(" from check_result_info(nolock) cri                                                      ");
		sql.append(" inner join check_result(nolock) cr on cr.id = cri.parent_id                             ");
		sql.append("  inner join map_common_regulation(nolock) crs on cr.keyword = crs.thirdt_code and cr.type = crs.check_type      ");
		sql.append(" and crs.product_code = '"+ProductmanagetService.IPC+"'");
		sql.append("  left join sys_common_regulation(nolock) acs on      acs.p_key = crs.sys_p_key                                  ");
		sql.append(" inner join map_check_level(nolock) dcd on cri.level = dcd.thirdt_code            ");
		sql.append(" and dcd.product_code = '"+ProductmanagetService.IPC+"'");
		sql.append(" inner join sys_check_level(nolock) dsc on dcd.sys_p_key = dsc.p_key ");
		sql.append(" where cri.is_new = 1                                                            ");
		sql.append(" and (cri.check_result_state = 'N'                                               ");
		sql.append(" or cri.check_result_state = 'T' or cri.check_result_state = 'B')                                                ");
		sql.append(" and cri.auto_audit_id = :auto_audit_id                                          ");
		sql.append(" order by cri.check_result_state,acs.sort_id                                                            ");
		return executeQuery(sql.toString(), params);                                           
	}
	
	public Result queryCheckResultInfo_v(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();		                                                                                                                   
		sql.append(" select acs.sort_id,acs.sort_name,dsc.level_code as sys_check_level, cr.doc_advice,doc_other_advice, ");
		sql.append(" dsc.level_name as sys_check_level_name,dsc.level_star as star_level,cri.*,cr.keyword                                   ");
		sql.append(" from v_check_result_info(nolock) cri                                                      ");
		sql.append(" inner join v_check_result(nolock) cr on cr.id = cri.parent_id                             ");
		sql.append("  inner join map_common_regulation(nolock) crs on cr.keyword = crs.thirdt_code and cr.type = crs.check_type      ");
		sql.append(" and crs.product_code = '"+ProductmanagetService.IPC+"'");
		sql.append("  left join sys_common_regulation(nolock) acs on      acs.p_key = crs.sys_p_key                                  ");
		sql.append(" inner join map_check_level(nolock) dcd on cri.level = dcd.thirdt_code            ");
		sql.append(" and dcd.product_code = '"+ProductmanagetService.IPC+"'");
		sql.append(" inner join sys_check_level(nolock) dsc on dcd.sys_p_key = dsc.p_key ");
		sql.append(" where cri.is_new = 1                                                            ");
		sql.append(" and (cri.check_result_state = 'N'                                               ");
		sql.append(" or cri.check_result_state = 'T' or cri.check_result_state = 'B')                                                ");
		sql.append(" and cri.auto_audit_id = :auto_audit_id                                          ");
		sql.append(" order by cri.check_result_state,acs.sort_id                                                            ");
		return executeQuery(sql.toString(), params);                                           
	}
	
	/**
	 * 查询审查结果信息{视图版}
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Result queryCheckResultInfo_view(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();		                                                                                                                   
		sql.append(" select acs.sort_id,acs.sort_name,dsc.level_code as sys_check_level, cr.doc_advice,doc_other_advice, ");
		sql.append(" dsc.level_name as sys_check_level_name,dsc.level_star as star_level,cri.*,cr.keyword                                   ");
		sql.append(" from V_check_result_info(nolock) cri                                                      ");
		sql.append(" inner join V_check_result(nolock) cr on cr.id = cri.parent_id                             ");
		sql.append("  inner join map_common_regulation(nolock) crs on cr.keyword = crs.thirdt_code and cr.type = crs.check_type      ");
		sql.append(" and crs.product_code = '"+ProductmanagetService.IPC+"'");
		sql.append("  left join sys_common_regulation(nolock) acs on      acs.p_key = crs.sys_p_key                                  ");
		sql.append(" inner join map_check_level(nolock) dcd on cri.level = dcd.thirdt_code            ");
		sql.append(" and dcd.product_code = '"+ProductmanagetService.IPC+"'");
		sql.append(" inner join sys_check_level(nolock) dsc on dcd.sys_p_key = dsc.p_key ");
		sql.append(" where cri.is_new = 1                                                            ");
		sql.append(" and (cri.check_result_state = 'N'                                               ");
		sql.append(" or cri.check_result_state = 'T' or cri.check_result_state = 'B')                                                ");
		sql.append(" and cri.auto_audit_id = :auto_audit_id                                          ");
		sql.append(" order by cri.check_result_state,acs.sort_id                                                            ");
		return executeQuery(sql.toString(), params);                                           
	}
	
	public Result queryCheckResultInfoByYwlsb(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();		                                                                                                                   
		sql.append(" select dsc.level_code sys_check_level, cri.doctor_advice doc_other_advice, ");
		sql.append(" dsc.level_name sys_check_level_name,dsc.level_star star_level,cri.*,cri.auto_audit_sort_id keyword,cri.related_drugs_pkey order_id ");
		sql.append(" from YWLSB_AUDIT_ORDERS_check_result(nolock) cri                                                      ");
		sql.append(" inner join map_check_level(nolock) dcd on cri.auto_audit_level = dcd.thirdt_code            ");
		sql.append(" and dcd.product_code = '"+ProductmanagetService.IPC+"'");
		sql.append(" inner join sys_check_level(nolock) dsc on dcd.sys_p_key = dsc.p_key ");
		sql.append(" where   is_new=1                                                        ");
		sql.append(" and (cri.check_result_state = 'N'                                               ");
		sql.append(" or cri.check_result_state = 'T')                                                ");
		sql.append(" and cri.auto_audit_id = :auto_audit_id                                          ");
		sql.append(" order by cri.sort_id                                                            ");
		return executeQuery(sql.toString(), params);                                           
	}
	
	//给新开医嘱问题标记
	public void updateIsNew(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("  update cri set cri.is_new = 1                                                             ");
		sql.append("  from auto_audit_orders aao                                                                ");
		sql.append("  inner join check_result_info(nolock) cri on ( charindex(','+aao.p_key+',',','+cri.order_id+',')>0 ");
		sql.append("  or cri.order_id is null)  and cri.auto_audit_id = :auto_audit_id                                    ");
		sql.append("  where aao.auto_audit_id = :auto_audit_id and (aao.sys_order_status = 0 or cri.order_id is null)         ");
		executeUpdate(sql.toString(), params);
	}
	
	//审查结果标记 
	public void updateResultState(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" update cri set cri.check_result_state = case when (acs.complain_level <= cri.level and aa.d_type='1') or (acs.complain_level_outp <= cri.level and aa.d_type='2') or (acs.complain_level_emergency <= cri.level and aa.d_type='3')  then 'B'   ");
		sql.append(" 		when (acs.interceptor_level <= cri.level and aa.d_type='1') or (acs.interceptor_level_outp <= cri.level and aa.d_type='2') or (acs.interceptor_level_emergency <= cri.level and aa.d_type='3') then 'N'                                   ");
		sql.append(" 		when (acs.info_level <= cri.level and aa.d_type='1') or (acs.info_level_outp <= cri.level and aa.d_type='2') or (acs.info_level_emergency <= cri.level and aa.d_type='3') then 'T' else 'Y' end                                   ");
		sql.append(" 		from check_result_info cri                                                               ");
		sql.append(" 		inner join check_result(nolock) cr                                                               ");
		sql.append(" 		on cri.parent_id = cr.id                                                                 ");
		sql.append("  inner join map_common_regulation(nolock) crs on cr.keyword = crs.thirdt_code and cr.type = crs.check_type      ");
		sql.append(" and crs.product_code = '"+ProductmanagetService.IPC+"'");
		sql.append(" 		inner join sys_common_regulation(nolock) acs                                                           ");
		sql.append(" 		on acs.p_key = crs.sys_p_key                                                            ");
		sql.append(" 		inner join auto_audit(nolock) aa                                                                 ");
		sql.append(" 		on aa.id = cri.auto_audit_id                                                             ");
		sql.append(" 		where                                                                                    ");
		sql.append(" 		cri.auto_audit_id = :auto_audit_id                                                       ");
		sql.append(" 		and (aa.is_after ='1' or (aa.is_after is null and cri.is_new='1'))    ");
		sql.append(" 		and acs.state = '1'                                                                      ");
		sql.append(" 		and (                                                                                    ");
		sql.append(" 		   (aa.d_type = '1' and acs.hospitalization_check = '1')                                   ");
		sql.append(" 		or (aa.d_type = '2' and acs.outpatient_check = '1')                                        ");
		sql.append(" 		or (aa.d_type = '3' and acs.emergency_check = '1') )                                       ");

		/*update cri set cri.is_new = 1 另一种方式 ：效率高
				from auto_audit_orders aao, check_result_info cri
				where cri.auto_audit_id = aao.auto_audit_id and aao.auto_audit_id = 2766 and aao.sys_order_status = 0 
				and ( charindex(','+aao.p_key+',',','+cri.order_id+',')>0 or cri.order_id is null)*/
		executeUpdate(sql.toString(), params);
	}
	
	public Record getsort(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select acs.sort_name,ds.level_star as star_level,ds.level_name as SYS_Check_level_name,ds.level_code as SYS_Check_level, ");
		sql.append(" a.* from check_result_info(nolock) a                                            ");
		sql.append(" left join check_result(nolock) r on r.id=a.parent_id                            ");
		sql.append(" left join map_common_regulation(nolock)  s on r.keyword=s.thirdt_code  and r.type = s.check_type          ");
		sql.append(" and s.product_code = '"+ProductmanagetService.IPC+"'");
		sql.append(" 		inner join sys_common_regulation(nolock) acs on                      ");
		sql.append(" 		acs.p_key = s.sys_p_key                                                            ");
		sql.append(" left join map_check_level(nolock) ch on ch.thirdt_code = a.level           ");
		sql.append(" and ch.product_code = '"+ProductmanagetService.IPC+"'");
		sql.append(" left join sys_check_level(nolock) ds on ds.p_key = ch.sys_p_key ");
		sql.append(" where a.auto_audit_id = :auto_audit_id                                  ");
		sql.append(" and a.id = :check_result_info_id                                  ");                                                                                                                               
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	/**
	 * 视图版
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Record getsort_view(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select acs.sort_name,ds.level_star as star_level,ds.level_name as SYS_Check_level_name,ds.level_code as SYS_Check_level, ");
		sql.append(" a.* from v_check_result_info(nolock) a                                            ");
		sql.append(" left join v_check_result(nolock) r on r.id=a.parent_id                            ");
		sql.append(" left join map_common_regulation(nolock)  s on r.keyword=s.thirdt_code  and r.type = s.check_type          ");
		sql.append(" and s.product_code = '"+ProductmanagetService.IPC+"'");
		sql.append(" 		inner join sys_common_regulation(nolock) acs on                      ");
		sql.append(" 		acs.p_key = s.sys_p_key                                                            ");
		sql.append(" left join map_check_level(nolock) ch on ch.thirdt_code = a.level           ");
		sql.append(" and ch.product_code = '"+ProductmanagetService.IPC+"'");
		sql.append(" left join sys_check_level(nolock) ds on ds.p_key = ch.sys_p_key ");
		sql.append(" where a.auto_audit_id = :auto_audit_id                                  ");
		sql.append(" and a.id = :check_result_info_id                                  ");                                                                                                                               
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result queryMaxLevelForType(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select max(level) maxlevel,cr.keyword,aao.sys_order_status,cr.type from check_result_info(nolock) info   ");
		sql.append(" inner join check_result(nolock) cr on cr.id =info.parent_id and cr.auto_audit_id=:auto_audit_id                                   ");
		sql.append(" inner join auto_audit_orders(nolock) aao on info.order_id+',' like '%'+aao.P_KEY+',%'  and aao.auto_audit_id=:auto_audit_id                        ");
		sql.append(" 		inner join auto_audit(nolock) aa                                                                 ");
		sql.append(" 		on aa.id = info.auto_audit_id                                                             ");
		sql.append(" where   1=1             ");
		sql.append(" 		and (aa.is_after ='1' or (aa.is_after is null and info.is_new='1'))    ");
		sql.append("  and info.auto_audit_id=:auto_audit_id                 ");
		sql.append(" group by cr.keyword,aao.sys_order_status,cr.type                                                 ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryYXK(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		params.put("auto_audit_id", params.get("id"));
		//sql.append(" select info.*,acs.sort_id,acs.sort_name,dsc.level_star as star_level,dsc.level_name as SYS_Check_level_name from check_result_info (nolock) info ");
		sql.append(" select ");
		sql.append("info.id,                           ");
		sql.append("info.level,                        ");
		sql.append("info.description,                  ");
		sql.append("info.reference,                    ");
		sql.append("info.parent_id,                    ");
		sql.append("info.order_id,                     ");
		sql.append("info.auto_audit_id,                ");
		sql.append("info.warning,                      ");
		sql.append("info.create_time,                  ");
		sql.append("info.good_reputation,              ");
		sql.append("info.check_result_state,           ");
		sql.append("info.is_new,                       ");
		sql.append("info.is_shenc_pass,                ");
		sql.append("info.shenc_pass_ren,               ");
		sql.append("info.shenc_pass_time,              ");
		sql.append("info.shenc_pass_gnmch,             ");
		sql.append("info.shenc_pass_pharmacist_advice, ");
		sql.append("info.shenc_change_level,           ");
		sql.append("info.shenc_pass_source,            ");
		sql.append(" acs.sort_id,acs.sort_name,dsc.level_star as star_level,dsc.level_name as SYS_Check_level_name from check_result_info (nolock) info ");
		sql.append(" inner join check_result(nolock) cr on info.parent_id = cr.id ");
		sql.append("  inner join map_common_regulation(nolock) crs on cr.keyword = crs.thirdt_code and cr.type = crs.check_type      ");
		sql.append(" and crs.product_code = '"+ProductmanagetService.IPC+"'");
		sql.append(" 		inner join sys_common_regulation(nolock) acs                                                           ");
		sql.append(" 		on acs.p_key = crs.sys_p_key                                                            ");
		sql.append(" inner join map_check_level(nolock) dcd on info.level = dcd.thirdt_code ");
		sql.append(" and dcd.product_code = '"+ProductmanagetService.IPC+"'");
		sql.append(" inner join sys_check_level(nolock) dsc  on dsc.p_key = dcd.sys_p_key ");
		sql.append(" where 1=1 ");
		setParameter(params, "auto_audit_id", " and info.auto_audit_id=:auto_audit_id", sql);
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
	
	
	public Result getPassAudit(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		/*sql.append(" select * into #tmp_real_aao from auto_audit_orders                                                  ");
		sql.append(" where auto_audit_id=:auto_audit_id                                              ");
		sql.append(" select d.SYS_Check_level_name,c.sort_id,c.sort_name,                                                ");
		sql.append(" (select '['+stuff((select '['+tmpaao.order_text+'] ' from #tmp_real_aao tmpaao                      ");
		sql.append(" where a.order_id+',' like '%'+tmpaao.p_key+',%' for xml path('')),1,1,''))                          ");
		sql.append(" as Related_drugs_show,                                                                              ");
		sql.append(" (select stuff((select ',['+tmpaao.order_code+'] ' from #tmp_real_aao tmpaao                         ");
		sql.append(" where a.order_id+',' like '%'+tmpaao.p_key+',%' for xml path('')),1,1,''))                          ");
		sql.append(" as drug_p_key_1,                                                                                    ");
		sql.append(" (select stuff((select ',['+tmpaao.order_code+' '+tmpaao.ADMINISTRATION+' '+tmpaao.FREQUENCY+'] '    ");
		sql.append(" from #tmp_real_aao tmpaao                                                                           ");
		sql.append(" where a.order_id+',' like '%'+tmpaao.p_key+',%' for xml path('')),1,1,''))                          ");
		sql.append(" as drug_p_key,                                                                                    ");
		sql.append(" a.*                                                                                                 ");
		sql.append(" from check_result_info a                                                                            ");
		sql.append(" left join check_result b                                                                            ");
		sql.append(" on a.auto_audit_id = b.auto_audit_id and a.parent_id=b.id                                           ");
		sql.append(" left join check_result_sort c                                                                       ");
		sql.append(" on b.type = c.check_type and b.keyword=c.type                                                       ");
		sql.append(" left join DICT_SYS_CHECKLEVEL d                                                                     ");
		sql.append(" on a.shenc_change_level=d.SYS_Check_level                                                           ");
		sql.append(" where a.id = :id                                                     ");
		sql.append(" drop table #tmp_real_aao                                                                            ");*/
		sql.append(" SELECT                                                                       ");
		sql.append(" 	(select level_name from sys_check_level                     ");
		sql.append(" 	where level_code = :shenc_change_level and product_code = '"+ProductmanagetService.IPC+"') SYS_Check_level_name,           ");
		sql.append(" 	a.auto_audit_sort_id sort_id,                                             ");
		sql.append(" 	a.sort_name,                                                              ");
		sql.append("   a.Related_drugs_show,                                                      ");
		sql.append("   a.ORDERMESSAGE AS drug_p_key,                                              ");
		sql.append(" 	a.*                                                                       ");
		sql.append(" FROM                                                                         ");
		sql.append(" 	YWLSB_AUDIT_ORDERS_check_result a  (nolock)                                       ");
		sql.append(" WHERE                                                                        ");
		sql.append(" 	a.check_result_info_id = :id                                              ");
		sql.append("   and a.auto_audit_id = :auto_audit_id                                       ");
		return executeQuery(sql.toString(), params);
	}
	
	
	/**
	 * @param params auto_audit_id
	 * @return
	 * @throws Exception
	 * @function 
	 * @author yankangkang 2019年1月20日
	 */
	public Result queryByAutoID(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();		                                                                                                                   
		 sql.append(" select check_result_state from "+TABLE_NAME+"(nolock) where auto_audit_id = :auto_audit_id and is_new = '1' ");
		return executeQuery(sql.toString(), params);                                           
	}
}
