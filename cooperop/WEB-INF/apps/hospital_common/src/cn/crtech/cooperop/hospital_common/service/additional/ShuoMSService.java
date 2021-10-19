package cn.crtech.cooperop.hospital_common.service.additional;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.additional.ShuoMSDao;

public class ShuoMSService extends BaseService{

	public Result queryDrug(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new ShuoMSDao().queryDrug(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	/**
	 * @param params
	 * @return
	 * @throws Exception
	 * @function 医策标准 --- 已有说明书药品
	 * @author yankangkang 2019年3月17日
	 */
	public Result querySysDrug(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new ShuoMSDao().querySysDrug(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			//params.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
			new ShuoMSDao().insert(params);
			return 1;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			//params.put("update_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
			new ShuoMSDao().update(params);
			return 1;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delUpdate(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.put("state", -1);
			new ShuoMSDao().update(params);
			return 1;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new ShuoMSDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	
	/**
	 * @params 药品code
	 * @time 2018年8月1日
	 * @function 获取药品说明书：先从zdy说明书表中查询，如没有则查询标准库说明书
	 */
	public Map<String, Object> getInstruction(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			ShuoMSDao  shuomsdao= new ShuoMSDao();
			rtnMap.put("smsdrug", shuomsdao.querySmsDesc(new HashMap<String, Object>()).getResultset());
			/*if(CommonFun.isNe(params.get("drug_code"))) {
				rtnMap.put("drug", "0");//没有传入drug_code
			}else {*/
				Record record = shuomsdao.getByHiscode_zdy(params);
				if(CommonFun.isNe(record)) {
					record = shuomsdao.getByHiscode(params);
					if(!CommonFun.isNe(record)) {
						rtnMap.put("drug", record);
						rtnMap.put("drug_name", record.get("drug_name"));
						rtnMap.put("is_zdy", "0");
					}
				}else {
					rtnMap.put("drug", record);
					rtnMap.put("drug_name", record.get("drug_name"));
					rtnMap.put("is_zdy", "1");
				}
				rtnMap.put("drug_code", params.get("drug_code"));
			//}
			return rtnMap;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	//
	public Map<String, Object> getSysInstruction(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			ShuoMSDao  shuomsdao= new ShuoMSDao();
			rtnMap.put("smsdrug", shuomsdao.querySmsDesc(new HashMap<String, Object>()).getResultset());
			Record record = shuomsdao.getBySyscode_zdy(params);
			if(CommonFun.isNe(record)) {
				record = shuomsdao.getBySyscode(params);
				if(!CommonFun.isNe(record)) {
					rtnMap.put("drug", record);
					rtnMap.put("drug_name", record.get("drug_name"));
					rtnMap.put("is_zdy", "0");
					rtnMap.put("drug_code", params.get("drug_code"));
				}
			}else {
				rtnMap.put("drug", record);
				rtnMap.put("drug_name", record.get("drug_name"));
				rtnMap.put("is_zdy", "1");
				rtnMap.put("drug_code", params.get("drug_code"));
			}
			return rtnMap;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Map<String, Object> shuoms_img(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("img", new ShuoMSDao().shuoms_img(params));
			rtnMap.put("local_ip",SystemConfig.getSystemConfigValue("hospital_common", "local_server_address", ""));
			return rtnMap;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
}
