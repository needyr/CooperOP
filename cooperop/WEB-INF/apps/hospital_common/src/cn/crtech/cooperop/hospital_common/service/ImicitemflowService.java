package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.ImicitemflowDao;
import cn.crtech.cooperop.hospital_common.dao.imiccustomre.YbxianzDao;

public class ImicitemflowService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new ImicitemflowDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result querymx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new ImicitemflowDao().querymx(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int addflow(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.remove("item_name");
			params.remove("p_key");
			params.remove("id");
			params.remove("item_spec");
			params.remove("item_units");
			params.remove("shengccj");
			params.remove("pizhwh");
			int res=new ImicitemflowDao().addflow(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	public int updateflow(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.remove("p_key");
			params.remove("item_name");
			params.remove("item_spec");
			params.remove("item_units");
			params.remove("shengccj");
			params.remove("pizhwh");
			int res=new ImicitemflowDao().updateflow(params);
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
			int res=new ImicitemflowDao().delete(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	public int insertmx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new ImicitemflowDao().insertmx(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int updatemx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new ImicitemflowDao().updatemx(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	public int deletemx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new ImicitemflowDao().deletemx(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	//切换项目信息
	public Record getItem(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new ImicitemflowDao().getItem(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new ImicitemflowDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record getmx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new ImicitemflowDao().getmx(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
