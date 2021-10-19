package cn.crtech.cooperop.hospital_common.schedule;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.crtech.cooperop.bus.im.ws.Connection;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.ylz.MAEngine;

public class MsgSendSchedule {
	public static Object sync_obj = new Object();

	public static void init() {
		auditlive t = new auditlive();
		t.setName("User弹窗消息发送线程");
		t.start();
	}

	public static void wakeup() {
		synchronized (sync_obj) {
			sync_obj.notifyAll();
		}
	}

	private static class auditlive extends Thread {

		protected boolean isrunning = false;
		protected boolean stoped = false;

		@Override
		public void run() {
			isrunning = true;
			stoped = false;
			try {
				while (isrunning) {
					Map<String, Object> msgAll = MAEngine.getMsgAll();
					if(msgAll != null) {
						Iterator<Entry<String, Object>> entries = msgAll.entrySet().iterator();
						while(entries.hasNext()){
							Record data = new Record();
							int count = 0;
							String send_to_user = "";
							String msg_ids = "";
						    Entry<String, Object> entry = entries.next();
						    List<Record> value = (List<Record>) entry.getValue();
						    for (Record record : value) {
						    	if(count == 0) {
						    		send_to_user = record.getString("send_to_user");
						    		msg_ids = record.getString("id");
						    	}else {
						    		msg_ids = msg_ids + "," + record.getString("id");
						    	}
				    			count++;
							}
						    data.put("send_to_user", send_to_user);
						    data.put("count", count);
						    data.put("msg_ids", msg_ids);
						    try {
						    	Connection.sendMessage(send_to_user, "sendTips", data);
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}
					
					/*MsgAlertService msg = new MsgAlertService();
					List<Record> re = msg.queryMsgToUser(null).getResultset();
					if(re.size()>0) {
						for (Record record : re) {
							try {
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("action", "sendTips");
								map.put("data", record);
								Connection.sendMessage(record.getString("send_to_user"), "sendTips", record);
							} catch (Exception ex) {
								ex.printStackTrace();
								log.error(ex);
							}
						}
					}*/
					try {
						sleep(10000);
					} catch (Exception ex) {
					}
				}
			} catch (Exception ex) {
				log.error(getName() + "：运行异常", ex);
			} finally {
				isrunning = false;
			}
		}

	}
}
