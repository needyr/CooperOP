package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class DictHisUsersDao extends BaseDao {
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select a.*,b.dept_NAME, ");
		sql.append("stuff(  ");
		sql.append("(select ','+DRUGTAGBH+'-'+  ");
		sql.append("DRUGTAG_SHUOM+'-'+   ");
		sql.append("DRUGTAG_SHOW    ");
		sql.append("from dict_sys_drug_tag (nolock)   ");
		sql.append("where ");
		sql.append("charindex(','+DRUGTAGBH+',', ','+a.tags+',')>0  ");
		sql.append("and BEACTIVE = '是'            ");
		sql.append("group by DRUGTAGBH,DRUGTAG_SHUOM,  ");
		sql.append("DRUGTAG_SHOW for xml path('')),1,1,'' ");
		sql.append(") qx_tags  ");
		sql.append(" from dict_his_users (nolock) a ");
		sql.append(" left join dict_his_deptment (nolock) b "); 
		sql.append("on a.user_dept=b.dept_CODE  where 1=1 ");
		setParameter(params, "filter", " and (a.p_key = :filter or a.user_name like '%'+'"+params.get("filter")+"'+'%')", sql);
		if("0".equals(params.get("is_wh_filter"))) {
			sql.append(" and (a.is_wh = 0 or isnull(a.is_wh,'')='' )");
		}else if("1".equals(params.get("is_wh_filter"))) {
			sql.append(" and a.is_wh = 1");
		}
		params.put("sort", "USER_ID");
		return executeQueryLimit(sql.toString(), params);
	}

	public void updateByCode(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("p_key", params.remove("p_key"));
		executeUpdate("dict_his_users", params, r);
	}
	
	public Result searchByUserTag(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.USER_ID as no,a.USER_NAME as name, "); 
		sql.append("b.dept_NAME as department_name             ");
		sql.append("from dict_his_users(nolock) a               "); 
		sql.append("left join dict_his_deptment (nolock) b      "); 
		sql.append("on a.USER_DEPT = b.dept_CODE                "); 
		sql.append("where CHARINDEX(:drugtagbh+',', a.tags+',')>0 ");
		params.put("sort", "no");
		return executeQuery(sql.toString(), params);
	}
	
	public void updateTag(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update a set a.tags =                ");
		sql.append("(STUFF((select ','+col from          ");
		sql.append("split(isnull(a.tags,'') + ',' + :drugtagbh,',') ");
		sql.append("where isnull(col,'')<>''             ");
		sql.append("and col <>                           ");
		sql.append("(case when exists                    ");
		sql.append("(select 1 from dict_his_users(nolock) ");
		sql.append("where a.user_id = user_id        ");
		sql.append("and user_id in (:user_id))       ");
		sql.append("then '' else :drugtagbh end)         ");
		sql.append("group by col for xml path(''))       ");
		sql.append(",1,1,''))                            ");
		sql.append("from dict_his_users a                 ");
		sql.append("where user_id in (:user_id)      ");
		sql.append("or charindex(:drugtagbh+',',a.tags+',')>0 ");
		execute(sql.toString(), params);
	}
}
