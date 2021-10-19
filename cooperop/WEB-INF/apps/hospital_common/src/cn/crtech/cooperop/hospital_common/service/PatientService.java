package cn.crtech.cooperop.hospital_common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.HttpClient;
import cn.crtech.cooperop.hospital_common.dao.HisInPatientDao;
import cn.crtech.precheck.EngineInterface;

public class PatientService extends BaseService{
	//患者详情页面显示
	public Map<String, Object> querypatient(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> ret = new HashMap<String, Object>();
			Map<String, Object> retMap = new HashMap<String, Object>();
			HisInPatientDao autoaudit=new HisInPatientDao();
			ret.put("patient", autoaudit.getpatient2(params));
			//ret.put("enjoin", autoaudit.queryenjoin(params).getResultset());
			//ret.put("diagnos", autoaudit.querydiagnos(params).getResultset());
			ret.put("diagnosGroup", autoaudit.querydiagnosForGroup(params).getResultset());
			//ret.put("exam", autoaudit.queryexam(params).getResultset());
			//ret.put("requesten", autoaudit.queryrequesten(params).getResultset());
			//ret.put("vital", autoaudit.queryvital(params).getResultset());
			ret.put("operation", autoaudit.queryOperation2(params).getResultset());
			String rtnStr = "";
			try {
				//rtnStr = HttpClient.post("http://"+GlobalVar.getSystemProperty("local_ip_port","8085")+"/checkandcomment",params);
				//retMap = CommonFun.json2Object(rtnStr, Map.class);
				retMap = EngineInterface.getPCHistory(params);
				ret.putAll(retMap);
				return ret;
			} catch (Throwable e) {
				return ret;
			}
		}catch(Exception ex) {
			throw new Exception();
		} finally {
			disconnect();
		}
	}
	
	/**
	 * 患者详情医嘱信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Record> patientdetailYZ(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			HisInPatientDao autoaudit=new HisInPatientDao();
			return autoaudit.queryenjoin(params).getResultset();
		}catch(Exception ex) {
			throw new Exception();
		} finally {
			disconnect();
		}
	}
	
	/**
	 * 患者详情诊断信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Record> patientdetailZD(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			HisInPatientDao autoaudit=new HisInPatientDao();
			return autoaudit.querydiagnos(params).getResultset();
		}catch(Exception ex) {
			throw new Exception();
		} finally {
			disconnect();
		}
	}
	
	/**
	 * 患者详情检查信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Record> patientdetailJC(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			HisInPatientDao autoaudit=new HisInPatientDao();
			return autoaudit.queryexam(params).getResultset();
		}catch(Exception ex) {
			throw new Exception();
		} finally {
			disconnect();
		}
	}
	
	/**
	 * 患者详情检验信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Record> patientdetailJY(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			HisInPatientDao autoaudit=new HisInPatientDao();
			return autoaudit.queryrequesten(params).getResultset();
		}catch(Exception ex) {
			throw new Exception();
		} finally {
			disconnect();
		}
	}
	
	/**
	 * 患者详情体征信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Record> patientdetailTZ(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			HisInPatientDao autoaudit=new HisInPatientDao();
			return autoaudit.queryvital(params).getResultset();
		}catch(Exception ex) {
			throw new Exception();
		} finally {
			disconnect();
		}
	}
		
	//获取检查明细
	public Map<String, Object> getexamdetail(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("jianc", new HisInPatientDao().getexamdetail(params).getResultset());
			return ret;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
		
	public Map<String, Object> queryrequestendetail(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("requestendetail", new HisInPatientDao().queryrequestendetail(params).getResultset());
			return ret;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
		
	//获取体征明细
	public Map<String, Object> queryvitaldetail(Map<String, Object> params) throws Exception {
			try {
				connect("hospital_common");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("vitaldetail", new HisInPatientDao().queryvitaldetail(params).getResultset());
				return ret;
			} catch (Exception ex) {
				throw ex;
			} finally {
				disconnect();
			}
	}
		
	//获取手术明细
	public Map<String, Object> queryOperDetil(Map<String, Object> params) throws Exception {
			try {
				connect("hospital_common");
				Map<String, Object> ret = new HashMap<String, Object>();
				ret.put("operdetail", new HisInPatientDao().queryOperDetil(params).getResultset());
				return ret;
			} catch (Exception ex) {
				throw ex;
			} finally {
				disconnect();
			}
	}	
		
	//患者审查详情_____________________________________________________________________
	public Map<String, Object> queryPatientBasicInfo(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> ret = new HashMap<String, Object>();
			Map<String, Object> retMap = new HashMap<String, Object>();
			HisInPatientDao patdao =  new HisInPatientDao();
			ret.put("pat", patdao.queryPatInfo(params));
			ret.put("usedrug", patdao.queryUseDrug(params).getResultset());
			ret.put("diagnosis", patdao.queryPatDiagnosis(params).getResultset());
			ret.put("diagnosisgroup", patdao.queryPatDiagnosisGroup(params).getResultset());
			ret.put("exams", patdao.queryPatExam_Master(params).getResultset());
			ret.put("requesten", patdao.queryPatRequesten(params).getResultset());
			ret.put("vital", patdao.queryPatVital(params).getResultset());
			String rtnStr = "";
			try {
				/*rtnStr = HttpClient.post("http://"+GlobalVar.getSystemProperty("local_ip_port","8085")+"/checkandcomment",params);
				retMap = CommonFun.json2Object(rtnStr, Map.class);*/
				retMap = EngineInterface.getPCHistory(params);
				ret.putAll(retMap);
				return ret;
			} catch (Throwable e) {
				return ret;
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> queryPatExam_Detail(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("data", new HisInPatientDao().queryPatExam_Detail(params).getResultset());
			return ret;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> queryPatRequesten_Detail(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("data", new HisInPatientDao().queryPatRequesten_Detail(params).getResultset());
			return ret;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> queryPatVital_Detail(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("data", new HisInPatientDao().queryPatVital_Detail(params).getResultset());
			return ret;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
		
	public Map<String, Object> queryPatCheckRunInfo(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> ret = new HashMap<String, Object>();
			HisInPatientDao patdao =  new HisInPatientDao();
			ret.put("data", patdao.queryPatCheckRunInfo(params).getResultset());
			ret.put("data_check", patdao.queryPatCheckResult(params).getResultset());
			ret.put("data_comment", patdao.queryPatCommentResult(params).getResultset());
			return ret;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> queryPatCheckResult(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("data", new HisInPatientDao().queryPatCheckResult(params).getResultset());
			return ret;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> queryPatCommentResult(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("data", new HisInPatientDao().queryPatCommentResult(params).getResultset());
			return ret;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Map<String, Object> queryRealYizu(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> retMap = new HashMap<String, Object>();
			String rtnStr = new String();
			try {
				/*rtnStr = HttpClient.post("http://"+GlobalVar.getSystemProperty("local_ip_port","8085")+"/commentandyizu",params);
				retMap = CommonFun.json2Object(rtnStr, Map.class);*/
				retMap = EngineInterface.getCommentOrders(params);
				return retMap;
			} catch (Throwable e) {
				return retMap;
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}	
	
	//患者详情 ：费用模块数据
	public Map<String, Object> queryBillItems(Map<String, Object> params) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			connect("hospital_common");
			retMap.put("bitems", new HisInPatientDao().queryBillItems(params));
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
		return retMap;
	}	
	
}
