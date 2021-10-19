package cn.crtech.precheck.ipc.ws.client;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
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

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.HttpClient;
import cn.crtech.cooperop.ipc.dao.CommentDao;
import cn.crtech.cooperop.ipc.service.AutoAuditService;
import cn.crtech.precheck.ipc.ws.bean.BlobItem;
import cn.crtech.precheck.ipc.service.DataService;
import cn.crtech.precheck.ipc.ws.bean.Message;

@ClientEndpoint
public class Connection {
	
	private static String LOGIN_URL;
	private static String LOGOUT_URL;
	private static String WS_URL;
	public static void init() throws Throwable {
		String server_url = GlobalVar.getSystemProperty("yun.server_url");
		LOGIN_URL = server_url + "/w/iadscp_yun/iadscp_yun/hospitallogin.json";
		LOGOUT_URL = server_url + "/w/iadscp_yun/iadscp_yun/yxkclientlogout.json";
		if (server_url.indexOf("https://") == 0) {
			WS_URL = server_url.replaceAll("https://", "wss://");
		} else {
			WS_URL = server_url.replaceAll("http://", "ws://");
		}
		WS_URL += "/ws";
		//connect();
	}
	public void send(Map<String, Object> params) throws Throwable {
		sendMessage(params);
	}

	private static Map<String, Session> sessions = new HashMap<String, Session>();
	private static Map<String, Map<String,Object>> logininfos = new HashMap<String, Map<String,Object>>();
	private boolean onconnect;
	public static Map<String, Object> reMap = new HashMap<String, Object>();
	public static Map<String, Object> FlagMap = new HashMap<String, Object>();
	public Connection(){
		
	}
	private String doctor_no;
	private String hospital_id;
	//private String password;
	public Connection(String doctor_no, String hospital_id) throws Throwable {
		this.doctor_no = doctor_no;
		this.hospital_id = hospital_id;
		newConnect();
		new HeartSend().start();
	}

	public void newConnect() throws Throwable {
		//if(CommonFun.isNe(sessions.get(doctor_no))){
			//JSONP登录获取token
		log.debug("create newConnect ===[yuan to yun]== "+doctor_no);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("doctor_no", doctor_no);
			params.put("hospital_id", hospital_id);
			Map<String, Object> logininfo = CommonFun.json2Object(HttpClient.post(LOGIN_URL, params), Map.class);
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			URI url=new URI(WS_URL + "?" + logininfo.get("app_key") + "=" + logininfo.get("app_id") + "&" + logininfo.get("token_key") + "=" + logininfo.get("ws_token"));
			Session session = container.connectToServer(Connection.class, url);
			sessions.put(doctor_no, session);
			logininfos.put(doctor_no, logininfo);
		//}
		onconnect = true;
	}
	
