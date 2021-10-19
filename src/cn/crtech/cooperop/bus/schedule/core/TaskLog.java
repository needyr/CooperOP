package cn.crtech.cooperop.bus.schedule.core;

import java.util.Date;

/**
 * 任务-日志
 * 
 * @author FORWAY R&D fuhong.chen
 * @version xx 1.0
 * @createTime 2010-11-1 下午01:52:20
 */
public class TaskLog {
	//日志编号
	private int id;
	// 任务实例ID
	private String instanceId;
	// 开始时间
	private Date startTime;
	// 结束时间
	private Date endTime;
	// 执行PC
	private String computer;
	// 执行结果
	private String result;

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getComputer() {
		return computer;
	}

	public void setComputer(String computer) {
		this.computer = computer;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "实例:"+instanceId+"\t结果:"+result;
	}
	
	
}
