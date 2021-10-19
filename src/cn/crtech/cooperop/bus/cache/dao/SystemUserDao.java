package cn.crtech.cooperop.bus.cache.dao;


import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class SystemUserDao extends BaseDao {
	public Result load() throws Exception {
		return executeQuery("select u.* from v_system_user(nolock) u ");
	}
	public Result loadFinger() throws Exception {
		return executeQuery("select u.*,f.finger_image from v_system_user(nolock) u left join system_user_finger(nolock) f on u.id=f.system_user_id");
	}
	public Record regFinger(Map<String, Object> params) throws Exception{
		executeInsert("system_user_finger", params);
		return executeQuerySingleRecord("select count(1) as fcount from system_user_finger where system_user_id=:system_user_id", params);
	}
	public void deleteFingers(Map<String, Object> params) throws Exception {
		execute("delete from system_user_finger where system_user_id=:system_user_id", params);
	}
}
