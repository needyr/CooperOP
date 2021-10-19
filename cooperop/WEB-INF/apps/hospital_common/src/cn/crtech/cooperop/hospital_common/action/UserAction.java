package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.hospital_common.service.UserService;

public class UserAction extends BaseAction {
	@DisLoggedIn
	public Map<String, Object> search(Map<String, Object> params) throws Exception {
		return new UserService().search(params);
	}
	@DisLoggedIn
	public Map<String, Object> searchCheck(Map<String, Object> params) throws Exception {
		return new UserService().searchCheck(params);
	}
	
	/**
	  * 此方法不可删除,用来判断im,是否与服务器保持连接!!!
	  * 禁止删除!!!!!禁止注释!!!!
	 * @param map
	 * @throws Exception
	 */
	@DisLoggedIn
	public void link(Map<String, Object> map) throws Exception{
		//return 1;
	}
}
