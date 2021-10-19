package cn.crtech.cooperop.hospital_common.service.dict;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.dict.SysDrugTagDao;

public class SysDrugTagService extends BaseService{
	
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysDrugTagDao().query(params);
		} finally {
			disconnect();
		}
	}
	
	public Result parenttag(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysDrugTagDao().parenttag(params);
		} finally {
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("drugtagbh", params.get("drugtagbh"));
			connect("hospital_common");
			SysDrugTagDao sysdicttagdao = new SysDrugTagDao();
			Record record = sysdicttagdao.get(map);
			if (record==null) { 
				return sysdicttagdao.insert(params);
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
			return new SysDrugTagDao().update(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysDrugTagDao().delete(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysDrugTagDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result queryTree(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			SysDrugTagDao dd = new SysDrugTagDao();
			return dd.queryTree(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
