package cn.crtech.cooperop.crdc.action.scheme;

import java.io.UnsupportedEncodingException;

import cn.crtech.cooperop.bus.rdms.Record;

public class SchemeUtil {
	public static void inToString(Record record) throws UnsupportedEncodingException {
		if (record != null) {
			for (String key : record.keySet()) {
				Object ob = record.get(key);
				if (ob != null && ob instanceof byte[]) {
					record.put(key, new String((byte[]) ob, "GBK"));
				}
			}
		}
	}
}
