package cn.crtech.cooperop.setting.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.setting.dao.NotificationDao;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;


public class NotificationService extends BaseService {
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			NotificationDao td = new NotificationDao();
			return td.query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result queryReader(Map<String, Object> params) throws Exception {
		try {
			connect();
			NotificationDao td = new NotificationDao();
			return td.queryReader(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record get(int id) throws Exception {
		try {
			connect();
			NotificationDao td = new NotificationDao();
			Record rtn = td.get(id);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("notice_id", id);
			rtn.put("sendto", td.querySendto(m).getResultset());
			return rtn;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void insert(Map<String, Object> params) throws Exception {
		try {
			connect();
			NotificationDao td = new NotificationDao();
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> sendto = (List<Map<String, Object>>)params.remove("sendto");
			start();
			int id = td.insert(params);
			if (sendto != null) {
				for (Map<String, Object> st : sendto) {
					td.insertSendto(id, st);
				}
			}
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void update(int id, Map<String, Object> params) throws Exception {
		try {
			connect();
			NotificationDao td = new NotificationDao();
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> sendto = (List<Map<String, Object>>)params.remove("sendto");
			start();
			int num = td.update(id, params);
			if (num > 0) {
				td.deleteSendto(id);
				if (sendto != null) {
					for (Map<String, Object> st : sendto) {
						td.insertSendto(id, st);
					}
				}
			}
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void delete(int id) throws Exception {
		try {
			connect();
			NotificationDao td = new NotificationDao();
			start();
			td.delete(id);
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void publish(int id) throws Exception {
		try {
			connect();
			NotificationDao td = new NotificationDao();
			start();
			td.publish(id);
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void unpublish(int id) throws Exception {
		try {
			connect();
			NotificationDao td = new NotificationDao();
			start();
			td.unpublish(id);
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
}
