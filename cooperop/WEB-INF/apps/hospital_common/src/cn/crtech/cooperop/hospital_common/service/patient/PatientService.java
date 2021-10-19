package cn.crtech.cooperop.hospital_common.service.patient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deepoove.poi.data.CellRenderData;
import com.deepoove.poi.data.TextRenderData;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.AutoCommonDao;
import cn.crtech.cooperop.hospital_common.dao.DrugSmsDao;
import cn.crtech.cooperop.hospital_common.dao.HisInPatientDao;
import cn.crtech.precheck.EngineInterface;

public class PatientService extends BaseService{
	
	public Map<String, Object> baseinfo(Map<String, Object> params) throws Throwable {
		try {
			connect("hospital_common");
			HisInPatientDao hisInPatientDao = new HisInPatientDao();
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("patient", hisInPatientDao.getpatient2(params));
			retMap.put("diagnosGroup", hisInPatientDao.querydiagnosForGroup(params).getResultset());
			retMap.put("yb", new HisInPatientDao().getPatYB(params));
			if(EngineInterface.allProduct.containsKey("hospital_imic")) {
				retMap.put("imic", 1);
			}
			return retMap;
		}catch (Throwable e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> queryOrders(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("order", new HisInPatientDao().queryenjoin(params).getResultset());
			return retMap;
		}catch(Exception ex) {
			throw new Exception();
		} finally {
			disconnect();
		}
	}
	
	//更换为系统表格显示
	public Result queryOrders2(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new HisInPatientDao().queryenjoin2(params);
		}catch(Exception ex) {
			throw new Exception();
		} finally {
			disconnect();
		}
	}
	
