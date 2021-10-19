package cn.crtech.cooperop.application.quartz;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.service.SystemMessageService;
import cn.crtech.cooperop.bus.im.service.MessageService;
import cn.crtech.cooperop.bus.im.ws.Connection;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.schedule.AbstractSchedule;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.ws.bean.ClientInfo;

public class ImSchedule extends AbstractSchedule {
	private static boolean b = false;
	@Override
	public boolean executeOn() throws Exception {
		if(b){
			return true;
		}
		b = true;
		try{
		SystemMessageService sms = new SystemMessageService();
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("maxSendNum", 50);
		p.put("state", 0);
		p.put("type", 3);
		p.put("xx", "Y");
		Result imResult = sms.query(p);
		List<Record> imlList = imResult.getResultset();
		//log.debug("[im]发送条数[" + imlList.size() + "]");
		
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
				params.put("target", "U");
				params.put("send_to", im.get("send_to"));
				params.put("type", "T");
				params.put("send_time", new Date());
				//params.put("system_user_id", "root000");
				StringBuffer sb = new StringBuffer();
				sb.append("<div style=\"font-family: 微软雅黑; font-size: 12px; color: rgb(0, 0, 0);");
				sb.append("font-weight: normal; font-style: normal; text-decoration: none;");
				if(!CommonFun.isNe(im.get("url_"))){
					sb.append("cursor: pointer;\"");
					sb.append(" onclick=\"openPage('"+im.get("url_")+"')\"");
					sb.append(">");
				}else{
					sb.append("\">");
				}
				sb.append(im.get("content"));
				sb.append("</div>");
				params.put("content", sb.toString());
				params.put("title", im.get("subject"));

				params.put("system_user_id", client.getUserinfo().get("id"));
				List<Record> list = ms.send(params);
				for (Record r : list) {
					Connection.send(client, r);
				}
				im.put("state", 1);
				im.remove("rowno");
				sms.update(im);
				Thread.sleep(1000);
			} catch (Exception e) {
				new Exception("发送im信息异常", e);
			}
		}
		}finally{
			b = false;
		}
		return true;
	}
}
