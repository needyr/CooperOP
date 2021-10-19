package cn.crtech.cooperop.hospital_common.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.license.License;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.additional.ShuoMSDao;
import cn.crtech.cooperop.hospital_common.dao.print.CommPrintDao;
import cn.crtech.cooperop.hospital_common.dao.AutoCommonDao;
import cn.crtech.cooperop.hospital_common.dao.DrugSmsDao;
import cn.crtech.cooperop.hospital_common.dao.HisInPatientDao;
import cn.crtech.precheck.EngineInterface;


public class ShowTurnsService extends BaseService{

	/**
	 * @param params auto_common 的 id
	 * @return 审查结果页面   需要展示的各个产品接口审查结果数据集 
	 * @author yan
	 * @time 2018年5月23日
	 * @function  取到所有需要展示在审查结果页面的数据  {展示页面使用，乱起八糟的修改起来麻烦的一批，审查结果页面重新写了一个查询}
	 */
	public Map<String, Object> detail(Map<String, Object> params) throws Throwable {
		long sta = System.currentTimeMillis();
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> proRtn = new HashMap<String,Object>();
		try {
			connect("hospital_common");
			params.put("common_id", params.get("id"));
			HashMap<String, Object> advicesMap = new HashMap<String,Object>();
			List<Object> sorts = new ArrayList<Object>();
			HisInPatientDao auto = new HisInPatientDao();
			HashMap<String, Object> product_code_check = new HashMap<String, Object>();
			// 获取患者信息
			Record r = auto.getpatient1(params);
			if(!CommonFun.isNe(r)) {
				retMap.put("patient", r);
				params.put("patient_id", r.get("patient_id"));
				params.put("visit_id", r.get("visit_id"));
			}
			//获取手术信息
			if(!CommonFun.isNe(r)) {
				retMap.put("opers", auto.detailOpers(params).getResultset());
			}
			//获取诊断信息
			List list = new HisInPatientDao().queryDiagnosis(params).getResultset();
			retMap.put("diagnosis", list);
			disconnect();
			
			//获取所有产品的审查结果
			proRtn = EngineInterface.getShowData(params);
			
			Iterator<String> iter = EngineInterface.interfaces.keySet().iterator();
			while(iter.hasNext()) {
				String code = iter.next();
				if(!CommonFun.isNe(retMap.get(code))) {
					Map<String, Object> mdp = ((Map<String, Object>)retMap.get(code));
					if(!CommonFun.isNe(mdp.get("tnum"))) {
						sorts.addAll((ArrayList<Object>)mdp.get("tnum"));
					}
				}
			}
			advicesMap.put("apa_check_sorts_name", sorts);
			
			List<Map<String, Object>> advice = new UseReasonService().queryTypeReason(advicesMap);
			List<Map<String, Object>> product_advices = new LinkedList<Map<String, Object>>();
			List<Map<String, Object>> products = new LinkedList<Map<String, Object>>();
			for(int i=0;i<sorts.size();i++) {
				String sys_product_code = (String) ((Map<String, Object>)sorts.get(i)).get("product_code");
				String String = (String)product_code_check.get(sys_product_code);
				if(CommonFun.isNe(String)) {
					List<Object> advices_add = new ArrayList<Object>();
					for (Map<String, Object> map2 : advice) {
						if(map2.get("sys_product_code").equals(sys_product_code)) {
							advices_add.add(map2);
						}
					}
					Map<String, Object> product = new HashMap<String,Object>();
					product.put("product_code", sys_product_code);
					products.add(product);
					product.put("advices", advices_add);
					product.put("tnum", ((Map<String, Object>)retMap.get(sys_product_code)).get("tnum"));
					product_advices.add(product);
					product_code_check.put(sys_product_code, "1");
					if(CommonFun.isNe(advice)) {
						product.put("is_null", "0");
					}else {
						if(i<advice.size()) {
							Map<String, Object> map = advice.get(i);
							if(CommonFun.isNe(map)) {product.put("is_null", "0");}
							else {product.put("is_null", "1");}
						}
					}
				}
			}
			retMap.put("product_advices", product_advices);
			retMap.put("product", CommonFun.object2Json(products));
			
			//-----药师审查结果-----
			Map<String,Object> auto_audit_id = new HashMap<>();
			auto_audit_id.put("id", params.get("auto_audit_id"));
			/*connect("hospital_common");
			
			disconnect();*/
			YaoShiAuditResultService auditResultService = new YaoShiAuditResultService();
			Record autoRecord = auditResultService.get(auto_audit_id);
			if(!CommonFun.isNe(autoRecord)){retMap.put("auto",auditResultService.get(auto_audit_id));}
			disconnect();
			Result comment_orders = auditResultService.getyzsAll(params);
			if(!CommonFun.isNe(comment_orders)){retMap.put("comment_orders", comment_orders.getResultset());}
			Result commonts = auditResultService.queryByAuditId(params);
			if(!CommonFun.isNe(commonts)){retMap.put("comments", commonts.getResultset());}
			Result ordersgroup = auditResultService.queryOrdersGroup(params);
			if(!CommonFun.isNe(ordersgroup)){retMap.put("ordersgroup", ordersgroup.getResultset());}
			//retMap.put("item_info", auditResultService.queryCheckResultInfo(params).getResultset());
			//----end------
		}catch (Exception e) {e.printStackTrace();} 
		finally {disconnect();}
		log.debug("#################################################获取审查结果数据耗时" + ( System.currentTimeMillis() - sta));
		proRtn.putAll(retMap);
		return proRtn;
	}
	
