package cn.crtech.cooperop.hospital_common.service.tpn;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.tpn.XiangmuwhDao;

public class XiangmuwhService extends BaseService {
	public Result getXm(Map<String, Object> params) throws Throwable{
		try {
			connect("hospital_common");
			return new XiangmuwhDao().getXm(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public List<Record> edit(Map<String, Object> params)throws Throwable {
		try {
			connect("hospital_common");
			Result edit = new XiangmuwhDao().edit(params);
			List<Record> list = edit.getResultset();
			return list;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Result getXmValue(Map<String, Object> params)throws Throwable {
		try {
			connect("hospital_common");
			return new XiangmuwhDao().getXmValue(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Integer deleteXm(Map<String, Object> params)throws Throwable {
		try {
			connect("hospital_common");
			if(params.get("xmid")==null) {
				return 0;
			}
			XiangmuwhDao dao = new XiangmuwhDao();
			start();
			Integer i1 = dao.deleteXm(params);
			Integer i2 = dao.deleteXmValue(params);
			commit();
			return i1+i2;
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}

	public Integer deleteXmValue(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(params.get("id")==null) {
				return 0;
			}
			XiangmuwhDao dao = new XiangmuwhDao();
			Integer i2 = dao.deleteXmValueById(params);
			return i2;
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}

	public Integer addXmValue(Map<String, Object> params)  throws Throwable{
		try {
			connect("hospital_common");
			return new XiangmuwhDao().addXmValue(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Integer addXm(Map<String, Object> params)  throws Throwable{
		try {
			connect("hospital_common");
			return new XiangmuwhDao().addXm(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Integer addClass(Map<String, Object> params)  throws Throwable{
		try {
			connect("hospital_common");
			return new XiangmuwhDao().addClass(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Integer deleteClass(Map<String, Object> params) throws Throwable{
		try {
			connect("hospital_common");
			return new XiangmuwhDao().deleteClass(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Result getData(Map<String, Object> params)throws Throwable {
		try {
			connect("hospital_common");
			return new XiangmuwhDao().getXmValue(params);
		}
		catch (Exception e) {
			throw e;
		}finally {
			disconnect();	
		}
	}
}
