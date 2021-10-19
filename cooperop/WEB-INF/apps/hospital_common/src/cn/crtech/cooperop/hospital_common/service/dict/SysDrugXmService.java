package cn.crtech.cooperop.hospital_common.service.dict;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.dict.SysDrugXmDao;

public class SysDrugXmService extends BaseService{
	
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysDrugXmDao().query(params);
		} finally {
			disconnect();
		}
	}
	
	
	/**
	 * @param params  isadd（新增方法标识）
	 * @return
	 * @throws Exception
	 * @function
	 * @author yankangkang 2019年2月25日 下午2:09:59
	 */
	public int insert(Map<String, Object> params) throws Exception {
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			connect("hospital_common");
			map.put("xmbh", params.get("xmbh"));
			map.put("isadd", params.get("isadd"));
			SysDrugXmDao sysdrugxmdao = new SysDrugXmDao();
			Record record = sysdrugxmdao.get(map);
			if (record==null) {
				params.remove("isadd");
				params.remove("xmid");
				int insert = sysdrugxmdao.insert(params);
				return insert;
			}else {
				return 2;
			}
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysDrugXmDao().update(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysDrugXmDao().delete(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysDrugXmDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	/**********************************以下为药品属性值的维护**************************************/
	
	/**
	 * @param params
	 * @return
	 * @throws Exception
	 * @function
	 * @author yankangkang 2019年2月19日 下午3:29:42
	 */
	public Result queryXmValue(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysDrugXmDao().queryXmValue(params);
		} finally {
			disconnect();
		}
	}
	
	/**
	 * @param params isadd=true  oldvalue（修改之前的数据，添加无数据）
	 * @return
	 * @throws Exception
	 * @function
	 * @author yankangkang 2019年2月28日
	 */
	public int xmValueInsert(Map<String, Object> params) throws Exception {
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("xmid", params.get("xmid"));
			connect("hospital_common");
			SysDrugXmDao sysdrugxmdao = new SysDrugXmDao();
			Record record = sysdrugxmdao.xmValueGet(params);
			if (record==null) {
				int xmvalueinsert = sysdrugxmdao.xmValueInsert(params);
				sysdrugxmdao.update(map);
				return xmvalueinsert;
			}else {
				return 2;
			}
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	/**
	 * @param params isupd=true  oldvalue（修改之前的数据）
	 * @return
	 * @throws Exception
	 * @function
	 * @author yankangkang 2019年2月28日
	 */
	public int xmValueUpdate(Map<String, Object> params) throws Exception {
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("xmid", params.get("xmid"));
			connect("hospital_common");
			SysDrugXmDao sysdrugxmdao = new SysDrugXmDao();
			Record record = sysdrugxmdao.xmValueGet(params);
			if (record==null) {
				int xmvalueupdate = sysdrugxmdao.xmValueUpdate(params);
				sysdrugxmdao.update(map);
				return xmvalueupdate;
			}else {
				return 2;
			}
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	
	public int xmValueDelete(Map<String, Object> params) throws Exception {
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("xmid", params.get("xmid"));
			connect("hospital_common");
			SysDrugXmDao sysdrugxmdao = new SysDrugXmDao();
			int xmvaluedelete = sysdrugxmdao.xmValueDelete(params);
			sysdrugxmdao.update(map);
			return xmvaluedelete;
			
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	
	
	public Record xmValueGet(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysDrugXmDao().xmValueGet(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
