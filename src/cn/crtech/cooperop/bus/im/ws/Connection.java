package cn.crtech.cooperop.bus.im.ws;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.websocket.CloseReason;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.im.action.IMAction;
import cn.crtech.cooperop.bus.interf.MyHttpClient;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.LocalThreadMap;
import cn.crtech.cooperop.bus.util.MD5;
import cn.crtech.cooperop.bus.ws.bean.ClientInfo;
import cn.crtech.cooperop.bus.ws.bean.Message;
import cn.crtech.cooperop.bus.ws.server.Engine;

public class Connection extends cn.crtech.cooperop.bus.ws.server.Connection {
	private final static String MD5_SPLIT = "_cooperop_im_";

	private static Map<String, Record> loginusers = new HashMap<String, Record>();
	public static Map<String, Record> disconnectusers = new HashMap<String, Record>();
	
	@SuppressWarnings("unchecked")
	public static String newConnection(Record userinfo, String clientType) {
		String token = MD5.md5(userinfo.getString("id") + MD5_SPLIT + clientType + MD5_SPLIT + System.currentTimeMillis());
		Map<String, Map<String, String>> tokens = null;
		if (loginusers.containsKey(userinfo.getString("id"))) {
			tokens = (Map<String, Map<String, String>>) loginusers.get(userinfo.getString("id")).get("tokens");
		} else {
			tokens = new HashMap<String, Map<String, String>>();
		}
		if (!tokens.containsKey(clientType)) {
			tokens.put(clientType, new HashMap<String, String>());
		}
		tokens.get(clientType).put(token, token);
		userinfo.put("tokens", tokens);
		loginusers.put(userinfo.getString("id"), userinfo);
		return token;
	}

	public Connection(String token) {
		super(token);
	}

	@Override
	public List<String> list2Tokens(Object req) {
		List<String> rtn = new ArrayList<String>();
		if (req != null) {
			@SuppressWarnings("unchecked")
			Map<String, Object> params = (Map<String, Object>) req;
			if (params.containsKey("send_to")) {
				String userid = (String) params.get("send_to");
				Record user = loginusers.get(userid);
				if (user.containsKey("tokens")) {
					@SuppressWarnings("unchecked")
					Map<String, String> tokens = (Map<String, String>) user.get("tokens");
					Iterator<String> keys = tokens.keySet().iterator();
					while (keys.hasNext()) {
						rtn.add(tokens.get(keys.next()));
					}
				}
			}
		}
		return rtn;
	}

	@Override
	public void onConnect() {
		String s = getToken();
		if (disconnectusers.containsKey(s)) {
			Record user = disconnectusers.remove(getToken());
			if (loginusers.containsKey(user.getString("id"))) {
				@SuppressWarnings("unchecked")
				Map<String, Map<String, String>> ts = (Map<String, Map<String, String>>) loginusers.get(user.getString("id")).get("tokens");
				if (!ts.containsKey(user.getString("browser"))) {
					ts.get(user.getString("browser")).put(getToken(), getToken());
					//ts.put(user.getString("browser"), getToken());
				}
			} else {
				Map<String, Map<String, String>> ts = (Map<String, Map<String, String>>) user.get("tokens");
				if (!ts.containsKey(user.getString("browser"))) {
					ts.get(user.getString("browser")).put(getToken(), getToken());
				}
				user.remove("offline_time");
				user.remove("browser");
				loginusers.put(user.getString("id"), user);
			}
		}
		
		Iterator<String> userids = loginusers.keySet().iterator();
		boolean b = false;
		while (userids.hasNext()) {
			Record user = loginusers.get(userids.next());
			if (user.containsKey("tokens")) {
				@SuppressWarnings("unchecked")
				Map<String, Map<String, String>> tokens = (Map<String, Map<String, String>>) user.get("tokens");
				Iterator<String> clientTypes = tokens.keySet().iterator();
				while (clientTypes.hasNext()) {
					Iterator<String> ts = tokens.get(clientTypes.next()).keySet().iterator();
					while (ts.hasNext()) {
						if (ts.next().equals(getToken())) {
							getClientInfo().setUserinfo(user);
							b = true;
							break;
						}
					}
				}
			}
			if (b) {
				break;
			}
		}
	}

