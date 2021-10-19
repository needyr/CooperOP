package cn.crtech.cooperop.hospital_common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.AfAuditDao;
import cn.crtech.cooperop.hospital_common.dao.HisInPatientDao;
import cn.crtech.precheck.EngineInterface;

/**
 * @ClassName: AfauditService   
 * @Description: 事后审查service
 * @author: 魏圣峰 
 * @date: 2019年1月9日 下午4:58:19   
 */
public class AfAuditService extends BaseService{
	
	/**
	 * @author: 魏圣峰
	 * @description: 查询事后审查队列信息
	 * @param: params Map 筛选条件
	 * @return: Result      
	 * @throws Exception
	 */
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new AfAuditDao().query(params);
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	
	/**
	 * @author: 魏圣峰
	 * @description: 查询事后审查队列详情信息
	 * @param: params Map audit_queue_id
	 * @return: Result      
	 * @throws Exception
	 */
	public Result queryDetails(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			//如果不是超级管理用户，则进行多机构判断
			if(!user().isSupperUser()){
				//登录职工ID
				String jigou_id=user().getJigid();
				if (!CommonFun.isNe(jigou_id)){
					params.put("jigou_id",jigou_id);
				}
			}
			Result r= new AfAuditDao().queryDetails(params);
			return r;
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	
	/**
	 * @author: 魏圣峰
	 * @description: 获取统计信息
	 * @param: params Map audit_queue_id
	 * @return: Record      
	 * @throws: Exception
	 */
	public Record getStatistics(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new AfAuditDao().getStatistics(params);
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	
	/**
	 * @author: 魏圣峰
	 * @description: 判断当前审查规则是否有修改
	 * @param: Map      
	 * @return: int 0未更新 1有更新     
	 * @throws: Exception
	 */
	public int isUpdateRegulation(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new AfAuditDao().isUpdateRegulation(params);
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	
	/**
	 * @author: 魏圣峰
	 * @description: 改变事后审查队列状态
	 * @param: params Map 队列state，队列id      
	 * @return: int      
	 * @throws: Exception
	 */
	public int changeState(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new AfAuditDao().changeState(params);
			return 1;
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	
	/**
	 * @author: 魏圣峰
	 * @description: 获取临时筛选队列
	 * @param: params Map 筛选条件
	 * @return: Result      
	 * @throws: Exception
	 */
	public Result queryTmpQueue(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new AfAuditDao().queryTmpQueue(params);
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	
	/**
	 * @author: 魏圣峰
	 * @description: 获取医嘱信息
	 * @param: params Map patient_id，visit_id 
	 * @return: Result      
	 * @throws: Exception
	 */
	public Result queryDrugList(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new AfAuditDao().queryDrugList(params);
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	/**
	 * @author: 魏圣峰
	 * @description: 添加事后审查队列
	 * @param: params 筛选参数 、remark 队列描述   
	 * @return: int      
	 * @throws: Exception
	 */
	public int insertQueue(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new AfAuditDao().insertQueue(params);
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new AfAuditDao().update(params);
			return 1;
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new AfAuditDao().delete(params);
			return 1;
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new AfAuditDao().get(params);
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	
	
	/**
	 * @param params auto_common 的 id
	 * @return 审查结果页面   需要展示的各个产品接口审查结果数据集 
	 * @author yan
	 * @time 2018年5月23日
	 * @function  取到所有需要展示在审查结果页面的数据  {展示页面使用，乱起八糟的修改起来麻烦的一批，审查结果页面重新写了一个查询}
	 */
	public Map<String, Object> auditDetail(Map<String, Object> params) throws Throwable {
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
			List list = new HisInPatientDao().queryDiagnosisAll(params).getResultset();
			retMap.put("diagnosis", list);
			disconnect();
			
			//获取所有产品的审查结果
			proRtn = EngineInterface.getAfterShowData(params);
			
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
		log.debug("#################################################获取[事后审查结果数据]耗时" + ( System.currentTimeMillis() - sta));
		proRtn.putAll(retMap);
		return proRtn;
	}
}
