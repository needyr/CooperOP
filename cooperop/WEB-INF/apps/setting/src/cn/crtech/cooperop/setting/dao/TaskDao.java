package cn.crtech.cooperop.setting.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class TaskDao extends BaseDao {
	
	
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
		sql.append("         ) as task_time_start ,                          ");
		sql.append("         ( select top 1                                 ");
		sql.append("                     operator_name                      ");
		sql.append("           from      query_process_history(wo.order_no)       ");
		sql.append("         ) as operator_name,                          ");
		sql.append("         ( select top 1                                 ");
		sql.append("                     operator                   ");
		sql.append("           from      query_process_history(wo.order_no)       ");
		sql.append("         ) as operator                          ");
		
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
		if("oa".equals(params.get("system_product_code"))){
			sql.append(" and (spp.system_product_code=:system_product_code or wp.name='free') "); 
		}else{
			setParameter(params, "system_product_code", " and spp.system_product_code=:system_product_code", sql);
		}
		setParameter(params, "creator", " and wo.creator=:creator", sql);
		sql.append(" ) t where  1 = 1                                 ");
		//params.put("system_user_id", user().getId());
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
		long start = CommonFun.isNe(params.get("start")) ? 1 : Long.parseLong((String)params.get("start"));
	    int limit = CommonFun.isNe(params.get("limit")) ? 0 : Integer.parseInt((String)params.get("limit"));
	    Result rs = executeQueryLimitTotal(sql.toString(), params, (String)params.remove("totals"), start, limit);
	    return rs;
	}
}
