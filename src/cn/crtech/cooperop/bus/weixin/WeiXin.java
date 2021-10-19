package cn.crtech.cooperop.bus.weixin;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.session.Session;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.ELExpression;
import cn.crtech.cooperop.bus.weixin.WeiXinCore.GET_INTERFACE;
import cn.crtech.cooperop.bus.weixin.WeiXinCore.POST_INTERFACE;
import cn.crtech.cooperop.bus.weixin.util.SHA1;

public class WeiXin {
	public static enum MATERIAL_TYPE {
		NEWS("news"), TEXT("text"), IMAGE("image"), VOICE("voice"), VIDEO("video"), MUSIC("music");
		private String key;

		private MATERIAL_TYPE(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}
	}

	public static void init(String workpath, String configfile) throws Exception {
		WeiXinCore.init(workpath, configfile);
		log.info("init weixin auth WeiXin success.");
	}

	public static String getDataBaseID() {
		return WeiXinCore.database_id;
	}

	public static String getCode() {
		return WeiXinCore.code;
	}

	public static String getLoginAction() {
		return WeiXinCore.login_action;
	}

	public static boolean checkSession(Session session) {
		if (session.get("wxinfo") != null) {
			//Record userinfo = (Record)session.get("wxinfo");
			if (WeiXinCore.userauthmap.containsKey(session.getId())) {
				return true;
			}
			session.remove("wxinfo");
		}
		return false;
	}

	public static void sendMassMessage(int group_id, String content) throws Exception {
		Record msg = new Record();

		Record filter = new Record();
		filter.put("is_to_all", group_id < 0);
		if (group_id > -1) {
			filter.put("tag_id", group_id);
		}
		msg.put("filter", filter);

		msg.put("msgtype", "text");

		Record text = new Record();
		text.put("content", content);

		msg.put("text", text);

		msg = WeiXinCore.post(POST_INTERFACE.message_mass_sendall, msg);
	}

	public static void sendMassMessage(String[] open_ids, String content) throws Exception {
		Record msg = new Record();

		msg.put("touser", open_ids);

		msg.put("msgtype", "text");

		Record text = new Record();
		text.put("content", content);

		msg.put("text", text);

		msg = WeiXinCore.post(POST_INTERFACE.message_mass_send, msg);
	}

	public static String sendTemplateMessage(String open_id, Record template_transform, Record data) throws Exception {
		Record msg = new Record();

		msg.put("touser", open_id);
		msg.put("template_id", template_transform.getString("template_id"));
		msg.put("url", ELExpression.excuteExpression(template_transform.getString("url"), data));
		msg.put("topcolor", template_transform.getString("topcolor"));

		Record miniprogram = new Record();
		miniprogram.put("appid", template_transform.getString("appid"));
		miniprogram.put("pagepath", ELExpression.excuteExpression(template_transform.getString("pagepath"), data));
		msg.put("miniprogram", miniprogram);

		Record msg_data = new Record(
				CommonFun.json2Object(template_transform.getString("transform_template"), Map.class));
		Iterator<String> keys = msg_data.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			Map<String, Object> keyvalue = (Map<String, Object>) msg_data.get(key);
			keyvalue.put("value", ELExpression.excuteExpression((String) keyvalue.get("value"), data));
			keyvalue.put("color", ELExpression.excuteExpression((String) keyvalue.get("color"), data));
		}
		msg.put("data", msg_data);

