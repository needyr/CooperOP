package cn.crtech.precheck.ipc.engine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.ipc.service.AuditServerResetLogService;

public class AuditServerRunThread {

	public static Object sync_obj = new Object();
	public static final String DLL_SERVER_CMD = "HYTSYSTEM";
	
	public static void wakeup() {
		synchronized (sync_obj) {
			sync_obj.notifyAll();
		}
	}
	
	public static void init() {
		Heart t = new Heart();
		t.setName("监测HYT服务,  是否关闭监测");
		t.start();
	}
	
	private static class Heart extends Thread {
		protected boolean isrunning = false;
		protected boolean stoped = false;
		@Override
		public void run() {
			isrunning = true;
			stoped = false;
			synchronized (sync_obj) {
				while(isrunning) {
					try {
						Thread.sleep(300000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					try {
						BufferedReader br = null;
						try {
							Runtime rt = Runtime.getRuntime();
							//Process p = rt.exec("sc start MySQl57"); // OK
					        //Process p = rt.exec("sc start tomcat_dll8"); // OK
							//rt.exec("sc start tomcat_dll8");
							Process p = rt.exec("net start");
					        br = new BufferedReader(new InputStreamReader(p.getInputStream()));
					        String line = null;
					        List<String> list = new ArrayList<String>();
					        while((line=br.readLine())!=null){
					            list.add(new String(line.getBytes("gb2312"), "gb2312").trim());
					        }
					        br.close();
					        if (!list.contains("HYTSYSTEM")) {
								rt.exec("net start "+DLL_SERVER_CMD);
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("message", "重新启动了一次HYT审查接口服务!!!");
								map.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
								new AuditServerResetLogService().insert(map);
							}
						} catch (Exception e) {
							e.printStackTrace();
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("message", "重启错误!!!");
							map.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
							new AuditServerResetLogService().insert(map);
						}finally {
							if (br != null) {
								br.close();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						isrunning = true;
					}
				}
			}
		}
	}
}
