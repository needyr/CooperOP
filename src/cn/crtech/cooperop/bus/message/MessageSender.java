/**
 * 
 */
package cn.crtech.cooperop.bus.message;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.application.service.SystemMessageService;
import cn.crtech.cooperop.bus.message.sender.EmailSender;
import cn.crtech.cooperop.bus.message.sender.SMSSender;

/**
 * @author Administrator
 * 
 */
public class MessageSender {
	public static void sendEmailImmediately(String productCode, String mailFrom, String mailTos, String subject, String content, String attachs) throws Exception {
		EmailSender.send(productCode, mailFrom, mailTos, subject, content, attachs);
	}
	
	public static void sendEmailByUserImmediately(String productCode, String mailFrom, String mailTos, String subject, String content, String attachs, String username, String password) throws Exception {
		EmailSender.sendByUser(productCode, mailFrom, mailTos, subject, content, attachs, username, password);
	}
	
	public static void sendEmail(String productCode, String mailFrom, String mailTos, String subject, String content, String attachs) throws Exception {
		EmailSender.save(productCode, mailFrom, mailTos, subject, content, attachs, null, null);
	}
	
	public static void sendEmailByUser(String productCode, String mailFrom, String mailTos, String subject, String content, String attachs, String username, String password) throws Exception {
		EmailSender.save(productCode, mailFrom, mailTos, subject, content, attachs, username, password);
	}
	
	public static void sendSmsImmediately(Map<String, Object> config, Map<String, Object> params) throws Exception {
		new SMSSender().sendImmediately(config, params);
	}
	public static void sendSms(Map<String, Object> config, Map<String, Object> params) throws Exception {
		new SMSSender().save(config, params);
	}
	public static void sendBillMessage(String system_product_code, String pageid, Map<String, Object> params) throws Exception {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("pageurl", pageid);
		m.put("sort", 3);
		m.put("system_product_code", system_product_code);
		new SystemMessageService().saveBatch(m, params);
	}
	public static void sendActionMessage(String system_product_code, String action, Map<String, Object> params) throws Exception {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("action_", action);
		m.put("sort", 1);
		m.put("system_product_code", system_product_code);
		new SystemMessageService().saveBatch(m, params);
	}
	public static void sendProcessMessageAfter(String system_product_code, String process, String node, Map<String, Object> params) throws Exception {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("system_product_process_id", process);
		m.put("system_product_process_node", node);
		m.put("sort", 2);
		m.put("system_product_code", system_product_code);
		new SystemMessageService().saveBatch(m, params);
	}
	public static void sendProcessMessageBefore(String system_product_code, String process, String node, Map<String, Object> params, String[] actor) throws Exception {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("system_product_process_id", process);
		m.put("system_product_process_node", node);
		m.put("sort", 4);
		m.put("system_product_code", system_product_code);
		new SystemMessageService().saveBatch(m, params, actor);
	}
	
	/*public static void testsend() throws Exception {
		ClientInfo client = new ClientInfo();
		client.setAppid("im");
		client.setRequest(new Record());
		client.setToken("root123");
		Record user = new Record();
		user.put("id", "root000");
		user.put("type", "U");
		client.setUserinfo(user);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("target", "U");
		params.put("send_to", "XTY00000241");
		params.put("type", "T");
		params.put("send_time", new Date());
		params.put("system_user_id", "root000");
		params.put("content", "<div style=\"font-family: 微软雅黑; font-size: 26px; color: rgb(0, 0, 0); "
				+"font-weight: normal; font-style: normal; text-decoration: none; line-height: 30px;\">ffffff</div>");


		MessageService ms = new MessageService();
		params.put("system_user_id", client.getUserinfo().get("id"));
		List<Record> list = ms.send(params);
		for (Record r : list) {
			Connection.send(client, r);
		}
	}*/
}
