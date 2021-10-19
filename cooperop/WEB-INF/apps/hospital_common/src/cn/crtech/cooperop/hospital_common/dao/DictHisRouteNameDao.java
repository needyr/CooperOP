package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DictHisRouteNameDao extends BaseDao {
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select a.* from dict_his_routename (nolock) a where 1=1");
		setParameter(params, "filter", " and (routename_code = :filter or routename_name like '%'+'"+params.get("filter")+"'+'%')", sql);
		if("0".equals(params.get("is_wh_filter"))) {
			sql.append(" and (is_wh = 0 or isnull(is_wh,'')='' )");
		}else if("1".equals(params.get("is_wh_filter"))) {
			sql.append(" and is_wh = 1");
		}
		return executeQueryLimit(sql.toString(), params);
	}

	public void updateByCode(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("p_key", params.remove("p_key"));
		executeUpdate("dict_his_routename", params, r);
	}
	
	public Result search(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select routename_code,routename_name   ");
		sql.append(" from dict_his_routename   (nolock)    where 1=1        ");
		if(!CommonFun.isNe(params.get("data"))){
			params.put("key", "%"+params.get("data")+"%");
			sql.append(" and (routename_name like :key or routename_code = :data) ");
		}
		sql.append(" order by routename_code                       ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result searchCheck(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select routename_code,routename_name   ");
		sql.append(" from dict_his_routename  (nolock)     where 1=1        ");
		sql.append(" and routename_code in(:code) ");
		sql.append(" order by routename_code                       ");
		return executeQuery(sql.toString(), params);
	}
}
