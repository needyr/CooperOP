package cn.crtech.cooperop.core.domain.pojo;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.core.domain.pojo.RunConfig;

/**
 * 子类扩展 - 关联Record进行执行服务或执行任务的配置参数初始化处理
 * @author chenjunhong
 * 2021年1月27日
 */
public class InitRunConfig extends RunConfig {

	public InitRunConfig(RunType type,Record e) {
		super();
		switch(type) {
		case SERVICE:
			initService(e);
			break;
		case TASK:
			initTask(e);
			break;	
		}
	}
	
	private void initService(Record e) {
		this.setWaitting(e.getInt("is_waitting"));
		this.setRunMode(e.getInt("run_mode"));
		this.setTimeout(e.getLong("timeout"));
		//
		this.sethCode(e.getString("h_code"));
		this.setpType(e.getString("p_type"));
		this.setdType(e.getString("d_type"));
		this.setQueue(e.getInt("queue"));
		this.setQueueTime(e.getLong("queue_time"));
		this.setCapacity(e.getInt("capacity"));
	}
	
	private void initTask(Record e) {
		this.setWaitting(e.getInt("is_waitting"));
		this.setRunMode(e.getInt("run_mode"));
		this.setTimeout(e.getLong("timeout"));
		//
		this.setCode(e.getString("run_interface"));
		this.setClassz(e.getString("classz"));
	}
	
}