	public void disconnect() throws Throwable {
		sessions.get(doctor_no).close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "消息发送完毕，主动断开"));
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> logininfo = logininfos.get(doctor_no);
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
			 int wait_time = Integer.parseInt(SystemConfig.getSystemConfigValue("hospital_common", "wait_time", "30"));
			if(!CommonFun.isNe(params)){
				 if("yxkjiedan".equals(params.get("action"))){
					 if(wait_time != 0) {
						 Map<String, Object> m = (Map<String, Object>) params.get("data");
						 reMap.remove("return_J_"+(String)FlagMap.get("flag_J_"+doctor_no));
						 reMap.put("return_J_"+(String)m.get("id"), m);
						 FlagMap.put("flag_J_"+doctor_no, m.get("id"));
					 }
				 }else if("yxkauditresult".equals(params.get("action"))){
					 Map<String, Object> m = (Map<String, Object>) params.get("data");
					 String auto_audit_id = "";
					 if (new AutoAuditService().isEnd(m)) {
					 if(wait_time != 0) {
						 reMap.remove("return_A_"+(String)FlagMap.get("flag_A_"+doctor_no));
						 reMap.put("return_A_"+(String)m.get("id"), m);
						 FlagMap.put("flag_A_"+doctor_no, m.get("id"));
					 }
					 /*if(!"D".equals(m.get("state"))){
						 //插入消息表中
						 Map<String, Object> p = new HashMap<String, Object>();
						 p.put("content", "");
						 p.put("subject", "");
						 p.put("type", "3");
						 p.put("state", 0);
						 p.put("system_product_code", "ipc");
						 p.put("sendto_", doctor_no);
						 new SystemMessageService().insert(p);
					 }*/
					 m.put("pharmacist_exam_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
					 auto_audit_id = (String)m.get("id");
					 //params.put("id", Integer.parseInt((String)params.remove("id")));
//					 m.remove("yaoshi_id");
					 new DataService().update(m);
					 }
					 m.put("id", auto_audit_id);
					 Client.pharmacist_result_return("admin_jq", m);
				 }else if("yisjueding".equals(params.get("action"))){//app的医生决定（双签名、取消）
					 Map<String, Object> m = (Map<String, Object>) params.get("data");
					 //reMap.put("return_S_"+(String)m.get("id"), m);
					 new DataService().updateDoctorResult(m);
				 }else if("yaoshijieguo".equals(params.get("action"))){//app的医生确认药师的结果
					 Map<String, Object> m = (Map<String, Object>) params.get("data");
					 //reMap.put("return_S_"+(String)m.get("id"), m);
					 new DataService().updateResult(m);
				 }/*else if("sysncdata".equals(params.get("action"))){
					 Map<String, Object> m = (Map<String, Object>) params.get("data");
					 new DataService().updateDoctors(m);
				 }*/
				 else if ("pharmacist_comment".equals(params.get("action"))) {
					 Map<String, Object> m = (Map<String, Object>) params.get("data");
					 new DataService().insertComment(m);
				 }else if ("check_result_info".equals(params.get("action"))) {
					 Map<String, Object> m = (Map<String, Object>) params.get("data");
					 Map<String, Object> re = new HashMap<String, Object>();
					 re.put("auto_audit_id", m.remove("auto_audit_id"));
					 re.put("check_results", 21);
					 new CommentDao().saveSetHLLS(re);
					 new DataService().updateCheckResultInfo(m);
				 }else if ("end_reply".equals(params.get("action"))) {
					 Map<String, Object> m = (Map<String, Object>) params.get("data");
					 //new AutoAuditService().updateMessageEndTime(m);
				 }
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
		log.debug(params.get("action")+"===isopen===="+sessions.get(doctor_no).isOpen());
		if (blobs.size() == 0) {
			sessions.get(doctor_no).getBasicRemote().sendText(CommonFun.object2Json(message));
		} else {
			message.setMulti(true);
			message.setState(Message.STATE_BEGIN);
			sessions.get(doctor_no).getBasicRemote().sendText(CommonFun.object2Json(message));

			for (Message bi : blobs) {
				ByteBuffer bb = ByteBuffer.wrap(bi.getBlob().get());
				bi.getBlob().setFile(null);
				bi.setState(Message.STATE_BEGIN);
				Map<String, Object> t = bi.serialize();
				sessions.get(doctor_no).getBasicRemote().sendText(CommonFun.object2Json(t));
				sessions.get(doctor_no).getBasicRemote().sendBinary(bb);
				t.put("state", Message.STATE_END);
				sessions.get(doctor_no).getBasicRemote().sendText(CommonFun.object2Json(t));
			}

			Message end = new Message();
			end.setId(message.getId());
			end.setType(Message.TYPE_TEXT);
			end.setState(Message.STATE_END);
			end.setMulti(message.isMulti());
			sessions.get(doctor_no).getBasicRemote().sendText(CommonFun.object2Json(end));
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
		sessions.get(doctor_no).getBasicRemote().sendText(CommonFun.object2Json(message));
	}
	protected class HeartSend extends Thread{
		@Override
		public void run() {
			try {
				while (true) {
					sleep(30000);
					if(onconnect){
						sendHeartMessage();
					}
					sleep(30000);
					if(!onconnect){
						newConnect();
					}
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
