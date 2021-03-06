package cn.crtech.cooperop.ipc.schedule;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.im.service.MessageService;
import cn.crtech.cooperop.bus.im.ws.Connection;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.schedule.AbstractSchedule;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.ws.bean.ClientInfo;
import cn.crtech.cooperop.hospital_common.service.system.MsgAlertService;
import cn.crtech.cooperop.ipc.service.AutoAuditService;
import cn.crtech.ylz.MAEngine;

public class NoticeSchedule extends AbstractSchedule {
	private static boolean b = false;
	@Override
	public boolean executeOn() throws Exception {
		if(b){
			return true;
		}
		b = true;
		try{
		AutoAuditService aas = new AutoAuditService();
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("maxSendNum", 10);
		p.put("states", "YND");
		p.put("is_overtime", "1");
		p.put("is_notice", "0");
		Result imResult = aas.query(p);
		List<Record> imlList = imResult.getResultset();
		if(imlList.size() > 0) {
			log.debug("[im]发送条数[" + imlList.size() + "]");
		}
		ClientInfo client = new ClientInfo();
		client.setAppid("im");
		client.setRequest(new Record());
		client.setToken("root123");
		Record user = new Record();
		user.put("id", "root000");
		user.put("type", "U");
		client.setUserinfo(user);
		MessageService ms = new MessageService();
		for (Record im : imlList) {
			try {
				Map<String, Object> params = new HashMap<String, Object>();
				Map<String, Object> update_message = new HashMap<String, Object>();
				params.put("target", "U");
				params.put("send_to", im.get("system_user_id"));
				params.put("type", "T");
				params.put("send_time", new Date());
				//params.put("system_user_id", "root000");
				StringBuffer sb = new StringBuffer();
				sb.append("<div style=\"font-family: 微软雅黑; font-size: 12px; color: rgb(0, 0, 0);");
				sb.append("font-weight: normal; font-style: normal; text-decoration: none;\">");
				String s = null;
				//插入数据到弹出提示表
				Record dtMap = new Record();
				// ygz。2021-04-01 修复因缺少字段导致的消息异常
				if(im.getString("yaoshi_name") == null) im.put("yaoshi_name", "");
				if(im.getString("patient_name") == null) im.put("patient_name", "");
				if("Y".equals(im.get("state"))) {
					s = SystemConfig.getSystemConfigValue("ipc", "check_result_y_notice", "药师:pharmacist:state患者:patient的:p_type_name！");
					s = s.replace(":pharmacist", im.getString("yaoshi_name"))
							.replace(":state","<span class='color3'>已通过</span>")
							.replace(":patient", "<span class='color1'>" + im.getString("patient_name") + "</span>")
							.replace(":p_type_name", "1".equals(im.getString("p_type"))?"医嘱":"处方");
					dtMap.put("page_url", "/w/ipc/auditflow/show.html?auto_audit_id=" + im.get("id"));
				}else if("N".equals(im.get("state"))) {
					s = SystemConfig.getSystemConfigValue("ipc", "check_result_n_notice", "药师:pharmacist:state患者:patient的:p_type_name！");
					s = s.replace(":pharmacist", im.getString("yaoshi_name") + "")
							.replace(":state","<span class='color2'>已驳回</span>")
							.replace(":patient", im.getString("patient_name")+"")
							.replace(":p_type_name", "1".equals(im.getString("p_type"))?"医嘱":"处方");
					dtMap.put("page_url", "/w/ipc/auditflow/show.html?auto_audit_id=" + im.get("id"));
					dtMap.put("can_is_read", 0);
				}else if("D".equals(im.get("state"))) {
					s = SystemConfig.getSystemConfigValue("ipc", "check_result_d_notice", "药师:pharmacist已返回患者:patient的:p_type_name，:state是否用药！");
					s = s.replace(":pharmacist", im.getString("yaoshi_name") + "")
							.replace(":state","<span class='color4'>医生决定</span>")
							.replace(":patient", im.getString("patient_name")+"")
							.replace(":p_type_name", "1".equals(im.getString("p_type"))?"医嘱":"处方");
					dtMap.put("page_url", "/w/ipc/auditflow/timeoutdetail.html?auto_audit_id=" + im.get("id"));
					dtMap.put("can_is_read", 0);
				}
				sb.append(s);
				sb.append("</div>");
				params.put("content", sb.toString());
				params.put("title", "title");
				dtMap.put("params", "");
				params.put("system_user_id", client.getUserinfo().get("id"));
				dtMap.put("title", "药师审查结果反馈");
				dtMap.put("content", params.get("content"));
				if("D".equals(im.get("state"))) {
					dtMap.put("source_type", "5");
				}else if("Y".equals(im.get("state")) || "N".equals(im.get("state"))) {
					if("Y".equals(im.get("state"))) {
						dtMap.put("can_is_read", "1");
					}
					dtMap.put("source_type", "6");
				}else {
					dtMap.put("source_type", "4");
				}
				dtMap.put("send_to_user", im.get("system_user_id"));
				dtMap.put("send_by_user", "system");
				//String MsgAlert_id = new MsgAlertService().insert(dtMap);
				String MsgAlert_id = MAEngine.addNewMsg(dtMap);
				/*update_message.put("id", im.get("id"));
				update_message.put("msg_alert_id", MsgAlert_id);
				new AutoAuditService().update(update_message);*/
				Map<String, Object> pa = new HashMap<String, Object>();
				pa.put("id", im.get("id"));
				pa.put("is_notice", 1);
				pa.put("msg_alert_id", MsgAlert_id);
				pa.put("is_notice_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				aas.update(pa);
				List<Record> list = ms.send(params);
				for (Record r : list) {
					r.put("auto_audit_id", im.get("id"));
					Connection.send(client, r);
				}
				Thread.sleep(1000);
			} catch (Exception e) {
				log.error("消息发送异常: 参数：" + im.toString(), e);
				e.printStackTrace();
			}
		}
		}finally{
			b = false;
		}
		return true;
	}
}
