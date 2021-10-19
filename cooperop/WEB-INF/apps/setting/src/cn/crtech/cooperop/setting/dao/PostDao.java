package cn.crtech.cooperop.setting.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class PostDao extends BaseDao{
	private final static String TABLE_NAME = "[dbo].[system_post]";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select f.*,d.name AS depname,isnull(pn.unum,0) as unum,('['+d.name+']'+f.name) as dep_post ");
		sql.append(" ,('['+d2.name+']'+f2.name) as superior_name,d.jgname,('['+isnull(d.name,'')+']'+f.name) as jg_dep_post ");
		sql.append(" from "+TABLE_NAME+"(nolock) f ");
		sql.append(" LEFT JOIN (select jg.id as jgid,jg.name as jgname,jg.jgjc,f.* from system_department(nolock) as f ");
		sql.append(" 			left join (select id,name,dbo.Fun_GetPY(name) as jgjc ");
		sql.append(" 			from system_department(nolock) where parent_id in (select id from system_department(nolock) where parent_id=0)) as  jg  ");
		sql.append(" 			on ','+f.ids+',' like '%,'+cast(jg.id as varchar)+',%') d ON f.depid = d.id  ");
		sql.append(" left join (select position as post,count(1) as unum from [system_user] where position is not null and user_state!=0 group by position) pn on f.id=pn.post ");
		sql.append(" left join "+TABLE_NAME+"(nolock) as  f2 on f.superior=f2.id");
		sql.append(" LEFT JOIN (select jg.id as jgid,jg.name as jgname,jg.jgjc,f.* from system_department(nolock) as f ");
		sql.append(" 			left join (select id,name,dbo.Fun_GetPY(name) as jgjc ");
		sql.append(" 			from system_department(nolock) where parent_id in (select id from system_department(nolock) where parent_id=0)) as  jg  ");
		sql.append(" 			on ','+f.ids+',' like '%,'+cast(jg.id as varchar)+',%') d2 ON f2.depid = d2.id  ");
		sql.append(" where 1 = 1 and f.state!=-1 ");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
		}
		if(!CommonFun.isNe(params.get("compilation_state"))){
			int n=Integer.parseInt(params.get("compilation_state").toString());
			if(n==0){
				sql.append(" and f.compilation=isnull(pn.unum,0) ");
			}else if(n==1){
				sql.append(" and f.compilation<isnull(pn.unum,0) ");
			}else if(n==-1){
				sql.append(" and f.compilation>isnull(pn.unum,0) ");
			}
		}
		setParameter(params, "depid", " and f.depid = :depid ", sql);
		setParameter(params, "depids", " and ','+d.ids+',' like '%,'+:depids+',%' ", sql);
		setParameter(params, "postfilter", " and f.name like '%'+:postfilter+'%' ", sql);
		setParameter(params, "state", " and f.state like '%'+:state+'%' ", sql);
		setParameter(params, "property", " and f.property=:property ", sql);
		setParameter(params, "filter", " and f.name like :key ", sql);
		setParameter(params, "out_id", " and f.id!=:out_id ", sql);
		setParameter(params, "jgid", " and d.jgid=:jgid ", sql);
		setParameter(params, "notin_position_id", " and f.id not in (select distinct isnull(post_id,0) from position_post p where p.position_id = :notin_position_id)", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	public Result queryDistinct(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		setParameter(params, "property", " select distinct property from "+TABLE_NAME+"(nolock) ", sql);
		return executeQueryLimit(sql.toString(), params);
	}

	public int insert(Map<String, Object> params) throws Exception {
		params.remove("id");
		executeInsert("system_post", params);
		int id = getSeqVal("system_post");
		return id;
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params, r);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select f.*,sd.name AS department_name,case when f.state=1 then '正常' when f.state=0 then '停用' end as nstate ");
		sql.append(" ,('['+sd2.name+']'+f2.name) as superior_name ");
		sql.append(" from "+TABLE_NAME+"(nolock) f ");
		sql.append(" LEFT JOIN (select jg.id as jgid,jg.name as jgname,jg.jgjc,f.* from system_department(nolock) as f ");
		sql.append(" 			left join (select id,name,dbo.Fun_GetPY(name) as jgjc ");
		sql.append(" 			from system_department(nolock) where parent_id in (select id from system_department(nolock) where parent_id=0)) as  jg  ");
		sql.append(" 			on ','+f.ids+',' like '%,'+cast(jg.id as varchar)+',%') sd ON f.depid = sd.id  ");
		sql.append(" left join "+TABLE_NAME+"(nolock) as  f2 on f.superior=f2.id");
		sql.append(" LEFT JOIN (select jg.id as jgid,jg.name as jgname,jg.jgjc,f.* from system_department(nolock) as f ");
		sql.append(" 			left join (select id,name,dbo.Fun_GetPY(name) as jgjc ");
		sql.append(" 			from system_department(nolock) where parent_id in (select id from system_department(nolock) where parent_id=0)) as  jg  ");
		sql.append(" 			on ','+f.ids+',' like '%,'+cast(jg.id as varchar)+',%') sd2 ON f2.depid = sd2.id  ");
		sql.append(" where 1 = 1 ");
		setParameter(params, "id", " and f.id=:id", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params,r);
	}
	
	public Result queryReq(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select f.* ");
		sql.append(" from system_post_requirements(nolock) f ");
		sql.append(" where 1=1 ");
		setParameter(params, "system_post_id", " and f.system_post_id=:system_post_id ", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	public int saveReq(Map<String, Object> params) throws Exception {
		params.remove("id");
		return executeInsert("system_post_requirements", params);
	}
	public int deleteReq(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		return executeUpdate("system_post_requirements", params,r);
	}
}
