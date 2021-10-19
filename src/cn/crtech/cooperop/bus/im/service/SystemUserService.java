package cn.crtech.cooperop.bus.im.service;

import java.util.Map;

import cn.crtech.cooperop.bus.im.dao.ContactorDao;
import cn.crtech.cooperop.bus.im.dao.MessageDao;
import cn.crtech.cooperop.bus.im.dao.SystemUserDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class SystemUserService extends IMBaseService {
	public Record login(String userid) throws Exception {
		try {
			connect();
			SystemUserDao sud = new SystemUserDao();
			Record user = sud.getV(userid);
			if (user == null) {
				throw new Exception("im user not found.");
			}
			return user;
		} finally {
			disconnect();
		}
	}

	public int changeOnlineStatus(String userid, String online_status) throws Exception {
		try {
			connect();
			SystemUserDao sud = new SystemUserDao();
			return sud.changeOnlineStatus(userid, online_status);
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> loadSessions(Map<String, Object> params) throws Exception {
		try {
			connect();
			MessageDao md = new MessageDao();
			Result rs = md.querySessions(params);
			Record rtn = new Record();
			rtn.put("sessions", rs.getResultset());
			return rtn;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> loadContactors(Map<String, Object> params) throws Exception{
		try {
			connect();
			ContactorDao dd = new ContactorDao();
			Result rs = dd.query(params);
			Record rtn = new Record();
			rtn.put("contactors", rs.getResultset());
			return rtn;
		} finally {
			disconnect();
		}
	}

	public Map<String, Object> listContractorUsers(Map<String, Object> params) throws Exception{
		try {
			connect();
			ContactorDao dd = new ContactorDao();
			Result rs = dd.queryUser(params);
			Record rtn = new Record();
			rtn.put("users", rs.getResultset());
			return rtn;
		} finally {
			disconnect();
		}
	}

}
