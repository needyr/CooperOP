package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class DictHisFreqDao extends BaseDao {
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select a.* from dict_his_freq (nolock) a where 1=1");
		setParameter(params, "filter", " and (p_key = :filter or freq_desc like '%'+'"+params.get("filter")+"'+'%')", sql);
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
		executeUpdate("dict_his_freq", params, r);
	}
}
