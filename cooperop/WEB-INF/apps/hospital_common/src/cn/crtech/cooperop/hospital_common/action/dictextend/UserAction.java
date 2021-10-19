package cn.crtech.cooperop.hospital_common.action.dictextend;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.DictHisUsersService;

public class UserAction extends BaseAction {
	public Result query(Map<String, Object> params) throws Exception {
		Result result = new DictHisUsersService().query(params);
		return result;
	}
	
	public void updateByCode(Map<String, Object> params) throws Exception {
		new DictHisUsersService().updateByCode(params);
	}
	
	/**
	 * 根据标签获取药品
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object>  searchByUserTag(Map<String, Object> params) throws Exception {
		return new DictHisUsersService().searchByUserTag(params);
	}
	
	/**
	 * 升级多个药品为某个标签
	 * @param params
	 * @throws Exception
	 */
	public void  updateTag(Map<String, Object> params) throws Exception {
		new DictHisUsersService().updateTag(params);
	}
}
