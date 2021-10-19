package cn.crtech.cooperop.application.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class NotificationDao extends BaseDao {
	
	public Result queryMine(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select  n.author,n.id,n.subject,n.attach_files,n.pdate,n.edate,n.published,n.modifier,n.modify_time,n.is_delete,n.header_image ");
		sql.append(" ,vsu.name as modifier_name ");
		sql.append("    ,case when ( select  count(1)                                                                                 ");
		sql.append("                from    notice_read(nolock) nr                                                                           ");
		sql.append("                where   nr.notice_id = n.id                                                                      ");
		sql.append("                        and nr.system_user_id = :system_user_id                                                          ");
		sql.append("              ) > 0 then 1                                                                                       ");
		sql.append("         else 0                                                                                                  ");
		sql.append("    end as readflag ,                                                                                            ");
		sql.append("    ( select    max(can_download)                                                                                ");
		sql.append("      from      notice_sendto(nolock) ns                                                                                 ");
		sql.append("      where     ns.notice_id = n.id                                                                              ");
		sql.append("                and ( ns.target = 'A'                                                                            ");
		sql.append("                      or ( ns.target = 'U'                                                                       ");
		sql.append("                           and ns.send_to = :system_user_id                                                              ");
		sql.append("                         )                                                                                       ");
		sql.append("                      or ( ns.target = 'D'                                                                       ");
		sql.append("                           and ( select count(1)                                                                 ");
		sql.append("                                 from   v_system_user(nolock) vsu                                                        ");
		if ("MSSQL".equals(conn.getDbType())) {
			sql.append("                                         where  (vsu.departments like '%,'+ns.send_to+'%' or vsu.department=ns.send_to)                ");
		} else if ("ORACLE".equals(conn.getDbType())) {
			
		} else if ("MySQL".equals(conn.getDbType())) {
			sql.append("                                         where  (vsu.departments like concat('%,',ns.send_to,'%') or vsu.department=ns.send_to)                ");
		}
		sql.append("                                        and vsu.id = :system_user_id                                                     ");
		sql.append("                               ) > 0                                                                             ");
		sql.append("                         )                                                                                       ");
		sql.append("                      or ( ns.target = 'G'                                                                       ");
		sql.append("                           and ( select count(1)                                                                 ");
		sql.append("                                 from   system_user_group_user(nolock) sugu                                              ");
		if ("MSSQL".equals(conn.getDbType())) {
			sql.append("                                         where  convert(varchar(16), sugu.system_user_group_id) = ns.send_to     ");
		} else {
			sql.append("                                         where  sugu.system_user_group_id = ns.send_to     ");
		}
		sql.append("                                        and sugu.system_user_id = :system_user_id                                        ");
		sql.append("                               ) > 0                                                                             ");
		sql.append("                         )                                                                                       ");
		sql.append("                    )                                                                                            ");
		sql.append("    ) as can_download                                                                                            ");
		sql.append("from    notice(nolock) n                                                                                         ");
//		
		sql.append("    ,( select distinct notice_id                                                                                ");
		sql.append("                 from   notice_sendto(nolock) ns                                                                         ");
		sql.append("                 where ( ns.target = 'A'                                                                    ");
		sql.append("                              or ( ns.target = 'U'                                                               ");
		sql.append("                                   and ns.send_to = :system_user_id                                                      ");
		sql.append("                                 )                                                                               ");
		sql.append("                              or ( ns.target = 'D'                                                               ");
		sql.append("                                   and ( select count(1)                                                         ");
		sql.append("                                         from   v_system_user(nolock) vsu                                                ");
		if ("MSSQL".equals(conn.getDbType())) {
			sql.append("                                         where  (vsu.departments like '%,'+ns.send_to+'%' or vsu.department=ns.send_to)                ");
		} else if ("ORACLE".equals(conn.getDbType())) {
			
		} else if ("MySQL".equals(conn.getDbType())) {
			sql.append("                                         where  (vsu.departments like concat('%,',ns.send_to,'%') or vsu.department=ns.send_to)                ");
		}
		sql.append("                                                and vsu.id = :system_user_id                                             ");
		sql.append("                                       ) > 0                                                                     ");
		sql.append("                                 )                                                                               ");
		sql.append("                              or ( ns.target = 'G'                                                               ");
		sql.append("                                   and ( select count(1)                                                         ");
		sql.append("                                         from   system_user_group_user(nolock) sugu                                      ");
		if ("MSSQL".equals(conn.getDbType())) {
			sql.append("                                         where  convert(varchar(16), sugu.system_user_group_id) = ns.send_to     ");
		} else {
			sql.append("                                         where  sugu.system_user_group_id = ns.send_to     ");
		}
		sql.append("                                                and sugu.system_user_id = :system_user_id                                ");
		sql.append("                                       ) > 0                                                                     ");
		sql.append("                                 )                                                                               ");
		sql.append("                            ) )  as t                                                                                ");
		sql.append(" ,v_system_user(nolock) as vsu ");
		
		
		sql.append("where  n.id=t.notice_id and is_delete = 0  and  n.modifier=vsu.id                       ");
		if(Integer.parseInt(params.get("isread").toString())==0){
			sql.append(" 	AND (CASE WHEN ( SELECT  COUNT(1) ");
		    sql.append("             FROM    notice_read (NOLOCK) nr ");
		    sql.append("             WHERE   nr.notice_id = n.id ");
		    sql.append("                     AND nr.system_user_id = :system_user_id ");
		    sql.append("           ) > 0 THEN 1 ");
		    sql.append("      ELSE 0 ");
		    sql.append(" END) = :isread ");
		}
		setParameter(params, "keyword", " and n.subject like '%'+:keyword+'%' ", sql);
		sql.append("    and published = 1                                                                                            ");
//
		
		sql.append("    and pdate <= getdate()                                                                                       ");
		sql.append("    and ( edate >= getdate()                                                                                     ");
		sql.append("          or isnull(edate, '') = ''                                                                              ");
		sql.append("        )                                                                                                        ");
		params.put("system_user_id", user().getId());
		long start = CommonFun.isNe(params.get("start")) ? 1 : Long.parseLong((String)params.get("start"));
	    int limit = CommonFun.isNe(params.get("limit")) ? 0 : Integer.parseInt((String)params.get("limit"));
	    Result rs = executeQueryLimitTotal(sql.toString(), params, (String)params.remove("totals"), start, limit);
	    return rs;
	}
	public Record getMine(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select  n.* ,                                                                                                    ");
		sql.append("    case when ( select  count(1)                                                                                 ");
		sql.append("                from    notice_read(nolock) nr                                                                           ");
		sql.append("                where   nr.notice_id = n.id                                                                      ");
		sql.append("                        and nr.system_user_id = :system_user_id                                                          ");
		sql.append("              ) > 0 then 1                                                                                       ");
		sql.append("         else 0                                                                                                  ");
		sql.append("    end as readflag ,                                                                                            ");
		sql.append("    ( select    max(can_download)                                                                                ");
		sql.append("      from      notice_sendto(nolock) ns                                                                                 ");
		sql.append("      where     ns.notice_id = n.id                                                                              ");
		sql.append("                and ( ns.target = 'A'                                                                            ");
		sql.append("                      or ( ns.target = 'U'                                                                       ");
		sql.append("                           and ns.send_to = :system_user_id                                                              ");
		sql.append("                         )                                                                                       ");
		sql.append("                      or ( ns.target = 'D'                                                                       ");
		sql.append("                           and ( select count(1)                                                                 ");
		sql.append("                                 from   v_system_user(nolock) vsu                                                        ");
		if ("MSSQL".equals(conn.getDbType())) {
			sql.append("                                         where  (vsu.departments like '%,'+ns.send_to+'%' or vsu.department=ns.send_to)                ");
		} else if ("ORACLE".equals(conn.getDbType())) {
			
		} else if ("MySQL".equals(conn.getDbType())) {
			sql.append("                                         where  (vsu.departments like concat('%,',ns.send_to,'%') or vsu.department=ns.send_to)                ");
		}
		sql.append("                                        and vsu.id = :system_user_id                                                     ");
		sql.append("                               ) > 0                                                                             ");
		sql.append("                         )                                                                                       ");
		sql.append("                      or ( ns.target = 'G'                                                                       ");
		sql.append("                           and ( select count(1)                                                                 ");
		sql.append("                                 from   system_user_group_user(nolock) sugu                                              ");
		if ("MSSQL".equals(conn.getDbType())) {
			sql.append("                                         where  convert(varchar(16), sugu.system_user_group_id) = ns.send_to     ");
		} else {
			sql.append("                                         where  sugu.system_user_group_id = ns.send_to     ");
		}
		sql.append("                                        and sugu.system_user_id = :system_user_id                                        ");
		sql.append("                               ) > 0                                                                             ");
		sql.append("                         )                                                                                       ");
		sql.append("                    )                                                                                            ");
		sql.append("    ) as can_download                                                                                            ");
		sql.append("from    notice(nolock) n                                                                                         ");
		sql.append("where   is_delete = 0                                                                                            ");
		if (CommonFun.isNe(params.get("preview"))) {
			sql.append("    and published = 1                                                                                            ");
			sql.append("    and exists ( select notice_id                                                                                ");
			sql.append("                 from   notice_sendto(nolock) ns                                                                         ");
			sql.append("                 where  ns.notice_id = n.id                                                                      ");
			sql.append("                        and ( ns.target = 'A'                                                                    ");
			sql.append("                              or ( ns.target = 'U'                                                               ");
			sql.append("                                   and ns.send_to = :system_user_id                                                      ");
			sql.append("                                 )                                                                               ");
			sql.append("                              or ( ns.target = 'D'                                                               ");
			sql.append("                                   and ( select count(1)                                                         ");
			sql.append("                                         from   v_system_user(nolock) vsu                                                ");
			if ("MSSQL".equals(conn.getDbType())) {
				sql.append("                                         where  (vsu.departments like '%,'+ns.send_to+'%' or vsu.department=ns.send_to)                ");
			} else if ("ORACLE".equals(conn.getDbType())) {
				
			} else if ("MySQL".equals(conn.getDbType())) {
				sql.append("                                         where  (vsu.departments like concat('%,',ns.send_to,'%') or vsu.department=ns.send_to)                ");
			}
			sql.append("                                                and vsu.id = :system_user_id                                             ");
			sql.append("                                       ) > 0                                                                     ");
			sql.append("                                 )                                                                               ");
			sql.append("                              or ( ns.target = 'G'                                                               ");
			sql.append("                                   and ( select count(1)                                                         ");
			sql.append("                                         from   system_user_group_user(nolock) sugu                                      ");
			if ("MSSQL".equals(conn.getDbType())) {
				sql.append("                                         where  convert(varchar(16), sugu.system_user_group_id) = ns.send_to     ");
			} else {
				sql.append("                                         where  sugu.system_user_group_id = ns.send_to     ");
			}
			sql.append("                                                and sugu.system_user_id = :system_user_id                                ");
			sql.append("                                       ) > 0                                                                     ");
			sql.append("                                 )                                                                               ");
			sql.append("                            ) )                                                                                  ");
			sql.append("    and pdate <= getdate()                                                                                       ");
			sql.append("    and ( edate >= getdate()                                                                                     ");
			sql.append("          or isnull(edate, '') = ''                                                                                     ");
			sql.append("        )                                                                                                 ");
		}
		sql.append("    and n.id = :id                                                                                       ");
		params.put("system_user_id", user().getId());
	    return executeQuerySingleRecord(sql.toString(), params);
	}
	public Record lastdetail(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select                                                                                                    ");
		
		if ("MSSQL".equals(conn.getDbType())) {
			sql.append(" top 1          ");
		} else if ("ORACLE".equals(conn.getDbType())) {
			
		} else if ("MySQL".equals(conn.getDbType())) {
			params.put("limit", 1);
		}
		
		sql.append("                                                                                                  ");
		sql.append("  n.* ,   case when ( select  count(1)                                                                                 ");
		sql.append("                from    notice_read(nolock) nr                                                                           ");
		sql.append("                where   nr.notice_id = n.id                                                                      ");
		sql.append("                        and nr.system_user_id = :system_user_id                                                          ");
		sql.append("              ) > 0 then 1                                                                                       ");
		sql.append("         else 0                                                                                                  ");
		sql.append("    end as readflag ,                                                                                            ");
		sql.append("    ( select    max(can_download)                                                                                ");
		sql.append("      from      notice_sendto(nolock) ns                                                                                 ");
		sql.append("      where     ns.notice_id = n.id                                                                              ");
		sql.append("                and ( ns.target = 'A'                                                                            ");
		sql.append("                      or ( ns.target = 'U'                                                                       ");
		sql.append("                           and ns.send_to = :system_user_id                                                              ");
		sql.append("                         )                                                                                       ");
		sql.append("                      or ( ns.target = 'D'                                                                       ");
		sql.append("                           and ( select count(1)                                                                 ");
		sql.append("                                 from   v_system_user(nolock) vsu                                                        ");
		if ("MSSQL".equals(conn.getDbType())) {
			sql.append("                                         where  (vsu.departments like '%,'+ns.send_to+'%' or vsu.department=ns.send_to)                ");
		} else if ("ORACLE".equals(conn.getDbType())) {
			
		} else if ("MySQL".equals(conn.getDbType())) {
			sql.append("                                         where  (vsu.departments like concat('%,',ns.send_to,'%') or vsu.department=ns.send_to)                ");
		}
		sql.append("                                        and vsu.id = :system_user_id                                                     ");
		sql.append("                               ) > 0                                                                             ");
		sql.append("                         )                                                                                       ");
		sql.append("                      or ( ns.target = 'G'                                                                       ");
		sql.append("                           and ( select count(1)                                                                 ");
		sql.append("                                 from   system_user_group_user(nolock) sugu                                              ");
		if ("MSSQL".equals(conn.getDbType())) {
			sql.append("                                         where  convert(varchar(16), sugu.system_user_group_id) = ns.send_to     ");
		} else {
			sql.append("                                         where  sugu.system_user_group_id = ns.send_to     ");
		}
		sql.append("                                        and sugu.system_user_id = :system_user_id                                        ");
		sql.append("                               ) > 0                                                                             ");
		sql.append("                         )                                                                                       ");
		sql.append("                    )                                                                                            ");
		sql.append("    ) as can_download                                                                                            ");
		sql.append("from    notice(nolock) n                                                                                         ");
		sql.append("where   is_delete = 0                                                                                            ");
		sql.append("    and published = 1                                                                                            ");
		sql.append("    and exists ( select notice_id                                                                                ");
		sql.append("                 from   notice_sendto(nolock) ns                                                                         ");
		sql.append("                 where  ns.notice_id = n.id                                                                      ");
		sql.append("                        and ( ns.target = 'A'                                                                    ");
		sql.append("                              or ( ns.target = 'U'                                                               ");
		sql.append("                                   and ns.send_to = :system_user_id                                                      ");
		sql.append("                                 )                                                                               ");
		sql.append("                              or ( ns.target = 'D'                                                               ");
		sql.append("                                   and ( select count(1)                                                         ");
		sql.append("                                         from   v_system_user(nolock) vsu                                                ");
		if ("MSSQL".equals(conn.getDbType())) {
			sql.append("                                         where  (vsu.departments like '%,'+ns.send_to+'%' or vsu.department=ns.send_to)                ");
		} else if ("ORACLE".equals(conn.getDbType())) {
			
		} else if ("MySQL".equals(conn.getDbType())) {
			sql.append("                                         where  (vsu.departments like concat('%,',ns.send_to,'%') or vsu.department=ns.send_to)                ");
		}
		sql.append("                                                and vsu.id = :system_user_id                                             ");
		sql.append("                                       ) > 0                                                                     ");
		sql.append("                                 )                                                                               ");
		sql.append("                              or ( ns.target = 'G'                                                               ");
		sql.append("                                   and ( select count(1)                                                         ");
		sql.append("                                         from   system_user_group_user(nolock) sugu                                      ");
		if ("MSSQL".equals(conn.getDbType())) {
			sql.append("                                         where  convert(varchar(16), sugu.system_user_group_id) = ns.send_to     ");
		} else {
			sql.append("                                         where  sugu.system_user_group_id = ns.send_to     ");
		}
		sql.append("                                                and sugu.system_user_id = :system_user_id                                ");
		sql.append("                                       ) > 0                                                                     ");
		sql.append("                                 )                                                                               ");
		sql.append("                            ) )                                                                                  ");
		sql.append("    and pdate <= getdate()                                                                                       ");
		sql.append("    and ( edate >= getdate()                                                                                     ");
		sql.append("          or isnull(edate, '') = ''                                                                              ");
		sql.append("        )                                                                                                        ");
		sql.append(" order by pdate desc ");
		params.put("system_user_id", user().getId());
	    return executeQuerySingleRecord(sql.toString(), params);
	}
	public int queryNotificationCount(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select  count(1) as num                                                                                                   ");
		sql.append("from    notice(nolock) n                                                                                         ");
		setParameter(params, "ignore", " left join system_bobmessage_ignore sbi on sbi.message_key=n.id and sbi.type='2' and sbi.system_user_id=:system_user_id ", sql);
		sql.append("   ,( select distinct notice_id                                                                                ");
		sql.append("                 from   notice_sendto(nolock) ns                                                                         ");
		sql.append("                 where  ( ns.target = 'A'                                                                    ");
		sql.append("                              or ( ns.target = 'U'                                                               ");
		sql.append("                                   and ns.send_to = :system_user_id                                                      ");
		sql.append("                                 )                                                                               ");
		sql.append("                              or ( ns.target = 'D'                                                               ");
		sql.append("                                   and ( select count(1)                                                         ");
		sql.append("                                         from   v_system_user(nolock) vsu                                                ");
		if ("MSSQL".equals(conn.getDbType())) {
			sql.append("                                         where  (vsu.departments like '%,'+ns.send_to+'%' or vsu.department=ns.send_to)                ");
		} else if ("ORACLE".equals(conn.getDbType())) {
			
		} else if ("MySQL".equals(conn.getDbType())) {
			sql.append("                                         where  (vsu.departments like concat('%,',ns.send_to,'%') or vsu.department=ns.send_to)                ");
		}
		sql.append("                                                and vsu.id = :system_user_id                                             ");
		sql.append("                                       ) > 0                                                                     ");
		sql.append("                                 )                                                                               ");
		sql.append("                              or ( ns.target = 'G'                                                               ");
		sql.append("                                   and ( select count(1)                                                         ");
		sql.append("                                         from   system_user_group_user(nolock) sugu                                      ");
		if ("MSSQL".equals(conn.getDbType())) {
			sql.append("                                         where  convert(varchar(16), sugu.system_user_group_id) = ns.send_to     ");
		} else {
			sql.append("                                         where  sugu.system_user_group_id = ns.send_to     ");
		}
		sql.append("                                                and sugu.system_user_id = :system_user_id                                ");
		sql.append("                                       ) > 0                                                                     ");
		sql.append("                                 )                                                                               ");
		sql.append("                            ) )  as t                                                                                ");
		

		sql.append("where  n.id=t.notice_id and is_delete = 0                                                                                            ");
		sql.append("    and published = 1                                                                                            ");
		sql.append("    and ( select  count(1)                                                                                 ");
		sql.append("                from    notice_read(nolock) nr                                                                           ");
		sql.append("                where   nr.notice_id = n.id                                                                      ");
		sql.append("                        and nr.system_user_id = :system_user_id                                                          ");
		sql.append("              ) = 0                                                                                                  ");
		sql.append("    and pdate <= getdate()                                                                                       ");
		sql.append("    and ( edate >= getdate()                                                                                     ");
		sql.append("          or isnull(edate, '') = ''                                                                              ");
		sql.append("        )                                                                                                        ");
		params.put("system_user_id", user().getId());
		setParameter(params, "ignore", " and sbi.message_key is not null ", sql);
	    Record record = executeQuerySingleRecord(sql.toString(), params);
	    if (record == null) return 0;
	    return record.getInt("num");
	}

	public Record getRead(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from notice_read where notice_id=:notice_id and system_user_id=:system_user_id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public void saveRead(int notification_id) throws Exception {
		Record params = new Record();
		params.put("notice_id", notification_id);
		params.put("system_user_id", user().getId());
		params.put("read_time", "sysdate");
		params.put("read_counts", 1);
		params.put("last_read_time", "sysdate");
	    executeInsert("notice_read", params);
	}
	
	public int updateRead(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("system_user_id",params.remove("system_user_id"));
		r.put("notice_id",params.remove("notice_id"));
		params.remove("preview");
		params.remove("ismodal");
		return executeUpdate("notice_read", params, r);
	}
	
	public Result queryHasRead(Map<String,Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select f.*,v.name as system_user_name,v.department_name,v.position_name from notice_read f ");
		sql.append(" left join v_system_user_hr v on v.id=f.system_user_id ");
		sql.append(" where f.notice_id=:notice_id ");
		setParameter(params, "department", " and v.department =:department", sql);
		setParameter(params, "position", " and v.position =:position", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result queryNotRead(Map<String,Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from v_system_user_hr where state=1 and type='employee' ");
		setParameter(params, "hasRead", " and id not in (:hasRead)", sql);
		setParameter(params, "department", " and department =:department", sql);
		setParameter(params, "position", " and position =:position", sql);
		return executeQueryLimit(sql.toString(), params);
	}
}
