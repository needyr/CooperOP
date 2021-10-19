package cn.crtech.cooperop.application.service;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.application.dao.NotificationDao;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;


public class NotificationService extends BaseService {
	public Result queryMine(Map<String, Object> params) throws Exception {
		try {
			connect();
			NotificationDao td = new NotificationDao();
			return td.queryMine(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record getMine(Map<String, Object> params) throws Exception {
		try {
			connect();
			NotificationDao td = new NotificationDao();
			Record rtn = td.getMine(params);
			Map<String, Object> m=new HashMap<>();
			m.put("notice_id",params.get("id"));
			params.put("notice_id",params.remove("id"));
			if (rtn.getInt("readflag") == 0) {
				td.saveRead(rtn.getInt("id"));
			}else{
				Record r=td.getRead(params);
				params.put("read_counts",Integer.parseInt( (String) r.get("read_counts"))+1);
				params.put("last_read_time","sysdate");
				td.updateRead(params);
			}
			rtn.put("read_counts", td.queryHasRead(m).getCount());
			rtn.put("not_read_counts", td.queryNotRead(m).getCount()-td.queryHasRead(m).getCount());
			return rtn;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record lastdetail(Map<String, Object> params) throws Exception {
		try {
			connect();
			NotificationDao td = new NotificationDao();
			Record rtn = td.lastdetail(params);
			return rtn;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void saveRead(Map<String, Object> params) throws Exception {
		try {
			connect();
			NotificationDao td = new NotificationDao();
			Record rtn = td.getMine(params);
			if (rtn.getInt("readflag") == 0) {
				td.saveRead(rtn.getInt("id"));
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public int queryNotificationCount(Map<String, Object> params) throws Exception {
		try {
			connect();
			NotificationDao td = new NotificationDao();
			return td.queryNotificationCount(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Result has_read(Map<String, Object> params) throws Exception {
		try {
			connect();
			NotificationDao td = new NotificationDao();
			return td.queryHasRead(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Result not_read(Map<String, Object> params) throws Exception {
		try {
			connect();
			NotificationDao td = new NotificationDao();
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("notice_id",params.get("notice_id"));
			Result r=td.queryHasRead(map);
			int l=(int) r.getCount();
			String[] st=new String[l];
			for(int i=0;i<l;i++){
				st[i]=r.getResultset().get(i).get("system_user_id").toString();
			}
			params.put("hasRead",st);
			return td.queryNotRead(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
