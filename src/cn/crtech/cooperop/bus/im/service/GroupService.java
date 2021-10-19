package cn.crtech.cooperop.bus.im.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.im.dao.GroupDao;


public class GroupService extends IMBaseService {
	
	public Map<String, Object> saveGroup(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			Map<String, Object> rtn = new HashMap<String, Object>();
			GroupDao gd = new GroupDao();
			List<Map<String, Object>> userlist = (List<Map<String, Object>>) params.remove("userlist");
			if("new".equals(params.get("id"))){
				int gid = gd.insert(params);
				rtn.put("id", gid);
				for(Map<String, Object> user : userlist){
					user.put("system_user_group_id", gid);
					//user.put("invite_time", "");
					user.put("is_holdconfirm", 0);
					gd.saveGroupUser(user);
				}
			}else{
				String gid = (String) params.get("id");
				gd.update(params);
				rtn.put("id", gid);
				gd.deleteGroupUser(gid);
				for(Map<String, Object> user : userlist){
					user.put("system_user_group_id", gid);
					//user.put("invite_time", "");
					user.put("is_holdconfirm", 0);
					gd.saveGroupUser(user);
				}
			}
			commit();
			return rtn;
		} finally {
			disconnect();
		}
	}
}