		/**
	    * @Method detailForCR
		* @param params common_id
		* @return 患者信息，产品审查结果数据，手术，诊断
	    * @Description 获取审查结果界面展示数据(客户端提交审查页面的那个，其他不要使用此方法)
		* @author yanguozhi  2019-06-20
	    */
	public Map<String, Object> detailForCR(Map<String, Object> params) throws Throwable {
		long sta = System.currentTimeMillis();
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> proRtn = new HashMap<String,Object>();
		try {
			connect("hospital_common");
			params.put("common_id", params.get("id"));
			HashMap<String, Object> advicesMap = new HashMap<String,Object>();
			List<Object> sorts = new ArrayList<Object>();
			HisInPatientDao auto = new HisInPatientDao();
			HashMap<String, Object> product_code_check = new HashMap<String, Object>();
			// 获取患者信息
			Record r = auto.getpatient1(params);
			if(!CommonFun.isNe(r)) {
				retMap.put("patient", r);
				params.put("patient_id", r.get("patient_id"));
				params.put("visit_id", r.get("visit_id"));
			}
			//获取手术信息
			if(!CommonFun.isNe(r)) {
				retMap.put("opers", auto.detailOpers(params).getResultset());
			}
			//获取诊断信息
			retMap.put("diagnosis", new HisInPatientDao().queryDiagnosis(params).getResultset());
			disconnect();
			
			//获取所有产品的审查结果
			proRtn = EngineInterface.getShowData(params);
			
			Iterator<String> iter = EngineInterface.interfaces.keySet().iterator();
			while(iter.hasNext()) {
				String code = iter.next();
				if(!CommonFun.isNe(proRtn.get(code))) {
					Map<String, Object> mdp = ((Map<String, Object>)proRtn.get(code));
					if(!CommonFun.isNe(mdp.get("tnum"))) {
						sorts.addAll((ArrayList<Object>)mdp.get("tnum"));
					}
				}
			}
			advicesMap.put("apa_check_sorts_name", sorts);
			
			List<Map<String, Object>> advice = new UseReasonService().queryTypeReason(advicesMap);
			List<Map<String, Object>> product_advices = new LinkedList<Map<String, Object>>();
			List<Map<String, Object>> products = new LinkedList<Map<String, Object>>();
			for(int i=0;i<sorts.size();i++) {
				String sys_product_code = (String) ((Map<String, Object>)sorts.get(i)).get("product_code");
				String String = (String)product_code_check.get(sys_product_code);
				if(CommonFun.isNe(String)) {
					List<Object> advices_add = new ArrayList<Object>();
					for (Map<String, Object> map2 : advice) {
						if(map2.get("sys_product_code").equals(sys_product_code)) {
							advices_add.add(map2);
						}
					}
					Map<String, Object> product = new HashMap<String,Object>();
					product.put("product_code", sys_product_code);
					products.add(product);
					product.put("advices", advices_add);
					product.put("tnum", ((Map<String, Object>)proRtn.get(sys_product_code)).get("tnum"));
					product_advices.add(product);
					product_code_check.put(sys_product_code, "1");
					if(CommonFun.isNe(advice)) {
						product.put("is_null", "0");
					}else {
						if(i<advice.size()) {
							Map<String, Object> map = advice.get(i);
							if(CommonFun.isNe(map)) {product.put("is_null", "0");}
							else {product.put("is_null", "1");}
						}
					}
				}
			}
			retMap.put("product_advices", product_advices);
			if(!CommonFun.isNe(proRtn.get("ipc"))) {
				retMap.put("ipc_d_type", ((Map<String, Object>)proRtn.get("ipc")).get("d_type"));
			}
			retMap.put("product", CommonFun.object2Json(products));
			retMap.put("appinfo", License.checkTime());
			proRtn.put("d_type",r.getString("d_type"));
			//----end------
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		log.debug("#################################################获取审查结果数据耗时" + ( System.currentTimeMillis() - sta));
		proRtn.putAll(retMap);
		//查询当前工作站是否能够转自费
		proRtn.put("dtype_can_toselfpaid", queryItemDTypeCanSelfPaid(new Record(params)) ? 1: 0);
		return proRtn;
	}

	//查询当前审查的d_type
	public boolean queryItemDTypeCanSelfPaid(Record params){
		if (CommonFun.isNe(params) || CommonFun.isNe(params.get("d_type"))){
			return true;
		}
		// 示例：imic_can_toselfpaid_dtype = 1,2,3
		String dtypeGroup = SystemConfig.getSystemConfigValue("hospital_imic",
				"imic_can_toselfpaid_dtype", "");
		if (dtypeGroup.indexOf(params.getString("d_type")) >= 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param params 
	 * @author yan
	 * @time 2018年11月27日
	 * @function 强制使用，将问题医嘱发送给药师审查
	 */
	public Map<String, Object> mustDo(Map<String, Object> params) throws Throwable {
		try {
			return EngineInterface.mustDo(params);
		}catch (Throwable e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	/**
	 * @param params 
	 * @author yan
	 * @time 2018年11月27日
	 * @function 获取药师审查结果 
	 */
	public Map<String, Object> getPCResult(Map<String, Object> params) throws Throwable {
		try {
			return EngineInterface.getPCResult(params);
		}catch (Throwable e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	/**
	 * @param params 
	 * @author yan
	 * @time 2018年11月27日
	 * @function 医生双签名用药[药师返回医生决定后，医生选择确认用药]
	 */
	public void sureAgain(Map<String, Object> params) throws Throwable {
		try {
			 EngineInterface.sureAgain(params);
		}catch (Throwable e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
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
    
    //获取药品简要信息
    public Map<String, Object> getOrdersCR(Map<String, Object> params) throws Exception {
    	Map<String, Object> rtnMap = new HashMap<String, Object>();
    	Map<String, Object> iparams = CommonFun.json2Object((String)params.get("id"), Map.class);
    	try {
    		connect("hospital_common");
    		HisInPatientDao auto = new HisInPatientDao();
    		Record r = auto.getpatient2(iparams);
			if(!CommonFun.isNe(r)) {
				rtnMap.put("opers", auto.detailOpers(iparams).getResultset());
				rtnMap.put("diagnosis", auto.queryDiagnosis(iparams).getResultset());
				rtnMap.put("pat", r);
				rtnMap.putAll(iparams);
			}
    		return rtnMap;
    	} catch (Exception e) {
    		throw e;
    	}finally {
    		disconnect();
    	}
    }
    
    // 简要提示说明书
 	public Map<String, Object> getbydrugcode(Map<String, Object> params) throws Exception {
 		try {
 			connect("hospital_common");
 			Map<String, Object> rtnMap = new HashMap<String, Object>();
 			ShuoMSDao  shuomsdao= new ShuoMSDao();
 			DrugSmsDao drugsms = new DrugSmsDao();
 			rtnMap.put("drug_content", shuomsdao.queryDrugByHisCode(params).getResultset());
 			rtnMap.put("smsdrug", shuomsdao.querySmsDesc(new HashMap<String, Object>()).getResultset());
 				Record record = shuomsdao.getByHiscode_zdy(params);
 				if(CommonFun.isNe(record)) {
 					record = shuomsdao.getByHiscode(params);
 					if(!CommonFun.isNe(record)) {
 						rtnMap.put("drug", record);
 						rtnMap.put("drug_name", record.get("drug_name"));
 						rtnMap.put("is_zdy", "0");
 					}else {
 						rtnMap.put("drug", 0);
 					}
 				}else {
 					rtnMap.put("drug", record);
 					rtnMap.put("drug_name", record.get("drug_name"));
 					rtnMap.put("is_zdy", "1");
 				}
 				//rtnMap.put("assist", drugsms.queryAssist(new HashMap<String, Object>()).getResultset());
 				rtnMap.put("drug_code", params.get("drug_code"));
 				Record drug = shuomsdao.getByCode(params);
 				//判断是不是药品
 				if(CommonFun.isNe(drug)) {
 					//非 药品 项目 
 					rtnMap.put("ybinfo", shuomsdao.queryClinicYBfo(params).getResultset());
 				}else {
 					rtnMap.put("ybinfo", shuomsdao.queryDrugYBfo(params).getResultset());
 				}
 			return rtnMap;
 		} catch (Exception e) {
 			throw e;
 		}finally {
 			disconnect();
 		}
 	}
 	
 	public Map<String, Object> queryDrugByHisCode(Map<String, Object> params) throws Exception {
 		try {
 			connect("hospital_common");
 			ShuoMSDao  shuomsdao= new ShuoMSDao();
 			DrugSmsDao drugsms = new DrugSmsDao();
 			params.put("drug_content", shuomsdao.queryDrugByHisCode(params).getResultset());
 			params.put("assist", drugsms.queryAssist(new HashMap<String, Object>()).getResultset());
 			params.put("local_ip",SystemConfig.getSystemConfigValue("hospital_common", "url_local", ""));
 			return params;
 		} catch (Exception e) {
 			throw e;
 		}finally {
 			disconnect();
 		}
 	}
 	
 	public Result queryAllShuoms(Map<String, Object> params) throws Exception {
 		try {
 			connect("hospital_common");
 			ShuoMSDao  shuomsdao= new ShuoMSDao();
 			return shuomsdao.queryDrugByHisCode(params);
 		} catch (Exception e) {
 			throw e;
 		}finally {
 			disconnect();
 		}
 	}

     
    //判断药师工作时间 
    public boolean isPharmacistWork(Map<String, Object> map) throws Exception {
    	/*boolean is_work = false ;
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
    	return is_work;*/
    	boolean is_work = false ;
    	try {
    		long s = System.currentTimeMillis();
    		if ("2".equals(map.get("d_type"))) {
    			Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String now_time =  sdf.format(date);
                String weekDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1 + "#";
                String temStr = SystemConfig.getSystemConfigValue("ipc", "time_pharmacist_work_outp", "1#08:00-11:30,13:30-17:00A2#08:00-11:30,13:30-17:00A3#08:00-11:30,13:30-17:00A4#08:00-11:30,13:30-17:00A5#08:00-11:30,13:30-17:00");
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
			}
    		else if ("1".equals(map.get("d_type"))) {
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
			}
    		else if ("3".equals(map.get("d_type"))) {
    			Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String now_time =  sdf.format(date);
                String weekDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1 + "#";
                String temStr = SystemConfig.getSystemConfigValue("ipc", "time_pharmacist_work_emer", "1#08:00-11:30,13:30-17:00A2#08:00-11:30,13:30-17:00A3#08:00-11:30,13:30-17:00A4#08:00-11:30,13:30-17:00A5#08:00-11:30,13:30-17:00");
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
			}
        	//String time_pharmacist_work = "08:00-10:00,13:30-14:00,14:09-17:00";
        	//log.debug("判断耗时——————————————————————————————————————"+(System.currentTimeMillis() -s ));
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
	
	public Map<String, Object> checkPrint(Map<String, Object> params) throws Throwable {
		try {
			connect("hospital_common");
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("prs", new CommPrintDao().queryBypat(params).getResultset());
			return retMap;
		}catch (Throwable e) {
			//e.printStackTrace();
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> print(Map<String, Object> params) throws Throwable {
		try {
			connect("hospital_common");
			new CommPrintDao().insert(params);
			return params;
		}catch (Throwable e) {
			throw e;
		} finally {
			disconnect();
		}
	}
    
}