		Record rtn = WeiXinCore.post(POST_INTERFACE.message_template_send, msg);
		return rtn.getString("msgid");
	}

	public static Record createQRCode(String code, long expire_seconds) throws Exception {
		Record msg = new Record();
		msg.put("action_name", expire_seconds > 0 ? "QR_STR_SCENE" : "QR_LIMIT_STR_SCENE");
		if (expire_seconds > 0) {
			msg.put("expire_seconds", expire_seconds);
		}
		Record info = new Record();
		Record scene = new Record();
		scene.put("scene_str", code);
		info.put("scene", scene);
		msg.put("action_info", info);
		Record rtn = WeiXinCore.post(POST_INTERFACE.create_qrcode, msg);
		rtn.put("content", rtn.get("url"));
		rtn.put("url", "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="
				+ URLEncoder.encode(rtn.getString("ticket"), "UTF-8"));
		return rtn;
	}

	public static List<Record> listSubscribeUser() throws Exception {
		return listSubscribeUser(null);
	}

	private static List<Record> listSubscribeUser(String next_openid) throws Exception {
		Record msg = new Record();
		if (!CommonFun.isNe(next_openid)) {
			msg.put("next_openid", next_openid);
		}
		Record rtn = WeiXinCore.post(POST_INTERFACE.user_list, msg);

		log.debug(rtn.toString());

		// int total = rtn.getInt("total");
		int count = rtn.getInt("count");
		String no = rtn.getString("next_openid");

		List<Record> userlist = new ArrayList<Record>();

		@SuppressWarnings("unchecked")
		Map<String, List<String>> data = (Map<String, List<String>>) rtn.get("data");

		for (String openid : data.get("openid")) {
			userlist.add(getUserInfo(openid));
		}

		if (!CommonFun.isNe(no) && count >= 10000) {
			userlist.addAll(listSubscribeUser(no));
		}

		return userlist;

	}

	public static Record getUserInfo(String openid) throws Exception {
		Record msg = new Record();
		msg.put("openid", openid);
		msg.put("lang", "zh_CN");

		Record userinfo = WeiXinCore.get(GET_INTERFACE.user_info, msg);

		return userinfo;
	}

	public static List<Record> refreshMaterialNews() throws Exception {
		return listMaterial("news");
	}

	public static List<Record> refreshMaterialImage() throws Exception {
		return listMaterial("image");
	}

	public static List<Record> refreshMaterialVideo() throws Exception {
		return listMaterial("video");
	}

	public static List<Record> refreshMaterialVoice() throws Exception {
		return listMaterial("voice");
	}

	/**
	 * 
	 * @param type
	 *            - voice video image news
	 * @return
	 * @throws Exception
	 */
	private static List<Record> listMaterial(String type) throws Exception {
		Record param = new Record();

		Record material_count = WeiXinCore.get(GET_INTERFACE.material_count, param);

		int type_count = material_count.getInt(type + "_count");

		param.put("type", type);
		param.put("count", 20);
		List<Record> rs = new ArrayList<Record>();
		for (int i = 0; i < type_count; i += 20) {
			param.put("offset", i);
			Record tmp = WeiXinCore.post(POST_INTERFACE.material_list, param);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> items = (List<Map<String, Object>>) tmp.get("item");
			for (Map<String, Object> item : items) {
				rs.add(new Record(item));
			}
		}

		return rs;
	}

	public static List<Record> refreshTemplate() throws Exception {
		Record param = new Record();

		Record tmp = WeiXinCore.get(GET_INTERFACE.template_list, param);

		List<Map<String, Object>> template_list = (List<Map<String, Object>>) tmp.get("template_list");

		List<Record> rs = new ArrayList<Record>();
		for (Map<String, Object> template : template_list) {
			rs.add(new Record(template));
		}

		return rs;
	}

	public static List<Record> refreshMenu() throws Exception {
		Record param = new Record();

		Record tmp = WeiXinCore.get(GET_INTERFACE.menu_list, param);

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> menu_list = (List<Map<String, Object>>) ((Map<String, Object>) tmp.get("menu"))
				.get("button");

		List<Record> rs = new ArrayList<Record>();
		for (Map<String, Object> fst_menu : menu_list) {
			rs.add(new Record(fst_menu));
		}

		return rs;
	}

	public static void createMenu(List<Record> menu_list) throws Exception {
		Record param = new Record();

		List<Record> fst_menu_list = new ArrayList<Record>();
		Record fst_menu = null, snd_menu = null;
		for (Record menu : menu_list) {
			menu.remove("rowno");
			menu.remove("rownuma");
			menu.remove("wx_code");
			menu.remove("order_no");
			if (CommonFun.isNe(menu.get("type"))) {
				menu.remove("type");
			}
			if (CommonFun.isNe(menu.get("parent_name"))) {
				fst_menu = new Record(menu);
				fst_menu.remove("parent_name");
				fst_menu_list.add(fst_menu);
			}
		}
		fst_menu = null;
		for (Record menu : menu_list) {
			if (!CommonFun.isNe(menu.get("parent_name"))) {
				if (fst_menu == null || !menu.getString("parent_name").equals(fst_menu.get("name"))) {
					for (Record t : fst_menu_list) {
						if (t.getString("name").equals(menu.getString("parent_name"))) {
							fst_menu = t;
							if (!fst_menu.containsKey("sub_button")) {
								fst_menu.put("sub_button", new ArrayList<Record>());
							}
							break;
						}
					}
				}
				snd_menu = new Record(menu);
				snd_menu.remove("parent_name");
				((List<Record>) fst_menu.get("sub_button")).add(snd_menu);
			}
		}

		param.put("button", fst_menu_list);

		WeiXinCore.post(POST_INTERFACE.menu_create, param);
	}

	public static Record getWXConfig(String url) {
		if (WeiXinCore.resourceauth == null) return null;
		if (url.indexOf('#') >= 0) {
			url = url.substring(0, url.indexOf('#'));
		}
		Record wxconfig = new Record();
		wxconfig.put("appId", WeiXinCore.AppID);
		wxconfig.put("timestamp", System.currentTimeMillis() / 1000);
		wxconfig.put("nonceStr", CommonFun.getITEMID());
		StringBuffer str = new StringBuffer();
		str.append("jsapi_ticket=" + WeiXinCore.resourceauth.getJsapi_ticket());
		str.append("&noncestr=" + wxconfig.getString("nonceStr"));
		str.append("&timestamp=" + wxconfig.getString("timestamp"));
		str.append("&url=" + url);
		String signature = SHA1.encode(str.toString());
		wxconfig.put("signature", signature);
		List<String> jsApiList = new ArrayList<String>();
		jsApiList.add("onMenuShareTimeline");
		jsApiList.add("onMenuShareAppMessage");
		jsApiList.add("onMenuShareQQ");
		jsApiList.add("onMenuShareWeibo");
		jsApiList.add("onMenuShareQZone");
		jsApiList.add("startRecord");
		jsApiList.add("stopRecord");
		jsApiList.add("onVoiceRecordEnd");
		jsApiList.add("playVoice");
		jsApiList.add("pauseVoice");
		jsApiList.add("stopVoice");
		jsApiList.add("onVoicePlayEnd");
		jsApiList.add("uploadVoice");
		jsApiList.add("downloadVoice");
		jsApiList.add("chooseImage");
		jsApiList.add("previewImage");
		jsApiList.add("uploadImage");
		jsApiList.add("downloadImage");
		jsApiList.add("translateVoice");
		jsApiList.add("getNetworkType");
		jsApiList.add("openLocation");
		jsApiList.add("getLocation");
		jsApiList.add("hideOptionMenu");
		jsApiList.add("showOptionMenu");
		jsApiList.add("hideMenuItems");
		jsApiList.add("showMenuItems");
		jsApiList.add("hideAllNonBaseMenuItem");
		jsApiList.add("showAllNonBaseMenuItem");
		jsApiList.add("closeWindow");
		jsApiList.add("scanQRCode");
		jsApiList.add("chooseWXPay");
		jsApiList.add("openProductSpecificView");
		jsApiList.add("addCard");
		jsApiList.add("chooseCard");
		jsApiList.add("openCard");
		wxconfig.put("jsApiList", jsApiList);
		return wxconfig;
	}

}
