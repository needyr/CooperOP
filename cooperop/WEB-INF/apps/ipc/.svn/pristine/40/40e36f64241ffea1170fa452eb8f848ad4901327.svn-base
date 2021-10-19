package cn.crtech.precheck.ipc.ws.client;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.HttpClient;
import cn.crtech.precheck.ipc.ws.bean.BlobItem;
import cn.crtech.precheck.ipc.service.DataService;
import cn.crtech.precheck.ipc.ws.bean.Message;

@ClientEndpoint
public class Client_bak {
	
	private static String LOGIN_URL;
	private static String LOGOUT_URL;
	private static String WS_URL;
	private static boolean onconnect;
	public static Map<String, Object> reMap = new HashMap<String, Object>();
	public static void init() throws Throwable {
		String server_url = GlobalVar.getSystemProperty("yun.server_url");
		LOGIN_URL = server_url + "/w/yaoxunkang/yxkclientlogin.json";
		LOGOUT_URL = server_url + "/w/yaoxunkang/yxkclientlogout.json";
		if (server_url.indexOf("https://") == 0) {
			WS_URL = server_url.replaceAll("https://", "wss://");
		} else {
			WS_URL = server_url.replaceAll("http://", "ws://");
		}
		WS_URL += "/ws";
		//connect();
	}

	public void send(Map<String, Object> params) throws Throwable {
		connect();
		sendMessage(params);
	}

	private  Session session;
	private  Map<String, Object> logininfo;

	private void connect() throws Throwable {
		//JSONP登录获取token
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", "csmd001");
		params.put("password", "000000");
		logininfo = CommonFun.json2Object(HttpClient.post(LOGIN_URL, params), Map.class);
		onconnect = true;
		String url = WS_URL;
		if (url.startsWith("wss")) {
//			int port = 443;
//			if (url.indexOf(":", "wss://".length() + 1) > 0) {
//				String p = url.substring(url.indexOf(":", "wss://".length() + 1) + 1);
//				if (p.indexOf("/") > 0) {
//					p = p.substring(0, p.indexOf("/"));
//				}
//				port = Integer.parseInt(p);
//			}
//			Protocol.registerProtocol("wss", new Protocol("wss", new MySecureProtocolSocketFactory(), port));
		}
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		session = container.connectToServer(Client.class, new URI(WS_URL + "?" + logininfo.get("app_key") + "=" + logininfo.get("app_id") + "&" + logininfo.get("token_key") + "=" + logininfo.get("ws_token")));
	}

