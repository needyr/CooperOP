package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.ImicCustomreDao;

public class ImicCustomreService extends BaseService{


	public Result queryimicsc(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new ImicCustomreDao().queryzdysc(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	
	public Result querynotimicsc(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new ImicCustomreDao().querynotimicsc(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result dictYBType(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new ImicCustomreDao().dictYBType(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	//切换项目信息
	public Record getforinsurvsprice(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new ImicCustomreDao().getforinsurvsprice(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
