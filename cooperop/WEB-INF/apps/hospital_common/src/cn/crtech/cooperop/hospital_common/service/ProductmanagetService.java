package cn.crtech.cooperop.hospital_common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.ProductmanagetDao;

public class ProductmanagetService extends BaseService {
	
	/** 医保控费审查产品名称 */
	public static final String IMIC = "hospital_imic";
	/** 合理用药审查产品名称 */
	public static final String IPC = "ipc";

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new ProductmanagetDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new ProductmanagetDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Result queryHas(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new ProductmanagetDao().queryHas(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new ProductmanagetDao().insert(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new ProductmanagetDao().update(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new ProductmanagetDao().delete(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
    /** 加载审查产品 */
	public Map<String, Object> loadCheckProduct(Map<String, Object> params) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			connect("hospital_common");
			ProductmanagetDao checkproducts = new ProductmanagetDao();
			Map<String, Object> pduMap = new HashMap<String, Object>();
			//pduMap.put("is_check_server", 1);
			pduMap.put("is_active", 1);
			Result result = checkproducts.query(pduMap);
			if(!CommonFun.isNe(result)) {
				List<Record> products = result.getResultset();
				retMap.put("check_product", products);
				for (Record product : products) {
					log.debug(" -- loading product [" + product.get("code") +"] success ...");
				}
			}
		} catch (Exception e) {
			log.debug("加载审查服务失败,请系统管理员检查...");
			throw e;
		}finally {
			disconnect();
		}
		return retMap;
	}
}
