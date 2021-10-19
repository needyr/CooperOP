package cn.crtech.cooperop.bus.im.ws;

import java.util.Iterator;
import java.util.Map;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.LocalThreadMap;
import cn.crtech.cooperop.bus.ws.server.Engine;

public class OfflineChecker {
	private static checker checker = null;

	public static void init() {
		checker = new checker();
		checker.setName("IM线检测线程");
		checker.start();
		log.release("IM掉线检测已启动。处理线程数=1");
	}

	private static class checker extends Thread {
		protected boolean isrunning = false;
		protected boolean stoped = false;

		@Override
		public void run() {
			isrunning = true;
			stoped = false;
			LocalThreadMap.put("pageid", "IM.offlinechecker");
			while (isrunning) {
				long s = Long.parseLong(SystemConfig.getSystemConfigValue("IM", "offline_check_time", "10"));
				try {
					sleep(s * 100);
				} catch (Exception e) {
				}
				if (stoped) {
					log.info(getName() + "：侦测到关闭标识，线程退出");
					break;
				}
				try {
					Iterator<String> keys = Connection.disconnectusers.keySet().iterator();
					while (keys.hasNext()) {
						String token = keys.next();
						Record user = Connection.disconnectusers.get(token);
						Map<String, String> ts = (Map<String, String>) user.get("tokens");//给别人发消息下线
						long offline_time = (long) user.get("offline_time");
						if (System.currentTimeMillis() - offline_time >= s * 1000) {
							/*if (ts.size() == 0) {
								try {
									log.debug(getName() + ": 检测到用户掉线" + user);
									if (user != null) {
										//OrderQuene.stopService(user.getInt("businessId"));
										//lls.insert(user.getInt("businessId"), LoginlogDao.MESSAGE_LOGOUT, LoginlogDao.TERMINAL_PC);
									}
								} catch (Exception e) {
									log.error(getName() + ": 用户掉线处理异常，" + e.getMessage(), e);
								}
							}*/
							if(!"quanyuanshenfang".equals(Connection.disconnectusers.get(token).get("appid"))){
								Connection.disConnToken(token, user);
							}
						}
					}
				} catch (Exception e) {
				} finally {
				}
			}
		}
	}
}
