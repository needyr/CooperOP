/**
 * 
 */
package cn.crtech.cooperop.bus.message.sender;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import cn.crtech.cooperop.application.service.EmailService;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.message.bean.MessageConfigEmail;
import cn.crtech.cooperop.bus.message.bean.MessageEmail;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rm.ResourceManager;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;

/**
 * @author Administrator
 * 
 */
public class EmailSender {
	public static void send(String productCode, String mailFrom, String mailTos, String subject, String content, String attachs) throws Exception {
		String config = GlobalVar.getSystemProperty("email.config");
		if (config.charAt(0) != '/' && config.charAt(1) != ':') {
			config = GlobalVar.getWorkPath() + File.separator + config;
		}
		Properties p = CommonFun.loadPropertiesFile(config);
		MessageConfigEmail mce = new MessageConfigEmail();
		mce.setDebug(Long.parseLong(p.getProperty("debug", "0")));
		mce.setHost(p.getProperty("smtp.host"));
		mce.setPort(Long.parseLong(p.getProperty("smtp.port", "25")));
		mce.setUsername(p.getProperty("username"));
		mce.setPassword(p.getProperty("password"));
		mce.setIsSsl(Long.parseLong(p.getProperty("smtp.isssl", "0")));
		
		MessageEmail email = new MessageEmail();
		email.setProductCode(productCode);
		email.setMailFrom(CommonFun.isNe(mailFrom) ? mce.getUsername() : mailFrom);
		email.setMailTo(mailTos);
		email.setSubject(subject);
		email.setContent(content);
		email.setAttach(attachs);
		
		send(mce, email);
	}
	
	public static void sendByUser(String productCode, String mailFrom, String mailTos, String subject, String content, String attachs, String username, String password) throws Exception {
		/*String config = GlobalVar.getSystemProperty("email.config");
		if (config.charAt(0) != '/' && config.charAt(1) != ':') {
			config = GlobalVar.getWorkPath() + File.separator + config;
		}
		Properties p = CommonFun.loadPropertiesFile(config);*/
		MessageConfigEmail mce = new MessageConfigEmail();
		mce.setDebug(Long.parseLong("0"));
		mce.setHost("smtp.163.com");
		mce.setPort(Long.parseLong("25"));
		mce.setUsername(username);
		mce.setPassword(password);
		mce.setIsSsl(Long.parseLong("0"));
		
		MessageEmail email = new MessageEmail();
		email.setProductCode(productCode);
		email.setMailFrom(CommonFun.isNe(mailFrom) ? mce.getUsername() : mailFrom);
		email.setMailTo(mailTos);
		email.setSubject(subject);
		email.setContent(content);
		email.setAttach(attachs);
		
		send(mce, email);
	}
	
