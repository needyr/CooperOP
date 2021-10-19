package cn.crtech.cooperop.bus.message.bean;

import java.io.Serializable;
import java.util.Date;

public class MessageEmail implements Serializable {

	private final static long serialVersionUID = 1L;
	private String id;	
	private String productCode;	
	private String mailFrom;	 
	private String mailTo;	
	private String mailCc;	
	private String mailBcc;	
	private String subject;	 
	private String content;	 
	private String attach;
	private Date send_time; 
	private int try_times;
	private Date last_send_time;
	private String error_msg;	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getMailFrom() {
		return mailFrom;
	}
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	public String getMailTo() {
		return mailTo;
	}
	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}
	public String getMailCc() {
		return mailCc;
	}
	public void setMailCc(String mailCc) {
		this.mailCc = mailCc;
	}
	public String getMailBcc() {
		return mailBcc;
	}
	public void setMailBcc(String mailBcc) {
		this.mailBcc = mailBcc;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public Date getSend_time() {
		return send_time;
	}
	public void setSend_time(Date send_time) {
		this.send_time = send_time;
	}
	public int getTry_times() {
		return try_times;
	}
	public void setTry_times(int try_times) {
		this.try_times = try_times;
	}
	public Date getLast_send_time() {
		return last_send_time;
	}
	public void setLast_send_time(Date last_send_time) {
		this.last_send_time = last_send_time;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
}