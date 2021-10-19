package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.SysDictDrugDao;

public class SysDictDrugService extends BaseService{

	/**
	 * @param params
	 * @return
	 * @throws Exception
	 * @function 已设置有问题的系统药品
	 * @author yankangkang 2019年3月18日
	 */
	public Result queryzdysc(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysDictDrugDao().queryzdysc(params);
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
	 * @function 快速查询  已设置有问题的系统药品
	 * @author yankangkang 2019年3月18日
	 */
	public Result queryZdyAll(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysDictDrugDao().queryZdyAll(params);
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
	 * @function 自动补全---未设置问题的系统药品
	 * @author yankangkang 2019年3月18日
	 */
	public Result querynotzdysc(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysDictDrugDao().querynotzdysc(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	//切换药品信息
	public Record getforzdy(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysDictDrugDao().getforzdy(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

}
