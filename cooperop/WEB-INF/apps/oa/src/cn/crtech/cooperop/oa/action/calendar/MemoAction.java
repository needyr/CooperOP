package cn.crtech.cooperop.oa.action.calendar;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.oa.service.MemoService;

/**
 * @author 作者 wangyu
 * @version 创建时间：2019年9月9日 类说明 处理备忘请求的action
 */

@DisValidPermission
public class MemoAction extends BaseAction {

	public Result query(Map<String, Object> req) throws Exception {
		return new MemoService().query(req);
	}

	public Result queryMine(Map<String, Object> req) throws Exception {
		return new MemoService().queryMine(req);
	}

	public Map<String, Object> get(Map<String, Object> req) throws Exception {
		return new MemoService().get(req);
	}

	public Map<String, Object> add(Map<String, Object> req) throws Exception {
		if (CommonFun.isNe(req.get("id"))) {
			return req;
		} else {
			return new MemoService().get(req);
		}
	}

	public Record update(Map<String, Object> req) throws Exception {
		return new MemoService().update(req);
	}

	public Record insert(Map<String, Object> req) throws Exception {
		return new MemoService().insert(req);
	}

	public Record delete(Map<String, Object> req) throws Exception {
		return new MemoService().delete(req);
	}

}