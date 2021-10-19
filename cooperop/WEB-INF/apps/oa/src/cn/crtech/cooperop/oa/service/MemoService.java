package cn.crtech.cooperop.oa.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.oa.dao.MemoDao;

/**
 * @author 作者 wangyu
 * @version 创建时间：2019年9月9日 
 * 类说明 处理备忘请求的service
 */
public class MemoService extends BaseService {

    public Result query(Map<String, Object> params) throws Exception {
	try {
	    connect();
	    MemoDao dao = new MemoDao();
	    return dao.query(params);
	} catch (Exception ex) {
	    throw ex;
	} finally {
	    disconnect();
	}
    }

    /**
     * 只能查询自己的备忘
     * 
     * @param params
     * @return
     * @throws Exception
     */
    public Result queryMine(Map<String, Object> params) throws Exception {
	try {
	    connect();
	    MemoDao dao = new MemoDao();
	    // 限制查询条件
	    params.put("creator", user().getId());
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
	    MemoDao dao = new MemoDao();
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
	    MemoDao dao = new MemoDao();
	    params.remove("id");
	    params.put("creator", user().getId());
	    params.put("create_time", "sysdate");
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
	    MemoDao dao = new MemoDao();
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
	    MemoDao dao = new MemoDao();
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