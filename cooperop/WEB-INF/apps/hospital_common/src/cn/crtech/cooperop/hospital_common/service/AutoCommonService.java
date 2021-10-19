package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.AutoCommonDao;

public class AutoCommonService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new AutoCommonDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	/** 查询审查列表 */
	public Result queryAudit(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Result r = new AutoCommonDao().query(params);
			return r;
		} catch (Exception e) { throw e; }
		finally { disconnect(); }
	}
	
	//所有的审查内容,根据YWLSB查询,因为有延迟,所以和"查询审查列表"分开
	public Result queryAuditAll(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new AutoCommonDao().queryYwlsb(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public String insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			String id = new AutoCommonDao().insert(params);
			// -- test yan
			/*new ThingHpTimeDao().insert(id, "进入审查服务入口", (String)params.get("create_time"));*/
			// --
			//params.put("create_time",  CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			return id;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	/*
	 * public void update(Map<String, Object> params) throws Exception { try {
	 * connect("hospital_common"); String id = (String)params.get("id"); new
	 * AutoCommonDao().update(params); } catch (Exception e) { throw e; } finally {
	 * disconnect(); } }
	 */
	
	//解决his接口visit_id 的错误问题，his处理后请删除
  	public Record queryVisitid(Map<String, Object> params) throws Exception {
  		try {
  			connect("hospital_common");
  			return new AutoCommonDao().queryVisitid(params);
  		} catch (Exception e) {
  			throw e;
  		}finally {
  			disconnect();
  		}
  	}
  	
  	/**
  	 * 插入auto_common_mx子表
  	 * @param params
  	 * @throws Exception
  	 */
  	public void insertMx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new AutoCommonDao().insertMx(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
