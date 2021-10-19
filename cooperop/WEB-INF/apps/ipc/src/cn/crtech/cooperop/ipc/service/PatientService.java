package cn.crtech.cooperop.ipc.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.HisInPatientDao;

public class PatientService extends BaseService{

	//病人详情页面显示
	public Result queryOperation(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.put("fenye", 1);
			return new HisInPatientDao().queryOperation2(params);
		} finally {
			disconnect();
		}
	}
	
	public Record getPatient2(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.put("fenye", 1);
			return  new HisInPatientDao().getpatient2(params);
		} finally {
			disconnect();
		}
	}
	
	public Result queryEnjoin(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.put("fenye", 1);
			return  new HisInPatientDao().queryenjoin(params);
		} finally {
			disconnect();
		}
	}
	
	public Result queryDiagnos(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.put("fenye", 1);
			return  new HisInPatientDao().querydiagnos(params);
		} finally {
			disconnect();
		}
	}
	
	public Result queryExam(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.put("fenye", 1);
			return  new HisInPatientDao().queryexam(params);
		} finally {
			disconnect();
		}
	}
	
	public Result queryRequesten(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.put("fenye", 1);
			return  new HisInPatientDao().queryrequesten(params);
		} finally {
			disconnect();
		}
	}
	
	public Result queryVital(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.put("fenye", 1);
			return  new HisInPatientDao().queryvital(params);
		} finally {
			disconnect();
		}
	}
	
	//获取检查明细
	public Result getExamDetail(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.put("fenye", 1);
			return new HisInPatientDao().getexamdetail(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Result queryRequestenDetail(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.put("fenye", 1);
			return new HisInPatientDao().queryrequestendetail(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
}
	
	//获取体征明细
	public Result queryVitalDetail(Map<String, Object> params) throws Exception {
			try {
				connect("hospital_common");
				//params.put("fenye", 1);
				return new HisInPatientDao().queryvitaldetail(params);
			} catch (Exception ex) {
				throw ex;
			} finally {
				disconnect();
			}
	}
	
	//获取手术明细
	public Result queryOperDetil(Map<String, Object> params) throws Exception {
			try {
				connect("hospital_common");
				params.put("fenye", 1);
				return new HisInPatientDao().queryOperDetil(params);
			} catch (Exception ex) {
				throw ex;
			} finally {
				disconnect();
			}
	}	
}
