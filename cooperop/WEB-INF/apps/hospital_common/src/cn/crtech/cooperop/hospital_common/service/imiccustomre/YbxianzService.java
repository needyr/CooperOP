package cn.crtech.cooperop.hospital_common.service.imiccustomre;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.auditset.CheckLevelDao;
import cn.crtech.cooperop.hospital_common.dao.imiccustomre.YbxianzDao;

public class YbxianzService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new YbxianzDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	public Result queryDias(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new YbxianzDao().queryDias(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	public Result queryWfprocess(Map<String, Object> params) throws Exception {
		try {
			connect("");
			return new YbxianzDao().queryWfprocess(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new YbxianzDao().insert(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int insertDia(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new YbxianzDao().insertDia(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new YbxianzDao().delete(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int deleteDia(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new YbxianzDao().deleteDia(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new YbxianzDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record getDia(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new YbxianzDao().getDia(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new YbxianzDao().update(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int updateDia(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new YbxianzDao().updateDia(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
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