	private void disconnect() throws Throwable {
		session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "消息发送完毕，主动断开"));
		Map<String, Object> params = new HashMap<String, Object>();
		HttpClient.post(LOGOUT_URL + "?" + logininfo.get("session_name") + "=" + logininfo.get("session_id"), params);
	}

	
	/**
	 * 建立链接
	 * 
	 * @param session
	 * @throws IOException
	 */
	@OnOpen
	public void onOpen(Session session) {
		log.debug("接收到来自" + session.getId() + "的链接: " + session.getBasicRemote());
		new HeartSend().start();
	}

	
	/**
	 * 接收消息
	 * 
	 * @param message
	 * @param session
	 */
	@OnMessage
	public void onTextMessage(Session session, String message, boolean last) {
		log.debug("接收到来自" + session.getId() + "的文本消息[" + last + "]：" + message);
		Map<String, Object> map = CommonFun.json2Object(message, Map.class);
		if("heartbeat".equals(map.get("type"))){
			onconnect = true;
		}
		Map<String, Object> params = (Map<String, Object>) map.get("params");
		try {
			if(!CommonFun.isNe(params) && "yxkauditresult".equals(params.get("action"))){
				Map<String, Object> m = (Map<String, Object>) params.get("data");
				reMap.put("return_"+(String)m.get("id"), m);
				new DataService().update(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 接收消息
	 * 
	 * @param message
	 * @param session
	 */
	@OnMessage
	public void onBinaryMessage(Session session, ByteBuffer message, boolean last) {
		log.debug("接收到来自" + session.getId() + "的二进制消息[" + last + "]：" + message);

	}

	/**
	 * 接收消息
	 * 
	 * @param message
	 * @param session
	 */
	@OnMessage
	public void onPongMessage(Session session, PongMessage message) {
		log.debug("接收到来自" + session.getId() + "的PONG消息：" + message);
	}

	/**
	 * 连接断开
	 */
	@OnClose
	public void onClose(Session session) {
		log.debug("和" + session.getId() + "断开");
		try {
			init();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * 链接异常
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.err.println("接收到来自" + session.getId() + "的异常：" + error.getMessage());
		error.printStackTrace();
	}


	private void sendMessage(Map<String, Object> params) throws IOException {
		Message message = null;
		List<Message> blobs = new ArrayList<Message>();
		// TODO Auto-generated method stub
		message = new Message();
		message.setId(CommonFun.getSSID());
		message.setType(Message.TYPE_TEXT);
		message.setState(Message.STATE_END);
		message.setTo(null);
		message.setMulti(false);
		List<String> path = new ArrayList<String>();
		path.add("params");
		Iterator<String> keys = params.keySet().iterator();
		while (keys.hasNext()) {
			String k = keys.next();
			computeblob(k, params, path, message, blobs);
		}
		message.setParams(params);

		if (blobs.size() == 0) {
			session.getBasicRemote().sendText(CommonFun.object2Json(message));
		} else {
			message.setMulti(true);
			message.setState(Message.STATE_BEGIN);
			session.getBasicRemote().sendText(CommonFun.object2Json(message));

			for (Message bi : blobs) {
				ByteBuffer bb = ByteBuffer.wrap(bi.getBlob().get());
				bi.getBlob().setFile(null);
				bi.setState(Message.STATE_BEGIN);
				Map<String, Object> t = bi.serialize();
				session.getBasicRemote().sendText(CommonFun.object2Json(t));
				session.getBasicRemote().sendBinary(bb);
				t.put("state", Message.STATE_END);
				session.getBasicRemote().sendText(CommonFun.object2Json(t));
			}

			Message end = new Message();
			end.setId(message.getId());
			end.setType(Message.TYPE_TEXT);
			end.setState(Message.STATE_END);
			end.setMulti(message.isMulti());
			session.getBasicRemote().sendText(CommonFun.object2Json(end));
		}

	}

	private static void computeblob(String key, Map<String, Object> parent, List<String> path, Message message, List<Message> blobs) {
		List<String> np = new ArrayList<String>(path);
		np.add(key);
		if (parent.get(key) instanceof BlobItem) {
			Message blob = new Message();
			blob.setId("_blob_" + CommonFun.getSSID());
			blob.setType(Message.TYPE_BLOB);
			blob.setState(Message.STATE_BEGIN);
			blob.setMsgid(message.getId());
			blob.setPath(np);
			blob.setBlob((BlobItem) parent.get(key));
			blobs.add(blob);
			parent.put(key, blob.getId());
		} else if (parent.get(key) instanceof File) {
			Message blob = new Message();
			blob.setId("_blob_" + CommonFun.getSSID());
			blob.setType(Message.TYPE_BLOB);
			blob.setState(Message.STATE_BEGIN);
			blob.setMsgid(message.getId());
			blob.setPath(np);
			blob.setBlob(new BlobItem((File) parent.get(key)));
			blobs.add(blob);
			parent.put(key, blob.getId());
		} else if (parent.get(key) instanceof Map) {
			Map<String, Object> m = (Map<String, Object>) parent.get(key);
			Iterator<String> keys = m.keySet().iterator();
			while (keys.hasNext()) {
				String k = keys.next();
				computeblob(k, m, np, message, blobs);
			}
		}
	}
	private void sendHeartMessage() throws Exception{
		onconnect = false;
		
		Message message = null;
		message = new Message();
		message.setId(CommonFun.getSSID());
		message.setType("heartbeat");
		message.setState(Message.STATE_END);
		message.setTo(null);
		message.setMulti(false);
		session.getBasicRemote().sendText(CommonFun.object2Json(message));
	}
	protected class HeartSend extends Thread{
		@Override
		public void run() {
			try {
				sleep(60000);
				if(onconnect){
					sendHeartMessage();
				}
				sleep(60000);
				if(!onconnect){
					init();
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
