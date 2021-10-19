package cn.crtech.cooperop.application.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.bean.Account;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.session.Session;
import cn.crtech.cooperop.bus.util.CRPasswordEncrypt;
import cn.crtech.cooperop.bus.util.CommonFun;

public class UserDao extends BaseDao {
	private final static String USER_TYPE_SUPER = "CRY";
	
	public Record loginSupper(String userid, String password) throws Exception {
		Record params = new Record();
		params.put("userid", userid);
		params.put("password", CRPasswordEncrypt.Encryptstring(password));
		params.put("type", USER_TYPE_SUPER);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_system_user(nolock) where no = :userid and password = :password and type = :type ");
		Record rtn = executeQuerySingleRecord(sql.toString(), params);
		return rtn;
	}

	public Record login(String user_type, String userid, String password) throws Exception {
		Record params = new Record();
		params.put("userid", userid);
		params.put("password", CRPasswordEncrypt.Encryptstring(password));
		params.put("user_type", user_type);
		StringBuffer sql = new StringBuffer(); //type = :user_type and 
		sql.append("select * from v_system_user(nolock) where no = :userid and password = :password");
		setParameter(params, "user_type", " and type = :user_type ", sql);
		Record rtn = executeQuerySingleRecord(sql.toString(), params);
		if (rtn != null) return rtn;
		sql = new StringBuffer();
		sql.append("select * from v_system_user(nolock) where mobile = :userid and password = :password");
		setParameter(params, "user_type", " and type = :user_type ", sql);
		rtn = executeQuerySingleRecord(sql.toString(), params);
		if (rtn != null) return rtn;
		sql = new StringBuffer();
		sql.append("select * from v_system_user(nolock) where id = :userid and password = :password");
		setParameter(params, "user_type", " and type = :user_type ", sql);
		rtn = executeQuerySingleRecord(sql.toString(), params);
		if (rtn != null) return rtn;
		sql = new StringBuffer();
		sql.append("select * from v_system_user(nolock) where email = :userid and password = :password");
		setParameter(params, "user_type", " and type = :user_type ", sql);
		rtn = executeQuerySingleRecord(sql.toString(), params);
		
		if (rtn != null) return rtn;
		sql = new StringBuffer();
		sql.append("select * from v_system_user(nolock) where name = :userid and password = :password");
		setParameter(params, "user_type", " and type = :user_type ", sql);
		rtn = executeQuerySingleRecord(sql.toString(), params);
		return rtn;
	}
	public Record weixinlogin(String openid) throws Exception {
		Record params = new Record();
		params.put("openid", openid);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_system_user(nolock) where openid = :openid");
		Record rtn = executeQuerySingleRecord(sql.toString(), params);
		return rtn;
	}

	public Record weixinlogin(String user_type, String openid, String unionid) throws Exception {
		Record params = new Record();
		params.put("unionid", unionid);
		params.put("openid", openid);
		params.put("user_type", user_type);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_system_user(nolock) where openid = :openid and unionid=:unionid");
		setParameter(params, "user_type", " and type = :user_type ", sql);
		Record rtn = executeQuerySingleRecord(sql.toString(), params);
		return rtn;
	}

	public Record sso(String userid) throws Exception {
		Record params = new Record();
		params.put("userid", userid);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_system_user(nolock) where id = :userid");
		Record rtn = executeQuerySingleRecord(sql.toString(), params);
		if (rtn != null) return rtn;
		sql = new StringBuffer();
		sql.append("select * from v_system_user(nolock) where no = :userid");
		rtn = executeQuerySingleRecord(sql.toString(), params);
		if (rtn != null) return rtn;
		sql = new StringBuffer();
		sql.append("select * from v_system_user(nolock) where mobile = :userid");
		rtn = executeQuerySingleRecord(sql.toString(), params);
		if (rtn != null) return rtn;
		sql = new StringBuffer();
		sql.append("select * from v_system_user(nolock) where email = :userid");
		rtn = executeQuerySingleRecord(sql.toString(), params);
		return rtn;
	}
	
