package cn.crtech.cooperop.hospital_common.util;

import java.lang.reflect.Method;
import java.util.Map;

public class GetAuditClass {
	public Method ipc_audit_method;
	public Method imic_audit_method;
	public Method ipc_audit_method_thirdparty;
	private static volatile GetAuditClass instance;
	
	private GetAuditClass(){
		Class<?> ipc;
		Class<?> imic;
		Class<?> ipc_thirdparty;
		try {
			ipc = Class.forName("cn.crtech.precheck.ipc.InterfaceCenter");
			ipc_audit_method = ipc.getMethod("audit",  Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			imic = Class.forName("cn.crtech.precheck.hospital_imic.InterfaceCenter");
			imic_audit_method = imic.getMethod("audit",  Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ipc_thirdparty = Class.forName("cn.crtech.precheck.ipc.thirdparty.InterfaceCenter");
			ipc_audit_method_thirdparty = ipc_thirdparty.getMethod("audit",  Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public static GetAuditClass getInstance() {
        if (instance == null) {
            synchronized (GetAuditClass.class) {
                if (instance == null) {
                    instance = new GetAuditClass();
                }
            }
        }
        return instance;
    }
}
