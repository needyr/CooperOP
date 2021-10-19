package cn.crtech.cooperop.ipc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.ipc.dao.AutoAuditDao;

public class AutoAuditService extends BaseService {
	
	public String insert(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new AutoAuditDao().insert(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new AutoAuditDao().update(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new AutoAuditDao().get((String)params.get("id"));
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new AutoAuditDao().query(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	/**
	 * get到一条AutoAudit数据
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Record getSingle(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new AutoAuditDao().getSingle(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	/**
	  * 药师审查结果是否返回
	  * 是 true
	  * 否 false
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public boolean isEnd(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Record end = new AutoAuditDao().isEnd(params);
			if (CommonFun.isNe(end)) {
				return true;
			}
			return false;
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Map<String, Object> auditTimeInfo(Map<String, Object> params) throws Exception{
		try {
			connect("ipc");
			Map<String, Object> rtn = new HashMap<String, Object>();
			AutoAuditDao auditDao = new AutoAuditDao();
			Record autoAudit = auditDao.getAudit(params);
			rtn.put("audit", autoAudit);
			//审查串
			Map<String, Object> auditbunch = new HashMap<String, Object>();
			auditbunch.put("auto_audit_id", autoAudit.get("id"));
			Record bunch = auditDao.getAuditBunch(auditbunch);
			rtn.put("bunchTime", bunch);//插入审查接口数据时间，插入审方返回串时间
			//时间
			List<Record> list = auditDao.procedureTime(params).getResultset();
			rtn.put("procedureTime", list);
			return rtn;
		}  catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	//审查全部问题
	public Result queryResult(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new AutoAuditDao().queryResult(params);
		} catch (Exception e) { throw e; }
		finally { disconnect(); }
	}
	
	
		/**
	    * @Method dealResult 
		* @param params auto_audit_id
		* @throws Exception
	    * @Description TODO 处理hyt审方结果,审方超时则需要处理结果，否则超时的审查结果页面不可见
		* @author yanguozhi  2019-06-04
	    */
	public void dealResult(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			if("1".equals(params.remove("is_deal"))) {
				//CheckResultInfoDao check = new CheckResultInfoDao();
				new AutoAuditDao().execFilterNotmind(params);
				/*check.updateIsNew(params);//给新开医嘱问题标记
		 		check.updateResultState(params);//审查结果标记
				 */		 		
			}
			params.remove("auto_audit_id");
			// 记录审方时间
			new AutoAuditDao().update(params);
		} catch (Exception e) {
			throw e; 
		}finally {
			disconnect();
		}
	}
	
	/**
	 * 发送医嘱到云端的线程查询,查询需要线程发送的异常医嘱
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Result querySendAuditTreah(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new AutoAuditDao().querySendAuditTreah(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	/**
	 * 更新传送到云端的时间
	 * @param params
	 * @throws Exception
	 */
	public void updateSendTime(Map<String, Object> params) throws Exception  {
		try {
			connect("ipc");
			new AutoAuditDao().updateSendTime(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	/*
	 * 存入最后一次审方通话时间,医生最后的操作结果发送到云端成功
	 */
	public void updateMessageEndTime(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			new AutoAuditDao().updateMessageEndTime(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Record get_audit_def_patient(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new AutoAuditDao().get_audit_def_patient(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
}
