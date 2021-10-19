package cn.crtech.precheck.ipc.service;

import com.sun.jna.Pointer;

import cn.crtech.cooperop.bus.cache.SystemConfig;

public class HYTInit {
	public static final String HYT_ADDRESS = SystemConfig.getSystemConfigValue("ipc", "hyt_dll_audit_address");
	public static final String HYT_JGDM = SystemConfig.getSystemConfigValue("ipc", "hyt_jgdm");
	public static final String HYT_TIMEOUT = SystemConfig.getSystemConfigValue("ipc", "hyt_timeout");
	static Pointer session = null;
	
	public void init() {
		session = HYTInterface.instanceDll.HYT_Initialize(HYT_ADDRESS, HYT_JGDM, Integer.valueOf(HYT_TIMEOUT));
	}
	
	public Pointer getSession() {
		if(session == null) {
			init();
		}
		//HYTInterface.instanceDll.HYT_UnInitialize(session);
		//init();
		return session;
	}
	
	private static class HYTInitInstance{
        private static final HYTInit instance=new HYTInit();
    }
     
    private HYTInit(){}
     
    public static HYTInit getInstance(){
        return HYTInitInstance.instance;
    }
}
