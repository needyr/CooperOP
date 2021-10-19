package cn.crtech.cooperop.hospital_common.service;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.DictCGDao;

public class DictCGService extends BaseService{
	public Record getByName(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictCGDao().getByName(params);
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			DictCGDao dictCGDao = new DictCGDao(); 
			Record r = dictCGDao.getByName(params);
			if(CommonFun.isNe(r)) {
				return dictCGDao.insert(params);
			}else {
				dictCGDao.updateByName(params);
				return r.getInt("id");
			}
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}

	public List<Record> queryByLike(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictCGDao().queryByLike(params).getResultset();
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	
	public Result queryAll(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictCGDao().queryAll(params);
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
}