	public static void send(MessageConfigEmail mce, MessageEmail email)
			throws Exception {
		Long debug = mce.getDebug();
		String host = mce.getHost();
		Long port = mce.getPort();
		String userName = mce.getUsername();
		String password = mce.getPassword();
		boolean ssl = mce.getIsSsl() == 1;

		String mailFrom = CommonFun.isNe(email.getMailFrom())?userName+"@"+host.substring(host.indexOf(".")+1):email.getMailFrom();
		String mailTo = email.getMailTo();
		String mailCc = email.getMailCc();
		String mailBcc = email.getMailBcc();
		String productCode = email.getProductCode();
		String attachIds = "";
		if (email.getAttach() != null) {
			attachIds = email.getAttach();
		}
		String subject = CommonFun.isNe(email.getSubject()) ? "无主题邮件" : email
				.getSubject();
		String content = email.getContent();

		if (debug == 1) {
			log.debug("EMAIL Setting[" + host + ":" + port + "/"
					+ userName + "]");
		}

		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		if (ssl) {// SSL
			final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
			props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.socketFactory.port", port + "");
		}

		Session s = Session.getDefaultInstance(props, null);
		MimeMessage message = new MimeMessage(s);
		message.setFrom(new InternetAddress(mailFrom));
		if (!CommonFun.isNe(mailTo)) {
			String[] mailTos = mailTo.split(";");
			if (mailTos.length == 1) {
				mailTos = mailTos[0].split(",");
				if (mailTos.length == 1) {
					mailTos = mailTos[0].split(" ");
				}
			}
			List<InternetAddress> addressList = new ArrayList<InternetAddress>();
			for (int i = 0; i < mailTos.length; i++) {
				if (!CommonFun.isNe(mailTos[i].trim())) {
					addressList.add(new InternetAddress(mailTos[i].trim()));
				}
			}
			log.info("EMAIL:[" + mailFrom + " TO " + addressList + "]");
			message.setRecipients(Message.RecipientType.TO,
					addressList.toArray(new InternetAddress[1]));
		}
		if (!CommonFun.isNe(mailCc)) {
			String[] mailCcs = mailCc.split(";");
			if (mailCcs.length == 1) {
				mailCcs = mailCcs[0].split(",");
				if (mailCcs.length == 1) {
					mailCcs = mailCcs[0].split(" ");
				}
			}
			List<InternetAddress> addressList = new ArrayList<InternetAddress>();
			for (int i = 0; i < mailCcs.length; i++) {
				if (!CommonFun.isNe(mailCcs[i].trim())) {
					addressList.add(new InternetAddress(mailCcs[i].trim()));
				}
			}
			log.info("EMAIL:[" + mailFrom + " CC " + addressList + "]");
			message.setRecipients(Message.RecipientType.CC,
					addressList.toArray(new InternetAddress[1]));
		}
		if (!CommonFun.isNe(mailBcc)) {
			String[] mailBccs = mailBcc.split(";");
			if (mailBccs.length == 1) {
				mailBccs = mailBccs[0].split(",");
				if (mailBccs.length == 1) {
					mailBccs = mailBccs[0].split(" ");
				}
			}
			List<InternetAddress> addressList = new ArrayList<InternetAddress>();
			for (int i = 0; i < mailBccs.length; i++) {
				if (!CommonFun.isNe(mailBccs[i].trim())) {
					addressList.add(new InternetAddress(mailBccs[i].trim()));
				}
			}
			log.info("EMAIL:[" + mailFrom + " BCC " + addressList + "]");
			message.setRecipients(Message.RecipientType.BCC,
					addressList.toArray(new InternetAddress[1]));
		}
		message.setSubject(subject, "utf-8");
		message.setSentDate(Calendar.getInstance().getTime());
		if (attachIds.length() > 2) {
			Multipart multipart = new MimeMultipart();
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setText(content);
			multipart.addBodyPart(contentPart);

			String[] attachIdArray = attachIds.split(",");
			List<Record> attachRecordList = ResourceManager.getResource(
					productCode, attachIdArray);
			for (Record attachRecord : attachRecordList) {
				contentPart = new MimeBodyPart();
				File file = ResourceManager.getFile(false, attachRecord);
				if (file != null && file.exists()) {
					FileDataSource fds = new FileDataSource(file);
					contentPart.setDataHandler(new DataHandler(fds));
					contentPart.setFileName(MimeUtility.encodeText(attachRecord
							.getString("file_name")));
					multipart.addBodyPart(contentPart);
				}
			}
			message.setContent(multipart);
		} else {
			message.setContent(content, "text/html;charset=utf-8");
		}
		message.saveChanges();
		if (CommonFun.isNe(productCode)) {
			log.warning(email.getId()
					+ "==== productCode is Null not add attach.");
		}

		Transport transport = s.getTransport("smtp");
		transport.connect(userName, password);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

	public static void sendXTY(Map<String, Object> email){
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("type", "to");
		p.put("target", "U");
		p.put("send_to", email.get("send_to"));
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(p);
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("system_user_id", "xt");
			params.put("subject", email.get("subject"));
			params.put("content", email.get("content"));
			params.put("send_time", "sysdate");
			params.put("email_folder_id", "1");
			new EmailService().save(params, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void save(String productCode, String mailFrom,
			String mailTos, String subject, String content, String attachs,
			String username, String password) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		try {
			sendByUser(null, "1441749200@qq.com", "444954119@qq.com", "知识库调整", "是否调整完成", null, "1441749200@qq.com", "qrfetiyqtxhnhjif");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