	public Result queryUserPlugIn(String userid) throws Exception {
		Record params = new Record();
		params.put("userid", userid);
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct plugin                         ");
		sql.append("  from system_role_popedom(nolock) srp                 ");
		sql.append("  left join system_popedom(nolock) sp                  ");
		sql.append("    on sp.id = srp.system_popedom_id           ");
		sql.append(" where srp.system_role_id in                   ");
		sql.append("       (select distinct system_role_id         ");
		sql.append("          from system_rule(nolock)                     ");
		sql.append("         where system_user_id = :userid)       ");
		sql.append("    or srp.system_role_id =                   ");
		
		sql.append(" (select default_role from system_user_type sut ");
		sql.append("        inner join v_system_user vsu on vsu.type=sut.type");
		sql.append("         where id= :userid)      ");
		//sql.append("    or srp.system_role_id in                   ");
		//sql.append("       (select distinct case when((select jigid from v_system_user where id=:userid)='999') then default_role1 else default_role end           ");   //_" + userid.substring(0, 3));
		//sql.append("          from system_product(nolock))                 ");
		return executeQuery(sql.toString(), params);
	}
	public Result queryUserPage(String userid) throws Exception {
		Record params = new Record();
		params.put("userid", userid);
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct code                           ");
		sql.append("  from system_role_popedom(nolock) srp                 ");
		sql.append("  inner join system_popedom(nolock) sp                  ");
		sql.append("    on sp.id = srp.system_popedom_id           ");
		sql.append(" where srp.system_role_id in                   ");
		sql.append("       (select distinct system_role_id         ");
		sql.append("          from system_rule(nolock)                     ");
		sql.append("         where system_user_id = :userid)       ");
		sql.append("    or srp.system_role_id =                   ");
		sql.append(" (select default_role from system_user_type sut ");
		sql.append("        inner join v_system_user vsu on vsu.type=sut.type");
		sql.append("         where id= :userid)      ");
//		sql.append("    or srp.system_role_id in                   ");
//		sql.append("       (select distinct case when((select jigid from v_system_user where id=:userid)='999') then default_role1 else default_role end           ");   //_" + userid.substring(0, 3));
//		sql.append("          from system_product(nolock))                 ");
		sql.append(" order by code                                 ");
		return executeQuery(sql.toString(), params);
	}
	public Result getMenuTree(String rootPopedom, String searchName) throws Exception {
		Record params = new Record();
		params.put("userid", user().getId());
		params.put("jigid", CommonFun.isNe(Session.getSession().get("curr_jigid"))?user().getJigid():Session.getSession().get("curr_jigid"));
		params.put("searchName", searchName);
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.*,sm.system_popedom_id as is_store                                   ");
		sql.append("   from system_popedom(nolock) a                                         ");
		sql.append("   left join system_user_store_menus sm on sm.system_popedom_id = a.id          ");
		sql.append("   and sm.system_user_id = :userid          ");
		sql.append("  where is_menu = 1                                              ");
		if (!CommonFun.isNe(rootPopedom)) {
			params.put("rootPopedom", rootPopedom);
			sql.append("    and (',' + ids + ',' like '%,' + :rootPopedom + ',%')        ");
		}
		if (!user().isSupperUser()) {
			sql.append(" AND EXISTS ( SELECT 1 ");
	        sql.append("         FROM   system_role (NOLOCK) sro ");
	        sql.append("             INNER JOIN system_role_popedom (NOLOCK) srp ON sro.id = srp.system_role_id ");
	        sql.append("             INNER JOIN ( SELECT DISTINCT ");
	        sql.append("                                 system_role_id ");
	        sql.append("                             FROM   system_rule(NOLOCK) ");
	        sql.append("                             WHERE  system_user_id = :userid ");
	        sql.append("                             UNION ALL ");
	        sql.append("                             SELECT default_role ");
	        sql.append("                             FROM   system_user_type sut ( NOLOCK ) ");
	        sql.append("                                 INNER JOIN v_system_user (NOLOCK) vsu ON vsu.type = sut.type ");
	        sql.append("                             WHERE  id = :userid ");
	        sql.append("                         ) sru ON sro.id = sru.system_role_id ");
	        sql.append("         WHERE  srp.system_popedom_id = a.id ) ");
//			sql.append("    and (a.id in (select distinct system_popedom_id              ");
//			sql.append("                   from system_role_popedom(nolock) srp                  ");
//			sql.append("                   left join system_rule(nolock) sr                      ");
//			sql.append("                     on srp.system_role_id = sr.system_role_id   ");
//			sql.append("                  where sr.system_user_id = :userid)             ");
//			sql.append("     or a.id in (select distinct system_popedom_id               ");
//			sql.append("                   from system_role_popedom(nolock) srp                  ");
//			sql.append(" inner join ");
//			sql.append(" (select default_role from system_user_type sut ");
//			sql.append("        inner join v_system_user vsu on vsu.type=sut.type");
//			sql.append("         where id= :userid) t on t.default_role = srp.system_role_id    ");
			
			/*sql.append("                  inner join system_product(nolock) sp                   ");
			if("999".equals(user().getJigid())){
				sql.append("                     on srp.system_role_id = sp.default_role1     ");  //_" + user().getType());
			}else{
				sql.append("                     on srp.system_role_id = sp.default_role     ");  //_" + user().getType());
			}*/
			
//			sql.append("                ))             ");
		}
		setParameter(params, "searchName", " and name like '%'+:searchName+'%'", sql);
		sql.append("  order by a.order_nos                                            ");
		return executeQuery(sql.toString(), params);
	
	}
	public Result getMenuTree(String rootPopedom) throws Exception {
		return getMenuTree(rootPopedom, null);
	}