	@Override
	public Object onMessage(Object message) throws Exception {
		// BlobItem bi = (BlobItem) message.get("img");
		// try {
		// File bf = new File("d:/" + req.get("id"), bi.getName());
		// bf.getParentFile().mkdirs();
		// bf.createNewFile();
		//
		// byte[] bytearray = new byte[512];
		// int len = 0;
		// InputStream input = bi.getInputStream();
		// FileOutputStream output = new FileOutputStream(bf);
		// try {
		// while ((len = input.read(bytearray)) != -1) {
		// output.write(bytearray, 0, len);
		// }
		// } catch (FileNotFoundException exc) {
		// throw exc;
		// } catch (SecurityException exc) {
		// throw exc;
		// } finally {
		// input.close();
		// output.close();
		// }
		// } catch (Exception ex) {
		//
		// }
		Map<String, Object> params = (Map<String, Object>) message;
		if (params.containsKey("action")) {
			IMAction action = new IMAction();
			Method m = null;
			try {
				m = IMAction.class.getMethod((String) params.get("action"), ClientInfo.class, Map.class);
			} catch (NoSuchMethodException e) {
				return null;
			}
			@SuppressWarnings("unchecked")
			Map<String, Object> msg = (Map<String, Object>) params.get("data");
			if (msg == null)
				msg = new Record();
			LocalThreadMap.put("pageid", "im.wsconnection");
			try {
				return m.invoke(action, getClientInfo(), msg);
			} finally {
				LocalThreadMap.clear();
			}
		} 
		return null;
	}

	public static void sendMessage2AllOnline(String action, Map<String, Object> params) {
		Iterator<String> userids = loginusers.keySet().iterator();
		while (userids.hasNext()) {
			sendMessage(userids.next(), action, params);
		}
	}

	public static void sendMessage(String userid, String action, Map<String, Object> params) {
		Record trade = new Record();
		trade.put("action", action);
		trade.put("data", params);
		Record user = loginusers.get(userid);
		if (user != null && user.containsKey("tokens")) {
			@SuppressWarnings("unchecked")
			Map<String, Map<String, String>> ts = (Map<String, Map<String, String>>) user.get("tokens");
			Iterator<String> clientTypes = ts.keySet().iterator();
			while (clientTypes.hasNext()) {
				Iterator<String> t = ts.get(clientTypes.next()).keySet().iterator();
				while (t.hasNext()) {
					String tmp = t.next();
					try {
						sendMessage(trade, tmp);
					} catch (Exception e) {
						//log.error("send message failed, error = " + e.getMessage() + ", user = " + user + ", trade = " + trade, e);
					}
				}
			}
		}
	}

