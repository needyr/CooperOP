package cn.crtech.cooperop.bus.cache;

import cn.crtech.cooperop.bus.cache.service.SystemMessageTempService;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.util.CommonFun;

public class SystemMessageTemplate {

	public static final String prix = "SMT";

	public static boolean needSendM(String cid) throws Exception {
		boolean flag = false;
		Object o = MemoryCache.get(prix, cid);
		if(!CommonFun.isNe(o)){
			flag = true;
		}

		return flag;
	}

	public static void load() throws Exception {
		MemoryCache.flushArea(prix);
		SystemMessageTempService service = new SystemMessageTempService();
		service.load();
		log.release("load system template success.");
	}
}