	public Result getMenu(String popedom) throws Exception {
		Record params = new Record();
		params.put("userid", user().getId());
		params.put("popedom", popedom);
		StringBuffer sql = new StringBuffer();
		sql.append("  select a.*                                                    ");
		sql.append("  from   system_popedom(nolock) a                                       ");
		sql.append("  where  exists ( select 1 from system_popedom(nolock) t ");
		sql.append("                                      where t.id = :popedom     ");
		if ("MSSQL".equals(conn.getDbType())) {
			sql.append("                                        and ',' + t.ids + ',' like '%,' + a.id + ',%'     ");
		} else if ("ORACLE".equals(conn.getDbType())) {
			
		} else if ("MySQL".equals(conn.getDbType())) {
			sql.append("                                        and concat(',', t.ids, ',') like concat('%,', a.id ,',%')     ");
		}
		sql.append("                 )                   ");
		sql.append("  order by a.order_nos                                          ");
		return executeQuery(sql.toString(), params);
	}
	public Result getMenus(Map<String, Object> map) throws Exception {
		Record params = new Record();
		params.put("userid", user().getId());
		StringBuffer sql = new StringBuffer();
		sql.append("  select a.*                                                    ");
		sql.append("  from   system_popedom(nolock) a   where a.is_menu=1           ");
		setParameter(map, "tel_menu", " and tel_menu=1", sql);
		if (!CommonFun.isNe(map.get("popedom"))) {
			params.put("popedom", map.get("popedom"));
			sql.append("  and  (a.ids like '%'+:popedom+',%' or a.id=:popedom)       ");
		}
		if(!CommonFun.isNe(map.get("query_level"))){
			params.put("query_level", map.get("query_level"));
			sql.append(" and a.level<=:query_level");
		}
		sql.append("    and (a.id in (select distinct system_popedom_id              ");
		sql.append("                   from system_role_popedom(nolock) srp                  ");
		sql.append("                   left join system_rule(nolock) sr                      ");
		sql.append("                     on srp.system_role_id = sr.system_role_id   ");
		sql.append("                  where sr.system_user_id = :userid)             ");
		sql.append("     or a.id in (select distinct system_popedom_id               ");
		sql.append("                   from system_role_popedom(nolock) srp                  ");
		sql.append(" inner join ");
		sql.append(" (select default_role from system_user_type sut ");
		sql.append("        inner join v_system_user vsu on vsu.type=sut.type");
		sql.append("         where id= :userid) t on t.default_role = srp.system_role_id    ");
		sql.append("                ))             ");
		sql.append("  order by a.order_nos                                          ");
		return executeQuery(sql.toString(), params);
	}
	public Result getSelfMenus(Map<String, Object> map) throws Exception {
		Record params = new Record();
		params.put("userid", user().getId());
		StringBuffer sql = new StringBuffer();
		sql.append("  select                                                 ");
		if(!CommonFun.isNe(map.get("query_product"))){
			sql.append("  sp.name ,sp.code                                                    ");
		}else{
			sql.append("   a.* ,ph.id as ph_id                                                 ");
		}
		sql.append("  from   system_popedom(nolock) a            ");
		sql.append(" left join personal_homepage ph on ph.type='M' and ph.fk_id= cast(a.id as varchar)   ");
		sql.append(" left join system_product sp on sp.code= a.plugin ");
		sql.append("  where a.is_menu=1 ");
		
		sql.append("  and a.child_num =0 and plugin is not null         ");
		/*sql.append("    and (a.id in (select distinct system_popedom_id              ");
		sql.append("                   from system_role_popedom(nolock) srp                  ");
		sql.append("                   left join system_rule(nolock) sr                      ");
		sql.append("                     on srp.system_role_id = sr.system_role_id   ");
		sql.append("                  where sr.system_user_id = :userid)             ");
		sql.append("     or a.id in (select distinct system_popedom_id               ");
		sql.append("                   from system_role_popedom(nolock) srp                  ");
		sql.append(" inner join ");
		sql.append(" (select default_role from system_user_type sut ");
		sql.append("        inner join v_system_user vsu on vsu.type=sut.type");
		sql.append("         where id= :userid) t on t.default_role = srp.system_role_id    ");
		sql.append("                ))             ");*/
		
		if(!CommonFun.isNe(map.get("query_product"))){
			sql.append("  group by  sp.name ,sp.code                                                   ");
		}else{
			sql.append("   order by a.order_nos                                          ");
		}
		return executeQuery(sql.toString(), params);
	}
	public Result getSelfCharts(Map<String, Object> map) throws Exception {
		Record params = new Record();
		params.put("userid", user().getId());
		StringBuffer sql = new StringBuffer();
		sql.append(" select st.*,ph.id as ph_id,ph.order_no as func_order,p.name as system_product_name,sv.description  from system_charts st                                                      ");
		/*sql.append(" inner join (                                                                    ");
		sql.append(" select code from system_popedom a where a.code like '%.chart.%' and                    ");
		sql.append("    (a.id in (select distinct system_popedom_id                                         ");
		sql.append(" 		                  from system_role_popedom(nolock) srp                          ");
		sql.append(" 		                  left join system_rule(nolock) sr                              ");
		sql.append(" 		                    on srp.system_role_id = sr.system_role_id                   ");
		sql.append(" 		                 where sr.system_user_id = :userid)                             ");
		sql.append(" 		    or a.id in (select distinct system_popedom_id                               ");
		sql.append(" 		                  from system_role_popedom(nolock) srp                          ");
		sql.append(" 		inner join                                                                      ");
		sql.append(" 		(select default_role from system_user_type sut                                  ");
		sql.append(" 		       inner join v_system_user vsu on vsu.type=sut.type                        ");
		sql.append(" 		        where id= :userid) t on t.default_role = srp.system_role_id             ");
		sql.append(" 		               ))                                                               ");
		sql.append(" ) t on t.code = st.system_product_code+'.chart.'+st.view_flag+'.'+st.view_id           ");*/
		sql.append(" left join personal_homepage ph on  ph.type='C' AND  st.system_product_code+'.chart.'+st.view_flag+'.'+st.view_id+'.'+st.flag =ph.fk_id                    ");
		sql.append(" left join system_product p on p.code=st.system_product_code                     ");
		sql.append(" left join system_view sv on sv.id=st.view_id and sv.flag=st.view_flag AND sv.system_product_code=st.system_product_code                     ");
		sql.append(" where st.welcome_func='Y'                     ");
		return executeQuery(sql.toString(), params);
	}
	public Record get(String userid) throws Exception {
		Record params = new Record();
		params.put("userid", userid);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_system_user(nolock) where id = :userid");
		Record rtn = executeQuerySingleRecord(sql.toString(), params);
		return rtn;
	}
	
