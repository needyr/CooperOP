package cn.crtech.cooperop.oa.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.oa.dao.CalendarEventDao;

/**
 * @Description:
 * @author: wangyu
 * @date: 2019年11月8日
 */
public class CalendarEventService extends BaseService {
    public Result query(Map<String, Object> params) throws Exception {
	try {
	    connect();
	    CalendarEventDao dao = new CalendarEventDao();
	    params.put("system_user_id", user().getId());
	    return dao.query(params);
	} catch (Exception ex) {
	    throw ex;
	} finally {
	    disconnect();
	}
    }

    public Result queryMine(Map<String, Object> params) throws Exception {
	try {
	    connect();
	    params.put("system_user_id", user().getId());
	    CalendarEventDao dao = new CalendarEventDao();
	    return dao.query(params);
	} catch (Exception ex) {
	    throw ex;
	} finally {
	    disconnect();
	}
    }

    public Record get(Map<String, Object> params) throws Exception {
	try {
	    connect();
	    CalendarEventDao dao = new CalendarEventDao();
	    return dao.get(params);
	} catch (Exception ex) {
	    throw ex;
	} finally {
	    disconnect();
	}
    }

    public Record insert(Map<String, Object> params) throws Exception {
	try {
	    Record br = new Record();
	    connect();
	    CalendarEventDao dao = new CalendarEventDao();
	    String actor = user().getId();
	    params.remove("id");
	    params.put("state", 1);
	    params.put("creator", actor);
	    params.put("create_time", "sysdate");
	    params.put("last_modifier", actor);
	    params.put("last_modify_time", "sysdate");
	    int i = dao.insert(params);
	    if (i > 0) {
		br.put("result", "success");
		br.put("msg", "保存完毕！");
	    } else {
		br.put("result", "error");
		br.put("msg", "保存失败！");
	    }
	    return br;
	} catch (Exception ex) {
	    throw ex;
	} finally {
	    disconnect();
	}
    }

    public Record update(Map<String, Object> params) throws Exception {
	try {
	    Record br = new Record();
	    connect();
	    CalendarEventDao dao = new CalendarEventDao();
	    Record conditions = new Record();
	    conditions.put("id", params.remove("id"));
	    params.put("last_modifier", user().getId());
	    params.put("last_modify_time", "sysdate");
	    int i = dao.update(params, conditions);
	    if (i > 0) {
		br.put("result", "success");
		br.put("msg", "更新完毕！");
	    } else {
		br.put("result", "error");
		br.put("msg", "更新失败！");
	    }
	    return br;
	} catch (Exception ex) {
	    throw ex;
	} finally {
	    disconnect();
	}
    }

    public Record delete(Map<String, Object> params) throws Exception {
	try {
	    Record br = new Record();
	    connect();
	    CalendarEventDao dao = new CalendarEventDao();
	    int i = dao.delete(params);
	    if (i > 0) {
		br.put("result", "success");
		br.put("msg", "删除完毕！");
	    } else {
		br.put("result", "error");
		br.put("msg", "删除失败！");
	    }
	    return br;
	} catch (Exception ex) {
	    throw ex;
	} finally {
	    disconnect();
	}
    }
}
