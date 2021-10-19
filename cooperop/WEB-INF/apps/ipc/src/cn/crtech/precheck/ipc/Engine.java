package cn.crtech.precheck.ipc;

import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.hospital_common.util.Util;
import cn.crtech.precheck.ipc.ws.client.Client;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.precheck.ipc.engine.AuditServerRunThread;
import cn.crtech.precheck.ipc.huiyaotong.EngineDrug;
import cn.crtech.precheck.ipc.huiyaotong.EngineHYT;

public class Engine {

	public static void init() throws Throwable{
		try {
			// EngineHYT.init();
			// EngineDrug.init();
			System.out.println(" -- ipc thirdt client for webservice create successfully...");
		} catch (Exception e) {
			log.error(" -- ipc thirdt client for webservice create failed...");
			e.printStackTrace();
		}
		/*try {
			AuditServerRunThread.init();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		try {
			// Client.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//判断药师端是否启动
		try {
			String pdc = GlobalVar.getSystemProperty("iadscpyun.autostart","0");
			if ("1".equals(pdc) && !Util.existsPort("8086")){
				String path = Util.getServerPath("a-pharmacist-8.0.24");
				Util.execCMD(path+"\\jdk1.8.0_51",path,"cmd /c start "+path+"\\bin\\startup.bat");
				Thread.sleep(4000);
				new Util().killProcess();
			}
		} catch (Exception e) {e.printStackTrace();}
	}

}
