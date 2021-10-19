package cn.crtech.cooperop.bus.weixin.listener;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;

public class TextEngine implements Engine {

	@Override
	public Record handle(Record message) throws Exception {
		String content = message.getString("Content");
		if (!CommonFun.isNe(content)) {
			if (content.indexOf("你好") >= 0) {
				Record respmsg = new Record();
				respmsg.put("MsgType", message.getString("MsgType"));
				respmsg.put("Content", "你也好!");
				return respmsg;
			}
		}
		return null;
	}

}
