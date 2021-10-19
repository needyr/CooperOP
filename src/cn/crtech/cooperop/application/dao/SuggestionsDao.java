package cn.crtech.cooperop.application.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class SuggestionsDao extends BaseDao {
	public Result querySuggestions(Map<String, Object> params) throws Exception {
		params.put("system_user_id", user().getId());
		StringBuffer sql = new StringBuffer();
		sql.append(" select s.*,case when(s.type='2') then '节点处理：'+su1.name+'('+su1.department_name+')' else '补充意见：'+su1.name+'('+su1.department_name+')' end as creator_name ");
		sql.append(" , (select distinct '['+su.name+']' from wf_cc_order wc(nolock)  left join v_system_user su(nolock) on su.id=wc.actor_id where wc.suggestions_id =s.id  ");
		sql.append(" FOR XML PATH('')) as csry  from wf_suggestions s (nolock)  ");
		sql.append(" left join v_system_user su1(nolock) on su1.id=s.creator ");
		sql.append(" where s.order_id=:order_id ");
		sql.append(" and (s.type!=1 or s.id in (select distinct(suggestions_id) from wf_cc_order where order_id=:order_id and (actor_id=:system_user_id or creator=:system_user_id) and suggestions_id is not null)) ");
		long start = CommonFun.isNe(params.get("start")) ? 1 : Long.parseLong((String)params.get("start"));
	    int limit = CommonFun.isNe(params.get("limit")) ? 0 : Integer.parseInt((String)params.get("limit"));
	    Result rs = executeQueryLimitTotal(sql.toString(), params, (String)params.remove("totals"), start, limit);
	    return rs;
	}
	
	public Result queryMine(Map<String, Object> params) throws Exception {
	    StringBuffer sql = new StringBuffer();
	    sql.append(" select f.* from (");
	    sql.append(" select wc.*,case when(wc.finish_time is not null or ws.message is null or ws.message ='') then '1' else '0' end as readflag ");
	    sql.append(" ,isnull(wo.order_no,who.order_no) as order_no,ws.pageid ");
	    sql.append(" , isnull(dbo.get_json_value(isnull(wo.variable,who.variable), 1, 'subject'),'[无主题]') as subject ");
	    sql.append(" from wf_cc_order wc(nolock)  ");
	    sql.append(" left join wf_suggestions ws(nolock) on wc.suggestions_id =ws.id ");
	    sql.append(" left join wf_order wo(nolock) on wc.order_id =wo.id ");
	    sql.append(" left join wf_hist_order who(nolock) on wc.order_id =who.id ");
	    sql.append(" where wc.actor_id =:system_user_id and ws.id is not null and ws.type<>'2' and wc.order_id is not null");
	    sql.append(" ) f where 1=1");
	    setParameter(params, "readflag", " and f.readflag=:readflag", sql);
	    setParameter(params, "keyword", " and f.subject like '%'+:keyword+'%' ", sql);
	    params.put("system_user_id", user().getId());
	    long start = CommonFun.isNe(params.get("start")) ? 1L : Long.parseLong((String)params.get("start"));
	    int limit = CommonFun.isNe(params.get("limit")) ? 0 : Integer.parseInt((String)params.get("limit"));
	    Result rs = executeQueryLimitTotal(sql.toString(), params, (String)params.remove("totals"), start, limit);
	    return rs;
	}
	public Result queryActors(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
//		sql.append(" select  actor_id ,su.name as actor_name from wf_cc_order wc(nolock) ");
//		sql.append(" left join v_system_user su(nolock) on wc.actor_id=su.id ");
//		sql.append(" where actor_id is not null and actor_id<>'' and wc.order_id=:order_id ");
//		sql.append(" group by wc.actor_id,su.name  ");
		sql.append(" SELECT actor_id,vsu.NAME+CASE WHEN t.lx=0 THEN '(发起)' WHEN t.lx=1 THEN '(节点)' ELSE '' END AS actor_name,lx ");
		sql.append(" FROM ( ");
		sql.append(" 	SELECT actor_id,MIN(lx) AS lx ");
		sql.append(" 	FROM( ");
		sql.append(" 		select actor_id,2 AS lx ");
		sql.append(" 		FROM wf_cc_order wc(nolock) ");
		sql.append(" 		WHERE actor_id is not null and actor_id<>'' and wc.order_id=:order_id ");
		sql.append(" 		UNION ALL  ");
		sql.append(" 		SELECT wta.actor_id,1 ");
		sql.append(" 		FROM wf_task_actor(NOLOCK) wta ");
		sql.append(" 		WHERE wta.task_id=(SELECT id FROM wf_task(NOLOCK) WHERE order_Id=:order_id) ");
		sql.append(" 		UNION ALL  ");
		sql.append(" 		SELECT wht.operator,1 ");
		sql.append(" 		FROM wf_hist_task(NOLOCK) wht ");
		sql.append(" 		WHERE wht.order_Id=:order_id ");
		sql.append(" 		UNION ALL  ");
		sql.append(" 		SELECT creator,0 ");
		sql.append(" 		FROM wf_order(NOLOCK) who ");
		sql.append(" 		WHERE id=:order_id ");
		sql.append(" 	) t1 ");
		sql.append(" 	where actor_id != :mine ");
		sql.append(" 	GROUP BY actor_id ");
		sql.append(" ) t ");
		sql.append(" LEFT JOIN v_system_user(NOLOCK) vsu ON t.actor_id=vsu.id ");
		params.put("sort", "lx asc");
		params.put("mine", user().getId());
		Result r = executeQuery(sql.toString(), params);
		params.remove("sort");
	    return r;
	}
	public int querySuggestionsCount(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select  count(1) as num                                         ");
		sql.append("from   wf_cc_order wc(nolock)                                       ");
		sql.append(" left join wf_suggestions ws(nolock) on wc.suggestions_id =ws.id    ");
		sql.append(" where wc.actor_id = :system_user_id and ws.id is not null   and wc.finish_Time is null and (ws.message is not null and ws.message <>'')  and ws.type!=2 and wc.order_id is not null");
		params.put("system_user_id", user().getId());
	    Record record = executeQuerySingleRecord(sql.toString(), params);
	    if (record == null) return 0;
	    return record.getInt("num");
	}
	
	public Record getCCOrder(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select  * from wf_cc_order wc(nolock)");
		sql.append(" where wc.order_id =:order_id ");
	    return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public void saveRead(int suggestions_id) throws Exception {
		Record conditions = new Record();
		conditions.put("suggestions_id", suggestions_id);
		conditions.put("actor_Id", user().getId());
		Record params = new Record();
		params.put("finish_Time", "sysdate");
	    executeUpdate("wf_cc_order", params, conditions);
	}
	public void saveRead(String order_id) throws Exception {
		Record conditions = new Record();
		conditions.put("order_id", order_id);
		conditions.put("actor_Id", user().getId());
		Record params = new Record();
		params.put("finish_Time", "sysdate");
	    executeUpdate("wf_cc_order", params, conditions);
	}
	public int save(Map<String, Object> params) throws Exception {
		executeInsert("wf_suggestions", params);
		return getSeqVal("wf_suggestions");
	}
	public int saveCCOrder(Map<String, Object> params) throws Exception {
		executeInsert("wf_cc_order", params);
		return getSeqVal("wf_cc_order");
	}
}
