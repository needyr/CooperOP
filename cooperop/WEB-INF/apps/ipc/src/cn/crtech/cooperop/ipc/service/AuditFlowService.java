package cn.crtech.cooperop.ipc.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.application.dao.BillDao;
import cn.crtech.cooperop.application.dao.TaskDao;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.im.ws.Connection;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.workflow.workflow;
import cn.crtech.cooperop.hospital_common.service.system.MsgAlertService;
import cn.crtech.cooperop.ipc.dao.AutoAuditDao;
import cn.crtech.cooperop.ipc.dao.AutoAuditFlowDao;
import cn.crtech.precheck.ipc.ws.client.Client;
import cn.crtech.ylz.MAEngine;

public class AuditFlowService extends BaseService {
	
	public String insert(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new AutoAuditFlowDao().insert(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new AutoAuditFlowDao().update(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	public void delete(Map<String, Object> params) throws Exception {
		try {
			connect();
			new AutoAuditFlowDao().delete(params);
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new AutoAuditFlowDao().get((String)params.get("id"));
		} catch (Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	public void submit(Map<String, Object> params) throws Exception{
		try {
			connect("base");
			String djbh = new BillDao().getBusinessNo("SFJG");
			disconnect();
			connect("ipc");
			AutoAuditFlowDao aafd = new AutoAuditFlowDao();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("auto_audit_id", params.get("auto_audit_id"));
			map.put("state", "0");
			map.put("doctor_no", params.get("doctor_no"));
			map.put("djbh", djbh);
			aafd.insert(map);
			workflow.start("ipc", "ipc.audit_result", djbh, (String)params.get("department"));
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public String approval(Map<String, Object> params) throws Exception {
		MAEngine.readOne(user().getId(), (String)params.get("msg_alert_id")); //消息已读
		String hisCancelOrders = "0";
		try {
			connect("ipc");
			if(params.get("state").equals("DN")) {
				hisCancelOrders = new AutoAuditFlowDao().hisCancelOrders(params);
			}
		}catch(Exception e) {
			return "fail";
		}finally {
			disconnect();
		}
		if("0".equals(hisCancelOrders)) {
			try {
				String task_id = (String) params.remove("task_id");
				String audited = (String) params.remove("audited");
				String advice = (String) params.remove("advice");
				if(CommonFun.isNe(task_id)){
					connect("base");
					Record task = new TaskDao().getTask((String) params.get("djbh")).getResultset(0);
					disconnect();
					task_id=task.getString("id");
				}
				connect("ipc");
				start();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("state", params.get("state"));
				map.put("id", params.get("auto_audit_id"));
				map.put("hospital_id", SystemConfig.getSystemConfigValue("hospital_common", "hospital_id", "syshid"));
				Client.sendDoctorDeal((String)params.get("doctor_no"), map);
				Map<String, Object> map1 = new HashMap<String, Object>();
				Map<String, Object> msg_map = new HashMap<String, Object>();
				AutoAuditDao aad = new AutoAuditDao();
				map1.put("state", params.get("state"));
				map1.put("id", params.get("auto_audit_id"));
				map1.put("is_sure", 1);
				msg_map.put("id", params.get("msg_alert_id"));
				aad.update(map1);
				//new MsgAlertDao().updateRead(msg_map);
				 //TODO 调用存储过程回写回写标识,
				
				Map<String, Object> map2 = new HashMap<String, Object>();
				AutoAuditFlowDao aafd = new AutoAuditFlowDao();
				map2.put("state", params.get("state"));
				map2.put("id", params.get("flow_id"));
				map2.put("advice", params.get("advice"));
				map2.put("deal_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				int af = aafd.update(map2);
				commit();
				workflow.next(task_id, audited, advice);//流程下一
				return "success";
			} catch (Exception ex){
				throw ex;
			}finally{
				disconnect();
				String id = user().getId();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("send_to_user", id);
				Connection.sendMessage(id, "sendTips", new MsgAlertService().getMsgToUser(map));
			}
		}else {
			return "fail";
		}
	}
}
