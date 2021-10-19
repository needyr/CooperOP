package cn.crtech.cooperop.application.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.dao.WeiXinDao;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.weixin.WeiXin;
import cn.crtech.cooperop.bus.weixin.WeiXin.MATERIAL_TYPE;

public class WeiXinService extends BaseService {

	@Override
	public void connect() throws Exception {
		super.connect(WeiXin.getDataBaseID());
	}

	public Result querySubscribeUser(Map<String, Object> params) throws Exception {
		try {
			connect();
			WeiXinDao wxd = new WeiXinDao();
			return wxd.querySubscribeUser(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
/*
	public void refreshSubscribeUser(Map<String, Object> params) throws Exception {
		try {
			List<Record> userlist = WeiXin.listSubscribeUser();
			connect();
			WeiXinDao wxd = new WeiXinDao();
			start();
			Record conditions = new Record();
			conditions.put("wx_code", WeiXin.getCode());
			for (Record user : userlist) {
				conditions.put("openid", user.getString("openid"));
				wxd.deleteSubscribeUser(conditions);
				user.put("wx_code", WeiXin.getCode());
				user.remove("LANGUAGE");
				user.remove("language");
				user.put("tagid_list", CommonFun.object2Json(user.get("tagid_list")));
				user.put("subscribe_time", new Date(user.getLong("subscribe_time") * 1000));
				wxd.insertSubscribeUser(user);
			}
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}

	}*/

	public void unSubscribeUser(String openid) throws Exception {
		try {
			connect();
			WeiXinDao wxd = new WeiXinDao();
			Record params = new Record();
			params.put("subscribe", 0);
			params.put("unsubscribe_time", "sysdate");
			Record conditions = new Record();
			conditions.put("openid", openid);
			conditions.put("wx_code", WeiXin.getCode());
			wxd.updateSubscribeUser(params, conditions);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	/*public void insertSubscribeUser(Record user) throws Exception {
		try {
			user.put("wx_code", WeiXin.getCode());
			connect();
			WeiXinDao wxd = new WeiXinDao();
			start();
			Record conditions = new Record();
			conditions.put("openid", user.getString("openid"));
			conditions.put("wx_code", WeiXin.getCode());
			wxd.deleteSubscribeUser(conditions);
			user.remove("LANGUAGE");
			user.remove("language");
			user.put("tagid_list", CommonFun.object2Json(user.get("tagid_list")));
			user.put("subscribe_time", new Date(user.getLong("subscribe_time") * 1000));
			wxd.insertSubscribeUser(user);
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}*/

	public Result queryMaterialNews(Map<String, Object> params) throws Exception {
		try {
			connect();
			WeiXinDao wxd = new WeiXinDao();
			Record conditions = new Record(params);
			conditions.put("wx_code", WeiXin.getCode());
			conditions.put("type", MATERIAL_TYPE.NEWS.getKey());
			return wxd.queryMaterial(conditions);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void refreshMaterialNews(Map<String, Object> params) throws Exception {
		try {
			List<Record> newslist = WeiXin.refreshMaterialNews();
			connect();
			WeiXinDao wxd = new WeiXinDao();
			start();
			Record conditions = new Record();
			conditions.put("wx_code", WeiXin.getCode());
			for (Record news : newslist) {
				conditions.put("media_id", news.getString("media_id"));
				wxd.deleteMaterial(conditions);
				news.put("wx_code", WeiXin.getCode());
				news.put("type", MATERIAL_TYPE.NEWS.getKey());
				news.put("update_time", new Date(news.getLong("update_time") * 1000));
				@SuppressWarnings("unchecked")
				Record content = new Record((Map<String, Object>) news.remove("content"));
				news.put("create_time", new Date(content.getLong("create_time") * 1000));
				wxd.insertMaterial(news);
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> news_items = (List<Map<String, Object>>) content.get("news_item");
				Map<String, Object> news_item = null;
				for (int i = 0; i < news_items.size(); i ++) {
					news_item = news_items.get(i);
					news_item.put("wx_code", news.get("wx_code"));
					news_item.put("media_id", news.get("media_id"));
					news_item.put("order_no", i + 1);
					wxd.insertMaterialContent(news_item);
				}
			}
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}

	}

	public Result queryMaterialImage(Map<String, Object> params) throws Exception {
		try {
			connect();
			WeiXinDao wxd = new WeiXinDao();
			Record conditions = new Record(params);
			conditions.put("wx_code", WeiXin.getCode());
			conditions.put("type", MATERIAL_TYPE.IMAGE.getKey());
			return wxd.queryMaterial(conditions);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void refreshMaterialImage(Map<String, Object> params) throws Exception {
		try {
			List<Record> imagelist = WeiXin.refreshMaterialImage();
			connect();
			WeiXinDao wxd = new WeiXinDao();
			start();
			Record conditions = new Record();
			conditions.put("wx_code", WeiXin.getCode());
			for (Record image : imagelist) {
				conditions.put("media_id", image.getString("media_id"));
				wxd.deleteMaterial(conditions);
				image.put("wx_code", WeiXin.getCode());
				image.put("type", MATERIAL_TYPE.IMAGE.getKey());
				image.put("update_time", new Date(image.getLong("update_time") * 1000));
				wxd.insertMaterial(image);
			}
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}

	}

	public Result queryMaterialVoice(Map<String, Object> params) throws Exception {
		try {
			connect();
			WeiXinDao wxd = new WeiXinDao();
			Record conditions = new Record(params);
			conditions.put("wx_code", WeiXin.getCode());
			conditions.put("type", MATERIAL_TYPE.VOICE.getKey());
			return wxd.queryMaterial(conditions);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void refreshMaterialVoice(Map<String, Object> params) throws Exception {
		try {
			List<Record> voicelist = WeiXin.refreshMaterialVoice();
			connect();
			WeiXinDao wxd = new WeiXinDao();
			start();
			Record conditions = new Record();
			conditions.put("wx_code", WeiXin.getCode());
			for (Record voice : voicelist) {
				conditions.put("media_id", voice.getString("media_id"));
				wxd.deleteMaterial(conditions);
				voice.put("wx_code", WeiXin.getCode());
				voice.put("type", MATERIAL_TYPE.VOICE.getKey());
				voice.put("update_time", new Date(voice.getLong("update_time") * 1000));
				wxd.insertMaterial(voice);
			}
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}

	}

	public Result queryMaterialVideo(Map<String, Object> params) throws Exception {
		try {
			connect();
			WeiXinDao wxd = new WeiXinDao();
			Record conditions = new Record(params);
			conditions.put("wx_code", WeiXin.getCode());
			conditions.put("type", MATERIAL_TYPE.VIDEO.getKey());
			return wxd.queryMaterial(conditions);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void refreshMaterialVideo(Map<String, Object> params) throws Exception {
		try {
			List<Record> videolist = WeiXin.refreshMaterialVideo();
			connect();
			WeiXinDao wxd = new WeiXinDao();
			start();
			Record conditions = new Record();
			conditions.put("wx_code", WeiXin.getCode());
			for (Record video : videolist) {
				conditions.put("media_id", video.getString("media_id"));
				wxd.deleteMaterial(conditions);
				video.put("wx_code", WeiXin.getCode());
				video.put("type", MATERIAL_TYPE.VIDEO.getKey());
				video.put("update_time", new Date(video.getLong("update_time") * 1000));
				wxd.insertMaterial(video);
			}
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}

	}

	public Result queryTemplate(Map<String, Object> params) throws Exception {
		try {
			connect();
			WeiXinDao wxd = new WeiXinDao();
			Record conditions = new Record(params);
			conditions.put("wx_code", WeiXin.getCode());
			return wxd.queryTemplate(conditions);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void refreshTemplate(Map<String, Object> params) throws Exception {
		try {
			List<Record> templatelist = WeiXin.refreshTemplate();
			connect();
			WeiXinDao wxd = new WeiXinDao();
			start();
			Record conditions = new Record();
			conditions.put("wx_code", WeiXin.getCode());
			for (Record template : templatelist) {
				conditions.put("template_id", template.getString("template_id"));
				wxd.deleteTemplate(conditions);
				template.put("wx_code", WeiXin.getCode());
				wxd.insertTemplate(template);
			}
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Result queryTemplateTransform(Map<String, Object> params) throws Exception {
		try {
			connect();
			WeiXinDao wxd = new WeiXinDao();
			Record conditions = new Record(params);
			conditions.put("wx_code", WeiXin.getCode());
			return wxd.queryTemplateTransform(conditions);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void sendTemplateMessage(Map<String, Object> params) throws Exception {
		try {
			connect();
			WeiXinDao wxd = new WeiXinDao();
			Record tt = wxd.getTemplateTransform(WeiXin.getCode(), (String)params.remove("code"));
			WeiXin.sendTemplateMessage((String)params.remove("openid"), tt, new Record(params));
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}

	}

	public Result queryMenu(Map<String, Object> params) throws Exception {
		try {
			connect();
			WeiXinDao wxd = new WeiXinDao();
			Record conditions = new Record(params);
			conditions.put("wx_code", WeiXin.getCode());
			return wxd.queryMenu(conditions);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void refreshMenu(Map<String, Object> params) throws Exception {
		try {
			List<Record> menulist = WeiXin.refreshMenu();
			connect();
			WeiXinDao wxd = new WeiXinDao();
			start();
			Record conditions = new Record();
			conditions.put("wx_code", WeiXin.getCode());
			int i = 0, j = 0;
			for (Record menu : menulist) {
				List<Map<String, Object>> snd_menu_list = (List<Map<String, Object>>) menu.remove("sub_button");
				for (Map<String, Object> snd_menu : snd_menu_list) {
					conditions.put("name", snd_menu.get("name"));
					conditions.put("parent_name", menu.getString("name"));
					wxd.deleteMenu(conditions);
				}
				conditions.remove("parent_name");
				conditions.put("name", menu.getString("name"));
				wxd.deleteMenu(conditions);
				menu.put("wx_code", WeiXin.getCode());
				menu.put("order_no", i);
				wxd.insertMenu(menu);
				j = 0;
				for (Map<String, Object> snd_menu : snd_menu_list) {
					snd_menu.remove("sub_button");
					snd_menu.put("wx_code", WeiXin.getCode());
					snd_menu.put("order_no", j);
					snd_menu.put("parent_name", menu.getString("name"));
					wxd.insertMenu(snd_menu);
					j ++;
				}
				i ++;
			}
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}

	}

	public void uploadMenu(Map<String, Object> params) throws Exception {
		try {
			connect();
			WeiXinDao wxd = new WeiXinDao();
			Record conditions = new Record(params);
			conditions.put("wx_code", WeiXin.getCode());
			Result rs = wxd.queryMenu(conditions);
			WeiXin.createMenu(rs.getResultset());
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}

	}
	
	public Result queryQRCode(Map<String, Object> params) throws Exception {
		try {
			connect();
			WeiXinDao wxd = new WeiXinDao();
			Record conditions = new Record(params);
			conditions.put("wx_code", WeiXin.getCode());
			return wxd.queryQRCode(conditions);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	/*public void createQRCode(Map<String, Object> params) throws Exception {
		try {
			String code = (String)params.get("code");
			long expire_seconds = CommonFun.isNe(params.get("expire_seconds")) ? 0 : Long.parseLong((String)params.get("expire_seconds"));
			Record p = WeiXin.createQRCode(code, expire_seconds);
			connect();
			WeiXinDao wxd = new WeiXinDao();
			start();
			Record conditions = new Record();
			conditions.put("wx_code", WeiXin.getCode());
			conditions.put("code", code);
			wxd.deleteQRCode(conditions);
			p.put("wx_code", WeiXin.getCode());
			p.put("code", code);
			p.put("expire_seconds", expire_seconds);
			p.put("create_time", "sysdate");
			wxd.insertQRCode(p);
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Record getEventReply(String event, String eventkey) throws Exception {
		try {
			connect();
			WeiXinDao wxd = new WeiXinDao();
			Record replay = wxd.getEventReply(WeiXin.getCode(), event, eventkey);
			if (replay != null) {
				replay.put("articles", wxd.listEventReplyArticles(WeiXin.getCode(), event, eventkey).getResultset());
			}
			return replay;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void insertEventLog(Record message, Record rtn) throws Exception {
		try {
			connect();
			WeiXinDao wxd = new WeiXinDao();
			Record log = new Record();
			log.put("wx_code", WeiXin.getCode());
			log.put("event", message.getString("Event"));
			log.put("eventkey", message.getString("EventKey"));
			log.put("from_openid", message.getString("FromUserName"));
			log.put("message_time", new Date(message.getLong("CreateTime") * 1000));
			log.put("message", CommonFun.object2Json(message));
			log.put("reply", CommonFun.object2Json(rtn));
			wxd.insertEventLog(log);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
		
	}*/
}
