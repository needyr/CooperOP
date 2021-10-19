package cn.crtech.cooperop.bus.weixin.listener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.crtech.cooperop.application.service.WeiXinService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.weixin.WeiXin;
import cn.crtech.cooperop.bus.weixin.WeiXin.MATERIAL_TYPE;

public class EventEngine implements Engine {

	@Override
	public Record handle(Record message) throws Exception {
		String event = message.getString("Event");
		Record rtn = null;
		try {
			Method mtd = this.getClass().getMethod(event, Record.class);
			rtn = (Record) mtd.invoke(this, message);
		} catch (Exception e) {
		}
		if (rtn == null) {
			rtn = eventAutoReply(message);
		}
		saveLog(message, rtn);
		return rtn;
	}

	private Record eventAutoReply(Record message) throws Exception {/*
		String event = message.getString("Event");
		String eventkey = CommonFun.isNe(message.getString("EventKey")) ? event : message.getString("EventKey");
		Record reply = new WeiXinService().getEventReply(event, eventkey);

		if (reply == null)  return null;
		
		switch (MATERIAL_TYPE.valueOf(reply.getString("msgtype").toUpperCase())) {
		case TEXT: {
			Record rtn = new Record();
			rtn.put("MsgType", MATERIAL_TYPE.TEXT.getKey());
			rtn.put("Content", reply.getString("content"));
			return rtn;
		}
		case NEWS: {
			Record rtn = new Record();
			rtn.put("MsgType", MATERIAL_TYPE.NEWS.getKey());
			List<Record> articles = (List<Record>) reply.get("articles");
			rtn.put("ArticleCount", articles.size());
			List<Record> as = new ArrayList<Record>();
			Record a = null, item = null;
			int i = 0;
			for (Record article : articles) {
				item = new Record();
				a = new Record();
				a.put("Title", article.getString("title"));
				a.put("Description", article.getString("description"));
				a.put("PicUrl", article.getString("picurl"));
				a.put("Url", article.getString("url"));
				item.put("item", a);
				as.add(item);
				i ++;
				if (i > 8) {
					break;
				}
			}
			rtn.put("Articles", as);
			return rtn;
		}
		default: {
			return null;
		}
		}
	*/return null;}
	
	private void saveLog(Record message, Record rtn) throws Exception {
		//new WeiXinService().insertEventLog(message, rtn);
	}

	/**
	 * 关注
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public Record subscribe(Record message) throws Exception {
		String key = message.getString("EventKey");

		Record userinfo = WeiXin.getUserInfo(message.getString("FromUserName"));

		//new WeiXinService().insertSubscribeUser(userinfo);

		if (!CommonFun.isNe(key)) {
			if (key.startsWith("qrscene_")) {
				String qrscene = key.substring("qrscene_".length());
				Record tmp = new Record(message);
				tmp.put("Event", "SCAN");
				tmp.put("EventKey", qrscene);
				return handle(tmp);
			}
			// } else {
			// Record rtn = new Record();
			// rtn.put("MsgType", "text");
			// rtn.put("Content", "亲感谢您的关注！请通过下方菜单中的绑定/注册您的药采集账户。");
			// return rtn;
		}
		return null;
	}

	/**
	 * 取消关注
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public Record unsubscribe(Record message) throws Exception {
		new WeiXinService().unSubscribeUser(message.getString("FromUserName"));
		return null;
	}
}
