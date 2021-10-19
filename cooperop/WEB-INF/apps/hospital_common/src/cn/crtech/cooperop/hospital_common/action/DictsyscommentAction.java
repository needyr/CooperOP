package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.hospital_common.service.DictSysCommentService;

/**
 * @className: DictsyscommentAction   
 * @description: 获取点评规则
 * @author: 魏圣峰 
 * @date: 2019年3月1日 下午9:39:13
 */
public class DictsyscommentAction extends BaseAction{

	/**
	 * @author: 魏圣峰
	 * @description: 获取点评规则
	 * @param: params Map  
	 * @return: Map      
	 * @throws: Exception
	 */
	public Map<String, Object> search(Map<String, Object> params) throws Exception {
		return new DictSysCommentService().search(params);
	}
	public Map<String, Object> searchCheck(Map<String, Object> params) throws Exception {
		return new DictSysCommentService().searchCheck(params);
	}
}
