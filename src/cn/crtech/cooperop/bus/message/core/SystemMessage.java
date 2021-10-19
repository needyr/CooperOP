/**
 * 
 */
package cn.crtech.cooperop.bus.message.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class SystemMessage implements Serializable{
	
	private static final long serialVersionUID = -2996234123509954578L;
	private int send_type; // 1 业务申请人 2 业务下一节点审批人 3 历史审批人 4 其他人员
	private String historyNode;
	private String subject;
	private String passContent;
	private String rejectContent;
	private String smsPassContent;
	private String smsRejectContent;
	private int sendSiteMsg;
	private int sendSms;
	private int sendEmail;
	private int sendMsn;
	private int sendQq;
	private int sendWeixin;
	private String weixinTemplate;
	public String getWeixinTemplate() {
		return weixinTemplate;
	}

	public void setWeixinTemplate(String weixinTemplate) {
		this.weixinTemplate = weixinTemplate;
	}

	public int getSendWeixin() {
		return sendWeixin;
	}

	public void setSendWeixin(int sendWeixin) {
		this.sendWeixin = sendWeixin;
	}

	private List<String> receiveUsers = new ArrayList<String>();

	public int getSend_type() {
		return send_type;
	}

	public String getHistoryNode() {
		return historyNode;
	}

	public String getSubject() {
		return subject;
	}

	public String getPassContent() {
		return passContent;
	}

	public String getRejectContent() {
		return rejectContent;
	}

	public String getSmsPassContent() {
		return smsPassContent;
	}

	public String getSmsRejectContent() {
		return smsRejectContent;
	}

	public int getSendSiteMsg() {
		return sendSiteMsg;
	}

	public int getSendSms() {
		return sendSms;
	}

	public int getSendEmail() {
		return sendEmail;
	}

	public int getSendMsn() {
		return sendMsn;
	}

	public int getSendQq() {
		return sendQq;
	}

	public void setSend_type(int send_type) {
		this.send_type = send_type;
	}

	public void setHistoryNode(String historyNode) {
		this.historyNode = historyNode;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setPassContent(String passContent) {
		this.passContent = passContent;
	}

	public void setRejectContent(String rejectContent) {
		this.rejectContent = rejectContent;
	}

	public void setSmsPassContent(String smsPassContent) {
		this.smsPassContent = smsPassContent;
	}

	public void setSmsRejectContent(String smsRejectContent) {
		this.smsRejectContent = smsRejectContent;
	}

	public void setSendSiteMsg(int sendSiteMsg) {
		this.sendSiteMsg = sendSiteMsg;
	}

	public void setSendSms(int sendSms) {
		this.sendSms = sendSms;
	}

	public void setSendEmail(int sendEmail) {
		this.sendEmail = sendEmail;
	}

	public void setSendMsn(int sendMsn) {
		this.sendMsn = sendMsn;
	}

	public void setSendQq(int sendQq) {
		this.sendQq = sendQq;
	}

	public List<String> getReceiveUsers() {
		return receiveUsers;
	}

	public void addReceiveUser(String receiveUserID) {
		this.receiveUsers.add(receiveUserID);
	}

	public void setReceiveUsers(List<String> receiveUsers) {
		this.receiveUsers = receiveUsers;
	}

}
