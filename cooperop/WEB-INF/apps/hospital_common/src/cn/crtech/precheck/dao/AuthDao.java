package cn.crtech.precheck.dao;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;

public class AuthDao extends BaseDao {
	public Record get(String userid) throws Exception {
		Record params = new Record();
		params.put("userid", userid);
		StringBuffer sql = new StringBuffer(); //type = :user_type and 
		sql.append("select * from [system_user](nolock) where id = :userid and state = 1");
		Record rtn = executeQuerySingleRecord(sql.toString(), params);
		return rtn;
	}
}