	public Record getUserInfo(String userno) throws Exception {
		Record params = new Record();
		params.put("userno", userno);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from [system_user](nolock) where no = :userno");
		Record rtn = executeQuerySingleRecord(sql.toString(), params);
		return rtn;
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		return executeUpdate("[system_user]", params, r);
	}
	public void updateByVID(Map<String, Object> params) throws Exception {
		params.put("vid", params.remove("id"));
		executeUpdate("update [system_user] set openid=:openid,unionid=:unionid where 'XTY' + RIGHT('00000000' + CAST(id AS VARCHAR(8)), 8) =:vid", params);
	}
	public int updatePassword(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		params.put("newpwd", CRPasswordEncrypt.Encryptstring(params.get("newpwd").toString()));
		params.put("oldpwd", CRPasswordEncrypt.Encryptstring(params.get("oldpwd").toString()));
		sql.append("update [system_user] set password = :newpwd where id =:id  and password = :oldpwd");
		return executeUpdate(sql.toString(), params);
	}
	public int updateSupperPassword(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		params.put("newpwd", CRPasswordEncrypt.Encryptstring(params.get("newpwd").toString()));
		params.put("oldpwd", CRPasswordEncrypt.Encryptstring(params.get("oldpwd").toString()));
		sql.append("update supper_user set password = :newpwd where no =:no  and password = :oldpwd");
		return executeUpdate(sql.toString(), params);
	}
	
