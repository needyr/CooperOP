package cn.crtech.precheck.message;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.precheck.message.service.MessageService;

public class MessageCenter {
	private static schedule scd = null;
	public static void reload() throws Exception {
		if (scd == null) {
			scd = new schedule();
			scd.reload();
			scd.start();
		}
	}
	
	private static class schedule extends Thread {
		private static Record email_config = null;
		
		private void reload() {
			email_config = null;
			try {
				email_config = new MessageService().getEmailConfig();
			} catch (Exception e) {
				log.error("读取异常消息通知计划配置失败", e);
			}
		}
		
		@Override
		public void run() {
			while (true) {
				try {
					reload();
					if (email_config == null ) {
						try {
							sleep(60000);
						} catch (Exception ex) {
						}
					} else {
						try {
							sleep(email_config.getInt("cycle") * 60 * 1000);
						} catch (Exception ex) {
						}
						
						new MessageService().send();
					}
				} catch (Exception ex) {
					log.error("异常消息通知计划异常", ex);
				}
			}
		}
	}
}
