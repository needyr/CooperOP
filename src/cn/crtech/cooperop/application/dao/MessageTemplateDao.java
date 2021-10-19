package cn.crtech.cooperop.application.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class MessageTemplateDao extends BaseDao {
	public final static String TABLE_NAME = "system_message_template";
	public final static String TABLE_NAME_temp = "system_message_template_sort";
	public final static String temp_type_sms = "1";
	public final static String temp_type_email = "2";
	public final static String temp_type_im = "3";
	public final static String temp_type_wx= "4";
	public final static String temp_type_sysmes = "5";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  s.*,sp.name as system_product_name                     ");
		sql.append(" from    "+ TABLE_NAME + "(nolock) s");
		sql.append(" left join system_product sp on sp.code = s.system_product_code ");
		sql.append(" where   1=1 and state > 0");
		setParameter(params, "state", " and s.state=:state", sql);
		setParameter(params, "title", " and s.title like '%'+:title+'%'", sql);
		setParameter(params, "sort", " and s.sort=:sort", sql);
		setParameter(params, "system_product_code", " and s.system_product_code=:system_product_code", sql);
	    return executeQuery(sql.toString(), params);
	}
	
	public Result queryTemplate(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  t.* ,s.system_product_code,s.system_product_process_id,                 ");
		sql.append(" s.system_product_process_node,s.action_, s.pageurl ,a.name as tunnel_name                ");
		sql.append(" from    "+ TABLE_NAME_temp + "(nolock) t");
		sql.append(" left join "+TABLE_NAME+"(nolock) s on s.id = t.sort_id");
		sql.append(" left join sms_tunnel(nolock) a on a.id = t.tunnel_id");
		sql.append(" where   1=1 and s.state > 0");
		setParameter(params, "sort_id", " and t.sort_id=:sort_id", sql);
		setParameter(params, "type", " and t.type=:type", sql);
		setParameter(params, "sort", " and s.sort=:sort", sql);
		setParameter(params, "state", " and s.state=:state", sql);
		setParameter(params, "t_state", " and t.state=:t_state", sql);
		setParameter(params, "system_product_code", " and s.system_product_code=:system_product_code", sql);
		setParameter(params, "pageurl", " and s.pageurl=:pageurl", sql);
		setParameter(params, "action_", " and s.action_=:action_", sql);
		setParameter(params, "system_product_process_id", " and s.system_product_process_id=:system_product_process_id", sql);
		setParameter(params, "system_product_process_node", " and s.system_product_process_node=:system_product_process_node", sql);
	    return executeQuery(sql.toString(), params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT s.*,sv.id+'-'+sv.description AS page_name,                                                ");
		sql.append(" spp.id+'-'+spp.name AS process_name,                                                             ");
		sql.append(" sppn.id+'-'+sppn.name AS node_name                                                               ");
		sql.append("  FROM "+TABLE_NAME+"(nolock) s                                                                      ");
		sql.append(" LEFT JOIN system_view(nolock) sv ON sv.type='bill' AND s.sort='3'                                        ");
		sql.append("  and sv.system_product_code+'.bill.'+sv.flag+'.'+sv.id=s.pageurl                                  ");
		sql.append("  LEFT JOIN system_product_process(nolock) spp ON s.sort='2' and spp.id=s.system_product_process_id       ");
		sql.append("  LEFT JOIN system_product_process_node(nolock) sppn ON s.sort='2'                                    ");
		sql.append("  AND sppn.system_product_process_id=spp.id AND sppn.id=s.system_product_process_node               ");
		sql.append("  where s.id =:id");
		params.remove("sort");
	    return executeQuerySingleRecord(sql.toString(), params);
	}
	public Map<String, Object> getTemplate(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT s.*,                                                          "); 
		sql.append(" CAST(ISNULL(st.id,et.id) AS VARCHAR)+'-'+ISNULL(st.name,et.name) as tunnel_name     "); 
		sql.append(" FROM "+ TABLE_NAME_temp + "(nolock) s                                       "); 
		sql.append(" LEFT JOIN sms_tunnel st ON st.id=s.tunnel_id                         "); 
		sql.append(" LEFT JOIN email_tunnel et ON et.id=s.tunnel_id                       ");
		sql.append(" select  *        ");
		sql.append("  where s.id =:id ");
	    return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		params.remove("id");
		params.put("last_modify_user", user().getId());
		params.put("last_modify_time", "sysdate");
	    executeInsert(TABLE_NAME, params);
	    return getSeqVal(TABLE_NAME);
	}
	public int insertTemplate(Map<String, Object> params) throws Exception {
		params.remove("id");
	    executeInsert(TABLE_NAME_temp, params);
	    return getSeqVal(TABLE_NAME_temp);
	}
	public void update(Map<String, Object> params) throws Exception {
		Record conditions = new Record();
		conditions.put("id", params.get("id"));
		Record parameters = new Record(params);
		parameters.remove("id");
		parameters.put("last_modify_user", user().getId());
		parameters.put("last_modify_time", "sysdate");
	    executeUpdate(TABLE_NAME, parameters, conditions);
	}
	
	public void updateTemplate(Map<String, Object> params) throws Exception {
		Record conditions = new Record();
		conditions.put("id", params.get("id"));
		Record parameters = new Record(params);
		parameters.remove("id");
	    executeUpdate(TABLE_NAME_temp, parameters, conditions);
	}
}
