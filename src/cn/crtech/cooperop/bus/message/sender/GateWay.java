package cn.crtech.cooperop.bus.message.sender;


import java.util.Map;

public interface GateWay {
	public void send(String mobile, String template, Map<String, Object> params, Object smsId) throws Exception;
	public void send(String[] mobiles, String template, Map<String, Object> params, Object smsId) throws Exception;
}
