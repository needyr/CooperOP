package cn.crtech.cooperop.application.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class WarnSettingDao extends BaseDao {
	private final static String TABLE_NAME = "sys_warning_info_setting";
	private final static String SORT_NAME = "sys_warning_sort";
	
	public Result query(Map<String, Object> params) throws Exception {
		params.put("system_user_id", user().getId());
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*,sp.name as system_product_name,sws.name as sort_name from " + TABLE_NAME +" t ");
		sql.append(" left join system_product sp on sp.code = t.system_product_code ");
		sql.append(" left join sys_warning_sort sws on sws.id = t.sort_id ");
		sql.append(" where 1 = 1 ");
		setParameter(params, "state", " and state=:state", sql);
		if(CommonFun.isNe(params.get("sort"))){
			params.put("sort", "t.order_no");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	public Result queryByRule(Map<String, Object> params) throws Exception {
		params.put("system_user_id", user().getId());
		StringBuffer sql = new StringBuffer();
		sql.append("select t.* from " + TABLE_NAME +" t ");
		sql.append(" where 1 = 1 ");
		setParameter(params, "state", " and state=:state", sql);
		if(CommonFun.isNe(params.get("sort"))){
			params.put("sort", "order_no");
		}
		if(!user().isSupperUser()){
			if("MYSQL".equals(conn.getDbType().toUpperCase())){
				sql.append(" and (EXISTS (select 1 from system_user_post where system_user_id=:system_user_id ");
				sql.append("	and (instr(t.system_department_id, concat(',', cast(department as char), ',')) >0 ");
				sql.append("	or instr(t.system_post_id, concat(',', cast(post as char), ',')) >0)) or is_public = '1')");
			}else{
				/*sql.append(" and (EXISTS (select 1 from system_user_post where system_user_id=:system_user_id ");
				sql.append("	and (CHARINDEX(','+cast(department as varchar)+',', t.system_department_id) >0 ");
				sql.append("	or CHARINDEX(','+cast(post as varchar)+',', t.system_post_id) >0)) or is_public = '1')");*/
				sql.append(" and exists(select 1 from v_system_user(nolock) where id=:system_user_id  ");
				sql.append(" and (exists (select 1 from split(cast(departments as varchar),',') a where  ");
				sql.append(" CHARINDEX(','+cast(a.col as varchar)+',', ','+t.system_department_id+',') >0 ) ");
				sql.append(" or is_public = '1' ) )  ");
			}
		}
		return executeQuery(sql.toString(), params);
	}
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		if("MYSQL".equals(conn.getDbType().toUpperCase())){
			sql.append("select *,  ");
			sql.append(" (SELECT group_concat('' , NAME) FROM             ");
			sql.append(" system_department WHERE                          ");
			sql.append(" instr (concat(',' , system_department_id , ','), ");
			sql.append(" 	concat(',' , cast(id AS char) , ',')) > 0     ");
			sql.append(" ) AS department_name                             ");
		}else{
			sql.append("select *,  ");
			sql.append("  stuff(( select ','+name from system_department where CHARINDEX(','+cast(id as varchar)+',', ','+system_department_id+',')>0");
			sql.append("  for xml path('')),1,1,'') as department_name" );
		}
		sql.append(" from " + TABLE_NAME);
		sql.append(" where id=:id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	public int insert(Map<String, Object> params) throws Exception {
		params.remove("id");
		return executeInsert(TABLE_NAME, params);
	}
	public int update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params, r);
	}
	public int delete(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from  "+ TABLE_NAME);
		sql.append(" where id=:id ");
		return executeUpdate(sql.toString(), params);
	}
	
	
	public Result querySort(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.* from " + SORT_NAME +" t ");
		sql.append(" where 1 = 1 ");
		if(CommonFun.isNe(params.get("sort"))){
			params.put("sort", "order_no");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	public Record getSort(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + SORT_NAME);
		sql.append(" where id=:id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	public int insertSort(Map<String, Object> params) throws Exception {
		params.remove("id");
		return executeInsert(SORT_NAME, params);
	}
	public int updateSort(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(SORT_NAME, params, r);
	}
	public int deleteSort(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from  "+ SORT_NAME);
		sql.append(" where id=:id ");
		return executeUpdate(sql.toString(), params);
	}
}