	public Record getUserPageRuleDeps(Account user, String pageid) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" select zh_concat(distinct sr.base_dep_code) as base_dep_code");
		sql.append("   from system_rule sr");
		sql.append("  inner join (select sr.system_role_id");
		sql.append("                from system_rule sr");
		sql.append("                join v_system_role_popedom srp");
		sql.append("                  on srp.system_role_id = sr.system_role_id");
		sql.append("                join (select column_value");
		sql.append("                       from table(get_matched_popedom(:pageid))) t");
		sql.append("                  on t.column_value = srp.id");
		sql.append("               where sr.system_user_id = :userid");
		if ("1".equals(user().getType())) {
			sql.append("                  or sr.system_role_id in");
			sql.append("                     (select system_role_id_teacher_default");
			sql.append("                        from system_product");
			sql.append("                       where system_role_id_teacher_default is not null)");
		} else {
			sql.append("     or sr.system_role_id in                                ");
			sql.append("        (select system_role_id_" + user.getType() + "_default              ");
			sql.append("           from system_product                              ");
			sql.append("          where system_role_id_" + user.getType() + "_default is not null) ");
		}
		sql.append("               group by sr.system_role_id) t");
		sql.append("     on t.system_role_id = sr.system_role_id");
		sql.append("  where sr.system_user_id = :userid");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userid", user.getId());
		map.put("pageid", pageid);
		Result result = executeQuery(sql.toString(), map);
		return zhConcat(result, "system_role_id");
	}
	
	private Record zhConcat(Result roleResult, String field) {
		List<Record> resultList = roleResult.getResultset();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < resultList.size(); i++) {
			Record record = resultList.get(i);
			builder.append(record.getString(field) + ",");
		}
		Record record = new Record();
		record.put(field, builder.substring(0, builder.length() - 1));
		return record;
	}
	
	public Result queryOrganization(Map<String, Object> map) throws Exception {
		map.put("system_user_id", user().getId());
		StringBuffer sql = new StringBuffer();
		sql.append(" select sd.jigid,so.jigname,so.image as jig_image, 0 as is_default from system_department sd  ");
		sql.append(" left join system_organization so on so.jigid=sd.jigid                                                 ");
		sql.append(" where sd.id in (select distinct system_department_id from system_rule where system_user_id=:system_user_id)       ");
		sql.append(" and sd.jigid != (select jigid from v_system_user where id=:system_user_id)                                        ");
		sql.append(" group by sd.jigid, so.jigname,so.image                                                                  ");
		sql.append(" union                                                                                                ");
		sql.append(" select su.jigid,so.jigname,so.image as jig_image, 1 as is_default from v_system_user su      ");
		sql.append(" left join system_organization so on so.jigid=su.jigid                                                 ");
		sql.append(" where su.id=:system_user_id ");
		return executeQuery(sql.toString(), map);
	}
	public Result initProduct(Map<String, Object> map) throws Exception {
		map.put("system_user_id", user().getId());
		map.put("jigid", CommonFun.isNe(Session.getSession().get("curr_jigid"))?user().getJigid():Session.getSession().get("curr_jigid"));
		StringBuffer sql = new StringBuffer();
		sql.append(" select sp.* from system_popedom sp                     ");
		if (!user().isSupperUser()) {
			sql.append(" left join system_role_popedom srp on srp.system_popedom_id=sp.id     ");
			sql.append(" left join system_rule sr on sr.system_role_id = srp.system_role_id  ");
			sql.append(" left join system_department sd on sd.id = sr.system_department_id    ");
		}
		sql.append(" where ((sp.PLUGIN is not null ");
		if (!user().isSupperUser()) {
			sql.append(" and sd.jigid=:jigid  and sr.system_user_id=:system_user_id  ");
		}
		sql.append(")");
		if (!user().isSupperUser()) {
			sql.append(" or exists (SELECT default_role ");
			sql.append(" FROM  system_user_type sut (NOLOCK) ");
			sql.append(" INNER JOIN v_system_user (NOLOCK) vsu ON vsu.type = sut.type ");
			// ygz-- and jigid = :jigid 机构先不加，加了慢
			sql.append(" WHERE  id = :system_user_id and srp.system_role_id=sut.default_role) ");
		}
		sql.append(")");
		sql.append(" and sp.level = 1 and IS_MENU =1                  ");
		//sql.append(" group by sp.PLUGIN, sp.id,sp.name,sp.icon              ");
		sql.append(" group by sp.ID,sp.NAME,sp.CODE,sp.ICON,sp.SYSTEM_POPEDOM_ID_PARENT, ");
		sql.append(" sp.DESCRIPTION,sp.ORDER_NO,sp.EVENT,sp.IS_MENU,sp.ABSTRACT, ");
		sql.append(" sp.PLUGIN,sp.ids,sp.names,sp.order_nos,sp.level,sp.child_num,sp.tel_menu, ");
		sql.append(" sp.custom_popedom,sp.is_cs_page,sp.page_lx,sp.page_bs,sp.page_type,sp.page_params ");
		if(CommonFun.isNe(map.get("sort"))) {
			map.put("sort", "order_no");
		}
		Result r = executeQuery(sql.toString(), map);
		return r;
	}
	
	public Result middbconfig(Account account, Map<String, Object> map) throws Exception {
		map.put("system_user_id", account.getId());
		map.put("jigid", account.getJigid());
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select aa.system_product_code, aa.[key], aa.[value] from system_mid_dbconfig aa                     ");
		sql.append(" inner join system_popedom sp on sp.PLUGIN = aa.system_product_code                  ");
		if (!account.isSupperUser()) {
			sql.append(" left join system_role_popedom srp on srp.system_popedom_id=sp.id     ");
			sql.append(" inner join system_rule sr on sr.system_role_id = srp.system_role_id  ");
			sql.append(" left join system_department sd on sd.id = sr.system_department_id    ");
		}
		sql.append(" where sp.PLUGIN is not null ");
		if (!account.isSupperUser()) {
			sql.append(" and sd.jigid=:jigid  and sr.system_user_id=:system_user_id  ");
		}
		sql.append(" and sp.level = 1 and IS_MENU =1                  ");
		return executeQuery(sql.toString(), map);
	}
	
	public Result queryMenuSetting(Map<String, Object> map) throws Exception {
		Record r = new Record();
		r.put("system_user_id", user().getId());
		r.put("type", map.get("type"));
		StringBuilder sql = new StringBuilder();
		sql.append(" select ph.*,sp.icon,sp.name,sp.code,sp.plugin,p.name as system_product_name from personal_homepage ph ");
		if ("MSSQL".equals(conn.getDbType())) {
			sql.append(" left join system_popedom sp on cast(sp.id as varchar)=ph.fk_id  ");
		} else {
			sql.append(" left join system_popedom sp on sp.id=ph.fk_id  ");
		}
		sql.append(" left join system_product p on p.code=sp.plugin  ");
		sql.append(" where ph.type=:type ");
		sql.append("    and (ph.fk_id in (select distinct system_popedom_id              ");
		sql.append("                   from system_role_popedom(nolock) srp                  ");
		sql.append("                   left join system_rule(nolock) sr                      ");
		sql.append("                     on srp.system_role_id = sr.system_role_id   ");
		sql.append("                  where sr.system_user_id = :system_user_id)             ");
		sql.append("     or ph.fk_id in (select distinct system_popedom_id               ");
		sql.append("                   from system_role_popedom(nolock) srp                  ");
		sql.append(" inner join ");
		sql.append(" (select default_role from system_user_type sut ");
		sql.append("        inner join v_system_user vsu on vsu.type=sut.type");
		sql.append("         where id= :system_user_id) t on t.default_role = srp.system_role_id    ");
		sql.append("                ))             ");
		return executeQuery(sql.toString(), r);
	}
	public Result queryChartSetting(Map<String, Object> map) throws Exception {
		Record r = new Record();
		r.put("system_user_id", user().getId());
		r.put("type", map.get("type"));
		StringBuilder sql = new StringBuilder();
		sql.append(" select ph.*,sc.title,sc.flag ,sc.tableid, sc.chart_height, sc.chart_width, ");
		sql.append(" sc.system_product_code+'.chart.'+sc.view_flag+'.'+sc.view_id+'.'+sc.flag as pageid  ");
		sql.append("  from personal_homepage ph ");
		sql.append(" left join system_charts sc on sc.system_product_code+'.chart.'+sc.view_flag+'.'+sc.view_id+'.'+sc.flag =ph.fk_id ");
		
		sql.append(" inner join (                                                                ");
		sql.append(" select code from system_popedom a where a.code like '%.chart.%' and                    ");
		sql.append("    (a.id in (select distinct system_popedom_id                                         ");
		sql.append(" 		                  from system_role_popedom(nolock) srp                          ");
		sql.append(" 		                  left join system_rule(nolock) sr                              ");
		sql.append(" 		                    on srp.system_role_id = sr.system_role_id                   ");
		sql.append(" 		                 where sr.system_user_id = :system_user_id)                             ");
		sql.append(" 		    or a.id in (select distinct system_popedom_id                               ");
		sql.append(" 		                  from system_role_popedom(nolock) srp                          ");
		sql.append(" 		inner join                                                                      ");
		sql.append(" 		(select default_role from system_user_type sut                                  ");
		sql.append(" 		       inner join v_system_user vsu on vsu.type=sut.type                        ");
		sql.append(" 		        where id= :system_user_id) t on t.default_role = srp.system_role_id             ");
		sql.append(" 		               ))                                                               ");
		sql.append(" ) t on t.code = sc.system_product_code+'.chart.'+sc.view_flag+'.'+sc.view_id           ");
		
		sql.append(" where  ph.type=:type ");
		sql.append(" order by order_no");
		return executeQuery(sql.toString(), r);
	}
	public void deleteHomeSetting(Map<String, Object> map) throws Exception {
		Record r = new Record();
		//r.put("system_user_id", user().getId());
		r.put("type", map.get("type"));
		executeDelete("personal_homepage", r);
	}
	public int saveHomeSetting(Map<String, Object> map) throws Exception {
		executeInsert("personal_homepage", map);
		return getSeqVal("personal_homepage");
	}
}
