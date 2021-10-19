package cn.crtech.cooperop.application.dao;

import java.util.Date;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class SystemMessageDao extends BaseDao {
	public final static String TABLE_NAME = "system_message";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select                                                         ");
		setParameter(params, "maxSendNum", " top "+ params.get("maxSendNum"), sql);
		sql.append(" * from    "+ TABLE_NAME + "(nolock)");
		sql.append(" where   1=1 ");
		setParameter(params, "tunnel_id", " and tunnel_id=:tunnel_id", sql);
		setParameter(params, "state", " and state=:state", sql);
		setParameter(params, "type", " and type=:type", sql);
		setParameter(params, "xx", " and content is not null and send_to is not null", sql);
	    return executeQuery(sql.toString(), params);
	}
	public Result messagenum(Map<String, Object> params) throws Exception {
		params.put("system_user_id", user().getId());
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(1) as num                                                          ");
		sql.append("  from    "+ TABLE_NAME + "(nolock) t");
		setParameter(params, "ignore", " left join system_bobmessage_ignore sbi on sbi.message_key=t.id and sbi.type='3' and sbi.system_user_id=:system_user_id ", sql);
		sql.append(" where   t.read_time is null and t.send_to=:system_user_id");
		setParameter(params, "type", " and t.type=:type", sql);
		setParameter(params, "ignore", " and sbi.message_key is not null ", sql);
	    return executeQuery(sql.toString(), params);
	}
	public Map<String, Object> get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  *        ");
		sql.append(" from    "+ TABLE_NAME + "(nolock) where id =:id");
	    return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		params.put("create_time", "sysdate");
	    executeInsert(TABLE_NAME, params);
	    return getSeqVal(TABLE_NAME);
	}
	public void ignoreAll(Map<String, Object> params) throws Exception {
		params.put("system_user_id", user().getId());
		params.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		//待办
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into system_bobmessage_ignore (message_key, type, system_user_id, create_time) ");
		sql.append(" select distinct task_id as message_key, '1' as type , :system_user_id ,:create_time as create_time from  wf_task_actor(nolock) where actor_id =:system_user_id");
		execute(sql.toString(), params);
		//通知公告
		sql = new StringBuffer();
		sql.append(" insert into system_bobmessage_ignore (message_key, type, system_user_id, create_time)         ");
		sql.append(" select  n.id as message_key, '1' as type , :system_user_id ,:create_time as create_time       ");
		sql.append(" from    notice(nolock) n        ,                                                             ");
		sql.append(" (select distinct notice_id                                                                    ");            
		sql.append(" 		                 from   notice_sendto(nolock) ns                                       ");                                  
		sql.append(" 		                 where  ( ns.target = 'A'                                              ");                      
		sql.append(" 		                              or ( ns.target = 'U'                                     ");                          
		sql.append(" 		                                   and ns.send_to = :system_user_id                    ");                      
		sql.append(" 		                                 )                                                     ");                          
		sql.append(" 		                              or ( ns.target = 'D'                                     ");                          
		sql.append(" 		                                   and ( select count(1)                               ");                          
		sql.append(" 		                                         from   v_system_user(nolock) vsu              ");                                  
		sql.append(" where  (vsu.departments like '%,'+ns.send_to+'%' or vsu.department=ns.send_to)                ");
		sql.append("                                                 and vsu.id = :system_user_id                  ");               
		sql.append(" 		                                       ) > 0                                           ");                          
		sql.append(" 		                                 )                                                     ");                          
		sql.append(" 		                              or ( ns.target = 'G'                                     ");                          
		sql.append(" 		                                   and ( select count(1)                               ");                          
		sql.append(" 		                                         from   system_user_group_user(nolock) sugu    ");                                  
		sql.append("  where  convert(varchar(16), sugu.system_user_group_id) = ns.send_to                          ");
		sql.append("                                                 and sugu.system_user_id = :system_user_id     ");               
		sql.append(" 		                                       ) > 0                                           ");                          
		sql.append(" 		                                 ))      )  as t                                       ");
		sql.append(" where  n.id=t.notice_id and is_delete = 0        and published = 1                            ");
		sql.append("     and ( select  count(1)                                                                    ");              
		sql.append(" 		                from    notice_read(nolock) nr                                         ");                                   
		sql.append(" 		                where   nr.notice_id = n.id                                            ");                           
		sql.append(" 		                        and nr.system_user_id = :system_user_id                        ");                      
		sql.append(" 		              ) = 0                                                                    ");                               
		sql.append(" 		    and pdate <= getdate()                                                             ");                           
		sql.append(" 		    and ( edate >= getdate()                                                           ");                           
		sql.append(" 		          or isnull(edate, '') = ''                                                    ");                           
		sql.append(" 		        )                                                                              ");                           
		                                                                        
		execute(sql.toString(), params);
		//站内消息
		sql = new StringBuffer();
		sql.append(" insert into system_bobmessage_ignore (message_key, type, system_user_id, create_time) ");
		sql.append(" select id as message_key, '3' as type , :system_user_id ,'' as create_time from  system_message(nolock) where send_to =:system_user_id and read_time is null");
		execute(sql.toString(), params);
	}
	public void update(Map<String, Object> params) throws Exception {
		Record parameters = new Record(params);
		parameters.remove("id");
		parameters.put("send_time", "sysdate");
		Record conditions = new Record();
		conditions.put("id", params.get("id"));
	    executeUpdate(TABLE_NAME, parameters, conditions);
	}
}
