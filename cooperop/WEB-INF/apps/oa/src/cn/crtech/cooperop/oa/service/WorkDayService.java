package cn.crtech.cooperop.oa.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.oa.dao.WorkDayDao;

/**
 * @Description:
 * @author: wangyu
 * @date: 2019年10月25日
 */
public class WorkDayService extends BaseService {
    public Result query(Map<String, Object> params) throws Exception {
	try {
	    connect();
	    WorkDayDao dao = new WorkDayDao();
	    params.put("system_user_id", user().getId());
	    Result result = dao.query(params);
	    return result;
	} catch (Exception ex) {
	    throw ex;
	} finally {
	    disconnect();
	}
    }

    public Record get(Map<String, Object> params) throws Exception {
	try {
	    connect();
	    WorkDayDao dao = new WorkDayDao();
	    Record rd = dao.get(params);
	    return rd;
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
	    start();
	    WorkDayDao dao = new WorkDayDao();
	    int i = dao.insert(params);
	    if (i > 0) {
		br.put("result", "success");
		br.put("msg", "保存完毕！");
	    } else {
		br.put("result", "error");
		br.put("msg", "保存失败！");
	    }
	    commit();
	    return br;
	} catch (Exception ex) {
	    rollback();
	    throw ex;
	} finally {
	    disconnect();
	}
    }

    public Record update(Map<String, Object> params) throws Exception {
	try {
	    Record br = new Record();
	    connect();
	    WorkDayDao dao = new WorkDayDao();
	    Record conditions = new Record();
	    conditions.put("id", params.remove("id"));
	    int i = dao.update(params, conditions);
	    if (i > 0) {
		br.put("result", "success");
		br.put("msg", "更新完毕！");
	    } else {
		br.put("result", "error");
		br.put("msg", "更新失败！");
	    }
	    commit();
	    return br;
	} catch (Exception ex) {
	    rollback();
	    throw ex;
	} finally {
	    disconnect();
	}
    }

    public Record delete(Map<String, Object> params) throws Exception {
	try {
	    Record br = new Record();
	    connect();
	    start();
	    WorkDayDao dao = new WorkDayDao();
	    int i = dao.delete(params);
	    if (i > 0) {
		br.put("result", "success");
		br.put("msg", "删除完毕！");
	    } else {
		br.put("result", "error");
		br.put("msg", "删除失败！");
	    }
	    commit();
	    return br;
	} catch (Exception ex) {
	    rollback();
	    throw ex;
	} finally {
	    disconnect();
	}
    }
}