	public Result queryBillType(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new HisInPatientDao().queryBillType(params);
		}catch(Exception ex) {
			throw new Exception();
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> queryCheck(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("exam", new HisInPatientDao().queryexam(params).getResultset());
			return retMap;
		}catch(Exception ex) {
			throw new Exception();
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
	
	//患者详情 ：费用模块数据
	public Result queryBill(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			// 如有医保审核 ，显示转自费标识
			String imic = EngineInterface.allProduct.get("hospital_imic");
			if(CommonFun.isNe(imic)) {
				return new HisInPatientDao().queryBillItems(params);
			}else {
				// 包含转自费标识
				return new HisInPatientDao().queryBillItemsSP(params);
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	//诊断
	public Result queryDiagnosis(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new HisInPatientDao().queryDia(params);
		}catch(Exception ex) {
			throw new Exception();
		} finally {
			disconnect();
		}
	}
	
	//体征
	public Map<String, Object> querySigns(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("signs", new HisInPatientDao().queryvital(params).getResultset());
			return rtnMap;
		}catch(Exception ex) {
			throw new Exception();
		} finally {
			disconnect();
		}
	}
	
	/**
	 * 体征图表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> sig_report(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("signs", new HisInPatientDao().sig_report(params).getResultset());
			return rtnMap;
		}catch(Exception ex) {
			throw new Exception();
		} finally {
			disconnect();
		}
	}
	
	//获取体征明细
	public Map<String, Object> querySignsDetail(Map<String, Object> params) throws Exception {
		try {
			connect();
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("signdtl", new HisInPatientDao().queryvitaldetail(params).getResultset());
			return ret;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	//获取检查明细
	public Map<String, Object> queryExamDtl(Map<String, Object> params) throws Exception {
		try {
			connect();
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("jianc", new HisInPatientDao().getexamdetail(params).getResultset());
			return ret;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	//获取手术明细
	public Map<String, Object> queryOperDtl(Map<String, Object> params) throws Exception {
		try {
			connect();
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("oper", new HisInPatientDao().getOperdetail(params).getResultset());
			return ret;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	//检验
	public Map<String, Object> queryInspection(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("inspection", new HisInPatientDao().queryrequesten(params).getResultset());
			return ret;
		}catch(Exception ex) {
			throw new Exception();
		} finally {
			disconnect();
		}
	}
	
	/**
	 * 检验导出
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> exportInspection(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> retMap = new HashMap<String, Object>();
			List<Record> labs = new HisInPatientDao().queryrequestendetail(params).getResultset();
			List<Record> lab_re = new ArrayList<Record>();
			int labs_size = labs.size();
			HisInPatientDao hisInPatientDao = new HisInPatientDao();
			retMap.put("patient", hisInPatientDao.getpatient2(params));
			retMap.put("diagnosGroup", hisInPatientDao.querydiagnosForGroup(params).getResultset());
			retMap.put("yb", new HisInPatientDao().getPatYB(params));
			retMap.put("num", labs_size);
			retMap.put("hospital_name", SystemConfig.getSystemConfigValue("hospital_common", "hospital_name", ""));
			if(EngineInterface.allProduct.containsKey("hospital_imic")) {
				retMap.put("imic", 1);
			}
			if(labs_size>0) {
				int lx = 0;
				if(labs_size%2 == 0) {
					lx = labs_size/2;
				}else {
					lx = labs_size/2 + 1;
				}
				for (int i = 0; i < lx; i++) {
					Record re = new Record();
					re.put("report_item_code", "");
					re.put("report_item_name", "");
					re.put("tag", "");
					re.put("result", "");
					re.put("units", "");
					re.put("print_context", "");
					re.put("report_item_code1", "");
					re.put("report_item_name1", "");
					re.put("tag1", "");
					re.put("result1", "");
					re.put("units1", "");
					re.put("print_context1", "");
					if(i*2+1 <= labs.size()) {
						Record lab = labs.get(i*2);
						re.put("report_item_code", lab.get("report_item_code")==null?"":lab.get("report_item_code"));
						re.put("report_item_name", lab.get("report_item_name")==null?"":lab.get("report_item_name"));
						re.put("tag", lab.get("tag")==null?"":lab.get("tag"));
						re.put("result", lab.get("result")==null?"":lab.get("result"));
						re.put("units", lab.get("units")==null?"":lab.get("units"));
						re.put("print_context", lab.get("print_context")==null?"":lab.get("print_context"));
						
					}
					if(i*2+2 <= labs.size()) {
						Record lab1 = labs.get(i*2+1);
						re.put("report_item_code1", lab1.get("report_item_code")==null?"":lab1.get("report_item_code"));
						re.put("report_item_name1", lab1.get("report_item_name")==null?"":lab1.get("report_item_name"));
						re.put("tag1", lab1.get("tag")==null?"":lab1.get("tag"));
						re.put("result1", lab1.get("result")==null?"":lab1.get("result"));
						re.put("units1", lab1.get("units")==null?"":lab1.get("units"));
						re.put("print_context1", lab1.get("print_context")==null?"":lab1.get("print_context"));
					}
					lab_re.add(re);
				}
			}
			retMap.put("labs", lab_re);
			retMap.put("user_name", user().getName());
			return retMap;
		}catch(Exception ex) {
			throw new Exception();
		} finally {
			disconnect();
		}
	}
	
	/**
	 * 检查导出
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> exportCheck(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> retMap = new HashMap<String, Object>();
			
			HisInPatientDao hisInPatientDao = new HisInPatientDao();
			Result queryexam = hisInPatientDao.getexamdetail(params);
			retMap.put("exam", queryexam.getResultset());
			retMap.put("patient", hisInPatientDao.getpatient2(params));
			retMap.put("hospital_name", SystemConfig.getSystemConfigValue("hospital_common", "hospital_name", ""));
			retMap.put("user_name", user().getName());
			return retMap;
		}catch(Exception ex) {
			throw new Exception();
		} finally {
			disconnect();
		}
	}

	/**
	 * 医嘱导出
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> exportOrders(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> retMap = new HashMap<String, Object>();
			HisInPatientDao hisInPatientDao = new HisInPatientDao();
			retMap.put("patient", hisInPatientDao.getpatient2(params));
			retMap.put("orders", new HisInPatientDao().queryenjoin2(params).getResultset());
			retMap.put("hospital_name", SystemConfig.getSystemConfigValue("hospital_common", "hospital_name", ""));
			return retMap;
		}catch(Exception ex) {
			throw new Exception();
		} finally {
			disconnect();
		}
	}

	/**
	*@title exportDignosis
	*@description 诊断导出
	*@return retMap 该患者所有的诊断数据
	*@author lichuanqian
	*@date 2021-04-02 14:40
	*/
	public Map<String, Object> exportDignosis(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> retMap = new HashMap<String, Object>();
			HisInPatientDao hisInPatientDao = new HisInPatientDao();
			//病人基本信息
			retMap.put("patient", hisInPatientDao.getpatient2(params));
			//诊断信息
			retMap.put("diagnosis", hisInPatientDao.queryDia(params).getResultset());
			retMap.put("hospital_name", SystemConfig.getSystemConfigValue("hospital_common", "hospital_name", ""));
			return retMap;
		}catch(Exception ex) {
			throw new Exception();
		} finally {
			disconnect();
		}
	}
	
	/**
	 * 检验图表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> insp_report(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("report", new HisInPatientDao().insp_report(params).getResultset());
			return ret;
		}catch(Exception ex) {
			throw new Exception();
		} finally {
			disconnect();
		}
	}
	
	
	//合理用药审查及点评
	public Map<String, Object> queryIpcResult(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret = EngineInterface.getPCHistory(params);
			return ret;
		}catch(Exception ex) {
			throw new Exception();
		} finally {
			disconnect();
		}
	}
	
	//获取审查规则
	public Map<String, Object> queryImicType(Map<String, Object> params) throws Exception {
		try {
			connect();
			Map<String, Object> ret = EngineInterface.queryImicType(params);
			return ret;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	//获取审查规则
	public Result queryImicDtl(Map<String, Object> params) throws Exception {
		try {
			connect();
			Result result = null;
			Map<String, Object> ret = EngineInterface.queryImicDtl(params);
			if(!CommonFun.isNe(ret.get("dtl"))) {
				result = (Result) ret.get("dtl");
			}
			return result;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	////////////////////////////////////////////////////////////////
	public Map<String, Object> getBasicInfo(Map<String, Object> params) throws Throwable {
		try {
			connect("hospital_common");
			Map<String, Object> retMap = new HashMap<String, Object>();
			HisInPatientDao auto = new HisInPatientDao();
			Record r = auto.getpatient1(params);
			retMap.put("patient", r);
			params.put("patient_id", r.get("patient_id"));
			params.put("visit_id", r.get("visit_id"));
			retMap.put("opers", auto.detailOpers(params).getResultset());
			return retMap;
		}catch (Throwable e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	//点评获取
	public Map<String, Object> queryComment(Map<String, Object> params) throws Throwable {
		try {
			connect("hospital_common");
			Map<String, Object> retMap = new HashMap<String, Object>();
			//获取ipc 的点评结果数据
		/*	rtnStr = HttpClient.post("http://"+GlobalVar.getSystemProperty("local_ip_port","8085")+"/commentresult",params);
			retMap = CommonFun.json2Object(rtnStr, Map.class);*/
			retMap = EngineInterface.getCommentResult(params);
			return retMap;
		}catch (Throwable e) {
			//e.printStackTrace();
			throw e;
		} finally {
			disconnect();
		}
	}
	
	/**
	 * @param params 主表 auto_common 的主键 common_id
	 * @return 无 
	 * @author yan
	 * @time 2018年5月23日
	 * @function  返回调整
	 */
	public Map<String, Object> backAdjust(Map<String, Object> params) throws Exception  {
		try {
			EngineInterface.backAdjust(params);
			//HttpClient.post("http://"+GlobalVar.getSystemProperty("local_ip_port","127.0.0.1:8080")+"/backadjust",params);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new Exception("调用ipc返回调整失败...");
		}
		return null;
	}
	
	
	//获取药品简要信息
    public Map<String, Object> getSimpleInfo(Map<String, Object> params) throws Exception {
    	try {
    		connect("ipc");
			DrugSmsDao  drugsms = new DrugSmsDao();
			Map<String, Object> ret = new HashMap<String, Object>();
			List<Record> list = drugsms.querySimpleDesc(new HashMap<String, Object>()).getResultset();
			ret.put("simpleDesc", list);
			ret.put("drug", drugsms.getSimpleDrug(params));
			ret.put("assist", drugsms.queryAssist(new HashMap<String, Object>()).getResultset());
			return ret;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
    }
     
    //判断药师工作时间 
    public boolean isPharmacistWork() throws Exception {
    	boolean is_work = false ;
    	try {
        	Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String now_time =  sdf.format(date);
            String weekDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1 + "#";
            String temStr = SystemConfig.getSystemConfigValue("ipc", "time_pharmacist_work", "1#08:00-11:30,13:30-17:00A2#08:00-11:30,13:30-17:00A3#08:00-11:30,13:30-17:00A4#08:00-11:30,13:30-17:00A5#08:00-11:30,13:30-17:00");
            String []  work_times = temStr.split("A");
            for (int i = 0; i < work_times.length; i++) {
            	if(work_times[i].indexOf(weekDay) > -1) {
            		String work_times_today = work_times[i].split("#")[1]; 
            		String []  start2end = work_times_today.split(",");
                	for (String wtime : start2end) {
                		String [] time_tmp = wtime.split("-");
                		if(now_time.compareTo(time_tmp[0]) >= 0 && now_time.compareTo(time_tmp[1]) <= 0) {
                			is_work = true;
                			break;
                		}
            		}
            		break;
            	};
			}
    	}catch(Exception ex){
    		return false;
    	}
    	return is_work;
    }
    
    //检查是否已经提交给药师审查
    public Map<String, Object> hadSubmit(Map<String, Object> params) throws Exception {
    	try {
    		connect("ipc");
    		AutoCommonDao autoDao = new AutoCommonDao();
    		params.put("rtnmap", autoDao.hadSubmit(params).getResultset().get(0));
			return params;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
    }
    
    //记录医保可疑状态下的选择
    public void iMicChoose(Map<String, Object> params) throws Exception {
    	EngineInterface.iMicChoose(params);
    }
    
    /**
	 * 审方历史记录查看基础信息
	 * @param params
	 * @return
	 * @throws Throwable
	 */
	public Map<String, Object> getBasicInfoByYwlsb(Map<String, Object> params) throws Throwable {
		try {
			connect("hospital_common");
			Map<String, Object> retMap = new HashMap<String, Object>();
			HisInPatientDao auto = new HisInPatientDao();
			Record r = auto.getpatientByYwlsb(params);
			retMap.put("patient", r);
			params.put("patient_id", r.get("patient_id"));
			params.put("visit_id", r.get("visit_id"));
			retMap.put("opers", auto.detailOpers(params).getResultset());
			return retMap;
		}catch (Throwable e) {
			//e.printStackTrace();
			throw e;
		} finally {
			disconnect();
		}
	}

	/**
	 * 患者详情手术(废弃)
	 * @param params
	 * @return
	 * @throws Throwable
	 */
	@Deprecated
	public Map<String, Object> queryOper(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("operation", new HisInPatientDao().queryOperation(params).getResultset());
			return ret;
		}catch (Throwable e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	/**
	 * 患者详情手术
	 * @param params
	 * @return
	 * @throws Throwable
	 */
	public Map<String, Object> queryOper2(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("operation", new HisInPatientDao().queryOperation2(params).getResultset());
			return ret;
		}catch (Throwable e) {
			throw e;
		} finally {
			disconnect();
		}
	}
    
/*申诉理由编辑，功能待修改    *//**
	 * @author wangsen
	 * @date 2018年12月26日
	 * @param imic_info_id
	 * @return  doctor_appeal_reason
	 * @function 根据imic_info_id获取申诉理由
	 *//*
    public Map<String, Object> getAppeal(Map<String, Object> params) throws Exception {
    	return EngineInterface.getAppeal(params);
    }*/
}

