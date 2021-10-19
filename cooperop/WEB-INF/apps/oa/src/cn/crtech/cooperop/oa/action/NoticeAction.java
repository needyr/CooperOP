package cn.crtech.cooperop.oa.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.oa.service.NoticeService;
@DisValidPermission
public class NoticeAction extends BaseAction{
	public Result query(Map<String, Object> req) throws Exception {
		return new NoticeService().query(req);
	}
	
	public Result queryMine(Map<String, Object> req) throws Exception {
		req.put("exclude_state", "-2");
		req.put("system_user_id", user().getId());
		return new NoticeService().query(req);
	}
	
	public Record getMyNoticenum(Map<String, Object> req) throws Exception {
		req.put("exclude_state", "-2");
		return new NoticeService().getMyNoticenum(req);
	}

	public Map<String, Object> get(Map<String, Object> req) throws Exception {
		return new NoticeService().get(req);
	}
	
	public Map<String, Object> add(Map<String, Object> req) throws Exception {
		if(CommonFun.isNe(req.get("id"))){
			return req;
		}else{
			return new NoticeService().get(req);
		}
	}
	
	public Record insert(Map<String, Object> req) throws Exception {
		return new NoticeService().insert(req);
	}
	
	public Record update(Map<String, Object> req) throws Exception {
		return new NoticeService().update(req);
	}
	
	public Record delete(Map<String, Object> req) throws Exception {
		return new NoticeService().delete(req);
	}

	public Map<String, Object> detail(Map<String, Object> req) throws Exception {
		req.put("detail", 1);
		return new NoticeService().get(req);
	}

}
