package cn.crtech.cooperop.ipc.action;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.ipc.service.PrecheckService;
public class PrecheckAction extends BaseAction{

	/**
	 * 修改
	 * @param req
	 * @throws Exception
	 */
	public int updatePassword(Map<String, Object> req) throws Exception {
		req.put("no", user().getNo());
		return new PrecheckService().updatePassword(req);
	}
	
}
