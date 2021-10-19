package cn.crtech.cooperop.ipc.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CRPasswordEncrypt;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.ipc.dao.DoctorDao;
import cn.crtech.precheck.ipc.ws.client.Client;

public class DoctorService extends BaseService{
	
	public static final String HOSPITAL_ID = SystemConfig.getSystemConfigValue("hospital_common", "hospital_id");

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new DoctorDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result queryHas(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new DoctorDao().queryHas(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		
		try {
			Map<String, Object> params2=new HashMap<String, Object>();
			connect("ipc");
			start();
			params.put("cooperop_sys_no",params.remove("no"));
			params2.put("no", params.get("cooperop_sys_no"));
			params2.put("name", params.remove("name"));
			params2.put("password", params.remove("password"));
			params2.put("telephone", params.remove("telephone"));
			params2.put("birthday", params.remove("birthday"));
			params2.put("gender", params.remove("gender"));
			params2.put("avatar", params.remove("avatar"));
			params2.put("state", params.remove("state"));
			params2.put("hospital_id", HOSPITAL_ID);
			if(!CommonFun.isNe(params2.get("password"))){
				params2.put("password", CRPasswordEncrypt.Encryptstring((String)params2.remove("password")));
			}else{
				params2.put("password", CRPasswordEncrypt.Encryptstring("000000"));
			}
			//params2.put("password", "000000");
			params2.put("department", params.remove("department"));
			params2.put("type", "doctor");
			params.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			if(CommonFun.isNe(params.get("doctor_no"))) {
				params.put("doctor_no", "temp_"+params2.get("no"));
			}
			new DoctorDao().insert(params);
			new DoctorDao().insertCsysUser(params2);
			commit();
			params2.put("doctor_no",params.get("doctor_no"));
			params2.put("create_time", params.get("create_time"));
			//params2.put("type", "doctor");
			params2.put("operation", "2");//操作 0删除 1新增 2 修改  3同步
			params2.put("lastmodifier", user().getId());
			params2.put("description", params.get("description"));
			params2.put("position", params.get("position"));
			params2.put("scope_id", params2.remove("hospital_id"));
			try {
				Client.syncUser("admin_jq", params2);
			} catch (Exception e) {
			}
			return 1;
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			start();
			Map<String, Object> params2=new HashMap<String, Object>();
			params.put("cooperop_sys_no",params.remove("no"));
			params2.put("no", params.get("cooperop_sys_no"));
			params2.put("name", params.remove("name"));
			params2.put("telephone", params.remove("telephone"));
			params2.put("birthday", params.remove("birthday"));
			params2.put("gender", params.remove("gender"));
			params2.put("avatar", params.remove("avatar"));
			params2.put("state", params.remove("state"));
			params2.put("department", params.remove("department"));
			params2.put("type", "doctor");
			params2.put("id", params.remove("sid"));
			params2.put("hospital_id", HOSPITAL_ID);
			params.put("lastModifyTime", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			if(CommonFun.isNe(params.get("doctor_no"))) {
				params.put("doctor_no", "temp_"+params2.get("no"));
			}
			int res=new DoctorDao().update(params);
			new DoctorDao().updateCsysUser(params2);
			commit();
			params2.put("doctor_no",params.get("doctor_no"));
			params2.put("lastModifyTime", params.get("lastModifyTime"));
			//params2.put("type", "doctor");
			params2.put("operation", "1");//操作 0删除 1新增 2 修改  3同步
			params2.put("lastmodifier", user().getId());
			params2.put("description", params.get("description"));
			params2.put("position", params.get("position"));
			params2.put("scope_id", params2.remove("hospital_id"));
			Client.syncUser("admin_jq", params2);
			return res;
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> params2=new HashMap<String, Object>();
		try {
			connect("ipc");
			start();
			DoctorDao doctor = new DoctorDao();
			params2.put("id", params.remove("sid"));
			//int res=new DoctorDao().delete(params);
			Record record = get(params);
			int res = doctor.delete(params);
			doctor.deleteCsysUser(params2);
			commit();
			record.put("operation","0");
			record.put("scope_id",HOSPITAL_ID);
		    Client.syncUser("admin_jq", record);
			return res;
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Record record=new DoctorDao().get(params);
			return record;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	
	public Result querydoctor(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new DoctorDao().querydoctor(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record getcdoctor(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new DoctorDao().getcdoctor(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
