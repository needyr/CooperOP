package cn.crtech.cooperop.application.quartz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.service.SystemMessageService;
import cn.crtech.cooperop.application.service.tunnel.EmailService;
import cn.crtech.cooperop.bus.message.bean.MessageConfigEmail;
import cn.crtech.cooperop.bus.message.bean.MessageEmail;
import cn.crtech.cooperop.bus.message.sender.EmailSender;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.schedule.AbstractSchedule;

public class EmailSchedule extends AbstractSchedule {
	private static boolean b = false;
	@Override
	public boolean executeOn() throws Exception {
		if(b){
			return true;
		}
		b = true;
		Result result = null;
		try {
			EmailService systemConfigEmailService = new EmailService();
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("state", "1");
			result = systemConfigEmailService.query(p);
			if (result == null || result.getCount() == 0)
				return false;
			List<Record> list = result.getResultset();
			SystemMessageService messageEmailService = new SystemMessageService();
			for (Record sce : list) {
				MessageConfigEmail mce = new MessageConfigEmail();
				mce.setHost(sce.getString("server"));
				//mce.setProtocol(sce.getString("protocol"));
				mce.setPort(sce.getLong("port"));
				mce.setUsername(sce.getString("account"));
				mce.setPassword(sce.getString("password"));
				mce.setIsSsl(sce.getLong("security_gateway"));
				mce.setDebug(sce.getLong("debugging"));
				int roadCode = sce.getInt("id");
				int maxSendNum = Integer.parseInt(sce.get("limit_num")
						.toString());
				Map<String, Object> pa = new HashMap<String, Object>();
				pa.put("tunnel_id", roadCode);
				pa.put("type", "2");
				pa.put("state", 0);
				pa.put("maxSendNum", maxSendNum);
				Result emailResult = messageEmailService.query(pa);
				if (emailResult == null || emailResult.getCount() == 0)
					continue;
				List<Record> emailList = emailResult.getResultset();
				System.out.println("[Eamil]发送条数[" + emailList.size() + "]");
				for (Record email : emailList) {
					try {
						MessageEmail me = new MessageEmail();
						me.setSubject(email.getString("subject"));
						me.setContent(email.getString("content"));
						me.setId(email.getString("id"));
						me.setMailTo(email.getString("send_to"));
						me.setMailFrom(email.getString("send_from"));
						if(email.getString("send_to").indexOf("@") > -1){
							EmailSender.send(mce, me);
						}else{
							EmailSender.sendXTY(email);
						}
						email.remove("rowno");
						email.put("state", 1);
						messageEmailService.update(email);
						Thread.sleep(500);
					} catch (Exception e) {
						new Exception("发送Email信息异常", e);
					}
				}
			}
			return true;
		} catch (Exception e) {
			throw e;
		}finally{
			b = false;
		}
	}
}
