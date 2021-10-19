package cn.crtech.cooperop.bus.schedule.core;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务
 * @author FORWAY R&D fuhong.chen
 * @version xx 1.0
 * @createTime 2010-11-1 下午01:52:20
 */

public class Task implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//任务示例ID
	private String instanceId;
	//任务 名称
	private String name;
	//执行类
	private String classz;
	//CRON表达式
	private String cron;
	//描述
	private String description;
	//授权PC的MAC地址
	private String mac;
	//状态
	private int state;
	//是否允许运行
	private boolean runable = true;
	private Date startTime;//启动时间
	
	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassz() {
		return classz;
	}

	public void setClassz(String classz) {
		this.classz = classz;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public boolean isRunable() {
		return runable;
	}

	public void setRunable(boolean runable) {
		this.runable = runable;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
}
