package cn.crtech.cooperop.hospital_common.service;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.DictSysCommentDao;

/**
 * @className: DictSysCommentService   
 * @description: 获取点评规则
 * @author: 魏圣峰 
 * @date: 2019年3月1日 下午9:40:36
 */
public class DictSysCommentService extends BaseService{

	/**
	 * @author: 魏圣峰
	 * @description: 获取点评规则
	 * @param: params Map  
	 * @return: Result      
	 * @throws: Exception
	 */
	public Map<String, Object> search(Map<String, Object> params) throws Exception {
		try {
				connect();
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("syscomment", new DictSysCommentDao().search(params).getResultset());
				return m;
		} catch (Exception ex) {throw ex;} 
		finally {disconnect();}
	}
	public Map<String, Object> searchCheck(Map<String, Object> params) throws Exception {
		try {
			connect();
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("syscomment", new DictSysCommentDao().searchCheck(params).getResultset());
			return m;
		} catch (Exception ex) {throw ex;} 
		finally {disconnect();}
	}
	
	public Result queryCommentByWay(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictSysCommentDao().queryCommentByWay(params);
		} catch (Exception ex) {throw ex;} 
		finally {disconnect();}
	}
	
	public Result queryComment_special(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictSysCommentDao().queryComment_special(params);
		} catch (Exception ex) {throw ex;} 
		finally {disconnect();}
	}
	
	
	public Result queryAllByWay(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictSysCommentDao().queryAllByWay(params);
		} catch (Exception ex) {throw ex;} 
		finally {disconnect();}
	}
	
	public Result queryAll(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictSysCommentDao().queryAll(params);
		} catch (Exception ex) {throw ex;} 
		finally {disconnect();}
	}
}
