package cn.crtech.cooperop.hospital_common.service.dict;

import java.util.Map;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.dict.ProductResultDao;

/**
 * @className: ProductResultManageService
 * @description: 各产品审查结果字典管理
 * @author: 魏圣峰
 * @date: 2019年2月11日 下午3:19:03
 */
public class ProductResultService extends BaseService {
	
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new ProductResultDao().query(params);
		} catch (Exception e) {throw e;} 
		finally {disconnect();}
	}

	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new ProductResultDao().insert(params);
			return 1;
		} catch (Exception e) {throw e;} 
		finally {disconnect();}
	}

	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new ProductResultDao().update(params);
			return 1;
		} catch (Exception e) {throw e;} 
		finally {disconnect();}
	}

	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new ProductResultDao().delete(params);
			return 1;
		} catch (Exception e) {throw e;} 
		finally {disconnect();}
	}

	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new ProductResultDao().get(params);
		} catch (Exception e) {throw e;} 
		finally {disconnect();}
	}
}
