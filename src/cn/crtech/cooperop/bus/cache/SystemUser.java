package cn.crtech.cooperop.bus.cache;

import cn.crtech.cooperop.bus.cache.service.SystemUserService;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import zk.jni.JavaToBiokey;

import java.util.List;

import cn.crtech.cooperop.bus.cache.MemoryCache;

public class SystemUser {

	public static final String prix = "system.user.";

	public static Record getSystemUser(String id) throws Exception {
		return (Record) MemoryCache.get(prix, id);
	}

	public static boolean CompareUser(String finger, String id) throws Exception {
		boolean flag = false;
		Record user = (Record) MemoryCache.get(prix, id);

		@SuppressWarnings("unchecked")
		List<Record> fingers = (List<Record>) user.get("fingers");
		for (Record f : fingers) {
			if (JavaToBiokey.NativeToProcess(f.getString("finger_image"),finger)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public static void load() throws Exception {
		MemoryCache.flushArea(prix);
		SystemUserService service = new SystemUserService();
		service.load();
		log.release("load system user success.");
	}
}
