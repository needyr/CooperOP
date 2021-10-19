package cn.crtech.cooperop.bus.weixin.listener;

import cn.crtech.cooperop.bus.rdms.Record;

public interface Engine {
	public Record handle(Record message) throws Exception;
}
