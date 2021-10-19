package cn.crtech.cooperop.hospital_common.service.dict;

import java.util.Map;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.dict.SysSpcommentDao;

/**
 * @className: SysSpcommentService   
 * @description: 专项点评规则
 * @author: 魏圣峰 
 * @date: 2019年3月1日 上午10:11:21
 */
public class SysSpcommentService extends BaseService {
	
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysSpcommentDao().query(params);
		} catch (Exception e) {throw e;} 
		finally {disconnect();}
	}

	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new SysSpcommentDao().insert(params);
			return 1;
		} catch (Exception e) {throw e;} 
		finally {disconnect();}
	}

	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new SysSpcommentDao().update(params);
			return 1;
		} catch (Exception e) {throw e;} 
		finally {disconnect();}
	}

	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new SysSpcommentDao().delete(params);
			return 1;
		} catch (Exception e) {throw e;} 
		finally {disconnect();}
	}

	public Result get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysSpcommentDao().get(params);
		} catch (Exception e) {throw e;} 
		finally {disconnect();}
	}
	
	public Record getByCode(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysSpcommentDao().getByCode(params);
		} catch (Exception e) {throw e;} 
		finally {disconnect();}
	}
	
	/**
	 * @author: 魏圣峰
	 * @description: 查询药品标签
	 * @param: params Map 
	 * @return: Result      
	 * @throws: Exception
	 */
	public Result queryTags(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysSpcommentDao().queryTags(params);
		} catch (Exception ex){throw ex;}
		finally{disconnect();}
	}
}
