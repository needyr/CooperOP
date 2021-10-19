package cn.crtech.cooperop.application.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class TaskDao extends BaseDao {
	
	private static final int FREE_TASK_STATE_HOLD = 0;
	private static final int FREE_TASK_STATE_AUDITING = 1;
	private static final int FREE_TASK_STATE_FINISH = 9;
	private static final int FREE_TASK_STATE_CANCEL = -1;
	
	public Result queryMine(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  t.id as task_id , t.order_id,                                                                          ");
		sql.append("         t.task_name as node ,                                                                                  ");
		sql.append("         t.display_name as node_name ,                                                                          ");
		sql.append("         wo.order_no ,                                                                                          ");
		sql.append("         t.create_time ,                                                                                        ");
		sql.append("         isnull((select  stringvalue                                 ");
		sql.append("             from  dbo.parsejson(wo.variable)                      ");
		sql.append("            where  name = 'subject'       ");
		sql.append("         ), case when wp.name = 'free'                                                                             ");
		sql.append("              then ( select  stringvalue                                                                        ");
		sql.append("                     from    dbo.parsejson(wo.variable)                                                         ");
		sql.append("                     where   name = 'process_name'                                                       ");
		sql.append("                   )                                                                                            ");
		sql.append("              else wp.display_name                                                                      ");
		sql.append("         end) as subject ,                           ");
		sql.append("         wp.display_name process_name ,                                                                         ");
		sql.append("         case when wp.name = 'free'                                                                             ");
		sql.append("              then ( select  stringvalue                                                                        ");
		sql.append("                     from    dbo.parsejson(wo.variable)                                                         ");
		sql.append("                     where   name = 'system_product_code'                                                       ");
		sql.append("                   )                                                                                            ");
		sql.append("              else spp.system_product_code                                                                      ");
		sql.append("         end as system_product_code ,                                                                           ");
		sql.append("         isnull(sp.name, 'CooperOP') as system_product_name ,                                                   ");
		sql.append("         case when wp.name = 'free' then wp.name                                                                ");
		sql.append("              else spp.id                                                                                       ");
		sql.append("         end as system_product_process_id ,                                                                     ");
		sql.append("         case when wp.name = 'free' then ( select    stringvalue                                                ");
		sql.append("                                           from      dbo.parsejson(wo.variable)                                 ");
		sql.append("                                           where     name = 'info_bill'                                         ");
		sql.append("                                         )                                                                      ");
		sql.append("              else spp.info_bill                                                                                ");
		sql.append("         end as info_bill ,                                                                                     ");
		sql.append("         wo.expire_time as order_expire_time ,                                                                  ");
		sql.append("         t.expire_time as task_expire_time ,                                                                  ");
		sql.append("         case                                                                                                  ");
		sql.append("	       when t.expire_time is not null and wo.expire_time is null  then                                     ");
		sql.append("	         datediff(mi, getdate(), convert(datetime, t.expire_time, 120))                                    ");
		sql.append("           when t.expire_time is null and wo.expire_time is not null then                                      ");
		sql.append("	         datediff(mi, getdate(), convert(datetime, wo.expire_time, 120))                                   ");
		sql.append("	       when t.expire_time < wo.expire_time then                                                            ");
		sql.append("	         datediff(mi, getdate(), convert(datetime, t.expire_time, 120))                                    ");
		sql.append("	       else                                                                                                ");
		sql.append("		     99999999                                                                                              ");
		sql.append("         end as expire_mins,                                                                  ");
		sql.append("         case when wp.name = 'free' then ( select    stringvalue                                                ");
		sql.append("                                           from      dbo.parsejson(wo.variable)                                 ");
		sql.append("                                           where     name = 'audit_bill'                                        ");
		sql.append("                                         )                                                                      ");
		sql.append("              else sppn.instance_bill                                                                           ");
		sql.append("         end as node_bill ,                                                                                     ");
		sql.append("         isnull(su.name, wo.creator) as create_user_name ,                                                      ");
//		sql.append("         ( select top 1                                 ");
//		sql.append("                     operator_name                      ");
//		sql.append("           from      query_process_history(wo.order_no)       ");
//		sql.append("         ) as operator_name ,                           ");
		sql.append("         case when isnull(wta.actor_id, '') > '' then                                                           ");
		sql.append("           0                                                                                                    ");
		sql.append("         else                                                                                                   ");
		sql.append("           1                                                                                                    ");
		sql.append("         end as is_cc  ,sppn.wx_auth                                                                                         ");
		sql.append(" from    wf_task(nolock) t                                                                                              ");
		sql.append("         left join wf_order(nolock) wo on wo.id = t.order_id                                                            ");
		//sql.append("         left join wf_cc_order(nolock) wco on wco.order_id = t.order_id and wco.suggestions_id is null and wco.actor_id = :system_user_id              ");
		sql.append("         left join wf_task_actor(nolock) wta on wta.task_id = t.id and wta.actor_id = :system_user_id                                                     ");
		sql.append("         left join wf_process(nolock) wp on wp.id = wo.process_id                                                       ");
		sql.append("         left join system_product_process(nolock) spp on spp.wf_process_id = wp.id                                      ");
		sql.append("         left join system_product_process_node(nolock) sppn on sppn.system_product_code = spp.system_product_code       ");
		sql.append("                                                       and sppn.system_product_process_id = spp.id              ");
		sql.append("                                                       and (sppn.type = 'task'  or sppn.type='countersign' )                                ");
		sql.append("                                                       and t.task_name = sppn.id                                ");
		sql.append("         left join v_system_user(nolock) su on su.id = wo.creator                                                       ");
		sql.append("         left join system_product(nolock) sp on sp.code = case when wp.name = 'free'                                    ");
		sql.append("                                                       then ( select                                            ");
		sql.append("                                                               stringvalue                                      ");
		sql.append("                                                              from                                              ");
		sql.append("                                                               dbo.parsejson(wo.variable)                       ");
		sql.append("                                                              where                                             ");
		sql.append("                                                               name = 'system_product_code'                     ");
		sql.append("                                                            )                                                   ");
		sql.append("                                                       else spp.system_product_code                             ");
		sql.append("                                                  end                                                           ");
		sql.append(" where   wo.id is not  null                                                                                     ");
		sql.append("         and wp.id is not null                                                                                  ");
		sql.append("         and ( wp.name = 'free'                                                                                 ");
		sql.append("               or ( wp.name != 'free'                                                                           ");
		sql.append("                    and spp.id is not null                                                                      ");
		sql.append("                    and sppn.id is not null                                                                     ");
		sql.append("                  )                                                                                             ");
		sql.append("             )                                                                                                  ");
		sql.append("         and (isnull(wta.actor_id, '') > '' )                                  ");
		params.put("system_user_id", user().getId());
		if("oa".equals(params.get("system_product_code"))){
			sql.append(" and (spp.system_product_code=:system_product_code or wp.name='free') "); 
		}else{
			setParameter(params, "system_product_code", " and spp.system_product_code=:system_product_code", sql);
		}
		if("ZYL".equals(params.get("process_id"))){
			sql.append(" and wp.name='free' ");
		}else{
			setParameter(params, "process_id", " and spp.id=:process_id", sql);
		}
		setParameter(params, "creator", " and wo.creator=:creator", sql);
		if(!CommonFun.isNe(params.get("keyword"))){
			params.put("keyword", "%"+params.get("keyword")+"%");
			sql.append("     and upper(isnull((select  stringvalue                                 ");
			sql.append("             from  dbo.parsejson(wo.variable)                      ");
			sql.append("            where  name = 'subject'       ");
			sql.append("         ), case when wp.name = 'free'                                                                             ");
			sql.append("              then ( select  stringvalue                                                                        ");
			sql.append("                     from    dbo.parsejson(wo.variable)                                                         ");
			sql.append("                     where   name = 'process_name'                                                       ");
			sql.append("                   )                                                                                            ");
			sql.append("              else wp.display_name                                                                      ");
			sql.append("         end)) + '|' + upper(wo.order_no) like '%' + upper(:keyword) + '%'                           ");
		}
		if (!CommonFun.isNe(params.get("expire_mins"))) {
			sql.append("         and case                                                                                                  ");
			sql.append("	       when t.expire_time is not null and wo.expire_time is null  then                                     ");
			sql.append("	         datediff(mi, getdate(), convert(datetime, t.expire_time, 120))                                    ");
			sql.append("           when t.expire_time is null and wo.expire_time is not null then                                      ");
			sql.append("	         datediff(mi, getdate(), convert(datetime, wo.expire_time, 120))                                   ");
			sql.append("	       when t.expire_time < wo.expire_time then                                                            ");
			sql.append("	         datediff(mi, getdate(), convert(datetime, t.expire_time, 120))                                    ");
			sql.append("	       else                                                                                                ");
			sql.append("		     99999999                                                                                              ");
			sql.append("         end <= :expire_mins                                                                   ");
		}
		if (!CommonFun.isNe(params.get("create_time_b"))) {
			sql.append(" and CONVERT(datetime,substring(t.create_time,1,10)) >= CONVERT(datetime,:create_time_b)");
		}
		if (!CommonFun.isNe(params.get("create_time_e"))) {
			sql.append(" and CONVERT(datetime,substring(t.create_time,1,10)) <= CONVERT(datetime,:create_time_e)");
		}
		long start = CommonFun.isNe(params.get("start")) ? 1 : Long.parseLong(params.get("start").toString());
	    int limit = CommonFun.isNe(params.get("limit")) ? 0 : Integer.parseInt(params.get("limit").toString());
	    Result rs = executeQueryLimitTotal(sql.toString(), params, (String)params.remove("totals"), start, limit);
	    return rs;
	}
	public Result listProcess(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  isnull(spp.id,'ZYL') as id, isnull(spp.name,'个人配合单') as name, count(1) as count                                                                                           ");
		sql.append(" from    wf_task(nolock) t                                                                                              ");
		sql.append("         left join wf_order(nolock) wo on wo.id = t.order_id                                                            ");
		//sql.append("         left join wf_cc_order(nolock) wco on wco.order_id = t.order_id and wco.actor_id = :system_user_id              ");
		sql.append("         left join wf_task_actor(nolock) wta on wta.task_id = t.id and wta.actor_id = :system_user_id                                                     ");
		sql.append("         left join wf_process(nolock) wp on wp.id = wo.process_id                                                       ");
		sql.append("         left join system_product_process(nolock) spp on spp.wf_process_id = wp.id                                      ");
		sql.append("         left join system_product_process_node(nolock) sppn on sppn.system_product_code = spp.system_product_code       ");
		sql.append("                                                       and sppn.system_product_process_id = spp.id              ");
		sql.append("                                                       and sppn.type = 'task'                                   ");
		sql.append("                                                       and t.task_name = sppn.id                                ");
		sql.append("         left join v_system_user(nolock) su on su.id = wo.creator                                                       ");
		sql.append("         left join system_product(nolock) sp on sp.code = case when wp.name = 'free'                                    ");
		sql.append("                                                       then ( select                                            ");
		sql.append("                                                               stringvalue                                      ");
		sql.append("                                                              from                                              ");
		sql.append("                                                               dbo.parsejson(wo.variable)                       ");
		sql.append("                                                              where                                             ");
		sql.append("                                                               name = 'system_product_code'                     ");
		sql.append("                                                            )                                                   ");
		sql.append("                                                       else spp.system_product_code                             ");
		sql.append("                                                  end                                                           ");
		sql.append(" where   wo.id is not  null                                                                                     ");
		sql.append("         and wp.id is not null                                                                                  ");
		sql.append("         and ( wp.name = 'free'                                                                                 ");
		sql.append("               or ( wp.name != 'free'                                                                           ");
		sql.append("                    and spp.id is not null                                                                      ");
		sql.append("                    and sppn.id is not null                                                                     ");
		sql.append("                  )                                                                                             ");
		sql.append("             )                                                                                                  ");
		sql.append("         and isnull(wta.actor_id, '') > ''                                 ");
		if("oa".equals(params.get("system_product_code"))){
			sql.append(" and (spp.system_product_code=:system_product_code or wp.name='free') "); 
		}else{
			setParameter(params, "system_product_code", " and spp.system_product_code=:system_product_code", sql);
		}
		sql.append(" group by spp.id, spp.name                  ");
		params.put("system_user_id", user().getId());
		long start = CommonFun.isNe(params.get("start")) ? 1 : Long.parseLong((String)params.get("start"));
	    int limit = CommonFun.isNe(params.get("limit")) ? 0 : Integer.parseInt((String)params.get("limit"));
	    Result rs = executeQueryLimitTotal(sql.toString(), params, (String)params.remove("totals"), start, limit);
	    return rs;
	}
	public Result queryTasking(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  * from (                                                                                             ");
		sql.append(" select  wo.order_no ,wo.id as order_id,                                                                                          ");
		sql.append("         wo.create_time as process_create_time,                                                                                        ");
		sql.append("         isnull((select  stringvalue                                 ");
		sql.append("             from  dbo.parsejson(wo.variable)                      ");
		sql.append("            where  name = 'subject'       ");
		sql.append("         ), case when wp.name = 'free'                                                                             ");
		sql.append("              then ( select  stringvalue                                                                        ");
		sql.append("                     from    dbo.parsejson(wo.variable)                                                         ");
		sql.append("                     where   name = 'process_name'                                                       ");
		sql.append("                   )                                                                                            ");
		sql.append("              else wp.display_name                                                                      ");
		sql.append("         end) as subject ,                           ");
		sql.append("         wp.display_name process_name ,                                                                         ");
		sql.append("         case when wp.name = 'free'                                                                             ");
		sql.append("              then ( select  stringvalue                                                                        ");
		sql.append("                     from    dbo.parsejson(wo.variable)                                                         ");
		sql.append("                     where   name = 'system_product_code'                                                       ");
		sql.append("                   )                                                                                            ");
		sql.append("              else spp.system_product_code                                                                      ");
		sql.append("         end as system_product_code ,                                                                           ");
		sql.append("         isnull(sp.name, 'CooperOP') as system_product_name ,                                                   ");
		sql.append("         case when wp.name = 'free' then wp.name                                                                ");
		sql.append("              else spp.id                                                                                       ");
		sql.append("         end as system_product_process_id ,                                                                     ");
		sql.append("         case when wp.name = 'free' then ( select    stringvalue                                                ");
		sql.append("                                           from      dbo.parsejson(wo.variable)                                 ");
		sql.append("                                           where     name = 'info_bill'                                         ");
		sql.append("                                         )                                                                      ");
		sql.append("              else spp.info_bill                                                                                ");
		sql.append("         end as info_bill ,                                                                                     ");
		sql.append("         wo.expire_time as order_expire_time ,                                                                  ");
		sql.append("         wo.end_time as order_end_time ,                                                                  ");
		sql.append("         case                                                                                                  ");
		sql.append("           when wo.expire_time is not null then                                      ");
		sql.append("	         datediff(mi, getdate(), convert(datetime, wo.expire_time, 120))                                   ");
		sql.append("	       else                                                                                                ");
		sql.append("		     99999999                                                                                              ");
		sql.append("         end as expire_mins,                                                                  ");
		sql.append("         ( select top 1                                 ");
		sql.append("                     node_name                      ");
		sql.append("           from      query_process_history(wo.order_no)       ");
		sql.append("         ) as node_name,                           ");
		sql.append("         ( select top 1                                 ");
		sql.append("                     task_time_start                      ");
		sql.append("           from      query_process_history(wo.order_no)       ");
		sql.append("         ) as task_time_start,                           ");
		sql.append("         ( select top 1                                 ");
		sql.append("                     operator_name                      ");
		sql.append("           from      query_process_history(wo.order_no)       ");
		sql.append("         ) as operator_name  ,                         ");
		sql.append("         ( select top 1                                 ");
		sql.append("                     audited                      ");
		sql.append("           from      query_process_history(wo.order_no)       ");
		sql.append("         ) as audited,                           ");
		sql.append(" (select top 1 skim_time from wf_task where skim_time is not null and skim_time!='' and order_id=wo.id) as is_skim_time ");
		sql.append(" from    wf_hist_order(nolock) wo                                                            ");
		sql.append("         left join wf_process(nolock) wp on wp.id = wo.process_id                                                       ");
		sql.append("         left join system_product_process(nolock) spp on spp.wf_process_id = wp.id                                      ");
		sql.append("         left join system_product(nolock) sp on sp.code = case when wp.name = 'free'                                    ");
		sql.append("                                                       then ( select                                            ");
		sql.append("                                                               stringvalue                                      ");
		sql.append("                                                              from                                              ");
		sql.append("                                                               dbo.parsejson(wo.variable)                       ");
		sql.append("                                                              where                                             ");
		sql.append("                                                               name = 'system_product_code'                     ");
		sql.append("                                                            )                                                   ");
		sql.append("                                                       else spp.system_product_code                             ");
		sql.append("                                                  end                                                           ");
		sql.append(" where   wo.id is not  null                                                                                     ");
		sql.append("         and wp.id is not null                                                                                  ");
		sql.append("         and wo.creator = :system_user_id                                  ");
		if("oa".equals(params.get("system_product_code"))){
			sql.append(" and (spp.system_product_code=:system_product_code or wp.name='free') "); 
		}else{
			setParameter(params, "system_product_code", " and spp.system_product_code=:system_product_code", sql);
		}
		sql.append(" ) t where  1 = 1                                 ");
		params.put("system_user_id", user().getId());
		
		setParameter(params, "process_id", " and system_product_process_id=:process_id", sql);
		if(!CommonFun.isNe(params.get("keyword"))){
			params.put("keyword", "%"+params.get("keyword")+"%");
			sql.append("     and upper(subject) + '|' + upper(order_no) like '%' + upper(:keyword) + '%'                           ");
		}
		if (!CommonFun.isNe(params.get("expire_mins"))) {
			sql.append("         and expire_mins <= :expire_mins                                                                   ");
		}
		if ("1".equals(params.get("process_state"))) {
			sql.append("        and t.order_end_time is null                  ");
		}else if ("2".equals(params.get("process_state"))){
			sql.append("        and t.order_end_time is not null                  ");
		}
		if (!CommonFun.isNe(params.get("create_time_b"))) {
			sql.append(" and CONVERT(datetime,substring(t.process_create_time,1,10)) >= CONVERT(datetime,:create_time_b)");
		}
		if (!CommonFun.isNe(params.get("create_time_e"))) {
			sql.append(" and CONVERT(datetime,substring(t.process_create_time,1,10)) <= CONVERT(datetime,:create_time_e)");
		}
		if(CommonFun.isNe(params.get("sort"))){
			params.put("sort", "process_create_time desc");
		}
		long start = CommonFun.isNe(params.get("start")) ? 1 : Long.parseLong((String)params.get("start"));
	    int limit = CommonFun.isNe(params.get("limit")) ? 0 : Integer.parseInt((String)params.get("limit"));
	    Result rs = executeQueryLimitTotal(sql.toString(), params, (String)params.remove("totals"), start, limit);
	    return rs;
	}
	public Result queryHistory(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  t.id as task_id ,t.order_id  ,                                                                         ");
		sql.append("         t.task_name as node ,                                                                                  ");
		sql.append("         t.display_name as node_name ,                                                                          ");
		sql.append("         wo.order_no ,                                                                                          ");
		sql.append("         t.create_time ,                                                                                        ");
		sql.append("         t.finish_time ,                                                                                        ");
		sql.append("         wo.end_time as order_end_time,                                                                                        ");
		sql.append(" 		 ( select  stringvalue                                                                        ");
		sql.append("                     from    parsejson(t.variable)                                                         ");
		sql.append("                     where   name = 'audited'                                                       ");
		sql.append("         ) as audited,   ");
		sql.append("         isnull((select  stringvalue                                 ");
		sql.append("             from  parsejson(wo.variable)                      ");
		sql.append("            where  name = 'subject'       ");
		sql.append("         ), case when wp.name = 'free'                                                                             ");
		sql.append("              then ( select  stringvalue                                                                        ");
		sql.append("                     from    parsejson(wo.variable)                                                         ");
		sql.append("                     where   name = 'process_name'                                                       ");
		sql.append("                   )                                                                                            ");
		sql.append("              else wp.display_name                                                                      ");
		sql.append("         end) as subject ,                           ");
		sql.append("         wp.display_name process_name ,                                                                         ");
		sql.append("         case when wp.name = 'free'                                                                             ");
		sql.append("              then ( select  stringvalue                                                                        ");
		sql.append("                     from    parsejson(wo.variable)                                                         ");
		sql.append("                     where   name = 'system_product_code'                                                       ");
		sql.append("                   )                                                                                            ");
		sql.append("              else spp.system_product_code                                                                      ");
		sql.append("         end as system_product_code ,                                                                           ");
		sql.append("         isnull(sp.name, 'CooperOP') as system_product_name ,                                                   ");
		sql.append("         case when wp.name = 'free' then wp.name                                                                ");
		sql.append("              else spp.id                                                                                       ");
		sql.append("         end as system_product_process_id ,                                                                     ");
		sql.append("         case when wp.name = 'free' then ( select    stringvalue                                                ");
		sql.append("                                           from      parsejson(wo.variable)                                 ");
		sql.append("                                           where     name = 'info_bill'                                         ");
		sql.append("                                         )                                                                      ");
		sql.append("              else spp.info_bill                                                                                ");
		sql.append("         end as info_bill ,                                                                                     ");
		sql.append("         wo.expire_time as order_expire_time ,                                                                  ");
		sql.append("         t.expire_time as task_expire_time ,                                                                  ");
		sql.append("         case                                                                                                  ");
		sql.append("	       when t.expire_time is not null and wo.expire_time is null  then                                     ");
		sql.append("	         datediff(mi, getdate(), convert(datetime, t.expire_time, 120))                                    ");
		sql.append("           when t.expire_time is null and wo.expire_time is not null then                                      ");
		sql.append("	         datediff(mi, getdate(), convert(datetime, wo.expire_time, 120))                                   ");
		sql.append("	       when t.expire_time < wo.expire_time then                                                            ");
		sql.append("	         datediff(mi, getdate(), convert(datetime, t.expire_time, 120))                                    ");
		sql.append("	       else                                                                                                ");
		sql.append("		     99999999                                                                                              ");
		sql.append("         end as expire_mins,                                                                  ");
		sql.append("         case when wp.name = 'free' then ( select    stringvalue                                                ");
		sql.append("                                           from      parsejson(wo.variable)                                 ");
		sql.append("                                           where     name = 'audit_bill'                                        ");
		sql.append("                                         )                                                                      ");
		sql.append("              else sppn.instance_bill                                                                           ");
		sql.append("         end as node_bill ,                                                                                     ");
		sql.append("         isnull(su.name, wo.creator) as create_user_name ,                                                      ");
		sql.append("         ( select top 1                                 ");
		sql.append("                     node_name                      ");
		sql.append("           from      query_process_history(wo.order_no)       ");
		sql.append("         ) as current_node_name,                           ");
		sql.append("         ( select top 1                                 ");
		sql.append("                     operator_name                      ");
		sql.append("           from      query_process_history(wo.order_no)       ");
		sql.append("         ) as operator_name    ,                       ");
		sql.append("         ( select top 1                                 ");
		sql.append("                     audited                      ");
		sql.append("           from      query_process_history(wo.order_no)       ");
		sql.append("         ) as last_audited,      ");
		sql.append(" (select top 1 skim_time from wf_task where skim_time is not null and skim_time!='' and order_id=wo.id) as is_skim_time ");
		sql.append(" from    wf_hist_task(nolock) t                                                                                              ");
		sql.append("         left join wf_hist_order(nolock) wo on wo.id = t.order_id                                                            ");
		sql.append("         left join wf_process(nolock) wp on wp.id = wo.process_id                                                       ");
		sql.append("         left join system_product_process(nolock) spp on spp.wf_process_id = wp.id                                      ");
		sql.append("         left join system_product_process_node(nolock) sppn on sppn.system_product_code = spp.system_product_code       ");
		sql.append("                                                       and sppn.system_product_process_id = spp.id              ");
		sql.append("                                                       and sppn.type = 'task'                                   ");
		sql.append("                                                       and t.task_name = sppn.id                                ");
		sql.append("         left join v_system_user(nolock) su on su.id = wo.creator                                                       ");
		sql.append("         left join system_product(nolock) sp on sp.code = case when wp.name = 'free'                                    ");
		sql.append("                                                       then ( select                                            ");
		sql.append("                                                               stringvalue                                      ");
		sql.append("                                                              from                                              ");
		sql.append("                                                               parsejson(wo.variable)                       ");
		sql.append("                                                              where                                             ");
		sql.append("                                                               name = 'system_product_code'                     ");
		sql.append("                                                            )                                                   ");
		sql.append("                                                       else spp.system_product_code                             ");
		sql.append("                                                  end                                                           ");
		sql.append(" where   wo.id is not  null                                                                                     ");
		sql.append("         and wp.id is not null                                                                                  ");
		sql.append("         and ( wp.name = 'free'                                                                                 ");
		sql.append("               or ( wp.name != 'free'                                                                           ");
		sql.append("                    and spp.id is not null                                                                      ");
		//sql.append("                    and sppn.id is not null                                                                     ");
		sql.append("                  )                                                                                             ");
		sql.append("             )                                                                                                  ");
		sql.append("         and t.operator = :system_user_id                                  ");
		params.put("system_user_id", user().getId());
		if("oa".equals(params.get("system_product_code"))){
			sql.append(" and (spp.system_product_code=:system_product_code or wp.name='free') "); 
		}else{
			setParameter(params, "system_product_code", " and spp.system_product_code=:system_product_code", sql);
		}
		setParameter(params, "process_id", " and spp.id=:process_id", sql);
		setParameter(params, "creator", " and wo.creator=:creator", sql);
		if(!CommonFun.isNe(params.get("keyword"))){
			params.put("keyword", "%"+params.get("keyword")+"%");
			sql.append("     and upper(isnull((select  stringvalue                                 ");
			sql.append("             from  parsejson(wo.variable)                      ");
			sql.append("            where  name = 'subject'       ");
			sql.append("         ), case when wp.name = 'free'                                                                             ");
			sql.append("              then ( select  stringvalue                                                                        ");
			sql.append("                     from    parsejson(wo.variable)                                                         ");
			sql.append("                     where   name = 'process_name'                                                       ");
			sql.append("                   )                                                                                            ");
			sql.append("              else wp.display_name                                                                      ");
			sql.append("         end)) + '|' + upper(wo.order_no) like '%' + upper(:keyword) + '%'                           ");
		}
		if (!CommonFun.isNe(params.get("expire_mins"))) {
			sql.append("         and case                                                                                                  ");
			sql.append("	       when t.expire_time is not null and wo.expire_time is null  then                                     ");
			sql.append("	         datediff(mi, getdate(), convert(datetime, t.expire_time, 120))                                    ");
			sql.append("           when t.expire_time is null and wo.expire_time is not null then                                      ");
			sql.append("	         datediff(mi, getdate(), convert(datetime, wo.expire_time, 120))                                   ");
			sql.append("	       when t.expire_time < wo.expire_time then                                                            ");
			sql.append("	         datediff(mi, getdate(), convert(datetime, t.expire_time, 120))                                    ");
			sql.append("	       else                                                                                                ");
			sql.append("		     99999999                                                                                              ");
			sql.append("         end <= :expire_mins                                                                   ");
		}
		if (!CommonFun.isNe(params.get("create_time_b"))) {
			sql.append(" and CONVERT(datetime,substring(t.create_time,1,10)) >= CONVERT(datetime,:create_time_b)");
		}
		if (!CommonFun.isNe(params.get("create_time_e"))) {
			sql.append(" and CONVERT(datetime,substring(t.create_time,1,10)) <= CONVERT(datetime,:create_time_e)");
		}
		if(CommonFun.isNe(params.get("sort"))){
			params.put("sort", "finish_time desc");
		}
		long start = CommonFun.isNe(params.get("start")) ? 1 : Long.parseLong((String)params.get("start"));
	    int limit = CommonFun.isNe(params.get("limit")) ? 0 : Integer.parseInt((String)params.get("limit"));
	    Result rs = executeQueryLimitTotal(sql.toString(), params, (String)params.remove("totals"), start, limit);
	    return rs;
	}
	public Record queryTaskCount(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  count(1) as task_nums                                                       ");
		sql.append(" from    wf_task(nolock) t                                                                                              ");
		sql.append("         left join wf_order(nolock) wo on wo.id = t.order_id                                                            ");
		sql.append("         left join wf_task_actor(nolock) wta on wta.task_id = t.id                                                      ");
		sql.append("         left join wf_process(nolock) wp on wp.id = wo.process_id                                                       ");
		sql.append("         left join system_product_process(nolock) spp on spp.wf_process_id = wp.id                                      ");
		sql.append("         left join system_product_process_node(nolock) sppn on sppn.system_product_code = spp.system_product_code       ");
		sql.append("                                                       and sppn.system_product_process_id = spp.id              ");
		sql.append("                                                       and sppn.type = 'task'                                   ");
		sql.append("                                                       and t.task_name = sppn.id                                ");
		setParameter(params, "ignore", " left join system_bobmessage_ignore sbi on sbi.message_key=t.task_id and sbi.type='1' and sbi.system_user_id=:system_user_id ", sql);
		sql.append(" where   wo.id is not  null                                                                                     ");
		sql.append("         and wp.id is not null                                                                                  ");
		sql.append("         and ( wp.name = 'free'                                                                                 ");
		sql.append("               or ( wp.name != 'free'                                                                           ");
		sql.append("                    and spp.id is not null                                                                      ");
		sql.append("                    and sppn.id is not null                                                                     ");
		sql.append("                  )                                                                                             ");
		sql.append("             )                                                                                                  ");
		sql.append("         and wta.actor_id = :system_user_id                                                                     ");
		setParameter(params, "ignore", " and sbi.message_key is not null ", sql);
		params.put("system_user_id", user().getId());
	    return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result queryTaskFromCS() throws Exception{
		return executeQuery("select * from  system_product_process_start(nolock) where (started = -1 or started = 0) and bmid is not null");
	}
	public Result queryBackProcessFromCS() throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.* from  system_product_process_start(nolock)  t ");
		sql.append(" left join wf_order(nolock) o on o.order_no =t.djbh");
		sql.append(" where started = -9 and o.id is not null and bmid is not null");
		return executeQuery(sql.toString());
	}
	
	public Result queryAutoPassProcess() throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append(" select wf.*,(select top 1 actor_id from  wf_task_actor where task_id =wf.id ) as operator_ from wf_task wf    ");
		sql.append(" left join wf_order wo on wo.id=wf.order_id                                                                    ");
		sql.append(" left join system_product_process spp on spp.wf_process_id=wo.process_id                                       ");
		sql.append(" left join system_product_process_node sppn on system_product_process_id=spp.id and sppn.id = wf.task_name     ");
		sql.append(" where sppn.auto_pass ='Y' and cast(wf.create_time as datetime) < dateadd(minute,-(isnull(sppn.expireTime,5)),GETDATE())                     ");
		return executeQuery(sql.toString());
	}
	
	public void updateTaskFromCS(Map<String, Object> params) throws Exception{
		executeUpdate("update system_product_process_start set started=:started where id=:id ",params);
	}
	
	public Result queryFreeTask(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  tf.* ,                                         ");
		sql.append("         ( select top 1                                 ");
		sql.append("                     operator_name                      ");
		sql.append("           from      query_process_history(tf.djbh)       ");
		sql.append("         ) as operator_name ,                           ");
		sql.append("         ( select top 1                                 ");
		sql.append("                     task_time_start                    ");
		sql.append("           from      query_process_history(tf.djbh)       ");
		sql.append("         ) as task_time_start ,                         ");
		sql.append("         ( select top 1                                 ");
		sql.append("                     task_time_end                      ");
		sql.append("           from      query_process_history(tf.djbh)       ");
		sql.append("         ) as task_time_end                             ");
		sql.append(" from    task_free(nolock) tf                                   ");
		sql.append(" where   system_user_id = :system_user_id               ");
		sql.append(" and     is_delete = 0                                  ");
		params.put("system_user_id", user().getId());
		long start = CommonFun.isNe(params.get("start")) ? 1 : Long.parseLong((String)params.get("start"));
		int limit = CommonFun.isNe(params.get("limit")) ? 0 : Integer.parseInt((String)params.get("limit"));
		Result rs = executeQueryLimitTotal(sql.toString(), params, (String)params.remove("totals"), start, limit);
		return rs;
	}

	public int insertFreeTask(Map<String, Object> params) throws Exception {
		Record d = new Record(params);
		d.remove("id");
		d.put("state", FREE_TASK_STATE_HOLD);
		d.put("system_user_id", user().getId());
		d.put("create_time", "sysdate");
		executeInsert("task_free", d);
		return getSeqVal("task_free");
	}
	
	public int updateFreeTask(int id, Map<String, Object> params) throws Exception {
		Record d = new Record(params);
		d.remove("id");
		Record conditions = new Record();
		conditions.put("id", id);
		return executeUpdate("task_free", d, conditions);
	}

	public int startFreeTask(int id) throws Exception {
		Record conditions = new Record();
		conditions.put("id", id);
		Record params = new Record();
		params.put("state", FREE_TASK_STATE_AUDITING);
		params.put("create_time", "sysdate");
		params.put("djbh", new BillDao().getBusinessNo("ZYL"));
		return executeUpdate("task_free", params, conditions);
	}
	
	public Record getFreeTask(int id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  tf.* ,                                         ");
		sql.append("         vsut.name as operator_name ,                   ");
		sql.append("         vsut.department_name as operator_department_name , ");
		sql.append("         substring((select ',' + actual_name from v_system_user(nolock) vsuc where id in (select col from split(tf.cc, ',')) order by id FOR xml path ('')), 2, 999999) AS cc_name, ");
		sql.append("         substring((select ',' + department_name from v_system_user(nolock) vsuc where id in (select col from split(tf.cc, ',')) order by id FOR xml path ('')), 2, 999999) AS cc_department_name, ");
		sql.append("         ( select top 1                                 ");
		sql.append("                     task_id                      ");
		sql.append("           from      query_process_history(tf.djbh)       ");
		sql.append("         ) as task_id                           ");
		sql.append(" FROM    task_free(nolock) tf                                   ");
		sql.append(" left join v_system_user(nolock) vsut on vsut.id = tf.operator  ");
		sql.append(" WHERE   tf.id = :id                                       ");
		Record conditions = new Record();
		conditions.put("id", id);
	    return executeQuerySingleRecord(sql.toString(), conditions);
	}

	public Record getFreeTask(String djbh) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  tf.*, vsu.name as system_user_name, vsu.department_name as system_department_name, vsu.role_names as system_role_names,                                                                                               ");
		sql.append("         vsut.name as operator_name ,                   ");
		sql.append("         vsut.department_name as operator_department_name , ");
		sql.append("         substring((select ',' + actual_name from v_system_user(nolock) vsuc where id in (select col from split(tf.cc, ',')) order by id FOR xml path ('')), 2, 999999) AS cc_name, ");
		sql.append("         substring((select ',' + department_name from v_system_user(nolock) vsuc where id in (select col from split(tf.cc, ',')) order by id FOR xml path ('')), 2, 999999) AS cc_department_name ");
		sql.append(" FROM    task_free(nolock) tf                                                                                          ");
		sql.append(" LEFT JOIN v_system_user(nolock) vsu on vsu.id = tf.system_user_id                                              ");
		sql.append(" left join v_system_user(nolock) vsut on vsut.id = tf.operator  ");
		sql.append(" WHERE   djbh = :djbh                                                                                              ");
		Record conditions = new Record();
		conditions.put("djbh", djbh);
	    return executeQuerySingleRecord(sql.toString(), conditions);
	}

	public int finishFreeTask(int id) throws Exception {
		Record conditions = new Record();
		conditions.put("id", id);
		Record params = new Record();
		params.put("state", FREE_TASK_STATE_FINISH);
		params.put("ender", user().getId());
		params.put("end_time", "sysdate");
		return executeUpdate("task_free", params, conditions);
	}
	
	public int cancelFreeTask(int id) throws Exception {
		Record conditions = new Record();
		conditions.put("id", id);
		Record params = new Record();
		params.put("state", FREE_TASK_STATE_CANCEL);
		params.put("ender", user().getId());
		params.put("end_time", "sysdate");
		return executeUpdate("task_free", params, conditions);
	}
	
	public int deleteFreeTask(int id) throws Exception {
		Record conditions = new Record();
		conditions.put("id", id);
		Record params = new Record();
		params.put("is_delete", 1);
		params.put("ender", user().getId());
		params.put("end_time", "sysdate");
		return executeUpdate("task_free", params, conditions);
	}
	public Result listOrderTaskHistory(String djbh) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM query_process_history(:djbh) ");
		Record params = new Record();
		params.put("djbh", djbh);
		Result rs = executeQuery(sql.toString(), params);
		return rs;
	}
	public Result getTask(String djbh) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from wf_task(nolock) where order_id in     ");
		sql.append(" (select id from wf_order(nolock) where order_no=:djbh)    ");
		Record conditions = new Record();
		conditions.put("djbh", djbh);
	    return executeQuery(sql.toString(), conditions);
	}
	public Record get(String id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from wf_task(nolock) where id=:id     ");
		Record conditions = new Record();
		conditions.put("id", id);
	    return executeQuerySingleRecord(sql.toString(), conditions);
	}
	public Record getOrder(String id, String djbh) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from wf_order(nolock) where 1=1     ");
		Record conditions = new Record();
		conditions.put("id", id);
		conditions.put("djbh", djbh);
		setParameter(conditions, "id", " and id=:id", sql);
		setParameter(conditions, "djbh", " and order_no=:djbh", sql);
	    return executeQuerySingleRecord(sql.toString(), conditions);
	}
	public Record getHistOrder(String id,String djbh) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from wf_hist_order(nolock) where 1=1    ");
		Record conditions = new Record();
		conditions.put("id", id);
		conditions.put("djbh", djbh);
		setParameter(conditions, "id", " and id=:id", sql);
		setParameter(conditions, "djbh", " and order_no=:djbh", sql);
	    return executeQuerySingleRecord(sql.toString(), conditions);
	}
	public Result getTaskActors(String task_id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select actor_id from wf_task_actor(nolock) where task_id=:task_id    ");
		Record conditions = new Record();
		conditions.put("task_id", task_id);
	    return executeQuery(sql.toString(), conditions);
	}
	public Result getHistTaskActors(String order_id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct actor_id from (   ");
		sql.append(" select actor_id from wf_task_actor(nolock) where task_id in (select id from wf_task(nolock) where order_id=:order_id)   ");
		sql.append(" union   ");
		sql.append(" select actor_id from wf_hist_task_actor(nolock) where task_id in (select id from wf_hist_task(nolock) where order_id=:order_id)    ");
		sql.append(" union   ");
		sql.append(" select creator as actor_id from wf_order(nolock) where id =:order_id   ");
		sql.append(" ) t    ");
		Record conditions = new Record();
		conditions.put("order_id", order_id);
	    return executeQuery(sql.toString(), conditions);
	}
	public Result getProcess(String order_id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from system_product_process(nolock) where   ");
		sql.append(" wf_process_id =(select process_id from wf_order(nolock) where id =:order_id)  ");
		sql.append(" or wf_process_id =(select process_id from wf_hist_order(nolock) where id =:order_id)  ");

		Record conditions = new Record();
		conditions.put("order_id", order_id);
	    return executeQuery(sql.toString(), conditions);
	}
	
	public void skimsave(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		params.put("skim_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		sql.append(" update wf_task set skim_time =:skim_time where id=:task_id ");
	    executeUpdate(sql.toString(), params);
	}
}
