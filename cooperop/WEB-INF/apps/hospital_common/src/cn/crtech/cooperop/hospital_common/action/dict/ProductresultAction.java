package cn.crtech.cooperop.hospital_common.action.dict;

import java.util.Map;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.dict.ProductResultService;
import cn.crtech.precheck.EngineInterface;

/**
 * @className: ProductResultManageService
 * @description: 各产品审查结果字典管理
 * @author: 魏圣峰
 * @date: 2019年2月11日 下午3:19:03
 */
public class ProductresultAction extends BaseService {
	
	public Result query(Map<String, Object> params) throws Exception {
		return new ProductResultService().query(params);
	}

	public int save(Map<String, Object> params) throws Exception {
		new ProductResultService().insert(params);
		EngineInterface.loadProductResultDicts();//重新加载产品审查结果字典
		return 1;
	}

	public int update(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("p_key"))) {
			new ProductResultService().update(params);
			EngineInterface.loadProductResultDicts();//重新加载产品审查结果字典
			return 1;
		}else {return -1;}
	}

	public int delete(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("p_key"))) {
			new ProductResultService().delete(params);
			EngineInterface.loadProductResultDicts();//重新加载产品审查结果字典
			return 1;
		}else {return -1;}
	}

	public Record edit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("p_key"))) {
			return new ProductResultService().get(params);
		}else {return null;}
		
	}
}
