package cn.crtech.cooperop.hospital_common.service.auditset;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.auditset.CheckLevelDao;

public class CheckLevelService extends BaseService{
	
	public Result query(Map<String,Object> params) throws Exception {
		
		try {
			connect("hospital_common");
			return new CheckLevelDao().query(params);
		} catch (Exception e) {
			throw e;
		}
		finally{
			disconnect();
		}
	}
	
	/**
	 * @author wangsen
	 * @date 2018年12月26日
	 * @param 
	 * @return 
	 * @function 用于自动补全
	 */
	public Result queryAllLevel(Map<String,Object> params) throws Exception {
		
		try {
			connect("hospital_common");
			return new CheckLevelDao().queryAllLevel(params);
		} catch (Exception e) {
			throw e;
		}
		finally{
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckLevelDao().insert(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}

	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckLevelDao().update(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckLevelDao().delete(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(CommonFun.isNe(params.get("p_key"))){
				return null;
			}
			return new CheckLevelDao().get(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}
	
	public Result queryListByIpc(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckLevelDao().queryListByIpc(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}
	
	public Result queryListByImic(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckLevelDao().queryListByImic(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}
}
    