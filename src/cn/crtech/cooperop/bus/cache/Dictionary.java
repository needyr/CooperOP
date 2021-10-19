package cn.crtech.cooperop.bus.cache;

import java.util.ArrayList;
import java.util.List;
import cn.crtech.cooperop.bus.cache.service.DictionaryService;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.cache.MemoryCache;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;

public class Dictionary {
	public static final String area = "system.dict";

	public static Record getField(String field) throws Exception {
		return (Record)MemoryCache.get(area, field);
	}


	@SuppressWarnings("unchecked")
	public static List<Record> listOptions(String field) throws Exception {
		Record f = (Record)MemoryCache.get(area, field);
		if(CommonFun.isNe(f)){
			return new ArrayList<Record>();
		}
		return (List<Record>) f.get("options");
	}

	public static Record getOption(String field, String option) throws Exception {
		Record f = (Record)MemoryCache.get(area, field);
		if(f!=null) {
			@SuppressWarnings("unchecked")
			List<Record> options = (List<Record>) f.get("options");
			for (Record opt : options) {
				if (opt.getString("dictlist").equals(option)) {
					return opt;
				}
			}
			return null;
		}
		else
			return null;
	}

	public static String getName(String tablefield, String option) throws Exception {
		Record opt = getOption(tablefield, option);
		return opt == null ? null : opt.getString("dictlist");
	}


	public static void load() throws Exception {
		MemoryCache.flushArea(area);
		DictionaryService service = new DictionaryService();
		service.load();
		log.release("load dictionary success.");
	}
}
