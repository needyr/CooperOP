package cn.crtech.precheck.ipc.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.ipc.service.AutoAuditService;


public class sendPrescriptionThread {

	public static Object sync_obj = new Object();
	public static final String HOSPITAL_ID = GlobalVar.getSystemProperty("hospital_id");//传送医嘱处方给审方平台
	public static final String AUDIT_ACTION = "initiate";//传送医嘱处方给审方平台
	
	public static void wakeup() {
		synchronized (sync_obj) {
			sync_obj.notifyAll();
		}
	}
	
	public static void init() {
		sendPrescription t = new sendPrescription();
		t.start();
	}
	
	private static class sendPrescription extends Thread {
		protected boolean isrunning = false;
		protected boolean stoped = false;
		@Override
		public void run() {
			isrunning = false;
			stoped = false;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("state", "DQ");
			AutoAuditService autoAuditService = new AutoAuditService();
			Map<String, Object> mm = new HashMap<String, Object>();
			mm.put("action", AUDIT_ACTION);
			synchronized (sync_obj) {
				while(isrunning) {
					try {
						long temp = Long.parseLong(SystemConfig.getSystemConfigValue("ipc", "send_sleep_time"));
						Thread.sleep(temp);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					try {
						map.put("await_time", SystemConfig.getSystemConfigValue("ipc", "send_await_time"));
						Result rs = autoAuditService.querySendAuditTreah(map);
						if (!CommonFun.isNe(rs)) {
							List<Record> list = rs.getResultset();
							for (Record record : list) {
								String doctor_no = (String) record.get("doctor_no");
								Map<String, Object> params = new HashMap<String, Object>();
								params.put("auto_audit_id", record.get("id"));
								params.put("id", record.get("id"));
								params.put("patient_id", record.get("patient_id"));
								params.put("visit_id", record.get("visit_id"));
								//Map<String,Object> dataMap = new DataService().queryForYXK(params);
								//dataMap.put("thread_send", "yes");
								//mm.put("data", dataMap);
								try {
									//send(doctor_no, HOSPITAL_ID, mm);
									new AutoAuditService().updateSendTime(params);
								} catch (Throwable e) {
									e.printStackTrace();
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/*private static void send(String doctor_no, String hospital_id, Map<String, Object> params) throws Throwable{
		try {
			Connection c = createConn(doctor_no, hospital_id);
			c.send(params);
		} catch (Throwable e) {
			try {
				Connection d = Client.conns.remove(doctor_no);
				if(d != null){
					try {
						d.disconnect();
					} catch (Throwable e1) {
						log.error(e1);
					}
				}
				Connection c = createConn(doctor_no, hospital_id);
				c.send(params);
			} catch (Exception e2) {
				throw e;
			}
		}
	}
	
	private static Connection createConn(String doctor_no, String hospital_id) throws Throwable{
		if(CommonFun.isNe(Client.conns.get(doctor_no))){
			Connection c = new Connection(doctor_no, hospital_id);
			Client.conns.put(doctor_no, c);
			return c;
		}else{
			return Client.conns.get(doctor_no);
		}
	}*/
}