	public static void send(ClientInfo clientinfo, Record message) {
		/*try{
			if("to".equals(message.get("send_type"))){
				String userid = message.getString("read_user_id")+"_"+SystemConfig.getSystemConfigValue("cooperop", "kehubh");
				Record param = new Record();
				if("F".equals(message.get("type"))){
					param.put("title", message.get("[文件]"));
					param.put("content", message.get("[文件]"));
				}else if("I".equals(message.get("type"))){
					param.put("title", message.get("[图片]"));
					param.put("content", message.get("[图片]"));
				}else{
					String c = "";
					if(CommonFun.isNe(message.get("title"))){
						c = message.getString("content");
						if(c.indexOf("<div") > -1){
							c = c.replace("<br />", "").replace("<br/>", "").replace("<div><br></div>", "");
							c = c.substring(c.indexOf(">")+1, c.indexOf("</div>"));
						}
					}else{
						c = message.getString("title");
					}
					if("root000".equals(message.get("system_user_id"))){
						c = "[系统消息]："+c;
					}else{
						c = "["+message.get("system_user_name")+"]："+c;
					}
					param.put("title", c);
					param.put("content", " ");
				}
				param.put("type", 1);
				param.put("platform", 0);
				param.put("platform", 0);
				param.put("userIds", userid);//可以逗号隔开多个用户发送
				MyHttpClient.sendm(param);
			}
		}catch(Exception e){
			log.error("push_ message failed, error = " + e.getMessage() + ", user = " + clientinfo.getUserinfo() + ", trade = " + message, e);
		}*/
		
		Record trade = new Record();
		trade.put("action", "send");
		trade.put("data", message);
		Record tt = loginusers.get(message.getString("read_user_id"));
		if (tt != null && tt.containsKey("tokens")) {
			@SuppressWarnings("unchecked")
			Map<String, Map<String, String>> tokens = (Map<String, Map<String, String>>) tt.get("tokens");
			Iterator<String> clientTypes = tokens.keySet().iterator();
			while (clientTypes.hasNext()) {
				Iterator<String> ts = tokens.get(clientTypes.next()).keySet().iterator();
				while (ts.hasNext()) {
					String tmp = ts.next();
					if (!tmp.equals(clientinfo.getToken())) {
						try {
							Message m = new Message();
							m.setId(CommonFun.getSSID());
							m.setParams(trade);
							m.setFrom(clientinfo);
							Engine.sendMessage(tmp, m);
						} catch (Exception e) {
							log.error("send message failed, error = " + e.getMessage() + ", user = " + clientinfo.getUserinfo() + ", trade = " + trade, e);
						}
					}
				}
			}
		}
	}

	@Override
	public void onError(Throwable error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDisconnect(CloseReason reason) {
		if(getClientInfo().getUserinfo()==null){
			return;
		}
		@SuppressWarnings("unchecked")
		Map<String, Map<String, String>> tokens = (Map<String, Map<String, String>>) getClientInfo().getUserinfo().get("tokens");
		Record user = new Record(getClientInfo().getUserinfo());
		Iterator<String> clientTypes = tokens.keySet().iterator();
		boolean found = false;
		while (clientTypes.hasNext()) {
			String clientType = clientTypes.next();
			Iterator<String> keys = tokens.get(clientType).keySet().iterator();
			String token = null;
			while (keys.hasNext()) {
				String tmp = keys.next();
				if (tmp.equals(getToken())) {
					token = tokens.get(clientType).get(tmp);
					found = true;
					Record t = new Record(user);
					t.put("offline_time", System.currentTimeMillis());
					t.put("browser", clientType);
					t.put("appid", getAppId());
					disconnectusers.put(token, t);
					break;
				}
			}
			if (found) {
				if (tokens.get(clientType).size() == 0) {
					tokens.remove(clientType);
				}
				break;
			}
		}
		if (tokens.size() == 0) {
			IMAction action = new IMAction();
			Record msg = new Record();
			msg.put("online_status", "offline");
			try {
				action.changeOnlineStatus(getClientInfo(), msg);
			} catch (Exception e) {
				log.error("save offline status failed, error = " + e.getMessage() + ", user = " + getClientInfo().getUserinfo(), e);
			}
		}
	}

	public static void disConnToken(String token, Record user){
		disconnectusers.remove(token);
		
		//((Map<String, Map<String, String>>) loginusers.get(user.getString("id")).get("tokens")).remove(token);
		Map<String, Map<String, String>> ts = (Map<String, Map<String, String>>) loginusers.get(user.getString("id")).get("tokens");
		Iterator<String> clientTypes = ts.keySet().iterator();
		while (clientTypes.hasNext()) {
			String ct = clientTypes.next();
			Iterator<String> t = ts.get(ct).keySet().iterator();
			while (t.hasNext()) {
				String tt = t.next();
				if (tt.equals(token)) {
					ts.get(ct).remove(token);
					if(ts.get(ct).size()==0){
						ts.remove(ct);
						//下线该状态？
					}
				}
			}
		}
	}
}
